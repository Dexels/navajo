import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.stream.NavajoStreamOperatorsNew;
import com.dexels.navajo.document.stream.xml.XML2;
import com.github.davidmoten.rx2.Bytes;

public class TestBinaries {

	@Test  
	public void testStreamParserAndSerializerWithBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"))
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.doOnNext(e->System.err.println("Event: "+e.toString()))
			.lift(NavajoStreamOperatorsNew.serialize())
			.blockingForEach(b -> {
					try {
						baos.write(b);
						System.err.println("e> "+new String(b));
					} catch (Exception e) {
					}
				});
		System.err.println("RESULT:\n"+new String(baos.toByteArray()));
		Assert.assertTrue(baos.toByteArray().length>5000);
	}
	

	@Test 
	public void testWithBinary() throws Exception {
		long nn = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"),4096)
//		int nn = Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.toObservable()
			.doOnNext(e->System.err.println("Event: "+e))
//			.lift(NavajoStreamOperatorsNew.collect())
//			.lift(NavajoStreamOperatorsNew.domStream())
//			.lift(NavajoStreamOperatorsNew.serializeObservable())
			.count().blockingGet();
		System.err.println("eventcount: "+nn);
//		nn.write(System.err);
//					System.err.println("RESULT:\n"+new String(baos.toByteArray()));
		Assert.assertTrue(nn==8);
	}
	
	
}
