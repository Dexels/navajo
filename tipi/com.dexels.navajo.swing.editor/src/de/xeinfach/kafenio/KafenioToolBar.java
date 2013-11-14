package de.xeinfach.kafenio;

import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;
import javax.swing.text.DefaultEditorKit;

import de.xeinfach.kafenio.action.StylesAction;
import de.xeinfach.kafenio.component.JButtonNoFocus;
import de.xeinfach.kafenio.component.JComboBoxNoFocus;
import de.xeinfach.kafenio.component.JToggleButtonNoFocus;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: creates a toolbar for the KafenioPanel based on the given
 * parameters.
 * @author Karsten Pawlik
 */
public class KafenioToolBar {

	private static LeanLogger log = new LeanLogger("KafenioToolBar.class");	

	public static final String KEY_TOOL_SEP = "separator";
	public static final String KEY_TOOL_NEW = "new";
	public static final String KEY_TOOL_OPEN = "open";
	public static final String KEY_TOOL_SAVE = "save";
	public static final String KEY_TOOL_CUT = "cut";
	public static final String KEY_TOOL_COPY = "copy";
	public static final String KEY_TOOL_PASTE = "paste";
	public static final String KEY_TOOL_FIND = "find";
	public static final String KEY_TOOL_BOLD = "bold";
	public static final String KEY_TOOL_ITALIC = "italic";
	public static final String KEY_TOOL_UNDERLINE = "underline";
	public static final String KEY_TOOL_STRIKE = "strike";
	public static final String KEY_TOOL_SUPER = "superscript";
	public static final String KEY_TOOL_SUB = "subscript";
	public static final String KEY_TOOL_ULIST = "ulist";
	public static final String KEY_TOOL_OLIST = "olist";
	public static final String KEY_TOOL_UNDO = "undo";
	public static final String KEY_TOOL_REDO = "redo";
	public static final String KEY_TOOL_CLEAR = "clearformats";
	public static final String KEY_TOOL_CHARACTER = "insertcharacter";
	public static final String KEY_TOOL_ANCHOR = "anchor";
	public static final String KEY_TOOL_SOURCE = "viewsource";
	public static final String KEY_TOOL_STYLES = "styleselect";
	public static final String KEY_TOOL_ALIGNLEFT = "left";
	public static final String KEY_TOOL_ALIGNCENTER = "center";
	public static final String KEY_TOOL_ALIGNRIGHT = "right";
	public static final String KEY_TOOL_ALIGNJUSTIFIED = "justify";
	public static final String KEY_TOOL_INDENTLEFT = "deindent";
	public static final String KEY_TOOL_INDENTRIGHT = "indent";
	public static final String KEY_TOOL_IMAGE = "image";
	public static final String KEY_TOOL_COLOR = "color";
	public static final String KEY_TOOL_TABLE = "table";
	public static final String KEY_TOOL_SAVECONTENT = "savecontent";
	public static final String KEY_TOOL_CONFIRMCONTENT = "confirmcontent";
	public static final String KEY_TOOL_DETACHFRAME = "detachframe";
	
	private KafenioPanel parent;
	private KafenioPanelConfiguration config;
	private JToolBar toolBar;
	private Hashtable toolbarItems;
	private JComboBoxNoFocus styleSelector;
	
	/**
	 * creates a new KafenioToolBar Object.
	 * @param newParent the toolbar's parent object.
	 */
	public KafenioToolBar(KafenioPanel newParent) {
		parent = newParent;
		config = parent.getConfig();
		toolBar = createNewToolbar();
		toolbarItems = createItems();
	}

	/**
	 * method that returns a JToolBar object containing the buttons specified in toolbarItemList.
	 * the button names are separated by spaces.
	 * @param toolbarItemList space-separated list of buttons names.
	 * @param show if show is false, the method returns null.
	 * @return returns a JToolBar object.
	 */
	public JToolBar createToolbar(Vector toolbarItemList, boolean show) {
		if (show) {
			for(int i=0; i < toolbarItemList.size(); i++) {
				String toolToAdd = toolbarItemList.get(i).toString();
				if(toolbarItems.containsKey(toolToAdd)) {
					if(toolbarItems.get(toolToAdd) instanceof JButtonNoFocus) {
						toolBar.add((JButtonNoFocus)(toolbarItems.get(toolToAdd)));
					} else if(toolbarItems.get(toolToAdd) instanceof JToggleButtonNoFocus) {
						toolBar.add((JToggleButtonNoFocus)(toolbarItems.get(toolToAdd)));
					} else if(toolbarItems.get(toolToAdd) instanceof JComboBoxNoFocus) {
						toolBar.add((JComboBoxNoFocus)(toolbarItems.get(toolToAdd)));
					} else if(toolbarItems.get(toolToAdd) instanceof Separator) {
						toolBar.add(new Separator());
					} else {
						toolBar.add((JComponent)(toolbarItems.get(toolToAdd)));
					}
				}
			}
			return toolBar;
		}
		return null;
	}

