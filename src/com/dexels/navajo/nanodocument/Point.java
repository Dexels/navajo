package com.dexels.navajo.nanodocument;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface Point {

   public void setValue(String s);

    public void setValue(int position, String s);

    public String getValue(int position);

    public int getSize();
}