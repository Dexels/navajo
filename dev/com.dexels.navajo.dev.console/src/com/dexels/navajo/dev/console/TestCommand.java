package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.karaf.shell.commands.Action;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;

@Command(scope = "jms", name = "browse", description = "Browse a queue")
public class TestCommand implements Action {
    @Argument(index = 0, name = "queueName", required = false, description = "queue name", multiValued = false)
    String queueName;
    
    @Option(name = "-s", required = false, description = "JMS selector to filter the view", multiValued = false)
    String selector;
    
    public TestCommand() {
    	
    }
    @Override
    public Object execute(CommandSession session) throws Exception {
    	System.out.println("Testing. queue: "+queueName+" option: "+selector);
    	return null;
    	
    }

}