/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin;

import java.util.*;

import org.eclipse.core.resources.*;
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

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof ArrayList) {
//            filter((ArrayList) inputElement);
            currentList = (ArrayList) inputElement;
            return currentList.toArray();
        }
        System.err.println("No arraylist?! "+(inputElement==null)+"> is null");
        return null;
    }



    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // TODO Auto-generated method stub

    }

    public String getText(Object element) {
        if (element instanceof SearchMatch) {
            SearchMatch s = (SearchMatch) element;
//            JavaElement ik = (JavaElement) JavaCore.create(s.getResource());
            if  (!(s.getResource() instanceof IFile)) {
                return "<unknown>";
            }
            ICompilationUnit icu = JavaCore.createCompilationUnitFrom((IFile)s.getResource());
            if (icu == null) {
                 return s.getResource().getFullPath().toString();
            }

            try {
                IType[] ttt = icu.getAllTypes();
                for (int i = 0; i < ttt.length; i++) {
                    return ttt[i].getFullyQualifiedName();
                }
            } catch (JavaModelException e) {
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
