package example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Collections;

import com.dexels.navajo.adapters.stream.Bytes;
import com.dexels.navajo.adapters.stream.HTTP;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import rx.Observable;

public class Example {

	public static void main(String[] args) {
//		NavajoStreamCollector nsc = new NavajoStreamCollector();
//		Navajo nn = doSomething()
//			.startWith(Events.started(new NavajoHead("Script", "Blib", "Blob", Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap())))
//			.concatWith(Observable.just(Events.done()))
//			.flatMap(nsc::feed)
//			.first()
//			.toBlocking().first();
//		nn.write(System.err);
		System.err.println(getWeatherForCity("Bamako").toBlocking().first());
		doSomething();
		
	}

	
	private static void doSomething() {
		Bytes.fromAbsoluteClassPathBuffer("tml_without_binary.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.lift(NAVADOC.collect(Collections.emptyMap()))
			.subscribe(a->a.write(System.err));
	}
//	private static Observable<NavajoStreamEvent> doSomething() {
//		
	
//		return create("FirstMessage",msg->{
//			msg.add(Prop.create("StringProp").withValue("tra"));
//			msg.add(Prop.create("EmptyProperty").emptyWithType("integer"));
//		}, m -> 
//			createArray("Array", array -> 
//				CSV.fromClassPath("example.csv")
//					.subscribeOn(Schedulers.newThread())
//					.observeOn(Schedulers.newThread())
//					.filter(row->"NJ".equals(row.get("state")))
//					.take(5)
//					.flatMap(row->
//						createElement("Row", elt->{
//							System.err.println("Thread: "+Thread.currentThread().getName()+" row: "+row);
//							elt.add(Prop.create("Company").withValue(row.get("company")));
//							elt.add(Prop.create("City").withValue(row.get("city")));
//							elt.add(Prop.create("State").withValue(row.get("state")));
//							elt.add(Prop.create("Weather").withValue(getWeatherForCity((String) row.get("city")).toBlocking().first() ));
////							getWeatherForCity(row.get("city"))
//						}, arrayElement->
//							create("Funding", mm->{
//								mm.add(Prop.create("RaisedAmount").withValue(row.get("raisedAmt")));
//							}, x->Observable.<NavajoStreamEvent>empty())
//						)					
//				)
//			).stream()
//		).concatWith(
//			create("Second",msg2->{},m->
//				Observable.empty()
//			)
//		);
//	}
	
	private static Observable<String> getWeatherForCity(String city) {
		try {
			return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city,"UTF-8")+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
			.map(bytearray->ByteBuffer.wrap(bytearray))
			.lift(XML.parse())
			.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
			.filter(e->e.getText().equals("weather"))
			.first()
			.map(xml->xml.getAttributes().get("value"));
		} catch (UnsupportedEncodingException e) {
			return Observable.<String>error(e);
		}
	}
}
