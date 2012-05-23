package com.dexels.tipi.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class TipiPluginActivator extends AbstractUIPlugin {
   private static TipiPluginActivator plugin;
   
   public TipiPluginActivator() {
   	System.err.println("Creating activator!");
   	plugin = this;
   }

   public void start(BundleContext context) throws Exception {
      super.start(context);
      System.err.println("Tipi plugin loaded with context");
		super.getPreferenceStore().setDefault("tipiServerUrl", "http://localhost:8080/TipiServer/TipiAdminServlet");
		super.getPreferenceStore().setDefault("tipiServerUsername", "ad");
		super.getPreferenceStore().setDefault("tipiServerPassword", "pw");
   }

   public static TipiPluginActivator getDefault() {
      return plugin;
  }
}
