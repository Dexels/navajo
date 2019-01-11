import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.NavajoStreamToMutableMessageStream;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.XML;
import com.github.davidmoten.rx2.Bytes;

public class TestMutableStream {

	@Before
	public void setup() {
//		.doOnNext(e->ImmutableFactory.createParser().describe(e))
	}
	@Test
	public void testSimplePath() {
		long c = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml.xml"), 128)
			.lift(XML.parseFlowable(10))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(NavajoStreamToMutableMessageStream.toMutable(Optional.of("Club/Accommodation")))
			.concatMap(e->e)
			.count()
			.blockingGet();
		
		Assert.assertEquals(c, 1);
	}

	@Test
	public void testArrayPath() {
		long c = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml.xml"), 128)
			.lift(XML.parseFlowable(10))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(NavajoStreamToMutableMessageStream.toMutable(Optional.of("Member")))
			.concatMap(e->e)
			.doOnNext(e->System.err.println(e.getAllProperties()))
			.count()
			.blockingGet();
		
		Assert.assertEquals(c, 3);
	}
	
	@Test
	public void testImplicitArray() {
		List<Message> mm = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("singlearray.xml"), 128)
			.lift(XML.parseFlowable(10))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(NavajoStreamToMutableMessageStream.toMutable(Optional.empty()))
			.concatMap(e->e)
			.doOnNext(e->e.write(System.err))
			.toList()
			.blockingGet();
		for (Message message : mm) {
			message.write(System.err);
		}
		
		Assert.assertEquals(3, mm.size());
	}
	
	@Test
	public void testImplicitSingle() {
		long c = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_date.xml"), 128)
			.lift(XML.parseFlowable(10))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(NavajoStreamToMutableMessageStream.toMutable(Optional.empty()))
			.concatMap(e->e)
			.doOnNext(e->e.write(System.err))
			.count()
			.blockingGet();
		
		Assert.assertEquals(1, c);
	}
	
	
	@Test
	public void testArrayPathToImmutable() {
		long c = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml.xml"), 128)
			.lift(XML.parseFlowable(10))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(NavajoStreamToMutableMessageStream.toMutable(Optional.of("Member")))
			.concatMap(e->e)
			.map(StreamDocument::messageToReplication)
			.doOnNext(e->System.err.println("whoop:"+ImmutableFactory.createParser().describe(e)))
			.count()
			.blockingGet();
		
		Assert.assertEquals(c, 3);
	}

	@Test
	public void testOddStreamBug() {
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml.xml"), 128)
				.lift(XML.parseFlowable(10))
				.concatMap(e->e)
				.lift(StreamDocument.parse())
				.concatMap(e->e)
				.blockingForEach(e->{
					System.err.println("Body: "+e.type()+"->"+e.path()+" body: "+e.body());
				});
		
	}
	

	@Test
	public void testOrderingStreamBug() {
		Msg m =Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("misorderedtml.xml"), 128)
			.lift(XML.parseFlowable(10))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.filter(e->"Member".equals(e.path()) && e.type()==NavajoEventTypes.MESSAGE)
			.firstOrError()
			.map(e->e.body())
			.cast(Msg.class)
			.blockingGet();

		Assert.assertEquals("Dmitri",m.toImmutableMessage().columnValue("Name"));
	}
}
