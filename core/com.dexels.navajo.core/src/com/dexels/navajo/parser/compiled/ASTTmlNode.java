/* Generated By:JJTree&JavaCC: Do not edit this line. ASTTmlNode.java */
package com.dexels.navajo.parser.compiled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
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
final class ASTTmlNode extends SimpleNode {
    String val = "";
    String option = "";
    String selectionOption = "";
    boolean exists = false;
    
    ASTTmlNode(int id) {
        super(id);
    }

    @Override
	public final ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		return new ContextExpression() {
	
			@Override
			public boolean isLiteral() {
				return false;
			}
			
			@Override
			public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
				List<Property> match = null;
				List<Object> resultList = new ArrayList<>();
		        boolean singleMatch = true;
		        String parts[] = val.split("\\|");
		        String text = parts.length > 1 ? parts[1] : val;
		        boolean isParam = false;
		        Property prop = null;
				if (parentSel != null) {
					String dum = text;
					if (dum.length() > 1 && dum.startsWith("[")) {
						dum = dum.substring(1, text.length());
					}
					if (dum.equals("name") || selectionOption.equals("name")) {
						return Operand.ofString(parentSel.getName());
					} else if (dum.equals("value") || selectionOption.equals("value")) {
						return Operand.ofString(parentSel.getValue());
					} else if (dum.equals("selected") || selectionOption.equals("selected")) {
						return Operand.ofBoolean(parentSel.isSelected());
					}
				}

		        if (!exists) {
					if(text.startsWith("[")) {
			            text = text.substring(1, text.length());
					}
		        }  else {
					if(text.startsWith("?[")) {
			            text = text.substring(2, text.length());
					}
		        }
		        if (text.length() > 0 && text.charAt(0) == '@') { // relative param property.
		        		isParam = true;
		        		text = text.substring(1);
		        }
		        
		        if (text.startsWith("/@")) { // Absolute param property.
		        		parentParamMsg = doc.getMessage("__parms__");
		        		isParam = true;
		        		text = text.substring(2);
		        }
		        if (text.contains("__globals__")) { // Absolute globals property.
		            parentMsg = doc.getMessage("__globals__");
		            int length = "__globals__".length();
		            if (text.startsWith("/")) {
		                length += 1;
		            }
		            length += 1; // trailing /
		            text = text.substring(length);
		        }
		    
		        if (Util.isRegularExpression(text))
		            singleMatch = false;
		        else
		            singleMatch = true;

		        try {
		        		if(!isParam && immutableMessage!=null && immutableMessage.isPresent()) {
		        			ImmutableMessage rm = immutableMessage.get();
		        			return parseImmutablePath(text, rm);
		        		}
		        		if(isParam && paramMessage!=null && paramMessage.isPresent()) {
		        			ImmutableMessage rm = paramMessage.get();
		        			return parseImmutablePath(text, rm);
		        			
		        		}
		            if (parentMsg == null && !isParam) {
		                if (text.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
		                	if(doc==null) {
		                		throw new NullPointerException("Can't evaluate TML node: No parent message and no document found.");
		                	}
		                	match = doc.getProperties(text);
		                    if (match.size() > 1) {
		                      singleMatch = false;
		                    }
		                }
		                else {
		                   throw new TMLExpressionException("No parent message present for property: " + text+" -> "+ImmutableFactory.getInstance().describe(immutableMessage.orElse(ImmutableFactory.empty())));
		                }
		            } else if (parentParamMsg == null && isParam) {
		            	parentParamMsg = doc.getMessage("__parms__");
		            	 if (text.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
		                    match = doc.getProperties(text);
		                    if (match.size() > 1) {
		                       singleMatch = false;
		                    }  
		                }
		                else
		                    throw new TMLExpressionException("No parent message present for param: " + text);
		            } else {
		                if (text.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
		                  match = (!isParam ? parentMsg.getProperties(text) : parentParamMsg.getProperties(text));
		                  if (match.size() > 1)
		                    singleMatch = false;

		                }
		                else {
		                    match = new ArrayList<>();
		                    match.add((!isParam ? parentMsg.getProperty(text) : parentParamMsg.getProperty(text)));
		                }
		            }
		        } catch (NavajoException te) {
		            throw new TMLExpressionException(te.getMessage(),te);
		        }
		         for (int j = 0; j < match.size(); j++) {
		            prop = (Property) match.get(j);
		              if (!exists && (prop == null))
		            	  if (parentMsg!=null) {
		                      throw new TMLExpressionException("TML property does not exist: " + text+" parent message: "+parentMsg.getFullMessageName());
						} else {
			                throw new TMLExpressionException("TML property does not exist: " + text+" exists? "+exists);
						}
		            else if (exists) { // Check for existence and datatype validity.
		                if (prop != null) {
		                    // Check type. If integer, float or date type and if is empty
		                    String type = prop.getType();
		                   
		                    // I changed getValue into getTypedValue, as it resulted in a serialization
		                    // of binary properties. Should be equivalent, and MUCH faster.
		                    if (prop.getTypedValue() == null && !type.equals(Property.SELECTION_PROPERTY)) {
		                        return Operand.FALSE;
		                    }

		                    if (type.equals(Property.INTEGER_PROPERTY)) {
		                       try {
		                          Integer.parseInt(prop.getValue());
		                          return Operand.TRUE;
		                       } catch (Exception e) {
			                          return Operand.FALSE;
		                       }
		                    } else if (type.equals(Property.FLOAT_PROPERTY)) {
		                      try {
		                          Double.parseDouble(prop.getValue());
		                          return Operand.TRUE;
		                       } catch (Exception e) {
			                          return Operand.FALSE;
		                       }
		                    } else if (type.equals(Property.DATE_PROPERTY)) {
		                    	try {
		                    		if ( prop.getTypedValue() instanceof Date ) {
				                          return Operand.TRUE;
		                    		} else {
		                    			return Operand.FALSE;
		                    		}
		                    	} catch (Exception e) {
		                    		return Operand.FALSE;
		                    	}
		                    } else if ( type.equals(Property.CLOCKTIME_PROPERTY)) {
		                    	try {
		                            ClockTime ct = new ClockTime(prop.getValue());
		                            if ( ct.calendarValue() == null ) {
		                            	return Operand.FALSE;
		                            }
		                            return Operand.TRUE;
		                         } catch (Exception e) {
		                            return Operand.FALSE;
		                         }
		                    } else
		                        return Operand.TRUE;
		                } else
		                    return Operand.FALSE;
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
		                        List<Selection> list = prop.getAllSelectedSelections();

		                        if (!list.isEmpty()) {
		                            Selection sel = list.get(0);
		                            resultList.add((selectionOption.equals("name") ? sel.getName() : sel.getValue()));
		                        } else {
		                          return Operand.NULL;
		                        }
		                    } catch (com.dexels.navajo.document.NavajoException te) {
		                        throw new TMLExpressionException(te.getMessage());
		                    }
		                } else { // Multi-selection property.
		                    try {
		                        List<Selection> list = prop.getAllSelectedSelections();
		                        List<Object> result = new ArrayList<>();
		                        for (int i = 0; i < list.size(); i++) {
		                            Selection sel = list.get(i);
		                            Object o = (selectionOption.equals("name")) ? sel.getName() : sel.getValue();
		                            result.add(o);
		                        }
		                        resultList.add(result);
		                    } catch (NavajoException te) {
		                        throw new TMLExpressionException(te.getMessage(),te);
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
		                      resultList.add(altA);
		                    }
		                    catch (Exception ue) {
		                      throw new TMLExpressionException("Invalid date: " + prop.getValue(),ue);
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
		                    throw new TMLExpressionException(e.getMessage(),e);
		                }
		            }
		        }

