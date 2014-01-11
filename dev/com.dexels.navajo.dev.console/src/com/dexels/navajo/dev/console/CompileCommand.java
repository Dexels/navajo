package com.dexels.navajo.dev.console;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.felix.service.command.Descriptor;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Option;

import org.apache.karaf.shell.console.OsgiCommandSupport;

import com.dexels.navajo.compiler.BundleCreator;

public class CompileCommand extends OsgiCommandSupport  {
	
//	import org.osgi.service.command.CommandProcessor;
	
//	private final static Logger logger = LoggerFactory
//			.getLogger(CompileCommand.class);
	private BundleCreator bundleCreator = null;

	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	/**
	 * 
	 * @param bundleCreator the bundle creator to clear
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

    @Argument(index = 0, name = "arg", 
            description = "The command argument", 
            required = false, multiValued = false)
    String script = null;
	
    @Option(name = "-f", aliases = {"--force"}, description = "Compile even if the script seems unchanged", required = false, multiValued = false)
         boolean force;

    @Option(name = "-k", aliases = {"--keep"}, description = "Keep temporary files, can be useful for debugging", required = false, multiValued = false)
    boolean keepIntermediateFiles;

	@Override
	@Descriptor(value = "compile a script with a certain path.") 
	protected Object doExecute() throws Exception {
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
			System.err.println("Compiling java complete. took: "+tm2+" millis.");
			System.err.println("Succeeded: "+success.size()+" failed: "+failures.size()+" skipped: "+skipped.size());
			System.err.println("Avg: "+(1000 * (float)success.size() / tm2)+" scripts / sec");
			for (String failed : failures) {
				System.err.println("Failed: "+failed);
			}
		} catch (Throwable e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	public void compile(String script) throws Exception {
		this.script = script;
		doExecute();
	}
}
