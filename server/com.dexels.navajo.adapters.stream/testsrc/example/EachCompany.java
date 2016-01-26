package example;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

public class EachCompany extends BaseScriptInstance {

	public void init(OutputSubscriber output) {
		onElement("Company", m->{
			Property p = m.getProperty("CompanyName");
			System.err.print(":: "+p.getValue());
		});
	}


	@Override
	public void complete(OutputSubscriber output) {
		onElement("Company", m->{
			Property p = m.getProperty("CompanyName");
			System.err.print("::| "+p.getValue());
		});
		output.onNext(EventFactory.navajoDone());
	}
}