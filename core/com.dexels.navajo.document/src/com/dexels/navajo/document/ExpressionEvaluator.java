/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Frank Lyaruu
 * @version $Id$
 */

public interface ExpressionEvaluator {
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Optional<ImmutableMessage> immutableMessage);
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage);
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Message currentParam,Selection selection, Object tipiLink, Map<String,Object> params, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage);
  public Operand evaluate(String clause, Navajo inMessage, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage);
  public Map<Property,List<Property>> createDependencyMap(Navajo n);
  public List<Property> processRefreshQueue(Map<Property,List<Property>> depMap);
}
