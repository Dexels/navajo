package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.projectbuilder.ClasspathBuilder;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.RemoteJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.VersionResolver;
import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiLocalJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiRemoteJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiWebProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class ProjectBuilder {
	private static void buildProfileJnlp(String profile,  boolean clean, File projectPath, String projectUrl, String extensions,
			String extensionRepository,String developmentRepository, String buildType) throws IOException {
		System.err.println("Building jnlp for extensionrep:"+extensionRepository);
		String profileName = profile==null?"Default":profile;
		if("remote".equals(buildType) ) {
			deleteLocalTipiBuild(projectPath,profile);
		}
		if("local".equals(buildType) ) {
			deleteRemoteTipiBuild(projectPath,profile);
		}
		if("remote".equals(buildType) || "both".equals(buildType)) {
			RemoteJnlpBuilder r = new RemoteJnlpBuilder();
			downloadExtensionJars(projectPath, extensions, extensionRepository,true,clean,buildType);
			r.build(extensionRepository,developmentRepository, extensions,projectPath, projectUrl,profileName+"Remote.jnlp",profile);
		}
		if("local".equals(buildType) || "both".equals(buildType)) {
			downloadExtensionJars(projectPath, extensions, extensionRepository,false,clean,buildType);
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			l.build(extensionRepository,developmentRepository, extensions,projectPath, projectUrl,profileName+"Local.jnlp",profile);
		}
	}	
	
//	private static void downloadExtensionJars(File projectPath, String extensions,
//			String repository, boolean onlyProxy, boolean clean) throws IOException {
//		StringTokenizer st = new StringTokenizer(extensions, ",");
//		Map<String,List<String>> repDefinition= ClientActions.getExtensions(repository);
//		while (st.hasMoreTokens()) {
//			try {
//				String token = st.nextToken();
//				VersionResolver vr = new VersionResolver();
//				vr.load(repository);
//				Map<String,String> versionMap = vr.resolveVersion(token);
//				String ext = versionMap.get("extension");
//				String version = versionMap.get("version");
//				XMLElement extensionXml = ClientActions.getExtensionXml(ext, version, repository);
//				if (onlyProxy) {
//					ClientActions.downloadProxyJars(ext,new URL(repository+ext+"/"),extensionXml,projectPath,clean);
//				} else {
//					ClientActions.downloadExtensionJars(ext,new URL(repository+vr.resultVersionPath(token)+"/"),extensionXml,projectPath,clean);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	private static void downloadExtensionJars(File projectPath, String extensions,String repository, boolean onlyProxy, boolean clean, String buildType) throws IOException {
		StringTokenizer st = new StringTokenizer(extensions, ",");
		Map<String,List<String>> repDefinition= ClientActions.getExtensions(repository);
		while (st.hasMoreTokens()) {
			try {
				String token = st.nextToken();
				VersionResolver vr = new VersionResolver();
				vr.load(repository);
				Map<String,String> versionMap = vr.resolveVersion(token);
				String ext = versionMap.get("extension");
				String version = versionMap.get("version");
				XMLElement extensionXml = ClientActions.getExtensionXml(ext, version, repository);
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
				tpb.downloadExtensionJars(ext, new URL(repository+vr.resultVersionPath(token)+"/"), extensionXml, projectPath, clean);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void deleteLocalTipiBuild(File baseDir, String profile) {
		File lib = new File(baseDir,"lib");

		if(lib.exists()) {
			File[] cc = lib.listFiles();
			for (int i = 0; i < cc.length; i++) {
				cc[i].delete();
			}
			lib.delete();
		}
	}
	// to be refactored:
	public static void deleteRemoteTipiBuild(File baseDir, String profile) {

	}
	private static void buildClassPath(File projectDir, String repository, String extensions) {	
		try {
			ClasspathBuilder cb = new ClasspathBuilder();
			cb.build(repository, extensions,projectDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void rebuildXsd(String repository, String extensions, File baseDir) {
		XsdBuilder b = new XsdBuilder();
		try {
			b.build(repository, extensions, baseDir);
			System.err.println("XSD rebuilt!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void buildTipiProject(File projectPath) throws IOException {
		FileInputStream is = new FileInputStream(new File(projectPath,"settings/tipi.properties"));
		PropertyResourceBundle pe = new PropertyResourceBundle(is);
		is.close();	
		String extensions = pe.getString("extensions").trim();
		String repository = pe.getString("repository").trim();

		String extensionRepository = repository+"Extensions/";
		String developmentRepository = repository+"Development/";

		List<String> profiles = new LinkedList<String>();
//		File profileFolder = new File(projectPath,"settings/profile/"+profile+".properties");
//		
//		if(profileFolder.exists() && profileFolder.isDirectory()) {
//			File[] profileCandidates = profileFolder.listFiles();
//			for (File candidate : profileCandidates) {
//				if(candidate.getName().endsWith(".properties") && candidate.isFile()) {
//					// ewwwww
//					String currentProfileName = candidate.getName().substring(0,candidate.getName().length()-".properties".length());
//					
//					profiles.add(currentProfileName);
//					System.err.println("Adding profile: "+currentProfileName);
//				}
//			}
//		}

		String buildType = pe.getString("build").trim();

//		if(clean) {
//			File libDir = new File(projectPath,"lib");
//			libDir.delete();		
//		}
		
//		if(!skipXsd) {
//			rebuildXsd(extensionRepository,extensions,projectPath);
//			
//		}
		if(buildType==null) {
			buildType = "remote";
		}
		

			downloadExtensionJars(projectPath, extensions, repository,false,false,buildType);
		

		File[] cc = projectPath.listFiles();
		for (int i = 0; i < cc.length; i++) {
			if(cc[i].getName().endsWith(".jnlp")) {
				cc[i].delete();
			}
		}
		
//		if(profiles==null || profiles.isEmpty()) {
//	//		buildProfileJnlp(null,clean,  projectPath, projectUrl, extensions, extensionRepository,developmentRepository, buildType);

//		} else {
//			for (String profile : profiles) {
//				System.err.println("Building profile: "+profile);
//				buildProfileJnlp(profile,clean, projectPath, projectUrl, extensions , extensionRepository, developmentRepository,buildType);
//				
//			}
//		}

		
//		try {
//			String cp = pe.getString("buildClasspath");
//			if("true".equals(cp)) {
//				buildClassPath(projectPath, repository, extensions);
//			}
//		} catch (MissingResourceException e) {
//		}
	}
}
