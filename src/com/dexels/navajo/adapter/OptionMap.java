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


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;


public class OptionMap implements Mappable {

    public OptionMap() {}

    public void load(Parameters parm2, Navajo parm3, Access parm4, NavajoConfig config) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {}

    public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {}

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
