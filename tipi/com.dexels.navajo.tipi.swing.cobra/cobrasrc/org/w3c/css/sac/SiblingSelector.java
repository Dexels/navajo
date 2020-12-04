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
 * @see Selector#SAC_DIRECT_ADJACENT_SELECTOR
 */
public interface SiblingSelector extends Selector {

    public static final short ANY_NODE = 201;

    /**
     * The node type to considered in the siblings list.
     * All DOM node types are supported. In order to support the "any" node
     * type, the code ANY_NODE is added to the DOM node types.
     */
    public short getNodeType();
    
    /**
     * Returns the first selector.
     */    
    public Selector getSelector();

    /*
     * Returns the second selector.
     */    
    public SimpleSelector getSiblingSelector();
}
