package tipipackage;

import java.util.LinkedList;
import java.util.List;

import tipi.TipiExtension;

public class TipiExtensionRegistry implements ITipiExtensionRegistry {

	private final List<TipiExtension> registeredExtensions = new LinkedList<TipiExtension>();
	@Override
	public void registerTipiExtension(TipiExtension te) {
		registeredExtensions.add(te);
	}

	@Override
	public void loadExtensions(ITipiExtensionContainer context) {
		for (TipiExtension te : registeredExtensions) {
			context.addOptionalInclude(te);
		}
	}

	@Override
	public void debugExtensions() {
		for (TipiExtension te : registeredExtensions) {
			System.err.println("Registered extension: "+te.getId());
		}
	}

}
