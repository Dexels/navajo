/*
 * Created on May 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.util.Generate;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowGeneratedInputTml extends BaseNavajoAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		try {
			IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(
					file.getProject(), scriptName);
			if (scriptFile != null) {
				// System.err.println("not null");
				// System.err.println("TML: "+tmlFile.getFullPath().toString());
				if (!scriptFile.exists()) {
					try {
						scriptFile.refreshLocal(0, null);
					} catch (CoreException e) {
						NavajoScriptPluginPlugin.getDefault().log(
								"Error refreshing TML file. Strange.", e);

					}
				}
				if (scriptFile.exists()) {
					// System.err.println("And it exists");
					Generate g = new Generate();
					InputStream is = scriptFile.getContents();
					Navajo input = g.getInputPart(null,is);
					input.write(System.err);
					input.addMethod(NavajoFactory.getInstance().createMethod(input, scriptName, null));
					Header h = NavajoFactory.getInstance().createHeader(input, "unknown", "unknown", "unknown", -1);
					input.addHeader(h);
					
					IFolder iff = NavajoScriptPluginPlugin.getDefault().getTmlFolder(scriptFile.getProject());
					System.err.println("TML FoldeRrrrr: "+iff.getLocation());
					IFile igenerated = iff.getFile("Generated.tml");
					if (igenerated.exists()) {
						igenerated.delete(true, null);
						System.err.println("Deleted");
					}
					System.err.println("creating iff: "+igenerated.getLocation().toString());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					input.write(baos);
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					System.err.println("Aout to create: ");
					igenerated.create(bais, true, null);
					System.err.println("created. "+igenerated.getLocation());
                    NavajoScriptPluginPlugin.getDefault().showTml(igenerated,scriptName+"(Generated)");
//					}
					//					NavajoScriptPluginPlugin.getDefault().showTml(scriptFile,
//							scriptName);
				} else {
					NavajoScriptPluginPlugin.getDefault().showInfo(
							"Tml file for: " + scriptName
									+ " not found. Run the script first.");
				}
			} else {
				NavajoScriptPluginPlugin.getDefault().showInfo(
						"Tml file for: " + scriptName
								+ " not found. Run the script first.");
			}
		} catch (Exception e) {
			NavajoScriptPluginPlugin.getDefault().showInfo(
					"Error retrieving TML file: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
