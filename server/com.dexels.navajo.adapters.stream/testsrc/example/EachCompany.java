package example;

import java.io.UnsupportedEncodingException;

import com.dexels.navajo.document.NavajoException;

public class EachCompany  {

//	public static Observable<Double> temperatureInCity(String city) {
//		try {
//			return HTTP.get("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(city,"UTF-8")+"&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
//					.subscribeOn(Schedulers.io())
//					.doOnNext(b->System.err.println(new String(b)+" - "+Thread.currentThread().getName()))
//					.lift(XML.parse())
//					.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
//					.filter(e->e.getText().equals("temperature"))
//					.first()
//					.map(xml->Double.parseDouble(xml.getAttributes().get("value"))-273);
//		} catch (UnsupportedEncodingException e) {
//			return Observable.<Double>error(e);
//		}
//	}

	public static void main(String[] args) throws NumberFormatException, UnsupportedEncodingException, NavajoException {
//		manual();
		csvExample();
//		Msg m = Msg.createElement();
//		Observable<NavajoStreamEvent> mm = temperatureInCity("Amersfoort").map(d->m.with(Prop.create("Weer", d))).flatMap(message->message.stream()) ;
	}
	
	public static void csvExample() {
//		Bytes.from(EachCompany.class.getClassLoader().getResourceAsStream("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv"))
//			.lift(StreamDocument.decode("UTF-8"))
//			.compose(StringFlowable.split("\r"))
//			.toObservable()
//			.lift(CSV.rows(","))
//			.map(r->r.toElement())
//		
//			.map(msg->msg.with("Oempaloempa", true,"boolean")
//						.without("category")
//						.without(Arrays.asList(new String[]{"category","numEmps","fundedDate"}))
//						.with("DoekoeType",msg.columnValue("raisedCurrency"),"string")
//			);
		
//		.take(30)
//		.subscribe(msg->System.err.println("MSG: "+msg));
//		.flatMap(msg->
//			temperatureInCity((String)msg.value("city"))
//				.map(temperature->msg.with(Prop.create("Weather", DecimalFormat.getInstance().format(temperature) )))
//			)
//		.flatMap(m->m.stream())
//		.compose(NavajoStreamOperators.inArray("Companies"))
//		.compose(NavajoStreamOperators.inNavajo("Companies","dummy","dummy"))
//		.lift(NAVADOC.collect(Collections.emptyMap()))
//    	.toBlocking().forEach(oa->{
//    		oa.write(System.err);
////    		System.err.println("Time elapsed: "+(System.currentTimeMillis() - current));
//    	});
	}
	

}
