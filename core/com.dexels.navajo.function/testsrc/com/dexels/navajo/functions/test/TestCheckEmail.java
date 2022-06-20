/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
