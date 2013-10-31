package com.dexels.navajo.tipi.swing.functions;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author frank
 * 
 */
public class CreateTitledBorder extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Creates a titled border based on a string and optionally a font style";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "CreateTitledBorder(String title, [String style])";
	}

	// GetComponent({component://init/desktop},{event:/from})
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		Object ss = null;
		if (getOperands().size() >= 2)
		{
			ss = getOperand(1);
		}
		if (pp == null) {
			return null;
		}
		if (!(pp instanceof String)) {
			throw new TMLExpressionException(this, "Invalid operand: "
					+ pp.getClass().getName());
		}
		String title = (String) pp;
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.darkGray, 1), title);
		if (ss != null && ss instanceof String)
		{
			String style = (String) ss;
			Font titleFont = border.getTitleFont();
			if (!style.contains("p"))
			{
				if (style.contains("b"))
				{
					border.setTitleFont(titleFont.deriveFont(Font.BOLD | titleFont.getStyle()));
					titleFont = border.getTitleFont();
				}
				if (style.contains("i"))
				{
					border.setTitleFont(titleFont.deriveFont(Font.ITALIC | titleFont.getStyle()));
				}
			}
			else
			{
				border.setTitleFont(titleFont.deriveFont(Font.PLAIN));
			}
		}
		return border;

	}

}
