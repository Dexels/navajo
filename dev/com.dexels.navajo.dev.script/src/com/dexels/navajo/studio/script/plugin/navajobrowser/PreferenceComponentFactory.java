/*
 * Created on Aug 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

public class PreferenceComponentFactory {

       public static ComboViewer createProjectListCombo(Composite parent, String nature) {
           ComboViewer cv = new ComboViewer(parent);
           cv.setLabelProvider(new ProjectViewLabelProvider());
           cv.setContentProvider(new ProjectViewContentProvider(nature));
           return cv;
       }


 }
class ProjectViewLabelProvider extends LabelProvider implements ILabelProvider {


    @Override
	public String getText(Object element) {
        IProject ip = (IProject)element;
        return ip.getName();
    }
   }

class ProjectViewContentProvider implements IStructuredContentProvider {

    private final String myNature;
    public ProjectViewContentProvider(String nature) {
        myNature = nature;
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    public Object[] getElements(Object inputElement) {
        List<IProject> l;
        try {
            l = NavajoScriptPluginPlugin.getProjectsByNature(myNature);
            return l.toArray();
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return new Object[]{};
    }
       
   }
