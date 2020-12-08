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
 * @see Selector#SAC_CONDITIONAL_SELECTOR
 */
public interface ConditionalSelector extends SimpleSelector {

    /**
     * Returns the simple selector.
     * <p>The simple selector can't be a <code>ConditionalSelector</code>.</p>
     */    
    public SimpleSelector getSimpleSelector();

    /**
     * Returns the condition to be applied on the simple selector.
     */    
    public Condition getCondition();
}
