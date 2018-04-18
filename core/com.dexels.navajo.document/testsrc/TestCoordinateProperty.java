import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dexels.navajo.document.types.Coordinate;

public class TestCoordinateProperty {

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
            Coordinate c = new Coordinate("-1 3");
            assertEquals(c.toString(), "[-1.0,3.0]");
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testinValidPropertyCreation() {
        try {
            Coordinate c = new Coordinate("[-1s,3]");
            assertTrue(false);
        } catch (Exception e) {
            System.out.println(e);
            assertTrue(true);
        }

        try {
            Coordinate c = new Coordinate("-1s,/3");
            assertTrue(false);
        } catch (Exception e) {
            System.out.println(e);
            assertTrue(true);
        }

        try {
            Coordinate c = new Coordinate("-1000,3");
            assertTrue(false);
        } catch (Exception e) {
            System.out.println(e);
            assertTrue(true);
        }

        try {
            Coordinate c = new Coordinate("-12.89", -800);
            assertTrue(false);
        } catch (Exception e) {
            System.out.println(e);
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
            assertTrue(false);
        } catch (Exception e) {
            System.out.println(e);
            assertTrue(true);
        }

    }

}