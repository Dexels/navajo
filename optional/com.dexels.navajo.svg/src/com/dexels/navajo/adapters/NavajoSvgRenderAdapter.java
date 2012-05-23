package com.dexels.navajo.adapters;

import java.io.*;

import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.*;
import org.apache.fop.svg.PDFTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.fop.svg.*;

import com.dexels.navajo.document.types.*;

public class NavajoSvgRenderAdapter {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoSvgRenderAdapter.class);

	public Binary renderSvg(Binary svg, double width, double height) throws IOException {
		Binary b = new Binary();
		renderPNG(svg.getDataAsStream(), b.getOutputStream(),(float)width,(float)height);
		return b;
	}
	
	public static void renderPDF(InputStream fis, OutputStream ostream, float width, float height) throws  IOException {
		 
		PDFTranscoder t = new PDFTranscoder();
        TranscoderInput input = new TranscoderInput(fis);
        t.addTranscodingHint(PDFTranscoder.KEY_HEIGHT,new Float(width));
        t.addTranscodingHint(PDFTranscoder.KEY_WIDTH,new Float(height));
        TranscoderOutput output = new TranscoderOutput(ostream);

        
        // Save the image.
        try {
			t.transcode(input, output);
		} catch (TranscoderException e) {
			logger.error("Error: ", e);
		}

        // Flush and close the stream.
        ostream.flush();
        ostream.close();
        fis.close();
	}
	public static void renderPNG(InputStream fis, OutputStream ostream, float width, float height) throws  IOException {
		 
		PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(fis);
        t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,new Float(width));
        t.addTranscodingHint(PNGTranscoder.KEY_WIDTH,new Float(height));
        TranscoderOutput output = new TranscoderOutput(ostream);

        
        // Save the image.
        try {
			t.transcode(input, output);
		} catch (TranscoderException e) {
			logger.error("Error: ", e);
		}

        // Flush and close the stream.
        ostream.flush();
        ostream.close();
        fis.close();
	}

}
