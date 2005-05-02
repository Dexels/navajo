/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TypeFieldContentProvider extends LabelProvider implements IStructuredContentProvider, ITableLabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        //        IType type = (IType)inputElement;
        JavaElement ik = (JavaElement) inputElement;
        try {

            ICompilationUnit cu = ik.getCompilationUnit();
            if (cu == null) {
                return null;
            }
            IType[] itts = cu.getTypes();
            if (itts.length == 0) {
                System.err.println("Strange. No types");
                return new Object[] {};
            }

            IType itt = itts[0];
            IField[] fields = itt.getFields();
            //            itt.getMethods()

            System.err.println("************    **************");
            for (int i = 0; i < fields.length; i++) {
                String sign = fields[i].getTypeSignature();
                System.err.println("FIELD: " + fields[i].getFlags() + " :: " + fields[i].getElementName() + " >> " + sign);
            }
            return fields;
        } catch (JavaModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
     *      int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
     *      int)
     */
    public String getColumnText(Object element, int columnIndex) {
        try {
            if (element instanceof IField) {
                IField iff = (IField) element;
                switch (columnIndex) {
                case 0:
                    StringBuffer sb = new StringBuffer(iff.getElementName());
                    //                if
                    // (Signature.getParameterCount(iff.getTypeSignature())>0) {
                    //                    sb.append(" (");
                    //                    String[] param =
                    // Signature.getParameterTypes(iff.getTypeSignature());
                    //                    for (int i = 0; i < param.length; i++) {
                    //                        sb.append(param[i]);
                    //                        if (i<param.length-1) {
                    //                            sb.append(", ");
                    //                        }
                    //                    }
                    //                    sb.append(")");
                    //        }
                    return sb.toString();
                case 1:
                    String type = Signature.getElementType(iff.getTypeSignature());
                    //                    	int arrayNest =
                    // Signature.getArrayCount(iff.getTypeSignature());
                    //                    	StringBuffer sbb = new StringBuffer(iff.()
                    // Signature.getTypeVariable(iff.()) +" ("+type+")");
                    //                    	for (int i = 0; i < arrayNest; i++) {
                    //                            sbb.append("[]");
                    //                        }
                    //                    	return sbb.toString();
                    return type;

                default:
                    break;
                }

            }
        } catch (JavaModelException e) {
            return "strange type";
        }
        return "aap";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
        // TODO Auto-generated method stub

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

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub

    }

}
