package com.dexels.navajo.tipi.vaadin.components.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import metadata.FormatDescription;
import metadata.FormatIdentification;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.dexels.navajo.tipi.vaadin.components.io.BufferedInputStreamSource;
import com.dexels.navajo.tipi.vaadin.components.io.URLInputStreamSource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public abstract class TipiVaadinComponentImpl extends TipiDataComponentImpl {

	
	private static final long serialVersionUID = -304628775000480212L;
	protected ComponentContainer layoutComponent;
	private InputStream stream;
	private String lastMimeType;
	
	
	
	@Override
	public void setContainer(Object c) {
		super.setContainer(c);
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not remove non-vaadin component from component: "+c);
		}
		Component comp = getVaadinContainer();
		comp.setDebugId(getPath());
	}

	// TODO Deal with layout at some point, ignore for now
	@Override
	public void setContainerLayout(Object layout) {
		this.layoutComponent = (ComponentContainer) layout;
		super.setContainerLayout(layout);
		ComponentContainer oo = (ComponentContainer) getVaadinContainer();
		oo.addComponent(this.layoutComponent);
		this.layoutComponent.setSizeFull();
	}

	@Override
	public void removeFromContainer(Object c) {
//		super.removeFromContainer(c);
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not remove non-vaadin component from component: "+c);
		}
		if(this.layoutComponent!=null) {
			removeFromLayoutContainer((Component)c);
		}
		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			ComponentContainer cc = (ComponentContainer)comp;
			cc.removeComponent((Component) c);
		} else {
			throw new IllegalArgumentException("Can not remove n component from non-vaadin container: "+comp);
		}	}

	
	private void removeFromLayoutContainer(Component c) {
		this.layoutComponent.removeComponent(c);
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not add non-vaadin component to component: "+c);
		}
		if(this.layoutComponent!=null) {
			System.err.println("Layout detected, adding to layout...");
			addToLayoutContainer((Component)c,constraints);
			return;
		}
		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			addToVaadinContainer((ComponentContainer)comp, (Component)c, constraints);
		} else {
			throw new IllegalArgumentException("Can not add n component to non-vaadin container: "+comp);
		}
		
	}
	
	private void addToLayoutContainer(Component c, Object constraints) {
		getLayout().addToLayout(c, constraints);
//		this.layoutComponent.add
	}

	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		currentContainer.addComponent(component);
		
	}
	
	public StreamResource getResource(Object u) {
		System.err.println("Getting resource: "+u);
		//		String mimeType = null;
		if (u == null) {
			return null;
		}
		StreamSource is = null;
		if (u instanceof URL) {
			System.err.println("URL: " + u);
			if (getVaadinApplication().isRunningInGae()) {
				try {
					is = resolve((URL) u);
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else {
					is = new URLInputStreamSource((URL) u);
			}
		}
		if (u instanceof Binary) {
			lastMimeType = ((Binary)u).guessContentType();
		}
		if (is == null) {
			return null;
		}
		
		StreamResource sr = new StreamResource(is, ""+u, getVaadinApplication());
		
		
//		getVaadinApplication().getMainWindow().
		sr.setMIMEType(lastMimeType);
		System.err.println("Stream resource created: " + u.toString()+" mime: "+lastMimeType);

		return sr;
	}

	private StreamSource resolve(URL u) throws IOException {
		return new URLInputStreamSource(u);
	}
	private StreamSource resolveAndBuffer(URL u) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = u.openStream();
		copyResource(baos, is);
		byte[] byteArray = baos.toByteArray();
		this.lastMimeType = FormatIdentification.identify(byteArray).getMimeType();
		System.err.println("Bytes buffered: "+byteArray.length);
		return new BufferedInputStreamSource(byteArray);
	}
	
	protected final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			read = bin.read(buffer);
			if (read > -1) {
				bout.write(buffer, 0, read);
			}
			if (read <= -1) {
				ready = true;
			}
		}
		bin.close();
		bout.flush();
		bout.close();
	}
	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("style")) {
			getActualVaadinComponent().addStyleName(""+object);
		}
		if(name.equals("visible")) {
			Boolean b = (Boolean) object;
			getActualVaadinComponent().setVisible(b);
		}
		if(name.equals("enabled")) {
			Boolean b = (Boolean) object;
			getActualVaadinComponent().setEnabled(b);
		}

		super.setComponentValue(name, object);
	}
	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("style")) {
			return getActualVaadinComponent().getStyleName();
		}
		return super.getComponentValue(name);
	}
	
	public Component getVaadinContainer() {
		return (Component) getContainer();
	}

	public Component getActualVaadinComponent() {
		return (Component) getActualComponent();
	}

	public TipiVaadinApplication getVaadinApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getVaadinApplication();
	}
}
