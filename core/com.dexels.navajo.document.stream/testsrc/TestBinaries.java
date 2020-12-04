/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamCompress;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.types.Binary;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.schedulers.Schedulers;


public class TestBinaries {

	@Test  
	public void testStreamParserAndSerializerWithBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"))
			.lift(XML.parseFlowable(5))
			.observeOn(Schedulers.io())
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.observeOn(Schedulers.io())
			.concatMap(e->e)
			.lift(StreamDocument.serialize())
			.observeOn(Schedulers.io())
			.compose(StreamCompress.deflate())
			.observeOn(Schedulers.io())
			.compose(StreamCompress.inflate())
			.blockingForEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		System.err.println("Length: "+baos.toByteArray().length);
		Assert.assertTrue(baos.toByteArray().length>5000);
		NavajoFactory.getInstance().createNavajo(new ByteArrayInputStream(baos.toByteArray()));
	}
	
	@Test  
	public void testBinaryStream() throws Exception {
		Binary b = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"))
			.toObservable()
			.lift(StreamDocument.createBinary())
			.firstOrError()
			.blockingGet();
//		5,258
		Assert.assertEquals(5258, b.getLength());
	}

	@Test 
	public void testWithBinary() throws Exception {
		long nn = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"),4096)
//		int nn = Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML.parseFlowable(5))
			.flatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
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
	
	@Test 
	public void testObserveBinary() throws Exception {
		long nn = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"),4096)
//		int nn = Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML.parseFlowable(5))
			.flatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(StreamDocument.observeBinary("SecureImage/Image"))
			.doOnNext(e->System.err.println("Event: "+e))
//			.lift(NavajoStreamOperatorsNew.collect())
//			.lift(NavajoStreamOperatorsNew.domStream())
//			.lift(NavajoStreamOperatorsNew.serializeObservable())
			.count().blockingGet();
		System.err.println("eventcount: "+nn);
//		nn.write(System.err);
//					System.err.println("RESULT:\n"+new String(baos.toByteArray()));
		Assert.assertTrue(nn==2);
	}
	
	@Test 
	public void testGatherBinary() throws Exception {
		Binary nn = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"),4096)
//		int nn = Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML.parseFlowable(5))
			.flatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.lift(StreamDocument.observeBinary("SecureImage/Image"))
			.lift(StreamDocument.gatherBinary())
			.doOnNext(e->System.err.println("Event: "+e))
//			.lift(NavajoStreamOperatorsNew.collect())
//			.lift(NavajoStreamOperatorsNew.domStream())
//			.lift(NavajoStreamOperatorsNew.serializeObservable())
			.blockingFirst();
		System.err.println("eventcount: "+nn.getLength());
//		nn.write(System.err);
//					System.err.println("RESULT:\n"+new String(baos.toByteArray()));
		Assert.assertTrue(nn.getLength()==3245);
	}
}
