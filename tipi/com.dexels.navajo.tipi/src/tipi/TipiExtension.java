package tipi;

import java.io.Serializable;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.TipiContext;

public interface TipiExtension extends Comparable<TipiExtension>,
		ExtensionDefinition, Serializable {

	public void initialize(TipiContext tc);

	public void loadDescriptor();

}
