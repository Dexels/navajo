package com.dexels.navajo.studio.eclipse.prefs;

/*
 * (c) Copyright Sysdeo SA 2001, 2002.
 * All Rights Reserved.
 */

import java.io.File;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.eclipse.prefs.*;
import com.dexels.navajo.studio.script.plugin.*;


public class AdvancedPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, NavajoPluginResources {

	private BooleanFieldEditor securityEditor;

	static final private int FIELD_WIDTH = 50;
	private RadioGroupFieldEditor version;
//	private DirectoryFieldEditor base;	
//	private FileFieldEditor configFile;
	private ProjectListEditor projectListEditor;
	private ModifyListener fModifyListener;
		
	public AdvancedPreferencePage() {
		super();
		setPreferenceStore(NavajoScriptPluginPlugin.getDefault().getPreferenceStore());
	}

	/*
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1, false));

						

		Group targetperspectiveGroup = new Group(composite,SWT.NONE);
						
		Group projectListGroup = new Group(composite,SWT.NONE);
		String[] excludedProjectsNature = {NavajoScriptPluginPlugin.NATURE_ID};	
		projectListEditor = new ProjectListEditor(excludedProjectsNature);
		projectListEditor.setLabel(PREF_PAGE_PROJECTINCP_LABEL);
		Control projectList = projectListEditor.getControl(projectListGroup);
		GridData gd2 = new GridData();
		gd2.horizontalAlignment = gd2.FILL;
		projectList.setLayoutData(gd2);
		initLayoutAndData(projectListGroup, 1);		

//		this.initField(base);

		new Label(composite, SWT.NULL); //blank

		return composite;
	}

	/*
	 * @see IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}


	public boolean performOk() {	
//		base.store();
//		targetPerspectiveEditor.store();
//		securityEditor.store();
		NavajoScriptPluginPlugin.getDefault().setProjectsInCP(projectListEditor.getCheckedElements());
		NavajoScriptPluginPlugin.getDefault().savePluginPreferences();
		return true;	
	}
	
	private void initField(FieldEditor field) {
		field.setPreferenceStore(getPreferenceStore());
		field.setPreferencePage(this);
		field.load();		
	}
	
	
	private void initLayoutAndData(Group aGroup, int spanH, int spanV, int numColumns) {
		GridLayout gl = new GridLayout(numColumns, false);
		aGroup.setLayout(gl);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = spanH;
		gd.verticalSpan = spanV;
		aGroup.setLayoutData(gd);
	}

	private void initLayoutAndData(Group aGroup, int numColumns) {
		GridLayout gl = new GridLayout(numColumns, false);
		aGroup.setLayout(gl);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		aGroup.setLayoutData(gd);
	}
	

}

