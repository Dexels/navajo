/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 *
 * $Id$
 *
 * $Log$
 * Revision 1.4  2012/03/20 10:43:15  frank
 * code improvements / cleaning
 *
 * Revision 1.3  2008/09/29 10:33:28  arjen
 * Several changes for Navajo Refactor (The NavaUnitTest Version)
 *
 * Revision 1.2  2005/06/04 08:43:52  arjen
 * Removed redundant imports.
 *
 * Revision 1.1.1.1  2003/04/16 14:09:33  arjen
 *
 *
 * Revision 1.5  2003/03/10 16:12:35  arjen
 * Post interfaced document version
 *
 * Revision 1.4  2002/11/06 09:33:46  arjen
 * Used Jacobe code beautifier over all source files.
 * Added log4j support.
 *
 * Revision 1.3  2002/09/25 12:05:59  arjen
 * *** empty log message ***
 *
 * Revision 1.2  2002/09/09 16:07:55  arjen
 * <No Comment Entered>
 *
 *
 */


import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


public class OptionMap implements Mappable {

    public OptionMap() {}

@Override
    public void load(Access parm4) throws UserException, MappableException {}

@Override
    public void store() throws UserException, MappableException {}

    @Override
	public void kill() {}

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public void setOptionSelected(boolean optionSelected) {
        this.optionSelected = optionSelected;
    }

    public boolean getOptionSelected() {
        return optionSelected;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
    public String optionName;
    public String optionValue;
    public boolean optionSelected;
}
