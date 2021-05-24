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
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPathConstants;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPathConstants;

def Message initLog(Message message) {
    def map =  message.getProperties();
    // Logging
	java.lang.String logger = map.get("logFile");
	if (logger == null){
        logger = new String();
	}
 	logger += "The material not update on Magento: " + System.lineSeparator();
	// Store the logfile as property on the message object
	message.setProperty("logFile",logger);
	return message;
}

def Message logInfo(Message message) { 
    
    def body = message.getBody(java.lang.String) as String;  
    
    
    //Properties 
    def map =  message.getProperties();
    java.lang.String logger = map.get("logFile");
    if (logger == null){
       logger = new String();
    }
    logger += body + System.lineSeparator();
    // Store the logfile as property on the message object
    message.setProperty("logFile",logger);       
    
       
   // def Properties =  message.getProperties();
    // Logging 
   // java.lang.String logger = Properties.get("logFile");
   // if (logger == null){
   //    logger = new String();
   // }
    
    /*// Gather the information to write into logfile
    // In this case: summary of payload from Odata	
    def xpath = XPathFactory.newInstance().newXPath();
    def builder     = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    def inputStream = new ByteArrayInputStream(body.getBytes("UTF-8") );
    def doc     = builder.parse(inputStream);
    
   String material = Properties.get("MATNR");  */
  /*  NodeList nodeList = (NodeList) xpath.compile("//materialnumber").evaluate(doc, XPathConstants.NODESET);
  
    String mat_list = new String();
    def length = nodeList.getLength();
    for (int i = 0; i < length; i++) {
                mat_list += nodeList.item(i).getTextContent() + ",";
            }*/
  
   // logger += "Material " + material + " Not found on Magento"+ System.lineSeparator();
   
   // logger += body + System.lineSeparator();
   
    // Store the logfile as property on the message object
    
    
  // message.setProperty("logFile",logger);    
    return message;
}

/**
 * Stores the logFile as attachment of the message processing
 * It can be found in the message processing dashboard in SCI Message Monitoring
**/
def Message storeLog(Message message) {
    //Properties 
    def map =  message.getProperties();
    java.lang.String logger = map.get("logFile");
    if (logger == null){
       logger = new String();
    }
    logger += "END Import process " + System.lineSeparator();
    // Store the logfile as property on the message object
    message.setProperty("logFile",logger);       
    // Store LOG file on MSGLog 
	def messageLog = messageLogFactory.getMessageLog(message);
	if (messageLog != null) {
        messageLog.addAttachmentAsString("logFile " , logger, "text/plain");
	}
    return message;
}

def Message logException(Message message) {
       //Properties 
       def map =  message.getProperties();
       java.lang.String logger = map.get("logFile");
       if (logger == null){
           logger = new String();
       }
       logger += "EXCEPTION occured in processing Import " + System.lineSeparator()  ;
       // Store the logfile as property on the message object
       message.setProperty("logFile",logger);
       // Call methode to store Log
       storeLog(message);
}