package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingOffsetPanel;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiOffsetPanel extends TipiSwingDataComponentImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5033222906501933602L;
	private TipiSwingOffsetPanel myPanel;

	@Override
	public void addToContainer(Object c, Object constraints) {
		myPanel.getClient().add((Component) c, constraints);
		myPanel.doLayout();
	}

	// @Override
	@Override
	public Object getContainerLayout() {
		return myPanel.getClient().getLayout();
	}

	// @Override
	@Override
	public void removeFromContainer(Object c) {
		myPanel.getClient().remove((Component) c);
		myPanel.doLayout();
	}

	@Override
	public void setContainerLayout(Object layout) {
		myPanel.getClient().setLayout((LayoutManager) layout);
		myPanel.doLayout();
	}

	@Override
	public Object createContainer() {
		myPanel = new TipiSwingOffsetPanel();
		myPanel.setOpaque(false);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);

		return myPanel;
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("Aap");
		jf.setSize(300, 200);
		jf.setVisible(true);
		// jf.getContentPane().setLayout(new FlowLayout());
		JPanel jp = new JPanel();
		jp.setLayout(new GridBagLayout());
		jp.setBorder(BorderFactory.createTitledBorder("Monkeeeyyy"));
		jf.getContentPane().add(jp);
		jp.add(Box.createHorizontalStrut(100), new GridBagConstraints(0, 0, 1,
				1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		jp.add(new JLabel("Da monkeyy"), new GridBagConstraints(0, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		JTextField jr = new JTextField("");
		jf.getContentPane().add(jp);

		jp.add(jr, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(2, 2, 2, 2), 0, 0));
		jp.add(Box.createHorizontalStrut(100), new GridBagConstraints(1, 0, 1,
				1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
	}

}
