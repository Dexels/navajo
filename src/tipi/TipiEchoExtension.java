package tipi;

import java.io.IOException;
import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiEchoExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiEchoExtension() throws XMLParseException, IOException {
		loadXML();
	}

	public void initialize(TipiContext tc) {
		// Do nothing

	}

}
