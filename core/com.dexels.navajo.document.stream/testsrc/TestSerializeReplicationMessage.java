/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
