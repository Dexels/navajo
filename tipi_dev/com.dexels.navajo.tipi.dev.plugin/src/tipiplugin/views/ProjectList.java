package tipiplugin.views;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectList implements IContentProvider, IStructuredContentProvider, ILabelProvider {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ProjectList.class);
	private final List<IProject> projects;
	public ProjectList(List<IProject> p ) {
		projects = p;
	}
	
	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public Object[] getElements(Object inputElement) {
		logger.info("Getting elements");
		return projects.toArray();
	}

	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		if(element instanceof IProject) {
			IProject ip = (IProject)element;
			return ip.getName();
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

}
