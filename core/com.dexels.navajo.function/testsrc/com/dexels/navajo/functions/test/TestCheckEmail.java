package com.dexels.navajo.functions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.functions.CheckEmail;

public class TestCheckEmail {
    CheckEmail checker = null;
    
    @Before
    public void setupFunction() {
        checker = new CheckEmail();
        checker.reset();
    }
    
    @Test
    public void testEmail1() throws Exception {
        checker.insertOperand("erik.versteeg@dexels.com");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    


}
