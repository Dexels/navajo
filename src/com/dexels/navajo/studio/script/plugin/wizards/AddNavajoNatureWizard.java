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
/**
     * @param project
     */
    public AddNavajoNatureWizard(IProject project) {
        myProject = project;
    }
    //    snew FileImageDescriptor("");
    public void addPages() {
    
        super.addPages();
        rootDirPage = new NavajoNatureWizardPageOne("RootPage", "",titleImage,myProject);
        repositoryDirPage = new NavajoRepositoryPage("RepositoryPage", "",titleImage,myProject); 
        addPage(rootDirPage);
        addPage(repositoryDirPage);
  }
    public boolean performFinish() {
        try {
            createRootFile();
            
            // suppress dialogs?
            NavajoScriptPluginPlugin.getDefault().addNavajoNature(myProject,false);
            
            IFile server = NavajoScriptPluginPlugin.getDefault().getServerXml(myProject);
            InputStream is = server.getContents();
            Navajo serverNavajo = NavajoFactory.getInstance().createNavajo(is);
             try {
                 is.close();
                 Property rootPath = serverNavajo.getProperty("server-configuration/paths/root");
                 Property repository = serverNavajo.getProperty("server-configuration/repository/class");
                 
                 IFolder rootFolder = myProject.getFolder(getRootDir());
                 IFolder auxFolder = rootFolder.getFolder(new Path(NavajoScriptPluginPlugin.NAVAJO_AUXILARY));
                 rootPath.setValue(auxFolder.getLocation().toOSString());
                 repository.setValue(getSelectedRepository());
                 
                 Property hotCompile = serverNavajo.getProperty("server-configuration/parameters/compile_scripts");
                 hotCompile.setValue("false");
                 
                 serverNavajo.write(System.err);
                String servPath = server.getLocation().toOSString();
                FileWriter fw = new FileWriter(servPath);
                serverNavajo.write(fw);
                fw.flush();
                fw.close();
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
        return true;
    }    

    /**
     * 
     */
    private void createRootFile() throws CoreException {
        IFile iff = myProject.getFile(".navajoroot");
        if (iff.exists()) {
            System.err.println("Root file existed. Deleting.");
            iff.delete(true, null);
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(getRootDir().getBytes());
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
