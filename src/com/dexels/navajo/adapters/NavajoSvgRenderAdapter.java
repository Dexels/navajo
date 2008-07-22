package com.dexels.navajo.adapters;

import java.io.*;

import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.*;

import com.dexels.navajo.document.types.*;

public class NavajoSvgRenderAdapter {

	/**
	 * @param args
	 * @throws TranscoderException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws TranscoderException, IOException {
        FileInputStream fis = new FileInputStream("output.svg");
        OutputStream ostream = new FileOutputStream("out.png");
        
        render(fis, ostream,300,500);
	}

	public Binary renderSvg(Binary svg, double width, double height) throws TranscoderException, IOException {
		Binary b = new Binary();
		render(svg.getDataAsStream(), b.getOutputStream(),(float)width,(float)height);
		return b;
	}
	
	public static void render(InputStream fis, OutputStream ostream, float width, float height) throws  IOException {
		PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(fis);
        t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,new Float(width));
        t.addTranscodingHint(PNGTranscoder.KEY_WIDTH,new Float(height));
        TranscoderOutput output = new TranscoderOutput(ostream);

        
        // Save the image.
        try {
			t.transcode(input, output);
		} catch (TranscoderException e) {
			e.printStackTrace();
		}

        // Flush and close the stream.
        ostream.flush();
        ostream.close();
        fis.close();
	}

}
