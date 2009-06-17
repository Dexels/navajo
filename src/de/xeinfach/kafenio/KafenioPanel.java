package de.xeinfach.kafenio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import de.xeinfach.kafenio.component.ExtendedHTMLDocument;
import de.xeinfach.kafenio.component.ExtendedHTMLEditorKit;
import de.xeinfach.kafenio.component.HTMLUtilities;
import de.xeinfach.kafenio.component.ImageFileChooser;
import de.xeinfach.kafenio.component.MutableFilter;
import de.xeinfach.kafenio.component.NameValuePair;
import de.xeinfach.kafenio.component.SyntaxPane;
import de.xeinfach.kafenio.component.dialogs.FileDialog;
import de.xeinfach.kafenio.component.dialogs.PropertiesDialog;
import de.xeinfach.kafenio.component.dialogs.SearchDialog;
import de.xeinfach.kafenio.component.dialogs.SimpleInfoDialog;
import de.xeinfach.kafenio.interfaces.KafenioContainerInterface;
import de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface;
import de.xeinfach.kafenio.interfaces.KafenioPanelInterface;
import de.xeinfach.kafenio.urlfetch.URLFetch;
import de.xeinfach.kafenio.util.Base64Codec;
import de.xeinfach.kafenio.util.LeanLogger;
import de.xeinfach.kafenio.util.HTMLTranslate;
import de.xeinfach.kafenio.util.Translatrix;
import de.xeinfach.kafenio.util.Utils;

/** 
 * Description: Main application class that creates a Java wysiwyg editor component
 * 
 * @author Karsten Pawlik, Howard Kistler
 */
public class KafenioPanel extends JPanel implements ActionListener, KeyListener, DocumentListener, KafenioPanelInterface {
	/* Constants. */
	private static LeanLogger log = new LeanLogger("KafenioPanel.class");
	private static ResourceBundle treepilotProperties;
	private static File currentFile = null;
	private static final String APP_NAME = "Kafenio";
	private static final String FONTSIZE_KEY = "size";
	private static final boolean USE_FORM_INDICATOR = true;
	private static final String FORM_INDICATOR_COLOR = "#cccccc";
	private static final int INDENT_STEP = 4;

	/* Components */
	private JMenuBar jMenuBar;
	private JToolBar toolbar2;
	private JToolBar toolbar1;
	private Container kafenioParent; //implements KafenioContainerInterface or not. Most probably JDialog or JFrame.
	private KafenioMenuBar kafenioMenuBar;
	private KafenioToolBar kafenioToolBar1;
	private KafenioToolBar kafenioToolBar2;
	private KafenioPanelConfiguration kafenioConfig;
	private Frame frameHandler;
	private JTextPane htmlPane;
	private SyntaxPane srcPane;
	private JScrollPane srcScrollPane;
	private JScrollPane htmlScrollPane;
	private JPanel mainPane;
	private JPanel toolbarPanel;
	private ExtendedHTMLEditorKit htmlKit;
	private ExtendedHTMLDocument htmlDoc;
	private StyleSheet styleSheet;
	private UndoManager undoManager;
	private HTMLUtilities htmlUtils = new HTMLUtilities(this);
	private java.awt.datatransfer.Clipboard sysClipboard;
	protected boolean documentConfirmed = false;
	private SecurityManager secManager;
	private Translatrix translatrix;
	
	private int splitPos = 0;
	private String lastSearchFindTerm     = null;
	private String lastSearchReplaceTerm  = null;
	private boolean lastSearchCaseSetting = false;
	private boolean lastSearchTopSetting  = false;
	private int indent = 0;
	private Vector toolbars = new Vector();
	private URLFetch urlFetcher = new URLFetch();
	private gnu.regexp.RE pattern1 = null;
	private gnu.regexp.RE pattern2 = null;
	private gnu.regexp.RE codeBasePattern = null;

	/* Actions */
	private Hashtable tActions;
	private RedoAction redoAction;
	private UndoAction undoAction;
	private KafenioPanelActions kafenioActions;

