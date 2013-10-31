package com.dexels.navajo.studio.script.plugin.navajobrowser.preferences;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.studio.eclipse.ServerEntry;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.navajobrowser.PreferenceComponentFactory;

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

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoPreferencePage.class);
	
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


    @Override
	public void init(IWorkbench workbench) {
    }

    @Override
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
        navajoSection = formToolKit.createSection(myForm.getBody(), ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
        navajoSection.setText("Navajo entries:");
        navajoSection.setExpanded(true);
                
        createNavajoParts();
        tmlBrowserSection = formToolKit.createSection(myForm.getBody(), ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
        tmlBrowserSection.setText("Navajo browser entries:");

  
        tmlBrowserSection.setExpanded(true);
        createTmlBrowserParts();
        createNavajoParts();
        return myForm.getBody();
    }

    @Override
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

    @Override
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

            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection sel = (IStructuredSelection) event.getSelection();
                System.err.println("FirsT: "+sel.getFirstElement());
//                IStructuredSelection sel2 = (IStructuredSelection)defaultProjectSelector.getCombo().getSelection();
                currentDefaultProject = (IProject)sel.getFirstElement();
            }});
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
            @Override
			public void widgetSelected(SelectionEvent e) {
                int index = selector.getCombo().getSelectionIndex();
                ServerEntry se = NavajoScriptPluginPlugin.getDefault().getServerEntries().get(index);
                nameField.setText(se.getName());
                serverField.setText(se.getServer());
                usernameField.setText(se.getUsername());
                passwordField.setText(se.getPassword());
                setProtocol(se.getProtocol());
            }

 
            @Override
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
           @Override
		public void widgetSelected(SelectionEvent e) {
               NavajoScriptPluginPlugin.getDefault().updateServerEntry(selector.getCombo().getSelectionIndex(),nameField.getText(),(String)(((IStructuredSelection)protocolSelector.getSelection()).getFirstElement()),serverField.getText(),usernameField.getText(),passwordField.getText());
               setupSelectorBox();
               rebuildTmlBrowserComposite();
            }

            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
              }});
        insertButton.addSelectionListener(new SelectionListener(){
            @Override
			public void widgetSelected(SelectionEvent e) {
                nameField.setText("New connection");
                serverField.setText("<enter server url>");
                usernameField.setText("");
                passwordField.setText("");
                protocolSelector.getCombo().select(0);
                NavajoScriptPluginPlugin.getDefault().addServerEntry(nameField.getText(),(String)(((IStructuredSelection)protocolSelector.getSelection()).getFirstElement()),serverField.getText(),usernameField.getText(),passwordField.getText());
                rebuildTmlBrowserComposite();
            }

             @Override
			public void widgetDefaultSelected(SelectionEvent e) {
               }});
        deleteButton.addSelectionListener(new SelectionListener(){
            @Override
			public void widgetSelected(SelectionEvent e) {
                NavajoScriptPluginPlugin.getDefault().deleteServerEntry(selector.getCombo().getSelectionIndex());
                setupSelectorBox();
                rebuildTmlBrowserComposite();
           }

             @Override
			public void widgetDefaultSelected(SelectionEvent e) {
               }});
        testButton.addSelectionListener(new SelectionListener(){
            @Override
			public void widgetSelected(SelectionEvent e) {
                ServerEntry sel = (ServerEntry)((IStructuredSelection)selector.getSelection()).getFirstElement();
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
                        	logger.error("Error: ", e1);
                        	NavajoScriptPluginPlugin.getDefault().showInfo("Something crashed. Message: "+e1.getMessage());
                        }
              }

             @Override
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
      List<ServerEntry> arr = NavajoScriptPluginPlugin.getDefault().getServerEntries();
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