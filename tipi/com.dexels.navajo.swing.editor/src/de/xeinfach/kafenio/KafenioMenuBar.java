package de.xeinfach.kafenio;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: class is used to create a Menubar for the Kafenio Editor Applet.
 * @author Karsten Pawlik
 */
public class KafenioMenuBar {

	private static LeanLogger log = new LeanLogger("KafenioMenuBar.class");
	
	public static final String KEY_MENU_FILE   = "file";
	public static final String KEY_MENU_EDIT   = "edit";
	public static final String KEY_MENU_VIEW   = "view";
	public static final String KEY_MENU_FONT   = "font";
	public static final String KEY_MENU_FORMAT = "format";
	public static final String KEY_MENU_INSERT = "insert";
	public static final String KEY_MENU_TABLE  = "table";
	public static final String KEY_MENU_FORMS  = "forms";
	public static final String KEY_MENU_SEARCH = "search";
	public static final String KEY_MENU_TOOLS  = "tools";
	public static final String KEY_MENU_HELP   = "help";
	public static final String KEY_MENU_DEBUG  = "debug";
	public static final String DOTS = "...";

	private KafenioPanel parent;
	private KafenioPanelConfiguration config;
	private JMenuBar menuBar;
	private Hashtable menus;
	private JCheckBoxMenuItem viewToolbarItem;
	private JCheckBoxMenuItem viewSourceItem;
	private static ArrayList parameters = new ArrayList();

	static {
		parameters.add(KEY_MENU_FILE);
		parameters.add(KEY_MENU_EDIT);
		parameters.add(KEY_MENU_VIEW);
		parameters.add(KEY_MENU_FONT);
		parameters.add(KEY_MENU_FORMAT);
		parameters.add(KEY_MENU_INSERT);
		parameters.add(KEY_MENU_TABLE);
		parameters.add(KEY_MENU_FORMS);
		parameters.add(KEY_MENU_SEARCH);
		parameters.add(KEY_MENU_TOOLS);
		parameters.add(KEY_MENU_HELP);
		parameters.add(KEY_MENU_DEBUG);
	}
	
	/**
	 * creates a new KafenioMenuBar Object based on the given values
	 * @param newParent the parent object for this class
	 */
	public KafenioMenuBar(KafenioPanel newParent) {
		parent = newParent;
		menuBar = new JMenuBar();
		config = parent.getConfig();
		log.debug("initializing menu hashtable.");
		initMenuHashtable();
	}

	/**
	 * initializes the menu hashtable
	 */
	private void initMenuHashtable() {
		log.debug("initializing...");
		menus = new Hashtable();
		menus.put(KEY_MENU_FILE, getFileMenu());
		log.debug("added File Menu.");
		menus.put(KEY_MENU_EDIT, getEditMenu());
		log.debug("added Edit Menu.");
		menus.put(KEY_MENU_VIEW, getViewMenu());
		log.debug("added View Menu.");
		menus.put(KEY_MENU_FONT, getFontMenu());
		log.debug("added Font Menu.");
		menus.put(KEY_MENU_FORMAT, getFormatMenu());
		log.debug("added Format Menu.");
		menus.put(KEY_MENU_SEARCH, getSearchMenu());
		log.debug("added Search Menu.");
		menus.put(KEY_MENU_INSERT, getInsertMenu());
		log.debug("added Insert Menu.");
		menus.put(KEY_MENU_TABLE, getTableMenu());
		log.debug("added Table Menu.");
		menus.put(KEY_MENU_FORMS, getFormsMenu());
		log.debug("added Forms Menu.");
		menus.put(KEY_MENU_HELP, getHelpMenu());
		log.debug("added Help Menu.");
		menus.put(KEY_MENU_DEBUG, getDebugMenu());
		log.debug("added Debug Menu\n...done.");
	}

	/**
	 * @return returns all available keys as a Set.
	 */
	public static ArrayList getOrderedMenuKeys() {
		return parameters;
	}

