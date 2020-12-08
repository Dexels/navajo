/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.echo.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiOptionPane;
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
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoShowQuestion extends TipiAction {

	private static final long serialVersionUID = 3346311371242713545L;

	public TipiEchoShowQuestion() {
    }

    protected void execute(TipiEvent e) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
        Operand text = getEvaluatedParameter("text", e);
        // TipiScreen s = (TipiScreen) myContext.getDefaultTopLevel();
        // Window w = (Window) s.getTopLevel();
        // ContentPane old = w.getContent();
        TipiOptionPane.showQuestion(myContext, (String) text.value, "Info:", "Yes", "No");
    }

}
