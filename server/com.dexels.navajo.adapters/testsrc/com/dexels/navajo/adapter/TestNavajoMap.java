package com.dexels.navajo.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;

import static com.dexels.navajo.document.Property.INTEGER_PROPERTY;;

public class TestNavajoMap {

    private NavajoMap map;

    private Navajo outDoc;

    @Before
    public void setup() {

        map = new NavajoMap();
        outDoc = NavajoFactory.getInstance().createNavajo();
        map.setOutDoc(outDoc);
    }

    @After
    public void destroy() {

        map = null;
        outDoc = null;
    }

    @Test
    public void testCheckSubArray() throws UserException {

        String testCase = "/parentMessage/ArrayMessage@0/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("parentMessage")
                .getMessage("ArrayMessage").getMessage(0).getProperty("newProperty").getName());
    }

    @Test
    public void testCheckSubArrayWithChildren() throws UserException {

        String testCase = "/parentMessage/ArrayMessage@0/arrayMessageChild/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("parentMessage")
                .getMessage("ArrayMessage").getMessage(0).getMessage("arrayMessageChild")
                .getProperty("newProperty").getName());
    }

    @Test
    public void testTopLevelArray() throws UserException {

        String testCase = "/ArrayMessage@0/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("ArrayMessage").getMessage(0)
                .getProperty("newProperty").getName());
    }

    @Test
    public void testTopLevelArrayWithMultipleChildrenMessages() throws UserException {

        String testCase = "/ArrayMessage@0/arrayMessageChild/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("ArrayMessage").getMessage(0)
                .getMessage("arrayMessageChild").getProperty("newProperty").getName());
    }

    @Test
    public void testNestedLevelArrays() throws UserException {

        String testCase = "/ArrayMessageParrent@0/ArrayMessageChild@0/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("ArrayMessageParrent").getMessage(0)
                .getMessage("ArrayMessageChild").getMessage(0).getProperty("newProperty").getName());
    }

    @Test
    public void testSimplePropertyMessage() throws UserException {

        String testCase = "/SimpleMessage/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("SimpleMessage")
                .getProperty("newProperty").getName());
    }

    @Test
    public void testSimplePropertyNestedMessage() throws UserException {

        String testCase = "/SimpleMessageParent/SimpleMessageChild/newProperty";
        map.setPropertyName(testCase);
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(3);

        assertEquals("newProperty", map.outDoc.getMessage("SimpleMessageParent")
                .getMessage("SimpleMessageChild").getProperty("newProperty").getName());
    }

    @Test
    public void testCopyInputMessage() throws MappableException, UserException {

        final String TEST_INTEGER_1 = "6";
        final String TEST_INTEGER_2 = "3";

        // Create a message on the in doc with a test value.
        Navajo inDoc = NavajoFactory.getInstance().createNavajo();
        Property property = NavajoFactory.getInstance().createProperty(outDoc, "newProperty",
                INTEGER_PROPERTY, TEST_INTEGER_1, 0, "", "out");
        Message message = NavajoFactory.getInstance().createMessage(outDoc, "SimpleMessage");
        message.addProperty(property);
        inDoc.addMessage(message);

        // Initialize the navajomap.
        DispatcherFactory.createDispatcher(new Dispatcher());
        Access access = new Access();
        access.setInDoc(inDoc);
        access.setOutputDoc(outDoc);
        map.load(access);

        // Create a property on the navajomap with a different value that could be overwritten with the test value.
        map.setPropertyName("/SimpleMessage/newProperty");
        map.setPropertyType(INTEGER_PROPERTY);
        map.setProperty(TEST_INTEGER_2);

        // Test the method while copying the input message.
        map.setCopyInputMessages("SimpleMessage");
        map.prepareOutDoc();

        // Ensure that the property created on the navajomap is not overwritten.
        assertEquals(TEST_INTEGER_2, map.outDoc.getMessage("SimpleMessage")
                .getProperty("newProperty").getValue());
    }

    @Test
    public void testSendThroughNotSet() throws MappableException, UserException {

        Navajo inDoc = NavajoFactory.getInstance().createNavajo();

        Property property = NavajoFactory.getInstance().createProperty(inDoc, "Meaning",
                INTEGER_PROPERTY, "42", 0, "", "out");
        Message message = NavajoFactory.getInstance().createMessage(inDoc, "SendThrough");
        message.addProperty(property);
        inDoc.addMessage(message);

        Message global = NavajoFactory.getInstance().createMessage(inDoc, "Global");
        global.setScope(Message.MSG_SCOPE_GLOBAL);
        inDoc.addMessage(global);

        Message local = NavajoFactory.getInstance().createMessage(inDoc, "Local");
        local.setScope(Message.MSG_SCOPE_LOCAL);
        inDoc.addMessage(local);

        // Initialize the navajomap.
        DispatcherFactory.createDispatcher(new Dispatcher());
        Access access = new Access();
        access.setInDoc(inDoc);
        access.setOutputDoc(outDoc);
        map.load(access);

        map.prepareOutDoc();

        assertFalse(map.getSendThrough());
        assertNull(map.outDoc.getMessage("SendThrough"));
        assertEquals(Message.MSG_SCOPE_GLOBAL, map.outDoc.getMessage("Global").getScope());
        assertNull(map.outDoc.getMessage("Local"));
    }

    @Test
    public void testSendThroughSetFalse() throws MappableException, UserException {

        Navajo inDoc = NavajoFactory.getInstance().createNavajo();

        Property property = NavajoFactory.getInstance().createProperty(inDoc, "Meaning",
                INTEGER_PROPERTY, "42", 0, "", "out");
        Message message = NavajoFactory.getInstance().createMessage(inDoc, "SendThrough");
        message.addProperty(property);
        inDoc.addMessage(message);

        Message global = NavajoFactory.getInstance().createMessage(inDoc, "Global");
        global.setScope(Message.MSG_SCOPE_GLOBAL);
        inDoc.addMessage(global);

        Message local = NavajoFactory.getInstance().createMessage(inDoc, "Local");
        local.setScope(Message.MSG_SCOPE_LOCAL);
        inDoc.addMessage(local);

        // Initialize the navajomap.
        DispatcherFactory.createDispatcher(new Dispatcher());
        Access access = new Access();
        access.setInDoc(inDoc);
        access.setOutputDoc(outDoc);
        map.load(access);

        map.setSendThrough(false);
        map.prepareOutDoc();

        assertFalse(map.getSendThrough());
        assertNull(map.outDoc.getMessage("SendThrough"));
        assertEquals(Message.MSG_SCOPE_GLOBAL, map.outDoc.getMessage("Global").getScope());
        assertNull(map.outDoc.getMessage("Local"));
    }

    @Test
    public void testSendThroughSetTrue() throws MappableException, UserException {

        Navajo inDoc = NavajoFactory.getInstance().createNavajo();

        Property property = NavajoFactory.getInstance().createProperty(inDoc, "Meaning",
                INTEGER_PROPERTY, "42", 0, "", "out");
        Message message = NavajoFactory.getInstance().createMessage(inDoc, "SendThrough");
        message.addProperty(property);
        inDoc.addMessage(message);

        Message global = NavajoFactory.getInstance().createMessage(inDoc, "Global");
        global.setScope(Message.MSG_SCOPE_GLOBAL);
        inDoc.addMessage(global);

        Message local = NavajoFactory.getInstance().createMessage(inDoc, "Local");
        local.setScope(Message.MSG_SCOPE_LOCAL);
        inDoc.addMessage(local);

        // Initialize the navajomap.
        DispatcherFactory.createDispatcher(new Dispatcher());
        Access access = new Access();
        access.setInDoc(inDoc);
        access.setOutputDoc(outDoc);
        map.load(access);

        map.setSendThrough(true);
        map.prepareOutDoc();

        assertTrue(map.getSendThrough());
        assertEquals("42", map.outDoc.getMessage("SendThrough").getProperty("Meaning").getValue());
        assertEquals(Message.MSG_SCOPE_GLOBAL, map.outDoc.getMessage("Global").getScope());
        assertNull(map.outDoc.getMessage("Local"));
    }

}
