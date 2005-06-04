package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import org.apache.poi.hssf.usermodel.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version $Id$
 */

public class CellMap implements Mappable {

  public int cellType;
  public String id;
  public Object cellValue;
  private HSSFCell cell;

  public void setCell(HSSFCell cell, String id) {
    this.cell = cell;
    this.id = id;
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }


  public Object getCellValue() throws UserException {
    cellValue = new String("");
    if (cell == null)
      return cellValue;
    switch (cell.getCellType()) {
       case HSSFCell.CELL_TYPE_BLANK: cellValue = new String("");break;
       case HSSFCell.CELL_TYPE_BOOLEAN: cellValue = new Boolean(cell.getBooleanCellValue());break;
       case HSSFCell.CELL_TYPE_NUMERIC: cellValue = new Double(cell.getNumericCellValue());break;
       case HSSFCell.CELL_TYPE_STRING: cellValue = new String(cell.getStringCellValue());break;
       case HSSFCell.CELL_TYPE_FORMULA: cellValue = new Double(cell.getNumericCellValue());break;
       default:
          throw new UserException(-1, "Unsuported XLS cell type: " + cell.getCellType());
    }
    return cellValue;
  }

  public String getId() {
    return this.id;
  }

  public String getCellType() {
    String value = "CELL_NULL";
    if (cell == null)
      return value;
    switch (cell.getCellType()) {
      case HSSFCell.CELL_TYPE_BLANK: value = "CELL_BLANK";break;
       case HSSFCell.CELL_TYPE_BOOLEAN: value = "CELL_BOOLEAN";break;
       case HSSFCell.CELL_TYPE_NUMERIC: value = "CELL_NUMERIC";break;
       case HSSFCell.CELL_TYPE_STRING: value = "CELL_STRING";break;
       case HSSFCell.CELL_TYPE_FORMULA: value = "CELL_FORMULA";break;
       default:
          value = "CELL_UNKNOWN:"+cell.getCellType();
    }
    return value;
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }
}
