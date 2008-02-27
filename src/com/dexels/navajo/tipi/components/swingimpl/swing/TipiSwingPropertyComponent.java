package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class TipiSwingPropertyComponent extends GenericPropertyComponent {
	
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
	
	
	public TipiSwingPropertyComponent(TipiComponent tc) {
		myComponent = tc;
	}
	
	public boolean isAlwaysUseLabel() {
		return alwaysUseLabel;
	}
	public void setAlwaysUseLabel(boolean alwaysUseLabel) {
		this.alwaysUseLabel = alwaysUseLabel;
	}
	public String getCapitalization() {
		return super.getCapitalization();
	}
	public void setCapitalization(String capitalization) {
		String old = super.getCapitalization();
		super.setCapitalization(capitalization);
		firePropertyChange(CAPITALIZATION, old, capitalization);
	}
	
	
// public void setProperty(Property p) {
		  public void constructPropertyComponent(Property p) {
		
		String subtype = p.getSubType("tipitype");
		if(subtype==null) {
			super.constructPropertyComponent(p);
			return;
		}
		System.err.println("TYPE: "+subtype);
		
		String[] types = Property.VALID_DATA_TYPES;
		for (int i = 0; i < types.length; i++) {
			if(subtype.equals(types[i])) {
				p.setType(subtype);
				super.constructPropertyComponent(p);
				return;
			}
		}
		if(subtype.equals("color")) {
			TipiSwingColorButton tscb = new TipiSwingColorButton(myComponent);
			tscb.setProperty(p);
			addPropertyComponent(tscb);
		} else if (subtype.equals("paint")){
			TipiSwingPaintEditor tscb = new TipiSwingPaintEditor(myComponent);
			tscb.setProperty(p);
			addPropertyComponent(tscb);
		} else {
			Object oo = p.getTypedValue();
			if(oo!=null) {
				addPropertyComponent(new JLabel("Huh: "+subtype+"=="+oo.getClass()));
			} else {
				addPropertyComponent(new JLabel("Huh: "+subtype));
				
			}
			System.err.println("Strange type: "+subtype);
		}
		// strange type found:
		
	}
	
	public void setCheckboxGroupColumnCount(int checkboxGroupColumnCount) {
		int old = getCheckboxGroupColumnCount();
		super.setCheckboxGroupColumnCount(checkboxGroupColumnCount);
		firePropertyChange(CHECKBOXGROUPCOLUMNCOUNT, old, checkboxGroupColumnCount);
	}
	
	public boolean isHorizontalScrolls() {
		return super.hasHorizontalScrolls();
	}
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
	public int getLabelIndent() {
		return super.getLabelIndent();
	}
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
		firePropertyChange(LABELVALIGN, old, labelVAlign);
	}
	public int getMaxImageHeight() {
		return super.getMaxImageHeight();
	}
	public void setMaxImageHeight(int maxImageHeight) {
		int old = getMaxImageHeight();
		super.setMaxImageHeight(maxImageHeight);
		firePropertyChange(MAXIMAGEHEIGHT, old, maxImageHeight);
	}
	public int getMaxImageWidth() {
		return super.getMaxImageWidth();
	}
	public void setMaxImageWidth(int maxImageWidth) {
		int old = getMaxImageWidth();
		super.setMaxImageWidth(maxImageWidth);
		firePropertyChange(MAXIMAGEWIDTH, old, maxImageWidth);
	}
	public int getMaxWidth() {
		return super.getMaxWidth();
	}
	public void setMaxWidth(int maxWidth) {
		int old = getMaxWidth();
		super.setMaxWidth(maxWidth);
		firePropertyChange(MAXWIDTH, old, maxWidth);
	}
	public int getMemoColumnCount() {
		return super.getMemoColumnCount();
	}
	public void setMemoColumnCount(int memoColumnCount) {
		int old = getMemoColumnCount();
		super.setMemoColumnCount(memoColumnCount);
		firePropertyChange(MEMOCOLUMNCOUNT, old, memoColumnCount);
	}
	public int getMemoRowCount() {
		return super.getMemoColumnCount();
	}
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
	
	public String getSelectionType() {
		return super.getSelectionType();
	}
	public void setSelectionType(String selectionType) {
		String old = getSelectionType();
		super.setSelectionType(selectionType);
		firePropertyChange(SELECTIONTYPE, old, selectionType);
	}
	
	public boolean isShowDatePicker() {
		return super.hasShowDatePicker();
	}
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
	
	public void setFocusable(boolean b) {
		boolean old = isFocusable();
		super.setFocusable(b);
		firePropertyChange(FOCUSABLE, old, b);
}
	
	public void setEnabled(boolean b) {
		//super.setEnabled(b);
//		System.err.println("IN SETENABLED. IGNORING");
//		Thread.dumpStack();
	}


}
