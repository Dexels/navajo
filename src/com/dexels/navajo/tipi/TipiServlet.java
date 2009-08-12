package com.dexels.navajo.tipi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.tipi.projectbuilder.BaseJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.ProjectBuilder;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

/**
 * Servlet implementation class TipiServlet
 */
public class TipiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TipiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//super.doGet(request, response);
//		String clear = 
		long st = System.currentTimeMillis();
		boolean build = false;
		boolean clean = false;
		if("true".equals(request.getParameter("create"))) {
			doCreateApp(request,response);
		}
		
		if("true".equals(request.getParameter("build"))) {
			build = true;
		}
		if("true".equals(request.getParameter("clean"))) {
			clean = true;
		}
		XMLElement jnlpElement =  doCreateJnlp(request,build,clean);
//		ClientActions.
		if(!clean) {
//			boolean isOk = doSanityCheck(request, jnlpElement);
//			isOk = true;
//			if(!isOk) {
//				// force clean build:
//				clean = true;
//				build = true;
//				jnlpElement =  doCreateJnlp(request,build,clean);
//			} else {
//				System.err.println("JNLP seems sane. No server-side action prompted");
//			}
		}
		response.setContentType("application/x-java-jnlp-file");
//		System.err.println(jnlpElement);
		Writer outWriter = response.getWriter();
		jnlpElement.write(outWriter);
		outWriter.flush();
		outWriter.close();
//		System.err.println("Time taken: "+(System.currentTimeMillis() - st));
	}

	
	private void doCreateApp(HttpServletRequest request, HttpServletResponse response) {
		
	}

//	private boolean doSanityCheck(HttpServletRequest request, XMLElement jnlpElement) {
//		if(true) {
//			System.err.println("Sanity check disabled!!");
//			return true;
//		}
//		XMLElement xx = jnlpElement.getElementByTagName("resources");
//		Vector<XMLElement> ll = xx.getChildren();
//		String servletPath = request.getServletPath();
//		File projectPath = new File(getServletContext().getRealPath("."));
//		String applicationPath = servletPath.substring(1,servletPath.lastIndexOf('/'));
//		File applicationDir = new File(projectPath,applicationPath);
//		
//		for (XMLElement element : ll) {
//			if(element.getName().equals("jar")) {
//				File jarFile = new File(applicationDir,element.getStringAttribute("href"));
//				if(!jarFile.exists()) {
//					System.err.println("Missing jar found: "+element.getStringAttribute("href"));
//					System.err.println("Full path: "+jarFile.getAbsolutePath());
//					return false;
//				}
//			}
//		}
//		return true;
//	}

	private XMLElement doCreateJnlp(HttpServletRequest request, boolean build, boolean clean) throws IOException {
		String servletPath = request.getServletPath();
		String appStoreUrl = getServletContext().getInitParameter("appUrl");
		String appFolder = getServletContext().getInitParameter("appFolder");
		String applicationPath = servletPath.substring(1,servletPath.lastIndexOf('/'));
		String myAppPath = appFolder+applicationPath;
		String myAppUrl = appStoreUrl+applicationPath;
		File applicationDir = new File(myAppPath);

//		System.err.println("Resolved app path: "+myAppPath);
//		System.err.println("Resolved app url: "+myAppUrl);

		String profile = servletPath.substring(servletPath.lastIndexOf('/')+1,servletPath.lastIndexOf('.'));
	//	String propertypath = getServletContext().getRealPath(myAppPath+"/settings/tipi.properties");
//		System.err.println("Using profile: "+profile);
		File prop = new File(myAppPath+"/settings/tipi.properties");
		FileInputStream fis = new FileInputStream(prop);
		PropertyResourceBundle prb = new PropertyResourceBundle(fis);

		fis.close();
		BaseJnlpBuilder l = new LocalJnlpBuilder();
		String codebase = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+applicationPath;
		String repository = prb.getString("repository");

		File profileSettings = new File(myAppPath+"/settings/profiles/"+profile+".properties");
		if(profileSettings.exists()) {
		} else {
			profile = null;
		}
		
		
		boolean useVersioning = false;
		
		try {
			useVersioning = prb.getString("useJnlpVersioning").equals("true");
		} catch (MissingResourceException e) {
		}
		XMLElement jnlp = l.buildElement(repository, prb.getString("extensions"),applicationDir, codebase,myAppUrl, profile+".jnlp",profile,useVersioning);
		return jnlp;

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

}
