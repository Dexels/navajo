package com.dexels.navajo.studio.script.plugin.navajobrowser;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.preferences.*;

//import javax.swing.*;
//import javax.swing.tree.*;
//import java.awt.*;
//import javax.swing.event.*;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.*;
import org.eclipse.ui.part.*;

import java.util.*;
import java.awt.event.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ScriptBrowserPanel extends ViewPart {
    private ScriptDir rootDir = null;

    private TreeViewer viewer;

    private DrillDownAdapter drillDownAdapter;

    private Action action1;

    //private Action action2;
    //private Action doubleClickAction;

    public ScriptBrowserPanel() {
        rootDir = new ScriptDir(null, "", "", (ServerConnection) null, false);
    }

    public ScriptFile getNodeByName(String name) {
        StringTokenizer st = new StringTokenizer(name, ":");
        String connectionName = st.nextToken();
        String scriptname = st.nextToken();
        if (scriptname.startsWith("/")) {
            scriptname = scriptname.substring(1);
        }
        for (int i = 0; i < rootDir.getChildCount(); i++) {
            ScriptDir sd = (ScriptDir) rootDir.getChildAt(i);
            if (sd.getName().equals(connectionName)) {
                return sd.getFileByFullPath(scriptname);
            }
        }
        System.err.println("Hmmm. Did not find node for: " + connectionName);
        return rootDir.getFileByFullPath(name);
    }

    public void addNodes(ServerConnection sc, IProgressMonitor monitor) {
        rootDir.addEntry(sc.getName(), sc, monitor);
    }

    public void clearNodes() {
        rootDir.clear();
    }

    //  public void navajoLoaded(String service, Navajo n) {
    //  }
    //
    //  public void navajoRemoved(String service) {
    //  }
    //
    //  public void navajoSelected(String service, Navajo n) {
    //
    //  }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        drillDownAdapter = new DrillDownAdapter(viewer);
        ScriptTreeContentProvider content = new ScriptTreeContentProvider(getViewSite());
        content.initialize(rootDir);
        viewer.setContentProvider(content);
        viewer.setLabelProvider(new ScriptTreeLabelProvider());
        viewer.setSorter(new ScriptTreeSorter());
//        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
        contributeToActionBars();

        IPreferenceStore ips = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        ips.addPropertyChangeListener(new IPropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent event) {
                System.err.println("Preference change detected.");
                update();
            }
        });
        viewer.setInput(getViewSite());
        update();

    }

    public void update() {
        final Display d = PlatformUI.getWorkbench().getDisplay();
   Job job = new Job("Updating scripts") {
            protected IStatus run(IProgressMonitor monitor) {
                monitor.beginTask("Updating", 10);
                IPreferenceStore ips = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();

                String username = ips.getString(NavajoPreferencePage.P_NAVAJO_USERNAME);
                if (username == null || "".equals(username)) {
                    username = ips.getDefaultString(NavajoPreferencePage.P_NAVAJO_USERNAME);
                }
                String password = ips.getString(NavajoPreferencePage.P_NAVAJO_PASSWORD);
                if (password == null || "".equals(password)) {
                    password = ips.getDefaultString(NavajoPreferencePage.P_NAVAJO_PASSWORD);
                }
                String serverUrl = ips.getString(NavajoPreferencePage.P_NAVAJO_SERVERURL);
                if (serverUrl == null || "".equals(serverUrl)) {
                    serverUrl = ips.getDefaultString(NavajoPreferencePage.P_NAVAJO_SERVERURL);
                }
                if (serverUrl == null || "".equals(serverUrl) || username == null || "".equals(username)) {
                    return Status.CANCEL_STATUS;
                }
                System.err.println(">>" + username + " || " + password + " || " + serverUrl);
                NavajoClientFactory.createDefaultClient().setUsername(username);
                NavajoClientFactory.createDefaultClient().setPassword(password);
                NavajoClientFactory.createDefaultClient().setServerUrl(serverUrl);
                ServerConnection sc = new ServerConnection(NavajoClientFactory.getClient(), ips.getString(NavajoPreferencePage.P_NAVAJO_SERVERURL),
                        ips.getString(NavajoPreferencePage.P_NAVAJO_USERNAME), ips.getString(NavajoPreferencePage.P_NAVAJO_PASSWORD), ips
                                .getString(NavajoPreferencePage.P_NAVAJO_SERVERURL), null, null);
                clearNodes();
                addNodes(sc, monitor);
                monitor.worked(2);
                d.syncExec(new Runnable() {

                    public void run() {
                        viewer.refresh();

                    }
                });
                monitor.done();
                return Status.OK_STATUS;
            }
        };
        job.setPriority(Job.LONG);
        job.setUser(true);
        job.schedule();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    public void setFocus() {
    }

    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                ScriptBrowserPanel.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalPullDown(bars.getMenuManager());
        fillLocalToolBar(bars.getToolBarManager());
    }

    private void fillLocalPullDown(IMenuManager manager) {
        manager.add(action1);
        manager.add(new Separator());
        //	manager.add(action2);
    }

    private void fillContextMenu(IMenuManager manager) {
        manager.add(action1);
        //	manager.add(action2);
        manager.add(new Separator());
        drillDownAdapter.addNavigationActions(manager);
        // Other plug-ins can contribute there actions here
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.add(action1);
        //	manager.add(action2);
        manager.add(new Separator());
        drillDownAdapter.addNavigationActions(manager);
       
        
  }

//    public void runScript(final String script) {
//        final Display d = PlatformUI.getWorkbench().getDisplay();
//       Job job = new Job("Running script") {
//            protected IStatus run(IProgressMonitor monitor) {
//                Navajo result = null;
//                monitor.beginTask("Updating", 10);
//                try {
//                    result = NavajoClientFactory.getClient().doSimpleSend(script);
//                } catch (ClientException e) {
//                    showMessage("Error running script: " + e.getMessage());
//                    return Status.OK_STATUS;
//                }
//                final Navajo res = result;
//                d.syncExec(new Runnable() {
//
//                    public void run() {
//                        final TmlSource ts = TmlSource.getInstance();
//                         if (ts != null) {
//                             ts.setText(res.toString());
//                        }
//                         final NavajoBrowser nb = NavajoBrowser.getInstance();
//                         if (nb != null) {
//                             nb.setNavajo(res,null);
//                        }
//
//                    }
//                });
//                monitor.done();
//                return Status.OK_STATUS;
//            }
//        };
//        job.setPriority(Job.SHORT);
//        //        job.setUser(true);
//        job.schedule();
//    }

//    private void makeActions() {
//        action1 = new Action() {
//            public void run() {
//                IStructuredSelection iss = (IStructuredSelection) viewer.getSelection();
//                Object o = iss.getFirstElement();
//                if (o instanceof ScriptFile) {
//                    ScriptFile sf = (ScriptFile) o;
//                    String name = sf.getFullPath();
//                    int index = name.lastIndexOf(":");
//                    String script = name.substring(index + 1, name.length());
//                    runScript(script);
//                }
//            }
//        };
//        action1.setText("Run");
//        action1.setToolTipText("Runs the selected script");
//        action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
//     }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                action1.run();
            }
        });
    }

    private void showMessage(String message) {
        MessageDialog.openInformation(viewer.getControl().getShell(), "NavajoView", message);
    }

}