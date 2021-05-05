<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"> 
	
    <!-- Copy everything -->
    <xsl:template match="node() | @*">
        <xsl:copy>
            <xsl:apply-templates select="node() | @*"/>
        </xsl:copy>
    </xsl:template>
	
    <!-- Elements with price = 0 need to be filtered and do not copy them to the output -->
    <!-- <xsl:template match="items/items[(price/text() = '0')]"/> -->
    
    <xsl:template match="items/items[(parent_item_id/text() > '0')]"/>
     
</xsl:stylesheet>