package com.dexels.navajo.tipi.swing.functions;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author frank
 * 
 */
public class CreateTitledBorder extends FunctionInterface {
	private Color defaultLineColor = Color.darkGray;
	private Color defaultShadowColor = Color.WHITE;
	private int titleAlignment = TitledBorder.LEFT;
	
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
		return "CreateTitledBorder(String title, [String bordertype], [Color linecolor], [Color shadowcolor], [titlealignment], [String style])";
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
		String bordertype = null;
		Color linecolor = this.defaultLineColor;
		Color shadowcolor = this.defaultShadowColor;
		String titlealignment = null;
		Object ss = null;
		if (getOperands().size() >= 2) {
			bordertype = (String)getOperand(1);
		}
		if (getOperands().size() >= 3) {
			linecolor = (Color)getOperand(2);
		}
		if (getOperands().size() >= 4) {
			shadowcolor = (Color)getOperand(3);
		}
		if (getOperands().size() >= 5) {
			titlealignment = (String)getOperand(4);
		}
		if (getOperands().size() >= 6) {
			ss = getOperand(5);
		}
		if (pp == null) {
			return null;
		}
		if (!(pp instanceof String)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
		String title = (String) pp;
		
		// See what type of border we are going to create
		Border typedBorder = null;
		if (bordertype != null && !bordertype.isEmpty()) {
			if ( bordertype.equalsIgnoreCase("rounded") ) {
				typedBorder = BorderFactory.createLineBorder(linecolor, 1, true);
			} else if ( bordertype.equalsIgnoreCase("etched") ) {
				typedBorder = BorderFactory.createEtchedBorder(1, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("raisedetched") ) {
				typedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("loweredetched") ) {
				typedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("raisedbevel") ) {
				typedBorder = BorderFactory.createRaisedBevelBorder();
			} else if ( bordertype.equalsIgnoreCase("loweredbevel") ) {
				typedBorder = BorderFactory.createLoweredBevelBorder();
			} else if ( bordertype.equalsIgnoreCase("softraisedbevel") ) {
				typedBorder = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("softloweredbevel") ) {
				typedBorder = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("compound") ) {
				Border inner = BorderFactory.createLineBorder(linecolor, 1, false);
				Border outer = BorderFactory.createLineBorder(shadowcolor, 1, false);
				typedBorder = BorderFactory.createCompoundBorder(inner, outer);
			}
		} else {
			typedBorder = BorderFactory.createLineBorder(linecolor, 1, false);
		}
		
		// Set title alignment
		if (titlealignment != null && !titlealignment.isEmpty()) {
			if (titlealignment.equalsIgnoreCase("left")) {
				titleAlignment = TitledBorder.LEFT;
			} else if (titlealignment.equalsIgnoreCase("center")) {
				titleAlignment = TitledBorder.CENTER;
			} else if (titlealignment.equalsIgnoreCase("right")) {
				titleAlignment = TitledBorder.RIGHT;
			}
		}
		
		TitledBorder border = BorderFactory.createTitledBorder( typedBorder, title );
		border.setTitleJustification(titleAlignment);
//		TitledBorder border = BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.darkGray, 1), title );
		if (ss != null && ss instanceof String) {
			String style = (String) ss;
			Font titleFont = border.getTitleFont();
			if (!style.contains("p")) {
				if (style.contains("b")) {
					border.setTitleFont(titleFont.deriveFont(Font.BOLD | titleFont.getStyle()));
					titleFont = border.getTitleFont();
				}
				if (style.contains("i")) {
					border.setTitleFont(titleFont.deriveFont(Font.ITALIC | titleFont.getStyle()));
				}
			} else {
				border.setTitleFont(titleFont.deriveFont(Font.PLAIN));
			}
		}
		return border;
	}
}
