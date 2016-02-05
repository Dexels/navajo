package example;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;

import rx.Observable;

public class CompanyList extends BaseScriptInstance {

	@Override
	public Observable<NavajoStreamEvent> complete() {
		return Observable.empty();
//			return array("Company",()->{
//				return CSV.fromClassPath("example.csv")
//					.take(10)
//					.subscribeOn(Schedulers.io())
//					.map(e->e.get("company"))
//					.cast(String.class)
//					.map(String::toUpperCase)
////					.observeOn(Schedulers.io())
//					.flatMap(name->{
//						Message elt = createElement();
//						elt.addProperty(createProperty("CompanyName", name));
//						return emitElement(elt, "Company");
//					});
//			});
	}

	// 
//	private Observable<NavajoStreamEvent> serializeMessage( String value) {
//		Message elt = createElement("Company");
//		elt.addProperty(createProperty("CompanyName", value));
//		return emitElement(elt, "Company");
//	}
	// TODO Name should be removed at some point


}
