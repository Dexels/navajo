<tsl>
  <map object="com.dexels.navajo.adapter.SQLMap">
    <field name="query" condition="">
      <expression name="&apos;select lid.relatie_cd AS lidrelatie ,vereniging.ver_nm AS ver_nm, lid.roep_nm, lid.achter_nm, lid.voorletters, lid.tussenvoegsel, lid.geslacht, lid.geb_dt, lid.eigenaar as lideigenaar , vereniging.eigenaar as vereigenaar from lid_index, lid, verenigings_lid, vereniging where lid_index.upper_achter_nm =? and lid_index.lid_cd = lid.relatie_cd and  lid.relatie_cd = verenigings_lid.rel_cd and verenigings_lid.relatie_cd = vereniging.relatie_cd&apos;" condition="[/BirthdateQueryMembers/SearchType:value] == 0 AND !(?[/BirthdateQueryMembers/Birthdate])"/>
      <expression name="&apos;select lid.relatie_cd AS lidrelatie ,vereniging.ver_nm AS ver_nm, lid.roep_nm, lid.achter_nm, lid.voorletters, lid.tussenvoegsel, lid.geslacht, lid.geb_dt, lid.eigenaar as lideigenaar , vereniging.eigenaar as vereigenaar from lid_index, lid, verenigings_lid, vereniging where lid_index.upper_achter_nm =? and lid_index.lid_cd = lid.relatie_cd and  lid.relatie_cd = verenigings_lid.rel_cd and verenigings_lid.relatie_cd = vereniging.relatie_cd&apos;" condition="[/BirthdateQueryMembers/SearchType:value] == 1 AND !(?[/BirthdateQueryMembers/Birthdate])"/>
      <expression name="&apos;select lid.relatie_cd AS lidrelatie ,vereniging.ver_nm AS ver_nm, lid.roep_nm, lid.achter_nm, lid.voorletters, lid.tussenvoegsel, lid.geslacht, lid.geb_dt, lid.eigenaar as lideigenaar , vereniging.eigenaar as vereigenaar from lid_index, lid, verenigings_lid, vereniging where lid_index.upper_achter_nm like(?) and lid_index.geb_dt=convert(datetime, ?) and lid_index.lid_cd = lid.relatie_cd and  lid.relatie_cd = verenigings_lid.rel_cd and verenigings_lid.relatie_cd = vereniging.relatie_cd&apos;" condition="[/BirthdateQueryMembers/SearchType:value] == 1"/>
      <expression name="&apos;select lid.relatie_cd AS lidrelatie ,vereniging.ver_nm AS ver_nm, lid.roep_nm, lid.achter_nm, lid.voorletters, lid.tussenvoegsel, lid.geslacht, lid.geb_dt, lid.eigenaar as lideigenaar , vereniging.eigenaar as vereigenaar from lid_index, lid, verenigings_lid, vereniging where lid_index.upper_achter_nm =? and lid_index.geb_dt=convert(datetime, ?) and lid_index.lid_cd = lid.relatie_cd and  lid.relatie_cd = verenigings_lid.rel_cd and verenigings_lid.relatie_cd = vereniging.relatie_cd&apos;" condition="[/BirthdateQueryMembers/SearchType:value] == 0"/>
    </field>
    <field name="parameter">
      <expression name="ToUpper([/BirthdateQueryMembers/Lastname])" condition="[/BirthdateQueryMembers/SearchType:value] == 0"/>
      <expression name="ToUpper([/BirthdateQueryMembers/Lastname]) + &apos;%&apos;" condition="[/BirthdateQueryMembers/SearchType:value] == 1"/>
    </field>
    <field name="parameter" condition="?[/BirthdateQueryMembers/Birthdate]">
      <expression name="FormatDate([/BirthdateQueryMembers/Birthdate], &apos;MM-dd-yyyy&apos;)" condition=""/>
    </field>
    <message name="Result" count="1" condition="">
      <message name="MemberData" count="1" condition="">
        <map ref="resultSet">
          <property name="FirstInitials" direction="out">
            <expression name="Trim($columnValue(&apos;voorletters&apos;))" condition=""/>
          </property>
          <property name="FirstName" direction="out">
            <expression name="Trim($columnValue(&apos;roep_nm&apos;))" condition=""/>
          </property>
          <property name="Infix" direction="out">
            <expression name="Trim($columnValue(&apos;tussenvoegsel&apos;))" condition=""/>
          </property>
          <property name="LastName" direction="out">
            <expression name="Trim($columnValue(&apos;achter_nm&apos;))" condition=""/>
          </property>
          <property name="Gender" direction="out">
            <expression name="Trim($columnValue(&apos;geslacht&apos;))" condition=""/>
          </property>
          <property name="BirthDate" direction="out">
            <expression name="$columnValue(&apos;geb_dt&apos;)" condition=""/>
          </property>
          <property name="MemberRegionOwner" direction="out">
            <expression name="Trim($columnValue(&apos;lideigenaar&apos;))" condition=""/>
          </property>
          <property name="ClubName" direction="out">
            <expression name="Trim($columnValue(&apos;ver_nm&apos;))" condition=""/>
          </property>
          <property name="RegionOwner" direction="out">
            <expression name="Trim($columnValue(&apos;vereigenaar&apos;))" condition=""/>
          </property>
          <property name="MemberIdentifier" direction="out">
            <expression name="Trim($columnValue(&apos;lidrelatie&apos;))" condition=""/>
          </property>
        </map>
      </message>
    </message>
  </map>
</tsl>
