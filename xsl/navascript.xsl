<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Id$ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- TSL header -->
  <xsl:template match="tsl">
    <xsl:variable name="id" select="@id"/>
    <xsl:if test=" string-length($id) &gt; 0 ">
      <h1>SportLink Service: <xsl:value-of select="@id"/>
      </h1>
    </xsl:if>
    <hr/>
    <h2>NavaScript</h2>
    <xsl:if test=" name( current() ) = 'tsl'">
      <table>
        <tr>
          <th>Title:</th>
          <td>
            <xsl:value-of select="@id"/>
          </td>
        </tr>
        <tr>
          <th>Description:</th>
          <td>
            <xsl:value-of select="@notes"/>
          </td>
        </tr>
        <tr>
          <th>Company:</th>
          <td>Dexels BV</td>
        </tr>
        <tr>
          <th>Author(s):</th>
          <td>
            <xsl:value-of select="@author"/>
          </td>
        </tr>
        <tr>
          <th>Repository:</th>
          <td>
            <xsl:value-of select="@repository"/>
          </td>
        </tr>
      </table>
    </xsl:if>
    <br/>
    <xsl:apply-templates select="include|map|property|message|param|comment"/>
    <xsl:apply-templates select="methods"/>
  </xsl:template>
  <!-- Required Node -->
  <xsl:template match="required">
    <font class="attrib">
      <xsl:value-of select="@message"/>
      <xsl:if test="position() &lt; last()">
        <xsl:text>, </xsl:text>
      </xsl:if>
    </font>
  </xsl:template>
  <!-- Comment node -->
  <xsl:template match="comment">
    <xsl:text>Comment: </xsl:text>
    <font class="comment">
      <xsl:value-of select="@value"/>
    </font>
  </xsl:template>
  <!-- Map Node -->
  <!-- maps can have properties, params, messages, maps and fields as children -->
  <xsl:template match="map">
    <p>
      <font class="tag">map</font>
      <xsl:if test=" string-length( @object ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> object: </xsl:text>
        </font>
        <xsl:element name="A">
          <xsl:attribute name="HREF">
            <xsl:value-of select="translate(@object,'.','/')"/>.html</xsl:attribute>
          <code>
            <xsl:value-of select="@object"/>
          </code>
        </xsl:element>
      </xsl:if>
      <xsl:if test=" string-length( @ref ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> reference: </xsl:text>
        </font>
        <code>$<xsl:value-of select="@ref"/>
        </code>
      </xsl:if>
      <xsl:if test=" string-length( @filter ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> filter: </xsl:text>
        </font>
        <code>
          <xsl:value-of select="@filter"/>
        </code>
      </xsl:if>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>
      <xsl:call-template name="fmtComment">
        <xsl:with-param name="c" select="@comment"/>
      </xsl:call-template>
      <xsl:if test=" count( ./* ) &gt; 0 ">
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
      <xsl:if test=" string-length( @name ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> name: </xsl:text>
        </font>
        <font class="value">
          <xsl:value-of select="@name"/>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @count ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> count: </xsl:text>
        </font>
        <font class="value">
          <xsl:value-of select="@count"/>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @mode ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> mode: </xsl:text>
        </font>
        <font class="value">
          <xsl:value-of select="@mode"/>
        </font>
      </xsl:if>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>
      <xsl:call-template name="fmtComment">
        <xsl:with-param name="c" select="@comment"/>
      </xsl:call-template>
      <xsl:if test=" count( ./* ) &gt; 0 ">
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
        <font class="tag">
          <xsl:text> parameter </xsl:text>
        </font>
      </xsl:if>
      <xsl:if test=" name( current() ) = 'property' ">
        <font class="tag">
          <xsl:text> property </xsl:text>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @name ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> name: </xsl:text>
        </font>
        <font class="value">
          <xsl:value-of select="@name"/>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @type ) &gt; 0 ">
        <font class="attrib">
          <xsl:text> type: </xsl:text>
        </font>
        <font class="value">
          <xsl:value-of select="@type"/>
        </font>
      </xsl:if>
      <xsl:call-template name="fmtComment">
        <xsl:with-param name="c" select="@comment"/>
      </xsl:call-template>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>
      <xsl:if test=" string-length( concat( @description, @value, @length, @cardinality, @direction ) ) &gt; 0 ">
        <xsl:if test=" string-length( @description ) &gt; 0 ">
          <font class="attrib">
            <xsl:text> description: </xsl:text>
          </font>
          <font class="value">
            <xsl:value-of select="@description"/>
          </font>
        </xsl:if>
        <xsl:if test=" string-length( @value ) &gt; 0 ">
          <font class="attrib">
            <xsl:text> value: </xsl:text>
          </font>
          <font class="value">
            <xsl:value-of select="@value"/>
          </font>
        </xsl:if>
        <xsl:if test=" string-length( @length ) &gt; 0 ">
          <font class="attrib">
            <xsl:text> length: </xsl:text>
          </font>
          <font class="value">
            <xsl:value-of select="@length"/>
          </font>
        </xsl:if>
        <xsl:if test=" string-length( @cardinality ) &gt; 0 ">
          <font class="attrib">
            <xsl:text> cardinality: </xsl:text>
          </font>
          <font class="value">
            <xsl:value-of select="@cardinality"/>
          </font>
        </xsl:if>
        <xsl:if test=" string-length( @direction ) &gt; 0 ">
          <font class="attrib">
            <xsl:text> direction: </xsl:text>
          </font>
          <font class="value">
            <xsl:value-of select="@direction"/>
          </font>
        </xsl:if>
      </xsl:if>
      <xsl:if test=" count( ./* ) &gt; 0 ">
        <xsl:if test=" count( ./option ) &gt; 0 ">
          <blockquote>
            <xsl:apply-templates select="option"/>
          </blockquote>
        </xsl:if>
        <xsl:apply-templates select="expression|map|comment"/>
      </xsl:if>
    </p>
  </xsl:template>
  <!-- Field Node -->
  <!-- fields can have maps and expressions for children -->
  <xsl:template match="field">
    <p>
      <xsl:variable name="name" select="@name"/>
      <font class="tag">field</font>
      <font class="attrib"> name: </font>
      <font class="value">
        <xsl:value-of select="@name"/>
      </font>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>
      <xsl:call-template name="fmtComment">
        <xsl:with-param name="c" select="@comment"/>
      </xsl:call-template>
      <xsl:if test=" count( ./* ) &gt; 0 ">
        <xsl:apply-templates select="expression|map|comment"/>
      </xsl:if>
    </p>
  </xsl:template>
  <!-- Expression Node -->
  <xsl:template match="expression">
    <xsl:choose>
      <xsl:when test="string-length ( parent::field/@condition ) &gt; 0 or string-length ( parent::property/@condition ) &gt; 0 or string-length( @condition ) &gt; 0 or string-length( preceding-sibling::expression/@condition ) &gt; 0 or string-length( @value ) &gt; 50 or string-length( preceding-sibling::expression/@value ) &gt; 50 ">
        <blockquote>
          <font class="tag">expression</font>
          <xsl:call-template name="fmtCondition">
            <xsl:with-param name="c" select="@condition"/>
          </xsl:call-template>
          <xsl:call-template name="fmtComment">
            <xsl:with-param name="c" select="@comment"/>
          </xsl:call-template>
          <xsl:if test=" string-length( @value ) &gt; 0 ">
            <font class="attrib"> value: </font>
            <code>
              <xsl:value-of select="@value"/>
            </code>
          </xsl:if>
          <xsl:if test=" string-length( current()/text() ) &gt; 0 ">
            <pre>
              <xsl:value-of select="current()/text()"/>
            </pre>
          </xsl:if>
          <xsl:if test="( string-length( @value ) = 0 ) and ( string-length( current()/text() ) = 0 )">
            <font class="attrib"> value: </font>
            <i>[empty]</i>
          </xsl:if>
        </blockquote>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="fmtCondition">
          <xsl:with-param name="c" select="@condition"/>
        </xsl:call-template>
        <xsl:call-template name="fmtComment">
          <xsl:with-param name="c" select="@comment"/>
        </xsl:call-template>
        <xsl:if test=" string-length( @value ) &gt; 0 ">
          <font class="attrib"> value: </font>
          <code>
            <xsl:value-of select="@value"/>
          </code>
        </xsl:if>
        <xsl:if test=" string-length( current()/text() ) &gt; 0 ">
          <pre>
            <xsl:value-of select="current()/text()"/>
          </pre>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <!-- Option Node -->
  <xsl:template match="option">
    <p>
      <font class="tag">option</font>
      <font class="attrib"> name: </font>
      <font class="value">
        <xsl:value-of select="@name"/>
      </font>
      <xsl:if test=" string-length( @value ) &gt; 0 ">
        <font class="attrib"> value: </font>
        <font class="value">
          <xsl:value-of select="@value"/>
        </font>
      </xsl:if>
      <xsl:if test="@selected = '1'">
        <font class="attrib">
          <xsl:text> selected</xsl:text>
        </font>
      </xsl:if>
    </p>
  </xsl:template>
  <!-- Comment attribute -->
  <xsl:template name="fmtComment">
    <xsl:param name="c"/>
    <xsl:if test=" string-length( $c ) &gt; 0 ">
      <xsl:text/>
      <font class="comment"> //<xsl:value-of select="$c"/>
      </font>
    </xsl:if>
  </xsl:template>
  <!-- Condition Attribute -->
  <xsl:template name="fmtCondition">
    <xsl:param name="c"/>
    <xsl:if test=" string-length( $c ) &gt; 0 ">
      <font class="attrib">
        <xsl:text> condition: </xsl:text>
      </font>
      <code>
        <xsl:value-of select="$c"/>
      </code>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
