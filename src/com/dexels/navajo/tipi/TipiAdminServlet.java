package com.dexels.navajo.tipi;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.MultipartStream;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.ProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;
import com.oreilly.servlet.MultipartRequest;

public class TipiAdminServlet extends HttpServlet {

	private File applicationFolder = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appStoreUrl = getServletContext().getInitParameter("appUrl");
		String appFolder = getServletContext().getInitParameter("appFolder");

		String application = request.getParameter("app");
		applicationFolder = new File(appFolder);
		// String servletPath = request.getServletPath();
		String myAppPath = appFolder + application;
		String myAppUrl = appStoreUrl + application;
		File applicationDir = new File(myAppPath);

		String commando = request.getParameter("cmd");
		if (commando.equals("download")) {
			download(application, new File(appFolder + "/"), response);
			return;
		}

		String resultMessage = performCommando(commando, application, applicationDir, new URL(myAppUrl),request);

		String destination = request.getParameter("destination");
		if(destination==null) {
			destination = "";
		}
		response.sendRedirect(getServletContext().getContextPath() + destination +"?result=" + URLEncoder.encode(resultMessage, "UTF-8")+"&application="+URLEncoder.encode(application, "UTF-8"));
		return;
	}

	private String performCommando(String commando, String application, File appDir, URL appUrl, HttpServletRequest request) {
		if (commando.equals("build")) {
			return build(application, appDir);
		}
		if (commando.equals("clean")) {
			return clean(application, appDir);
		}
		if (commando.equals("delete")) {
			return delete(application, appDir);
		}
		if (commando.equals("create")) {
			return create(application, appDir);
		}
		if (commando.equals("upload")) {
			return upload(application, appDir,request);
		}
		return "Unknown commando!";
	}

	private String upload(String application, File appDir, HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			InputStream is =  request.getInputStream();
//			File apppp = new File(applicationFolder);
			MultipartRequest mr = new MultipartRequest(request, System.getProperty("java.io.tmpdir"),20000000);
			Enumeration e =  mr.getFileNames();
			String appName = mr.getParameter("appName");
			if(appName==null) {
				appName = "NewApplication";
			}
			while (e.hasMoreElements()) {
				String object = (String) e.nextElement();
				File f = mr.getFile(object);
				System.err.println("Filename returned: "+f.getAbsolutePath());
				createApp(f,appName);
				f.delete();
				System.err.println("File detected: "+object);
			}
//			File tmp = File.createTempFile("testUpload",".zip");
//			FileOutputStream fos = new FileOutputStream(tmp);
//			copyResource(fos, is);
//			tmp.deleteOnExit();
		//	createApp(tmp);
			
			return "File dumped @";// + tmp.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return "Problem: "+e.getMessage();
		}
	}
	private void createApp(File tmp , String appName) {
		File dest = new File(applicationFolder,appName);
		dest.mkdirs();
		System.err.println("File: "+tmp.getAbsolutePath()+" exists? "+tmp.exists()+" size: "+tmp.length());
		ZipUtils.unzip(tmp, dest);
		
	}
	private String create(String application, File appDir) {
		File newApp = new File(appDir,application);
		boolean result = newApp.mkdirs();
		String appStoreUrl = getServletContext().getInitParameter("appUrl");
		String appFolder = getServletContext().getInitParameter("appFolder");

		String template = getServletContext().getInitParameter("defaultTemplate");
		String repository =  getServletContext().getInitParameter("extensionRepository");
		String developmentRepository =  getServletContext().getInitParameter("developmentRepository");
		try {
			ClientActions.downloadZippedDemoFiles(developmentRepository,repository, appDir,template);
			return result?application+" created!":"Could not create application: "+application;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error creating and downloading new application: "+e.getMessage();
		}

	}

	private void download(String application, File appDir, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// application/x-zip-compressed
		response.setContentType("application/x-zip-compressed");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + application + ".zip" + "\"");

		InputStream is = getZippedDir(appDir, application);
		copyResource(response.getOutputStream(), is);
	}

	private String delete(String application, File appDir) {
		
		String appPath = appDir.getAbsolutePath();
//		File ff = new File(ap)
		deleteDirectory(appDir);
		return appPath + " deleted!";
	}

	private String clean(String application, File appDir) {

		File libDir = new File(appDir, "lib");
		if (libDir != null && libDir.exists()) {
			for (File f : libDir.listFiles()) {
				f.delete();
			}
		}
		libDir.delete();
		return libDir.getAbsolutePath() + " cleaned!";
	}

	private String build(String application, File appDir) {
		// TipiProjectBuilder
		try {
			ProjectBuilder.buildTipiProject(appDir);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error building " + application + ": " + e.getMessage();
		}
		return application + " built!";

	}

	public boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	private  InputStream getZippedDir(File appStoreFolder, String appName) throws IOException {
		File tmp = File.createTempFile("tmpDownload", ".zip");
		System.err.println("Zipping in folder: " + appStoreFolder.getAbsolutePath() + " adding directory: " + appName + "/"
				+ " to file: " + tmp.getAbsolutePath());
		ZipUtils.zipAll(appStoreFolder, appName , tmp);
		tmp.deleteOnExit();
		FileInputStream fis = new FileInputStream(tmp);
		return fis;
	}

	
	private final void copyResource(OutputStream out, InputStream in) {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			try {
				read = bin.read(buffer);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}
			} catch (IOException e) {
			}
			if (read <= -1) {
				ready = true;
			}
		}
		try {
			bin.close();
			bout.flush();
			bout.close();
		} catch (IOException e) {

		}
	}

	
	

	
}
