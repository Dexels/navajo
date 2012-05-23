<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsd="http://www.w3.org/2001/XMLSchema">

<xsl:output method="xml"/>
<xsl:output indent="yes"/>

<xsl:template match="tml">
  <definitions>
   <types>
    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:nav="http://www.dexels.com/xsd/navajo">
      <xsd:element name="option" maxOccurs="'unbounded'">
          <xsd:attribute name="name" type="xsd:string" use="required"/>
          <xsd:attribute name="value" type="xsd:string" use="required"/>
          <xsd:attribute name="selected" type="xsd:boolean" use="required"/>
      </xsd:element>
      <xsl:apply-templates select="message"/>
    </xsd:schema>
  </types>
  </definitions>
</xsl:template>

<xsl:template match="message">
     <xsl:element name="xsd:element">
        <xsl:attribute name="name">
          <xsl:value-of select="@name"/>
        </xsl:attribute>
        <xsl:if test="@index='0'">
          <xsl:attribute name="maxOccurs">
            <xsl:value-of select="'unbounded'"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:element name="xsd:complexType">
           <xsl:element name="xsd:sequence">
              <xsl:apply-templates select="message"/>
              <xsl:apply-templates select="property"/>
           </xsl:element>
        </xsl:element>
     </xsl:element>
</xsl:template>

<xsl:template match="property">
  <xsl:variable name="propertyName" select="@name"/>
  <xsl:element name="xsd:element">
    <xsl:attribute name="name">
      <xsl:value-of select="@name"/>
    </xsl:attribute>
    <xsl:attribute name="type">
      <xsl:choose>
        <xsl:when test="@type='string'">
          <xsl:value-of select="'xsd:string'"/>
        </xsl:when>
        <xsl:when test="@type='integer'">
          <xsl:value-of select="'xsd:int'"/>
        </xsl:when>
        <xsl:when test="@type='selection'">
          <xsl:value-of select="'nav:option'"/>
        </xsl:when>
        <xsl:when test="@type='boolean'">
          <xsl:value-of select="'xsd:boolean'"/>
        </xsl:when>
        <xsl:when test="@type='float'">
          <xsl:value-of select="'xsd:double'"/>
        </xsl:when>
        <xsl:when test="@type='date'">
          <xsl:value-of select="'xsd:datetime'"/>
        </xsl:when>
      </xsl:choose>
    </xsl:attribute>
  </xsl:element>
</xsl:template>
</xsl:stylesheet>
