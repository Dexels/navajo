package tipi;

public class TipiJabberExtension implements TipiExtension {

	public String getDescription() {
		return "Jabber extensions. Swing component for now, can easily be refactored to core";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/xmmp/xmmpclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}

	@Override
	public String getId() {
		
		return "jabber";
	}

	@Override
	public String requiresMainImplementation() {
		// TODO Auto-generated method stub
		return "swing";
	} 

}
