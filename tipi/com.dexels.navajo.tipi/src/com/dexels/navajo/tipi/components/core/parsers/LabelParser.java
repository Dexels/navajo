/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.DescriptionProvider;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

public class LabelParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1158955291783039066L;

	public LabelParser() {
	}

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		DescriptionProvider dp = source.getContext().getDescriptionProvider();

		if (dp == null) {

			return "[" + expression + "]";
		} else {
			return source.getContext().XMLUnescape(dp.getDescription(expression));
		}
	}

	/**
	 * Replace all occurrences of the escaped characters &amp;, &quot;, &apos;,
	 * &lt; and &gt; by the unescaped characters &, ', ", < and >.
	 */

}
