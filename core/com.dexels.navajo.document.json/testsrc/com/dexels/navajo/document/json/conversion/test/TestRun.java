package com.dexels.navajo.document.json.conversion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.conversion.JsonTmlFactory;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestRun {

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
	}
	
	@Test
	public void testReplicationToTML()  {
		ImmutableMessage msg = ReplicationFactory.getInstance().parseStream(getClass().getResourceAsStream("test.json")).message();
		Navajo nn =  JsonTmlFactory.getInstance().toFlatNavajo("Pool",msg);
		Message standings = nn.getMessage("Pool").getMessage("standings");
		Assert.assertEquals(14,standings.getArraySize());
		nn.write(System.err);
	}
	
	@Test
	public void testClocktimeReplicationToTML()  {
		ImmutableMessage msg = ReplicationFactory.getInstance().parseStream(getClass().getResourceAsStream("calendarday.json")).message();
		Navajo nn =  JsonTmlFactory.getInstance().toFlatNavajo("CalendarDay",msg);
		nn.write(System.err);
		Message calendarday = nn.getMessage("CalendarDay");
		System.err.println(">> "+calendarday.getProperty("starttime").getValue());
		ClockTime ct = (ClockTime) calendarday.getProperty("starttime").getTypedValue();
		Assert.assertFalse(ct.isEmpty());
		int hours = ct.getHours();
		Assert.assertEquals(17, hours);
		System.err.println("Clocktime: "+hours);
		
//		ReplicationMessage rm2 =  protoBufParser.parseBytes(bb);
//		Assert.assertEquals(7, rm2.values().size());
//		
//		final Date columnValue2 = (Date) rm2.columnValue("starttime");
//		Assert.assertTrue(Math.abs(c.getTime().getTime() - columnValue2.getTime())<1000);
//
//		Assert.assertEquals(14,standings.getArraySize());
	}
	
	   @Test
    public void testTimestamp()  {
		    ImmutableMessage msg = ReplicationFactory.getInstance().parseStream(getClass().getResourceAsStream("test3.json")).message();
	        Navajo nn =  JsonTmlFactory.getInstance().toFlatNavajo("Match",msg);
	        Message match = nn.getMessage("Match");
	        assertNotNull( match.getProperty("matchtime"));
	        Property matchtime = match.getProperty("matchtime");
	        assertEquals(Property.DATE_PROPERTY, matchtime.getType());
	        Date matchtimeobj = (Date) matchtime.getTypedValue();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(matchtimeobj);
	        assertEquals(1971, cal.get(Calendar.YEAR));
	        assertEquals(12, cal.get(Calendar.HOUR_OF_DAY));
	        assertEquals(0, cal.get(Calendar.MINUTE));
	        
	        Property matchtime2 = match.getProperty("matchtime2");
            assertEquals(Property.CLOCKTIME_PROPERTY, matchtime2.getType());
	    }
}
