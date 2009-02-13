package tipi;

import java.util.*;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.*;

public interface TipiExtension extends Comparable<TipiExtension>, ExtensionDefinition {

	public void initialize(TipiContext tc);



}
