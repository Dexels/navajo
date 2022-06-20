/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

public class TestTMLReplicationConversion {

	@Test
	public void testReplToMessage() {
		ReplicationMessage rm = ReplicationFactory.empty().with("Monkey", "Koko", "string");
		Navajo n = NavajoFactory.getInstance().createNavajo();
		n.addMessage(StreamDocument.replicationToMessage(rm.message(),"Message",false));
		n.write(System.err);
		String mn = (String) n.getMessage("Message").getProperty("Monkey").getTypedValue();
		Assert.assertEquals("Koko", mn);
	}
}
