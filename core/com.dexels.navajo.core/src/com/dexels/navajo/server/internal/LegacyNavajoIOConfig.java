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
	public String getAdapterPath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getAdapterPath();
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

	@Override
	public String getResourcePath() {
		return DispatcherFactory.getInstance().getNavajoConfig().getResourcePath();
	}

    @Override
    public String getDeployment() {
        return DispatcherFactory.getInstance().getNavajoConfig().getDeployment();
    }


}
