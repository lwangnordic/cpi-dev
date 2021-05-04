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

def Message ReplaceOnResponse(Message message) {
    //Body 
    def body = message.getBody(java.lang.String) as String;
    
    String newBody = body.replace ("<br>", ""); 
       message.setBody(newBody);
       
       return message;
}

def Message Replace_Tags(Message message) {
    //Body   
    def body = message.getBody(java.lang.String) as String;
    
    String newBody = body.replace ("<ORDERS05>", "<ORDERS05 encoding=\"UTF-8\">"); 
    
    body = newBody.replace ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""); 
  
    
       message.setBody(body);
       
       return message;
}