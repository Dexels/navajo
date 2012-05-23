import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoLaszloConverter;


public class PostLaszlo  {

	@Test public void testLocal() throws IOException, NavajoException {
		System.err.println("Starting test");
		FileInputStream fis = new FileInputStream("testdata/initgetcurrentmatches.xml");
		BufferedInputStream bis = new BufferedInputStream(fis);
		com.dexels.navajo.document.Navajo nnn = NavajoLaszloConverter.createNavajoFromLaszlo(bis);
		nnn.write(System.err);
	}
	
	
	@Test public void testServer() throws IOException, NavajoException {
		System.err.println("Starting test");
		FileInputStream fis = new FileInputStream("testdata/dummygetcurrentmatches.xml");
		
		URL u = new URL("http://localhost:9080/knvb/Laszlo");
		URLConnection uc = u.openConnection();
		uc.setDoInput(true);
		uc.setDoOutput(true);
		OutputStream os = uc.getOutputStream();
		
		copyResource(os, fis);
		
		FileOutputStream fos = new FileOutputStream("out.xml");
		InputStream is = uc.getInputStream();
		copyResource(fos, is);

	}
	

	protected final void copyResource(OutputStream out, InputStream in){
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			try {
				read = bin.read(buffer);
				if ( read > -1 ) {
					bout.write(buffer,0,read);
				}
			} catch (IOException e) {
			}
			if ( read <= -1) {
				ready = true;
			}
		}
		try {
			bin.close();
			bout.flush();
			bout.close();
		} catch (IOException e) {

		}
	}
}
