package com.dexels.navajo.studio.script.plugin.navajobrowser.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.*;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */


public class NavajoPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	public static final String P_NAVAJO_PATH = "navajoPathPreference";
	public static final String P_NAVAJO_SERVERURL = "navajoServerUrlPreference";
	public static final String P_NAVAJO_USERNAME = "navajoUsernamePreference";
	public static final String P_NAVAJO_PASSWORD = "navajoPasswordPreference";
//
//	public static final String P_BOOLEAN = "booleanPreference";
//	public static final String P_CHOICE = "choicePreference";
//	public static final String P_STRING = "stringPreference";
//
	public NavajoPreferencePage() {
		super(GRID);
		setPreferenceStore(NavajoScriptPluginPlugin.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
		initializeDefaults();
	}
/**
 * Sets the default values of the preferences.
 */
	private void initializeDefaults() {
		IPreferenceStore store = getPreferenceStore();
		if (store==null) {
            return;
        }
		store.setDefault(P_NAVAJO_PATH, System.getProperty("user.dir"));
		store.setDefault(P_NAVAJO_SERVERURL, "193.172.187.148:3000/sportlink/knvb/servlet/Postman");
		store.setDefault(P_NAVAJO_USERNAME, "ROOT");
		store.setDefault(P_NAVAJO_PASSWORD, "");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();		
		propertyChange(new PropertyChangeEvent(this,"aap","noot","mies"));
		return super.performOk();
	}
/**
 * Creates the field editors. Field editors are abstractions of
 * the common GUI blocks needed to manipulate various types
 * of preferences. Each field editor knows how to save and
 * restore itself.
 */

	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(P_NAVAJO_PATH, 
				"&Path preference:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(P_NAVAJO_SERVERURL, "Server url:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(P_NAVAJO_USERNAME, "Username:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(P_NAVAJO_PASSWORD, "Password:", getFieldEditorParent()));

	}
	
	public void init(IWorkbench workbench) {
	}
}