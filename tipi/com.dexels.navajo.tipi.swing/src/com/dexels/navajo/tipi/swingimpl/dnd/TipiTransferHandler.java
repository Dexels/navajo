package com.dexels.navajo.tipi.swingimpl.dnd;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiTransferHandler extends TransferHandler {

	private static final long serialVersionUID = -7253771780427276069L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTransferHandler.class);
	private final TransferHandler myParent;

	public TipiTransferHandler(TransferHandler parent) {
		myParent = parent;

	}

	public TipiTransferHandler() {
		myParent = null;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {

		logger.debug("TRAAAAAAAAANS: " + c);
		if (c instanceof TipiDndCapable) {
			TipiDndCapable tdc = (TipiDndCapable) c;
			TipiDndManager manager = tdc.getDndManager();
			if (manager.isDraggable()) {
				return new TipiTransferable(manager, this);
			}
		}
		return super.createTransferable(c);
	}

	@Override
	public boolean canImport(TransferSupport support) {
		if (!(support.getComponent() instanceof TipiDndCapable)) {
			if (super.canImport(support)) {
				return true;
			} else {
				if (myParent != null) {
					return myParent.canImport(support);
				}
			}
		}
		TipiDndManager manager = ((TipiDndCapable) support.getComponent())
				.getDndManager();

		Transferable transferable = support.getTransferable();
		try {
			TipiDraggable o = (TipiDraggable) transferable
					.getTransferData(TipiTransferable.getFlavor());
			TipiDroppable td = manager;
			boolean b = td.acceptsDropCategory(o.getDragCategory());
			if (!b) {
				return super.canImport(support);
			}
			return true;
		} catch (UnsupportedFlavorException e1) {
			// logger.error("Error detected",e1);
		} catch (IOException e1) {
			logger.error("Error detected",e1);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return super.canImport(support);
	}

	@Override
	public boolean importData(TransferSupport support) {
		Transferable transferable = support.getTransferable();
		logger.debug("Transferable: " + transferable);

		TipiDndManager managerForDropSide = ((TipiDndCapable) support
				.getComponent()).getDndManager();
		try {
			TipiDraggable o = (TipiDraggable) transferable
					.getTransferData(TipiTransferable.getFlavor());
			TipiDroppable td = managerForDropSide;
			boolean b = td.acceptsDropCategory(o.getDragCategory());
			if (!b) {
				if (super.importData(support)) {
					return true;
				} else {
					if (myParent != null) {
						return myParent.importData(support);
					}
				}

				return super.importData(support);
			}
			td.fireDropEvent(o.getDragValue());
			return true;
		} catch (UnsupportedFlavorException e1) {
			logger.error("Error detected",e1);
		} catch (IOException e1) {
			logger.error("Error detected",e1);
		}
		if (myParent != null) {
			return myParent.importData(support);
		}
		return super.importData(support);
	}

	public TransferHandler getParentHandler() {
		return myParent;
	}

}