	/**
	 * Contructs a new KafenioPanel using the given configuration object.
	 * @param config the configuration to use for creation.
	 */
	public KafenioPanel(KafenioPanelConfigurationInterface iConfiguration) {
		super();
		log.info("1");
		kafenioConfig = (KafenioPanelConfiguration)iConfiguration;
		log.info("12");

		// Determine if system clipboard is available
		secManager = System.getSecurityManager();
		if(secManager != null) {
			try {
				secManager.checkSystemClipboardAccess();
				sysClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			} catch (SecurityException se) {
				sysClipboard = null;
			} catch (Exception se) {
				sysClipboard = null;
			} finally {
				log.debug("System clipboard is not available. Possible reason: no permission.");
			}
		}
		log.info("13");
		
		/* Localize for language */
		translatrix = new Translatrix("de.xeinfach.kafenio.LanguageResources");
		log.info("14");
		Locale baseLocale = (Locale)null;
		log.info("15");
		if(kafenioConfig.getLanguage() != null && kafenioConfig.getCountry() != null) {
			log.info("16");
			baseLocale = new Locale(kafenioConfig.getLanguage(), kafenioConfig.getCountry());
			log.info("17");
		}
		translatrix.setLocale(baseLocale);
		log.info("18");
		
		/* initialize all other components */
		kafenioParent = (Container)kafenioConfig.getKafenioParent();
		frameHandler = new Frame();
		toolbarPanel = new JPanel();
		kafenioActions = new KafenioPanelActions(this);
		undoManager = new UndoManager();
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		toolbarPanel = new JPanel();
		log.info("19");

		/* Load TreePilot properties */
		try {
			treepilotProperties = ResourceBundle.getBundle("de.xeinfach.kafenio.TreePilot");
		} catch(MissingResourceException mre) {
			log.error("MissingResourceException while loading treepilot file: " + mre.fillInStackTrace());
		}
		
		/* Create the editor kit, document, and stylesheet */
		htmlPane = new JTextPane();
		htmlKit = new ExtendedHTMLEditorKit();
		try {
			htmlDoc = (ExtendedHTMLDocument) (htmlKit.createDefaultDocument(new URL(getConfig().getCodeBase())));
		} catch (Exception e) {
			htmlDoc = (ExtendedHTMLDocument) (htmlKit.createDefaultDocument());
		}
		htmlDoc.putProperty("de.xeinfach.kafenio.docsource", getConfig().getCodeBase());
		styleSheet = htmlDoc.getStyleSheet();
		htmlKit.setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));

		/* Set up the text pane */
		htmlPane.setEditorKit(htmlKit);
		htmlPane.setDocument(htmlDoc);
		htmlPane.setMargin(new Insets(0, 0, 0, 0));
		htmlPane.addKeyListener(this);
		
		/* Create the source text area */
		srcPane = new SyntaxPane();
		srcPane.setBackground(new Color(255,255,255));
		srcPane.setSelectionColor(new Color(255, 192, 192));
		srcPane.setText(htmlPane.getText());
		srcPane.getDocument().addDocumentListener(this);

		/* Add CaretListener for tracking caret location events */
		htmlPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent ce) {
				handleCaretPositionChange(ce);
			}
		});
		htmlPane.getDocument().addUndoableEditListener(new CustomUndoableEditListener());

		/* create menubar and toolbar objects before loading the document
		 * (we need the styles-combo-box for registering a document) */
		kafenioMenuBar = new KafenioMenuBar(this);
		kafenioToolBar1 = new KafenioToolBar(this);
		kafenioToolBar2 = new KafenioToolBar(this);

		/* create menubar */
		if (getConfig().isShowMenuBar()) {
			if (kafenioConfig.getCustomMenuItems() != null) {
				jMenuBar = kafenioMenuBar.createCustomMenuBar(kafenioConfig.getCustomMenuItems());
			} else {
				jMenuBar = kafenioMenuBar.createDefaultKafenioMenuBar();
			}
			kafenioMenuBar.getViewToolbarItem().setSelected(getConfig().isShowToolbar() || getConfig().isShowToolbar2());
			kafenioMenuBar.getViewSourceItem().setSelected(getConfig().isShowViewSource());
			if (kafenioParent != null) {
				if (kafenioParent instanceof JDialog) {
				    ((JDialog)kafenioParent).setJMenuBar(jMenuBar); 
				} else if (kafenioParent instanceof JFrame) {
				    ((JFrame)kafenioParent).setJMenuBar(jMenuBar); 
				} else if (kafenioParent instanceof KafenioContainerInterface) {
				    ((KafenioContainerInterface)kafenioParent).setJMenuBar(jMenuBar); 
				}
			}
		}

		/* create toolbars */ 
		log.debug("config: " + getConfig());
		toolbarPanel.setLayout(new BorderLayout());
		toolbarPanel.setBackground(getConfig().getBgcolor());
		// TODO: refactor code - duplicate method calls are used...
		if (getConfig().isShowToolbar()) {
			toolbar1 = kafenioToolBar1.createToolbar(getConfig().getCustomToolBar1(), getConfig().isShowToolbar());
			toolbarPanel.add(toolbar1, BorderLayout.NORTH);
			toolbars.add(toolbar1);
		}
		if (getConfig().isShowToolbar2()) {
			toolbar2 = kafenioToolBar2.createToolbar(getConfig().getCustomToolBar2(), getConfig().isShowToolbar2());
			toolbarPanel.add(toolbar2, BorderLayout.SOUTH);
			toolbars.add(toolbar2);
		}
		
		/* Insert raw document, if exists */
		String content = "<html><body></body></html>";
		if(kafenioConfig.getRawDocument() != null && kafenioConfig.getRawDocument().length() > 0) {
			if(kafenioConfig.isBase64()) {
				content = Base64Codec.decode(kafenioConfig.getRawDocument()); 
			} else {
				content = kafenioConfig.getRawDocument();
			}
			// insert full url for images before setting the document text:
			content = addURLPrefixToImagePath(content);
		}
		setDocumentText(content);
		htmlPane.setCaretPosition(0);
		htmlPane.getDocument().addDocumentListener(this);
		
		if (kafenioConfig.getStyleSheetFileList() != null) {
			try {
				loadStyleSheets(kafenioConfig.getStyleSheetFileList(), kafenioConfig.getCodeBase());
			} catch(Exception e) {
				log.warn("could not load stylesheet file list: " + e.fillInStackTrace());
			}
		}
		
		/* Import CSS from reference, if exists */
		if(kafenioConfig.getUrlStyleSheet() != null) {
			String[] urlcss = new String[] {kafenioConfig.getUrlStyleSheet().toString()};
			try {
				loadStyleSheets(urlcss, kafenioConfig.getCodeBase());
			} catch(Exception e) {
				log.warn("could not load stylesheet from url: " + e.fillInStackTrace());
			}
		}
		
		/* Preload the specified HTML document, if exists */
		if(kafenioConfig.getDocument() != null) {
			File defHTML = new File(kafenioConfig.getDocument());
			if(defHTML.exists()) {
				try {
					openDocument(defHTML);
				} catch(Exception e) {
					log.error("Exception in preloading HTML document: " + e.fillInStackTrace());
				}
			}
		}
		
		/* Preload the specified CSS document, if exists */
		if(kafenioConfig.getStyleSheet() != null) {
			File defCSS = new File(kafenioConfig.getStyleSheet());
			if(defCSS.exists()) {
				try {
					openStyleSheet(defCSS);
				} catch(Exception e) {
					log.error("Exception in preloading CSS stylesheet: " + e.fillInStackTrace());
				}
			}
		}

		/* Create the scroll area for the text pane */
		htmlScrollPane = new JScrollPane(htmlPane);
		htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		htmlScrollPane.setPreferredSize(new Dimension(400, 400));
		htmlScrollPane.setMinimumSize(new Dimension(128, 128));
		
		/* Create the scroll area for the source viewer */
		srcScrollPane = new JScrollPane(srcPane);
		srcScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		srcScrollPane.setPreferredSize(new Dimension(400, 100));
		srcScrollPane.setMinimumSize(new Dimension(64, 64));
		
		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		if(kafenioConfig.isShowViewSource()) {
			mainPane.add(srcScrollPane, BorderLayout.CENTER);
		} else {
			mainPane.add(htmlScrollPane, BorderLayout.CENTER);
		}
		registerDocumentStyles();
		
		/* Add the components to the app */
		this.setLayout(new BorderLayout());
		this.add(toolbarPanel, BorderLayout.NORTH);
		this.add(mainPane, BorderLayout.CENTER);
		this.validate();
	}

	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelInterface#getTActions()
	 */
	public JMenuBar getJMenuBar() {
	    return jMenuBar; 
	}
	
	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelInterface#getJToolBar1()
	 */
	public JToolBar getJToolBar1() {
	    return toolbar1; 
	}
	
	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelInterface#getJToolBar2()
	 */
	public JToolBar getJToolBar2() {
	    return toolbar2; 
	}
	
	/**
	 * @param documentConfirmed true or false, default is false.
	 */
	public void setDocumentConfirmed(boolean documentConfirmed) {
	    this.documentConfirmed = documentConfirmed; 
	}
	
	/**
	 * @return returns the value of documentConfirmed. default is false.
	 */
	public boolean getDocumentConfirmed() {
	    return documentConfirmed; 
	}

	/**
	 * @return returns tActions for a JTextPane as Hashtable.
	 */
	public Hashtable getTActions() {
		if (tActions == null) {
			tActions = new Hashtable();
			Action[] actionsArray = htmlPane.getActions();
			for(int i = 0; i < actionsArray.length; i++) {
				Action a = actionsArray[i];
				tActions.put(a.getValue(Action.NAME), a);
			}
		}
		return tActions;
	}

	/**
	 * adds the given url prefix to an image path
	 * @param string url-prefix as string
	 * @return returns complete image path including url prefix.
	 */
	private String addURLPrefixToImagePath(String string) {
		if (string != null 
			&& (kafenioConfig.getCodeBase()!= null) && (kafenioConfig.getCodeBase().startsWith("http://")
			|| kafenioConfig.getCodeBase().startsWith("file://"))) 
		{
			StringBuffer textToParse = new StringBuffer(string);
			String searchString = "src=\"";
			// toString() for java 1.3 compatibility
			int nextPosition = textToParse.toString().indexOf(searchString);
			int currentPosition;
			while (nextPosition > -1) {
				currentPosition = nextPosition + searchString.length();
				if (! textToParse.substring(currentPosition + 4, currentPosition + 7).equalsIgnoreCase("://")) {
					if (textToParse.substring(currentPosition, currentPosition + 1).equalsIgnoreCase("/")) {
						textToParse.delete(currentPosition, currentPosition + 1);
					}
					textToParse.insert(currentPosition, kafenioConfig.getCodeBase());
				}
				// toString() for java 1.3 compatibility
				nextPosition = textToParse.toString().indexOf(searchString, currentPosition + 1);
				if (nextPosition == currentPosition) {
					break;
				}
			}
			return textToParse.toString();
		} else {
			return string;
		}
	}

	/**
	 * ActionListener method
	 * @param ae an action event to handle  
	 */
	public void actionPerformed(ActionEvent ae) {
		try {
			String command = ae.getActionCommand();
			if(command.equals("newdoc")) {
				SimpleInfoDialog sidAsk = 
					new SimpleInfoDialog(	this, 
											"", 
											true, 
											translatrix.getTranslationString("AskNewDocument"), 
											SimpleInfoDialog.QUESTION);

				String decision = sidAsk.getDecisionValue();

				if(decision.equals(translatrix.getTranslationString("DialogAccept"))) {
					if(styleSheet != null) {
						htmlDoc = new ExtendedHTMLDocument(styleSheet);
					} else {
						htmlDoc = (ExtendedHTMLDocument)(htmlKit.createDefaultDocument());
					}

					setDocumentText("<HTML><BODY></BODY></HTML>");
					registerDocument(htmlDoc);
					currentFile = null;
					updateTitle();
				}
			} else if(command.equals("openhtml")) {
				openDocument(null);
			} else if(command.equals("opencss")) {
				openStyleSheet(null);
			} else if(command.equals("openb64")) {
				openDocumentBase64(null);
			} else if(command.equals("save")) {
				writeOut(currentFile);
				updateTitle();
			} else if(command.equals("saveas")) {
				writeOut(null);
			} else if(command.equals("savebody")) {
				writeOutFragment("body");
			} else if(command.equals("savecontent")) {
				postContent(getDocumentBody());
			} else if(command.equals("savedocument")) {
				postContent(getDocumentText());
			} else if(command.equals("confirmcontent")) {
			    setAsConfirmedAndQuit();
			} else if(command.equals("detachframe")) {
				detachFrame();
			} else if(command.equals("savertf")) {
				writeOutRTF((StyledDocument)(htmlPane.getStyledDocument()));
			} else if(command.equals("saveb64")) {
				writeOutBase64(getDocumentText());
			} else if(command.equals("textcut")) {
				if(srcScrollPane.isShowing() && srcPane.hasFocus()) {
					srcPane.cut();
				} else {
					htmlPane.cut();
				}
			} else if(command.equals("textcopy")) {
				if(srcScrollPane.isShowing() && srcPane.hasFocus()) {
					srcPane.copy();
				} else {
					htmlPane.copy();
				}
			} else if(command.equals("textpaste")) {
				if(srcScrollPane.isShowing() && srcPane.hasFocus()) {
					srcPane.paste();
				} else {
					htmlPane.paste();
				}
			} else if(command.equals("describe")) {
				log.debug("------------DOCUMENT------------");
				log.debug("Content Type : " + htmlPane.getContentType());
				log.debug("Editor Kit   : " + htmlPane.getEditorKit());
				log.debug("Doc Tree     :");
				log.debug("");
				describeDocument(htmlPane.getStyledDocument());
				log.debug("--------------------------------");
				log.debug("");
			} else if(command.equals("describecss")) {
				log.debug("-----------STYLESHEET-----------");
				log.debug("Stylesheet Rules");
				Enumeration rules = styleSheet.getStyleNames();
				while(rules.hasMoreElements()) {
					String ruleName = (String)(rules.nextElement());
					Style styleRule = styleSheet.getStyle(ruleName);
					log.debug(styleRule.toString());
				}
				log.debug("--------------------------------");
				log.debug("");
			} else if(command.equals("whattags")) {
				log.debug("Caret Position : " + htmlPane.getCaretPosition());
				AttributeSet attribSet = htmlPane.getCharacterAttributes();
				Enumeration attribs = attribSet.getAttributeNames();
				log.debug("Attributes     : ");
				while(attribs.hasMoreElements()) {
					String attribName = attribs.nextElement().toString();
					log.debug("                 " + attribName + " | " + attribSet.getAttribute(attribName));
				}
			} else if(command.equals("toggletoolbar")) {
				for (int i=0; i < toolbars.size(); i++) {
					JToolBar jtb = (JToolBar)toolbars.get(i); 
					jtb.setVisible(kafenioMenuBar.getViewToolbarItem().isSelected());
				}
			} else if(command.equals("viewsource")) {
				htmlScrollPane.setCursor(new Cursor(3));
				srcScrollPane.setCursor(new Cursor(3));
				htmlScrollPane.setCursor(new Cursor(3));
				setCursor(new Cursor(3));
				toggleSourceWindow();
				htmlScrollPane.setCursor(new Cursor(0));
				srcScrollPane.setCursor(new Cursor(0));
				htmlScrollPane.setCursor(new Cursor(0));
				setCursor(new Cursor(0));
			} else if(command.equals("serialize")) {
				serializeOut((HTMLDocument)(htmlPane.getDocument()));
			} else if(command.equals("readfromser")) {
				serializeIn();
			} else if(command.equals("inserttable")) {
				String[] fieldNames = { "rows", "cols", "border", "cellspacing", "cellpadding", "width" };
				String[] fieldTypes = { "text", "text", "text",   "text",        "text",        "text" };
				insertTable((Hashtable)null, fieldNames, fieldTypes);
			} else if(command.equals("inserttablerow")) {
				insertTableRow();
			} else if(command.equals("inserttablecolumn")) {
				insertTableColumn();
			} else if(command.equals("deletetablerow")) {
				deleteTableRow();
			} else if(command.equals("deletetablecolumn")) {
				deleteTableColumn();
			} else if(command.equals("insertbreak")) {
				insertBreak();
			} else if(command.equals("insertlocalimage")) {
				insertLocalImage(null);
			} else if(command.equals("insertnbsp")) {
				insertNonbreakingSpace();
			} else if(command.equals("insertform")) {
				String[] fieldNames  = { "name", "method",   "enctype" };
				String[] fieldTypes  = { "text", "combo",    "text" };
				String[] fieldValues = { "",     "POST,GET", "text" };
				insertFormElement(HTML.Tag.FORM, "form", (Hashtable)null, fieldNames, fieldTypes, fieldValues, true);
			} else if(command.equals("inserttextfield")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "text");
				String[] fieldNames = { "name", "value", "size", "maxlength" };
				String[] fieldTypes = { "text", "text",  "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("inserttextarea")) {
				String[] fieldNames = { "name", "rows", "cols" };
				String[] fieldTypes = { "text", "text", "text" };
				insertFormElement(HTML.Tag.TEXTAREA, "textarea", (Hashtable)null, fieldNames, fieldTypes, true);
			} else if(command.equals("insertcheckbox")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "checkbox");
				String[] fieldNames = { "name", "checked" };
				String[] fieldTypes = { "text", "bool" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("insertradiobutton")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "radio");
				String[] fieldNames = { "name", "checked" };
				String[] fieldTypes = { "text", "bool" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("insertpassword")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "password");
				String[] fieldNames = { "name", "value", "size", "maxlength" };
				String[] fieldTypes = { "text", "text",  "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("insertbutton")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "button");
				String[] fieldNames = { "name", "value" };
				String[] fieldTypes = { "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("insertbuttonsubmit")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "submit");
				String[] fieldNames = { "name", "value" };
				String[] fieldTypes = { "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("insertbuttonreset")) {
				Hashtable htAttribs = new Hashtable();
				htAttribs.put("type", "reset");
				String[] fieldNames = { "name", "value" };
				String[] fieldTypes = { "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			} else if(command.equals("find")) {
				doSearch((String)null, (String)null, false, lastSearchCaseSetting, lastSearchTopSetting, command);
			} else if(command.equals("findagain")) {
				doSearch(lastSearchFindTerm, (String)null, false, lastSearchCaseSetting, false, command);
			} else if(command.equals("replace")) {
				doSearch((String)null, (String)null, true, lastSearchCaseSetting, lastSearchTopSetting, command);
			} else if(command.equals("exit")) {
				this.dispose();
			} else if(command.equals("helpabout")) {
				SimpleInfoDialog sidAbout = 
					new SimpleInfoDialog(	this, 
											translatrix.getTranslationString("About"), 
											true, 
											translatrix.getTranslationString("AboutMessage")
											+ translatrix.getTranslationString("Contributors")
											+ translatrix.getTranslationString("ContributorNames")
											+ translatrix.getTranslationString("Version")
											+ translatrix.getTranslationString("VersionNumber"), 
											SimpleInfoDialog.INFO);
			}
		} catch(IOException ioe) {
			log.error("IOException in actionPerformed method: " + ioe.fillInStackTrace());
			SimpleInfoDialog sidAbout = 
				new SimpleInfoDialog(	this, 
										translatrix.getTranslationString("Error"), 
										true, 
										translatrix.getTranslationString("ErrorIOException"), 
										SimpleInfoDialog.ERROR);
		} catch(BadLocationException ble) {
			log.error("BadLocationException in actionPerformed method: " + ble.fillInStackTrace());
			SimpleInfoDialog sidAbout = 
				new SimpleInfoDialog(	this, 
										translatrix.getTranslationString("Error"), 
										true, 
										translatrix.getTranslationString("ErrorBadLocationException"), 
										SimpleInfoDialog.ERROR);
		} catch(NumberFormatException nfe) {
			log.error("NumberFormatException in actionPerformed method: " + nfe.fillInStackTrace());
			SimpleInfoDialog sidAbout = 
				new SimpleInfoDialog(	this,
										translatrix.getTranslationString("Error"),
										true,
										translatrix.getTranslationString("ErrorNumberFormatException"),
										SimpleInfoDialog.ERROR);
		} catch(ClassNotFoundException cnfe) {
			log.error("ClassNotFound Exception in actionPerformed method: " + cnfe.fillInStackTrace());
			SimpleInfoDialog sidAbout = 
				new SimpleInfoDialog(	this,
										translatrix.getTranslationString("Error"), 
										true, 
										translatrix.getTranslationString("ErrorClassNotFoundException "), 
										SimpleInfoDialog.ERROR);
		} catch(RuntimeException re) {
			log.error("RuntimeException in actionPerformed method: " + re.fillInStackTrace());
			SimpleInfoDialog sidAbout = 
				new SimpleInfoDialog(	this, 
										translatrix.getTranslationString("Error"), 
										true, 
										translatrix.getTranslationString("ErrorRuntimeException"), 
										SimpleInfoDialog.ERROR);
		}
	}

	/**
	 * bridge method to detach the applet into a separate window and back.
	 */
	public void detachFrame() {
	    if (kafenioParent != null && kafenioParent instanceof KafenioContainerInterface) {
	        ((KafenioContainerInterface)kafenioParent).detachFrame();
		}
	}

	/**
	 * KeyListener method, handles the given KeyEvent
	 * @param ke a KeyEvent to handle 
	 */
	public void keyTyped(KeyEvent ke) {
		log.debug("key-event caught: " + ke);
		Element elem;
		String selectedText;
		int pos = this.getCaretPosition();
		int repos = -1;
		if(	ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			try {
				if(pos > 0) {
					if((selectedText = htmlPane.getSelectedText()) != null) {
						htmlPane.replaceSelection("");
						refreshOnUpdate();
						return;
					} else {
						int sOffset = htmlDoc.getParagraphElement(pos).getStartOffset();
						if(sOffset == htmlPane.getSelectionStart()) {
							boolean content = true;
							if(htmlUtils.checkParentsTag(HTML.Tag.LI)) {
								elem = htmlUtils.getListItemParent();
								content = false;
								int so = elem.getStartOffset();
								int eo = elem.getEndOffset();
								if(so + 1 < eo) {
									char[] temp = htmlPane.getText(so, eo - so).toCharArray();
									for(int i=0; i < temp.length; i++) {
										if(!(Character.isWhitespace(temp[i]))) {
											content = true;
										}
									}
								}
								if(!content) {
									Element listElement = elem.getParentElement();
									htmlUtils.removeTag(elem, true);
									this.setCaretPosition(sOffset - 1);
									return;
								} else {
									htmlPane.setCaretPosition(htmlPane.getCaretPosition() - 1);
									htmlPane.moveCaretPosition(htmlPane.getCaretPosition() - 2);
									htmlPane.replaceSelection("");
									return;
								}
							} else if(htmlUtils.checkParentsTag(HTML.Tag.TABLE)) {
								htmlPane.setCaretPosition(htmlPane.getCaretPosition() - 1);
								ke.consume();
								return;
							}
						}
						htmlPane.replaceSelection("");
						return;
					}
				}
			} catch (BadLocationException ble) {
				log.error("BadLocationException in keyTyped method: " + ble.fillInStackTrace());
				SimpleInfoDialog sidAbout = 
					new SimpleInfoDialog(	this, 
											translatrix.getTranslationString("Error"), 
											true, 
											translatrix.getTranslationString("ErrorBadLocationException"), 
											SimpleInfoDialog.ERROR);
			}
		} else if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
			try {
				if(	htmlUtils.checkParentsTag(HTML.Tag.UL) 
					| htmlUtils.checkParentsTag(HTML.Tag.OL)) 
				{
					if (ke.isShiftDown()) {
						log.debug("shift-enter pressed inside list item.");
					} else {
						log.debug("enter pressed inside list item.");
						elem = htmlUtils.getListItemParent();
						int so = elem.getStartOffset();
						int eo = elem.getEndOffset();
						char[] temp = this.getTextPane().getText(so,eo-so).toCharArray();
						boolean content = false;
	
						for(int i=0;i<temp.length;i++) {
							if(!Character.isWhitespace(temp[i])) {
								content = true;
							}
						}
	
						if(content) {
							int end = -1;
							int j = temp.length;
							do {
								j--;
								if(Character.isLetterOrDigit(temp[j])) {
									end = j;
								}
							} while (end == -1 && j >= 0);
							j = end;
							do {
								j++;
								if(!Character.isSpaceChar(temp[j])) {
									repos = j - end -1;
								}
							} while (repos == -1 && j < temp.length);
							if(repos == -1) {
								repos = 0;
							}
						}
						if(elem.getStartOffset() == elem.getEndOffset() || !content) {
							manageListElement(elem);
						} else {
							if(this.getCaretPosition() + 1 == elem.getEndOffset()) {
								insertListStyle(elem);
								this.setCaretPosition(pos - repos);
							} else {
								int caret = this.getCaretPosition();
								String tempString = this.getTextPane().getText(caret, eo - caret);
								this.getTextPane().select(caret, eo - 1);
								this.getTextPane().replaceSelection("");
								htmlUtils.insertListElement(tempString);
								Element newLi = htmlUtils.getListItemParent();
								this.setCaretPosition(newLi.getEndOffset() - 1);
							}
						}
					}
				} else {
					if(!ke.isShiftDown()){
						getKafenioActions().getActionAddParagraph().actionPerformed(
							new ActionEvent(ke.getSource(), 10, ""));
					}
				}
			} catch (BadLocationException ble) {
				log.error("BadLocationException in keyTyped method: " + ble.fillInStackTrace());
				SimpleInfoDialog sidAbout = 
					new SimpleInfoDialog(	this, 
											translatrix.getTranslationString("Error"), 
											true, 
											translatrix.getTranslationString("ErrorBadLocationException"), 
											SimpleInfoDialog.ERROR);
			} catch (IOException ioe) {
				log.error("IOException in keyTyped method: " + ioe.fillInStackTrace());
				SimpleInfoDialog sidAbout = 
					new SimpleInfoDialog(	this, 
											translatrix.getTranslationString("Error"), 
											true, 
											translatrix.getTranslationString("ErrorIOException"), 
											SimpleInfoDialog.ERROR);
			}
		} else if(ke.getKeyChar() == KeyEvent.VK_SPACE) {
			try {
				int caret = this.getCaretPosition();
				String tempString = this.getTextPane().getText(caret -1, 1);
				if("&".equals(tempString)) {
					insertNonbreakingSpace();
				}
			} catch (BadLocationException ble) {
				log.error("BadLocationException in keyTyped method: " + ble.fillInStackTrace());
				SimpleInfoDialog sidAbout = 
					new SimpleInfoDialog(	this, 
											translatrix.getTranslationString("Error"), 
											true, 
											translatrix.getTranslationString("ErrorBadLocationException"), 
											SimpleInfoDialog.ERROR);
			} catch (IOException ioe) {
				log.error("IOException in keyTyped method: " + ioe.fillInStackTrace());
				SimpleInfoDialog sidAbout = 
					new SimpleInfoDialog(	this, 
											translatrix.getTranslationString("Error"), 
											true, 
											translatrix.getTranslationString("ErrorIOException"), 
											SimpleInfoDialog.ERROR);
			}
		} else if (ke.getKeyChar() == KeyEvent.VK_ESCAPE &&
		           getConfig().getProperty("escapeCloses").equals("true")) 
		{
		    getKafenioParent().hide();
		}
	}
	
	/**
	 * handles the given KeyEvent if key was pressed down (do nothing)
	 * @param e a KeyEvent to handle
	 */
	public void keyPressed(KeyEvent e) {}
	
	/**
	 * handles the given KeyEvent if key was released (do nothing)
	 * @param e a KeyEvent to handle
	 */
	public void keyReleased(KeyEvent e) {}

	/**
	 * inserts a list item into the document at "element"'s position.
	 * @param element the element to add the list to
	 * @throws BadLocationException is thrown if something went wrong during insertion
	 * @throws IOException is thrown if an io exception occured.
	 */
	public void insertListStyle(Element element) throws BadLocationException,IOException {
		if(element.getParentElement().getName() == "ol") {
			getKafenioActions().getActionListOrdered().actionPerformed(
				new ActionEvent(new Object(), 0, "newListPoint"));
		} else {
			getKafenioActions().getActionListUnordered().actionPerformed(
				new ActionEvent(new Object(), 0, "newListPoint"));
		}
	}

	/**
	 * handles a DocumentEvent if the document was changed
	 * @param de DocumentEvent to handle 
	 */
	public void changedUpdate(DocumentEvent de)	{
		handleDocumentChange(de); 
	}
	
	/**
	 * handles a DocumentEvent if a document was inserted
	 * @param de DocumentEvent to handle 
	 */
	public void insertUpdate(DocumentEvent de)	{
		handleDocumentChange(de); 
	}
	
	/**
	 * handles a DocumentEvent if the document was removed
	 * @param de DocumentEvent to handle 
	 */
	public void removeUpdate(DocumentEvent de)	{
		handleDocumentChange(de);
	}
	
	/**
	 * implementation for Document Handler methods
	 * @param de DocumentEvent to handle 
	 */
	public void handleDocumentChange(DocumentEvent de) {
	}

	/** 
	 * Method for setting a document as the current document for the text pane
	 * and re-registering the controls and settings for it
	 * @param newHtmlDoc new Html Document to register in the editor pane
	 */
	public void registerDocument(ExtendedHTMLDocument newHtmlDoc) {
		htmlPane.setDocument(newHtmlDoc);
		htmlPane.getDocument().addUndoableEditListener(new CustomUndoableEditListener());
		htmlPane.getDocument().addDocumentListener(this);
		purgeUndos();
		registerDocumentStyles();
	}

	/** 
	 * Method for locating the available CSS style for the document and adding
	 * them to the styles selector
	 */
	public void registerDocumentStyles() {
		if(	kafenioToolBar1.getStyleSelector() == null
			|| kafenioToolBar2.getStyleSelector() == null
			|| htmlDoc == null) {
			return;
		}
		kafenioToolBar1.getStyleSelector().setEnabled(false);
		kafenioToolBar1.getStyleSelector().removeAllItems();
		kafenioToolBar1.getStyleSelector().addItem(translatrix.getTranslationString("NoCSSStyle"));
		kafenioToolBar2.getStyleSelector().setEnabled(false);
		kafenioToolBar2.getStyleSelector().removeAllItems();
		kafenioToolBar2.getStyleSelector().addItem(translatrix.getTranslationString("NoCSSStyle"));
		Vector cssClasses = new Vector();
		Enumeration e = htmlDoc.getStyleNames();
		while(e.hasMoreElements()) {
			String ce = e.nextElement().toString();
			if(ce.length() > 0 && ce.charAt(0) == '.') {
				cssClasses.add(ce.substring(1));
			}
		}
		Collections.sort(cssClasses);
			 
		for (int i=0; i < cssClasses.size(); i++) {
			String name = (String) cssClasses.get(i);
			kafenioToolBar1.getStyleSelector().addItem(name);
			kafenioToolBar2.getStyleSelector().addItem(name);
		}
		kafenioToolBar1.getStyleSelector().setEnabled(true);
		kafenioToolBar2.getStyleSelector().setEnabled(true);
	}

	/** 
	 * Method for inserting an HTML Table
	 */
	private void insertTable(Hashtable attribs, String[] fieldNames, String[] fieldTypes)
		throws IOException, BadLocationException, RuntimeException, NumberFormatException
	{
		log.debug("starting insertTable action...");
		int caretPos = htmlPane.getCaretPosition();
		StringBuffer compositeElement = new StringBuffer("<TABLE");
		if(attribs != null && attribs.size() > 0) {
			Enumeration attribEntries = attribs.keys();
			while(attribEntries.hasMoreElements()) {
				Object entryKey   = attribEntries.nextElement();
				Object entryValue = attribs.get(entryKey);
				if(entryValue != null && entryValue != "") {
					compositeElement.append(" " + entryKey + "=" + '"' + entryValue + '"');
				}
			}
		}
		int rows = 0;
		int cols = 0;
		if(fieldNames != null && fieldNames.length > 0) {
			PropertiesDialog propertiesDialog = 
				new PropertiesDialog(	this, 
										fieldNames, 
										fieldTypes, 
										translatrix.getTranslationString("FormDialogTitle"), 
										true);
			String decision = propertiesDialog.getDecisionValue();
			if(decision.equals(translatrix.getTranslationString("DialogCancel"))) {
				propertiesDialog.dispose();
				return;
			} else {
				for(int iter = 0; iter < fieldNames.length; iter++) {
					String fieldName = fieldNames[iter];
					String propValue = propertiesDialog.getFieldValue(fieldName);
					if(propValue != null && propValue != "" && propValue.length() > 0) {
						if(fieldName.equals("rows")) {
							rows = Integer.parseInt(propValue);
						} else if(fieldName.equals("cols")) {
							cols = Integer.parseInt(propValue);
						} else {
							compositeElement.append(" " + fieldName + "=" + '"' + propValue + '"');
						}
					}
				}
			}
			propertiesDialog.dispose();
		}
		compositeElement.append(">");
		for(int i = 0; i < rows; i++) {
			compositeElement.append("<TR>");

			for(int j = 0; j < cols; j++) {
				compositeElement.append("<TD></TD>");
			}

			compositeElement.append("</TR>");
		}
		compositeElement.append("</TABLE><P>&nbsp;<P>");
		htmlKit.insertHTML(htmlDoc, caretPos, compositeElement.toString(), 0, 0, HTML.Tag.TABLE);
		htmlPane.setCaretPosition(caretPos + 1);
		refreshOnUpdate();
	}

	/** 
	 * Method for inserting a row into an HTML Table
	 */
	private void insertTableRow() {
		int caretPos = htmlPane.getCaretPosition();
		Element	element = htmlDoc.getCharacterElement(htmlPane.getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint  = -1;
		int columnCount = -1;
		while(elementParent != null && !elementParent.getName().equals("body")) {
			if(elementParent.getName().equals("tr")) {
				startPoint  = elementParent.getStartOffset();
				columnCount = elementParent.getElementCount();
				break;
			} else {
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && columnCount > -1) {
			htmlPane.setCaretPosition(startPoint);
			StringBuffer sRow = new StringBuffer();
			sRow.append("<TR>");
			for(int i = 0; i < columnCount; i++) {
				sRow.append("<TD></TD>");
			}
			sRow.append("</TR>");
			ActionEvent actionEvent = new ActionEvent(htmlPane, 0, "insertTableRow");
			new HTMLEditorKit.InsertHTMLTextAction(	"insertTableRow", 
													sRow.toString(), 
													HTML.Tag.TABLE, 
													HTML.Tag.TR).actionPerformed(actionEvent);
			refreshOnUpdate();
			htmlPane.setCaretPosition(caretPos);
		}
	}

	/** 
	 * Method for inserting a column into an HTML Table
	 */
	private void insertTableColumn() {
		int caretPos = htmlPane.getCaretPosition();
		Element	element = htmlDoc.getCharacterElement(htmlPane.getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int rowCount   = -1;
		int cellOffset =  0;
		while(elementParent != null && !elementParent.getName().equals("body")) {
			if(elementParent.getName().equals("table")) {
				startPoint = elementParent.getStartOffset();
				rowCount   = elementParent.getElementCount();
				break;
			} else if(elementParent.getName().equals("tr")) {
				int rowStart = elementParent.getStartOffset();
				int rowCells = elementParent.getElementCount();
				for(int i = 0; i < rowCells; i++) {
					Element currentCell = elementParent.getElement(i);
					if(	htmlPane.getCaretPosition() >= currentCell.getStartOffset() 
						&& htmlPane.getCaretPosition() <= currentCell.getEndOffset()) 
					{
						cellOffset = i;
					}
				}
				elementParent = elementParent.getParentElement();
			} else {
				elementParent = elementParent.getParentElement();
			}
		}
		
		if(startPoint > -1 && rowCount > -1) {
			htmlPane.setCaretPosition(startPoint);
			String sCell = "<TD></TD>";
			ActionEvent actionEvent = new ActionEvent(htmlPane, 0, "insertTableCell");
			for(int i = 0; i < rowCount; i++) {
				Element row = elementParent.getElement(i);
				Element whichCell = row.getElement(cellOffset);
				htmlPane.setCaretPosition(whichCell.getStartOffset());
				new HTMLEditorKit.InsertHTMLTextAction(	"insertTableCell", 
														sCell, 
														HTML.Tag.TR, 
														HTML.Tag.TD, 
														HTML.Tag.TH, 
														HTML.Tag.TD).actionPerformed(actionEvent);
			}
			refreshOnUpdate();
			htmlPane.setCaretPosition(caretPos);
		}
	}

	/** 
	 * Method for inserting a cell into an HTML Table
	 */
	private void insertTableCell() {
		String sCell = "<TD></TD>";
		ActionEvent actionEvent = new ActionEvent(htmlPane, 0, "insertTableCell");
		new HTMLEditorKit.InsertHTMLTextAction(	"insertTableCell", 
												sCell, 
												HTML.Tag.TR, 
												HTML.Tag.TD, 
												HTML.Tag.TH, 
												HTML.Tag.TD).actionPerformed(actionEvent);
		refreshOnUpdate();
	}

	/** 
	 * Method for deleting a row from an HTML Table
	 */
	private void deleteTableRow() throws BadLocationException {
		int caretPos = htmlPane.getCaretPosition();
		Element	element = htmlDoc.getCharacterElement(htmlPane.getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int endPoint   = -1;
		while(elementParent != null && !elementParent.getName().equals("body")) {
			if(elementParent.getName().equals("tr")) {
				startPoint = elementParent.getStartOffset();
				endPoint   = elementParent.getEndOffset();
				break;
			} else {
				elementParent = elementParent.getParentElement();
			}
		}
		
		if(startPoint > -1 && endPoint > startPoint) {
			htmlDoc.remove(startPoint, endPoint - startPoint);
			htmlPane.setDocument(htmlDoc);
			registerDocument(htmlDoc);
			refreshOnUpdate();
			if(caretPos >= htmlDoc.getLength()) {
				caretPos = htmlDoc.getLength() - 1;
			}
			htmlPane.setCaretPosition(caretPos);
		}
	}

	/** 
	 * Method for deleting a column from an HTML Table
	 */
	private void deleteTableColumn() throws BadLocationException {
		int caretPos = htmlPane.getCaretPosition();
		Element	element       = htmlDoc.getCharacterElement(htmlPane.getCaretPosition());
		Element elementParent = element.getParentElement();
		Element	elementCell   = (Element)null;
		Element	elementRow    = (Element)null;
		Element	elementTable  = (Element)null;
		// Locate the table, row, and cell location of the cursor
		while(elementParent != null && !elementParent.getName().equals("body")) {
			if(elementParent.getName().equals("td")) {
				elementCell = elementParent;
			} else if(elementParent.getName().equals("tr")) {
				elementRow = elementParent;
			} else if(elementParent.getName().equals("table")) {
				elementTable = elementParent;
			}
			elementParent = elementParent.getParentElement();
		}
		int whichColumn = -1;
		if(elementCell != null && elementRow != null && elementTable != null) {
			// Find the column the cursor is in
			for(int i = 0; i < elementRow.getElementCount(); i++) {
				if(elementCell == elementRow.getElement(i)) {
					whichColumn = i;
				}
			}
			if(whichColumn > -1) {
				// Iterate through the table rows, deleting cells from the indicated column
				for(int i = 0; i < elementTable.getElementCount(); i++) {
					elementRow  = elementTable.getElement(i);
					if (elementRow.getElementCount() > whichColumn) {
						elementCell = elementRow.getElement(whichColumn);
					} else {
						elementCell = elementRow.getElement(elementRow.getElementCount() - 1);
					}
					int columnCellStart = elementCell.getStartOffset();
					int columnCellEnd   = elementCell.getEndOffset();
					htmlDoc.remove(columnCellStart, columnCellEnd - columnCellStart);
				}
				htmlPane.setDocument(htmlDoc);
				registerDocument(htmlDoc);
				refreshOnUpdate();
				if(caretPos >= htmlDoc.getLength()) {
					caretPos = htmlDoc.getLength() - 1;
				}
				htmlPane.setCaretPosition(caretPos);
			}
		}
	}

	/** 
	 * Method for inserting a break (BR) element
	 */
	private void insertBreak() throws IOException, BadLocationException, RuntimeException {
		int caretPos = htmlPane.getCaretPosition();
		htmlKit.insertHTML(htmlDoc, caretPos, "<BR>", 0, 0, HTML.Tag.BR);
		htmlPane.setCaretPosition(caretPos + 1);
	}

	/** 
	 * Method for inserting a non-breaking space (&amp;nbsp;)
	 */
	private void insertNonbreakingSpace() throws IOException, BadLocationException, RuntimeException {
		int caretPos = htmlPane.getCaretPosition();
		htmlDoc.insertString(caretPos, "\240", htmlPane.getInputAttributes());
		htmlPane.setCaretPosition(caretPos + 1);
	}

	/** 
	 * Method for inserting a form element
	 */
	private void insertFormElement(	HTML.Tag baseTag, 
									String baseElement, 
									Hashtable attribs, 
									String[] fieldNames, 
									String[] fieldTypes, 
									String[] fieldValues, 
									boolean hasClosingTag)
		throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = htmlPane.getCaretPosition();
		StringBuffer compositeElement = new StringBuffer("<" + baseElement);
		if(attribs != null && attribs.size() > 0) {
			Enumeration attribEntries = attribs.keys();
			while(attribEntries.hasMoreElements()) {
				Object entryKey   = attribEntries.nextElement();
				Object entryValue = attribs.get(entryKey);
				if(entryValue != null && entryValue != "") {
					compositeElement.append(" " + entryKey + "=" + '"' + entryValue + '"');
				}
			}
		}
		
		if(fieldNames != null && fieldNames.length > 0) {
			PropertiesDialog propertiesDialog = 
				new PropertiesDialog(	this, 
										fieldNames, 
										fieldTypes, 
										fieldValues, 
										translatrix.getTranslationString("FormDialogTitle"), 
										true);
			String decision = propertiesDialog.getDecisionValue();
			if(decision.equals(translatrix.getTranslationString("DialogCancel"))) {
				propertiesDialog.dispose();
				return;
			} else {
				for(int iter = 0; iter < fieldNames.length; iter++) {
					String fieldName = fieldNames[iter];
					String propValue = propertiesDialog.getFieldValue(fieldName);
					if(propValue != null && propValue.length() > 0) {
						if(fieldName.equals("checked")) {
							if(propValue.equals("true")) {
								compositeElement.append(" " + fieldName + "=" + '"' + propValue + '"');
							}
						} else {
							compositeElement.append(" " + fieldName + "=" + '"' + propValue + '"');
						}
					}
				}
			}
			propertiesDialog.dispose();
		}
		// --- Convenience for editing, this makes the FORM visible
		if(USE_FORM_INDICATOR && baseElement.equals("form")) {
			compositeElement.append(" bgcolor=" + '"' + FORM_INDICATOR_COLOR + '"');
		}
		// --- END
		compositeElement.append(">");
		if(hasClosingTag) {
			compositeElement.append("</" + baseElement + ">");
		}
		
		if(baseTag == HTML.Tag.FORM) {
			compositeElement.append("<P>&nbsp;</P>");
		}
		htmlKit.insertHTML(htmlDoc, caretPos, compositeElement.toString(), 0, 0, baseTag);
		refreshOnUpdate();
	}

	/** 
	 * Alternate method call for inserting a form element
	 * @param baseTag html base tag to insert form into
	 * @param baseElement base element to insert into form
	 * @param attribs attributes
	 * @param fieldNames names of the fields
	 * @param fieldTypes types of the fields
	 * @param hasClosingTag is there a closing tag?
	 * @throws IOException
	 * @throws BadLocationException
	 * @throws RuntimeException
	 */
	private void insertFormElement(	HTML.Tag baseTag, 
									String baseElement, 
									Hashtable attribs, 
									String[] fieldNames, 
									String[] fieldTypes, 
									boolean hasClosingTag)
		throws IOException, BadLocationException, RuntimeException
	{
		insertFormElement(	baseTag, 
							baseElement, 
							attribs, 
							fieldNames, 
							fieldTypes, 
							new String[fieldNames.length], 
							hasClosingTag);
	}

	/** 
	 * Method that handles initial list insertion and deletion
	 * @param element list element to manage
	 */
	public void manageListElement(Element element) {
		Element h = htmlUtils.getListItemParent();
		Element listElement = h.getParentElement();
		if(h != null) {
			htmlUtils.removeTag(h, true);
		}
	}

	/** 
	 * Method to initiate a find/replace operation
	 * @param searchFindTerm term to find
	 * @param searchReplaceTerm string to replace found term with
	 * @param bIsFindReplace was something replaced?
	 * @param bCaseSensitive search case sensitive?
	 * @param bStartAtTop start at top?
	 */
	private void doSearch(	String searchFindTerm, 
							String searchReplaceTerm, 
							boolean bIsFindReplace, 
							boolean bCaseSensitive, 
							boolean bStartAtTop,
							String command)
	{
		boolean bReplaceAll = false;
		JTextPane searchPane = htmlPane;
		if(srcScrollPane.isShowing() || srcPane.hasFocus()) {
			searchPane = srcPane;
		}
		
		if(command.equals("find") || command.equals("replace")){
		//if(searchFindTerm == null || (bIsFindReplace && searchReplaceTerm == null)) {
			SearchDialog sdSearchInput = new SearchDialog(	this, 
															translatrix.getTranslationString("SearchDialogTitle"), 
															true, 
															bIsFindReplace, 
															bCaseSensitive, 
															bStartAtTop,
															searchFindTerm,
															searchReplaceTerm);
															
			searchFindTerm    = sdSearchInput.getFindTerm();
			searchReplaceTerm = sdSearchInput.getReplaceTerm();
			bCaseSensitive    = sdSearchInput.getCaseSensitive();
			bStartAtTop       = sdSearchInput.getStartAtTop();
			bReplaceAll       = sdSearchInput.getReplaceAll();
		}
		if(searchFindTerm != null && (!bIsFindReplace || searchReplaceTerm != null)) {
			if(bReplaceAll) {
				int results = findText(searchFindTerm, searchReplaceTerm, bCaseSensitive, 0);
				int findOffset = 0;
				if(results > -1) {
					while(results > -1) {
						findOffset = findOffset + searchReplaceTerm.length();
						results = findText(searchFindTerm, searchReplaceTerm, bCaseSensitive, findOffset);
					}
				} else {
					SimpleInfoDialog sidWarn = 
						new SimpleInfoDialog(	this, 
												"", 
												true, 
												translatrix.getTranslationString("ErrorNoOccurencesFound") 
												+ ":\n" 
												+ searchFindTerm, 
												SimpleInfoDialog.WARNING);
				}
			} else {
				int results = 
					findText(	searchFindTerm, 
								searchReplaceTerm, 
								bCaseSensitive, 
								(bStartAtTop ? 0 : searchPane.getCaretPosition()));
				if(results == -1) {
					SimpleInfoDialog sidWarn = 
						new SimpleInfoDialog(	this, 
												"", 
												true, 
												translatrix.getTranslationString("ErrorNoMatchFound") 
												+ ":\n" 
												+ searchFindTerm, 
												SimpleInfoDialog.WARNING);
				}
			}
			
			lastSearchFindTerm = new String(searchFindTerm);
			if(searchReplaceTerm != null) {
				lastSearchReplaceTerm = new String(searchReplaceTerm);
			} else {
				lastSearchReplaceTerm = (String)null;
			}
			lastSearchCaseSetting = bCaseSensitive;
			lastSearchTopSetting  = bStartAtTop;
		}
	}

	/** 
	 * Method for finding (and optionally replacing) a string in the text
	 */
	private int findText(String findTerm, String replaceTerm, boolean bCaseSenstive, int iOffset) {
		JTextPane jtpFindSource;
		if(srcScrollPane.isShowing() || srcPane.hasFocus()) {
			jtpFindSource = srcPane;
		} else {
			jtpFindSource = htmlPane;
		}
		int searchPlace = -1;
		try {
			Document baseDocument = jtpFindSource.getDocument();
			if (bCaseSenstive) {
				searchPlace = baseDocument.getText(0, baseDocument.getLength()).indexOf(findTerm, iOffset);
			} else {
				searchPlace = 
					baseDocument.getText(	0, 
											baseDocument.getLength()).toLowerCase().indexOf(findTerm.toLowerCase(), 
											iOffset);
			}
			
			if(searchPlace > -1) {
				if(replaceTerm != null) {
					AttributeSet attribs = null;
					if(baseDocument instanceof HTMLDocument) {
						Element element = ((HTMLDocument)baseDocument).getCharacterElement(searchPlace);
						attribs = element.getAttributes();
					}
					baseDocument.remove(searchPlace, findTerm.length());
					baseDocument.insertString(searchPlace, replaceTerm, attribs);
					jtpFindSource.setCaretPosition(searchPlace + replaceTerm.length());
					jtpFindSource.requestFocus();
					jtpFindSource.select(searchPlace, searchPlace + replaceTerm.length());
				} else {
					jtpFindSource.setCaretPosition(searchPlace + findTerm.length());
					jtpFindSource.requestFocus();
					jtpFindSource.select(searchPlace, searchPlace + findTerm.length());
				}
			}
		} catch(BadLocationException ble) {
			log.error("BadLocationException in actionPerformed method: " + ble.fillInStackTrace());
			SimpleInfoDialog sidAbout = 
				new SimpleInfoDialog(	this, 
										translatrix.getTranslationString("Error"), 
										true, 
										translatrix.getTranslationString("ErrorBadLocationException"), 
										SimpleInfoDialog.ERROR);
		}
		return searchPlace;
	}

	// TODO: check the write methods!
	/** 
	 * Method for inserting an image from a file
	 */
	private void insertLocalImage(File whatImage) throws IOException, BadLocationException, RuntimeException {
		if(whatImage == null) {
			whatImage = getImageFromChooser(".", 
											MutableFilter.EXT_IMG, 
											translatrix.getTranslationString("FiletypeIMG"));
		}
		if(whatImage != null) {
			int caretPos = htmlPane.getCaretPosition();
			htmlKit.insertHTML(htmlDoc, caretPos, "<IMG SRC=\"" + whatImage + "\">", 0, 0, HTML.Tag.IMG);
			htmlPane.setCaretPosition(caretPos + 1);
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for inserting a file (either image-file or a url)
	 * @return returns the filecontents from the selected file
	 */
	public String insertFile() {
		String selectedFile = null;
		if(kafenioConfig.getServletUrl() != null) {
			try {
				Vector listData = new Vector();
				if (kafenioConfig.getServletMode().equalsIgnoreCase("cgi")) {
					Vector fileList = new Vector();
					// check if servlet url already contains parameters...
					String joiner;
					if (kafenioConfig.getServletUrl().indexOf("?") >= 0) {
						joiner = "&";
					} else {
						joiner = "?";
					}
					URL theServlet = new URL(	kafenioConfig.getServletUrl() 
												+ joiner 
												+ "GetFiles=true&FileExtensions=" 
												+ treepilotProperties.getString("ValidFileExtensions"));
												
					URLConnection conn = theServlet.openConnection();
					conn.setAllowUserInteraction(true);
					conn.setDoOutput(false);
					conn.setDoInput(true);
					conn.setUseCaches(false);
	
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String inputLine;

					// read the directory prefix of the files
					// from the first line
					// i.e.: http://www.xeinfach.de/myimages
					kafenioConfig.setFileDir(in.readLine());
	
					// ...all following lines contain file names and the
					// path relative to the above prefix
					// i.e.: /autum/leafs.gif
					StringTokenizer st;
					Vector it;
					while ((inputLine = in.readLine()) != null) {
						st = new StringTokenizer(inputLine, ";");
						it = new Vector();
						while (st.hasMoreTokens()) {
								it.add(st.nextToken());
						}
						if (it.size() == 2) {
							listData.add(new NameValuePair(it.get(0).toString(), it.get(1).toString()));
						} else if (it.size() == 1) {
							listData.add(new NameValuePair(it.get(0).toString()));
						} 
					}
					in.close();
					
					int caretPos = htmlPane.getCaretPosition();
					if (listData != null && listData.size() > 0) {
						FileDialog fileDialog = 
							new FileDialog(	this, 
											kafenioConfig.getFileDir() + kafenioConfig.getTreePilotSystemID(), 
											listData, 
											"File Chooser", 
											true);
						selectedFile = fileDialog.getSelectedFile();
						fileDialog.dispose();
					}
				} else if(kafenioConfig.getServletMode().equalsIgnoreCase("java")) {	
					String[] fileList = null;
					URL theServlet = new URL(	kafenioConfig.getServletUrl() 
												+ "?GetFiles=" 
												+ kafenioConfig.getTreePilotSystemID()
												+ "&FileExtensions=" 
												+ treepilotProperties.getString("ValidFileExtensions"));
								
					URLConnection conn = theServlet.openConnection();
					ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
					fileList = (String[]) in.readObject();
					int caretPos = htmlPane.getCaretPosition();
					if (fileList != null && fileList.length > 0) {	
						FileDialog fileDialog = 
							new FileDialog(	this, 
											kafenioConfig.getFileDir() + kafenioConfig.getTreePilotSystemID(), 
											fileList, 
											"File Chooser", 
											true);
						selectedFile = fileDialog.getSelectedFile();
						fileDialog.dispose();
					}
				}

			} catch (MalformedURLException mue) {
				log.error("MalFormedURLException during insertFile: " + mue);
			} catch (IOException ie) {
				log.error("IOException during insertFile: " + ie);
			} catch (Exception cnfe) {
				log.error("Exception during insertFile: " + cnfe);
			}
		}
		return selectedFile;
	}

	// TODO: check the write methods!
	/** 
	 * Method for saving text as a complete HTML document
	 */
	private void writeOut(File whatFile) throws IOException, BadLocationException {
		if(whatFile == null) {
			whatFile = getFileFromChooser(	".", 
											JFileChooser.SAVE_DIALOG, 
											MutableFilter.EXT_HTML, 
											translatrix.getTranslationString("FiletypeHTML"));
		}
		if(whatFile != null) {
			FileWriter fw = new FileWriter(whatFile);
			fw.write(getDocumentText(), 0, getDocumentText().length());
			fw.flush();
			fw.close();
			currentFile = whatFile;
			updateTitle();
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for saving text as an HTML fragment
	 */
	private void writeOutFragment(String containingTag) throws IOException, BadLocationException {
		File whatFile = getFileFromChooser(	".", 
											JFileChooser.SAVE_DIALOG, 
											MutableFilter.EXT_HTML, 
											translatrix.getTranslationString("FiletypeHTML"));
											
		if(whatFile != null) {
			FileWriter fw = new FileWriter(whatFile);
			String docContents = getSubText("body", srcPane.getText());
			fw.write(docContents, 0, docContents.length());
			fw.flush();
			fw.close();
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for saving text as an RTF document
	 */
	private void writeOutRTF(StyledDocument doc) throws IOException, BadLocationException {
		File whatFile = getFileFromChooser(	".", 
											JFileChooser.SAVE_DIALOG, 
											MutableFilter.EXT_RTF, 
											translatrix.getTranslationString("FiletypeRTF"));
											
		if(whatFile != null) {
			FileOutputStream fos = new FileOutputStream(whatFile);
			RTFEditorKit rtfKit = new RTFEditorKit();
			rtfKit.write(fos, doc, 0, doc.getLength());
			fos.flush();
			fos.close();
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for saving text as a Base64 encoded document
	 */
	private void writeOutBase64(String text) throws IOException, BadLocationException {
		File whatFile = getFileFromChooser(	".", 
											JFileChooser.SAVE_DIALOG, 
											MutableFilter.EXT_BASE64, 
											translatrix.getTranslationString("FiletypeB64"));
											
		if(whatFile != null) {
			String base64text = Base64Codec.encode(text);
			FileWriter fw = new FileWriter(whatFile);
			fw.write(base64text, 0, base64text.length());
			fw.flush();
			fw.close();
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method to invoke loading HTML into the app from a file
	 */
	private void openDocument(File whatFile) throws IOException, BadLocationException {
		if(whatFile == null) {
			whatFile = getFileFromChooser(	".", 
											JFileChooser.OPEN_DIALOG, 
											MutableFilter.EXT_HTML, 
											translatrix.getTranslationString("FiletypeHTML"));
		}
		if(whatFile != null) {
			try {
				loadDocument(whatFile, null);
			} catch(ChangedCharSetException ccse) {
				String charsetType = ccse.getCharSetSpec().toLowerCase();
				int pos = charsetType.indexOf("charset");
				if(pos == -1) {
					throw ccse;
				}
				
				while(pos < charsetType.length() && charsetType.charAt(pos) != '=') {
					pos++;
				}
				
				pos++; // Places file cursor past the equals sign (=)
				String whatEncoding = charsetType.substring(pos).trim();
				loadDocument(whatFile, whatEncoding);
			}
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for loading HTML document into the app from a file, including document encoding setting
	 */
	private void loadDocument(File whatFile, String whatEncoding) throws IOException, BadLocationException {
		Reader r = null;
		currentFile = whatFile;
		htmlDoc = (ExtendedHTMLDocument)(htmlKit.createDefaultDocument());
		try {
			if(whatEncoding == null) {
				r = new InputStreamReader(new FileInputStream(whatFile));
			} else {
				r = new InputStreamReader(new FileInputStream(whatFile), whatEncoding);
				htmlDoc.putProperty("IgnoreCharsetDirective", new Boolean(true));
			}
			htmlKit.read(r, htmlDoc, 0);
			r.close();
			registerDocument(htmlDoc);
			srcPane.setText(htmlPane.getText());
			updateTitle();
		} finally {
			if(r != null) {
				r.close();
			}
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for loading a Base64 encoded document from a file
	 */
	private void openDocumentBase64(File whatFile) throws IOException, BadLocationException {
		if(whatFile == null) {
			whatFile = getFileFromChooser(	".", 
											JFileChooser.OPEN_DIALOG, 
											MutableFilter.EXT_BASE64, 
											translatrix.getTranslationString("FiletypeB64"));
		}
		if(whatFile != null) {
			FileReader fr = new FileReader(whatFile);
			int nextChar = 0;
			StringBuffer encodedText = new StringBuffer();
			try {
				while((nextChar = fr.read()) != -1) {
					encodedText.append((char)nextChar);
				}
				fr.close();
				setDocumentText(addURLPrefixToImagePath(Base64Codec.decode(encodedText.toString())));
				registerDocument((ExtendedHTMLDocument)(htmlPane.getDocument()));
			} finally {
				if(fr != null) {
					fr.close();
				}
			}
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for loading a Stylesheet-File into the app
	 */
	private void openStyleSheet(File fileCSS) throws IOException {
		if(fileCSS == null) {
			fileCSS = getFileFromChooser(	".", 
											JFileChooser.OPEN_DIALOG, 
											MutableFilter.EXT_CSS, 
											translatrix.getTranslationString("FiletypeCSS"));
		}
		if(fileCSS != null) {
			String currDocText = htmlPane.getText();
			htmlDoc = (ExtendedHTMLDocument)(htmlKit.createDefaultDocument());
			styleSheet = htmlDoc.getStyleSheet();
			URL cssUrl = fileCSS.toURL();
			InputStream is = cssUrl.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			styleSheet.loadRules(br, cssUrl);
			br.close();
			htmlDoc = new ExtendedHTMLDocument(styleSheet);
			registerDocument(htmlDoc);
			setDocumentText(currDocText);
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for loading any number of Stylesheets from an array of URLs into the app
	 * @param cssFiles filenames to load 
	 * @param myCodeBase applet's codebase
	 * @throws IOException an IOException
	 */
	private void loadStyleSheets(String[] cssFiles, String myCodeBase) throws IOException {
		if(cssFiles != null) {
			String currDocText = htmlPane.getText();
			htmlDoc = (ExtendedHTMLDocument)(htmlKit.createDefaultDocument());
			styleSheet = htmlDoc.getStyleSheet();
			// try to load each file...
			for (int i=0; i < cssFiles.length; i++) {
				log.debug("loading stylesheet file: " + cssFiles[i]);
				boolean err = false;
				// try to load the file
				InputStream is = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try {
					URL cssUrl = new URL(cssFiles[i]);
					is = cssUrl.openStream();
					isr = new InputStreamReader(is);
					br = new BufferedReader(isr);
					styleSheet.loadRules(br, cssUrl);
					br.close();
				} catch (Exception e1) {
					// if file cannot be found, try again using the codebase in front.
					log.debug("file could not be found, trying again using codebase: " + e1.fillInStackTrace());
					try {
						try {
							br.close();
						} catch (Exception exc2) {}
						try {
							isr.close();
						} catch (Exception e) {}
						try {
							is.close();
						} catch (Exception exc1) {}
						log.debug("done closing streams");
						URL cssUrl = new URL(myCodeBase + cssFiles[i]);
						is = cssUrl.openStream();
						isr = new InputStreamReader(is);
						br = new BufferedReader(isr);
						styleSheet.loadRules(br, cssUrl);
						log.debug("done loading css file...");
					} catch (Exception e2) {
						log.warn("file could not be found: " + e2.fillInStackTrace());
					} finally {
						try {
							br.close();
						} catch (Exception exc2) {}
						try {
							isr.close();
						} catch (Exception e) {}
						try {
							is.close();
						} catch (Exception exc1) {}
					}
				} finally {
					try {
						br.close();
					} catch (Exception exc2) {}
					try {
						isr.close();
					} catch (Exception e) {}
					try {
						is.close();
					} catch (Exception exc1) {}
					log.debug("done closing streams");
				}
			}
			if (styleSheet == null) htmlDoc = new ExtendedHTMLDocument();
			else htmlDoc = new ExtendedHTMLDocument(styleSheet);
			if (htmlDoc == null) htmlDoc = new ExtendedHTMLDocument();
			registerDocument(htmlDoc);
			setDocumentText(currDocText);
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for serializing the document out to a file
	 * 
	 * @param doc a HTMLDocument
	 * @throws IOException an IOException
	 */
	public void serializeOut(HTMLDocument doc) throws IOException {
		File whatFile = getFileFromChooser(	".", 
											JFileChooser.SAVE_DIALOG, 
											MutableFilter.EXT_SER, 
											translatrix.getTranslationString("FiletypeSer"));
											
		if(whatFile != null) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(whatFile));
			oos.writeObject(doc);
			oos.flush();
			oos.close();
			refreshOnUpdate();
		}
	}

	// TODO: check the write methods!
	/** 
	 * Method for reading in a serialized document from a file
	 * 
	 * @throws IOException an io exception
	 * @throws ClassNotFoundException is thrown if class was not found.
	 */
	public void serializeIn() throws IOException, ClassNotFoundException {
		File whatFile = getFileFromChooser(	".", 
											JFileChooser.OPEN_DIALOG, 
											MutableFilter.EXT_SER, 
											translatrix.getTranslationString("FiletypeSer"));
											
		if(whatFile != null) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(whatFile));
			htmlDoc = (ExtendedHTMLDocument)(ois.readObject());
			ois.close();
			registerDocument(htmlDoc);
			refreshOnUpdate();
		}
	}

	/** 
	 * Method for obtaining a File for input/output using a JFileChooser dialog
	 */
	private File getFileFromChooser(String startDir, int dialogType, String[] exts, String desc) {
		JFileChooser jfileDialog = new JFileChooser(startDir);
		jfileDialog.setDialogType(dialogType);
		jfileDialog.setFileFilter(new MutableFilter(exts, desc));
		int optionSelected = JFileChooser.CANCEL_OPTION;
		
		if(dialogType == JFileChooser.OPEN_DIALOG) {
			optionSelected = jfileDialog.showOpenDialog(this);
		} else if(dialogType == JFileChooser.SAVE_DIALOG) {
			optionSelected = jfileDialog.showSaveDialog(this);
		} else {
			optionSelected = jfileDialog.showOpenDialog(this);
		}
		
		if(optionSelected == JFileChooser.APPROVE_OPTION) {
			return jfileDialog.getSelectedFile();
		}
		return (File)null;
	}

	/** 
	 * Method for obtaining an Image for input using a custom JFileChooser dialog
	 */
	private File getImageFromChooser(String startDir, String[] exts, String desc) {
		ImageFileChooser jImageDialog = new ImageFileChooser(startDir);
		jImageDialog.setDialogType(JFileChooser.CUSTOM_DIALOG);
		jImageDialog.setFileFilter(new MutableFilter(exts, desc));
		jImageDialog.setDialogTitle(translatrix.getTranslationString("ImageDialogTitle"));
		int optionSelected = JFileChooser.CANCEL_OPTION;
		optionSelected = jImageDialog.showDialog(this, translatrix.getTranslationString("Insert"));
		
		if(optionSelected == JFileChooser.APPROVE_OPTION) {
			return jImageDialog.getSelectedFile();
		}
		
		return (File)null;
	}

	/** 
	 * Method for describing the node hierarchy of the document
	 */
	private void describeDocument(StyledDocument doc) {
		Element[] elements = doc.getRootElements();
		for(int i = 0; i < elements.length; i++) {
			indent = INDENT_STEP;
			for(int j = 0; j < indent; j++) { 
				log.debug(" "); 
			}
			log.debug(elements[i].toString());
			traverseElement(elements[i]);
			log.debug("");
		}
	}

	/** 
	 * This method is used to tokenize multi-value parameter fields given in the applet
	 *
	 * @param input The string to tokenize
	 * @return An array containing each token of the input string
	 */
	public static String[] tokenize(String input) {
		String[] output = null;
		if (input != null) {
			StringTokenizer st = new StringTokenizer(input, ", ");
			output = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				output[i] = st.nextToken();
				i++;
			}
		}
		return output;
	}

	/** 
	 * Traverses nodes for the describing method
	 */
	private void traverseElement(Element element) {
		indent += INDENT_STEP;
		for(int i = 0; i < element.getElementCount(); i++) {
			for(int j = 0; j < indent; j++) { 
				log.debug(" "); 
			}
			log.debug(element.getElement(i).toString());
			traverseElement(element.getElement(i));
		}
		indent -= INDENT_STEP;
	}

	/** 
	 * Method to locate a node element by name
	 */
	private Element locateElementInDocument(StyledDocument doc, String elementName) {
		Element[] elements = doc.getRootElements();
		for(int i = 0; i < elements.length; i++) {
			if(elements[i].getName().equalsIgnoreCase(elementName)) {
				return elements[i];
			} else {
				Element rtnElement = locateChildElementInDocument(elements[i], elementName);
				if(rtnElement != null) {
					return rtnElement;
				}
			}
		}
		return (Element)null;
	}

	/** 
	 * Traverses nodes for the locating method
	 */
	private Element locateChildElementInDocument(Element element, String elementName) {
		for(int i = 0; i < element.getElementCount(); i++) {
			if(element.getElement(i).getName().equalsIgnoreCase(elementName)) {
				return element.getElement(i);
			}
		}
		return (Element)null;
	}

	/**
	 * Can be used for setting different preferred sizes
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelInterface#getHTMLScrollPane()
	 */
	public JScrollPane getHTMLScrollPane() {
	    return htmlScrollPane; 
	}
	
	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelInterface#getSrcScrollPane()
	 */
	public JScrollPane getSrcScrollPane() {
	    return srcScrollPane; 
	}   

	/** 
	 * Convenience method for obtaining the WYSIWYG JTextPane
	 * @return returns the editor's HTML-view JTextPane
	 */
	public JTextPane getTextPane() {
		return htmlPane;
	}

	/** 
	 * Convenience method for obtaining the Source JTextPane
	 * @return returns the editor's Source-view JTextPane
	 */
	public JTextPane getSourcePane() {
		return srcPane;
	}

	/** 
	 * Convenience method for obtaining the application's Frame
	 * @return returns the editor applications Frame
	 */
	public Frame getFrame() {
		return frameHandler;
	}

	/** 
	 * Convenience method for obtaining the current file handle
	 * @return returns the File Object for the currently edited file.
	 */
	public static File getCurrentFile() {
		return currentFile;
	}

	/** 
	 * Convenience method for obtaining the application name
	 * @return returns the application name as string.
	 */
	public String getAppName() {
		return APP_NAME;
	}

	/** 
	 * Convenience method for obtaining the document text
	 * @return returns the document text as string.
	 */
	public String getDocumentText() {
		String docContent = null;
		updateBeforeSave();
		if (kafenioConfig.isUnicode()){
			docContent = HTMLTranslate.decode(srcPane.getText());
		} else {
			docContent = srcPane.getText();
		}
		if (Utils.checkNullOrEmpty(getConfig().getCodeBase()) == null) {
			return docContent;
		}
		return replaceAbsoluteUrls(docContent);
	}

	/**
	 * updates the sourcePane with the text from the 
	 * htmlPane if the sourcePane is currently not showing on screen
	 */
	private void updateBeforeSave() {
		if (!srcPane.isShowing()) {
			srcPane.setText(htmlPane.getText());
		}
	}

	/**
	 * Method for extracting that part of the original text that is
	 * between the defined html-tag.<BR>
	 * <BR>i.e.: if containingTag is "body" and originalText is 
	 * <code>&lt;body&gt;blafaseltralala&lt;/body&gt;</code> the returned
	 * String is "blafaseltralala".
	 * @param containingTag html-tag without brackets
	 * @param originalText complete text to scan for containing tags
	 * @return returns the text between the given html-tags.
	 */
	private String getSubText(String containingTag, String originalText) {
		if (originalText != null) {
			if (kafenioConfig.isUnicode()) {
				originalText = HTMLTranslate.decode(originalText);
			}
			String docTextCase = originalText.toLowerCase();
			int tagStart       = docTextCase.indexOf("<" + containingTag.toLowerCase());
			int tagStartClose  = docTextCase.indexOf(">", tagStart) + 1;
			String closeTag    = "</" + containingTag.toLowerCase() + ">";
			int tagEndOpen     = docTextCase.indexOf(closeTag);
			if(tagStartClose < 0) { tagStartClose = 0; }
			if(tagEndOpen < 0 || tagEndOpen > docTextCase.length()) { tagEndOpen = docTextCase.length(); }
			return originalText.substring(tagStartClose, tagEndOpen);
		} else {
			return "";
		}
	}
	/**
	 * quits without saving.
	 */
	protected void setAsConfirmedAndQuit() {
		documentConfirmed = true;
		getKafenioParent().hide();
	}

	/**
	 * posts the content body either as plain-urlencoded ascii text or base64 encoded to a
	 * given server-URL
	 * @return returns true if successful, false otherwise.
	 */
	public boolean postContentBody() {
		return postContent(getDocumentBody());
	}

	/**
	 * posts the content body either as plain-urlencoded ascii text or base64 encoded to a
	 * given server-URL
	 * @param contentToPost the content to post.
	 * @return returns true if successful, false otherwise.
	 */
	public boolean postContent(String contentToPost) {
		String bodyContent = null;
		try {
			if ("base64".equalsIgnoreCase(getConfig().getOutputmode())) {
				bodyContent = Base64Codec.encode(contentToPost);
			} else {
				try {
					bodyContent = URLEncoder.encode(contentToPost, "UTF-8");
				} catch (Throwable e) {
					// if system property cannot be read, encode using old encode() method.
					bodyContent = URLEncoder.encode(contentToPost);
				}
			}
			urlFetcher.setPOSTData(getConfig().getContentParameter()+"="+bodyContent);
			urlFetcher.setURL(new URL(getConfig().getPostUrl()));
			urlFetcher.fetch();
			if (!(urlFetcher.getHTTPStatusCode() == 200)) {
				log.warn("Posting content to " 
									+ getConfig().getPostUrl() 
									+ " failed. Server Status Code was: " 
									+ urlFetcher.getResponseStatus());
				return false;
			} else {
				log.info(	"Content successfully posted to: "
									+ getConfig().getPostUrl() 
									+ ". Server Status Code was: " 
									+ urlFetcher.getResponseStatus());
				return true;
			}
		} catch (Exception e) {
			log.error("an error occured while posting content to: " + getConfig().getPostUrl());
			return false;
		} 
	}

	/** 
	 * Convenience method for obtaining the document text
	 * contained within the BODY tags (a common request)
	 * @return the document's body as html-text
	 */
	public String getDocumentBody() {
		log.debug("codebase: " + getConfig().getCodeBase());
		updateBeforeSave();
		if (Utils.checkNullOrEmpty(getConfig().getCodeBase()) == null) {
			return getSubText("body", srcPane.getText());
		}
		return replaceAbsoluteUrls(getSubText("body", srcPane.getText()));
	}

	/**
	 * deletes all ocurrences of the codebase within html-tags.
	 * @param string html-code
	 * @return returns the input string with relative urls.
	 */
	private String replaceAbsoluteUrls(String string) {
		try {
			if (codeBasePattern == null && getConfig().getCodeBase() != null) {
				codeBasePattern = new gnu.regexp.RE("\"" + getConfig().getCodeBase());
			}
			log.debug("replacing codebase: \"" + getConfig().getCodeBase());
			
			return codeBasePattern.substituteAll(string, "\"");
		} catch (Exception e) {
			log.warn("An error ocurred while creating relative urls: " + e.fillInStackTrace());
			return string;
		}
	}

	/** 
	 * Convenience method for setting the document text
	 * contains hack around JDK bug 4799813
	 * see http://developer.java.sun.com/developer/bugParade/bugs/4799813.html
	 * regression in 1.4.x, to be fixed in 1.5
	 * When setting the text to be "&amp; footext", it becomes "&amp;footext" (space disappears)
	 * same ocurrs for "&lt;/a&gt; &amp;amp;", it becomes "&lt;/a&gt;&amp;amp;" (space disappears)
	 * with the hack it now does not occur anymore.
	 * @param sText the html-text of the document
	 */
	public void setDocumentText(String sText) {
		try {
			if( System.getProperty("java.version").substring(0,3).equals("1.4") ) {
				if (pattern1 == null)
					pattern1 = new gnu.regexp.RE("(&\\w+;|&#\\d+;)(\\s|&#160;|&nbsp;)(?=<|&\\w+;|&#\\d+;)");
				sText=pattern1.substituteAll(sText,"$1&#160;$3");
				if (pattern2 == null)
					pattern2 = new gnu.regexp.RE("<(/[^>])>(\\s|&#160;|&nbsp;|\\n\\s+)(?!&#160;)(&\\w+;|&#\\d+;)");
				sText=pattern2.substituteAll(sText,"<$1>&#160;$3$4");
			}
		} catch (gnu.regexp.REException ree) {
			log.error("gnu.regexp.REException in setDocumentText: " + ree.fillInStackTrace());
		}
		htmlPane.setText(HTMLTranslate.decode(addURLPrefixToImagePath(sText)));
		srcPane.setText(htmlPane.getText());
	}

	/** 
	 * Convenience method for obtaining the document title
	 */
	private void updateTitle() {
		frameHandler.setTitle(APP_NAME + (currentFile == null ? "" : " - " + currentFile.getName()));
	}

	/** 
	 * Convenience method for clearing out the UndoManager
	 */
	public void purgeUndos() {
		if(undoManager != null) {
			undoManager.discardAllEdits();
			undoAction.updateUndoState();
			redoAction.updateRedoState();
		}
	}

	/** 
	 * Convenience method for refreshing and displaying changes
	 */
	public void refreshOnUpdate() {
		setDocumentText(addURLPrefixToImagePath(htmlPane.getText()));
		validate();
	}

	/** 
	 * Convenience method for deallocating the app resources
	 */
	public void dispose() {
		quitApp();
//		frameHandler.dispose();
//		System.exit(0);
	}

	/** 
	 * Convenience method for fetching icon images from jar file
	 * @param iconName name of the icon without "HK.gif" at the end
	 * @return returns image as ImageIcon
	 */
	public ImageIcon getKafenioIcon(String iconName) {
		log.debug("trying to fetch icon: " + iconName);
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(	"/" 
																							+ iconName 
																							+ "HK.gif")));
	}

	/**
	 * returns an ImageIcon if isShowMenuIcons() returns true or null if false is returned.
	 * @param iconName name of the icon without "HK.gif" at the end.
	 * @return returns an ImageIcon if isShowMenuIcons() returns true or null if false is returned.
	 */
	public ImageIcon getMenuIcon(String iconName) {
		if (kafenioConfig.isShowMenuIcons()) return getKafenioIcon(iconName);
		return null;
	}

	/** 
	 * Convenience method for toggling source window visibility
	 */
	public void toggleSourceWindow() {
		if(!srcScrollPane.isShowing()) {
			srcPane.setText(htmlPane.getText());
			mainPane.removeAll();
			mainPane.add(srcScrollPane, BorderLayout.CENTER);
		} else {
			setDocumentText(addURLPrefixToImagePath(srcPane.getText()));
			mainPane.removeAll();
			mainPane.add(htmlScrollPane, BorderLayout.CENTER);
		}
		repaint();
		validate();
		kafenioMenuBar.getViewSourceItem().setSelected(srcScrollPane.isShowing());
	}

	/**
	 * Searches the specified element for CLASS attribute setting
	 * 
	 * @param element element to find style for
	 * @return returns the corresponding style.
	 */
	private String findStyle(Element element) {
		AttributeSet as = element.getAttributes();
		if(as == null) {
			return null;
		}
		Object val = as.getAttribute(HTML.Attribute.CLASS);
		if(val != null && (val instanceof String)) {
			return (String)val;
		}
		for(Enumeration e = as.getAttributeNames(); e.hasMoreElements();) {
			Object key = e.nextElement();
			if(key instanceof HTML.Tag) {
				AttributeSet eas = (AttributeSet)(as.getAttribute(key));
				if(eas != null) {
					val = eas.getAttribute(HTML.Attribute.CLASS);
					if(val != null) {
						return (String)val;
					}
				}
			}

		}
		return null;
	}

	/** 
	 * Handles caret tracking and related events, such as displaying the current style
	 * of the text under the caret
	 * @param ce the CaretEvent to handle
	 */
	private void handleCaretPositionChange(CaretEvent ce) {
		int caretPos = ce.getDot();
		Element	element = htmlDoc.getCharacterElement(caretPos);
		if(element == null) {
			return;
		}
		String style = null;
		Vector vcStyles = new Vector();
		while(element != null) {
			if(style == null) {
				style = findStyle(element);
			}
			vcStyles.add(element);
			element = element.getParentElement();
		}
		int stylefound = -1;
		if(style != null) {
			for(int i = 0; i < kafenioToolBar1.getStyleSelector().getItemCount(); i++) {
				String in = (String)(kafenioToolBar1.getStyleSelector().getItemAt(i));
				if(in.equalsIgnoreCase(style)) {
					stylefound = i;
					break;
				}
			}
		}
		if(stylefound > -1) {
			Action ac = kafenioToolBar1.getStyleSelector().getAction();
			ac.setEnabled(false);
			kafenioToolBar1.getStyleSelector().setSelectedIndex(stylefound);
			ac.setEnabled(true);
		} else {
		    try {
                kafenioToolBar1.getStyleSelector().setSelectedIndex(0);
            } catch (Exception e) {
            }
		}
	}

	/**
	 * @return returns the currently used ExtendedHTMLDocument Object
	 */
	public ExtendedHTMLDocument getExtendedHtmlDoc() {
		return (ExtendedHTMLDocument)htmlDoc;
	}
	
	/**
	 * @return returns the currently used ExtendedHTMLEditorKit Object.
	 */
	public ExtendedHTMLEditorKit getExtendedHtmlKit() {
		return htmlKit;
	}

	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelInterface#getHTMLEditorKit()
	 */
	public HTMLEditorKit getHTMLEditorKit() {
	    return (HTMLEditorKit)htmlKit;
	}
	
	/**
	 * @return returns the current caret position
	 */
	public int getCaretPosition() {
		return htmlPane.getCaretPosition();
	}

	/**
	 * sets the new caret position
	 * @param newPositon new position of the caret.
	 */
	public void setCaretPosition(int newPositon) {
		boolean end = true;
		do {
			end = true;
			try {
				htmlPane.setCaretPosition(newPositon);
			} catch (IllegalArgumentException iae) {
				end = false;
				newPositon--;
			}
		} while(!end && newPositon >= 0);
	}

/* accessor methods*/
	/**
	 * @return returns the parent KafenioContainerInterface of this Class
	 */
	public Container getKafenioParent() {
		return kafenioParent;
	}
	
	/**
	 * @param newApplet parent KafenioContainerInterface
	 */
	public void setKafenioParent(Window newParent) {
		kafenioParent = newParent;
	}
	
	/**
	 * @return returns redo action
	 */
	public RedoAction getRedoAction() {
		return redoAction;
	}
	
	/**
	 * @return returns undo action
	 */
	public UndoAction getUndoAction() {
		return undoAction;
	}

	/**
	 * @return returns a java.awt.datatransfer.Clipboard object, null if clipboard is not available.
	 */
	public java.awt.datatransfer.Clipboard getSysClipboard() {
		return sysClipboard;
	}
		
	/**
	 * @return returns the current configuration.
	 */
	public KafenioPanelConfiguration getConfig() {
		return kafenioConfig;
	}

	/**
	 * @return returns the translatrix-component for this editor component
	 * @param stringToTranslate the string that is to be translated.
	 */
	public String getTranslation(String stringToTranslate) {
		try {
		    return translatrix.getTranslationString(stringToTranslate); 
		} catch (Exception ex) {
		    return stringToTranslate; 
		}
	}

	/**
	 * @return returns the current KafenioPanelActions.
	 */
	public KafenioPanelActions getKafenioActions() {
		return kafenioActions;
	}
	
/* Inner Classes --------------------------------------------- */

	/** 
	 * Class for implementing Undo as an autonomous action
	 */
	class UndoAction extends AbstractAction {		
		/**
		 * creates a new UndoAction Object.
		 */
		public UndoAction() {
			super(translatrix.getTranslationString("Undo"));
			setEnabled(false);
		}

		/**
		 * handles the given ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.undo();
			} catch(CannotUndoException ex) {
				log.warn("Exception while performing undo: " + ex.fillInStackTrace());
			}
			updateUndoState();
			redoAction.updateRedoState();
		}

		/**
		 * updates the undo state
		 */
		protected void updateUndoState() {
			if(undoManager.canUndo()) {
				setEnabled(true);
				putValue(Action.SHORT_DESCRIPTION, undoManager.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.SHORT_DESCRIPTION, translatrix.getTranslationString("Undo"));
			}
		}
	}

	/** 
	 * Class for implementing Redo as an autonomous action
	 */
	class RedoAction extends AbstractAction {		
		/**
		 * creates a new RedoAction Object
		 */
		public RedoAction() {
			super(translatrix.getTranslationString("Redo"));
			setEnabled(false);
		}

		/**
		 * handles the given ActionEvent.
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.redo();
			} catch(CannotUndoException ex) {
				log.warn("Exception while performing redo: " + ex.fillInStackTrace());
			}
			updateRedoState();
			undoAction.updateUndoState();
		}

		/**
		 * updates the redo state
		 */
		protected void updateRedoState() {
			if(undoManager.canRedo()) {
				setEnabled(true);
				putValue(Action.SHORT_DESCRIPTION, undoManager.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.SHORT_DESCRIPTION, translatrix.getTranslationString("Redo"));
			}
		}
	}

	/** 
	 * Class for implementing the Undo listener to handle the Undo and Redo tActions
	 */
	class CustomUndoableEditListener implements UndoableEditListener {

		/**
		 * handles the given UndoableEditEvent
		 */
		public void undoableEditHappened(UndoableEditEvent uee) {
			undoManager.addEdit(uee.getEdit());
			undoAction.updateUndoState();
			redoAction.updateRedoState();
		}
	}

	/**
	 * ask for confirmation if there are unsaved changes.
	 */
	public void quitApp(){
		if(undoManager.canUndo()){
			if (JOptionPane.showConfirmDialog(	getParent(), 
												translatrix.getTranslationString("QuitWithoutSave"), 
												"", 
												JOptionPane.YES_NO_OPTION, 
												JOptionPane.QUESTION_MESSAGE) == 0) 
			{
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
}
