package com.dexels.navajo.document;

import java.util.ArrayList;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;

public class DocumentUtils {
    public static final String determineNavajoType(Object o)  {

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
       else if (o instanceof ArrayList)
           return Property.SELECTION_PROPERTY;
       else if (o instanceof Boolean)
           return Property.BOOLEAN_PROPERTY;
       else if (o.getClass().getName().startsWith("[Ljava.util.Vector"))
           return Property.POINTS_PROPERTY;
       // Added by arjen 19/2/2004.
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
       else if (o instanceof Selection []) {
       	return Property.SELECTION_PROPERTY;
       }
       else
         return "unknown";

//           throw new TMLExpressionException("Could not determine NavajoType for Java type: " + o.getClass().getName());
   }
	
}
