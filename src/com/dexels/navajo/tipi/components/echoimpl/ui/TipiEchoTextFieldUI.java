package com.dexels.navajo.tipi.components.echoimpl.ui;

/*
 * This file is part of the Echo Point Project.  This project is a collection
 * of Components that have extended the Echo Web Application Framework.
 *
 * EchoPoint is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * EchoPoint is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Echo Point; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.layout.*;
import echopoint.Panel;
import echopoint.PasswordField;
import echopoint.TextField;
import echopoint.layout.*;
import echopoint.ui.util.*;
import nextapp.echo.*;
import nextapp.echo.text.*;
import nextapp.echoservlet.*;
import nextapp.echoservlet.html.*;
import nextapp.echoservlet.ui.*;

/**
 * <code>TextFieldUI</code> is the peer for the <code>echopoint.TextField</code>
 * <p>
 * Its has support for the new Validation classes as well as modularises
 * the creation of the INPUT element.
 */
public class TipiEchoTextFieldUI
    extends nextapp.echoservlet.ui.TextFieldUI {

  /** a ToolTipPopUpSupportHelper available to subclasses */
  protected ToolTipPopUpSupportHelper toolTipHelper;

  /**
   * @see nextapp.echoservlet.ui.TextComponentUI#registered()
   */
  public void registered() {
    super.registered();
    toolTipHelper = new ToolTipPopUpSupportHelper(this);
  }

  /**
   * @see nextapp.echoservlet.ui.TextComponentUI#unregistered()
   */
  public void unregistered() {
    super.unregistered();
    toolTipHelper.unregistered();
  }

  /**
   * Registers the Component with its peer bindings
   */
  public static void register() {
    EchoServer.loadPeerBindings("com.dexels.navajo.tipi.components.echoimpl.ui.TipiEchoTextField");
  }

  /**
   * A helper method to get the TextField safelt instead of
   * via (TextField) getComponent().
   *
   * @return the TextField associated with this peer.
   */
  protected TextField getTextField() {
    return (TextField) getComponent();
  }

  /**
   * @see nextapp.echoservlet.ui.TextComponentUI#getStyle()
   */
  protected ComponentStyle getStyle() {
    TextField textField = (TextField) getComponent();
    ComponentStyle style = ComponentStyle.forComponent(this, true);

    PositioningHelper.setBorderableStyle(textField, style);
    style.setHorizontalAlignment(textField.getHorizontalAlignment());

    switch (textField.getColumnUnits()) {
      case TextField.CHARACTER_UNITS:
        break;
      case TextField.PIXEL_UNITS:
        style.addAttribute("width", textField.getColumns(), ComponentStyle.PIXEL_UNITS);
        break;
      case TextField.PERCENT_UNITS:
        style.addAttribute("width", textField.getColumns(), ComponentStyle.PERCENT_UNITS);
        break;
      default:
        throw new IllegalStateException("Unsupported column units.");
    }
    return style;
  }

  /**
   * Called to return the HTML element that will
   * be used as the visiual representation of the TextField.
   * <p>
   * This default version returns a "input type=text" element
   * that an id="getId()".  It morphs into a "input type=password"
   * if the field is indeed a PasswordField.
   * <p>
   * It also adds a tooltip support to the element.
   *
   * @param rc - ther renderind context in play
   * @param textField -the textfield component
   * @param styleClassName - the componentn style name to use
   * @return the HTML element representing the textfield
   */
  protected Element getTextInput(RenderingContext rc, TextField textField, String styleClassName) {
    Element textE = new Element(ElementNames.INPUT, false);
    textE.setWhitespaceRelevant(true);
    textE.addAttribute("id", getId().toString());
    textE.addAttribute("name", getId().toString());

    if (styleClassName != null) {
      textE.addAttribute(ElementNames.Attributes.CLASS, styleClassName);

    }

    if (!textField.isEditable()) {
      textE.addAttribute("readonly");
    }
    if (!textField.isEnabled()) {
      textE.addAttribute("disabled");
    }
    if (textField.getColumnUnits() == TextComponent.CHARACTER_UNITS) {
      textE.addAttribute("size", textField.getColumns());
    }
    if (textField.getMaximumLength() >= 0) {
      textE.addAttribute("maxlength", textField.getMaximumLength());
    }

    if (textField instanceof PasswordField) {
      textE.addAttribute("type", "password");
      if (textField.getText() != null) {
        int length = textField.getText().length();
        StringBuffer sb = new StringBuffer();
        for (int index = 0; index < length; ++index) {
          sb.append("*");
        }
        textE.addAttribute("value", sb.toString());
      }
      textE.addAttribute("onfocus", "this.value=\'\'");
    }
    else {
      textE.addAttribute("type", "text");
      textE.addAttribute("value", Html.encode(textField.getText(), Html.NEWLINE_TO_SPACE));
    }
    if (textField.hasActionListeners()) {
      // Add text field script (only necessary for retrieving action events)
      rc.getDocument().addScriptInclude(SERVICE_TEXT_FIELD_SCRIPT);
      textE.addAttribute("onkeypress", "E_textFieldKeyPress(event, '" + getId() + "', this.value);");
    }

    // tool tip support
    toolTipHelper.render(rc, textE);

    // validation support
    ValidationHelper.addValidationSupport(rc, getId().toString(), textField);

    return textE;
  }

  /**
   * Called to return the onblur script that saves the state of
   * the TextField and also performs client side validation.  It
   * returns a script that ends in a semi colon, ready for chaining
   * with other scripts.
   *
   * @param rc - The rendering
   * @return
   */
  protected String generateOnBlurJavaScript(RenderingContext rc) {
    return _getOnBlurOrChangeScript(rc, true);
  }

  /**
   * Called to return the onchange script that saves the state of
   * the TextField and also performs client side validation.  It
   * returns a script that ends in a semi colon, ready for chaining
   * with other scripts.
   *
   * @param rc - The rendering
   * @return
   */
  protected String generateOnChangeJavaScript(RenderingContext rc) {
    return _getOnBlurOrChangeScript(rc, false);
  }

  private String _getOnBlurOrChangeScript(RenderingContext rc, boolean isOnBlur) {
    TextField tf = (TextField) getComponent();
    StringBuffer sb = new StringBuffer();
    sb.append("E_setParameter('");
    sb.append(getId());
    sb.append("',this.value); ");
    String valScript;
    if (isOnBlur) {
      valScript = ValidationHelper.getOnBlurRule(rc, getId().toString(), tf);
    }
    else {
      valScript = ValidationHelper.getOnChangeRule(rc, getId().toString(), tf);
    }
    if (valScript != null && valScript.length() > 0) {
      sb.append(" ");
      sb.append(valScript);
      sb.append(" ");
    }
    return sb.toString();
  }

  /**
   * @see nextapp.echoservlet.ComponentPeer#render(RenderingContext, Element)
   */
  public void render(RenderingContext rc, Element parent) {
//    System.err.println("Using Custom textfield");
    TipiEchoTextField textField = (TipiEchoTextField) getComponent();
    Object cs = null;
    try {
      Component parentComp = textField.getParent();
      if (Panel.class.isInstance(parentComp)) {
        Panel p = (Panel) parentComp;
        LayoutManager lm = p.getLayoutManager();
        cs = lm.getContraints(textField);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    renderedActive = textField.isEditable() && textField.isEnabled();

    ComponentStyle style = getStyle();
    style.addElementType(ElementNames.INPUT);

    if (cs != null) {
//      System.err.println("Got constraints");
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

    String styleName = rc.getDocument().addStyle(style);

    // create our HTML base element
    Element textE = getTextInput(rc, textField, styleName);

    // add validation support
    textE.addAttribute("onblur", generateOnBlurJavaScript(rc));
    textE.addAttribute("onchange", generateOnChangeJavaScript(rc));

    parent.add(textE);
    rc.getDocument().setCursorOnNewLine(false);

  }
}
