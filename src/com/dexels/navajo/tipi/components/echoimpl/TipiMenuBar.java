package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.extras.app.MenuBarPane;
import nextapp.echo2.extras.app.menu.DefaultMenuModel;
import nextapp.echo2.extras.app.menu.DefaultOptionModel;
import nextapp.echo2.extras.app.menu.DefaultRadioOptionModel;
import nextapp.echo2.extras.app.menu.DefaultToggleOptionModel;
import nextapp.echo2.extras.app.menu.ItemModel;
import nextapp.echo2.extras.app.menu.MenuModel;
import nextapp.echo2.extras.app.menu.SeparatorModel;
import echopointng.MenuBar;

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

public class TipiMenuBar extends TipiEchoDataComponentImpl {
    private DefaultMenuModel defaultMenuModel;

	public TipiMenuBar() {
    }

    public Object createContainer() {
    	
//        MenuBar b = new MenuBar();
        MenuBarPane b = new MenuBarPane();
        defaultMenuModel = new DefaultMenuModel();
		//        b.setStyleName("Default");
//        b.setTopOffset(0);
//        DefaultMenuModel fileMenuModel = new DefaultMenuModel(null, "File");
//      fileMenuModel.addItem(new DefaultOptionModel("new", "New", null));
//      fileMenuModel.addItem(new DefaultOptionModel("open", "Open", null));
//      DefaultMenuModel openRecentMenuModel = new DefaultMenuModel(null, "Open Recent");
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-1", "Hotel.pdf", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-2", "Alpha.txt", null));

      
      b.setModel(defaultMenuModel);
      b.setProperty("tipi", this);
      b.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			String path = e.getActionCommand();
//			String pp = path.substring("component:/".length(),path.length());
			System.err.println("EVENT::::::: "+path);
			TipiComponent tc = myContext.getTipiComponentByPath(path);
			if(tc instanceof TipiMenuItem) {
				System.err.println("Alllright!");
				TipiMenuItem tmi = (TipiMenuItem)tc;
				try {
					tmi.performAction();
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}
		}});   	  
      
//      defaultMenuModel.addItem(fileMenuModel);
//      defaultMenuModel.addItem(openRecentMenuModel);

      return b;
    }

    protected void setComponentValue(String name, Object object) {
//    	MenuBarPane b = (MenuBarPane) getContainer();
//    	DefaultMenuModel menuModel = new DefaultMenuModel();
        super.setComponentValue(name, object);
    }

	public void addToContainer(Object c, Object constraints) {

		
		defaultMenuModel.addItem((ItemModel)c);
	}  
}
