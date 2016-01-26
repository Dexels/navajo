package example;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

public class ClubQuery extends BaseScriptInstance {

	public void init(OutputSubscriber output) {
		
		onMessage("Club/Accommodation", (message)->{
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
		onElement("Member", (message)->{
			System.err.println("Element detected: ");
			message.write(System.err);
		});
	}

	@Override
	public void complete(OutputSubscriber output) {
		output.onNext(EventFactory.navajoDone());
	}
}