

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;


import java.util.*;
import com.dexels.navajo.*;


public class Operand {

    public String type;
    public String option;
    public Object value;

    /**
     * Store a new Operand.
     * An operand is an internal Navajo representation of a value object.
     * Value contains the Java representation.
     * Type describes the Navajo type of the object.
     *
     * @param value
     * @param type
     * @param option
     */
    public Operand(Object value, String type, String option) {
        this.value = value;
        this.type = type;
        this.option = option;
    }

}
