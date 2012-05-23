package com.dexels.navajo.plugin.workflow.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;

public class WorkflowElement extends WorkflowModelElement {
	/** Property ID to use when a child is added to this diagram. */
	public static final String CHILD_ADDED_PROP = "ShapesDiagram.ChildAdded";
	/** Property ID to use when a child is removed from this diagram. */
	public static final String CHILD_REMOVED_PROP = "ShapesDiagram.ChildRemoved";
	private List<StateElement> shapes = new ArrayList<StateElement>();
	private Map<String,StateElement> shapeMap = new HashMap<String,StateElement>();

	public void load(InputStream is) throws XMLParseException, IOException {
		InputStreamReader isr = new InputStreamReader(is);
		XMLElement e = new CaseSensitiveXMLElement();
		e.parseFromReader(isr);
		load(e);
	}

	private void load(XMLElement e) {
		Vector<XMLElement> states = e.getChildren();
		for (int i = 0; i < states.size(); i++) {
			XMLElement state = states.get(i);
			StateElement s = new StateElement();
			s.setRoot(this);
			s.load(state);
			addChild(s);
		}

		StateElement nullState = new StateElement();
		nullState.setId("null");
		nullState.setRoot(this);
		nullState.load(null);
		addChild(nullState);

		List<StateElement> l = getChildren();
		for (int i = 0; i < l.size(); i++) {
			StateElement s =  l.get(i);
			s.loadState();
			Point old = s.getLocation();
			Point locationForIndex = getLocationForIndex(i, l.size());
			s.setLocation(locationForIndex);
			s.firePropertyChange(StateElement.LOCATION_PROP, old,
					locationForIndex);
		}
	}

	/**
	 * Add a shape to this diagram.
	 * 
	 * @param s
	 *            a non-null shape instance
	 * @return true, if the shape was added, false otherwise
	 */
	public boolean addChild(StateElement s) {
		if (s != null && shapes.add(s)) {
			shapeMap.put(s.getId(), s);
			firePropertyChange(CHILD_ADDED_PROP, null, s);
			return true;
		}
		return false;
	}

	/**
	 * Return a List of Shapes in this diagram. The returned List should not be
	 * modified.
	 */
	public List<StateElement> getChildren() {
		return shapes;
	}

	/**
	 * Remove a shape from this diagram.
	 * 
	 * @param s
	 *            a non-null shape instance;
	 * @return true, if the shape was removed, false otherwise
	 */
	public boolean removeChild(StateElement s) {
		if (s != null && shapes.remove(s)) {
			firePropertyChange(CHILD_REMOVED_PROP, null, s);
			return true;
		}
		return false;
	}

	public StateElement getShape(String id) {
		return (StateElement) shapeMap.get(id);
	}

	public Point getLocationForIndex(int in, int total) {
		double xCenter = 250;
		double yCenter = 250;
		double xScale = 200;
		double yScale = 150;
		double frac = (double) in / (double) total;
		double radAngle = frac * Math.PI * 2;
		double x = xCenter + Math.cos(radAngle) * xScale;
		double y = yCenter + Math.sin(radAngle) * yScale;
		return new Point(x, y);
	}

	public void doSave(IFile f, IProgressMonitor ip) {
		XMLElement x = toXml();
		// x.write(System.err);
		System.err.println(x.toString());
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(x.toString()
					.getBytes("UTF-8"));
			f.setContents(bais, IResource.KEEP_HISTORY, ip);

		} catch (CoreException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private XMLElement toXml() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("workflow");
		for (int i = 0; i < shapes.size(); i++) {
			StateElement st = (StateElement) shapes.get(i);
			if (!"null".equals(st.getId())) {
				xe.addChild(st.toXml());
			}
		}
		return xe;
	}
}
