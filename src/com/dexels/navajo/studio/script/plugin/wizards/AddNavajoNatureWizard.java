/*
 * Created on Apr 27, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.wizards;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.internal.ui.wizards.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.internal.ide.dialogs.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AddNavajoNatureWizard extends Wizard implements IWizard {

    //    public void init(IWorkbench workbench, IStructuredSelection
    // currentSelection) {
    //        super.init(workbench, currentSelection);
    //// setInitialProjectCapabilities(new Capability[]{});
    //    }
  
    ImageDescriptor titleImage = ImageDescriptor.createFromURL(getClass().getClassLoader().getResource("/com/dexels/navajo/studio/images/navajo.jpg"));
    private NavajoNatureWizardPageOne rootDirPage;
    private NavajoRepositoryPage repositoryDirPage;
    private final IProject myProject;
    private NavajoCheckProjectPage navajoCheckProject;
    private Job finishingJob;
/**
     * @param project
     */
    public AddNavajoNatureWizard(IProject project) {
        myProject = project;
    }
    //    snew FileImageDescriptor("");
    public void addPages() {
    
        super.addPages();
        navajoCheckProject = new NavajoCheckProjectPage("CheckPage", "",titleImage,myProject);
        rootDirPage = new NavajoNatureWizardPageOne("RootPage", "",titleImage,myProject);
        repositoryDirPage = new NavajoRepositoryPage("RepositoryPage", "",titleImage,myProject); 
        addPage(navajoCheckProject);
        addPage(rootDirPage);
        addPage(repositoryDirPage);
  }
    public boolean performFinish() {
        final String selRep = getSelectedRepository();
        final String selRoot = getRootDir();
        finishingJob = new Job("Adding navajo nature...") {
                    protected IStatus run(IProgressMonitor monitor) {
                        try {
                            monitor.beginTask("Adding nature...", 5);
                            createRootFile(selRoot);
                            
                            // suppress dialogs?
                            NavajoScriptPluginPlugin.getDefault().addNavajoNature(myProject,true);
                            monitor.worked(1);
                            IFile server = NavajoScriptPluginPlugin.getDefault().getServerXml(myProject);
                            InputStream is = server.getContents();
                            Navajo serverNavajo = NavajoFactory.getInstance().createNavajo(is);
                            monitor.worked(1);
                             try {
                                 is.close();
        
                                 setupServer(server, serverNavajo,selRoot,selRep);
                                 monitor.worked(1);
                                 
                                   
                                 serverNavajo.write(System.err);
                                 monitor.worked(1);
                                String servPath = server.getLocation().toOSString();
                                FileWriter fw = new FileWriter(servPath);
                                serverNavajo.write(fw);
                                fw.flush();
                                fw.close();
                                monitor.worked(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    is.close();
                                } catch (IOException e1) {
                                     e1.printStackTrace();
                                }
                            }
                            
                        } catch (CoreException ce) {
                            ce.printStackTrace();
                        }
                       return Status.OK_STATUS;
                    }};
    //        finishingJob.setUser(true);
        finishingJob.schedule();
          return true;
    }    

    private void setupServer(IFile server, Navajo serverNavajo, String rootDir, String selectedRepository) throws CoreException {
        IFolder rootFolder = myProject.getFolder(rootDir);
         IFolder auxFolder = rootFolder.getFolder(new Path(NavajoScriptPluginPlugin.NAVAJO_AUXILARY));
           Message servConf = serverNavajo.getMessage("server-configuration");
         if (servConf==null) {
//             NavajoScriptPluginPlugin.getDefault().reportProblem("'server-configuration' message not found in server.xml", server, 0, true, IMarker.PROBLEM, "aap", 1, 10);
//         throw new CoreException(Status.OK_STATUS);
             servConf = NavajoFactory.getInstance().createMessage(serverNavajo, "server-configuration");
             servConf.addMessage(servConf);

         }
         Message paths = servConf.getMessage("paths");
         if (paths==null) {
//              NavajoScriptPluginPlugin.getDefault().reportProblem("'paths' message not found in server-configuration", server, 0, true, IMarker.PROBLEM, "aap", 1, 10);
//          throw new CoreException(Status.OK_STATUS);
             paths = NavajoFactory.getInstance().createMessage(serverNavajo, "paths");
             servConf.addMessage(paths);
         }
         Message parameters = servConf.getMessage("parameters");
         if (parameters==null) {
             parameters = NavajoFactory.getInstance().createMessage(serverNavajo, "parameters");
             servConf.addMessage(parameters);
         }
         Message repositoryMessage = servConf.getMessage("repository");
         if (repositoryMessage==null) {
             repositoryMessage = NavajoFactory.getInstance().createMessage(serverNavajo, "repository");
             servConf.addMessage(repositoryMessage);
         }
         
         
         Property rootPath = serverNavajo.getProperty("server-configuration/paths/root");
         Property repository = serverNavajo.getProperty("server-configuration/repository/class");
         Property hotCompile = serverNavajo.getProperty("server-configuration/parameters/compile_scripts");
         
           try {
            if (rootPath==null) {
                   rootPath = NavajoFactory.getInstance().createProperty(serverNavajo, "root", Property.STRING_PROPERTY, "", 99, null, Property.DIR_IN);
                   paths.addProperty(rootPath);
               }
               if (repository==null) {
                   repository = NavajoFactory.getInstance().createProperty(serverNavajo, "class", Property.STRING_PROPERTY, "", 99, null, Property.DIR_IN);
                   repositoryMessage.addProperty(repository);
               }
               if (hotCompile==null) {
                   hotCompile = NavajoFactory.getInstance().createProperty(serverNavajo, "compile_scripts", Property.STRING_PROPERTY, "", 99, null, Property.DIR_IN);
                   parameters.addProperty(hotCompile);
               }

               hotCompile.setValue("false");
               rootPath.setValue(auxFolder.getLocation().toOSString());
             repository.setValue(selectedRepository);
        } catch (NavajoException e) {
            System.err.println("Serious problems configuring server.xml!");
            e.printStackTrace();
        }
    }
    /**
     * 
     */
    private void createRootFile(String rootDir) throws CoreException {
        IFile iff = myProject.getFile(".navajoroot");
        if (iff.exists()) {
            System.err.println("Root file existed. Deleting.");
            iff.delete(true, null);
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(rootDir.getBytes());
            iff.create(bais,true, null);
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getRootDir() {
        return rootDirPage.getRootDir();
    }
    
    public String getSelectedRepository() {
        return repositoryDirPage.getSelectedRepository();
    }


}
