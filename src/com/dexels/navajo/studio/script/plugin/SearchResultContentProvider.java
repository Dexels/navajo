/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin;

import java.util.*;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jface.viewers.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SearchResultContentProvider extends LabelProvider implements IStructuredContentProvider {

    private ArrayList currentList;

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof ArrayList) {
            filter((ArrayList)inputElement);
            currentList = (ArrayList)inputElement;
            return currentList.toArray();
        }
        return null;
    }

    /**
     * @param list
     */
    private void filter(ArrayList list) {
        for (int i = list.size()-1; i >= 0; i--) {
            SearchMatch s = (SearchMatch)list.get(i);
            JavaElement ik = (JavaElement)JavaCore.create(s.getResource());
            if (ik.getCompilationUnit()==null) {
                list.remove(i);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // TODO Auto-generated method stub

    }
    public String getText(Object element) {
//        System.err.println("CLASS: "+element.getClass());
        if (element instanceof SearchMatch) {
            SearchMatch s = (SearchMatch)element;
//            new JavaElement()
//            System.err.println("Element: "+s.getElement());
            JavaElement ik = (JavaElement)JavaCore.create(s.getResource());
            if (ik == null) {
                return "<unknown>";
            }
            if (ik.getCompilationUnit() == null) {
                System.err.println("STRANGE STUFF: "+ik.getClass());
                System.err.println("AAP: "+ik);
                return ik.toString();
            }
            
            try {
                IType[] ttt = ik.getCompilationUnit().getAllTypes();
                for (int i = 0; i < ttt.length; i++) {
//                    System.err.println("AAP: "+ttt[i].getFullyQualifiedName()+" size: "+ttt.length);
                    return ttt[i].getFullyQualifiedName();
                }
            } catch (JavaModelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return s.getResource().getName();
        }
        return super.getText(element);
    }

    /**
     * @param value
     * @return
     */
    public int getIndexOfLabel(String value) {
        for (int i = 0; i < currentList.size(); i++) {
            if (getText(currentList.get(i)).equals(value)) {
                return i;
            }
        }
        return -1;
    }

}
