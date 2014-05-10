/*
 * Created on Oct 30, 2007
 */
package net.atlanticbb.tantlinger.ui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.bushe.swing.action.ActionUIFactory;



/**
 * A collection of static UI helper methods.
 * 
 * @author Bob Tantlinger
 *
 */
public class UIUtils
{
    
    /**
     * The 16 x 16 icon package
     */
    public static final String X16 = "resources/images/x16/";
    
    /**
     * The 24 x 24 icon package
     */
    public static final String X24 = "resources/images/x24/";
    
    /**
     * The 32 x 32 icon package
     */    
    public static final String X32 = "resources/images/x32/";
    
    /**
     * The 48 x 48 icon package
     */
    public static final String X48 = "resources/images/x32/";
    
    
    /**
     * Misc icons that are unsized
     */
    public static final String MISC = "resources/images/misc/";
    
    /**
     * Gets the icon in the specified package with the specified name.
     * 
     * A package might be UIUtils.X16, UIUtils.X32, etc
     * 
     * @param _package
     * @param iconName
     * @return The icon, or null if the icon doesn't exist
     */
    public static ImageIcon getIcon(String _package, String iconName)
    {
        if(!_package.endsWith("/"))
            _package += "/";
        return getIcon(_package + iconName);
    }
    
    /**
     * Gets the icon at the specified path
     * 
     * @param path The path of the icon
     * @return The icon, or null if the icon file doesn't exist
     */
    public static ImageIcon getIcon(String path)
    {
        return createImageIcon(path);
    }
    
    /**
     * Create an ImageIcon from the image at the specified path
     * @param path The path of the image
     * @return The image icon, or null if the image doesn't exist
     */
    public static ImageIcon createImageIcon(String path)
    {
        
        URL u = Thread.currentThread().getContextClassLoader().getResource(path);
        //URL u = ClassLoader.getSystemResource(path);//UIUtils.class.getResource(path);
        if(u == null)
            return null;
        return new ImageIcon(u);
    }
    
    /**
     * Shows an error message dialog
     *
     * @param msg     
     */
    public static void showError(String msg)
    {
        showError(null, msg);
    }
    
    public static void showError(Component c, Throwable ex)
    {
        Window w = SwingUtilities.getWindowAncestor(c);
        if(w instanceof Frame)
           showError((Frame)w, ex);
        else if(w instanceof Dialog)
           showError((Dialog)w, ex);
        else
            showError(c, ex.getLocalizedMessage());
    }
    
    /**
     * Shows an error message dialog
     * @param owner
     * @param msg
     */
    public static void showError(Component owner, String msg) 
    {        
        showError(owner, "Error", msg);
    }
    
    /**
     * Shows an error message dialog
     * @param owner
     * @param title
     * @param msg
     */
    public static void showError(Component owner, String title, String msg)
    {
        JOptionPane.showMessageDialog(
            owner, msg, title, JOptionPane.ERROR_MESSAGE);  
    }
    
    /**
     * Shows an exception dialog
     * @param owner
     * @param title
     * @param th
     */
    public static void showError(Frame owner, String title, Throwable th)
    {
        JDialog d = new ExceptionDialog(owner, th);
        if(title != null)
            d.setTitle(title);
        d.setLocationRelativeTo(owner);
        d.setVisible(true);
        th.printStackTrace();
    }
    
        
    /**
     * Shows an exception dialog
     * @param owner
     * @param th
     */
    public static void showError(Frame owner, Throwable th)
    {
        showError(owner, null, th);;
    }
    
    /**
     * Shows an exception dialog
     * @param owner
     * @param title
     * @param th
     */
    public static void showError(Dialog owner, String title, Throwable th)
    {
        JDialog d = new ExceptionDialog(owner, th);
        if(title != null)
            d.setTitle(title);
        d.setLocationRelativeTo(owner);
        d.setVisible(true);
        th.printStackTrace();
    }
    
    
    
