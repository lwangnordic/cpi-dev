<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hci="http://sap.com/it/" exclude-result-prefixes="hci">
<xsl:output method="xml" omit-xml-declaration="yes" />
<xsl:template match="/">
ISA*<xsl:value-of select="/ediroot/interchange/@AuthorizationQual"/>*<xsl:value-of select="/ediroot/interchange/@Authorization"/>*<xsl:value-of select="/ediroot/interchange/@SecurityQual"/>*<xsl:value-of select="/ediroot/interchange/@Security"/>*<xsl:value-of select="/ediroot/interchange/receiver/address/@Qual"/>*<xsl:value-of select="/ediroot/interchange/receiver/address/@Id"/>*<xsl:value-of select="/ediroot/interchange/sender/address/@Qual"/>*<xsl:value-of select="/ediroot/interchange/sender/address/@Id"/>*<xsl:value-of select="/ediroot/interchange/@Date"/>*<xsl:value-of select="/ediroot/interchange/@Time"/>*<xsl:value-of select="/ediroot/interchange/@StandardsId"/>*<xsl:value-of select="/ediroot/interchange/@Version"/>*<xsl:value-of select="/ediroot/interchange/@Control"/><!-- need param-->*<xsl:value-of select="/ediroot/interchange/@AckRequest"/>*<xsl:value-of select="/ediroot/interchange/@TestIndicator"/>*<xsl:text disable-output-escaping="yes">&gt;</xsl:text>~
<xsl:for-each select="/ediroot/interchange/group">GS*PR*3173515333*<xsl:value-of select="@ApplSender"/>*<xsl:value-of select="@Date"/>*<xsl:value-of select="@Time"/>*<xsl:value-of select="@Control"/><!-- need param-->*X*004010UCS~
<xsl:for-each select="transaction">ST*855*<xsl:value-of select="format-number(position(), '0000')" />~
BAK*00*AD*<xsl:value-of select="./segment[@Id='W05']/element[@Id='W0502']" />*<xsl:value-of select="format-dateTime(current-dateTime(),'[Y0001][M01][D01]')"/>~
<xsl:for-each select="/ediroot/interchange/group/transaction/segment[@Id='LX']">PO1*<xsl:value-of select="position()" />*<xsl:value-of select="following-sibling::segment/element[@Id='W0101']" />*<xsl:value-of select="following-sibling::segment/element[@Id='W0102']" />***<xsl:value-of select="following-sibling::segment/element[@Id='W0104']" />*<xsl:value-of select="following-sibling::segment/element[@Id='W0105']" />~
ACK*IA***201~
ACK*IA***911~
ACK*IA***19~
CTT*<xsl:value-of select="position()" />~
</xsl:for-each>SE*<xsl:value-of select="count(segment[@Id='LX'])*5+1" />*<xsl:value-of select="format-number(position(), '0000')" />~</xsl:for-each>
GE*<xsl:value-of select="count(transaction)" />*<xsl:value-of select="@Control"/><!-- need param-->~</xsl:for-each>
IEA*<xsl:value-of select="count(/ediroot/interchange/group)" />*<xsl:value-of select="/ediroot/interchange/@Control"/><!-- need param-->~
</xsl:template>
</xsl:stylesheet>