/*
 * Created on Mar 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse.prefs;

import java.util.*;

import org.eclipse.jdt.internal.debug.ui.launcher.*;
import org.eclipse.jdt.launching.*;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.script.plugin.*;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoJVMPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, NavajoPluginResources {

	static final private int FIELD_WIDTH = 50;
	private ComboFieldEditor jvmChoice;
	private ListFieldEditor jvmParamaters;
	private ClasspathFieldEditor jvmClasspath;
	private ClasspathFieldEditor jvmBootClasspath;
	private BooleanFieldEditor debugModeEditor;

	public NavajoJVMPreferencePage() {
		super();
		setPreferenceStore(NavajoScriptPluginPlugin.getDefault().getPreferenceStore());
		//		setDescription("");
	}

	/*
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		//		GridData gd = new GridData(GridData.FILL_BOTH);
		//		gd.grabExcessVerticalSpace = true;
		//		gd.grabExcessHorizontalSpace = true;
		//		composite.setLayoutData(gd);

		// Collect all JREs
		ArrayList allVMs = new ArrayList();
		IVMInstallType[] vmTypes = JavaRuntime.getVMInstallTypes();
		for (int i = 0; i < vmTypes.length; i++) {
			IVMInstall[] vms = vmTypes[i].getVMInstalls();
			for (int j = 0; j < vms.length; j++) {
				allVMs.add(vms[j]);
			}
		}

		String[][] namesAndValues = new String[allVMs.size()][2];
		for (int i = 0; i < allVMs.size(); i++) {
			namesAndValues[i][0] = ((IVMInstall) allVMs.get(i)).getName();
			namesAndValues[i][1] = ((IVMInstall) allVMs.get(i)).getId();
		}

		jvmChoice = new ComboFieldEditor(NavajoScriptPluginPlugin.NAVAJO_PREF_JRE_KEY, NavajoPluginResources.PREF_PAGE_JRE_LABEL, namesAndValues, composite);

		debugModeEditor = new BooleanFieldEditor(NavajoScriptPluginPlugin.NAVAJO_PREF_DEBUGMODE_KEY,NavajoPluginResources.PREF_PAGE_DEBUGMODE_LABEL, composite);
		this.initField(debugModeEditor);

		new Label(composite, SWT.NULL);
		Composite group = new Composite(composite, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		group.setLayout(new GridLayout(2, false));
//		Button btAddLaunch = new Button(group, SWT.PUSH);
//		btAddLaunch.setText(NavajoPluginResources.PREF_PAGE_CREATE_LAUNCH_LABEL);
//		btAddLaunch.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					TomcatLauncherPlugin.getDefault().getTomcatBootstrap().addLaunch();
//				} catch (Exception ex) {
//					TomcatLauncherPlugin.log("Failed to create launch configuration/n");
//					TomcatLauncherPlugin.log(ex);
//				}
//			}
//		});
//		Button btLog = new Button(group, SWT.PUSH);
//		btLog.setText(PREF_PAGE_DUMP_CONFIG_LABEL);
//		btLog.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					TomcatLauncherPlugin.getDefault().getTomcatBootstrap().logConfig();
//				} catch (Exception ex) {
//					TomcatLauncherPlugin.log("Failed to create launch configuration/n");
//					TomcatLauncherPlugin.log(ex);
//				}
//			}
//		});


		jvmParamaters = new ListFieldEditor(NavajoScriptPluginPlugin.NAVAJO_PREF_JVM_PARAMETERS_KEY, PREF_PAGE_PARAMETERS_LABEL, composite);
		jvmClasspath = new ClasspathFieldEditor(NavajoScriptPluginPlugin.NAVAJO_PREF_JVM_CLASSPATH_KEY, PREF_PAGE_CLASSPATH_LABEL, composite);
		jvmBootClasspath = new ClasspathFieldEditor(NavajoScriptPluginPlugin.NAVAJO_PREF_JVM_BOOTCLASSPATH_KEY, PREF_PAGE_BOOTCLASSPATH_LABEL, composite);

		this.initField(jvmChoice);
		this.initField(jvmParamaters);
		this.initField(jvmClasspath);
		this.initField(jvmBootClasspath);

		return composite;
	}

	/*
	 * @see IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	public boolean performOk() {
		jvmChoice.store();
		jvmBootClasspath.store();
		jvmClasspath.store();
		jvmParamaters.store();
		debugModeEditor.store();

		NavajoScriptPluginPlugin.getDefault().savePluginPreferences();
		return true;
	}

	private void initField(FieldEditor field) {
		field.setPreferenceStore(getPreferenceStore());
		field.setPreferencePage(this);
		field.load();
	}

}
