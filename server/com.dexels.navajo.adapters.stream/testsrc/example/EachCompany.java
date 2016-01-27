package example;

import java.net.URLEncoder;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.adapters.stream.sqlmap.example.HTTP;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

import rx.Observable;
import rx.schedulers.Schedulers;

public class EachCompany extends BaseScriptInstance {

	public void init(OutputSubscriber output) {
	}


	@Override
	public Observable<NavajoStreamEvent> complete() {
		return array("Company",()->{
			return CSV.fromClassPath("example.csv")
				.map(e->e.get("city"))
				.cast(String.class)
				.distinct()
				.take(10)
				.concatMap(
					city->{
//						return Observable.<NavajoStreamEvent>empty();
						return findWeather(city)
								.flatMap(temperature->{
									Message elt = createElement();
									elt.addProperty(createProperty("CompanyName", city));
									elt.addProperty(createProperty("Temperature", temperature));
									return emitElement(elt, "Company");
									}
								);
					}
			);
		});
}

//	return findWeather(city,elt)
//			.flatMap(temp->{
//				return Observable.<NavajoStreamEvent>empty();
//			});
	
//	Message elt = createElement();
//	elt.addProperty(createProperty("CompanyName", city));
//	return emitElement(elt, "Company");
	
	
	private Observable<Double> findWeather(String city) {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city)+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
//		.subscribeOn(Schedulers.io())
		.flatMap(oxf::feed)
		.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
		.filter(e->e.getText().equals("temperature"))
		.first()
		.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273)
		.doOnNext(e->System.err.println(e+" in "+city));
//		.subscribe(temperature->{
//			m.addProperty(createProperty("City", city));
//			m.addProperty(createProperty("Temperature", temperature));
//		})
	}
	
}