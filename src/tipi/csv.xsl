<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<xsl:strip-space elements="*"/>
  <xsl:template match="message">
    <xsl:variable name="type" select="@type"/>
    <xsl:choose>
      <xsl:when test="$type = 'array'">
        <xsl:apply-templates select="message"/>
      </xsl:when>
      <xsl:otherwise>
<xsl:text>
</xsl:text>
        <xsl:apply-templates select="property"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  <xsl:template match="property">
   <xsl:text>"</xsl:text>
   <xsl:value-of select="@value"/><xsl:text>"</xsl:text>
    <xsl:if test="not(position() = last())">, </xsl:if>
  </xsl:template>  
</xsl:stylesheet>
