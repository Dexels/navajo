/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package com.dexels.navajo.studio.script.plugin.swtimpl;
import java.util.*;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.dexels.navajo.document.*;

/**
 * Sorter for the TableViewerExample that displays items of type 
 * <code>ExampleTask</code>.
 * The sorter supports three sort criteria:
 * <p>
 * <code>DESCRIPTION</code>: Task description (String)
 * </p>
 * <p>
 * <code>OWNER</code>: Task Owner (String)
 * </p>
 * <p>
 * <code>PERCENT_COMPLETE</code>: Task percent completed (int).
 * </p>
 */
public class PropertySorter extends ViewerSorter {

	/**
	 * Constructor argument values that indicate to sort items by 
	 * description, owner or percent complete.
	 */

    private String myPropertyName;
    
	public PropertySorter() {
	   super();
	}

	/* (non-Javadoc)
	 * Method declared on ViewerSorter.
	 */

	public void setPropertyName(String propertyName) {
		   myPropertyName = propertyName;
	}
	
	public int compare(Viewer viewer, Object o1, Object o2) {
//		Property task1 = (Property) o1;
//		Property task2 = (Property) o2;

	    Message m1 = (Message)o1;
	    Message m2 = (Message)o2;
	    if (myPropertyName==null) {
            return 0;
        }
	    Property p1 = m1.getProperty(myPropertyName);
	    Property p2 = m2.getProperty(myPropertyName);
	    int res = p1.compareTo(p2);
	    System.err.println("Comparing: "+p1.getValue()+" and "+p2.getValue()+" result: "+res);
	    return res;
	    //	    return super.compare(viewer, o1, o2);
	}

	public boolean isSorterProperty(Object element, String property) {
		return true;
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerSorter#sort(org.eclipse.jface.viewers.Viewer, java.lang.Object[])
     */
//    public void sort(Viewer viewer, Object[] elements) {
//        // TODO Auto-generated method stub
//        super.sort(viewer, elements);
//    }
    
//    public void sort(final Viewer viewer, Object[] elements) {
//    	Arrays.sort(elements, new Comparator() {
//    		public int compare(Object a, Object b) {
//    			return ViewerSorter.this.compare(viewer, a, b);
//    		}
//    	});
//    }
}
