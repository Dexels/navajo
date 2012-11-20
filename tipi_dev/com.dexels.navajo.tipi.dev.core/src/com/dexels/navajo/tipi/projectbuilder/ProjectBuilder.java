package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.projectbuilder.impl.TipiLocalJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiRemoteJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiWebProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class ProjectBuilder {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ProjectBuilder.class);
	
	
	private static void downloadExtensionJars(File projectPath, String extensions,String extensionRepository, boolean onlyProxy, boolean clean, String buildType, boolean useVersioning, boolean localSign) throws IOException {
		if(!extensionRepository.endsWith("Extensions/")) {
			extensionRepository = extensionRepository+ "Extensions/";
		}
		StringTokenizer st = new StringTokenizer(extensions, ",");
		// Probably dead code, but I'm not completely convinced.
		ClientActions.getExtensions(extensionRepository);
		while (st.hasMoreTokens()) {
				String token = st.nextToken();
				VersionResolver vr = new VersionResolver();
				vr.load(extensionRepository);
				Map<String,String> versionMap = vr.resolveVersion(token);
				String ext = versionMap.get("extension");
				String version = versionMap.get("version");
				XMLElement extensionXml = ClientActions.getExtensionXml(ext, version, extensionRepository);
				TipiProjectBuilder tpb = null;
				if("web".equals(buildType)) {
					tpb = new TipiWebProjectBuilder();
				} else {
					if (onlyProxy) {
						tpb = new TipiRemoteJnlpProjectBuilder();
					} else {
						tpb = new TipiLocalJnlpProjectBuilder();
					}
					
				}
				tpb.setUseVersioning(useVersioning);
				tpb.downloadExtensionJars(ext, version, new URL(extensionRepository+vr.resultVersionPath(token)+"/"), extensionXml, projectPath, clean, localSign);
			
		}
	}
	
