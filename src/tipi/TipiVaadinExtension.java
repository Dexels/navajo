package tipi;

import java.io.IOException;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiVaadinExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiVaadinExtension() throws XMLParseException,
			IOException {
		System.err.println("Loading xml... ");
		loadXML();
	}

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}
}
