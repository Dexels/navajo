package com.dexels.navajo.studio.script.plugin.navajobrowser.preferences;

import java.util.*;

import org.eclipse.core.resources.*;
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
import com.dexels.navajo.studio.eclipse.prefs.*;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.navajobrowser.*;

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
    private Composite tmlBrowserComposite;
    private FormToolkit formToolKit;
    private Section tmlBrowserSection;
    private Section navajoSection;
    private ComboViewer defaultProjectSelector;
    //
    
    private IProject currentDefaultProject = null;
    
    public NavajoPreferencePage() {
        super("Navajo preferences");
        setPreferenceStore(NavajoScriptPluginPlugin.getDefault().getPreferenceStore());
    }


    public void init(IWorkbench workbench) {
    }

    public Control createContents(Composite parent) {
        myParent = parent;
        formToolKit = new FormToolkit(parent.getDisplay());
        formToolKit.setBackground(new Color(Display.getCurrent(),220,220,240));
        myForm = new ScrolledForm(parent, SWT.V_SCROLL | SWT.H_SCROLL);
        myForm.setExpandHorizontal(true);
        myForm.setExpandVertical(true);
        formToolKit = new FormToolkit(parent.getDisplay());
        myForm.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true));
        myForm.getBody().setLayout(new FillLayout(SWT.VERTICAL));
        navajoSection = formToolKit.createSection(myForm.getBody(), Section.TITLE_BAR | Section.TWISTIE);
        navajoSection.setText("Navajo entries:");
        navajoSection.setExpanded(true);
                
        createNavajoParts();
        tmlBrowserSection = formToolKit.createSection(myForm.getBody(), Section.TITLE_BAR | Section.TWISTIE);
        tmlBrowserSection.setText("Navajo browser entries:");

  
        tmlBrowserSection.setExpanded(true);
        createTmlBrowserParts();
        createNavajoParts();
        return myForm.getBody();
    }

    protected void performApply() {
          super.performApply();
//          IStructuredSelection iss = (IStructuredSelection)defaultProjectSelector.getSelection();
//          if (iss==null || iss.isEmpty()) {
//              System.err.println("No selection?");
//              return;
//        }
//          IProject ipp = (IProject)iss.getFirstElement();
          commitDefaultProject();
    }

    private void commitDefaultProject() {
        System.err.println("Project: "+currentDefaultProject);
        if (currentDefaultProject!=null) {
            NavajoScriptPluginPlugin.getDefault().getPreferenceStore().setValue(NavajoScriptPluginPlugin.NAVAJO_DEFAULT_PROJECT_KEY, currentDefaultProject.getName());
      } else {
          NavajoScriptPluginPlugin.getDefault().getPreferenceStore().setToDefault(NavajoScriptPluginPlugin.NAVAJO_DEFAULT_PROJECT_KEY);

      }
        
    }

    public boolean performOk() {
         boolean b = super.performOk();
        commitDefaultProject();
        return b;
    }


    private void createNavajoParts() {
        String currentDefaultProjectSetting = NavajoScriptPluginPlugin.getDefault().getPreferenceStore().getString(NavajoScriptPluginPlugin.NAVAJO_DEFAULT_PROJECT_KEY);
        Composite navajoComposite = formToolKit.createComposite(navajoSection);
        navajoSection.setClient(navajoComposite);
         TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 2;
        navajoComposite.setLayout(layout);
       formToolKit.createLabel(navajoComposite, "Default Navajo project:         ");
        defaultProjectSelector = PreferenceComponentFactory.createProjectListCombo(navajoComposite,NavajoScriptPluginPlugin.NAVAJO_NATURE);
        defaultProjectSelector.setInput(ResourcesPlugin.getWorkspace());
        String[] it = defaultProjectSelector.getCombo().getItems();
        if (currentDefaultProjectSetting!=null) {
            for (int i = 0; i < it.length; i++) {
                System.err.println("Checking: "+it[i]+" against: "+currentDefaultProjectSetting);
                if (it[i].equals(currentDefaultProjectSetting)) {
                    defaultProjectSelector.getCombo().select(i);
                }
            }
        }
        
        defaultProjectSelector.addSelectionChangedListener(new ISelectionChangedListener(){

            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection sel = (IStructuredSelection) event.getSelection();
                System.err.println("FirsT: "+sel.getFirstElement());
//                IStructuredSelection sel2 = (IStructuredSelection)defaultProjectSelector.getCombo().getSelection();
                currentDefaultProject = (IProject)sel.getFirstElement();
//                System.err.println("FirsT: "+sel2.getFirstElement());
            }});
