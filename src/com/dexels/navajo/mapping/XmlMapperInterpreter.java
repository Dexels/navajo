package com.dexels.navajo.mapping;

/**
 * $Id$
 *
 */

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.*;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.xml.*;

import org.xml.sax.*;
import org.w3c.dom.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import utils.FileUtils;

public class XmlMapperInterpreter {
    private String tmlPath = "";
    private String fileName = "";
    private Navajo tmlDoc = null;  // Input document
    private FileUtils fu = null;
    private Navajo outputDoc = null;  // Output document
    private Parameters parameters = null;
    private Access access = null;   // Caller data
    private Document tsldoc;
    private TslNode rootNode;
    private TslNode currentNode;
    private static int requestCount = 0;
    private static double totaltiming = 0.0;
    private NavajoConfig config = null;
    private boolean oldStyleScripts = false;

    // Private error methods.
    private static final String ERROR_PREFIX = "NAVAJO SCRIPT ERROR: ";

    private static Logger logger = Logger.getLogger( XmlMapperInterpreter.class );

    private String errorFieldNotFound(String field, Object o) {
        return   "Field (" + field + ") not found in mappable class ("
                + o.getClass().getName() + ")";
    }

    private String errorMethodNotFound(String method, Object o) {
        return   "Method (" + method + ") not found in mappable class("
                + o.getClass().getName() + ")";
    }

    private String errorIllegalTag(String tag) {
        return   "Did not expect this tag: " + tag;
    }

    private String errorCouldNotFindClass(String cls) {
        return   "Mappable class not found: " + cls;
    }

    private String errorCallingLoadMethod(String msg) {
        return   "While calling load() on mappable object: " + msg;
    }

    private String errorCallingStoreMethod(String msg) {
        return   "While calling store() on mappable object: " + msg;
    }

    private String errorExpression(String msg, String expr) {
        return   "Expression evaluation error. Expr (" + expr + "), message ("
                + msg + ")";
    }

    private String generalError(String msg) {
        return   msg;
    }

    private String errorEmptyAttribute(String attribute, String tag) {
        return   "Empty '" + attribute + "' attribute in " + tag + " tag";
    }

    private String errorIllegalSubMap(String tag, String msg) {
        return   tag + " tags cannot be sumapped: " + msg;
    }

    private String errorTooManyMsgInstances(String type) {
        return   "Too many message instances for this field type: " + type;
    }

    /**
     * Constructor. Sets the path for
     * 1. The script.
     * 2. The TML basis output document.
     * 3. The TML templates.
     * Passing the four basic Navajo objects (which can be used in Mappable objects).
     * 1. Navajo input document (received from client)
     * 2. Parameters object (constructed from Database using client TML input)
     * 3. Context object (the current naming context for calling EJBs)
     * 4. Access object (containing e.g. rpc-name, rpc-user, etc., constructed from TML client header)
     */
    public XmlMapperInterpreter(String name, Navajo doc, Parameters parms, NavajoConfig config, Access acs)
            throws org.xml.sax.SAXException, IOException {

        this.config = config;
        tmlPath = config.getScriptPath();
        fileName = name;
        tmlDoc = doc;
        parameters = parms;
        access = acs;

        oldStyleScripts = config.getScriptVersion().equals("1.0");

        // open the script file and save in fu
        logger.log(Priority.DEBUG,"in XMlMapperInterpreter(), XMLfile:" + tmlPath + "/" + fileName + ".xsl :");
        try {
            FileInputStream input = null;

            if (access.betaUser) {
                try {
                    input = new FileInputStream(new File(tmlPath + "/" + fileName + ".xsl_beta"));
                } catch (FileNotFoundException fnfe) {// //System.out.println("Could not find beta version, using normal version...");
                }
            }
            if (input == null)
                input = new FileInputStream(new File(tmlPath + "/" + fileName + ".xsl"));
            tsldoc = XMLDocumentUtils.createDocument(input, false);
        } catch (NavajoException tbe) {
            logger.log(Priority.DEBUG,"error in XmlMapperInterpreter, xml script is not correct");
        }
    }

    private String getFieldType(MappableTreeNode o, String field) throws MappingException {
        try {
            String type = o.myObject.getClass().getField(field).getType().getName();
            if (type.startsWith("[L")) { // We have an array determine member type.
                type = type.substring(2, type.length() - 1);
            }
            return type;
        } catch (java.lang.NoSuchFieldException nsfe) {
            throw new MappingException(errorFieldNotFound(field, o.myObject));
        }
    }

