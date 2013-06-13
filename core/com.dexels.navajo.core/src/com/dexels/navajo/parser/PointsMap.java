package com.dexels.navajo.parser;

import java.util.Vector;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class PointsMap implements Mappable {

    public Vector<Integer>[] myPoints;
    public String myString;

    public PointsMap() {}

    @SuppressWarnings("unchecked")
	public void load(Access access) throws MappableException {
        myPoints = new Vector[2];
        myPoints[0] = new Vector<Integer>();
        myPoints[0].add(new Integer(0));
        myPoints[0].add(new Integer(2));
        myPoints[1] = new Vector<Integer>();
        myPoints[1].add(new Integer(10));
        myPoints[1].add(new Integer(20));
        this.myString = "albert";
    }

    public Vector[] getMyPoints() {
        return this.myPoints;
    }

    public String getMyString() {
        return this.myString;
    }

    public void store() throws MappableException, UserException {}

    public void kill() {}

}
