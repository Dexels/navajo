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
        // //System.out.println("In MapperInterpreter constructor");
        // //System.out.println("Classloader: " + config.getClassloader());
        this.config = config;
        tmlPath = config.getScriptPath();
        fileName = name;
        tmlDoc = doc;
        parameters = parms;
        access = acs;
        // fu = new FileUtils();
        //Util.debugLog("FileUtils: " + fu);
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

    private String getFieldType(Object o, String field) throws MappingException {
        try {
            String type = o.getClass().getField(field).getType().getName();

            if (type.startsWith("[L")) { // We have an array determine member type.
                type = type.substring(2, type.length() - 1);
            }
            return type;
        } catch (java.lang.NoSuchFieldException nsfe) {
            throw new MappingException(errorFieldNotFound(field, o));
        }
    }

    private Mappable getMappable(String object, String name) throws MappingException {
        String error = "";

        //Util.debugLog(">>>>>>>>>>>>>>>>>>>>>>>>>. in getMappable(): object = " + object);
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
        // //System.out.println("callKillMethod(id = " + id + "), o = " + o);
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

    private boolean isArrayAttribute(Object o, String field) throws MappingException {
        try {
            String objectType = o.getClass().getField(field).getType().getName();

            return objectType.startsWith("[L");
        } catch (NoSuchFieldException nsfe) {
            throw new MappingException(errorFieldNotFound(field, o));
        }
    }

    private ArrayList getObjectList(Object o, String field, String filter, Navajo doc, Message parent)
            throws com.dexels.navajo.server.UserException, MappingException, SystemException {
        ArrayList result = new ArrayList();

        try {
            String objectType = "";

            if (!field.equals("")) {
                try {
                    objectType = o.getClass().getField(field).getType().getName();
                } catch (NoSuchFieldException nsfe) {
                    throw new MappingException(errorFieldNotFound(field, o));
                }
                if (objectType.startsWith("[L")) { // Array
                    // System.out.println( "An arraylist found, returning arraylist... field: " + field );
                    Object[] dum = (Object[]) getAttributeObject(o, field, null);

                    if (dum == null)  // If no instances assigned!
                        return result;
                    for (int i = 0; i < dum.length; i++)
                        result.add(dum[i]);
                    // System.out.println("Added objects to result");
                } else if (!objectType.equals("int")
                        && !objectType.equals("long")
                        && !objectType.endsWith("java.lang.String")
                        && !objectType.equals("char")
                        && !objectType.equals("float")
                        && !objectType.equals("double")
                        && !objectType.equals("long")
                        && !objectType.equals("boolean")
                        && !objectType.equals("java.util.Date")) {
                    //Util.debugLog("getAttributeObject(o, field): " + field);
                    Mappable sub = (Mappable) getAttributeObject(o, field, null);

                    if (sub != null) // Only add non-null objects.
                        result.add(sub);
                }
            } else {
                //Util.debugLog("o = a prime object, adding object...");
                result.add(o);
            }
            if (!filter.equals("")) {
                ArrayList dummy = new ArrayList();

                for (int i = 0; i < result.size(); i++) {
                    Object op = result.get(i);
                    // System.out.println("filter = " + filter);
                    boolean match = Condition.evaluate(filter, doc, op, parent);

                    // System.out.println("match = " + match);
                    if (match) {
                        dummy.add(op);
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
        //Util.debugLog("isSelection(), msg = " + msg + ", msgName = " + msgName);
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
        //Util.debugLog("prop = " + prop);
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

        //Util.debugLog("in getSelectedItems(): msg = " + msg + ", msgName = " + msgName);
        if (msg != null)
            prop = msg.getProperty(msgName);
        else
            prop = doc.getProperty(msgName);
        if (!prop.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoException("Selection Property expected");
        result = prop.getAllSelectedSelections();
        //Util.debugLog("Number of selections: " + result.size());
        return result;
    }

    private ArrayList getMessageList(Message msg, Navajo doc, String str, String filter, Object o)
            throws NavajoException, SystemException, MappingException {
        try {
            ArrayList result = new ArrayList();

            if (str.equals("")) {
                result.add(Message.create(doc, "__JUST_FOR_ITERATING_ONCE__"));
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

    //
    /**
     * createMapping() actually executes the parsed MAP tree (starting from root).
     */
    private void createMapping(TslNode root, Message msg, Object o, Message outMessage, Message parmMessage, boolean loadObject, boolean emptyMap)
            throws Exception, BreakEvent {
        //Util.debugLog("FIRST STEP in createMapping: o = " + o + ", msg = " + msg);
        String condition = root.getAttribute("condition");

        //Util.debugLog("condition = " + condition);
        boolean eval = false;

        try {
            eval = Condition.evaluate(root.getAttribute("condition"), tmlDoc, o, msg);
        } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
            tmle.printStackTrace();
            throw new MappingException(errorExpression(tmle.getMessage(), root.getAttribute("condition")));
        }
        //Util.debugLog("eval = " + eval);
        if (!eval) // Condition to execute simple map failed.
            return;
        // MapObject root
        ArrayList repetitions = null;

        // First, call load on object.
        if ((o != null) && loadObject) {
            callLoadMethod(o);
            //Util.debugLog("in createMapping: called load() method()");
        }
        try {
            //Util.debugLog("Node " + root.getTagName() + " has " + root.getNodesSize() + " children tags");
            // If emptyMap is true the construct is of the form <message><map ref=""></message> without a parent map preceding the message tag.
            int countNr = (emptyMap) ? 1 : root.getNodesSize();

            for (int i = 0; i < countNr; i++) {
                TslNode map = (emptyMap) ? root : root.getNode(i); // deze kan zijn: field(toNavajo) of property(!toNavajo)

                currentNode = map;
                if (map.getTagName().equals("break"))
                    processBreak(map, o, msg);
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
                // //System.out.println( "in createMapping(): map.getTagName() = " + map.getTagName() );
                // //System.out.println( "in createMapping(): submap = " + submap );
                // //System.out.println( "in createMapping(): ref = " + ref );
                // //System.out.println( "in createMapping(): type = " + maptype );
                // //System.out.println( "in createMapping(): filter = " + filter );
                if (map.getTagName().equals("map")) { // Encountered a submap with new object.
                    doMapping(outputDoc, map, msg, outMessage, parmMessage, o);
                } else
                if ((map.getTagName().equals("message")
                        || map.getTagName().equals("paramessage"))
                        && ((submap != null && ref.equals(""))
                        || (submap == null))
                        ) {  // Add message with new object instance submap.
                    String name = map.getAttribute("name");

                    //Util.debugLog("in createMapping(): message name = " + name);
                    //Util.debugLog("in createMapping(): calling doAdding()");
                    interpretAddBody(outMessage, outputDoc, map, o, msg, parmMessage, map.getTagName().equals("paramessage"));
                } else {
                    // System.out.println( "processing Node " + map.getTagName() + " " + map.getAttribute( "name" ) );
                    if (submap != null) {  // We have a submapping in map.
                        // //Util.debugLog("submapping to: " + submap.getAttribute("object"));
                        // Determine number of times that the submapping needs to be executed:
                        // 1. For Object to TML the number of object instances is used.
                        // 2. For TML to Object:
                        // a) For non-selection properties, the number of matched messages is used.
                        // b) For selection properties, the number of selected options is used.
                        boolean isSelectionRef = false;
                        boolean isArrayAttribute = false;

                        // if (map.getTagName().equals("param")) {
                        // throw new MappingException(generalError("Mapped param messaged are not yet supported."));
                        // } else
                        if (map.getTagName().equals("message")
                                || map.getTagName().equals("paramessage")) {
                            eval = false;
                            try {
                                eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, o, msg);
                            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                                tmle.printStackTrace();
                                throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
                            }
                            if (eval) {
                                if (maptype.equals("tml")) {
                                    if (!isSelection(msg, tmlDoc, submap.getAttribute("ref"))) {
                                        //Util.debugLog("getMessageList with: " + submap.getAttribute("ref"));
                                        repetitions = getMessageList(msg, tmlDoc, submap.getAttribute("ref"), filter, o);
                                    } else {
                                        isSelectionRef = true;
                                        // What if we have repeated selected items of a selection property?
                                        //Util.debugLog("getSelectedItems with: " + submap.getAttribute("ref") + " selection name: " + map.getAttribute("name"));
                                        repetitions = getSelectedItems(msg, tmlDoc, submap.getAttribute("ref"));
                                    }
                                } else {
                                    repetitions = getObjectList(o, submap.getAttribute("ref"), filter, tmlDoc, msg);
                                    isArrayAttribute = this.isArrayAttribute(o, submap.getAttribute("ref"));
                                }
                            } else
                                repetitions = new ArrayList();
                        } else
                        if (map.getTagName().equals("property")) {// it's object-to-tml
                            //Util.debugLog("getObjectList with: " + submap.getAttribute("ref"));
                            eval = false;
                            try {
                                eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, o, msg);
                            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                                tmle.printStackTrace();
                                throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
                            }
                            if (eval)
                                repetitions = getObjectList(o, submap.getAttribute("ref"), filter, tmlDoc, msg);
                            else
                                repetitions = new ArrayList();
                        } else {// it's tml-to-object
                            eval = false;
                            //Util.debugLog(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CONDITION ================ " + map.getAttribute("condition"));
                            try {
                                eval = Condition.evaluate(map.getAttribute("condition"), tmlDoc, o, msg);
                            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                                tmle.printStackTrace();
                                throw new MappingException(errorExpression(tmle.getMessage(), map.getAttribute("condition")));
                            }
                            if (eval) {
                                if (!isSelection(msg, tmlDoc, submap.getAttribute("ref"))) {
                                    // System.out.println( "getMessageList with: " + submap.getAttribute( "ref" ) );
                                    repetitions = getMessageList(msg, tmlDoc, submap.getAttribute("ref"), filter, o);
                                } else {
                                    isSelectionRef = true;
                                    // What if we have repeated selected items of a selection property?
                                    // System.out.println( "getSelectedItems with: " + submap.getAttribute( "ref" ) + " selection name: " + map.getAttribute( "name" ) );
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

                        // System.out.println( "!!!DEBUG!!! repetitions: " + repeat );
                        for (int j = 0; j < repeat; j++) {
                            Mappable expandedObject = null;
                            Message expandedMessage = null;
                            Selection expandedSelection = null;
                            Point expandedPoint = null;

                            if ((repeat > 1) || isArrayAttribute) {
                                // For TML to object mappings with multiple messages, expand the messsageName with a counter.
                                messageName = map.getAttribute("name") + j;
                            } else {
                                messageName = map.getAttribute("name");
                            }
                            // TODO: WE CAN ONLY ENCOUNTER SELECTION PROPERTIES AT THIS POINT!!!!
                            if (map.getTagName().equals("paramessage")) {
                                if (map.getAttribute("name").equals(""))
                                    throw new MappingException(errorEmptyAttribute("name", "message"));
                                else
                                    expandedMessage = getMessageObject(messageName, parmMessage, true, tmlDoc);
                                    expandedMessage.setIndex(j);
                                if (maptype.equals("tml")) {
                                    if (!isSelectionRef) { // Get message from list.
                                        createMapping(submap, (Message) repetitions.get(j), o, outMessage, expandedMessage, true, false);
                                    } else {  // or, get selection option from list.
                                        expandedSelection = (Selection) repetitions.get(j);
                                        createSelection(submap, o, expandedSelection);
                                    }
                                } else {
                                    // Get Mappable object from the current instance list.
                                    expandedObject = (Mappable) repetitions.get(j);
                                    createMapping(submap, msg, expandedObject, outMessage, expandedMessage, true, false);
                                }
                            } else
                            if (map.getTagName().equals("message")) {
                                if (map.getAttribute("name").equals(""))
                                    throw new MappingException(errorEmptyAttribute("name", "message"));
                                else
                                    expandedMessage = getMessageObject(messageName, outMessage, true, outputDoc);
                                    expandedMessage.setIndex(j);
                                if (maptype.equals("tml")) {
                                    if (!isSelectionRef) { // Get message from list.
                                        createMapping(submap, (Message) repetitions.get(j), o, expandedMessage, parmMessage, true, false);
                                    } else {  // or, get selection option from list.
                                        expandedSelection = (Selection) repetitions.get(j);
                                        createSelection(submap, o, expandedSelection);
                                    }
                                } else {
                                    // Get Mappable object from the current instance list.
                                    expandedObject = (Mappable) repetitions.get(j);
                                    createMapping(submap, msg, expandedObject, expandedMessage, parmMessage, true, false);
                                }
                            } else
                            if (map.getTagName().equals("property")) { // Map Mappable Object to TML
                                //Util.debugLog("PROPERTY NAME IS: " + map.getAttribute("name"));
                                //Util.debugLog("PROPERTY TYPE IS: " + map.getAttribute("type"));
                                //Util.debugLog("!!!DEBUG!!! messageName: " + messageName);
                                // Get Mappable object from the current instance list.
                                expandedObject = (Mappable) repetitions.get(j);
                                if (map.getAttribute("name").equals(""))
                                    throw new MappingException(errorEmptyAttribute("name", "property"));
                                if (map.getAttribute("type").equals("selection")) {
                                    // get a Selection property.
                                    //Util.debugLog("Getting expanded selection: msg = " + outMessage);
                                    // expandedSelection = getSelectionObject(msg, map);
                                    expandedSelection = getSelectionObject(outMessage, map);
                                    // call createSelection() to handle special case of selection submapping.
                                    createSelection(submap, expandedObject, expandedSelection);
                                } else if (map.getAttribute("type").equals("points")) {
                                    //Util.debugLog("Getting expanded points: msg = " + outMessage);
                                    expandedPoint = getPointsObject(outMessage, map);
                                    createPoint(submap, expandedObject, expandedPoint);
                                } else {
                                    throw new MappingException(errorIllegalSubMap(map.getAttribute("type"), " Only selection pr point types can be submapped"));
                                }
                            } else if (map.getTagName().equals("field")) {  // Map TML message to Mappable Object
                                // Create a new instance of Mappable object.
                                String type = getFieldType(o, map.getAttribute("name"));

                                expandedObject = getMappable(type, map.getAttribute("name"));
                                // System.out.println("expandedObject = " + expandedObject);
                                if (!isSelectionRef) {// Get message from list.
                                    expandedMessage = (Message) repetitions.get(j);
                                    // Recursively call createMapping() to execute submapping on expandedMessage and expandedObject
                                    createMapping(submap, expandedMessage, expandedObject, outMessage, parmMessage, true, false);
                                } else {  // or, get selection option from list.
                                    expandedSelection = (Selection) repetitions.get(j);
                                    // call createSelection() to handle special case of selection submapping.
                                    createSelection(submap, expandedObject, expandedSelection);
                                }
                                // Add newly created object instance to subObject list.
                                if (subObjects == null)
                                    subObjects = new Object[repetitions.size()];
                                subObjects[j] = expandedObject;
                            } else {
                                throw new MappingException(errorIllegalTag(map.getTagName()));
                            }
                        }

                        // System.out.println("Finalized for loop for repetitions");

                        // We will have to adapt the parent object with the newly made additions in
                        // case of new object instances.
                        if ((repetitions.size() > 0)
                                && map.getTagName().equals("field")) { // Is there anything mapped to an object?
                            String type = "";

                            try {
                                // System.out.println("Try to determine type");
                                type = o.getClass().getField(map.getAttribute("name")).getType().getName();
                                // System.out.println("type: " + type);
                            } catch (NoSuchFieldException nsfe) {
                                throw new MappingException(errorFieldNotFound(map.getAttribute("name"), o));
                            }
                            if (type.startsWith("[L")) { // Array
                                // System.out.println("About to call setAttribute");
                                setAttribute(o, map.getAttribute("name"), subObjects);
                            } else {
                                if (repetitions.size() > 1)
                                    throw new MappingException(errorTooManyMsgInstances(type));
                                setAttribute(o, map.getAttribute("name"), subObjects[0]);
                            }
                        }
                    } else { // We have a simple mapping
                        //Util.debugLog("About to execute simple map");
                        executeSimpleMap(o, msg, map, outMessage, parmMessage);
                    }
                }
            }
            // System.out.println("About to call store method for object: " + o);
            // Finally, call store method for object.
            if ((o != null) && loadObject)
                callStoreMethod(o);
        } catch (BreakEvent be) {
            if (loadObject)
                callStoreMethod(o);
            throw be;
        } catch (Exception e) {
            if (loadObject)
                callKillMethod(o, 1);
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

        ref = getMessageObject(propertyName, parent, false, outputDoc);
        if (ref == null)
            ref = parent;
        if (ref == null)
            throw new MappingException("Could not find/create points property: " + propertyName);
        String realProperty = propertyName.substring(propertyName.lastIndexOf(Navajo.MESSAGE_SEPARATOR) + 1, propertyName.length());

        //Util.debugLog("in getPointsObject: propertyname: " + propertyName);
        //Util.debugLog("in getPointsObject: realProperty: " + realProperty);
        //Util.debugLog("in getPointsObject: ref: " + ref.getName() + "(" + ref + ")");
        if (propertyName.equals(""))
            return null;
        Property prop = ref.getProperty(realProperty);

        //Util.debugLog("in getSelectionObject: prop: " + prop);
        if (prop == null) {
            prop = Property.create(outputDoc, realProperty, Property.POINTS_PROPERTY, "", 0, "", Property.DIR_OUT);
            ref.addProperty(prop);
        }
        prop.setDirection(direction);
        prop.setDescription(description);
        if (!prop.getType().equals(Property.POINTS_PROPERTY))
            throw new MappingException("Not a points property: " + propertyName);
        Point point = new Point(prop);

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

        ref = getMessageObject(propertyName, parent, false, outputDoc);
        if (ref == null)
            ref = parent;
        if (ref == null)
            throw new MappingException("Could not find/create selection property: " + propertyName);
        String realProperty = propertyName.substring(propertyName.lastIndexOf(Navajo.MESSAGE_SEPARATOR) + 1, propertyName.length());

        //Util.debugLog("in getSelectionObject: propertyname: " + propertyName);
        //Util.debugLog("in getSelectionObject: realProperty: " + realProperty);
        //Util.debugLog("in getSelectionObject: ref: " + ref.getName() + "(" + ref + ")");
        if (propertyName.equals(""))
            return null;
        Property prop = ref.getProperty(realProperty);

        //Util.debugLog("in getSelectionObject: prop: " + prop);
        if (prop == null) {
            prop = Property.create(outputDoc, realProperty, cardinality, "Selection", Property.DIR_IN);
            ref.addProperty(prop);
        }
        // prop.setName(realProperty);
        prop.setCardinality(cardinality);
        prop.setDirection(direction);
        prop.setDescription(description);
        if (!prop.getType().equals(Property.SELECTION_PROPERTY))
            throw new MappingException("Not a selection property: " + propertyName);
        Selection sel = Selection.create(outputDoc, "UNKNOWN", "UNKOWN", false);

        prop.addSelection(sel);
        return sel;
    }

    public static Message getMessageObject(String name, Message parent, boolean messageOnly, Navajo source)
            throws NavajoException {
        Message msg = parent;

        if (name.startsWith(Navajo.MESSAGE_SEPARATOR)) {// We have an absolute message reference.
            msg = null;
            name = name.substring(1, name.length());
        }
        //Util.debugLog("in getMessageObject(): name=" + name + ", parent=" + msg);
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
              //System.out.println("In getMessageObject(), messageName = " + messageName);
              //Util.debugLog("Trying to find message name: " + messageName);
              if (msg == null)
                  newMsg = source.getMessage(messageName);
              else
                  newMsg = msg.getMessage(messageName);
              //Util.debugLog("newMsg: " + newMsg);
              if (newMsg == null) {
                  newMsg = Message.create(source, messageName);
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
        //Util.debugLog("in getMessageObject(): returning ref = " + newMsg);
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
        //Util.debugLog("in setPointsProperty(): name=" + name + ", value=" + value + ", description=" + description);;
        Message ref = getMessageObject(name, msg, false, outputDoc);

        if (ref == null)
            ref = msg;
        String actualName = getStrippedPropertyName(name);

        if (ref == null)
            throw new MappingException("Property can only be created under a message");
        Property prop = ref.getProperty(actualName);

        if (prop == null) { // Property does not exist.
            prop = Property.create(outputDoc, name, Property.POINTS_PROPERTY, "", 0, description, Property.DIR_OUT);
            prop.setPoints((Vector[]) value);
            ref.addProperty(prop);
        } else {
            prop.setType(Property.POINTS_PROPERTY);
            prop.setPoints((Vector[]) value);
            prop.setName(actualName);  // Should not matter ;)
        }
    }

    private void setProperty(boolean parameter, Message msg, String name, Object value, String type, String direction, String description,
            int length)
            throws NavajoException, MappingException {

        //System.out.println("in setProperty(): name=" + name + ", value=" + value + "(" + value.getClass().getName() + "), type=" + type + ", direction=" + direction + ", description=" + description + ", length=" + length);;

        if (parameter) {
            if (msg == null)
                msg = tmlDoc.getMessage("__parms__");
        }
        Message ref = getMessageObject(name, msg, false, outputDoc);

        String sValue = Util.toString(value, type);

        if (ref == null)
            ref = msg;
        String actualName = getStrippedPropertyName(name);

        if (ref == null)
            throw new MappingException("Property can only be created under a message");
        Property prop = ref.getProperty(actualName);

        if (prop == null) { // Property does not exist.
            if (!parameter)
                prop = Property.create(outputDoc, actualName, type, sValue, length, description,
                        direction);
            else
                prop = Property.create(tmlDoc, actualName, type, sValue, length, description,
                        direction);
            ref.addProperty(prop);
        } else {
            prop.setType(type);
            prop.setValue(sValue);
            prop.setName(actualName);  // Should not matter ;)
        }
    }

    private void executePointMap(Object o, TslNode map, Point point) throws MappingException {
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
                    //Util.debugLog("CONDITION = " + condition);
                    boolean eval = Condition.evaluate(condition, tmlDoc, o, null);

                    if (eval) {
                        //Util.debugLog("in executePointMap(): <expression name = " + childNode.getAttribute("name"));
                        operand = Expression.evaluate(childNode.getAttribute("name"), tmlDoc, o, null);
                        value = operand.value;
                        type = operand.type;
                        //Util.debugLog("in executeSelectionMap(): value = " + value + ", type = " + type);
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
                //Util.debugLog("in executeSelectionMap(): object to tml");
                point.setValue(value.toString());
            }
        } catch (Exception e) {
           logger.log(Priority.DEBUG, e.getMessage(), e);
        }
    }

    private void executeSelectionMap(Object o, TslNode map, Selection sel) throws MappingException {
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
                    //Util.debugLog("CONDITION = " + condition);
                    boolean eval = Condition.evaluate(condition, tmlDoc, o, null);

                    if (eval) {
                        //Util.debugLog("in executeSelectionMap(): <expression name = " + childNode.getAttribute("name"));
                        operand = Expression.evaluate(childNode.getAttribute("name"), tmlDoc, o, null, sel);
                        value = operand.value;
                        type = operand.type;
                        //Util.debugLog("in executeSelectionMap(): value = " + value + ", type = " + type);
                        i = allNodes.size() + 1; // Jump out of for loop.
                    } else {
                        childNode = null;
                    }
                }
            } catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
                throw new MappingException(errorExpression(tmle.getMessage(), condition));
            }
            //Util.debugLog("in executeSelectionMap(): Mappable: " + o.getClass().getName());
            //Util.debugLog("in executeSelectionMap(): map: " + map.getTagName());
            //Util.debugLog("in executeSelectionMap(): childNode: " + childNode.getTagName());
            //Util.debugLog("in executeSelectionMap(): property: " + map.getAttribute("name") + " selection: " + sel.getName());
            if (map.getTagName().equals("field")) { // tml-to-object
                setSimpleAttribute(o, map.getAttribute("name"), value, type);
            } else {
                //Util.debugLog("in executeSelectionMap(): object to tml");
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
            //Util.debugLog("msg.getProperty(" + propName + ")");
        } else {
            prop = doc.getProperty(propName);
            //Util.debugLog("doc.getProperty(" + propName + ")");
        }
        //if (prop == null)
            //Util.debugLog("!!!ERROR prop == NULL!!!");
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
    public static Object getAttributeValue(Object o, String name, Object[] arguments) throws com.dexels.navajo.server.UserException,
            MappingException {
        //Util.debugLog("getAttributeValue(): o = " + o + ", name = " + name);
        Object result = null;
        StringTokenizer tokens = new StringTokenizer(name, ".");
        int count = tokens.countTokens();

        // ////System.out.println("count = " + count);
        if (count == 1) {
            result = getAttributeObject(o, name, arguments);
        } else {
            String object = tokens.nextToken();

            //Util.debugLog("Got object field: " + object);
            Object dum = getAttributeObject(o, object, null);
            String remaining = name.substring(object.length() + 1, name.length());

            //Util.debugLog("Trying with remaining argument = " + remaining + ", on object = " + dum);
            return getAttributeValue(dum, remaining, arguments);
        }
        if (result != null) {
            //Util.debugLog("!!!!!!!!!! result: " + result.toString());
            String type = result.getClass().getName();

            //Util.debugLog("!!!!!!!!!! type: " + type);
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
            //Util.debugLog("Found null value for object attribute");
            return null;
        }
    }

    private static Object getAttributeObject(Object o, String name, Object[] arguments) throws com.dexels.navajo.server.UserException, MappingException {
        Object result = null;
        String methodName = "";

        try {
            methodName = "get" + (name.charAt(0) + "").toUpperCase()
                    + name.substring(1, name.length());
            //Util.debugLog("in getAttributeObject: methodName " + methodName);
            Class c = o.getClass();
            java.lang.reflect.Method m = null;

            if (arguments == null) {
                m = c.getMethod(methodName, null);
                result = m.invoke(o, null);
            } else {
                // Invoke method with arguments.
                Class[] classArray = new Class[arguments.length];
                for (int i = 0; i < arguments.length; i++) {
                    classArray[i] = arguments[i].getClass();
                }
                m = c.getMethod(methodName, classArray);
                result = m.invoke(o, arguments);
            }
        } catch (NoSuchMethodException nsme) {
            throw new MappingException("Method not found: " + methodName + " in object: " + o);
        } catch (IllegalAccessException iae) {
            throw new MappingException(methodName + " illegally accessed in mappable class: " + o.getClass().getName());
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
            //Util.debugLog(Array.get(dest, i) + "");
        }
    }

    /**
     * This method is used for setting instances of mappable object fields.
     */
    private void setAttribute(Object o, String name, Object arg) throws com.dexels.navajo.server.UserException, MappingException {
        setAttribute(o, name, arg, null);
    }

    private void setAttribute(Object o, String name, Object arg, Class type) throws com.dexels.navajo.server.UserException, MappingException {
        //System.out.println("in setAttribute(), o = " + o + ", name = " + name + ", arg = " + arg + ", type = " + type);

        String methodName = "set" + (name.charAt(0) + "").toUpperCase()
                + name.substring(1, name.length());
        Class c = o.getClass();
        Class[] parameters = null;
        Object[] arguments = null;

        // System.out.println("arg.getClass() = " + ((arg != null) ? arg.getClass().toString() : "null"));
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
            // System.out.println("methodName = " + methodName + ", Checking methodname = " + all[i].getName());
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
            m.invoke(o, arguments);
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

    private void setSimpleAttribute(Object o, String name, Object value, String propertyType) throws com.dexels.navajo.server.UserException, MappingException,
            java.lang.NumberFormatException {
        String type = "";

        type = getFieldType(o, name);

        //System.out.println("in setSimpleAttribute(), name = " + name + ", value = " + value + ", type = " + type + ", propertyType = " + propertyType);

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
            //Util.debugLog("UNKNOWN attribute type: " + type);
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
    private void executeSimpleMap(Object o, Message msg, TslNode map, Message outMessage, Message parmMessage)
            throws MappingException, NavajoException, com.dexels.navajo.server.UserException,
            java.lang.NumberFormatException, SystemException {
        Object value = null;
        String type = "";
        Operand operand = null;
        TslNode childNode = null;
        String condition = map.getAttribute("condition");
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

        if (allNodes.size() == 0)
          throw new MappingException("<expression> tag(s) expected");
        try {
            for (int i = 0; i < allNodes.size(); i++) {

                childNode = (TslNode) allNodes.get(i);
                condition = childNode.getAttribute("condition");
                //Util.debugLog("CONDITION = " + condition);
                eval = Condition.evaluate(condition, tmlDoc, o, msg);
                if (eval) {
                    // Check for match attribute. If match attribute present use message that matches.
                    // Syntax: match="[property regular expression];[expression]". The message of the property that matches the value of the expression
                    // is used as reference for the expression in name="".
                    if (!childNode.getAttribute("match").equals("")) {
                        Message referenceMsg = Expression.match(childNode.getAttribute("match"), tmlDoc, o, msg);

                        if (referenceMsg == null)
                            throw new MappingException("No matching message found. " + showNodeInfo(childNode));
                        operand = Expression.evaluate(childNode.getAttribute("name"), tmlDoc, o, referenceMsg);
                    } else {
                        operand = Expression.evaluate(childNode.getAttribute("name"), tmlDoc, o, msg);
                    }
                    value = operand.value;
                    if (value == null)
                        value = new String("");
                    type = operand.type;
                    //Util.debugLog("in executeSimpleMap(): value = " + value + ", type = " + type);
                    i = allNodes.size() + 1; // Jump out of for loop.
                } else {
                    childNode = null;
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
            // //System.out.println("executeSimpleMap(): attribute="+map.getAttribute("name") +", property="+childNode.getAttribute("name") + "selection: "+ map.getAttribute("selection").equals("true"));
            setSimpleAttribute(o, map.getAttribute("name"), operand.value, type);
        } else if (map.getTagName().equals("property")
                || map.getTagName().equals("param")) {  // Object to TML. we zitten in een <property> tag
            // Check if description is an object attribute.
            // String description = map.getAttribute("description"); //Expression.evaluate(map.getAttribute("description"), tmlDoc, o).value;
            String description = "";

            description = map.getAttribute("description");
            //Util.debugLog(this.getClass() + "executeSimpleMap(): " + " description = '" + description + "'");

            // Check if property name is an object attribute.
            // String propertyName = map.getAttribute("name"); //Expression.evaluate(map.getAttribute("name"), tmlDoc, o).value;
            String propertyName = "";

            try {
                propertyName = map.getAttribute("name");// MAYBE IN THE FUTURE: Expression.evaluate(map.getAttribute("name"), tmlDoc, o, msg).value;
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            // Set new property value.s      setProperty(msg, propertyName, value, type, map.getAttribute("direction"), description, Integer.parseInt(map.getAttribute("length")));
            String length = map.getAttribute("length");
            String v = map.getAttribute("value");

            if (value != null) {
                if (value.equals(""))
                    value = v;
                if (type.equals("")) // If the operand does not define a type, use the type as specified by the "type" attribute of the property or param.
                    type = map.getAttribute("type");
                if (map.getTagName().equals("param")) // We have a parameter property.
                    outMessage = parmMessage;
                // //System.out.println("Before setProperty(), type = " + type + ", value = " + value);
                setProperty(map.getTagName().equals("param"), outMessage, propertyName, value, type, map.getAttribute("direction"), description,
                        (!length.equals("")) ? Integer.parseInt(length) : 0);
            } else {  // We have an object value (points property!)
                setPointsProperty(outMessage, propertyName, operand.value, description);
            }
        } else if (map.getTagName().equals("message")) {
            throw new MappingException("Did not expect <message> tag at this point");
        }
    }

    private void createPoint(TslNode root, Object o, Point point) throws Exception {
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

    private void createSelection(TslNode root, Object o, Selection selection) throws Exception {
        callLoadMethod(o);
        for (int i = 0; i < root.getNodesSize(); i++) {
            TslNode map = root.getNode(i);

            currentNode = map;
            if (map.getNodeByType("map") != null)
                throw new MappingException("No submappings allowed here");
            try {
                executeSelectionMap(o, map, selection);
            } catch (Exception e) {
                callKillMethod(o, 4);
                throw e;
            }
        }
        callStoreMethod(o);
    }

    private void doMapping(Navajo doc, TslNode node, Message absoluteParent, Message outMessage, Message parmMessage, Object context) throws
            Exception, BreakEvent {
        Mappable o = getMappable(node.getAttribute("object"), "");

        //Util.debugLog("Got mappable object = " + o);
        createMapping(node, absoluteParent, o, outMessage, parmMessage, true, false);
    }

    private void addAntiMessage(Navajo doc, Message parent, String message) throws NavajoException {
        Message msg = AntiMessage.create(doc, message);

        if (parent == null)
            doc.addMessage(msg);
        else
            parent.addMessage(msg);
    }

    private Message[] addMessage(Navajo doc, Message parent, String message, String template, int count)
            throws java.io.IOException, NavajoException, org.xml.sax.SAXException, MappingException {
        // ArrayList messages = new ArrayList();
        //Util.debugLog("in addMessage(): about to add message " + message);
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
            msg = Message.create(doc, message);
        }
        if (count > 1) {
            msg.setName(message + "0");
            msg.setIndex(0);
            if (parent == null)
                msg = doc.addMessage(msg, false);
            else
                msg = parent.addMessage(msg, false);
            // messages.add(msg);
            messages[index++] = msg;
        } else if (count == 1) {
            if (parent == null)
                msg = doc.addMessage(msg, false);
            else
                msg = parent.addMessage(msg, false);
            // messages.add(msg);
            messages[index++] = msg;
        }
        for (int i = 1; i < count; i++) {
            Message extra = doc.copyMessage(msg, doc);

            extra.setName(message + i);
            extra.setIndex(i);
            if (parent == null)
                extra = doc.addMessage(extra, false);
            else
                extra = parent.addMessage(extra, false);
            // messages.add(extra);
            messages[index++] = extra;
        }
        // return msg;
        return messages;
    }

    private Navajo createTML(TslNode node) throws java.io.IOException,
            org.xml.sax.SAXException, MappingException, NavajoException {
        Navajo doc = null;
        String fileName = node.getAttribute("service");

        //Util.debugLog("filename: " + fileName);
        doc = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + fileName + ".tml");
        return doc;
    }

    /**
     * interpretAddBody() parses and executed ADD constructs. If an ADD constructs contains
     * a MAP construct, doMapping() is called to handle the MAP construct.
     */
    private void interpretAddBody(Message parent, Navajo doc, TslNode node, Object o, Message parentInMessage, Message parmMessage, boolean parameter)
            throws Exception, BreakEvent {
        TslNode addNode = node;

        //Util.debugLog("in interpetAddBody(): node = " + node.getTagName());
        String command = addNode.getTagName();
        String message = addNode.getAttribute("name");
        String template = addNode.getAttribute("template");
        String condition = addNode.getAttribute("condition");

        //Util.debugLog("in interpetAddBody(): command: " + command + " message: " + message + " template: " + template + " conditie: " + condition);
        boolean eval = false;

        try {
            //Util.debugLog("!!! DEBUG in interpretAddBody: evaluate condition: " + condition);
            eval = Condition.evaluate(condition, tmlDoc, o, parentInMessage);
            //Util.debugLog("!!! DEBUG in interpretAddBody: evaluation = " + eval);
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
                op = Expression.evaluate(sCount, tmlDoc, o, parentInMessage);
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
                messages = addMessage(tmlDoc, parmMessage, message, template, count);
            else
                messages = addMessage(doc, parent, message, template, count);
            for (int nrMesg = 0; nrMesg < messages.length; nrMesg++) {
                Message newParent = messages[nrMesg];
                TslNode childNode;

                if (parameter) {
                    createMapping(node, parentInMessage, o, parent, newParent, false, false);
                } else {
                    createMapping(node, parentInMessage, o, newParent, parmMessage, false, false);
                }
            }
        } else {
            // Add anti-message if condition was not true.
            // Client ignore antiMessage or use it to invalidate and remove previous versions of
            // the message.
            //Util.debugLog("!!!! debug in interpretAddBody: posting addAntiMessage");
            addAntiMessage(doc, parent, message);
        }
    }

    private void processBreak(TslNode tsl, Object o, Message msg) throws BreakEvent, MappingException, SystemException {
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
            // String line = "";
            // Default behavior: create output TML document using the service name as base.
            // This can be overridden using the "CREATETML" statement in the user script.
            // ////System.out.println("Starting Interpreter");
            // long start = System.currentTimeMillis();
            requestCount++;
            //Util.debugLog("interpret version 10.0 (): reading output file: " + tmlPath + "/" + service + ".tml");
            if (access.betaUser) {
                try {
                    outputDoc = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + service + ".tml_beta");
                } catch (Exception e) {// //System.out.println("Could not find beta version of tml file");
                }
            }
            if (outputDoc == null)
                outputDoc = com.dexels.navajo.util.Util.readNavajoFile(tmlPath + "/" + service + ".tml");
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
                }
            }
            // long end = System.currentTimeMillis();
            // double total = (end - start) / 1000.0;
            // totaltiming += total;
            // ////System.out.println("finished interpreter in " + total + " seconds. Average intepreter time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
            return outputDoc;
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
