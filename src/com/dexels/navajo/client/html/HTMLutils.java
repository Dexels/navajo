

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client.html;


import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.dexels.navajo.document.*;

public class HTMLutils {

    private static String YEAR = "_YEAR_";
    private static String MONTH = "_MONTH_";
    private static String DAY = "_DAY_";
    private static int MAX_CHECKBOX_LENGTH = 10;

    public HTMLutils() {}

    public static String generateCheckBoxList(String description, String message, ArrayList v) {

        int i;
        StringBuffer buffer = new StringBuffer();
        int nrColumns = 0;

        nrColumns = (int) (v.size() / MAX_CHECKBOX_LENGTH);

        if (nrColumns > 4)
            nrColumns = 4;


        buffer.append("<TR><TD>" + description + "</TD><TD>\n");
        buffer.append("<TABLE>\n");

        buffer.append("<TR>\n");
        for (i = 0; i < v.size(); i++) {

            if (((nrColumns == 0) && (i > 0))
                    || ((nrColumns > 0) && (i % nrColumns == 0) && (i > 0))) {
                // Create new row.
                buffer.append("</TR><TR>\n");
            }

            buffer.append("<TD>");
            Selection s = (Selection) v.get(i);

            buffer.append("<input type=checkbox name=");
            buffer.append(message + ":" + s.getName());
            buffer.append(" value=\"false\">");
            buffer.append(s.getName() + "\n");
            buffer.append("</TD>\n");

        }
        buffer.append("</TR></TABLE>\n");
        buffer.append("</TD></TR>\n");

        return buffer.toString();
    }

    public static String generateSelectList(String description, String name, ArrayList v) {

        int i;
        StringBuffer buffer = new StringBuffer();

        buffer.append("<TR><TD>" + description + "</TD><TD>");
        buffer.append("<select name=\"");
        buffer.append(name + "\">");

        for (i = 0; i < v.size(); i++) {

            Selection s = (Selection) v.get(i);

            buffer.append("<option value=\"");
            buffer.append(s.getName() + "\">");
            buffer.append(s.getName() + "</option>\n");
        }

        buffer.append("</select>");
        buffer.append("</TD></TR>");

        return buffer.toString();
    }

    public static String generateDateInputField(String description, String name, String value) throws java.text.ParseException {

        int year, month, day;
        Date date = new Date();
        String result = "";

        java.text.SimpleDateFormat parser = new java.text.SimpleDateFormat("yyyy-MM-dd");

        if (!value.equals("")) {
            date = parser.parse(value);
        }

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        result = "<TR><TD>" + description + "</TD><TD><input type=text name=\""
                + name + DAY + "\" value=\"" + day + "\" size=2>"
                + "<input type=text name=\"" + name + MONTH + "\" value=\""
                + month + "\" size=2>" + "<input type=text name=\"" + name
                + YEAR + "\" value=\"" + year + "\" size=4></TD></TR>";

        return result;
    }

    public static String generateDateOutputField(String description, String value) {

        String result;

        result = "<TR><TD>" + description + "</TD><TD>" + value + "</TD></TR>\n";

        return result;
    }

    public static String generateInputField(String description, String name, String length,
            String value) {

        String result;

        result = "<TR><TD>" + description + "</TD><TD><input type=text name=\""
                + name + "\" value=\"" + value + "\" size=" + length
                + "></TD></TR>\n";

        return result;
    }

    public static String generatePasswordField(String description, String name, String length,
            String value) {

        String result;

        result = "<TR><TD>" + description
                + "</TD><TD><input type=password name=\"" + name + "\" value=\""
                + value + "\" size=" + length + "></TD></TR>\n";

        return result;
    }

    public static String generateCheckboxField(String description,
            String name, String value) {
        String result;

        result = "<TR><TD>" + description
                + "</TD><TD><input type=checkbox name=\"" + name + "\" value=\""
                + value + "\"></TD></TR>\n";

        return result;
    }

    public static String generateOutputField(String description, String value) {

        String result;

        result = "<TR><TD>" + description + "</TD><TD>" + value + "</TD></TR>\n";

        return result;

    }

    public static String generateActions(ArrayList actions) {

        StringBuffer buffer;
        String methodName = "";
        int i, j;
        Vector req, v;
        boolean found;

        buffer = new StringBuffer();
        buffer.append("");

        for (i = 0; i < actions.size(); i++) {
            Method m = (Method) actions.get(i);

            methodName = m.getName();
            buffer.append("<TR><TD><INPUT type=submit name=command value=" + methodName + "></TD><TD>\n");
        }

        return buffer.toString();
    }

