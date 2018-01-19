package com.dexels.navajo.tipi.swing.pdf.parser;

import java.io.IOException;
import java.util.List;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.graphics.text.LineText;
import org.icepdf.core.pobjects.graphics.text.PageText;
import org.icepdf.core.pobjects.graphics.text.WordText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.core.TipiHeadlessComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiPDFSearch extends TipiHeadlessComponentImpl {
    private static final long serialVersionUID = 4898398670800512047L;

    private final static Logger logger = LoggerFactory.getLogger(TipiPDFSearch.class);

    private Binary binary;
    private String searchString;

    public TipiPDFSearch() {
        System.out.println("AA");
    }

    @Override
    public void setComponentValue(final String name, final Object object) {
        if (name.equals("binary")) {
            this.binary = (Binary) object;

        }
        if (name.equals("searchString")) {
            this.searchString = (String) object;

        }
    }

    @Override
    protected void performComponentMethod(String methodName, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
        if ("start".equals(methodName)) {
            // open the document
            Document document = new Document();
            try {
                // document.setFile(binary.getTempFileName(false));
                document.setFile("/home/chris/Documents/220401884.pdf");
            } catch (PDFException | PDFSecurityException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }

            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                PageText pageText;
                try {
                    pageText = document.getPageText(pageIndex);

                    for (LineText line : pageText.getPageLines()) {
                        if (!line.toString().contains(searchString)) {
                            continue;
                        }
                        List<WordText> words = line.getWords();
                        for (int wordCounter = 0; wordCounter < words.size(); wordCounter++) {
                            WordText word = words.get(wordCounter);
                            WordText dataWord = null;
                            if (word.toString().equalsIgnoreCase(searchString)) {
                                System.out.println("Match!");

                                // Continue scanning this line until we get a non-empty non- word...
                                wordCounter += 1;
                                dataWord = words.get(wordCounter);

                                while (dataWord != null && dataWord.isWhiteSpace() && wordCounter < words.size()) {
                                    wordCounter += 1;
                                    dataWord = words.get(wordCounter);
                                }
                                System.out.println("Next: " + dataWord);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }

}
