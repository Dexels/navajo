<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
  <xsl:template match="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <!-- defines the layout master -->
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simple" page-height="29.7cm" page-width="21cm" margin-top="1.5cm" margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
          <fo:region-body margin-top="3cm"/>
          <fo:region-before extent="1.5cm"/>
          <fo:region-after extent="1.5cm"/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simple">
        <fo:static-content flow-name="xsl-region-before">
          <fo:block text-align="left" font-size="24pt" font-family="serif" line-height="14pt">
					Lidgegevens
          </fo:block>
          <fo:block text-align="end" font-size="10pt" font-family="serif" line-height="14pt">
          Dexels BV - p. <fo:page-number/>
          </fo:block>
        </fo:static-content>
        <fo:flow flow-name="xsl-region-body">
          <xsl:apply-templates select="message"/>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <xsl:template match="message">
    <xsl:variable name="type" select="@type"/>
    <xsl:choose>
      <xsl:when test="$type = 'array'">

      </xsl:when>
      <xsl:otherwise>
        <fo:table>
          <fo:table-column column-width="60mm"/>
          <fo:table-column column-width="60mm"/>
          <fo:table-body>
            <xsl:apply-templates select="property"/>
          </fo:table-body>
        </fo:table>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  <xsl:template match="property">
    <fo:table-row>
      <fo:table-cell><fo:block font-size="10pt" line-height="14pt"><xsl:value-of select="@description"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block font-size="10pt" line-height="14pt">: <xsl:value-of select="@value"/></fo:block></fo:table-cell>
    </fo:table-row>
  </xsl:template>
</xsl:stylesheet>
