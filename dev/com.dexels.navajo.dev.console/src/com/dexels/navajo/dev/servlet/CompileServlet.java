package com.dexels.navajo.dev.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

public class CompileServlet extends HttpServlet {

	private static final long serialVersionUID = 1696342524348410364L;
	private BundleCreator bundleCreator = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(CompileServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String script = req.getParameter("script");
		Thread.currentThread().setName("Compile");
		final String extension = ".xml";
		if(script==null) {
			resp.sendError(400,"No script parameter supplied");
			return;
		}
		String tenant = req.getParameter("tenant");
		if(tenant==null) {
			tenant = "default";
		}
		boolean force = true;
		boolean keepIntermediateFiles = false;
		List<String> success = new ArrayList<String>();
		List<String> failures = new ArrayList<String>();
		List<String> skipped = new ArrayList<String>();
		long tm=0;
		long tm2=0;
		long tm3=0;
		try {
			tm = System.currentTimeMillis();
			if(script.equals("/")) {
				script = "";
			}
//			System.err.println("Force: "+force);
			bundleCreator.createBundle(script,new Date(),".xml",failures,success,skipped, force,keepIntermediateFiles);
			long tstamp = System.currentTimeMillis();
			tm2 = tstamp - tm;
			logger.info("Compiling java complete. took: "+tm2+" millis.");
			logger.info("Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			logger.info("Avg: "+(1000 * (float)success.size() / tm2)+" scripts / sec");
			for (String failed : failures) {
				logger.info("Failed: "+failed);
			}
		//	bundleCreator.installBundles(script,failures, success, skipped, true,extension);
			tm3 = System.currentTimeMillis() - tstamp;
			logger.info("Installing bundles took "+tm3+" millis.");
			
		} catch (Throwable e) {
			logger.error("Error compiling scripts form servlet:",e);
		}
		if(req.getParameter("redirect")!=null) {
			resp.sendRedirect("/index.jsp");
		} else {
			resp.getWriter().write("OK");
			resp.getWriter().write("Compiling java complete. took: "+tm2+" millis.");
			resp.getWriter().write("Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			resp.getWriter().write("Avg: "+(1000 * (float)success.size() / tm2)+" scripts / sec");
			for (String failed : failures) {
				resp.getWriter().write("Failed: "+failed);
			}

		}
	}
	
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	/**
	 * 
	 * @param bundleCreator the bundlecreator to clear
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}


	
}
