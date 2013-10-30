package com.dexels.navajo.pdf.functions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;

public final class SecurePDF extends FunctionInterface{

	
	private final static Logger logger = LoggerFactory
			.getLogger(SecurePDF.class);
	
	@Override
	public String remarks() {
        return "Returns a pdf where all text has been replaced by images";
    }

    @Override
	public String usage() {
        return "SecurePDF(<Binary pdf>)";
    }
    
    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Binary b = (Binary)getOperand(0);
        if(b!=null){
        	try{
        
	        	PDDocument doc = PDDocument.load(b.getDataAsStream());
				PDDocumentCatalog catalog = doc.getDocumentCatalog();
				PDDocument target = new PDDocument();
				
				for(int i=0;i<doc.getNumberOfPages();i++){
					PDPage page = (PDPage)catalog.getAllPages().get(i);
					BufferedImage img = page.convertToImage(BufferedImage.TYPE_INT_RGB, 288); // page is 72
					PDJpeg jpg = new PDJpeg(target, img);
					PDPage np = new PDPage(PDPage.PAGE_SIZE_A0);
					target.addPage(np); 
					PDPageContentStream contentStream = new PDPageContentStream(target, np, false, true );
					contentStream.drawImage(jpg, 0f, 0f);
					contentStream.close();	
				}
				Binary secured = new Binary();
				target.save(secured.getOutputStream());
				doc.close();
				return secured;
        	} catch(Exception e){
        		logger.error("Error: ", e);
        	}
        }
    	return null;
    }
    
    public static void main(String args[]){
    	try{
    		Binary input = new Binary(new File("/Users/arjenschoneveld/OfficialEvaluation.pdf"));
    		
    		SecurePDF pdf = new SecurePDF();
    		pdf.reset();
    		pdf.insertOperand(input);
    		Binary result = (Binary)pdf.evaluate();
    		if(result != null){
    			result.write(new FileOutputStream(new File("/Users/arjenschoneveld/converted.pdf")));
    		}
    	}catch(Exception e){
    		logger.error("Error: ", e);
    	}
    }

}
