package com.dexels.navajo.adapter.sqlmap;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class DbTable implements Mappable {

  public String name;
  public String datasource;
  public String catalogName;
  public String schemaName;

  public DbColumn[] columns;
  
private final static Logger logger = LoggerFactory.getLogger(DbTable.class);

  @Override
public void load(Access access) throws MappableException, UserException {
  }

  @Override
public void store() throws MappableException, UserException {
  }

  @Override
public void kill() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DbColumn[] getColumns() {
    if (columns == null) {
      try {
        setColumns();
      }
      catch (Exception ex) {
    	  logger.error("Error: ", ex);
      }
    }
    return columns;
  }

  protected final void setColumns() throws Exception {

    SQLMap sqlMap = new SQLMap();
    sqlMap.load(null);
    sqlMap.setDatasource(datasource);
    Connection c = sqlMap.getConnection();
    ArrayList l = new ArrayList();

    if (c != null) {

      DatabaseMetaData metaData = c.getMetaData();

      try {
        ResultSet rs = metaData.getColumns(catalogName, schemaName, name, null);
        while (rs.next()) {
          String value = rs.getString("COLUMN_NAME");
          String type = rs.getString("TYPE_NAME");
          String nullable = rs.getString("NULLABLE");
          String size = rs.getString("COLUMN_SIZE");
          DbColumn t = new DbColumn();
          t.name = value;
          t.type = type;
          l.add(t);
        }
        rs.close();
      }
      catch (SQLException ex) {
    	  logger.error("Error: ", ex);
      }
      finally {
        sqlMap.store();
      }
    }
    columns = new DbColumn[l.size()];
    columns = (DbColumn[]) l.toArray(columns);
  }

}