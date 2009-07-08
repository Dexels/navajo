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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.tipi.projectbuilder.ProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;

public class TipiAdminServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appStoreUrl = getServletContext().getInitParameter("appUrl");
		String appFolder = getServletContext().getInitParameter("appFolder");

		String application = request.getParameter("app");

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
		return "Upload not yet implemented!";
	}
	private String create(String application, File appDir) {
		File newApp = new File(appDir,application);
		boolean result = newApp.mkdirs();
		return result?application+" created!":"Could not create application: "+application;
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
		zipAll(appStoreFolder, appName , tmp);
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

	
	
	
	
	private  void zipAll(File here, String dirsStartingWith, File zipFile) throws IOException {
	    //create a ZipOutputStream to zip the data to 
	    ZipOutputStream zos = new 
	           ZipOutputStream(new FileOutputStream(zipFile)); 
	    //assuming that there is a directory named inFolder (If there 
	    //isn't create one) in the same directory as the one the code 
	    //call the zipDir method 
	    File dir = new File(here,dirsStartingWith);
	    zipDir(dir, zos); 
	    //close the stream 
	    zos.close(); 
	
	}
	//here is the code for the method 
	private void zipDir(File zipDir, ZipOutputStream zos) throws IOException 
	{ 
	        String[] dirList = zipDir.list(); 
	        byte[] readBuffer = new byte[2156]; 
	        int bytesIn = 0; 
	        //loop through dirList, and zip the files 
	        for(int i=0; i<dirList.length; i++) 
	        { 
	            File f = new File(zipDir, dirList[i]); 
	        if(f.isDirectory()) 
	        { 
	                //if the File object is a directory, call this 
	                //function again to add its content recursively 
//	            String filePath = f.getPath(); 
	            zipDir(f, zos); 
	                //loop again 
	            continue; 
	        } 
	            FileInputStream fis = new FileInputStream(f); 
	        ZipEntry anEntry = new ZipEntry(f.getPath()); 
	        zos.putNextEntry(anEntry); 
	            //now write the content of the file to the ZipOutputStream 
	            while((bytesIn = fis.read(readBuffer)) != -1) 
	            { 
	                zos.write(readBuffer, 0, bytesIn); 
	            } 
	           //close the Stream 
	           fis.close(); 
	    } 
	} 

	
}
