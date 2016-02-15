import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

import org.junit.Test;

import com.dexels.navajo.document.stream.io.ObservableStreams;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

public class TestChannelByteBuffer {

	
	@Test 
	public void testStreamIterator() {
		InputStream in = getClass().getClassLoader().getResourceAsStream("tiny_tml.xml");
		Iterator<ByteBuffer> vv = ObservableStreams.streamToIterator(in);
		byte[] dstbuf = new byte[10];
		while(vv.hasNext()) {
			ByteBuffer byteBuffer = vv.next().get(dstbuf);
			System.err.println("Got: "+byteBuffer);
		}
	}
	
	@Test 
	public void testStreamTml() throws InterruptedException, IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream("tiny_tml.xml");
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableStreams.streamInputStreamWithBufferSize(in, 15)
		.flatMap(oxf::feed)
		.toBlocking()
		.forEach(a->{
			System.err.println("Event: "+a+" null? "+(a==null));
		});
	}
}
