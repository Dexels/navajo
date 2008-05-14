package tipi;

public class TipiBatikExtension implements TipiExtension {

	public String getDescription() {
		return "Batik extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/swing/svgclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}

	@Override
	public String getId() {
		return "batik";
	}

	@Override
	public String requiresMainImplementation() {
		return "swing";
	} 

}
