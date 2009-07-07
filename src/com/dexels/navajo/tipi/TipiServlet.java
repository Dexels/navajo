package com.dexels.navajo.tipi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
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
			boolean isOk = doSanityCheck(request, jnlpElement);
			if(!isOk) {
				// force clean build:
				clean = true;
				build = true;
				jnlpElement =  doCreateJnlp(request,build,clean);
			} else {
				System.err.println("JNLP seems sane. No server-side action prompted");
			}
		}
		response.setContentType("application/x-java-jnlp-file");
		System.err.println(jnlpElement);
		Writer outWriter = response.getWriter();
		jnlpElement.write(outWriter);
		outWriter.flush();
		outWriter.close();

	}

	
	private void doCreateApp(HttpServletRequest request, HttpServletResponse response) {
		
	}

	private boolean doSanityCheck(HttpServletRequest request, XMLElement jnlpElement) {
		XMLElement xx = jnlpElement.getElementByTagName("resources");
		Vector<XMLElement> ll = xx.getChildren();
		String servletPath = request.getServletPath();
		File projectPath = new File(getServletContext().getRealPath("."));
		String applicationPath = servletPath.substring(1,servletPath.lastIndexOf('/'));
		File applicationDir = new File(projectPath,applicationPath);
		
		for (XMLElement element : ll) {
			if(element.getName().equals("jar")) {
				File jarFile = new File(applicationDir,element.getStringAttribute("href"));
				if(!jarFile.exists()) {
					System.err.println("Missing jar found: "+element.getStringAttribute("href"));
					System.err.println("Full path: "+jarFile.getAbsolutePath());
					return false;
				}
			}
		}
		return true;
	}

	private XMLElement doCreateJnlp(HttpServletRequest request, boolean build, boolean clean) throws IOException {
		String servletPath = request.getServletPath();
		
		
		
		
		File projectPath = new File(getServletContext().getRealPath("."));

		
		
		String applicationPath = servletPath.substring(1,servletPath.lastIndexOf('/'));
		File applicationDir = new File(projectPath,applicationPath);
		String profile = servletPath.substring(servletPath.lastIndexOf('/')+1,servletPath.lastIndexOf('.'));
		String propertypath = getServletContext().getRealPath(applicationPath+"/settings/tipi.properties");
		File prop = new File(propertypath);
		FileInputStream fis = new FileInputStream(prop);
		PropertyResourceBundle prb = new PropertyResourceBundle(fis);
		fis.close();
		BaseJnlpBuilder l = new LocalJnlpBuilder();
		String codebase = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+applicationPath;
		String repository = prb.getString("repository");
		
		if(build) {
			fis = new FileInputStream(prop);
			ProjectBuilder.buildTipiProject(applicationDir,codebase, fis, clean,true);
			fis.close();
		}
		XMLElement jnlp = l.buildElement(repository+"Extensions/", prb.getString("extensions"),applicationDir, codebase, profile+".jnlp",null);
		return jnlp;

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

}
