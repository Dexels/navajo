package com.dexels.navajo.document.json.conversion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.conversion.JsonTmlFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

public class TestRun {

	@Test
	public void testReplicationToTML()  {
		ReplicationMessage msg = ReplicationFactory.getDefaultInstance().parseStream(getClass().getResourceAsStream("test.json"));
		Navajo nn =  JsonTmlFactory.getInstance().toFlatNavajo("Pool",msg);
		Message standings = nn.getMessage("Pool").getMessage("standings");
		Assert.assertEquals(14,standings.getArraySize());
		nn.write(System.err);
	}
	
	   @Test
    public void testTimestamp()  {
	        ReplicationMessage msg = ReplicationFactory.getDefaultInstance().parseStream(getClass().getResourceAsStream("test3.json"));
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
	    }
}
