package de.xeinfach.kafenio.component;

import gnu.regexp.RE;
import gnu.regexp.REMatch;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import de.xeinfach.kafenio.util.LeanLogger;
/**
 * Description: An editor pane with syntax highlighting for HTML tags.
 *
 * <p>This is a basic approach to syntax highlighting
 * using regular expressions. It is used to make the plain
 * HTML view of application SimplyHTML more legible by
 * separating tags and attributes from content.</p>
 *
 * <p>The method doing the actual highlighting work
 * is put into an implementation of the Runnable interface
 * so that it can be called without conflicts.</p>
 *
 * <p>Can be refined in the way Patterns are set up, i.e.
 * not hard wire pattern setup and a GUI to let the
 * use choose styles for patterns.</p>
 *
 * <p><b>This class only works with java versions 1.4 and newer</b></p>
 * 
 * <p>Recommended readings:<br>
 * 'Regular Expressions and the JavaTM Programming Language' at<br>
 * <a href="http://developer.java.sun.com/developer/technicalArticles/releases/1.4regex/" 
 * target="_blank">http://developer.java.sun.com/developer/technicalArticles/releases/1.4regex/</a><br>
 * and<br>
 * Presentation slides 'Rich Clients for Web Services' from JavaOne 2002 at<br>
 * <a href="http://servlet.java.sun.com/javaone/resources/content/sf2002/conf/sessions/pdfs/2274.pdf" 
 * target="_blank">http://servlet.java.sun.com/javaone/resources/content/sf2002/conf/sessions/pdfs/2274.pdf</a>
 * </p>
 *
 * @author Ulrich Hilger <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author Karsten Pawlik
 */
public class SyntaxPane extends JTextPane implements CaretListener {

	private static LeanLogger log = new LeanLogger("SyntaxPane.class");
	
	/** Patterns registered with this SnytaxPane */
	private Vector patterns;

	/** the cursor to use to indicate a lengthy operation is going on */
	private Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

	/**
	 * Creates a new <code>SyntaxPane</code>.
	 */
	public SyntaxPane() {
		super();
		setupPatterns();
	}

	/**
	 * set up HTML patterns and attributes
	 */
	private void setupPatterns() {
	
		RE pattern;
		SimpleAttributeSet set;
		patterns = new Vector();
		
		// content text
		pattern = getRegex("\\b\\w+");
		set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.black);
		StyleConstants.setBold(set, false);
		patterns.addElement(new RegExStyle(pattern, set));
		
