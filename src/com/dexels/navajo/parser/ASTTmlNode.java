package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import java.util.*;

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

    /**
    try {
      int a = Integer.parseInt(value);
      return new Integer(a);
    } catch (NumberFormatException p1) {
      try {
        double d = Double.parseDouble(value);
        return new Double(d);
      } catch (NumberFormatException p2) {
        try {
          Date a = com.dexels.navajo.util.Util.getDate(value);
          return a;
        } catch (Exception e) {
          return value;
        }
      }
    }*/
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
        return new Integer(parentSel.getValue());
      else
        throw new TMLExpressionException("No name or value specified for selection: " + val);
    }

    if (!exists)
      val = val.substring(1, val.length());
    else
      val = val.substring(2, val.length());

    com.dexels.navajo.util.Util.debugLog("in ASTTmlNode()");
    com.dexels.navajo.util.Util.debugLog("val = " + val);
    com.dexels.navajo.util.Util.debugLog("parentMsg = " + parentMsg);
    if (parentMsg != null)
      com.dexels.navajo.util.Util.debugLog("ParentMsg name = " + parentMsg.getName());

      if (Util.isRegularExpression(val))
       singleMatch = false;
      else
       singleMatch = true;

      try {
        if (parentMsg == null) {
          if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1)
            match = doc.getProperties(val);
         else
            throw new TMLExpressionException("No parent message present for property: " + val);
       }
        else {
          if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1)
           match = parentMsg.getProperties(val);
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
        else if (exists) { // Check for existence
          boolean b = false;
          if (prop != null) {
            // Check type. If integer, float or date type and if is empty
            String type = prop.getType();
            if (type.equals(Property.INTEGER_PROPERTY) || type.equals(Property.FLOAT_PROPERTY) || type.equals(Property.DATE_PROPERTY)) {
              if (prop.getValue().equals(""))
                return new Boolean(false);
              else
                return new Boolean(true);
            } else
              return new Boolean(true);
          } else
            return new Boolean(false);
        }
        String value = prop.getValue();
        // Determine type
        String type = prop.getType();

        com.dexels.navajo.util.Util.debugLog("exists = " + exists);
        Util.debugLog("selectionOption = " + selectionOption);

        if (type.equals(Property.SELECTION_PROPERTY)) {
          if (prop.getCardinality().equals("1")) { // Uni-selection property.
            try {
              ArrayList list = prop.getAllSelectedSelections();
              if (list.size() > 0) {
                Selection sel = (Selection) list.get(0);
                resultList.add(determineType((selectionOption.equals("name")) ? sel.getName() : sel.getValue()));
                //return determineType((selectionOption.equals("name")) ? sel.getName() : sel.getValue());
              }
              else
                throw new TMLExpressionException("No selected options for selection property: " + prop.getName());
            } catch (com.dexels.navajo.document.NavajoException te) {
              throw new TMLExpressionException(te.getMessage());
            }
          } else { // Multi-selection property.
              //throw new TMLExpressionException("Multiselection properties not supported in expression: " + prop.getName());
            try {
                ArrayList list = prop.getAllSelectedSelections();
                ArrayList result = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                  Selection sel = (Selection) list.get(i);
                  Object o =  determineType((selectionOption.equals("name")) ? sel.getName() : sel.getValue());
                  result.add(o);
                }
                resultList.add(result);
                //return result;
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
        }
        else if (type.equals(Property.FLOAT_PROPERTY)) {
          try {
            resultList.add(new Double(value));
          } catch (Exception e) {
            throw new TMLExpressionException("Could not parse float value: " + e.getMessage());
          }
        }
        else if (type.equals(Property.STRING_PROPERTY)) {
          try {
            resultList.add(new String(value));
          } catch (Exception e) {
            throw new TMLExpressionException(e.getMessage());
          }
        }
        else if (type.equals(Property.DATE_PROPERTY)) {
          if (!option.equals("")) {
          try {
             Date a = com.dexels.navajo.util.Util.getDate(prop.getValue());
             Calendar cal = Calendar.getInstance();
              cal.setTime(a);
              int altA = 0;
              if (option.equals("month")) {
                  altA = cal.get(Calendar.MONTH)+1;
              } else if (option.equals("day")) {
                  altA = cal.get(Calendar.DAY_OF_MONTH);
              } else if (option.equals("year")) {
                  altA = cal.get(Calendar.YEAR);
              }  else {
                throw new TMLExpressionException("Option not supported: " + option+ ", for type: " + type);
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
          Calendar cal = Calendar.getInstance();
          java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
          try {
            cal.setTime(format.parse(value));
          } catch (java.text.ParseException pe) {
            throw new TMLExpressionException("Invalid date given: " + value);
          }
          resultList.add(cal.getTime());
         }
        }
        else
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
