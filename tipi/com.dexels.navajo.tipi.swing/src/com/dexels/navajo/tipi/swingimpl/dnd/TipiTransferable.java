package com.dexels.navajo.tipi.swingimpl.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiTransferable implements Transferable {

	final DataFlavor myFlavor;
	final TipiDraggable source;
	final TipiTransferHandler myHandler;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTransferable.class);
	
	public TipiTransferable(TipiDraggable source, TipiTransferHandler handler) {
		myHandler = handler;
		myFlavor = getFlavor();

		logger.debug("Created flava: " + myFlavor);
		this.source = source;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {

		return source;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
//		DataFlavor[] parentFla = null;
		if (myHandler.getParentHandler() != null) {
			// parentFla = myHandler.getParentHandler().
		}
		return new DataFlavor[] { myFlavor };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		logger.debug("Chickin!");
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
