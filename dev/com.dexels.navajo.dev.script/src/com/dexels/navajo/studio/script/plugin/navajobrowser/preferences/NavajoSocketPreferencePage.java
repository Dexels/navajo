/*
 * Created on Aug 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser.preferences;

import java.util.StringTokenizer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
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

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

public class NavajoSocketPreferencePage extends PreferencePage implements IWorkbenchPreferencePage{
    private ScrolledForm myForm;
    private Composite panel;
    private FormToolkit formToolKit;
    private Section remoteSocketSection;
//    private Section navajoSection;
    private Text serverUrlText;
    private Text usernameText;
    private Text passwordText;
    private Text portText;
    
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoSocketPreferencePage.class);

    @Override
	public Control createContents(Composite parent) {
        formToolKit = new FormToolkit(parent.getDisplay());
        formToolKit.setBackground(new Color(Display.getCurrent(),220,220,240));
        myForm = new ScrolledForm(parent, SWT.V_SCROLL | SWT.H_SCROLL);
        myForm.setExpandHorizontal(true);
        myForm.setExpandVertical(true);
        formToolKit = new FormToolkit(parent.getDisplay());
        myForm.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true));
        myForm.getBody().setLayout(new FillLayout(SWT.VERTICAL));
        remoteSocketSection = formToolKit.createSection(myForm.getBody(), ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
        remoteSocketSection.setText("Navajo socket settings:");

        panel = formToolKit.createComposite(remoteSocketSection);
        remoteSocketSection.setClient(panel);
         TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 2;
        panel.setLayout(layout);
        
        formToolKit.createLabel(panel, "Server url: (without protocol)");
        serverUrlText = formToolKit.createText(panel, NavajoScriptPluginPlugin.getDefault().getRemoteServer(),SWT.BORDER);
        serverUrlText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
        formToolKit.createLabel(panel, "Username:");
        usernameText = formToolKit.createText(panel, NavajoScriptPluginPlugin.getDefault().getRemoteUsername(),SWT.BORDER);
        usernameText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
        formToolKit.createLabel(panel, "Password:");
        passwordText = formToolKit.createText(panel, NavajoScriptPluginPlugin.getDefault().getRemotePassword(),SWT.BORDER);
        passwordText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
        formToolKit.createLabel(panel, "Port: (requires socket restart)");
        portText = formToolKit.createText(panel, ""+NavajoScriptPluginPlugin.getDefault().getRemotePort(),SWT.BORDER);
        portText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
        remoteSocketSection.setExpanded(true);
        return myForm.getBody();
    }

    @Override
	public boolean okToLeave() {
       
        return super.okToLeave();
    }
    @Override
	public boolean performOk() {
        IPreferenceStore ipp = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        if ("".equals(serverUrlText.getText())) {
            NavajoScriptPluginPlugin.getDefault().showError("Server URL is required!");
            return false;
        }
        if ("".equals(usernameText.getText())) {
            NavajoScriptPluginPlugin.getDefault().showError("Username is required!");
            return false;
        }
        if ("".equals(portText.getText())) {
            NavajoScriptPluginPlugin.getDefault().showError("Port number is required!");
            return false;
        }
        try {
            StringTokenizer st = new StringTokenizer(serverUrlText.getText(),":");
//            String serv = st.nextToken();
            int port = Integer.parseInt(st.nextToken());
            int portport = Integer.parseInt(portText.getText());
            if (portport!=port) {
                NavajoScriptPluginPlugin.getDefault().showError("No, no no...\nThe server port number should match the port number to start the socket on.\nMakes sense, right?");
                return false;
           }
        } catch (Exception e) {
        	logger.error("Error: ", e);
        	NavajoScriptPluginPlugin.getDefault().showError("The server url should have the format:  hostname:port");
            return false;
        }
        if (serverUrlText.getText().indexOf("/")!=-1) {
            NavajoScriptPluginPlugin.getDefault().showError("The server url should have the format:  hostname:port");
            return false;
        }
        
        if (!serverUrlText.getText().equals(NavajoScriptPluginPlugin.getDefault().getRemoteServer())) {
            ipp.setValue(NavajoScriptPluginPlugin.REMOTE_SERVERURL, serverUrlText.getText());
        }
        if (!usernameText.getText().equals(NavajoScriptPluginPlugin.getDefault().getRemoteUsername())) {
            ipp.setValue(NavajoScriptPluginPlugin.REMOTE_USERNAME, usernameText.getText());
        }
        if (!passwordText.getText().equals(NavajoScriptPluginPlugin.getDefault().getRemotePassword())) {
            ipp.setValue(NavajoScriptPluginPlugin.REMOTE_PASSWORD, passwordText.getText());
        }
        if (!portText.getText().equals(""+NavajoScriptPluginPlugin.getDefault().getRemotePort())) {
            if (NavajoScriptPluginPlugin.getDefault().getCurrentSocketLaunch() != null) {
                NavajoScriptPluginPlugin.getDefault().showWarning("Changing the port number while the server is running requires a restart of the socket server.");
            }
            ipp.setValue(NavajoScriptPluginPlugin.REMOTE_PORT, portText.getText());
        }
        
        return super.performOk();
    }
    @Override
	public void init(IWorkbench workbench) {
        
    }
}
