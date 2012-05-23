package tipirich;

import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;


public class Mapholland extends BaseAdapter {

	public Mapholland() {
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

	



}
