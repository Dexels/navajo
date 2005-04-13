package com.dexels.navajo.studio.script.plugin.propertypages;

import java.io.*;
import java.util.*;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.internal.core.search.processing.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;

public class NavajoStudioPropertyPage extends PropertyPage implements IWorkbenchPropertyPage {

	private static final String PATH_TITLE = "Path:";
	private static final String OWNER_TITLE = "&Owner:";
	private static final String OWNER_PROPERTY = "OWNER";
	private static final String DEFAULT_OWNER = "John Doe";

	private static final int TEXT_FIELD_WIDTH = 50;

	private Text ownerText;

	private ArrayList repositories = new ArrayList();
    private IProject myProject;
    private ComboViewer repositorySelector;
    private Navajo myServerNavajo;
    private Property repositoryProperty;
    private SearchResultContentProvider searchProvider;
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public NavajoStudioPropertyPage() {
		super();
//		System.err.println(">>>>>>>>>>> CREATING PROPERTYPAGE");
//		org.eclipse.gef.
//		s.createJavaSearchScope(null);
//		ISelection is = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getSelection();
//		if (!(is instanceof IStructuredSelection)) {
//            return;
//        }
//		
//		IStructuredSelection iss = (IStructuredSelection)is;

		myProject = NavajoScriptPluginPlugin.getDefault().getCurrentProject();
		
		new Job("Looking for repositories"){

            protected IStatus run(IProgressMonitor monitor) {
                loadRepositories();
                IFile myServerXml = NavajoScriptPluginPlugin.getDefault().getServerXml(myProject);
                try {
                    InputStream serverIn = myServerXml.getContents();
                    myServerNavajo = NavajoFactory.getInstance().createNavajo(serverIn);
                    serverIn.close();
                    System.err.println("NAVAJO:");
                    myServerNavajo.write(System.err);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                repositoryProperty = myServerNavajo.getProperty("server-configuration/repository/class");
                
                System.err.println("PROP: "+repositoryProperty.getValue());
                Display.getDefault().syncExec(new Runnable() {

                    public void run() {
                        repositorySelector.setInput(repositories);
                        int index = searchProvider.getIndexOfLabel(repositoryProperty.getValue());
                        System.err.println("Index::: "+index);
                        if (index>0) {
                            repositorySelector.getCombo().select(index);
                        }
                    }});
              return Status.OK_STATUS;
           
            }
		}.schedule();

	}

	private void loadRepositories() {
        try {
            repositories = NavajoScriptPluginPlugin.getDefault().searchForImplementingClasses(myProject, NavajoScriptPluginPlugin.NAVAJO_REPOSITORY_INTERFACE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
	}
	
	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		//Label for path field
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(PATH_TITLE);

		repositorySelector = new ComboViewer(composite);
		searchProvider = new SearchResultContentProvider();
        repositorySelector.getCombo().setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.HORIZONTAL_ALIGN_BEGINNING,true,false));

		repositorySelector.setContentProvider(searchProvider);
        repositorySelector.setLabelProvider(searchProvider);

 
        composite.layout(true);
         // Path text field
//		Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
//		pathValueText.setText(((IResource) getElement()).getFullPath().toString());
//		Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().addSelectionListener(new ISelectionListener(){
//
//            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
//                // TODO Auto-generated method stub
//                if (selection instanceof IStructuredSelection) {
//                    IStructuredSelection iss = (IStructuredSelection)selection;
//                    Object first = iss.getFirstElement();
//                    if (first!=null) {
//                        System.err.println("Found: "+first.getClass()+" >> "+first.toString());
//                    } else {
//                        System.err.println("Null selection found");
//                    }
//
//                } else {
//                    System.err.println("No structured selection found!");
//                }
//            }});
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addSecondSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		// Label for owner field
		Label ownerLabel = new Label(composite, SWT.NONE);
		ownerLabel.setText(OWNER_TITLE);

		// Owner text field
		ownerText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		ownerText.setLayoutData(gd);

		// Populate owner text field
		try {
			String owner =
				((IResource) getElement()).getPersistentProperty(
					new QualifiedName("", OWNER_PROPERTY));
			ownerText.setText((owner != null) ? owner : DEFAULT_OWNER);
		} catch (CoreException e) {
			ownerText.setText(DEFAULT_OWNER);
		}
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		addSeparator(composite);
		addSecondSection(composite);
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		// Populate the owner text field with the default value
		ownerText.setText(DEFAULT_OWNER);
	}
	
	public boolean performOk() {
		// store the value in the owner text field
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", OWNER_PROPERTY),
				ownerText.getText());
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}