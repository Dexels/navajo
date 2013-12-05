package tipipackage.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tipi.TipiExtension;
import tipipackage.TipiExtensionProvider;

public class TipiExtensionProviderImpl implements TipiExtensionProvider {

	private final List<TipiExtension> tipiExtensions = new ArrayList<TipiExtension>();
	
	public void addTipiExtension(TipiExtension te) {
		tipiExtensions.add(te);
	}

	public void removeTipiExtension(TipiExtension te) {
		tipiExtensions.remove(te);
		
	}

	@Override
	public List<TipiExtension> getExtensionList() {
		return Collections.unmodifiableList(tipiExtensions);
	}

}
