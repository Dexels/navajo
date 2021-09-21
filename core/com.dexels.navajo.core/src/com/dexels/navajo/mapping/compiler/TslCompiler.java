/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler;

import java.io.BufferedReader;

/**
 * <p>Title: Navajo Product Project</p>"
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$get
 */

/**
 * IMPLEMENT SUPPORT FOR ARBITRARY JAVA BEANS (NEXT TO MAPPABLE AND ASYNCMAPPABLE OBJECTS.
 *
 * SYMBOL TABLE BIJHOUDEN VAN ATTRIBUUT WAARDEN UIT MAPPABLE OBJECTEN, DAN DEZE SYMBOL TABLE MEEGEVEN AAN EXPRESSION.EVALUATE(),
 * ZODAT NIET VIA INTROSPECTIE DE ATTRIBUUT WAARDEN HOEVEN TE WORDEN BEPAALD
 *
 * $columnValue('AAP') -> Object o = contextMap.getColumnValue('AAP') -> symbolTable.put("$columnValue('AAP')", o);
 *laz
 *
 *
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericMultipleDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.mapping.bean.DomainObjectMapper;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;
import com.dexels.navajo.mapping.compiler.meta.ExtendDependency;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.JavaDependency;
import com.dexels.navajo.mapping.compiler.meta.KeywordException;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.mapping.compiler.meta.MetaCompileException;
import com.dexels.navajo.mapping.compiler.navascript.NS3ToNSXML;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.script.api.CompilationException;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.script.api.MappingException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.internal.LegacyNavajoIOConfig;

import navajocore.Version;

public class TslCompiler {

	private ClassLoader loader = null;

	private int messageListCounter = 0;
	private int asyncMapCounter = 0;
	private int lengthCounter = 0;
	private int objectCounter = 0;
	private int subObjectCounter = 0;
	private int startIndexCounter = 0;
	private int startElementCounter = 0;
	private String scriptPath;
	private int methodCounter = 0;
	private List<StringBuilder> methodClipboard = new ArrayList<>();
	private List<String> variableClipboard = new ArrayList<>();
	private StringBuilder dependencies = new StringBuilder();
	private Set<String> dependentObjects = new HashSet<>();

	/**
	 * Use this as a placeholder for instantiated adapters (for meta data
	 * usage).
	 */
	private Map<Class<?>, DependentResource[]> instantiatedAdapters = new HashMap<>();

	private Stack<Class> contextClassStack = new Stack<>();
	private Class contextClass = null;
	private int included = 0;

	private String scriptType = "tsl";

	private final NavajoIOConfig navajoIOConfig;

	private static final Logger logger = LoggerFactory
			.getLogger(TslCompiler.class);

	public TslCompiler(ClassLoader loader) {
		this.navajoIOConfig = new LegacyNavajoIOConfig();
		initialize(loader);
	}

	public TslCompiler(ClassLoader loader, NavajoIOConfig config) {
		this.navajoIOConfig = config;
		initialize(loader);
	}

	private void initialize(ClassLoader loader) {
		this.loader = loader;
		messageListCounter = 0;
		if (loader == null) {
			this.loader = this.getClass().getClassLoader();
		}
	}

	public NavajoIOConfig getNavajoIOConfig() {
		return this.navajoIOConfig;
	}


	private String replaceQuotesValue(String str) {
	    StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '"') {
                // Check if already escaped
                boolean escaped = false;
                if (i != 0) {
                    if (str.charAt(i-1) == '\\') {
                        escaped = true;
                    }
                }
                if (escaped) {
                    result.append(c); // No need to escape again
                } else {
                    result.append("\\\"");
                }
            } else if (c == '\n') {
                result.append(' ');
            } else if (c == '\r') {
                result.append(' ');
            } else {
                result.append(c);
            }
        }
        return "\"" + result.toString() + "\"";
	}

	private String replaceQuotes(String str) {
		if (str.length() > 0 && str.charAt(0) == '#') {
			str = "(String) userDefinedRules.get(\"" + str.substring(1) + "\")";
			return str;
		}
		return replaceQuotesValue(str);

	}

	private String removeNewLines(String str) {
		StringBuilder result = new StringBuilder(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '\n') {
				result.append("\\n");
			} else if (c == '\r') {
				// IGNORE CRS
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	private String printIdent(int count) {
		StringBuilder identStr = new StringBuilder();
		for (int i = 0; i < count; i++) {
			identStr.append(' ');
		}
		return identStr.toString();
	}

	private Element getNextElement(Node n) {
		NodeList children = n.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i) instanceof Element) {
				return (Element) children.item(i);
			}
		}
		return null;
	}

	private int countNodes(NodeList l, String name) {
		int count = 0;
		for (int i = 0; i < l.getLength(); i++) {
			if (l.item(i).getNodeName().equals(name)) {
				count++;
			}
		}
		return count;
	}

	private String removeWhiteSpaces(String s) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				result.append(c);
			}
		}
		return result.toString();
	}

	private final void addDependency(String code, String id) {
		if (!dependentObjects.contains(id)) {
			dependencies.append(code);
			dependentObjects.add(id);
		}
	}

	/**
	 * VERY NASTY METHOD. IT TRIES ALL KINDS OF TRICKS TO TRY TO AVOID CALLING
	 * THE EXPRESSION.EVALUATE() METHOD IN THE GENERATED JAVA.
	 *
	 * @param ident
	 * @param clause
	 * @param className
	 * @return
	 */
	public String optimizeExpresssion(int ident, String clause,
			String className, String objectName) throws UserException {

		boolean exact = false;
		StringBuilder result = new StringBuilder();
		char firstChar = ' ';
		boolean functionCall = false;
		StringBuilder functionNameBuffer = new StringBuilder();
		String functionName = "";
		String call = "";



		// Try if clause contains only a (Navajo) function and a mappable
		// attribute call.
		for (int i = 0; i < clause.length(); i++) {
			char c = clause.charAt(i);

			if (c != ' ' && firstChar == ' ') {
				firstChar = c;
			}

			if (((firstChar > 'a' && firstChar < 'z'))
					|| ((firstChar > 'A') && (firstChar < 'Z'))) {
				functionCall = true;
			}

			if ((functionCall) && (c != '(')) {
				functionNameBuffer.append(c);
			} else if (functionCall && c == '(') {
				functionName = functionNameBuffer.toString();
				functionNameBuffer = new StringBuilder();
			}

			if (c == '$') { // New attribute found
				StringBuilder name = new StringBuilder();
				i++;
				c = clause.charAt(i);
				while (c != '(' && i < clause.length() && c != ')') {
					name.append(c);
					i++;
					if (i < clause.length()) {
						c = clause.charAt(i);
					}
				}
				if (name.toString().contains("..")) {
				    // We cannot optimize these yet
				    continue;

				}
				i++;

				StringBuilder params = new StringBuilder();

				if (clause.indexOf("(") != -1) {
					// Determine parameters.
					int endOfParams = 1;

					while (endOfParams > 0 && i < clause.length()) {
						c = clause.charAt(i);
						if (c == '(') {
							endOfParams++;
						} else if (c == ')') {
							endOfParams--;
						} else {
							params.append(c);
						}
						i++;
					}
				}

				String expr = "";
				if (functionName.equals("")) {
					expr = (params.toString().length() > 0 ? "$" + name + "("
							+ params + ")" : "$" + name);
				} else {
					expr = functionName
							+ "("
							+ (params.toString().length() > 0 ? "$" + name
									+ "(" + params + ")" : "$" + name) + ")";

				}
				if (removeWhiteSpaces(expr).equals(removeWhiteSpaces(clause))) {
					// Let's evaluate this directly.
					exact = true;
					Class expressionContextClass = null;

					try {
						StringBuilder objectizedParams = new StringBuilder();
						StringTokenizer allParams = new StringTokenizer(
								params.toString(), ",");
						while (allParams.hasMoreElements()) {
							String param = allParams.nextToken();
							// Try to evaluate expression (NOTE THAT IF
							// REFERENCES ARE MADE TO EITHER NAVAJO OR MAPPABLE
							// OBJECTS THIS WILL FAIL
							// SINCE THESE OBJECTS ARE NOT KNOWN AT COMPILE
							// TIME!!!!!!!!!!!!!!1
							Operand op = Expression.evaluate(param, null);
							Object v = op.value;
							if (v instanceof String) {
								objectizedParams.append("\"" + v + "\"");
							} else if (v instanceof Integer) {
								objectizedParams.append("new Integer(" + v
										+ ")");
							} else if (v instanceof Long) {
								objectizedParams.append("Long.valueOf(" + v + ")");
							} else if (v instanceof Float) {
								objectizedParams.append("new Float(" + v + ")");
							} else if (v instanceof Boolean) {
								objectizedParams.append("new Boolean(" + v
										+ ")");
							} else if (v instanceof Double) {
								objectizedParams
										.append("Double.valueOf(" + v + ")");
							} else if (v == null) {
							    // Null support
							    objectizedParams.append(v);
							} else {
								throw new UserException(-1,
										"Unknown type encountered during compile time: "
												+ v.getClass().getName()
												+ " @clause: " + clause);
							}
							if (allParams.hasMoreElements()) {
								objectizedParams.append(',');
							}
						}

						try {
							expressionContextClass = Class.forName(className,
									false, loader);
						} catch (Exception e) {
							throw new Exception("Could not find adapter: "
									+ className);
						}

						String attrType = MappingUtils.getFieldType(
								expressionContextClass, name.toString());

						// Try to locate class:
						if (!functionName.equals("")) {
							try {
								Class.forName("com.dexels.navajo.functions."
										+ functionName, false, loader);
							} catch (Exception e) {
								throw new Exception(
										"Could not find Navajo function: "
												+ functionName);
							}

						}
						call = objectName + ".get"
								+ (name.charAt(0) + "").toUpperCase()
								+ name.substring(1) + "("
								+ objectizedParams.toString() + ")";

						if (attrType.equals("int")) {
							call = "new Integer(" + call + ")";
						} else if (attrType.equals("float")
								|| attrType.equals("double")) {
							call = "Double.valueOf(" + call + ")";
						} else if (attrType.equals("boolean")) {
							call = "new Boolean(" + call + ")";
						} else if (attrType.equals("long")) {
							call = "Long.valueOf(" + call + ")";
						}
					} catch (ClassNotFoundException cnfe) {
						if (expressionContextClass == null) {
							throw new UserException(-1,
									"Error in script: Could not find adapter: "
											+ className + " @clause: " + clause);
						} else {
							throw new UserException(-1,
									"Error in script: Could not locate function: "
											+ functionName + " @ clause: "
											+ clause);
						}
					} catch (Throwable e) {
						exact = false;
					}
				}
			}
		}

		// Try to evaluate clause directly (compile time).
		if ((!exact) && !clause.equals("TODAY") && !clause.equals("null")
				&& (clause.indexOf("[") == -1) && (clause.indexOf("$") == -1)
				&& (clause.indexOf("(") == -1) && (clause.indexOf("+") == -1)) {
			try {
				Operand op = Expression.evaluate(clause, null);
				Object v = op.value;
				exact = true;
				if (v instanceof String) {
					call = replaceQuotesValue((String) v);
				} else if (v instanceof Integer) {
					call = "new Integer(" + v + ")";
				} else if (v instanceof Long) {
					call = "Long.valueOf(" + v + ")";
				} else if (v instanceof Float) {
					call = "new Float(" + v + ")";
				} else if (v instanceof Boolean) {
					call = "new Boolean(" + v + ")";
				} else if (v instanceof Double) {
					call = "Double.valueOf(" + v + ")";
				} else
					throw new UserException(-1,
							"Unknown type encountered during compile time: "
									+ v.getClass().getName() + " @clause: "
									+ clause);

			} catch (NullPointerException|TMLExpressionException ne) {
				exact = false;
			} catch (SystemException se) {
				exact = false;
				if (clause.length() == 0 || clause.charAt(0) != '#') {
					throw new UserException(-1,
							"Could not compile script, Invalid expression: "
									+ clause);
				}
			} catch (Throwable e) {
				exact = false;
			}
		}

		if (!exact && clause.equals("null")) {
			call = "null";
			exact = true;
		}

		// Use Expression.evaluate() if expression could not be executed in an
		// optimized way.
        result.append(printIdent(ident) + "op = Expression.evaluate(" + replaceQuotes(clause)
                + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null, getEvaluationParams());\n");
        result.append(printIdent(ident) + "sValue = op.value;\n");

		return result.toString();
	}

	private String getCDATAContent(Node n) {
		NodeList nl = n.getChildNodes();
		for (int j = 0; j < nl.getLength(); j++) {
			if (nl.item(j).getNodeType() == Node.CDATA_SECTION_NODE) {
				return nl.item(j).getNodeValue();
			}
		}
		return null;
	}

	/**
	 * Extract the value from an expression node.
	 *
	 * @param exprElmnt
	 * @return
	 * @throws Exception
	 */
	private Object[] getExpressionValue(Element exprElmnt,
			Boolean isStringOperand) throws UserException {
		String value = null;

		isStringOperand = Boolean.FALSE;

		Element valueElt = (Element) XMLutils.findNode(exprElmnt, "value");

		if (valueElt == null) {
			value = XMLutils.XMLUnescape(exprElmnt.getAttribute("value"));
		} else {
			value = getCDATAContent(valueElt);
			if (value == null) {
				Node child = valueElt.getFirstChild();
				if ( child == null ) {
					logger.error("Could not child in element: {}", valueElt.getNodeName());
					throw new UserException("Invalid script: " + valueElt.getNodeName());
				}
				value = child.getNodeValue();
			}
		}

		// Check if operand is given as text node between <expression> tags.
		if (value == null || value.equals("")) {
			Node child = exprElmnt.getFirstChild();
			String cdata = getCDATAContent(exprElmnt);
			if (cdata != null) {
				isStringOperand = Boolean.TRUE;
				value = cdata;
			} else if (child != null) {
				isStringOperand = Boolean.TRUE;
				value = child.getNodeValue();
			} else {
				throw new UserException(
						"Error line "+ exprElmnt.getAttribute("linenr") +":"+ exprElmnt.getAttribute("startoffset")  +" @"
								+ (exprElmnt.getParentNode() + "/" + exprElmnt)
								+ ": <expression> node should either contain a value attribute or a text child node: >"
								+ value + "<");
			}
		} else {
			value = value.trim();
			value = value.replaceAll("\n", " ");
			value = value.replaceAll("\r", " ");
			value = XMLutils.XMLUnescape(value);
		}

		return new Object[] { removeNewLines(value), isStringOperand };
	}

	public String expressionNode(int ident, Element exprElmnt, int leftOver,
			String className, String objectName) throws UserException {

		StringBuilder result = new StringBuilder();
		Boolean isStringOperand = false;

		String condition = exprElmnt.getAttribute("condition");

		Object[] valueResult = getExpressionValue(exprElmnt, isStringOperand);
		String value = (String) valueResult[0];
		isStringOperand = (Boolean) valueResult[1];

		if (!condition.equals("")) {
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access))");
		}

		result.append(printIdent(ident) + "{\n");
		ident += 2;

		if (!isStringOperand) {
			result.append(optimizeExpresssion(ident, value, className,
					objectName));
		} else {
			result.append(printIdent(ident) + "sValue = " + replaceQuotesValue(value) + ";\n");
		}

		// Check dependencies.
		Dependency[] allDeps = ExpressionValueDependency.getDependencies(value);
		for (int a = 0; a < allDeps.length; a++) {
			addDependency(
					"dependentObjects.add( new ExpressionValueDependency(-1, \""
							+ allDeps[a].getId() + "\", \""
							+ allDeps[a].getType() + "\"));\n",
					"FIELD" + "ExpressionValueDependency" + ";"
							+ allDeps[a].getType() + ";" + allDeps[a].getId());
		}

		result.append(printIdent(ident) + "matchingConditions = true;\n");

		ident -= 2;
		result.append(printIdent(ident) + "}\n");

		if (leftOver > 0) {
			result.append(printIdent(ident) + " else \n");

		}

		return result.toString();

	}

	public String operationsNode(int ident, Element n) throws MappingException {

		StringBuilder result = new StringBuilder();

		NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("operation")) {
                Element e = (Element) children.item(i);
                String entity = e.getAttribute("entity");
                String service = e.getAttribute("service");
                String tenant = e.getAttribute("tenant");
                String validationService = e.getAttribute("validationService");
                String method = e.getAttribute("method");
                String debug = e.getAttribute("debug");
                String scopes = e.getAttribute("scopes");
                String description = e.getAttribute("description");

                result.append(printIdent(ident) + "if (true) {\n");

                String operationString = "com.dexels.navajo.document.Operation o = " + "NavajoFactory.getInstance().createOperation(access.getOutputDoc(), "
                        + "\"" + method + "\", \"" + service;
                if (!validationService.equals("")) {
                    operationString += "\", \"" + validationService;
                }

                operationString += "\", \"" + entity + "\", null);\n";

                result.append(printIdent(ident + 2) + operationString);
                // Find extra message definition.
                NodeList extraMessages = e.getChildNodes();
                String extraMessageName = null;
                Element extraMessageElement = null;

                for (int j = 0; j < extraMessages.getLength(); j++) {
                    if (extraMessages.item(j).getNodeName().equals("message")) {
                        extraMessageElement = (Element) extraMessages.item(j);
                        extraMessageName = extraMessageElement.getAttribute("name");
                        break;
                    }
                }

                try {
					if (extraMessageName != null) {
					    DOMSource domSource = new DOMSource(extraMessageElement);
					    Transformer transformer = TransformerFactory.newInstance().newTransformer();
					    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
					    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
					    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
					    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					    StringWriter sw = new StringWriter();
					    StreamResult sr = new StreamResult(sw);
					    transformer.transform(domSource, sr);

					    String extraNavajo = removeNewLines("<tml>" + sw.toString().replace('\"', '\'') + "</tml>");

					    String extraNavajoOperation = "Navajo extra = NavajoFactory.getInstance().createNavajo(new java.io.StringReader(\"" + extraNavajo
					            + "\"));\n" + "o.setExtraMessage(extra.getMessage(\"" + extraMessageName + "\"));\n";
					    result.append(printIdent(ident + 2) + extraNavajoOperation);
					}
				} catch (IllegalArgumentException | TransformerFactoryConfigurationError | TransformerException e1) {
					throw new MappingException("Error parsing operations",e1);
				}
                if (debug != null && !debug.equals("")) {
                    result.append(printIdent(ident + 2) + "o.setDebug(\"" + debug + "\");\n");
                }
                if (tenant != null && !tenant.equals("")) {
                    result.append(printIdent(ident + 2) + "o.setTenant(\"" + tenant + "\");\n");
                }
                if (scopes != null && !scopes.equals("")) {
                    result.append(printIdent(ident + 2) + "o.setScopes(\"" + scopes + "\");\n");
                }
                if (description != null && !description.equals("")) {
                    result.append(printIdent(ident + 2) + "o.setDescription(\"" + description + "\");\n");
                }

                result.append(printIdent(ident + 2) + "access.getOutputDoc().addOperation(o);\n");
                result.append(printIdent(ident) + "}\n");
            }
        }

		return result.toString();

	}

	public String methodsNode(int ident, Element n) {

		StringBuilder result = new StringBuilder();

		// Process children.
		NodeList children = n.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals("method")) {
				Element e = (Element) children.item(i);
				String name = e.getAttribute("name");
				String condition = e.getAttribute("condition");
				String description = e.getAttribute("description");
				condition = (condition == null) ? "" : condition;
				description = (description == null) ? "" : description;
				if (!condition.equals("")) {

					condition = condition.replace('\r', ' ');
					condition = condition.replace('\n', ' ');
					result.append(printIdent(ident) + "if (Condition.evaluate("
							+ replaceQuotes(condition)
							+ ", access.getInDoc(), null, null, null,access)) {\n");
				} else {
					result.append(printIdent(ident) + "if (true) {\n");
					// Get required messages.
				}
				result.append(printIdent(ident + 2)
						+ "com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), \""
						+ name + "\", \"\");\n");
				result.append(printIdent(ident + 2) + "m.setDescription(\""
						+ description + "\");\n");
				NodeList required = e.getChildNodes();
				for (int j = 0; j < required.getLength(); j++) {
					if (required.item(j).getNodeName().equals("required")) {
						String reqMsg = ((Element) required.item(j))
								.getAttribute("message");
						String filter = ((Element) required.item(j))
								.getAttribute("filter");
						result.append(printIdent(ident + 2)
								+ "m.addRequired(\"" + reqMsg + "\"" + ", \""
								+ filter + "\");\n");
					}
				}
				result.append(printIdent(ident + 2)
						+ "access.getOutputDoc().addMethod(m);\n");
				result.append(printIdent(ident) + "}\n");
			}
		}

		return result.toString();
	}

	//method for implementing if-condition --b
	public String blockNode(int ident, Element n, String className,
			String objectName, List<Dependency> deps, String tenant) throws ClassNotFoundException, UserException, IOException, MetaCompileException, ParseException, MappingException{

		StringBuilder result = new StringBuilder();

		String condition = n.getAttribute("condition");

		condition = (condition == null) ? "" : condition;

		boolean conditionClause = false;

		if (!condition.equals("")) {
			conditionClause = true;
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
			ident += 2;
		}

//		Element nextElt = getNextElement(n);

		NodeList children = n.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					result.append(compile(ident + 4, children.item(i), className, objectName, deps, tenant));
				}
			}

			if (conditionClause) {
				ident -= 2;
				result.append(printIdent(ident) + "} // EOF message condition \n");
			}

			return result.toString();

	}

	@SuppressWarnings("unchecked")
	public String messageNode(int ident, Element n, String className,
			String objectName, List<Dependency> deps, String tenant) throws MappingException, ClassNotFoundException, UserException, IOException, MetaCompileException, ParseException {
		StringBuilder result = new StringBuilder();

		String messageName = n.getAttribute("name");
		String condition = n.getAttribute("condition");
		String type = n.getAttribute("type");
		String mode = n.getAttribute("mode");
		String count = n.getAttribute("count");
		String startindex = n.getAttribute("start_index");
		String orderby = n.getAttribute("orderby");
		String extendsMsg = n.getAttribute("extends");
		String scopeMsg = n.getAttribute("scope");
		String method = n.getAttribute("method");
		String subType = n.getAttribute("subtype");

		type = (type == null) ? "" : type;
		mode = (mode == null) ? "" : mode;
		condition = (condition == null) ? "" : condition;
		count = (count == null || count.equals("")) ? "1" : count;
		method = (method == null) ? "" : method;
		subType = (subType == null) ? "" : subType;
		int startIndex = (startindex == null || startindex.equals("")) ? -1
				: Integer.parseInt(startindex);

		boolean conditionClause = false;

		// If <message> node is conditional:
		if (!condition.equals("")) {
			conditionClause = true;
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
			ident += 2;
		}

		Element nextElt = getNextElement(n);
		String ref = "";
		String filter = "";
		String startElement = "";
		String elementOffset = "";

		boolean isArrayAttr = false;
		boolean isSubMapped = false;
		boolean forceArray = false;
		boolean isIterator = false;
		boolean isMappedMessage = false;
		String mapPath = null;

		// Check if <message> is mapped to an object attribute:
		if (nextElt != null && nextElt.getNodeName().equals("map")
				&& nextElt.getAttribute("ref") != null && !nextElt.getAttribute("ref").equals("") ) {

			String refOriginal = null;
			isSubMapped = true;
			isMappedMessage = false;
			refOriginal = nextElt.getAttribute("ref");

			// Check if ref contains [ char, if it does, an array message of a selection property is mapped.
			if ( refOriginal.length() > 0 && refOriginal.charAt(0) == '[' ) {
				refOriginal = refOriginal.replaceAll("\\[", "");
				refOriginal = refOriginal.replaceAll("\\]", "");
				isMappedMessage = true;
				isArrayAttr = true;
				type = Message.MSG_TYPE_ARRAY;
			} else if ( refOriginal.indexOf('$') != -1 ) {
				// Remove leading $ (if present).
				refOriginal = refOriginal.replaceAll("\\$", "");
			}

			if ( !isMappedMessage && refOriginal.indexOf('/') != -1) {
				ref = refOriginal.substring(refOriginal.lastIndexOf('/') + 1,
						refOriginal.length());
				mapPath = refOriginal
						.substring(0, refOriginal.lastIndexOf('/'));
			} else {
				ref = refOriginal;
			}

			forceArray = type.equals(Message.MSG_TYPE_ARRAY);
			filter = nextElt.getAttribute("filter");
			if (filter != null) {
				filter = filter.replace('\r', ' ');
				filter = filter.replace('\n', ' ');
			}
			startElement = nextElt.getAttribute("start_element");
			elementOffset = nextElt.getAttribute("element_offset");
			startElement = ((startElement == null || startElement.equals("")) ? ""
					: startElement);
			elementOffset = ((elementOffset == null || elementOffset.equals("")) ? ""
					: elementOffset);

			if ( !isMappedMessage ) {
				try {
					if (mapPath != null) {
						contextClass = locateContextClass(mapPath, 0);
						className = contextClass.getName();
					} else {
						contextClass = Class.forName(className, false, loader);
					}
				} catch (Exception e) {
					throw new MappingException("Could not find field: " + className + "/" + mapPath, e);
				}

				addDependency("dependentObjects.add( new JavaDependency( -1, \""
						+ className + "\"));\n", "JAVA" + className);

				if (DomainObjectMapper.class.isAssignableFrom(contextClass)) {
					isArrayAttr = forceArray;
					type = Message.MSG_TYPE_ARRAY;
				} else {
					isArrayAttr = MappingUtils.isArrayAttribute(contextClass, ref);
					isIterator = MappingUtils
							.isIteratorAttribute(contextClass, ref);

					if (isIterator) {
						isArrayAttr = true;
					}
					if (isArrayAttr) {
						type = Message.MSG_TYPE_ARRAY;
					}
				}
			}

		}
		// Create the message(s). Multiple messages are created if count > 1.
		result.append(printIdent(ident)
				+ "count = "
				+ (count.equals("1") ? "1"
						: "((Integer) Expression.evaluate(\""
								+ count
								+ "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,null,null,getEvaluationParams()).value).intValue()")
				+ ";\n");
		String messageList = "messageList" + (messageListCounter++);
		result.append(printIdent(ident) + "Message [] " + messageList
				+ " = null;\n");

		String orderbyExpression = ("".equals(orderby) ? "\"\""
				: "(String) Expression.evaluate("
						+ replaceQuotes(orderby)
						+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,null,null,getEvaluationParams()).value");

		if (n.getNodeName().equals("message")) {
			result.append(printIdent(ident)
					+ messageList
					+ " = MappingUtils.addMessage(access.getOutputDoc(), currentOutMsg, \""
					+ messageName + "\", \"\", count, \"" + type + "\", \""
					+ mode + "\", " + orderbyExpression + ");\n");
			result.append("");
		} else { // must be parammessage.

			result.append(printIdent(ident)
					+ messageList
					+ " = MappingUtils.addMessage(access.getInDoc(), currentParamMsg, \""
					+ messageName + "\", \"\", count, \"" + type + "\", \""
					+ mode + "\");\n");
		}

		result.append(printIdent(ident) + "for (int messageCount" + (ident)
				+ " = 0; messageCount" + (ident) + " < " + messageList
				+ ".length; messageCount" + (ident) + "++) {\n if (!kill) {\n");

		if (n.getNodeName().equals("message")) {
			result.append(printIdent(ident + 2)
					+ "outMsgStack.push(currentOutMsg);\n");
			result.append(printIdent(ident + 2) + "currentOutMsg = "
					+ messageList + "[messageCount" + (ident) + "];\n");

			if (subType != null && !subType.equals("")) {
				result.append(printIdent(ident + 2)
						+ "currentOutMsg.setSubType(\"" + subType + "\");\n");
				
				String[] subTypeElements = subType.split(",");
				for (String subTypeElement: subTypeElements) {
				    if (subTypeElement.startsWith("interface=")) {
				        for (String iface: subTypeElement.replace("interface=", "").split(";")) {
		                    String version = "0";
		                    if (iface.indexOf(".") != -1) {
		                        version = iface.substring(iface.indexOf(".") + 1, iface.indexOf("?") == -1 ? iface.length() : iface.indexOf("?"));
		                    }
		                    String replace = "." + version;
		                    iface = iface.replace(replace, "");
		                    
		                    String options = null;
		                    if (iface.indexOf('?') > 0) {
		                        options = iface.split("\\?")[1];
		                        iface = iface.split("\\?")[0];
		                    }
		                    addDependency(
	                                "dependentObjects.add( new ExtendDependency( Long.valueOf(\""
	                                        + ExtendDependency.getScriptTimeStamp(iface)
	                                        + "\"), \"" + iface + "\"));\n", "EXTEND" + iface);
		                    deps.add(new ExtendDependency(ExtendDependency.getScriptTimeStamp(iface),iface ));
		                }
				    }
				}
			}
			if (extendsMsg != null && !extendsMsg.equals("")) {
				result.append(printIdent(ident + 2)
						+ "currentOutMsg.setExtends(\"" + extendsMsg + "\");\n");


				if (extendsMsg.startsWith("navajo://")) {
				    String ext = extendsMsg.substring(9);
                    String version = "0";
                    if (ext.indexOf('.') != -1) {
                        version = ext.substring(ext.indexOf('.') + 1, ext.indexOf('?') == -1 ? ext.length() : ext.indexOf('?'));
                    }
                    String rep = "." + version;
                    ext = ext.replace(rep, "");
		            String[] superEntities = ext.split(",");
		            for (String superEntity : superEntities) {
		                if (superEntity.indexOf('?') > 0) {
		                    superEntity = superEntity.split("\\?")[0];
		                }
		                addDependency(
		        				"dependentObjects.add( new ExtendDependency( Long.valueOf(\""
		        						+ ExtendDependency.getScriptTimeStamp(superEntity)
		        						+ "\"), \"" + superEntity + "\"));\n", "EXTEND" + superEntity);

		                deps.add(new ExtendDependency(ExtendDependency.getScriptTimeStamp(superEntity),superEntity ));
		            }
				}


			}
			if (scopeMsg != null) {
				result.append(printIdent(ident + 2)
						+ "currentOutMsg.setScope(\"" + scopeMsg + "\");\n");
			}
			result.append(printIdent(ident + 2)
					+ "currentOutMsg.setMethod(\"" + method + "\");\n");

		} else { // must be parammessage.
			result.append(printIdent(ident + 2)
					+ "paramMsgStack.push(currentParamMsg);\n");
			result.append(printIdent(ident + 2) + "currentParamMsg = "
					+ messageList + "[messageCount" + (ident) + "];\n");
		}

		result.append(printIdent(ident + 2)
				+ "access.setCurrentOutMessage(currentOutMsg);\n");

		if (isSubMapped && isMappedMessage ) {

			boolean isParam = false;

			result.append(printIdent(ident + 2)
					+ "// Map message(s) to message\n");
			String messageListName = "messages" + ident;

			result.append(printIdent(ident + 2) + "List "
					+ messageListName + " = null;\n");
			result.append(printIdent(ident + 2)
					+ "inSelectionRef = MappingUtils.isSelection(currentInMsg, access.getInDoc(), \""
					+ ref + "\");\n");

			result.append(printIdent(ident + 2) + "if (!inSelectionRef)\n");
			result.append(printIdent(ident + 4)
					+ messageListName
					+ " = MappingUtils.getMessageList(currentInMsg, access.getInDoc(), \""
					+ ref + "\", \"" + ""
					+ "\", currentMap, currentParamMsg,access);\n");
			result.append(printIdent(ident + 2) + "else\n");
			result.append(printIdent(ident + 4)
					+ messageListName
					+ " = MappingUtils.getSelectedItems(currentInMsg, access.getInDoc(), \""
					+ ref + "\");\n");
			String loopCounterName = "j" + subObjectCounter++;

			variableClipboard.add("int " + loopCounterName + ";\n");

			result.append(printIdent(ident + 2) + "for (" + loopCounterName
					+ " = 0; " + loopCounterName + " < " + messageListName
					+ ".size(); " + loopCounterName
					+ "++) {\n if (!kill){\n");
			// currentInMsg, inMsgStack
			ident += 4;
			result.append(printIdent(ident)
					+ "inMsgStack.push(currentInMsg);\n");
			if (isParam) {
				result.append(printIdent(ident)
						+ "paramMsgStack.push(currentParamMsg);\n");
			}
			result.append(printIdent(ident)
					+ "inSelectionRefStack.push(new Boolean(inSelectionRef));\n");

			if (isParam) {
				result.append(printIdent(ident) + "if (!inSelectionRef)\n");
				result.append(printIdent(ident + 2)
						+ "currentParamMsg = (Message) " + messageListName
						+ ".get(" + loopCounterName + ");\n");
			}
			result.append(printIdent(ident) + "if (!inSelectionRef)\n");
			result.append(printIdent(ident + 2)
					+ "currentInMsg = (Message) " + messageListName
					+ ".get(" + loopCounterName + ");\n");
			result.append(printIdent(ident) + "else \n");
			// currentSelection.
			result.append(printIdent(ident + 2)
					+ "currentSelection = (Selection) " + messageListName
					+ ".get(" + loopCounterName + ");\n");

			// If filter is specified, evaluate filter first:
			if (!filter.equals("")) {
				result.append(printIdent(ident + 4)
						+ "if (inSelectionRef || Condition.evaluate("
						+ replaceQuotes(filter)
						+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) {\n");
				ident += 2;
			}

			if (n.getNodeName().equals("message")) {
				result.append(printIdent(ident + 4)
						+ "outMsgStack.push(currentOutMsg);\n");
				result.append(printIdent(ident + 4)
						+ "currentOutMsg = MappingUtils.getMessageObject(\""
						+ MappingUtils.getBaseMessageName(messageName)
						+ "\", currentOutMsg, true, access.getOutputDoc(), false, \"\", "
						+ "-1"
						+ ");\n");
				result.append(printIdent(ident + 4)
						+ "access.setCurrentOutMessage(currentOutMsg);\n");
			} else { // parammessage.
				result.append(printIdent(ident + 4)
						+ "paramMsgStack.push(currentParamMsg);\n");
				result.append(printIdent(ident + 4)
						+ "currentParamMsg = MappingUtils.getMessageObject(\""
						+ MappingUtils.getBaseMessageName(messageName)
						+ "\", currentParamMsg, true, access.getInDoc(), false, \"\", "
						+  "-1"
						+ ");\n");
			}

			result.append(printIdent(ident) + "try {\n");
			ident = ident + 2;

			NodeList children = nextElt.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					result.append(compile(ident + 4, children.item(i), className, objectName, deps, tenant));
				}
			}

			ident = ident - 2;
			result.append(printIdent(ident) + "} catch (Exception e"
					+ ident + ") {\n");

			result.append(printIdent(ident) + "}\n");

			if (n.getNodeName().equals("message")) {
				result.append(printIdent(ident + 2)
						+ "currentOutMsg = (Message) outMsgStack.pop();\n");
				result.append(printIdent(ident + 2)
						+ "access.setCurrentOutMessage(currentOutMsg);\n");
			} else {
				result.append(printIdent(ident)
						+ "currentParamMsg = (Message) paramMsgStack.pop();\n");
			}

			if (filter != null && !filter.equals("")) {
				ident -= 2;
				result.append(printIdent(ident + 4) + "}\n");
			}

			result.append(printIdent(ident)
					+ "currentInMsg = (Message) inMsgStack.pop();\n");
			if (isParam) {
				result.append(printIdent(ident)
						+ "currentParamMsg = (Message) paramMsgStack.pop();\n");
			}
			result.append(printIdent(ident)
					+ "inSelectionRef = ((Boolean) inSelectionRefStack.pop()).booleanValue();\n");
			result.append(printIdent(ident) + "currentSelection = null;\n");

			ident -= 4;
			result.append(printIdent(ident + 2) + "}\n} // FOR loop for "
					+ loopCounterName + "\n");


		} else if (isSubMapped && isArrayAttr) {
			type = Message.MSG_TYPE_ARRAY_ELEMENT;
			String lengthName = "length" + (lengthCounter++);

			String mappableArrayName = "mappableObject" + (objectCounter++);

			boolean isDomainObjectMapper = false;

			contextClassStack.push(contextClass);
			String subClassName = null;
			NodeList children = nextElt.getChildNodes();
			try {
				subClassName = MappingUtils.getFieldType(contextClass, ref);
				contextClass = Class.forName(subClassName, false, loader);
			} catch (Exception e) {
				isDomainObjectMapper = contextClass
						.isAssignableFrom(DomainObjectMapper.class);
				subClassName = "com.dexels.navajo.mapping.bean.DomainObjectMapper";
				contextClass = com.dexels.navajo.mapping.bean.DomainObjectMapper.class;
				if (isDomainObjectMapper) {
					type = "java.lang.Object";
				} else {
					throw new MappingException("Could not find adapter: "
							+ subClassName);
				}
			}

			addDependency("dependentObjects.add( new JavaDependency( -1, \""
					+ subClassName + "\"));\n", "JAVA" + subClassName);

			// Extract ref....
			if (mapPath == null) {
				if (isDomainObjectMapper) {
					result.append(printIdent(ident + 2) + "try {\n");
					result.append(printIdent(ident + 2)
							+ mappableArrayName
							+ " = com.dexels.navajo.mapping.bean.DomainObjectMapper.createArray( (Object []) (("
							+ className
							+ ") currentMap.myObject).getDomainObjectAttribute(\""
							+ ref + "\", null) ) ;\n");
					result.append(printIdent(ident + 2)
							+ "} catch (Exception e) {\n");
					result.append(printIdent(ident + 2)
							+ "  String subtype = (("
							+ className
							+ ") currentMap.myObject).getDomainObjectAttribute(\""
							+ ref + "\", null).getClass().getName();\n");
					result.append(printIdent(ident + 2)
							+ "  throw new Exception(\" Could not cast " + ref
							+ "(type = \" + subtype + \") to an array\");\n");
					result.append(printIdent(ident + 2) + "}\n");
				} else {
					result.append(printIdent(ident + 2)
							+ mappableArrayName
							+ " = (("
							+ className
							+ ") currentMap.myObject).get"
							+ ((ref.charAt(0) + "").toUpperCase() + ref
									.substring(1)) + "();\n");
				}
			} else {
				if (isDomainObjectMapper) {
					result.append(printIdent(ident + 2) + "try {\n");
					result.append(printIdent(ident + 2)
							+ mappableArrayName
							+ " = com.dexels.navajo.mapping.bean.DomainObjectMapper.createArray( (Object []) (("
							+ className + ") findMapByPath( \"" + mapPath
							+ "\")).getDomainObjectAttribute(\"" + ref
							+ "\", null) ) ;\n");
					result.append(printIdent(ident + 2)
							+ "} catch (Exception e) {\n");
					result.append(printIdent(ident + 2)
							+ "  String subtype = ((" + className
							+ ") findMapByPath( \"" + mapPath
							+ "\")).getDomainObjectAttribute(\"" + ref
							+ "\", null).getClass().getName();\n");
					result.append(printIdent(ident + 2)
							+ "  throw new Exception(\" Could not cast " + ref
							+ "(type = \" + subtype + \") to an array\");\n");
					result.append(printIdent(ident + 2) + "}\n");
				} else {
					result.append(printIdent(ident + 2)
							+ mappableArrayName
							+ " = (("
							+ className
							+ ") findMapByPath( \""
							+ mapPath
							+ "\")).get"
							+ ((ref.charAt(0) + "").toUpperCase() + ref
									.substring(1)) + "();\n");
				}
			}

			String mappableArrayDefinition = (isIterator ? "java.util.Iterator<"
					+ subClassName + "> " + mappableArrayName + " = null;\n"
					: "Object [] " + mappableArrayName + " = null;\n");
			variableClipboard.add(mappableArrayDefinition);

			if (!isIterator) {
				result.append(printIdent(ident + 2) + "int " + lengthName
						+ " = " + "(" + mappableArrayName + " == null ? 0 : "
						+ mappableArrayName + ".length);\n");
			}

			String startIndexVar = "startIndex" + (startIndexCounter++);

			result.append(printIdent(ident + 2) + "int " + startIndexVar
					+ " = " + startIndex + ";\n");
			String startElementVar = "startWith" + (startElementCounter);
			String offsetElementVar = "offset" + (startElementCounter++);

			// Use a different than 0 as start for for loop.
			// result.append(printIdent(ident) + "count = " +

			result.append(printIdent(ident + 2)
					+ "int "
					+ startElementVar
					+ " = "
					+ (startElement.equals("") ? "0"
							: "((Integer) Expression.evaluate(\""
									+ startElement
									+ "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,null,null,getEvaluationParams()).value).intValue()")
					+ ";\n");
			result.append(printIdent(ident + 2)
					+ "int "
					+ offsetElementVar
					+ " = "
					+ (elementOffset.equals("") ? "1"
							: "((Integer) Expression.evaluate(\""
									+ elementOffset
									+ "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,null,null,getEvaluationParams()).value).intValue()")
					+ ";\n");

			if (!isIterator) {
				result.append(printIdent(ident + 2) + "for (int i"
						+ (ident + 2) + " = " + startElementVar + "; i"
						+ (ident + 2) + " < " + lengthName + "; i"
						+ (ident + 2) + " = i" + (ident + 2) + "+"
						+ offsetElementVar + ") {\n if (!kill) {\n");
			} else {
				result.append(printIdent(ident + 2) + "while ("
						+ mappableArrayName + ".hasNext() ) {\n if (!kill) {\n");
			}

			result.append(printIdent(ident + 4)
					+ "treeNodeStack.push(currentMap);\n");

			if (!isIterator) {
				result.append(printIdent(ident + 4)
						+ "currentMap = new MappableTreeNode(access, currentMap, "
						+ mappableArrayName + "[i" + (ident + 2)
						+ "], true);\n");
			} else {
				result.append(printIdent(ident + 4)
						+ "currentMap = new MappableTreeNode(access, currentMap, "
						+ mappableArrayName + ".next(), true);\n");
			}
			result.append(printIdent(ident + 4)
			            + "currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");


			// If filter is specified, evaluate filter first:
			if (filter != null && !filter.equals("")) {
				result.append(printIdent(ident + 4)
						+ "if (Condition.evaluate("
						+ replaceQuotes(filter)
						+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) {\n");
				ident += 2;
			}

			if (n.getNodeName().equals("message")) {
				result.append(printIdent(ident + 4)
						+ "outMsgStack.push(currentOutMsg);\n");
				result.append(printIdent(ident + 4)
						+ "currentOutMsg = MappingUtils.getMessageObject(\""
						+ MappingUtils.getBaseMessageName(messageName)
						+ "\", currentOutMsg, true, access.getOutputDoc(), false, \"\", "
						+ ((startIndex == -1) ? "-1" : startIndexVar + "++")
						+ ");\n");
				result.append(printIdent(ident + 4)
						+ "access.setCurrentOutMessage(currentOutMsg);\n");
			} else { // parammessage.
				result.append(printIdent(ident + 4)
						+ "paramMsgStack.push(currentParamMsg);\n");
				result.append(printIdent(ident + 4)
						+ "currentParamMsg = MappingUtils.getMessageObject(\""
						+ MappingUtils.getBaseMessageName(messageName)
						+ "\", currentParamMsg, true, access.getInDoc(), false, \"\", "
						+ ((startIndex == -1) ? "-1" : startIndexVar + "++")
						+ ");\n");
			}

			result.append(printIdent(ident)
					+ "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(access);}\n");

			String subObjectName = "mappableObject" + (objectCounter++);
			result.append(printIdent(ident + 4) + subObjectName + " = ("
					+ subClassName + ") currentMap.myObject;\n");

			String objectDefinition = subClassName + " " + subObjectName
					+ " = null;\n";
			variableClipboard.add(objectDefinition);

			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					result.append(compile(ident + 4, children.item(i),
							subClassName, subObjectName, deps, tenant));
				}
			}

			contextClass = contextClassStack.pop();

			result.append(printIdent(ident + 2)
					+ "MappingUtils.callStoreMethod(currentMap.myObject);\n");

			if (n.getNodeName().equals("message")) {
				result.append(printIdent(ident + 2)
						+ "currentOutMsg = (Message) outMsgStack.pop();\n");
				result.append(printIdent(ident + 2)
						+ "access.setCurrentOutMessage(currentOutMsg);\n");
			} else {
				result.append(printIdent(ident)
						+ "currentParamMsg = (Message) paramMsgStack.pop();\n");
			}

			if (filter != null && !filter.equals("")) {
				ident -= 2;
				result.append(printIdent(ident + 4) + "}\n");
			}

			result.append(printIdent(ident + 2) + "currentMap.setEndtime();\n"
					+ "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
			result.append(printIdent(ident + 2)
					+ "}\n} // EOF Array map result from contextMap \n");
		} else if (isSubMapped) { // Not an array

			if (mapPath == null) {
				result.append(printIdent(ident + 2)
						+ "treeNodeStack.push(currentMap);\n");

				if (className
						.equals("com.dexels.navajo.mapping.bean.DomainObjectMapper")) {

					result.append(printIdent(ident + 2)
							+ "currentMap = new MappableTreeNode(access, currentMap, new com.dexels.navajo.mapping.bean.DomainObjectMapper( (("
							+ className
							+ ") currentMap.myObject).getDomainObjectAttribute(\""
							+ ref + "\", null) ), false);\n");

				} else {
					result.append(printIdent(ident + 2)
							+ "currentMap = new MappableTreeNode(access, currentMap, (("
							+ className
							+ ") currentMap.myObject).get"
							+ ((ref.charAt(0) + "").toUpperCase() + ref
									.substring(1)) + "(), false);\n");
				}
				result.append(printIdent(ident + 4)
                        + "currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");


			} else {
				String localObjectName = "mappableObject" + (objectCounter++);
				result.append(printIdent(ident + 2) + "Object "
						+ localObjectName + " = findMapByPath( \"" + mapPath
						+ "\");\n");
				result.append(printIdent(ident + 2)
						+ "treeNodeStack.push(currentMap);\n");

				if (className
						.equals("com.dexels.navajo.mapping.bean.DomainObjectMapper")) {
					result.append(printIdent(ident + 2)
							+ "currentMap = new MappableTreeNode(access, currentMap,new com.dexels.navajo.mapping.bean.DomainObjectMapper( (("
							+ className + ")" + localObjectName
							+ ").getDomainObjectAttribute(\"" + ref
							+ "\", null) ), false);\n");
				} else {
					result.append(printIdent(ident + 2)
							+ "currentMap = new MappableTreeNode(access, currentMap, (("
							+ className
							+ ")"
							+ localObjectName
							+ ").get"
							+ ((ref.charAt(0) + "").toUpperCase() + ref
									.substring(1)) + "(), false);\n");
				}
				result.append(printIdent(ident + 4)
                        + "currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");

			}

			result.append(printIdent(ident + 2)
					+ "if (currentMap.myObject != null) {\n");
			result.append(printIdent(ident)
					+ "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(access);}\n");

			contextClassStack.push(contextClass);
			String subClassName = null;

			if (DomainObjectMapper.class.isAssignableFrom(contextClass)) {
				subClassName = "com.dexels.navajo.mapping.bean.DomainObjectMapper";
				contextClass = DomainObjectMapper.class;
			} else {
				subClassName = MappingUtils.getFieldType(contextClass, ref);
				contextClass = null;
				try {
					contextClass = Class.forName(subClassName, false, loader);
				} catch (Exception e) {
					throw new MappingException("Could not find adapter "
							+ subClassName);
				}

			}

			addDependency("dependentObjects.add( new JavaDependency( -1, \""
					+ subClassName + "\"));\n", "JAVA" + subClassName);

			String subObjectName = "mappableObject" + (objectCounter++);
			result.append(printIdent(ident + 4) + subObjectName + " = ("
					+ subClassName + ") currentMap.myObject;\n");

			String objectDefinition = subClassName + " " + subObjectName
					+ " = null;\n";
			variableClipboard.add(objectDefinition);

			NodeList children = nextElt.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				result.append(compile(ident + 4, children.item(i),
						subClassName, subObjectName, deps, tenant));
			}

			contextClass = contextClassStack.pop();

			result.append(printIdent(ident + 2) + "}\n");
			result.append(printIdent(ident + 2) + "currentMap.setEndtime();\n"
					+ "MappingUtils.callStoreMethod(currentMap.myObject);\n"
					+ "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
		} else { // Just some new tags under the "message" tag.
			NodeList children = n.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				result.append(compile(ident + 2, children.item(i), className,
						objectName, deps, tenant));
			}
		}

		if (n.getNodeName().equals("message")) {
			result.append(printIdent(ident)
					+ "currentOutMsg = (Message) outMsgStack.pop();\n");
			result.append(printIdent(ident)
					+ "access.setCurrentOutMessage(currentOutMsg);\n");
		} else {
			result.append(printIdent(ident)
					+ "currentParamMsg = (Message) paramMsgStack.pop();\n");
		}

		result.append(printIdent(ident) + "}\n } // EOF messageList for \n");

		if (conditionClause) {
			ident -= 2;
			result.append(printIdent(ident) + "} // EOF message condition \n");
		}

		return result.toString();
	}

	public String propertyNode(int ident, Element n, boolean canBeSubMapped,
			String className, String objectName) throws UserException, ParseException {
		StringBuilder result = new StringBuilder();

		String propertyName = n.getAttribute("name");
		String direction = n.getAttribute("direction");
		String type = n.getAttribute("type");
		String subtype = n.getAttribute("subtype");
		String lengthStr = n.getAttribute("length");
		int length = ((lengthStr != null && !lengthStr.equals("")) ? Integer
				.parseInt(lengthStr) : -1);
		String value = n.getAttribute("value");
		String description = n.getAttribute("description");
		String cardinality = n.getAttribute("cardinality");
		String condition = n.getAttribute("condition");
		String key = n.getAttribute("key");
		String reference = n.getAttribute("reference");
		String extendsProp = n.getAttribute("extends");
		String bindProp = n.getAttribute("bind");
		String methodProp = n.getAttribute("method");

		value = (value == null) || (value.equals("")) ? "" : value;
		type = (type == null) ? "" : type;
		subtype = (subtype == null) ? "" : subtype;
		description = (description == null) ? "" : description;
		cardinality = (cardinality == null || cardinality.equals("")) ? "1"
				: cardinality;
		condition = (condition == null) ? "" : condition;
		methodProp = (methodProp == null) ? "" : methodProp;

		boolean conditionClause = false;
		if (!condition.equals("")) {
			conditionClause = true;
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
			ident += 2;
		}

		NodeList children = n.getChildNodes();

		boolean hasChildren = false;
		boolean isSelection = false;
		boolean isMapped = false;
		Element mapNode = null;

		StringBuilder optionItems = new StringBuilder();

		int exprCount = countNodes(children, "expression");

		if ("".equals(value)) {
			result.append(printIdent(ident) + "matchingConditions = false;\n");
		}
		Class localContextClass = null;
		for (int i = 0; i < children.getLength(); i++) {
			hasChildren = true;
			// Has condition
			if (children.item(i).getNodeName().equals("expression")) {
				result.append(expressionNode(ident, (Element) children.item(i),
						--exprCount, className, objectName));
			} else if (children.item(i).getNodeName().equals("option")) {
				isSelection = true;
				String optionCondition = ((Element) children.item(i))
						.getAttribute("condition");
				String optionName = ((Element) children.item(i))
						.getAttribute("name");
				String optionValue = ((Element) children.item(i))
						.getAttribute("value");
				String selectedValue = ((Element) children.item(i))
						.getAttribute("selected");

				type = "selection";

				String selected = selectedValue;
				if (!(selected.equals("0") || selected.equals("1"))) {
					selected = "Expression.evaluate("
							+ replaceQuotes(selectedValue)
							+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null,getEvaluationParams()).value";
				}

				// Created condition statement if condition is given!
				String conditional = (optionCondition != null && !optionCondition
						.equals("")) ? "if (Condition.evaluate("
						+ replaceQuotes(optionCondition)
						+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access))\n"
						: "";
				optionItems
						.append(conditional
								+ "p.addSelection(NavajoFactory.getInstance().createSelection(access.getOutputDoc(), \""
								+ optionName + "\", \"" + optionValue + "\", "
								+ selected + "));\n");
			} else if (children.item(i).getNodeName().equals("map")) { // ABout
																		// to
																		// map a
																		// "selection"
																		// property!!!
				if (!canBeSubMapped) {
					throw new ParseException("This property can not be submapped: "
							+ propertyName);
				}
				if (!type.equals("selection")) {
					throw new ParseException(
							"Only selection properties can be submapped: "
									+ propertyName);
				}
				mapNode = (Element) children.item(i);
				isMapped = true;
				isSelection = true;

			} else if (children.item(i) instanceof Element) {
				String tagValue = "<" + n.getNodeName() + " name=\""
						+ propertyName + "\">";
				throw new ParseException("Illegal child tag <"
						+ children.item(i).getNodeName() + "> in " + tagValue
						+ " (Check your script) ");
			}
		}

		if (!hasChildren || isSelection) {
			if (!isSelection) {
				result.append(printIdent(ident)
						+ "sValue = new StringLiteral(\"" + value + "\");\n");
				result.append(printIdent(ident)
                        + "matchingConditions = true;\n");
			} else {
				result.append(printIdent(ident) + "sValue = new String(\""
						+ value + "\");\n");
			}
			result.append(printIdent(ident) + "type = \"" + type + "\";\n");
		} else {
			if (!Property.EXPRESSION_LITERAL_PROPERTY.equals(type)
					&& !Property.EXPRESSION_PROPERTY.equals(type)) {
				result.append(printIdent(ident)
						+ "type = (sValue != null) ? MappingUtils.determineNavajoType(sValue) : \""
						+ type + "\";\n");
			} else {
				result.append(printIdent(ident) + "type = \"" + type + "\";\n");
			}
		}

		result.append(printIdent(ident) + "subtype = \"" + subtype + "\";\n");

		if (n.getNodeName().equals("property")) {
			result.append(printIdent(ident)
					+ "p = MappingUtils.setProperty(false, currentOutMsg, \""
					+ propertyName
					+ "\", sValue, type, subtype, \""
					+ direction
					+ "\", \""
					+ description
					+ "\", "
					+ length
					+ ", access.getOutputDoc(), access.getInDoc(), !matchingConditions);\n");
			if (!"".equals(key)) {
				result.append(printIdent(ident) + "p.setKey(\"" + key
						+ "\");\n");
			}
			if (!"".equals(reference)) {
				result.append(printIdent(ident) + "p.setReference(\""
						+ reference + "\");\n");
			}
			if (!"".equals(extendsProp)) {
				result.append(printIdent(ident) + "p.setExtends(\""
						+ extendsProp + "\");\n");
			}
			if (!"".equals(bindProp)) {
				result.append(printIdent(ident) + "p.setBind(\""
						+ bindProp + "\");\n");
			}
			result.append(printIdent(ident) + "p.setMethod(\""+ methodProp + "\");\n");

		} else { // parameter
			result.append(printIdent(ident)
					+ "MappingUtils.setProperty(true, currentParamMsg, \""
					+ propertyName
					+ "\", sValue, type, subtype, \""
					+ direction
					+ "\", \""
					+ description
					+ "\", "
					+ length
					+ ", access.getOutputDoc(), access.getInDoc(), !matchingConditions);\n");
		}

		if (isMapped) {
			try {
				localContextClass = Class.forName(className, false, loader);
			} catch (Exception e) {
				throw new ParseException("Could not find adapter: " + className);
			}

			addDependency("dependentObjects.add( new JavaDependency( -1, \""
					+ className + "\"));\n", "JAVA" + className);
			if (mapNode == null) {
				throw new IllegalStateException("Unexpected null mapNode");
			}
			String ref = mapNode.getAttribute("ref");
			if ( ref != null && !"".equals(ref)) {
				ref = ref.replaceAll("\\$", ""); // replace $ for refs.
			}
			String filter = mapNode.getAttribute("filter");

			String mappableArrayName = "mappableObject" + (objectCounter++);
			result.append(printIdent(ident + 2) + mappableArrayName + " = "
					+ objectName + ".get"
					+ ((ref.charAt(0) + "").toUpperCase() + ref.substring(1))
					+ "();\n");

			String mappableArrayDefinition = "Object [] " + mappableArrayName
					+ " = null;\n";
			variableClipboard.add(mappableArrayDefinition);

			result.append(printIdent(ident + 2) + "for (int i" + (ident + 2)
					+ " = 0; i" + (ident + 2) + " < " + mappableArrayName
					+ ".length; i" + (ident + 2) + "++) {\n if (!kill) {\n");
			result.append(printIdent(ident + 4)
					+ "treeNodeStack.push(currentMap);\n");
			result.append(printIdent(ident + 4)
					+ "currentMap = new MappableTreeNode(access, currentMap, "
					+ mappableArrayName + "[i" + (ident + 2) + "], true);\n");
			result.append(printIdent(ident + 4)
                    + "currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");

			// If filter is specified, evaluate filter first (31/1/2007)
			if (!filter.equals("")) {
				result.append(printIdent(ident + 4)
						+ "if (Condition.evaluate("
						+ replaceQuotes(filter)
						+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) {\n");
				ident += 2;
			}

			result.append(printIdent(ident + 4) + "String optionName = \"\";\n");
			result.append(printIdent(ident + 4)
					+ "String optionValue = \"\";\n");
			result.append(printIdent(ident + 4)
					+ "boolean optionSelected = false;\n");
			children = mapNode.getChildNodes();
			String subClassName = MappingUtils.getFieldType(localContextClass,
					ref);
			String subClassObjectName = "mappableObject" + (objectCounter++);
			result.append(printIdent(ident + 4) + subClassObjectName + " = ("
					+ subClassName + ") currentMap.myObject;\n");

			String objectDefinition = subClassName + " " + subClassObjectName
					+ " = null;\n";
			variableClipboard.add(objectDefinition);

			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i).getNodeName().equals("property")) {
					Element elt = (Element) children.item(i);
					String subPropertyName = elt.getAttribute("name");
					if (!(subPropertyName.equals("name")
							|| subPropertyName.equals("value") || subPropertyName
								.equals("selected"))) {
						throw new ParseException(
								"Only 'name' or 'value' named properties expected when submapping a 'selection' property");
					}
					NodeList expressions = elt.getChildNodes();
					int leftOver = countNodes(expressions, "expression");

					for (int j = 0; j < expressions.getLength(); j++) {
						if ((expressions.item(j) instanceof Element)
								&& expressions.item(j).getNodeName()
										.equals("expression")) {
							result.append(expressionNode(ident + 4,
									(Element) expressions.item(j), --leftOver,
									subClassName, subClassObjectName));
						}
					}
					if (subPropertyName.equals("name")) {
						result.append(printIdent(ident + 4)
								+ "optionName = (sValue != null) ? sValue + \"\" : \"\";\n");
					} else if (subPropertyName.equals("value")) {
						result.append(printIdent(ident + 4)
								+ "optionValue = (sValue != null) ? sValue + \"\" : \"\";\n");
					} else {
						result.append(printIdent(ident + 4)
								+ "optionSelected = (sValue != null) ? ((Boolean) sValue).booleanValue() : false;\n");
					}
				} else if (children.item(i).getNodeName().equals("debug")) {
					result.append(debugNode(ident, (Element) children.item(i)));
				} else if (children.item(i).getNodeName().equals("param")) {
					result.append(propertyNode(ident,
							(Element) children.item(i), false, className,
							objectName));
				} else if (children.item(i) instanceof Element) {
					throw new ParseException(
							"<property> tag expected while sub-mapping a selection property: "
									+ children.item(i).getNodeName());
				}
			}
			result.append(printIdent(ident + 4)
					+ "p.addSelection(NavajoFactory.getInstance().createSelection(access.getOutputDoc(), optionName, optionValue, optionSelected));\n");

			// If filter is specified add closing bracket (31/1/2007)
			if (!filter.equals("")) {
				ident -= 2;
				result.append(printIdent(ident + 4) + "}\n");
			}

			result.append(printIdent(ident + 4)
					+ "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
			result.append(printIdent(ident + 2)
					+ "}\n} // EOF Array map result to property\n");
		}

		if (isSelection) { // Set selection property stuff.
			result.append(optionItems.toString());
		}
		if (n.getNodeName().equals("property")) {
			result.append("p.setCardinality(\"" + cardinality + "\");\n");
		}
		if (conditionClause) {
			ident -= 2;
			result.append(printIdent(ident) + "} // EOF property condition \n");
		}

		return result.toString();

	}

	private final void checkDependentFieldResource(Class localContextClass,
			String fieldName, List<String> expressionValues,
			List<Dependency> deps) {

		if (!(HasDependentResources.class.isAssignableFrom(localContextClass))) {
			return;
		}

		if (expressionValues == null) {
			return;
		}

		for (int all = 0; all < expressionValues.size(); all++) {

			String expressionValue = expressionValues.get(all);

			DependentResource[] dependentFields = instantiatedAdapters
					.get(localContextClass);

			if (dependentFields == null
					&& HasDependentResources.class.isAssignableFrom(localContextClass)) {
				try {
					HasDependentResources hr = (HasDependentResources) localContextClass
					        .getDeclaredConstructor().newInstance();
					dependentFields = hr.getDependentResourceFields();
				} catch (Throwable t) {
					logger.error("Dependency detection problem:", t);
				}
				instantiatedAdapters.put(localContextClass, dependentFields);
			}

			if (dependentFields == null) {
				return;
			}

			for (int i = 0; i < dependentFields.length; i++) {
				if (fieldName.equals(dependentFields[i].getValue())) {

					if (dependentFields[i] instanceof GenericMultipleDependentResource) {
						Class<? extends AdapterFieldDependency> depClass = dependentFields[i]
								.getDependencyClass();
						try {
							Constructor c = depClass
									.getConstructor(long.class,
											String.class, String.class,
											String.class);
							AdapterFieldDependency afd = (AdapterFieldDependency) c
									.newInstance( -1,
											localContextClass.getName(),
											dependentFields[i].getType(),
											expressionValue);
							deps.add(afd);
							AdapterFieldDependency[] allDeps = (AdapterFieldDependency[]) afd
									.getMultipleDependencies();
							for (int a = 0; a < allDeps.length; a++) {
								addDependency("dependentObjects.add( new "
										+ depClass.getName() + "(-1, \""
										+ allDeps[a].getJavaClass() + "\", \""
										+ allDeps[a].getType() + "\", \""
										+ allDeps[a].getId() + "\"));\n",
										"FIELD" + allDeps[a].getJavaClass()
												+ ";" + allDeps[a].getType()
												+ ";" + fieldName + ";"
												+ allDeps[a].getId());
								deps.add(allDeps[a]);
							}

						} catch (Exception e) {
							logger.info("Error adding dependency, wasn't logged before so reduced level to info. ", e);
						}
					} else {
						addDependency(
								"dependentObjects.add( new AdapterFieldDependency(-1, \""
										+ localContextClass.getName()
										+ "\", \""
										+ dependentFields[i].getType()
										+ "\", \"" + expressionValue
										+ "\"));\n", "FIELD"
										+ localContextClass.getName() + ";"
										+ dependentFields[i].getType() + ";"
										+ fieldName + ";" + expressionValue);
						Dependency d = new AdapterFieldDependency(-1,
								localContextClass.getName(),
								dependentFields[i].getType(), expressionValue);
						deps.add(d);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String fieldNode(int ident, Element n, String className,
			String objectName, List<Dependency> dependencies, String tenant)
			throws UserException, MappingException, ClassNotFoundException, KeywordException, IOException, MetaCompileException, ParseException {

		StringBuilder result = new StringBuilder();

		String attributeOriginal = n.getAttribute("name");
		String condition = n.getAttribute("condition");
		String attribute = null;

		String mapPath = null;

		if (attributeOriginal.indexOf('/') != -1) {
			attribute = attributeOriginal.substring(
					attributeOriginal.lastIndexOf('/') + 1,
					attributeOriginal.length());
			mapPath = attributeOriginal.substring(0,
					attributeOriginal.lastIndexOf('/'));
		} else {
			attribute = attributeOriginal;
		}

		if (attribute == null || attribute.equals(""))
			throw new UserException("Name attribute is required for field tags");

		condition = (condition == null) ? "" : condition;

		String totalMethodName = "set"
				+ (attribute.charAt(0) + "").toUpperCase()
				+ attribute.substring(1, attribute.length());

		String methodName = null;
		if (totalMethodName.indexOf('/') != -1) {
			methodName = totalMethodName.substring(
					totalMethodName.lastIndexOf('/') + 1,
					totalMethodName.length());
		} else {
			methodName = totalMethodName;
		}

		NodeList children = n.getChildNodes();

		if (!condition.equals("")) {
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
		} else {
			result.append(printIdent(ident) + "if (true) {\n");
		}
		// Expression nodes.
		boolean isMapped = false;
		Element mapNode = null;

		int exprCount = countNodes(children, "expression");
		List<String> exprValues = new ArrayList<>();
		for (int i = 0; i < children.getLength(); i++) {
			// Has condition
			if (children.item(i).getNodeName().equals("expression")) {
				result.append(expressionNode(ident + 2,
						(Element) children.item(i), --exprCount, className,
						objectName));
				Boolean b = false;
				exprValues.add((String) getExpressionValue(
						((Element) children.item(i)), b)[0]);
			} else if (children.item(i).getNodeName().equals("map")) {
				isMapped = true;
				mapNode = (Element) children.item(i);
			}
		}

		if (!isMapped) {
			String castedValue = "";
			boolean isDomainObjectMapper = false;
			try {
				Class localContextClass = null;

				try {
					if (mapPath != null) {
						localContextClass = locateContextClass(mapPath, 0);
					} else {
						if (Version.osgiActive()) {
							localContextClass = resolveClassUsingService(className);
						} else {
							localContextClass = Class.forName(className, false,
									loader);
						}
					}

				} catch (Exception e) {
					throw new UserException("Could not find adapter: " + className,
							e);
				}

				addDependency(
						"dependentObjects.add( new JavaDependency( -1, \""
								+ className + "\"));\n", "JAVA" + className);
				dependencies.add(new JavaDependency(-1, className));

				String type = null;

				try {
					type = MappingUtils.getFieldType(localContextClass,
							attribute);
					checkDependentFieldResource(localContextClass, attribute,
							exprValues, dependencies);
				} catch (Exception e) {
					isDomainObjectMapper = localContextClass
							.isAssignableFrom(DomainObjectMapper.class);
					if (isDomainObjectMapper) {
						type = "java.lang.Object";
					} else {
						throw new UserException("Could not find field: "
								+ attribute + " in adapter "
								+ localContextClass.getName(), e);
					}
				}

				if (type.equals("java.lang.String")) {
					castedValue = "(String) sValue";
				} else if (type
						.equals("com.dexels.navajo.document.types.ClockTime")) {
					castedValue = "(com.dexels.navajo.document.types.ClockTime) sValue";
				} else if (type.equals("int")) {
					castedValue = "((Integer) sValue).intValue()";
				} else if (type.equals("double")) {
					castedValue = "((Double) sValue).doubleValue()";
				} else if (type.equals("java.util.Date")) {
					castedValue = "((java.util.Date) sValue)";
				} else if (type.equals("boolean")) {
					castedValue = "((Boolean) sValue).booleanValue()";
				} else if (type.equals("float")) { // sValue is never float,
													// internally always Double!
					castedValue = "(new Float(sValue+\"\")).floatValue()";
				} else if (type
						.equals("com.dexels.navajo.document.types.Binary")) {
					castedValue = "((com.dexels.navajo.document.types.Binary) sValue)";
				} else if (type
						.equals("com.dexels.navajo.document.types.Money")) {
					castedValue = "((com.dexels.navajo.document.types.Money) sValue)";
				} else if (type
						.equals("com.dexels.navajo.document.types.Percentage")) {
					castedValue = "((com.dexels.navajo.document.types.Percentage) sValue)";
				} else if (type.equals("java.lang.Integer")) {
					castedValue = "((Integer) sValue)";
				} else if (type.equals("java.lang.Long")) {
					castedValue = "((Long) sValue)";
				} else if (type.equals("java.lang.Float")) { // sValue is never
																// float,
																// internally
																// always
																// Double!
					castedValue = "new Float(sValue+\"\")";
				} else if (type.equals("java.lang.Double")) {
					castedValue = "((Double) sValue)";
				} else if (type.equals("java.lang.Boolean")) {
					castedValue = "((Boolean) sValue)";
				} else if (type.equals("java.util.List")) {
					castedValue = "((List<Object>) sValue)";
				} else {
					castedValue = "sValue";
				}
			} catch (UserException e) {
				throw new UserException(-1,
						"Error in script: could not find mappable object: "
								+ className, e);
			}

			if (mapPath != null) {
				if (!isDomainObjectMapper) {
					result.append(printIdent(ident + 2) + "(("
							+ locateContextClass(mapPath, 0).getName()
							+ ")findMapByPath(\"" + mapPath + "\"))."
							+ methodName + "(" + castedValue + ");\n");
				} else {
					result.append(printIdent(ident + 2) + "(("
							+ locateContextClass(mapPath, 0).getName()
							+ ")findMapByPath(\"" + mapPath
							+ "\")).setDomainObjectAttribute(\"" + attribute
							+ "\"," + castedValue + ");\n");
				}
			} else {
				if (!isDomainObjectMapper) {
					result.append(printIdent(ident + 2) + objectName + "."
							+ methodName + "(" + castedValue + ");\n");
				} else {
					// set attribute in excluded fields.
					// USE INTROSPECTION METHOD TO CALL METHOD ON PROXIED
					// DOMAIN OBJECT...
					result.append(printIdent(ident + 2) + objectName
							+ ".setDomainObjectAttribute(\"" + attribute
							+ "\"," + castedValue + ");\n");
				}
			}

		} else { // Field with ref: indicates that a message or set of messages
					// is mapped to attribute (either Array Mappable or singular
					// Mappable)
			if (mapNode == null) {
				throw new IllegalStateException("Unexpected null mapNode");
			}
			String ref = mapNode.getAttribute("ref");
			boolean isParam = false;

			if (ref.indexOf("[") != -1) { // remove square brackets...
				ref = ref.replace('[', ' ');
				ref = ref.replace(']', ' ');
				ref = ref.trim();
			}

			if (ref.startsWith("/@")) { // replace @ with __parms__ 'parameter'
										// message indication.
				ref = ref.replaceAll("@", "__parms__/");
				isParam = true;
			}

			String filter = mapNode.getAttribute("filter");
			filter = (filter == null) ? "" : filter;
			result.append(printIdent(ident + 2)
					+ "// And by the way, my filter is " + filter + "\n");

			result.append(printIdent(ident + 2)
					+ "// Map message(s) to field\n");
			String messageListName = "messages" + ident;

			result.append(printIdent(ident + 2) + "List "
					+ messageListName + " = null;\n");
			result.append(printIdent(ident + 2)
					+ "inSelectionRef = MappingUtils.isSelection(currentInMsg, access.getInDoc(), \""
					+ ref + "\");\n");
			result.append(printIdent(ident + 2) + "if (!inSelectionRef)\n");
			result.append(printIdent(ident + 4)
					+ messageListName
					+ " = MappingUtils.getMessageList(currentInMsg, access.getInDoc(), \""
					+ ref + "\", \"" + ""
					+ "\", currentMap, currentParamMsg,access);\n");
			result.append(printIdent(ident + 2) + "else\n");
			result.append(printIdent(ident + 4)
					+ messageListName
					+ " = MappingUtils.getSelectedItems(currentInMsg, access.getInDoc(), \""
					+ ref + "\");\n");

			contextClassStack.push(contextClass);

			Class localContextClass = null;
			try {
				if (mapPath != null) {
					localContextClass = locateContextClass(mapPath, 1);
				} else {
					localContextClass = contextClass;
				}

			} catch (Exception e) {
				throw new UserException("Could not find adapter: " + className, e);
			}

			String type = null;
			try {
				type = MappingUtils.getFieldType(localContextClass, attribute);
			} catch (Exception e) {
				throw new UserException("Could not find field: " + attribute
						+ " in adapter " + localContextClass.getName()
						+ ", mappath = " + mapPath);
			}
			/**
			 * END.
			 */

			boolean isArray = MappingUtils.isArrayAttribute(localContextClass,
					attribute);

			try {
				contextClass = Class.forName(type, false, loader);
			} catch (Exception e) {
				throw new UserException("Could not find adapter: " + type);
			}

			addDependency("dependentObjects.add( new JavaDependency( -1, \""
					+ type + "\"));\n", "JAVA" + type);

			if (isArray) {
				String subObjectsName = "subObject" + subObjectCounter;
				String loopCounterName = "j" + subObjectCounter;
				subObjectCounter++;

				String objectDefinition = type + " [] " + subObjectsName
						+ " = null;\n";
				variableClipboard.add(objectDefinition);
				variableClipboard.add("int " + loopCounterName + ";\n");

				result.append(printIdent(ident + 2) + subObjectsName
						+ " = new " + type + "[" + messageListName
						+ ".size()];\n");
				result.append(printIdent(ident + 2) + "for (" + loopCounterName
						+ " = 0; " + loopCounterName + " < " + messageListName
						+ ".size(); " + loopCounterName
						+ "++) {\n if (!kill){\n");
				// currentInMsg, inMsgStack
				ident += 4;
				result.append(printIdent(ident)
						+ "inMsgStack.push(currentInMsg);\n");
				if (isParam) {
					result.append(printIdent(ident)
							+ "paramMsgStack.push(currentParamMsg);\n");
				}
				result.append(printIdent(ident)
						+ "inSelectionRefStack.push(new Boolean(inSelectionRef));\n");

				if (isParam) {
					result.append(printIdent(ident) + "if (!inSelectionRef)\n");
					result.append(printIdent(ident + 2)
							+ "currentParamMsg = (Message) " + messageListName
							+ ".get(" + loopCounterName + ");\n");
				}
				result.append(printIdent(ident) + "if (!inSelectionRef)\n");
				result.append(printIdent(ident + 2)
						+ "currentInMsg = (Message) " + messageListName
						+ ".get(" + loopCounterName + ");\n");
				result.append(printIdent(ident) + "else\n");
				// currentSelection.
				result.append(printIdent(ident + 2)
						+ "currentSelection = (Selection) " + messageListName
						+ ".get(" + loopCounterName + ");\n");

				if (!filter.equals("")) {
					result.append(printIdent(ident + 4)
							+ "if (inSelectionRef || Condition.evaluate("
							+ replaceQuotes(filter)
							+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) {\n");
					ident += 2;
				}

				result.append(printIdent(ident)
						+ "treeNodeStack.push(currentMap);\n");
				result.append(printIdent(ident)
						+ "currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\""
						+ type + "\").newInstance(), false);\n");
				result.append(printIdent(ident + 4)
                        + "currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");
				result.append(printIdent(ident)
						+ "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(access);}\n");
				result.append(printIdent(ident) + subObjectsName + "["
						+ loopCounterName + "] = (" + type
						+ ") currentMap.myObject;\n");
				result.append(printIdent(ident) + "try {\n");
				ident = ident + 2;

				children = mapNode.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					result.append(compile(ident + 2, children.item(i), type,
							subObjectsName + "[" + loopCounterName + "]",
							dependencies, tenant));
				}

				ident = ident - 2;
				result.append(printIdent(ident) + "} catch (Exception e"
						+ ident + ") {\n");
				result.append(printIdent(ident + 2)
						+ "MappingUtils.callKillOrStoreMethod( "
						+ subObjectsName + "[" + loopCounterName + "], e"
						+ ident + ");\n");
				result.append(printIdent(ident + 2) + "throw e" + ident + ";\n");

				result.append(printIdent(ident) + "}\n");

				result.append(printIdent(ident)
						+ "MappingUtils.callStoreMethod(" + subObjectsName
						+ "[" + loopCounterName + "]);\n");

				result.append(printIdent(ident)
						+ "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

				if (!filter.equals("")) {
					ident -= 2;
					result.append(printIdent(ident + 4) + "}\n");
				}

				result.append(printIdent(ident)
						+ "currentInMsg = (Message) inMsgStack.pop();\n");
				if (isParam) {
					result.append(printIdent(ident)
							+ "currentParamMsg = (Message) paramMsgStack.pop();\n");
				}
				result.append(printIdent(ident)
						+ "inSelectionRef = ((Boolean) inSelectionRefStack.pop()).booleanValue();\n");
				result.append(printIdent(ident) + "currentSelection = null;\n");

				ident -= 4;
				result.append(printIdent(ident + 2) + "}\n} // FOR loop for "
						+ loopCounterName + "\n");

				if (mapPath == null) {
					result.append(printIdent(ident + 2) + objectName + "."
							+ methodName + "(" + subObjectsName + ");\n");
				} else {
					result.append(printIdent(ident + 2) + "(("
							+ locateContextClass(mapPath, 1).getName()
							+ ") findMapByPath(\"" + mapPath + "\"))."
							+ methodName + "(" + subObjectsName + ");\n");
				}

			} else { // Not an array type field, but single Mappable object.

				// Push current mappable object on stack.
				result.append(printIdent(ident)
						+ "treeNodeStack.push(currentMap);\n");

				// Create instance of object.
				result.append(printIdent(ident)
						+ "currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\""
						+ type + "\").newInstance(), false);\n");
				result.append(printIdent(ident + 4)
                        + "currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");


				// Create local variable to address new object.
				String subObjectsName = "subObject" + subObjectCounter;
				subObjectCounter++;

				// push currentInMsg, currentParamMsg and inSelectionRef
				ident += 4;
				result.append(printIdent(ident)
						+ "inMsgStack.push(currentInMsg);\n");
				if (isParam) {
					result.append(printIdent(ident)
							+ "paramMsgStack.push(currentParamMsg);\n");
				}
				result.append(printIdent(ident)
						+ "inSelectionRefStack.push(new Boolean(inSelectionRef));\n");

				if (isParam) {
					result.append(printIdent(ident) + "if (!inSelectionRef)\n");
					result.append(printIdent(ident + 2)
							+ "currentParamMsg = (Message) " + messageListName
							+ ".get(0);\n");
				}
				result.append(printIdent(ident) + "if (!inSelectionRef)\n");
				result.append(printIdent(ident + 2)
						+ "currentInMsg = (Message) " + messageListName
						+ ".get(0);\n");
				result.append(printIdent(ident) + "else\n");
				// currentSelection.
				result.append(printIdent(ident + 2)
						+ "currentSelection = (Selection) " + messageListName
						+ ".get(0);\n");

				// Call load on object.
				result.append(printIdent(ident)
						+ "if ( currentMap.myObject instanceof Mappable) { ((Mappable) currentMap.myObject).load(access);}\n");
				// Assign local variable reference.
				result.append(printIdent(ident) + type + " " + subObjectsName
						+ " = (" + type + ") currentMap.myObject;\n");
				result.append(printIdent(ident) + "try {\n");
				ident = ident + 2;

				// Recursively dive into children.
				children = mapNode.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					result.append(compile(ident + 2, children.item(i), type,
							subObjectsName, dependencies, tenant));
				}

				ident = ident - 2;
				result.append(printIdent(ident) + "} catch (Exception e"
						+ ident + ") {\n");
				result.append(printIdent(ident + 2)
						+ "MappingUtils.callKillOrStoreMethod( "
						+ subObjectsName + ", e" + ident + ");\n");
				result.append(printIdent(ident + 2) + "throw e" + ident + ";\n");
				result.append(printIdent(ident) + "}\n");
				result.append(printIdent(ident)
						+ "MappingUtils.callStoreMethod(" + subObjectsName
						+ ");\n");

				result.append(printIdent(ident)
						+ "currentInMsg = (Message) inMsgStack.pop();\n");
				if (isParam) {
					result.append(printIdent(ident)
							+ "currentParamMsg = (Message) paramMsgStack.pop();\n");
				}
				result.append(printIdent(ident)
						+ "inSelectionRef = ((Boolean) inSelectionRefStack.pop()).booleanValue();\n");
				result.append(printIdent(ident) + "currentSelection = null;\n");
				result.append(printIdent(ident)
						+ "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

				if (mapPath == null) {
					result.append(printIdent(ident + 2) + objectName + "."
							+ methodName + "(" + subObjectsName + ");\n");
				} else {
					result.append(printIdent(ident + 2) + "(("
							+ locateContextClass(mapPath, 1).getName()
							+ ") findMapByPath(\"" + mapPath + "\"))."
							+ methodName + "(" + subObjectsName + ");\n");
				}

			}
			contextClass = contextClassStack.pop();
		}
		result.append(printIdent(ident) + "}\n");
		return result.toString();
	}

	private Class resolveClassUsingService(String className) {
		BundleContext bc = Version.getDefaultBundleContext();
		Collection<ServiceReference<Class>> sr;
		try {
			sr = bc.getServiceReferences(Class.class, "(adapterClass="
					+ className + ")");
			Class result = bc.getService(sr.iterator().next());
			return result;
		} catch (InvalidSyntaxException e) {
			logger.error("Adapter resolution error: ", e);
			return null;
		}

	}

	/**
	 * Locate a contextclass in the class stack based upon a mappath. Depending
	 * on the way this method is called an additional offset to stack index must
	 * be supplied...
	 *
	 */
	private Class locateContextClass(String mapPath, int offset) throws UserException {

		StringTokenizer st = new StringTokenizer(mapPath, "/");

		int count = 0;
		while (st.hasMoreTokens()) {
			String element = st.nextToken();
			if (!"..".equals(element)) {
				logger.debug("Huh? : {}", element);
			}
			count++;
		}
		if (count == 0) {
			return contextClass;
		}
		if ( contextClassStack.size() - count - offset < 0 ) {
			throw new UserException("Could not resolve field: " + mapPath);
		}
		return contextClassStack.get(contextClassStack.size() - count - offset);
	}

	public String breakNode(int ident, Element n) throws UserException {

		StringBuilder result = new StringBuilder();
		String condition = n.getAttribute("condition");
		if (condition.equals("")) {
			result.append(printIdent(ident) + "if (true) {");
		} else {
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");

		}
		String linenr = n.getAttribute("linenr");
        // Check for error definition. If error is defined throw UserException. If
        // conditionError is defined also throw User Exception.
        // Else just BreakEvent

		String error = n.getAttribute("error");
        String conditionDescription = n.getAttribute("conditionDescription");
        String conditionId = n.getAttribute("conditionId");
        if ((conditionDescription != null && !conditionDescription.equals("")) || (conditionId != null && !conditionId.equals(""))) {
            if (conditionDescription == null || conditionDescription.equals("")) {
                throw new UserException(-1, "Validation syntax error: conditionDescription attribute missing or empty");
            }
            if (conditionId == null || conditionId.equals("")) {
                throw new UserException(-1, "Validation syntax error: conditionId attribute missing or empty");
            }
            result.append(printIdent(ident + 2) + "Navajo outMessage = access.getOutputDoc();\n"
                    + "            Message msg = NavajoFactory.getInstance().createMessage(outMessage, \"ConditionErrors\");\n"
                    + "            msg.setType(Message.MSG_TYPE_ARRAY);\n"
                    + "            Message childMsg = NavajoFactory.getInstance().createMessage(outMessage, \"ConditionErrors\");\n"
                    + "            childMsg.setType(Message.MSG_TYPE_ARRAY_ELEMENT);\n" + "            msg.addMessage(childMsg);\n"
                    + "            Property prConditionId = NavajoFactory.getInstance().createProperty(outMessage, \"Id\", Property.STRING_PROPERTY, "
                    + "String.valueOf(Expression.evaluate(" + replaceQuotes(conditionId)
                    + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null,getEvaluationParams()).value)"
                    + "                    ,120, \"\", Property.DIR_OUT);\n"
                    + "            Property prConditionDesc = NavajoFactory.getInstance().createProperty(outMessage, \"Description\", Property.STRING_PROPERTY,\n"
                    + "String.valueOf(Expression.evaluate(" + replaceQuotes(conditionDescription)
                    + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null,getEvaluationParams()).value)"
                    + ", 120, \"\", Property.DIR_OUT);\n"
                    + "            childMsg.addProperty(prConditionId);\n" + "            childMsg.addProperty(prConditionDesc);\n"
                    + "            outMessage.addMessage(msg);");

            result.append(printIdent(ident + 2) + "throw new BreakEvent();\n");
            result.append(printIdent(ident) + "}\n");

        } else if (error == null || error.equals("")) {
			result.append(printIdent(ident + 2) + "Access.writeToConsole(access, \"Breaking at line: " + linenr + "\");\n");
			result.append(printIdent(ident + 2) + "throw new BreakEvent();\n");
			result.append(printIdent(ident) + "}\n");
		}
		else {
			result.append(printIdent(ident + 2) + "op = Expression.evaluate("
					+ replaceQuotes(error)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null,getEvaluationParams());\n");

			result.append(printIdent(ident + 2) + "throw new UserException(UserException.BREAK_EXCEPTION, op.value + \"\");\n");
			result.append(printIdent(ident) + "}\n");
		}

		return result.toString();
	}

	public String debugNode(int ident, Element n) {
		StringBuilder result = new StringBuilder();
		String value = n.getAttribute("value");
		String condition = n.getAttribute("condition");
        if (condition.equals("")) {
            result.append(printIdent(ident) + "if (true) {");
        } else {
            result.append(printIdent(ident)
                    + "if (Condition.evaluate("
                    + replaceQuotes(condition)
                    + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
        }

		result.append(printIdent(ident + 2)
				+ "op = Expression.evaluate("
				+ replaceQuotes(value)
				+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null,getEvaluationParams());\n");
		result.append(printIdent(ident + 2) + "Access.writeToConsole(access, \"DEBUG: \" + op.value );\n");
		result.append(printIdent(ident) + "}\n");
		return result.toString();
	}

	public String logNode(int ident, Element n) {
		StringBuilder result = new StringBuilder();
        String value = n.getAttribute("value");
        String condition = n.getAttribute("condition");
        if (condition.equals("")) {
            result.append(printIdent(ident) + "if (true) {");
        } else {
            result.append(printIdent(ident)
                    + "if (Condition.evaluate("
                    + replaceQuotes(condition)
                    + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
        }

        result.append(printIdent(ident + 2)
                + "op = Expression.evaluate("
                + replaceQuotes(value)
                + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null,getEvaluationParams());\n");
        result.append(printIdent(ident + 2) + "writeToLog( op.value + \"\");\n");
        result.append(printIdent(ident) + "}\n");
        return result.toString();
    }

	public String requestNode(int ident, Element n) {
		return "";
	}

	public String responseNode(int ident, Element n) {
		return "";
	}

	public String runningNode(int ident, Element n) {
		return "";
	}

	public String mapNode(int ident, Element n, List<Dependency> deps,
			String tenant) throws ParseException, ClassNotFoundException, KeywordException, UserException, IOException, MetaCompileException, MappingException {

		StringBuilder result = new StringBuilder();

		String object = n.getAttribute("object");
		String condition = n.getAttribute("condition");
		// If name, is specified it could be an AsyncMap.
		String name = n.getAttribute("name");
		boolean asyncMap = false;
		condition = (condition == null) ? "" : condition;

		boolean conditionClause = false;
		if (!condition.equals("")) {
			conditionClause = true;
			result.append(printIdent(ident)
					+ "if (Condition.evaluate("
					+ replaceQuotes(condition)
					+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
			ident += 2;
		}

		String className = object;

		if (className.equals("")) {
		    throw new ParseException("Error in reading Map xml - found map with empty object! Line " + n.getAttribute("linenr") +":"+ n.getAttribute("startoffset"));
		}

		if (contextClass != null) {
			contextClassStack.push(contextClass);
		}
		contextClass = Class.forName(className, false, loader);
		addDependency("dependentObjects.add( new JavaDependency( -1, \""
				+ className + "\"));\n", "JAVA" + className);

		if (!name.equals("")) { // We have a potential async mappable object.
			if (contextClass.getSuperclass().getName()
					.equals("com.dexels.navajo.mapping.AsyncMappable")) {
				asyncMap = true;

			} else {
				asyncMap = false;
			}
		}

		if (asyncMap) {
			String aoName = "ao" + asyncMapCounter;
			String headerName = "h" + asyncMapCounter;
			String asyncMapFinishedName = "asyncMapFinished" + asyncMapCounter;
			String callbackRefName = "callbackRef" + asyncMapCounter;
			String interruptTypeName = "interruptType" + asyncMapCounter;
			String asyncMapName = "asyncMap" + asyncMapCounter;
			String asyncStatusName = "asyncStatus" + asyncMapCounter;
			String resumeAsyncName = "resumeAsync" + asyncMapCounter;
			asyncMapCounter++;

			variableClipboard.add("boolean " + asyncMapName + ";\n");
			variableClipboard.add("Header " + headerName + ";\n");
			variableClipboard.add("String " + callbackRefName + ";\n");
			variableClipboard.add(className + " " + aoName + ";\n");
			variableClipboard.add("boolean " + asyncMapFinishedName + ";\n");
			variableClipboard.add("boolean " + resumeAsyncName + ";\n");
			variableClipboard.add("String " + asyncStatusName + ";\n");
			variableClipboard.add("String " + interruptTypeName + ";\n");

			result.append(printIdent(ident) + asyncMapName + " = true;\n");
			result.append(printIdent(ident) + headerName
					+ " = access.getInDoc().getHeader();\n");
			result.append(printIdent(ident) + callbackRefName + " = "
					+ headerName + ".getCallBackPointer(\"" + name + "\");\n");
			result.append(printIdent(ident) + aoName + " = null;\n");
			result.append(printIdent(ident) + asyncMapFinishedName
					+ " = false;\n");
			result.append(printIdent(ident) + resumeAsyncName + " = false;\n");
			result.append(printIdent(ident) + asyncStatusName
					+ " = \"request\";\n\n");
			result.append(printIdent(ident) + "if (" + callbackRefName
					+ " != null) {\n");
			ident += 2;
			result.append(printIdent(ident)
					+ aoName
					+ " = ("
					+ className
					+ ") DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().getInstance("
					+ callbackRefName + ");\n");
			result.append(printIdent(ident) + interruptTypeName + " = "
					+ headerName + ".getCallBackInterupt(\"" + name + "\");\n");

			result.append(printIdent(ident)
					+ " if ("
					+ aoName
					+ " == null) {\n "
					+ "  throw new UserException( -1, \"Asynchronous object reference instantiation error: no sych instance (perhaps cleaned up?)\");\n}\n");
			result.append(printIdent(ident)
					+ "if ("
					+ interruptTypeName
					+ ".equals(\"kill\")) { // Kill thread upon client request.\n"
					+ "   "
					+ aoName
					+ ".stop();\n"
					+ "   DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance("
					+ callbackRefName
					+ ");\n"
					+ "   return;\n"
					+ "} else if ( "
					+ aoName
					+ ".isKilled() ) "
					+ "{ "
					+ "     DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance("
					+ callbackRefName + ");\n"
					+ "     throw new UserException(-1, " + aoName
					+ ".getException().getMessage()," + aoName
					+ ".getException());\n" + "} else if (" + interruptTypeName
					+ ".equals(\"interrupt\")) {\n" + "   " + aoName
					+ ".interrupt();\n " + "   return;\n" + "} else if ("
					+ interruptTypeName + ".equals(\"resume\")) { " + "  "
					+ aoName + ".resume();\n" + "return;\n" + "}\n");
			ident -= 2;
			result.append(printIdent(ident) + "} else { // New instance!\n");

			result.append(printIdent(ident)
					+ aoName
					+ " = ("
					+ className
					+ ") classLoader.getClass(\""
					+ object
					+ "\").newInstance();\n"
					+ "  // Call load method for async map in advance:\n"
					+ "  "
					+ aoName
					+ ".load(access);\n"
					+ "  "
					+ callbackRefName
					+ " = DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().addInstance( "
					+ aoName + ", access );\n" + "}\n");

			result.append(printIdent(ident)
					+ "treeNodeStack.push(currentMap);\n");
			result.append(printIdent(ident)
					+ "currentMap = new MappableTreeNode(access, currentMap, "
					+ aoName + ", false);\n");

			result.append(printIdent(ident) + "currentMap.name = \"" + name
					+ "\";\n");
			result.append(printIdent(ident) + "currentMap.ref = "
					+ callbackRefName + ";\n");
			result.append(printIdent(ident) + aoName + ".afterReload(\"" + name
					+ "\", " + callbackRefName + ");\n");

			result.append(printIdent(ident) + "try {\n");
			ident += 2;

			result.append(printIdent(ident) + asyncMapFinishedName + " = "
					+ aoName + ".isFinished(access.getOutputDoc(), access);\n");
			NodeList response = n.getElementsByTagName("response");
			boolean hasResponseNode = false;

			if (response.getLength() > 0) {
				hasResponseNode = true;
			}
			NodeList running = n.getElementsByTagName("running");
			boolean hasRunningNode = false;

			if (running.getLength() > 0) {
				hasRunningNode = true;
			}
			NodeList request = n.getElementsByTagName("request");

			boolean whileRunning = ((Element) response.item(0)).getAttribute(
					"while_running").equals("true");
			result.append(printIdent(ident) + "if (" + asyncMapFinishedName
					+ " || (" + aoName + ".isActivated() && " + hasResponseNode
					+ " && " + whileRunning + ")) {\n");
			result.append(printIdent(ident) + "  " + asyncStatusName
					+ " = \"response\";\n");
			result.append(printIdent(ident) + "  " + aoName
					+ ".beforeResponse(access);\n");
			result.append(printIdent(ident) + "  if (" + aoName
					+ ".isActivated() && " + whileRunning + ") {\n");
			result.append(printIdent(ident) + "     " + resumeAsyncName
					+ " = true;\n");

			result.append(printIdent(ident) + "  }\n");
			result.append(printIdent(ident) + "} else if (!" + aoName
					+ ".isActivated()) {\n");
			result.append(printIdent(ident) + "  " + asyncStatusName
					+ " = \"request\";\n");
			result.append(printIdent(ident) + "} else if (" + hasRunningNode
					+ ") {\n");
			result.append(printIdent(ident) + "  " + asyncStatusName
					+ " = \"running\";\n");
			result.append(printIdent(ident) + "  " + resumeAsyncName
					+ " = true;\n");
			result.append(printIdent(ident) + "}\n");

			NodeList children = null;
			result.append(printIdent(ident) + "if (" + asyncStatusName
					+ ".equals(\"response\")) {\n");
			children = response.item(0).getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					result.append(compile(ident + 2, children.item(i),
							className, aoName, deps, tenant));
				}
			}
			result.append(printIdent(ident) + "} else if (" + asyncStatusName
					+ ".equals(\"request\")) {\n");
			children = request.item(0).getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					result.append(compile(ident + 2, children.item(i),
							className, aoName, deps, tenant));
				}
			}
			result.append(printIdent(ident) + "} else if (" + asyncStatusName
					+ ".equals(\"running\")) {\n");
			children = running.item(0).getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					result.append(compile(ident + 2, children.item(i),
							className, aoName, deps, tenant));
				}
			}
			result.append(printIdent(ident) + "}\n");

			result.append(printIdent(ident)
					+ "if ((currentMap.myObject != null)) {\n");
			result.append(printIdent(ident + 2) + "if (!"
					+ asyncMapFinishedName + ") {\n");
			result.append(printIdent(ident + 4) + "if (" + resumeAsyncName
					+ ") { " + aoName + ".afterResponse(); } else { " + aoName
					+ ".afterRequest(); " + aoName + ".runThread(); }\n");
			result.append(printIdent(ident + 2) + "} else {\n");
			result.append(printIdent(ident + 4)
					+ "MappingUtils.callStoreMethod(currentMap.myObject);\n");
			result.append(printIdent(ident + 4)
					+ "DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance(currentMap.ref);\n");
			result.append(printIdent(ident + 2) + "}\n");
			result.append(printIdent(ident) + "}\n");

			result.append(printIdent(ident) + "} catch (Exception e" + ident
					+ ") {\n");
			result.append(printIdent(ident)
					+ " MappingUtils.callKillOrStoreMethod(currentMap.myObject, e"
					+ ident + ");\n");
			result.append(printIdent(ident)
					+ " DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance(currentMap.ref);\n");

			result.append(printIdent(ident) + "  throw e" + ident + ";\n");
			result.append(printIdent(ident) + "}\n");
		} else {

			result.append(printIdent(ident)
					+ "treeNodeStack.push(currentMap);\n");
			result.append(printIdent(ident)
					+ "currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\""
					+ object + "\").newInstance(), false);\n");
			result.append("currentMap.setNavajoLineNr("+n.getAttribute("linenr") + ");\n");
			n.getAttribute("navajoScript");
			String objectName = "mappableObject" + (objectCounter++);
			result.append(printIdent(ident) + objectName + " = (" + className
					+ ") currentMap.myObject;\n");
			boolean objectMappable = false;

			try {
				objectMappable = MappingUtils.isObjectMappable(className);
			} catch (UserException e) {
				objectMappable = MappingUtils.isObjectMappable(className,
						loader);
			}

			if (objectMappable) {
				result.append(printIdent(ident) + objectName
						+ ".load(access);\n");
			}

			String objectDefinition = className + " " + objectName
					+ " = null;\n";
			variableClipboard.add(objectDefinition);

			result.append(printIdent(ident) + "try {\n");

			NodeList children = n.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				result.append(compile(ident + 2, children.item(i), className,
						objectName, deps, tenant));
			}

			result.append(printIdent(ident) + "} catch (Exception e" + ident
					+ ") {\n");
			result.append(printIdent(ident)
					+ "MappingUtils.callKillOrStoreMethod( " + objectName
					+ ", e" + ident + ");\n");
			result.append(printIdent(ident) + "  throw e" + ident + ";\n");
			result.append(printIdent(ident) + "}\n");
			result.append(printIdent(ident) + "MappingUtils.callStoreMethod("
					+ objectName + ");\n");
			result.append(printIdent(ident)
					+ "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

		}

		if (conditionClause) {
			ident -= 2;
			result.append(printIdent(ident) + "} // EOF map condition \n");
		}
		if (!contextClassStack.isEmpty()) {
			contextClass = contextClassStack.pop();
		} else {
			contextClass = null;
		}
		return result.toString();
	}

	private String fetchScriptFileName(String scriptName) {

		// Check for xml based script
		File f1 = new File(scriptName + ".xml");
		if ( f1.exists() ) {
			return scriptName + ".xml";
		}

		// Check navascript3 based script
		File f2 = new File(scriptName + ".ns");
		if ( f2.exists() ) {
			return scriptName + ".ns";
		}

		// Check for scala script
		File f3 = new File(scriptName + ".scala");
		if ( f3.exists() ) {
			return scriptName + ".scala";
		}

		return null;
	}
	  
	/**
	 * Resolve include nodes in the script: <include
	 * script="[name of script to be included]"/>
	 *
	 * @param scriptPath
	 * @param n
	 * @param parent
	 * @param deps
	 * @throws MetaCompileException
	 * @throws IOException
	 * @throws KeywordException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private final void includeNode(String scriptPath, Node n, Document parent,
			String tenant, List<Dependency> deps) throws UserException, ClassNotFoundException, KeywordException, IOException, MetaCompileException {

		included++;

		if (included > 1000) {
			throw new UserException(-1, "Too many included scripts!!!");
		}

		String script = ((Element) n).getAttribute("script");
		if (script == null || script.equals("")) {
			throw new UserException(-1,
					"No script name found in include tag ("
					+ "missing or empty script attribute): "
							+ n);
		}

		// Construct scriptName:
		// First try if applicationGroup specific script exists.
		String fileName = script + "_" + tenant;

		Document includeDoc = null;
		String includeFileName = fetchScriptFileName(scriptPath + "/" + fileName);
		File includedFile = null;
		
		if (includeFileName != null) {
			includedFile = new File(includeFileName);
			includeDoc = XMLDocumentUtils.createDocument(new FileInputStream(includeFileName), false);
		} else { // no tenant specific include found. Try non-tenant include instead.
			fileName = script;
			includeFileName = fetchScriptFileName(scriptPath + "/" + fileName);
			if ( includeFileName != null ) {
				includedFile = new File(includeFileName);
				if ( includeFileName.endsWith(".ns")) { // It's an NS3 based script
					NS3ToNSXML nstoxml = new NS3ToNSXML();
					nstoxml.initialize();
					try {
						String content = nstoxml.read(includeFileName);
						String tslResult = MapMetaData.getInstance().parse(scriptPath + "/" + fileName + ".ns", nstoxml.parseNavascript(content));
						includeDoc = XMLDocumentUtils.createDocument(new ByteArrayInputStream(tslResult.getBytes()), false);
					} catch (Exception e) {
						throw new UserException(e.getLocalizedMessage(), e);
					}
				} else { // It's an XML based script
					includeDoc = XMLDocumentUtils.createDocument(new FileInputStream(includedFile), false);
				}
			}
		}

		if ( includedFile == null ) {
			logger.error("Could not file include file: {}", script);
			throw new UserException("Could not find include file for script: " +script);
		}
		// Add dependency.
		addDependency(
				"dependentObjects.add( new IncludeDependency( Long.valueOf(\""
						+ IncludeDependency.getFileTimeStamp(includedFile)
						+ "\"), \"" + fileName + "\"));\n", "INCLUDE" + script);
		deps.add(new IncludeDependency(IncludeDependency.getFileTimeStamp(includedFile), fileName , fileName));



		if (includeDoc.getElementsByTagName("tsl").item(0) == null) { // Maybe
																		// it is
																		// navascript??
			String tslResult = MapMetaData.getInstance().parse(
					scriptPath + "/" + fileName + ".xml");
			includeDoc = XMLDocumentUtils.createDocument(
					new ByteArrayInputStream(tslResult.getBytes()), false);
		}

		NodeList content = includeDoc.getElementsByTagName("tsl").item(0)
				.getChildNodes();

		Node nextNode = n.getNextSibling();
		while (nextNode != null && !(nextNode instanceof Element)) {
			nextNode = nextNode.getNextSibling();
		}
		if (nextNode == null || !(nextNode instanceof Element)) {
			nextNode = n;
		}

		Node parentNode = nextNode.getParentNode();

		for (int i = 0; i < content.getLength(); i++) {
			Node child = content.item(i);
			Node imported = parent.importNode(child.cloneNode(true), true);
			parentNode.insertBefore(imported, nextNode);
		}

		parentNode.removeChild(n);

	}

	public String compile(int ident, Node n, String className,
			String objectName, List<Dependency> deps, String tenant)
			throws UserException, ClassNotFoundException, IOException, MetaCompileException, ParseException, MappingException {
		StringBuilder result = new StringBuilder();

		if (n.getNodeName().equals("include")) {
			includeNode(scriptPath, n, n.getParentNode().getOwnerDocument(),
					tenant, deps);
		} else if (n.getNodeName().equals("map")) {
			result.append(printIdent(ident)
					+ "{ // Starting new mappable object context. \n");
			result.append(mapNode(ident + 2, (Element) n, deps, tenant));
			result.append(printIdent(ident) + "} // EOF MapContext \n");
		} else if (n.getNodeName().equals("field")) {
			result.append(fieldNode(ident, (Element) n, className, objectName,
					deps, tenant));
		} else if ((n.getNodeName().equals("param") && !((Element) n)
				.getAttribute("type").equals("array") && !((Element) n)
				.getAttribute("type").equals("array_element"))
				|| n.getNodeName().equals("property")) {
			result.append(propertyNode(ident, (Element) n, true, className,
					objectName));
		}

		else if (n.getNodeName().equals("message")
				|| (n.getNodeName().equals("param") && (((Element) n)
						.getAttribute("type").equals("array") || ((Element) n)
						.getAttribute("type").equals("array_element")))) {
			String methodName = "execute_sub" + (methodCounter++);
			result.append(printIdent(ident) + "if (!kill) { " + methodName
					+ "(access); }\n");

			StringBuilder methodBuffer = new StringBuilder();

			methodBuffer.append(printIdent(ident) + "private final void "
					+ methodName + "(Access access) throws Exception {\n\n");
			ident += 2;
			methodBuffer.append(printIdent(ident) + "if (!kill) {\n");
			methodBuffer.append(messageNode(ident, (Element) n, className,
					objectName, deps, tenant));
			methodBuffer.append(printIdent(ident) + "}\n");
			ident -= 2;
			methodBuffer.append("}\n");

			methodClipboard.add(methodBuffer);
			//
		} else if (n.getNodeName().equals("antimessage")) {

			String messageName = ((Element) n).getAttribute("name");
			String condition = ((Element) n).getAttribute("condition");
			boolean conditionClause = false;

			if (!condition.equals("")) {
				conditionClause = true;
				result.append(printIdent(ident)
						+ "if (Condition.evaluate("
						+ replaceQuotes(condition)
						+ ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,access)) { \n");
				ident += 2;
			}

			result.append(printIdent(ident)
					+ "if ( currentOutMsg != null && currentOutMsg.getMessage(\""
					+ messageName + "\") != null ) {\n");
			result.append(printIdent(ident + 2)
					+ "   currentOutMsg.removeMessage(currentOutMsg.getMessage(\""
					+ messageName + "\"));\n");
			result.append(printIdent(ident) + "} else \n");
			result.append(printIdent(ident)
					+ "if (access.getOutputDoc().getMessage(\"" + messageName
					+ "\") != null) { \n");
			result.append(printIdent(ident + 2)
					+ "access.getOutputDoc().removeMessage(\"" + messageName
					+ "\");\n");
			result.append(printIdent(ident) + "}\n");

			if (conditionClause) {
				ident -= 2;
				result.append(printIdent(ident) + "} // EOF message condition \n");
			}

		} else if (n.getNodeName().equals("methods")) {
			result.append(methodsNode(ident, (Element) n));
		} else if (n.getNodeName().equals("operations")) {
			result.append(operationsNode(ident, (Element) n));
		} else if (n.getNodeName().equals("debug")) {
			result.append(debugNode(ident, (Element) n));
		 } else if (n.getNodeName().equals("log")) {
	            result.append(logNode(ident, (Element) n));
		} else if (n.getNodeName().equals("break")) {
			result.append(breakNode(ident, (Element) n));
		} else if ( n.getNodeName().equals("synchronized")) {
			Element elt = (Element) n;
			StringBuilder methodBuffer = new StringBuilder();

			String context = elt.getAttribute("context");
			String key = elt.getAttribute("key");
			String breakOnNoLock = elt.getAttribute("breakOnNoLock");
			String keyValue = "null";
			if ( key != null && !key.equals("") ) {
			  keyValue=	"(\"\" + Expression.evaluate(\""
						+ key
						+ "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg,null,null,getEvaluationParams()).value)";
			}
			String timeout = elt.getAttribute("timeout");

			boolean user = false;
			boolean service = false;

			if ( context.indexOf("user") != -1 ) {
				user = true;
			}
			if ( context.indexOf("service") != -1 ) {
				service = true;
			}

			String methodName = "execute_sub" + (methodCounter++);
			result.append(printIdent(ident) + "if (!kill) { " + methodName
					+ "(access); }\n");

			methodBuffer.append(printIdent(ident) + "private final void "
					+ methodName + "(Access access) throws Exception {\n\n");
			ident += 2;
			methodBuffer.append(printIdent(ident) + "if (!kill) {\n");
	        ident += 2;

			String lock = "Lock l = getLock(" + ( user ? "access.rpcUser" : null) + "," + ( service ? "access.rpcName" : "null" )+
					"," + keyValue + ");\n";
			methodBuffer.append(printIdent(ident) + lock);

			boolean useTrylock = false;
			if ( "".equals(timeout)) {
				useTrylock = false;
				methodBuffer.append(printIdent(ident) + "l.lock();\n");
				methodBuffer.append(printIdent(ident) + "try {\n");
			} else {
				useTrylock = true;
				methodBuffer.append(printIdent(ident) + "if ( l.tryLock(" + timeout + ", TimeUnit.MILLISECONDS) ) {\n");
				ident += 2;
				methodBuffer.append(printIdent(ident) + "try {\n");

			}
			methodBuffer.append(printIdent(ident + 4) + "acquiredLock(l);\n");
			NodeList children = n.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i) instanceof Element) {
					methodBuffer.append(compile(ident + 4, children.item(i), className, objectName, deps, tenant));
				}
			}

			methodBuffer.append(printIdent(ident) + "} finally {\n");
            methodBuffer.append(printIdent(ident + 2) +"releaseLock(l);\n");
            methodBuffer.append(printIdent(ident) +"}\n");

			if ( useTrylock ) {
			    ident -= 2;
				methodBuffer.append(printIdent(ident) + "} else {");
				if ("true".equals(breakOnNoLock)) {
				    methodBuffer.append(printIdent(ident + 2) + "throw new UserException(-1, \"Failed to aquire lock\");\n");
				    methodBuffer.append(printIdent(ident) + "}\n");
				} else {
				    methodBuffer.append(printIdent(ident) + "Access.writeToConsole(access, \"No lock was obtained! \");\n");
				    methodBuffer.append(printIdent(ident) + "}\n");
				}

			}
			ident -= 2;
			methodBuffer.append(printIdent(ident) +"}\n");
			ident -= 2;
			methodBuffer.append(printIdent(ident) +"}\n");

			methodClipboard.add(methodBuffer);
		}else if (n.getNodeName().equals("block")) {
			result.append(blockNode(ident, (Element) n, className, objectName, deps, tenant));
		}

		return result.toString();
	}

	private final void generateFinalBlock(Document d,
			StringBuilder generatedCode, List<Dependency> deps, String tenant)
			throws Exception {
		generatedCode
				.append("public final void finalBlock(Access access) throws Exception {\n");

		NodeList list = d.getElementsByTagName("finally");

		if (list != null && list.getLength() > 0) {
			NodeList children = list.item(0).getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				String str = compile(0, children.item(i), "", "", deps, tenant);
				generatedCode.append(str);
			}
		}

		generatedCode.append("}\n");

	}

	/**
	 * Check condition/validation rules inside the script.
	 *
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private final void generateRules(Document d, StringBuilder generatedCode)
			throws Exception {

		NodeList list = d.getElementsByTagName("defines");
		for (int i = 0; i < list.getLength(); i++) {
			NodeList rules = list.item(i).getChildNodes();
			for (int j = 0; j < rules.getLength(); j++) {
				if (rules.item(j).getNodeName().equals("define")) {
					String name = ((Element) rules.item(j)).getAttribute("name");
					String expression = rules.item(j).getFirstChild().getNodeValue();
					expression = expression.replace('\r', ' ');
					expression = expression.replace('\n', ' ');
					// Replace any references to another define
					// (Starting with #) by a dynamic lookup
					expression = expression.replaceAll("#([A-Za-z0-9]*)",
							" ( \" + userDefinedRules.get(\"$1\") + \" ) ");
					generatedCode.append("userDefinedRules.put(\"" + name
							+ "\",\"" + expression + "\");\n");
				}
			}
		}
	}

	/**
	 * Check condition/validation rules inside the script.
	 *
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private final void generateValidations(Document d,
			StringBuilder generatedCode) throws Exception {

		boolean hasValidations = false;

		StringBuilder conditionString = new StringBuilder(
				"conditionArray = new String[]{\n");
		StringBuilder ruleString = new StringBuilder(
				"ruleArray = new String[]{\n");
		StringBuilder codeString = new StringBuilder(
				"codeArray = new String[]{\n");
		StringBuilder descriptionString = new StringBuilder(
				"descriptionArray = new String[]{\n");

		NodeList list = d.getElementsByTagName("validations");

		for (int i = 0; i < list.getLength(); i++) {
			NodeList rules = list.item(i).getChildNodes();
			for (int j = 0; j < rules.getLength(); j++) {
				if (rules.item(j).getNodeName().equals("check")) {
					Element rule = (Element) rules.item(j);
					String code = rule.getAttribute("code");
					String description = rule.getAttribute("description");
					String value = rule.getAttribute("value");
					String condition = rule.getAttribute("condition");
					if (value.equals("")) {
						value = rule.getFirstChild().getNodeValue();
					}
					if (code.equals("")) {
						throw new UserException(-1,
								"Validation syntax error: code attribute missing or empty");
					}
					if (value.equals("")) {
						throw new UserException(-1,
								"Validation syntax error: value attribute missing or empty");
					}
					value = value.replaceAll("\r", "");
					// Check if condition evaluates to true, for evaluating
					// validation ;)
					hasValidations = true;
					conditionString.append("\""
							+ condition.replace('\n', ' ').trim() + "\"");
					ruleString.append("\"" + value.replace('\n', ' ').trim()
							+ "\"");
					codeString.append("\"" + code.replace('\n', ' ').trim()
							+ "\"");
					descriptionString.append("\""
							+ description.replace('\n', ' ').trim() + "\"");
					if (j != (rules.getLength() - 2)) { // Add ","
						conditionString.append(",\n");
						ruleString.append(",\n");
						codeString.append(",\n");
						descriptionString.append(",\n");
					}
				}
			}
			if (i < list.getLength() - 1) {
				conditionString.append(",\n");
				ruleString.append(",\n");
				codeString.append(",\n");
				descriptionString.append(",\n");
			}
		}

		conditionString.append("};\n");
		ruleString.append("};\n");
		codeString.append("};\n");
		descriptionString.append("};\n");

		generatedCode.append("public final void setValidations() {\n");
		if (hasValidations) {
			generatedCode.append(conditionString.toString());
			generatedCode.append(ruleString.toString());
			generatedCode.append(codeString.toString());
			generatedCode.append(descriptionString.toString());
		}
		generatedCode.append("}\n\n");

	}

    private void generateSetScriptDebug(String value, StringBuilder generatedCode) {
        generatedCode.append("@Override \n");
        generatedCode.append("public final String getScriptDebugMode() {\n");
        generatedCode.append("    return \"" + value + "\";");
        generatedCode.append("}\n\n");
    }


	private final void compileScript(InputStream is, String packagePath,
			String script, String scriptPath, Writer fo, List<Dependency> deps,
			String tenant, boolean forceTenant) throws SystemException, SkipCompilationException {

		boolean broadcast = false;

		this.scriptPath = scriptPath;

		try {
			Document tslDoc = null;
			StringBuilder result = new StringBuilder();

			tslDoc = XMLDocumentUtils.createDocument(is, false);

			NodeList tsl = tslDoc.getElementsByTagName("tsl");
			// Invesitigate if it's a direct tml script:
			NodeList nodes = tslDoc.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);
				if (n instanceof Element) {
					Element e = (Element) n;
					if (e.getTagName().equals("tml")
							|| e.getTagName().equals("message")) {
						throw new SkipCompilationException(
								"Direct tml needs no compilation");
					}
				}
			}

			if (tsl == null || tsl.getLength() != 1
					|| !(tsl.item(0) instanceof Element)) {
				throw new SkipCompilationException("Ignoring file: "
						+ scriptPath);
			}
			Element tslElt = (Element) tsl.item(0);
			boolean includeOnly = "true".equals(tslElt
					.getAttribute("includeOnly"));
			if (includeOnly) {
				throw new SkipCompilationException("Include only for: "
						+ scriptPath);
			}
			String debugLevel = tslElt.getAttribute("debug");

			String description = tslElt.getAttribute("notes");
			String author = tslElt.getAttribute("author");

			broadcast = (tslElt.getAttribute("broadcast").indexOf("true") != -1);
			String actualPackagePath = packagePath;
			if (packagePath.equals("")) {
				if (Version.osgiActive()) {
					actualPackagePath = "defaultPackage";
				}
			}

			String className = script;
			if (forceTenant) {
				className += "_" + tenant;
			}

			String importDef = (actualPackagePath.equals("") ? "" : "package "
					+ MappingUtils.createPackageName(actualPackagePath)
					+ ";\n\n")
					+ "import com.dexels.navajo.server.*;\n"
					+ "import com.dexels.navajo.mapping.*;\n"
					+ "import com.dexels.navajo.document.*;\n"
					+ "import com.dexels.navajo.parser.*;\n"
					+ "import com.dexels.navajo.script.api.*;\n"
					+ "import com.dexels.navajo.expression.api.*;\n"
					+ "import java.util.ArrayList;\n"
					+ "import java.util.List;\n"
					+ "import java.util.HashMap;\n"
					+ "import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.ExtendDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.InheritDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.JavaDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.NavajoDependency;\n"
					+ "import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;\n"
					+ "import java.util.concurrent.locks.Lock;\n"
					+ "import java.util.concurrent.TimeUnit;\n"
					+ "import java.util.Stack;\n"
					+ "import java.util.Date;\n\n\n";
			result.append(importDef);

			result.append("/**\n");
			result.append(" * Generated Java code by TSL compiler.\n");
			result.append(" * \n");
			result.append(" *\n");
			result.append(" * Java version: "
					+ System.getProperty("java.vm.name") + " ("
					+ System.getProperty("java.runtime.version") + ")\n");
			result.append(" * OS: " + System.getProperty("os.name") + " "
					+ System.getProperty("os.version") + "\n");
			result.append(" *\n");
			result.append(" * WARNING NOTICE: DO NOT EDIT THIS FILE UNLESS YOU ARE COMPLETELY AWARE OF WHAT YOU ARE DOING\n");
			result.append(" *\n");
			result.append(" */\n\n");

			String classDef = "public final class " + className
					+ " extends CompiledScript {\n\n\n";

			result.append(classDef);

			result.append("private volatile ArrayList<Dependency> dependentObjects = null;\n\n");

			// Add constructor.
			String constructorDef = "  public "
					+ className
					+ "() {\n "
					+ "        if ( dependentObjects == null ) {\n"
					+ "             dependentObjects = new ArrayList<Dependency>();\n"
					+ "             setDependencies();\n" + "        }\n"
					+ "  }\n\n";

			result.append(constructorDef);

			// First resolve includes.
			NodeList includes = tslDoc.getElementsByTagName("include");
			Node[] includeArray = new Node[includes.getLength()];
			for (int i = 0; i < includes.getLength(); i++) {
				includeArray[i] = includes.item(i);
			}

			for (int i = 0; i < includeArray.length; i++) {
				includeNode(scriptPath, includeArray[i], tslDoc, tenant, deps);
			}
			generateSetScriptDebug(debugLevel, result);

			// Generate validation code.
			generateValidations(tslDoc, result);

			// Generate final block code.
			generateFinalBlock(tslDoc, result, deps, tenant);

			String methodDef = "public final void execute(Access access) throws Exception { \n\n";
			result.append(methodDef);

			result.append("try {\n");

			result.append("inDoc = access.getInDoc();\n");

			// File Rules HashMap
			generateRules(tslDoc, result);

			NodeList children = tslDoc.getElementsByTagName("tsl").item(0)
					.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {

				String str = compile(0, children.item(i), "", "", deps, tenant);
				result.append(str);
			}

			result.append("} finally {\n");

			if (broadcast) {
				result.append("try { \n");
				result.append("   TribeManagerFactory.getInstance().broadcast(inDoc, access.getTenant());\n");
				result.append("} catch (Exception e) { \n");
				result.append("   e.printStackTrace(System.err);\n");
				result.append("}\n");
			}
			result.append("}\n");

			result.append("}// EOM\n");

			// Add generated methods.
			for (int i = 0; i < methodClipboard.size(); i++) {
				result.append(methodClipboard.get(i).toString() + "\n\n");
			}

			// Add generated variables.
			for (int i = 0; i < variableClipboard.size(); i++) {
				result.append(variableClipboard.get(i));
			}

			// Add public void setDependencies
			if (!dependencies.toString().equals("")) {
				result.append("public void setDependencies() {\n");
				result.append(dependencies.toString());
				result.append("}\n\n");
				result.append("public ArrayList<Dependency> getDependentObjects() {\n");
				result.append("   return dependentObjects;\n");
				result.append("}\n\n");
			}

			// Add getDescription() and getAuthor()
			if (author != null) {
			    String flatAuthor = description.replace("\n", "").replace("\r", "");
				result.append("public String getAuthor() {\n");
				result.append("   return \"" + flatAuthor + "\";\n");
				result.append("}\n\n");
			}

			if (description != null) {
			    String flatDescription = description.replace("\n", "").replace("\r", "");
				result.append("public String getDescription() {\n");
				result.append("   return \"" + flatDescription + "\";\n");
				result.append("}\n\n");
			}

			result.append("public String getScriptType() {\n");
			result.append("   return \"" + scriptType + "\";\n");
			result.append("}\n\n");

			result.append("}//EOF");

			fo.write(result.toString());
			fo.close();
		} catch (SkipCompilationException e) {
			throw (e);
		} catch (Exception e) {
			throw new SystemException(-1,
					"Error while generating Java code for script: " + script + ": " + e.getMessage(), e);
		}
	}

	public void compileScript(String script, String scriptPath,
			String workingPath, String packagePath, Writer outputWriter,
			List<Dependency> deps, String tenant,
			boolean hasTenantSpecificScript, boolean forceTenant) throws SystemException,
			SkipCompilationException {

		final String extension = ".xml";
		String fullScriptPath = scriptPath + "/" + packagePath + "/" + script +  extension;
		String ns3ScriptPath =  scriptPath + "/" + packagePath + "/" + script + ".ns";

		List<String> inheritedScripts = new ArrayList<>();
		List<String> extendEntities = new ArrayList<>();
		InputStream is = null;
		boolean isNavascript = false;

		try {

			
			if (new File(ns3ScriptPath).exists() ) {
				NS3ToNSXML ns3toxml = new NS3ToNSXML();
				ns3toxml.initialize();
				scriptType = "navascript";
				String content = ns3toxml.read(ns3ScriptPath);
				InputStream metais = ns3toxml.parseNavascript(content);
				MapMetaData mmd = MapMetaData.getInstance();
				String intermed = mmd.parse(fullScriptPath, metais);
				metais.close();
				is = new ByteArrayInputStream(intermed.getBytes());
				isNavascript = true;
			} else if (MapMetaData.isMetaScript(fullScriptPath)) { // Check for metascript.
				scriptType = "navascript";
				MapMetaData mmd = MapMetaData.getInstance();
				InputStream metais = navajoIOConfig.getScript(packagePath + "/"
						+ script, tenant,extension);

				String intermed = mmd.parse(fullScriptPath, metais);
				metais.close();
				is = new ByteArrayInputStream(intermed.getBytes());
			} else {
				is = navajoIOConfig.getScript(packagePath + "/" + script,
						tenant,extension);
			}
			
			if ( !isNavascript ) { // NS3 does NOT support inheritance at this moment.
				InputStream sis = navajoIOConfig.getScript(packagePath + "/" + script, tenant,extension);
				logger.debug("Getting script: {}/{}", packagePath, script);
				if (ScriptInheritance.containsInject(sis)) {
					// Inheritance preprocessor before compiling.
					InputStream ais = null;
					ais = ScriptInheritance.inherit(is, scriptPath,
							inheritedScripts);
					is.close();
					is = ais;
				}
				sis.close();
			}

			for (int i = 0; i < inheritedScripts.size(); i++) {
			    File inheritedFile = new File(fetchScriptFileName(scriptPath + "/" + inheritedScripts .get(i)));
				addDependency(
						"dependentObjects.add( new InheritDependency( Long.valueOf(\""
								+ IncludeDependency.getFileTimeStamp(inheritedFile) + "\"), \""
								+ inheritedScripts.get(i) + "\"));\n",
						"INHERIT" + inheritedScripts.get(i));
			}
			
			compileScript(is, packagePath, script, scriptPath, outputWriter,
					deps, tenant, forceTenant);

		} catch (SkipCompilationException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(-1,
					"Error while generating Java code for script: " + script, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private String compileToJava(String script, String input, String output,
			String packagePath, ClassLoader classLoader,
			NavajoIOConfig navajoIOConfig, List<Dependency> deps,
			String tenant, boolean hasTenantSpecificScript) throws Exception {
		return compileToJava(script, input, output, packagePath, packagePath,
				classLoader, navajoIOConfig, deps, tenant,
				hasTenantSpecificScript, false);
	}

	/**
	 * Only used by OSGi now
	 *
	 * @param script
	 * @param input
	 * @param output
	 * @param packagePath
	 *            The package in the Java file. OSGi doesn't handle default
	 *            packages well, so default package will be translated to
	 *            'defaultPackage'
	 * @param scriptPackagePath
	 *            the path of the scriptFile from the scriptRoot
	 * @param classLoader
	 * @param navajoIOConfig
	 * @param forceTenant TODO
	 * @return
	 * @throws Exception
	 */
	public String compileToJava(String script, String input, String output,
			String packagePath, String scriptPackagePath,
			ClassLoader classLoader, NavajoIOConfig navajoIOConfig,
			List<Dependency> deps, String tenant,
			boolean hasTenantSpecificScript, boolean forceTenant) throws CompilationException,SkipCompilationException {
		String tenantScript = script;
		if (forceTenant) {
			tenantScript = script + "_" + tenant;
		}
		String javaFile = output + "/" + tenantScript + ".java";

		TslCompiler tslCompiler = new TslCompiler(classLoader, navajoIOConfig);
		try {
			String bareScript;

			if (script.indexOf('/') >= 0) {
				bareScript = script.substring(script.lastIndexOf('/') + 1,
						script.length());
			} else {
				bareScript = script;
			}

			tslCompiler.compileScript(bareScript, input, output,
					scriptPackagePath, navajoIOConfig.getOutputWriter(output,
							packagePath, tenantScript, ".java"), deps, tenant,
					hasTenantSpecificScript, forceTenant);

			return javaFile;
		} catch (SkipCompilationException ex) {
			throw new SkipCompilationException("Skip compilation exception, script: "+script+" does not need compiling");
		} catch (Throwable ex) {
			logger.error("Error compiling script: " + script, ex);
			// Isn't this what 'finally' is for?
			File f = new File(javaFile);
			if (f.exists()) {
				f.delete();
			}
			if (ex instanceof Exception) {
				throw new CompilationException("Other compilation exception:", ex);
			}
			return null;
		}
	}

}
