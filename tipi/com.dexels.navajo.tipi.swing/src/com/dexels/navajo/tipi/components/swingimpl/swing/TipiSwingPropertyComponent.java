package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.swingclient.components.GenericPropertyComponent;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

public class TipiSwingPropertyComponent extends GenericPropertyComponent
		implements TipiDndCapable {

	private static final long serialVersionUID = 1703359870781330307L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingPropertyComponent.class);
	
	private static final String CAPITALIZATION = "capitalization";
	private static final String CHECKBOXGROUPCOLUMNCOUNT = "checkboxGroupColumnCount";
	private static final String HORIZONTALSCROLLS = "horizontalscrolls";
	private static final String LABELHALIGN = "labelHAlign";
	private static final String LABELINDENT = "labelIndent";
	private static final String LABELVALIGN = "labelVAlign";
	private static final String MAXIMAGEHEIGHT = "maxImageHeight";
	private static final String MAXIMAGEWIDTH = "maxImageWidth";
	private static final String MAXWIDTH = "maxWidth";
	private static final String MEMOCOLUMNCOUNT = "memoColumnCount";
	private static final String MEMOROWCOUNT = "memoRowCount";
	private static final String PROPERTYNAME = "propertyName";
	private static final String SELECTIONTYPE = "selectionType";
	private static final String SHOWDATEPICKER = "showDatePicker";
	private static final String SHOWLABEL = "showLabel";
	private static final String VERTICALSCROLLS = "verticalScrolls";
	private static final String FOCUSABLE = "focusable";
	private static final String VISIBLEROWCOUNT = "visibleRowCount";
	private String propertyName;
	private String labelHAlign;
	private String labelVAlign;
	private boolean alwaysUseLabel;
	private TipiComponent myComponent;
	private TipiSwingPropertyDescriptionLabel myLabel;
	private final TipiDndManager myTipiDndManager;
	private String requiredPostfix = null;

	public TipiSwingPropertyComponent(TipiComponent tc) {
		myComponent = tc;
		myTipiDndManager = new TipiDndManager(this, tc);
		setBackground(Color.red);
	}

	public boolean isAlwaysUseLabel() {
		return alwaysUseLabel;
	}

	@Override
	public void setAlwaysUseLabel(boolean alwaysUseLabel) {
		this.alwaysUseLabel = alwaysUseLabel;
	}

	@Override
	public String getCapitalization() {
		return super.getCapitalization();
	}

	@Override
	public void setCapitalization(String capitalization) {
		String old = super.getCapitalization();
		super.setCapitalization(capitalization);
		firePropertyChange(CAPITALIZATION, old, capitalization);
	}

	@Override
	public String getRequiredPostfix()
	{
		if (myComponent != null && requiredPostfix == null)
		{
			try {
				requiredPostfix = myComponent.getContext().getSystemProperty("requiredPostfix");
			} catch (SecurityException e) {

			}
		}
		else
		{
			requiredPostfix = super.getRequiredPostfix();
		}
		return requiredPostfix;
	}
	// public void setProperty(Property p) {
	@Override
	public void constructPropertyComponent(Property p) {

		String subtype = p.getSubType("tipitype");
		if (subtype == null) {
			super.constructPropertyComponent(p);
			return;
		}

		String[] types = Property.VALID_DATA_TYPES;
		for (int i = 0; i < types.length; i++) {
			if (subtype.equals(types[i])) {
				p.setType(subtype);
				super.constructPropertyComponent(p);
				return;
			}
		}
		if (subtype.equals("color")) {
//			TipiSwingColorButton tscb = new TipiSwingColorButton(myComponent);
//			tscb.setProperty(p);
//			addPropertyComponent(tscb);
		} else if (subtype.equals("paint")) {
//			TipiSwingPaintEditor tscb = new TipiSwingPaintEditor(myComponent);
//			tscb.setProperty(p);
//			addPropertyComponent(tscb);
		} else {
			Object oo = p.getTypedValue();
			if (oo != null) {
				addPropertyComponent(new JLabel("Huh: " + subtype + "=="
						+ oo.getClass()));
			} else {
				addPropertyComponent(new JLabel("Huh: " + subtype));

			}
			logger.debug("Strange type: " + subtype);
		}
		// strange type found:

	}

	@Override
	public void setCheckboxGroupColumnCount(int checkboxGroupColumnCount) {
		int old = getCheckboxGroupColumnCount();
		super.setCheckboxGroupColumnCount(checkboxGroupColumnCount);
		firePropertyChange(CHECKBOXGROUPCOLUMNCOUNT, old,
				checkboxGroupColumnCount);
	}

	public boolean isHorizontalScrolls() {
		return super.hasHorizontalScrolls();
	}

	@Override
	public void setHorizontalScrolls(boolean horizontalScrolls) {
		boolean old = super.hasHorizontalScrolls();
		super.setHorizontalScrolls(horizontalScrolls);
		firePropertyChange(HORIZONTALSCROLLS, old, horizontalScrolls);
	}

	public String getLabelHAlign() {
		return labelHAlign;
	}

	public void setLabelHAlign(String labelHAlign) {
		String old = getLabelHAlign();
		this.labelHAlign = labelHAlign;
		firePropertyChange(LABELHALIGN, old, labelHAlign);
	}

	@Override
	public int getLabelIndent() {
		return super.getLabelIndent();
	}

	@Override
	public void setLabelIndent(int labelIndent) {
		int old = getLabelIndent();
		super.setLabelIndent(labelIndent);
		firePropertyChange(LABELINDENT, old, labelIndent);
	}

	public String getLabelVAlign() {
		return labelVAlign;
	}

	public void setLabelVAlign(String labelVAlign) {
		String old = getLabelVAlign();
		this.labelVAlign = labelVAlign;
		if ("top".equals(labelVAlign)) {
			super.setVerticalLabelAlignment(SwingConstants.TOP);
		}
		if ("bottom".equals(labelVAlign)) {
			super.setVerticalLabelAlignment(SwingConstants.BOTTOM);
		}
		if ("center".equals(labelVAlign)) {
			super.setVerticalLabelAlignment(SwingConstants.CENTER);
		}
		firePropertyChange(LABELVALIGN, old, labelVAlign);
	}

	@Override
	public int getMaxImageHeight() {
		return super.getMaxImageHeight();
	}

	@Override
	public void setMaxImageHeight(int maxImageHeight) {
		int old = getMaxImageHeight();
		super.setMaxImageHeight(maxImageHeight);
		firePropertyChange(MAXIMAGEHEIGHT, old, maxImageHeight);
	}

	@Override
	public int getMaxImageWidth() {
		return super.getMaxImageWidth();
	}

	@Override
	public void setMaxImageWidth(int maxImageWidth) {
		int old = getMaxImageWidth();
		super.setMaxImageWidth(maxImageWidth);
		firePropertyChange(MAXIMAGEWIDTH, old, maxImageWidth);
	}

	@Override
	public int getMaxWidth() {
		return super.getMaxWidth();
	}

	@Override
	public void setMaxWidth(int maxWidth) {
		int old = getMaxWidth();
		super.setMaxWidth(maxWidth);
		firePropertyChange(MAXWIDTH, old, maxWidth);
	}

	@Override
	public int getMemoColumnCount() {
		return super.getMemoColumnCount();
	}

	@Override
	public void setMemoColumnCount(int memoColumnCount) {
		int old = getMemoColumnCount();
		super.setMemoColumnCount(memoColumnCount);
		firePropertyChange(MEMOCOLUMNCOUNT, old, memoColumnCount);
	}

	public int getMemoRowCount() {
		return super.getMemoColumnCount();
	}

	@Override
	public void setMemoRowCount(int memoRowCount) {
		int old = getMemoRowCount();
		super.setMemoRowCount(memoRowCount);
		firePropertyChange(MEMOROWCOUNT, old, memoRowCount);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyname) {
		String old = propertyName;
		this.propertyName = propertyname;
		firePropertyChange(PROPERTYNAME, old, propertyname);
	}

	@Override
	public String getSelectionType() {
		return super.getSelectionType();
	}

	@Override
	public void setSelectionType(String selectionType) {
		String old = getSelectionType();
		super.setSelectionType(selectionType);
		firePropertyChange(SELECTIONTYPE, old, selectionType);
	}

	public boolean isShowDatePicker() {
		return super.hasShowDatePicker();
	}

	@Override
	public void setShowDatePicker(boolean showDatePicker) {
		boolean old = isShowDatePicker();
		super.setShowDatePicker(showDatePicker);
		firePropertyChange(SHOWDATEPICKER, old, showDatePicker);
	}

	public void setShowLabel(boolean showLabel) {
		boolean old = isLabelVisible();
		setLabelVisible(showLabel);
		firePropertyChange(SHOWLABEL, old, showLabel);
	}

	@Override
	public void setVerticalScrolls(boolean verticalScrolls) {
		boolean old = hasVerticalScrolls();
		super.setVerticalScrolls(verticalScrolls);
		firePropertyChange(VERTICALSCROLLS, old, verticalScrolls);
	}

	public void setVisibleRowCount(int visibleRowCount) {
		int old = getVisibleRowCount();
		super.setVisibleRows(visibleRowCount);
		firePropertyChange(VISIBLEROWCOUNT, old, visibleRowCount);
	}

	@Override
	public void setFocusable(boolean b) {
		boolean old = isFocusable();
		super.setFocusable(b);
		firePropertyChange(FOCUSABLE, old, b);
	}

	@Override
	public void setEnabled(boolean b) {
		// super.setEnabled(b);
		// logger.debug("IN SETENABLED. IGNORING");
		// Thread.dumpStack();
	}

	@Override
	protected JLabel getLabel() {
		if (myLabel == null) {
			myLabel = new TipiSwingPropertyDescriptionLabel(this, myComponent);
		}
		return myLabel;
	}

	@Override
	public TipiDndManager getDndManager() {
		return myTipiDndManager;
	}
}
