package tipiplugin.views;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.tipi.plugin.TipiNature;

public class TipiHelpView extends ViewPart {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiHelpView.class);
	private long lastUpdate = -1;
	private ViewContentProvider treeProvider;
	private TreeViewer tv;
	//private ComboViewer combo;

	private final List<IProject> myProjects = new ArrayList<IProject>();
	private IProject project;
	private Combo comboBox;
	
	public IProject getProject() {
		return project;
	}
	public void setProject(IProject project) {
		this.project = project;
	}
	
	public void switchToProject(String projectName) {
//		logger.info("Switching to project: "+projectName);
		IProject p =  ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if(p==null) {
			//huh?
			return;	
		}
		String current = comboBox.getText();
		if(projectName.equals(current)) {
			// don't update the combobox
		} else {
			int index = comboBox.indexOf(projectName);
			comboBox.select(index);
		}
		setProject(p);
		treeProvider.initialize(project);
		tv.expandAll();
	}
	
	public void listProjects() {
		myProjects.clear();
		IProject[] pp = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		comboBox.removeAll();
		for (int i = 0; i < pp.length; i++) {
			try {
				if(pp[i].isOpen()) {
				if(pp[i].hasNature(TipiNature.NATURE_ID)) {
					myProjects.add(pp[i]);
					comboBox.add(pp[i].getName());
				}
				}
			} catch (CoreException e) {
				logger.error("Error: ",e);
			}
		}
//		ProjectList pr = new ProjectList(myProjects);

		//		combo.setContentProvider(pr);
//		combo.setLabelProvider(pr);
//		pr.inputChanged(combo, oldInput, newInput)
	//	logger.info(">>>"+combo.getElementAt(0));
		tv.setInput(getViewSite());

	}
	
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2,false));
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = false;
		comboBox = new Combo(parent,SWT.READ_ONLY);
		comboBox.setLayoutData(data);
		comboBox.addSelectionListener(
				  new SelectionAdapter()
				  {
				    public void widgetSelected(SelectionEvent e)
				    {
				      switchToProject(comboBox.getText());
				    }
				  });

		tv = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		 data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = false;
		data.grabExcessVerticalSpace = true;
		data.widthHint = 180;
		tv.getTree().setLayoutData(data);
		treeProvider = new ViewContentProvider();
		tv.setContentProvider(treeProvider);
		tv.setLabelProvider(new ViewLabelProvider());
		tv.setInput(getViewSite());

		tv.expandAll();
		
		//combo.setContentProvider(new ProjectList());
		
		
		final Browser browser;
		browser = new Browser(parent, SWT.NONE);
		
		tv.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection is = (IStructuredSelection) event.getSelection();
				XMLElement xe = (XMLElement) is.getFirstElement();
				if(xe!=null) {
					String href = xe.getStringAttribute("href");
					if(href!=null) {
						browser.setUrl(href);
					}
				}
			}});
			try {
		} catch (SWTError e) {
			logger.info("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		browser.setLayoutData(data);
//		browser.setUrl("http://spiritus.dexels.nl:41766346/Tipi/");
		listProjects();
		logger.info("Proj: "+myProjects);
		parent.redraw();
	}
protected void checkupdate() {
		
	}

//	public void initialize() {
//		treeProvider.initialize(project);
//
//	}

/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		logger.info("Focus reseived");
	}

	class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
		private XMLElement invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
//				logger.info("AAAAAP");
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize(project);
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			
			if (child instanceof XMLElement) {
				return ((XMLElement) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof XMLElement) {
				Vector<XMLElement>xx = ((XMLElement) parent).getChildren();
				return xx.toArray();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof XMLElement)
				return ((XMLElement) parent).getChildren().size()>0;
			return false;
		}


		public void initialize(IProject ip) {
			if(ip==null) {
				return;
			}
			IFile metadata = ip.getFile(".tipiproject/tipi.metadata");
			long localTimeStamp = metadata.getLocalTimeStamp();
			if(invisibleRoot != null && (lastUpdate > localTimeStamp)) {
				logger.info("Still up to date!");
				return;
			}
			logger.info("Commencing rebuild: "+lastUpdate);
			logger.info("Commencing dv: "+(lastUpdate-localTimeStamp));
			lastUpdate = new Date().getTime();
			
			XMLElement xe = new CaseSensitiveXMLElement();
			try {
				InputStream is = metadata.getContents();
				xe.parseFromStream(is);
				is.close();
			} catch (CoreException e) {
				logger.error("Error: ",e);
				lastUpdate = -1;
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		//	logger.info("XE: "+xe);

			invisibleRoot = xe;
			tv.setContentProvider(treeProvider);
			tv.setInput(getViewSite());

		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			if(obj instanceof XMLElement) {
				XMLElement x = (XMLElement)obj;
				if(x.getName().equals("components")) {
					return x.getName();
				}
				if(x.getName().equals("actions")) {
					return x.getName();
				}
				if(x.getName().equals("types")) {
					return x.getName();
				}
				return x.getStringAttribute("name");
			}
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof XMLElement) {
				XMLElement xxx = (XMLElement)obj;
				if(xxx.getChildren().size()>0) {
					imageKey = ISharedImages.IMG_OBJ_FOLDER;
			
				}
			}
		
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}

}