	/** 
	 * Convenience method for obtaining a custom menu bar
	 * @param customMenuItems menus to show
	 * @return returns a custom JMenuBar Object.
	 */
	public JMenuBar createCustomMenuBar(Vector customMenuItems) {
		JMenuBar customMenuBar = new JMenuBar();
		customMenuBar.setBackground(config.getBgcolor());
		for(int i = 0; i < customMenuItems.size(); i++) {
			String menuToAdd = ((String)(customMenuItems.elementAt(i))).toLowerCase();
			if(menus.containsKey(menuToAdd)) {
				customMenuBar.add((JMenu)(menus.get(menuToAdd)));
			}
		}
		return customMenuBar;
	}

	/**
	 * creates the complete menubar including every single menuitem.
	 * @return returns the default menubar
	 */
	public JMenuBar createDefaultKafenioMenuBar() {
		JMenuBar newMenuBar = new JMenuBar();
		newMenuBar.setBackground(config.getBgcolor());
		
		for(int i=0; i < getOrderedMenuKeys().size(); i++) {
			if(menus.containsKey((String)getOrderedMenuKeys().get(i))) {
				newMenuBar.add((JMenu)menus.get((String)getOrderedMenuKeys().get(i)));
			}
		}
		return newMenuBar;
	}

	/**
	 * @return returns the debug menu
	 */
	public JMenu getDebugMenu() {
		JMenu debugMenu = getNewMenu("Debug");

		debugMenu.add(new KafenioMenuItem(this, getString("DescribeDoc"), "describe",getParent()));
		debugMenu.add(new KafenioMenuItem(this, getString("DescribeCSS"), "describecss",getParent()));
		debugMenu.add(new KafenioMenuItem(this, getString("WhatTags"), "whattags", getParent()));

		return debugMenu;
	}

	/**
	 * @return returns the help menu
	 */
	public JMenu getHelpMenu() {
		JMenu helpMenu = getNewMenu("Help");
		helpMenu.add(new KafenioMenuItem(this, getString("About"), "helpabout", getParent()));
		return helpMenu;
	}

	/**
	 * @return returns the search menu
	 */
	public JMenu getSearchMenu() {
		JMenu searchMenu = getNewMenu("Search");
		
		searchMenu.add(new KafenioMenuItem(	this,
										 	getString("SearchFind"), 
											"find",
											getParent(),
											KeyStroke.getKeyStroke('F', java.awt.Event.CTRL_MASK, false)));
		searchMenu.add(new KafenioMenuItem(	this,
										 	getString("SearchFindAgain"),
											"findagain",
											getParent(),
											KeyStroke.getKeyStroke('G', java.awt.Event.CTRL_MASK, false)));
		searchMenu.add(new KafenioMenuItem(	this,
										 	getString("SearchReplace"),
											"replace",
											getParent(),
											KeyStroke.getKeyStroke('R', java.awt.Event.CTRL_MASK, false)));
		return searchMenu;
	}

	/**
	 * @return returns the forms menu.
	 */
	public JMenu getFormsMenu() {
		JMenu formsMenu = getNewMenu("Forms");

		formsMenu.add(new KafenioMenuItem(this, getString("FormInsertForm"), "insertform", getParent()));
		formsMenu.addSeparator();
		formsMenu.add(new KafenioMenuItem(this, getString("FormTextfield"), "inserttextfield", getParent()));
		formsMenu.add(new KafenioMenuItem(this, getString("FormTextarea"), "inserttextarea", getParent()));
		formsMenu.add(new KafenioMenuItem(this, getString("FormCheckbox"), "insertcheckbox", getParent()));
		formsMenu.add(new KafenioMenuItem(this, getString("FormRadio"), "insertradiobutton", getParent()));
		formsMenu.add(new KafenioMenuItem(this, getString("FormPassword"), "insertpassword", getParent()));
		formsMenu.addSeparator();
		formsMenu.add(new KafenioMenuItem(this, getString("FormButton"), "insertbutton", getParent()));
		formsMenu.add(new KafenioMenuItem(this, getString("FormButtonSubmit"), "insertbuttonsubmit", getParent()));
		formsMenu.add(new KafenioMenuItem(this, getString("FormButtonReset"), "insertbuttonreset", getParent()));
		return formsMenu;
	}

