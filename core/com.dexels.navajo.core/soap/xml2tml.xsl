<xsl:stylesheet
  version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsd="http://www.w3.org/2001/XMLSchema"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:nav="http://www.dexels.com/xsd/navajo"
  xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding"
  >

<xsl:output method="xml"/>

<xsl:template match="/">
  <xsl:element name="tml">
      <xsl:apply-templates select="./Envelope/Body/Process/*"/>
  </xsl:element>
</xsl:template>

<xsl:template name="nav:option">
 <xsl:element name="option">
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

<xsl:template match="*[(*)]">
 <xsl:choose>
    <xsl:when test="count(child::nav:option) > 0">
      <xsl:element name="property">
        <xsl:attribute name="name">
          <xsl:value-of select="name(.)"/>
        </xsl:attribute>
        <xsl:attribute name="type">
          <xsl:value-of select="'selection'"/>
        </xsl:attribute>
        <xsl:for-each select="nav:option">
            <xsl:call-template name="nav:option"/>
        </xsl:for-each>
      </xsl:element>
    </xsl:when>
    <xsl:otherwise>
      <xsl:element name="message">
        <xsl:if test="string-length(@SOAP-ENC:arrayType) &gt; 0">
           <xsl:attribute name="type"><xsl:value-of select="'array'"/></xsl:attribute>
        </xsl:if>
        <xsl:attribute name="name">
          <xsl:choose>
             <xsl:when test="contains(name(.), ':')">
               <xsl:value-of select="substring-after(name(.), ':')"/>
             </xsl:when>
             <xsl:otherwise>
                <xsl:value-of select="name(.)"/>
             </xsl:otherwise>
           </xsl:choose>
        </xsl:attribute>
        <xsl:apply-templates select="child::*"/>
      </xsl:element>
    </xsl:otherwise>
 </xsl:choose>
</xsl:template>

<xsl:template match="*[not(*)]">
  <xsl:element name="property">
    <xsl:attribute name="name">
      <xsl:value-of select="name(.)"/>
    </xsl:attribute>
    <xsl:attribute name="value">
      <xsl:value-of select="."/>
    </xsl:attribute>
    <xsl:attribute name="type">
      <xsl:choose>
         <xsl:when test="@xsi:type='xsd:string'">
            <xsl:value-of select="'string'"/>
         </xsl:when>
         <xsl:when test="@xsi:type='xsd:int'">
             <xsl:value-of select="'integer'"/>
         </xsl:when>
        <xsl:when test="@xsi:type='xsd:boolean'">
          <xsl:value-of select="'boolean'"/>
        </xsl:when>
        <xsl:when test="@xsi:type='xsd:double'">
          <xsl:value-of select="'float'"/>
        </xsl:when>
        <xsl:when test="@xsi:type='xsd:datetime'">
          <xsl:value-of select="'date'"/>
        </xsl:when>
      </xsl:choose>
    </xsl:attribute>
  </xsl:element>
</xsl:template>
</xsl:stylesheet>
