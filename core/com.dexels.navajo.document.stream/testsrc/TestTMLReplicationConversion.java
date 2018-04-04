import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

public class TestTMLReplicationConversion {

	public TestTMLReplicationConversion() {
		// TODO Auto-generated constructor stub
	}
	
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
