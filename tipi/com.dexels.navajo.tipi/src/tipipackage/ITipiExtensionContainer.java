package tipipackage;

import tipi.TipiExtension;

public interface ITipiExtensionContainer {
	public void addOptionalInclude(TipiExtension te);

	public void setSystemProperty(String key, String value);

}
