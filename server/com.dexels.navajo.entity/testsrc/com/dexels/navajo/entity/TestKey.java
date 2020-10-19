package com.dexels.navajo.entity;

import org.junit.Assert;
import org.junit.Test;

public class TestKey {

    @Test
    public void testGetKeyId() {

        String id = Key.getKeyId("auto,id=noot,optional");
        Assert.assertEquals("noot", id);
    }

    @Test
    public void testGetKeyId2() {

        String id = Key.getKeyId("auto,optional");
        Assert.assertNull(id);
    }

    @Test
    public void testGetKeyId3() {

        String id = Key.getKeyId("");
        Assert.assertNull(id);
    }

    @Test
    public void testGetKeyId4() {

        String id = Key.getKeyId("id=noot");
        Assert.assertEquals("noot", id);
    }

    @Test
    public void testVersionName() {

        String myRegex = "^[\\$a-zA-Z0-9._-]*$";

        Assert.assertTrue("12$".matches(myRegex));
        Assert.assertTrue("12e".matches(myRegex));
        Assert.assertTrue("e12".matches(myRegex));
        Assert.assertTrue("112_32.test".matches(myRegex));

        Assert.assertFalse("1@32".matches(myRegex));
        Assert.assertFalse("1%^#".matches(myRegex));
        Assert.assertFalse("e12_.~".matches(myRegex));
    }

}
