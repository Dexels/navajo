package com.dexels.navajo.tipi.components.echoimpl.ui;

import java.io.Serializable;

import nextapp.echo.EchoConstants;
import nextapp.echoservlet.ComponentPeer;
import nextapp.echoservlet.ComponentStyle;
import nextapp.echoservlet.RenderingContext;
import nextapp.echoservlet.html.Element;
import nextapp.echoservlet.html.ElementNames;

import echopoint.layout.LayoutManageable;
import echopoint.positionable.ScrollableContainer;
import echopoint.ui.layout.LayoutManagerPeer;
import echopoint.ui.layout.LayoutManagerPeerFactory;
import echopoint.ui.util.*;
import echopoint.ui.util.ToolTipPopUpSupportHelper;

/**
 * ScrollableRenderer will render a ScrollablePanelUI for
 * a backend peer class of a the component that implements
 * ScrollablePanel.
 * <p>
 * Note that this relys on CSS 2.0 support in the client browser.
 * <p>
 * This will use layout managers if the panel is in fact
 * LayoutManageable.
 *
 * @author Brad Baker
 */
public class TipiScrollableRenderer implements Serializable  {

        private ScrollableContainer scrollable = null;
        private ComponentPeer panelPeer = null;
        private ComponentPeer[] children = null;
        private ToolTipPopUpSupportHelper toolTipHelper;


        /**
         *
         * Constructs a <code>ScrollableRenderer</code>
         *
         */
        public TipiScrollableRenderer(ScrollableContainer scrollable, ComponentPeer panelPeer, ComponentPeer[] children) {
                this.scrollable = scrollable;
                this.panelPeer  = panelPeer;
                this.children   = children;
                toolTipHelper = new ToolTipPopUpSupportHelper(panelPeer);
        }

        /**
         * Renders the Scrollable
         */
        public void render(RenderingContext rc, Element parent, boolean maximiseArea, String saveStateJavaScript) {
                System.err.println("YUess,. using custom scrollablerenderer");

                //
                // add our signature to the html
                String id  = panelPeer.getId().toString();

                ComponentStyle style = ComponentStyle.forComponent(panelPeer);
                style.addElementType(ElementNames.DIV);
                style.setHorizontalAlignment(scrollable.getHorizontalAlignment());
                style.setVerticalAlignment(scrollable.getVerticalAlignment());

                PositioningHelper.setPositionableStyle(scrollable, style);

                PositioningHelper.setScrollableStyle(scrollable, style);

                PositioningHelper.setClippableStyle(scrollable, style);

                PositioningHelper.setBorderableStyle(scrollable, style);

                String styleName = rc.getDocument().addStyle(style);

                style = ComponentStyle.forComponent(panelPeer);
                style.addElementType(ElementNames.TD);
                style.setHorizontalAlignment(scrollable.getHorizontalAlignment());
                style.setVerticalAlignment(scrollable.getVerticalAlignment());
                String styleNameTD = rc.getDocument().addStyle(style);


                Element div = new Element(ElementNames.DIV);
                div.setWhitespaceRelevant(true);
                div.addAttribute(ElementNames.Attributes.CLASS, styleName);
                div.addAttribute(ElementNames.Attributes.ID, id);

                toolTipHelper.render(rc,div);

                PositioningHelper.addScrollableSaveSupport(rc, scrollable, id, div, saveStateJavaScript);

                nextapp.echo.Insets insets = scrollable.getInsets();
                if (insets != null) {
                        if (insets.getTop() == insets.getBottom() && insets.getLeft() == insets.getRight() && insets.getTop() == insets.getLeft()) {
                                if (insets.getTop() != 0) {
                                        style.addAttribute("padding", insets.getTop());
                                }
                        } else {
                                style.addAttribute("padding-left", insets.getLeft());
                                style.addAttribute("padding-top", insets.getTop());
                                style.addAttribute("padding-right", insets.getRight());
                                style.addAttribute("padding-bottom", insets.getBottom());
                        }
                }
                String hAlignment = "left";
                switch (scrollable.getHorizontalAlignment()) {
                        case EchoConstants.LEFT :
                                hAlignment = "left";
                                break;
                        case EchoConstants.CENTER :
                                hAlignment = "center";
                                break;
                        case EchoConstants.RIGHT :
                                hAlignment = "right";
                                break;
                }
                div.addAttribute("align", hAlignment);

                if (scrollable instanceof LayoutManageable && ((LayoutManageable)scrollable).getLayoutManager() != null) {
                        LayoutManageable layoutManageable = (LayoutManageable) scrollable;
                        LayoutManagerPeer layoutPeer = LayoutManagerPeerFactory.createLayoutManagerPeer(layoutManageable.getLayoutManager());
                        if (layoutPeer != null)
                                layoutPeer.render(rc, div, panelPeer, children);
                } else {
                        for (int index = 0; index < children.length; ++index) {
                                children[index].render(rc, div);
                        }
                }
                parent.addAttribute("width", "100%");
                parent.addAttribute("height", "100%");
                if (maximiseArea) {
                        parent.add(div);
                } else {
                        Element tableE = new Element(ElementNames.TABLE);
                        parent.add(tableE);
                        tableE.addAttribute("border",0);
                        tableE.addAttribute("cellpadding",0);
                        tableE.addAttribute("cellspacing",0);

                        tableE.addAttribute("width", "100%");
                        tableE.addAttribute("height", "100%");

                        tableE.setWhitespaceRelevant(true);

                        Element trE = new Element(ElementNames.TR);
                        tableE.add(trE);
                        trE.setWhitespaceRelevant(true);

                        Element tdE = new Element(ElementNames.TD);
                        trE.add(tdE);
                        tdE.setWhitespaceRelevant(true);
                        tdE.addAttribute(ElementNames.Attributes.CLASS, styleNameTD);
                        tdE.addAttribute("align", hAlignment);

                        tdE.add(div);
                }
                rc.getDocument().setCursorOnNewLine(true);
        }
}
