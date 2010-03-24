package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.projectbuilder.impl.TipiLocalJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiRemoteJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiWebProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class ProjectBuilder {
	
	
	private static void downloadExtensionJars(File projectPath, String extensions,String extensionRepository, boolean onlyProxy, boolean clean, String buildType, boolean useVersioning, boolean localSign) throws IOException {
		if(!extensionRepository.endsWith("Extensions/")) {
			extensionRepository = extensionRepository+ "Extensions/";
		}
		StringTokenizer st = new StringTokenizer(extensions, ",");
		Map<String,List<String>> repDefinition= ClientActions.getExtensions(extensionRepository);
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
	
	public static String buildTipiProject(File projectPath, String codebase) throws IOException {
		System.err.println("Project patH: "+projectPath.getAbsoluteFile());
		FileInputStream is = new FileInputStream(new File(projectPath,"settings/tipi.properties"));
		PropertyResourceBundle pe = new PropertyResourceBundle(is);
		is.close();	
		String extensions = pe.getString("extensions").trim();
		String repository = pe.getString("repository").trim();

		String extensionRepository = repository; //+"Extensions/";
		String developmentRepository = repository+"Development/";

		List<String> profiles = new LinkedList<String>();
		File profileFolder = new File(projectPath,"settings/profiles/");
		System.err.println("Profile folder detected: "+profileFolder.getAbsolutePath());
		if(profileFolder.exists() && profileFolder.isDirectory()) {
			File[] profileCandidates = profileFolder.listFiles();
			for (File candidate : profileCandidates) {
				System.err.println("Current: "+candidate.getName());
				if(candidate.getName().endsWith(".properties") && candidate.isFile()) {
					// ewwwww
					String currentProfileName = candidate.getName().substring(0,candidate.getName().length()-".properties".length());
					
					profiles.add(currentProfileName);
					System.err.println("     : "+currentProfileName);
				}
			}
		}

		String buildType = pe.getString("build").trim();

//		if(clean) {
//			File libDir = new File(projectPath,"lib");
//			libDir.delete();		
//		}http://spiritus.dexels.nl:8080/TipiServer/editor.jsp?application=SportlinkClub&filePath=settings/tipi.properties
		
//		if(!skipXsd) {
//			rebuildXsd(extensionRepository,extensions,projectPath);
//			
//		}
		if(buildType==null) {
			buildType = "remote";
		}
		

		//	downloadExtensionJars(projectPath, extensions, extensionRepository,false,false,buildType,true);
		

//		File[] cc = projectPath.listFiles();
//		for (int i = 0; i < cc.length; i++) {
//			if(cc[i].getName().endsWith(".jnlp")) {
//				cc[i].delete();
//			}
//		}
		boolean clean = false;
		if(codebase==null) {
			codebase = "$$codebase";
		}
		System.err.println("PRofiles: "+profiles);
		String keystore = null;
		try {
			keystore = pe.getString("keystore");
		} catch (MissingResourceException e) {
			System.err.println("No resource found");
		}
		boolean resign = keystore!=null;
		downloadExtensionJars(projectPath, extensions, extensionRepository,false,clean,buildType,false,resign);
		
		String postProcessAnt = null;
		if(profiles==null || profiles.isEmpty()) {
			postProcessAnt = buildProfileJnlp(null,clean,  projectPath, codebase, extensions, extensionRepository,developmentRepository, buildType,true);
		} else {
			for (String profile : profiles) {
				// TODO: Beware, multiple profiles in Echo / Web will not work
				System.err.println("Building profile: "+profile);
				postProcessAnt = buildProfileJnlp(profile,clean, projectPath, codebase, extensions , extensionRepository, developmentRepository,buildType,true);
			}
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



	private static String buildProfileJnlp(String profile,  boolean clean, File projectPath, String projectUrl, String extensions,
			String repository,String developmentRepository, String buildType, boolean useVersioning) throws IOException {
		
		System.err.println("Writing in: "+projectPath.getAbsolutePath());
		// TODO: Don't think we need this one!
		downloadExtensionJars(projectPath, extensions, repository,false,clean,buildType,useVersioning,false);

		String profileName = profile==null?"Default":profile;
		String postProcessAnt = null;
		if("remote".equals(buildType)) {
			BaseDeploymentBuilder r = new RemoteJnlpBuilder();
			downloadExtensionJars(projectPath, extensions, repository,true,clean,buildType,useVersioning,false);
			postProcessAnt = r.build(repository,developmentRepository, extensions,projectPath, projectUrl,profileName,profile,useVersioning);
		}
		if("local".equals(buildType) ) {
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			postProcessAnt = l.build(repository,developmentRepository, extensions,projectPath, projectUrl,profileName,profile,useVersioning);
		}
		if("web".equals(buildType)) {
			BaseDeploymentBuilder l = new WebDescriptorBuilder();
			postProcessAnt = l.build(repository,developmentRepository, extensions,projectPath, projectUrl,profileName,profile,useVersioning);
		}
		
		return postProcessAnt;
	}

}
