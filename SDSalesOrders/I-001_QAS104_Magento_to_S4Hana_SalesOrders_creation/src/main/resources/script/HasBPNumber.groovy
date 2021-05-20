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
import com.sap.it.api.mapping.*;
import java.util.HashMap;

def Message processData(Message message) {
    
    String IsPro = "0"; //Default value
    
    //Body 
    def body = message.getBody(java.lang.String);
    def xml = new XmlSlurper().parseText(body);
  
    //Properties
    map   = message.getProperties();
    //value = map.get("oldProperty");
    
    
    //Get value for the ERP Customer number 
    value = xml.items.extension_attributes.erp_customer_number;
    
    //Check if the Order is for Patient/Pro
    if (xml.items.extension_attributes.order_type == "patient_order"){
        IsPro = "1";
        value = xml.items.extension_attributes.professional_sap_id; 
    }
        
    message.setProperty("IsPro", IsPro);
    message.setProperty("ERPCustNum", value); 
    return message;
}