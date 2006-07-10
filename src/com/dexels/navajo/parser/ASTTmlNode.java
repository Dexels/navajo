package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import java.util.*;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;

/**
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public final class ASTTmlNode extends SimpleNode {
    String val = "";
    Navajo doc;
    Message parentMsg;
    Message parentParamMsg;
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
    private final static Object determineType(String value) {
        return value;
    }

    public final Object interpret() throws TMLExpressionException {

        ArrayList match = null;
        ArrayList resultList = new ArrayList();
        boolean singleMatch = true;
        boolean selectionProp = false;
        
        boolean isParam = false;

        Property prop = null;
//        System.err.println("Interpreting TMLNODE... val= "+val);
        
        if (parentSel != null) {
            String dum = val;
            if (dum.length() > 1) 
              dum = dum.substring(1, val.length());
            if (dum.equals("name") ||  selectionOption.equals("name")) {
              return parentSel.getName();
            }
            else if (dum.equals("value") || selectionOption.equals("value")) {
              return parentSel.getValue();
            } else if (dum.equals("selected") || selectionOption.equals("selected")) {
              return new Boolean(parentSel.isSelected());
            }
        }

        if (!exists) 
            val = val.substring(1, val.length());
        else
            val = val.substring(2, val.length());
        
        if (val.startsWith("@")) { // relative param property.
        	isParam = true;
        	val = val.substring(1);
        }
        
        if (val.startsWith("/@")) { // Absolute param property.
        	parentParamMsg = doc.getMessage("__parms__");
        	isParam = true;
        	val = val.substring(2);
        }
    
        if (Util.isRegularExpression(val))
            singleMatch = false;
        else
            singleMatch = true;

        try {
            if (parentMsg == null && !isParam) {
                if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
                    match = doc.getProperties(val);
                    if (match.size() > 1)
                      singleMatch = false;
                }
                else
                    throw new TMLExpressionException("No parent message present for property: " + val);
            } else if (parentParamMsg == null && isParam) {
            	parentParamMsg = doc.getMessage("__parms__");
            	 if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
                    match = doc.getProperties(val);
                    if (match.size() > 1)
                      singleMatch = false;
                }
                else
                    throw new TMLExpressionException("No parent message present for param: " + val);
            } else {
                //System.err.println("Looking for properties: "+val+" parentMessage: "+parentMsg.getFullMessageName());

                if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
                  match = (!isParam ? parentMsg.getProperties(val) : parentParamMsg.getProperties(val));
                  if (match.size() > 1)
                    singleMatch = false;

                }
                else {
                    match = new ArrayList();
                    match.add((!isParam ? parentMsg.getProperty(val) : parentParamMsg.getProperty(val)));
                }
//                System.err.println("# of matches: "+match.size());
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
//                    System.err.println("In ASTTmlNODE found prop with type: "+type);
                    // I changed getValue into getTypedValue, as it resulted in a serialization
                    // of binary properties. Should be equivalent, and MUCH faster.
                    if (prop.getTypedValue() == null && !type.equals(Property.SELECTION_PROPERTY))
                        return new Boolean(false);

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
              
              
              String type = prop.getType();
         
              /**
               * <String> property.getValue() should not be performed on binary properties
               */
              if(type.equals(Property.BINARY_PROPERTY)) {
                  
                  Binary data = (Binary) prop.getTypedValue();
                  //System.err.println("GETTING BINARY PROPERTY...., data = " + data);
                  resultList.add(data);
                  continue;
                }  
              
            String value = prop.getValue();
            // Determine type
       
