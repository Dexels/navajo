package com.dexels.navajo.document.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestMessageComparison {
    Navajo n1;
    Navajo n2;

    @Before
    public void setup() {
        n1 = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("messages1.xml"));
        n2 = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("messages2.xml"));

    }

    @Test
    public void testEquals() {
        assertTrue(n1.getMessage("TestMessage1").isEqual(n2.getMessage("TestMessage1")));
        assertTrue(n1.getMessage("TestMessage3").isEqual(n2.getMessage("TestMessage3")));
        assertTrue(n1.getMessage("TestMessage5").isEqual(n2.getMessage("TestMessage5")));

    }

    @Test
    public void testNotEquals() {
        assertFalse(n1.getMessage("TestMessage2").isEqual(n2.getMessage("TestMessage2")));
        assertFalse(n1.getMessage("TestMessage4").isEqual(n2.getMessage("TestMessage4")));
        assertFalse(n1.getMessage("TestMessage6").isEqual(n2.getMessage("TestMessage6")));
    }

    @Test
    public void testArray() {
        assertTrue(n1.getMessage("TestMessage7").isEqual(n2.getMessage("TestMessage7")));
        assertTrue(n1.getMessage("TestMessage8").isEqual(n2.getMessage("TestMessage8")));

        assertFalse(n1.getMessage("TestMessage9").isEqual(n2.getMessage("TestMessage9")));
        assertFalse(n1.getMessage("TestMessage10").isEqual(n2.getMessage("TestMessage10")));

    }

}