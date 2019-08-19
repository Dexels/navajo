package com.dexels.navajo.document.types;

import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class TypeUtils {

    public static final String determineNavajoType(Object o) {
    	return determineNavajoType(o, "unknown");
    }

    public static final String determineNavajoType(Object o, String defaultType) {

        if (o == null) {
            return "";
        }
        if (o instanceof Integer)
            return Property.INTEGER_PROPERTY;
        else if (o instanceof String)
            return Property.STRING_PROPERTY;
        else if (o instanceof java.util.Date)
            return Property.DATE_PROPERTY;
        else if (o instanceof Double)
            return Property.FLOAT_PROPERTY;
        else if (o instanceof Float)
            return Property.FLOAT_PROPERTY;
        else if (o instanceof List)
            return Property.LIST_PROPERTY;
        else if (o instanceof Boolean)
            return Property.BOOLEAN_PROPERTY;
        else if (o.getClass().getName().startsWith("[Ljava.util.Vector"))
            return Property.POINTS_PROPERTY;
        else if (o instanceof Money)
            return Property.MONEY_PROPERTY;
        else if (o instanceof Percentage)
            return Property.PERCENTAGE_PROPERTY;
        else if (o instanceof ClockTime)
            return Property.CLOCKTIME_PROPERTY;
        else if (o instanceof StopwatchTime)
            return Property.STOPWATCHTIME_PROPERTY;
        // Added by frank... To enable tipi-expressions, without creating a dep
        else if (o.getClass().getName().startsWith("com.dexels.navajo.tipi"))
            return Property.TIPI_PROPERTY;
        else if (o instanceof Message)
            return Message.MSG_DEFINITION;
        else if (o instanceof Binary)
            return Property.BINARY_PROPERTY;
        else if (o instanceof Memo)
            return Property.MEMO_PROPERTY;
        else if (o instanceof Selection[]) {
            return Property.SELECTION_PROPERTY;
        } else if (o instanceof Coordinate) {
            return Property.COORDINATE_PROPERTY;
        } else if (o instanceof Property) {
            return "property";
        } else
            return defaultType;
    }

}
