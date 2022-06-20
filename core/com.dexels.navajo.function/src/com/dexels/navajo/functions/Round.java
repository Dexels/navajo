/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class Round extends FunctionInterface {

    @Override
    public String remarks() {
        return "With this function a floating point value can be rounded to a given number of digits. Round(2.372, 2) = 2.37";
    }

    @Override
    public String usage() {
        return "Round(float, integer).";
    }

    @Override
    public final Object evaluate() throws TMLExpressionException {

        Object a = getOperands().get(0);
        Object b = getOperands().get(1);

        try {
            Double value = (Double) a;
            Integer digits = (Integer) b;

            value = (int) Math.signum(value) * ((int) (0.5 + Math.abs(value) * Math.pow(10.0, digits)))
                    / Math.pow(10.0, digits);

            return Double.valueOf(value);
        } catch (Exception e) {
            throw new TMLExpressionException(this,
                    "Illegal type specified in Round() function: " + e.getMessage());
        }
    }

}
