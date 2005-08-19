/*
 * Created on Aug 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import com.dexels.navajo.studio.script.plugin.*;

public class PreferenceComponentFactory {

       public static ComboViewer createProjectListCombo(Composite parent, String nature) {
           ComboViewer cv = new ComboViewer(parent);
           cv.setLabelProvider(new ProjectViewLabelProvider());
           cv.setContentProvider(new ProjectViewContentProvider(nature));
           return cv;
       }


 }
class ProjectViewLabelProvider extends LabelProvider implements ILabelProvider {


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
        // TODO Auto-generated method stub
    }

    public Object[] getElements(Object inputElement) {
        ArrayList l;
        try {
            l = NavajoScriptPluginPlugin.getProjectsByNature(myNature);
            return l.toArray();
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return new Object[]{};
    }
       
   }
