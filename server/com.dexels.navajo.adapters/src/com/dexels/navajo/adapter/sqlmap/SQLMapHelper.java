package com.dexels.navajo.adapter.sqlmap;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * Class that sets the right parametertype according to the given dbIdentifier (if necessary)
 * The default is Oracle (which needs no deviations) 
 * @author Erik Versteeg
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SQLMapHelper {
	
	/**
	 * Set the parameters for the statement
	 * @param statement
	 * @param param
	 * @param idx
	 * @param binaryStreamList
	 * @param dbIdentifier
	 * @param isLegacyMode
	 * @param debug
	 * @param myAccess
	 * @return PreparedStatement
	 * @throws java.sql.SQLException
	 */
	public static PreparedStatement setParameter(PreparedStatement statement, 
												 final Object param, 
												 final int idx,
												 Class classHoldingBinaryStreamList,
												 String dbIdentifier, 
												 boolean isLegacyMode,
												 boolean debug,
  												 Access myAccess) throws java.sql.SQLException {
		
		Access access = myAccess;
		if (access == null) {
			access = new Access();
			if (debug) {
				Access.writeToConsole(access, "Created a new Access object to write to the console");
			}
		}
		
		if ( debug ) {
			if (idx == 0) {
				System.out.println("*************************** statement         = " + statement.toString());
				System.out.println("*************************** dbIdentifier      = " + dbIdentifier);
				System.out.println("*************************** myAccess          = " + access.accessID);
				System.out.println("*************************** isLegacyMode      = " + isLegacyMode);
				System.out.println("*************************** debug             = " + debug);
			}
			System.out.println("*************************** idx               = " + idx);
			System.out.println("*************************** param             = " + param + " (" + ( (param != null) ? param.getClass().getName() : "") + ")");
			System.out.println("*************************** binaryStreamList  = " + classHoldingBinaryStreamList.getName());
		}
		
		if ((param == null) || (param instanceof NavajoType && !(param instanceof Binary) && ((NavajoType) param).isEmpty())) {
			if (SQLMapConstants.POSTGRESDB.equals(dbIdentifier)) { 
				if (debug) {
					Access.writeToConsole(access, "Had to do something in order to not get the cast error from a null value, because it concerns " + dbIdentifier + "\n");
				}
				if (param == null) {
					statement.setNull(idx + 1, Types.NULL);
				} else {
					statement.setNull(idx + 1, Types.VARCHAR);
				}
			} else {
				statement.setNull(idx + 1, Types.VARCHAR);
			}
		} else if (param instanceof String) {
			statement.setString(idx + 1, (String) param);
		} else if (param instanceof Integer) {
			statement.setInt(idx + 1, ((Integer) param).intValue());
		} else if (param instanceof Double) {
			statement.setDouble(idx + 1, ((Double) param).doubleValue());
		} else if (param instanceof Percentage) {
			statement.setDouble(idx + 1, ((Percentage) param).doubleValue());
		} else if (param instanceof java.util.Date) {

			long time = ((java.util.Date) param).getTime();
			if (isLegacyMode) {
				java.sql.Date sqlDate = new java.sql.Date(time);
				statement.setDate(idx + 1, sqlDate);
			} else {
				Timestamp sqlDate = new java.sql.Timestamp(time);
				statement.setTimestamp(idx + 1, sqlDate);
			}
		} else if (param instanceof Boolean) {
			// After conversion of a oracle schema, char(1) is not converted to a boolean field
			// It will be a bpchar(1) field, but will still be recognized as a boolean type here
			// That creates a "value too long" error when trying to
			// update/insert, because the value is true or false and not 0 or 1
			// So prevent the error by using setInt instead
			if (SQLMapConstants.POSTGRESDB.equals(dbIdentifier)) {
				if (debug) {
					Access.writeToConsole(access, "Used setInt instead of setBoolean, because it concerns " + dbIdentifier + "\n");
				}
				statement.setInt(idx + 1, ((Boolean) param).booleanValue() == Boolean.TRUE ? 1 : 0);
			} else {
				statement.setBoolean(idx + 1, ((Boolean) param).booleanValue());
			}
		} else if (param instanceof ClockTime) {
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(((ClockTime) param).dateValue().getTime());
			statement.setTimestamp(idx + 1, sqlDate);
		} else if (param instanceof Money) {
			statement.setDouble(idx + 1, ((Money) param).doubleValue());
		} else if (param instanceof Memo) {
			String memoString = ((Memo) param).toString();
			statement.setCharacterStream(idx + 1, new StringReader(memoString), memoString.length());
		} else if (param instanceof Binary) {
			Binary b = (Binary) param;
//			System.out.println("*************************** Adding a BLOB     = " + b.getMimeType());
			setBlob(statement, idx, b, classHoldingBinaryStreamList);
			if (debug) {
				Access.writeToConsole(access, "ADDED BLOB\n");
			}
		} else {
			throw new SQLException("Unknown type encountered in SQLMap.setStatementParameters(): " + param);
		}
		return statement;
	}

	/**
	 * BEWARE! Possible resource leak!!! Should the stream be closed?
	 * 
	 * @param statement
	 * @param i
	 * @param b
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected static void setBlob(PreparedStatement statement, int i, Binary b, Class classHoldingBinaryStreamList) throws SQLException {
		if (b != null) {

			InputStream is = b.getDataAsStream();

			if (is != null && b.getLength() > 0) {
				statement.setBinaryStream(i + 1, is, (int) b.getLength());
				// All streams in this list will be closed on kill() or store()
				try {
					Class params[] = new Class[1];
					params[0] = InputStream.class;
					String className = classHoldingBinaryStreamList.getName();
					Class cls = Class.forName(className);
					Object obj = cls.newInstance();
					Method method = cls.getDeclaredMethod("addToBinaryStreamList", params);
					method.invoke(obj, is);
				} catch (ClassNotFoundException e) {
					throw new SQLException("ClassNotFoundException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				} catch (InstantiationException e) {
					throw new SQLException("InstantiationException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				} catch (IllegalAccessException e) {
					throw new SQLException("IllegalAccessException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				} catch (SecurityException e) {
					throw new SQLException("SecurityException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				} catch (NoSuchMethodException e) {
					throw new SQLException("NoSuchMethodException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				} catch (IllegalArgumentException e) {
					throw new SQLException("IllegalArgumentException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				} catch (InvocationTargetException e) {
					throw new SQLException("InvocationTargetException encountered in : " + classHoldingBinaryStreamList.getName(), e);
				}
			} else {
				statement.setNull(i + 1, Types.BLOB);
			}
		} else {
			statement.setNull(i + 1, Types.BLOB);
		}
	}
	
	/**
	 * Gets the columnvalue from the resultset while checking the correct datatype
	 * @param rs
	 * @param type
	 * @param columnIndex
	 * @return Object
	 * @throws SQLException
	 * @throws UserException 
	 */
	public static Object getColumnValue(ResultSet rs, int type, int columnIndex) throws SQLException, UserException {
		Object value = null;
		ResultSetMetaData meta = rs.getMetaData();

		switch (type) {

		case Types.SQLXML:
		case Types.CLOB:
		case Types.NCLOB:
		case Types.BINARY:
		case Types.BLOB:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			InputStream is = rs.getBinaryStream(columnIndex);
			if (is != null) {
				value = new Binary(is);
			}
			break;

		case Types.INTEGER:
		case Types.BIGINT:
		case Types.SMALLINT:
		case Types.TINYINT:
		    int tmpValue = rs.getInt(columnIndex);
		    if (rs.wasNull()) {
		        value = null;
		    } else {
		        value = new Integer(tmpValue);
		    }
			break;

		case Types.LONGNVARCHAR:
		case Types.LONGVARCHAR:
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.CHAR:
		case Types.VARCHAR:
			if (rs.getString(columnIndex) != null) {
				value = new String(rs.getString(columnIndex));
			}
			break;

		case Types.NUMERIC:
//			int prec = meta.getPrecision(columnIndex);
			int scale = meta.getScale(columnIndex);

			// if (debug) System.err.println(columnIndex + ", prec = " + prec + ", scale =  " + scale);
			if (scale <= 0) {
	            int tmpValueNumeric = rs.getInt(columnIndex);
	            if (rs.wasNull()) {
	                value = null;
	            } else {
	                value = new Integer(tmpValueNumeric);
	            }
			} else {
	            double tmpValueDouble = rs.getDouble(columnIndex);
	            if (rs.wasNull()) {
	                value = null;
	            } else {
	                value = new Double(tmpValueDouble);
	            }
			}
			break;

		case Types.DECIMAL:
		case Types.FLOAT:
		case Types.DOUBLE:
            double tmpValueDouble = rs.getDouble(columnIndex);
            if (rs.wasNull()) {
                value = null;
            } else {
                value = new Double(tmpValueDouble);
            }
			break;

		case Types.DATE:
			if (rs.getDate(columnIndex) != null) {
				long l = -1;
				try {
					Date d = rs.getDate(columnIndex);
					l = d.getTime();
				} catch (Exception e) {
					Date d = rs.getDate(columnIndex);
					l = d.getTime();
				}
				value = new java.util.Date(l);
			}
			break;

		case -101: // For Oracle; timestamp with
					// timezone, treat this as
					// clocktime.
			if (rs.getTimestamp(columnIndex) != null) {
				long l = -1;
				try {
					Timestamp ts = rs.getTimestamp(columnIndex);
					l = ts.getTime();
				} catch (Exception e) {
					Date d = rs.getDate(columnIndex);
					l = d.getTime();
				}
				value = new ClockTime(new java.util.Date(l));
			}

			break;

		case Types.TIMESTAMP:
			if (rs.getTimestamp(columnIndex) != null) {
				long l = -1;
				try {
					Timestamp ts = rs.getTimestamp(columnIndex);
					l = ts.getTime();
				} catch (Exception e) {
					Date d = rs.getDate(columnIndex);
					l = d.getTime();
				}
				value = new java.util.Date(l);
			}
			break;

		case Types.TIME:
			value = new Time(rs.getTime(columnIndex).getTime());
			break;

		case Types.BOOLEAN:
		case Types.BIT:
            boolean tmpValueBoolean = rs.getBoolean(columnIndex);
            if (rs.wasNull()) {
                value = null;
            } else {
                value = new Boolean(tmpValueBoolean);
            }
			break;

		case Types.ARRAY:
			value = rs.getArray(columnIndex);
			break;

		case Types.REF:
			value = rs.getRef(columnIndex);
			break;

		case Types.ROWID:
			value = rs.getRowId(columnIndex);
			break;

		case Types.NULL:
			break;
			
		// TODO: No idea what to do with these types
		case Types.DATALINK:
		case Types.DISTINCT:
		case Types.JAVA_OBJECT:
		case Types.OTHER:
		case Types.REAL:
		case Types.STRUCT:
			value = rs.getObject(columnIndex);
			break;
			
		default:
			// If it concerns an unknown type, then throw exception
			throw new UserException(-1, "Unknown SQL type : " + type);
		}
		
		return value;
	}

	/**
	 * Returns the Type
	 * @param i
	 * @return String
	 */
	public static String getType(int i) {
		switch (i) {
		case java.sql.Types.DOUBLE:
			return "DOUBLE";

		case Types.FLOAT:
			return "FLOAT";

		case Types.INTEGER:
			return "INTEGER";

		case Types.DATE:
			return "DATE";

		case Types.VARCHAR:
			return "VARCHAR";

		case Types.BIT:
			return "BOOLEAN";

		case Types.TIME:
			return "TIME";

		case Types.TIMESTAMP:
			return "TIMESTAMP";

		case Types.BIGINT:
			return "BIGINT";

		case Types.DECIMAL:
			return "DECIMAL";

		case Types.NULL:
			return "NULL";

		case Types.NUMERIC:
			return "NUMERIC";

		case Types.OTHER:
			return "OTHER";

		case Types.REAL:
			return "REAL";

		case Types.SMALLINT:
			return "SMALLINT";

		case Types.BLOB:
			return "BLOB";

		case Types.DISTINCT:
			return "DISTINCT";

		case Types.STRUCT:
			return "STRUCT";

		case Types.JAVA_OBJECT:
			return "JAVA_OBJECT";

		case Types.TINYINT:
			return "TINYINT";

		default:
			return "UNSUPPORTED: " + i;
		}
	}
}
