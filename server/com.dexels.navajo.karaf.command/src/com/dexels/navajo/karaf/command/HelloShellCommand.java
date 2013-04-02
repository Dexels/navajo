package com.dexels.navajo.karaf.command;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Parameter;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

@Command(scope = "test", name = "hello", description="Says hello")
public class HelloShellCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "arg", 
            description = "The command argument", 
            required = false, multiValued = false)
    boolean forceField = false;
    
    @Argument(index = 0, name = "arg", 
            description = "The command argument", 
            required = false, multiValued = false)
    String arg = null;
    

    @Override
    protected Object doExecute() throws Exception {
    	return null;
    }
    

	@Descriptor(value = "compile a script with a certain path.") 
	public void testcommand(CommandSession session, 
			@Descriptor(value = "Compile even if the script seems unchanged") 
			@Parameter(names = { "-f", "--force" }, presentValue = "true", absentValue = "false") boolean force, 
			@Descriptor(value = "Keep temporary files, can be useful for debugging") 
			@Parameter(names = { "-k", "--keep" }, presentValue = "true", absentValue = "false") boolean keepIntermediateFiles, 
			@Descriptor(value = "The path, prefix, or '/' to compile everything") 
			String script ) {
		try {
			long tm = System.currentTimeMillis();
			System.err.println("Testcompile. Force: "+force+" keep: "+keepIntermediateFiles+" path: "+script);
//			System.err.println("Force: "+force);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
   
}
