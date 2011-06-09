package tipi;

import java.io.IOException;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiEchoExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiEchoExtension() throws XMLParseException, IOException {
		loadXML();
	}

	public void initialize(TipiContext tc) {
		// Do nothing

	}

}
