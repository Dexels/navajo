package com.dexels.navajo.tipi.components.echoimpl.ui;

import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.layout.*;
import echopoint.Panel;
import echopoint.layout.*;
import echopoint.ui.*;
import echopoint.ui.layout.*;
import echopoint.ui.util.*;
import nextapp.echo.*;
import nextapp.echoservlet.*;
import nextapp.echoservlet.html.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoPanelUI
    extends PanelUI {
  private String ALIGN = "align";
  public TipiEchoPanelUI() {
  }

  public static void register() {
    EchoServer.loadPeerBindings("com.dexels.navajo.tipi.components.echoimpl.ui.TipiEchoPanel");
  }

  public void render(RenderingContext rc, Element parent) {
    TipiEchoPanel panel = (TipiEchoPanel) getComponent();
    LayoutManager layoutManager = panel.getLayoutManager();

    ComponentStyle style = ComponentStyle.forComponent(this);
    style.addElementType(ElementNames.DIV);
    Insets insets = panel.getInsets();
    if (insets != null) {
      if (insets.getTop() == insets.getBottom() && insets.getLeft() == insets.getRight() && insets.getTop() == insets.getLeft()) {
        if (insets.getTop() != 0) {
          style.addAttribute("padding", insets.getTop());
        }
      }
      else {
        style.addAttribute("padding-left", insets.getLeft());
        style.addAttribute("padding-top", insets.getTop());
        style.addAttribute("padding-right", insets.getRight());
        style.addAttribute("padding-bottom", insets.getBottom());
      }
    }
    PositioningHelper.setBorderableStyle(panel, style);
    String styleName = rc.getDocument().addStyle(style);

    Object cs = null;
    try {
      Component parentComp = panel.getParent();
      if (Panel.class.isInstance(parentComp)) {
        Panel p = (Panel) parentComp;
        LayoutManager lm = p.getLayoutManager();
        cs = lm.getContraints(panel);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if (cs != null) {
      System.err.println("Panel has gridbag constraints, using those..");
      if (EchoGridBagConstraints.class.isInstance(cs)) {
//        System.err.println("its gridbag..");
        EchoGridBagConstraints egc = (EchoGridBagConstraints) cs;
        if (egc.weightx > 0 && (egc.fill == 2 || egc.fill == 1)) {
          style.addAttribute("width", "100%");
        }
        if (egc.weighty > 0 && (egc.fill == 3 || egc.fill == 1)) {
          style.addAttribute("height", "100%");
        }
      }
    }
    else {
      if (panel.w > -1) {
        style.addAttribute("width", panel.w + "px");
//      parent.addAttribute("width", panel.w+"px");
      }
      else {
        style.addAttribute("width", "100%");
        parent.addAttribute("width", "100%");
      }
    }

    if (panel.h > -1) {
      style.addAttribute("height", panel.h + "px");
//      parent.addAttribute("height", panel.h+"px");
    }

    Element div = new Element(ElementNames.DIV);
    div.addAttribute(ElementNames.Attributes.CLASS, styleName);
    switch (panel.getHorizontalAlignment()) {
      case EchoConstants.LEFT:
        div.addAttribute(ALIGN, "left");
        break;
      case EchoConstants.CENTER:
        div.addAttribute(ALIGN, "center");
        break;
      case EchoConstants.RIGHT:
        div.addAttribute(ALIGN, "right");
        break;
    }

    ComponentPeer[] children = getChildren();
    LayoutManagerPeer layoutPeer = LayoutManagerPeerFactory.createLayoutManagerPeer(layoutManager);

    if (layoutPeer != null) {
      layoutPeer.render(rc, div, this, children);
    }
    else {
      for (int index = 0; index < children.length; ++index) {
        children[index].render(rc, div);
      }
    }
    parent.add(div);
    rc.getDocument().setCursorOnNewLine(true);

  }

}
