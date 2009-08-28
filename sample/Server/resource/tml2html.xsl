<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="basic-tml2html.xsl"/>
    <xsl:output method="html"/>
    <xsl:template match="tml">        
        <html>
            <head>
                <title>Navajo Demo Server - Dexels BV</title>
                <link rel="stylesheet" type="text/css" href="./resource/navajotester.css" />
           </head>
           <body>
               <div id="wrap">
                   <div class="message">
                       <a href="./NavajoTester"><img src="logo-dexels.gif" border="0" alt="Back to the Navajo Tester"/></a>
                   </div>
                   <form action="NavajoTester" method="POST">
                       <xsl:apply-templates select="header"/>
                       <xsl:apply-templates select="message">
                           <xsl:with-param name="level" select="'0'"/>
                       </xsl:apply-templates>
                       <xsl:apply-templates select="methods"/>        
                   </form>
               </div>
           </body>
        </html>
    </xsl:template>
    <xsl:template match="methods">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="method">
        <div class="method">
            <xsl:element name="input">
                <xsl:attribute name="type">submit</xsl:attribute>
                <xsl:attribute name="name">command</xsl:attribute>
                <xsl:attribute name="value">
                    <xsl:value-of select="@name"/>
                </xsl:attribute>
                <xsl:attribute name="class">method</xsl:attribute>
            </xsl:element>
        </div>
    </xsl:template>
    <xsl:template match="message">
        <xsl:param name="level"/>
        <xsl:variable name="msgName" select="@name"/>
        <div class="message">
            <xsl:choose>
                <xsl:when test="parent::tml">
                    <!-- if (the current node is directly beneath <tml>) -->
                    <h3>
                        <xsl:value-of select="@name"/>
                    </h3>
                    <xsl:choose>
                        <xsl:when test="@type='array'">
                            <xsl:apply-templates>
                                <xsl:with-param name="msgName" select="$msgName"/>
                                <xsl:with-param name="level" select="$level + 1"/>
                                <xsl:with-param name="counter" select="'0'"/>
                            </xsl:apply-templates>
                        </xsl:when>
                        <xsl:when test="child::property">
                            <!-- if (the current node is has a child <property>) -->
                            <xsl:apply-templates>
                                <xsl:with-param name="msgName" select="$msgName"/>
                                <xsl:with-param name="level" select="$level + 1"/>
                                <xsl:with-param name="counter" select="'0'"/>
                            </xsl:apply-templates>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates>
                                <xsl:with-param name="msgName" select="$msgName"/>
                                <xsl:with-param name="level" select="$level + 1"/>
                                <xsl:with-param name="counter" select="'0'"/>
                            </xsl:apply-templates>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <!-- else //the currentnode is NOT directly beneath <tml> -->
                    <div class="sub_header">
                        <xsl:choose>
                            <xsl:when test="../@type='array'">
                                <xsl:value-of select="concat(@name, '(', @index, ')')"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="@name"/>
                                <xsl:if test="@mode='lazy'"> (lazy total=<xsl:value-of select="@lazy_total"/>, current
                                        total=<xsl:value-of select="@array_size"/>, remaining=<xsl:value-of
                                    select="@lazy_remaining"/>) </xsl:if>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                    <xsl:choose>
                        <xsl:when test="@type='array'">
                            <xsl:apply-templates>
                                <xsl:with-param name="msgName" select="$msgName"/>
                                <xsl:with-param name="level" select="$level + 1"/>
                                <xsl:with-param name="counter" select="'0'"/>
                            </xsl:apply-templates>
                        </xsl:when>
                        <xsl:when test="child::property">
                            <div class="label">
                                <xsl:apply-templates>
                                    <xsl:with-param name="msgName">
                                        <xsl:value-of select="$msgName"/>/<xsl:choose>
                                            <xsl:when test="../@type='array'">
                                                <xsl:value-of select="concat(@name, '(', @index, ')')"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="@name"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:with-param>
                                    <xsl:with-param name="level" select="$level + 1"/>
                                    <xsl:with-param name="counter" select="'0'"/>
                                </xsl:apply-templates>
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates select="message">
                                <xsl:with-param name="msgName">
                                    <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                                </xsl:with-param>
                                <xsl:with-param name="level" select="$level + 1"/>
                                <xsl:with-param name="counter" select="'0'"/>
                            </xsl:apply-templates>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    <xsl:template match="property">
        <xsl:param name="msgName"/>
        <xsl:param name="level"/>
        <xsl:param name="counter"/>
        <div class="property">
        <div class="property_label">
            <xsl:value-of select="@name"/>  
        </div>
        <!-- @direction='out' to display as labels -->
            <xsl:if test="@direction='out' ">
                <xsl:choose>
                    <xsl:when test="@type='selection'">
                        <xsl:call-template name="select-out"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="property-out"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <!--@direction='in' to display as input types -->
            <xsl:if test="@direction='in'">
                <xsl:choose>
                    <xsl:when test="@type='selection'">
                        <xsl:choose>
                            <xsl:when test="@cardinality='1'">
                                <xsl:element name="select">
                                    <xsl:attribute name="name">
                                        <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                                    </xsl:attribute>
                                    <xsl:for-each select="option">
                                        <xsl:if test="not (@name='')">
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
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:element>
                            </xsl:when>
                            <xsl:when test="@cardinality='+'">
                            <!-- Since HTML forms actually OMIT checkboxes when none are checked, we make sure the variable exists in a hidden input -->
                                    <xsl:element name="input">
                                        <xsl:attribute name="type">hidden</xsl:attribute>
                                        <xsl:attribute name="name">
                                            <xsl:value-of select="$msgName"/>/<xsl:value-of
                                                select="@name"/>:value
                                        </xsl:attribute>
                                    </xsl:element>
                                    <xsl:apply-templates select="option">
                                        <xsl:with-param name="propertyName">
                                            <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                                        </xsl:with-param>
                                    </xsl:apply-templates>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="@type='string' or @type='integer' or @type='float' or @type='password'">
                        <xsl:call-template name="property-in"/>
                    </xsl:when>
                    <xsl:when test="@type='memo'">
                        <xsl:element name="TEXTAREA">
                            <xsl:attribute name="type">
                                <xsl:value-of select="@type"/>
                            </xsl:attribute>
                            <xsl:attribute name="name">
                                <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                            </xsl:attribute>
                            <xsl:attribute name="COLS">
                                <xsl:value-of select="@length"/>
                            </xsl:attribute>
                            <xsl:attribute name="ROWS">6</xsl:attribute>
                            <xsl:value-of select="@value"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="@type='boolean'">
                        <xsl:element name="input">
                            <xsl:attribute name="type">checkbox</xsl:attribute>
                            <xsl:attribute name="class">checkbox</xsl:attribute>
                            <xsl:attribute name="name">
                                <xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
                            </xsl:attribute>
                            <xsl:if test="@value='true'">
                                <xsl:attribute name="checked"/>
                            </xsl:if>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="@type='date'">
                        <xsl:element name="input">
                            <xsl:attribute name="type">text</xsl:attribute>
                            <xsl:attribute name="class">day</xsl:attribute>
                            <xsl:attribute name="name"><xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>_DAY_</xsl:attribute>
                            <xsl:attribute name="size">2</xsl:attribute>
                            <xsl:attribute name="value">
                                <xsl:value-of select="substring(@value,9,2)"/>
                            </xsl:attribute>
                        </xsl:element>
                        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                        <xsl:element name="input">
                            <xsl:attribute name="type">text</xsl:attribute>
                            <xsl:attribute name="class">month</xsl:attribute>
                            <xsl:attribute name="name"><xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>_MONTH_</xsl:attribute>
                            <xsl:attribute name="size">2</xsl:attribute>
                            <xsl:attribute name="value">
                                <xsl:value-of select="substring(@value,6,2)"/>
                            </xsl:attribute>
                        </xsl:element>
                        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                        <xsl:element name="input">
                            <xsl:attribute name="type">text</xsl:attribute>
                            <xsl:attribute name="class">year</xsl:attribute>
                            <xsl:attribute name="name"><xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>_YEAR_</xsl:attribute>
                            <xsl:attribute name="size">4</xsl:attribute>
                            <xsl:attribute name="value">
                                <xsl:value-of select="substring(@value,1,4)"/>
                            </xsl:attribute>
                        </xsl:element>
                    </xsl:when>
                    <xsl:otherwise>
                        (<xsl:value-of select="@type"/>)
                        <xsl:value-of select="@name"/><xsl:value-of select="@value"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </div>
    </xsl:template>
    <xsl:template match="option">
        <xsl:param name="propertyName"/>
        <xsl:for-each select=".">
            <xsl:if test="not (@name='')">
                    <xsl:element name="input">
                        <xsl:attribute name="type">checkbox</xsl:attribute>
                        <xsl:attribute name="name">
                            <xsl:value-of select="$propertyName"/>:value|selection[<xsl:value-of select="count(preceding-sibling::*)"/>]</xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of select="@value"/>
                        </xsl:attribute>
                        <xsl:if test="@selected='1'">
                            <xsl:attribute name="checked"/>
                        </xsl:if>
                    </xsl:element>
                    <xsl:value-of select="@name"/>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
