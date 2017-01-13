package com.dexels.navajo.document.json.conversion.test;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
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
}