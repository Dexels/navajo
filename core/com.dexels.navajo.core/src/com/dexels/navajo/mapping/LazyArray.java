/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

import com.dexels.navajo.script.api.UserException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * The interface defines the support for lazy arrays.
 * A lazy array contains elements that are not neccessarily all instantiated.
 * @deprecated
 */

public interface LazyArray {

  public int getTotalElements(String name) throws UserException;
  public int getCurrentElements(String name) throws UserException;
  public int getRemainingElements(String name) throws UserException;

  public void setStartIndex(String name, int index) throws UserException;
  public void setEndIndex(String name, int index) throws UserException;
  public void setTotalElements(String name, int index) throws UserException;

  public int getStartIndex(String name) throws UserException;
  public int getEndIndex(String name) throws UserException;

}