    private Mappable getMappable(String object, String name) throws MappingException {
        String error = "";

        Mappable o = null;

        try {
            Class c = null;
            c = config.getClassloader().getClass(object);
            o = (Mappable) c.newInstance();
        } catch (java.lang.ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            throw new MappingException(errorCouldNotFindClass(object));
        } catch (java.lang.ClassCastException cce) {
            cce.printStackTrace();
            throw new MappingException("Not a mappable object: " + name + " (" + object + ")");
        } catch (java.lang.IllegalAccessException iae) {
            iae.printStackTrace();
            throw new MappingException("Illegal access for object: " + name + ", message: " + iae.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            error = e.getMessage();
        }
        if (o == null)
            throw new MappingException(error);
        return o;
    }

    private void callLoadMethod(Object o) throws MappableException, MappingException, UserException {
        if (o == null)
            return;
        if (o instanceof Mappable) {
            ((Mappable) o).load(parameters, tmlDoc, access, config);
        } else {
            try {
                Class c = o.getClass();
                Class[] parms = {parameters.getClass(), tmlDoc.getClass(), access.getClass(), config.getClass()};
                java.lang.reflect.Method m = c.getMethod("load", parms);
                Object[] arguments = {parameters, tmlDoc, access, config};
                if (m != null)
                  m.invoke(o, arguments);
                else
                  throw new MappingException(
                            errorCallingLoadMethod("load() does not exist for this object: " + o.getClass().getName()));
            } catch (Exception e) {
                throw new MappingException(errorCallingLoadMethod(e.getMessage()));
            }
        }
    }

    private void callStoreMethod(Object o)throws MappableException, MappingException, UserException {
        if (o == null)
            return;
        if (o instanceof Mappable) {
            ((Mappable) o).store();
        } else {
            try {
                Class c = o.getClass();
                java.lang.reflect.Method m = c.getMethod("store", null);
                if (m != null)
                  m.invoke(o, null);
                else
                  throw new MappingException(
                            errorCallingLoadMethod("store() does not exist for this object: " + o.getClass().getName()));
            } catch (Exception e) {
                throw new MappingException(errorCallingLoadMethod(e.getMessage()));
            }
        }
    }

    private void callKillMethod(Object o, int id)throws MappableException, MappingException, UserException {

        if (o == null)
            return;
        if (o instanceof Mappable) {
            ((Mappable) o).kill();
        } else {
            try {
                Class c = o.getClass();
                java.lang.reflect.Method m = c.getMethod("kill", null);
                if (m != null)
                  m.invoke(o, null);
                else
                  throw new MappingException(
                            errorCallingLoadMethod("kill() does not exist for this object: " + o.getClass().getName()));
            } catch (Exception e) {
                throw new MappingException(errorCallingLoadMethod(e.getMessage()));
            }
        }
    }

    private boolean isArrayAttribute(MappableTreeNode o, String field) throws MappingException {
        try {
            String objectType = o.myObject.getClass().getField(field).getType().getName();
            return objectType.startsWith("[L");
        } catch (NoSuchFieldException nsfe) {
            throw new MappingException(errorFieldNotFound(field, o.myObject));
        }
    }

    private ArrayList getObjectList(MappableTreeNode o, String field, String filter, Navajo doc,
                                    Message parent)
            throws com.dexels.navajo.server.UserException, MappingException, SystemException {
        ArrayList result = new ArrayList();

        try {
            String objectType = "";

            if (!field.equals("")) {
                try {
                    objectType = o.myObject.getClass().getField(field).getType().getName();
                } catch (NoSuchFieldException nsfe) {
                    throw new MappingException(errorFieldNotFound(field, o.myObject));
                }
                if (objectType.startsWith("[L")) { // Array
                    Object[] dum = (Object[]) getAttributeObject(o, field, null);

                    if (dum == null)  // If no instances assigned!
                        return result;
                    for (int i = 0; i < dum.length; i++) {
                        MappableTreeNode mapTreeNode = new MappableTreeNode(o, dum[i]);
                        result.add(mapTreeNode);
                    }

                } else if (!objectType.equals("int")
                        && !objectType.equals("long")
                        && !objectType.endsWith("java.lang.String")
                        && !objectType.equals("char")
                        && !objectType.equals("float")
                        && !objectType.equals("double")
                        && !objectType.equals("long")
                        && !objectType.equals("boolean")
                        && !objectType.equals("java.util.Date")) {

                    Mappable sub = (Mappable) getAttributeObject(o, field, null);
                    MappableTreeNode mapTreeNode = new MappableTreeNode(o, sub);
                    if (sub != null) // Only add non-null objects.
                        result.add(mapTreeNode);
                }
            } else {
                result.add(o);
            }
            if (!filter.equals("")) {
                ArrayList dummy = new ArrayList();
                for (int i = 0; i < result.size(); i++) {
                    MappableTreeNode op = (MappableTreeNode) result.get(i);
                    boolean match = Condition.evaluate(filter, doc, op, parent);
                    if (match) {
                        MappableTreeNode mapTreeNode = new MappableTreeNode(o, op);
                        dummy.add(mapTreeNode);
                    }
                }
                result = dummy;
            }
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
        }
        return result;
    }

    private boolean isSelection(Message msg, Navajo doc, String msgName) {

        Message ref = null;
        Property prop = null;

        if (msgName.startsWith(Navajo.MESSAGE_SEPARATOR)) { // Absolute reference!
            msg = null;
            msgName = msgName.substring(1, msgName.length());
        }
        if (msg != null)
            prop = msg.getProperty(msgName);
        else
            prop = doc.getProperty(msgName);

        if (prop == null)
            return false;
        if (prop.getType().equals(Property.SELECTION_PROPERTY))
            return true;
        else
            return false;
    }

    private ArrayList getSelectedItems(Message msg, Navajo doc, String msgName)
            throws NavajoException {
        Message ref = null;
        Property prop = null;
        ArrayList result = new ArrayList();

        if (msg != null)
            prop = msg.getProperty(msgName);
        else
            prop = doc.getProperty(msgName);
        if (!prop.getType().equals(Property.SELECTION_PROPERTY))
            throw NavajoFactory.getInstance().createNavajoException("Selection Property expected");
        result = prop.getAllSelectedSelections();

        return result;
    }

    private ArrayList getMessageList(Message msg, Navajo doc, String str, String filter, MappableTreeNode o)
            throws NavajoException, SystemException, MappingException {
        try {
            ArrayList result = new ArrayList();

            if (str.equals("")) {
                result.add(NavajoFactory.getInstance().createMessage(doc, "__JUST_FOR_ITERATING_ONCE__"));
                return result;
            } else {
                if (str.startsWith(Navajo.MESSAGE_SEPARATOR)) {
                    msg = null;
                    str = str.substring(1, str.length());
                }
                if (msg != null)
                    result = msg.getMessages(str);
                else
                    result = doc.getMessages(str);
                // If filter is defined use it to filter out the proper messages. Filter contains an expression that uses
                // the matched message as offset (parent) message.
                if (!filter.equals("")) {
                    ArrayList dummy = new ArrayList();
                    for (int i = 0; i < result.size(); i++) {
                        Message parent = (Message) result.get(i);
                        boolean match = Condition.evaluate(filter, doc, o, parent);
                        if (match) {
                            dummy.add(parent);
                        }
                    }
                    result = dummy;
                }
                return result;
            }
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
            throw new MappingException(tmle.getMessage() + showNodeInfo(currentNode));
        }
    }

    private Object evaluateExpression(String expression) throws SystemException {
        Operand op = null;

        try {
            op = Expression.evaluate(expression, tmlDoc);
        } catch (com.dexels.navajo.parser.TMLExpressionException te) {
            logger.log(Priority.DEBUG,te.getMessage(),te);
        }
        return op.value;
    }

    /**
     * createMapping() actually executes the parsed MAP tree (starting from root).
     */
    private void createMapping(TslNode root, Message msg, MappableTreeNode currentObject, Message outMessage,
                                Message parmMessage, boolean loadObject, boolean emptyMap)
            throws Exception, BreakEvent {

        String condition = root.getAttribute("condition");

        boolean eval = false;

        try {
            eval = Condition.evaluate(root.getAttribute("condition"), tmlDoc, currentObject, msg);
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
            throw new MappingException(errorExpression(tmle.getMessage(), root.getAttribute("condition")));
        }

        if (!eval) // Condition to execute simple map failed.
            return;
        // MapObject root
        ArrayList repetitions = null;

        // First, call load on object.
        if ((currentObject != null) && (currentObject.myObject != null) && loadObject) {
            access.setCurrentOutMessage(outMessage);
            callLoadMethod(currentObject.myObject);
        }
        try {
            // If emptyMap is true the construct is of the form <message><map ref=""></message> without a parent map preceding the message tag.
            int countNr = (emptyMap) ? 1 : root.getNodesSize();

            for (int i = 0; i < countNr; i++) {
                TslNode map = (emptyMap) ? root : root.getNode(i); // deze kan zijn: field(toNavajo) of property(!toNavajo)

                currentNode = map;
                if (map.getTagName().equals("break"))
                    processBreak(map, currentObject, msg);
                TslNode submap = map.getNodeByType("map");
                String ref = "";
                String maptype = "";
                String filter = "";

                if (submap != null) {
                    ref = submap.getAttribute("ref"); // We have a submapping with a ref attribute.
                    // Default behavior: <message><map/></message> is object to tml (type="object").
                    // If type="tml" is specified behavior is tml input to tml output.
                    maptype = submap.getAttribute("type");
                    filter = submap.getAttribute("filter");
                }

                if (map.getTagName().equals("map")) { // Encountered a submap with new object.
                    doMapping(outputDoc, map, msg, outMessage, parmMessage, currentObject);
                } else
                if ((map.getTagName().equals("message")
                        || map.getTagName().equals("paramessage"))
                        && ((submap != null && ref.equals(""))
                        || (submap == null))
                        ) {  // Add message with new object instance submap.
                    String name = map.getAttribute("name");
                    interpretAddBody(outMessage, outputDoc, map, currentObject, msg, parmMessage,
                                     map.getTagName().equals("paramessage"));
                } else {
                    if (submap != null) {  // We have a submapping in map.

                        // Determine number of times that the submapping needs to be executed:
                        // 1. For Object to TML the number of object instances is used.
                        // 2. For TML to Object:
                        // a) For non-selection properties, the number of matched messages is used.
                        // b) For selection properties, the number of selected options is used.
                        boolean isSelectionRef = false;
                        boolean isArrayAttribute = false;

                        if (map.getTagName().equals("message")
                                || map.getTagName().equals("paramessage")) {
                            eval = false;
                            try {
                                eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, currentObject, msg);
                            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                                tmle.printStackTrace();
                                throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
                            }
                            if (eval) {
                                if (maptype.equals("tml")) {
                                    if (!isSelection(msg, tmlDoc, submap.getAttribute("ref"))) {
                                        repetitions = getMessageList(msg, tmlDoc, submap.getAttribute("ref"),
                                                                     filter, currentObject);
                                    } else {
                                        isSelectionRef = true;
                                        // What if we have repeated selected items of a selection property?
                                        repetitions = getSelectedItems(msg, tmlDoc, submap.getAttribute("ref"));
                                    }
                                } else {
                                    // Check for lazyiness.
                                    boolean isLazy = map.getAttribute("mode").equals(Message.MSG_MODE_LAZY);
                                    LazyMessage lm = access.getLazyMessages();
                                    String fullMsgName = "/" + ((msg != null) ? (msg.getFullMessageName()+"/") : "") +
                                                         map.getAttribute("name");
                                    if (isLazy && lm.isLazy(fullMsgName)) {
                                        LazyArray la = (LazyArray) currentObject.myObject;
                                        la.setEndIndex(submap.getAttribute("ref"), lm.getEndIndex(fullMsgName));
                                        la.setStartIndex(submap.getAttribute("ref"), lm.getStartIndex(fullMsgName));
                                    }
                                    repetitions = getObjectList(currentObject, submap.getAttribute("ref"), filter, tmlDoc,
                                                                msg);
                                    isArrayAttribute = this.isArrayAttribute(currentObject, submap.getAttribute("ref"));
                                }
                            } else
                                repetitions = new ArrayList();
                        } else
                        if (map.getTagName().equals("property")) {// it's object-to-tml

                            eval = false;
                            try {
                                eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, currentObject, msg);
                            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                                tmle.printStackTrace();
                                throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
                            }
                            if (eval)
                                repetitions = getObjectList(currentObject, submap.getAttribute("ref"), filter, tmlDoc, msg);
                            else
                                repetitions = new ArrayList();
                        } else {// it's tml-to-object
                            eval = false;
                            try {
                                eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, currentObject, msg);
                            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                                tmle.printStackTrace();
                                throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
                            }
                            if (eval) {
                                if (!isSelection(msg, tmlDoc, submap.getAttribute("ref"))) {
                                   repetitions = getMessageList(msg, tmlDoc, submap.getAttribute("ref"),
                                                                filter, currentObject);
                                } else {
                                    isSelectionRef = true;
                                    // What if we have repeated selected items of a selection property?
                                    repetitions = getSelectedItems(msg, tmlDoc, submap.getAttribute("ref"));
                                }
                            } else {
                                repetitions = new ArrayList();
                            }
                        }
                        // subObject ArrayList is used as temporary storage for newly created object instances
                        // as a result of the mapping.
                        // ArrayList subObjects = new ArrayList();
                        Object[] subObjects = null;
                        String messageName = "";
                        int repeat = repetitions.size();

                        Message arrayMessage = null;

                        boolean isLazy = map.getAttribute("mode").equals(Message.MSG_MODE_LAZY);

                        // For TML to object mappings with multiple messages, expand the messsageName with a counter.
                        messageName = map.getAttribute("name");

                        // Check condition.
                        boolean msgEval = false;
                        try {
                          msgEval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, currentObject, msg);
                        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                          tmle.printStackTrace();
                          throw new MappingException(errorExpression(tmle.getMessage(), root.getAttribute("condition")));
                        }
                        if (msgEval) {

                          if (!oldStyleScripts) {
                              if (map.getTagName().equals("paramessage"))
                                  arrayMessage = getMessageObject(messageName, parmMessage, true, tmlDoc,
                                                                  (isArrayAttribute));
                              else if (map.getTagName().equals("message")) {
                                 arrayMessage = getMessageObject(messageName, outMessage, true,
                                                                 outputDoc, (isArrayAttribute));
                                  if (isLazy) {
                                            LazyArray la = (LazyArray) currentObject.myObject;
                                            String fieldName = submap.getAttribute("ref");
                                            arrayMessage.setMode(Message.MSG_MODE_LAZY);
                                            arrayMessage.setLazyTotal(la.getTotalElements(fieldName));
                                            arrayMessage.setLazyRemaining(la.getRemainingElements(fieldName));
                                            arrayMessage.setArraySize(la.getCurrentElements(fieldName));
                                  }
                              }

                          }

                          String baseMessageName = messageName;

                          for (int j = 0; j < repeat; j++) {
                              MappableTreeNode expandedObject = null;
                              Message expandedMessage = null;
                              Selection expandedSelection = null;
                              Point expandedPoint = null;

                              // For old style scripts use name-with-index appending for sub-messages
                              if (oldStyleScripts)
                                messageName = baseMessageName + j;

                              // TODO: WE CAN ONLY ENCOUNTER SELECTION PROPERTIES AT THIS POINT!!!!
                              if (map.getTagName().equals("paramessage")) {
                                  if (map.getAttribute("name").equals(""))
                                      throw new MappingException(errorEmptyAttribute("name", "message"));
                                  else {
                                      if (oldStyleScripts) {
                                         expandedMessage = getMessageObject(messageName, parmMessage, true,
                                                                           tmlDoc, false);
                                      } else {
                                        expandedMessage = getMessageObject(messageName, arrayMessage, true,
                                                                           tmlDoc, false);
                                      }
                                  }

                                  if (maptype.equals("tml")) {
                                      if (!isSelectionRef) { // Get message from list.
                                          createMapping(submap, (Message) repetitions.get(j), currentObject, outMessage,
                                                        expandedMessage, true, false);
                                      } else {  // or, get selection option from list.
                                          expandedSelection = (Selection) repetitions.get(j);
                                          createSelection(submap, currentObject, expandedSelection, expandedMessage, outMessage, parmMessage);
                                      }
                                  } else {
                                      // Get Mappable object from the current instance list.
                                      expandedObject = (MappableTreeNode) repetitions.get(j);
                                      createMapping(submap, msg, expandedObject, outMessage, expandedMessage,
                                                    true, false);
                                  }
                              } else
                              if (map.getTagName().equals("message")) {
                                  if (map.getAttribute("name").equals(""))
                                      throw new MappingException(errorEmptyAttribute("name", "message"));
                                  else {
                                      if (oldStyleScripts) {
                                         expandedMessage = getMessageObject(messageName, outMessage, true,
                                                                            outputDoc, false);
                                      } else {
                                        expandedMessage = getMessageObject(messageName, arrayMessage, true,
                                                                           outputDoc, false);
                                      }
                                  }

                                  if (maptype.equals("tml")) {
                                      if (!isSelectionRef) { // Get message from list.
                                          createMapping(submap, (Message) repetitions.get(j), currentObject, expandedMessage, parmMessage, true, false);
                                      } else {  // or, get selection option from list.
                                          expandedSelection = (Selection) repetitions.get(j);
                                          createSelection(submap, currentObject, expandedSelection, expandedMessage, outMessage, parmMessage);
                                      }
                                  } else {
                                      // Get Mappable object from the current instance list.
                                      expandedObject = (MappableTreeNode) repetitions.get(j);
                                      createMapping(submap, msg, expandedObject, expandedMessage, parmMessage, true, false);
                                  }
                              } else
                              if (map.getTagName().equals("property")) { // Map Mappable Object to TML
                                  // Get Mappable object from the current instance list.
                                  expandedObject = (MappableTreeNode) repetitions.get(j);
                                  if (map.getAttribute("name").equals(""))
                                      throw new MappingException(errorEmptyAttribute("name", "property"));
                                  if (map.getAttribute("type").equals("selection")) {
                                      // get a Selection property.
                                      expandedSelection = getSelectionObject(outMessage, map);
                                      // call createSelection() to handle special case of selection submapping.
                                      createSelection(submap, expandedObject, expandedSelection, expandedMessage, outMessage, parmMessage);
                                  } else if (map.getAttribute("type").equals("points")) {
                                      expandedPoint = getPointsObject(outMessage, map);
                                      createPoint(submap, expandedObject, expandedPoint);
                                  } else {
                                      throw new MappingException(errorIllegalSubMap(map.getAttribute("type"), " Only selection pr point types can be submapped"));
                                  }
                              } else if (map.getTagName().equals("field")) {  // Map TML message to Mappable Object
                                  // Create a new instance of Mappable object.
                                  String type = getFieldType(currentObject, map.getAttribute("name"));

                                  Object  mapObject = getMappable(type, map.getAttribute("name"));
                                  expandedObject = new MappableTreeNode(currentObject, mapObject);
                                  if (!isSelectionRef) {// Get message from list.
                                      expandedMessage = (Message) repetitions.get(j);
                                      // Recursively call createMapping() to execute submapping on expandedMessage and expandedObject
                                      createMapping(submap, expandedMessage, expandedObject, outMessage, parmMessage, true, false);
                                  } else {  // or, get selection option from list.
                                      expandedSelection = (Selection) repetitions.get(j);
                                      // call createSelection() to handle special case of selection submapping.
                                      createSelection(submap, expandedObject, expandedSelection, expandedMessage, outMessage, parmMessage);
                                  }
                                  // Add newly created object instance to subObject list.
                                  if (subObjects == null)
                                      subObjects = new Object[repetitions.size()];
                                  subObjects[j] = mapObject;
                              } else {
                                  throw new MappingException(errorIllegalTag(map.getTagName()));
                              }
                          }

                          // We will have to adapt the parent object with the newly made additions in
                          // case of new object instances.
                          if ((repetitions.size() > 0)
                                  && map.getTagName().equals("field")) { // Is there anything mapped to an object?
                              String type = "";

                              try {
                                 type = currentObject.myObject.getClass().getField(map.getAttribute("name")).getType().getName();
                              } catch (NoSuchFieldException nsfe) {
                                  throw new MappingException(errorFieldNotFound(map.getAttribute("name"), currentObject.myObject));
                              }
                              if (type.startsWith("[L")) { // Array
                                  setAttribute(currentObject, map.getAttribute("name"), subObjects);
                              } else {
                                  if (repetitions.size() > 1)
                                      throw new MappingException(errorTooManyMsgInstances(type));
                                  setAttribute(currentObject, map.getAttribute("name"), subObjects[0]);
                              }
                          }
                      } // if (msgEval)
                    } else { // We have a simple mapping
                        executeSimpleMap(currentObject, msg, map, outMessage, parmMessage);
                    }
                }
            }
            // Finally, call store method for object.
            if (currentObject != null && (currentObject.myObject != null) && loadObject)
                callStoreMethod(currentObject.myObject);
        } catch (BreakEvent be) {
            if (loadObject)
                callStoreMethod(currentObject.myObject);
            throw be;
        } catch (Exception e) {
            if (loadObject)
                callKillMethod(currentObject.myObject, 1);
            throw e;
        }
    }

    /**
     * Method for creating "empty" points object, to be used to store cartesian coordinates.
     * Point object represents <value> tag. Vector elements of a coordinate are stored as x0...xN attributes of the <value> tag.
     */
    private Point getPointsObject(Message parent, TslNode map) throws NavajoException, MappingException {
        Message ref = null;
        String propertyName = map.getAttribute("name");
        String direction = map.getAttribute("direction");
        String description = map.getAttribute("description");

        ref = getMessageObject(propertyName, parent, false, outputDoc, false);
        if (ref == null)
            ref = parent;
        if (ref == null)
            throw new MappingException("Could not find/create points property: " + propertyName);
        String realProperty = propertyName.substring(propertyName.lastIndexOf(Navajo.MESSAGE_SEPARATOR) + 1, propertyName.length());

        if (propertyName.equals(""))
            return null;
        Property prop = ref.getProperty(realProperty);

        if (prop == null) {
            prop =  NavajoFactory.getInstance().createProperty(outputDoc, realProperty, Property.POINTS_PROPERTY, "", 0, "", Property.DIR_OUT);
            ref.addProperty(prop);
        }
        prop.setDirection(direction);
        prop.setDescription(description);
        if (!prop.getType().equals(Property.POINTS_PROPERTY))
            throw new MappingException("Not a points property: " + propertyName);
        Point point = NavajoFactory.getInstance().createPoint(prop);

        return point;
    }

    /**
     * Method for creating "empty" selection object.
     */
    private Selection getSelectionObject(Message parent, TslNode map)
            throws NavajoException, MappingException {
        Message ref = null;
        String cardinality = "";
        String propertyName = map.getAttribute("name");

        cardinality = map.getAttribute("cardinality");
        if (cardinality.equals(""))
            cardinality = "1";
        String direction = map.getAttribute("direction");
        String description = map.getAttribute("description");

        ref = getMessageObject(propertyName, parent, false, outputDoc, false);
        if (ref == null)
            ref = parent;
        if (ref == null)
            throw new MappingException("Could not find/create selection property: " + propertyName);
        String realProperty = propertyName.substring(propertyName.lastIndexOf(Navajo.MESSAGE_SEPARATOR) + 1, propertyName.length());

        if (propertyName.equals(""))
            return null;
        Property prop = ref.getProperty(realProperty);

        if (prop == null) {
            prop = NavajoFactory.getInstance().createProperty(outputDoc, realProperty, cardinality, "Selection", Property.DIR_IN);
            ref.addProperty(prop);
        }

        prop.setCardinality(cardinality);
        prop.setDirection(direction);
        prop.setDescription(description);
        if (!prop.getType().equals(Property.SELECTION_PROPERTY))
            throw new MappingException("Not a selection property: " + propertyName);
        Selection sel = NavajoFactory.getInstance().createSelection(outputDoc, "UNKNOWN", "UNKOWN", false);

        prop.addSelection(sel);
        return sel;
    }

    public static Message getMessageObject(String name, Message parent, boolean messageOnly,
                                           Navajo source, boolean array)
            throws NavajoException {
        Message msg = parent;

        if (name.startsWith(Navajo.MESSAGE_SEPARATOR)) {// We have an absolute message reference.
            msg = null;
            name = name.substring(1, name.length());
        }
        StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
        // Create and/or find all required messages.
        int count = tok.countTokens();
        Message newMsg = null;

        if (!messageOnly) {
            count = count - 1;
            newMsg = msg;
        }
        for (int i = 0; i < count; i++) {
            String messageName = tok.nextToken();
            while (messageName.equals(Navajo.PARENT_MESSAGE)) {
                messageName = tok.nextToken();
                msg = msg.getParentMessage();
                i++;
            }
            if (i < count) {
              if (msg == null) {
                  newMsg = source.getMessage(messageName);
              }
              else {
                  if (!msg.getType().equals(Message.MSG_TYPE_ARRAY))  // For array type messages always add element message!!!
                      newMsg = msg.getMessage(messageName);
              }
              if (newMsg == null) {
                  newMsg = NavajoFactory.getInstance().createMessage(source, messageName,
                                                (array ? Message.MSG_TYPE_ARRAY : ""));
                  if (msg == null)
                      source.addMessage(newMsg);
                  else
                      msg.addMessage(newMsg);
              }
              msg = newMsg;
            } else {
              newMsg = msg;
            }
        }

        if (array)
          newMsg.setType(Message.MSG_TYPE_ARRAY);

        return newMsg;
    }

    public static String getStrippedPropertyName(String name) {
        StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
        String result = "";

        while (tok.hasMoreElements()) {
            result = tok.nextToken();
        }
        return result;
    }

    private void setPointsProperty(Message msg, String name, Object value, String description)
            throws NavajoException, MappingException {
        Message ref = getMessageObject(name, msg, false, outputDoc, false);

        if (ref == null)
            ref = msg;
        String actualName = getStrippedPropertyName(name);

        if (ref == null)
            throw new MappingException("Property can only be created under a message");
        Property prop = ref.getProperty(actualName);

        if (prop == null) { // Property does not exist.
            prop = NavajoFactory.getInstance().createProperty(outputDoc, name, Property.POINTS_PROPERTY, "", 0, description, Property.DIR_OUT);
            prop.setPoints((Vector[]) value);
            ref.addProperty(prop);
        } else {
            prop.setType(Property.POINTS_PROPERTY);
            prop.setPoints((Vector[]) value);
            prop.setName(actualName);  // Should not matter ;)
        }
    }

    private Property setProperty(boolean parameter, Message msg, String name, Object value, String type, String direction, String description,
            int length)
            throws NavajoException, MappingException {

        //System.out.println("IN SETPROPERTY(), NAME = " + name + ", value = " + value + ", msg = " + msg);
        if (parameter) {
            if (msg == null)
                msg = tmlDoc.getMessage("__parms__");
        }
        Message ref = getMessageObject(name, msg, false, outputDoc, false);
        //System.out.println("ref = " + ref);

        String sValue = Util.toString(value, type);

        if (ref == null)
            ref = msg;
        String actualName = getStrippedPropertyName(name);

        if (ref == null)
            throw new MappingException("Property can only be created under a message");
        Property prop = ref.getProperty(actualName);

        if (prop == null) { // Property does not exist.
            if (!parameter)
                prop = NavajoFactory.getInstance().createProperty(outputDoc, actualName, type, sValue, length, description,
                        direction);
            else
                prop = NavajoFactory.getInstance().createProperty(tmlDoc, actualName, type, sValue, length, description,
                        direction);
            ref.addProperty(prop);
        } else {
            prop.setType(type);
            prop.setValue(sValue);
            prop.setName(actualName);  // Should not matter ;)
        }

        //if (parameter) {
        //  System.out.println("CREATED PARAMETER: " + actualName + " WITH VALUE = " + sValue);
        //}
        //System.out.println("LEAVING SETPROPERTY");
        return prop;
    }

    private void executePointMap(MappableTreeNode o, TslNode map, Point point) throws MappingException {
        try {
            Object value = null;
            String type = "";
            Operand operand = null;
            TslNode childNode = null;
            String condition = "";
            Vector allNodes = map.getAllNodes();

            try {
                for (int i = 0; i < allNodes.size(); i++) {
                    childNode = (TslNode) allNodes.get(i);
                    condition = childNode.getAttribute("condition");
                    boolean eval = Condition.evaluate(condition, tmlDoc, o, null);

                    if (eval) {
                        operand = Expression.evaluate(childNode.getAttribute("value"), tmlDoc, o, null);
                        value = operand.value;
                        type = operand.type;
                        i = allNodes.size() + 1; // Jump out of for loop.
                    } else {
                        childNode = null;
                    }
                }
            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                throw new MappingException(errorExpression(tmle.getMessage(), condition));
            }

            if (map.getTagName().equals("field")) { // tml-to-object. NOT YET SUPPORTED FOR POINTS PROPERTIES.
                setSimpleAttribute(o, map.getAttribute("name"), value, type);
            } else {
                point.setValue(value.toString());
            }
        } catch (Exception e) {
           logger.log(Priority.DEBUG, e.getMessage(), e);
        }
    }

    private void executeSelectionMap(MappableTreeNode o, TslNode map, Selection sel, Message parentMsg, Message outMessage, Message parmMessage) throws MappingException {
        try {
            Object value = null;
            String type = "";
            Operand operand = null;
            TslNode childNode = null;
            String condition = "";
            Vector allNodes = map.getAllNodes();

            try {
                for (int i = 0; i < allNodes.size(); i++) {
                    childNode = (TslNode) allNodes.get(i);
                    condition = childNode.getAttribute("condition");
                    boolean eval = Condition.evaluate(condition, tmlDoc, o, null);

                    if (eval) {
                        operand = Expression.evaluate(childNode.getAttribute("value"), tmlDoc, o, parentMsg, sel);
                        value = operand.value;
                        type = operand.type;
                        i = allNodes.size() + 1; // Jump out of for loop.
                    } else {
                        childNode = null;
                    }
                }
            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                throw new MappingException(errorExpression(tmle.getMessage(), condition));
            }

            if (map.getTagName().equals("param")) {
              executeSimpleMap(o, parentMsg, map, outMessage, parmMessage);
            } else
            if (map.getTagName().equals("field")) { // tml-to-object
                setSimpleAttribute(o, map.getAttribute("name"), value, type);
            } else {
                if (map.getAttribute("name").equals("name"))
                    sel.setName(value.toString());
                else if (map.getAttribute("name").equals("value"))
                    sel.setValue(value.toString());
                else if (map.getAttribute("name").equals("selected"))
                    sel.setSelected(value.toString().equals("true"));
                else
                    throw new MappingException("either ':name' or ':value' expected for selected properties instead of " + map.getAttribute("name"));
            }
        } catch (Exception e) {
            //Util.debugLog(e.getMessage());
        }
    }

    private static String getSelectionField(String name) {
        int e = name.lastIndexOf(Navajo.MESSAGE_SEPARATOR);

        return name.substring(e + 1, name.length());
    }

    public static String getPropertyPart(String name) {
        int e = name.lastIndexOf(Navajo.MESSAGE_SEPARATOR);

        return name.substring(0, e);
    }

    private static String getSelectionValue(Navajo doc, Message msg, String name) throws MappingException, NavajoException {
        String propName = getPropertyPart(name);
        String selField = getSelectionField(name);
        Property prop = null;

        if (msg != null) {
            prop = msg.getProperty(propName);
        } else {
            prop = doc.getProperty(propName);
        }

        ArrayList list = prop.getAllSelectedSelections();

        if (list.size() > 1)
            throw new MappingException("Only cardinality 1 selections allowed here");
        Selection sel = (Selection) list.get(0);

        if (selField.equals("value"))
            return sel.getValue();
        else if (selField.equals("name"))
            return sel.getName();
        else
            throw new MappingException("No such selection field");
    }

    /**
     * The next two methods: getAttribute()/setAttribute() are used for the set/get functionality
     * of the new Mappable interface (see also Java Beans standard). The use of set/get methods
     * enables the use of triggers that can be implemented within the set/get methods.
     * So if there is a field:
     * private double noot;
     * within a Mappable object, the following methods need to be implemented:
     * public double getNoot();
     * and
     * public void setNoot(double d);
     */
    public static Object getAttributeValue(MappableTreeNode o, String name, Object[] arguments) throws com.dexels.navajo.server.UserException,
            MappingException {

        Object result = null;
        // The ../ token is used to denote the parent of the current MappableTreeNode.
        // e.g., $../myField or $../../myField is used to identifiy respectively the parent
        // and the grandparent of the current MappableTreeNode.
        //System.out.println("IN GETATTRIBUTEVALUE(), name = " + name);

        int strip = -1;
        while ((strip = name.indexOf("../")) != -1) {
            //String object = tokens.nextToken();
            //Object dum = getAttributeObject(o, object, null);
            //String remaining = name.substring(object.length() + 1, name.length());
            o = o.parent;
            if (o == null)
              throw new MappingException("Null parent object encountered: " + name);
            name = name.substring(3, name.length());
            //System.out.println("NEW NAME = " + name);
            //return getAttributeValue(o, name, arguments);
        }
        //System.out.println("O = " + o + ", NAME =" + name);
        result = getAttributeObject(o, name, arguments);

        if (result != null) {

            String type = result.getClass().getName();
            if (type.equals("java.lang.Long")) {
                return new Integer(result.toString());
            } else if (type.equals("java.lang.Boolean")) {
                return new Boolean(result.toString());
            } else if (type.equals("java.util.Date")) {
                return result;
            } else if (type.equals("java.lang.Integer")) {
                return new Integer(result.toString());
            } else if (type.equals("java.lang.Double")) {
                return new Double(result.toString());
            } else if (type.equals("java.lang.Float")) {
                return new Double(result.toString());
            } else if (type.startsWith("[Ljava.util.Vector")) {
                return result;
            } else if (type.startsWith("[L")) {
                // Encountered array cast to ArrayList.
                Object[] array = (Object[]) result;
                ArrayList list = new ArrayList();
                for (int i = 0; i < array.length; i++) {
                    list.add(array[i]);
                }
                return list;
            } else {
                return result.toString();
            }
        } else {
            return null;
        }
    }

    public static Object getAttributeObject(MappableTreeNode o, String name, Object[] arguments) throws com.dexels.navajo.server.UserException, MappingException {
        Object result = null;
        StringBuffer methodNameBuffer = new StringBuffer();
        String methodName = "";

        //System.out.println("IN GETATTRIBUTEOBJECT");
        //System.out.println("MAPPABLE OBJECT = " + o.myObject + ", name = " + name);
        try {
            methodNameBuffer.append("get").append((name.charAt(0) + "").toUpperCase()).
                            append(name.substring(1, name.length()));
            methodName = methodNameBuffer.toString();

            Class c = o.myObject.getClass();
            java.lang.reflect.Method m = null;

            if (arguments == null) {
                m = c.getMethod(methodName, null);
                result = m.invoke(o.myObject, null);
            } else {
                // Invoke method with arguments.
                Class[] classArray = new Class[arguments.length];
                for (int i = 0; i < arguments.length; i++) {
                    classArray[i] = arguments[i].getClass();
                }
                m = c.getMethod(methodName, classArray);
                result = m.invoke(o.myObject, arguments);
            }
        } catch (NoSuchMethodException nsme) {
            throw new MappingException("Method not found: " + methodName + " in object: " + o.myObject);
        } catch (IllegalAccessException iae) {
            throw new MappingException(methodName + " illegally accessed in mappable class: " + o.myObject.getClass().getName());
        } catch (InvocationTargetException ite) {
            Throwable t = ite.getTargetException();
            logger.log(Priority.DEBUG, "in getAttributeObject()", t);
            if (t instanceof com.dexels.navajo.server.UserException)
                throw (com.dexels.navajo.server.UserException) t;
            else
                throw new MappingException("Illegal exception thrown: " + t.getMessage());
        }
        return result;
    }

    private void copyArray(Object source, Object dest) {
        for (int i = 0; i < Array.getLength(source); i++) {
            Array.set(dest, i, Array.get(source, i));
        }
    }

    /**
     * This method is used for setting instances of mappable object fields.
     */
    private void setAttribute(MappableTreeNode o, String name, Object arg) throws com.dexels.navajo.server.UserException, MappingException {
        setAttribute(o, name, arg, null);
    }

    private void setAttribute(MappableTreeNode o, String name, Object arg, Class type) throws com.dexels.navajo.server.UserException, MappingException {

        String methodName = "set" + (name.charAt(0) + "").toUpperCase()
                + name.substring(1, name.length());
        Class c = o.myObject.getClass();
        Class[] parameters = null;
        Object[] arguments = null;

        if ((arg != null) && arg.getClass().isArray()) {
            Object[] castarg = (Object[]) arg;
            Object single = castarg[0];
            Object arrayarg = Array.newInstance(single.getClass(), castarg.length);
            copyArray(arg, arrayarg);
            parameters = new Class[] {arrayarg.getClass()};
            arguments = new Object[] {arrayarg};
        } else {
            if (type == null)
                parameters = new Class[] {arg.getClass()};
            else
                parameters = new Class[] {type};
            arguments = new Object[] {arg};
        }
        java.lang.reflect.Method m = null;

        java.lang.reflect.Method[] all = c.getMethods();
        for (int i = 0; i < all.length; i++) {
            if (all[i].getName().equals(methodName)) {
                m = all[i];
                Class [] inputParameters = m.getParameterTypes();
                if (inputParameters.length == parameters.length) {
                  for (int j = 0; j < inputParameters.length; j++) {
                     Class myParm = parameters[j];
                     if (inputParameters[j].isAssignableFrom(myParm)) {
                        i = all.length + 1;
                        j = inputParameters.length + 1;
                        break;
                     }
                  }
                }
            }
        }

        if (m == null) {
            throw new MappingException("Could not find method in Mappable object: " + methodName);
        }
        try {
            m.invoke(o.myObject, arguments);
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
            String error = "Error in accessing method " + methodName
                    + " in mappable object: " + c.getName();

            throw new MappingException(error);
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
            Throwable t = ite.getTargetException();

            if (t instanceof com.dexels.navajo.server.UserException)
                throw (com.dexels.navajo.server.UserException) t;
            else
                throw new MappingException("Illegal exception thrown: " + t.getMessage());
        }
    }

    private void setSimpleAttribute(MappableTreeNode o, String name, Object value, String propertyType) throws com.dexels.navajo.server.UserException, MappingException,
            java.lang.NumberFormatException {
        String type = "";

        //System.out.println("IN SETSIMPLEATTRIBUTE(), o.myObject = " + o.myObject);
        //System.out.println("NAME = " + name);
        //System.out.println("VALUE = " + value);
        type = getFieldType(o, name);

        if (value == null) {
            setAttribute(o, name, null, java.lang.Object.class);
            return;
        }
        if (type.equals("java.lang.Object")) {
            // Set type according to property type.
            if (propertyType.equals(Property.INTEGER_PROPERTY))
                type = "int";
            else if (propertyType.equals(Property.FLOAT_PROPERTY))
                type = "double";
            else if (propertyType.equals(Property.BOOLEAN_PROPERTY))
                type = "boolean";
            else if (propertyType.equals(Property.DATE_PROPERTY))
                type = "java.util.Date";
            else
                type = "java.lang.String";
        }
        if (type.equals("boolean")) {
            setAttribute(o, name, (value instanceof Boolean) ? value : new Boolean(value.toString()), Boolean.TYPE);
        } else if (type.equals("int")) {
            setAttribute(o, name, (value instanceof Integer) ? value : new Integer(value.toString()), Integer.TYPE);
        } else if (type.equals("long")) {
            setAttribute(o, name, (value instanceof Long) ? value : new Long(value.toString()), Long.TYPE);
        } else if (type.equals("float")) {
            setAttribute(o, name, (value instanceof Float) ? value: new Float(value.toString()), Float.TYPE);
        } else if (type.equals("double")) {
            setAttribute(o, name, (value instanceof Double) ? value : new Double(value.toString()), Double.TYPE);
        } else if (type.equals("char")) {
            setAttribute(o, name, new Character(value.toString().charAt(0)), Character.TYPE);
        } else if (type.equals("java.util.Date")) {
            setAttribute(o, name, value, java.util.Date.class);
        } else if (type.equals("java.lang.String")) {
            setAttribute(o, name, value, String.class);
        } else {
            Util.debugLog("UNKNOWN attribute type: " + type);
        }
    }

    private String showNodeInfo(TslNode map) {
        if (map != null)
            return " @node [" + map.toString() + "]:";
        else
            return " @node [No node info: null]: ";
    }

    private String showNodeInfo() {
        return showNodeInfo(currentNode);
    }

    /**
     * executeSimpleMap() executes a simple map of the form:
     * attribute = property (either way).
     */
    private void executeSimpleMap(MappableTreeNode o, Message msg, TslNode map, Message outMessage, Message parmMessage)
            throws MappingException, NavajoException, com.dexels.navajo.server.UserException,
            java.lang.NumberFormatException, SystemException {

        Object value = null;
        String type = "";
        Operand operand = null;
        TslNode childNode = null;
        String condition = map.getAttribute("condition");

        // Check for <methods> tag
        if (map.getTagName().equals("methods")) {
            includeMethods(map);
            return;
        }

        boolean eval = false;
        try {
            eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, o, msg);
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
            throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
        }
        if (!eval) // Condition to execute simple map failed.
            return;
        Vector allNodes = map.getAllNodes();

        if (allNodes.size() == 0) {
          if (map.getTagName().equals("field"))  // <field> tag should ALWAYS be followed by an <expression> tag.
              throw new MappingException("<expression> tag(s) expected");
        }

        ArrayList options = new ArrayList();

        try {

            for (int i = 0; i < allNodes.size(); i++) {
                childNode = (TslNode) allNodes.get(i);

                if (childNode.getTagName().equals("expression")) {
                  condition = childNode.getAttribute("condition");
                  eval = Condition.evaluate(condition, tmlDoc, o, msg);
                  if (eval) {
                      // Check for match attribute. If match attribute present use message that matches.
                      // Syntax: match="[property regular expression];[expression]". The message of the property that matches the value of the expression
                      // is used as reference for the expression in name="".

                      org.w3c.dom.Element elmnt = (Element) childNode.getNode();
                      if (elmnt.getFirstChild() != null) {
                        value = elmnt.getFirstChild().getNodeValue();
                        type = Property.STRING_PROPERTY;
                        operand = new Operand(value, type, "");
                      } else {
                        if (!childNode.getAttribute("match").equals("")) {
                            Message referenceMsg = Expression.match(childNode.getAttribute("match"), tmlDoc, o, msg);
                            if (referenceMsg == null)
                                throw new MappingException("No matching message found. " + showNodeInfo(childNode));
                            operand = Expression.evaluate(childNode.getAttribute("value"), tmlDoc, o, referenceMsg);
                        } else {
                            operand = Expression.evaluate(childNode.getAttribute("value"), tmlDoc, o, msg);
                        }
                      }

                      //System.out.println("OPERAND = " + operand);
                      value = operand.value;

                      if (value == null)
                          value = new String("");
                      type = operand.type;

                      i = allNodes.size() + 1; // Jump out of for loop.
                  } else {
                      childNode = null;
                  }
                } else if (childNode.getTagName().equals("option")) {
                   Selection sel = NavajoFactory.getInstance().createSelection(outputDoc, childNode.getAttribute("name"),
                                                    childNode.getAttribute("value"),
                                                    (childNode.getAttribute("selected").equals("1") ? true : false));
                   options.add(sel);
                } else {
                  throw new MappingException("expected <expression> or <option> tag");
                }
            }
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
            throw new MappingException(tmle.getMessage() + showNodeInfo(childNode));
        }
        if ((childNode == null) && (allNodes.size() > 0))
            throw new MappingException("No matching conditions found for simple map");
        if (map.getTagName().equals("field")) {  // TML to object. we zitten in een <field> tag
            if (childNode == null)
                throw new MappingException("No <expression> tags found under <field> tag");
            setSimpleAttribute(o, map.getAttribute("name"), operand.value, type);
        } else if (map.getTagName().equals("property")
                || map.getTagName().equals("param")) {  // Object to TML. we zitten in een <property> tag
            // Check if description is an object attribute.
            // String description = map.getAttribute("description"); //Expression.evaluate(map.getAttribute("description"), tmlDoc, o).value;
            String description = "";
            description = map.getAttribute("description");
            String propertyName = "";
            try {
                propertyName = map.getAttribute("name");// MAYBE IN THE FUTURE: Expression.evaluate(map.getAttribute("name"), tmlDoc, o, msg).value;
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            // Set new property value.s      setProperty(msg, propertyName, value, type, map.getAttribute("direction"), description, Integer.parseInt(map.getAttribute("length")));
            String length = map.getAttribute("length");
            String v = map.getAttribute("value");

            if (allNodes.size() == 0 || (options.size() > 0)) {
                // We don not have an <expression> tag.
                Property p = setProperty(map.getTagName().equals("param"), outMessage, propertyName, v,
                              map.getAttribute("type"), map.getAttribute("direction"),
                              map.getAttribute("description"),
                              (!length.equals("")) ? Integer.parseInt(length) : 25);
                // Add defined options
                if (options.size() > 0)
                  p.setCardinality(map.getAttribute("cardinality"));
                for (int k = 0; k < options.size(); k++) {
                    p.addSelection((Selection) options.get(k));
                }
                return;
            }

            if (value != null) {
                if (value.equals(""))
                    value = v;
                if (type.equals("")) // If the operand does not define a type, use the type as specified by the "type" attribute of the property or param.
                    type = map.getAttribute("type");
                if (map.getTagName().equals("param")) // We have a parameter property.
                    outMessage = parmMessage;
                setProperty(map.getTagName().equals("param"), outMessage, propertyName,
                            value, type, map.getAttribute("direction"), description,
                            (!length.equals("")) ? Integer.parseInt(length) : 0);
            } else {  // We have an object value (points property!)
                setPointsProperty(outMessage, propertyName, operand.value, description);
            }
        } else if (map.getTagName().equals("message")) {
            throw new MappingException("Did not expect <message> tag at this point");
        }
        //System.out.println("LEAVING EXECUTESIMPLEMAP()");
    }

    private void createPoint(TslNode root, MappableTreeNode o, Point point) throws Exception {
        callLoadMethod(o);
        for (int i = 0; i < root.getNodesSize(); i++) {
            TslNode map = root.getNode(i);

            currentNode = map;
            if (map.getNodeByType("map") != null)
                throw new MappingException("No submappings allowed here");
            try {
                executePointMap(o, map, point);
            } catch (Exception e) {
                callKillMethod(o, 3);
                throw e;
            }
        }
        callStoreMethod(o);
    }

    private void createSelection(TslNode root, MappableTreeNode o, Selection selection, Message parentMsg, Message outMessage, Message parmMessage) throws Exception {
        callLoadMethod(o);
        for (int i = 0; i < root.getNodesSize(); i++) {
            TslNode map = root.getNode(i);
            currentNode = map;

            if (map.getNodeByType("map") != null)
                throw new MappingException("No submappings allowed here");
            try {
                if (map.getTagName().equals("message"))
                  addMessage(outputDoc, parentMsg, map.getAttribute("name"), null,
                            (map.getAttribute("condition").equals("") ? 1 : Integer.parseInt(map.getAttribute("condition"))),
                             map.getAttribute("type"));
                if (map.getTagName().equals("map"))
                  doMapping(outputDoc, map, parentMsg, outMessage, parmMessage, null);
                else
                  executeSelectionMap(o, map, selection, parentMsg, outMessage, parmMessage);
            } catch (Exception e) {
                callKillMethod(o, 4);
                throw e;
            }
        }
        callStoreMethod(o);
    }

    private void doMapping(Navajo doc, TslNode node, Message absoluteParent, Message outMessage,
                           Message parmMessage, MappableTreeNode context) throws
            Exception, BreakEvent {
        //System.out.println("IN DOMAPPING: TRYING TO INSTANTIATE: " + node.getAttribute("object"));
        Mappable o = getMappable(node.getAttribute("object"), "");
        //System.out.println("CREATED MAPPABLE OBJECT: " + o);
        MappableTreeNode mapTreeNode = new MappableTreeNode(context, o);
        //System.out.println("CREATED MAPPABLETREENODE: " + mapTreeNode);
        createMapping(node, absoluteParent, mapTreeNode, outMessage, parmMessage, true, false);
    }

    private void addAntiMessage(Navajo doc, Message parent, String message) throws NavajoException {
        Message msg = NavajoFactory.getInstance().createAntiMessage(doc, message);

        if (parent == null)
            doc.addMessage(msg);
        else
            parent.addMessage(msg);
    }

    private Message[] addMessage(Navajo doc, Message parent, String message, String template, int count,
                                 String type)
            throws java.io.IOException, NavajoException, org.xml.sax.SAXException, MappingException {

        if (message.indexOf(Navajo.MESSAGE_SEPARATOR) != -1)
            throw new MappingException("No submessage constructs allowed in <message> tags: " + message);
        Message[] messages = new Message[count];
        Message msg = null;
        int index = 0;

        if (!template.equals("")) { // Read template file.
            Navajo tmp = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + template + ".tmpl");
            Message bluePrint = tmp.getMessage(template);
            bluePrint.setName(message);
            msg = tmp.copyMessage(bluePrint, doc);
        } else {
            msg = NavajoFactory.getInstance().createMessage(doc, message);
        }
        if (count > 1) {
            msg.setName(message + "0");
            msg.setIndex(0);
            msg.setType(Message.MSG_TYPE_ARRAY);
            if (parent == null)
                msg = doc.addMessage(msg, false);
            else
                msg = parent.addMessage(msg, false);
            messages[index++] = msg;
        } else if (count == 1) {
            if (parent == null)
                msg = doc.addMessage(msg, false);
            else {
                msg = parent.addMessage(msg, false);
            }
            messages[index++] = msg;
            if (!type.equals(""))
                msg.setType(type);
        }
        for (int i = 1; i < count; i++) {
            Message extra = doc.copyMessage(msg, doc);
            extra.setName(message + i);
            extra.setIndex(i);
            if (parent == null)
                extra = doc.addMessage(extra, false);
            else
                extra = parent.addMessage(extra, false);
            messages[index++] = extra;
        }

        return messages;
    }

    private Navajo createTML(TslNode node) throws java.io.IOException,
            org.xml.sax.SAXException, MappingException, NavajoException {
        Navajo doc = null;
        String fileName = node.getAttribute("service");

        doc = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + fileName + ".tml");
        return doc;
    }

    /**
     * interpretAddBody() parses and executed ADD constructs. If an ADD constructs contains
     * a MAP construct, doMapping() is called to handle the MAP construct.
     */
    private void interpretAddBody(Message parent, Navajo doc, TslNode node, MappableTreeNode currentObject, Message parentInMessage, Message parmMessage, boolean parameter)
            throws Exception, BreakEvent {
        TslNode addNode = node;

        String command = addNode.getTagName();
        String message = addNode.getAttribute("name");
        String template = addNode.getAttribute("template");
        String condition = addNode.getAttribute("condition");
        String type = addNode.getAttribute("type");

        boolean eval = false;

        try {
          eval = Condition.evaluate(condition, tmlDoc, currentObject, parentInMessage);
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
            throw new MappingException(tmle.getMessage() + ": " + condition);
        }
        // Only do parsing and calculation of <count> argument if condition is true (performance!).
        if (eval) {  // Interpret body if condition is true, else add anti-message
            // Calculate count argument (can be a TML expression, instead of just a constant)
            String sCount = "";

            sCount = addNode.getAttribute("count");
            if (sCount.equals(""))
                sCount = "1";  // Default is 1.
            Operand op = null;

            try {
                op = Expression.evaluate(sCount, tmlDoc, currentObject, parentInMessage);
            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                throw new MappingException(tmle.getMessage());
            }
            int count = 1;

            if (op.type.equals(Property.INTEGER_PROPERTY))
                count = Integer.parseInt(op.value.toString());
            else if (op.type.equals(Property.FLOAT_PROPERTY))
                count = (int) Double.parseDouble(op.value.toString());
            else if (op.type.equals(Property.STRING_PROPERTY)) {
                try {
                    count = Integer.parseInt(op.value.toString());
                } catch (Exception e) {
                    throw new MappingException("Only integer-expressions allowed in <count> argument for ADD constructs");
                }
            } else
                throw new MappingException("Only integer-expressions allowed in <count> argument for ADD constructs");
            // Create <count> messages (Use array and not ArrayList (performance!).
            Message[] messages;

            if (parameter)
                messages = addMessage(tmlDoc, parmMessage, message, template, count, type);
            else
                messages = addMessage(doc, parent, message, template, count, type);
            for (int nrMesg = 0; nrMesg < messages.length; nrMesg++) {
                Message newParent = messages[nrMesg];
                TslNode childNode;
                if (parameter) {
                    createMapping(node, parentInMessage, currentObject, parent, newParent, false, false);
                } else {
                    createMapping(node, parentInMessage, currentObject, newParent, parmMessage, false, false);
                }
            }
        } else {
            // Add anti-message if condition was not true.
            // Client ignore antiMessage or use it to invalidate and remove previous versions of
            // the message.
            addAntiMessage(doc, parent, message);
        }
    }

    private void processBreak(TslNode tsl, MappableTreeNode o, Message msg) throws BreakEvent, MappingException, SystemException {
        String condition = tsl.getAttribute("condition");

        if ((condition == null) || (condition.equals(""))) // Unconditional break.
            throw new BreakEvent();
        else {
            try {
                boolean eval = Condition.evaluate(condition, tmlDoc, o, msg);
                if (eval)
                    throw new BreakEvent();
            } catch (TMLExpressionException tmle) {
                throw new MappingException(errorExpression(tmle.getMessage(), condition));
            }
        }
    }

    private void includeMethods(TslNode methods) throws MappingException, NavajoException {
        for (int i = 0; i < methods.getAllNodes().size(); i++) {
            TslNode child = methods.getNode(i);
            String name = child.getAttribute("name");
            String condition = child.getAttribute("condition");
            boolean eval = true;
            if ((condition != null) && (!condition.equals(""))) {
               try {
                  eval = Condition.evaluate(condition, tmlDoc, null, null);
               } catch (Exception tmle) {
                  throw new MappingException(errorExpression(tmle.getMessage(), condition));
               }
            }
            if (eval) {
              com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(outputDoc, name, "");
              m.setDescription(child.getAttribute("description"));
              for (int j = 0; j < child.getAllNodes().size(); j++) {
                  m.addRequired(child.getNode(j).getAttribute("message"));
              }
              outputDoc.addMethod(m);
            }
        }
    }

    /**
     * interpret(String service)
     * interprets the script <service>.ts to control the business-flow:
     * 1. create the default TML output document <service>.tml or override it (CREATETML)
     * 2. dynamically add messages/properties to the output document (ADD/MAP)
     * 2. instantiate (MAP) the proper Mappable objects .
     */
    public Navajo interpret(String service) throws MappingException,
            MappableException,
            com.dexels.navajo.server.UserException,
            SystemException {
        try {

            //System.out.println("IN INTERPRET(), SERVICE = " + service);
            // Default behavior: create output TML document using the service name as base.
            // This can be overridden using the "CREATETML" statement in the user script.

            // long start = System.currentTimeMillis();
            requestCount++;

            if (config.getScriptVersion().equals("1.0")) {
                Util.debugLog("interpret version 10.0 (): reading output file: " + tmlPath + "/" + service + ".tml");
                if (access.betaUser) {
                    try {
                        outputDoc = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + service + ".tml_beta");
                    } catch (Exception e) {// //System.out.println("Could not find beta version of tml file");
                    }
                }
                if (outputDoc == null)
                    outputDoc = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + service + ".tml");
            } else
              outputDoc = NavajoFactory.getInstance().createNavajo();

            access.setOutputDoc(outputDoc);

            rootNode = new TslNode(tsldoc);
            Message parmMessage = tmlDoc.getMessage("__parms__");
            // read and process the xml script
            int index = rootNode.getNodesSize();
            TslNode childNode;
            String tag;

            for (int i = 0; i < index; i++) {
                childNode = rootNode.getNode(i);
                currentNode = childNode;
                tag = childNode.getTagName();
                //Util.debugLog("!!! >>>>  childnode: " + tag + "<<<<<<<");
                if (tag.equals("break")) {
                    processBreak(childNode, null, null);
                } else
                if (tag.equals("map")) {
                    //Util.debugLog("in map tag");
                    doMapping(outputDoc, childNode, null, null, parmMessage, null);
                } else
                if (tag.equals("message") || tag.equals("paramessage")) {
                    if (outputDoc == null)
                        throw new MappingException("No output document specified");
                    // interpretAddBody(null, outputDoc, childNode, null, null);
                    createMapping(childNode, null, null, null, parmMessage, false, true);
                } else
                if (tag.equals("field") || tag.equals("property")
                        || tag.equals("param")) {
                    executeSimpleMap(null, null, childNode, null, parmMessage);
                } else
                if (tag.equals("creattml")) {
                    //Util.debugLog("!!! <<<<<< childnode: " + childNode.getTagName());
                    outputDoc = createTML(childNode);
                } else
                if (tag.equals("methods")) {
                    includeMethods(childNode);
                }
            }
            // long end = System.currentTimeMillis();
            // double total = (end - start) / 1000.0;
            // totaltiming += total;
            // ////System.out.println("finished interpreter in " + total + " seconds. Average intepreter time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
            return access.getOutputDoc();
        } catch (BreakEvent be) {
            // NOTE: In the future add break() methods in Mappable objects to react on a <break> event.
            return outputDoc;
        } catch (Exception me) {
            logger.log(Priority.DEBUG, "Fatal error", me);
            throw new MappableException(showNodeInfo() + me.getMessage());
        }
    }

    public static void main(String [] args) {
        String s = "";
        System.out.println("isAssignable = " + Object.class.isAssignableFrom(s.getClass()));
        System.out.println("isAssignable = " + s.getClass().isAssignableFrom(Object.class));
    }
}
