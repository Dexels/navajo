/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.test;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Coordinate;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;

public class TestTMLJson {
	
	private static final Logger logger = LoggerFactory.getLogger(TestTMLJson.class);
    
    @Test
    public void testJson() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message.xml"));
        JSONTML json = JSONTMLFactory.getInstance();
        
        Writer sw = new StringWriter();
        json.format(n,  sw, true);
        
        String result = sw.toString();
        logger.info(result);

        // Length should be... 64! right?
        Assert.assertEquals(64, result.length());
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        Assert.assertTrue(n.getMessage("SimpleMessage").isEqual(n2.getMessage("SimpleMessage")));
    }
    
    @Test
    public void testJsonIgnoreMessage() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message.xml"));
        JSONTML json = JSONTMLFactory.getInstance();
        
        Message ignoreMessage = NavajoFactory.getInstance().createMessage(n,  "ignoreme");
        n.addMessage(ignoreMessage);
        ignoreMessage.setMode(Message.MSG_MODE_IGNORE);
        Property testprop = NavajoFactory.getInstance().createProperty(n,  "TestProp", "", "", "");
        testprop.setAnyValue("100a");
        ignoreMessage.addProperty(testprop);
        
        
        
        Writer sw = new StringWriter();
        json.format(n,  sw, true);
        
        String result = sw.toString();
        logger.info(result);

        // Length should be... 64! right?
        Assert.assertEquals(64, result.length());
        
        // Ignore message shouldnt be present
        Assert.assertFalse(result.contains("ignoreme"));
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        Assert.assertTrue(n.getMessage("SimpleMessage").isEqual(n2.getMessage("SimpleMessage")));
    }
    
    
    // TODO fix time zone issue
    @Test @Ignore
    public void testDates() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message.xml"));
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
        logger.info(result);

        //Length should be 133... Right?
        Assert.assertEquals(133, result.length());
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        
        Assert.assertEquals("2016-10-25", n2.getMessage("SimpleMessage").getProperty("date").getValue());
        String resultValue = n2.getMessage("SimpleMessage").getProperty("timestamp").getValue();
		Assert.assertEquals("2016-10-25T12:57:07", resultValue.substring(0, resultValue.length()-5));

    }
    
    @Test
    public void testJsonBinary() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message.xml"));
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
        logger.info(result);

        // Length should be 113, right?
        Assert.assertEquals(113, result.length());
        
        // Turn back into a Navajo and compare
        Navajo n2 = json.parse(new StringReader(result), "SimpleMessage");
        Assert.assertEquals(n2.getMessage("SimpleMessage").getProperty("Bin").getTypedValue(), "YWJjZGVmZw==");
    }

    @Test
    public void testMoney() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message2.xml"));
        JSONTML json = JSONTMLFactory.getInstance();

        Property moneyProp = NavajoFactory.getInstance().createProperty(n, "moneyProp", "", "", "");
        moneyProp.setAnyValue(new Money("32.22"));
        n.getMessage("SimpleMessage").addProperty(moneyProp);

        Writer sw = new StringWriter();
        json.format(n, sw, true);

        String result = sw.toString();
        Assert.assertEquals("{\n  \"moneyProp\" : 3222.0\n}", result);
    }

    @Test
    public void testClockTime() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message2.xml"));
        JSONTML json = JSONTMLFactory.getInstance();

        Property prop = NavajoFactory.getInstance().createProperty(n, "clockTimeProp", "", "", "");
        prop.setAnyValue(new ClockTime("11:12:12"));
        n.getMessage("SimpleMessage").addProperty(prop);

        Writer sw = new StringWriter();
        json.format(n, sw, true);

        String result = sw.toString();
        Assert.assertEquals("{\n  \"clockTimeProp\" : \"11:12:00\"\n}", result);
    }

    @Test
    public void testPercentage() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message2.xml"));
        JSONTML json = JSONTMLFactory.getInstance();

        Property prop = NavajoFactory.getInstance().createProperty(n, "percentageProp", "", "", "");
        prop.setAnyValue(new Percentage("43"));
        n.getMessage("SimpleMessage").addProperty(prop);

        Writer sw = new StringWriter();
        json.format(n, sw, true);

        String result = sw.toString();
        Assert.assertEquals("{\n  \"percentageProp\" : 43.0\n}", result);
    }

    @Test
    public void testMemo() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message2.xml"));
        JSONTML json = JSONTMLFactory.getInstance();

        Property prop = NavajoFactory.getInstance().createProperty(n, "memoProp", "", "", "");
        prop.setAnyValue(new Memo("This is a memo value"));
        n.getMessage("SimpleMessage").addProperty(prop);

        Writer sw = new StringWriter();
        json.format(n, sw, true);

        String result = sw.toString();
        logger.info(result);
        Assert.assertEquals("{\n  \"memoProp\" : \"This is a memo value\"\n}", result);
    }

    @Test
    public void testCoordinate() throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("message2.xml"));
        JSONTML json = JSONTMLFactory.getInstance();

        Property prop = NavajoFactory.getInstance().createProperty(n, "coordinateProp", "", "", "");
        prop.setAnyValue(new Coordinate(-12.65342223, 13.12323425));
        n.getMessage("SimpleMessage").addProperty(prop);

        Writer sw = new StringWriter();
        json.format(n, sw, true);

        String result = sw.toString();
        Assert.assertEquals("{\n  \"coordinateProp\" : \"[-12.65342223,13.12323425]\"\n}", result);
    }

}
