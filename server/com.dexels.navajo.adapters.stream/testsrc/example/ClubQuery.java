package example;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

public class ClubQuery extends BaseScriptInstance {

	public void init() {
		
		onMessage("Club/Accommodation", (message,output)->{
			output.onNext(EventFactory.navajoStarted());
			array("Company",output,()->{
				CSV.fromClassPath("example.csv")
					.map(e->e.get("company"))
					.cast(String.class)
					.flatMap(f->element("Company", m->{
						Navajo nav = NavajoFactory.getInstance().createNavajo();
						Property prop = NavajoFactory.getInstance().createProperty(nav, "CompanyName", Property.STRING_PROPERTY, f, 32, "", Property.DIR_OUT);
						m.addProperty(prop);
					}))
					.subscribe(o->output.onNext(o));
			});
		});
		onElement("Member", (message,output)->{
			System.err.println("Element detected: ");
			message.write(System.err);
		});
	}

	private void array(String name, OutputSubscriber output, Action0 action) {
		output.onNext(EventFactory.arrayStarted(NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(),name,Message.MSG_TYPE_ARRAY),"Company" ));
		action.call();
		output.onNext(EventFactory.arrayDone(NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(),"Company",Message.MSG_TYPE_ARRAY),"Company" ));
	}
	
	private Observable<NavajoStreamEvent> element(String name,Action1<Message> action) {
		Navajo nav = NavajoFactory.getInstance().createNavajo();
		Message element = NavajoFactory.getInstance().createMessage(nav,name,Message.MSG_TYPE_ARRAY_ELEMENT);
		action.call(element);
		return  Observable.just(EventFactory.arrayElementStarted(element, name), EventFactory.arrayElement(element, name)); //EventFactory.arrayElement(element, "Company");
		
	}

	@Override
	public void complete(OutputSubscriber output) {
		output.onNext(EventFactory.navajoDone());
	}
}