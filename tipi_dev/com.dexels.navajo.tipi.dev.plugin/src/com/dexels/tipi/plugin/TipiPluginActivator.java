package com.dexels.tipi.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiPluginActivator extends AbstractUIPlugin {
   private static TipiPluginActivator plugin;
   private final static Logger logger = LoggerFactory
		.getLogger(TipiPluginActivator.class);

   public TipiPluginActivator() {
   	logger.info("Creating activator!");
   	plugin = this;
   }

   @Override
public void start(BundleContext context) throws Exception {
      super.start(context);
      logger.info("Tipi plugin loaded with context");
		super.getPreferenceStore().setDefault("tipiServerUrl", "http://localhost:8080/TipiServer/TipiAdminServlet");
		super.getPreferenceStore().setDefault("tipiServerUsername", "ad");
		super.getPreferenceStore().setDefault("tipiServerPassword", "pw");
   }

   public static TipiPluginActivator getDefault() {
      return plugin;
  }
}
