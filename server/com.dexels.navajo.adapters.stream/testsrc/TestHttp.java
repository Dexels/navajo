import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.adapters.stream.sqlmap.example.HTTP;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

public class TestHttp {

	@Test
	public void testHttpGet() {
		String weather = HTTP.get("http://api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
		.map(b->ByteBuffer.wrap(b))
		.lift(XML.parse())
		.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
		.filter(e->e.getText().equals("weather"))
		.first()
		.map(xml->xml.getAttributes().get("value")).toBlocking().first();
	
		System.err.println("Weather: "+weather);
		// Not really a good unit test (... or is it?)
		Assert.assertEquals("few clouds", weather);
	}
}
