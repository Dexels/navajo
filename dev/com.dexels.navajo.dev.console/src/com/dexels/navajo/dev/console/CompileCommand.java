package com.dexels.navajo.dev.console;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import  org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Parameter;

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

	@Descriptor(value = "compile a script with a certain path.") 
	public void compile(CommandSession session, @Descriptor(value = "Compile even if the script seems unchanged") @Parameter(names = { "-f", "--force" }, presentValue = "true", absentValue = "false") boolean force, @Descriptor(value = "The path, prefix, or '/' to compile everything") String script ) {
		try {
			long tm = System.currentTimeMillis();
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String formatted = df.format(new Date());
			if(script.equals("/")) {
				script = "";
			}
			System.err.println("Force: "+force);
			List<String> success = new ArrayList<String>();
			List<String> failures = new ArrayList<String>();
			List<String> skipped = new ArrayList<String>();
			bundleCreator.createBundle(script,formatted,"xml",failures,success,skipped, force);
			long tm2 = System.currentTimeMillis() - tm;
			session.getConsole().println("Compiling java complete. took: "+tm2+" millis.");
			session.getConsole().println("Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			session.getConsole().println("Avg: "+(1000 * (float)success.size() / tm2)+" scripts / sec");
			for (String failed : failures) {
				session.getConsole().println("Failed: "+failed);
			}
		} catch (Throwable e) {
			e.printStackTrace(session.getConsole());
		}
	}
}