	/**
	 * @return returns a new JToolBar Object
	 */
	private JToolBar createNewToolbar() {
		JToolBar newToolBar = new JToolBar(JToolBar.HORIZONTAL);
		newToolBar.setBackground(config.getBgcolor());
		newToolBar.setFloatable(false);
		return newToolBar;
	}

	/**
	 * @return creates all possible items and returns them in a hashtable.
	 */
	private Hashtable createItems() {
		Hashtable items = new Hashtable();
		items.put(KEY_TOOL_NEW, createButton("New", "NewDocument", "newdoc", getParent(), null));
		items.put(KEY_TOOL_OPEN, createButton("Open", "OpenDocument", "openhtml", getParent(), null));
		items.put(KEY_TOOL_SAVE, createButton("Save", "SaveDocument", "save", getParent(), null));
		items.put(KEY_TOOL_SEP, createSeparator());
		items.put(KEY_TOOL_CUT, createButton("Cut", "Cut", null, null, new DefaultEditorKit.CutAction()));
		items.put(KEY_TOOL_COPY, createButton("Copy", "Copy", null, null, new DefaultEditorKit.CopyAction()));
		items.put(KEY_TOOL_PASTE, createButton("Paste", "Paste", null, null, new DefaultEditorKit.PasteAction()));
		items.put(KEY_TOOL_BOLD, createButton("Bold", "FontBold", null, null, getActionList().getActionFontBold()));
		items.put(KEY_TOOL_ITALIC, createButton("Italic", 
												"FontItalic", 
												null, 
												null,
												getActionList().getActionFontItalic()));
		items.put(KEY_TOOL_UNDERLINE, createButton(	"Underline", 
													"FontUnderline", 
													null, 
													null, 
													getActionList().getActionFontUnderline()));
		items.put(KEY_TOOL_STRIKE, createButton("Strike", 
												"FontStrike", 
												null, 
												null, 
												getActionList().getActionFontStrike()));
		items.put(KEY_TOOL_SUPER, createButton(	"Super", 
												"FontSuperscript", 
												null, 
												null, 
												getActionList().getActionFontSuperscript()));
		items.put(KEY_TOOL_SUB, createButton(	"Sub", 
												"FontSubscript", 
												null, 
												null, 
												getActionList().getActionFontSubscript()));
		items.put(KEY_TOOL_ULIST, createButton(	"UList", 
												"ListUnordered", 
												null, 
												null, 
												getActionList().getActionListUnordered()));
		items.put(KEY_TOOL_OLIST, createButton(	"OList", 
												"ListOrdered", 
												null, 
												null, 
												getActionList().getActionListOrdered()));
		items.put(KEY_TOOL_CLEAR, createButton(	"ClearFormat", 
												"FormatClear", 
												null, 
												null, 
												getActionList().getActionClearFormat()));
		items.put(KEY_TOOL_CHARACTER, createButton(	"Character", 
													"InsertCharacterDialogTitle", 
													null, 
													null, 
													getActionList().getActionInsertCharacter()));
		items.put(KEY_TOOL_ALIGNLEFT, createButton(	"AlignLeft", 
													"AlignLeft", 
													null, 
													null, 
													getActionList().getActionAlignLeft()));
		items.put(KEY_TOOL_ALIGNCENTER, createButton(	"AlignCenter", 
														"AlignCenter", 
														null, 
														null, 
														getActionList().getActionAlignCenter()));
		items.put(KEY_TOOL_ALIGNRIGHT, createButton("AlignRight", 
													"AlignRight", 
													null, 
													null, 
													getActionList().getActionAlignRight()));
		items.put(KEY_TOOL_ALIGNJUSTIFIED, createButton("AlignJustified", 
													"AlignJustified", 
													null, 
													null, 
													getActionList().getActionAlignJustified()));
		if (config.isApplet()) {
			log.debug("insert server image");
			items.put(KEY_TOOL_IMAGE, createButton(	"InsertImage", 
													"InsertServerImage", 
													null, 
													null, 
													getActionList().getActionInsertServerImage()));
		} else {
			log.debug("insert local image");
			items.put(KEY_TOOL_IMAGE, createButton(	"InsertImage", 
													"InsertLocalImage", 
													"insertlocalimage", 
													getParent(), 
													null));
		}

		items.put(KEY_TOOL_UNDO, createButton(	"Undo", 
												"Undo", 
												null, 
												getParent(), 
												getParent().getUndoAction()));
		items.put(KEY_TOOL_REDO, createButton(	"Redo", 
												"Redo", 
												null, 
												getParent(), 
												getParent().getRedoAction()));
		items.put(KEY_TOOL_INDENTLEFT, createButton("IndentLeft", 
													"IndentLeft", 
													null, 
													null, 
													getActionList().getActionIndentLeft()));
		items.put(KEY_TOOL_INDENTRIGHT, createButton(	"IndentRight", 
														"IndentRight", 
														null, 
														null, 
														getActionList().getActionIndentRight()));
		items.put(KEY_TOOL_ANCHOR, createButton("Anchor", 
												"ToolAnchor", 
												null, 
												null, 
												getActionList().getActionInsertHyperlink()));
		items.put(KEY_TOOL_FIND, createButton(	"Find", 
												"SearchFind", 
												"find", 
												getParent(), 
												null));
		items.put(KEY_TOOL_COLOR, createButton(	"Color", 
												"CustomColor", 
												null, 
												null, 
												getActionList().getActionFontCustomColor()));
		items.put(KEY_TOOL_TABLE, createButton(	"Table", 
												"InsertTable", 
												"inserttable", 
												getParent(), 
												null));
		items.put(KEY_TOOL_SOURCE, createButton("Source", 
												"ViewSource", 
												"viewsource", 
												getParent(), 
												null));
		items.put(KEY_TOOL_SAVECONTENT, createButton(	"SaveContent", 
														"SaveDocument", 
														"savecontent", 
														getParent(), 
														null));
		items.put(KEY_TOOL_CONFIRMCONTENT, createButton("SaveContent", //Although ConfirmContent, use the icon of SaveContent
														"ConfirmContent", 
														"confirmcontent", 
														getParent(), 
														null));
		items.put(KEY_TOOL_DETACHFRAME, createButton(	"DetachFrame", 
														"DetachFrame", 
														"detachframe", 
														getParent(), 
														null));
		
		styleSelector = new JComboBoxNoFocus();
		styleSelector.setBackground(config.getBgcolor());
		styleSelector.setAction(new StylesAction(styleSelector));
		items.put(KEY_TOOL_STYLES, styleSelector);
		return items;
	}

