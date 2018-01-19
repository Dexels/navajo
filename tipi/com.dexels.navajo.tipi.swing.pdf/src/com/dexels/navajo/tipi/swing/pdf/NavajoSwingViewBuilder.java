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
    public JToolBar buildAnnotationUtilityToolBar() {
        return new JToolBar(); // Annotations is not supported
    }

    

    @Override
    public JToolBar buildUtilityToolBar(boolean embeddableComponent, PropertiesManager propertiesManager) {
        return new JToolBar();
    }

  
    
    

}
