package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import java.util.*;

/**
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ASTTmlNode extends SimpleNode {
    String val = "";
    Navajo doc;
    Message parentMsg;
    Selection parentSel;
    String option = "";
    String selectionOption = "";
    boolean exists = false;

    public ASTTmlNode(int id) {
        super(id);
    }

    /**
     * CHANGED (1/8/2002). DetermineType always return String (implicit typing does not always work).
     * DetermineType() should only be used for selection type value and name attributes.
     */
    private Object determineType(String value) {
        return value;
    }

    public Object interpret() throws TMLExpressionException {

        ArrayList match = null;
        ArrayList resultList = new ArrayList();
        boolean singleMatch = true;
        boolean selectionProp = false;

        Property prop = null;

        if (parentSel != null) {
            if (selectionOption.equals("name"))
                return parentSel.getName();
            else if (selectionOption.equals("value"))
                return parentSel.getValue();
        }

        if (!exists)
            val = val.substring(1, val.length());
        else
            val = val.substring(2, val.length());

        if (Util.isRegularExpression(val))
            singleMatch = false;
        else
            singleMatch = true;


        try {
            if (parentMsg == null) {
                if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
                    match = doc.getProperties(val);
                    if (match.size() > 1)
                      singleMatch = false;

                }
                else
                    throw new TMLExpressionException("No parent message present for property: " + val);
            } else {
                if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
                  match = parentMsg.getProperties(val);
                  if (match.size() > 1)
                    singleMatch = false;

                }
                else {
                    match = new ArrayList();
                    match.add(parentMsg.getProperty(val));
                }
            }
        } catch (NavajoException te) {
            throw new TMLExpressionException(te.getMessage());
        }

        for (int j = 0; j < match.size(); j++) {
            prop = (Property) match.get(j);
            if (!exists && (prop == null))
                throw new TMLExpressionException("TML property does not exist: " + val);
            else if (exists) { // Check for existence and datatype validity.
                boolean b = false;

                if (prop != null) {
                    // Check type. If integer, float or date type and if is empty
                    String type = prop.getType();
                    if (type.equals(Property.INTEGER_PROPERTY)) {
                       try {
                          Integer.parseInt(prop.getValue());
                          return new Boolean(true);
                       } catch (Exception e) {
                          return new Boolean(false);
                       }
                    } else if (type.equals(Property.FLOAT_PROPERTY)) {
                      try {
                          Double.parseDouble(prop.getValue());
                          return new Boolean(true);
                       } catch (Exception e) {
                          return new Boolean(false);
                       }
                    } else if (type.equals(Property.DATE_PROPERTY)) {
                      try {
                          com.dexels.navajo.util.Util.getDate(prop.getValue());
                          return new Boolean(true);
                       } catch (Exception e) {
                          return new Boolean(false);
                       }
                    } else
                        return new Boolean(true);
                } else
                    return new Boolean(false);
            }
            String value = prop.getValue();
            // Determine type
            String type = prop.getType();

            if (type.equals(Property.SELECTION_PROPERTY)) {
                if (!prop.getCardinality().equals("+")) { // Uni-selection property.
                    try {
                        ArrayList list = prop.getAllSelectedSelections();

                        if (list.size() > 0) {
                            Selection sel = (Selection) list.get(0);
                            resultList.add((selectionOption.equals("name") ? sel.getName() : sel.getValue()));
                        } else {
                          return null;
                        }
                    } catch (com.dexels.navajo.document.NavajoException te) {
                        throw new TMLExpressionException(te.getMessage());
                    }
                } else { // Multi-selection property.
                    try {
                        ArrayList list = prop.getAllSelectedSelections();
                        ArrayList result = new ArrayList();
                        for (int i = 0; i < list.size(); i++) {
                            Selection sel = (Selection) list.get(i);
                            Object o = (selectionOption.equals("name")) ? sel.getName() : sel.getValue();
                            result.add(o);
                        }
                        resultList.add(result);
                    } catch (com.dexels.navajo.document.NavajoException te) {
                        throw new TMLExpressionException(te.getMessage());
                    }
                }
            } else
            if (type.equals(Property.BOOLEAN_PROPERTY)) {
                try {
                    resultList.add(new Boolean(value.equals(Property.TRUE)));
                } catch (Exception e) {
                    throw new TMLExpressionException(e.getMessage());
                }
            } else
            if (type.equals(Property.INTEGER_PROPERTY)) {
                try {
                    resultList.add(new Integer(value));
                } catch (Exception e) {
                    throw new TMLExpressionException("Could not parse integer value: " + e.getMessage());
                }
            } else if (type.equals(Property.FLOAT_PROPERTY)) {
                try {
                    resultList.add(new Double(value));
                } catch (Exception e) {
                    throw new TMLExpressionException("Could not parse float value: " + e.getMessage());
                }
            } else if (type.equals(Property.STRING_PROPERTY)) {
                try {
                    resultList.add(new String(value));
                } catch (Exception e) {
                    throw new TMLExpressionException(e.getMessage());
                }
            } else if (type.equals(Property.DATE_PROPERTY)) {
                if (!option.equals("")) {
                    try {
                        Date a = com.dexels.navajo.util.Util.getDate(prop.getValue());
                        Calendar cal = Calendar.getInstance();

                        cal.setTime(a);
                        int altA = 0;

                        if (option.equals("month")) {
                            altA = cal.get(Calendar.MONTH) + 1;
                        } else if (option.equals("day")) {
                            altA = cal.get(Calendar.DAY_OF_MONTH);
                        } else if (option.equals("year")) {
                            altA = cal.get(Calendar.YEAR);
                        } else {
                            throw new TMLExpressionException("Option not supported: " + option + ", for type: " + type);
                        }
                        try {
                            resultList.add(new Integer(altA));
                        } catch (Exception e) {
                            throw new TMLExpressionException(e.getMessage());
                        }
                    } catch (com.dexels.navajo.server.UserException ue) {
                        throw new TMLExpressionException("Invalid date: " + prop.getValue());
                    }
                } else {

                    try {
                        Date a = com.dexels.navajo.util.Util.getDate(prop.getValue());
                        resultList.add(a);
                    } catch (java.lang.Exception pe) {
                        pe.printStackTrace();
                        System.out.println("Invalid date given: " + value);
                        resultList.add(null);
                    }
                }
            } else
                throw new TMLExpressionException("Unknown TML type specified: " + type);
        }

        if (!singleMatch)
            return resultList;
        else if (resultList.size() > 0)
            return resultList.get(0);
        else if (!exists)
            throw new TMLExpressionException("Property does not exist: " + val);
        else
            return new Boolean(false);
    }
}
