import java.io.ByteArrayOutputStream;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.xml.Bytes;
import com.dexels.navajo.document.stream.xml.XML;

public class TestBinaries {

	@Test  
	public void testStreamParserAndSerializerWithBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.doOnNext(e->System.err.println("Event: "+e))
			.lift(NAVADOC.serialize())
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		System.err.println("RESULT:\n"+new String(baos.toByteArray()));
		Assert.assertTrue(baos.toByteArray().length>5000);
	}
	

	@Test 
	public void testWithBinary() throws Exception {
		int nn = Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.doOnNext(e->System.err.println("Event: "+e))
			.lift(NAVADOC.collect(Collections.emptyMap()))
			.lift(NAVADOC.stream())
			.count()
			.toBlocking()
			.first();
		System.err.println("eventcount: "+nn);
//		nn.write(System.err);
//					System.err.println("RESULT:\n"+new String(baos.toByteArray()));
		Assert.assertTrue(nn==8);
	}
	
	
}