//	public static void deleteLocalTipiBuild(File baseDir) {
//		File lib = new File(baseDir,"lib");
//
//		if(lib.exists()) {
//			File[] cc = lib.listFiles();
//			for (int i = 0; i < cc.length; i++) {
//				cc[i].delete();
//			}
//			lib.delete();
//		}
//	}
//	// to be refactored:
//	public static void deleteRemoteTipiBuild(File baseDir, String profile) {
//
//	}

	// Used for server side appstore building


	public static String getCurrentDeploy(File projectPath) throws IOException {
		File path = new File(projectPath, "settings/tipi.properties");
		Map<String,String> map = parsePropertyFile(path);
		return map.get("deploy");
	}
	
	public static Map<String,String> assembleTipi(File projectPath) throws IOException {
		logger.info("Project patH: "+projectPath.getAbsoluteFile());
//		FileInputStream is = new FileInputStream(new File(projectPath,"settings/tipi.properties"));
//		PropertyResourceBundle tipiProperties = new PropertyResourceBundle(is);		
//		is.close();	
		File path = new File(projectPath, "settings/tipi.properties");
		Map<String,String> tipiPropertyMap = parsePropertyFile(path);


		


		File deploymentFolder = new File(projectPath,"settings/deploy/");
		Set<String> deployments = new HashSet<String>();

		logger.info("Deployment folder detected: "+deploymentFolder.getAbsolutePath());
		if(deploymentFolder.exists() && deploymentFolder.isDirectory()) {
			File[] deployCandidates = deploymentFolder.listFiles();
			String selectedDeploy = tipiPropertyMap.get("deploy");
			if(selectedDeploy!=null) {
				for (File candidate : deployCandidates) {
					logger.info("Current: "+candidate.getName());
					if(candidate.getName().endsWith(".properties") && candidate.isFile()) {
						String currentDeploymentName = candidate.getName().substring(0,candidate.getName().length()-".properties".length());
						deployments.add(currentDeploymentName);
						logger.info("     : "+currentDeploymentName);
						if(currentDeploymentName.equals(selectedDeploy)) {
							logger.info("Deployment found!");
							Map<String,String> res = parsePropertyFile(candidate);
							tipiPropertyMap.putAll(res);
						}
					}
				}
			}
		}	
		return tipiPropertyMap;
		
	}
	
	public static String buildTipiProject(File projectPath, String codebase, String deployment) throws IOException {
		
		Map<String,String> tipiPropertyMap = assembleTipi(projectPath);
		
		String extensions = tipiPropertyMap.get("extensions").trim();
		String repository = tipiPropertyMap.get("repository").trim();
		String extensionRepository = repository; //+"Extensions/";
		String developmentRepository = repository+"Development/";

		
		List<String> profiles = getProfiles(projectPath);
		
		String buildType = tipiPropertyMap.get("build");
		if(buildType==null) {
			buildType = "remote";
		} else {
			buildType = buildType.trim();
		}
		
		boolean clean = false;
		if(codebase==null) {
			codebase = "$$codebase";
		}
		logger.info("PRofiles: "+profiles);
		String keystore = null;
		keystore = tipiPropertyMap.get("keystore");
		boolean resign = keystore!=null;
		downloadExtensionJars(projectPath, extensions, extensionRepository,false,clean,buildType,false,resign);
		
		if(deployment==null) {
			deployment = tipiPropertyMap.get("deploy");			
		}

	
		
		String postProcessAnt = null;
		if(profiles==null || profiles.isEmpty()) {
			postProcessAnt = buildProfileDescriptor(null,clean,  tipiPropertyMap,deployment,projectPath, codebase, extensions, extensionRepository,developmentRepository, buildType,true);
		} else {
			postProcessAnt = buildProfileDescriptor(profiles,clean,tipiPropertyMap,deployment, projectPath, codebase, extensions , extensionRepository, developmentRepository,buildType,true);
//			for (String profile : profiles) {
//				// TODO: Beware, multiple profiles in Echo / Web will not work
//				logger.info("Building profile: "+profile);
//			}
		}

		return postProcessAnt;
//		try {
//			String cp = pe.getString("buildClasspath");
//			if("true".equals(cp)) {
//				buildClassPath(projectPath, repository, extensions);
//			}
//		} catch (MissingResourceException e) {
//		}
	}

	public static List<String> getProfiles(File projectPath) {
		List<String> profiles = new LinkedList<String>();
		File profileFolder = new File(projectPath,"settings/profiles/");
		logger.info("Profile folder detected: "+profileFolder.getAbsolutePath());
		if(profileFolder.exists() && profileFolder.isDirectory()) {
			File[] profileCandidates = profileFolder.listFiles();
			for (File candidate : profileCandidates) {
				logger.info("Current: "+candidate.getName());
				if(candidate.getName().endsWith(".properties") && candidate.isFile()) {
					// ewwwww
					String currentProfileName = candidate.getName().substring(0,candidate.getName().length()-".properties".length());
					
					profiles.add(currentProfileName);
					logger.info("     : "+currentProfileName);
				}
			}
		}
		return profiles;
	}


	private static Map<String, String> parsePropertyFile(File path) throws FileNotFoundException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		FileInputStream fr = new FileInputStream(path);

		PropertyResourceBundle p = new PropertyResourceBundle(fr);
		fr.close();
		Enumeration<String> eb = p.getKeys();
		while (eb.hasMoreElements()) {
			String string = eb.nextElement();
			params.put(string, p.getString(string));
		}
		logger.info("params: " + params);
		return params;
	}

	private static String buildProfileDescriptor(List<String> profiles,  boolean clean,Map<String,String> tipiProperties, String deployment, File projectPath, String projectUrl, String extensions,
			String repository,String developmentRepository, String buildType, boolean useVersioning) throws IOException {
		
		logger.info("Writing in: "+projectPath.getAbsolutePath());
		// TODO: Don't think we need this one!
		downloadExtensionJars(projectPath, extensions, repository,false,clean,buildType,useVersioning,false);

//		String profileName = null;
//		if(profiles==null || profiles.isEmpty()) {
//			profileName = "Default";
//		} 
//		profiles==null?"Default":profiles;

		String postProcessAnt = null;
		if("remote".equals(buildType)) {
			BaseDeploymentBuilder r = new RemoteJnlpBuilder();
			downloadExtensionJars(projectPath, extensions, repository,true,clean,buildType,useVersioning,false);
			postProcessAnt = r.build(repository,developmentRepository, extensions,tipiProperties,deployment,projectPath, projectUrl,profiles,useVersioning);
		}
		if("local".equals(buildType) ) {
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			postProcessAnt = l.build(repository,developmentRepository, extensions,tipiProperties,deployment,projectPath, projectUrl,profiles,useVersioning);
		}
		if("web".equals(buildType)) {
			BaseDeploymentBuilder l = new WebDescriptorBuilder();
			postProcessAnt = l.build(repository,developmentRepository, extensions,tipiProperties,deployment,projectPath, projectUrl,profiles,useVersioning);
		}
		
		return postProcessAnt;
	}

	
	public static void main(String[] args) throws IOException {
		ProjectBuilder.buildTipiProject(new File("/Users/frank/Documents/Spiritus/SportlinkOfficialPortal"), "aap","somedeploy");
	}

}
