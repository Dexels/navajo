package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.text.*;

import javax.swing.*;

import com.dexels.navajo.document.types.Money;

public class MoneyField extends AbstractPropertyField implements PropertyControlled {
	private DecimalFormat myEditFormat = (DecimalFormat) DecimalFormat.getInstance();
	public MoneyField() {
		myEditFormat.setGroupingUsed(false);
		myEditFormat.setDecimalSeparatorAlwaysShown(false);
		myEditFormat.getDecimalFormatSymbols().setDecimalSeparator(',');
		myEditFormat.getDecimalFormatSymbols().setGroupingSeparator('.');
		myEditFormat.setMaximumFractionDigits(2);
		myEditFormat.setMinimumFractionDigits(2);
		setHorizontalAlignment(JTextField.RIGHT);
	}
	
	protected String getEditingFormat(Object o) {
		Money p = (Money)o;
		if(p.isEmpty()) {
			return "";
		}
		double d = p.doubleValue();
		return myEditFormat.format(d);
	}

	protected Object parseProperty(String text) {
		try {
			Number b = myEditFormat.parse(text);
			Money money = new Money(b.doubleValue());
			updateColor(money);
			return money;
		} catch (ParseException e) {
			return new Money();
		}
	}
	
	protected String getPresentationFormat(Object newValue) {
		if(newValue instanceof Money) {
			Money p = (Money)newValue;
			return p.formattedString();
		} else {
			return "";
		}
	}

	public void gainFocus() {
		
	}
	
	public void updateColor(Money value) {
		if(getProperty()==null) {
			return;
		}
		if (isEnabled()) {
			setForeground(value.doubleValue()<0?Color.RED:Color.BLACK);
		} else {
			setForeground(value.doubleValue()<0?Color.PINK.darker():Color.GRAY);
		}

	}

	@Override
	public boolean isGhosted() {
		return false;
	}

	@Override
	public void setGhosted(boolean b) {
		
	}

	@Override
	public void update() {
		updateProperty();
	}
	
}
