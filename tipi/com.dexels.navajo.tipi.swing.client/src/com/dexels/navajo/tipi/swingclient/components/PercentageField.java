package com.dexels.navajo.tipi.swingclient.components;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Percentage;

public class PercentageField extends AbstractPropertyField implements
		PropertyControlled {
	private static final long serialVersionUID = -4614624658588347172L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PercentageField.class);
	
	private DecimalFormat myEditFormat = (DecimalFormat) NumberFormat
			.getInstance();

	public PercentageField() {
		myEditFormat.setGroupingUsed(false);
		myEditFormat.setDecimalSeparatorAlwaysShown(false);
		myEditFormat.getDecimalFormatSymbols().setDecimalSeparator(',');
		myEditFormat.getDecimalFormatSymbols().setGroupingSeparator('.');
		myEditFormat.setMaximumFractionDigits(2);
		myEditFormat.setMinimumFractionDigits(2);
		setHorizontalAlignment(SwingConstants.RIGHT);
	}

	@Override
	protected String getEditingFormat(Object o) {
		Percentage p = (Percentage) o;
		double d = p.doubleValue();
		logger.info("Double: " + d);
		return myEditFormat.format(d * 100);
	}

	@Override
	protected Object parseProperty(String text) {
		try {
			Number b = myEditFormat.parse(text);
			return new Percentage(b.doubleValue() / 100);
		} catch (ParseException e) {
			return new Percentage();
		}
	}

	@Override
	protected String getPresentationFormat(Object newValue) {
		Percentage p = (Percentage) newValue;
		return p.formattedString();
	}

	@Override
	public void update() {
		updateProperty();
	}

	final class PercentageNumberDocument extends PlainDocument {
		private static final long serialVersionUID = -5356270889071289298L;
		boolean hasFocus = false;

		@Override
		public final void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (hasFocus) {
				insertFocusString(offs, str, a);
			} else {
				insertNonFocusString(offs, str, a);
			}
		}

		private final void insertFocusString(int offs, String str,
				AttributeSet a) throws BadLocationException {
			char[] source = str.toCharArray();
			char[] result = new char[source.length];
			int j = 0;
			DecimalFormat nf = (DecimalFormat) NumberFormat
					.getPercentInstance();
			char decSep = nf.getDecimalFormatSymbols().getDecimalSeparator();
			for (int i = 0; i < result.length; i++) {
				if (Character.isDigit(source[i])
						|| (i == 0 && source[i] == '-') || source[i] == decSep
						|| source[i] == '.') {
					result[j++] = source[i];
				}
			}
			super.insertString(offs, new String(result, 0, j), a);
		}

		private final void insertNonFocusString(int offs, String str,
				AttributeSet a) throws BadLocationException {
			super.insertString(offs, str, a);
		}

		public final void setFocused(boolean b) {
			hasFocus = b;
		}
	}

}
