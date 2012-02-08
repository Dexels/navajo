package navajolisteners;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

public class NavajoContextFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Navajo Context Factory";
	}

	@Override
	public void updated(String arg0, Dictionary arg1)
			throws ConfigurationException {
		// TODO Auto-generated method stub

	}

}
