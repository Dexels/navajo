package com.dexels.navajo.tipi.swingimpl.dnd;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;

public class TipiDndManager implements TipiDraggable, TipiDroppable {

	private final TipiComponent myTipiComponent;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDndManager.class);
	
	public TipiDndManager(JComponent j, final TipiComponent ts) {
//		myComponent = j;
		myTipiComponent = ts;
		if (!(j instanceof TipiDndCapable)) {
			logger.debug("Not drag and drop capable");
			return;
		}

		j.setTransferHandler(new TipiTransferHandler(j.getTransferHandler()));
		j.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				JComponent c = (JComponent) e.getSource();
				if (c instanceof JTextComponent) {
					if (!e.isControlDown()) {
						return;
					}
				}
				List<String> dragCategories = (List<String>) ts
						.getValue("dragCategory");
				if (dragCategories != null && !dragCategories.isEmpty()) {
					try {
						try {
							ts.performTipiEvent("onDrag", null, true);
						} catch (TipiException e1) {
							logger.error("Error detected",e1);
						}
						logger.debug("Drag val: "
								+ myTipiComponent.getValue("dragValue"));
						TransferHandler th = c.getTransferHandler();
						th.exportAsDrag(c, e, TransferHandler.COPY);
					} catch (TipiBreakException e1) {
						logger.debug("Component did a break on drag!");
					}
				}
			}
		});

	}

	@Override
	public List<String> getDragCategory() {
		List<String> dragCategory = (List<String>) myTipiComponent
				.getValue("dragCategory");
		return dragCategory;
	}

	@Override
	public Object getDragValue() {
		return myTipiComponent.getValue("dragValue");
	}

	@Override
	public boolean acceptsDropCategory(List<String> category) {
		List<String> dropCategory = (List<String>) myTipiComponent
				.getValue("dropCategory");
		String negotiated = negotiateCategory(category, dropCategory);
		return negotiated != null;
	}

	public String negotiateCategory(List<String> drags, List<String> drops) {
		if (drags == null || drops == null) {
			return null;
		}
		for (String string : drops) {
			if (drags.contains(string)) {
				return string;
			}
		}
		return null;
	}

	@Override
	public void fireDropEvent(Object o) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("value", o);
		try {
			logger.debug("Value: " + o);
			myTipiComponent.performTipiEvent("onDrop", params, true);
		} catch (TipiBreakException e) {
			logger.error("Error detected",e);
		} catch (TipiException e) {
			logger.error("Error detected",e);
		}
	}

	public boolean isDraggable() {
		return getDragValue() != null && !"".equals(getDragValue())
				&& !"".equals(getDragCategory());
	}

}
