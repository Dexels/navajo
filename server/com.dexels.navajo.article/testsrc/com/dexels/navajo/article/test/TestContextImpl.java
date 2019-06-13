package com.dexels.navajo.article.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;

import com.dexels.navajo.article.command.impl.ElementCommand;
import com.dexels.navajo.article.command.impl.ServiceCommand;
import com.dexels.navajo.article.command.impl.SetValueCommand;
import com.dexels.navajo.article.command.impl.TableCommand;
import com.dexels.navajo.article.impl.BaseContextImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.server.NavajoIOConfig;

public abstract class TestContextImpl extends BaseContextImpl {
	public TestContextImpl(final String rootPath) {
		super();
		setConfig(new NavajoIOConfig() {
			
			@Override
			public void writeOutput(String scriptName, String suffix, InputStream is)
					throws IOException {
				
			}
			
			@Override
			public void writeConfig(String name, Navajo conf) throws IOException {
				
			}
			
			@Override
			public Navajo readConfig(String s) throws IOException {
				return null;
			}
			
			@Override
			public String getScriptPath() {
				return null;
			}

			@Override
			public InputStream getScript(String name) throws IOException {
				return null;
			}
			
			@Override
			public String getRootPath() {
				return rootPath;
			}
			
			@Override
			public String getResourcePath() {
				return null;
			}
			
			@Override
			public InputStream getResourceBundle(String name) throws IOException {
				return null;
			}
			
			@Override
			public Writer getOutputWriter(String outputPath, String scriptPackage,
					String scriptName, String extension) throws IOException {
				return null;
			}
			
			@Override
			public Reader getOutputReader(String outputPath, String scriptPackage,
					String scriptName, String extension) throws IOException {
				return null;
			}

			@Override
			public File getContextRoot() {
				return null;
			}
			
			@Override
			public String getConfigPath() {
				return null;
			}
			
			@Override
			public InputStream getConfig(String name) throws IOException {
				return null;
			}
			
			@Override
			public String getCompiledScriptPath() {
				return null;
			}

			
			@Override
			public String getAdapterPath() {
				return null;
			}


			@Override
			public boolean hasTenantScriptFile(String rpcName, String tenant,String scriptPath) {
				return false;
			}

			@Override
			public InputStream getScript(String name, String tenant,String extension)
					throws IOException {
				return null;
			}


            @Override
            public String getDeployment() {
                return null;
            }
		});
		final ElementCommand element = new ElementCommand("element");
		
		addCommand(element);
		final SetValueCommand setvalue = new SetValueCommand("setvalue");
		addCommand(setvalue);
		final TableCommand table = new TableCommand("table");
		addCommand(table);
		ServiceCommand service = new TestServiceCommand();
		addCommand(service);
	}
	
	public abstract Navajo call(String instance, Navajo n) throws FatalException;

	public abstract Navajo call(Navajo n) throws FatalException;
}
