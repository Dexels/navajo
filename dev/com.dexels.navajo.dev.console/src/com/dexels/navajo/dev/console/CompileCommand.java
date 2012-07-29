package com.dexels.navajo.dev.console;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.compiler.BundleCreator;

public class CompileCommand {
	
//	import org.osgi.service.command.CommandProcessor;
	
//	private final static Logger logger = LoggerFactory
//			.getLogger(CompileCommand.class);
	private BundleCreator bundleCreator = null;

	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	public void compile(CommandSession session, String script) {
		try {
			long tm = System.currentTimeMillis();
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String formatted = df.format(new Date());
			if(script.equals("/")) {
				script = "";
			}
			List<String> success = new ArrayList<String>();
			List<String> failures = new ArrayList<String>();
			bundleCreator.createBundle(script,formatted,"xml",failures,success);
			long tm2 = System.currentTimeMillis() - tm;
			session.getConsole().println("Compiling java complete. took: "+tm2+" millis.");
			session.getConsole().println("Succeeded: "+success.size()+" failed: "+failures.size());
			session.getConsole().println("Avg: "+(1000 * (float)success.size() / tm2)+" scripts / sec");
			for (String failed : failures) {
				session.getConsole().println("Failed: "+failed);
			}
		} catch (Throwable e) {
			e.printStackTrace(session.getConsole());
		}
	}
}
