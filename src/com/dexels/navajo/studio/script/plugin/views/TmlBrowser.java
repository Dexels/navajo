/*
 * Created on Jul 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.editors.*;

public class TmlBrowser extends ViewPart implements INavajoScriptListener, IServerEntryListener {
    private Navajo myCurrentNavajo = null;

     private TmlFormComposite formComposite;
    private ComboViewer selector;
    private TextViewer myService;
    private Button goButton;
    private Button backButton;
    private Button forwardButton;
    private Button reloadButton;
   private String currentService = null;
    private String lastInit = null;
   private final Stack historyList = new Stack();
   private final Stack futureList = new Stack();
   private final Map scriptMap = new HashMap();
//    private Composite mainPanel;
//    int iii= 0;
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */

    private Composite myContainer;

    private Composite myParent;
    
    
  
    public void createPartControl(Composite parent) {
        myParent = parent;
        Control[] c = parent.getChildren();
        for (int i = 0; i < c.length; i++) {
            System.err.println("Child: "+c[i].getClass());
            c[i].dispose();
        }
        myContainer = new Composite(parent,SWT.NONE);
        myContainer.setBackground(new Color(Display.getCurrent(), 240, 240, 220));

//        myContainer.setLayout(new TableWrapLayout());
        myContainer.setLayout(new GridLayout(1,false));
      
        
        Composite headComp = new Composite(myContainer,SWT.BORDER);
//        headComp.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
        headComp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
        headComp.setBackground(new Color(Display.getCurrent(), 240, 240, 220));
//        headComp.setLayout(new FillLayout(SWT.HORIZONTAL));

        TableWrapLayout twl = new TableWrapLayout();
        twl.numColumns=7;
        headComp.setLayout(twl);
        Label l = new Label(headComp,SWT.NONE);
        l.setBackground(new Color(Display.getCurrent(), 240, 240, 220));
        l.setText("Server: ");
        l.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.MIDDLE));
        selector = new ComboViewer(headComp);
        selector.getCombo().setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
//        TableWrapData tw = new TableWrapData(TableWrapData.LEFT,TableWrapData.TOP);
        ArrayList arr = NavajoScriptPluginPlugin.getDefault().getServerEntries();
        for (int i = 0; i < arr.size(); i++) {
            selector.add(arr.get(i));
        }
        
        selector.addSelectionChangedListener(new ISelectionChangedListener(){

            public void selectionChanged(SelectionChangedEvent event) {
                serverChanged();
            }});
        
        myService = new TextViewer(headComp,SWT.SINGLE | SWT.BORDER);
        myService.getTextWidget().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB));
        myService.setDocument(new Document());
        final ContentAssistant assistant = new ContentAssistant();
        assistant.setContentAssistProcessor(new ScriptContentAssist(), IDocument.DEFAULT_CONTENT_TYPE);
        assistant.install(myService);        
        
        myService.getControl().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
             switch(e.keyCode)
            {
            case ' ':
                if ((e.stateMask | SWT.CTRL) != 0) {
                    assistant.showPossibleCompletions();
                }
                break;
            case '\n':
                go();
            break;
            default:
//            ignore everything else
            }
            }
            });        
        goButton = new Button(headComp,SWT.PUSH);
        goButton.setText("Go!");
        goButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        goButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                go();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});
        
        
        backButton = new Button(headComp,SWT.PUSH);
        backButton.setText("<");
        backButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        backButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                back();

            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});
        forwardButton = new Button(headComp,SWT.PUSH);
        forwardButton.setText(">");
        forwardButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        forwardButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                forward();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});
        reloadButton = new Button(headComp,SWT.PUSH);
        reloadButton.setText("Reload");
        reloadButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        reloadButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                reload();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});

        
        forwardButton.setEnabled(false);
        backButton.setEnabled(false);
        reloadButton.setEnabled(false);
        
        formComposite = new TmlFormComposite(null, myContainer);
        formComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
        formComposite.addNavajoScriptListener(this);
        NavajoScriptPluginPlugin.getDefault().addServerEntryListener(this);
        //        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB);
