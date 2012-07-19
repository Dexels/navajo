package com.dexels.navajo.server.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoIOConfig;

public class LegacyNavajoIOConfig implements NavajoIOConfig {

	@Override
	public InputStream getScript(String name) throws IOException {
		return DispatcherFactory.getInstance().getNavajoConfig().getScript(name);
	}

	@Override
	public InputStream getConfig(String name) throws IOException {
		return DispatcherFactory.getInstance().getNavajoConfig().getConfig(name);
	}

	@Override
	public String getConfigPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getConfigPath();
	}

	@Override
	public String getRootPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getRootPath();
	}

	@Override
	public InputStream getResourceBundle(String name) throws IOException {
		return DispatcherFactory.getInstance().getNavajoConfig().getResourceBundle(name);
	}

	@Override
	@Deprecated
	public String getClassPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getClassPath();
	}

	@Override
	public String getAdapterPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getAdapterPath();
	}

	@Override
	@Deprecated
	public File getJarFolder() {
		return DispatcherFactory.getInstance().getNavajoConfig().getJarFolder();
	}

	@Override
	public String getScriptPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
	}

	@Override
	public String getCompiledScriptPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath();
	}

}
