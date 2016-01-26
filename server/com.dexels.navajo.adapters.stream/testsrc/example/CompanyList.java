package example;

import com.dexels.navajo.adapters.stream.sqlmap.example.CSV;
import com.dexels.navajo.adapters.stream.sqlmap.example.TML;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

public class CompanyList extends BaseScriptInstance {

	public void complete(OutputSubscriber output) {
		
			array("Company",output,()->{
				CSV.fromClassPath("example.csv")
					.take(10)
					.map(e->e.get("company"))
					.cast(String.class)
					.map(String::toUpperCase)
					.flatMap(
						f->element("Company", m->{
							m.addProperty(createProperty("CompanyName", f));
							TML.fromClassPath("/tiny_tml.xml")
							
						})
					).subscribe(
						o->{
							output.onNext(o);
						}
					);
			});

	}

	@Override
	public void init(OutputSubscriber output) {
		
	}

}
