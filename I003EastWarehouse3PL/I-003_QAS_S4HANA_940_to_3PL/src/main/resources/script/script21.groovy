/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def String getPriceUSD(String Attributes,     String textSku, 
					   String store_id,       String orderType = "", 
					   String base_row_total = "0", String qty_ordered = "0",
					   String item_id = "0"){
    
    int cont  = 0;
    int index = 0;
  
	double dResult = 0;
	double dRowTotal = base_row_total as double;
	double dQuantity = qty_ordered as double;
    String out = "";
    
    //Convert string to XML //it must be send like a XML
    def xml = new XmlSlurper().parseText(Attributes) 
 
	if (orderType == "patient_order"){
		
	/*	It was changed to looking by ItemId 2020.09.08 JRIVERA
	    //Looking for position of the actual SKU 
		for (sku in xml.patient_order_items_sku) {
			if(sku == textSku)
				index = cont; 
			
			cont = cont + 1;
		}*/
			
		int iItemId = item_id as int;      // Cast to integer
		iItemId++;                         // Add 1 number by Parent Item
        String Temp_id = iItemId;
		
		//Looking for position of the actual SKU by ItemId 
		for (items_item_id in xml.patient_order_items_item_id) {
			if(items_item_id == Temp_id)
				index = cont; 
			
			cont = cont + 1;
		}
		//Get the Price by position
		out = xml.patient_order_items_price_usd[index];
		  
	}
	else{
		  
		if (store_id == '7'){
		  
			//Looking for position of the actual SKU 
			for (sku in xml.china_order_items_sku) {
				if(sku == textSku) 
					index = cont;  
				
				cont = cont + 1;
			}
			//Get the Price by position
			out =  xml.china_order_items_price_usd[index];
		}
		else{
			if(qty_ordered != 0) 
				dResult = dRowTotal / dQuantity;
			
		////	out = dResult.round(2);
		//	out = dResult.round(3);   // Comentarized, no rounding
		}
		  
	}
	
    return out;

}