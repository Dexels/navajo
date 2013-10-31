package com.dexels.navajo.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class QuestionMergeMap
    implements Mappable {

  public String dataPath = null;
  public String questionPath = null;
  public String definitionPropertyName = null;
  public String formListPath = null;

  public String pathPrefix;
  public String fileNamePropertyPath;
  public String fileName;
  public boolean overwrite = false;
  public boolean backup = false;
  
private final static Logger logger = LoggerFactory
		.getLogger(QuestionMergeMap.class);

  private Navajo inNavajo = null;

  public void setPathPrefix(String p) {
    pathPrefix = p;
  }

  public void setFileName(String fn) {
    fileName = fn;
  }
  public void setFileNamePropertyPath(String p) {
    fileNamePropertyPath = p;
  }

  public String getPathPrefixPropertyPath() {
    return pathPrefix;
  }

  public void setOverwrite(boolean b) {
    overwrite = b;
  }

  public void setBackup(boolean b) {
    backup = b;
  }





  public void setDataPath(String dp) {
    dataPath = dp;
  }

  public void setQuestionPath(String qp) {
    questionPath = qp;
  }
  public void setFormListPath(String qp) {
    formListPath = qp;
  }


  public void setDefinitionPropertyName(String d) {
    definitionPropertyName = d;
  }


  @Override
public void kill() {
  }

  @Override
public void load(Access access) throws MappableException, UserException {
    inNavajo = access.getInDoc();
  }

  /** @todo Fridged it for a while need to test all this.
   *  */

  @Override
public void store() throws MappableException, UserException {
    Message listMessage = inNavajo.getMessage(formListPath);
    Message dataMessage = inNavajo.getMessage(dataPath);
    if (listMessage == null) {
      throw new UserException( -1, "Error: No questionmessage found");
    }
    if (dataMessage == null) {
      throw new UserException( -1, "Error: No datamessage found");
    }
    NavajoSaveAdapter nas = new NavajoSaveAdapter();
    nas.setBackup(backup);
    nas.setFileName(fileName);
    nas.setFileNamePropertyPath(fileNamePropertyPath);
    nas.setOverwrite(overwrite);
    nas.setPathPrefix(pathPrefix);

    for (int i = 0; i < listMessage.getArraySize(); i++) {
      Message current = listMessage.getMessage(i);
      Property p = current.getProperty(definitionPropertyName);
      if (p!=null) {
        Binary b = (Binary)p.getTypedValue();
        InputStream is = b.getDataAsStream();
        Navajo n = NavajoFactory.getInstance().createNavajo(is);
        try {
			is.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
        mergeData(n, dataMessage);

      }
    }
   }

  private void mergeData(Navajo questionNavajo, Message dataMessage) {
    Message questionMessage = questionNavajo.getMessage(questionPath);
    for (int i = 0; i < dataMessage.getArraySize(); i++) {
      Message m = dataMessage.getMessage(i);
      Property id = m.getProperty("Id");
//      Property value = m.getProperty("Value");
      logger.info("Looking for question: "+id.getValue());

      StringTokenizer idTok = new StringTokenizer( (String) id.getTypedValue());
      Message question = getQuestionById(idTok, questionMessage);
      if (question==null) {
        logger.info("returned no message!");
      } else {
        logger.info("returned: "+question.getFullMessageName());
      }
    }

  }

  private Message getQuestionById(StringTokenizer idTok,
                                  Message questionMessage) {
    String groupName = idTok.nextToken();
    if (!idTok.hasMoreTokens()) {
      return questionMessage;
    }
    for (int i = 0; i < questionMessage.getArraySize(); i++) {
      Message m = questionMessage.getMessage(i);
      Property p = m.getProperty("Id");
      if (groupName.equals( p.getTypedValue())) {
        if (questionMessage.getName().equals("QuestionList")) {
          getQuestionById(idTok, m.getMessage("Group"));
        }
        else {
          getQuestionById(idTok, m.getMessage("QuestionList"));
        }
      }
    }
    return null;
  }

}
