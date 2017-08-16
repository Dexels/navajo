package example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

import com.dexels.navajo.adapters.stream.ByteSource;
import com.dexels.navajo.adapters.stream.HTTP;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.xml.XML2;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import rx.Observable;

public class Example {

//	public static void main(String[] args) {
//		System.err.println(getWeatherForCity("Bamako").toBlocking().first());
//		doSomething();
//		
//	}

	
//	private static void doSomething() {
//		ByteSource.fromAbsoluteClassPath("tml_without_binary.xml")
//			.lift(XML.parse())
//			.lift(NAVADOC.parse(Collections.emptyMap()))
//			.lift(NAVADOC.collect(Collections.emptyMap()))
//			.subscribe(a->a.write(System.err));
//	}
//
//	private static Observable<String> getWeatherForCity(String city) {
//		try {
//			return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city,"UTF-8")+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
//			.lift(XML2.parse())
//			.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
//			.filter(e->e.getText().equals("weather"))
//			.first()
//			.map(xml->xml.getAttributes().get("value"));
//		} catch (UnsupportedEncodingException e) {
//			return Observable.<String>error(e);
//		}
//	}
}
