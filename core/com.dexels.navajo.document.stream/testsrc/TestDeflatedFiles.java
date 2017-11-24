import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.junit.Test;

import com.dexels.navajo.document.stream.StreamCompress;
import com.github.davidmoten.rx2.Bytes;

public class TestDeflatedFiles {

	@Test
	public void testDeflated() throws IOException {
		InputStream in = TestDeflatedFiles.class.getClassLoader().getResourceAsStream("file2.xml.deflated");
		InflaterInputStream iis = new InflaterInputStream(in);
		int read = 0;
		long total = 0;
		byte[] buffer = new byte[100];
		do {
			read = iis.read(buffer);
			System.err.println("read: "+read);
			total+=read;
		} while (read > 0);
		System.err.println("Total: "+total);
	}
	
	@Test
	public void testReactiveInflation() {
		Bytes.from(TestDeflatedFiles.class.getClassLoader().getResourceAsStream("file2.xml.deflated"),100)
			.compose(StreamCompress.deflate())
			.blockingForEach(b->System.err.println(">data: "+b.length));
	}
}
