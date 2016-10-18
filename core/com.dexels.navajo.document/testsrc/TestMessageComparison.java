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
        n1 = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("messages1.xml"));
        n2 = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("messages2.xml"));

    }

    @Test
    public void testEquals() {
        assertTrue(n1.getMessage("TestMessage1").messageEquals(n2.getMessage("TestMessage1")));
        assertTrue(n1.getMessage("TestMessage3").messageEquals(n2.getMessage("TestMessage3")));
        assertTrue(n1.getMessage("TestMessage5").messageEquals(n2.getMessage("TestMessage5")));

    }

    @Test
    public void testNotEquals() {
        assertFalse(n1.getMessage("TestMessage2").messageEquals(n2.getMessage("TestMessage2")));
        assertFalse(n1.getMessage("TestMessage4").messageEquals(n2.getMessage("TestMessage4")));
        assertFalse(n1.getMessage("TestMessage6").messageEquals(n2.getMessage("TestMessage6")));
    }

    @Test
    public void testArray() {
        assertTrue(n1.getMessage("TestMessage7").messageEquals(n2.getMessage("TestMessage7")));
        assertTrue(n1.getMessage("TestMessage8").messageEquals(n2.getMessage("TestMessage8")));

        assertFalse(n1.getMessage("TestMessage9").messageEquals(n2.getMessage("TestMessage9")));
        assertFalse(n1.getMessage("TestMessage10").messageEquals(n2.getMessage("TestMessage10")));

    }

}