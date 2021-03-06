/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.document.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

public final class Money extends NavajoType implements Comparable<Money> {

    private static final long serialVersionUID = -6669128119091978210L;

    private static final int INTERNAL_DECIMAL_PLACES = 4;

    private static final Logger logger = LoggerFactory.getLogger(Money.class);
    private Double value = null;
    private static DecimalFormat nf = new DecimalFormat("\u00A4 #,##0.00;\u00A4 -#,##0.00");
    private static DecimalFormat internalTmlFormat = new DecimalFormat("0.00");
    private static NumberFormat editingFormat = NumberFormat.getInstance();

    private DecimalFormat customFormat = null;

    static {
        internalTmlFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        internalTmlFormat.setGroupingUsed(false);
        internalTmlFormat.setMaximumFractionDigits(2);
        internalTmlFormat.setMinimumFractionDigits(2);
    }

    /**
     * Create a new Money object from a given Double
     */
    public Money(Double d) {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        setValue(d);
    }

    private void setValue(Double d) {

        value = d;
        roundValue();
    }

    /**
     * Create a new Money object from a given Double and with a given subtype
     */
    public Money(Double d, String subtype) {

        super(Property.MONEY_PROPERTY, subtype);
        setupSubtypes();
        setValue(d);
    }

    /**
     * Create a new Money object
     */
    public Money() {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        value = null;
    }

    /**
     * Create a new Money object from an arbitrary Object
     */
    public Money(Object o) {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        if (o instanceof Money) {
            value = ((Money) o).value;
        } else if (o instanceof Double) {
            setValue((Double) o);
        } else if (o instanceof Integer) {
            setValue(Double.valueOf((Integer) o));
        } else if (o instanceof Long) {
            setValue(Double.valueOf((Long) o));
        } else if (o instanceof String && !((String) o).trim().equals("")) {
            Pattern p = Pattern.compile("-?[0-9]+[.,]?[0-9]{0,2}");
            Matcher m = p.matcher((String) o);
            boolean isMoneyFormat = m.find();

            if (isMoneyFormat) {
                setValue(Double.valueOf(((String) o).substring(m.start(), m.end()) + ""));
            } else {
                setValue(Double.valueOf(o + ""));
            }
        } else {
            value = null;
        }
    }

    /**
     * Create a new Money object from an Integer
     */
    public Money(Integer d) {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        if (d != null) {
            setValue(Double.valueOf(d));
        }
    }

    /**
     * Create a new Money object from an int
     */
    public Money(int d) {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        setValue(Double.valueOf(d));
    }

    /**
     * Create a new Money object from a double
     */
    public Money(double d) {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        setValue(d);
    }

    /**
     * Create a new Money object from a double and with a given subtype
     */
    public Money(double d, String subtype) {

        super(Property.MONEY_PROPERTY, subtype);
        setupSubtypes();
        setValue(d);
    }

    /**
     * Create a new Money object from a String
     */
    public Money(String d) {

        super(Property.MONEY_PROPERTY);
        setupSubtypes();
        d = d.replaceAll("\\.", "");
        if (d.indexOf(',') != -1) {
            d = d.replace(',', '.');
        }
        try {
            if (d != null && !d.trim().equals("")) {
                setValue(Double.valueOf(d));
            }
        } catch (Throwable t) {
            value = null;
        }
    }

    private void roundValue() {

        if (value == null) {
            return;
        }

        value = round(value, INTERNAL_DECIMAL_PLACES);
    }

    private double round(double d, int decimalPlace) {

        // see the Javadoc about why we use a String in the constructor
        // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void setupSubtypes() {

        String format = getSubType("format");
        if (format != null) {
            customFormat = new DecimalFormat(format);
        }
    }

    /**
     * Return formatted representation of this Money object (incl. the currency character)
     */
    public String formattedString() {

        if (value == null) {
            return "-";
        }

        if (customFormat != null) {
            logger.info("FOrmatting money with customformat: " + customFormat.toPattern());
            return customFormat.format(value);

        }

        return nf.format(value);
    }

    public String editingString() {

        if (value == null) {
            return "-";
        }

        return editingFormat.format(value);
    }

    @Override
    public String toTmlString() {

        if (value == null) {
            return "";
        }

        return internalTmlFormat.format(value);
    }

    /**
     * Get the String representation of this Money object
     */
    @Override
    public String toString() {

        if (value == null) {
            return "";
        }

        return internalTmlFormat.format(value).replace(',', '.');
    }

    /**
     * Get this Money object's value as a double
     */
    public final double doubleValue() {

        if (value == null) {
            return 0;
        }

        return value.doubleValue();
    }

    @Override
    public final int compareTo(Money o) {

        if (o == null) {
            return 0;
        }

        Money other = o;
        if (other.doubleValue() == this.doubleValue()) {
            return 0;
        } else if (this.doubleValue() > other.doubleValue()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * TODO WTF?
     */
    @Override
    public int hashCode() {

        if (value == null) {
            return 312321;
        }

        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (value == null && obj == null) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof Money) {
            Money m = (Money) obj;
            if (m.value == null) {
                return value == null;
            } else {
                if (value == null) {
                    return false;
                } else {
                    return compareTo(m) == 0;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

}
