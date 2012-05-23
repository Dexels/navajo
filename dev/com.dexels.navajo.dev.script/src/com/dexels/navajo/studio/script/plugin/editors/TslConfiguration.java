/*
 * Created on Jun 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
//public class TslConfiguration {
    public class TslConfiguration extends SourceViewerConfiguration {
        @Override
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
        @Override
		public IContentAssistant getContentAssistant(ISourceViewer sv) {
           ContentAssistant ca = new ContentAssistant();
           IContentAssistProcessor cap = new TslCompletionProcessor();
           ca.setContentAssistProcessor(cap, 
              IDocument.DEFAULT_CONTENT_TYPE);
           ca.setInformationControlCreator(
              getInformationControlCreator(sv));
           return ca;
        }
        @Override
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