		        if (!singleMatch)
		            return Operand.ofList(resultList);
		        else if (!resultList.isEmpty())
		            return Operand.ofDynamic(resultList.get(0));
		        else if (!exists)
		            throw new TMLExpressionException("Property does not exist: " + text);
		        else
		            return Operand.FALSE;
			}

			private Operand parseImmutablePath(String text, ImmutableMessage rm) {
				if(text.endsWith("/")) {
					String trunc = text.substring(0,text.length()-1);
					List<String> parts = Arrays.asList(trunc.split("/"));
					return parseImmutableMessagePath(parts, rm);
				}
				List<String> parts = Arrays.asList(text.split("/"));
				return parseImmutablePath(parts, rm);
			}

			private Operand parseImmutableMessagePath(List<String> path, ImmutableMessage rm) {
				if(!path.isEmpty()) {
					return Operand.ofImmutable(rm);
				}
				String first = path.get(0);
				Optional<ImmutableMessage> sub = rm.subMessage(first);
				if(!sub.isPresent()) {
					throw new TMLExpressionException("Missing submessage: "+first);
				}
				List<String> copy = new ArrayList<>(path);
				copy.remove(0);
				return parseImmutableMessagePath(copy, sub.get());
			}

			private Operand parseImmutablePath(List<String> path, ImmutableMessage rm) {
				if(path.size()>1) {
					Optional<ImmutableMessage> imm = rm.subMessage(path.get(0));
					if(imm.isPresent()) {
						List<String> parts = new LinkedList<>(path);
						parts.remove(0);
						return parseImmutablePath(parts,imm.get());
					}
					return null;
				}
				String type = rm.columnType(path.get(0));
				if(type!=null) {
					return Operand.ofCustom(rm.value(path.get(0)).orElse(null), type);
				}
				return Operand.ofDynamic(rm.value(path.get(0)).orElse(null));
			}

			
			@Override
			public Optional<String> returnType() {
				return Optional.empty();
			}
			
			@Override
			public String expression() {
				return expression;
			}
		};
	}
}
