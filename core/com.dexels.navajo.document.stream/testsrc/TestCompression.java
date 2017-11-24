import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.CRC32;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.stream.StreamCompress;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.schedulers.Schedulers;

public class TestCompression {

	@Test
	public void testCompressionFileGzip() {
		final CRC32 original = new CRC32();
		final CRC32 compressed = new CRC32();
		final CRC32 uncompressed = new CRC32();
		AtomicLong originalSize = new AtomicLong();
		AtomicLong compressedSize = new AtomicLong();
		AtomicLong unCompressedSize = new AtomicLong();
		Bytes.from(getClass().getResourceAsStream("randomfile"),2000)
			.doOnNext(e->originalSize.addAndGet(e.length))
			.doOnNext(e->original.update(e))
			.observeOn(Schedulers.io())
			.compose(StreamCompress.gzip())
			.doOnNext(e->compressedSize.addAndGet(e.length))
			.observeOn(Schedulers.io())
			.doOnNext(e->compressed.update(e))
			.observeOn(Schedulers.io())
			.compose(StreamCompress.gunzip())
			.doOnNext(e->unCompressedSize.addAndGet(e.length))
			.doOnNext(e->uncompressed.update(e))
			.observeOn(Schedulers.io())
			.blockingForEach(e->{});
		System.err.println("Original: "+original.getValue()+" -> "+compressed.getValue()+" == "+uncompressed.getValue());
		System.err.println("Original size: "+originalSize.get()+" uUncompressed: "+unCompressedSize.get()+" compressed: "+compressedSize.get());
		Assert.assertEquals(original.getValue(), uncompressed.getValue());
		Assert.assertEquals(originalSize.get(), unCompressedSize.get());
	}
	
	@Test
	public void testCompressionFileDeflate() {
		final CRC32 original = new CRC32();
		final CRC32 compressed = new CRC32();
		final CRC32 uncompressed = new CRC32();
		AtomicLong originalSize = new AtomicLong();
		AtomicLong compressedSize = new AtomicLong();
		AtomicLong unCompressedSize = new AtomicLong();
		Bytes.from(getClass().getResourceAsStream("randomfile"),2000)
			.doOnNext(e->originalSize.addAndGet(e.length))
			.doOnNext(e->original.update(e))
			.observeOn(Schedulers.io())
			.compose(StreamCompress.deflate())
			.doOnNext(e->compressedSize.addAndGet(e.length))
			.observeOn(Schedulers.io())
			.doOnNext(e->compressed.update(e))
			.observeOn(Schedulers.io())
			.compose(StreamCompress.inflate())
			.doOnNext(e->unCompressedSize.addAndGet(e.length))
			.doOnNext(e->uncompressed.update(e))
			.observeOn(Schedulers.io())
			.blockingForEach(e->{});
		System.err.println("Original: "+original.getValue()+" -> "+compressed.getValue()+" == "+uncompressed.getValue());
		System.err.println("Original size: "+originalSize.get()+" uUncompressed: "+unCompressedSize.get()+" compressed: "+compressedSize.get());
		Assert.assertEquals(original.getValue(), uncompressed.getValue());
		Assert.assertEquals(originalSize.get(), unCompressedSize.get());
	}


}
