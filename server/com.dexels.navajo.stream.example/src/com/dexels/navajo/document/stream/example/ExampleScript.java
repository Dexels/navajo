package com.dexels.navajo.document.stream.example;

import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Collections;

import com.dexels.navajo.adapters.stream.CSV;
import com.dexels.navajo.adapters.stream.HTTP;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ExampleScript implements SimpleScript {
	
	@Override
	public Observable<NavajoStreamEvent> call(Navajo input) {

		Observable<NavajoStreamEvent> sports = SQL.queryToMessage("authentication", "select * from SPORT")
			.subscribeOn(Schedulers.io())
			.map(m->m.renameProperty("SPORTID", "Id")
			.without(new String[]{"UPDATEBY","LASTUPDATE"}))
			.take(20)
			.flatMap(m->m.stream())
			.compose(inArray("Sport"));
		Observable<NavajoStreamEvent> staticEmpty = Observable.<NavajoStreamEvent>just(Events.arrayStarted("Blib", Collections.emptyMap()),Events.arrayDone("Blib"));
		Observable<NavajoStreamEvent> companies = CSV.fromClassPath("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv", Charset.forName("UTF-8"), "\r", ",")
			.map(msg->msg.with(Prop.create("Oempaloempa", true))
			.without("category")
			.without(new String[]{"category","numEmps","fundedDate"})
			.with(Prop.create("DoekoeType",msg.value("raisedCurrency")))
			)
			.take(5)
			.flatMap(msg->
				temperatureInCity((String)msg.value("city"))
					.map(temperature->msg.with(Prop.create("Weather", DecimalFormat.getInstance().format(temperature) )))
				)
			.flatMap(m->m.stream())
			.compose(NavajoStreamOperators.inArray("Companies"));
		return staticEmpty.concatWith(sports).concatWith(companies).doOnNext(e->System.err.println("Concatted event: "+e));		
				
	}

	public static Observable<Double> temperatureInCity(String city) {
		try {
			return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city,"UTF-8")+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
				.subscribeOn(Schedulers.io())
				.doOnNext(b->System.err.println(new String(b)+" - "+Thread.currentThread().getName()))
				.lift(XML.parse())
				.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
				.filter(e->e.getText().equals("temperature"))
				.first()
				.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273)
				.onErrorReturn(e->-1d);
		} catch (UnsupportedEncodingException e) {
			return Observable.<Double>error(e);
		}
	}
}
