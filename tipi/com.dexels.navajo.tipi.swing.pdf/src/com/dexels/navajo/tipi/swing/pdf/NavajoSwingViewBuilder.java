/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swing.pdf;


import javax.swing.JToolBar;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

public class NavajoSwingViewBuilder extends SwingViewBuilder{

    public NavajoSwingViewBuilder(SwingController c) {
        super(c);
    }

    @Override
    public JToolBar buildAnnotationlToolBar() {
        return new JToolBar(); // Disable
    }

    @Override
    public JToolBar buildUtilityToolBar(boolean embeddableComponent, PropertiesManager propertiesManager) {
        return new JToolBar(); // Disable
    }
    @Override
    public JToolBar buildFormsToolBar() {
        return new JToolBar(); // Disable
    }

  
    
    

}
