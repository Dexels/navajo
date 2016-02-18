import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.Bytes;

import rx.Observable;

public class TestCompression {
	
	@Test
	public void testDeflate() throws FileNotFoundException {
		byte[] original = Bytes.fromAbsoluteClassPath("TestCompression.class").lift(NavajoStreamOperators.collect()).toBlocking().first();
		System.err.println("original: "+original.length);
		byte[] compressed = Bytes.fromAbsoluteClassPath("TestCompression.class").lift(NavajoStreamOperators.deflate()).lift(NavajoStreamOperators.collect()).toBlocking().first();
		System.err.println("Compressed: "+compressed.length);
		byte[] reflated = Observable.<byte[]>just(compressed).map(b->ByteBuffer.wrap(b)).lift(NavajoStreamOperators.inflate()).map(b->b.array()).lift(NavajoStreamOperators.collect()).toBlocking().first();
		System.err.println("reinflated: "+reflated.length);
		Assert.assertArrayEquals(original, reflated);
		
	}
}
