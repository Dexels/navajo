package com.dexels.navajo.article.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.impl.ServiceCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestServiceCommand extends ServiceCommand {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TestServiceCommand.class);
	
	public TestServiceCommand() {
		super("service");
	}
	
	protected Navajo performCall(ArticleRuntime runtime, String name, Navajo n)
			throws ArticleException {
		
		File tmlFolder = new File("testresources/tml");
		File scriptFile = new File(tmlFolder,name+".xml");
		if(scriptFile.exists()) {
			FileReader fr = null;
			try {
				fr = new FileReader(scriptFile);
				return NavajoFactory.getInstance().createNavajo(fr);
			} catch (FileNotFoundException e) {
				throw new ArticleException("Error reading tml stub at: "+scriptFile.getAbsolutePath(),e);
			} finally {
				if(fr!=null) {
					try {
						fr.close();
					} catch (IOException e) {
						logger.error("Error closing file: ", e);
					}
				}
			}
			
		} else {
			throw new ArticleException("No tml stub present for service: "+name+" at: "+scriptFile.getAbsolutePath());
		}
	}
}
