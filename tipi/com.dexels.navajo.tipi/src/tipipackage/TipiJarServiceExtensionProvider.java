package tipipackage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.spi.ServiceRegistry;

import tipi.TipiExtension;

public class TipiJarServiceExtensionProvider extends TipiManualExtensionRegistry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5065605262245351282L;

	public TipiJarServiceExtensionProvider() {
		List<TipiExtension> ll = listExtensions();
		for (TipiExtension tipiExtension : ll) {
			tipiExtension.loadDescriptor();
			registerTipiExtension(tipiExtension);
		}
	}

	private List<TipiExtension> listExtensions() {
		List<TipiExtension> extensionList = new LinkedList<TipiExtension>();
		try {
			Iterator<TipiExtension> iter = ServiceRegistry
					.lookupProviders(TipiExtension.class);
			while (iter.hasNext()) {
				TipiExtension tipiExtension = iter.next();
				extensionList.add(tipiExtension);
//				tipiExtension.loadDescriptor();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return extensionList;
	}

}
