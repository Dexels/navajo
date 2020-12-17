/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

import javax.servlet.ServletContext;

/**
 * 
 * @author frank
 * 
 */
public interface TmlScheduler extends Scheduler {
  
    /**
     * Called first upon creation. Put any initialization code in this method.
     * We pass the servlet so you can retrieve other initParameters.
     */
    public void initialize(ServletContext servlet);
}
