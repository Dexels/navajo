package tipipackage;

import java.util.List;

import tipi.TipiExtension;

public interface ITipiExtensionRegistry {
	public void registerTipiExtension(TipiExtension te);

	public void loadExtensions(ITipiExtensionContainer context);

	public void debugExtensions();

	public List<TipiExtension> getExtensionList();
}
