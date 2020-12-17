/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class StringDistance 
    extends FunctionInterface {

//  public void prototype(String s1, String s2) {}

  public StringDistance() {}

  @Override
public String remarks() {
    return "Returns an Integer representing how much the two strings differ, 0 is equal";
  }

  @Override
public String usage() {
    return "Usage: StringDistance([String1], [String2])";
  }

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    String s1 = (String)this.getOperands().get(0);
    String s2 = (String)this.getOperands().get(1);

    int cost;
    char[] c1 = (" "+s1.toUpperCase()).toCharArray();
    char[] c2 = (" "+s2.toUpperCase()).toCharArray();
    int[][] d = new int[c1.length][c2.length];

    for (int i = 0; i < c1.length; i++) {
      d[i][0] = i;
    }
    for (int j = 0; j < c2.length; j++) {
      d[0][j] = j;
    }

    for (int i = 1; i < c1.length; i++) {
      for (int j = 1; j < c2.length; j++) {
        if (c1[i] == c2[j]) {
          cost = 0;
        }
        else {
          cost = 1;
        }
        d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1),
                           d[i - 1][j - 1] + cost);
        if (i > 1 && j > 1 && c1[i] == c2[j - 1] && c1[i - 1] == c2[j]) {
          d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + cost);
        }
      }
    }

    return Integer.valueOf(d[c1.length - 1][c2.length - 1]);

  }
}
