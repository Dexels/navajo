package tipi;

import java.io.Serializable;


import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.version.ExtensionDefinition;

public interface TipiExtension extends Comparable<TipiExtension>,
		ExtensionDefinition, Serializable {

	public void initialize(TipiContext tc);

	public void loadDescriptor();

}
