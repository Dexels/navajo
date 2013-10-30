import java.beans.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.swing.geo.impl.tilefactory.*;

 
public class TestGmap {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestGmap.class);
	
	public static void main(String[] args)  {
//	 UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		JXMapKit j = new JXMapKit();
		JFrame jf = new JFrame("Google map");
		j.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				logger.info("Property: "+pce.getPropertyName());
			}});
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		String s = Locale.getDefault().getCountry();
		logger.info(":: "+s);
		
		j.setTileFactory(new DefaultTileFactory(new GoogleTileFactoryInfo(0, 15, 17, 256,  true,true,false)));
//		j.setTileFactory(new DefaultTileFactory(new OpenStreetMapTileFactoryInfo(17)));
		j.setZoom(14);
			j.setAddressLocation(new GeoPosition(52,5));
			j.setAddressLocation(new GeoPosition(52,6));
			j.setAddressLocation(new GeoPosition(52,7));
			jf.getContentPane().add(j);
		jf.setSize(400, 600);
		jf.setVisible(true);
	}
	
	
}
