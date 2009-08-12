package com.dexels.navajo.tipi;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.util.http.fileupload.MultipartStream;

import com.dexels.navajo.tipi.appmanager.ApplicationStatus;
import com.dexels.navajo.tipi.projectbuilder.BaseJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.ProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;
import com.oreilly.servlet.MultipartRequest;

public class TipiAdminServlet extends HttpServlet {

//	private File applicationFolder = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appStoreUrl = getServletContext().getInitParameter("appUrl");
		if(appStoreUrl==null) {
			appStoreUrl = 		request.getRequestURL().toString();
		}
		String application = request.getParameter("app");
		String commando = request.getParameter("cmd");
		
		if(application==null || commando == null) {
			response.sendRedirect(getServletContext().getContextPath()  +"/index.jsp?result=" + URLEncoder.encode("Huh?", "UTF-8"));
			return;
		}

		//		applicationFolder = ff; // new File(appFolder);
		// String servletPath = request.getServletPath();
		//String myAppPath = appFolder + application;
		String myAppUrl = appStoreUrl + application;
		File ff = getAppFolder();
		File applicationDir = new File(ff, application);

		if (commando.equals("download")) {
			download(application, ff, response);
			return;
		}
		if (commando.equals("uploaddirect")) {
			String result = upload(application,request);
			response.getWriter().write(result+"\n");
			return;
		}
		String resultMessage;
		String destination;
		resultMessage = performCommando(commando, application, applicationDir, new URL(myAppUrl),request);
		destination = request.getParameter("destination");
		// filthy hack
		if(commando.equals("upload")) {
			destination="#";
		}
		if (destination==null) {
			response.getWriter().write(resultMessage+"\n");
		} else {

			if(destination==null) {
				destination = "";
			}
			response.sendRedirect(getServletContext().getContextPath() + destination +"?result=" + URLEncoder.encode(resultMessage, "UTF-8")+"&application="+URLEncoder.encode(application, "UTF-8"));
		}
		
		return;
	}
	private File getAppFolder() {
		String appFolder = getServletContext().getInitParameter("appFolder"); 
		File ff = null;
		if(appFolder==null) {
			ff = new File(getServletConfig().getServletContext().getRealPath("DefaultApps"));

		} else {
			File suppliedFolder = new File(appFolder);
			if(suppliedFolder.isAbsolute()) {
				ff = suppliedFolder;
			} else {
				ff = new File(getServletConfig().getServletContext().getRealPath(appFolder));
			}
			//ff = new File(appFolder);
		}
		return ff;
	}

	
