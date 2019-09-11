package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dexels.navajo.adapter.messagemap.PropertyAggregate;
import com.dexels.navajo.adapter.messagemap.ResultMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.NavajoExpression;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * MessageMap is used to manipulate/join Messages.
 * 
 * <map>
 *   <field name="joinMessage1"><expression value="'Message1'"/></field>
 *   <field name="joinMessage2"><expression value="'Message2'"/></field>
 *   <field name="joinProperties"><expression value="'Property1,Property2'"/></field>
 *   <message name="ResultMessage">
 *      <map ref="joinedMessage" filter="[some filter conditions]"/>
 *   </message>
 * </map>
 * 
 * new style:
 * 
 * <map.joinmap>
 *   <joinmap.join joinType="[inner|outer]" message1="Message1" message2="Message2" joinCondition="'Property1,Property2'" removeMessageSources="true"/>
 *   <message name="ResultMessage">
 *      <map ref="joinedMessage" filter="[some filter conditions]"/>
 *   </message>
 * </map.joinmap>
 * 
 * @author arjen
 *
 */
class JoinCondition {

	public String property1;
	public String property2;

}

public class MessageMap implements Mappable {

	private static final String INNER_JOIN = "inner";
	private static final String OUTER_JOIN = "outer";

//	private ResultMessage [] resultMessage;
	private String joinMessage1;
	private String joinMessage2;
	private ArrayList<JoinCondition> joinConditions = new ArrayList<>();
	private boolean removeSource = false;
	private String joinType = INNER_JOIN;
	private String suppressProperties = null;

	private String groupBy = null;
	private List<String> groupByProperties = null;

	private boolean removeDuplicates = false;

	private Access myAccess;
	private Message msg1 = null;
	private Message msg2 = null;

	private NavajoExpression joinExpression = null;
	private Message msg1pointer = null;
	private Message msg2pointer = null;
	
	@Override
	public void kill() {
	}

	public Object getProperty_1(String name) throws UserException {
		if ( msg1pointer.getProperty(name) == null ) {
			throw new UserException(-1, "Could not find property " + name + " in message1");
		}
		return msg1pointer.getProperty(name).getTypedValue();
	}
	
	public Object getProperty_2(String name) throws UserException {
		if ( msg2pointer.getProperty(name) == null ) {
			throw new UserException(-1, "Could not find property " + name + " in message2");
		}
		return msg2pointer.getProperty(name).getTypedValue();
	}
	
	private void clearPropertyValues(Message m) throws UserException {
	    List<Property> properties = m.getAllProperties();
		for ( int i = 0; i < properties.size(); i++ ) {
			properties.get(i).setAnyValue(null);
		}
		List<Message> subMessages = m.getAllMessages();
		for ( int i = 0; i < subMessages.size(); i++ ) {
			clearPropertyValues(subMessages.get(i));
		}
	}

	private Message checkMessage(String m) throws UserException {
		Message msg = ( myAccess.getCurrentOutMessage() == null ? 
				myAccess.getOutputDoc().getMessage(m) : myAccess.getCurrentOutMessage().getMessage(m) );
		if ( msg == null ) {
			throw new UserException(-1, "Exception joining message " + m + ": does not exist.");
		}
		if ( !Message.MSG_TYPE_ARRAY.equals( msg.getType())  ) {
			throw new UserException(-1, "Exception joining message " + m + ": not an array message, but a: "+msg.getType());
		}
		return msg;
	}
	
	private Message checkMessageTrial(String m) throws UserException {
		Message msg = ( myAccess.getCurrentOutMessage() == null ? 
				myAccess.getOutputDoc().getMessage(m) : myAccess.getCurrentOutMessage().getMessage(m) );
		if ( msg == null ) {
			throw new UserException(-1, "Exception joining message " + m + ": does not exist.");
		}
		/*if ( !Message.MSG_TYPE_ARRAY.equals( msg.getType())  ) {
			throw new UserException(-1, "Exception joining message " + m + ": not an array message, but a: "+msg.getType());
		}*/
		return msg;
	}

	public void setJoinType(String t) {
		this.joinType = t;
	}

