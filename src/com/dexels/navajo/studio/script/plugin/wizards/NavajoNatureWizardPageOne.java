/*
 * Created on May 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.wizards;

import org.eclipse.core.resources.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoNatureWizardPageOne extends WizardPage {

    private Text rootField;
    private final IProject myProject;
    private Label statusLabel;

    /**
     * @param pageName
     * @param title
     * @param titleImage
     * @param myProject
     */
    protected NavajoNatureWizardPageOne(String pageName, String title, ImageDescriptor titleImage, IProject project) {
        super(pageName, title, titleImage);
        myProject = project;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    
    private GridData createGridData() {
        GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;
//       return new GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.VERTICAL_ALIGN_FILL,true,true);
        return gd;
    }
    
    public void createControl(Composite parent) {
        Composite top = new Composite(parent,SWT.NONE);
        top.setLayout(new GridLayout(1,false));
        Label l = new Label(top,SWT.BOLD);
        l.setLayoutData(createGridData());
        l.setText("Select the root directory for the navajo: ");
        Label l2 = new Label(top,SWT.NONE);
        l2.setLayoutData(createGridData());
        l.setText("Usually this is something like: navajo-tester or runtime_dir.\n" +
        		"For existing projects, it is important to get this one right. ");
        statusLabel = new Label(top,SWT.NONE);
        statusLabel.setText("-");
        statusLabel.setLayoutData(createGridData());
        rootField = new Text(top,SWT.BORDER);
        rootField.setLayoutData(createGridData());
       rootField.addModifyListener(new ModifyListener(){
            public void modifyText(ModifyEvent e) {
                updateState();
            }

        });
        setControl(top);
     
    }

    private void updateState() {
        System.err.println("Setting to: "+(rootField.getText().length()>0));
        IFolder rootfolder = myProject.getFolder(rootField.getText());
        if (rootfolder.exists()) {
         statusLabel.setText("Ok, the folder exists!");
        } else {
            statusLabel.setText("The folder does not exist. It will be created!");
        }
        setPageComplete(rootField.getText().length()>0);

    }    
    
    
    /**
     * @return
     */
    public String getRootDir() {
        return rootField.getText();
    }
}
