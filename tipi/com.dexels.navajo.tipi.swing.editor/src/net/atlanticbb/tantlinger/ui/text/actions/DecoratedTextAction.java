/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Nov 16, 2007
 */
package net.atlanticbb.tantlinger.ui.text.actions;

import javax.swing.Action;
import javax.swing.text.TextAction;;


/**
 * @author Bob Tantlinger
 *
 */
public abstract class DecoratedTextAction extends TextAction
{
    Action delegate;
    
    public DecoratedTextAction(String name, Action delegate)
    {
        super(name);
        this.delegate = delegate;
    }
    
    public Action getDelegate()
    {
        return delegate;
    }
}
