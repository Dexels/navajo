package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.components.echoimpl.TipiScreen;

import echopointng.TemplatePanel;
import echopointng.template.StringTemplateDataSource;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiOptionPane {

    public static void showQuestion(TipiContext context, String text, String title, String yes, String no) throws TipiBreakException {
        TipiScreen s = (TipiScreen) context.getDefaultTopLevel();
        final Window w = (Window) s.getTopLevel();
        w.setStyleName("Default");
		        
        final WindowPane wp = new WindowPane("?", new Extent(400, Extent.PX), new Extent(300, Extent.PX));
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(wp.getClass(), "Default");
        wp.setStyle(ss);

        w.getContent().add(wp);
        wp.setTitle(title);
        wp.setModal(true);

        SplitPane sp = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);

//        Column cp = new Column();

        wp.add(sp);
        TemplatePanel tp = new TemplatePanel();
        StringTemplateDataSource std = new StringTemplateDataSource((String)text);
        tp.setTemplateDataSource(std);
        
        

        sp.setSeparatorPosition(new Extent(40,Extent.PX));
        Row toolbar = new ToolbarImpl();
        sp.add(toolbar);

        sp.add(tp);

        
        Button yesButton = new ButtonImpl("Yes");
        toolbar.add(yesButton);
        Button noButton = new ButtonImpl("No");
        toolbar.add(noButton);

        wp.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);

        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                wp.dispose();
                w.getContent().remove(wp);
            }
        });
        yesButton.setText(yes);
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                wp.dispose();
                w.getContent().remove(wp);
                // throw new TipiBreakException();
            }
        });
        noButton.setText(no);
        wp.setVisible(true);
        throw new TipiBreakException();
    }

    public static void showInfo(final TipiContext context, final String text, String title, String closeText) {
//        w.setStyleName("Default");
        TipiScreen s = (TipiScreen) context.getDefaultTopLevel();
        final Window w = (Window) s.getTopLevel();
        final WindowPane wp = new WindowPane(title, new Extent(400, Extent.PX), new Extent(150, Extent.PX));
        w.getContent().add(wp);
        wp.setModal(true);

        wp.setTitleBackground(new Color(232, 232, 232));
        wp.setTitleInsets(new Insets(3, 2, 0, 0));
        wp.setTitleForeground(new Color(0, 0, 0));
        wp.setTitleFont(new Font(Font.ARIAL, Font.BOLD, new Extent(11, Extent.PT)));
        wp.setTitleHeight(new Extent(20, Extent.PX));

        Column cp = new Column();

        wp.add(cp);
        cp.add(new Label((String) text));

        Row toolbar = new Row();
        cp.add(toolbar);
        //
        // Button closeButton = new Button("Ok");
        // toolbar.add(closeButton);

        wp.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);

        // closeButton.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent ae) {
        // System.err.println("ouwe: " + text);
        // w.getContent().remove(wp);
        // wp.dispose();
        // }
        // });
        // closeButton.setText(closeText);
        wp.setVisible(true);
    }

}
