package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;
import com.dexels.navajo.tipi.components.swingimpl.parsers.TipiGradientPaint;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiReference;
import com.dexels.navajo.tipi.swing.colorpicker.ColorPicker;

public class TipiChooseColor extends TipiAction {
    String DIALOG_TITLE_NL = "Selecteer kleur";
    String DIALOG_TITLE_EN = "Select color";
    private static final long serialVersionUID = -8109317107799742273L;
    private static final Color DEFAULT_COLOR = Color.blue;

    @Override
    protected void execute(final TipiEvent event) throws TipiBreakException, TipiException, TipiSuspendException {
        final Color initialColor;
        final Operand evaluated = getEvaluatedParameter("setTo", event); 
        final Operand initialValue =  getEvaluatedParameter("initialValue", event);
        if (initialValue == null) {
            initialColor = DEFAULT_COLOR;
        } else {
            initialColor = getColorFromHex((String) initialValue.value);
        }
        event.
        
        getComponent().runSyncInEventThread(new Runnable() {
            @Override
            public void run() {
                // JColorChooser c = new JColorChooser();
                Component container = (Component) getComponent().getContainer();
                Window top = (Window) SwingUtilities.getWindowAncestor(container);

                // bgp panel should contain paint
                TipiPanel bpgpanel = (TipiPanel) getComponent().findTipiComponentById("bgp");
                GradientPaint paint = null;
                if (bpgpanel != null) {
                    TipiGradientPaint bpgpaint = (TipiGradientPaint) bpgpanel.getValue("paint");
                    bpgpaint.setBounds(new Rectangle(0, 0, 450, 350));
                    paint = bpgpaint.getPaint();
                }
               
              
                Color c = ColorPicker.showDialog(top, initialColor, paint);

                if (c == null) {
                    return;
                }
                String color = toHexString(c);

                // Set the value to the 'setTo' parameter
                if (evaluated.value instanceof Property) {
                    Property p = (Property) evaluated.value;
                    p.setAnyValue(color, false);
                } else if (evaluated.value instanceof TipiReference) {
                    TipiReference p = (TipiReference) evaluated.value;
                    p.setValue(color);
                }
            }
        });

    }

    public final static String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
            hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour.toUpperCase();
    }
    

    /**
     * 
     * @param colorStr e.g. "#FFFFFF"
     */
    private final static Color getColorFromHex(String colorStr) {
        if (colorStr == null || colorStr.equals("")) {
            return DEFAULT_COLOR;
        }
        
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

  

}
