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
		if(script==null) {
			resp.sendError(500,"No script parameter supplied");
			return;
		}
		boolean force = true;
		boolean keepIntermediateFiles = false;
		
		try {
			long tm = System.currentTimeMillis();
			if(script.equals("/")) {
				script = "";
			}
//			System.err.println("Force: "+force);
			List<String> success = new ArrayList<String>();
			List<String> failures = new ArrayList<String>();
			List<String> skipped = new ArrayList<String>();
			bundleCreator.createBundle(script,new Date(),"xml",failures,success,skipped, force,keepIntermediateFiles,"default");
			long tm2 = System.currentTimeMillis() - tm;
			logger.info("Compiling java complete. took: "+tm2+" millis.");
			logger.info("Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			logger.info("Avg: "+(1000 * (float)success.size() / tm2)+" scripts / sec");
			for (String failed : failures) {
				logger.info("Failed: "+failed);
			}
			bundleCreator.installBundles(script, failures, success, skipped, true);
		} catch (Throwable e) {
			logger.error("Error compiling scripts form servlet:",e);
		}
		if(req.getParameter("redirect")!=null) {
			resp.sendRedirect("/index.jsp");
		} else {
			resp.getWriter().write("OK");
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
