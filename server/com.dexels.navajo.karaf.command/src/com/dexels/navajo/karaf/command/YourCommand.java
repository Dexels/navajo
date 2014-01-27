package com.dexels.navajo.karaf.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

@Command(scope = "yourcommand", name = "hello", description="Says hello")
public class YourCommand extends OsgiCommandSupport {

	public YourCommand() {
		System.err.println("Constructing command");
		
	}

    @Argument(index = 0, name = "arg", 
              description = "The command argument", 
              required = false, multiValued = false)
    String arg = null;


    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Executing your Command Demo");
        return null;
    }
}