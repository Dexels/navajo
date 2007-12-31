/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.embed.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiEmbedComponent extends TipiPanel {

	private static final String TIPLET_PROPERTY_PATH = null;
	private static final String NAVAJO_MESSAGE_PATH = null;
	private static final String NAVAJO_PROPERTY_NAME = null;
	private static final String NAVAJONAME_PROPERTY_NAME = null;
	protected TipiStandaloneContainer stc = null;

	// public void loadDefinition(String tipiPath, String definitionName, String
	// resourceBaseDirectory) throws IOException, TipiException;
	// public void loadClassPathLib(String location);
	// public TipiContext getContext();
	// public void shutDownTipi();

	// String tipiCodeBase = (String) properties.get("tipiCodeBase");
	// String resourceCodeBase = (String) properties.get("resourceCodeBase");
	// setTipiResourceLoader(tipiCodeBase);
	// setGenericResourceLoader(resourceCodeBase);

	public TipiEmbedComponent() {
		// jf.getContentPane().add((Container)stc.getContext().getTopLevel(),BorderLayout.CENTER);
	}

	public void loadData(Navajo n, String method) throws TipiException {
		if (isTipletService(n)) {
			try {
				Binary b = (Binary) n.getProperty(TIPLET_PROPERTY_PATH).getTypedValue();

				ZipResourceLoader zr = new ZipResourceLoader(b);
				stc.getContext().setTipiResourceLoader(new TmlResourceLoader(zr, "tipi/"));
				stc.getContext().setGenericResourceLoader(new TmlResourceLoader(zr, "resource/"));
				parseLocation("start.xml");
				switchToDefinition("init");
				Message m = n.getMessage(NAVAJO_MESSAGE_PATH);
				ArrayList al = m.getAllMessages();
				for (int i = 0; i < al.size(); i++) {
					Message element = (Message) al.get(i);
					parseNavajoMessage(element);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void parseNavajoMessage(Message element) {
		Property navajoBinary = element.getProperty(NAVAJO_PROPERTY_NAME);
		Property navajoName = element.getProperty(NAVAJONAME_PROPERTY_NAME);
		Binary bb = (Binary) navajoBinary.getTypedValue();
		Navajo embedded = NavajoFactory.getInstance().createNavajo(bb.getDataAsStream());
		try {
			stc.getContext().loadNavajo(embedded, navajoName.getValue());
		} catch (TipiBreakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isTipletService(Navajo n) {
		return false;
	}

	public Object createContainer() {
		
		final JWindow panel;
		panel = new JWindow();
			panel.setLayout(new BorderLayout());
		panel.setVisible(true);
		panel.setSize(500, 300);
		stc = new TipiStandaloneContainer();
		((SwingTipiContext)stc.getContext()).setOtherRoot(panel);
		
		panel.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent arg0) {
				disposeComponent();
				stc.shutDownTipi();
				getContext().disposeTipiComponent(TipiEmbedComponent.this);	
			}

		});
				
				
//				new InternalFrameAdapter(){
//			public void internalFrameClosing(InternalFrameEvent arg0) {
//				disposeComponent();
//				stc.shutDownTipi();
//				getContext().disposeTipiComponent(TipiWindowEmbedComponent.this);
//			}
//
//		});
		stc.getContext().addShutdownListener(new ShutdownListener(){

			public void contextShutdown() {
				panel.setVisible(false);
				panel.dispose();
			}});
		//stc.setRootComponent(panel);
		return panel;	}

	@Override
	public void setComponentValue(String name, Object value) {

		if (name.equals("tipiCodeBase")) {
			try {
				stc.getContext().setTipiResourceLoader((String) value);
				// stc.getContext().setGenericResourceLoader((String) value);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (name.equals("resourceCodeBase")) {
			try {
				stc.getContext().setGenericResourceLoader((String) value);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
		if (name.equals("binaryCodeBase")) {
			Binary b = (Binary) value;
			ZipResourceLoader tr;
			try {
				tr = new ZipResourceLoader(b);
				stc.getContext().setTipiResourceLoader(new TmlResourceLoader(tr, "tipi/"));
				stc.getContext().setGenericResourceLoader(new TmlResourceLoader(tr, "resource/"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// if (name.equals("tipiCodeBase")) {
		// try {
		// System.err.println("Setting tipi: "+(String)value);
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// }
		// }
		super.setComponentValue(name, value);
	}

	protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
		if ("loadDefinition".equals(name)) {
			try {
				Operand oo = compMeth.getEvaluatedParameter("location", event);
				String loc = (String) oo.value;
				parseLocation(loc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("switch".equals(name)) {
			try {
				Operand oo = compMeth.getEvaluatedParameter("definition", event);
				String nameVal = (String) oo.value;

				switchToDefinition(nameVal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("addStartupProperty".equals(name)) {
			try {
				Operand nameOperand = compMeth.getEvaluatedParameter("propertyName", event);
				String nameVal = (String) nameOperand.value;
				Operand valueOperand = compMeth.getEvaluatedParameter("value", event);
				String vakueVal = (String) valueOperand.value;
				System.err.println("Adding: " + nameVal + " value: " + vakueVal);
				stc.getContext().setSystemPropertyLocal(nameVal, vakueVal);
				// ((Container) getContainer()).add((Component)
				// stc.getContext().getTopLevel(), BorderLayout.CENTER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("loadNavajo".equals(name)) {
			try {
				Operand navajoOperand = compMeth.getEvaluatedParameter("navajo", event);
				Navajo navajo = (Navajo) navajoOperand.value;
				Operand methodOperand = compMeth.getEvaluatedParameter("method", event);
				String method = (String) methodOperand.value;
				loadNavajo(navajo, method);
				// ((Container) getContainer()).add((Component)
				// stc.getContext().getTopLevel(), BorderLayout.CENTER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("loadAllNavajo".equals(name)) {
			try {
				Operand messageOperand = compMeth.getEvaluatedParameter("message", event);
				Message arrayMessage = (Message) messageOperand.value;
				ArrayList elements = arrayMessage.getAllMessages();
				for (int i = 0; i < elements.size(); i++) {
					Message current = (Message)elements.get(i);
					Property ob = current.getProperty("Navajo");
					Binary b = (Binary) ob.getTypedValue();
					Navajo n = NavajoFactory.getInstance().createNavajo(b.getDataAsStream());
					loadNavajo(n, n.getHeader().getRPCName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void switchToDefinition(String nameVal) throws TipiException {
		stc.getContext().setTopLevelContainer(getContainer());
		stc.getContext().switchToDefinition(nameVal);
	}

	private void parseLocation(String loc) throws IOException, TipiException {
		InputStream tipiResourceStream = stc.getContext().getTipiResourceStream(loc);
		stc.getContext().parseStream(tipiResourceStream);
	}

	private void loadNavajo(Navajo n, String method) throws IOException, TipiException {
		System.err.println("CURRENT NAV: "+method);
		try {
			n.write(System.err);
		} catch (NavajoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stc.getContext().loadNavajo(n.copy(), method);
		} catch (TipiBreakException e) {
			e.printStackTrace();
		}
	}

	public void disposeComponent() {
		super.disposeComponent();
		stc.shutDownTipi();
	}

}
