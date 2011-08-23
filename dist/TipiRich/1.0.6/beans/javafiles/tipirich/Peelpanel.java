package tipirich;

import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;


public class Peelpanel extends BaseAdapter {

	public Peelpanel() {
		super();
	}
	
  public void setBacksidecolor(Object value) {
    myComponent.setValue("backsidecolor",value);
  }
  public void setBacksideimage(java.net.URL value) {
    myComponent.setValue("backsideimage",value);
  }
  public void setVisible(Boolean value) {
    myComponent.setValue("visible",value);
  }

	



}
