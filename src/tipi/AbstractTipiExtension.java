package tipi;

import java.io.InputStream;

abstract class AbstractTipiExtension extends TipiAbstractOSGiExtension
		implements TipiExtension {

	@Override
	public int compareTo(TipiExtension o) {
		return getId().compareTo(o.getId());
	}

	public final InputStream getDefinitionAsStream() {
		String[] includes = getIncludes();
		if (includes.length == 0) {
			System.err
					.println("Can not getDefinitionStream: no includes defined!");
		}
		if (includes.length > 1) {
			System.err
					.println("Can not getDefinitionStream: multiple includes defined!");

		}
		return getClass().getResourceAsStream(includes[0]);

	}
}
