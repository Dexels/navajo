import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.document.types.Binary;

public class TestTMLJson {
    
    @Test
    public void TestJson() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("message.xml"));
        JSONTML json = JSONTMLFactory.getInstance();
        
        Writer sw = new StringWriter();
        json.format(n,  sw, true);
        
        String result = sw.toString();
        System.out.println(result);

        // Length should be... 64! right?
        Assert.assertEquals(64, result.length());
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        Assert.assertTrue(n.getMessage("SimpleMessage").messageEquals(n2.getMessage("SimpleMessage")));
    }
    
    @Test
    public void testDates() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("message.xml"));
        JSONTML json = JSONTMLFactory.getInstance();
        
        // Add date property
        Property dateprop = NavajoFactory.getInstance().createProperty(n,  "date", "", "", "");
        dateprop.setAnyValue(new Date(1477393027000L));
        n.getMessage("SimpleMessage").addProperty(dateprop);
        
        // Add date property
        Property tsprop = NavajoFactory.getInstance().createProperty(n,  "timestamp", "", "", "");
        tsprop.setType(Property.TIMESTAMP_PROPERTY);
        tsprop.setAnyValue(new Date(1477393027000L));
        n.getMessage("SimpleMessage").addProperty(tsprop);
        
        Writer sw = new StringWriter();
        json.format(n,  sw, true);
        
        String result = sw.toString();
        System.out.println(result);

        //Length should be 133... Right?
        Assert.assertEquals(133, result.length());
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        
        Assert.assertEquals("2016-10-25", n2.getMessage("SimpleMessage").getProperty("date").getValue());
        Assert.assertEquals("2016-10-25T12:57:07+0200", n2.getMessage("SimpleMessage").getProperty("timestamp").getValue());

    }
    
    @Test
    public void TestJsonBinary() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("message.xml"));
        JSONTML json = JSONTMLFactory.getInstance();
        Binary binary1 = new Binary(getClass().getResourceAsStream("binary1.txt"));
        Property binProp = NavajoFactory.getInstance().createProperty(n,  "Bin", "", "", "");
        binProp.setAnyValue(binary1);
        n.getMessage("SimpleMessage").addProperty(binProp);
        Property testprop = NavajoFactory.getInstance().createProperty(n,  "TestProp", "", "", "");
        testprop.setAnyValue("100a");
        n.getMessage("SimpleMessage").addProperty(testprop);
        
        
        Writer sw = new StringWriter();
        json.format(n,  sw, true);

        String result = sw.toString();
        System.out.println(result);

        // Length should be 113, right?
        Assert.assertEquals(113, result.length());
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        Assert.assertEquals(n2.getMessage("SimpleMessage").getProperty("Bin").getTypedValue(), "YWJjZGVmZw==");
    }

}
