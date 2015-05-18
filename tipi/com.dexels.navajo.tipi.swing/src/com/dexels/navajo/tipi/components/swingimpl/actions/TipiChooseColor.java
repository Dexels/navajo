package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.Color;
import java.awt.Component;
import java.util.Locale;

import javax.swing.JColorChooser;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiReference;

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
        
        getComponent().runSyncInEventThread(new Runnable() {
            @Override
            public void run() {
                // JColorChooser c = new JColorChooser();
                Color c = JColorChooser.showDialog((Component) getComponent().getContainer(), getDialogTitle(), initialColor);
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

    private String getDialogTitle() {
        Locale locale = null;
        if (locale == null) {
            locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
        }

        if (locale.getDisplayLanguage().toUpperCase() == "EN") {
            return DIALOG_TITLE_EN;
        } else {
            return DIALOG_TITLE_NL;
        }
    }
}
