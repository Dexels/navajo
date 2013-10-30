/*
 * Created on Mar 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.BinaryOpenerFactory;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.gface.date.DatePickerCombo;
import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;
import com.gface.date.HourSelectionCombo;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GenericPropertyComponent {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GenericPropertyComponent.class);
    private static SimpleDateFormat navajoDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Property myProperty = null;

    private final Composite myParent;

    private Composite currentComposite = null;

    private Control currentControl = null;

    private ScrolledForm myForm;

    // private final FormToolkit toolkit ;
    private Label myLabel;

    private boolean labelShown;

    private Label fileImageLabel;

    public GenericPropertyComponent(Composite parent, ScrolledForm form) {
        this(parent);
        this.myForm = form;
    }

    public GenericPropertyComponent(Composite parent) {
        super();
        myParent = parent;
        currentComposite = new Composite(parent, SWT.BORDER);
        currentComposite.setLayout(new GridLayout(2, false));
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
            // myLabel = toolkit.createLabel(currentComposite, "-");
            if (labelShown) {
                myLabel = new Label(currentComposite, SWT.LEFT);
                myLabel.setToolTipText(myProperty.getDescription());
            }

            // currentComposite = toolkit.createComposite(myParent);
            // currentComposite.setLayout(new GridLayout(2,false));
            // myLabel = toolkit.createLabel(currentComposite, "-");
            // label1.setText("Label");

        }
        if (labelShown) {
            if (myProperty.getDescription() != null && !myProperty.getDescription().equals("")) {
                myLabel.setText(myProperty.getName() + " (" + myProperty.getDescription() + ")");

            } else {
                myLabel.setText(myProperty.getName());
            }
        }
        if (Property.SELECTION_PROPERTY.equals(myProperty.getType())) {
            if ("+".equals(myProperty.getCardinality())) {
                createMultiSelectionProperty();
            } else {
                createSelectionProperty();
            }
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
        if (Property.CLOCKTIME_PROPERTY.equals(myProperty.getType())) {
            createOtherProperty(false);
//            createClocktimeProperty();
            return;
        }
        if (Property.BINARY_PROPERTY.equals(myProperty.getType())) {
            createBinaryProperty();
            return;
        }
        if (Property.EXPRESSION_PROPERTY.equals(myProperty.getType())) {
            createExpressionProperty();
            return;
        }
        
        createOtherProperty(false);
        if (currentControl != null) {
            // GridData gf = new GridData(GridData.FILL_BOTH);
            // gf.grabExcessVerticalSpace = true;
            GridData gd = new GridData();
            gd.minimumWidth = 200;
            gd.grabExcessHorizontalSpace = true;
            gd.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
            gd.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
            currentControl.setLayoutData(gd);
        }
        // currentComposite.layout();
    }

    private void createBinaryProperty() {
        // String value = myProperty.getValue();
        Composite binaryComposite = new Composite(currentComposite, SWT.NONE);
        binaryComposite.setLayout(new GridLayout(5, false));
        binaryComposite.setBackground(new Color(Display.getDefault(), 255, 255, 255));
        fileImageLabel = new Label(binaryComposite, SWT.NONE);
        fileImageLabel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1));
        currentControl = binaryComposite;
        // if (myProperty.isDirIn()) {
        final Label mimeLabel = new Label(binaryComposite, SWT.NONE);
        mimeLabel.setText("Unknown mime.");
        mimeLabel.setBackground(new Color(Display.getDefault(), 255, 255, 255));
        Button updateButton = new Button(binaryComposite, SWT.NONE);
        updateButton.setText("Load..");
        updateButton.setEnabled(myProperty.isDirIn());
        updateButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell());
                fd.setText("Select a file for the binary");
                String res = fd.open();
                // FileInputStream fis = null;
                try {
                    if (res == null) {
                        return;
                    }
                    // fis= new FileInputStream(res);
                    Binary b = new Binary(new File(res));
                    myProperty.setValue(b);
                    setProperty(myProperty);
                    // String mime = b.getMimeType();
                    // setBinaryLabel(myProperty, fileImageLabel);
                    // mimeLabel.setText(mime);
                    // myParent.layout(true,true);
                    setBinaryLabel(b, fileImageLabel);

                } catch (Exception e1) {
                	logger.error("Error: ", e1);
                	// } finally {
                    // if (fis!=null) {
                    // try {
                    // fis.close();
                    // } catch (IOException e1) {
                    // e1.printStackTrace();
                    // }
                    // }
                }
            }
        });
        final Binary bin;
        Object o = myProperty.getTypedValue();
        if (o != null && (o instanceof Binary)) {
            bin = (Binary) o;
            mimeLabel.setText(bin.getMimeType()+" ("+bin.getLength()+" bytes)");
        } else {
            bin = null;
        }
        Button saveButton = new Button(binaryComposite, SWT.NONE);
        saveButton.setText("Save..");
        saveButton.setEnabled(bin != null);
        saveButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell());
                fd.setText("Select a file for the binary");
                String res = fd.open();
                FileOutputStream fis = null;
                try {
                    if (res == null) {
                        return;
                    }
                    fis = new FileOutputStream(res);
                    if(bin!=null) {
                        bin.write(fis);
                    	
                    }
                    fis.flush();
                } catch (Exception e1) {
                	logger.error("Error: ", e1);
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e1) {
                        	logger.error("Error: ", e1);
                        }
                    }
                }
            }
        });
        Button openButton = new Button(binaryComposite, SWT.NONE);
        openButton.setText("Open..");
        openButton.setEnabled(bin != null);
        openButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
               openBinary(bin);
            }
        });

        // }
        Button clearButton = new Button(binaryComposite, SWT.NONE);
        clearButton.setText("Clear..");
        clearButton.setEnabled(bin != null);
        clearButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                myProperty.setValue(new Binary());
                setBinaryLabel(null, fileImageLabel);
            }
        });

        // }

        setBinaryLabel(bin, fileImageLabel);
        // toolkit.create
    }

    private void setBinaryLabel(Binary b, Label l) {
        l.setText("");
        if (b == null) {
            l.setText("[Empty binary property.]");
            l.setImage(null);
            return;
        } else {
            ImageData id;
            try {
                id = new ImageData(b.getDataAsStream());
                Image i = new Image(Display.getCurrent(), id);
                l.setImage(i);
            } catch (RuntimeException e) {
                logger.info("Not an image. No prob.");
                l.setText("?");
            }
        }
        l.pack();
        l.redraw();
        myParent.layout(true, true);
        if (myForm != null) {
            myForm.reflow(true);
        }
    }



        
    private void createExpressionProperty() {
        String value = ""+myProperty.getTypedValue();
        final Text ttt = new Text(currentComposite, SWT.BORDER | SWT.SINGLE);
        // toolkit.adapt(ttt,true,true);
        currentControl = ttt;
//        if (myProperty.getLength() > 0 && useLength) {
//            ttt.setTextLimit(myProperty.getLength());
//            if (value.length() > myProperty.getLength()) {
//                value = value.substring(0, myProperty.getLength());
//            }
//
//        }
        ttt.setSize(100, 20);
        ttt.setEnabled(false);
        ttt.setText(value);
        ttt.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
        // toolkit.create
    }
    
    private void createOtherProperty(boolean useLength) {
        String value = myProperty.getValue();
        if (value == null) {
            value = "";
        }
        final Text ttt = new Text(currentComposite, SWT.BORDER | SWT.SINGLE);
        // toolkit.adapt(ttt,true,true);
        currentControl = ttt;
        if (myProperty.getLength() > 0 && useLength) {
            ttt.setTextLimit(myProperty.getLength());
            if (value.length() > myProperty.getLength()) {
                value = value.substring(0, myProperty.getLength());
            }

        }
        ttt.setSize(100, 20);
        ttt.setEnabled(myProperty.isDirIn());
        ttt.setText(value);
        ttt.setToolTipText(myProperty.getDescription());

        ttt.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
        ttt.addFocusListener(new FocusListener() {

            @Override
			public void focusGained(FocusEvent e) {
            }

            @Override
			public void focusLost(FocusEvent e) {
                String oldVal = myProperty.getValue();
                // logger.info("OldVal: "+oldVal);
                myProperty.setValue(ttt.getText());
                if (myProperty.getValue() == null) {
                    ttt.setText(oldVal);
                    myProperty.setValue(oldVal);
                } else {
                    ttt.setText(myProperty.getValue());
                }
            }
        });
        // toolkit.create
    }

    /**
     * 
     */
    private void createDateProperty() {

        String value = myProperty.getValue();
        if (value == null) {
            value = "";
        }
        final Composite subComponent =  new Composite(currentComposite,SWT.NONE);
        subComponent.setBackground(new Color(Display.getDefault(), 255, 255, 255));
        subComponent.setLayout(new GridLayout(2,true));
        subComponent.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
        
        final Text ttt = new Text(subComponent, SWT.BORDER | SWT.SINGLE);
        // toolkit.adapt(ttt,true,true);
        currentControl = ttt;
        ttt.setSize(100, 20);
        ttt.setEnabled(myProperty.isDirIn());
        ttt.setText(value);
        ttt.setToolTipText(myProperty.getDescription());

        final DatePickerCombo dp = new DatePickerCombo(subComponent, SWT.BORDER | SWT.SINGLE);
        ttt.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
        dp.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
        
        ttt.addFocusListener(new FocusListener() {
            @Override
			public void focusGained(FocusEvent e) {
            }

            @Override
			public void focusLost(FocusEvent e) {
                String oldVal = myProperty.getValue();
                try {
                    Date d = getDate(ttt.getText());
                    ttt.setText(navajoDateFormat.format(d));
                    dp.setDate(d);
                    myProperty.setValue(navajoDateFormat.format(d));
                } catch (ParseException e1) {
                	logger.error("Error: ", e1);
                }
                if (myProperty.getValue() == null) {
                    ttt.setText(oldVal);
                    myProperty.setValue(oldVal);
                } else {
                    // ttt.setText(myProperty.getValue());
                }
            }
        });
        dp.addDateSelectionListener(new DateSelectionListener() {

            @Override
			public void dateSelected(DateSelectedEvent d) {
                String st = navajoDateFormat.format(d.date);
                myProperty.setValue(st);
                ttt.setText(st);
            }
        });
        currentControl = null;
        dp.setSize(150, 20);
        dp.setEnabled(myProperty.isDirIn());
        dp.setDate((Date) myProperty.getTypedValue());
        dp.setSize(150, 20);
    }

    /**
     * 
     */
    private void createFloatProperty() {
        createOtherProperty(false);

    }

    /**
     * 
     */
    private void createIntegerProperty() {
        createOtherProperty(false);

    }

    // Disabled?
    @SuppressWarnings("unused")
	private void createClocktimeProperty() {
        // Not used (yet)
        final HourSelectionCombo hsc = new HourSelectionCombo(currentComposite, SWT.BORDER);
        hsc.setMinuteInterval(1);
        if (!(myProperty.getTypedValue() instanceof ClockTime)) {
            return;
        }
        ClockTime ct = (ClockTime) myProperty.getTypedValue();
        if (ct == null) {
            return;
        }
        Date dd = ct.dateValue();
        if (dd == null) {
            return;
        }
        hsc.setTime(dd);
        logger.info("Adding listener to component: "+hsc.hashCode());
        hsc.addDateSelectionListener(new DateSelectionListener(){

			@Override
			public void dateSelected(DateSelectedEvent d) {
				logger.info("Source: "+d.getSource());
				logger.info("Date selected: "+d);
				logger.info("Component hash: "+hsc.hashCode());
				myProperty.setValue(new ClockTime(d.date));
			}});
        hsc.setToolTipText(myProperty.getDescription());
    }

    private void createBooleanProperty() {
        final Button b = new Button(currentComposite, SWT.CHECK);
        String value = myProperty.getValue();
        b.setEnabled(myProperty.isDirIn());
        b.setSelection("true".equals(value));
        b.setToolTipText(myProperty.getDescription());

        b.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                myProperty.setAnyValue(new Boolean(b.getSelection()));
            }

            @Override
			public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
    }

    /**
     * 
     */
    private void createSelectionProperty() {
        final Combo ttt = new Combo(currentComposite, SWT.DROP_DOWN);
        currentControl = ttt;
        ttt.setToolTipText(myProperty.getDescription());

        // toolkit.adapt(ttt,true,true);
        try {
            final ArrayList<Selection> al = myProperty.getAllSelections();
            boolean hasDummy = false;
            for (Iterator<Selection> iter = al.iterator(); iter.hasNext();) {
            	Selection element =  iter.next();
            	if (Selection.DUMMY_ELEMENT.equals(element.getValue())) {
					hasDummy = true;
				}
            	ttt.add(element.getName());
            }
            if (!hasDummy) {
                ttt.add("-");
			}
//            for (Iterator iter = al.iterator(); iter.hasNext();) {
//                Selection element = (Selection) iter.next();
//                ttt.add(element.getName());
//            }
            boolean hasSel = false;
            for (int i = 0; i < al.size(); i++) {
                Selection element = al.get(i);
                if (element.isSelected()) {
                    ttt.select(i );
                    hasSel = true;
                }

            }
            if (!hasSel) {
                ttt.select(0);
            }
            ttt.setEnabled(myProperty.isDirIn());
            ttt.addSelectionListener(new SelectionListener() {
                @Override
				public void widgetSelected(SelectionEvent e) {

                    for (int i = 0; i < al.size(); i++) {
                        Selection element = al.get(i);
                        int index = ttt.getSelectionIndex();
                        element.setSelected(index  == i);
                        // ttt.add(element.getName());
                        logger.info("Index: "+i+" selected: "+index);
                    }

                }

                @Override
				public void widgetDefaultSelected(SelectionEvent e) {

                }
            });
        } catch (NavajoException e) {
            logger.error("Error: ",e);
        }

    }

    private void createMultiSelectionProperty() {
        final Composite comp = new Composite(currentComposite, SWT.BORDER);
        // comp.setLayout(new FillLayout(SWT.VERTICAL));
        comp.setLayout(new RowLayout());
        ArrayList<Selection> sel;
        try {
            sel = myProperty.getAllSelections();
            for (Iterator<Selection> iter = sel.iterator(); iter.hasNext();) {
                final Selection element = iter.next();
                final Button b = new Button(comp, SWT.CHECK);
                b.setBackground(new Color(Display.getDefault(), 255, 255, 255));
                b.setToolTipText(myProperty.getDescription());
                b.setSelection(element.isSelected());
                b.setEnabled(myProperty.isDirIn());
                b.setText(element.getName());
                b.setToolTipText(element.getValue());
                b.addSelectionListener(new SelectionListener() {
                    @Override
					public void widgetSelected(SelectionEvent e) {
                        element.setSelected(b.getSelection());
                    }

                    @Override
					public void widgetDefaultSelected(SelectionEvent e) {
                    }
                });
            }
            currentControl = comp;
        } catch (NavajoException e1) {
        	logger.error("Error: ", e1);
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

    public static void openBinary(Binary b)  {
    	
    	BinaryOpenerFactory.getInstance().open(b);
    	
    }

    public final Date getDate(String text) throws ParseException {
        if (text == null || text.equals("")) {
            return null;
        }

        // see if there is only one separator provided; if so, add the current
        // year to string
        if (text.indexOf('-') >= 0 && text.indexOf('-') == text.lastIndexOf('-')) {
            text = text + "-" + Calendar.getInstance().get(Calendar.YEAR);
        }
        if (text.indexOf('/') >= 0 && text.indexOf('/') == text.lastIndexOf('/')) {
            text = text + "/" + Calendar.getInstance().get(Calendar.YEAR);

            // Try different parsers:
        }

        return navajoDateFormat.parse(text);
        // try {
        // return inputFormat1.parse(text);
        // }
        // catch (ParseException pe1) {
        // try {
        // return inputFormat2.parse(text);
        // }
        // catch (ParseException pe2) {
        // try {
        // return inputFormat3.parse(text);
        // }
        // catch (ParseException pe6) {
        // try {
        // return displayDateFormat.parse(text);
        // }
        // catch (ParseException pe4) {
        // try {
        // // There is a bug in SimpleDateFormat causing the date to reset to
        // 01-01-1970
        // // So we make sure the right month and year are set again.
        // Date wrong1 = inputFormat4.parse(text);
        // Calendar c = Calendar.getInstance();
        // c.setTime(wrong1);
        // c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        // return c.getTime();
        // }
        // catch (ParseException pe5) {
        // try {
        // // There is a bug in SimpleDateFormat causing the date to reset to
        // 01-01-1970
        // // So we make sure the right month and year are set again.
        // Date wrong2 = inputFormat5.parse(text);
        // Calendar x = Calendar.getInstance();
        // x.setTime(wrong2);
        // x.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        // x.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        // return x.getTime();
        // }
        // catch (ParseException pe3) {
        // return displayDateFormat.parse(text);
        // // If this one fails, data entry person should get an other job
        // (person is too creative!);
        // }
        // }
        // }
        // }
        // }
        // }
    }
}
