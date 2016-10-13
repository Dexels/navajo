import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestMessageComparison {
    Navajo n;
    
    @Before
    public void setup() {
        n = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("messages.xml"));

    }
    
    @Test
    public void testEquals() {
        assertTrue(n.getMessage("TestMessage1").messageEquals(n.getMessage("TestMessage2")));
        assertTrue(n.getMessage("TestMessage3").messageEquals(n.getMessage("TestMessage4")));
        assertTrue(n.getMessage("TestMessage5").messageEquals(n.getMessage("TestMessage6")));

    }
    
    @Test
    public void testNotEquals() {
        assertFalse(n.getMessage("TestMessage1").messageEquals(n.getMessage("TestMessage3")));
        assertFalse(n.getMessage("TestMessage3").messageEquals(n.getMessage("TestMessage2")));
        assertFalse(n.getMessage("TestMessage5").messageEquals(n.getMessage("TestMessage3")));

        assertFalse(n.getMessage("TestMessage1").messageEquals(n.getMessage("NTestMessage1")));
        assertFalse(n.getMessage("TestMessage1").messageEquals(n.getMessage("NTestMessage2")));



    }
   
}