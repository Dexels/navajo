/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

import java.util.Collections;

/**
 * Mocks the CompiledScriptFactory even when no java has been created 
 *
 */
public class CompiledScriptFactoryMock extends CompiledScriptFactory
{

    private String scriptName;
    
    public CompiledScriptFactoryMock( String name )
    {
        this.scriptName = name;
        activate( Collections.singletonMap( "navajo.scriptName", name ) );
    }
    
    @Override
    public CompiledScriptInterface getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        // This might need to return something (a Mock probably), but for now this seems to work
        return null;
    }

    @Override
    protected String getScriptName()
    {
        return scriptName;
    }

}
