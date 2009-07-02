package com.dexels.tipi.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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
import com.dexels.navajo.tipi.projectbuilder.ProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.RemoteJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.VersionResolver;
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
//		System.err.println("Entering rebuild.");
//		System.err.println("resource name: "+resource.getName());
		monitor.setTaskName("Rebuilding tipi project");
		if(!(resource instanceof IFile)) {
			return;
		}
		IProject myProject = resource.getProject();
		IFolder profilesFolder = myProject.getFolder("settings/profiles");
		
		if ( resource.getName().equals("tipi.properties") || resource.getName().equals("arguments.properties") || resource.getParent().equals(profilesFolder)) {
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
				monitor.worked(5);
				
				rebuildLocalTipi((IProject)ic,clean);
			//}
//			deleteMarkers(file);
//			XMLErrorHandler reporter = new XMLErrorHandler(file);
//			try {
//				getParser().parse(file.getContents(), reporter);
//			} catch (Exception e1) {
//			}
		}
	}



	private void rebuildLocalTipi(final IProject project, boolean clean) {

		try {
			IFile file = project.getFile("settings/tipi.properties");
			File projectPath = project.getLocation().toFile();
			String projectUrl = project.getLocationURI().toURL().toString();
			InputStream is = file.getContents();
			ProjectBuilder.buildTipiProject(projectPath, projectUrl, is, clean);
			
			project.refreshLocal(IResource.DEPTH_INFINITE,null);
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





	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			getProject().accept(new TipiResourceVisitor(monitor));
		} catch (CoreException e) {
		}
	}



	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		monitor.beginTask("Tipi rebuild", 100);
		delta.accept(new TipiResourceProjectVisitor(false,monitor));
		monitor.done();
	}
}
