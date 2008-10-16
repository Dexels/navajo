package tipi;

import javax.swing.*;

public class SwingTipiApplicationInstance extends TipiApplicationInstance {

	public SwingTipiApplicationInstance(String path) {
		super(path);
		}

	@Override
	public void checkStudio() {
		// TODO Auto-generated method stub
		super.checkStudio();
	}

	@Override
	public void init() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		super.init();
	}

	@Override
	public void switchToDefinition(String name) {
		// TODO Auto-generated method stub
		super.switchToDefinition(name);
	}

}