//	private XMLElement doCreateJnlp(HttpServletRequest request, boolean build, boolean clean) throws IOException {
//		String servletPath = request.getServletPath();
//		String appStoreUrl = getServletContext().getInitParameter("appUrl");
//		String appFolder = getServletContext().getInitParameter("appFolder");
//		String applicationPath = servletPath.substring(1,servletPath.lastIndexOf('/'));
//		String myAppPath = appFolder+applicationPath;
//		String myAppUrl = appStoreUrl+applicationPath;
//		File applicationDir = new File(myAppPath);
//		String profile = servletPath.substring(servletPath.lastIndexOf('/')+1,servletPath.lastIndexOf('.'));
//		File prop = new File(myAppPath+"/settings/tipi.properties");
//		FileInputStream fis = new FileInputStream(prop);
//		PropertyResourceBundle prb = new PropertyResourceBundle(fis);
//		fis.close();
//		BaseJnlpBuilder l = new LocalJnlpBuilder();
//		String repository = prb.getString("repository");
//		File profileSettings = new File(myAppPath+"/settings/profiles/"+profile+".properties");
//		if(profileSettings.exists()) {
//			System.err.println("Profile actually found!");
//		} else {
//			profile = null;
//		}
//		boolean useVersioning = false;
//		try {
//			useVersioning = prb.getString("useJnlpVersioning").equals("true");
//		} catch (MissingResourceException e) {
//		}
//		XMLElement jnlp = l.buildElement(repository, prb.getString("extensions"),applicationDir, "$$codebase",myAppUrl, profile+".jnlp",profile,useVersioning);
//		return jnlp;
//	}
	/**
	 * Throws interupted to prevent redirections
	 * @param commando
	 * @param application
	 * @param appDir
	 * @param appUrl
	 * @param request
	 * @return
	 * @throws ServletException 
	 * @throws InterruptedException
	 */
	private String performCommando(String commando, String application, File appDir, URL appUrl, HttpServletRequest request) throws ServletException {
		if (commando.equals("build")) {
			return build(application, appDir,getServletContext());
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
			return uploadMultipart(application,request);
		}
		if (commando.equals("saveConfig")) {
			return saveConfig(application, appDir,request);
		}
	
		return "Unknown commando!";
	}


	private String saveConfig(String application, File appDir, HttpServletRequest request) throws ServletException {
//		File currentAppDir = new File(appDir,application);
		String filePath = request.getParameter("filePath");
			if(filePath.indexOf("..")!=-1) {
  				throw new ServletException("Illegal path: "+filePath);
  			}
		File currentFilePath = new File(appDir,filePath);

		String content = request.getParameter("content");
		try {
			FileWriter fw = new FileWriter(currentFilePath);
			StringReader sr = new StringReader(content);
			copyResource(fw, sr);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error saving: "+filePath+" problem: "+e.getMessage();
		}
		build(application, appDir,getServletContext());
		return "Configuration saved";
	}
	private String upload(String application, HttpServletRequest request)  {
		try {
			InputStream is =  request.getInputStream();
  			File tmp = File.createTempFile("testUpload",".zip");
			FileOutputStream fos = new FileOutputStream(tmp);
			copyResource(fos, is);
			tmp.deleteOnExit();
			createApp(tmp,application);

			return "OK - "+application+" uploaded\n";
//			return "File dumped @"+ tmp.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR -  "+e.getMessage();
		}}
	private String uploadMultipart(String application, HttpServletRequest request) {
//		System.err.println(">>" +request.getParameterMap());
		try {
			InputStream is =  request.getInputStream();
//			File apppp = new File(applicationFolder);
			MultipartRequest mr = new MultipartRequest(request, System.getProperty("java.io.tmpdir"),20000000);
			Enumeration e =  mr.getFileNames();
	
			while (e.hasMoreElements()) {
				String object = (String) e.nextElement();
				File f = mr.getFile(object);
//				System.err.println("Filename returned: "+f.getAbsolutePath());
				createApp(f,application);
				f.delete();
//				System.err.println("File detected: "+object);
			}
			File tmp = File.createTempFile("testUpload",".zip");
			FileOutputStream fos = new FileOutputStream(tmp);
			copyResource(fos, is);
			tmp.deleteOnExit();	
			createApp(tmp,application);
			// fnished!
			
			return "File dumped @"+ tmp.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return "Problem: "+e.getMessage();
		}
	}
	
	private void createApp(File tmp , String appName) {
		File dest = new File(getAppFolder(),appName);
		dest.mkdirs();
//		System.err.println("Deploying app: "+appName+" to: "+dest.getAbsolutePath());
//		System.err.println("File: "+tmp.getAbsolutePath()+" exists? "+tmp.exists()+" size: "+tmp.length());
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
	public static void buildIfNecessary(HttpServletRequest request, File appDir, ServletContext context) {
		final String appsTag = "Apps/";
		String requestString = request.getServletPath();
		String requestURI = request.getRequestURI();
		int ind =  requestURI.indexOf(appsTag);
		if(ind==-1) {
			return;
		}
		int jnlpUnd =  requestURI.indexOf(".jnlp");
		if(jnlpUnd==-1) {
			return;
		}
		String appName = requestURI.substring(ind+appsTag.length(),requestURI.lastIndexOf('/'));
		String profileName = requestURI.substring(requestURI.lastIndexOf('/')+1,jnlpUnd);
		File currentAppDir = new File(appDir,appName);
		ApplicationStatus as = new ApplicationStatus();
		try {
			as.load(currentAppDir);
			boolean res = as.getRebuildMap().get(profileName);
			if(res) {
				build(appName, currentAppDir, context);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void download(String application, File appDir, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// application/x-zip-compressed
		response.setContentType("application/x-zip-compressed");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + application + ".zip" + "\"");
		
		Map<String,String> userProperties = new HashMap<String,String>();
			String path = getServletContext().getRealPath("WEB-INF/ant/zipoutput.xml");
			try {
				userProperties.put("application", application);
				File actualAppFolder = new File(appDir, application);
				userProperties.put("zipDir", actualAppFolder.getAbsolutePath());
				String result = AntRun.callAnt(new File(path), actualAppFolder, userProperties);
//				System.err.println("Result: "+result);
				File output = new File(actualAppFolder,application+".zip");
				FileInputStream fis = new FileInputStream(output);
				OutputStream os = response.getOutputStream();
				copyResource(os, fis);
				os.flush();
				output.delete();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error building " + application + ": " + e.getMessage());
				response.getWriter().write("Error building " + application + ": " + e.getMessage());
				response.getWriter().flush();
				
	}		
	}

	private String delete(String application, File appDir) {
		
		String appPath = appDir.getAbsolutePath();
//		File ff = new File(ap)
		deleteDirectory(appDir);
		return "OK - "+appPath + " deleted!";
	}

	private String clean(String application, File appDir) {

		File libDir = new File(appDir, "lib");
		if (libDir != null && libDir.exists()) {
			for (File f : libDir.listFiles()) {
				f.delete();
			}
		}
		libDir.delete();
		return "OK - "+libDir.getAbsolutePath() + " cleaned!";
	}

	public static String build(String application, File appDir, ServletContext context) {
		// TipiProjectBuilder
		String codebase = context.getInitParameter("appUrl");
		if(codebase!=null) {
			codebase = codebase+application+"/";
		}
		try {
			ProjectBuilder.buildTipiProject(appDir,codebase);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error building " + application + ": " + e.getMessage();
		}
		boolean localSign = false;
		
		PropertyResourceBundle pe;
		Map<String,String> userProperties = new HashMap<String,String>();
		try {
			FileInputStream is = new FileInputStream(new File(appDir,"settings/tipi.properties"));
			pe = new PropertyResourceBundle(is);
			Enumeration<String> keys = pe.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();

			}
			userProperties.put("keystore", pe.getString("keystore"));
			userProperties.put("alias", pe.getString("alias"));
			userProperties.put("storepass", pe.getString("storepass"));
			String keystore = pe.getString("keystore");
			is.close();
			if(keystore!=null) {
				String path = context.getRealPath("WEB-INF/ant/localsign.xml");
				try {
					System.err.println("Calling ant with: "+userProperties+" in folder: "+appDir);
					String result = AntRun.callAnt(new File(path), appDir, userProperties);
					System.err.println("Result: "+result);
					return "OK - Local signing succeeded. I think.";
				} catch (IOException e) {
					e.printStackTrace();
					return "Error building " + application + ": " + e.getMessage();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	catch (MissingResourceException me) {
			System.err.println("No keystore found");
		}

		
		
		return "OK - "+application + " built!";
 
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

	
	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			read = bin.read(buffer);
			if (read > -1) {
				bout.write(buffer, 0, read);
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

	
	
	/**
	 * Same as the stream edition. Does not close streams!
	 * @param out
	 * @param in
	 * @throws IOException
	 */
	private final void copyResource(Writer out, Reader in) throws IOException {
		BufferedReader bin = new BufferedReader(in);
		BufferedWriter bout = new BufferedWriter(out);
		char[] buffer = new char[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
				read = bin.read(buffer);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}
			if (read <= -1) {
				ready = true;
			}
		}
		bout.flush();
	
	}

	
}
