<tsl>
  <map object="com.dexels.navajo.adapter.SQLMaintenanceMap">
    <field name="datasources">
      <map filter="[Update] == true" ref="/SQLMap/Datasource.*">
        <field name="max_connections">
          <expression name="[MaxConnections]" condition=""/>
        </field>
        <field name="url">
          <expression name="[Url]" condition=""/>
        </field>
        <field name="driver">
          <expression name="[Username]" condition=""/>
        </field>
        <field name="password">
          <expression name="[Password]" condition=""/>
        </field>
        <field name="datasourceName">
          <expression name="[Name]" condition=""/>
        </field>
        <field name="min_connections">
          <expression name="[MinConnections]" condition=""/>
        </field>
        <field name="refresh">
          <expression name="[Refresh]" condition=""/>
        </field>
        <field name="username">
          <expression name="[Username]" condition=""/>
        </field>
        <field name="driver">
          <expression name="[Driver]" condition=""/>
        </field>
        <field name="logfile">
          <expression name="[LogFile]" condition=""/>
        </field>
      </map>
    </field>
  </map>
  <message name="Result">
    <property name="Status" type="string" value="Done" direction="out">
      <expression name="&apos;1&apos;"/>
    </property>
  </message>
</tsl>
