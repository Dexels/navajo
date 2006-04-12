<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id$ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" method="html" indent="yes"/>
  <!-- DON'T TOUCH THIS: v -->
  <xsl:variable name="documentroot" select="'[DOCUMENTROOT]/'"/>
  <!-- DON'T TOUCH THIS: ^ -->
  <xsl:include href="include-static.xsl"/>
  <xsl:include href="navascript.xsl"/>
  <xsl:include href="method-static.xsl"/>
  <xsl:template match="/">
    <div class="saPage">
      <xsl:apply-templates select="tsl|tml"/>
      <xsl:element name="a">
      <xsl:attribute name="class">
	<xsl:text>saIndex</xsl:text>
      </xsl:attribute>
      <xsl:attribute name="href">
        <xsl:value-of select="concat( $documentroot, 'index.html')"/>
      </xsl:attribute>
      <xsl:text>[index]</xsl:text>
      </xsl:element>
   </div>
  </xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
