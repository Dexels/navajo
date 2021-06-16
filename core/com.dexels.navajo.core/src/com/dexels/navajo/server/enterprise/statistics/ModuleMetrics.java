/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.server.enterprise.statistics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModuleMetrics {

    private String name;

    private HasMetrics hasMetrics;

    public ModuleMetrics(String name, HasMetrics hasMetrics) {
        this.name = name;
        this.hasMetrics = hasMetrics;
    }

    public Iterator<KeyValueMetric> getMetrics() {

        List<KeyValueMetric> metrics = new ArrayList<KeyValueMetric>();
        for (String name : hasMetrics.getMetrics().keySet()) {
            metrics.add(new KeyValueMetric(name, hasMetrics.getMetrics().get(name)));
        }

        return metrics.iterator();
    }

    public String getName() {
        return name;
    }

}
