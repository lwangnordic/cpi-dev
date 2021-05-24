import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;



def Message Replace_Tags(Message message) {
    //Body   
    def body = message.getBody(java.lang.String) as String;
    
    String newBody = body.replace ("<ORDERS05>", "<ORDERS05 encoding=\"UTF-8\">"); 
    
    body = newBody.replace ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""); 
  
    
    message.setBody(body);
       
    return message;
}

def Message Payload_Before_Mapping(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload_Before_Mapping", body, "text/xml");
    }                              
    
    return message;
}

def Message Payload_Before_BP(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload_Before_BP", body, "text/xml");
    }                              
    
    return message;
}

def Message ErrorOnBP(Message message) { //SaveLogErrorOnManagementBP.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("ErrorOnUpsertBP", body, "text/xml");
    }                              
                
    return message;
	
	
}

def Message ErrorOnCreateOrderProcess(Message message) { //SaveLogErrorMain.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("Error On Create Order Process", body, "text/xml");
    }                              
                
    return message;
}


def Message ErrorOnMainProcess(Message message) { //SaveLogErrorMain.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("ErrorOnMainProcess", body, "text/xml");
    }                              
                
    return message;
}

def Message ErrorOnGetFromMagento(Message message) {  //SaveLogErrOnMagento.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("ErrorOnGetFromMagento", body, "text/xml");
    }                              
                
    return message;
}