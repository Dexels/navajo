package com.dexels.navajo.tipi.swingclient.components;

import java.text.*;

import javax.swing.*;
import javax.swing.text.*;

import com.dexels.navajo.document.types.*;

public class PercentageField extends AbstractPropertyField implements PropertyControlled {
	private DecimalFormat myEditFormat = (DecimalFormat) DecimalFormat.getInstance();
	public PercentageField() {
		myEditFormat.setGroupingUsed(false);
		myEditFormat.setDecimalSeparatorAlwaysShown(false);
		myEditFormat.getDecimalFormatSymbols().setDecimalSeparator(',');
		myEditFormat.getDecimalFormatSymbols().setGroupingSeparator('.');
		myEditFormat.setMaximumFractionDigits(2);
		myEditFormat.setMinimumFractionDigits(2);
		setHorizontalAlignment(JTextField.RIGHT);
	}
	
	protected String getEditingFormat(Object o) {
		Percentage p = (Percentage)o;
		double d = p.doubleValue();
		System.err.println("Double: "+d);
		return myEditFormat.format(d*100);
	}

	protected Object parseProperty(String text) {
		try {
			Number b = myEditFormat.parse(text);
			return new Percentage(b.doubleValue()/100);
		} catch (ParseException e) {
			return new Percentage();
		}
	}
	
	protected String getPresentationFormat(Object newValue) {
		Percentage p = (Percentage)newValue;
		return p.formattedString();
	}

	public void gainFocus() {
		
	}


	public boolean isGhosted() {
		// TODO Auto-generated method stub
		return false;
	}


	public void setGhosted(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void update() {
		updateProperty();
	}
	final class PercentageNumberDocument extends PlainDocument {
		  boolean hasFocus = false;

		  public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		    if (hasFocus) {
		      insertFocusString(offs,str,a);
		    } else {
		      insertNonFocusString(offs,str,a);
		    }
		  }

		  private final void insertFocusString(int offs, String str, AttributeSet a) throws BadLocationException {
		    char[] source = str.toCharArray();
		    char[] result = new char[source.length];
		    int j = 0;
		    DecimalFormat nf = (DecimalFormat)DecimalFormat.getPercentInstance();
		    char decSep = nf.getDecimalFormatSymbols().getDecimalSeparator();
		    for (int i = 0; i < result.length; i++) {
		      if (Character.isDigit(source[i]) || (i==0 && source[i] == '-') || source[i]==decSep || source[i]=='.') {
		        result[j++] = source[i];
		      }
		    }
		    super.insertString(offs, new String(result, 0, j), a);
		  }

		  private final void insertNonFocusString(int offs, String str, AttributeSet a) throws BadLocationException {
		     super.insertString(offs, str, a);
		  }


		  public final void setFocused(boolean b) {
		    hasFocus = b;
		  }
		}

}
