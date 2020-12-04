/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.echo.actions;

import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoExit extends TipiAction {

	private static final long serialVersionUID = 3291088681688700176L;

	public TipiEchoExit() {
    }

    public void execute(TipiEvent e) {
        //Operand destination = getEvaluatedParameter("destination", e);
        //String dest = ((EchoTipiContext) myContext).getDynamicResourceBaseUrl();
        ((EchoTipiContext) myContext).exit();
    }

}
