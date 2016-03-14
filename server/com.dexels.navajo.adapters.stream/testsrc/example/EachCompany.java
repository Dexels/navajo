package example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dexels.navajo.adapters.stream.Bytes;
import com.dexels.navajo.adapters.stream.CSV;
import com.dexels.navajo.adapters.stream.HTTP;
import com.dexels.navajo.adapters.stream.impl.StringObservable;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import rx.Observable;
import rx.schedulers.Schedulers;

public class EachCompany  {

	public static Observable<Double> temperatureInCity(String city) {
		try {
			return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city,"UTF-8")+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
					.subscribeOn(Schedulers.io())
					.doOnNext(b->System.err.println(new String(b)+" - "+Thread.currentThread().getName()))
					.lift(XML.parse())
					.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
					.filter(e->e.getText().equals("temperature"))
					.first()
					.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273);
		} catch (UnsupportedEncodingException e) {
			return Observable.<Double>error(e);
		}
	}

	public static void main(String[] args) throws NumberFormatException, UnsupportedEncodingException, NavajoException {
//		manual();
		csvExample();
//		Msg m = Msg.createElement();
//		Observable<NavajoStreamEvent> mm = temperatureInCity("Amersfoort").map(d->m.with(Prop.create("Weer", d))).flatMap(message->message.stream()) ;
	}
	
	public static void csvExample() {
		CSV.fromClassPath("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv", Charset.forName("UTF-8"), "\r", ",")
		.map(msg->
			msg.with(Prop.create("Oempaloempa", true))
			.without("category")
			.without(new String[]{"category","numEmps","fundedDate"})
			.with(Prop.create("DoekoeType",msg.value("raisedCurrency")))
		)
		.take(30)
		.flatMap(msg->
			temperatureInCity((String)msg.value("city"))
				.map(temperature->msg.with(Prop.create("Weather", DecimalFormat.getInstance().format(temperature) )))
			)
		.flatMap(m->m.stream())
		.compose(NavajoStreamOperators.inArray("Companies"))
		.compose(NavajoStreamOperators.inNavajo("Companies","dummy","dummy"))
		.lift(NAVADOC.collect(Collections.emptyMap()))
    	.toBlocking().forEach(oa->{
    		oa.write(System.err);
//    		System.err.println("Time elapsed: "+(System.currentTimeMillis() - current));
    	});
	}
	
	@SuppressWarnings("deprecation")
	public static void manual() throws NumberFormatException, UnsupportedEncodingException, NavajoException {
		final long current = System.currentTimeMillis();
        Bytes.fromAbsoluteClassPath("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv")
        	.lift(StringObservable.decode("UTF-8"))
        	.lift(StringObservable.split("\r"))
        	.lift(CSV.rows(","))
			.filter(row->!"".equals(row.get("city")))
			.distinct(row->row.get("city"))
			.take(20)
			.concatMapEager(r->
				HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode((String)r.get("city"))+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
				.subscribeOn(Schedulers.io())
				.lift(XML.parse())
				.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
				.filter(e->e.getText().equals("temperature"))
				.first()
				.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273)
				.concatMap(temperature->{
					List<Prop> props = new ArrayList<>();
					props.add(Prop.create("Company", r.get("company")));
					props.add(Prop.create("City", r.get("city")));
					props.add(Prop.create("Temperature", temperature));
					return Msg.createElement(props).stream();
				})
			)
			.compose(NavajoStreamOperators.inArray("Companies"))
    		.compose(NavajoStreamOperators.inNavajo("Companies","dummy","dummy"))
			.lift(NAVADOC.collect(Collections.emptyMap()))
        	.toBlocking().forEach(oa->{
        		oa.write(System.err);
        		System.err.println("Time elapsed: "+(System.currentTimeMillis() - current));
        	});
	}
}
