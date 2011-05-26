package com.dexels.navajo.server.embedded;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jetty.server.Server;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.dsl.expression.ui.contentassist.NavajoExpressionProposalProvider;
import com.dexels.navajo.studio.script.plugin.ServerInstance;

/**
 * The activator class controls the plug-in life cycle
 */
public class EmbeddedServerActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dexels.navajo.server.embedded"; //$NON-NLS-1$

	// The shared instance
	private static EmbeddedServerActivator plugin;
	protected Server jettyServer;
	private NavajoContext currentContext = null;
//	private ServletContextHandler webappContextHandler;
	private NavajoExpressionProposalProvider navajoExpressionProvider;

	private final Map<IProject,ServerInstance> projectMap = new HashMap<IProject, ServerInstance>();
	
	public NavajoContext getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(NavajoContext currentContext) {
		this.currentContext = currentContext;
	}

	
	public NavajoExpressionProposalProvider getNavajoExpressionProvider() {
		return navajoExpressionProvider;
	}

	/**
	 * The constructor
	 */
	public EmbeddedServerActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
//		context.installBundle("bla");
		plugin = this;
		touchNavajoParts(context);
//		   getB	"com.dexels.navajo.dsl.expression.ui.contentassist.NavajoExpressionProposalProvider"
		
	}

	private void touchNavajoParts(BundleContext context) {
		System.err.println("Navajo embedded server activating!");
		navajodocument.Version.getRandom();
		navajoclient.Version.getRandom();
		navajolisteners.Version.getRandom();
		navajo.Version.getRandom();
		navajorhino.Version.getRandom();
		navajoadapters.Version.getRandom();
		navajofunctions.Version.getRandom();
		navajoenterprise.Version.getRandom();
		navajoenterpriseadapters.Version.getRandom();
		navajoenterpriselisteners.Version.getRandom();
//		NavajoExpressionRuntimeModule a;
		System.err.println("Touch complete!");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EmbeddedServerActivator getDefault() {
		return plugin;
	}

	public ServerInstance getServerInstanceForProject(IProject currentProject) {
		return projectMap.get(currentProject);
	}

	public void registerServerInstance(IProject navajoProject,
			ServerInstance serverInstanceImpl) {
		projectMap.put(navajoProject, serverInstanceImpl);
		
	}

	public void deregisterServerInstance(IProject project) {
		projectMap.remove(project);
	}

	public Map<String,ServerInstance> getSelectorMap() {
		Map<String,ServerInstance> result = new HashMap<String,ServerInstance>();
		for (Map.Entry<IProject,ServerInstance> e : projectMap.entrySet()) {
			String label = e.getKey().getProject().getName()+" @localhost:"+e.getValue().getPort();
			result.put(label, e.getValue());
		}
		return result;
	}
}
