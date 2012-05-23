<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id$ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Include node -->
  <xsl:template match="include">
    <font class="tag">
      <xsl:text> include: </xsl:text>
    </font>
    <xsl:element name="a">
      <xsl:attribute name="href">
        <xsl:value-of select="concat( $documentroot, @script, '.html' )"/>
      </xsl:attribute>
      <xsl:value-of select="@script"/>
    </xsl:element>
    <xsl:call-template name="fmtComment">
      <xsl:with-param name="c" select="@comment"/>
    </xsl:call-template>
    <xsl:element name="br"/>
  </xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
