<?xml version="1.0" encoding="UTF-8"?>
<!-- $Id$ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" method="html"/>
  <xsl:include href="method-static.xsl"/>
  <xsl:include href="include-static.xsl"/>
  <xsl:include href="navascript.xsl"/>
  <xsl:template match="/">
    <span class="navajo-body">
      <xsl:apply-templates select="tsl"/>
      <xsl:element name="a">
        <xsl:attribute name="href">
          <xsl:text>./index.html</xsl:text>
        </xsl:attribute>
        <xsl:text>[ index ]</xsl:text>
      </xsl:element>
    </span>
  </xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
