package com.dexels.navajo.pdf.functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.jdbc.JDBCFactory;
import com.dexels.navajo.jdbc.JDBCMappable;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class MergePDFsFromDatasource extends FunctionInterface {
	
	@Override
	public String remarks() {
		return "MergePDFsFromDatasource merges all gotten PDF documents from datasource using their object ids.";
	}
	
	@Override
	public String usage() {
        return "MergePDFsFromDatasource(String/List of Ids | Transaction Context | Datasource | Username | Key For Id | Type | Binay Column Name)";
    }
	
	public MergePDFsFromDatasource() {
	  super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object evaluate() throws TMLExpressionException {
		
		long start = System.currentTimeMillis();
		
		if ( getOperands().size() != 7 ) {
			throw new TMLExpressionException("Invalid number of operands.");
		}
		
		// List items contains all the ids of the document
		ArrayList<String> items = null;
		if ( getOperand(0) != null) {
			if (getOperand(0) instanceof ArrayList ) {
				items = (ArrayList<String>) getOperand(0);
			} else if(getOperand(0) instanceof String ) {
				items = (ArrayList<String>) Arrays.stream(((String) getOperand(0)).split(";")).map(o -> o.toString()).collect(Collectors.toList());
			}
		}
		
		int transactionContext = -1;
		if (getOperand(1) != null && getOperand(1) instanceof Integer) {
			transactionContext = ((Integer) getOperand(1)).intValue();
		}
		
		String datasource = null;
		if ( getOperand(2) != null && getOperand(2) instanceof String) {
			datasource = (String) getOperand(2);
		}
		
		String username = null;
		if ( getOperand(3) != null && getOperand(3) instanceof String) {
			username = (String) getOperand(3);
		}
		
		String tableId = null;
		if ( getOperand(4) != null && getOperand(4) instanceof String) {
			tableId = (String) getOperand(4);
		}
		
		String objectType = null;
		if ( getOperand(5) != null && getOperand(5) instanceof String) {
			objectType = (String) getOperand(5);
		}
		
		String binaryColumnName = null;
		if ( getOperand(6) != null && getOperand(6) instanceof String) {
			binaryColumnName = (String) getOperand(6);
		}
		
		// Max in operator in oracle takes 1000 ites, but we need more ::
		int numOfItems = items.size();
		String queryPart = "";
		int startPosition = 0;
		int endPosition = 999;
		if(startPosition + endPosition >= numOfItems) {
			endPosition = numOfItems - 1;
		} else {
			endPosition = startPosition + 999;
		}
		while(endPosition <= numOfItems && startPosition != endPosition) {
			queryPart = queryPart + " or "+ tableId +" in " + items.subList(startPosition, endPosition).toString().replace(",","','").replace("[", "('").replace("]","')").replace(" ", "");
			startPosition = endPosition;
			if(endPosition + 999 >= numOfItems) {
				endPosition = numOfItems;
			} else {
				endPosition = startPosition + 999;
			}
			
		}
		
		queryPart = queryPart.substring(4).substring(tableId.length());
		
		String query = "select * FROM document where " + tableId 
						+	queryPart
						+ " AND objectType  = '" + objectType +"'";

		JDBCMappable sql = null;
		ArrayList result = new ArrayList();
		
		try {
			sql = JDBCFactory.getJDBCMap(getAccess());
			if (transactionContext != -1) {
				  sql.setTransactionContext(transactionContext);
			} else {
				sql.setDatasource(datasource);
				sql.setUsername(username);
			}
			sql.setQuery(query);
			System.out.println(query);
			ResultSetMap [] resultSet = sql.getResultSet();
			if (resultSet.length > 0) {
				for (int i = 0; i < resultSet.length; i++ ) {
					result.add(resultSet[i].getColumnValue(new Integer(0)));
				}
			}
			int dataPosition = -1;
			int idPosition = -1;
			
			// We got them all, now MERGE :D
			if(result.size() > 0) {
				
				// First find DATA position
				for(int i = 0; i < resultSet[0].getValuesSize(); i++ ) {
					if(resultSet[0].getColumnName(i).equalsIgnoreCase(binaryColumnName)) {
						dataPosition = i;
						break;
					}
				}
				
				// Find ID position
				for(int i = 0; i < resultSet[0].getValuesSize(); i++ ) {
					if(resultSet[0].getColumnName(i).equalsIgnoreCase(tableId)) {
						idPosition = i;
						break;
					}
				}
				
				
				// Then combine
				try {
					PDFMergerUtility merger = new PDFMergerUtility();
					File tempFile = File.createTempFile("pdfmerge", "pdf");
					String fileName = tempFile.getCanonicalPath();
					merger.setDestinationFileName(fileName);
					
					
					// Logic to short by items. 
					for(String item : items) {
						for(int i = 0; i<resultSet.length; i++) {
							if(resultSet[i].getColumnValue(tableId).toString().equals(item)) {
								// FOUND
								merger.addSource(((Binary)resultSet[i].getColumnValue(dataPosition)).getFile());
								break;
							}
						}
					}

					
					merger.mergeDocuments();
					Binary resultPDF = new Binary(new File(fileName), false);
					tempFile.delete();
					
					return resultPDF;
					
				} catch (IOException e) {
					throw new TMLExpressionException(this, e.getMessage(), e);
				} 
			}
			
		} catch (Exception e) {
			sql.kill();
			throw new TMLExpressionException(this, "Fatal error: " + e.getMessage() + ", query = " + query,e);
		} finally {
			long end = System.currentTimeMillis();
			sql.kill();
		}
				
		return null;
			
	}

}
