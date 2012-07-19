package com.dexels.navajo.mapping.compiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ScriptInheritance {
	
	private static String REPLACE_MESSAGE = "replace";
	private static String EXTEND_MESSAGE = "extend";
	private static String BLOCK_ELEMENT = "block";
	
	private int maxLevel = 0;
	
	/**
	 * Determines whether two elements are considered equal.
	 * Equality is only supported for elements that have an attribute 'name'.
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	private boolean equalElements(XMLElement one, XMLElement two) {
		if ( one.getName().equals(two.getName())) {
			String nameOne = ( one.getAttribute("name") != null ? (String) one.getAttribute("name") : (String) one.getAttribute(BLOCK_ELEMENT) );
			String nameTwo = ( two.getAttribute("name") != null ? (String) two.getAttribute("name") : (String) two.getAttribute(BLOCK_ELEMENT) );
			if ( nameOne != null  && nameTwo != null && nameOne.equals(nameTwo)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Replaces a message in tsl on level level and with index targetIndex on that level with insertedMessage.
	 * 
	 * @param level
	 * @param leveledIndex
	 * @param targetIndex
	 * @param tsl
	 * @param insertedMessage
	 */
	private boolean replaceMessagesWithLevel(int level, int leveledIndex, int targetIndex, XMLElement tsl, 
			                                 XMLElement insertedMessage) throws Exception {
		
		Vector<XMLElement> children = tsl.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if ( child.getName().equals("message") && child.getAttribute("level").equals(level+"") ) {
				if ( leveledIndex == targetIndex) {
					XMLElement parent = child.getParent();
					// Find current child index..
					Vector<XMLElement> allChildrenOfParent = parent.getChildren();
					for (int j = 0; j < allChildrenOfParent.size(); j++) {
						
						if ( allChildrenOfParent.get(j).equals(child) ) {
						
							String nameOfInsertedMessage = null;
							String operation = "";
							if ( nameOfInsertedMessage == null ) {
								nameOfInsertedMessage = (String) insertedMessage.getAttribute(REPLACE_MESSAGE);
								insertedMessage.removeAttribute(REPLACE_MESSAGE);
								operation = REPLACE_MESSAGE;
							}
							if ( nameOfInsertedMessage == null ) {
								nameOfInsertedMessage = (String) insertedMessage.getAttribute(EXTEND_MESSAGE);
								insertedMessage.removeAttribute(EXTEND_MESSAGE);
								operation = EXTEND_MESSAGE;
							}
							if ( nameOfInsertedMessage == null ) {
								nameOfInsertedMessage = (String) insertedMessage.getAttribute(BLOCK_ELEMENT);
								insertedMessage.removeAttribute(BLOCK_ELEMENT);
								operation = BLOCK_ELEMENT;
							} 
							
							String newName = (String) insertedMessage.getAttribute("name");
							
							if ( newName == null && nameOfInsertedMessage == null ) {
								throw new Exception("Unknown inheritance operation @line: " + insertedMessage.getLineNr());
							}
							insertedMessage.setAttribute("name", ( newName != null ? newName : nameOfInsertedMessage) );
							
							
							if ( operation.equals(BLOCK_ELEMENT) ) {
								allChildrenOfParent.remove(j);
							} else if ( operation.equals(REPLACE_MESSAGE) ) { // simply replace.
								allChildrenOfParent.remove(j);
								allChildrenOfParent.add(j, insertedMessage);
							} else {
								// Add all children of insertedMessage.
								XMLElement orig = allChildrenOfParent.get(j);
								orig.setAttribute("name", ( newName != null ? newName : nameOfInsertedMessage) );
								Vector<XMLElement> allChildrenOfInsertedMessage = insertedMessage.getChildren();
								
								// replace 'overlap' with 'new' and delete 'blocked'.
								for (int aci = 0; aci < allChildrenOfInsertedMessage.size(); aci++) {
									boolean added = false;
									for (int oci = 0; oci < orig.getChildren().size(); oci++) {
										// Remove elements that have a blocked attribute.
										if ( orig.getChildren().get(oci).getAttribute(BLOCK_ELEMENT) != null ) {
											orig.getChildren().remove(oci);
										}
										if ( equalElements ( orig.getChildren().get(oci), allChildrenOfInsertedMessage.get(aci)) ) {
											orig.getChildren().remove(oci);
											if ( allChildrenOfInsertedMessage.get(aci).getAttribute(BLOCK_ELEMENT) == null ) {
												// Only add if blocked attribute was NOT specified.
												orig.getChildren().add(oci, allChildrenOfInsertedMessage.get(aci));
											}
											oci = orig.getChildren().size() + 1;
											added = true;
										}
									
									}
									if (!added) {
										orig.addChild(allChildrenOfInsertedMessage.get(aci));
									}
									
								}
								
							}
							// Tag parent message 1 level up, such that it is not replaced.
							if ( level > 0 ) {
								XMLElement parentMessage = null;
								while (parentMessage == null) {
									if ( parent.getName().equals("message") ) {
										parentMessage = parent;
									} else {
										parent = parent.getParent();
									}
								}
								
								parentMessage.setAttribute("processed", "0");
							}
							return true;
						}
					}
					return false;
				} else {
					leveledIndex++;		
				}
			} else {
				if ( replaceMessagesWithLevel(level, leveledIndex, targetIndex, child, insertedMessage) ) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	private void findMessagesWithLevel(int level, XMLElement tsl, Vector<XMLElement> leveledMessages) {
		
		Vector<XMLElement> children = tsl.getChildren();
		
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if ( child.getName().equals("message") && child.getAttribute("level").equals(level+"") ) {
				leveledMessages.add(child);
			} 
			findMessagesWithLevel(level, child, leveledMessages);
		}
	}
	
	private XMLElement findMessageWithLevel(String name, String level, XMLElement sub) {

		Vector<XMLElement> children = sub.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if ( child.getName().equals("message") &&
			     ( name.equals(child.getAttribute(REPLACE_MESSAGE)) ||  
			       name.equals(child.getAttribute(EXTEND_MESSAGE)) ||
			       name.equals(child.getAttribute(BLOCK_ELEMENT))
			     ) 
			       
			     && child.getAttribute("level").equals(level) ) {
				return child;
			} 
			XMLElement depthChild = findMessageWithLevel(name, level, child);
			if ( depthChild != null ) {
				return depthChild;
			}
		}
		
		return null;
	}
	
	private void findNearestMessageChildren(XMLElement e, Vector<XMLElement> normalized) {
		
		Vector<XMLElement> messages = e.getChildren();
		
		for (int i = 0; i < messages.size(); i++) {
			XMLElement child = messages.elementAt(i);
			if ( child.getName().equals("message") ) {
				normalized.add(child);
			} else {
				findNearestMessageChildren(child, normalized);
			}
		}
		
	}
	
	private void addMessageLevels(XMLElement e, int level) {
		
		Vector<XMLElement> messages = new Vector<XMLElement>();
		findNearestMessageChildren(e, messages);
		
		for (int i = 0; i < messages.size(); i++) {
			XMLElement msg = messages.get(i);
			msg.setAttribute("level", level+"");
			if ( level > maxLevel ) {
				maxLevel = level;
			}
			addMessageLevels(msg, level + 1);
		}
	
	}
	
	private XMLElement extend(XMLElement superScript, XMLElement subScript) throws Exception {
		
		addMessageLevels(superScript, 0);
		addMessageLevels(subScript, 0);
		
		// Replace children.
		for (int levelindex = maxLevel; levelindex >= 0; levelindex--) {
			Vector<XMLElement> superMessages = new Vector<XMLElement>();
			findMessagesWithLevel(levelindex, superScript, superMessages);
			// Loop over all messages with this level...
			for (int i = 0; i < superMessages.size(); i++) {
				XMLElement childPrev = superMessages.elementAt(i);
				if ( childPrev.getName().equals("message") && childPrev.getAttribute("processed") == null ) {
					String messageName = (String) childPrev.getAttribute("name");
					String level = (String) childPrev.getAttribute("level");
					XMLElement found = findMessageWithLevel(messageName, level, subScript);
					if ( found != null ) {
						replaceMessagesWithLevel(new Integer(level).intValue(), 0, i, superScript, found);
					} 
				}
			}
		}
		
		return superScript;
		
	}
	
	private void removeTslTag(int indx, XMLElement tsl) {
		
		if ( tsl.getName().equalsIgnoreCase("tsl") || tsl.getName().equalsIgnoreCase("navascript")) {
			
			Vector<XMLElement> tslChildren = tsl.getChildren();
			for ( int c = 0; c < tslChildren.size(); c++ ) {
				tslChildren.get(c).setParent(tsl);
				removeTslTag( c, tslChildren.get(c) );
			}
			
			tsl.getParent().getChildren().addAll(indx, tslChildren);
			tsl.getParent().removeChild(tsl);
		}
	}
	
	private void cleanTslFragments(XMLElement raw) {
		
		Vector<XMLElement> children = new Vector<XMLElement>(raw.getChildren());
		
		for (int i = 0; i < children.size(); i++ ) {
			children.get(i).setParent(raw);
			removeTslTag(i, children.get(i));
		}
	
	}
	
	private void doInject(XMLElement subScript, XMLElement child, String scriptPath, ArrayList<String> inheritedScripts) throws Exception {
		// find inject tags.
		
		Vector<XMLElement> children = ( child == null ? subScript.getChildren() : child.getChildren() );
		
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getName().equalsIgnoreCase("inject")) {
				XMLElement injectNode = children.get(i);
				String script = (String) injectNode.getAttribute("script");
				XMLElement result = null;
				if ( script != null ) {
					// Recursively do inheritance for inherited scripts...
					BufferedReader br = new BufferedReader(new InputStreamReader(inherit(new FileInputStream(scriptPath + script + ".xml"), scriptPath, inheritedScripts)));
					XMLElement superScript = new CaseSensitiveXMLElement();
					superScript.parseFromReader(br);
					br.close();
					inheritedScripts.add(script);
					result = extend(superScript, injectNode);
				}
				children.remove(i);
				children.add(i, result);
			} else {
				doInject(subScript, children.get(i), scriptPath, inheritedScripts);
			}
		}
	}
	
	private static boolean hasInject(XMLElement e) {
		if ( e.getName().equals("inject") ) {
			return true;
		}
		Vector<XMLElement> c = e.getChildren();
		if ( c != null && c.size() > 0 ) {
			for (int i = 0; i < c.size(); i++) {
				boolean b = hasInject(c.get(i));
				if ( b ) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks the stream for an inject. WARNING: closes the stream
	 * @param fis
	 * @return
	 * @throws Exception
	 */
	public static boolean containsInject(InputStream fis) throws Exception {
		XMLElement e = new CaseSensitiveXMLElement();
//		FileInputStream fis = new FileInputStream(scriptPath);
		e.parseFromStream(fis);
		fis.close();
		
		return hasInject(e);
		
	}
	
	public static InputStream inherit(InputStream raw, String scriptPath, ArrayList<String> inheritedScripts) throws Exception {
		
		ScriptInheritance ti = new ScriptInheritance();
		
		XMLElement before = new CaseSensitiveXMLElement();
		before.parseFromStream(raw);
		
		// Remember tsl attributes.
		HashMap<String,String> tslAttributes = new HashMap<String, String>();
		Iterator<String> all = before.enumerateAttributeNames();
		while ( all.hasNext() ) {
			String name = all.next().toString();
			String value = before.getAttribute(name)+"";
			tslAttributes.put(name, value);
		}
		
		
		ti.doInject(before, null, scriptPath, inheritedScripts);
	
		StringWriter sw = new StringWriter();
		before.write(sw);
		
		ti.cleanTslFragments(before);
		XMLElement after = before;
		
		// Reinsert tsl attributes.
		all = tslAttributes.keySet().iterator();
		while ( all.hasNext() ) {
			String name = all.next().toString();
			String value = tslAttributes.get(name);
			after.setAttribute(name, value);
		}

		StringWriter s = new StringWriter();
		after.write(s);
		
		return new java.io.ByteArrayInputStream(s.toString().getBytes());
	}
	
	public static void main(String [] args) throws Exception {
		
//		System.err.println("hasinject: " + containsInject("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/competition/ProcessQueryFacilityOccupation.xml"));
//		System.err.println("hasinject: " + containsInject("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/PageableService.xml"));
		
		
		InputStream is = ScriptInheritance.inherit(
				new FileInputStream("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/vla/accounting/journaltransactions/ProcessSearchTransactionItemsAccounting.xml"),
				"/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/", new ArrayList<String>());

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ( ( line = br.readLine() ) != null ) {
			System.err.println(line);
		}
//		StringWriter writer = new StringWriter();
//			
//		BufferedReader br2 = new BufferedReader(new FileReader("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/PageableService.xml"));
//		XMLElement subScript = new CaseSensitiveXMLElement();
//		subScript.parseFromReader(br2);
//		br2.close();
//				
//		XMLElement result = ti.doInject(subScript);
//	
//		writer = new StringWriter();
//		result.write(writer);
//		System.err.println("AFTER INHERITANCE:");
//		System.err.println(writer.toString());
		
	}
}
