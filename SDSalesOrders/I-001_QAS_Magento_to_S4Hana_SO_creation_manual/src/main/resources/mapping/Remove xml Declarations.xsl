<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" omit-xml-declaration="yes" version="1.0" encoding="UTF-8"/>
<xsl:template match="*">
<xsl:element name="{local-name()}">
<xsl:apply-templates select="@* | node()"/>
</xsl:element>
</xsl:template>
</xsl:stylesheet>