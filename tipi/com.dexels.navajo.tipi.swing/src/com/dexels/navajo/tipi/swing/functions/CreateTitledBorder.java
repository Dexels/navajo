package com.dexels.navajo.tipi.swing.functions;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.tipi.components.swingimpl.layout.TipiRoundedBorder;
import com.dexels.navajo.tipi.components.swingimpl.layout.TipiTextBalloonBorder;

/**
 * @author frank
 * 
 */
public class CreateTitledBorder extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(CreateTitledBorder.class);

	private Color defaultLineColor = Color.BLACK;
	private Color defaultShadowColor = Color.DARK_GRAY;
	private int titleAlignment = TitledBorder.LEFT;
	private Color startGradientColor = Color.RED;
	private Color endGradientColor = Color.WHITE;
	private int lineSize = 1;
	
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

    @Override
	public boolean isPure() {
    		return true;
    }

	// GetComponent({component://init/desktop},{event:/from})
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = operand(0).value;
		String bordertype = null;
		Color linecolor = this.defaultLineColor;
		Color shadowcolor = this.defaultShadowColor;
		String titlealignment = null;
		final Object ss;
		if (getOperands().size() >= 2) {
			bordertype = getStringOperand(1);
		}
		if (getOperands().size() >= 3) {
			linecolor = (Color)operand(2).value;
		}
		if (getOperands().size() >= 4) {
			shadowcolor = (Color)operand(3).value;
		}
		if (getOperands().size() >= 5) {
			titlealignment = (String)operand(4).value;
		}
		if (getOperands().size() >= 6) {
			ss = operand(5).value;
		} else {
		    ss = null;
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
				typedBorder = new TipiRoundedBorder(linecolor, lineSize);
				// typedBorder = BorderFactory.createLineBorder(linecolor, lineSize, true);
			} else if ( bordertype.equalsIgnoreCase("window") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "vertical", false);
			} else if ( bordertype.equalsIgnoreCase("rounded") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, null, null, "horizontal", false);
			} else if ( bordertype.equalsIgnoreCase("roundedgradient_horizontal") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "horizontal", false);
			} else if ( bordertype.equalsIgnoreCase("roundedgradient_vertical") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "vertical", false);
			} else if ( bordertype.equalsIgnoreCase("roundedbordergradient_horizontal") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, Color.DARK_GRAY, Color.LIGHT_GRAY, "horizontal", true);
//				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "horizontal", true);
			} else if ( bordertype.equalsIgnoreCase("roundedbordergradient_vertical") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "vertical", true);
			} else if ( bordertype.equalsIgnoreCase("roundedshadow") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("roundedshadowgradient_horizontal") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "horizontal", false, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("roundedshadowgradient_vertical") ) {
				typedBorder = new TipiRoundedBorder(linecolor, lineSize, Color.BLUE, Color.GREEN, "vertical", false, shadowcolor);
//				typedBorder = new TipiRoundedBorder(linecolor, lineSize, startGradientColor, endGradientColor, "vertical", false, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("balloon") || bordertype.equalsIgnoreCase("textballoon") ) {
				typedBorder = new TipiTextBalloonBorder(linecolor, lineSize);
			} else if ( bordertype.equalsIgnoreCase("etched") ) {
				// typedBorder = BorderFactory.createEtchedBorder(lineSize, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("raisedetched") ) {
				// typedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("loweredetched") ) {
				// typedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("raisedbevel") ) {
				typedBorder = BorderFactory.createRaisedBevelBorder();
			} else if ( bordertype.equalsIgnoreCase("loweredbevel") ) {
				typedBorder = BorderFactory.createLoweredBevelBorder();
			} else if ( bordertype.equalsIgnoreCase("softraisedbevel") ) {
				// typedBorder = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("softloweredbevel") ) {
				// typedBorder = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, linecolor, shadowcolor);
			} else if ( bordertype.equalsIgnoreCase("compound") ) {
				// Border inner = BorderFactory.createLineBorder(linecolor, lineSize, false);
				Border inner = BorderFactory.createLineBorder(linecolor, lineSize);
				// Border outer = BorderFactory.createLineBorder(shadowcolor, lineSize, false);
				Border outer = BorderFactory.createLineBorder(shadowcolor, lineSize);
				typedBorder = BorderFactory.createCompoundBorder(inner, outer);
			}
		} else {
			typedBorder = BorderFactory.createLineBorder(linecolor, lineSize);
			// typedBorder = BorderFactory.createLineBorder(linecolor, lineSize, false);
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
		
        TitledBorder result = null;
        if (!SwingUtilities.isEventDispatchThread()) {

            Border typedBorderParse = typedBorder;

            FutureTask<TitledBorder> createrTast = new FutureTask<TitledBorder>(new Callable<TitledBorder>() {
                @Override
                public TitledBorder call() {
                    return createBorder(ss, title, typedBorderParse);
                }
            });

            try {
                SwingUtilities.invokeAndWait(createrTast);
                result = createrTast.get();
            } catch (InvocationTargetException | InterruptedException | ExecutionException e) {
                logger.error("Exception on running task: - unable to create border!", e);
            }
        } else {
        		result = createBorder(ss, title, typedBorder);
		}
        return result;
	}

    private TitledBorder createBorder(Object ss, String title, Border typedBorder) {
        TitledBorder border;
        TitledBorder result;
        border = BorderFactory.createTitledBorder( typedBorder, title );
        border.setTitlePosition(2); // default position is in the border
        border.setTitleJustification(titleAlignment);
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
         result = border;
        return result;
    }
}
