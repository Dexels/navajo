package com.dexels.navajo.dnd;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.dexels.navajo.document.types.Binary;

public class BinaryTransferHandler extends TransferHandler {

	private static final long serialVersionUID = -7126949648142176528L;
	private JComponent myBeanComponent = null;

	public BinaryTransferHandler() {
		super("binary");
	}

	public BinaryTransferHandler(JComponent beanComponent) {
		super("binary");
		myBeanComponent = beanComponent;
	}

	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action) {
		System.err.println("Here we go: " + comp);
		if (myBeanComponent != null) {
			comp = myBeanComponent;
		}
		super.exportAsDrag(comp, e, action);
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		System.err.println("All clear: " + source);
		if (myBeanComponent != null) {
			source = myBeanComponent;
		}

		super.exportDone(source, data, action);
	}

	@Override
	public void exportToClipboard(JComponent comp, Clipboard clip, int action)
			throws IllegalStateException {
		if (myBeanComponent != null) {
			comp = myBeanComponent;
		}
		super.exportToClipboard(comp, clip, action);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importData(TransferSupport support) {
		Transferable transferable = support.getTransferable();

		try {
			Binary b = null;
			// if(s.isFlavorJavaFileListType()) {

			List<File> data = (List<File>) transferable
					.getTransferData(DataFlavor.javaFileListFlavor);
			if (data == null || data.size() == 0) {
				System.err.println("Whoops!");
			}
			if (data.size() > 1) {
				return false;
			}
			File f = data.get(0);
			b = new Binary(f);
			JComponent comp = null;
			if (myBeanComponent != null) {
				comp = myBeanComponent;
			} else {
				comp = (JComponent) support.getComponent();
			}

			setBinaryToComponent(comp, b);
			return true;

		} catch (UnsupportedFlavorException e) {
			System.err.println("Try other flavor now:");
			Object o;
			try {
				o = support.getTransferable().getTransferData(
						DataFlavor.stringFlavor);
				System.err.println("Result: " + o);
				try {
					URL u = new URL((String) o);
					InputStream is = u.openStream();
					Binary b = new Binary(is, false);
					is.close();
					JComponent comp = null;
					if (myBeanComponent != null) {
						comp = myBeanComponent;
					} else {
						comp = (JComponent) support.getComponent();
					}

					setBinaryToComponent(comp, b);
					return true;
				} catch (MalformedURLException e1) {
					System.err.println("Not a URL, I guess");
				}

			} catch (UnsupportedFlavorException e1) {
				e1.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.importData(support);
	}

	private void setBinaryToComponent(Component component, Binary b) {
		try {
			Method m = component.getClass().getMethod("setBinaryValue",
					Binary.class);
			m.invoke(component, new Object[] { b });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		boolean found = false;
		for (int i = 0; i < transferFlavors.length; i++) {
			System.err.println("Primary: "
					+ transferFlavors[i].getRepresentationClass()
							.getCanonicalName() + " mim3: "
					+ transferFlavors[i].getMimeType());
			System.err.println(""
					+ i
					+ "/"
					+ transferFlavors.length
					+ "Class rep: "
					+ transferFlavors[i]
							.getDefaultRepresentationClassAsString());
			// transferFlavors[i].isMimeTypeEqual(mimeType)
			if (transferFlavors[i].isFlavorJavaFileListType()) {
				System.err.println("yes. " + i + "/" + transferFlavors.length
						+ "\n\n");
				found = true;
			}
			if (transferFlavors[i].isRepresentationClassInputStream()) {
				System.err.println("Byting!");
				found = true;
			}
			// if(transferFlavors[i].getDefaultRepresentationClassAsString())
		}
		return found;
	}

}
