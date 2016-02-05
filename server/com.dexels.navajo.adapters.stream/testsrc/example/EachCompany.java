package example;

import java.net.URLEncoder;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.adapters.stream.sqlmap.example.HTTP;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;

import rx.Observable;
import rx.schedulers.Schedulers;

public class EachCompany extends BaseScriptInstance {

	@Override
	public Observable<NavajoStreamEvent> complete() {
		return array("Company",()->{
			return CSV.fromClassPath("example.csv")
//				.observeOn(Schedulers.io())
				.map(e->e.get("city"))
				.cast(String.class)
				.distinct()
				.filter(city->!"".equals(city))
				.take(20)
				.flatMap(
					city->{
						ObservableXmlFeeder oxf = new ObservableXmlFeeder();
						return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city)+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
								.doOnNext(b->System.err.println(new String(b)+" - "+Thread.currentThread().getName()))
								.flatMap(oxf::feed)
								.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
								.filter(e->e.getText().equals("temperature"))
								.first()
								.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273)
								.flatMap(temperature->
									Msg.createElement("Elt",elt->{
										elt.add(Prop.create("City").withValue(city));
										elt.add(Prop.create("Temperature").withValue(temperature));
										
									},children->Observable.empty())
							);
					}
			);
		});
}

	
}