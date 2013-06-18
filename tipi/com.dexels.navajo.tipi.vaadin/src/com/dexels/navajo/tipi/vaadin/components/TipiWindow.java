package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class TipiWindow extends TipiVaadinComponentImpl{

	private static final Logger logger = LoggerFactory.getLogger(TipiWindow.class);
	private static final long serialVersionUID = -4874496951046547933L;
	private Window window;

	@Override
	public Object createContainer() {
		VerticalLayout componentContainer = new VerticalLayout();
		componentContainer.setSizeFull();
		componentContainer.setMargin(true);
		window = new Window("",componentContainer);
		window.setPositionX(30);
		window.setPositionY(30);
		window.setScrollable(true);
		//		window.setWidth(200, Sizeable.UNITS_PIXELS);
//		window.setHeight(200, Sizeable.UNITS_PIXELS);

//		window.addListener(new Window.ResizeListener() {
//			
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void windowResized(ResizeEvent e) {
//				if(getLayout()!=null) {
//					Component cc = (Component) getLayout().getLayout();
//					cc.setWidth (e.getWindow().getWidth(),e.getWindow().getWidthUnits());
//					cc.setHeight (e.getWindow().getHeight(),e.getWindow().getHeightUnits());
//				}
//			}
//		});
		window.addListener(new CloseListener() {

			private static final long serialVersionUID = -5365729943023879264L;

			@Override
            public void windowClose(CloseEvent e) {

            	try	{
            		try {
            			performTipiEvent("onWindowClosed", null, true);
            		} catch (TipiException e1) {
            			logger.error("Exception at onWindowClosed, ignoring...", e1);
            		}
            		myContext.disposeTipiComponent(TipiWindow.this);
        		} catch (TipiBreakException e1) {
					if (e1.getType() == TipiBreakException.COMPONENT_DISPOSED) {
						myContext.disposeTipiComponent(TipiWindow.this);
					}
        		}
            }
        });
		getVaadinApplication().getMainWindow().addWindow(window);
		return window;
	}

	
	
	
	  @Override
	public void disposeComponent() {
		super.disposeComponent();
		getVaadinApplication().getMainWindow().removeWindow(window);
	}




	@Override
	public void addToContainer(Object c, Object constraints) {
		  
		  super.addToContainer(c, constraints);
		  Component cc = (Component)c;
		  cc.setWidth("100%");
	  }



	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
		        if (name.equals("title")) {
		          window.setCaption( (String) object);
		        }
		        if ("icon".equals(name)) {
		        	window.setIcon( getResource(object));
		        }
		        if ("h".equals(name)) {
		        	window.setHeight(""+object+"px");
		        }
		        if ("w".equals(name)) {
		        	window.setWidth(""+object+"px");
		        }
		        if ("x".equals(name)) {
		        	window.setPositionX((Integer) object);
		        }
		        if ("y".equals(name)) {
		        	window.setPositionY((Integer) object);
		        }
		        if ("visible".equals(name)) {
		        	window.setVisible((Boolean) object);
		        }
		        if ("closable".equals(name)) {
		        	window.setClosable((Boolean) object);
		        }
		        if ("resizable".equals(name)) {
		        	window.setResizable((Boolean) object);
		        }
		        if ("selected".equals(name)) {
		        	if(window.getParent()!=null) {
			        	window.bringToFront();
		        	}
		        }

		       
		  }
	

}
