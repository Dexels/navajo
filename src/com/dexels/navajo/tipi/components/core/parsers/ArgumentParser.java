package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ArgumentParser extends TipiTypeParser {
	public ArgumentParser() {
	}

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		String arg = myContext.getArgument(expression);
		System.err.println("Parsed argument: " + expression + " into: " + arg);
		return arg;
	}

	/**
	 * Replace all occurrences of the escaped characters &amp;, &quot;, &apos;,
	 * &lt; and &gt; by the unescaped characters &, ', ", < and >.
	 */

}
