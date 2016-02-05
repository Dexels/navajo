package example;

import static com.dexels.navajo.document.stream.api.ArrayMessage.createArray;
import static com.dexels.navajo.document.stream.api.Msg.create;
import static com.dexels.navajo.document.stream.api.Msg.createElement;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.adapters.stream.sqlmap.example.HTTP;
import com.dexels.navajo.adapters.stream.sqlmap.example.Row;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Example {

	public static void main(String[] args) {
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		Navajo nn = doSomething()
			.startWith(Events.started(new NavajoHead("Script", "Blib", "Blob", null)))
			.concatWith(Observable.just(Events.done()))
			.flatMap(nsc::feed)
			.first()
			.toBlocking().first();

		nn.write(System.err);
		Row r;
//		System.err.println(">> "+nn.getMessage.().size());
		//		.doOnNext(System.err::println).flatMap(nsc::feed);
		//		.count().toBlocking().first();
//		System.err.println("Count: " + count);
	}

	private static Observable<NavajoStreamEvent> doSomething() {
		
		return create("FirstMessage",msg->{
			msg.add(Prop.create("StringProp").withValue("tra"));
			msg.add(Prop.create("EmptyProperty").emptyWithType("integer"));
		}, m -> 
			createArray("Array", array -> 
				CSV.fromClassPath("example.csv")
					.subscribeOn(Schedulers.newThread())
					.observeOn(Schedulers.newThread())
					.filter(row->"NJ".equals(row.get("state")))

					.flatMap(row->
						createElement("Row", elt->{
							System.err.println("Thread: "+Thread.currentThread().getName()+" row: "+row);
							elt.add(Prop.create("Company").withValue(row.get("company")));
							elt.add(Prop.create("City").withValue(row.get("city")));
							elt.add(Prop.create("Weather").withValue(row.get("weather")));
//							getWeatherForCity(row.get("city"))
						}, arrayElement->
							create("Funding", mm->{
								mm.add(Prop.create("RaisedAmount").withValue(row.get("raisedAmt")));
							}, x->Observable.<NavajoStreamEvent>empty())
						)					
				)
			).stream()
		).concatWith(
			create("Second",msg2->{},m->
				Observable.empty()
			)
		);
	}
	
	private static Observable<String> getWeatherForCity(String city) {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
		.flatMap(oxf::feed)
		.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
		.filter(e->e.getText().equals("weather"))
		.first()
		.map(xml->xml.getAttributes().get("value"));

	}

}
