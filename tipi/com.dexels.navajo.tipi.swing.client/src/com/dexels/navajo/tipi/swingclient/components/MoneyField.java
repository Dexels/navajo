package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.SwingConstants;

import com.dexels.navajo.document.types.Money;

public class MoneyField extends AbstractPropertyField implements
		PropertyControlled {
	private static final long serialVersionUID = 2015322918566278566L;
	private DecimalFormat myEditFormat = (DecimalFormat) NumberFormat
			.getInstance();

	public MoneyField() {
		this(true);
		
	}
	public MoneyField(Boolean addFocusLostListener) {
		super(addFocusLostListener);
		myEditFormat.setGroupingUsed(false);
		myEditFormat.setDecimalSeparatorAlwaysShown(false);
		myEditFormat.getDecimalFormatSymbols().setDecimalSeparator(',');
		myEditFormat.getDecimalFormatSymbols().setGroupingSeparator('.');
		myEditFormat.setMaximumFractionDigits(2);
		myEditFormat.setMinimumFractionDigits(2);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setOpaque(true);

	}

	@Override
	protected String getEditingFormat(Object o) {
		Money p = (Money) o;
		updateColor(p);
		if (p.isEmpty()) {
			return "";
		}
		double d = p.doubleValue();
		return myEditFormat.format(d);
	}

	@Override
	protected Object parseProperty(String text) {
		text = text.replaceAll("\\.", ",");
		try {
			Number b = myEditFormat.parse(text);
			Money money = new Money(b.doubleValue());
			updateColor(money);
			return money;
		} catch (ParseException e) {
			return new Money();
		}
	}

	@Override
	protected String getPresentationFormat(Object newValue) {
		if (newValue instanceof Money) {
			Money p = (Money) newValue;
			updateColor(p);
			return p.formattedString();
		} else {
			return "";
		}
	}

	public void updateColor(Money value) {
		if (getProperty() == null) {
			return;
		}
		if (isEnabled()) {
			super.setForeground(value.doubleValue() < 0 ? Color.RED
					: Color.BLACK);
		} else {
			// Has no effect, look and feel overrides this to GRAY (both substance and metal do this!!)
			super.setForeground(value.doubleValue() < 0 ? Color.PINK.darker()
					: Color.GRAY);
		}

	}

	@Override
	public void setForeground(Color c) {
		// ignore, otherwise the money colors get overridden
	}

}