    /**
     * Shows an exception dialog
     * @param owner
     * @param th
     */
    public static void showError(Dialog owner, Throwable th)
    {
        showError(owner, null, th);;
    }
    
    /**
     * Shows a warning dialog
     * @param owner
     * @param title
     * @param msg
     */
    public static void showWarning(Component owner, String title, String msg)
    {
        JOptionPane.showMessageDialog(
            owner, msg, title, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows a warning dialog
     * @param owner
     * @param msg
     */
    public static void showWarning(Component owner, String msg)
    {
        showWarning(owner, "Warning", msg);
    }
    
    /**
     * Shows a warning dialog
     * @param msg
     */
    public static void showWarning(String msg)
    {
        showWarning(null, msg);
    }
    
    /**
     * Shows an info dialog
     * 
     * @param owner
     * @param title
     * @param msg
     */
    public static void showInfo(Component owner, String title, String msg)
    {
        JOptionPane.showMessageDialog(
            owner, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows an info dialog
     * @param owner
     * @param msg
     */
    public static void showInfo(Component owner, String msg)
    {
        showInfo(owner, "Information", msg);
    }
       
    /**
     * Shows an info dialog
     * @param msg
     */
    public static void showInfo(String msg)
    {
        showInfo(null, msg);
    }
        
    public static AbstractButton addToolBarButton(JToolBar tb, Action a)
    {        
        return addToolBarButton(tb, ActionUIFactory.getInstance().createButton(a));
    }
    
    public static AbstractButton addToolBarButton(JToolBar tb, Action a, boolean focusable, boolean showIconOnly)
    {
    	return addToolBarButton(tb, ActionUIFactory.getInstance().createButton(a), false, true);
    }
    
    public static AbstractButton addToolBarButton(JToolBar tb, AbstractButton button)
    {
        return addToolBarButton(tb, button, false, true);
    }
    
    public static AbstractButton addToolBarButton(JToolBar tb, AbstractButton button, boolean focusable, boolean showIconOnly)
    {
    	if(button.getAction() != null)
        {
        	button.setToolTipText((String)button.getAction().getValue(Action.NAME));
        	//prefer large icons for toolbar buttons
        	if(button.getAction().getValue("LARGE_ICON") != null)
        	{
        		try{
        			button.setIcon((Icon)button.getAction().getValue("LARGE_ICON"));
        		}catch(ClassCastException cce){}
        	}
        }
    	
    	Icon ico = button.getIcon();    	
    	if(ico != null && showIconOnly)
    	{
    		button.setText(null);
    		button.setMnemonic(0);        
    		button.putClientProperty("hideActionText", Boolean.TRUE);
    		int square = Math.max(ico.getIconWidth(), ico.getIconHeight()) + 6;
    		Dimension size = new Dimension(square, square);
    		button.setPreferredSize(size);
    		//button.setMinimumSize(size);
    		//button.setMaximumSize(size);
    	}
        
        if(!focusable)
        {
        	button.setFocusable(false);
        	button.setFocusPainted(false);
        }
        
        button.setMargin(new Insets(1, 1, 1, 1));
        tb.add(button);     
        return button;
    }
    
    public static JMenuItem addMenuItem(JMenu menu, Action action)
    {            
        JMenuItem item = menu.add(action);
        configureMenuItem(item, action);        
        return item;
    }
    
    public static JMenuItem addMenuItem(JPopupMenu menu, Action action)
    {
        JMenuItem item = menu.add(action);
        configureMenuItem(item, action);        
        return item;
    }
    
    private static void configureMenuItem(JMenuItem item, Action action)
    {
        KeyStroke keystroke = (KeyStroke)action.getValue(Action.ACCELERATOR_KEY);
        if(keystroke != null)
           item.setAccelerator(keystroke);

        item.setIcon(null);
        item.setToolTipText(null);        
    }
}