	public void setRemoveSource(boolean b) throws UserException {
		this.removeSource = b;
		Message m1 = null;
		Message m2 = null;
		if ( this.joinMessage1 != null ) {
			m1 = checkMessage(joinMessage1);
		}
		if ( this.joinMessage2 != null )  {
			m2 = checkMessage(joinMessage2);
		}
		if ( removeSource ) {
			if ( myAccess.getCurrentOutMessage() == null ) {
				try {
					myAccess.getOutputDoc().removeMessage(m1);
					if ( m2 != null ) {
						myAccess.getOutputDoc().removeMessage(m2);
					}
				} catch (NavajoException ne) {}
			} else {
				if ( m1.getParentMessage() != null ) {
					m1.getParentMessage().removeMessage(m1);
				} else {
					myAccess.getOutputDoc().removeMessage(m1);
				}
				myAccess.getCurrentOutMessage().removeMessage(m1);
				if ( m2 != null ) {
					if ( m2.getParentMessage() != null ) {
						m2.getParentMessage().removeMessage(m2);
					} else {
						myAccess.getOutputDoc().removeMessage(m2);
					}
				}
			}
		}
	}

	public void setJoinMessage1(String m) throws UserException {
		this.msg1 = checkMessage(m).copy();
		this.joinMessage1 = m;
	}

	public void setJoinMessage2(String m) throws UserException {
		/* initial code*/
		//this.msg2 = checkMessage(m).copy();
		//this.joinMessage2 = m; 
		
		/*trial code*/
		this.msg2 = checkMessageTrial(m).copy();
		this.joinMessage2 = m;
	}

	public void setJoinCondition(Object jco) throws UserException {
		
		if ( jco instanceof String ) {
			String c = (String) jco;
			String [] conditions = c.split(",");
			for (int i = 0; i < conditions.length; i++) {
				if ( conditions[i].split("=").length != 2 ) {
					throw new UserException(-1, "Exception joining messages " + joinMessage1 + " and " + joinMessage2 + ": invalid join condition: " + c);
				}
				String prop1 = conditions[i].split("=")[0];
				String prop2 = conditions[i].split("=")[1];
				JoinCondition jc = new JoinCondition();
				jc.property1 = prop1;
				jc.property2 = prop2;
				joinConditions.add(jc);
			}
		} else
		if ( jco instanceof NavajoExpression ) {
			joinExpression = (NavajoExpression) jco;
		} else {
		throw new UserException(-1, "Invalid joincondition type: " + jco);
		}
	}

	public void setRemoveDuplicates(boolean b) {
		this.removeDuplicates = b;
	}
	
