package com.dexels.navajo.dnd;

import java.awt.Component;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;

public class BinaryTransferHandler extends TransferHandler {


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
		System.err.println("Here we go: "+comp);
		if(myBeanComponent!=null) {
			comp = myBeanComponent;
		}
		super.exportAsDrag(comp, e, action);
	}


	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		System.err.println("All clear: "+source);
		if(myBeanComponent!=null) {
			source = myBeanComponent;
		}
	
			super.exportDone(source, data, action);
	}


	@Override
	public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
		if(myBeanComponent!=null) {
			comp = myBeanComponent;
		}
		super.exportToClipboard(comp, clip, action);
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean importData(TransferSupport support) {
		Transferable transferable = support.getTransferable();
		try {
			List<File> data = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
			if(data.size()>1) {
				return false;
			}
			File f = data.get(0);
			Binary b = new Binary(f);
			JComponent comp = null;
			if(myBeanComponent!=null) {
				comp = myBeanComponent;
			} else {
				comp = (JComponent) support.getComponent();
			}

			setBinaryToComponent(comp,b);
			return true;
			
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.importData(support);
	}

	
	private void setBinaryToComponent(Component component, Binary b) {
		try {
			Method m = component.getClass().getMethod("setBinaryValue", Binary.class);
			m.invoke(component, new Object[]{b});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		boolean found = false;
		for (int i = 0; i < transferFlavors.length; i++) {
			System.err.println("Primary: "+transferFlavors[i].getMimeType());
			if(transferFlavors[i].isFlavorJavaFileListType()) {
				System.err.println("yes. "+i+"/"+transferFlavors.length);
				found = true;
			}
		}
		return found;
	}


	
}
