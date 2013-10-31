/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author Erik Versteeg
 * This function is created in order to be able to reset text that has been equipped with some bold, italic or other stuff 
 */
public class ToPlainText extends FunctionInterface {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#remarks()
     */
    @Override
	public String remarks() {
        return "Make text plain and simple. Html-style (depending on 2nd parameter including html tag)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#usage()
     */
    @Override
	public String usage() {
        return "ToPlainText(string, boolean|empty)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
     */
    @Override
	public Object evaluate() throws TMLExpressionException {
        Object pp = getOperand(0);
        String result = null;

        if (!(pp instanceof String)) {
            throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
        } else {
        	String intermediateResult = (String) pp;
        	boolean hadHtmlTag = false;
        	if (intermediateResult.startsWith("<html>") && intermediateResult.endsWith("</html>"))
        	{
        		hadHtmlTag = true;
        		intermediateResult = intermediateResult.substring(6, intermediateResult.length()-7);
        	}
        	boolean done = false;
        	while (!done)
        	{
        		// undo ToBold
            	if (intermediateResult.startsWith("<b>") && intermediateResult.endsWith("</b>"))
            	{
            		intermediateResult = intermediateResult.substring(3, intermediateResult.length()-4);
            	}
        		// undo ToItalic
            	else if (intermediateResult.startsWith("<i>") && intermediateResult.endsWith("</i>"))
            	{
            		intermediateResult = intermediateResult.substring(3, intermediateResult.length()-4);
            	}
        		// undo ToSubscript
            	else if (intermediateResult.startsWith("<sub>") && intermediateResult.endsWith("</sub>"))
            	{
            		intermediateResult = intermediateResult.substring(5, intermediateResult.length()-6);
            	}
        		// undo ToSuperscript
            	else if (intermediateResult.startsWith("<sup>") && intermediateResult.endsWith("</sup>"))
            	{
            		intermediateResult = intermediateResult.substring(5, intermediateResult.length()-6);
            	}
        		// undo ToUnderline
            	else if (intermediateResult.startsWith("<u>") && intermediateResult.endsWith("</u>"))
            	{
            		intermediateResult = intermediateResult.substring(3, intermediateResult.length()-4);
            	}
            	// remove intermediate html tags if we already started with one on the outside
            	else if (hadHtmlTag && intermediateResult.startsWith("<html>") && intermediateResult.endsWith("</html>"))
            	{
            		intermediateResult = intermediateResult.substring(6, intermediateResult.length()-7);
            	}
            	else
            	{
            		done = true;
            	}
        	}
            result = "<html>" + intermediateResult + "</html>";
            if (!hadHtmlTag && getOperands().size() > 1) {
                if (getOperand(1) instanceof Boolean && (Boolean)getOperand(1)) {
                    result = intermediateResult.toString();
                }
            }
        }
        return result;
    }
}
