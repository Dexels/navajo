package com.dexels.navajo.tipi.swingimpl.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TipiTransferable implements Transferable {

	final DataFlavor myFlavor;
	final TipiDraggable source;
	final TipiTransferHandler myHandler;

	public TipiTransferable(TipiDraggable source, TipiTransferHandler handler) {
		myHandler = handler;
		myFlavor = getFlavor();

		System.err.println("Created flava: " + myFlavor);
		this.source = source;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {

		return source;
	}

	public DataFlavor[] getTransferDataFlavors() {
//		DataFlavor[] parentFla = null;
		if (myHandler.getParentHandler() != null) {
			// parentFla = myHandler.getParentHandler().
		}
		return new DataFlavor[] { myFlavor };
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		System.err.println("Chickin!");
		if (flavor.equals(myFlavor)) {
			return true;
		}
		return false;
	}

	public static DataFlavor getFlavor() {
		return new DataFlavor(TipiDraggable.class,
				DataFlavor.javaJVMLocalObjectMimeType);
		// return new
		// ActivationDataFlavor(TipiDraggable.class,DataFlavor.javaJVMLocalObjectMimeType,DataFlavor.javaJVMLocalObjectMimeType);
	}
}
