<tsl>
  <map object="com.dexels.navajo.adapter.SQLMaintenanceMap">
    <message name="SQLMap" count="1" condition="">
      <message name="Datasource" count="1" condition="">
        <map ref="datasources">
          <property name="Delete" type="boolean" direction="in">
            <expression name="false" condition=""/>
          </property>
          <property name="Update" type="boolean" direction="in">
            <expression name="false" condition=""/>
          </property>
          <property name="Name" direction="out">
            <expression name="$datasourceName" condition=""/>
          </property>
          <property name="Url" length="50" direction="in">
            <expression name="$url" condition=""/>
          </property>
          <property name="Driver" length="50" direction="in">
            <expression name="$driver" condition=""/>
          </property>
          <property name="Username" length="50" direction="in">
            <expression name="$username" condition=""/>
          </property>
          <property name="Password" length="50" direction="in">
            <expression name="$password" condition=""/>
          </property>
          <property name="MinConnections" type="integer" length="10" direction="in">
            <expression name="$min_connections" condition=""/>
          </property>
          <property name="MaxConnections" type="integer" length="10" direction="in">
            <expression name="$max_connections" condition=""/>
          </property>
          <property name="LogFile" length="40" direction="in">
            <expression name="$logfile" condition=""/>
          </property>
          <property name="Refresh" type="float" length="10" direction="in">
            <expression name="$refresh" condition=""/>
          </property>
        </map>
      </message>
    </message>
  </map>
</tsl>
