<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
  <!-- $Id$ -->

  <xsl:template match="/">
    <span class="navajo-body">
      <xsl:apply-templates select="tml|tsl"/>
    </span>
  </xsl:template>

  <!-- TSL or TML header -->
  <xsl:template match="tml|tsl">
    <xsl:variable select="@id" name="id"/>
    <xsl:if test=" string-length($id) > 0 ">
      <h1>SportLink Service: <xsl:value-of select="@id"/></h1>
    </xsl:if>
    <hr/>
    <h2>Business Process 
      <xsl:choose>
        <xsl:when test=" name( current() ) = 'tml' "><xsl:text>Form</xsl:text></xsl:when>
        <xsl:when test=" name( current() ) = 'tsl' "><xsl:text>Control</xsl:text></xsl:when>
      </xsl:choose> Language
    </h2>
    <table>
      <tr><th>Title:</th><td><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text><xsl:value-of select="@id"/></td></tr>
      <tr><th>Description:</th><td><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text><xsl:value-of select="@notes"/></td></tr>
      <tr><th>Company:</th><td><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>Dexels BV</td></tr>
      <tr><th>Author(s):</th><td><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text><xsl:value-of select="@author"/></td></tr>
      <tr><th>Repository:</th><td><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text><xsl:value-of select="@repository"/></td></tr>
    </table>
    <br/>
    <xsl:apply-templates select="map|property|message|param|comment"/>
    <xsl:apply-templates select="methods"/>
  </xsl:template>

  <!-- Method node -->
  <xsl:template match="methods">
    <font class="tag">methods</font>
    <blockquote>
      <xsl:apply-templates select="method"/>
    </blockquote>
  </xsl:template>

  <!-- Methods node -->
  <xsl:template match="method">
    <font class="attrib"><xsl:text> name: </xsl:text></font>
    <xsl:element name="a">
      <xsl:attribute name="href"><xsl:value-of select="@name"/>.html</xsl:attribute>
      <xsl:value-of select="@name"/>
      <xsl:text>(</xsl:text><xsl:apply-templates select="required"/><xsl:text>)</xsl:text>
    </xsl:element>
    <xsl:call-template name="fmtComment">
       <xsl:with-param name="c" select="@comment"/>
    </xsl:call-template>
    <xsl:element name="br"/>
  </xsl:template>

  <!-- Required Node -->
  <xsl:template match="required">
    <xsl:value-of select="@name"/><xsl:text>,</xsl:text>
  </xsl:template>

  <!-- Comment node -->
  <xsl:template match="comment">
    <xsl:text>Comment: </xsl:text>
    <xsl:value-of select="@value"/>
  </xsl:template>

  <!-- Map Node -->
  <!-- maps can have properties, params, messages, maps and fields as children --> 
  <xsl:template match="map">
  	<p>
      <font class="tag">map</font>
        <xsl:if test=" string-length( @object ) > 0 ">
    	    <font class="attrib"><xsl:text> object: </xsl:text></font><code><xsl:value-of select="@object"/></code>
    		</xsl:if>
    		<xsl:if test=" string-length( @ref ) > 0 ">
    	    <font class="attrib"><xsl:text> reference: </xsl:text></font><code><xsl:value-of select="@ref"/></code>
    	  </xsl:if>
    	  <xsl:if test=" string-length( @filter ) > 0 ">
    	    <font class="attrib"><xsl:text> filter: </xsl:text></font><code><xsl:value-of select="@filter"/></code>
    		</xsl:if>
      <xsl:if test=" count( ./* ) > 0 ">
    		<blockquote>
       		<xsl:apply-templates select="field|message|property|param|map|comment"/>
        </blockquote>
      </xsl:if>
    </p>
  </xsl:template>

  <!-- Message Node -->
  <!-- messages can have objects, maps, properties, params, and other messages as children -->
  <xsl:template match="message">
    <p>
      <font class="tag">message</font>
      <xsl:if test=" string-length( @name ) > 0 ">
        <font class="attrib"><xsl:text> name: </xsl:text></font>
        <font class="value"><xsl:value-of select="@name"/></font>
      </xsl:if>
    	<xsl:if test=" string-length( @count ) > 0 ">
        <font class="attrib"><xsl:text> count: </xsl:text></font>
    	  <font class="value"><xsl:value-of select="@count"/></font>
      </xsl:if>
    	<xsl:call-template name="fmtCondition">
    	  <xsl:with-param name="c" select="@condition"/>
    	</xsl:call-template>
    	<xsl:call-template name="fmtComment">
    	  <xsl:with-param name="c" select="@comment"/>
    	</xsl:call-template>
      <xsl:if test=" count( ./* ) > 0 ">
        <blockquote>
     		  <xsl:apply-templates select="property|param|message|map|comment"/>
        </blockquote>
      </xsl:if>
    </p>
  </xsl:template>

  <!-- Parameter or Property Node -->
  <!-- properties can have objects and expressions as children -->
  <xsl:template match="property|param"> 
    <p>
      <xsl:if test=" name( current() ) = 'param' ">
        <font class="tag"><xsl:text> parameter </xsl:text></font>
      </xsl:if>
      <xsl:if test=" name( current() ) = 'property' ">
        <font class="tag"><xsl:text> property </xsl:text></font>
      </xsl:if>
      <xsl:if test=" string-length( @name ) > 0 ">
        <font class="attrib"><xsl:text> name: </xsl:text></font>
        <font class="value"><xsl:value-of select="@name"/></font>
      </xsl:if>
      <xsl:if test=" string-length( @type ) > 0 ">
        <font class="attrib"><xsl:text> type: </xsl:text></font>
        <font class="value"><xsl:value-of select="@type"/></font>
      </xsl:if>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>

      <xsl:if test=" string-length( concat( @description, @value, @length, @cardinality, @direction ) ) > 0 ">
          <xsl:if test=" string-length( @description ) > 0 ">
            <font class="attrib"><xsl:text> description: </xsl:text></font>
            <font class="value"><xsl:value-of select="@description"/></font>
          </xsl:if>
          <xsl:if test=" string-length( @value ) > 0 ">
            <font class="attrib"><xsl:text> value: </xsl:text></font>
            <font class="value"><xsl:value-of select="@value"/></font>
          </xsl:if>
          <xsl:if test=" string-length( @length ) > 0 ">
            <font class="attrib"><xsl:text> length: </xsl:text></font>
            <font class="value"><xsl:value-of select="@length"/></font>
          </xsl:if>
          <xsl:if test=" string-length( @cardinality ) > 0 ">
            <font class="attrib"><xsl:text> cardinality: </xsl:text></font>
            <font class="value"><xsl:value-of select="@cardinality"/></font>
          </xsl:if>
          <xsl:if test=" string-length( @direction ) > 0 ">
            <font class="attrib"><xsl:text> direction: </xsl:text></font>
            <font class="value"><xsl:value-of select="@direction"/></font>
          </xsl:if>
      </xsl:if>
      <xsl:if test=" count( ./* ) > 0 ">
        <xsl:if test=" count( ./option ) > 0 ">
          <blockquote>
            <xsl:apply-templates select="option"/>
          </blockquote>
        </xsl:if>
        <xsl:apply-templates select="expression|map|comment"/>
      </xsl:if>
      <xsl:call-template name="fmtComment">
        <xsl:with-param name="c" select="@comment"/>
      </xsl:call-template>
    </p>
  </xsl:template>

  <!-- Field Node -->
  <!-- fields can have maps and expressions for children -->
  <xsl:template match="field">
    <p>
      <xsl:variable select="@name" name="name"/>
      <font class="tag">field</font><font class="attrib"> name: </font>
      <font class="value"><xsl:value-of select="@name"/></font>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>
      <xsl:call-template name="fmtComment">
        <xsl:with-param name="c" select="@comment"/>
      </xsl:call-template>
      <xsl:if test=" count( ./* ) > 0 ">
        <xsl:apply-templates select="expression|map|comment"/>
      </xsl:if>
    </p>
  </xsl:template>

  <!-- Expression Node -->
  <xsl:template match="expression">
    <xsl:choose>
      <xsl:when test="string-length ( parent::field/@condition ) > 0 or string-length ( parent::property/@condition ) > 0 or string-length( @condition ) > 0 or string-length( preceding-sibling::expression/@condition ) > 0 or string-length( @name ) > 50 or string-length( preceding-sibling::expression/@name ) > 50 ">
        <blockquote>
          <font class="tag">expression</font>
          <xsl:call-template name="fmtCondition">
            <xsl:with-param name="c" select="@condition"/>
          </xsl:call-template>
          <xsl:call-template name="fmtComment">
            <xsl:with-param name="c" select="@comment"/>
          </xsl:call-template> 
          <font class="attrib"> value: </font><code><xsl:value-of select="@name"/></code>
        </blockquote>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="fmtCondition">
          <xsl:with-param name="c" select="@condition"/>
        </xsl:call-template>
         <xsl:call-template name="fmtComment">
           <xsl:with-param name="c" select="@comment"/>
         </xsl:call-template> 
         <font class="attrib"> value: </font><code><xsl:value-of select="@name"/></code>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- Option Node -->
  <xsl:template match="option">
    <p><font class="tag">option</font>
      <font class="attrib"> name: </font><font class="value"><xsl:value-of select="@name"/></font>
      <xsl:if test=" string-length( @value ) > 0 ">
        <font class="attrib"> value: </font><font class="value"><xsl:value-of select="@value"/></font>
      </xsl:if>
      <xsl:if test="@selected = '1'">
        <font class="attrib"><xsl:text> selected</xsl:text></font>
      </xsl:if>
    </p>
  </xsl:template>

  <!-- Comment attribute -->
  <xsl:template name="fmtComment">
     <xsl:param name="c"/>
     <xsl:if test=" string-length( $c ) > 0 ">
        <xsl:text></xsl:text><font class="comment"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>//<xsl:value-of select="$c"/></font></xsl:if>
  </xsl:template>

  <!-- Condition Attribute -->
  <xsl:template name="fmtCondition">
    <xsl:param name="c"/>
      <xsl:if test=" string-length( $c ) > 0 ">
        <font class="attrib"><xsl:text> condition: </xsl:text></font>
        <code><xsl:value-of select="$c"/></code>
      </xsl:if>
  </xsl:template>

</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
