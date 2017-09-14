import org.junit.Test;

import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.api.ReplicationMessageParser;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestSerializeReplicationMessage {

	@Test
	public void testRepl() {
		ReplicationMessageParser parser = new JSONReplicationMessageParserImpl();
		ReplicationMessage m = parser.parseStream(this.getClass().getClassLoader().getResourceAsStream("person.json"));
		
		StreamDocument.replicationMessageToStreamEvents("Person", m, false)
			.compose(StreamDocument.inNavajo("TestService", "user", "pass"))
			.lift(StreamDocument.serialize())
			.lift(StreamDocument.decode("UTF-8"))
			.blockingForEach(e->{
				System.err.print(e);
			});
		
	}
}