	/**
	 * @return returns the table menu
	 */
	public JMenu getTableMenu() {
		JMenu tableMenu = getNewMenu("Table");

		tableMenu.add(new KafenioMenuItem(	this, 
											getString("InsertTable") + DOTS, 
											"inserttable", 
											getParent(), 
											null, 
											getParent().getMenuIcon("Table")));
		tableMenu.addSeparator();
		tableMenu.add(new KafenioMenuItem(this, getString("InsertTableRow"), "inserttablerow", getParent()));
		tableMenu.add(new KafenioMenuItem(this, getString("InsertTableColumn"), "inserttablecolumn", getParent()));
		tableMenu.addSeparator();
		tableMenu.add(new KafenioMenuItem(this, getString("DeleteTableRow"), "deletetablerow", getParent()));
		tableMenu.add(new KafenioMenuItem(this, getString("DeleteTableColumn"), "deletetablecolumn", getParent()));
		return tableMenu;
	}

	/**
	 * @return returns the insert menu
	 */
	public JMenu getInsertMenu() {
		JMenu insertMenu = getNewMenu("Insert");

		//insertMenu.add(new KafenioMenuItem(this, getActionList().getActionInsertAnchor(), null, null, false));
		insertMenu.add(new KafenioMenuItem(	this, 
											getActionList().getActionInsertHyperlink(), 
											null, 
											getParent().getMenuIcon("Anchor")));
		insertMenu.add(new KafenioMenuItem(	this,
											getString("InsertBreak"), 
											"insertbreak",
											getParent(), 
											KeyStroke.getKeyStroke(	KeyEvent.VK_ENTER, 
																	java.awt.Event.SHIFT_MASK, false)));
		insertMenu.add(new KafenioMenuItem(this, getString("InsertNBSP"), "insertnbsp", getParent()));
		insertMenu.add(new KafenioMenuItem(this,(Action)getParent().getTActions().get("InsertHR"), null, null));
		insertMenu.add(new KafenioMenuItem(	this, 
											getActionList().getActionInsertCharacter(), 
											null, 
											getParent().getMenuIcon("Character")));
		insertMenu.add(new KafenioMenuItem(this, getActionList().getActionAddParagraph(), null, null));
		insertMenu.addSeparator();


    /** @todo RLR et FF rajout d'un subMenu si on est pas en Applet sinon on met que image Serveur*/
    if (!config.isApplet()) {
      JMenu imgMenu = getNewMenu("InsertLocalImage");
      imgMenu.setIcon(getParent().getMenuIcon("InsertImage"));
      imgMenu.add(new KafenioMenuItem(this,
                                      getString("InsertLocalImage") + DOTS,
                                      "insertlocalimage",
                                      getParent(),
                                      null,
                                      null));

      imgMenu.add(new KafenioMenuItem(this,
                                      getActionList().
                                      getActionInsertServerImage(), null, null));
      insertMenu.add(imgMenu);
    }
    else {

      insertMenu.add(new KafenioMenuItem(this,
                                         getActionList().
                                         getActionInsertServerImage(), null,
                                         getParent().getMenuIcon("InsertImage")));
    }

/*
		if (!config.isApplet()) {
			insertMenu.add(new KafenioMenuItem(	this, 
												getString("InsertLocalImage") + DOTS, 
												"insertlocalimage", 
												getParent(), 
												null, 
												null));
		}
		insertMenu.add(new KafenioMenuItem(this, getActionList().getActionInsertServerImage(), null, null));
*/		return insertMenu;
	}


