/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.parser.compiled;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.compiled.GiveLongTestFunction;
import com.dexels.navajo.expression.compiled.GiveMoneyTestFunction;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.Expression;

public class TestCompiledMoneyExpression {

    @Before
    public void setup() {

        FunctionInterface giveLongFunction = new GiveLongTestFunction();
        FunctionDefinition fd = new FunctionDefinition(giveLongFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToLong", fd);

        FunctionInterface giveMoneyFunction = new GiveMoneyTestFunction();
        fd = new FunctionDefinition(giveMoneyFunction.getClass().getName(),
                "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ToMoney", fd);
    }

    @Test
    public void testAddWithMoney() {

        Operand result;
        result = Expression.evaluate("ToMoney(15) + ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(25), result.value);

        result = Expression.evaluate("15 + ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(25), result.value);

        result = Expression.evaluate("ToMoney(15) + 10", null, null, null);
        Assert.assertEquals(new Money(25), result.value);

        result = Expression.evaluate("ToLong(15) + ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(25), result.value);

        result = Expression.evaluate("ToMoney(15) + ToLong(10)", null, null, null);
        Assert.assertEquals(new Money(25), result.value);

        result = Expression.evaluate("15.0 + ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(25), result.value);

        result = Expression.evaluate("ToMoney(15) + 10.0", null, null, null);
        Assert.assertEquals(new Money(25), result.value);
    }

    @Test
    public void testSubtractWithMoney() {

        Operand result;
        result = Expression.evaluate("ToMoney(15) - ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(5), result.value);

        result = Expression.evaluate("15 - ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(5), result.value);

        result = Expression.evaluate("ToMoney(15) - 10", null, null, null);
        Assert.assertEquals(new Money(5), result.value);

        result = Expression.evaluate("ToLong(15) - ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(5), result.value);

        result = Expression.evaluate("ToMoney(15) - ToLong(10)", null, null, null);
        Assert.assertEquals(new Money(5), result.value);

        result = Expression.evaluate("15.0 - ToMoney(10)", null, null, null);
        Assert.assertEquals(new Money(5), result.value);

        result = Expression.evaluate("ToMoney(15) - 10.0", null, null, null);
        Assert.assertEquals(new Money(5), result.value);
    }

}
