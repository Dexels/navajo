/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.dexels.navajo.document.types.TypeFormatter;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class GenericPropertyComponent extends JPanel {
	private static final long serialVersionUID = -2357812021805333562L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(GenericPropertyComponent.class);
	
	private JComponent currentComponent = null;
	private int labelWidth = 0;
	private int valign = SwingConstants.CENTER;
	private int halign = SwingConstants.LEADING;
	int max_img_width = 100;
	int max_img_height = 100;
	private String mySelectionType = null;
	private String myLabelText;
	private boolean addedCustomListeners = false;

	// private int height
	private int propertyWidth = 0;
	private boolean showLabel = true;
	private JLabel myLabel = new JLabel();

	// BorderLayout borderLayout = new BorderLayout();
	// private Map failedPropertyIdMap = null;
	private final List<FocusListener> focusListeners = new ArrayList<FocusListener>();
	private int checkboxGroupColumnCount = 0;
	private int memoColumnCount = 0;
	private int memoRowCount = 0;
	private String forcedAlignment = null;

	private String toolTipText;

	MultipleSelectionPropertyCheckboxGroup myMultiple = null;
	MultipleSelectionPropertyList myMultipleList = null;
	TextPropertyField myField = null;
	DatePropertyField myDateField = null;
	PropertyCheckBox myCheckBox = null;
	IntegerPropertyField myIntField = null;
	FloatPropertyField myFloatField = null;
	PropertyPasswordField myPasswordField = null;
	ClockTimeField myClockTimeField = null;
	StopwatchTimeField myStopwatchTimeField = null;
	MoneyField myMoneyField = null;
	PercentageField myPercentageField = null;
	PropertyRadioSelection myRadioButtonField = null;
	PropertyTextArea myMemoField = null;
	PropertyHiddenField myHiddenField = null;
	MultipleSelectionPropertyPickList myPickList = null;
	URIPropertyField myURIField = null;
	// MultiSelectPropertyBox myMultiSelectBox = null;
	PropertyBox myBox = null;
	BinaryEditor myBinaryLabel = null;

	private boolean verticalScrolls = true;
	private boolean horizontalScrolls = false;

	private boolean hardEnabled = false;
	private boolean myEnableState = true;
	private boolean use_checkbox = false;
	private JComponent currentPropertyComponent = null;
	private String myCapitalization = "off";
	private String mySearch = "off";
	private boolean setPropFlag = false;
	private boolean isLabelSet = false;
	private Property myProperty;
	private boolean isFocusable = false;
	private final ArrayList<PropertyEventListener> myPropertyEventListeners = new ArrayList<PropertyEventListener>();
	private boolean alwaysUseLabel = false;
	// private Dimension myPreferredSize = null;
	private Component currentLabelIndentStrut = null;
	private int forcedTotalWidth = -1;
	private boolean showDatePicker = true;
	private int visibleRowCount = 0;
	private JScrollPane memoFieldScrollPane;
	private Color listSelectionColor = new Color(220, 220, 255);
	private boolean orderListBySelected = false;

	private final List<KeyListener> myKeyListeners = new ArrayList<KeyListener>();
	private String forceFieldAlignment = null;
	private int limitFieldWidth = -1;

	private TypeFormatter formatter = null;

	private PropertyChangeListener myPropertyChangeListener = null;
	private Component valueStrut = null;

	public GenericPropertyComponent() {

		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	protected JLabel getLabel() {
		if (myLabel == null) {
			myLabel = new JLabel();
		}
		return myLabel;
	}

	public void addPropertyKeyListener(KeyListener kl) {
		myKeyListeners.add(kl);
	}

	public void removePropertyKeyListener(KeyListener kl) {
		myKeyListeners.remove(kl);
	}

	public void setForcedAlignment(String align) {
		forcedAlignment = align;
		if (currentComponent instanceof PropertyField) {
			PropertyField pf = (PropertyField) currentComponent;
			pf.setForcedAlignment(align);
		}
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	private void setPropertyKeyListeners(JComponent c) {
		for (int i = 0; i < myKeyListeners.size(); i++) {
			KeyListener kl = myKeyListeners.get(i);
			c.addKeyListener(kl);
		}
	}

	private void clearPropertyKeyListeners(JComponent currentPropertyComponent) {
		for (int i = 0; i < myKeyListeners.size(); i++) {
			KeyListener kl = myKeyListeners.get(i);
			currentPropertyComponent.removeKeyListener(kl);
		}

	}

	public void setMaxImageWidth(int w) {
		max_img_width = w;
		if (myBinaryLabel != null) {
			myBinaryLabel.setMaxImgWidth(w);
		}
	}

	public void setMaxImageHeight(int h) {
		max_img_height = h;
		if (myBinaryLabel != null) {
			myBinaryLabel.setMaxImgHeight(h);
		}
	}

	public void addCustomFocusListener(FocusListener l) {
		focusListeners.add(l);
	}

	public void removeCustomFocusListener(FocusListener l) {
		focusListeners.remove(l);
	}

	//
	// public void doLayout() {
	// super.doLayout();
	// }

	public void setMaxImageDimension(int width, int height) {

	}

	public void setLabelVisible(boolean b) {
		if (b) {
			showLabel();
		} else {
			hideLabel();
		}
	}

	public final Property getProperty() {
		return myProperty;
	}

	public void setProperty(final Property p) {
		//
		if (!SwingUtilities.isEventDispatchThread()) {
			Thread.dumpStack();
		}

		if (myProperty != null) {
			if (myPropertyChangeListener != null) {
				myProperty
						.removePropertyChangeListener(myPropertyChangeListener);
			}
		}
		myProperty = p;
		if (p == null) {
			return;
		}
		myPropertyChangeListener = new SerializablePropertyChangeListener() {

			private static final long serialVersionUID = -2232067207475872106L;

			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("description")) {
					runSyncInEventThread(new Runnable() {

						@Override
						public void run() {
							setLabel((String) evt.getNewValue());
						}
					});
				}
				else if (evt.getPropertyName().equals("subType")) {
					runSyncInEventThread(new Runnable() {

						@Override
						public void run() {
							setLabel(myProperty.getDescription());
						}
					});
				}
			}
		};

		myProperty.addPropertyChangeListener(myPropertyChangeListener);

		String caps = p.getSubType("capitalization");
		if (caps != null) {
			setCapitalization(caps);
		}

		setPropFlag = true;
		String description = p.getDescription();
		if (description == null || "".equals(description)) {
			description = p.getName();
		}
		if (showLabel) {
			if (!isLabelSet) {
				setLabel(description);
			}
		} else {
			hideLabel();
		}
		constructPropertyComponent(p);
		if (hardEnabled) {
			setEnabled(myEnableState);
		}

		if (currentComponent instanceof PropertyField) {
			PropertyField pf = (PropertyField) currentComponent;
			pf.setForcedAlignment(forcedAlignment);
		}

		String search = p.getSubType("search");
		if (search != null) {
			setSearch(search);
		}
		if (currentComponent != null) {
			if (toolTipText != null) {
				currentComponent.setToolTipText(toolTipText);
			}
		}
		setPropFlag = false;
	}

	@Override
	public void requestFocus() {
		if (currentComponent != null) {
			currentComponent.requestFocus();
		}

	}

	@Override
	public boolean requestFocusInWindow() {
		if (currentComponent != null) {
			return currentComponent.requestFocusInWindow();
		}
		return false;
	}

	public final void setPropertyComponent(JComponent c) {
		setPropertyComponent(c, false);
	}

	public final void setPropertyComponent(JComponent c, boolean verticalWeight) {
		if (currentComponent != null) {
			remove(currentComponent);
		}
		currentComponent = c;
		if (verticalWeight) {
			add(currentComponent, new GridBagConstraints(1, 0, 1, 1, 1, 1.0,
					GridBagConstraints.WEST, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
		} else {
			add(currentComponent, new GridBagConstraints(1, 0, 1, 1, 1, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));
		}
		if (valueStrut != null) {
			remove(valueStrut);
		}
		if (propertyWidth > 0) {
			// Not exactly beautiful. Don't know why the strut is not enough,
			// why do I need to pad too?
			setComponentWidth();
		}

		revalidate();
	}

	public final void setListSelectionColor(Color c) {
		listSelectionColor = c;
	}

	public final void setOrderListBySelected(boolean b) {
		orderListBySelected = b;
	}
	
	protected String getRequiredPostfix()
	{
		return "";
	}
	
	public final void setLabel(final String s) {
		if (myProperty != null && myProperty.getSubType(Property.SUBTYPE_REQUIRED) != null && myProperty.getSubType(Property.SUBTYPE_REQUIRED).equals(Property.TRUE))
		{
			myLabelText = s + getRequiredPostfix();
		}
		else
		{
			myLabelText = s;
		}
		final String labelText = myLabelText;
		if (getLabel().getParent() != this) {
			add(getLabel(), new GridBagConstraints(0, 0, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));
		} else {
			logger.info("Already present!");
		}
		// if (myLabel == null) {
		// myLabel = new JLabel();
		getLabel().setVisible(true);
		// myLabel.setVerticalAlignment(JLabel.CENTER_ALIGNMENT);
		getLabel().setOpaque(false);
		// add(myLabel, BorderLayout.WEST);
		getLabel().setText(labelText);
		// }
		if (labelWidth != 0) {
			setLabelIndent(labelWidth);
		}
		getLabel().setHorizontalAlignment(halign);
		getLabel().setVerticalAlignment(valign);
		getLabel().setVisible(showLabel);
		isLabelSet = true;
	}

	public final void showLabel() {
		showLabel = true;
		// if (myLabel != null) {
		getLabel().setVisible(showLabel);
		// }
	}

	public final void hideLabel() {
		showLabel = false;
		// if (myLabel != null) {
		// remove(myLabel);
		// }
		if (currentLabelIndentStrut != null) {
			remove(currentLabelIndentStrut);
		}
		// myLabel = null;
	}

	public final void setVerticalLabelAlignment(final int alignment) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				valign = alignment;
				if (getLabel() != null) {
					getLabel().setVerticalAlignment(alignment);
				}
			}
		});
	}

	public final void update() {
		// Bleh
	}

	// created for tipi, used to notify when a property has changed
	public void updatePropertyValue(PropertyChangeEvent e) {
		// if(e.getNewValue().equals(myProperty.getTypedValue())) {
		// // ignore
		// } else {
		constructPropertyComponent(myProperty);

		// }

	}

	public final void updateProperty() {
		if (PropertyControlled.class.isInstance(currentComponent)) {
			PropertyControlled pc = (PropertyControlled) currentComponent;
			pc.update();
		}
	}

	public final void setHorizontalLabelAlignment(final int alignment) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				halign = alignment;
				if (getLabel() != null) {
					getLabel().setHorizontalAlignment(alignment);
				}
			}
		});
	}

	public final void setCheckBoxLabelPosition(final int alignment) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (myCheckBox != null) {
					myCheckBox.setHorizontalTextPosition(alignment);
				}
			}
		});
	}

	public void runSyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InvocationTargetException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
			}
		}
	}

	public void runAsyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void setLabelIndent(final int lindent) {
		labelWidth = lindent;
		if (getLabel() == null) {
			return;
		}
		if (currentLabelIndentStrut != null) {
			remove(currentLabelIndentStrut);
			currentLabelIndentStrut = null;
		}

		currentLabelIndentStrut = Box.createHorizontalStrut(lindent);
		add(currentLabelIndentStrut, new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		// int height = getPreferredSize().height;

		// myLabel.setPreferredSize(new Dimension(lindent,
		// ComponentConstants.PREFERRED_HEIGHT));
		// invalidate();
		// myLabel.invalidate();
	}

	public void setForcedTotalWidth(int width) {
		forcedTotalWidth = width;
	}

	public int getLabelIndent() {
		return labelWidth;
	}

	public final boolean isLabelVisible() {
		if (getLabel() != null) {
			return getLabel().isVisible();
		}
		return false;
	}

	private final void jbInit() throws Exception {
		this.setLayout(new GridBagLayout());
		setOpaque(false);
	}

	public void constructPropertyComponent(Property p) {
		String type = p.getType();
		if (type.equals(Property.EXPRESSION_PROPERTY)) {
			try {
				type = p.getEvaluatedType();
				// make sure it got evaluated
				p.getTypedValue();
			} catch (NavajoException ex) {
				logger.error("Error: ", ex);
			}
		}

		if ("true".equals(p.getSubType("hidden"))) {
			createPropertyHiddenField(p);
			return;
		}
		if (type == null) {
			type = "string";
		}
		if (type.equals(Property.SELECTION_PROPERTY)) {
			if (!"+".equals(p.getCardinality())) {
				if ("radio".equals(mySelectionType)) {
					createRadioButtonPropertyField(p);
				} else if ("list".equals(mySelectionType)) {
					createPropertyList(p);
				} else {
					createPropertyBox(p);
				}
				return;
			} else {
				if ("dropdown".equals(mySelectionType)) {
					// createMultiSelectPropertyBox(p);
					throw new UnsupportedOperationException(
							"I removed the dropdown for cardinality+ selections. I thought nobody used them");
				} else if ("checkbox".equals(mySelectionType)) {
					createPropertyCheckboxList(p);
				} else if ("picklist".equals(mySelectionType)) {
					createPickList(p);
				} else {
					createPropertyList(p);

				}
				// if radio is passed, it will use checkboxes when cardinality =
				// +
				// Not really pretty, but checkboxes are more similar to
				// radiobuttons
				// than a list. This construction is used for the Questions in
				// forms.
				// All questions have this hint.
				if ("radio".equals(mySelectionType)) {
					createPropertyCheckboxList(p);
				}

				return;
			}
		}

		if (type.equals(Property.BOOLEAN_PROPERTY)) {
			createPropertyCheckbox(p);
			return;
		}
		if (type.equals(Property.DATE_PROPERTY)) {
			createPropertyDateField(p);
			return;
		}
		if (type.equals(Property.INTEGER_PROPERTY)) {
			createIntegerField(p);
			return;
		}
		if (type.equals(Property.LONG_PROPERTY)) {
			createLongField(p);
			return;
		}
		if (type.equals(Property.BINARY_PROPERTY)) {
			createBinaryComponent(p);
			return;
		}
		if (type.equals(Property.PASSWORD_PROPERTY)) {
			createPropertyPasswordField(p);
			return;
		}
		if (type.equals(Property.FLOAT_PROPERTY)) {
			createPropertyFloatField(p);
			return;
		}
		if (type.equals(Property.MONEY_PROPERTY)) {
			createMoneyPropertyField(p);
			return;
		}
		if (type.equals(Property.PERCENTAGE_PROPERTY)) {
			createPercentagePropertyField(p);
			return;
		}
		if (type.equals(Property.CLOCKTIME_PROPERTY)) {
			createClockTimeField(p);
			return;
		}
		if (type.equals(Property.STOPWATCHTIME_PROPERTY)) {
			createStopwatchTimeField(p);
			return;
		}
		if (type.equals(Property.MEMO_PROPERTY)) {
			createMemoField(p);
			return;
		}
		if (type.equals("string") && "true".equals(p.getSubType("uri"))) {
			createURIPropertyField(p);
			return;
		}
		if (type.equals("string") && "true".equals(p.getSubType("password"))) {
			createPropertyPasswordField(p);
			return;
		}
		if (type.equals(Property.TIPI_PROPERTY)) {
			if (getFormatter() != null) {
				createTipiField(p);
				return;
			}
		}

		createPropertyField(p);
		return;
	}

	//
	// private final void constructPropertyComponent(Property p) {
	// if (p.getType().equals("selection")) {
	// if (!"+".equals(p.getCardinality())) {
	// if (use_checkbox) {
	// createRadioButtonPropertyField(p);
	// }
	// else {
	// createPropertyList(p);
	// }
	// return;
	// }
	// else {
	// if (use_checkbox) {
	// createPropertyCheckboxList(p);
	// }
	// else {
	// createPropertyList(p);
	// }
	// return;
	// }
	// }
	// String type = p.getType();
	// if (type.equals(Property.EXPRESSION_PROPERTY)) {
	// try {
	// type = p.getEvaluatedType();
	// logger.info("Found evaluated type: "+type);
	// evaluatedValue = p.getTypedValue();
	// }
	// catch (NavajoException ex) {
	// ex.printStackTrace();
	// }
	// }
	//
	// if (type.equals("boolean")) {
	// createPropertyCheckbox(p);
	// return;
	// }
	// if (type.equals("date")) {
	// createPropertyDateField(p);
	// return;
	// }
	// if (type.equals("integer")) {
	// createIntegerField(p);
	// return;
	// }
	// if (type.equals("binary")) {
	// createBinaryComponent(p);
	// return;
	// }
	// if (type.equals("password")) {
	// createPropertyPasswordField(p);
	// return;
	// }
	// if (type.equals("float")) {
	// createPropertyFloatField(p);
	// return;
	// }
	// if (type.equals("money")) {
	// createMoneyPropertyField(p);
	// return;
	// }
	// if (type.equals("percentage")) {
	// createPercentagePropertyField(p);
	// return;
	// }
	// if (type.equals("clocktime")) {
	// createClockTimeField(p);
	// return;
	// }
	// if (type.equals(Property.MEMO_PROPERTY)) {
	// createMemoField(p);
	// return;
	// }
	// createPropertyField(p);
	// return;
	// }

	// private final void createBinaryComponent(Property p) {
	// //Test case image..
	// /** @todo Shouldnt the old component be removed first? */
	// Binary b = (Binary)p.getTypedValue();
	// if (b==null) {
	// myBinaryLabel = new BaseLabel();
	// ((BaseLabel)myBinaryLabel).setText("Null binary property");
	// addPropertyComponent(myBinaryLabel);
	// return;
	// }
	// byte[] data = b.getData();
	// String mime = b.getMimeType();
	// if (mime.indexOf("image")!=-1) {
	// ImageIcon img = new ImageIcon(data);
	// myBinaryLabel = new BaseLabel();
	// ((BaseLabel)myBinaryLabel).setIcon(img);
	// addPropertyComponent(myBinaryLabel);
	// return;
	// }
	// if (mime.indexOf("text")!=-1) {
	// myBinaryLabel = new JTextArea();
	// ((JTextArea)myBinaryLabel).setText(new String(data));
	// addPropertyComponent(myBinaryLabel);
	// return;
	// }
	// ImageIcon img = new ImageIcon(data);
	// myBinaryLabel = new BaseLabel();
	// ((BaseLabel)myBinaryLabel).setText("Unknown binary property. Mimetype:
	// "+mime+" size: "+data.length);
	// addPropertyComponent(myBinaryLabel);
	// return;
	// }

	private void createTipiField(Property p) {
		String result = getFormatter().formatObject(p.getTypedValue(),
				p.getTypedValue().getClass());
		JLabel lh = new JLabel(result);
		addPropertyComponent(lh);
	}

	private final void createBinaryComponent(final Property p) {
		// Test case image..
		/** @todo Shouldnt the old component be removed first? */
		// if (p.getLength() > 0) {
		// max_img_size = p.getLength();
		// }
		// logger.info("Length: "+p.getLength());
		if (myBinaryLabel == null) {
			myBinaryLabel = new BinaryEditor();
			myBinaryLabel.setMaxImgWidth(max_img_width);
			myBinaryLabel.setMaxImgHeight(max_img_height);
			addPropertyComponent(myBinaryLabel);
		}
		myBinaryLabel.setProperty(p);
	}

	// public void setComponentBackground(Color c) {
	// if (currentPropertyComponent != null) {
	// currentPropertyComponent.setBackground(c);
	// }
	// }

	protected final void addPropertyComponent(JComponent c) {
		addPropertyComponent(c, false);
	}
	protected final void addPropertyComponent(JComponent c,
			boolean verticalWeight) {
		addPropertyComponent(c, c, verticalWeight);
	}
	protected final void addPropertyComponent(JComponent c, JComponent dataComponent,
			boolean verticalWeight) {
		if (currentPropertyComponent != null) {
			clearPropertyKeyListeners(currentPropertyComponent);
		}
		setPropertyKeyListeners(c);
		setPropertyComponent(c, verticalWeight);
		currentPropertyComponent = c;

		if (!addedCustomListeners) {
			c.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					for (int i = 0; i < focusListeners.size(); i++) {
						FocusListener fl = focusListeners.get(i);
						fl.focusGained(e);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					for (int i = 0; i < focusListeners.size(); i++) {
						FocusListener fl = focusListeners.get(i);
						fl.focusLost(e);
					}
				}
			});
			addedCustomListeners = true;
			// logger.info("Component class: "+c.getClass());
			if (c instanceof JTextField) {
				JTextField jj = (JTextField) c;
				// logger.info("Text component found. forceAlignment:
				// "+forceFieldAlignment+" limit: "+limitFieldWidth);
				if ("left".equals(forceFieldAlignment)) {
					jj.setHorizontalAlignment(SwingConstants.LEFT);
				}
				if ("right".equals(forceFieldAlignment)) {
					jj.setHorizontalAlignment(SwingConstants.RIGHT);
				}
				if ("center".equals(forceFieldAlignment)) {
					jj.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (limitFieldWidth > 0) {
					logger.info("Limiting field size to: "
							+ limitFieldWidth);
					// jj.setSize(new Dimension(limitFieldWidth,
					// jj.getPreferredSize().height));
					jj.setColumns(limitFieldWidth);

					// jj.setBackground(new Color(255, 0, 0));
					this.doLayout();
				}
			}
		}
		// if c and dataComponent are not equal, set c to non-focusable
		// examples: MemoField and RadioSelection
		if (!c.equals(dataComponent))
		{
			c.setFocusable(false);
		}
		
		if (dataComponent != null && myProperty != null
				&& !isFocusable) {
			dataComponent.setFocusable(myProperty.isDirIn());
		}
	}

	@Override
	public void setFocusable(boolean b) {
		isFocusable = b;
		setRequestFocusEnabled(b);
	}

	public final boolean getFocusable() {
		return isFocusable;
	}

	private final void createPropertyBox(Property p) {
		if (myBox == null) {
			myBox = new PropertyBox();
			// myBox.addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// myBox_actionPerformed(e);
			// }
			// });
			myBox.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myBox_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myBox_focusLost(e);
				}
			});
			myBox.addItemListener(new java.awt.event.ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					myBox_itemStateChanged(e);
				}
			});
		}
		addPropertyComponent(myBox);
		myBox.loadProperty(p);
	}

	private final void createPropertyList(Property p) {
		if (myMultipleList == null) {
			myMultipleList = new MultipleSelectionPropertyList();
			myMultipleList
					.addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							myMultipleList_valueChanged(e);
						}

					});
		}
		if (visibleRowCount != 0) {
			myMultipleList.setVisibleRowCount(visibleRowCount);
		}
		myMultipleList.setVerticalScrolls(verticalScrolls);
		myMultipleList.setHorizontalScrolls(horizontalScrolls);

		myMultipleList.setSelectedColor(listSelectionColor);
		myMultipleList.setOrderBySelected(orderListBySelected);
		addPropertyComponent(myMultipleList, true);
		myMultipleList.setProperty(p);
	}

	public int getVisibleRowCount() {
		return visibleRowCount;
	}

	private final void createPropertyCheckboxList(Property p) {
		if (myMultiple == null) {
			myMultiple = new MultipleSelectionPropertyCheckboxGroup();
			myMultiple.addCheckboxListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					fireTipiEvent("onValueChanged");
				}
			});

		}
		myMultiple.setVerticalScrolls(verticalScrolls);
		myMultiple.setHorizontalScrolls(horizontalScrolls);
		if (checkboxGroupColumnCount > 0) {
			myMultiple.setColumnMode(true);
			myMultiple.setColumns(checkboxGroupColumnCount);
		} else {
			myMultiple.setColumnMode(false);
		}
		addPropertyComponent(myMultiple);
		myMultiple.setProperty(p);
	}

	public void setCheckboxGroupColumnCount(int count) {
		checkboxGroupColumnCount = count;
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	private final void createPickList(Property p) {
		if (myPickList == null) {
			myPickList = new MultipleSelectionPropertyPickList();
		}
		// myPickList.setVerticalScrolls(verticalScrolls);
		// myPickList.setHorizontalScrolls(horizontalScrolls);
		// logger.info("PICKLIST: "+visibleRowCount);
		addPropertyComponent(myPickList, true);
		myPickList.setProperty(p);
		if (visibleRowCount > 0) {
			myPickList.setVisibleRowCount(visibleRowCount);
		}
	}

	private final void createPropertyCheckbox(Property p) {
		if (myCheckBox == null) {
			myCheckBox = new PropertyCheckBox();
			addPropertyComponent(myCheckBox);
			myCheckBox.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myCheckBox_actionPerformed(e);
				}
			});
			myCheckBox.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myCheckBox_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myCheckBox_focusLost(e);
				}
			});
			myCheckBox.addItemListener(new java.awt.event.ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					myCheckBox_itemStateChanged(e);
				}
			});
		}
		myCheckBox.setProperty(p);
		if (!showLabel) {
			myCheckBox.setText(myLabelText);
		}
	}

	private final void createIntegerField(Property p) {
		// logger.info("Create int field: "+p.getValue()+" type:
		// "+p.getType()+"aaa: "+p.getTypedValue().getClass());
		// Thread.dumpStack();
		if (myIntField == null) {
			myIntField = new IntegerPropertyField();
			myIntField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myField_focusLost(e);
				}
			});
			myIntField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myField_actionPerformed(e);
				}
			});
		}
		addPropertyComponent(myIntField);
		myIntField.setProperty(p);
	}

	private final void createLongField(Property p) {
		if (myIntField == null) {
			myIntField = new IntegerPropertyField(true);
			myIntField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myField_focusLost(e);
				}
			});
			myIntField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myField_actionPerformed(e);
				}
			});
		}
		myIntField.setLongMode(true);
		addPropertyComponent(myIntField);
		myIntField.setProperty(p);
	}

	private final void createPropertyFloatField(Property p) {
		if (myFloatField == null) {
			myFloatField = new FloatPropertyField();
			myFloatField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myField_focusLost(e);
				}
			});
			myFloatField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myField_actionPerformed(e);
				}
			});
		}
		addPropertyComponent(myFloatField);
		myFloatField.setProperty(p);
	}

	private final void createPropertyDateField(Property p) {
		if (myDateField == null) {
			myDateField = new DatePropertyField();
			myDateField.setShowCalendarPickerButton(showDatePicker);
			myDateField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myDateField_actionPerformed(e);
				}
			});
			myDateField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myDateField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myDateField_focusLost(e);
				}
			});
		}
		addPropertyComponent(myDateField);
		myDateField.setProperty(p);
	}

	private final void createPropertyField(Property p) {
		if (myField == null) {
			myField = new TextPropertyField();

			myField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myField_focusLost(e);
				}
			});
			myField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myField_actionPerformed(e);
				}
			});
			myField.setCapitalizationMode(myCapitalization);
		}
		myField.setProperty(p);
		myField.setCaretPosition(0);
		if (alwaysUseLabel) {
			myField.setBorder(BorderFactory.createCompoundBorder());
			// myField.setBorder(null);
		}
		addPropertyComponent(myField);
	}

	private final void createURIPropertyField(Property p) {
		if (myURIField == null) {
			myURIField = new URIPropertyField();

			myURIField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myURIField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myURIField_focusLost(e);
				}
			});
			myURIField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myURIField_actionPerformed(e);
				}
			});
			myURIField.setCapitalizationMode(myCapitalization);
		}
		myURIField.setProperty(p);
		addPropertyComponent(myURIField);
	}

	private final void createPropertyPasswordField(Property p) {
		if (myPasswordField == null) {
			myPasswordField = new PropertyPasswordField();
			myPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myPasswordField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myPasswordField_focusLost(e);
				}
			});
			myPasswordField
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							myPasswordField_actionPerformed(e);
						}
					});
		}
		myPasswordField.setProperty(p);
		addPropertyComponent(myPasswordField);
	}

	private final void createPropertyHiddenField(Property p) {
		if (myHiddenField == null) {
			myHiddenField = new PropertyHiddenField();
			myHiddenField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myPasswordField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myPasswordField_focusLost(e);
				}
			});
			myHiddenField
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							myPasswordField_actionPerformed(e);
						}
					});
		}
		myHiddenField.setProperty(p);
		addPropertyComponent(myHiddenField);
	}

	private final void createMoneyPropertyField(Property p) {
		if (myMoneyField == null) {
			myMoneyField = new MoneyField();
			myMoneyField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					myMoneyField_focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					myMoneyField_focusLost(e);
				}
			});
			myMoneyField.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myMoneyField_actionPerformed(e);
				}
			});
		}
		myMoneyField.setPropertyValue(p);
		addPropertyComponent(myMoneyField);
	}

	private final void createPercentagePropertyField(Property p) {
		if (myPercentageField == null) {
			myPercentageField = new PercentageField();
			// myPercentageField.addFocusListener(new
			// java.awt.event.FocusAdapter() {
			// public final void focusGained(FocusEvent e) {
			// myPercentageField_focusGained(e);
			// }
			//
			// public final void focusLost(FocusEvent e) {
			// myPercentageField_focusLost(e);
			// }
			// });
			// myPercentageField.addActionListener(new
			// java.awt.event.ActionListener() {
			// public final void actionPerformed(ActionEvent e) {
			// myPercentageField_actionPerformed(e);
			// }
			// });
		}
		myPercentageField.setProperty(p);
		addPropertyComponent(myPercentageField);
	}

	private final void createRadioButtonPropertyField(Property p) {
		if (myRadioButtonField == null) {
			myRadioButtonField = new PropertyRadioSelection(
					new java.awt.event.FocusAdapter() {
						@Override
						public final void focusGained(FocusEvent e) {
							myRadioButtonField_focusGained(e);
						}

						@Override
						public final void focusLost(FocusEvent e) {
							myRadioButtonField_focusLost(e);
						}
					});
			if (checkboxGroupColumnCount > 0) {
				myRadioButtonField.setColumnMode(true);
				myRadioButtonField.setColumns(checkboxGroupColumnCount);
			} else {
				myRadioButtonField.setColumnMode(false);
			}

			myRadioButtonField
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							myRadioButtonField_actionPerformed(e);
						}
					});
			// myRadioButtonField.add1 myRadioButtonField_itemStateChanged
		}
		myRadioButtonField.setProperty(p);
		addPropertyComponent(myRadioButtonField, true);
		// there is more than one "dataComponent" that should be passed along if we want to use the addPropertyComponent version for differing components
		// hence we cannot and need to do it manually.
		myRadioButtonField.setFocusable(false);
	}

	private final void createClockTimeField(Property p) {
		if (myClockTimeField == null) {
			myClockTimeField = new ClockTimeField();
			myClockTimeField
					.addFocusListener(new java.awt.event.FocusAdapter() {
						@Override
						public final void focusGained(FocusEvent e) {
							myClockTimeField_focusGained(e);
						}

						@Override
						public final void focusLost(FocusEvent e) {
							myClockTimeField_focusLost(e);
						}
					});
			myClockTimeField
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							myClockTimeField_actionPerformed(e);
						}
					});
		}
		if ("true".equals(p.getSubType("showseconds"))) {
			myClockTimeField.showSeconds(true);
		} else {
			myClockTimeField.showSeconds(false);
		}
		myClockTimeField.setProperty(p);
		addPropertyComponent(myClockTimeField);
	}

	private final void createStopwatchTimeField(Property p) {
		if (myStopwatchTimeField == null) {
			myStopwatchTimeField = new StopwatchTimeField();
			myStopwatchTimeField
					.addFocusListener(new java.awt.event.FocusAdapter() {
						@Override
						public final void focusGained(FocusEvent e) {
							myStopwatchTimeField_focusGained(e);
						}

						@Override
						public final void focusLost(FocusEvent e) {
							myStopwatchTimeField_focusLost(e);
						}
					});
			myStopwatchTimeField
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public final void actionPerformed(ActionEvent e) {
							myStopwatchTimeField_actionPerformed(e);
						}
					});
		}
		myStopwatchTimeField.setProperty(p);
		addPropertyComponent(myStopwatchTimeField);
	}

	private final void createMemoField(final Property p) {
		if (myMemoField == null) {
			myMemoField = new PropertyTextArea();
			myMemoField.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public final void focusGained(FocusEvent e) {
					fireTipiEvent("onFocusGained");
				}

				@Override
				public final void focusLost(FocusEvent e) {
					fireTipiEvent("onFocusLost");
					p.setValue(myMemoField.getText());
				}
			});
			// myMemoField.addActionListener(new java.awt.event.ActionListener()
			// {
			// public void actionPerformed(ActionEvent e) {
			// myClockTimeField_actionPerformed(e);
			// }
			// });
		}

		if (memoColumnCount != 0) {
			myMemoField.setColumns(memoColumnCount);
		}
		if (memoRowCount != 0) {
			myMemoField.setRows(memoRowCount);
		} else {
			myMemoField.setRows(8);
		}
		// if (!p.isDirIn()) {
		// myMemoField.setBackground(Color.lightGray);
		// } else {
		// myMemoField.setBackground(Color.white);
		// }
		myMemoField.setLineWrap(true);
		myMemoField.setWrapStyleWord(true);
		// myMemoField.setMinimumSize(new Dimension(100,16));
		// TODO I don't like this. Memo field should take care of its own
		// scrolling
		// It would require a refactor of the v2 which I don't want to do. Never
		// mind.

		memoFieldScrollPane = new JScrollPane() {
			private static final long serialVersionUID = 1L;

			
		};
		memoFieldScrollPane.getViewport().add(myMemoField);
		memoFieldScrollPane
				.setHorizontalScrollBarPolicy(horizontalScrolls ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
						: JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		memoFieldScrollPane
				.setVerticalScrollBarPolicy(verticalScrolls ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
						: JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		memoFieldScrollPane.setPreferredSize(memoFieldScrollPane.getPreferredSize());
		myMemoField.setProperty(p);
		if (toolTipText != null) {
			myMemoField.setToolTipText(toolTipText);
		}
		// TODO might leak listeners! Investigate..
		setPropertyKeyListeners(myMemoField);
		addPropertyComponent(memoFieldScrollPane, myMemoField, true);
	}

	public void setMemoRowCount(int row) {
		memoRowCount = row;
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	public void setMemoColumnCount(int column) {
		memoColumnCount = column;
		if (getProperty() != null) {
			setProperty(getProperty());
		}
	}

	final void myMultiSelectBox_actionPerformed(ActionEvent e) {
		if (!setPropFlag) {
			fireTipiEvent("onActionPerformed");
		}
	}

	final void myBox_actionPerformed(ActionEvent e) {
		if (!setPropFlag) {
			if (e.getActionCommand().equals("comboBoxChanged")) {
				fireTipiEvent("onValueChanged");
				// PREVIOUS_SELECTION_INDEX = myBox.getSelectedIndex();
			} else {
				fireTipiEvent("onActionPerformed");
			}
		}
	}

	final void myBox_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myBox_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myMultipleList_valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			fireTipiEvent("onValueChanged");
		}
	}

	final void myBox_itemStateChanged(ItemEvent e) {
		if (!setPropFlag) {
			fireTipiEvent("onStateChanged");
		}
	}

	final void myRadioButtonField_actionPerformed(ActionEvent e) {
		if (!setPropFlag) {
			fireTipiEvent("onValueChanged");
			// PREVIOUS_SELECTION_INDEX = myRadioButtonField.getSelectedIndex();
		} else {
			fireTipiEvent("onActionPerformed");
		}
	}

	final void myRadioButtonField_itemStateChanged(ItemEvent e) {
		if (!setPropFlag) {
			fireTipiEvent("onStateChanged");
		}
	}

	final void myRadioButtonField_focusGained(FocusEvent e) {
		if (e.getOppositeComponent() == null || e.getOppositeComponent().getParent() == null || e.getComponent().getParent() == null ||
				!e.getOppositeComponent().getParent().equals(e.getComponent().getParent()))
		{
			fireTipiEvent("onFocusGained");
		}
	}

	final void myRadioButtonField_focusLost(FocusEvent e) {
		if (e.getOppositeComponent() == null || e.getOppositeComponent().getParent() == null || e.getComponent().getParent() == null ||
				!e.getOppositeComponent().getParent().equals(e.getComponent().getParent()))
		{
			fireTipiEvent("onFocusLost");
		}
	}

	// final void myRadioButtonField_itemStateChanged(ItemEvent e) {
	// if (!setPropFlag) {
	// fireTipiEvent("onStateChanged");
	// }
	// }

	final void myField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myURIField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myURIField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myURIField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myPasswordField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myPasswordField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myPasswordField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myDateField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myDateField_focusGained(FocusEvent e) {
		// logger.info(" IN myDateField_focusGained ");
		fireTipiEvent("onFocusGained");
	}

	final void myDateField_focusLost(FocusEvent e) {
		// logger.info(" IN myDateField_focusLOST ");
		fireTipiEvent("onFocusLost");
	}

	final void myCheckBox_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myCheckBox_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myCheckBox_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myCheckBox_itemStateChanged(ItemEvent e) {
		fireTipiEvent("onStateChanged");
	}

	final void myClockTimeField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myClockTimeField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myClockTimeField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myStopwatchTimeField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myStopwatchTimeField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myStopwatchTimeField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myMoneyField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myMoneyField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myMoneyField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	final void myPercentageField_focusGained(FocusEvent e) {
		fireTipiEvent("onFocusGained");
	}

	final void myPercentageField_focusLost(FocusEvent e) {
		fireTipiEvent("onFocusLost");
	}

	final void myPercentageField_actionPerformed(ActionEvent e) {
		fireTipiEvent("onActionPerformed");
	}

	protected final void fireTipiEvent(String type) {
		firePropertyEvents(myProperty, type, true);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setEnabled(boolean value) {
		if (myProperty != null) {
			if (myProperty.getType().equals("selection")
					&& !"+".equals(myProperty.getCardinality())) {
				if (use_checkbox) {
					if (myRadioButtonField != null) {
						myRadioButtonField.setFocusable(value);
						myRadioButtonField.setEnabled(value);
					}
				} else {
					if (myBox != null) {
						myBox.setEnabled(value);
						myBox.setFocusable(value);
					}
				}
				return;
			}
			if (myProperty.getType().equals("selection")
					&& "+".equals(myProperty.getCardinality())) {
				if (use_checkbox) {
					if (myMultiple != null) {
						myMultiple.setFocusable(value);
						myMultiple.setEnabled(value);
					}
				} else {
					if (myMultipleList != null) {
						myMultipleList.setFocusable(value);
						myMultipleList.setEnabled(value);
					}
				}
				return;
			}
			if (myProperty.getType().equals("boolean")) {
				if (myCheckBox != null) {
					myCheckBox.setFocusable(value);
					myCheckBox.setEnabled(value);
				}
				return;
			}
			if (myProperty.getType().equals("date")) {
				if (myDateField != null) {
					myDateField.setFocusable(value);
					myDateField.setEnabled(value);
					myDateField.setEditable(value);
				}
				return;
			}

			if (myProperty.getType().equals("string")
					&& "true".equals(myProperty.getSubType("uri"))) {
				if (myURIField != null) {
					myURIField.setFocusable(value);
					myURIField.setEnabled(value);
					myURIField.setEditable(value);
				}
				return;
			}

			if (myProperty.getType().equals("integer")) {
				if (myIntField != null) {
					myIntField.setFocusable(value);
					myIntField.setEnabled(value);
					myIntField.setEditable(value);
				}
				return;
			}
			if (myProperty.getType().equals("float")) {
				if (myFloatField != null) {
					myFloatField.setFocusable(value);
					myFloatField.setEnabled(value);
					myFloatField.setEditable(value);
				}
				return;
			}
			if (myProperty.getType().equals("clocktime")) {
				if (myClockTimeField != null) {
					myClockTimeField.setFocusable(value);
					myClockTimeField.setEnabled(value);
					myClockTimeField.setEditable(value);
				}
				return;
			}
			if (myProperty.getType().equals("money")) {
				if (myMoneyField != null) {
					myMoneyField.setFocusable(value);
					myMoneyField.setEnabled(value);
					myMoneyField.setEditable(value);
				}
				return;
			}
			if (myProperty.getType().equals("percentage")) {
				if (myPercentageField != null) {
					myPercentageField.setFocusable(value);
					myPercentageField.setEnabled(value);
					myPercentageField.setEditable(value);
				}
				return;
			}
			if (myProperty.getType().equals("password")) {
				if (myPasswordField != null) {
					myPasswordField.setFocusable(value);
					myPasswordField.setEnabled(value);
					myPasswordField.setEditable(value);
				}
				return;
			}
			// if (myProperty.getType().equals("password")) {
			// if (myPasswordField!=null) {
			// myPasswordField.setFocusable(value);
			// myPasswordField.setEnabled(value);
			// myPasswordField.setEditable(value);
			// }
			// return;
			// }
			if (myField != null) {
				myField.setFocusable(value);
				myField.setEnabled(value);
				myField.setEditable(value);
			}
			return;
		} else {
		}
	}

	@Override
	public final void setCursor(Cursor cursor) {
		super.setCursor(cursor);
		if (currentPropertyComponent != null) {
			currentPropertyComponent.setCursor(cursor);

		}
	}

	public final void addPropertyEventListener(PropertyEventListener pel) {
		myPropertyEventListeners.add(pel);
	}

	public final void removePropertyEventListner(PropertyEventListener pel) {
		myPropertyEventListeners.remove(pel);
	}

	protected final void firePropertyEvents(Property p, String eventType,
			boolean internalChange) {
		for (int i = 0; i < myPropertyEventListeners.size(); i++) {
			PropertyEventListener current = myPropertyEventListeners.get(i);
			current.propertyEventFired(p, eventType, internalChange);
		}
	}

	public void setSelectionType(String s) {
		mySelectionType = s;
	}

	public void setCapitalization(String mode) {
		myCapitalization = mode;
		if (myField != null) {
			myField.setCapitalizationMode(mode);
		}

	}

	public void setSearch(String mode) {
		mySearch = mode;
		if (myField != null) {
			myField.setSearchMode(mode);
		}

	}

	public String getCapitalization() {
		return myCapitalization;
	}

	public String getSearch() {
		return mySearch;
	}

	public final void setVisibleRows(int count) {
		visibleRowCount = count;
		if (myMultipleList != null) {
			myMultipleList.setVisibleRowCount(count);
		}
		if (myPickList != null) {
			myPickList.setVisibleRowCount(count);
		}
	}

	public void setShowDatePicker(boolean b) {
		showDatePicker = b;
		if (myDateField != null) {
			myDateField.setShowCalendarPickerButton(b);
		}

	}

	public void setVerticalScrolls(boolean b) {
		verticalScrolls = b;
		if (myMultipleList != null) {
			myMultipleList.setVerticalScrolls(b);
		}
		if (myMultiple != null) {
			myMultiple.setVerticalScrolls(b);
		}
		if (myMemoField != null) {
			// No null check, if there is no scroll pane, it deserves to crash
			memoFieldScrollPane
					.setVerticalScrollBarPolicy(verticalScrolls ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
							: JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
	}

	public void setHorizontalScrolls(boolean b) {
		horizontalScrolls = b;
		if (myMultipleList != null) {
			myMultipleList.setHorizontalScrolls(b);
		}
		if (myMultiple != null) {
			myMultiple.setHorizontalScrolls(b);
		}
		if (myMemoField != null) {
			memoFieldScrollPane
					.setHorizontalScrollBarPolicy(horizontalScrolls ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
							: JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
	}

	public void resetChanged() {
	}

	@Override
	public final boolean hasFocus() {
		if (currentComponent != null) {
			return currentComponent.hasFocus();
		}
		return false;
	}

	public void setAlwaysUseLabel(boolean b) {
		alwaysUseLabel = b;
		setProperty(getProperty());
	}

	// public Dimension getMinimumSize() {
	// return getPreferredSize();
	// }
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(super.getMaximumSize().width,
				super.getMaximumSize().height);
		// return super.getMaximumSize();
	}

	@Override
	public Dimension getPreferredSize() {
		// if (true) {
		return limitTo(super.getPreferredSize(), getMaximumSize());
		// }
		// // if (myProperty == null || currentComponent == null) {
		// // // logger.info("no prop or no component");
		// // }
		// Dimension labelDimension = null;
		// if (showLabel && getLabel() != null) {
		// labelDimension = getLabel().getPreferredSize();
		// if (currentLabelIndentStrut != null) {
		// labelDimension = new Dimension(Math.max(labelDimension.width,
		// labelWidth), labelDimension.height);
		// }
		// if (labelDimension != null && labelWidth > labelDimension.width) {
		// labelDimension.width = labelWidth;
		// }
		// }
		// labelWidth = -1;
		// Dimension componentDimension = currentComponent.getPreferredSize();
		// if (componentDimension == null) {
		// // logger.info("Component without dimension");
		// if (myPreferredSize != null) {
		// return limitTo(new Dimension(myPreferredSize.width,
		// super.getPreferredSize().height), getMaximumSize());
		// }
		// return limitTo(super.getPreferredSize(), getMaximumSize());
		// }
		// if (labelDimension != null) {
		// int height = Math.max(labelDimension.height,
		// componentDimension.height);
		// int w = labelDimension.width + componentDimension.width;
		// if (myPreferredSize != null) {
		// w = myPreferredSize.width;
		// }
		//
		// return limitTo(new Dimension(w, height), getMaximumSize());
		// } else {
		// return limitTo(componentDimension, getMaximumSize());
		// }
	}

	public void setTextFieldColumns(int columnCount) {
		if (currentComponent != null && currentComponent instanceof JTextField) {
			JTextField j = (JTextField) currentComponent;
			j.setColumns(Math.max(columnCount, 3));
		}
	}

	private Dimension limitTo(Dimension preferredSize, Dimension maximumSize) {
		if (maximumSize == null) {
			if (forcedTotalWidth > -1) {
				return new Dimension(forcedTotalWidth, preferredSize.height);
			}
			return preferredSize;
		}
		if (forcedTotalWidth > -1) {
			return new Dimension(forcedTotalWidth, Math.min(
					preferredSize.height, maximumSize.height));
		}
		return new Dimension(Math.min(preferredSize.width, maximumSize.width),
				Math.min(preferredSize.height, maximumSize.height));
	}

	public void forceFieldAlignment(String forceFieldAlignment) {
		this.forceFieldAlignment = forceFieldAlignment;
	}

	public void limitFieldWidth(int i) {
		this.limitFieldWidth = i;

	}

	public void setMaxWidth(int i) {
		Dimension d = getPreferredSize();
		if (d != null) {
			d.width = i;
		} else {
			setMaximumSize(new Dimension(i, Integer.MAX_VALUE));
		}

	}

	public void requestPropertyFocus() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (currentComponent != null) {
					currentComponent.requestFocusInWindow();
				}
			}
		});
	}

	public void setComponentBorder(Border b) {
		if (currentComponent != null) {
			currentComponent.setBorder(b);
		}
	}

	// private InputValidator myInputValidator = null;

	public boolean hasHorizontalScrolls() {
		return horizontalScrolls;
	}

	public boolean hasVerticalScrolls() {
		return verticalScrolls;
	}

	public int getMaxImageHeight() {
		return max_img_height;
	}

	public int getMaxImageWidth() {
		return max_img_width;
	}

	public int getMaxWidth() {
		return Integer.MAX_VALUE;
	}

	public int getMemoColumnCount() {
		return memoColumnCount;
	}

	public String getSelectionType() {
		return mySelectionType;
	}

	public boolean hasShowDatePicker() {
		return showDatePicker;
	}

	public int getCheckboxGroupColumnCount() {
		return checkboxGroupColumnCount;
	}

	public TypeFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(TypeFormatter formatter) {
		this.formatter = formatter;
	}

	// quick hack:
	public void hideBorder() {
		setBorder(null);
		if (currentComponent != null) {
			currentComponent.setBorder(null);
			currentComponent.setOpaque(false);
		}
		setOpaque(false);
	}

	public int getPropertyWidth() {
		return propertyWidth;
	}

	public void setPropertyWidth(int propertyWidth) {
		this.propertyWidth = propertyWidth;

		// if(currentComponent!=null) {
		// currentComponent.setPreferredSize(new Dimension(propertyWidth,
		// ComponentConstants.PREFERRED_HEIGHT));
		// }

		// if (propertyWidth > 0) {
		// if (valueStrut != null) {
		// remove(valueStrut);
		// }
		// valueStrut = Box.createHorizontalStrut(propertyWidth);
		// add(valueStrut, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
		// GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
		// new Insets(0, 0, 0, 0), propertyWidth, 0));
		//
		// }
		setComponentWidth();
	}

	private void setComponentWidth() {
		valueStrut = Box.createHorizontalStrut(propertyWidth);
		add(valueStrut, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), propertyWidth, 0));
		currentComponent.setMaximumSize(new Dimension(propertyWidth,
				Integer.MAX_VALUE));
		valueStrut.setMaximumSize(new Dimension(propertyWidth,
				Integer.MAX_VALUE));
	}

	@Override
	public String getToolTipText() {
		return toolTipText;
	}

	@Override
	public void setToolTipText(String toolTipText) {
	    if (this.toolTipText != null && this.toolTipText.equals(toolTipText)) {
	        return;
	    }
		logger.debug("Setting tooltiptext: " + toolTipText);
		this.toolTipText = toolTipText;
		super.setToolTipText(toolTipText);
		if (currentComponent != null) {
			currentComponent.setToolTipText(toolTipText);
		}
	}

	public void setLabelColor(Color c) {
		JLabel ll = getLabel();
		ll.setForeground(c);
	}
	public void setLabelFont(Font f) {
		JLabel ll = getLabel();
		ll.setFont(f);
	}

}