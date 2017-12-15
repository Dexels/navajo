import java.util.Optional;

import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.replication.api.ReplicationMessageParser;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestSerializeReplicationMessage {

	@Test
	public void testRepl() {
		ReplicationMessageParser parser = new JSONReplicationMessageParserImpl();
		ImmutableMessage m = parser.parseStream(this.getClass().getClassLoader().getResourceAsStream("person.json")).message();
		
		StreamDocument.replicationMessageToStreamEvents("Person", m, false)
			.compose(StreamDocument.inNavajo("TestService",  Optional.empty(),  Optional.empty()))
			.lift(StreamDocument.serialize())
			.lift(StreamDocument.decode("UTF-8"))
			.blockingForEach(e->{
				System.err.print(e);
			});
		
	}
}
