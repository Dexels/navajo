package example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dexels.navajo.adapters.stream.sqlmap.example.Bytes;
import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.adapters.stream.sqlmap.example.HTTP;
import com.dexels.navajo.adapters.stream.sqlmap.example.StringObservable;
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
					.map(b->ByteBuffer.wrap(b))
					.lift(XML.parse())
					.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
					.filter(e->e.getText().equals("temperature"))
					.first()
					.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273);
		} catch (UnsupportedEncodingException e) {
			return Observable.<Double>error(e);
		}
	}

	public static void main(String[] args) {
		final long current = System.currentTimeMillis();
        Bytes.fromAbsoluteClassPath("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv")
        	.lift(StringObservable.decode("UTF-8"))
        	.lift(StringObservable.split("\r"))
        	.lift(CSV.row())
			.filter(row->!"".equals(row.get("city")))
			.distinct(row->row.get("city"))
			.take(10)
			.concatMapEager(r->
				temperatureInCity((String)r.get("city")).concatMap(temperature->{
					List<Prop> props = new ArrayList<>();
					props.add(Prop.create("Company", r.get("company")));
					props.add(Prop.create("City", r.get("city")));
					props.add(Prop.create("Temperature", temperature));
					return Msg.createElement("Companies", props);
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
