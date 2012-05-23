package com.dexels.tipi.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

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
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.VersionResolver;
import com.dexels.navajo.tipi.projectbuilder.WebDescriptorBuilder;
import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiLocalJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiRemoteJnlpProjectBuilder;
import com.dexels.navajo.tipi.projectbuilder.impl.TipiWebProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class TipiRepositoryBuilder extends IncrementalProjectBuilder {

	class TipiResourceProjectVisitor implements IResourceDeltaVisitor {
		
		private final boolean clean;
		private final IProgressMonitor myMonitor;
		private boolean needRebuild = false;
		public boolean isNeedRebuild() {
			return needRebuild;
		}

		public TipiResourceProjectVisitor(boolean clean, IProgressMonitor prm) {
			this.clean = clean;
			myMonitor = prm; 
		}

		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// handle added resource
				boolean b = checkTipiProperties(resource,clean,myMonitor);
				needRebuild = b || needRebuild;
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				boolean c = checkTipiProperties(resource,clean,myMonitor);
				needRebuild = c || needRebuild;
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

	@SuppressWarnings("rawtypes")
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

	boolean checkTipiProperties(IResource resource,boolean clean,IProgressMonitor monitor) {
//		System.err.println("Entering rebuild.");
//		System.err.println("resource name: "+resource.getName());
		monitor.setTaskName("Rebuilding tipi project");
		if(!(resource instanceof IFile)) {
			return false;
		}
		IProject myProject = resource.getProject();
		IFolder profilesFolder = myProject.getFolder("settings/profiles");
		
		if ( resource.getName().equals("tipi.properties") || resource.getName().equals("arguments.properties") || resource.getParent().equals(profilesFolder)) {
			IFile file = (IFile) resource;
//			IContainer ic = file.getProject();
			//if(ic instanceof IProject) {
				if(!file.isSynchronized(IResource.DEPTH_INFINITE)) {
					try {
						file.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				monitor.worked(5);
				return true;
//				rebuildLocalTipi((IProject)ic,clean,monitor);
			//}
//			deleteMarkers(file);
//			XMLErrorHandler reporter = new XMLErrorHandler(file);
//			try {
//				getParser().parse(file.getContents(), reporter);
//			} catch (Exception e1) {
//			}
		}
		return false;
	}

	private void rebuildXsd(String repository, String extensionRepository, String extensions, File baseDir,IProgressMonitor m) {
		XsdBuilder b = new XsdBuilder();
		try { 
			b.build(repository,extensionRepository, extensions, baseDir);
			System.err.println("XSD rebuilt!");
			m.setTaskName("Building XSD");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void rebuildLocalTipi(String deployment, final IProject project, boolean clean,IProgressMonitor m) {

		try {
			IFile file = project.getFile("settings/tipi.properties");
			InputStream is = file.getContents();
			PropertyResourceBundle pe = new PropertyResourceBundle(is);
			is.close();	
			File projectPath = project.getLocation().toFile();
			String projectUrl = project.getLocationURI().toURL().toString();
			String extensions = pe.getString("extensions").trim();
			String repository = pe.getString("repository").trim();

			String extensionRepository = repository+"Extensions/";
			String developmentRepository = repository+"Development/";

			List<String> profiles = ProjectBuilder.getProfiles(projectPath);

			
			boolean useVersioning = false;
			

			String buildType = pe.getString("build").trim();
		
			rebuildXsd(repository, extensionRepository,extensions,projectPath,m);
			if(buildType==null) {
				buildType = "remote";
			}
			
		
			m.worked(5);
			if("remote".equals(buildType) || "both".equals(buildType)) {
				downloadExtensionJars(projectPath, extensions, extensionRepository,true,clean,buildType,useVersioning);
				m.worked(10);
			}
			if("local".equals(buildType) || "both".equals(buildType)) {
				downloadExtensionJars(projectPath, extensions, extensionRepository,false,clean,buildType,useVersioning);
				m.worked(10);
			}
			if("web".equals(buildType)) {
				downloadExtensionJars(projectPath, extensions, extensionRepository,false,clean,buildType,useVersioning);
				m.worked(10);
			}


			File[] cc = projectPath.listFiles();
			for (int i = 0; i < cc.length; i++) {
				if(cc[i].getName().endsWith(".jnlp")) {
					cc[i].delete();
				}
			}
			
			if("web".equals(buildType)) {
				// -- I dunno.
				// TODO Add support for profiles
				//buildWebXml
				if(profiles==null || profiles.isEmpty()) {
					buildWebXml(deployment,profiles,clean, m, projectPath, projectUrl, extensions, extensionRepository, buildType,developmentRepository);

				} else {
					for (String profile : profiles) {
						System.err.println("Building profile: "+profile);
						buildWebXml(deployment,profiles,clean, m, projectPath, projectUrl, extensions, extensionRepository, buildType,developmentRepository);
						
					}
				}
			
			} else {
				if(profiles==null || profiles.isEmpty()) {
					buildProfileJnlp(deployment,profiles,clean, m, projectPath, projectUrl, extensions, extensionRepository,developmentRepository, buildType,useVersioning);

				} else {
					for (String profile : profiles) {
						System.err.println("Building profile: "+profile);
						buildProfileJnlp(deployment,profiles,clean, m, projectPath, projectUrl, extensions, extensionRepository,developmentRepository, buildType,useVersioning);
						
					}
				}
			
			}

			
			m.worked(10);
			try {
				String cp = pe.getString("buildClasspath");
				if("true".equals(cp)) {
					buildClassPath(project, extensionRepository, extensions);
				}
			} catch (MissingResourceException e) {
			}
			
			project.refreshLocal(IResource.DEPTH_INFINITE, m);
			m.worked(10);
			
			  switchTiProject(project);
				m.worked(10);
							
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		
		
		
	}
	private void buildWebXml(String deployment,List<String> profiles,  boolean clean, IProgressMonitor m, File projectPath, String projectUrl, String extensions,String repository, String buildType, String developmentRepository) throws IOException {
		m.worked(10);
		WebDescriptorBuilder wdb = new WebDescriptorBuilder();
		Map<String,String> tipiArgs = ProjectBuilder.assembleTipi(projectPath);
		wdb.build(repository, developmentRepository,extensions,tipiArgs, deployment,projectPath, null, profiles,false);

	}
	
//	public String build(String repository, String developmentRepository, String extensions, Map<String,String> tipiProperties, String deployment, File baseDir, String codebase, List<String> profiles, boolean useVersioning)


	private void buildProfileJnlp( String deployment,List<String> profiles,  boolean clean, IProgressMonitor m, File projectPath, String projectUrl, String extensions,
			String repository,String developmentRepository, String buildType, boolean useVersioning) throws IOException {
		
		Map<String,String> tipiArgs = ProjectBuilder.assembleTipi(projectPath);
		
//		String profileName = profile==null?"Default":profile;
//		if("remote".equals(buildType) ) {
//			deleteLocalTipiBuild(projectPath,profile);
//		}
//		if("local".equals(buildType) ) {
//			deleteRemoteTipiBuild(projectPath,profile);
//		}
		m.worked(5);
		if("remote".equals(buildType) || "both".equals(buildType)) {
			RemoteJnlpBuilder r = new RemoteJnlpBuilder();
			downloadExtensionJars(projectPath, extensions, repository,true,clean,buildType,useVersioning);
			m.worked(10);
			r.build(repository,developmentRepository, extensions  ,tipiArgs,deployment, projectPath, projectUrl,profiles,useVersioning);
		}
		if("local".equals(buildType) || "both".equals(buildType)) {
			downloadExtensionJars(projectPath, extensions, repository,false,clean,buildType,useVersioning);
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			m.worked(10);
			l.build(repository,developmentRepository, extensions,tipiArgs,deployment,projectPath, projectUrl,profiles,useVersioning);
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

	private void downloadExtensionJars(File projectPath, String extensions,String repository, boolean onlyProxy, boolean clean, String buildType, boolean useVersioning) throws IOException {
		StringTokenizer st = new StringTokenizer(extensions, ",");
//		Map<String,List<String>> repDefinition= ClientActions.getExtensions(repository);
		while (st.hasMoreTokens()) {
			try {
				String token = st.nextToken();
				VersionResolver vr = new VersionResolver();
				vr.load(repository);
				Map<String,String> versionMap = vr.resolveVersion(token);
				String ext = versionMap.get("extension");
				String version = versionMap.get("version");
				XMLElement extensionXml = ClientActions.getExtensionXml(ext, version, repository);
				TipiProjectBuilder tpb = null;
				if("web".equals(buildType)) {
					tpb = new TipiWebProjectBuilder();
				} else {
					if (onlyProxy) {
						tpb = new TipiRemoteJnlpProjectBuilder();
					} else {
						tpb = new TipiLocalJnlpProjectBuilder();
					}
					
				}
				tpb.setUseVersioning(useVersioning);
				tpb.downloadExtensionJars(ext, version, new URL(repository+vr.resultVersionPath(token)+"/"), extensionXml, projectPath, clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// to be refactored:
	public void deleteLocalTipiBuild(File baseDir, String profile) {
		File lib = new File(baseDir,"lib");
//		File localJnlp = new File(baseDir,"Local.jnlp");
//		File localJnlp = null;
//		if (profile==null) {
//			localJnlp =  new File(baseDir,"Local.jnlp");
//		} else {
//			localJnlp =  new File(baseDir,profile+"Local.jnlp");
//		}
		if(lib.exists()) {
			File[] cc = lib.listFiles();
			for (int i = 0; i < cc.length; i++) {
				cc[i].delete();
			}
			lib.delete();
		}

//		if(localJnlp.exists()) {
//
//			localJnlp.delete();
//		}
	}
	// to be refactored:
	public void deleteRemoteTipiBuild(File baseDir, String profile) {
//		File remoteJnlp = null;
//		if (profile==null) {
//			remoteJnlp =  new File(baseDir,"Remote.jnlp");
//		} else {
//			remoteJnlp =  new File(baseDir,profile+"Remote.jnlp");
//		}
//
//		if(remoteJnlp.exists()) {
//			remoteJnlp.delete();
//		}
		// FIXME also remove proxy jar
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
		monitor.beginTask("Tipi rebuild", 100);
		TipiResourceProjectVisitor tipiResourceProjectVisitor = new TipiResourceProjectVisitor(false,monitor);
		delta.accept(tipiResourceProjectVisitor);
		if(tipiResourceProjectVisitor.isNeedRebuild()) {
			try {
				rebuildLocalTipi(ProjectBuilder.getCurrentDeploy(getProject().getLocation().toFile()), getProject(),false,monitor);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		monitor.done();
	}
}
