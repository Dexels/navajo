/*
 * Created on Feb 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;

import com.dexels.navajo.studio.script.plugin.*;

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
//        BUILDER_ID = NavajoScriptPluginPlugin.getDefault().getDescriptor().getUniqueIdentifier() + ".NavajoScriptBuilder";
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        // TODO Auto-generated method stub
        System.err.println("Configuring NavajoNature");
        addBuilderToProject(myProject);
        new Job("Building scripts...") {
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    try {
                        System.err.println("Building scriptssssss...");
                        IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(myProject);
                        if (!tml.exists()) {
                            tml.create(true, true, monitor);
                        }
                        IFolder scripts = NavajoScriptPluginPlugin.getDefault().getScriptFolder(myProject);
                        if (!scripts.exists()) {
                            scripts.create(true, true, monitor);
                        }
                        IFolder compiled = NavajoScriptPluginPlugin.getDefault().getCompileFolder(myProject);
                        if (!compiled.exists()) {
                            compiled.create(true, true, monitor);
                        }
                    } catch (NavajoPluginException e1) {
                        e1.printStackTrace();
                    }
                    System.err.println("Starting build");
                    myProject.build(IncrementalProjectBuilder.FULL_BUILD, BUILDER_ID, null, monitor);
                } catch (CoreException e) {
                    e.printStackTrace();
                }
                return Status.OK_STATUS;
            }
        }.schedule();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        System.err.println("Deconfiguring NavajoNature");
        removeBuilderFromProject(myProject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return myProject;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
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

        // Get the description.
        IProjectDescription description;
        try {
            description = project.getDescription();
        } catch (CoreException e) {
            //          FavoritesLog.logError(e);
            e.printStackTrace();
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
        List newCmds = new ArrayList();

        newCmds.addAll(Arrays.asList(cmds));
        newCmds.add(newCmd);
        description.setBuildSpec((ICommand[]) newCmds.toArray(new ICommand[newCmds.size()]));
        try {
            System.err.println("Committing to project...");
            project.setDescription(description, null);

        } catch (CoreException e) {
            e.printStackTrace();
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
            //          FavoritesLog.logError(e);
            e.printStackTrace();
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
        List newCmds = new ArrayList();
        newCmds.addAll(Arrays.asList(cmds));
        newCmds.remove(index);
        description.setBuildSpec((ICommand[]) newCmds.toArray(new ICommand[newCmds.size()]));
        try {
            project.setDescription(description, null);
        } catch (CoreException e) {
            //          FavoritesLog.logError(e);
            e.printStackTrace();
        }
    }
}
