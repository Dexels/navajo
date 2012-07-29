package com.dexels.navajo.server.internal;

import java.io.File;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.FileNavajoConfig;
import com.dexels.navajo.server.NavajoIOConfig;

public class LegacyNavajoIOConfig extends FileNavajoConfig implements NavajoIOConfig {

	@Override
	public String getConfigPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getConfigPath();
	}

	@Override
	public String getRootPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getRootPath();
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

	@Override
	public File getContextRoot() {
		return DispatcherFactory.getInstance().getNavajoConfig().getContextRoot();
	}


}
