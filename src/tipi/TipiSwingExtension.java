package tipi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiSwingExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiSwingExtension() throws XMLParseException,
			IOException {
		System.err.println("Loading xml... ");
		loadXML();
	}

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}
}
