package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Date;

public class TML {

    public TML() {}

    public static String showValue(Object aap) {
        if (aap instanceof Integer)
            return ((Integer) aap).intValue() + "";
        else if (aap instanceof Boolean)
            return ((Boolean) aap).booleanValue() + "";
        else if (aap instanceof String)
            return (String) aap;
        else if (aap instanceof Double)
            return ((Double) aap).doubleValue() + "";
        else if (aap instanceof Date)
            return ((Date) aap).toString();
        else
            return "";
    }

    public static String showList(ArrayList aap) {

        StringBuffer result = new StringBuffer();

        result.append("{");
        for (int i = 0; i < aap.size(); i++) {
            Object o = aap.get(i);

            if (o instanceof ArrayList) {
                result.append(showList((ArrayList) o));
            } else {
                result.append(showValue(o));
            }
            if ((i + 1) < aap.size())
                result.append(",");
        }
        result.append("}");
        return result.toString();
    }


}
