package com.dexels.navajo.studio.script.plugin.navajobrowser.preferences;

import java.util.*;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.activities.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.*;
import org.eclipse.jface.viewers.*;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage </samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class NavajoPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    //	public static final String P_NAVAJO_SERVERURL =
    // "navajoServerUrlPreference";
    //	public static final String P_NAVAJO_USERNAME =
    // "navajoUsernamePreference";
    //	public static final String P_NAVAJO_PASSWORD =
    // "navajoPasswordPreference";

    private Text serverField;
    private Text usernameField;
    private Text passwordField;
    private ComboViewer protocolSelector;
    private Text nameField;
    private Button saveButton;
    private Button deleteButton;
    private Button insertButton;
    private ComboViewer selector;
    private Button testButton;
    private Composite myParent;

    public final static String[] protocols = new String[]{"http","socket","local"};
    private ScrolledForm myForm;
    //
    public NavajoPreferencePage() {
        super("Navajo preferences");
        setPreferenceStore(NavajoScriptPluginPlugin.getDefault().getPreferenceStore());
    }

    /**
     * Sets the default values of the preferences.
     */

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.IPreferencePage#performOk()
     */
    public boolean performOk() {
        IViewPart iv = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().findView("com.dexels.TmlBrowser");
        if (iv!=null) {
            iv.dispose();
        } else {
            System.err.println("View not found!!");
        }
        return super.performOk();
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    
    
//    public void createFieldEditors() {
//          addField(new PathEditor(NavajoScriptPluginPlugin.P_NAVAJO_PATH, "&Path preference:", "Choose", getFieldEditorParent()));
//        Button aap = new Button(getFieldEditorParent(),SWT.PUSH);
//        aap.setText("BRAA");
//        aap.addSelectionListener(new SelectionListener(){
//
//            public void widgetSelected(SelectionEvent e) {
//                double d = Math.random();
//                NavajoScriptPluginPlugin.getDefault().addServerEntry("Apekool"+d, "http", "APENSERVER", "AAP", "Konijn");
//            }
//
//            public void widgetDefaultSelected(SelectionEvent e) {
//            }});

//        createServerEntryComposite(getFieldEditorParent());
        //		addField(
        //				new StringFieldEditor(P_NAVAJO_SERVERURL, "Server url:",
        // getFieldEditorParent()));
        //		addField(
        //				new StringFieldEditor(P_NAVAJO_USERNAME, "Username:",
        // getFieldEditorParent()));
        //		addField(
        //				new StringFieldEditor(P_NAVAJO_PASSWORD, "Password:",
        // getFieldEditorParent()));

//    }

    public void init(IWorkbench workbench) {
    }

    public Control createContents(Composite parent) {
        FormToolkit kit;
        myParent = parent;
//        
//        Button aaaaap = new Button(parent,SWT.PUSH);
//        aaaaap.setText("BRAAAAAAAAAAAA");
 
        kit = new FormToolkit(parent.getDisplay());
        kit.setBackground(new Color(Display.getCurrent(),220,220,240));
        myForm = new ScrolledForm(parent, SWT.V_SCROLL | SWT.H_SCROLL);
        myForm.setExpandHorizontal(true);
        myForm.setExpandVertical(true);
        
        kit = new FormToolkit(parent.getDisplay());
//        Form fff = kit.createForm(parent);
//                System.err.println("PARENT LAYOUT: "+parent.getLayout().getClass());
//        parent.setLayout(new FillLayout());
        myForm.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true));
                //        myForm = new ScrolledForm(parent, SWT.V_SCROLL | SWT.H_SCROLL);
//        myForm.setExpandHorizontal(true);
//        myForm.setExpandVertical(true);
        myForm.getBody().setLayout(new FillLayout());
//        myForm.getBody().setLayout(new TableWrapLayout());

        Section serverEntrySection = kit.createSection(myForm.getBody(), Section.TITLE_BAR | Section.TWISTIE);
        serverEntrySection.setExpanded(true);
        Composite client = kit.createComposite(serverEntrySection);
//        client.setBackground(new Color(Display.getCurrent(),220,220,240));
        serverEntrySection.setClient(client);
//        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.BOTTOM);

        serverEntrySection.setText("Navajo browser entries:");
        TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 2;
        client.setLayout(layout);
        kit.createLabel(client, "Server entry:         ");
//        if (true) {
//            return myForm;
//        }
        selector = new ComboViewer(client);
//        selector.getCombo().setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        setupSelectorBox();
        selector.getCombo().addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                int index = selector.getCombo().getSelectionIndex();
                ServerEntry se = (ServerEntry)NavajoScriptPluginPlugin.getDefault().getServerEntries().get(index);
                nameField.setText(se.getName());
                serverField.setText(se.getServer());
                usernameField.setText(se.getUsername());
                passwordField.setText(se.getPassword());
                setProtocol(se.getProtocol());
            }

 
            public void widgetDefaultSelected(SelectionEvent e) {
            }});
        selector.getCombo().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
              
        kit.createLabel(client, "Name:");
        nameField = kit.createText(client, "",SWT.BORDER);
        kit.createLabel(client, "Protocol:");
        protocolSelector = new ComboViewer(client);
        protocolSelector.add(protocols);
        kit.createLabel(client, "Server:");
        serverField = kit.createText(client, "",SWT.BORDER);
        kit.createLabel(client, "Username:");
        usernameField = kit.createText(client, "",SWT.BORDER);
        kit.createLabel(client, "Password:");
        passwordField = kit.createText(client, "",SWT.BORDER| SWT.PASSWORD);
        passwordField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB));
        
        nameField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        protocolSelector.getCombo().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        serverField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        usernameField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        passwordField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        
