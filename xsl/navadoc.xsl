<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
     doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
<!-- $Id$ -->

<xsl:template match="/">
  <span class="navajo-body">
    <xsl:apply-templates select="tml|tsl"/>
  </span>
</xsl:template>

  <!-- TSL or TML  header -->
  <xsl:template match="tml|tsl">
    <h1>SportLink Service: <xsl:value-of select="@id"/></h1>
    <h1>Business Process <xsl:choose>
    <xsl:when test=" name( current() ) = 'tml' "><xsl:text>Form</xsl:text></xsl:when>
    <xsl:when test=" name( current() ) = 'tsl' "><xsl:text>Control</xsl:text></xsl:when>
		</xsl:choose> Language</h1>
    <hr/><table border="0">
    <tr><th align="left" valign="top">Title:</th><td align="left" valign="top"><xsl:value-of select="@id"/></td></tr>
    <tr><th align="left" valign="top">Description:</th><td align="left" valign="top"><xsl:value-of select="@notes"/></td></tr>
    <tr><th align="left" valign="top">Company:</th><td align="left" valign="top">Dexels.com</td></tr>
    <tr><th align="left" valign="top">Author(s):</th><td align="left" valign="top"><xsl:value-of select="@author"/></td></tr>
    <tr><th align="left" valign="top">Repository:</th><td align="left" valign="top"><xsl:value-of select="@repository"/></td></tr>
    </table><hr/>
		<xsl:apply-templates select="map|property|message|param|comment"/>
		<xsl:apply-templates select="methods"/>
  </xsl:template>

  <!-- Method node -->
  <xsl:template match="methods">
     <h2>Methods</h2>
     <blockquote>
       <xsl:apply-templates select="method"/>
     </blockquote>
  </xsl:template>

  <!-- Methods node -->
  <xsl:template match="method">
     <xsl:text> Method:  </xsl:text>
     <xsl:element name="a">
       <xsl:attribute name="href"><xsl:value-of select="@name"/>.html</xsl:attribute>
       <em><xsl:value-of select="@name"/></em>
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
     <xsl:text>Comment:  </xsl:text>
     <em><xsl:value-of select="@value"/></em>
  </xsl:template>

  <!-- Map Node -->
  <!-- maps can have properties, params, messages, maps and fields as children --> 
  <xsl:template match="map">
  	<h2>Map 
		  <xsl:if test=" string-length( @object ) > 0 ">
      	<xsl:text> Object: </xsl:text><code><xsl:value-of select="@object"/></code>
			</xsl:if>
		  <xsl:if test=" string-length( @ref ) > 0 ">
      	<xsl:text> Reference: </xsl:text><code><xsl:value-of select="@ref"/></code>
		  </xsl:if>
		  <xsl:if test=" string-length( @filter ) > 0 ">
      	<xsl:text> Filter: </xsl:text><code><xsl:value-of select="@filter"/></code>
		  </xsl:if>
  
    </h2>
    <xsl:if test=" count( ./* ) > 0 ">
			<blockquote>
   		<xsl:apply-templates select="field|message|property|param|map|comment"/>
      </blockquote>
    </xsl:if>
  </xsl:template>

	<!-- Message Node -->
  <!-- messages can have objects, maps, properties, params, and other messages as children -->
  <xsl:template match="message">
		<h2>Message
			<xsl:if test=" string-length( @name ) > 0 "><xsl:text> Name: </xsl:text>
				<em><xsl:value-of select="@name"/></em></xsl:if>
			<xsl:if test=" string-length( @count ) > 0 "><xsl:text> Count: </xsl:text>
				<em><xsl:value-of select="@count"/></em></xsl:if>
			<xsl:call-template name="fmtCondition">
				<xsl:with-param name="c" select="@condition"/>
			</xsl:call-template>
			<xsl:call-template name="fmtComment">
				<xsl:with-param name="c" select="@comment"/>
			</xsl:call-template>
    </h2>
     <xsl:if test=" count( ./* ) > 0 ">
			<blockquote>
   		<xsl:apply-templates select="property|param|message|map|comment"/>
      </blockquote>
    </xsl:if>
 </xsl:template>

  <!-- Parameter or Property Node -->
  <!-- properties can have objects and expressions as children -->
  <xsl:template match="property|param">
		<h2>
    	<xsl:if test=" name( current() ) = 'param' ">
				<xsl:text>Parameter </xsl:text>
			</xsl:if>
    	<xsl:if test=" name( current() ) = 'property' ">
				<xsl:text>Property </xsl:text>
			</xsl:if>
			<xsl:if test=" string-length( @name ) > 0 ">
    		<xsl:text> Name: </xsl:text><em><xsl:value-of select="@name"/></em>
			</xsl:if>
			<xsl:if test=" string-length( @type ) > 0 ">
    		<xsl:text> Type: </xsl:text><em><xsl:value-of select="@type"/></em>
			</xsl:if>
                <xsl:call-template name="fmtCondition">
                                <xsl:with-param name="c" select="@condition"/>
                </xsl:call-template>
                <xsl:call-template name="fmtComment">
                                <xsl:with-param name="c" select="@comment"/>
                </xsl:call-template>
    </h2>
		<xsl:if test=" string-length( concat( @description, @value, @length, @cardinality, @direction ) ) > 0 ">
			<p>
			<xsl:if test=" string-length( @description ) > 0 ">
    		<b><xsl:text> Description: </xsl:text></b>
				<em><xsl:value-of select="@description"/></em>
			</xsl:if>
			<xsl:if test=" string-length( @value ) > 0 ">
    		<b><xsl:text> Value: </xsl:text></b>
				<em><xsl:value-of select="@value"/></em>
			</xsl:if>
			<xsl:if test=" string-length( @length ) > 0 ">
    		<b><xsl:text> Length: </xsl:text></b>
				<em><xsl:value-of select="@length"/></em>
			</xsl:if>
			<xsl:if test=" string-length( @cardinality ) > 0 ">
    		<b><xsl:text> Cardinality: </xsl:text></b>
				<em><xsl:value-of select="@cardinality"/></em>
			</xsl:if>
			<xsl:if test=" string-length( @direction ) > 0 ">
    		<b><xsl:text> Direction: </xsl:text></b>
				<em><xsl:value-of select="@direction"/></em>
			</xsl:if>
			</p>
		</xsl:if>

     <xsl:if test=" count( ./* ) > 0 ">
			<blockquote>
			<xsl:if test=" count( ./option ) > 0 ">
				<h3>Select Options:</h3>
				<ul>
				<xsl:apply-templates select="option"/>
				</ul>
			</xsl:if>
   		<xsl:apply-templates select="expression|map|comment"/>
      </blockquote>
    </xsl:if>
  </xsl:template>

  <!-- Field Node -->
  <!-- fields can have maps and expressions for children -->
  <xsl:template match="field">
		<h2>Field Name: <xsl:value-of select="@name"/>
			<xsl:call-template name="fmtCondition">
				<xsl:with-param name="c" select="@condition"/>
			</xsl:call-template>
			<xsl:call-template name="fmtComment">
				<xsl:with-param name="c" select="@comment"/>
			</xsl:call-template>
		</h2>
    <xsl:if test=" count( ./* ) > 0 ">
			<blockquote>
   		<xsl:apply-templates select="expression|map|comment"/>
      </blockquote>
    </xsl:if>
  </xsl:template>

  <!-- Expression Node -->
  <xsl:template match="expression">
		<h3>Expression
			<xsl:call-template name="fmtCondition">
				<xsl:with-param name="c" select="@condition"/>
			</xsl:call-template> Value:
			<xsl:call-template name="fmtComment">
				<xsl:with-param name="c" select="@comment"/>
			</xsl:call-template> Value:
		</h3>
		<p><code><xsl:value-of select="@name"/></code></p>
	</xsl:template>

  <!-- Option Node -->
  <xsl:template match="option">
	  <li>
		<b><xsl:value-of select="@name"/></b>
		<xsl:if test=" string-length( @value ) > 0 ">
			<xsl:text> (</xsl:text><xsl:value-of select="@value"/><xsl:text>)</xsl:text>
		</xsl:if>
		<xsl:if test="@selected = '1'">
			<xsl:text> [SELECTED]</xsl:text>
		</xsl:if>
		</li>
	</xsl:template>

  <!-- Comment Attribute -->
  <xsl:template name="fmtComment">
     <xsl:param name="c"/>
     <xsl:if test=" string-length( $c ) > 0 ">
				<br/><xsl:text>  (</xsl:text><code><xsl:value-of select="$c"/></code><xsl:text>)</xsl:text></xsl:if>
  </xsl:template>

  <!-- Condition Attribute -->
  <xsl:template name="fmtCondition">
		<xsl:param name="c"/>
			<xsl:if test=" string-length( $c ) > 0 ">
				<xsl:text> Condition: </xsl:text><code><xsl:value-of select="$c"/></code></xsl:if>
  </xsl:template>

</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