//        if (navajoProjects == null || navajoProjects.size()==0) {
//            formToolKit.createLabel(navajoComposite, "No open navajo projects found.");
//            
//        } else {
//            formToolKit.createLabel(navajoComposite, navajoProjects.size()+" open navajo project(s) found.");
//        }
     }
    
    private void createTmlBrowserParts() {
        tmlBrowserComposite = formToolKit.createComposite(tmlBrowserSection);
        tmlBrowserSection.setClient(tmlBrowserComposite);
         TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 2;
        tmlBrowserComposite.setLayout(layout);
        formToolKit.createLabel(tmlBrowserComposite, "Server entry:         ");
        selector = new ComboViewer(tmlBrowserComposite);
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
              
        formToolKit.createLabel(tmlBrowserComposite, "Name:");
        nameField = formToolKit.createText(tmlBrowserComposite, "",SWT.BORDER);
        formToolKit.createLabel(tmlBrowserComposite, "Protocol:");
        protocolSelector = new ComboViewer(tmlBrowserComposite);
        protocolSelector.add(protocols);
        formToolKit.createLabel(tmlBrowserComposite, "Server:");
        serverField = formToolKit.createText(tmlBrowserComposite, "",SWT.BORDER);
        formToolKit.createLabel(tmlBrowserComposite, "Username:");
        usernameField = formToolKit.createText(tmlBrowserComposite, "",SWT.BORDER);
        formToolKit.createLabel(tmlBrowserComposite, "Password:");
        passwordField = formToolKit.createText(tmlBrowserComposite, "",SWT.BORDER| SWT.PASSWORD);
        passwordField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB));
        
        nameField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        protocolSelector.getCombo().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        serverField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        usernameField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        passwordField.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,1));
        
        Composite buttonBar = formToolKit.createComposite(tmlBrowserComposite);
        buttonBar.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB,1,2));
        buttonBar.setLayout(new RowLayout());
        saveButton = formToolKit.createButton(buttonBar, "Save", SWT.PUSH);
        deleteButton = formToolKit.createButton(buttonBar, "Delete", SWT.PUSH);
        insertButton = formToolKit.createButton(buttonBar, "Insert", SWT.PUSH);
        testButton = formToolKit.createButton(buttonBar, "Test connection", SWT.PUSH);
        saveButton.addSelectionListener(new SelectionListener(){
           public void widgetSelected(SelectionEvent e) {
               NavajoScriptPluginPlugin.getDefault().updateServerEntry(selector.getCombo().getSelectionIndex(),nameField.getText(),(String)(((IStructuredSelection)protocolSelector.getSelection()).getFirstElement()),serverField.getText(),usernameField.getText(),passwordField.getText());
               setupSelectorBox();
               rebuildTmlBrowserComposite();
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
                rebuildTmlBrowserComposite();
            }

             public void widgetDefaultSelected(SelectionEvent e) {
               }});
        deleteButton.addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                NavajoScriptPluginPlugin.getDefault().deleteServerEntry(selector.getCombo().getSelectionIndex());
                setupSelectorBox();
                rebuildTmlBrowserComposite();
           }

             public void widgetDefaultSelected(SelectionEvent e) {
               }});
        testButton.addSelectionListener(new SelectionListener(){
            public void widgetSelected(SelectionEvent e) {
                ServerEntry sel = (ServerEntry)((IStructuredSelection)selector.getSelection()).getFirstElement();
                ArrayList al = NavajoScriptPluginPlugin.getDefault().getServerEntries();
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
              }

             public void widgetDefaultSelected(SelectionEvent e) {
               }});
    }

    protected void rebuildTmlBrowserComposite() {
        tmlBrowserComposite.dispose();
        createTmlBrowserParts();
        tmlBrowserSection.setExpanded(true);
//        myForm.dispose();
//        createContents(myParent);
        myParent.layout();
    }

    private void setupSelectorBox() {
      ArrayList arr = NavajoScriptPluginPlugin.getDefault().getServerEntries();
        for (int i = 0; i < arr.size(); i++) {
            selector.add(arr.get(i));
        }
    }

    private void setProtocol(String protocol) {
        for (int i = 0; i < protocols.length; i++) {
            if (protocol.equals(protocols[i])) {
                protocolSelector.getCombo().select(i);
            }
        }
    }
    
}