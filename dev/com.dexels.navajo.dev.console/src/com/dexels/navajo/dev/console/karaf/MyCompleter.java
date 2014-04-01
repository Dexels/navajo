/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.dexels.navajo.dev.console.karaf;

import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;

/**
* <p>
* My completer.
* </p>
*/

public class MyCompleter implements Completer {

    /**
     * @param buffer it's the beginning string typed by the user
     * @param cursor it's the position of the cursor
     * @param candidates the list of completions proposed to the user
     */
    @Override
	public int complete(String buffer, int cursor, List<String> candidates) {

        StringsCompleter delegate = new StringsCompleter();
        delegate.getStrings().add("one");
        delegate.getStrings().add("two");
        delegate.getStrings().add("three");
        return delegate.complete(buffer, cursor, candidates);
    }
}
