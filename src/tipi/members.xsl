<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
  <xsl:template match="message">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <!-- defines the layout master -->
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simple" page-height="21cm" page-width="29.7cm" margin-top="1.5cm" margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
          <fo:region-body margin-top="3cm"/>
          <fo:region-before extent="1.5cm"/>
          <fo:region-after extent="1.5cm"/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simple">
        <fo:static-content flow-name="xsl-region-before">
          <fo:block text-align="left" font-size="24pt" font-family="serif" line-height="14pt">
					Gegevens leden
          </fo:block>
          <fo:block text-align="end" font-size="10pt" font-family="serif" line-height="14pt">
          p. <fo:page-number/>
          </fo:block>
        </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
        
   
          <xsl:apply-templates select="message" mode="header"/>


        
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>


  <xsl:template match="message" mode="header">
    <!--<xsl:if test="position() = 1">-->
    <fo:table>
      <xsl:apply-templates select="property" mode="header"/>
            <fo:table-body>
      <xsl:choose>
      <xsl:when test="position() = 1">
        <fo:table-row>
          <xsl:apply-templates select="property" mode="header_titles"/>
        </fo:table-row>
        <fo:table-row>
          <xsl:apply-templates select="property" mode="table"/>
        </fo:table-row>
      </xsl:when>
      <xsl:otherwise>
        <fo:table-row>
          <xsl:apply-templates select="property" mode="table"/>
        </fo:table-row>
      </xsl:otherwise>
    </xsl:choose>
    </fo:table-body>
    </fo:table>
    <!--</xsl:if>-->
  </xsl:template>

  <xsl:template match="message" mode="table">
    <xsl:choose>
      <xsl:when test="position() = 1">
        <fo:table-row>
          <xsl:apply-templates select="property" mode="header_titles"/>
        </fo:table-row>
        <fo:table-row>
          <xsl:apply-templates select="property" mode="table"/>
        </fo:table-row>
      </xsl:when>
      <xsl:otherwise>
        <fo:table-row>
          <xsl:apply-templates select="property" mode="table"/>
        </fo:table-row>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="property" mode="header">
    <fo:table-column column-width="30mm"/>
  </xsl:template>
  
  <xsl:template match="property" mode="header_titles">
    <fo:table-cell>
      <fo:block font-size="10pt" line-height="14pt" font-weight="bold">
        <xsl:value-of select="@description"/>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template match="property" mode="table">
    <fo:table-cell>
      <fo:block font-size="10pt" line-height="14pt">
        <xsl:value-of select="@value"/>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

</xsl:stylesheet>
