/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Coordinate;

public class TestCoordinateProperty {

	
	private static final Logger logger = LoggerFactory.getLogger(TestCoordinateProperty.class);

    @Test
    public void testValidPropertyCreation() {
        try {
            Coordinate c = new Coordinate(-1, 3);
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate("-1", 3);
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate("-1", Integer.parseInt("3"));
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate(-1.2, 3.4);
            assertEquals(c.toString(), "[-1.2,3.4]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate("-1,3");
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate("[-1,3]");
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate("[-1, 3]");
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            Coordinate c = new Coordinate("-1 3");
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testinValidPropertyCreation() {
        try {
            new Coordinate("[-1s,3]");
        } catch (Exception e) {
            logger.info("Error",e);
            assertTrue(true);
        }

        try {
            new Coordinate("-1s,/3");
            fail();
        } catch (Exception e) {
            logger.info("Error",e);
            assertTrue(true);
        }

        try {
            new Coordinate("-1000,3");
            fail();
        } catch (Exception e) {
            logger.info("Error",e);
            assertTrue(true);
        }

        try {
            new Coordinate("-12.89", -800);
            fail();
        } catch (Exception e) {
            logger.info("Error",e);
            assertTrue(true);
        }
    }

    @Test
    public void testPropertyMethods() throws Exception {
        Coordinate c = new Coordinate(-1, 3);
        assertEquals(c.toString(), "[-1.0,3.0]");
        assertEquals(c.isEmpty(), false);

        c.setLatitude("32");
        assertEquals(c.toString(), "[-1.0,32.0]");

        c.setLongitude(-11.455555);
        assertEquals(c.toString(), "[-11.455555,32.0]");

        try {
            c.setLatitude(null);
        } catch (NumberFormatException e) {
            assertTrue(true);
        }

        try {
            c.setLatitude(-900);
            fail();
        } catch (Exception e) {
            logger.info("Error",e);
        }

    }

}