//        if (true) {
//            return myForm.getBody();
//        }
        
        Composite buttonBar = kit.createComposite(client);
        buttonBar.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,2));
        buttonBar.setLayout(new RowLayout());
        saveButton = kit.createButton(buttonBar, "Save", SWT.PUSH);
        deleteButton = kit.createButton(buttonBar, "Delete", SWT.PUSH);
        insertButton = kit.createButton(buttonBar, "Insert", SWT.PUSH);
        testButton = kit.createButton(buttonBar, "Test connection", SWT.PUSH);
        saveButton.addSelectionListener(new SelectionListener(){
           public void widgetSelected(SelectionEvent e) {
               NavajoScriptPluginPlugin.getDefault().updateServerEntry(selector.getCombo().getSelectionIndex(),nameField.getText(),(String)(((IStructuredSelection)protocolSelector.getSelection()).getFirstElement()),serverField.getText(),usernameField.getText(),passwordField.getText());
               setupSelectorBox();
               rebuild();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
              }});
        insertButton.addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                nameField.setText("New connection");
                serverField.setText("<enter server url>");
                usernameField.setText("");
                passwordField.setText("");
                protocolSelector.getCombo().select(0);
                NavajoScriptPluginPlugin.getDefault().addServerEntry(nameField.getText(),(String)(((IStructuredSelection)protocolSelector.getSelection()).getFirstElement()),serverField.getText(),usernameField.getText(),passwordField.getText());
                rebuild();
            }

             public void widgetDefaultSelected(SelectionEvent e) {
               }});
        deleteButton.addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                NavajoScriptPluginPlugin.getDefault().deleteServerEntry(selector.getCombo().getSelectionIndex());
                setupSelectorBox();
                rebuild();
           }

             public void widgetDefaultSelected(SelectionEvent e) {
               }});
        testButton.addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                ServerEntry sel = (ServerEntry)((IStructuredSelection)selector.getSelection()).getFirstElement();
                ArrayList al = NavajoScriptPluginPlugin.getDefault().getServerEntries();
//                for (int i = 0; i < al.size(); i++) {
//                    ServerEntry current = (ServerEntry)al.get(i);
//                    if (current.getName().equals(sel)) {
                        try {
                            Navajo n = sel.runInit("InitNavajoStatus");
                            Message msg = n.getMessage("error");
                            Message cond = n.getMessage("ConditionErrors");
                            n.write(System.err);
                            if (msg!=null) {
                                NavajoScriptPluginPlugin.getDefault().showInfo("Something wrong.");
                                return;
                            }
                            if (cond!=null) {
                                NavajoScriptPluginPlugin.getDefault().showInfo("Something wrong: "+n.getProperty("ConditionErrors@0/Description").getValue());
                                return;
                            }
                            Property version = n.getProperty("NavajoStatus/Kernel/Version");
                            String ver = "unclear";
                            if (version!=null) {
                                ver = version.getValue();
                            }
                            NavajoScriptPluginPlugin.getDefault().showInfo("Seems fine. Running version: "+ver);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            NavajoScriptPluginPlugin.getDefault().showInfo("Something crashed. Message: "+e1.getMessage());
                        }
//                    }
//                }
              }

             public void widgetDefaultSelected(SelectionEvent e) {
               }});
//       fff.redraw();
                return myForm.getBody();
//        return client;
    }

    protected void rebuild() {
        myForm.dispose();
        createContents(myParent);
        myParent.layout();
    }

    private void setupSelectorBox() {
//        int size = selector.getCombo().getItemCount();
//        for (int i = size-1; i >= 0; i--) {
//            Object o = selector.getElementAt(i);
//            selector.remove(o);
//        }
      ArrayList arr = NavajoScriptPluginPlugin.getDefault().getServerEntries();
        for (int i = 0; i < arr.size(); i++) {
            selector.add(arr.get(i));
        }
//        selector.setInput(selector.getInput());
//        selector.refresh();
    }
    
//    private ServerEntry createServerEntry() {
//        ServerEntry se = new ServerEntry(nameField.getText(),(String)(((IStructuredSelection)protocolSelector.getSelection()).getFirstElement()),serverField.getText(),usernameField.getText(),passwordField.getText());
//        return se;
//    }
    private void setProtocol(String protocol) {
        for (int i = 0; i < protocols.length; i++) {
            if (protocol.equals(protocols[i])) {
                protocolSelector.getCombo().select(i);
            }
        }
    }
    
}