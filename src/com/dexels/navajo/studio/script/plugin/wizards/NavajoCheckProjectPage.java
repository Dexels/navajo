/*
 * Created on May 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.wizards;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoCheckProjectPage extends WizardPage {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */

    private boolean javaOk = false;

    private boolean docOk = false;

    private boolean navajoOk = false;

    private boolean adaptersOk = false;

    private boolean otherOk = true;

    private final IProject myProject;

    private Composite top;

    ImageDescriptor okImage = ImageDescriptor.createFromURL(getClass().getClassLoader().getResource("/com/dexels/navajo/studio/images/ok.GIF"));
    ImageDescriptor errorImage = ImageDescriptor.createFromURL(getClass().getClassLoader().getResource("/com/dexels/navajo/studio/images/cancel.GIF"));

    public void createControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        top.setLayout(new GridLayout(3, false));
 
        otherOk = true;
        try {
            checkProject(myProject);
        } catch (JavaModelException e) {
            showError(false,"Core","Error checking project, javamodel error: " + e.getMessage(),"Ok");
            otherOk = false;
            e.printStackTrace();
        } catch (CoreException e) {
            showError(false,"Core", "Error checking project, coreexception: " + e.getMessage(),"Ok");
            otherOk = false;
            e.printStackTrace();
        }
        setControl(top);
    }

    protected NavajoCheckProjectPage(String pageName, String title, ImageDescriptor titleImage, IProject project) {
        super(pageName, title, titleImage);
        myProject = project;
    }

    private GridData createGridData() {
        GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;
        //       return new
        // GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.VERTICAL_ALIGN_FILL,true,true);
        return gd;
    }

    public void checkProject(IProject ipp) throws JavaModelException, CoreException {
        IProjectNature ipn = ipp.getNature("org.eclipse.jdt.core.javanature");
        if (ipn == null) {
            showError(ipn != null, "Java Project", "Not a java project. Strange. Make sure it is a java project, and try again.", "Ok.");
            javaOk = false;
            return;
        }
        javaOk = true;
        JavaProject jp = (JavaProject) ipn;
        IType itt = jp.findType("com.dexels.navajo.document.Navajo");
        docOk = itt != null;
        showError(itt != null, "Navajo Document","Navajo document not found. Check the classpath.", "Ok.");
        itt = jp.findType("com.dexels.navajo.server.Dispatcher");
        navajoOk = itt != null;
        showError(itt != null, "Navajo", "Navajo not found. Check the classpath.", "Ok.");
        itt = jp.findType("com.dexels.navajo.adapter.NavajoMap");
        adaptersOk = itt != null;
        showError(itt != null,"Navajo Adapters","Navajo adapters not found. Check the classpath.", "Ok.");
    }

    private void showError(boolean condition, String name, String message, String okMessage) {
        Label l = new Label(top, SWT.BOLD);
        l.setText(name);
        l.setLayoutData(createGridData());
        l = new Label(top, SWT.NONE);
        l.setImage(getImage());
        l.setImage(condition?okImage.createImage():errorImage.createImage());
//        l.setText(condition?"V":"X");
        l.setLayoutData(createGridData());
        l = new Label(top, SWT.NONE);
        l.setText(condition?okMessage:message);
        l.setLayoutData(createGridData());

    }

    public boolean isPageComplete() {
        return javaOk && docOk && navajoOk && adaptersOk && otherOk;
    }
}
