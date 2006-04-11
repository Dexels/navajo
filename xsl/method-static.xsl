<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id$ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Method node -->
  <xsl:template match="methods">
    <font class="tag">methods</font>
    <blockquote>
      <xsl:apply-templates select="method"/>
    </blockquote>
  </xsl:template>
  <!-- Methods node -->
  <xsl:template match="method">
    <font class="attrib">
      <xsl:text> name: </xsl:text>
    </font>
    <xsl:element name="a">
      <xsl:attribute name="href">
          <xsl:value-of select="concat( $documentroot, @name, '.html' )"/>
      </xsl:attribute>
      <xsl:value-of select="@name"/>
      <xsl:text>( </xsl:text>
      <xsl:apply-templates select="required"/>
      <xsl:text> )</xsl:text>
    </xsl:element>
    <xsl:call-template name="fmtComment">
      <xsl:with-param name="c" select="@comment"/>
    </xsl:call-template>
    <xsl:element name="br"/>
  </xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
