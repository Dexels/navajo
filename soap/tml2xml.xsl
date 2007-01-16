<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsd="http://www.w3.org/2001/XMLSchema"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:nav="http://www.dexels.com/xsd/navajo"
  xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding"
  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope"
  >

<xsl:output method="xml"/>
<xsl:output indent="yes"/>

<xsl:template match="tml">
  <xsl:element name="SOAP-ENV:Envelope">
    <xsl:element name="SOAP-ENV:Body">
        <xsl:apply-templates select="message"/>
    </xsl:element>
    </xsl:element>
</xsl:template>

<xsl:template match="message">
  <xsl:variable name="messageName" select="@name"/>
  <xsl:element name="{$messageName}">
    <xsl:if test="@type='array'">
      <xsl:attribute name="SOAP-ENC:arrayType">
        <xsl:value-of select="concat(child::*[position()=1]/@name,'[',count(child::*),']')"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:apply-templates select="message"/>
    <xsl:apply-templates select="property"/>
  </xsl:element>
</xsl:template>

<xsl:template match="option">
 <xsl:element name="nav:option">
    <xsl:attribute name="name">
      <xsl:value-of select="@name"/>
    </xsl:attribute>
    <xsl:attribute name="value">
      <xsl:value-of select="@value"/>
    </xsl:attribute>
    <xsl:attribute name="selected">
      <xsl:value-of select="@selected"/>
    </xsl:attribute>
 </xsl:element>
</xsl:template>

<xsl:template match="property">
  <xsl:variable name="propertyName" select="@name"/>
  <xsl:element name="{$propertyName}">
    <xsl:attribute name="xsi:type">
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
    <xsl:choose>
      <xsl:when test="@type='selection'">
          <xsl:apply-templates select="option"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@value"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:element>
</xsl:template>
</xsl:stylesheet>
