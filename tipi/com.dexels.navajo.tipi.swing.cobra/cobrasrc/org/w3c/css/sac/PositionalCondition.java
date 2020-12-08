/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * (c) COPYRIGHT 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id$
 */
package org.w3c.css.sac;

/**
 * @version $Revision$
 * @author  Philippe Le Hegaret
 * @see Condition#SAC_POSITIONAL_CONDITION
 */
public interface PositionalCondition extends Condition {

    /**
     * Returns the position in the tree.
     * <p>A negative value means from the end of the child node list.
     * <p>The child node list begins at 0.
     */    
    public int getPosition();

    /**
     * <code>true</code> if the child node list only shows nodes of the same
     * type of the selector (only elements, only PIS, ...)
     */
    public boolean getTypeNode();

    /**
     * <code>true</code> if the node should have the same node type (for
     *  element, same namespaceURI and same localName).
     */
    public boolean getType();
}
