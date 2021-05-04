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
import com.sap.it.api.mapping.*;

def Message processData(Message message) {
    //Body 
       def body = message.getBody();
       message.setBody(body + "Body is modified");
       //Headers 
       def map = message.getHeaders();
       def value = map.get("oldHeader");
       message.setHeader("oldHeader", value + "modified");
       message.setHeader("newHeader", "newHeader");
       //Properties 
       map = message.getProperties();
       value = map.get("oldProperty");
       message.setProperty("oldProperty", value + "modified");
       message.setProperty("newProperty", "newProperty");
       return message;
}

def Message ObtainToken(Message message) {
    

    def body = message.getBody(java.lang.String) as String;
    
    int beginS = ( body.indexOf("token\":\"") + 8);
    int endS   = body.indexOf("\",\"token_type");  

    String newBody =  "Bearer " + body.substring(beginS,endS); 
    
    message.setBody(newBody);
       
    return message;
}

def Message replaceInputFields(Message message) {
    

    def body = message.getBody(java.lang.String) as String;
  /* int beginS = body.indexOf("<cards>");
    int endS = (body.indexOf("</id></" )) + 5;  

    String newBody = body.substring(beginS,endS);
    body = newBody.replace("2600", "CC");
    newBody = body.replace("3520", "CC");
    message.setBody("<root>" + newBody + "</root>");*/
    
      
  /* String newBody = body.replaceAll(/<([0-9]+)([^<     ]+)/, "<numeric>"); 
           body    = newBody.replaceAll(/<\/([0-9]+)([^<     ]+)/, "</numeric>");
    message.setBody(body); */
       
    // Old Customer Id
    String newBody = body.replaceAll(/<([0-9]+)([^<     ]+)<cards>/, "<OLD_CUSTOMER_ID><cards>"); 
    body    = newBody.replaceAll(/<\/id><\/([0-9]+)([^<     ]+)/, "</id></OLD_CUSTOMER_ID>");  
    
    
    // Old Card Id
    newBody = body.replaceAll(/<([0-9]+)([^<     ]+)<id>/, "<OLD_CARD_ID><id>"); 
    body    = newBody.replaceAll(/<\/brand><\/([0-9]+)([^<     ]+)/, "</brand></OLD_CARD_ID>"); 
    message.setBody(body);
    return message;
}

def Message replaceOutputFields(Message message) {
    

    def body = message.getBody(java.lang.String) as String;
    
     
   /*   String newBody  = body.replace("\"root\":{", "");
        body     = newBody.replace("}}", "}");
        message.setBody(body);*/
           
    String newBody  = body.replace("\"root\":{", "");
    body    = newBody.replace("}}}", "}]}");
    newBody = body.replace("\"PaymentCards\":{", "\"PaymentCards\":[{");
    
    message.setBody(body);
       
    return message;
}


def Message Payload_Mapping(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload_Mapping", body, "text/xml");
    }                              
    
    return message;
}

def Message Payload_Main(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload_Main_Process", body, "text/xml");
    }                              
    
    return message;
}

def Message Payload_Send2DPA(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload_Send_to_DPA", body, "text/xml");
    }                              
    
    return message;
}

def Message Payload_CreateCSVFile(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload_Create_CSV_file", body, "text/xml");
    }                              
    
    return message;
}


///Temporal

def Message Payload_test(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Before_DPA", body, "text/xml");
    }                              
    
    return message;
}
