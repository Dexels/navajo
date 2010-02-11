#require 'jdbc/mysql'
#require 'java'
require 'docutils.rb'
#require 'sql.rb'
#include_class Java::com.mysql.jdbc.Driver
include_class Java::com.dexels.navajo.adapter.SQLMap

class SQLMapper
  
  def initialize(datasourceName)
    @datasource = datasourceName  
    @sqlMap = SQLMap.new
    if($access!=nil) 
      @sqlMap.load($access);
    end
  
  end
  
  def getConnection
    @sqlMap.getConnection
  end
  
  def setCustom(url,user,pass)
        @customConnection = java.sql.DriverManager.get_connection(url, user, pass)
  end
  
  def query(query,params=nil)
    connection = nil
    if(@customConnection!=nil) 
      connection = @customConnection
    else
      connection = @sqlMap.getConnection
    end
    stmtSelect = connection.prepare_statement(query);
    if (params!=nil) 
      count = 1;
#      params.each { |p| stmtSelect.set }      
    end
    res = stmtSelect.executeQuery()
    meta = res.getMetaData
    colcnt = meta.getColumnCount
    names = {}
    colcnt.times {|i|
      names[i+1] = meta.getColumnName(i+1)
    }
    while (res.next) do
      row = {}
      colcnt.times {|i|
        row[names[i+1]]= res.getString(i+1)
      }
      yield row
    end
    stmtSelect.close
  end
end