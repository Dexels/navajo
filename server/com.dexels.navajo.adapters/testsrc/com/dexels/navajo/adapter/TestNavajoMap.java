package com.dexels.navajo.adapter;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.UserException;

public class TestNavajoMap {
    private NavajoMap map;
    private Navajo n;

    @Before
    public void setup() {
        map = new NavajoMap();
        n = NavajoFactory.getInstance().createNavajo();
        map.setOutDoc(n);

    }

    @After
    public void destroy() {
        map = null;
    }

    @Test
    public void testCheckSubArray() throws UserException {
        String testCase = "/parentMessage/ArrayMessage@0/newProperty";
        map.setOutDoc(n);
        map.setPropertyName(testCase);
        map.setPropertyType("integer");
        map.setProperty(3);

        // System.out.println(map.outDoc);
        if (map.outDoc.getMessage("parentMessage").getMessage("ArrayMessage").getMessage(0).getProperty("newProperty").getName()
                .equals("newProperty")) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testCheckSubArrayWithChildren() throws UserException {
        String testCase = "/parentMessage/ArrayMessage@0/arrayMessageChild/newProperty";
        map.setOutDoc(n);
        map.setPropertyName(testCase);
        map.setPropertyType("integer");
        map.setProperty(3);

        // System.out.println(map.outDoc);
        if (map.outDoc.getMessage("parentMessage").getMessage("ArrayMessage").getMessage(0).getMessage("arrayMessageChild")
                .getProperty("newProperty").getName().equals("newProperty")) {
            assertTrue(true);
        }
    }

    @Test
    public void testTopLevelArray() throws UserException {
        String testCase = "/ArrayMessage@0/newProperty";
        map.setOutDoc(n);
        map.setPropertyName(testCase);
        map.setPropertyType("integer");
        map.setProperty(3);

        // System.out.println(map.outDoc);
        if (map.outDoc.getMessage("ArrayMessage").getMessage(0).getProperty("newProperty").getName()
                .equals("newProperty")) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testTopLevelArrayWithMultipleChildrenMessages() throws UserException {
        String testCase = "/ArrayMessage@0/arrayMessageChild/newProperty";
        map.setOutDoc(n);
        map.setPropertyName(testCase);
        map.setPropertyType("integer");
        map.setProperty(3);

        if (map.outDoc.getMessage("ArrayMessage").getMessage(0).getMessage("arrayMessageChild").getProperty("newProperty").getName()
                .equals("newProperty")) {
            assertTrue(true);
        }
    }

    @Test
    public void testNestedLevelArrays() throws UserException {
        String testCase = "/ArrayMessageParrent@0/ArrayMessageChild@0/newProperty";
        map.setOutDoc(n);
        map.setPropertyName(testCase);
        map.setPropertyType("integer");
        map.setProperty(3);

        if (map.outDoc.getMessage("ArrayMessageParrent").getMessage(0).getMessage("ArrayMessageChild").getMessage(0)
                .getProperty("newProperty").getName().equals("newProperty")) {
            assertTrue(true);
        }
    }

}
