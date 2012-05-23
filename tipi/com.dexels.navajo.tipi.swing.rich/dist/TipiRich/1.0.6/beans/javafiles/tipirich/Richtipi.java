package tipirich;

import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;


public class Richtipi extends BaseAdapter {

	public Richtipi() {
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
  public void setOpacity(Double value) {
    myComponent.setValue("opacity",value);
  }
  public void setBordersize(Double value) {
    myComponent.setValue("bordersize",value);
  }
  public void setArc(Integer value) {
    myComponent.setValue("arc",value);
  }

	



}
