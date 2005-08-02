/*
 * Created on Mar 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.swtimpl;

import java.io.*;
import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;

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
        if (Property.BOOLEAN_PROPERTY.equals(myProperty.getType())) {
            createBooleanProperty();
            return;
        }
        if (Property.BINARY_PROPERTY.equals(myProperty.getType())) {
            System.err.println("Creating a binary property");
            createBinaryProperty();
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

    private void createBinaryProperty() {
        String value = myProperty.getValue();
        final Label ttt = new Label(currentComposite, SWT.NONE);
        if (value == null || "".equals(value)) {
            ttt.setText("[Empty binary property.]");
            return;
        }
        Object o = myProperty.getTypedValue();
        if (o==null ) {
            ttt.setText("[Empty binary property.]");
            return;
        }
        if (!(o instanceof Binary)) {
            ttt.setText("[Binary: Bad data]");
            return;
        }
        Binary b = (Binary)o;
        ImageData id = new ImageData(new ByteArrayInputStream(b.getData()));
        Image i = new Image(Display.getCurrent(),id);
        //        toolkit.adapt(ttt,true,true);
        currentControl = ttt;
        ttt.setImage(i);
         //        toolkit.create
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
            if (value.length()> myProperty.getLength()) {
                value = value.substring(0, myProperty.getLength());
            }

        }
        ttt.setEnabled(myProperty.isDirIn());
        ttt.setText(value);
        ttt.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub

            }

            public void focusLost(FocusEvent e) {
                String oldVal = myProperty.getValue();
                System.err.println("OldVal: "+oldVal);
                myProperty.setValue(ttt.getText());
                if (myProperty.getValue()==null) {
                    ttt.setText(oldVal);
                    myProperty.setValue(oldVal);
                } else {
                    ttt.setText(myProperty.getValue());
                }
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

    private void createBooleanProperty() {
        final Button b = new Button(currentComposite,SWT.CHECK);
        String value = myProperty.getValue();
        b.setEnabled(myProperty.isDirIn());
        b.setSelection("true".equals(value));
        b.addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                myProperty.setAnyValue(new Boolean(b.getSelection()));
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub
                
            }});
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
            ttt.add("-");
            for (Iterator iter = al.iterator(); iter.hasNext();) {
                Selection element = (Selection) iter.next();
                ttt.add(element.getName());
            }
            boolean hasSel = false;
           for (int i = 0; i < al.size(); i++) {
                Selection element = (Selection) al.get(i);
                if (element.isSelected()) {
                    ttt.select(i+1);
                    hasSel = true;
                }

           }
           if (!hasSel) {
               ttt.select(0);
           }
           ttt.setEnabled(myProperty.isDirIn());
             ttt.addSelectionListener(new SelectionListener() {
                public void widgetSelected(SelectionEvent e) {
                    
                    for (int i = 0; i < al.size(); i++) {
                        Selection element = (Selection) al.get(i);
                        int index = ttt.getSelectionIndex();
                        element.setSelected(index-1 == i);
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
