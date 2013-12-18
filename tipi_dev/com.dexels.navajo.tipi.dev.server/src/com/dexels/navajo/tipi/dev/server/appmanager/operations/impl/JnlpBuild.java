package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.ant.AntRun;
import com.dexels.navajo.tipi.dev.ant.LoggingOutputStream;
import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.core.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.RepositoryInstanceWrapper;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.UnsignJarTask;

public class JnlpBuild extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = -325075211700621696L;
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpBuild.class);
	
	public void build(String name) throws IOException {
		RepositoryInstance as = applications.get(name);
		if(as==null) {
			for (RepositoryInstance a: applications.values()) {
				build(a);
			}
			
		} else {
			build(as);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String val = req.getParameter("app");
		if(val!=null) {
			build(val);
		} else {
			build();
		}
		writeValueToJsonArray(resp.getOutputStream(),"build ok");
		
	}
	
	public void activate() {
		System.err.println("Activating!");
	}
	
	public void build() {
		for (RepositoryInstance a: applications.values()) {
			try {
				build(a);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
	}	
	
	@Override
	public void build(RepositoryInstance repoInstance) throws IOException {
		List<String> extraHeaders = new ArrayList<String>();
		extraHeaders.add("Permissions: all-permissions");
		String applicationName = appStoreManager.getApplicationName();
		if(applicationName!=null) {
			extraHeaders.add("Application-Name: "+applicationName);
		}
		String manifestCodebase = appStoreManager.getManifestCodebase();
		if(manifestCodebase!=null) {
			extraHeaders.add("Codebase: "+manifestCodebase);
		} else {
			extraHeaders.add("Codebase: *");
		}
		
		
		File unsigned = new File(repoInstance.getRepositoryFolder(), "unsigned");

		if(unsigned.exists()) {
			FileUtils.deleteDirectory(unsigned);
		}
		if (!unsigned.exists()) {
			unsigned.mkdirs();
		}
		
		File repo = new File(getRepositoryManager().getRepositoryFolder(), "repo");
		File lib = new File(repoInstance.getRepositoryFolder(),"lib");
		if(lib.exists()) {
			FileUtils.deleteDirectory(lib);
		}
		if(!lib.exists()) {
			lib.mkdirs();
		}
		logger.info("Loading application");
		
		RepositoryInstanceWrapper a = new RepositoryInstanceWrapper(repoInstance);
		a.load();
		for (Dependency dd : a.getDependencies()) {
			File localSigned = dd.getFilePathForDependency(repo);
			if(!localSigned.exists()) {
				UnsignJarTask.downloadDepencency(dd,repo, new File(unsigned.getAbsolutePath()),extraHeaders);
				signdependency(dd, a.getSettingString("sign_alias"),  a.getSettingString("sign_storepass"),  new File(repoInstance.getRepositoryFolder(),a.getSettingString("sign_keystore")), repo);
			}
			FileUtils.copyFileToDirectory(localSigned, lib );
		}
		logger.info("Detected dependencies: "+a.getDependencies());
		LocalJnlpBuilder jj = new LocalJnlpBuilder();
		jj.buildFromMaven(a.getSettingsBundle(),a.getDependencies(),repoInstance.getRepositoryFolder(),a.getProfiles(),"",appStoreManager.getCodeBase(),repoInstance.getRepositoryName());
		for (String profile : a.getProfiles()) {
			createSignedJnlpJar(repoInstance.getRepositoryFolder(),profile,a);
		}
		//		signall(a);
	}

	private void createSignedJnlpJar(File repositoryFolder, String profile,RepositoryInstanceWrapper repositoryInstance) throws IOException {
		File f = new File(repositoryFolder,"tmp");
		f.mkdir();
		File metaInf = new File(f,"META-INF");
		metaInf.mkdirs();
		File manifest = new File(metaInf,"MANIFEST.MF");
		FileWriter maniWriter = new FileWriter(manifest);
		maniWriter.write("Permissions: all-permissions\r\n");
		maniWriter.write("Codebase: *\r\n");
		maniWriter.write("Application-Name: "+profile+"\r\n");
		maniWriter.flush();
		maniWriter.close();

		File jnlpInf = new File(f,"JNLP-INF");
		jnlpInf.mkdirs();
		File jnlpFile = new File(repositoryFolder,profile+".jnlp");
		FileUtils.copyFile(jnlpFile, new File(jnlpInf,"APPLICATION.JNLP"));
		
		File libFolder = new File(repositoryFolder,"lib");
		File jarFile = new File(libFolder,profile+"_jnlp.jar");
		addFolderToJar(f, null, jarFile, f.getAbsolutePath()+"/");
		f.delete();
//		signdependency(dd, a.getSettingString("sign_alias"),  a.getSettingString("sign_storepass"),  new File(repoInstance.getRepositoryFolder(),a.getSettingString("sign_keystore")), repo);

		signJnlp(jnlpFile,profile, repositoryInstance.getSettingString("sign_alias"),  repositoryInstance.getSettingString("sign_storepass"),new File(repositoryFolder,repositoryInstance.getSettingString("sign_keystore")), libFolder);
		
		
	}
	
	private void addFolderToJar(File folder,
			ZipArchiveOutputStream jarOutputStream, File jarFile,
			String baseName) throws IOException {
		boolean toplevel = false;
		if (jarOutputStream == null) {
			jarOutputStream = new ZipArchiveOutputStream(jarFile);
			toplevel = true;
		}
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				addFolderToJar(file, jarOutputStream, jarFile, baseName);
			} else {
				String name = file.getAbsolutePath().substring(
						baseName.length());
				ZipArchiveEntry zipEntry = new ZipArchiveEntry(name);
				jarOutputStream.putArchiveEntry(zipEntry);
				FileInputStream input = new FileInputStream(file);
				IOUtils.copy(input, jarOutputStream);
				input.close();
				jarOutputStream.closeArchiveEntry();
			}
		}
		if (toplevel) {
			jarOutputStream.flush();
			jarOutputStream.close();
		}
	}

	private void signJnlp(File jnlpFile,String profile, String alias, String storepass, File keystore, File baseDir) {
		Map<String,String> props = new HashMap<String, String>();
		try {
			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
			props.put("storepass",storepass);
			props.put("alias", alias);
			props.put("profile", profile);
			props.put("keystore",keystore.getAbsolutePath());
			props.put("jnlpFile", jnlpFile.getAbsolutePath());
			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
			AntRun.callAnt(JnlpBuild.class.getClassLoader().getResourceAsStream("ant/signjnlp.xml"), baseDir, props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	private void signdependency(Dependency d, String alias, String storepass, File keystore, File repo) {
		Map<String,String> props = new HashMap<String, String>();
		try {
			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
			props.put("storepass",storepass);
			props.put("alias", alias);
			props.put("keystore",keystore.getAbsolutePath());
			File cachedFile = d.getFilePathForDependency(repo);
			props.put("jarfile", cachedFile.getAbsolutePath());
			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
			AntRun.callAnt(JnlpBuild.class.getClassLoader().getResourceAsStream("ant/signsingle.xml"), repo, props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}



}
