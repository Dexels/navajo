package tipirich;

import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;


public class Flip extends BaseAdapter {

	public Flip() {
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
  public void setSpeed(Integer value) {
    myComponent.setValue("speed",value);
  }
  public void setDirection(String value) {
    myComponent.setValue("direction",value);
  }
  public void setVisible(Boolean value) {
    myComponent.setValue("visible",value);
  }
  public void flip() {
    java.util.Map<String, Object> parameters = null;
   myComponent.performMethod("flip",parameters,invocation,event);
  }
  public void flipBackwards() {
    java.util.Map<String, Object> parameters = null;
   myComponent.performMethod("flipBackwards",parameters,invocation,event);
  }

	



}
