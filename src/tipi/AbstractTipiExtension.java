package tipi;

public abstract class AbstractTipiExtension implements TipiExtension {
	public int compareTo(TipiExtension o) {
		return getId().compareTo(o.getId());
	}

}
