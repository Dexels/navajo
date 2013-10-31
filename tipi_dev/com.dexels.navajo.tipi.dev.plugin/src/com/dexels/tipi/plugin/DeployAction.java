package com.dexels.tipi.plugin;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.tipi.plugin.impl.Deployer;

public class DeployAction implements IObjectActionDelegate {

	
	private final static Logger logger = LoggerFactory
			.getLogger(DeployAction.class);
	
	private ISelection selection;
	private Combo templateCombo = null;

	private String selectedTemplate = null;
	private boolean includeJarsSelected;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void run(IAction action) {
		try {
		if (selection instanceof IStructuredSelection) {
			for (Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				final IProject rProject = project;
				if (rProject != null) {
					final String[] retrieveTemplates = deployments(rProject); // new

					includeJarsSelected = true;
					
					if(retrieveTemplates.length != 0) {
						Dialog dlg = new Dialog(Display.getCurrent().getActiveShell()) {
							@Override
							public Composite createDialogArea(Composite parent) {
								Composite composite = (Composite) super.createDialogArea(parent);
								composite.setLayout(new GridLayout(2, false));
								new Label(composite,SWT.NONE).setText("Select deployment configuration");
								
								templateCombo = new Combo(composite, SWT.READ_ONLY);
								templateCombo.setItems(retrieveTemplates);
								templateCombo.setSize(200, 20);
								templateCombo.addSelectionListener(new SelectionListener() {
									@Override
									public void widgetDefaultSelected(SelectionEvent e) {
									}

									@Override
									public void widgetSelected(SelectionEvent e) {
										selectedTemplate = templateCombo.getItems()[templateCombo.getSelectionIndex()];
									}
								});
								selectedTemplate = retrieveTemplates[0];
								templateCombo.select(0);
								new Label(composite,SWT.NONE).setText("Include JARS in deploy?");

								final Button includeJars = new Button(composite,SWT.CHECK);
//								includeJars.setText("Include JARS in deploy?");
							   includeJars.setSelection(true);
								includeJars.addSelectionListener(new SelectionListener(){

									@Override
									public void widgetDefaultSelected(SelectionEvent arg0) {
										
									}

									@Override
									public void widgetSelected(SelectionEvent arg0) {
										includeJarsSelected = includeJars.getSelection();
										
									}});
								
								return composite;

							}
						};
//						dlg.setTitle("Additional deployment parameters needed.");
						if (dlg.open() == Window.OK) {
						} else {
							return;
						}
					   boolean b = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Confirmation:", "Deploying project: "+rProject.getName()+" to "+selectedTemplate+". Are you sure?");

					   if(!b) {
					   	 MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Wimp!", "You are not a brave navajo!");
					   	return;
					   }
					}
					InputDialog logindlg = new InputDialog(Display.getCurrent().getActiveShell(), "SSH Login",
							"Deployment ssh password", "", null);
					if (logindlg.open() != Window.OK) {
						return;
					}
					String password = logindlg.getValue();

					Deployer d = new Deployer();
					d.deployProject(project, selectedTemplate, "navajo", password, includeJarsSelected);
				}
			}
		}
		}catch (Throwable e) {
			logger.error("Error: ",e);
		}
	}

	private String[] deployments(IProject p) {
		List<String> result = new LinkedList<String>();
		IFolder settings = p.getFolder("settings");
		IFolder deploys = settings.getFolder("deployments");
		if (deploys == null || !deploys.exists()) {
			logger.info("whoops");
			return new String[] {};
		}
		IResource[] res;
		try {
			res = deploys.members();
			for (IResource resource : res) {
				if (resource instanceof IFile) {
					String name = resource.getName();
					if(name.endsWith(".properties")) {
						String trunk = name.substring(0,name.length() - ".properties".length());
						result.add(trunk);
						
					}
				}
			}
		} catch (CoreException e) {
			logger.error("Error: ",e);
		}
		
	
		String[] resultArray = new String[result.size()];
		                             
		int i=0;
		for (String string : result) {
			resultArray[i++] = string;
		}
		return resultArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action
	 * .IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
