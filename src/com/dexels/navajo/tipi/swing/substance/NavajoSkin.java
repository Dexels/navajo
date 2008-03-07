package com.dexels.navajo.tipi.swing.substance;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.SubstanceSliderUI;
import org.jvnet.substance.color.BarbyPinkColorScheme;
import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.painter.SpecularGradientPainter;
import org.jvnet.substance.painter.text.SubstanceTextPainter;
import org.jvnet.substance.plugin.SubstanceTitlePainterPlugin;
import org.jvnet.substance.skin.RavenSkin;
import org.jvnet.substance.theme.SubstanceTheme;
import org.jvnet.substance.theme.SubstanceTheme.ThemeKind;
import org.jvnet.substance.watermark.SubstanceWatermark;

public class NavajoSkin extends RavenSkin{

	@Override
	public String getDisplayName() {
		return "NavajoSubstance";
	}

	@Override
	public SubstanceTheme getTheme() {
		// TODO Auto-generated method stub
		return super.getTheme();
	}

	@Override
	public SubstanceWatermark getWatermark() {
		// TODO Auto-generated method stub
		return super.getWatermark();
	}

	@Override
	public boolean set() {
		super.set();
		SubstanceLookAndFeel.setCurrentButtonShaper(new org.jvnet.substance.button.StandardButtonShaper());
		SubstanceLookAndFeel.setCurrentGradientPainter(new SpecularGradientPainter());
		
		SubstanceTheme theme = SubstanceLookAndFeel.getTheme();
//		theme.addUserDefined(new BarbyPinkColorScheme(),ThemeKind.DARK, "apenoot");
		return true;
	}

}
