<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
  <!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" encoding="ISO-8859-1"/>
    <!-- standard property-in, property-out and property-hidden templates -->
    <xsl:template name="property-in">
        <xsl:variable name="msgName" select="../@name"/>
            <xsl:element name="input">
                <xsl:attribute name="type">
                    <xsl:value-of select="@type"/>
                </xsl:attribute>
                <xsl:attribute name="name">
                    <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                </xsl:attribute>
                <xsl:attribute name="value">
                    <xsl:value-of select="@value"/>
                </xsl:attribute>
            </xsl:element>
    </xsl:template>
    <xsl:template name="property-out">
        <xsl:variable name="msgName" select="../@name"/>
        <xsl:element name="input">
            <xsl:attribute name="type">hidden</xsl:attribute>
            <xsl:attribute name="name">
                <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
            </xsl:attribute>
            <xsl:attribute name="value">
                <xsl:value-of select="@value"/>
            </xsl:attribute>
        </xsl:element>
        <xsl:value-of select="@value"/>
    </xsl:template>
    <xsl:template name="property-display">
        <xsl:choose>
            <xsl:when test="string-length(@value)=0"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></xsl:when>
		 <xsl:otherwise>
			  <xsl:value-of select="@value"/>
		 </xsl:otherwise>
	</xsl:choose>
    </xsl:template>
    <xsl:template name="property-display1">
        <xsl:choose>
	   <xsl:when test="string-length(@value)=0"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></xsl:when>
		 <xsl:otherwise>
			  <xsl:value-of select="@value"/>
			  <xsl:text disable-output-escaping="yes">&lt;br&gt;</xsl:text>
		 </xsl:otherwise>
	</xsl:choose>
    </xsl:template>
    <xsl:template name="date-out">
        <xsl:variable name="msgName" select="../@name"/>
        <xsl:element name="input">
            <xsl:attribute name="type">hidden</xsl:attribute>
            <xsl:attribute name="name">
                <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
            </xsl:attribute>
            <xsl:attribute name="value">
                <xsl:value-of select="@value"/>
            </xsl:attribute>
        </xsl:element>
        <xsl:value-of select="substring(@value, 1, 11)"/>
    </xsl:template>
    <xsl:template name="property-hidden">
        <xsl:variable name="msgName" select="../@name"/>
        <xsl:element name="input">
            <xsl:attribute name="type">hidden</xsl:attribute>
            <xsl:attribute name="name">
                <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
            </xsl:attribute>
            <xsl:attribute name="value">
                <xsl:value-of select="@value"/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
    <!-- standard select-in, select-out and option templates -->
    <xsl:template name="select-out">
        <xsl:variable name="msgName" select="../@name"/>
            <xsl:element name="select">
                <xsl:attribute name="name">
                    <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                </xsl:attribute>
                <xsl:attribute name="disabled"/>
                <xsl:apply-templates select="option" mode="option-in"/>
            </xsl:element>
        <xsl:element name="input">
            <xsl:attribute name="type">hidden</xsl:attribute>
            <xsl:attribute name="name">
                <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
            </xsl:attribute>
            <xsl:attribute name="value">
                <xsl:apply-templates mode="option-out" select="option"/>           
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
    <xsl:template name="select-in">
        <xsl:variable name="msgName" select="../@name"/>
            <xsl:element name="select">
                <xsl:attribute name="name">
                    <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                </xsl:attribute>
                <xsl:apply-templates mode="option-in" select="option"/>
            </xsl:element>
    </xsl:template>
    <xsl:template match="option" mode="option-in">
        <xsl:element name="option">
            <xsl:if test="@selected='0'">
                <xsl:attribute name="value">
                    <xsl:value-of select="@value"/>
                </xsl:attribute>
                <xsl:value-of select="@name"/>
            </xsl:if>
            <xsl:if test="@selected='1'">
                <xsl:attribute name="selected"/>
                <xsl:attribute name="value">
                    <xsl:value-of select="@value"/>
                </xsl:attribute>
                <xsl:value-of select="@name"/>
            </xsl:if>
        </xsl:element>
    </xsl:template>
    <xsl:template match="option" mode="option-out">
        <xsl:if test="@selected='1'">
            <xsl:value-of select="@value"/>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
