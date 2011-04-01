package tipi;

import com.dexels.navajo.tipi.TipiContext;

public class TipiCoreExtension extends TipiAbstractXMLExtension  {

	public TipiCoreExtension() {
		super();
		loadXML("TipiExtension.xml");
	}

	public void initialize(TipiContext tc) {
		// Do nothing

	}
	


public static void main(String[] args) {
	new TipiCoreExtension();
}

}