//            System.out.println("in ASTTmlNode(), VALUE FOR " + prop.getName() + " = " + value + " (type = " + type + ")");

            if (value == null && !type.equals(Property.SELECTION_PROPERTY)) {  // If value attribute does not exist AND property is not selection property assume null value
               resultList.add(null);
            } else
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
            if (type.equals(Property.MONEY_PROPERTY)) {
              if (value.equals(""))
                resultList.add(null);
              else {
                try {
                  Money m = new Money(value);
                  if (m.toString() != null) {
                    resultList.add(new Money(value));
                  } else {
                    resultList.add(null);
                  }
                } catch (Throwable t) {
                  throw new TMLExpressionException("Could not parse percentage property: " + value);
                }
              }
            } else
            if (type.equals(Property.PERCENTAGE_PROPERTY)) {
               if (value.equals(""))
                 resultList.add(null);
               else {
                 try {
                   Percentage m = new Percentage(value);
                   if (m.toString() != null) {
                     resultList.add(new Percentage(value));
                   } else {
                     resultList.add(null);
                   }
                 } catch (Throwable t) {
                   throw new TMLExpressionException("Could not parse money property: " + value);
                 }
               }
             } else

            if (type.equals(Property.CLOCKTIME_PROPERTY)) {
              if (value.equals(""))
                resultList.add(null);
              else {
                try {
                  ClockTime ct = new ClockTime(value);
                  if (ct.calendarValue() != null) {
                    resultList.add(new ClockTime(value));
                  } else {
                    resultList.add(null);
                  }
                } catch (Throwable t) {
                  throw new TMLExpressionException("Could not parse clocktime property: " + value);
                }
              }

            } else
                if (type.equals(Property.STOPWATCHTIME_PROPERTY)) {
                    if (value.equals(""))
                      resultList.add(null);
                    else {
                      try {
                        if (value != null) {
                          resultList.add(new StopwatchTime(value));
                        } else {
                          resultList.add(null);
                        }
                      } catch (Throwable t) {
                        throw new TMLExpressionException("Could not parse StopwatchTime property: " + value);
                      }
                    }

                  } else
            if (type.equals(Property.BOOLEAN_PROPERTY)) {
                if (value.equals(""))
                  resultList.add(null);
                else {
                  try {
                    resultList.add(new Boolean(value.equals(Property.TRUE)));
                  }
                  catch (Exception e) {
                    throw new TMLExpressionException(e.getMessage());
                  }
                }
            } else
            if (type.equals(Property.INTEGER_PROPERTY)) {
              if (value.equals(""))
                  resultList.add(null);
                else {
                  try {
                    resultList.add(new Integer(value));
                  }
                  catch (Exception e) {
                    throw new TMLExpressionException("Could not parse integer value: " +
                                                     e.getMessage());
                  }
                }
            } else if (type.equals(Property.FLOAT_PROPERTY)) {
              if (value.equals(""))
                  resultList.add(null);
                else {
                  try {
                    resultList.add(new Double(value));
                  }
                  catch (Exception e) {
                    throw new TMLExpressionException("Could not parse float value: " +
                                                     e.getMessage());
                  }
                }
            } else if (type.equals(Property.STRING_PROPERTY)) {
                try {
                    resultList.add(new String(value));
                } catch (Exception e) {
                    throw new TMLExpressionException(e.getMessage());
                }
            } else if (type.equals(Property.DATE_PROPERTY)) {

                if (value.equals(""))
                  resultList.add(null);
                else {
                  if (!option.equals("")) {
                    try {
                      Date a = com.dexels.navajo.util.Util.getDate(prop.getValue());
                      Calendar cal = Calendar.getInstance();

                      cal.setTime(a);
                      int altA = 0;

                      if (option.equals("month")) {
                        altA = cal.get(Calendar.MONTH) + 1;
                      }
                      else if (option.equals("day")) {
                        altA = cal.get(Calendar.DAY_OF_MONTH);
                      }
                      else if (option.equals("year")) {
                        altA = cal.get(Calendar.YEAR);
                      }
                      else if (option.equals("hour")) {
                        altA = cal.get(Calendar.HOUR_OF_DAY);
                      }
                      else if (option.equals("minute")) {
                        altA = cal.get(Calendar.MINUTE);
                      }
                      else if (option.equals("second")) {
                        altA = cal.get(Calendar.SECOND);
                      }
                      else {
                        throw new TMLExpressionException("Option not supported: " +
                                                         option + ", for type: " + type);
                      }
                      try {
                        resultList.add(new Integer(altA));
                      }
                      catch (Exception e) {
                        throw new TMLExpressionException(e.getMessage());
                      }
                    }
                    catch (com.dexels.navajo.server.UserException ue) {
                      throw new TMLExpressionException("Invalid date: " + prop.getValue());
                    }
                  }
                  else {

                    try {
                      Date a = com.dexels.navajo.util.Util.getDate(prop.getValue());
                      resultList.add(a);
                    }
                    catch (java.lang.Exception pe) {
                      //pe.printStackTrace();
                      //System.out.println("Invalid date given: " + value);
                      resultList.add(null);
                    }
                  }
                }
            } else if(type.equals(Property.EXPRESSION_PROPERTY)) {
              resultList.add(prop.getTypedValue());
            } else {
                try {
                    resultList.add(new String(value));
                } catch (Exception e) {
                    throw new TMLExpressionException(e.getMessage());
                }
                //throw new TMLExpressionException("Unknown TML type specified: " + type);
            }
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
