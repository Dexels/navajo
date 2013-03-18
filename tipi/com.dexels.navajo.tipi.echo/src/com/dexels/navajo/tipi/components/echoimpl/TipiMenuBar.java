package com.dexels.navajo.tipi.components.echoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.extras.app.MenuBarPane;
import nextapp.echo2.extras.app.menu.DefaultMenuModel;
import nextapp.echo2.extras.app.menu.ItemModel;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;


public class TipiMenuBar extends TipiEchoDataComponentImpl {
	private static final long serialVersionUID = -3747839523676117308L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMenuBar.class);
	
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

		private static final long serialVersionUID = -4082030215532682456L;

		public void actionPerformed(ActionEvent e) {
			String path = e.getActionCommand();
//			String pp = path.substring("component:/".length(),path.length());
			logger.info("EVENT::::::: "+path);
			TipiComponent tc = myContext.getTipiComponentByPath(path);
			if(tc instanceof TipiMenuItem) {
				logger.info("Alllright!");
				TipiMenuItem tmi = (TipiMenuItem)tc;
				try {
					tmi.performAction();
				} catch (TipiException e1) {
					logger.error("Error: ", e1);
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
