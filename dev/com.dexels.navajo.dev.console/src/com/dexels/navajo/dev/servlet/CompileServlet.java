package com.dexels.navajo.dev.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
		if(script==null) {
			resp.sendError(400,"No script parameter supplied");
			return;
		}
		String tenant = req.getParameter("tenant");
		if(tenant==null) {
			tenant = "default";
		}
		boolean force = true;
		Boolean keepIntermediateFiles = false;
		if (req.getParameter("keepIntermediateFiles") != null) {
		    keepIntermediateFiles = Boolean.valueOf(req.getParameter("keepIntermediateFiles"));
		} else if ("true".equals(System.getenv("DEVELOP_MODE"))) {
            keepIntermediateFiles = true;
        }
		
		List<String> success = new ArrayList<String>();
		List<String> failures = new ArrayList<String>();
		List<String> skipped = new ArrayList<String>();
		String failedReason = null;
		long tsStart=0;
		long compileDuration=0;
		long tsInstall=0;
		try {
			tsStart = System.currentTimeMillis();
			if(script.equals("/")) {
				script = "";
			}
//			System.err.println("Force: "+force);
			bundleCreator.createBundle(script,failures,success,skipped,force, keepIntermediateFiles,null);
			long ts1 = System.currentTimeMillis();
			compileDuration = ts1 - tsStart;
			logger.info("Compiling java complete. took: "+compileDuration+" millis.");
			logger.info("Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			logger.info("Avg: "+(1000 * (float)success.size() / compileDuration)+" scripts / sec");
			for (String failed : failures) {
				logger.info("Failed: "+failed);
			}
		    bundleCreator.installBundles(script,failures, success, skipped, true, null);
			tsInstall = System.currentTimeMillis() - ts1;
			logger.info("Installing bundles took "+tsInstall+" millis.");
			
		} catch (Throwable e) {
			logger.error("Error compiling scripts form servlet:",e);
			failedReason = e.getMessage();
			
		}
		
		if(req.getParameter("redirect")!=null) {
			resp.sendRedirect("/index.jsp");
		} else {
		    resp.setContentType("text/plain");
			resp.getWriter().write("Compiling java complete. took: "+compileDuration+" millis.");
			resp.getWriter().write(" Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			resp.getWriter().write(" Avg: "+(1000 * (float)success.size() / compileDuration)+" scripts / sec");
			for (String failed : failures) {
				resp.getWriter().write(" Failed: "+failed);
			}
			if (failedReason != null) {
			    resp.getWriter().write(" failreason=" + failedReason);
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
