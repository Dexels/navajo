package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import echopointng.image.URLImageReference;

import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.extras.app.MenuBarPane;
import nextapp.echo2.extras.app.menu.DefaultMenuModel;
import nextapp.echo2.extras.app.menu.DefaultOptionModel;
import nextapp.echo2.extras.app.menu.DefaultRadioOptionModel;
import nextapp.echo2.extras.app.menu.DefaultToggleOptionModel;
import nextapp.echo2.extras.app.menu.ItemModel;
import nextapp.echo2.extras.app.menu.MenuModel;
import nextapp.echo2.extras.app.menu.SeparatorModel;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiMenu extends TipiEchoDataComponentImpl {
    public TipiMenu() {
    }

    public Object createContainer() {
    	DefaultMenuModel b = new DefaultMenuModel();
    	//        b.setStyleName("Default");
        return b;
    }

    protected void setComponentValue(String name, Object object) {
    	DefaultMenuModel b = (DefaultMenuModel) getContainer();
        if ("text".equals(name)) {
            b.setText("" + object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
                b.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
        super.setComponentValue(name, object);
    }

	public void addToContainer(Object c, Object constraints) {
		ItemModel cc = (ItemModel)c;
		DefaultMenuModel b = (DefaultMenuModel) getContainer();
        b.addItem(cc);
	}

    
//  
//  private MenuModel createMenuModel() {
//      DefaultMenuModel menuModel = new DefaultMenuModel();
//      
//      DefaultMenuModel fileMenuModel = new DefaultMenuModel(null, "File");
//      fileMenuModel.addItem(new DefaultOptionModel("new", "New", null));
//      fileMenuModel.addItem(new DefaultOptionModel("open", "Open", null));
//      DefaultMenuModel openRecentMenuModel = new DefaultMenuModel(null, "Open Recent");
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-1", "Hotel.pdf", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-2", "Alpha.txt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-3", "q4-earnings.txt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-4", "Bravo.odt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-5", "Golf.pdf", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-6", "Alpha.txt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-7", "q3-earnings.txt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-8", "Charlie.odt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-9", "XYZ.pdf", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-10", "Delta.txt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-11", "q1-earnings.txt", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-12", "Foxtrot.odt", null));
//      fileMenuModel.addItem(openRecentMenuModel);
//
//      DefaultMenuModel openFrequentMenuModel = new DefaultMenuModel(null, "Open Frequently Used");
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-1", "q2-earnings.txt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-2", "q3-earnings.txt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-3", "q4-earnings.txt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-4", "Bravo.odt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-5", "Golf.pdf", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-7", "q1-earnings.txt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-8", "Charlie.odt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-9", "XYZ.pdf", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-10", "CharlieBravo.txt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-11", "q1-earnings.txt", null));
//      openFrequentMenuModel.addItem(new DefaultOptionModel("open-recent-12", "Foxtrot.odt", null));
//      fileMenuModel.addItem(openFrequentMenuModel);
//      
//      fileMenuModel.addItem(new SeparatorModel());
//      fileMenuModel.addItem(new DefaultOptionModel("save", "Save", null));
//      fileMenuModel.addItem(new DefaultOptionModel("save-as", "Save as...", null));
//      menuModel.addItem(fileMenuModel);
//      
//      DefaultMenuModel optionsMenuModel = new DefaultMenuModel(null, "Options");
//      optionsMenuModel.addItem(new DefaultOptionModel("load-preferences", "Load Preferences...", null));
//      optionsMenuModel.addItem(new DefaultOptionModel("save-preferences", "Save Preferences...", null));
//      optionsMenuModel.addItem(new SeparatorModel());
//      optionsMenuModel.addItem(new DefaultToggleOptionModel("abc", "Enable ABC"));
//      optionsMenuModel.addItem(new DefaultToggleOptionModel("def", "Enable DEF"));
//      optionsMenuModel.addItem(new DefaultToggleOptionModel("ghi", "Enable GHI"));
//      optionsMenuModel.addItem(new SeparatorModel());
//      optionsMenuModel.addItem(new DefaultOptionModel("disabled1", "Disabled Option", null));
//      optionsMenuModel.addItem(new DefaultToggleOptionModel("disabled2", "Disabled Toggle"));
//      optionsMenuModel.addItem(new DefaultToggleOptionModel("def", "Enable DEF"));
//      optionsMenuModel.addItem(new SeparatorModel());
//      optionsMenuModel.addItem(new DefaultRadioOptionModel("foo1", "foomode", "Foo Mode 1"));
//      optionsMenuModel.addItem(new DefaultRadioOptionModel("foo2", "foomode", "Foo Mode 2"));
//      optionsMenuModel.addItem(new DefaultRadioOptionModel("foo3", "foomode", "Foo Mode 3"));
//      optionsMenuModel.addItem(new DefaultRadioOptionModel("foo4", "foomode", "Foo Mode 4"));
//      optionsMenuModel.addItem(new SeparatorModel());
//      optionsMenuModel.addItem(new DefaultRadioOptionModel("bar1", "barmode", "Bar Mode 1"));
//      optionsMenuModel.addItem(new DefaultRadioOptionModel("bar2", "barmode", "Bar Mode 2"));
//      menuModel.addItem(optionsMenuModel);
//      return menuModel;
//  }
//  
  
}
