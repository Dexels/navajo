package com.dexels.navajo.adapter.poi;

import com.dexels.navajo.adapter.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * A set of routines used by the Excel adapter (SheetMap) to access Excel spreadsheet data.
 *
 */
import org.apache.poi.hssf.dev.*;
import org.apache.poi.hssf.eventmodel.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.*;

//import gnu.regexp.*;
import java.util.*;
import java.io.*;

public class POITools {

  public static int toRowValue(int r) {
    return (r - 1);
  }

  public static int toColumnValue(String s) {
    int v = 0;
    for (int i = 0; i < s.length(); i++) {
        int k = (s.charAt(i) - (int) 'A') + 1;
        v += Math.pow((double) 26, (double) (s.length() - i - 1)) * k;
    }
    return v - 1;
  }

  private static String subStr(String s, int end) {
    if (end < 0)
      return null;
    if (end > (s.length()-1))
      return null;
    else {

      return s.substring(0, end);
    }
  }

  private static String addOne(String begin, char end) {
    if (end == 'Z') {
      if (begin == null || begin.equals(""))
        return "AA";
      else
        return addOne(subStr(begin, begin.length()-1), begin.charAt(begin.length()-1)) + 'A';
    }
    else
      return begin + (char) (end + 1);
  }

  protected static String addOne(String s) {
    s = addOne(subStr(s, s.length() - 1), s.charAt(s.length()-1));
    return s;
  }

  /**
   * format id: ([A-z]+[1-9][0-9]*:[1-9][0-9]*), A1:10.
   * @param sheet
   * @param id
   * @return
   */
  public static ArrayList getColumn(HSSFSheet sheet, String spec) {
    try {
      StringTokenizer tokens = new StringTokenizer(spec, ":");
      String begin = tokens.nextToken();
      String end = tokens.nextToken();
      String alpha1 = "";// new RE("[A-z]*").getMatch(begin).toString().toUpperCase();
      String alpha2 = ""; //new RE("[A-z]*").getMatch(end).toString().toUpperCase();
      String numeric1 = ""; //new RE("[1-9][0-9]*").getMatch(begin).toString();
      String numeric2 = ""; //new RE("[1-9][0-9]*").getMatch(end).toString();
      int startRow = Integer.parseInt(numeric1);
      int endRow = Integer.parseInt(numeric2);
      int startColumn = toColumnValue(alpha1);
      int endColumn = toColumnValue(alpha2);
      ArrayList list = new ArrayList();
      String id = alpha1;
      for (int j = startColumn; j <= endColumn; j++) {
        ArrayList row = new ArrayList();
        for (int i = startRow; i <= endRow; i++) {
           HSSFCell cell = getCell(sheet, id+i);
           CellMap cellMap = new CellMap();
           cellMap.setCell(cell, id+i);
           row.add(cellMap);
        }
        list.add(row);
        id = addOne(id);
      }
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HSSFCell getCell(HSSFSheet sheet, String id) {
    try {
        String alpha = ""; //new RE("[A-z]*").getMatch(id).toString().toUpperCase();
        String numeric = ""; //new RE("[1-9][0-9]*").getMatch(id).toString();
        int row = toRowValue(Integer.parseInt(numeric));
        int column = toColumnValue(alpha);
        //System.out.println("row = " + row + ", column = " + column);
        if (sheet.getRow(row) == null)
          return null;

        HSSFCell c = sheet.getRow(row).getCell((short) column);
        // System.out.println("getCell(), cell = " + c);
        return c;
    } catch (Exception ree) {
      ree.printStackTrace();
    }
    return null;
  }

  public static void main(String args[]) throws Exception {
        POIFSFileSystem fs      =
              new POIFSFileSystem(new FileInputStream("/home/arjen/cijfers.xls"));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      ArrayList all = getColumn(sheet, "A1:E10");
      for (int i = 0; i < all.size(); i++) {
        ArrayList row = (ArrayList) all.get(i);
        for (int j = 0; j < row.size(); j++) {
          CellMap cellMap = (CellMap) row.get(j);
          System.out.println(cellMap.getId() + ":" + cellMap.getCellValue());
        }
      }
  }

}