//        td.grabHorizontal = true;
//        td.grabVertical = true;
//        formComposite.setLayoutData(td);
        }

    protected void reload() {
        if (currentService==null) {
            return;
        }
        IStructuredSelection iss = (IStructuredSelection)selector.getSelection();
        if (iss.isEmpty()) {
            return;
        }
        final ServerEntry se = (ServerEntry)iss.getFirstElement();
        if (currentService.equals(lastInit)) {
            // init function;
            myService.getTextWidget().setText(lastInit);
            go();
        } else {
            if (historyList.size()>1) {
                // top = current (size-1) , item before is calling script
                final String source = (String)historyList.get(historyList.size()-2);
                final Navajo sourceNavajo = (Navajo)scriptMap.get(source);
                if (sourceNavajo==null) {
                    return;
                }
                Job j = new Job("Running "+currentService+" on "+se.getServer()){

                    protected IStatus run(IProgressMonitor monitor) {
                        try {
                            myCurrentNavajo = se.runProcess(currentService,sourceNavajo);
                            setNavajo(myCurrentNavajo, currentService);
                                    return Status.OK_STATUS;
                        } catch (ClientException e) {
                            e.printStackTrace();
                            return Status.OK_STATUS;
                        }
                    }};
                    j.schedule();

            } else {
                System.err.println("No init but nothing in history?!");
            }
        }
    }

    public void dispose() {
        formComposite.removeNavajoScriptListener(this);
        NavajoScriptPluginPlugin.getDefault().removeServerEntryListener(this);
        super.dispose();
    }
    protected void back() {
         if (historyList.isEmpty()) {
             System.err.println("NO HISTORY?!");
            return;
         }
         System.err.println(">>> "+historyList);
       String current = (String)historyList.pop();
        String nn = (String)historyList.pop();
        System.err.println("BACKING TO: "+nn+" current: "+currentService);
         System.err.println(">>> "+historyList);
//         while (nn.equals(currentService) && !historyList.isEmpty()) {
//            nn = (String)historyList.pop();
//        }
//         if (nn.equals(currentService) && historyList.isEmpty()) {
//             System.err.println("Could not find anything decent on the historystack");
//             return;
//        }
         Navajo n = (Navajo)scriptMap.get(nn);
         if (n!=null) {
//            scriptMap.put(current, myCurrentNavajo);
            setNavajo(n, nn);
            futureList.push(current);
        } else {
            System.err.println("ERROR GETTIN' HISTORY");
        }
         updateNavigationButtons();
    }

    protected void forward() {
        if (futureList.isEmpty()) {
            return;
        }
        System.err.println("FUTURELIST: "+futureList);
        String nn = (String)futureList.pop();
        System.err.println("IN FORWARD: going to script: "+nn);
        Navajo n = (Navajo)scriptMap.get(nn);
        if (n!=null) {
            historyList.push(currentService);
//           scriptMap.put(currentService, myCurrentNavajo);
           setNavajo(n, nn);
                  }
        updateNavigationButtons();
    }

    protected void serverChanged() {
        IStructuredSelection iss = (IStructuredSelection)selector.getSelection();
        if (iss.isEmpty()) {
            return;
        }
        final ServerEntry se = (ServerEntry)iss.getFirstElement();
        formComposite.setServerEntry(se);
    }

    protected void go() {
        IStructuredSelection iss = (IStructuredSelection)selector.getSelection();
        if (iss.isEmpty()) {
            return;
        }
        final ServerEntry se = (ServerEntry)iss.getFirstElement();
         final String script = myService.getTextWidget().getText();
         lastInit = script;
        Job j = new Job("Running "+script+" on "+se.getServer()){

            protected IStatus run(IProgressMonitor monitor) {
                try {
                    myCurrentNavajo = se.runInit(script);
                    setNavajo(myCurrentNavajo, script);
                    if (myCurrentNavajo.getMessage("error")==null && myCurrentNavajo.getMessage("AuthenticationError")==null) {
                        NavajoScriptPluginPlugin.getDefault().addToScriptInvocations(script);
                    }
                    return Status.OK_STATUS;
                } catch (ClientException e) {
                    e.printStackTrace();
                    return Status.OK_STATUS;
                }
            }};
            j.schedule();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub
    }

    public void setNavajo(final Navajo n, final String scriptName) {
         final Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            public void run() {
                currentService = scriptName;
                System.err.println("Setting NAVAJO TO SCRIPT: "+currentService);
                   myCurrentNavajo = n;
                if (formComposite != null) {
                    currentService = scriptName;
                    formComposite.setNavajo(n,null,scriptName);
                   myContainer.layout();
                    formComposite.reflow();
                } else {
                    System.err.println("hmmm. No formComposite");
                }
            }
        });

    }

    public void callingScript(String name) {
        // TODO Auto-generated method stub
        futureList.clear();
//        currentService = name;
        System.err.println("\n\n CALLING: "+name+"\n\n");
    }

    public void gotoScript(String name, Navajo n) {
        System.err.println("GOING TO: "+name);
        currentService = name;
//        if (name.equals(currentService)) {
//            return;
//        }
        scriptMap.put(name, n);
        if (historyList.isEmpty()) {
            historyList.push(name);
            return;
        }
        String last = (String)historyList.peek();
        if (name.equals(last)) {
            return;
        }
        historyList.push(name);
        updateNavigationButtons();
    }

    private void updateNavigationButtons() {
       forwardButton.setEnabled(!futureList.isEmpty());        
       backButton.setEnabled(historyList.size()>1);
       reloadButton.setEnabled(myCurrentNavajo!=null);
    }

    public void serverEntryChanged(int index) {
//        formComposite.dispose();
//        formComposite = null;
//        myContainer.dispose();
//        myContainer = null;
//        createPartControl(myParent);
//        myParent.layout();
//        myParent.redraw();
        //        ArrayList arr = NavajoScriptPluginPlugin.getDefault().getServerEntries();
//        System.err.println("Entry changed. # of entries: "+arr.size());
//        //        selector.getContentProvider().
//        for (int i = 0; i < arr.size(); i++) {
//            selector.add(arr.get(i));
//        }
//        selector.refresh();
    }

}
