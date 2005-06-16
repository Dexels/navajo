/*
 * Created on Jun 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.*;
import org.eclipse.jface.text.presentation.*;
import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.source.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
//public class TslConfiguration {
    public class TslConfiguration extends SourceViewerConfiguration {
        public IPresentationReconciler getPresentationReconciler(
           ISourceViewer sourceViewer) {
           PresentationReconciler pr = 
              new PresentationReconciler();
           
           DefaultDamagerRepairer ddr = 
              new DefaultDamagerRepairer(new TslRuleScanner());
           pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
           pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
           return pr;
        }
        public IContentAssistant getContentAssistant(ISourceViewer sv) {
           ContentAssistant ca = new ContentAssistant();
           IContentAssistProcessor cap = new TslCompletionProcessor();
           ca.setContentAssistProcessor(cap, 
              IDocument.DEFAULT_CONTENT_TYPE);
           ca.setInformationControlCreator(
              getInformationControlCreator(sv));
           return ca;
        }
        public ITextHover getTextHover(ISourceViewer sv, 
           String contentType) {
            
              return new ITextHover() {

                public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
                    return "AAP!";
                }

                public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
                    // TODO Auto-generated method stub
                    return null;
                }
            
            };
        }

    public static void main(String[] args) {
    }
}
