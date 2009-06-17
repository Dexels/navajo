package de.xeinfach.kafenio;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.swing.text.html.HTML;

import de.xeinfach.kafenio.KafenioPanel.RedoAction;
import de.xeinfach.kafenio.KafenioPanel.UndoAction;
import de.xeinfach.kafenio.action.CustomAction;
import de.xeinfach.kafenio.action.FormatAction;
import de.xeinfach.kafenio.action.IndentAction;
import de.xeinfach.kafenio.action.ListAutomationAction;
import de.xeinfach.kafenio.action.ServerImageAction;
import de.xeinfach.kafenio.action.SpecialCharAction;
import de.xeinfach.kafenio.action.ToggleCaseAction;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: This class contains all shared actions of the editor.
 * (currently redoaction, undoaction and the JTextPane actions are still
 * initialized in KafenioPanel class, this will be changed later on.)
 * 
 * @author Karsten Pawlik
 */
public class KafenioPanelActions {
	
	private static LeanLogger log = new LeanLogger("KafenioPanelActions.class");

	private static final String FONTSIZE_KEY = "size";
	private KafenioPanel parent;
	
	private CustomAction actionSelectFont;
	private CustomAction actionClearFormat;
	private ServerImageAction actionInsertServerImage;
	private CustomAction actionInsertHyperlink;
	private CustomAction actionAlignLeft;
	private CustomAction actionAlignCenter;
	private CustomAction actionAlignRight;
	private CustomAction actionAlignJustified;
	private CustomAction actionFontSize1;
	private CustomAction actionFontSize2;
	private CustomAction actionFontSize3;
	private CustomAction actionFontSize4;
	private CustomAction actionFontSize5;
	private CustomAction actionFontSize6;
	private CustomAction actionFontSize7;
	private CustomAction actionFontCustomColor;
	private IndentAction actionIndentLeft;
	private IndentAction actionIndentRight;
	private FormatAction actionFontStrike;
	private FormatAction actionFontSuperscript;
	private FormatAction actionFontSubscript;
	private FormatAction actionAddParagraph;
	private FormatAction actionHeading1;
	private FormatAction actionHeading2;
	private FormatAction actionHeading3;
	private FormatAction actionHeading4;
	private FormatAction actionHeading5;
	private FormatAction actionHeading6;
	private FormatAction actionAddListItem;
	private FormatAction actionSpan;
	private FormatAction actionTT;
	private FormatAction actionEmphasis;
	private FormatAction actionStrong;
	private FormatAction actionPre;
	private FormatAction actionBlockquote;
	private FormatAction actionFormatBig;
	private FormatAction actionFormatSmall;
	private ForegroundAction actionFontColorYellow;
	private ForegroundAction actionFontColorWhite;
	private ForegroundAction actionFontColorTeal;
	private ForegroundAction actionFontColorSilver;
	private ForegroundAction actionFontColorRed;
	private ForegroundAction actionFontColorPurple;
	private ForegroundAction actionFontColorOlive;
	private ForegroundAction actionFontColorNavy;
	private ForegroundAction actionFontColorMaroon;
	private ForegroundAction actionFontColorLime;
	private ForegroundAction actionFontColorGreen;
	private ForegroundAction actionFontColorGray;
	private ForegroundAction actionFontColorFuschia;
	private ForegroundAction actionFontColorBlue;
	private ForegroundAction actionFontColorBlack;
	private ForegroundAction actionFontColorAqua;
	private ListAutomationAction actionListUnordered;
	private ListAutomationAction actionListOrdered;
	private SpecialCharAction actionInsertCharacter;
	private StyledEditorKit.BoldAction actionFontBold;
	private StyledEditorKit.ItalicAction actionFontItalic;
	private StyledEditorKit.UnderlineAction actionFontUnderline;
	private ToggleCaseAction actionToggleCase;

