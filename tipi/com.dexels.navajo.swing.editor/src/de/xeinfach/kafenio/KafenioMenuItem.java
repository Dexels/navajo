package de.xeinfach.kafenio;

import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: class is an extension of the JMenuItem class that adds special i18n-aware constructors.
 * @author Karsten Pawlik
 */
public class KafenioMenuItem extends JMenuItem {

	private static LeanLogger log = new LeanLogger("KafenioMenuItem.class");

	/**
	 * creates a new KafenioMenuItem Object based on the given values.
	 * @param parent the parent component
	 * @param label label of the menu item
	 * @param actionCommand action command to be used.
	 * @param listener action listener to be used
	 */
	public KafenioMenuItem(	KafenioMenuBar parent,
							String label,
							String actionCommand,
							ActionListener listener)
	{
		this(parent, label, actionCommand, listener, null, null);
	}

	/**
	 * creates a new KafenioMenuItem Object based on the given values.
	 * @param parent the parent component
	 * @param label label of the menu item
	 * @param actionCommand action command to be used.
	 * @param listener action listener to be used
	 * @param defaultIcon icon to be displayed with this menu item.
	 */
	public KafenioMenuItem(	KafenioMenuBar parent,
							String label,
							String actionCommand,
							ActionListener listener,
							Icon defaultIcon) 
	{
		this(parent, label, actionCommand, listener, null, defaultIcon);
	}

	/**
	 * creates a new KafenioMenuItem Object based on the given values.
	 * @param parent the parent component
	 * @param label label of the menu item
	 * @param actionCommand action command to be used.
	 * @param listener action listener to be used
	 * @param shortcut key shortcut for this object.
	 */
	public KafenioMenuItem(	KafenioMenuBar parent,
							String label,
							String actionCommand,
							ActionListener listener,
							KeyStroke shortcut)
	{
		this(parent, label, actionCommand, listener, shortcut, null);
	}
	
	/**
	 * creates a new KafenioMenuItem Object based on the given values.
	 * @param parent the parent component
	 * @param anAction action to be used.
	 * @param shortcut key shortcut for this object.
	 * @param menuIcon icon to be displayed with this menu item.
	 */
	public KafenioMenuItem(	KafenioMenuBar parent,
							Action anAction,
							KeyStroke shortcut,
							Icon menuIcon) 
	{
		this(parent, null, anAction, shortcut, menuIcon);
	}

	/**
	 * creates a new KafenioMenuItem Object based on the given values.
	 * @param parent the parent component
	 * @param labelText of the menu item
	 * @param anAction action to be used.
	 * @param shortcut key shortcut for this object.
	 * @param menuIcon icon to be displayed with this menu item.
	 */
	public KafenioMenuItem(	KafenioMenuBar parent,
							String labelText,
							Action anAction,
							KeyStroke shortcut,
							Icon menuIcon) 
	{
		super(anAction);
		setBackground(parent.getParent().getConfig().getBgcolor());
		if (labelText != null) this.setText(labelText);
		if (shortcut != null) this.setAccelerator(shortcut);
		if (menuIcon != null) this.setIcon(menuIcon);
		log.debug("new KafenioMenuItem created: " + this.getText());
	}

	/**
	 * creates a new KafenioMenuItem Object based on the given values.
	 * @param parent the parent component
	 * @param labelText of the menu item
	 * @param actionCommand action command to be used.
	 * @param listener action listener to be used
	 * @param shortcut key shortcut for this object.
	 * @param menuIcon icon to be displayed with this menu item.
	 */
	public KafenioMenuItem(	KafenioMenuBar parent,
							String labelText,
							String actionCommand,
							ActionListener listener,
							KeyStroke shortcut,
							Icon menuIcon) 
	{
		this(parent, null, shortcut, menuIcon);
		if (labelText != null) this.setText(labelText);
		if (actionCommand != null) this.setActionCommand(actionCommand);        
		if (listener != null) this.addActionListener(listener);
		log.debug("new KafenioMenuItem created: " + this.getText());
	}

}
