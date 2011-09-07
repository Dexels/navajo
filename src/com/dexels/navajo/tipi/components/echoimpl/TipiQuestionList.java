package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionList;

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
 * @author not attributable
 * @version 1.0
 * @deprecated
 */
@Deprecated
public class TipiQuestionList extends TipiBaseQuestionList {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5101521294168485042L;

	// private String messagePath = null;
    // private String questionDefinitionName = null;
    // private String questionGroupDefinitionName = null;
    //  

    public TipiQuestionList() {
    }

    protected Object getGroupConstraints(Message groupMessage) {
        return null;
    }

    protected void clearQuestions() {
    }

 
    public Object createContainer() {
        return null;
    }

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            // Color background = (Color) object;
            // myColumn.setBackground(background);
            // return;
        }

        super.setComponentValue(name, object);
    }
}
