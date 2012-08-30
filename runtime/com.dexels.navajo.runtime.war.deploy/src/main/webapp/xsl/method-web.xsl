<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id: method-web.xsl,v 1.1 2010/07/28 16:36:16 frank Exp $ -->
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
      <xsl:if test="not( contains( @name, '/' ) )">
        <xsl:attribute name="href">
          <xsl:text>/NavaDocWeb/webservice?sname=</xsl:text>
          <xsl:value-of select="@name"/>
        </xsl:attribute>
      </xsl:if>
      <xsl:if test="contains( @name, '/' )">
        <xsl:attribute name="href">
          <xsl:text>/NavaDocWeb/webservice?sname=</xsl:text>
          <xsl:value-of select="substring-after( @name, '/' )"/>
        </xsl:attribute>
      </xsl:if>
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
<!-- EOF: $RCSfile: method-web.xsl,v $ -->