    public static String readHTMLForm(Navajo tbMessage, HttpServletRequest request) throws NavajoException {

        int i, j;
        String rawName, messageName, property, value, type, cardinality = "";
        Vector props;
        ArrayList v;
        StringBuffer dummy = new StringBuffer();

        NavajoFactory.getInstance().createProperty(tbMessage, "nr", Property.INTEGER_PROPERTY, "value", 1, "", Property.DIR_OUT);
        Enumeration allParameters = request.getParameterNames();

        Header header = tbMessage.getHeader();
        if (header == null) {
          header = NavajoFactory.getInstance().createHeader(tbMessage, "", "", "", -1);
          tbMessage.addHeader(header);
        }
        if (request != null) {
          String objectValue = "";
          String objectName = "";
          String interrupt = "";
          // Determine if any header parameters are set.
          Enumeration all = request.getParameterNames();
          while (all.hasMoreElements()) {
            String name = (String) all.nextElement();
            //System.out.println("PARAMETER NAME: " + name);
            if (name.startsWith("header.callback.")) {
              if (!name.endsWith(".interrupt")) {
                objectValue = request.getParameter(name);
                objectName = name.substring("header.callback.".length());
                // Check if interrupt is given.
                interrupt = request.getParameter("header.callback." + objectName +
                                                 ".interrupt");
                System.out.println("HEADER PARAMETER OBJECT: " + objectName +
                                   ", VALUE = " + objectValue + ", INTERRUPT = " +
                                   interrupt);
                header.setCallBack(objectName, objectValue, 0, false, interrupt);
              }
            }
          }
  }


        tbMessage.clearAllSelections();

        while (allParameters.hasMoreElements()) {
            rawName = (String) allParameters.nextElement();
            //System.out.println("Raw parameter: " + rawName);
            if (rawName.endsWith(YEAR)) {
                int end = rawName.indexOf(YEAR);
                rawName = rawName.substring(0, end);
            }
            Property prop = tbMessage.getProperty(rawName);

            if (prop != null) {
                if (prop.getType().equals(Property.SELECTION_PROPERTY)) {
                    prop.clearSelections();
                }
            }
        }

        allParameters = request.getParameterNames();

        while (allParameters.hasMoreElements()) {
            rawName = (String) allParameters.nextElement();


            // Check if it is a date fields. Only pick one, for example YEAR field. Ignore the rest.
            if (rawName.endsWith(YEAR)) {
                int end = rawName.indexOf(YEAR);
                rawName = rawName.substring(0, end);
            }

            Property prop = tbMessage.getProperty(rawName);
            Selection mySel = tbMessage.getSelection(rawName);

            dummy.append(rawName);
            dummy.append("\n");


            // Check if property exists
            if (prop != null) {
                type = prop.getType();


                if (type.equals("selection")) {
                    cardinality = prop.getCardinality();

                }
                // Process selection from Checklists and radiobuttons
                if (type.equals("selection") && cardinality.equals("1")) {
                    value = request.getParameter(rawName);

                    v = prop.getAllSelections();
                    for (int k = 0; k < v.size(); k++) {
                        Selection sel = (Selection) v.get(k);

                        if (sel.getName().equals(value)) {
                            sel.setSelected(true);

                        } else {
                            sel.setSelected(false);

                        }
                    }
                } else if (type.equals("selection") && cardinality.equals("+")) {

                    v = prop.getAllSelections();
                    for (int k = 0; k < v.size(); k++) {
                        Selection sel = (Selection) v.get(k);

                        value = request.getParameter(rawName);


                        if (sel.getName().equals(mySel.getName()))
                            sel.setSelected(true);

                    }
                } else  if (type.equals("string") || type.equals("integer")
                        || type.equals("float")
                        || type.equals(Property.PASSWORD_PROPERTY) || type.equals(Property.MONEY_PROPERTY) || type.equals(Property.CLOCKTIME_PROPERTY)) {
                    value = request.getParameter(rawName);
                    if (!(request.getParameter(rawName).equals("") && prop.getValue() == null))
                      prop.setValue(value);
                } else if (type.equals("date")) {
                    String year = request.getParameter(rawName + YEAR);
                    String month = request.getParameter(rawName + MONTH);
                    String day = request.getParameter(rawName + DAY);

                    if (year.equals("") || month.equals("") || day.equals("")) {
                        System.out.println("EMPTY DATE GIVEN IN HTML CLIENT");
                        prop.setValue("");
                    } else
                        prop.setValue(year + "-" + month + "-" + day);
                } else if (type.equals("boolean")) {
                    value = request.getParameter(rawName);
                    prop.setValue(Property.TRUE);
                }
            } // end if (type != null)
        } // end while()

        // for debugging
        return dummy.toString();
    }
}
