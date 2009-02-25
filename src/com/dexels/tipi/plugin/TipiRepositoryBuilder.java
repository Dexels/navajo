package com.dexels.tipi.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import tipiplugin.views.TipiHelpView;

import com.dexels.navajo.tipi.projectbuilder.ClasspathBuilder;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.RemoteJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class TipiRepositoryBuilder extends IncrementalProjectBuilder {

	class TipiResourceProjectVisitor implements IResourceDeltaVisitor {
		
		private final boolean clean;
		private final IProgressMonitor myMonitor;
		public TipiResourceProjectVisitor(boolean clean, IProgressMonitor prm) {
			this.clean = clean;
			myMonitor = prm; 
		}

		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// handle added resource
				checkTipiProperties(resource,clean,myMonitor);
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				checkTipiProperties(resource,clean,myMonitor);
				break;
			}
			//return true to continue visiting children.
			return true;
		}
	}

	class TipiResourceVisitor implements IResourceVisitor {
		private final IProgressMonitor myMonitor;
		public TipiResourceVisitor(IProgressMonitor prm) {
			myMonitor = prm; 
		}
		public boolean visit(IResource resource) {
			checkTipiProperties(resource,true,myMonitor);
			//return true to continue visiting children.
			return true;
		}
	}

	public static final String BUILDER_ID = "TipiPlugin.tipiRepositoryBuilder";

//	private static final String MARKER_TYPE = "TipiPlugin.xmlProblem";

	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		
		return null;
	}

	void checkTipiProperties(IResource resource,boolean clean,IProgressMonitor monitor) {
		System.err.println("Entering rebuild.");
		System.err.println("resource name: "+resource.getName());
		if(!(resource instanceof IFile)) {
			System.err.println("No file");
			return;
		}
		if ( resource.getName().equals("tipi.properties") || resource.getName().equals("arguments.properties")) {
			IFile file = (IFile) resource;
			IContainer ic = file.getProject();
			//if(ic instanceof IProject) {
				if(!file.isSynchronized(IResource.DEPTH_INFINITE)) {
					try {
						file.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				
				rebuildLocalTipi((IProject)ic,clean,monitor);
			//}
//			deleteMarkers(file);
//			XMLErrorHandler reporter = new XMLErrorHandler(file);
//			try {
//				getParser().parse(file.getContents(), reporter);
//			} catch (Exception e1) {
//			}
		}
	}

	private void rebuildXsd(String repository, String extensions, File baseDir,IProgressMonitor m) {
		XsdBuilder b = new XsdBuilder();
		b.build(repository, extensions, baseDir);
		System.err.println("XSD rebuilt!");
		m.setTaskName("Building XSD");
	}

	private void rebuildLocalTipi(final IProject project, boolean clean,IProgressMonitor m) {

		try {
			IFile file = project.getFile("settings/tipi.properties");
			InputStream is = file.getContents();
			PropertyResourceBundle pe = new PropertyResourceBundle(is);
			is.close();	
			File projectPath = project.getLocation().toFile();
			String projectUrl = project.getLocationURI().toURL().toString();
			String extensions = pe.getString("extensions").trim();
			String repository = pe.getString("repository").trim();
			String buildType = pe.getString("build").trim();
			
			
			rebuildXsd(repository,extensions,projectPath,m);
			if(buildType==null) {
				buildType = "remote";
			}
			if("remote".equals(buildType) ) {
				deleteLocalTipiBuild(projectPath);
	}
			if("local".equals(buildType) ) {
				deleteRemoteTipiBuild(projectPath);
			}

			if("remote".equals(buildType) || "both".equals(buildType)) {
				RemoteJnlpBuilder r = new RemoteJnlpBuilder();
				downloadExtensionJars(projectPath, extensions, repository,true,clean);
				m.setTaskName("Downloading jars...");
				m.worked(1);
				r.build(repository, extensions,projectPath, projectUrl,"Remote.jnlp");
			}
			if("local".equals(buildType) || "both".equals(buildType)) {
				downloadExtensionJars(projectPath, extensions, repository,false,clean);
				LocalJnlpBuilder l = new LocalJnlpBuilder();
				l.build(repository, extensions,projectPath, projectUrl,"Local.jnlp");
				m.worked(1);

			}
			
			try {
				String cp = pe.getString("buildClasspath");
				if("true".equals(cp)) {
					buildClassPath(project, repository, extensions);
				}
			} catch (MissingResourceException e) {
			}
			
			project.refreshLocal(IResource.DEPTH_INFINITE, m);
 	
			  switchTiProject(project);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		
	}

	private void switchTiProject(final IProject project) {
		Display.getDefault().asyncExec(new Runnable(){
				public void run() {
		
		 IWorkbench wb = PlatformUI.getWorkbench();
		   IWorkbenchWindow[] windows = wb.getWorkbenchWindows();
		   for (int j = 0; j < windows.length; j++) {
			   IWorkbenchPage[] pages = windows[j].getPages();
			   for (int i = 0; i < pages.length; i++) {
					  final TipiHelpView ww = (TipiHelpView) pages[i].findView("tipiplugin.views.TipiHelpView");
					  if(ww!=null) {
								ww.switchToProject(project.getName());
					  }

			   }
				
			}
				}});
	}

	
private void buildClassPath(IProject project, String repository, String extensions) {	
	try {
		ClasspathBuilder cb = new ClasspathBuilder();
		cb.build(repository, extensions,project.getLocation().toFile());
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	private void downloadExtensionJars(File projectPath, String extensions,
			String repository, boolean onlyProxy, boolean clean) {
		StringTokenizer st = new StringTokenizer(extensions, ",");
		while (st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
//					ClientActions.
//					URL rep = new URL(repository);
//					URL projectURL = new URL(rep,ext+"/");
				//URL extensionURL = new URL(projectURL,"definition.xml");
				//XMLElement result = ClientActions.getXMLElement(extensionURL);
				XMLElement extensionXml = ClientActions.getExtensionXml(ext, repository);
				if (onlyProxy) {
					//ClientActions.downloadProxyJars(project, projectURL, result, baseDir)
					ClientActions.downloadProxyJars(ext,new URL(repository+ext+"/"),extensionXml,projectPath,clean);
				} else {
					ClientActions.downloadExtensionJars(ext,new URL(repository+ext+"/"),extensionXml,projectPath,clean);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// to be refactored:
	public void deleteLocalTipiBuild(File baseDir) {
		File lib = new File(baseDir,"lib");
		File localJnlp = new File(baseDir,"Local.jnlp");
		if(lib.exists()) {
			File[] cc = lib.listFiles();
			for (int i = 0; i < cc.length; i++) {
				cc[i].delete();
			}
			lib.delete();
		}
		if(localJnlp.exists()) {

			localJnlp.delete();
		}
	}
	// to be refactored:
	public void deleteRemoteTipiBuild(File baseDir) {
		File remoteJnlp = new File(baseDir,"Remote.jnlp");
		if(remoteJnlp.exists()) {
			remoteJnlp.delete();
		}
	}
	
//	private void deleteMarkers(IFile file) {
//		try {
//			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
//		} catch (CoreException ce) {
//		}
//	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			getProject().accept(new TipiResourceVisitor(monitor));
		} catch (CoreException e) {
		}
	}

//	private SAXParser getParser() throws ParserConfigurationException,
//			SAXException {
//		if (parserFactory == null) {
//			parserFactory = SAXParserFactory.newInstance();
//		}
//		return parserFactory.newSAXParser();
//	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new TipiResourceProjectVisitor(false,monitor));
	}
}
