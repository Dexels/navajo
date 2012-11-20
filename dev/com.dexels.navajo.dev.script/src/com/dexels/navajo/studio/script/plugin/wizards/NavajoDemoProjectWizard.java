package com.dexels.navajo.studio.script.plugin.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "tsl". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */

public class NavajoDemoProjectWizard extends Wizard implements INewWizard {
	private WizardNewProjectCreationPage page;

//	private ISelection selection;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoDemoProjectWizard.class);

	protected IResource selectedFile = null;


	/**
	 * Constructor for NewScriptWizard.
	 */
	public NavajoDemoProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		page = new WizardNewProjectCreationPage("Create a demo project");
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	@Override
	public boolean performFinish() {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					URL u = new URL("http://www.navajo.nl/downloads/NavajoServer.tgz");
					logger.warn("Not performing finish for url "+u);
//					doFinish(u,projectName, monitor);
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException
					.getMessage());
			return false;
		}
		return true;
	}


	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */
//
//	private void doFinish(URL downloadUrl, String projectName, final IProgressMonitor monitor) {
//		// create a sample file
//
//		try {
//			URLConnection uco = downloadUrl.openConnection();
//			final int contentLength = uco.getContentLength();
//			InputStream is = uco.getInputStream();
//
//			System.err.println("stream opened. size: " + contentLength);
//			Display.getDefault().syncExec(new Runnable() {
//				public void run() {
//					selectedPath = page.getLocationPath();
//					// monitor.setTaskName();
//					monitor
//							.beginTask("Downloading installation",
//									contentLength);
//				}
//			});
//
//			IProjectDescription pp = ResourcesPlugin.getWorkspace()
//					.newProjectDescription(projectName);
////			ResourcesPlugin.getWorkspace().addResourceChangeListener(
////
////					new IResourceChangeListener(){
////
////						public void resourceChanged(IResourceChangeEvent event) {
////						}});
//
//			final IProject ipp = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//			ipp.create(pp, monitor);
//			
//			final IProject reloaded = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//			System.err.println("Getlocation: " + reloaded.getLocationURI());
//			GZIPInputStream giz = new GZIPInputStream(is);
//			System.err.println("gzipinputstream opened");
//			final TarInputStream tis = new TarInputStream(giz);
//			System.err.println("tarinputstream opened");
//			try {
//				System.err.println("REAL path:" + selectedPath.toOSString());
//				while (true) {
//					final TarEntry te = tis.getNextEntry();
//
//					if (te == null) {
//						break;
//					}
////					System.err.println("Entry: " + te.getName() + " size: "
////							+ te.getSize());
//					Display.getDefault().syncExec(new Runnable() {
//						public void run() {
//							monitor.worked((int) te.getSize());
//						}
//					});
//					if (te.getFileType() == TarEntry.DIRECTORY) {
//						createDirectory(te.getName(),reloaded.getLocation());
//					} else {
//						 createFile(te.getName(),reloaded.getLocation(),tis,te.getSize());
//					}
//
//					long l = te.getSize();
//					byte[] b = new byte[(int) l];
//
//					int result = tis.read(b, 0, (int) l);
//				}
//			} catch (Throwable e) {
//				e.printStackTrace();
//			}
//			ipp.refreshLocal(IResource.DEPTH_INFINITE, monitor);
//				ipp.open(monitor);
//				Display.getDefault().syncExec(new Runnable() {
//					public void run() {
//						try {
//							ipp.refreshLocal(IResource.DEPTH_INFINITE, monitor);
//						} catch (CoreException e) {
//							e.printStackTrace();
//						}
//							}
//				});
//			Job j;
//			j = new Job("Recompiling new project...") {
//	            @Override
//				protected IStatus run(IProgressMonitor monitor) {
//	            	try {
//						touchRecursive(ipp,monitor);
//					} catch (CoreException e) {
//						e.printStackTrace();
//					}
//	            	return Status.OK_STATUS;
//                }
//			};
//			j.schedule(1000);
//			
//			} catch (Throwable e) {
//			e.printStackTrace();
//		}
//
//		System.err.println("Creating project: " + projectName);
//		}
//


//	private void createFile(String name, IPath path, TarInputStream tis,
//			long size) throws IOException {
//		if (name.startsWith("NavajoServer/")) {
//			name = name.substring("NavajoServer/".length());
//		}
//		Path p = new Path(path.toString()+"/"+name);
//		File f = new File(p.toString());
//		FileOutputStream fos = new FileOutputStream(f);
//		copyResource(fos, tis,size);
//		fos.flush();
//		fos.close();
//	}  
	
//	private final void copyResource(OutputStream out, InputStream in, long size) throws IOException {
//	    BufferedInputStream bin = new BufferedInputStream(in);
//	    BufferedOutputStream bout = new BufferedOutputStream(out);
//	    int count = 0;
//	    byte[] buffer = new byte[10000];
//	    int read;
//	    int remaining = (int) size;
//	    while((read = bin.read(buffer,0,Math.min(buffer.length, remaining))) > -1) {
//	    	count += read;
//	    	remaining -= read;
//	    	bout.write(buffer, 0, read);
//	    	if(remaining<=0) {
//	    		break;
//	    	}
//	    }
//	    bout.flush();
//	}

	protected void createDirectory(String name, IPath path) {
		if (name.startsWith("NavajoServer/")) {
			name = name.substring("NavajoServer/".length());
		}
		Path p = new Path(path.toString()+"/"+name);
		File f = new File(p.toString());
		f.mkdirs();
	}

	
	/**
	 * We will initialize file contents with a sample text.
	 */

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if (selection == null) {
			return;
		}
		IStructuredSelection iss = selection;
		Object oss = iss.getFirstElement();
		if (!(oss instanceof IResource)) {
			return;
		}
		selectedFile = (IResource) oss;
		// System.err.println("FILE::: " + selectedFile);
	}
	
//	private void touchRecursive(IResource fold, IProgressMonitor monitor) throws CoreException {
//		fold.touch(monitor);
//		if(fold instanceof IContainer) {
//			IContainer icc = (IContainer)fold;
//			IResource[] ir =  icc.members();
//		    for (int i = 0; i < ir.length; i++) {
//	          if (ir[i] instanceof IFolder) {
//	              touchRecursive((ir[i]),monitor);
//	          }  else {
//		          ir[i].touch(monitor);
//	          }
//	      }
//		}
//	}
//

	
}