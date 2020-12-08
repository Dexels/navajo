/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * @(#)DelegateFocusTraversalPolicy.java
 *
 * $Date: 2014-03-13 04:15:48 -0400 (Thu, 13 Mar 2014) $
 *
 * Copyright (c) 2011 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.dexels.navajo.tipi.swing.colorpicker;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

/**
 * A simple <code>FocusTraversalPolicy</code> object that delegates to another object.
 *
 */
public class DelegateFocusTraversalPolicy extends FocusTraversalPolicy {
    FocusTraversalPolicy ftp;

    public DelegateFocusTraversalPolicy(FocusTraversalPolicy policy) {
        ftp = policy;
    }

    @Override
    public Component getComponentAfter(Container focusCycleRoot, Component component) {
        return ftp.getComponentAfter(focusCycleRoot, component);
    }

    @Override
    public Component getComponentBefore(Container focusCycleRoot, Component component) {
        return ftp.getComponentBefore(focusCycleRoot, component);
    }

    @Override
    public Component getDefaultComponent(Container focusCycleRoot) {
        return ftp.getDefaultComponent(focusCycleRoot);
    }

    @Override
    public Component getFirstComponent(Container focusCycleRoot) {
        return ftp.getFirstComponent(focusCycleRoot);
    }

    @Override
    public Component getLastComponent(Container focusCycleRoot) {
        return ftp.getLastComponent(focusCycleRoot);
    }
}
