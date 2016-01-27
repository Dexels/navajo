package example;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.adapters.stream.sqlmap.example.HTTP;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

public class WeatherInAmsterdam extends BaseScriptInstance {

	public void init(OutputSubscriber output) {
	}


	public void complete(OutputSubscriber output) {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		HTTP.get("api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
			.flatMap(oxf::feed)
			.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
			.filter(e->e.getText().equals("clouds"))
			.first()
			.map(xml->xml.getAttributes().get("name")).toBlocking().first();
		
		

}}