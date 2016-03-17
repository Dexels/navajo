import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.Bytes;

import rx.Observable;

public class TestCompression {
	
	
	@Test
	public void repro() {
		
		Bytes.fromPath("/Users/frank/bigorganizations.xml")
		.doOnNext(a->System.err.println(">> Data: "+new String(a)))
		.lift(NavajoStreamOperators.decompress("inflate"))
		.doOnNext(a->System.err.println(">> Decompressed Data: "+new String(a)))
		.subscribe(NavajoStreamOperators.dumpToFile("/Users/frank/duump.xml"));
		
	}
	@Test
	public void testDeflate() throws FileNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] original = 
				Bytes.fromAbsoluteClassPath("TestCompression.class")
				.reduce(baos, (byteout,bytes)->{try {
					System.err.println("Bytes collected: "+bytes.length);
					byteout.write(bytes);
					} catch (Exception e) {
					} return byteout;})
				.toBlocking()
				.first()
				.toByteArray();
		ByteArrayOutputStream baos_compressed = new ByteArrayOutputStream();
		byte[] compressed = Bytes.fromAbsoluteClassPath("TestCompression.class")
				.lift(NavajoStreamOperators.deflate())
				.reduce(baos_compressed, (byteout,bytes)->{try {
					System.err.println("Bytes compressed: "+bytes.length);
					byteout.write(bytes);
				} catch (Exception e) {
				} return byteout;})
			.toBlocking()
			.first()
			.toByteArray();
		ByteArrayOutputStream baos_reinflate = new ByteArrayOutputStream();
		byte[] reflated = Observable.<byte[]>just(compressed)
				.lift(NavajoStreamOperators.inflate())
				.reduce(baos_reinflate, (byteout,bytes)->{try {
					System.err.println("Bytes decompressed: "+bytes.length);
					byteout.write(bytes);
				} catch (Exception e) {
				} return byteout;})
			.toBlocking()
			.first()
			.toByteArray();
		System.err.println("original: "+original.length);
		System.err.println("deflated: "+compressed.length);

		System.err.println("reinflated: "+reflated.length);
		Assert.assertArrayEquals(original, reflated);
		
	}
}