	/**
	 * @return returns the format menu
	 */
	public JMenu getFormatMenu() {
		JMenu formatMenu = getNewMenu("Format");

		formatMenu.add(getFormatAlignMenu());
		formatMenu.addSeparator();
		formatMenu.add(getFormatHeadingMenu());
		formatMenu.addSeparator();
		formatMenu.add(new KafenioMenuItem(	this,
										 	getActionList().getActionListUnordered(), 
											null, 
											getParent().getMenuIcon("UList")));
		formatMenu.add(new KafenioMenuItem(	this,
										 	getActionList().getActionListOrdered(), 
											null, 
											getParent().getMenuIcon("OList")));
		formatMenu.add(new KafenioMenuItem(	this, 
											getActionList().getActionAddListItem(), 
											null, 
											null));
		formatMenu.addSeparator();
		formatMenu.add(new KafenioMenuItem(	this, 
											getActionList().getActionBlockquote(), 
											null, 
											null));
		formatMenu.add(new KafenioMenuItem(this, getActionList().getActionPre(), null, null));
		formatMenu.add(new KafenioMenuItem(this, getActionList().getActionStrong(), null, null));
		formatMenu.add(new KafenioMenuItem(	this, 
											getActionList().getActionEmphasis(), 
											null, 
											null));
		formatMenu.add(new KafenioMenuItem(this, getActionList().getActionTT(), null, null));
		formatMenu.add(new KafenioMenuItem(this, getActionList().getActionSpan(), null, null));
		formatMenu.add(new KafenioMenuItem(	this, 
											getActionList().getActionToggleCase(), 
											null, 
											null));
		formatMenu.addSeparator();
		formatMenu.add(new KafenioMenuItem(	this,
										 	getActionList().getActionClearFormat(), 
											null, 
											getParent().getMenuIcon("ClearFormat")));
		return formatMenu;
	}

	/**
	 * @return returns the format -> heading submenu
	 */
	public JMenu getFormatHeadingMenu() {
		JMenu formatHeadingMenu = getNewMenu("Heading");
	
		formatHeadingMenu.add(new KafenioMenuItem(this, getActionList().getActionHeading1(), null, null));
		formatHeadingMenu.add(new KafenioMenuItem(this, getActionList().getActionHeading2(), null, null));
		formatHeadingMenu.add(new KafenioMenuItem(this, getActionList().getActionHeading3(), null, null));
		formatHeadingMenu.add(new KafenioMenuItem(this, getActionList().getActionHeading4(), null, null));
		formatHeadingMenu.add(new KafenioMenuItem(this, getActionList().getActionHeading5(), null, null));
		formatHeadingMenu.add(new KafenioMenuItem(this, getActionList().getActionHeading6(), null, null));

		return formatHeadingMenu;
	}

	/**
	 * @return returns the format -> align submenu
	 */
	public JMenu getFormatAlignMenu() {
		JMenu formatAlignMenu = getNewMenu("Align");

		formatAlignMenu.add(new KafenioMenuItem(this, 
												getActionList().getActionAlignLeft(), 
												null, 
												getParent().getMenuIcon("AlignLeft")));
		formatAlignMenu.add(new KafenioMenuItem(this, 
												getActionList().getActionAlignCenter(), 
												null, 
												getParent().getMenuIcon("AlignCenter")));
		formatAlignMenu.add(new KafenioMenuItem(this, 
												getActionList().getActionAlignRight(), 
												null, 
												getParent().getMenuIcon("AlignRight")));
		formatAlignMenu.add(new KafenioMenuItem(this, 
												getActionList().getActionAlignJustified(), 
												null, 
												getParent().getMenuIcon("AlignJustified")));
		return formatAlignMenu;
	}

