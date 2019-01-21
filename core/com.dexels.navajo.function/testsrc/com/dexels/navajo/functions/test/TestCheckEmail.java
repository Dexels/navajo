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
    public void testValidEmail1() throws Exception {
        checker.insertStringOperand("test@example.com");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    
    @Test
    public void testValidEmail2() throws Exception {
        checker.insertStringOperand("test123@example.com");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    
    @Test
    public void testValidEmail3() throws Exception {
        checker.insertStringOperand("test.person@example.com");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    
    @Test
    public void testValidEmail4() throws Exception {
        checker.insertStringOperand("test01@sub.example.com");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    
    @Test
    public void testValidEmail5() throws Exception {
        checker.insertStringOperand("123aaa@dexels.com");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    
    @Test
    public void testValidEmail6() throws Exception {
        checker.insertStringOperand("stefan@awesomo.amsterdam");
        Object isValid = checker.evaluate();
        assertEquals(true, isValid);
    }
    
    @Test
    public void testInvalidEmail1() throws Exception {
        checker.insertStringOperand("test_dexels.com");
        Object isValid = checker.evaluate();
        assertEquals(false, isValid);
    }
    
    @Test
    public void testInvalidEmail2() throws Exception {
        checker.insertStringOperand("test.@dexels.com");
        Object isValid = checker.evaluate();
        assertEquals(false, isValid);
    }
}
