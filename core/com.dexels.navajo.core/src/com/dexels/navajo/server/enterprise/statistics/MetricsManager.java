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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class MetricsManager implements Mappable {

    private static final Map<String, HasMetrics> registeredModules = new HashMap<String, HasMetrics>();

    public static void addModule(String moduleName, HasMetrics hm) {
        registeredModules.put(moduleName, hm);
    }

    public Map<String, String> getModuleMetrics(String name) throws Exception {

        if (registeredModules.get(name) == null) {
            throw new Exception("Could not find metrics for module: " + name);
        }

        return registeredModules.get(name).getMetrics();
    }

    public Iterator<ModuleMetrics> getModules() {

        List<ModuleMetrics> allMetrics = new ArrayList<ModuleMetrics>();
        for (String name : registeredModules.keySet()) {
            allMetrics.add(new ModuleMetrics(name, registeredModules.get(name)));
        }

        return allMetrics.iterator();
    }

    public static String getStatus() {

        StringBuffer sb = new StringBuffer();
        sb.append("NavajoInstance : " + TribeManagerFactory.getInstance().getMyUniqueId() + "\n");

        for (String name : registeredModules.keySet()) {
            sb.append("\t Module : " + name + "\n");
            List<String> keys = new ArrayList<String>();
            Set<String> metrics = registeredModules.get(name).getMetrics().keySet();
            keys.add("_implementation");
            for (String metric : metrics) {
                keys.add(metric);
            }

            Collections.sort(keys);
            for (String key : keys) {
                if (key.equals("_implementation")) {
                    sb.append("\t\t" + key + " : " + registeredModules.get(name).getClass().getName()
                            + "\n");
                } else {
                    sb.append("\t\t" + key + " : "
                            + registeredModules.get(name).getMetrics().get(key) + "\n");
                }
            }
        }
        sb.append("\n");

        return sb.toString();
    }

    public static void removeModule(String module) {
        registeredModules.remove(module);
    }

    @Override
    public void load(Access access) throws MappableException, UserException {}

    @Override
    public void store() throws MappableException, UserException {}

    @Override
    public void kill() {}

}
