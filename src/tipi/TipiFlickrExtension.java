package tipi;

public class TipiYoutubeExtension implements TipiExtension {

	public String getDescription() {
		return "Youtube extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/youtube/actions/actiondef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}

	@Override
	public String getId() {
		return "youtube";
	}

	@Override
	public String requiresMainImplementation() {
		return null;
	} 

}
