package tipirich;

import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;


public class Macbutton extends BaseAdapter {

	public Macbutton() {
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
  public void setDefaultSize(Integer value) {
    myComponent.setValue("defaultSize",value);
  }
  public void setTooltiptext(String value) {
    myComponent.setValue("tooltiptext",value);
  }
  public void setIcon(java.net.URL value) {
    myComponent.setValue("icon",value);
  }
  public void setShowSpring(Boolean value) {
    myComponent.setValue("showSpring",value);
  }
  public void flip() {
    java.util.Map<String, Object> parameters = null;
   myComponent.performMethod("flip",parameters,invocation,event);
  }

	



}
