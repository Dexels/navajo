package com.dexels.navajo.studio.eclipse.prefs;

/*
 * (c) Copyright Sysdeo SA 2001, 2002.
 * All Rights Reserved.
 */

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.script.plugin.*;


public class NavajoSourcePathPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, NavajoPluginResources {

	private ProjectListEditor projectListEditor;
		
	public NavajoSourcePathPreferencePage() {
		super();
		setPreferenceStore(NavajoScriptPluginPlugin.getDefault().getPreferenceStore());
	}

	/*
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		projectListEditor = new ProjectListEditor();
		projectListEditor.setLabel(PREF_PAGE_PROJECTINSOURCEPATH_LABEL);
		Control projectList = projectListEditor.getControl(composite);
		GridData gd2 = new GridData(GridData.FILL_BOTH);
//		gd2.horizontalAlignment = gd2.FILL;
		projectList.setLayoutData(gd2);		

		projectListEditor.setCheckedElements(NavajoScriptPluginPlugin.getDefault().getProjectsInSourcePath());		 
		
		return composite;
	}

	/*
	 * @see IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}


	public boolean performOk() {	
	    NavajoScriptPluginPlugin.getDefault().setProjectsInSourcePath(projectListEditor.getCheckedElements());
	    NavajoScriptPluginPlugin.getDefault().savePluginPreferences();
		return true;	
	}
		
}

