/*
 * Created on Mar 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

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

    private Label fileImageLabel;

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
        Composite binaryComposite = new Composite(currentComposite,SWT.NONE);
        binaryComposite.setLayout(new GridLayout(4,false));
        binaryComposite.setBackground(new Color(Display.getDefault(),255,255,255));
       fileImageLabel = new Label(binaryComposite, SWT.NONE);
        fileImageLabel.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,4,1));
        currentControl = binaryComposite;
//        if (myProperty.isDirIn()) {
            final Label mimeLabel = new Label(binaryComposite,SWT.NONE);
            mimeLabel.setText("Unknown mime.");
            mimeLabel.setBackground(new Color(Display.getDefault(),255,255,255));
            Button updateButton = new Button(binaryComposite,SWT.NONE);
            updateButton.setText("Load..");
            updateButton.setEnabled(myProperty.isDirIn());
            updateButton.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e) {
                    FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell());
                    fd.setText("Select a file for the binary");
                    String res = fd.open();
                    FileInputStream fis = null;
                    try {
                        fis= new FileInputStream(res);
                        Binary b = new Binary(fis);
                        myProperty.setValue(b);
                        setProperty(myProperty);
//                        String mime = b.getMimeType();
//                        setBinaryLabel(myProperty, fileImageLabel);
//                        mimeLabel.setText(mime);
//                        myParent.layout(true,true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        if (fis!=null) {
                            try {
                                fis.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }});
            final Binary bin;
            Object o = myProperty.getTypedValue();
            if (o!=null && (o instanceof Binary)) {
                bin = (Binary)o;
                mimeLabel.setText(bin.getMimeType());
            } else {
                bin = null;
            }
            Button saveButton = new Button(binaryComposite,SWT.NONE);
            saveButton.setText("Save..");
            saveButton.setEnabled(bin!=null);
            saveButton.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e) {
                    FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell());
                    fd.setText("Select a file for the binary");
                    String res = fd.open();
                    FileOutputStream fis = null;
                    try {
                        fis= new FileOutputStream(res);
                        fis.write(bin.getData());
                        fis.flush();
                         } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        if (fis!=null) {
                            try {
                                fis.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }});
            Button openButton = new Button(binaryComposite,SWT.NONE);
            openButton.setText("Open..");
            openButton.setEnabled(bin!=null);
            openButton.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e) {
                    try {
                        openBinary(bin);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }});

//        }

        setBinaryLabel(myProperty, fileImageLabel);
        //        toolkit.create
    }
    
    private void setBinaryLabel(Property p, Label l) {
        l.setText("");
        String value = p.getValue();
        if (value == null || "".equals(value)) {
            l.setText("[Empty binary property.]");
            return;
        }
        Object o = p.getTypedValue();
        if (o==null ) {
            l.setText("[Empty binary property.]");
            return;
        }
        if (!(o instanceof Binary)) {
            l.setText("[Binary: Bad data]");
            return;
        }
        Binary b = (Binary)o;
        ImageData id;
        try {
            id = new ImageData(new ByteArrayInputStream(b.getData()));
            Image i = new Image(Display.getCurrent(),id);
            l.setImage(i);
          } catch (RuntimeException e) {
              System.err.println("Not an image. No prob.");
           l.setText("?");
          }
        myParent.layout(true,true);
        l.redraw();
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

        } else {
            ttt.setSize(80,20);
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
    
    private void openBinary(Binary b) throws IOException {
        FileOutputStream fos = null;
        File f = null;
        String mime = b.getMimeType();
        String extension = "dat";
        if (mime!=null) {
            if (mime.indexOf('/')>=0) {
                StringTokenizer st = new StringTokenizer(mime,"/");
                String general = st.nextToken();
                extension = st.nextToken();
            }
        }
        try {
            f = File.createTempFile("tempeclipse", "."+extension);
            f.deleteOnExit();
            fos = new FileOutputStream(f);
            fos.write(b.getData());
            fos.flush();
        } finally {
            if (fos!=null) {
                fos.close();
            }
        }
        if (f==null) {
            return;
        }
        String fileName = f.getAbsolutePath();
        if ( System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0 ) {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
          }
          else {  // non-Windows platform, assume Linux/Unix
            String[] cmd = new String[2];

            if(fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".xls") ||
               fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".rtf") ){
              cmd[0] = "ooffice";
            } else if(fileName.toLowerCase().endsWith(".txt")){
              cmd = new String[4];    // resize

              cmd[0] = "xterm";
              cmd[1] = "-e";
              cmd[2] = "vi";
            } else if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".png")){
              cmd[0] = "display";
            } else if(fileName.toLowerCase().endsWith(".pdf")){
              cmd[0] = "xpdf";
            }else{ // we don't have a clue, try a plain web browser
              cmd[0] = "mozilla";
            }

            cmd[ (fileName.toLowerCase().endsWith(".txt")) ? 3 : 1] = fileName;

            if( cmd[0] != null ) {
              Process p = Runtime.getRuntime().exec(cmd);
            }
          }
        }
}
