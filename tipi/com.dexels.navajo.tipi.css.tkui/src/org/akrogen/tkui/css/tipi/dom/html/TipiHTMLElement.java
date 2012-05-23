/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.tipi.dom.html;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.dom.TipiElement;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * w3c Element which wrap Swing Component to manage HTML/XUL selectors.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * TODO I'm not sure if I need this one at all
 */
public class TipiHTMLElement extends TipiElement {

	protected String attributeType;

	public TipiHTMLElement(TipiComponent component, CSSEngine engine) {
		super(component, engine);
		this.attributeType = computeAttributeType();
	}

	protected String computeLocalName() {
		TipiComponent component = getComponent();
		// HTML name
		if (component instanceof JTextArea)
			return "textarea";
		if (component instanceof JTextComponent)
			return "input";
		if (component instanceof JCheckBox)
			return "input";
		if (component instanceof JRadioButton)
			return "input";
		if (component instanceof JButton)
			return "input";
		if (component instanceof JLabel)
			return "label";
		if (component instanceof JComboBox)
			return "select";
		if (component instanceof JFrame)
			return "body";
		if (component instanceof JRootPane)
			return "div";
		if (component instanceof JPanel)
			return "div";
		if (component instanceof JLayeredPane)
			return "div";
		// XUL name
		if (component instanceof JTree)
			return "tree";
		// XUL name
		if (component instanceof JTable)
			return "listbox";
		return super.computeLocalName();
	}

	public String getAttribute(String attr) {
		if ("type".equals(attr))
			return attributeType;
		return super.getAttribute(attr);
	}

	protected String computeAttributeType() {
		TipiComponent component = getComponent();
		if (component instanceof JTextComponent)
			if (!(component instanceof JTextArea))
				return "text";
		if (component instanceof JCheckBox)
			return "checkbox";
		if (component instanceof JRadioButton)
			return "radio";
		if (component instanceof JButton)
			return "button";
		return "";
	}

}
