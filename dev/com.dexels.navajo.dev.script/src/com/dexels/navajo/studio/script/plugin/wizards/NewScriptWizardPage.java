package com.dexels.navajo.studio.script.plugin.wizards;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (tsl).
 */

public class NewScriptWizardPage extends WizardPage {
    private Text containerText;


    private String scriptName;

    public NewScriptWizardPage() {
        super("wizardPage");
        setTitle("Empty script wizard");
        setDescription("This wizard creates a new, empty script file.");
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    @Override
	public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 3;
        layout.verticalSpacing = 9;
        Label label = new Label(container, SWT.NULL);
        label.setText("Script name:");

        //		scriptText = new Text(container, SWT.BORDER | SWT.SINGLE |
        // SWT.READ_ONLY);
        //		scriptText.setText(NavajoScriptPluginPlugin.getDefault().getScriptPath());
        //		scriptText.setLayoutData(new GridData(GridData.END));

        containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
        containerText.setText("[Scriptname]");
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        containerText.setLayoutData(gd);
        containerText.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        //		Button button = new Button(container, SWT.PUSH);

        //		Button radioEmpty = new Button(container, SWT.RADIO);
        //		radioEmpty.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        //		radioEmpty.setText("Empty script");
        //		Button radioInit = new Button(container, SWT.RADIO);
        //		radioInit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        //		radioInit.setText("Init script");
        //		Button radioProcess = new Button(container, SWT.RADIO);
        //		radioProcess.setText("Process script");
        //		radioProcess.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //		button.setText("Browse...");
        //		button.addSelectionListener(new SelectionAdapter() {
        //			public void widgetSelected(SelectionEvent e) {
        //				handleBrowse();
        //			}
        //		});
//        label = new Label(container, SWT.NULL);
//        label.setText("&File name:");

        //		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
        //		gd = new GridData(GridData.FILL_HORIZONTAL);
        //		fileText.setLayoutData(gd);

        containerText.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        initialize();
        dialogChanged();
        setControl(container);
    }

    /**
     * Tests if the current workbench selection is a suitable container to use.
     */

    private void initialize() {
//        System.err.println("INITIALIZING....");
//        if (selection == null) {
//            System.err.println("hmmm...");
//        } else {
//            System.err.println("affe: " + selection);
//        }
//        
        containerText.setText("[Enter your scriptname]");
        containerText.selectAll();
        
//        if (selection != null && selection.isEmpty() == false && selection instanceof IStructuredSelection) {
//            IStructuredSelection ssel = (IStructuredSelection) selection;
//            if (ssel.size() > 1)
//                return;
//            Object obj = ssel.getFirstElement();
//            if (obj instanceof IResource) {
//                IContainer container;
//                if (obj instanceof IContainer)
//                    container = (IContainer) obj;
//                else
//                    container = ((IResource) obj).getParent();
//                containerText.setText("[Enter your scriptname]");
//                containerText.selectAll();
//            }
//        }
//        //		fileText.setText("newscript.tsl");
    }

    /**
     * Uses the standard container selection dialog to choose the new value for
     * the container field.
     */
//
//    private void handleBrowse() {
//        ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
//                "Select new file container");
//        if (dialog.open() == ContainerSelectionDialog.OK) {
//            Object[] result = dialog.getResult();
//            if (result.length == 1) {
//                containerText.setText(((Path) result[0]).toOSString());
//            }
//        }
//    }

    /**
     * Ensures that both text fields are set.
     */

    private void dialogChanged() {
        scriptName = containerText.getText();
        //		String fileName = getFileName();
        System.err.println("Current scriptname: "+scriptName);
        if (scriptName.length() == 0) {
            updateStatus("Script file name must be defined!");
            return;
        }
        //		int dotLoc = fileName.lastIndexOf('.');
        //		if (dotLoc != -1) {
        //			String ext = fileName.substring(dotLoc + 1);
        //			if (ext.equalsIgnoreCase("tsl") == false) {
        //				updateStatus("File extension must be \"tsl\"");
        //				return;
        //			}
        //		}
        updateStatus(null);
    }

    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    public String getScriptName() {
        return scriptName;
    }
    //	public String getFileName() {
    //		return fileText.getText();
    //	}
}