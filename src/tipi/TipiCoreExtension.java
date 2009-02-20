package tipi;

import com.dexels.navajo.tipi.TipiContext;

public class TipiCoreExtension extends TipiAbstractXMLExtension  {

	public TipiCoreExtension() {
		// bit of
		super();
		System.err.println("Loading core xml");
		loadXML("TipiExtension.xml");
		System.err.println("Core xml loaded!");
	}

	public void initialize(TipiContext tc) {
		// Do nothing

	}


public static void main(String[] args) {
	new TipiCoreExtension();
}

}
