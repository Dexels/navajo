/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.expression.compiled;

import java.util.Map;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GivePercentageTestFunction extends FunctionInterface {

    @Override
    public String remarks() {
        return "";
    }

    @Override
    public Object evaluate() throws TMLExpressionException {

        Object o = operand(0).value;
        if (o == null) {
            return 0L;
        }

        if (o instanceof Percentage) {
            return new Percentage(((Percentage)o).doubleValue());
        } else if (o instanceof Integer) {
            return new Percentage(o);
        } else if (o instanceof Long) {
            return new Percentage(o);
        } else if (o instanceof Float) {
            return new Percentage(o);
        } else if (o instanceof Double) {
            return new Percentage(o);
        } else {
            throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
        }
    }

    public Map<String, Operand> namedOperands() {
        return getNamedParameters();
    }

}
