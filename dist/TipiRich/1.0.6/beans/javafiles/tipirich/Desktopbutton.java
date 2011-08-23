package tipirich;

import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;


public class Desktopbutton extends BaseAdapter {

	public Desktopbutton() {
		super();
	}
	
  public void setBackground(Object value) {
    myComponent.setValue("background",value);
  }
  public void setForeground(Object value) {
    myComponent.setValue("foreground",value);
  }
  public void setBorder(Object value) {
    myComponent.setValue("border",value);
  }
  public void setText(String value) {
    myComponent.setValue("text",value);
  }
  public void setSubtext(String value) {
    myComponent.setValue("subtext",value);
  }
  public void setIcon(java.net.URL value) {
    myComponent.setValue("icon",value);
  }
  public void setOpaque(Boolean value) {
    myComponent.setValue("opaque",value);
  }
  public void setShowSpring(Boolean value) {
    myComponent.setValue("showSpring",value);
  }
  public void setEnabled(Boolean value) {
    myComponent.setValue("enabled",value);
  }
  public void setTooltip(String value) {
    myComponent.setValue("tooltip",value);
  }
  public void setTextfont(java.net.URL value) {
    myComponent.setValue("textfont",value);
  }
  public void setSubtextfont(java.net.URL value) {
    myComponent.setValue("subtextfont",value);
  }
  public void setVisible(Boolean value) {
    myComponent.setValue("visible",value);
  }
  public void setOpacity(Double value) {
    myComponent.setValue("opacity",value);
  }
  public void setSpeed(Integer value) {
    myComponent.setValue("speed",value);
  }
  public void flip() {
    java.util.Map<String, Object> parameters = null;
   myComponent.performMethod("flip",parameters,invocation,event);
  }

	



}
