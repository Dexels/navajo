package tipipackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tipi.TipiExtension;

public class TipiManualExtensionRegistry implements ITipiExtensionRegistry, Serializable {

	private static final long serialVersionUID = 3925646413574906584L;
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
			System.err.println("Registered extension: " + te.getId());
		}
	}
	
	public List<TipiExtension> getExtensionList() {
		return new ArrayList<TipiExtension>(registeredExtensions);
	}

}
