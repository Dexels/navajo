import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.Bytes;

import rx.Observable;

public class TestCompression {
	
	
	@Test
	public void repro() throws IOException {
		
		File compressed = File.createTempFile("dump", ".xml.deflated");
		File uncompressed = File.createTempFile("dump", ".xml");
		
		Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.subscribe(NavajoStreamOperators.dumpToFile(uncompressed.getAbsolutePath()));
		
		Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(NavajoStreamOperators.decompress("deflate"))
			.subscribe(NavajoStreamOperators.dumpToFile(compressed.getAbsolutePath()));
		

		Assert.assertTrue(uncompressed.exists());
		Assert.assertTrue(uncompressed.length()>5000);
		System.err.println("Compressed: "+compressed.length());
		System.err.println("Uncompressed: "+uncompressed.length());
		Assert.assertTrue(compressed.length()<uncompressed.length());
		
		compressed.delete();
		uncompressed.delete();
	}
	@Test
	public void testDeflate() throws FileNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] original = 
				Bytes.fromAbsoluteClassPath("TestCompression.class")
				.reduce(baos, (byteout,bytes)->{try {
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
