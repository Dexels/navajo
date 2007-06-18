package com.dexels.navajo.echoclient.components;

import nextapp.echo2.app.Style;
import nextapp.echo2.app.TextField;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoTextField extends TextField {
    public TipiEchoTextField() {
        super();
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "Default");
        setStyle(ss);

	}

    public TipiEchoTextField(String text) {
        super();
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "Default");
        setStyle(ss);
		setText(text);
    }

}
