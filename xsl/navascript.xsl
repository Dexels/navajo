<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Id$ -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- TSL header -->
  <xsl:template match="tsl">
    <div class="saPanel">
    <xsl:variable name="id" select="@id"/>
    <xsl:if test=" string-length($id) &gt; 0 ">
      <div class="saHeader"><xsl:value-of select="@id"/></div>
    </xsl:if>
    <xsl:if test=" name( current() ) = 'tsl'">
      <table class="saTable" cellpadding="3" cellspacing="0">
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
  </div>
  <br/>
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
    <font class="comment">
      <xsl:value-of select="concat('//', @value)"/>
    </font>
    <xsl:element name="br"/>
  </xsl:template>
  <!-- Map Node -->
  <!-- maps can have properties, params, messages, maps and fields as children -->
  <xsl:template match="map">
    <div class="saMap">
      <xsl:if test="string-length( @condition ) &gt; 0">
        <xsl:call-template name="fmtCondition">
          <xsl:with-param name="c" select="@condition"/>
        </xsl:call-template>
        <font class="condition">
           <xsl:text> then </xsl:text>
        </font>
      </xsl:if>
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
      <xsl:if test=" count( ./* ) &gt; 0 ">
        <blockquote>
          <xsl:apply-templates select="field|message|property|param|map|include"/>
        </blockquote>
      </xsl:if>
    </div>
  </xsl:template>
  <!-- Message Node -->
  <!-- messages can have objects, maps, properties, params, and other messages as children -->
  <xsl:template match="message">
    <div class="saMessage">
      <xsl:if test="string-length( @condition ) &gt; 0">
        <xsl:call-template name="fmtCondition">
          <xsl:with-param name="c" select="@condition"/>
        </xsl:call-template>
        <font class="condition">
           <xsl:text> then </xsl:text>
        </font>
      </xsl:if>
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
      <xsl:if test=" count( ./* ) &gt; 0 ">
        <blockquote>
          <xsl:apply-templates select="property|param|message|map|include"/>
        </blockquote>
      </xsl:if>
    </div>
  </xsl:template>
  <!-- Parameter or Property Node -->
  <!-- properties can have objects and expressions as children -->
  <xsl:template match="property|param">
    <p>
      <xsl:if test="string-length( @condition ) &gt; 0">
        <xsl:call-template name="fmtCondition">
          <xsl:with-param name="c" select="@condition"/>
        </xsl:call-template>
        <font class="condition">
           <xsl:text> then </xsl:text>
        </font>
      </xsl:if>
      <xsl:if test=" name( current() ) = 'param' ">
        <font class="tag">
          <xsl:text>@</xsl:text>
        </font>
      </xsl:if>
      <xsl:if test=" name( current() ) = 'property' ">
        <font class="tag">
          <xsl:text> property </xsl:text>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @name ) &gt; 0 ">
        <font class="value">
          <xsl:value-of select="@name"/>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @type ) &gt; 0 ">
        <font class="value">
          <xsl:value-of select="concat(' (', @type)"/>
        </font>
      </xsl:if>
      <xsl:if test=" string-length( @direction ) &gt; 0 ">
          <font class="value">
            <xsl:value-of select="concat(' ', @direction)"/>
          </font>
      </xsl:if>
      <xsl:if test=" string-length( @cardinality ) &gt; 0 ">
          <font class="value">
            <xsl:value-of select="concat('{', @cardinality, '}')"/>
          </font>
      </xsl:if>
      <xsl:if test=" string-length( concat( @direction, @type, @cardinality ) ) &gt; 0 ">
          <font class="value">
            <xsl:text>)</xsl:text>
          </font>
      </xsl:if>
      <xsl:if test=" string-length( @value ) &gt; 0 ">
          <font class="attrib">
            <xsl:text> = </xsl:text>
          </font>
          <font class="value">
            <xsl:text disable-output-escaping="yes">&apos;</xsl:text>
            <xsl:value-of select="@value"/>
            <xsl:text disable-output-escaping="yes">&apos;</xsl:text>
          </font>
      </xsl:if>
      <xsl:if test=" count( ./* ) = 0 ">
        <xsl:if test=" string-length( @description ) &gt; 0 ">
          <font class="comment">
            <xsl:value-of select="concat(' // ', @description)"/>
          </font>
        </xsl:if>
      </xsl:if>
      <xsl:if test=" count( ./* ) &gt; 0 ">
        <xsl:if test=" count( ./option ) &gt; 0 ">
          <blockquote>
            <xsl:apply-templates select="option"/>
          </blockquote>
        </xsl:if>
        <font class="attrib"> = </font>
        <xsl:apply-templates select="expression|map"/>
      </xsl:if>
    </p>
  </xsl:template>
  <!-- Field Node -->
  <!-- fields can have maps and expressions for children -->
  <xsl:template match="field">
    <p>
      <xsl:variable name="name" select="@name"/>
      <font class="tag">$</font>
      <font class="value">
        <xsl:value-of select="@name"/>
      </font>
      <xsl:call-template name="fmtCondition">
        <xsl:with-param name="c" select="@condition"/>
      </xsl:call-template>
      <xsl:if test=" count( ./* ) &gt; 0 ">
        <font class="attrib"> = </font>
        <xsl:apply-templates select="expression|map"/>
      </xsl:if>
    </p>
  </xsl:template>
  <!-- Expression Node -->
  <xsl:template match="expression">
    <xsl:choose>
      <xsl:when test="string-length ( parent::field/@condition ) or (string-length ( parent::property/@condition ) &gt; 0 and count(following-sibling::expression) &gt; 0) or string-length( @condition ) &gt; 0 or string-length( preceding-sibling::expression/@condition ) &gt; 0 or string-length( @value ) &gt; 50 or string-length( preceding-sibling::expression/@value ) &gt; 50 ">
        <xsl:if test=" string-length( parent::*/@description ) &gt; 0 ">
          <font class="comment">
            <xsl:value-of select="concat(' // ', parent::*/@description)"/>
          </font>
        </xsl:if>
        <blockquote>
          <xsl:call-template name="fmtCondition">
            <xsl:with-param name="c" select="@condition"/>
          </xsl:call-template>
          <xsl:if test=" string-length( @value ) &gt; 0 ">
            <xsl:if test="count( following-sibling::expression ) = 0 and count ( preceding-sibling::expression ) = 0 and string-length ( @condition) = 0">
                <font class="attrib"></font>
            </xsl:if>
            <xsl:if test="count( following-sibling::expression ) = 0 and count ( preceding-sibling::expression ) = 0 and string-length ( @condition) &gt; 0">
                <font class="condition"> then </font>
            </xsl:if>
            <xsl:if test="count( following-sibling::expression ) = 0 and count ( preceding-sibling::expression ) &gt; 0 ">
                <font class="condition"> else </font>
            </xsl:if>
            <xsl:if test="count( following-sibling::expression ) &gt; 0 ">
                <font class="condition"> then </font>
            </xsl:if>
            <xsl:if test="starts-with(@value, '&quot;')">
              <code style="color:purple">
                 <xsl:value-of select="@value"/>
              </code>
            </xsl:if>
            <xsl:if test="not(starts-with(@value, '&quot;'))">
              <code>
                 <xsl:value-of select="@value"/>
              </code>
            </xsl:if>
          </xsl:if>
          <xsl:if test=" string-length( current()/text() ) &gt; 0 ">
            <pre>
              <xsl:value-of select="current()/text()"/>
            </pre>
          </xsl:if>
          <xsl:if test="( string-length( @value ) = 0 ) and ( string-length( current()/text() ) = 0 )">
            <i>[empty]</i>
          </xsl:if>
        </blockquote>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="fmtCondition">
          <xsl:with-param name="c" select="@condition"/>
        </xsl:call-template>
        <xsl:if test=" string-length( @value ) &gt; 0 ">
            <xsl:if test="starts-with(@value, '&quot;')">
              <code style="color:purple">
                 <xsl:value-of select="@value"/>
              </code>
            </xsl:if>
            <xsl:if test="not(starts-with(@value, '&quot;'))">
              <code>
                 <xsl:value-of select="@value"/>
              </code>
            </xsl:if>
        </xsl:if>
        <xsl:if test=" string-length( current()/text() ) &gt; 0 ">
          <pre>
            <xsl:value-of select="current()/text()"/>
          </pre>
        </xsl:if>
        <xsl:if test=" string-length( parent::*/@description ) &gt; 0 ">
          <font class="comment">
            <xsl:value-of select="concat(' // ', parent::*/@description)"/>
          </font>
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
      <font class="condition">
        <xsl:text> if </xsl:text>
      </font>
      <code>
        <xsl:value-of select="$c"/>
      </code>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