		// a tag start
		pattern = getRegex("<[/a-zA-Z0-9\\s]+");
		set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, new Color(0, 0, 128));
		StyleConstants.setBold(set, true);
		patterns.addElement(new RegExStyle(pattern, set));
		
		// a tag end
		pattern = getRegex(">");
		patterns.addElement(new RegExStyle(pattern, set));
		
		// an attribute
		pattern = getRegex("\\s[/a-zA-Z0-9]+=");
		set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, new Color(158, 119, 0));
		StyleConstants.setBold(set, true);
		patterns.addElement(new RegExStyle(pattern, set));
		
		// attribute values
		pattern = getRegex("\"[\\x2D;:/.%#?=,\\w\\s]+\"");
		set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.blue);
		StyleConstants.setBold(set, false);
		patterns.addElement(new RegExStyle(pattern, set));
	}

	/**
	 * @param string
	 * @return
	 */
	private RE getRegex(String string) {
		try {
			return new RE(string);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * apply syntax highlighting to all HTML tags found in the given
	 * area of the given document
	 *
	 * @param doc document to apply syntax highlighting to
	 * @param offset position inside the given document to start to apply syntax highlighting to
	 * @param len number of characters to apply syntax highlighting to
	 */
	public void setMarks(StyledDocument doc, int offset, int len) {
		SwingUtilities.invokeLater(new StyleUpdater(this, doc, offset, len));
	}

	/**
	 * overridden from JEditorPane
	 * to suppress line wraps
	 *
	 * @see setSize
	 */
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	/**
	 * overridden from JEditorPane
	 * to suppress line wraps
	 *
	 * @see getScrollableTracksViewportWidth
	 */
	public void setSize(Dimension d) {
		if(d.width < getParent().getSize().width) {
			d.width = getParent().getSize().width;
		}
		super.setSize(d);
	}

	/**
	 * internal class. StyleUpdater does the actual syntax highlighting work
	 * and can be used in SwingUtilities.invokeLater()
	 */
	private class StyleUpdater implements Runnable {
	
		private StyledDocument doc;
		private int offset;
		private int len;
		private SyntaxPane syntaxPane;

		/**
		 * construct a <code>StyleUpdater</code>
		 *
		 * @param spthe SyntaxPane this StyleUpdater works on
		 * @param docthe document to apply syntax highlighting to
		 * @param offsetthe position inside the given document to start to apply syntax highlighting to
		 * @param lenthe number of characters to apply syntax highlighting to
		 */
		public StyleUpdater(SyntaxPane panel, StyledDocument newDoc, int newOffset, int newLen) {
			this.doc = newDoc;
			this.offset = newOffset;
			this.len = newLen;
			this.syntaxPane = panel;
		}
	
		/**
		 * apply syntax highlighting
		 */
		public void run() {
			setCursor(new Cursor(3));
			REMatch[] m;
			RegExStyle style;
			syntaxPane.removeCaretListener(syntaxPane);
			try {
				int length = doc.getLength();
				if(length > 0 && len > 0) {
					String text = doc.getText(offset, len);
					if (text != null && text.length() > 0) {
						Enumeration pe = patterns.elements();
						while (pe.hasMoreElements()) {
							style = (RegExStyle) pe.nextElement();
							m = style.getPattern().getAllMatches(text);
							for (int i=0; i < m.length; i++) {
								doc.setCharacterAttributes(	offset + m[i].getStartIndex(), 
															m[i].getEndIndex() - m[i].getStartIndex(), 
															style.getStyle(), 
															true);
							}
						}
					}
				}
			} catch (Exception ex) {
				log.warn("StyleUpdater ERROR: " + ex.fillInStackTrace());
			}
			syntaxPane.addCaretListener(syntaxPane);
			setCursor(new Cursor(0));
		}
	}

	/**
	 * CaretListener implementation
	 * 
	 * <p>updates syntax highlighting for the current line
	 * when the caret moves</p>
	 * 
	 * @param e the CaretEvent to process
	 */
	public void caretUpdate(CaretEvent e) {
		try {
			StyledDocument sDoc = (StyledDocument) getDocument();
			int cPos = e.getDot();
			int length = sDoc.getLength();
			String text = sDoc.getText(0, length);
			int lineStart = text.substring(0, cPos).lastIndexOf("\n") + 1;
			int lineEnd = text.indexOf("\n", cPos);
			if(lineEnd < 0) {
				lineEnd = length;
			}
			setMarks(sDoc, lineStart, lineEnd - lineStart);
		} catch(Exception ex) {
			log.warn("something went wrong while updating the caret position.");
		}
	}

	/**
	 * overridden to keep caret changes during the initial text load
	 * from triggering syntax highlighting repetitively
	 * 
	 * @param t text to set
	 */
	public void setText(String t) {
		removeCaretListener(this);
		super.setText(t);
		StyledDocument sDoc = (StyledDocument) getDocument();
		setMarks(sDoc, 0, sDoc.getLength());
		setCaretPosition(0);
		addCaretListener(this);
	}

	/**
	 * internal convenience class associating a pattern with a
	 * set of attributes
	 */
	public class RegExStyle {
		
		private RE p;
		private AttributeSet a;
		
		/**
		 * construct a <code>RegExStyle</code> instance
		 *
		 * @param aPattern the <code>Pattern</code> to apply this style to
		 * @param anAttributeSet the attributes making up this style
		 */
		public RegExStyle(RE aPattern, AttributeSet anAttributeSet) {
			this.p = aPattern;
			this.a = anAttributeSet;
		}
		
		/**
		 * get the <code>Pattern</code> this style is to be applied to
		 *
		 * @return the Pattern
		 */
		public RE getPattern() {
			return p;
		}
		
		/**
		 * get the attributes making up this style
		 *
		 * @return the set of attributes
		 */
		public AttributeSet getStyle() {
			return a;
		}
		
		/**
		 * set the Pattern to apply a given set of attributes to
		 *
		 * @param aPattern the Pattern
		 */
		public void setPattern(RE aPattern) {
			this.p = aPattern;
		}
		
		/**
		 * set the set of attributes to apply to a given Pattern
		 *
		 * @param anAttributeSet the set of attributes to use
		 */
		public void setStyle(AttributeSet anAttributeSet) {
			this.a = anAttributeSet;
		}
	}
}