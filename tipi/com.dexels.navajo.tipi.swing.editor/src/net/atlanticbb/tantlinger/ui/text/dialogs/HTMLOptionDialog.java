/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Mar 4, 2005
 *
 */
package net.atlanticbb.tantlinger.ui.text.dialogs;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.Icon;

import net.atlanticbb.tantlinger.ui.OptionDialog;



/**
 * An abstract OptionDialog for HTML editor dialog boxes.
 * 
 * Subclasses should implement dialogs for inserting HTML elements
 * such as tables, links, images, etc.
 * 
 * @author Bob Tantlinger
 *
 */
public abstract class HTMLOptionDialog extends OptionDialog
{    
    public HTMLOptionDialog(Frame parent, String title, String desc, Icon ico)
    {
        super(parent, title, desc, ico);        
    }
    
    public HTMLOptionDialog(Dialog parent, String title, String desc, Icon ico)
    {
        super(parent, title, desc, ico);        
    }    
    
    /**
     * Gets the generated HTML from the dialog
     * 
     * @return the HTML
     */
    public abstract String getHTML();
}
