package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class PaintParser extends TipiTypeParser {
  private String GRADIENT = "gradient";
  private ColorParser cp = new ColorParser();
  public Object parse(TipiComponent source, String expression,TipiEvent event) {
    return parsePaint(expression);
  }

  private TipiGradientPaint parsePaint(String s) {
    TipiGradientPaint p = null;
    if(s.startsWith(GRADIENT)){
      try{
        String def = s.substring(s.indexOf("-") + 1);
        String direction = def.substring(0, def.indexOf("-"));
        def = def.substring(def.indexOf("-") + 1);
        String colorOne = def.substring(0, def.indexOf("-"));
        String colorTwo = def.substring(def.indexOf("-") + 1);
        Color c1 = (Color) cp.parse(null, colorOne, null);
        Color c2 = (Color) cp.parse(null, colorTwo, null);
        p = new TipiGradientPaint(direction, c1, c2);
      }catch(Exception e){
//        System.err.println("Could not construct Paint object. returning Color.gray");
        return p;
      }
    }
    return p;
  }
  
  public static TipiGradientPaint parse(String s) {
	  return new PaintParser().parsePaint(s);
  }
}
