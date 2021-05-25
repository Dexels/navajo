/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class UtilsDateTest {

    @Test
    public void testSubtractDatesWith24HoursDifference() throws Exception {

        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        b.add(Calendar.DAY_OF_YEAR, 1);

        Object result = Utils.subtract(a.getTime(), b.getTime());

        assertEquals("-1", result.toString());
    }

    @Test
    public void testSubtractDatesLessThan24HoursDifference() throws Exception {

        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        b.add(Calendar.DAY_OF_YEAR, 1);
        b.add(Calendar.HOUR_OF_DAY, -3);

        Object result = Utils.subtract(a.getTime(), b.getTime());

        assertEquals("0", result.toString());
    }

    @Test
    public void testSubtractDatesMoreThan24HoursDifference() throws Exception {

        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        b.add(Calendar.DAY_OF_YEAR, 1);
        b.add(Calendar.HOUR_OF_DAY, 3);

        Object result = Utils.subtract(a.getTime(), b.getTime());

        assertEquals("-1", result.toString());
    }

    @Test
    public void testSubtractDatesInWinterTime() throws Exception {

        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        a.set(2009, 0, 2, 0, 0, 0);
        a.set(Calendar.MILLISECOND, 0);
        b.set(2009, 0, 28, 0, 0, 0);
        b.set(Calendar.MILLISECOND, 0);

        int result = (Integer) Utils.subtract(a.getTime(), b.getTime());

        assertEquals(-26, result);
    }

    @Test
    public void testSubtractDatesInWinterAndSummerTime() throws Exception {

        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        a.set(2009, 2, 2, 0, 0, 0);
        a.set(Calendar.MILLISECOND, 0);
        b.set(2009, 2, 30, 0, 0, 0);
        b.set(Calendar.MILLISECOND, 0);

        int result = (Integer) Utils.subtract(a.getTime(), b.getTime());

        assertEquals(-28, result);
    }

}