	/**
	 * @return returns the font menu
	 */
	public JMenu getFontMenu() {
		Hashtable customAttr;
		JMenu fontMenu = getNewMenu("Font");

		fontMenu.add(new KafenioMenuItem(	this, 	
											getActionList().getActionFontBold(),
											KeyStroke.getKeyStroke('B', java.awt.Event.CTRL_MASK, false),
											getParent().getMenuIcon("Bold")));
		fontMenu.add(new KafenioMenuItem(	this, 	
											getActionList().getActionFontItalic(),
											KeyStroke.getKeyStroke('I', java.awt.Event.CTRL_MASK, false),
											getParent().getMenuIcon("Italic")));
		fontMenu.add(new KafenioMenuItem(	this, 	
											getActionList().getActionFontUnderline(),
											KeyStroke.getKeyStroke('U', java.awt.Event.CTRL_MASK, false),
											getParent().getMenuIcon("Underline")));
		fontMenu.add(new KafenioMenuItem(	this, 	
											getActionList().getActionFontStrike(),
											null,
											getParent().getMenuIcon("Strike")));
		fontMenu.addSeparator();
		
		fontMenu.add(new KafenioMenuItem(this, getActionList().getActionFormatBig(), null, null));
		fontMenu.add(new KafenioMenuItem(this, getActionList().getActionFormatSmall(), null, null));
		fontMenu.add(getFontSizeMenu());
		fontMenu.addSeparator();
		fontMenu.add(new KafenioMenuItem(	this,
										 	getActionList().getActionFontSuperscript(), 
											null, 
											getParent().getMenuIcon("Super")));
		fontMenu.add(new KafenioMenuItem(	this,
										 	getActionList().getActionFontSubscript(), 
											null, 
											getParent().getMenuIcon("Sub")));
		fontMenu.addSeparator();
		fontMenu.add(new KafenioMenuItem(	this,
										 	getString("FontSerif"), 
											(Action)getParent().getTActions().get("font-family-Serif"),
											null,
											null));
		fontMenu.add(new KafenioMenuItem(	this,
										 	getString("FontSansserif"), 
											(Action)getParent().getTActions().get("font-family-SansSerif"),
											null,
											null));
		fontMenu.add(new KafenioMenuItem(	this,
										 	getString("FontMonospaced"), 
											(Action)getParent().getTActions().get("font-family-Monospaced"),
											null,
											null));
		fontMenu.add(new KafenioMenuItem(	this, 
											getString("FontSelect"), 
											getActionList().getActionSelectFont(), 
											null, 
											null));
		fontMenu.addSeparator();
		fontMenu.add(getFontColorMenu());

		return fontMenu;
	}

	/**
	 * @return returns font -> font color menu
	 */
	public JMenu getFontColorMenu() {
		Hashtable customAttr;
		JMenu fontColorMenu = getNewMenu("Color");
		fontColorMenu.setIcon(getParent().getMenuIcon("Color"));

		customAttr = new Hashtable(); customAttr.put("color","black");
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontCustomColor(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorAqua(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorBlack(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorBlue(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorFuschia(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorGray(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorGreen(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorLime(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorMaroon(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorNavy(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorOlive(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorPurple(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorSilver(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorTeal(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorWhite(), null, null));
		fontColorMenu.add(new KafenioMenuItem(this, getActionList().getActionFontColorYellow(), null, null));

		return fontColorMenu;
	}

	/**
	 * @return returns font -> fontsize submenu
	 */
	public JMenu getFontSizeMenu() {
		JMenu fontSizeMenu = getNewMenu("FontSize");

		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize1(), null, null));
		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize2(), null, null));
		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize3(), null, null));
		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize4(), null, null));
		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize5(), null, null));
		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize6(), null, null));
		fontSizeMenu.add(new KafenioMenuItem(this, getActionList().getActionFontSize7(), null, null));

		return fontSizeMenu;
	}

	/**
	 * @return returns the view menu
	 */
	public JMenu getViewMenu() {
		JMenu viewMenu = getNewMenu("View");

		viewToolbarItem = new JCheckBoxMenuItem(getString("ViewToolbar"), false);
		viewToolbarItem.setActionCommand("toggletoolbar");
		viewToolbarItem.addActionListener(parent);
		viewToolbarItem.setBackground(config.getBgcolor());
		viewMenu.add(viewToolbarItem);
		log.debug("added ViewToolbar Menuitem: toolbar1: " + config.isShowToolbar());

		viewSourceItem = 
			new JCheckBoxMenuItem(getString("ViewSource"), false);
		viewSourceItem.setActionCommand("viewsource");
		viewSourceItem.addActionListener(parent);
		viewSourceItem.setBackground(config.getBgcolor());
		viewMenu.add(viewSourceItem);
		log.debug("added ViewSource Menuitem");

		return viewMenu;
	}

