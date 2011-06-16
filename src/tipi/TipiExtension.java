package tipi;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.TipiContext;

public interface TipiExtension extends Comparable<TipiExtension>,
		ExtensionDefinition {

	public void initialize(TipiContext tc);

	public void loadDescriptor();

}
