package com.dexels.navajo.tipi.components.echoimpl.parsers;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.DescriptionProvider;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class LabelBorderParser extends TipiTypeParser {
	private static final long serialVersionUID = 7206185842548108711L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(LabelBorderParser.class);
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
        return parseBorder(expression,source);
    }
//    private Object parseBorder(String s) {
//        return parseBorder(s);
//    }

    public Object parseBorder(String s, TipiComponent source) {
        if(s==null) {
            return null;
        }
        Color c = new Color(50, 50, 50);
        // if (s.endsWith("mm")) {
        // return parseMillis(s.substring(0,s.length()-2));
        // }
        // if (s.endsWith("%")) {
        // return parsePercent(s.substring(0,s.length()-2));
        // }
        // return parsePixels(s);
//        logger.info("PARSING BORDER:::: "+s);
        int size = 1;
        StringTokenizer st = new StringTokenizer(s, "-");
        String borderName = st.nextToken();
       
        String title = "";
        if (borderName.equals("titled")) {
        	title = st.nextToken();
		} else {
			 if (st.hasMoreTokens()) {
		            String sizeString = st.nextToken();
		            size = Integer.parseInt(sizeString);
		        } 			
		      if (st.hasMoreTokens()) {
		            String colorString = st.nextToken();
		            c = ColorParser.parseColor(colorString);
		        }

		}
  
        if ("etched".equals(borderName)) {
            return new Border(size, c, Border.STYLE_RIDGE);
        }
        if ("raised".equals(borderName)) {
            return new Border(size, c, Border.STYLE_GROOVE);
        }
        if ("lowered".equals(borderName)) {
            return new Border(size, c, Border.STYLE_INSET);
        }
        if ("titled".equals(borderName)) {
        	logger.info("RETURNING TITLED BORDER: "+title);
        	 DescriptionProvider dp = source.getContext().getDescriptionProvider();
	
	       	  if (dp==null) {
	       		  
	       		  return "["+title+"]";
	       	} else {
	       		return source.getContext().XMLUnescape(dp.getDescription(title));
	       	}
          
            
        }
        // return BorderFactory.createEmptyBorder();
        return null;
    }

}
