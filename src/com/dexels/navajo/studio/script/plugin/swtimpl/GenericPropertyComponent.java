/*
 * Created on Mar 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.swtimpl;

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;

import com.dexels.navajo.document.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GenericPropertyComponent {

    /**
     *  
     */
    private Property myProperty = null;

    private final Composite myParent;

    private Composite currentComposite = null;

    private Control currentControl = null;

    //    private final Form myForm ;
    //    private final FormToolkit toolkit ;
    private Label myLabel;

    private boolean labelShown;

    public GenericPropertyComponent(Composite parent) {
        super();
        myParent = parent;
        //        this.myForm = myForm;
        //         currentComposite = toolkit.createComposite(parent);
        currentComposite = new Composite(parent, SWT.BORDER);
        currentComposite.setLayout(new GridLayout(2, false));
        //        myLabel = toolkit.createLabel(currentComposite, "-");
//        myLabel = new Label(currentComposite, SWT.LEFT);
        //        myLabel.setFont(myLabel.getFont().)
    }

    /**
     * @return Returns the currentComposite.
     */
    public Composite getComposite() {
        return currentComposite;
    }

    /**
     * @return Returns the myComposite.
     */
    public Composite getParent() {
        return myParent;
    }

    public void showLabels(boolean b) {
        labelShown = b;
    }
    
    /**
     * @return Returns the myProperty.
     */
    public Property getProperty() {
        return myProperty;
    }

    /**
     * @param myProperty
     *            The myProperty to set.
     */
    public void setProperty(Property myProperty) {
        this.myProperty = myProperty;
        if (currentComposite != null) {
            currentComposite.dispose();
            currentComposite = new Composite(myParent, SWT.NONE);
            currentComposite.setLayout(new GridLayout(2, false));
            //            myLabel = toolkit.createLabel(currentComposite, "-");
            if (labelShown) {
                myLabel = new Label(currentComposite, SWT.LEFT);
            }

            //            currentComposite = toolkit.createComposite(myParent);
            //            currentComposite.setLayout(new GridLayout(2,false));
            //           myLabel = toolkit.createLabel(currentComposite, "-");
            //   		label1.setText("Label");

        }
        if (labelShown) {
	        if (myProperty.getDescription() != null && !myProperty.getDescription().equals("")) {
	            myLabel.setText(myProperty.getName() + " (" + myProperty.getDescription() + ")");
	
	        } else {
	            myLabel.setText(myProperty.getName());
	        }
        }
        if (Property.SELECTION_PROPERTY.equals(myProperty.getType())) {
            createSelectionProperty();
            return;
        }
        if (Property.INTEGER_PROPERTY.equals(myProperty.getType())) {
            createIntegerProperty();
            return;
        }
        if (Property.FLOAT_PROPERTY.equals(myProperty.getType())) {
            createFloatProperty();
            return;
        }
        if (Property.DATE_PROPERTY.equals(myProperty.getType())) {
            createDateProperty();
            return;
        }
        createOtherProperty();
        if (currentControl != null) {
            //            GridData gf = new GridData(GridData.FILL_BOTH);
            //            gf.grabExcessVerticalSpace = true;
            GridData gd = new GridData();
            gd.grabExcessHorizontalSpace = true;
            gd.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
            gd.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
            currentControl.setLayoutData(gd);
        }
//        currentComposite.layout();
    }

    /**
     *  
     */
    private void createOtherProperty() {
        String value = myProperty.getValue();
        if (value == null) {
            value = "";
        }
        final Text ttt = new Text(currentComposite, SWT.BORDER | SWT.SINGLE);
        //        toolkit.adapt(ttt,true,true);
        currentControl = ttt;
        if (myProperty.getLength() > 0) {
            ttt.setTextLimit(myProperty.getLength());
        }
        ttt.setText(value);
        ttt.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub

            }

            public void focusLost(FocusEvent e) {
                myProperty.setValue(ttt.getText());

            }
        });
        //        toolkit.create
    }

    /**
     *  
     */
    private void createDateProperty() {
        Object val = myProperty.getTypedValue();
        if (!(val instanceof Date)) {
            System.err.println("Not a date???");
        }
        createOtherProperty();
    }

    /**
     *  
     */
    private void createFloatProperty() {
        createOtherProperty();
        // TODO Auto-generated method stub

    }

    /**
     *  
     */
    private void createIntegerProperty() {
        createOtherProperty();

    }

    /**
     *  
     */
    private void createSelectionProperty() {
        final Combo ttt = new Combo(currentComposite, SWT.DROP_DOWN);
        currentControl = ttt;
        //        toolkit.adapt(ttt,true,true);
        try {
            final ArrayList al = myProperty.getAllSelections();
            for (Iterator iter = al.iterator(); iter.hasNext();) {
                Selection element = (Selection) iter.next();
                ttt.add(element.getName());
            }
            if (al.size() > 0) {
                ttt.select(0);
            }
            ttt.addSelectionListener(new SelectionListener() {

                public void widgetSelected(SelectionEvent e) {
                    for (int i = 0; i < al.size(); i++) {
                        Selection element = (Selection) al.get(i);
                        int index = ttt.getSelectionIndex();
                        element.setSelected(index == i);
//                        ttt.add(element.getName());
                    }

                }

                public void widgetDefaultSelected(SelectionEvent e) {
                    // TODO Auto-generated method stub

                }
            });
        } catch (NavajoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param kit
     */
    public void adapt(FormToolkit kit) {
        if (getComposite() != null) {
            kit.adapt(getComposite());
        }

        if (myLabel != null && labelShown) {
            kit.adapt(myLabel, false, false);
        }
        if (currentControl != null) {
            kit.adapt(currentControl, true, true);
        }
    }
}