	/**
	 * creates all actions required by KafenioPanel Object.
	 * @param newParent the KafenioPanel that these actions are for.
	 */
	public KafenioPanelActions(KafenioPanel newParent) {
		/* Collect the actions that the JTextPane is naturally aware of */
		parent = newParent;
		
		log.debug("creating shared actions...");
		
		/* Create shared actions */
		actionFontBold = new StyledEditorKit.BoldAction();
		actionFontItalic = new StyledEditorKit.ItalicAction();
		actionFontUnderline = new StyledEditorKit.UnderlineAction();
		actionFontStrike = 
			new FormatAction(parent, getString("FontStrike"), HTML.Tag.STRIKE);
		actionFontSuperscript = 
			new FormatAction(parent, getString("FontSuperscript"), HTML.Tag.SUP);
		actionFontSubscript = 
			new FormatAction(parent, getString("FontSubscript"), HTML.Tag.SUB);
		actionListUnordered = 
			new ListAutomationAction(parent, getString("ListUnordered"), HTML.Tag.UL);
		actionListOrdered = 
			new ListAutomationAction(parent, getString("ListOrdered"), HTML.Tag.OL);
		
		Hashtable customAttr0 = new Hashtable();
		customAttr0.put("align","left");
		actionAlignLeft = 
			new CustomAction(parent, getString("AlignLeft"), HTML.Tag.P, customAttr0);
		
		Hashtable customAttr1 = new Hashtable();
		customAttr1.put("align","center");
		actionAlignCenter = 
			new CustomAction(parent, getString("AlignCenter"), HTML.Tag.P, customAttr1);
		
		Hashtable customAttr2 = new Hashtable();
		customAttr2.put("align","right");
		actionAlignRight = 
			new CustomAction(parent, getString("AlignRight"), HTML.Tag.P, customAttr2);
		
		Hashtable customAttr3 = new Hashtable();
		customAttr3.put("align","justify");
		actionAlignJustified = 
			new CustomAction(parent, getString("AlignJustified"), HTML.Tag.DIV, customAttr3);
		
		actionIndentLeft = new IndentAction(parent, KafenioToolBar.KEY_TOOL_INDENTLEFT);
		actionIndentRight = new IndentAction(parent, KafenioToolBar.KEY_TOOL_INDENTRIGHT);
		
		Hashtable customAttr = new Hashtable();
		customAttr.put("face","");
		actionSelectFont = 
			new CustomAction(	parent, 
								getString("FontSelect") + KafenioMenuBar.DOTS, 
								HTML.Tag.FONT, 
								customAttr);
		
		actionClearFormat = 
			new CustomAction(parent, getString("FormatClear"), new HTML.UnknownTag(""));
		actionInsertHyperlink = new CustomAction(	parent, 
													getString("InsertAnchor") 
													+ KafenioMenuBar.DOTS, 
													HTML.Tag.A);
		actionInsertServerImage = new ServerImageAction(parent);
		actionInsertCharacter = 
			new SpecialCharAction(parent, getString("InsertCharacter") + KafenioMenuBar.DOTS);
		actionToggleCase =
			new ToggleCaseAction(parent, getString("ToggleCase") + KafenioMenuBar.DOTS);
		actionAddParagraph = 
			new FormatAction(parent, getString("AddParagraph"), HTML.Tag.P, "left");

		actionHeading1 = new FormatAction(parent, getString("Heading1"), HTML.Tag.H1);
		actionHeading2 = new FormatAction(parent, getString("Heading2"), HTML.Tag.H2);
		actionHeading3 = new FormatAction(parent, getString("Heading3"), HTML.Tag.H3);
		actionHeading4 = new FormatAction(parent, getString("Heading4"), HTML.Tag.H4);
		actionHeading5 = new FormatAction(parent, getString("Heading5"), HTML.Tag.H5);
		actionHeading6 = new FormatAction(parent, getString("Heading6"), HTML.Tag.H6);
		
		actionAddListItem = new FormatAction(parent, getString("ListItem"), HTML.Tag.LI);
		
		actionBlockquote = 
			new FormatAction(parent, getString("FormatBlockquote"), HTML.Tag.BLOCKQUOTE);
		actionPre = new FormatAction(parent, getString("FormatPre"), HTML.Tag.PRE);
		actionStrong = new FormatAction(parent, getString("FormatStrong"), HTML.Tag.STRONG);
		actionEmphasis = new FormatAction(parent, getString("FormatEmphasis"), HTML.Tag.EM);
		actionTT = new FormatAction(parent, getString("FormatTT"), HTML.Tag.TT);
		actionSpan = new FormatAction(parent, getString("FormatSpan"), HTML.Tag.SPAN);
		
		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"1");
		actionFontSize1 = 
			new CustomAction(parent, getString("FontSize1"), HTML.Tag.FONT, customAttr);

		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"2");
		actionFontSize2 = 
			new CustomAction(parent, getString("FontSize2"), HTML.Tag.FONT, customAttr);

		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"3");
		actionFontSize3 = 
			new CustomAction(parent, getString("FontSize3"), HTML.Tag.FONT, customAttr);

		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"4");
		actionFontSize4 = 
			new CustomAction(parent, getString("FontSize4"), HTML.Tag.FONT, customAttr);

		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"5");
		actionFontSize5 = 
			new CustomAction(parent, getString("FontSize5"), HTML.Tag.FONT, customAttr);

		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"6");
		actionFontSize6 = 
			new CustomAction(parent, getString("FontSize6"), HTML.Tag.FONT, customAttr);

		customAttr = new Hashtable(); 
		customAttr.put(FONTSIZE_KEY,"7");
		actionFontSize7 = 
			new CustomAction(parent, getString("FontSize7"), HTML.Tag.FONT, customAttr);
		
		actionFormatBig = new FormatAction(parent, getString("FormatBig"), HTML.Tag.BIG);
		actionFormatSmall = new FormatAction(parent, getString("FormatSmall"), HTML.Tag.SMALL);
		
		customAttr = new Hashtable(); 
		customAttr.put("color","black");
		actionFontCustomColor = new CustomAction(parent, 
			getString("CustomColor") + KafenioMenuBar.DOTS, HTML.Tag.FONT, customAttr);
		
		actionFontColorAqua = new StyledEditorKit.ForegroundAction(
			getString("ColorAqua"), new Color(0,255,255));
		
		actionFontColorBlack = new StyledEditorKit.ForegroundAction(
			getString("ColorBlack"), new Color(0,0,0));
		
		actionFontColorBlue = new StyledEditorKit.ForegroundAction(
			getString("ColorBlue"), new Color(0,0,255));
		
		actionFontColorFuschia = new StyledEditorKit.ForegroundAction(
			getString("ColorFuschia"), new Color(255,0,255));
		
		actionFontColorGray = new StyledEditorKit.ForegroundAction(
			getString("ColorGray"), new Color(128,128,128));
		
		actionFontColorGreen = new StyledEditorKit.ForegroundAction(
			getString("ColorGreen"), new Color(0,128,0));
		
		actionFontColorLime = new StyledEditorKit.ForegroundAction(
			getString("ColorLime"), new Color(0,255,0));
		
		actionFontColorMaroon = new StyledEditorKit.ForegroundAction(
			getString("ColorMaroon"), new Color(128,0,0));
		
		actionFontColorNavy = new StyledEditorKit.ForegroundAction(
			getString("ColorNavy"), new Color(0,0,128));
		
		actionFontColorOlive = new StyledEditorKit.ForegroundAction(
			getString("ColorOlive"), new Color(128,128,0));
		
		actionFontColorPurple = new StyledEditorKit.ForegroundAction(
			getString("ColorPurple"), new Color(128,0,128));
		
		actionFontColorRed = new StyledEditorKit.ForegroundAction(
			getString("ColorRed"), new Color(255,0,0));
		
		actionFontColorSilver = new StyledEditorKit.ForegroundAction(
			getString("ColorSilver"), new Color(192,192,192));
		
		actionFontColorTeal = new StyledEditorKit.ForegroundAction(
			getString("ColorTeal"), new Color(0,128,128));
		
		actionFontColorWhite = new StyledEditorKit.ForegroundAction(
			getString("ColorWhite"), new Color(255,255,255));
		
		actionFontColorYellow = new StyledEditorKit.ForegroundAction(
			getString("ColorYellow"), new Color(255,255,0));

		log.debug("...done.\nLoading config files and libraries...");
	}

	/**
	 * returns a translated representation of the given string.
	 * @param stringToTranslate the string to translate.
	 * @return returns a translated representation of the given string.
	 */
	private String getString(String stringToTranslate) {
		return parent.getTranslation(stringToTranslate);
	}

	/**
	 * @return returns action to align paragraph centric
	 */
	public CustomAction getActionAlignCenter() {
		return actionAlignCenter;
	}
	
	/**
	 * @return returns action to align paragraph justified
	 */
	public CustomAction getActionAlignJustified() {
		return actionAlignJustified;
	}
	
	/**
	 * @return returns action to align paragraph left
	 */
	public CustomAction getActionAlignLeft() {
		return actionAlignLeft;
	}
	
	/**
	 * @return returns action to align paragraph right
	 */
	public CustomAction getActionAlignRight() {
		return actionAlignRight;
	}
	
	/**
	 * @return returns action to clear all formattings of selected text
	 */
	public CustomAction getActionClearFormat() {
		return actionClearFormat;
	}
	
	/**
	 * @return returns action to format selected text bold
	 */
	public StyledEditorKit.BoldAction getActionFontBold() {
		return actionFontBold;
	}
	
	/**
	 * @return returns action to format selected text italic
	 */
	public StyledEditorKit.ItalicAction getActionFontItalic() {
		return actionFontItalic;
	}
	
	/**
	 * @return returns action to format selected text strikedthrough
	 */
	public FormatAction getActionFontStrike() {
		return actionFontStrike;
	}
	
	/**
	 * @return returns action to format selected text subscripted
	 */
	public FormatAction getActionFontSubscript() {
		return actionFontSubscript;
	}
	
	/**
	 * @return returns action to format selected text superscripted
	 */
	public FormatAction getActionFontSuperscript() {
		return actionFontSuperscript;
	}
	
	/**
	 * @return returns action to format selected text underlined
	 */
	public StyledEditorKit.UnderlineAction getActionFontUnderline() {
		return actionFontUnderline;
	}
	
	/**
	 * @return returns action to de-indent the current paragraph
	 */
	public IndentAction getActionIndentLeft() {
		return actionIndentLeft;
	}
	
	/**
	 * @return returns action to indent the current paragraph
	 */
	public IndentAction getActionIndentRight() {
		return actionIndentRight;
	}
	
	/**
	 * @return returns action that adds an anchor to the selected text.
	 */
	public CustomAction getActionInsertHyperlink() {
		return actionInsertHyperlink;
	}
	
	/**
	 * @return returns action that adds an image from a server
	 */
	public ServerImageAction getActionInsertServerImage() {
		return actionInsertServerImage;
	}

	/**
	 * @return returns action to insert a character
	 */
	public SpecialCharAction getActionInsertCharacter() {
		return actionInsertCharacter;
	}
	
	/**
	 * @return returns action to insert/modify ordered lists
	 */
	public ListAutomationAction getActionListOrdered() {
		return actionListOrdered;
	}
	
	/**
	 * @return returns action to insert/modify unordered lists.
	 */
	public ListAutomationAction getActionListUnordered() {
		return actionListUnordered;
	}
	
	/**
	 * @return returns action to select a font for the selected text
	 */
	public CustomAction getActionSelectFont() {
		return actionSelectFont;
	}
	
	/**
	 * @return returns action to toggle case of the selected text.
	 */
	public ToggleCaseAction getActionToggleCase() {
		return actionToggleCase;
	}
	
	/**
	 * @return returns redo action
	 */
	public RedoAction getRedoAction() {
		return parent.getRedoAction();
	}
	
	/**
	 * @return returns undo action
	 */
	public UndoAction getUndoAction() {
		return parent.getUndoAction();
	}

	/**
	 * @return returns action to add a paragraph.
	 */
	public FormatAction getActionAddParagraph() {
		return actionAddParagraph;
	}
	
	/**
	 * @return returns action to format selected text with H1
	 */
	public FormatAction getActionHeading1() {
		return actionHeading1;
	}
	
	/**
	 * @return returns action to format selected text with H2
	 */
	public FormatAction getActionHeading2() {
		return actionHeading2;
	}
	
	/**
	 * @return returns action to format selected text with H3
	 */
	public FormatAction getActionHeading3() {
		return actionHeading3;
	}
	
	/**
	 * @return returns action to format selected text with H4
	 */
	public FormatAction getActionHeading4() {
		return actionHeading4;
	}
	
	/**
	 * @return returns action to format selected text with H5
	 */
	public FormatAction getActionHeading5() {
		return actionHeading5;
	}
	
	/**
	 * @return returns action to format selected text with H6
	 */
	public FormatAction getActionHeading6() {
		return actionHeading6;
	}
	
	/**
	 * @return returns action to add a list item.
	 */
	public FormatAction getActionAddListItem() {
		return actionAddListItem;
	}
	
	/**
	 * @return returns action to format text blockquoted
	 */
	public FormatAction getActionBlockquote() {
		return actionBlockquote;
	}
	
	/**
	 * @return  returns action to format text empasized
	 */
	public FormatAction getActionEmphasis() {
		return actionEmphasis;
	}
	
	/**
	 * @return returns action to format text using pre
	 */
	public FormatAction getActionPre() {
		return actionPre;
	}
	
	/**
	 * @return returns action to format text using span
	 */
	public FormatAction getActionSpan() {
		return actionSpan;
	}
	
	/**
	 * @return returns action to format text strong
	 */
	public FormatAction getActionStrong() {
		return actionStrong;
	}
	
	/**
	 * @return returns action to format text using tt
	 */
	public FormatAction getActionTT() {
		return actionTT;
	}
	
	/**
	 * @return returns action that formats text with fontsize 1
	 */
	public CustomAction getActionFontSize1() {
		return actionFontSize1;
	}
	
	/**
	 * @return returns action that formats text with fontsize 2
	 */
	public CustomAction getActionFontSize2() {
		return actionFontSize2;
	}
	
	/**
	 * @return returns action that formats text with fontsize 3
	 */
	public CustomAction getActionFontSize3() {
		return actionFontSize3;
	}
	
	/**
	 * @return returns action that formats text with fontsize 4
	 */
	public CustomAction getActionFontSize4() {
		return actionFontSize4;
	}
	
	/**
	 * @return returns action that formats text with fontsize 5
	 */
	public CustomAction getActionFontSize5() {
		return actionFontSize5;
	}
	
	/**
	 * @return returns action that formats text with fontsize 6
	 */
	public CustomAction getActionFontSize6() {
		return actionFontSize6;
	}
	
	/**
	 * @return returns action that formats text with fontsize 7
	 */
	public CustomAction getActionFontSize7() {
		return actionFontSize7;
	}
	
	/**
	 * @return returns action that formats text using big
	 */
	public FormatAction getActionFormatBig() {
		return actionFormatBig;
	}
	
	/**
	 * @return returns action that formats text using small
	 */
	public FormatAction getActionFormatSmall() {
		return actionFormatSmall;
	}
	
	/**
	 * @return returns action that formats text-foreground in color aqua
	 */
	public ForegroundAction getActionFontColorAqua() {
		return actionFontColorAqua;
	}
	
	/**
	 * @return returns action that formats text-foreground in color black
	 */
	public ForegroundAction getActionFontColorBlack() {
		return actionFontColorBlack;
	}
	
	/**
	 * @return returns action that formats text-foreground in color blue
	 */
	public ForegroundAction getActionFontColorBlue() {
		return actionFontColorBlue;
	}
	
	/**
	 * @return returns action that formats text-foreground in color fuschia
	 */
	public ForegroundAction getActionFontColorFuschia() {
		return actionFontColorFuschia;
	}
	
	/**
	 * @return returns action that formats text-foreground in color gray
	 */
	public ForegroundAction getActionFontColorGray() {
		return actionFontColorGray;
	}
	
	/**
	 * @return returns action that formats text-foreground in color green
	 */
	public ForegroundAction getActionFontColorGreen() {
		return actionFontColorGreen;
	}
	
	/**
	 * @return returns action that formats text-foreground in color lime
	 */
	public ForegroundAction getActionFontColorLime() {
		return actionFontColorLime;
	}
	
	/**
	 * @return returns action that formats text-foreground in color maroon
	 */
	public ForegroundAction getActionFontColorMaroon() {
		return actionFontColorMaroon;
	}
	
	/**
	 * @return returns action that formats text-foreground in color navy
	 */
	public ForegroundAction getActionFontColorNavy() {
		return actionFontColorNavy;
	}
	
	/**
	 * @return returns action that formats text-foreground in color olive
	 */
	public ForegroundAction getActionFontColorOlive() {
		return actionFontColorOlive;
	}
	
	/**
	 * @return returns action that formats text-foreground in color purple
	 */
	public ForegroundAction getActionFontColorPurple() {
		return actionFontColorPurple;
	}
	
	/**
	 * @return returns action that formats text-foreground in color red
	 */
	public ForegroundAction getActionFontColorRed() {
		return actionFontColorRed;
	}
	
	/**
	 * @return returns action that formats text-foreground in color silver
	 */
	public ForegroundAction getActionFontColorSilver() {
		return actionFontColorSilver;
	}
	
	/**
	 * @return returns action that formats text-foreground in color teal
	 */
	public ForegroundAction getActionFontColorTeal() {
		return actionFontColorTeal;
	}
	
	/**
	 * @return returns action that formats text-foreground in color white
	 */
	public ForegroundAction getActionFontColorWhite() {
		return actionFontColorWhite;
	}
	
	/**
	 * @return returns action that formats text-foreground in color yellow
	 */
	public ForegroundAction getActionFontColorYellow() {
		return actionFontColorYellow;
	}
	
	/**
	 * @return returns action that formats text-foreground in default color (black)
	 */
	public CustomAction getActionFontCustomColor() {
		return actionFontCustomColor;
	}
	
}
