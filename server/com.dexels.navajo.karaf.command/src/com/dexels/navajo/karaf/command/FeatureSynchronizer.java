package com.dexels.navajo.karaf.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.features.FeaturesService.Option;
import org.apache.karaf.features.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureSynchronizer implements Runnable {
	private FeaturesService featureService;
	private Map<String, Object> settings;
	private boolean running = false;
	private Properties propertyFile = new Properties();
	
	private final static Logger logger = LoggerFactory
			.getLogger(FeatureSynchronizer.class);
	
	private Thread updateThread = null;
	private String cacheDir = null;
	private final Set<URI> owned = new HashSet<URI>();
	
	public void setFeaturesService(FeaturesService featureService) {
		this.featureService = featureService;
	}
	
	public void clearFeaturesService(FeaturesService featureService) {
		this.featureService = null;
	}
	
	
	public void activate(Map<String,Object> settings) throws IOException {
		this.settings = settings;
		updateThread = new Thread(this);

		this.cacheDir  = (String) settings.get("cacheDir");
		loadOwnedFeatures();
		logger.info("Preload owned features: "+owned);
		boolean start = "true".equals((String) settings.get("start"));
		if(start) {
			this.running = true;
			updateThread.start();
			logger.info("Sync started!");
		} else {
			logger.warn("Start setting not set, not starting synchronize thread.");
		}
	}

	private void loadOwnedFeatures() throws FileNotFoundException, IOException {
		File cacheFolder = new File(cacheDir);
		if(!cacheFolder.exists()) {
			cacheFolder.mkdirs();
		}
		File ownershipFile = new File(cacheFolder,"ownedfeatures.properties");
		if(ownershipFile.exists()) {
			try(FileReader fr = new FileReader(ownershipFile)) {
				propertyFile.load(fr);
				String rep = propertyFile.getProperty("ownedrepos");
				owned.clear();
				if(rep!=null) {
					String[] reps = rep.split(",");
					for (String r : reps) {
						try {
							owned.add(new URI(r));
						} catch (URISyntaxException e) {
							logger.error("Bad URI syntax: "+r, e);
						}
					}
				}
			}
			
		}
	}
	
	private void saveOwnedFeatures() throws FileNotFoundException, IOException {
		File cacheFolder = new File(cacheDir);
		File ownershipFile = new File(cacheFolder,"ownedfeatures.properties");
		boolean first = true;
		StringBuffer sb = new StringBuffer();
		for (URI uri : owned) {
			if(!first) {
				sb.append(",");
			} else {
				first = false;
			}
			sb.append(uri);
		}
		propertyFile.setProperty("ownedrepos", sb.toString());
		try (FileWriter fw = new FileWriter(ownershipFile)) {
			propertyFile.store(fw, "Owned by the feature synchronizer");
		}
	}

	public void deactivate() {
		this.running = false;
		if(updateThread!=null) {
			updateThread.interrupt();
		}
	}
	
	public synchronized void updateFeatures() throws IOException {
		logger.debug("Scanning for feature updates");
		String repos = (String) settings.get("repo");
		updateRepos(repos);
		String installed = (String) settings.get("installed");
		String uninstalled = (String) settings.get("uninstalled");
		if(installed!=null) {
			String[] ins = installed.split(",");
			for (String in : ins) {
				ensureInstalled(in.trim());
			}
		}
		if(uninstalled!=null) {
			String[] uns = uninstalled.split(",");
			for (String in : uns) {
				ensureUninstalled(in);
			}
		}
	}

	private synchronized void updateRepos(String repos) {
		if(repos==null) {
			logger.warn("No repositories mentioned. Leaving repositories alone.");
			return;
		}
		final Set<URI> added = new HashSet<URI>();

		final Map<URI,Repository> present = new HashMap<URI,Repository>();
		final Repository[] r = featureService.listRepositories();
		for (Repository repository : r) {
			present.put(repository.getURI(),repository);
		}

		final Set<URI> encountered = new HashSet<URI>();
		final Set<Feature> attemptInstall = new HashSet<Feature>();
		final String[] repolist = repos.split(",");
		for (String repo : repolist) {
			URI repoURI;
			try {
				
				repoURI = new URI(repo);
				encountered.add(repoURI);
				if(!present.containsKey(repoURI)) {
					// owned should be persistent
					owned.add(repoURI);
					added.add(repoURI);
					featureService.addRepository(repoURI, false);
					logger.info("Adding repo: "+repo);

				} else {
					logger.debug("repo: "+repo+" is already present");
					Repository presentRepo = present.get(repoURI);
					if(presentRepo==null) {
						logger.warn("Repo with URI: "+repoURI+" is supposed to be already present, but I can't find it. Continuing.");
						continue;
					}
					Feature[] presentFeatures = presentRepo.getFeatures();
					for (Feature feature : presentFeatures) {
						if(!featureService.isInstalled(feature)) {
							logger.info("Feature: "+feature.getName()+" is in an already present repo (and requested), but it hasn't been installed. Will retry.");
							attemptInstall.add(feature);
						}
					}
				}
				// perhaps we need to wait installing until all repo's have been added. Don't know
				// owned - encountered = orphaned
				// remove orphaned
				// install all features in added
			} catch (URISyntaxException e) {
				logger.error("Error: ", e);
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}
		if(added.size()>0) {
			logger.info("# of added repos: "+added.size());
		}
		Set<URI> orphaned = new HashSet<URI>(owned);
		orphaned.removeAll(encountered);
		if(orphaned.size()>0) {
			logger.info("# of added repos to remove: "+orphaned.size());
		}
		for (URI uri : orphaned) {
			try {
				logger.info("repo with uri: "+uri+" is owned, and is now orphaned. Will remove.");
				owned.remove(uri);
				Repository toremove = present.get(uri);
				if(toremove!=null) {
					for (Feature feature : toremove.getFeatures()) {
						if(featureService.isInstalled(feature)) {
							featureService.uninstallFeature(feature.getName(),feature.getVersion());
						}
					}
				}
				featureService.removeRepository(uri, false);
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}
		Map<URI,Repository> presentNow = new HashMap<URI,Repository>();
		Repository[] r2 = featureService.listRepositories();
		for (Repository repository : r2) {
			presentNow.put(repository.getURI(),repository);
		}
		for (URI uri : added) {
			Repository a = presentNow.get(uri);
			if(a==null) {
				logger.warn("Repository with URI: "+uri+" should have been added, but is not available.");
				continue;
			}
			logger.info("Installing all features in repository: "+a.getName());
			try {
				Feature[] f = a.getFeatures();
				Set<Feature> features = new HashSet<Feature>();
				for (Feature feature : f) {
					if(!featureService.isInstalled(feature)) {
						features.add(feature);
					}
				}
				featureService.installFeatures(features, EnumSet.noneOf(Option.class));
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
			
		}
		if(attemptInstall.size()>0) {
			logger.info("Now attempting previous missing features:");
		}
		for (Feature reattempt : attemptInstall) {
			if(!featureService.isInstalled(reattempt)) {
				try {
					logger.info("Installing: "+reattempt.getName());
					featureService.installFeature(reattempt, EnumSet.noneOf(Option.class));
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			}
		}
		try {
			saveOwnedFeatures();
		} catch (FileNotFoundException e) {
			logger.error("Error: ", e);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

		logger.debug("All done");
	}

	private void ensureInstalled(String feature) {
		logger.debug("Ensuring installation of: "+feature);
		try {
			Feature f = featureService.getFeature(feature);
			if(f==null) {
				logger.warn("Should install: {}, but it seems unavailable. Can't be helped now, will keep trying.",feature);
			} else {
				
//				logger.info("Getting install>"+f.getInstall()+"< id: >"+f.getId()+"<");
				if(featureService.isInstalled(f)) {
					logger.debug("Feature: {} is installed. Good.",feature);
				} else {
					logger.info("Feature {} is not installed, installing.",feature);
					featureService.installFeature(feature);
				}
				
			}
			saveOwnedFeatures();
		} catch (Exception e2) {
			logger.error("Error: ", e2);
		}
	}
	
	private void ensureUninstalled(String feature) {
		try {
			Feature f = featureService.getFeature(feature);
			if(f==null) {
				logger.debug("Want to uninstall feature {}, but seems unavailable. Assuming all is well.",feature);
			} else {
				
				if(featureService.isInstalled(f)) {
					logger.info("Feature {} is installed, uninstalling. ",feature);
					featureService.uninstallFeature(feature);
				} else {
					logger.debug("Feature: {} is not installed: ok",feature);
				}
				
			}
		} catch (Exception e2) {
			logger.error("Error: ", e2);
		}
	}


	@Override
	public void run() {
		while(running) {
			try {
				long now = System.currentTimeMillis();
				updateFeatures();
				long elapsed = System.currentTimeMillis() - now;
				logger.debug("Feature sync iteration took: "+elapsed+" millis.");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
		}
	}

}