	public ResultMessage [] getResultMessage() throws UserException {

		Message definitionMsg1 = null;
		Message definitionMsg2 = null;

		List<ResultMessage> resultingMessage = new ArrayList<>();

		List<Message> children = this.msg1.getAllMessages();
		// Determine definition message, unless groupBy is defined.
		if ( groupBy == null && this.msg1.getDefinitionMessage() != null ) {
			definitionMsg1 = this.msg1.getDefinitionMessage();
		}

		if (  groupBy == null && definitionMsg1 != null && this.msg2 != null && this.msg2.getDefinitionMessage() != null ) {
			definitionMsg2 = this.msg2.getDefinitionMessage();
			definitionMsg1.merge(definitionMsg2);
		}

		for (int i = 0; i < children.size(); i++) {
			msg1pointer = children.get(i);
			if (msg1pointer.getType().equals(Message.MSG_TYPE_DEFINITION)) {
			    // Skip definition messages
			    continue;
			}
			Object [] joinValues1 = new Object[joinConditions.size()];
			for (int p = 0; p < joinConditions.size(); p++ ) {
				JoinCondition jc = joinConditions.get(p);
				Property prop = msg1pointer.getProperty(jc.property1);
				if ( prop == null ) {
					throw new UserException(-1, "Exception joining messages " + joinMessage1 + " and " + joinMessage2 + ": property not found: " + jc.property1);
				}
				joinValues1[p] = prop.getValue();
			}

			// Find c2;
			msg2pointer = null;
			boolean foundJoinMessage = false;
			boolean isSingleMessage = false;

			if ( this.msg2 != null ) {
			    List<Message> children2 = this.msg2.getAllMessages();
			    
			    //Message child2 = this.msg2.getMessage(msg2.MSG_NAME); //v
			    
			    // ==========
			    if (this.msg2.getType().equals(Message.MSG_TYPE_SIMPLE)) { // v SIMPLE MESSAGE if
			    	isSingleMessage = true;
			    	
			    	
			    } // ========== // v SIMPLE MESSAGE end if
			    else { // ==========

					for (int j = 0; j < children2.size(); j++) {
						msg2pointer = children2.get(j);
						
						if (msg2pointer.getType().equals(Message.MSG_TYPE_DEFINITION)) {
						    // Skip definition messages
			                continue;
			            }
	
						Object [] joinValues2 = new Object[joinConditions.size()];
						for (int p = 0; p < joinConditions.size(); p++ ) {
							JoinCondition jc = joinConditions.get(p);
							Property prop = msg2pointer.getProperty(jc.property2);
							if ( prop == null ) {
								throw new UserException(-1, "Exception joining messages " + joinMessage1 + " and " + joinMessage2 + ": property not found: " + jc.property2);
							}
							joinValues2[p] = prop.getValue();
						}
						// Compare joinValues...
						boolean equal = true;
						for (int jv = 0; jv < joinConditions.size(); jv++) {
							if ( joinValues1[jv] != null && joinValues2[jv] != null && !joinValues1[jv].equals(joinValues2[jv])) {
								equal = false;
							} else if ( joinValues1[jv] == null && joinValues2[jv] != null ) {
								equal = false;
							} else if ( joinValues1[jv] != null && joinValues2[jv] == null ) {
								equal = false;
							}
						}
						
						if ( joinExpression != null ) {
							// Evaluate joinExpression.
							try {
								Operand ro = Expression.evaluate(joinExpression.toString(), myAccess.getInDoc(), myAccess.getCompiledScript().getCurrentMap(), myAccess.getCurrentInMessage());
								equal = (Boolean) ro.value;
							} catch (Exception e) {
								throw new UserException(-1, "Exception joining messages: " + e.getMessage(), e);
							}
						}
	
						if ( equal ) {
							Message newMsg = NavajoFactory.getInstance().createMessage(myAccess.getOutputDoc(), "tmp");
							// Check for duplicate property names. If found, rename to _1 _2 respectively
							// DO NOT, CAN LEAD TO STRANGE BEHAVIOR: renameDuplicates(msg1pointer, msg2pointer);
							newMsg.merge(msg2pointer);
							newMsg.merge(msg1pointer);
							ResultMessage rm = new ResultMessage();
							rm.setMessage(definitionMsg1, newMsg, this.suppressProperties);
							resultingMessage.add(rm);
							foundJoinMessage = true;
						}
					} // end for
			    } // ==========
			}

			if ( !foundJoinMessage && joinType.equals(OUTER_JOIN) && !isSingleMessage) {
				// Append dummy message with empty property values in case no join condition match...
				if ( msg2pointer != null ) {
					Message newMsg = NavajoFactory.getInstance().createMessage(myAccess.getOutputDoc(), "tmp");
					Message c2c = msg2pointer.copy();
					clearPropertyValues(c2c);
					newMsg.merge(c2c);
					newMsg.merge(msg1pointer);
					ResultMessage rm = new ResultMessage();
					rm.setMessage(definitionMsg1, newMsg, this.suppressProperties);
					resultingMessage.add(rm);
				} else {
					// Assume empty second array message
					Message c1c = msg1pointer.copy();
					ResultMessage rm = new ResultMessage();
					rm.setMessage(definitionMsg1, c1c, this.suppressProperties);
					resultingMessage.add(rm);
				}
			} 
			if( !foundJoinMessage && joinType.equals(OUTER_JOIN) && isSingleMessage) {
				// Append dummy message with empty property values in case no join condition match...
				//if ( msg2pointer != null ) {
				if ( msg2 != null ) { //msg2 instead of msg2pointer if something goes wrong change back to msg2pointer
					Message newMsg = NavajoFactory.getInstance().createMessage(myAccess.getOutputDoc(), "tmp");
					Message c1c = msg1pointer.copy();
					//clearPropertyValues(c1c);
					newMsg.merge(c1c);
					//newMsg.merge(msg1pointer);
					newMsg.merge(msg2); //msg2 instead of msg2pointer if something goes wrong change back to msg2pointer
					ResultMessage rm = new ResultMessage();
					rm.setMessage(definitionMsg1, newMsg, this.suppressProperties);
					resultingMessage.add(rm);
				} else {
					// Assume empty second array message
					Message c1c = msg1pointer.copy();
					ResultMessage rm = new ResultMessage();
					rm.setMessage(definitionMsg1, c1c, this.suppressProperties);
					resultingMessage.add(rm);
				}
			}
		}
		
		if (children.isEmpty() && definitionMsg1 != null && msg1 != null) {
		    // Make sure definition message stays intact
		    Navajo out = myAccess.getOutputDoc();
		    Message newMessage = NavajoFactory.getInstance().createMessage(out, msg1.getName(), msg1.getType());
		    newMessage.addMessage(definitionMsg1.copy(myAccess.getOutputDoc()));
		    
		    
		    myAccess.getOutputDoc().addMessage(newMessage);
		}

		if ( groupBy != null ) {

			removeDuplicates = true;
			Map<String,PropertyAggregate> aggregates = new HashMap<>();

			for ( int i = 0; i < resultingMessage.size(); i++ ) {

				Map<String,Object> group = new TreeMap<>();

				ResultMessage rm = resultingMessage.get(i);
				Message m = rm.getMsg();
				List<Property> properties = m.getAllProperties();

				for ( int j = 0; j < properties.size(); j++ ) {
					Property p = properties.get(j);
					if ( groupByProperties.contains(p.getName()) ) {
						group.put(p.getName(), p.getTypedValue());
					} 
				}

				for  (int j = 0; j < properties.size(); j++ ) {
					Property p = properties.get(j);
					if (!groupByProperties.contains(p.getName()) ) {
						PropertyAggregate pa = aggregates.get(p.getName());
						if ( pa == null ) {
							pa = new PropertyAggregate();
							aggregates.put(p.getName(), pa);
						}
						pa.addProperty(p, group);
						m.removeProperty(p);
					}
				}

			}

			for ( int i = 0 ; i < resultingMessage.size(); i++ ) {
				resultingMessage.get(i).setAggregates(aggregates);
			}
		}


		if ( removeDuplicates ) {
			for ( int i = 0; i < resultingMessage.size(); i++ ) {
				Message m1 = resultingMessage.get(i).getMsg().copy();
				resultingMessage.get(i).processSuppressedProperties(m1);
				if ( !resultingMessage.get(i).isRemove() ) {

					for ( int j = i+1; j < resultingMessage.size(); j++ ) {
						Message m2 = resultingMessage.get(j).getMsg().copy();
						resultingMessage.get(j).processSuppressedProperties(m2);
						if ( m1.isEqual(m2)) {
							resultingMessage.get(j).setRemove(true);
						} 
					}
				}
			}
		}

		Iterator<ResultMessage> iter = resultingMessage.iterator();
		while ( iter.hasNext() ) {
			ResultMessage c = iter.next();
			if ( c.isRemove() ) {
				iter.remove();
			}
		}
		return resultingMessage.toArray(new ResultMessage[] {});
	}

	
	@Override
	public void load(Access access) throws MappableException, UserException {
		this.myAccess = access;
	}

	@Override
	public void store() throws MappableException, UserException {
	}

	
	public void setSuppressProperties(String suppressProperties) {
		this.suppressProperties = suppressProperties;
	}

	public void setGroupBy(String groupBy) {
		if ( groupBy != null ) {
			this.groupBy = groupBy;
			String [] props = groupBy.split(",");
			groupByProperties = new ArrayList<>();
			for ( int i = 0; i < props.length; i++ ) {
				groupByProperties.add(props[i].trim());
			}
		}
	}

}