	/**
	 * @return returns the currently set parent object.
	 */
	private KafenioPanel getParent() {
		return parent;
	}
	
	/**
	 * @return returns a style selector combobox
	 */
	public JComboBoxNoFocus getStyleSelector() {
		return styleSelector;
	}

	/**
	 * @return returns a new JToolBar.Separator Object.
	 */
	private Separator createSeparator() {
		return new JToolBar.Separator();		
	}
	
	/**
	 * creates a new JButtonNoFocus Object using the given values.
	 * @param icon the icon string without path and the trailing "HK.gif" at the end.
	 * @param labelText the id of the tooltip text to translate
	 * @param actionCommand an action command (can be null if action is specified)
	 * @param listener an action listener (can be null if action is specified)
	 * @param action an action (can be null if actionCommand and listener are specified.)
	 * @return returns a new JButtonNoFocus Object.
	 */
	private JButtonNoFocus createButton(String icon,
										String labelText,
										String actionCommand,
										ActionListener listener,
										Action action)
	{
		JButtonNoFocus newNoFocusButton;
		if (action != null) {
			newNoFocusButton = new JButtonNoFocus(action);
			newNoFocusButton.setIcon((getParent().getKafenioIcon(icon)));
		} else { 
			newNoFocusButton = new JButtonNoFocus((getParent().getKafenioIcon(icon)));
			newNoFocusButton.setActionCommand(actionCommand);
			newNoFocusButton.addActionListener(listener);
		}
		newNoFocusButton.setBackground(config.getBgcolor());
		newNoFocusButton.setToolTipText(getParent().getTranslation(labelText));
		newNoFocusButton.setText("");
		return newNoFocusButton;
	}

	/**
	 * convenience method to replace getActionList().
	 * @return returns the same as getActionList()
	 */
	private KafenioPanelActions getActionList() {
		return getParent().getKafenioActions();
	}
	
}
