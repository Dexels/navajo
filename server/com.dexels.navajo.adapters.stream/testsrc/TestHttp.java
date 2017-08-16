import java.net.MalformedURLException;

import org.junit.Test;

import com.dexels.navajo.adapters.stream.HTTP;
import com.dexels.navajo.document.stream.xml.XML2;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

public class TestHttp {

	@Test
	public void testHttpGet() throws MalformedURLException {
		
		String weather = HTTP.get("http://api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
		.lift(XML2.parse())
		.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
		.filter(e->e.getText().equals("weather"))
		.firstElement()
		.map(xml->xml.getAttributes().get("value")).blockingGet();
	
		System.err.println("Weather: "+weather);
		// Not really a good unit test (... or is it?)
//		Assert.assertEquals("few clouds", weather);
	}


	@Test
	public void testBiggerDownload() throws MalformedURLException {
		String url = "https://repo.dexels.com/nexus/service/local/repositories/central/content/org/apache/tika/tika-bundle/1.6/tika-bundle-1.6.jar";
		HTTP.get(url).subscribe(e->System.err.println("C: "+e.length));
		Thread.sleep(20000);
	}

}
