/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and
 * at https://www.gnu.org/licenses/agpl-3.0.txt. No part of the Navajo Project, including this file, may be copied,
 * modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.example.adapter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.UserException;

public class MonkeyAdapter implements Mappable {

    @Override
    public void load(Access access) throws MappableException, UserException {

        Navajo out = access.getOutputDoc();
        Message monkey = NavajoFactory.getInstance().createMessage(out, "Monkey");
        Binary b = new Binary(getClass().getResourceAsStream("monkey.jpeg"));
        Property p = NavajoFactory.getInstance().createProperty(out, "Monkey", Property.BINARY_PROPERTY, null, -1, "",
                Property.DIR_OUT);
        monkey.addProperty(p);
        p.setAnyValue(b);
        out.addMessage(monkey);
    }

    @Override
    public void store() throws MappableException, UserException {}

    @Override
    public void kill() {}

}
