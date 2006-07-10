/*
 * Created on Mar 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

import java.io.*;
import java.text.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;

import metadata.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.gface.date.*;

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

    private static SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    private static SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd-MM-yy");

    private static SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd/MM/yy");

    private static SimpleDateFormat inputFormat3 = new SimpleDateFormat("ddMMyy");

    private static SimpleDateFormat inputFormat4 = new SimpleDateFormat("ddMM");

    private static SimpleDateFormat inputFormat5 = new SimpleDateFormat("dd");

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
            return;
        }
        if (Property.BINARY_PROPERTY.equals(myProperty.getType())) {
            createBinaryProperty();
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
                    e1.printStackTrace();
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
            mimeLabel.setText(bin.getMimeType());
        } else {
            bin = null;
        }
        Button saveButton = new Button(binaryComposite, SWT.NONE);
        saveButton.setText("Save..");
        saveButton.setEnabled(bin != null);
        saveButton.addSelectionListener(new SelectionAdapter() {
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
                    // fis.write(bin.getData());
                    bin.write(fis);
                    fis.flush();
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        Button openButton = new Button(binaryComposite, SWT.NONE);
        openButton.setText("Open..");
        openButton.setEnabled(bin != null);
        openButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                try {
                    openBinary(bin);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // }
        Button clearButton = new Button(binaryComposite, SWT.NONE);
        clearButton.setText("Clear..");
        clearButton.setEnabled(bin != null);
        clearButton.addSelectionListener(new SelectionAdapter() {
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
                System.err.println("Not an image. No prob.");
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

    /**
     * 
     */
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
        ttt.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
        ttt.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub

            }

            public void focusLost(FocusEvent e) {
                String oldVal = myProperty.getValue();
                // System.err.println("OldVal: "+oldVal);
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
        final Text ttt = new Text(currentComposite, SWT.BORDER | SWT.SINGLE);
        // toolkit.adapt(ttt,true,true);
        currentControl = ttt;
        ttt.setSize(100, 20);
        ttt.setEnabled(myProperty.isDirIn());
        ttt.setText(value);
        final DatePickerCombo dp = new DatePickerCombo(currentComposite, SWT.BORDER | SWT.SINGLE);
        ttt.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub

            }

            public void focusLost(FocusEvent e) {
                String oldVal = myProperty.getValue();
                try {
                    Date d = getDate(ttt.getText());
                    ttt.setText(navajoDateFormat.format(d));
                    dp.setDate(d);
                    myProperty.setValue(navajoDateFormat.format(d));
                } catch (ParseException e1) {
                    e1.printStackTrace();
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

            public void dateSelected(DateSelectedEvent d) {
                String st = navajoDateFormat.format(d.date);
                myProperty.setValue(st);
                ttt.setText(st);
            }
        });
        currentControl = dp;
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
        // TODO Auto-generated method stub

    }

    /**
     * 
     */
    private void createIntegerProperty() {
        createOtherProperty(false);

    }

    private void createClocktimeProperty() {
        // Not used (yet)
        HourSelectionCombo hsc = new HourSelectionCombo(currentComposite, SWT.BORDER);
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
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
    }

    private void createBooleanProperty() {
        final Button b = new Button(currentComposite, SWT.CHECK);
        String value = myProperty.getValue();
        b.setEnabled(myProperty.isDirIn());
        b.setSelection("true".equals(value));
        b.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                myProperty.setAnyValue(new Boolean(b.getSelection()));
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 
     */
    private void createSelectionProperty() {
        final Combo ttt = new Combo(currentComposite, SWT.DROP_DOWN);
        currentControl = ttt;
        // toolkit.adapt(ttt,true,true);
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
                    ttt.select(i + 1);
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
                        element.setSelected(index - 1 == i);
                        // ttt.add(element.getName());
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

    private void createMultiSelectionProperty() {
        final Composite comp = new Composite(currentComposite, SWT.BORDER);
        // comp.setLayout(new FillLayout(SWT.VERTICAL));
        comp.setLayout(new RowLayout());
        ArrayList sel;
        try {
            sel = myProperty.getAllSelections();
            for (Iterator iter = sel.iterator(); iter.hasNext();) {
                final Selection element = (Selection) iter.next();
                final Button b = new Button(comp, SWT.CHECK);
                b.setBackground(new Color(Display.getDefault(), 255, 255, 255));

                b.setEnabled(myProperty.isDirIn());
                b.setText(element.getName());
                b.setToolTipText(element.getValue());
                b.addSelectionListener(new SelectionListener() {
                    public void widgetSelected(SelectionEvent e) {
                        element.setSelected(b.getSelection());
                    }

                    public void widgetDefaultSelected(SelectionEvent e) {
                    }
                });
            }
            currentControl = comp;
        } catch (NavajoException e1) {
            e1.printStackTrace();
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
        if (mime == null || "".equals(mime)) {
            mime = b.guessContentType();
        }
        System.err.println("Mime: " + mime);
        FormatDescription fd = b.getFormatDescription();
        String extension = "dat";
        if (fd!=null) {
            List l = fd.getFileExtensions();
            System.err.println("All extensions: "+l);
            if (l!=null && !l.isEmpty()) {
                extension = (String)l.get(0);
                System.err.println("Extension: "+extension);
            }
        }
        if (mime != null) {
            if (mime.indexOf('/') >= 0) {
                StringTokenizer st = new StringTokenizer(mime, "/");
                String general = st.nextToken();
                extension = st.nextToken();
            }
        }
        try {
            f = File.createTempFile("tempeclipse", "." + extension);
            f.deleteOnExit();
            fos = new FileOutputStream(f);
            b.write(fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        if (f == null) {
            return;
        }
        String fileName = f.getAbsolutePath();
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
        } else { // non-Windows platform, assume Linux/Unix
            String[] cmd = new String[2];

            if (fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".ppt")
                    || fileName.toLowerCase().endsWith(".rtf")) {
                cmd[0] = "ooffice";
            } else if (fileName.toLowerCase().endsWith(".txt")) {
                cmd = new String[4]; // resize

                cmd[0] = "xterm";
                cmd[1] = "-e";
                cmd[2] = "vi";
            } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".png")) {
                cmd[0] = "display";
            } else if (fileName.toLowerCase().endsWith(".pdf")) {
                cmd[0] = "xpdf";
            } else { // we don't have a clue, try a plain web browser
                cmd[0] = "mozilla";
            }

            cmd[(fileName.toLowerCase().endsWith(".txt")) ? 3 : 1] = fileName;

            if (cmd[0] != null) {
                Process p = Runtime.getRuntime().exec(cmd);
            }
        }
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
