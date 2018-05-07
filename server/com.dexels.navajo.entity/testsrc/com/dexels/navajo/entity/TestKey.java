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
        System.out.println("12$:: " + "12$".matches(myRegex));
        System.out.println("12e:: " + "12e".matches(myRegex));
        System.out.println("e12:: " + "e12".matches(myRegex));
        System.out.println("112_32.test:: " + "112_32.test".matches(myRegex));

        System.out.println("1@32:: " + "1@32".matches(myRegex));
        System.out.println("1%^#:: " + "1%^#".matches(myRegex));
        System.out.println("e12_.~:: " + "e12_.~".matches(myRegex));

    }
}