	/**
	 * @return returns the edit menu
	 */
	public JMenu getEditMenu() {
		/* EDIT Menu */
		JMenu editMenu = getNewMenu("Edit");
		if (!"off".equalsIgnoreCase(config.getOutputmode())) {
			if ("true".equalsIgnoreCase(config.getProperty("confirmRatherThanPost"))) {
				editMenu.add(new KafenioMenuItem(	this,
				getString("ConfirmContent"),
				"confirmcontent", 
				getParent(),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, java.awt.Event.ALT_MASK, false)));
			} else {
				editMenu.add(new KafenioMenuItem(	this,
				getString("SaveBody"),
				"savecontent", 
				getParent(),
				KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK, false)));
			}
			editMenu.addSeparator();
		}
		if (getParent().getSysClipboard() != null) {
			editMenu.add(new KafenioMenuItem(	this,
											 	getString("Cut"), 
												"textcut", 
												getParent(), 
												KeyStroke.getKeyStroke('X', java.awt.Event.CTRL_MASK, false),
												getParent().getMenuIcon("Cut")));
			editMenu.add(new KafenioMenuItem(	this,
											 	getString("Copy"), 
												"textcopy", 
												getParent(), 
												KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK, false),
												getParent().getMenuIcon("Copy")));
			editMenu.add(new KafenioMenuItem(	this,
											 	getString("Paste"), 
												"textpaste", 
												getParent(), 
												KeyStroke.getKeyStroke('V', java.awt.Event.CTRL_MASK, false),
												getParent().getMenuIcon("Paste")));
		} else {
			editMenu.add(new KafenioMenuItem(	this,
											 	getString("Cut"), 
												new DefaultEditorKit.CutAction(),
												KeyStroke.getKeyStroke('X', java.awt.Event.CTRL_MASK, false),
												getParent().getMenuIcon("Cut")));
			editMenu.add(new KafenioMenuItem(	this,
											 	getString("Copy"), 
												new DefaultEditorKit.CopyAction(),
												KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK, false),
												getParent().getMenuIcon("Copy")));
			editMenu.add(new KafenioMenuItem(	this,
											 	getString("Paste"), 
												new DefaultEditorKit.PasteAction(),
												KeyStroke.getKeyStroke('V', java.awt.Event.CTRL_MASK, false),
												getParent().getMenuIcon("Paste")));
		}
		editMenu.addSeparator();
		editMenu.add(new KafenioMenuItem(	this,
										 	getParent().getUndoAction(), 
											KeyStroke.getKeyStroke('Z', java.awt.Event.CTRL_MASK, false), 
											getParent().getMenuIcon("Undo")));
		editMenu.add(new KafenioMenuItem(	this,
										 	getParent().getRedoAction(), 
											KeyStroke.getKeyStroke('Y', java.awt.Event.CTRL_MASK, false), 
											getParent().getMenuIcon("Redo")));
		editMenu.addSeparator();
		editMenu.add(new KafenioMenuItem(	this,
										 	getString("SelectAll"),
											(Action)getParent().getTActions().get(DefaultEditorKit.selectAllAction),
											KeyStroke.getKeyStroke('A', java.awt.Event.CTRL_MASK, false),
											null));		
		editMenu.add(new KafenioMenuItem(	this,
										 	getString("SelectParagraph"),
											(Action)getParent().getTActions().get(
												DefaultEditorKit.selectParagraphAction),
											null,
											null));		
		editMenu.add(new KafenioMenuItem(	this,
										 	getString("SelectLine"),
											(Action)getParent().getTActions().get(DefaultEditorKit.selectLineAction),
											null,
											null));		
		editMenu.add(new KafenioMenuItem(	this,
										 	getString("SelectWord"),
											(Action)getParent().getTActions().get(DefaultEditorKit.selectWordAction),
											null,
											null));		
		return editMenu;
	}

	/**
	 * @return returns the file menu
	 */
	public JMenu getFileMenu() {
		/* FILE Menu */
		JMenu fileMenu = getNewMenu("File");

		fileMenu.add(new KafenioMenuItem(	this,
										 	getString("NewDocument"), 
											"newdoc", 
											getParent(),
											KeyStroke.getKeyStroke('N', java.awt.Event.CTRL_MASK, false),
											getParent().getMenuIcon("New")));
		fileMenu.add(new KafenioMenuItem(	this,
										 	getString("OpenDocument") + DOTS, 
											"openhtml", 
											getParent(),
											KeyStroke.getKeyStroke('O', java.awt.Event.CTRL_MASK, false),
											getParent().getMenuIcon("Open")));
		fileMenu.add(new KafenioMenuItem(this, getString("OpenStyle"), "opencss", getParent(), null, null));
		fileMenu.add(new KafenioMenuItem(this, getString("OpenBase64Document"), "openb64", getParent(), null, null));
		fileMenu.addSeparator();
		fileMenu.add(new KafenioMenuItem(	this,
										 	getString("Save"), 
											"save", 
											getParent(),
											KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK, false),
											getParent().getMenuIcon("Save")));
		fileMenu.add(new KafenioMenuItem(this, getString("SaveAs") + DOTS, "saveas", getParent(), null, null));
		fileMenu.add(new KafenioMenuItem(this, getString("SaveBody") + DOTS, "savebody", getParent(), null, null));
		fileMenu.add(new KafenioMenuItem(this, getString("SaveRTF") + DOTS, "savertf", getParent(), null, null));
		fileMenu.add(new KafenioMenuItem(this, getString("SaveB64") + DOTS, "saveb64", getParent(), null, null));
		fileMenu.addSeparator();
		fileMenu.add(new KafenioMenuItem(this, getString("Serialize") + DOTS, "serialize", getParent(), null, null));
		fileMenu.add(new KafenioMenuItem(	this, 
											getString("ReadFromSer") + DOTS, 
											"readfromser", 
											getParent(), 
											null, 
											null));
		fileMenu.addSeparator();
		fileMenu.add(new KafenioMenuItem(this, getString("Exit"), "exit", getParent(), null, null));
		
		return fileMenu;
	}
	
	/**
	 * @return returns the currently created menubar
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * @return returns the parent of this object.
	 */
	public KafenioPanel getParent() {
		return parent;
	}

	/**
	 * @param bar the menubar to set
	 */
	public void setMenuBar(JMenuBar bar) {
		menuBar = bar;
	}

	/**
	 * @param object the to set the parent to.
	 */
	public void setParent(KafenioPanel object) {
		parent = object;
	}

	/**
	 * @return returns JCheckBoxMenuItem for source view
	 */
	public JCheckBoxMenuItem getViewSourceItem() {
		return viewSourceItem;
	}

	/**
	 * @return returns JCheckBoxMenuItem for toolbar view.
	 */
	public JCheckBoxMenuItem getViewToolbarItem() {
		return viewToolbarItem;
	}

	/**
	 * creates a new JMenu Object and sets the background color.
	 * @param translationID menuname to translate
	 * @return returns a new JMenu Object.
	 */
	public JMenu getNewMenu(String translationID) {
		JMenu newMenu = new JMenu(getString(translationID));
		newMenu.setBackground(config.getBgcolor());
		return newMenu;
	}
	
	/**
	 * convenience method to replace getActionList().
	 * @return returns the same as getActionList()
	 */
	private KafenioPanelActions getActionList() {
		return getParent().getKafenioActions();
	}

	/**
	 * returns a translated representation of the given string.
	 * @param stringToTranslate the string to translate.
	 * @return returns a translated representation of the given string.
	 */
	private String getString(String stringToTranslate) {
		return getParent().getTranslation(stringToTranslate);
	}
}
