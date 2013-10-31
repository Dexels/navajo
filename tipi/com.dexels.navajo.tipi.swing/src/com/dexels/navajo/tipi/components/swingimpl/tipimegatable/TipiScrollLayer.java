package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import java.awt.BorderLayout;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiScrollLayer extends TipiTableBaseLayer {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiScrollLayer.class);
	
	private int direction = BoxLayout.Y_AXIS;
	private boolean scroll = false;
	private Font titleFont = null;
	private String titleFontString = null;

	public TipiScrollLayer(TipiMegaTable tmt) {
		super(tmt);
	}

	@Override
	public void loadLayer(XMLElement elt) {
		super.loadLayer(elt);
		/**
		 * @todo Implement this
		 *       com.dexels.navajo.tipi.components.swingimpl.tipimegatable
		 *       .TipiMegaTableLayer abstract method
		 */
		messagePath = elt.getStringAttribute("messagePath");
		String direc = elt.getStringAttribute("direction");
		if ("horizontal".equals(direc)) {
			direction = BoxLayout.X_AXIS;
		}
		if ("vertical".equals(direc)) {
			direction = BoxLayout.Y_AXIS;
		}
		titleFontString = elt.getStringAttribute("titleFont");
		if (titleFontString != null) {
			try {
				titleFont = (Font) myTable.evaluateExpression(titleFontString);
			} catch (Exception ex) {
				logger.error("Error detected",ex);
			}
		}
		scroll = elt.getBooleanAttribute("scroll", "true", "false", false);
	}

	@Override
	public void loadData(final Navajo n, Message current,
			Stack<TipiTableBaseLayer> layerStack, final JComponent currentPanel) {
		Message nextMessage = null;
		if (current == null) {
			nextMessage = n.getMessage(messagePath);
		} else {
			nextMessage = current.getMessage(messagePath);
		}
		// logger.debug("scroll. Loading with nextMessage: "+nextMessage.getName()+" type: "+nextMessage.getType());
		// logger.debug("My messagePatH: "+messagePath);
		if (layerStack.isEmpty()) {
			return;
		}
		final Stack<TipiTableBaseLayer> newStack = new Stack<TipiTableBaseLayer>();
		newStack.addAll(layerStack);
		final TipiTableBaseLayer nextLayer = newStack.pop();
		final Message msg = nextMessage;
		Runnable rr = new Runnable() {

			@Override
			public void run() {
				JPanel jt = new JPanel();
				BoxLayout myLayout = new BoxLayout(jt, direction);
				jt.setLayout(myLayout);
				if (scroll) {
					JScrollPane jj = new JScrollPane(jt);
					currentPanel.add(jj, BorderLayout.CENTER);
				} else {
					currentPanel.add(jt, BorderLayout.CENTER);
				}
				for (int i = 0; i < msg.getArraySize(); i++) {
					Message cc = msg.getMessage(i);
					String title = null;
					Operand titleOperand = myTable.getContext().evaluate(
							titleProperty, myTable, null, cc.getRootDoc(), cc);
					if (titleOperand == null) {
						Property titleProp = cc.getProperty(titleProperty);
						title = "";
						if (titleProp != null) {
							title = titleProp.getValue();
						}
					} else {
						title = "" + titleOperand.value;
					}
					// if (titleProp!=null) {
					// logger.debug("*********\nDEPRECATED: You used only a propertyname as title in your scroll layer, in TipiMegaTabel\nYou should just use an expression..\n********");
					// title = titleProp.getValue();
					// } else {
					// if (titleOperand!=null) {
					// title = ""+titleOperand.value;
					// }
					// }

					JPanel newPanel = new JPanel();
					newPanel.setLayout(new BorderLayout());
					if (titleFont != null) {
						newPanel.setBorder(BorderFactory.createTitledBorder(
								newPanel.getBorder(), title, 1, 1, titleFont));
					} else {
						newPanel.setBorder(BorderFactory
								.createTitledBorder(title));
					}
					jt.add(newPanel);
					nextLayer.loadData(n, cc, newStack, newPanel);
				}
			}
		};

		if (SwingUtilities.isEventDispatchThread()) {
			rr.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(rr);
			} catch (InterruptedException e) {
				logger.error("Error detected",e);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				logger.error("Error detected",e);
			}
		}
	}

	@Override
	public XMLElement store() {
		XMLElement newElt = super.store();
		newElt.setAttribute("type", "scroll");
		switch (direction) {
		case BoxLayout.X_AXIS:
			newElt.setAttribute("direction", "horizontal");
			break;
		case BoxLayout.Y_AXIS:
			newElt.setAttribute("direction", "vertical");
			break;
		}
		if (titleFontString != null) {
			newElt.setAttribute("titleFont", titleFontString);
		}
		newElt.setAttribute("scroll", scroll ? "true" : "false");
		return newElt;
	}

	@Override
	public int getCurrentSelection() {

		return -1;
	}

	@Override
	public void setCurrentSelection(int s) {

	}
}
