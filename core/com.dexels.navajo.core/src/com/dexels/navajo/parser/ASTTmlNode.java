package com.dexels.navajo.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.util.Util;

/**
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public final class ASTTmlNode extends SimpleNode {
    String val = "";
    Navajo doc = null;
    Message parentMsg = null;
    Message parentParamMsg = null;
    Selection parentSel = null;
    String option = "";
    String selectionOption = "";
    boolean exists = false;

    public ASTTmlNode(int id) {
        super(id);
    }

    @Override
	public final Object interpret() throws TMLExpressionException {

        ArrayList match = null;
        ArrayList resultList = new ArrayList();
        boolean singleMatch = true;
       // boolean selectionProp = false;
        
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
              return Boolean.valueOf(parentSel.isSelected());
            }
        }

        if (!exists) {
            val = val.substring(1, val.length());
        }  else {
            val = val.substring(2, val.length());
        }
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
                	if(doc==null) {
                		throw new NullPointerException("Can't evaluate TML node: No parent message and no document found.");
                	}
                	match = doc.getProperties(val);
                    if (match.size() > 1) {
                      singleMatch = false;
                    }
                }
                else {
                   throw new TMLExpressionException("No parent message present for property: " + val);
                }
            } else if (parentParamMsg == null && isParam) {
            	parentParamMsg = doc.getMessage("__parms__");
            	 if (val.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
                    match = doc.getProperties(val);
                    if (match.size() > 1) {
                       singleMatch = false;
                    }  
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
            	  if (parentMsg!=null) {
                      throw new TMLExpressionException("TML property does not exist: " + val+" parent message: "+parentMsg.getFullMessageName());
				} else {
	                throw new TMLExpressionException("TML property does not exist: " + val);
				}
            else if (exists) { // Check for existence and datatype validity.
                if (prop != null) {
                    // Check type. If integer, float or date type and if is empty
                    String type = prop.getType();
                   
                    // I changed getValue into getTypedValue, as it resulted in a serialization
                    // of binary properties. Should be equivalent, and MUCH faster.
                    if (prop.getTypedValue() == null && !type.equals(Property.SELECTION_PROPERTY)) {
                        return Boolean.valueOf(false);
                    }

                    if (type.equals(Property.INTEGER_PROPERTY)) {
                       try {
                          Integer.parseInt(prop.getValue());
                          return Boolean.valueOf(true);
                       } catch (Exception e) {
                          return Boolean.valueOf(false);
                       }
                    } else if (type.equals(Property.FLOAT_PROPERTY)) {
                      try {
                          Double.parseDouble(prop.getValue());
                          return Boolean.valueOf(true);
                       } catch (Exception e) {
                          return Boolean.valueOf(false);
                       }
                    } else if (type.equals(Property.DATE_PROPERTY)) {
                    	try {
                    		if ( prop.getTypedValue() instanceof Date ) {
                    			return Boolean.valueOf(true);
                    		} else {
                    			return Boolean.valueOf(false);
                    		}
                    	} catch (Exception e) {
                    		return Boolean.valueOf(false);
                    	}
                    } else if ( type.equals(Property.CLOCKTIME_PROPERTY)) {
                    	try {
                            ClockTime ct = new ClockTime(prop.getValue());
                            if ( ct.calendarValue() == null ) {
                            	return Boolean.valueOf(false);
                            }
                            return Boolean.valueOf(true);
                         } catch (Exception e) {
                            return Boolean.valueOf(false);
                         }
                    } else
                        return Boolean.valueOf(true);
                } else
                    return Boolean.valueOf(false);
            }
              
              
            String type = prop.getType();
              
            Object value = prop.getTypedValue();

            /** 
             * LEGACY MODE! 
             */
            if ( value instanceof NavajoType && ((NavajoType) value).isEmpty() ) {
            	value = null;
            }
            /**
             * END OF LEGACY MODE!
             */
            
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
            if (type.equals(Property.DATE_PROPERTY)) {
                if (value == null )
                  resultList.add(null);
                else {
                  if (!option.equals("")) {
                    try {
                      Date a = (Date) prop.getTypedValue();
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
                        resultList.add(Integer.valueOf(altA));
                      }
                      catch (Exception e) {
                        throw new TMLExpressionException(e.getMessage());
                      }
                    }
                    catch (Exception ue) {
                      throw new TMLExpressionException("Invalid date: " + prop.getValue());
                    }
                  }
                  else {

                    try {
                      Date a = (Date) prop.getTypedValue();
                      resultList.add(a);
                    }
                    catch (java.lang.Exception pe) {
                      resultList.add(null);
                    }
                  }
                }
            } else if(type.equals(Property.EXPRESSION_PROPERTY)) {
              resultList.add(prop.getTypedValue());
            } else {
                try {
                    resultList.add(value);
                } catch (Exception e) {
                    throw new TMLExpressionException(e.getMessage());
                }
            }
        }

        if (!singleMatch)
            return resultList;
        else if (resultList.size() > 0)
            return resultList.get(0);
        else if (!exists)
            throw new TMLExpressionException("Property does not exist: " + val);
        else
            return Boolean.valueOf(false);
    }
}
