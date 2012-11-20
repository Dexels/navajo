package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TipiSwingExportSeparatorPanel extends JPanel {

	private static final long serialVersionUID = 4447847774972763717L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingExportSeparatorPanel.class);
	
	JPanel jPanel1 = new JPanel();
	TitledBorder titledBorder1;
	JRadioButton commaOption = new JRadioButton();
	JRadioButton tabOption = new JRadioButton();
	JRadioButton semicolonOption = new JRadioButton();
	JRadioButton spaceOption = new JRadioButton();
	JRadioButton otherOption = new JRadioButton();
	JTextField customSeparatorField = new JTextField();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	ButtonGroup bg = new ButtonGroup();
	JCheckBox includeTitles = new JCheckBox();

	public TipiSwingExportSeparatorPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error detected",e);
		}
	}

	private final void jbInit() throws Exception {
		this.setLayout(gridBagLayout2);
		jPanel1.setBorder(titledBorder1);
		jPanel1.setLayout(gridBagLayout1);
		jPanel1.add(commaOption, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2,
						2, 2, 2), 0, 0));
		jPanel1.add(tabOption, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2,
						2, 2, 2), 0, 0));
		jPanel1.add(semicolonOption, new GridBagConstraints(0, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
		jPanel1.add(spaceOption, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2,
						2, 2, 2), 0, 0));
		jPanel1.add(otherOption, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2,
						2, 2, 2), 0, 0));
		jPanel1.add(customSeparatorField, new GridBagConstraints(1, 4, 1, 1,
				1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 20, 0));
		jPanel1.add(includeTitles, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(171, 171, 171)), "Scheidings teken");
		commaOption.setSelected(true);
		commaOption.setText("Komma");
		tabOption.setText("Tab");
		semicolonOption.setText("Puntkomma");
		spaceOption.setText("Spatie");
		otherOption.setText("Anders nl.");
		customSeparatorField.setText("");
		includeTitles.setSelected(true);
		includeTitles.setText("Kolomnamen op de eerste rij");
		bg.add(commaOption);
		bg.add(tabOption);
		bg.add(semicolonOption);
		bg.add(spaceOption);
		bg.add(otherOption);
		this.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH,
				new Insets(4, 4, 0, 0), 0, 0));
	}

	public String getSelectedSeparator() {
		if (commaOption.isSelected()) {
			return ",";
		} else if (tabOption.isSelected()) {
			return "\t";
		} else if (semicolonOption.isSelected()) {
			return ";";
		} else if (spaceOption.isSelected()) {
			return " ";
		} else if (otherOption.isSelected()) {
			return customSeparatorField.getText();
		} else {
			return ",";
		}
	}

	public boolean isHeaderSelected() {
		return includeTitles.isSelected();
	}
}