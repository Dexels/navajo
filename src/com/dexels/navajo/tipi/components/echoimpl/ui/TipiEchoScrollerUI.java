package com.dexels.navajo.tipi.components.echoimpl.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import nextapp.echoservlet.ClientInputProducer;
import nextapp.echoservlet.RenderingContext;
import nextapp.echoservlet.html.Element;
import nextapp.echoservlet.ui.PanelUI;
import echopoint.ScrollableBox;
import echopoint.ui.util.*;
import echopoint.ui.*;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;

/**
 * ScrollableBoxUI is the backend peer class for the component ScrollableBox.
 *
 * Note that this relys on CSS 2.0 support in the client browser.
 *
 * @author Brad Baker
 */
public class TipiEchoScrollerUI extends PanelUI implements PropertyChangeListener, ClientInputProducer {

        /**
          * Called when a client sends an input string through an input field.
          *
          * @param input The data in the hidden input field in the controller form
          *        as last known.
          */
        public void clientInput(String input) {
                PositioningHelper.storeScrollablePositions(input, getComponent());
        }

        /**
         * @see nextapp.echoservlet.Alignment#getHorizontalAlignment()
         */
        public int getHorizontalAlignment() {
                return ((ScrollableBox) getComponent()).getHorizontalAlignment();
        }

        /**
         * @see nextapp.echoservlet.Alignment#getVerticalAlignment()
         */
        public int getVerticalAlignment() {
                return ((ScrollableBox) getComponent()).getVerticalAlignment();
        }

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent e) {
                redraw();
        }
        /**
         * @see nextapp.echoservlet.ComponentPeer#registered()
         */
        public void registered() {
                super.registered();

                getComponent().addPropertyChangeListener(this);
        }

        /**
         *  @see nextapp.echoservlet.ComponentPeer#render(nextapp.echoservlet.RenderingContext, nextapp.echoservlet.html.Element)
         */
        public void render(RenderingContext rc, Element parent) {
                //
                // add our signature to the html
                Installer.startComment(this,parent);

                String jsSaveScript = "javascript:ep_saveScrollBarPositions('" + getId() + "',this);";
                System.err.println("In TipiEchoScroller");
                TipiEchoScroller box = (TipiEchoScroller) getComponent();
                TipiScrollableRenderer renderer = new TipiScrollableRenderer(box,this,getChildren());
                renderer.render(rc,parent,false,jsSaveScript);

                Installer.endComment(this,parent);
        }

        /**
         * @see nextapp.echoservlet.ComponentPeer#unregistered()
         */
        public void unregistered() {
                super.unregistered();

                getComponent().removePropertyChangeListener(this);
        }
        /**
         * Registers the Component with its peer bindings
         */
        public static void register() {
                nextapp.echoservlet.EchoServer.loadPeerBindings("com.dexels.navajo.tipi.components.echoimpl.ui.TipiEchoScroller");
        }
}
