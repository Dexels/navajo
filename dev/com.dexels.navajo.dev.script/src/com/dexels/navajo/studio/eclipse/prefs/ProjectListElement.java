package com.dexels.navajo.studio.eclipse.prefs;

import org.eclipse.core.resources.IProject;

public class ProjectListElement {

    IProject project;

    public ProjectListElement(IProject project) {
        super();
        this.project = project;
    }

    @Override
	public String toString() {
        return getID(project);
    }

    public String getID() {
        return getID(project);
    }

    static protected String getID(IProject project) {
        return project.getName();
    }


    /*
     * @see Object#equals(Object)
     */
    @Override
	public boolean equals(Object obj) {
        if (obj instanceof ProjectListElement)
            return this.getID().equals(((ProjectListElement) obj).getID());

        return false;
    }

    /*
     * @see Object#hashCode()
     */
    @Override
	public int hashCode() {
        return this.getID().hashCode();
    }

    /**
     * Gets the project.
     * 
     * @return Returns a IProject
     */
    public IProject getProject() {
        return project;
    }

}
