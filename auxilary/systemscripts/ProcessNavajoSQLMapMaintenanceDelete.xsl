<tsl>
  <map object="com.dexels.navajo.adapter.SQLMaintenanceMap">
    <field name="deleteDatasources">
      <map ref="/SQLMap/Datasource.*" filter="[Delete] == true">
        <field name="datasourceName">
          <expression name="[Name]" condition=""/>
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
