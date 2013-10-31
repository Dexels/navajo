/*
 * Created on Feb 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.PlatformObject;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoNature extends PlatformObject implements IProjectNature {

    private IProject myProject = null;

    public String BUILDER_ID = "com.dexels.plugin.NavajoScriptBuilder";

    /**
     *  
     */
    public NavajoNature() {
        super();
        System.err.println("CONSTRUCTING: NAVAJONATURE");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    @Override
	public void configure() throws CoreException {
    	System.err.println("NOT adding builder to project.");
//    	if(true) {
//    		return;
//    	}
//    	addBuilderToProject(myProject);
//        new Job("Building scripts...") {
//            protected IStatus run(IProgressMonitor monitor) {
//                try {
//                    try {
//                        System.err.println("Building scriptssssss...");
//                        IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(myProject);
//                        if (!tml.exists()) {
//                            tml.create(true, true, monitor);
//                        }
//                        IFolder scripts = NavajoScriptPluginPlugin.getDefault().getScriptFolder(myProject);
//                        if (!scripts.exists()) {
//                            scripts.create(true, true, monitor);
//                        }
//                        IFolder compiled = NavajoScriptPluginPlugin.getDefault().getCompileFolder(myProject);
//                        if (!compiled.exists()) {
//                            compiled.create(true, true, monitor);
//                        }
//                    } catch (NavajoPluginException e1) {
//                        NavajoScriptPluginPlugin.getDefault().log("Error configuring navajo project!",e1);
//                    }
//                    System.err.println("Starting build");
//                    myProject.build(IncrementalProjectBuilder.FULL_BUILD, BUILDER_ID, null, monitor);
//                } catch (CoreException e) {
//                    NavajoScriptPluginPlugin.getDefault().log("Error preforming full build after configuring navajo project!",e);
//                }
//                return Status.OK_STATUS;
//            }
//        }.schedule();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    @Override
	public void deconfigure() throws CoreException {
        System.err.println("Deconfiguring NavajoNature. NOT decomissioning Builder");
//        if(true) {
//        	return;
//        }
//        removeBuilderFromProject(myProject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    @Override
	public IProject getProject() {
        return myProject;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    @Override
	public void setProject(IProject project) {
        myProject = project;
    }

    public void addBuilderToProject(IProject project) {
        System.err.println("Adding builder to project...");
        // Cannot modify closed projects.
        if (!project.isOpen()) {
            System.err.println("Not open");
            return;
        }
        IProjectDescription description;
        try {
            description = project.getDescription();
        } catch (CoreException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error getting project description of project: "+project.getName(),e);
            return;
        }

        // Look for builder already associated.
        ICommand[] cmds = description.getBuildSpec();
        for (int j = 0; j < cmds.length; j++)
            if (cmds[j].getBuilderName().equals(BUILDER_ID)) {
                System.err.println("ALREADY FOUND: " + cmds[j].getBuilderName());
                return;
            }

         // TODO MAKE SURE THE BUILDER ORDER IS CORRECT
            
        // Associate builder with project.
        ICommand newCmd = description.newCommand();
        newCmd.setBuilderName(BUILDER_ID);
        List<ICommand> newCmds = new ArrayList<ICommand>();

        newCmds.addAll(Arrays.asList(cmds));
        newCmds.add(newCmd);
        description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));
        try {
            System.err.println("Committing to project...");
            project.setDescription(description, null);

        } catch (CoreException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error setting project description of project: "+project.getName(),e);
            //         FavoritesLog.logError(e);
        }
    }

    public void removeBuilderFromProject(IProject project) {
        // Cannot modify closed projects.
        if (!project.isOpen())
            return;

        // Get the description.
        IProjectDescription description;
        try {
            description = project.getDescription();
        } catch (CoreException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error getting project description of project: "+project.getName(),e);
            return;
        }

        // Look for builder.
        int index = -1;
        ICommand[] cmds = description.getBuildSpec();
        for (int j = 0; j < cmds.length; j++) {
            if (cmds[j].getBuilderName().equals(BUILDER_ID)) {
                index = j;
                break;
            }
        }
        if (index == -1)
            return;

        // Remove builder from project.
        List<ICommand> newCmds = new ArrayList<ICommand>();
        newCmds.addAll(Arrays.asList(cmds));
        newCmds.remove(index);
        description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));
        try {
            project.setDescription(description, null);
        } catch (CoreException e) {
            //          FavoritesLog.logError(e);
            NavajoScriptPluginPlugin.getDefault().log("Error setting project description of project: "+project.getName(),e);
        }
    }
}
