<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="tml">
		<html>
			<head>
				<title>Dexels Server II - NavajoTester</title>
				<link href="/navajo/default.css" rel="stylesheet" type="text/css"/>
			</head>
			<body>
				<div id="wrapper">
					<form action="NavajoTester" method="POST">
						<table border="0" cellpadding="3" cellspacing="0" id="main">
							<xsl:apply-templates select="header"/>
							<xsl:apply-templates select="message">
								<xsl:with-param name="level" select="'0'"/>
							</xsl:apply-templates>
							<xsl:apply-templates select="methods"/>
						</table>
					</form>
				</div>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="header">
		<xsl:apply-templates select="callback/object"/>
	</xsl:template>
	<xsl:template match="object">
		<tr>
			<td colspan="2"> Mappable object (<xsl:value-of select="@name"/>), ref: <xsl:if test="@finished='false'">
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="name">header.callback.<xsl:value-of select="@name"/>
						</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="@ref"/>
						</xsl:attribute>
					</xsl:element>
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="name">header.callback.<xsl:value-of select="@name"/>.interrupt</xsl:attribute>
						<xsl:attribute name="value"/>
					</xsl:element>
				</xsl:if> (Finished: <xsl:value-of select="@finished"/>, <xsl:value-of select="@perc_ready"/>% finished) <xsl:if test="@finished='false'">
					<xsl:element name="input">
						<xsl:attribute name="type">submit</xsl:attribute>
						<xsl:attribute name="name">command</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="//header/transaction/@rpc_name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="methods">
		<tr>
			<td class="row_1" colspan="2" height="2" style="padding:0 0 0 0"/>
		</tr>
		<tr>
			<td class="msg_footer" colspan="2" height="1" style="padding:0 0 0 0;height:1px"/>
		</tr>
		<tr>
			<td class="row_1" colspan="2" height="2" style="padding:0 0 0 0"/>
		</tr>
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="method">
		<tr>
			<td align="center" class="msg_footer" colspan="2">
				<xsl:element name="input">
					<xsl:attribute name="type">submit</xsl:attribute>
					<xsl:attribute name="name">command</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="class">method</xsl:attribute>
				</xsl:element>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="message">
		<xsl:param name="level"/>
		<xsl:param name="msgName"/>
		<xsl:choose>
			<xsl:when test="parent::tml">
				<!-- if (the current node is directly beneath <tml>) -->
				<tr>
					<td align="center" class="msg_header1" colspan="2">
						<xsl:value-of select="@name"/>
					</td>
				</tr>
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
							<xsl:with-param name="msgName" select="@name"/>
							<xsl:with-param name="level" select="$level + 1"/>
							<xsl:with-param name="counter" select="'0'"/>
						</xsl:apply-templates>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates>
							<xsl:with-param name="msgName" select="@name"/>
							<xsl:with-param name="level" select="$level + 1"/>
							<xsl:with-param name="counter" select="'0'"/>
						</xsl:apply-templates>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<!-- else //the currentnode is NOT directly beneath <tml> -->
				<tr>
					<xsl:choose>
						<xsl:when test="../@type='array'">
							<xsl:element name="td">
								<xsl:attribute name="align">center</xsl:attribute>
								<xsl:attribute name="colspan">2</xsl:attribute>
								<xsl:attribute name="class">msg_header<xsl:value-of select="$level"/>
								</xsl:attribute>
								<xsl:value-of select="concat(@name, '@', @index)"/>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:element name="td">
								<xsl:attribute name="align">center</xsl:attribute>
								<xsl:attribute name="colspan">2</xsl:attribute>
								<xsl:attribute name="class">msg_header<xsl:value-of select="$level"/>
								</xsl:attribute>
								<xsl:value-of select="@name"/>
								<xsl:if test="@mode='lazy'"> (lazy total=<xsl:value-of select="@lazy_total"/>, current
										total=<xsl:value-of select="@array_size"/>, remaining=<xsl:value-of select="@lazy_remaining"/>)</xsl:if>
							</xsl:element>
						</xsl:otherwise>
					</xsl:choose>
				</tr>
				<xsl:choose>
					<xsl:when test="@type='array'">
						<xsl:apply-templates>
							<xsl:with-param name="msgName" select="$msgName"/>
							<xsl:with-param name="level" select="$level + 1"/>
							<xsl:with-param name="counter" select="'0'"/>
						</xsl:apply-templates>
					</xsl:when>
					<xsl:when test="child::property">
						<tr>
							<td colspan="2" style="border-width:0 0 0 0;padding:0 0 0 0" width="800">
								<table border="0" cellpadding="3" cellspacing="0" width="100%">
									<xsl:apply-templates>
										<xsl:with-param name="msgName">
											<xsl:value-of select="$msgName"/>/<xsl:choose>
												<xsl:when test="../@type='array'">
													<xsl:value-of select="concat(@name, '@', @index)"/>
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="@name"/>
												</xsl:otherwise>
											</xsl:choose>
										</xsl:with-param>
										<xsl:with-param name="level" select="$level + 1"/>
										<xsl:with-param name="counter" select="'0'"/>
									</xsl:apply-templates>
								</table>
							</td>
						</tr>
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
	</xsl:template>
	<xsl:template match="property">
		<xsl:param name="msgName"/>
		<xsl:param name="level"/>
		<xsl:param name="counter"/>
		<!-- @direction='out' to display as labels -->
		<xsl:if test="@direction='out' ">
			<xsl:choose>
				<xsl:when test="@type='selection'">
					<tr>
						<xsl:element name="td">
							<xsl:attribute name="width">250</xsl:attribute>
							<xsl:attribute name="class">row_<xsl:value-of select="$level"/>
							</xsl:attribute>
							<xsl:if test="not(@description)">
								<xsl:value-of select="@name"/>
							</xsl:if>
							<xsl:value-of select="@description"/>
							<small>
								<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> [<xsl:value-of select="@name"/>] </small>
						</xsl:element>
						<td class="row_1" width="550">
							<xsl:choose>
								<xsl:when test="@cardinality='1'">
									<xsl:element name="select">
										<xsl:attribute name="name">
											<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
										</xsl:attribute>
										<xsl:attribute name="disabled">
											<xsl:value-of select="'true'"/>
										</xsl:attribute>
										<xsl:for-each select="option">
											<xsl:if test="not (@name='')">
												<xsl:element name="option">
													<xsl:if test="@selected='0'">
														<xsl:attribute name="value">
															<xsl:value-of select="@name"/>
														</xsl:attribute>
														<xsl:value-of select="@name"/>
													</xsl:if>
													<xsl:if test="@selected='1'">
														<xsl:attribute name="selected"/>
														<xsl:attribute name="value">
															<xsl:value-of select="@name"/>
														</xsl:attribute>
														<xsl:value-of select="@name"/>
													</xsl:if>
												</xsl:element>
											</xsl:if>
										</xsl:for-each>
									</xsl:element>
								</xsl:when>
								<xsl:when test="@cardinality='*'"> Not implemented yet </xsl:when>
								<xsl:when test="@cardinality='+'">
									<table border="0" cellpadding="0" cellspacing="0">
										<xsl:apply-templates select="option">
											<xsl:with-param name="propertyName">
												<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
											</xsl:with-param>
											<xsl:with-param name="msgDirection">
												<xsl:value-of select="@direction"/>
											</xsl:with-param>
										</xsl:apply-templates>
									</table>
								</xsl:when>
							</xsl:choose>
						</td>
					</tr>
				</xsl:when>
				<xsl:otherwise>
					<tr>
						<xsl:element name="td">
							<xsl:attribute name="width">250</xsl:attribute>
							<xsl:attribute name="class">row_<xsl:value-of select="$level"/>
							</xsl:attribute>
							<xsl:if test="not(@description)">
								<xsl:value-of select="@name"/>
							</xsl:if>
							<xsl:value-of select="@description"/>
							<small>
								<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> [<xsl:value-of select="@name"/>] </small>
						</xsl:element>
						<td class="row_1" width="550">
							<b>
								<xsl:value-of select="@value"/>
							</b>
							<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
						</td>
					</tr>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<!--@direction='in' to display as input types -->
		<xsl:if test="@direction='in'">
			<tr>
				<xsl:element name="td">
					<xsl:attribute name="width">250</xsl:attribute>
					<xsl:attribute name="class">row_<xsl:value-of select="$level"/>
					</xsl:attribute>
					<xsl:if test="not(@description)">
						<xsl:value-of select="@name"/>
					</xsl:if>
					<xsl:value-of select="@description"/>
					<small>
						<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> [<xsl:value-of select="@name"/>] </small>
				</xsl:element>
				<xsl:choose>
					<xsl:when test="@type='selection'">
						<td class="row_1" width="550">
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
															<xsl:value-of select="@name"/>
														</xsl:attribute>
														<xsl:value-of select="@name"/>
													</xsl:if>
													<xsl:if test="@selected='1'">
														<xsl:attribute name="selected"/>
														<xsl:attribute name="value">
															<xsl:value-of select="@name"/>
														</xsl:attribute>
														<xsl:value-of select="@name"/>
													</xsl:if>
												</xsl:element>
											</xsl:if>
										</xsl:for-each>
									</xsl:element>
								</xsl:when>
								<xsl:when test="@cardinality='*'"> Not implemented yet </xsl:when>
								<xsl:when test="@cardinality='+'">
									<table border="0" cellpadding="0" cellspacing="0">
										<xsl:apply-templates select="option">
											<xsl:with-param name="propertyName">
												<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
											</xsl:with-param>
											<xsl:with-param name="msgDirection">
												<xsl:value-of select="@direction"/>
											</xsl:with-param>
										</xsl:apply-templates>
									</table>
								</xsl:when>
							</xsl:choose>
						</td>
					</xsl:when>
					<xsl:when test="@type='string' or @type='integer' or @type='money' or @type='clocktime' or @type='float' or @type='password'">
						<td class="row_1" width="550">
							<xsl:element name="input">
								<xsl:attribute name="type">
									<xsl:value-of select="@type"/>
								</xsl:attribute>
								<xsl:attribute name="name">
									<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
								</xsl:attribute>
								<xsl:if test="@length &gt;= 50">
									<xsl:attribute name="size">100</xsl:attribute>
									<xsl:attribute name="style">width:250px;</xsl:attribute>
								</xsl:if>
								<xsl:if test="@length &lt; 50">
									<xsl:attribute name="size">50</xsl:attribute>
									<xsl:attribute name="style">width:100px;</xsl:attribute>
								</xsl:if>
								<xsl:attribute name="maxlength">
									<xsl:value-of select="@length"/>
								</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="@value"/>
								</xsl:attribute>
							</xsl:element>
						</td>
					</xsl:when>
					<xsl:when test="@type='memo'">
						<td class="row_1">
							<xsl:element name="TEXTAREA">
								<xsl:attribute name="type">
									<xsl:value-of select="@type"/>
								</xsl:attribute>
								<xsl:attribute name="name">
									<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
								</xsl:attribute>
		<!-- show a text area of 64 columns and at most 6 rows -->
		<!-- for some reason the maximum number must be set at 5 -->
		<!-- to show 6(!) rows -->
								<xsl:attribute name="COLS">
									<xsl:value-of select="64"/>
								</xsl:attribute>
								<xsl:if test="( @length div 64 ) &lt; 6">
									<xsl:attribute name="ROWS">
										<xsl:value-of select="@length div 64"/>
									</xsl:attribute>
								</xsl:if>
								<xsl:if test="@length div 64 &gt;= 6">
									<xsl:attribute name="ROWS">
										<xsl:value-of select="5"/>
									</xsl:attribute>
								</xsl:if>
								<xsl:value-of select="@value"/>
							</xsl:element>
						</td>
					</xsl:when>
					<xsl:when test="@type='boolean'">
						<td class="row_1">
							<xsl:element name="input">
								<xsl:attribute name="type">checkbox</xsl:attribute>
								<xsl:attribute name="name">
									<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>
								</xsl:attribute>
								<xsl:if test="@value='true'">
									<xsl:attribute name="checked"/>
								</xsl:if>
							</xsl:element>
						</td>
					</xsl:when>
					<xsl:when test="@type='date'">
						<td class="row_1">
							<xsl:element name="input">
								<xsl:attribute name="type">text</xsl:attribute>
								<xsl:attribute name="name">
									<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>_DAY_</xsl:attribute>
								<xsl:attribute name="size">2</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="substring(@value,9,2)"/>
								</xsl:attribute>
							</xsl:element>
							<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
							<xsl:element name="input">
								<xsl:attribute name="type">text</xsl:attribute>
								<xsl:attribute name="name">
									<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>_MONTH_</xsl:attribute>
								<xsl:attribute name="size">2</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="substring(@value,6,2)"/>
								</xsl:attribute>
							</xsl:element>
							<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
							<xsl:element name="input">
								<xsl:attribute name="type">text</xsl:attribute>
								<xsl:attribute name="name">
									<xsl:value-of select="$msgName"/>/<xsl:value-of select="@name"/>_YEAR_</xsl:attribute>
								<xsl:attribute name="size">4</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="substring(@value,1,4)"/>
								</xsl:attribute>
							</xsl:element>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td class="row_1" width="250">
							<b>(<xsl:value-of select="@type"/>)</b> is not implemented yet <br/>
							<xsl:value-of select="@name"/> : </td>
						<td class="row_1">
							<xsl:value-of select="@value"/>
						</td>
					</xsl:otherwise>
				</xsl:choose>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template match="option">
		<xsl:param name="propertyName"/>
		<xsl:param name="msgDirection"/>
		<xsl:for-each select=".">
			<xsl:if test="not (@name='')">
				<tr>
					<td>
						<xsl:element name="input">
							<xsl:attribute name="type">checkbox</xsl:attribute>
							<xsl:attribute name="name">
								<xsl:value-of select="$propertyName"/>:<xsl:value-of select="@name"/>
							</xsl:attribute>
							<xsl:if test="$msgDirection='out'">
								<xsl:attribute name="disabled">
									<xsl:value-of select="'true'"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="@selected='1'">
								<xsl:attribute name="checked"/>
							</xsl:if>
						</xsl:element>
					</td>
					<td>
						<xsl:value-of select="@name"/>
					</td>
				</tr>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
<!-- EOF: $RCSfile$ -->
