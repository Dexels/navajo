package tipipackage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.spi.ServiceRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiExtension;

public class TipiJarServiceExtensionProvider extends TipiManualExtensionRegistry {

	private static final long serialVersionUID = 5065605262245351282L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiJarServiceExtensionProvider.class);
	
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
			logger.error("Error: ",e);
		}
		return extensionList;
	}

}
