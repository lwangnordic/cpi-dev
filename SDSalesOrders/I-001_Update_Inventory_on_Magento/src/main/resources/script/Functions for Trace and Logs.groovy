import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message SaveLogAttachment(Message message) {
     
    //Body 
       def body = message.getBody(java.lang.String);  
       //Properties 
       map = message.getProperties();
       value = map.get("Urls_not_found"); 
       String newValue = value + body;
       
       
       
       
       message.setProperty("Urls_not_found", newValue);
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
 
def Message LogCreateUrl(Message message) { //SaveLogErrorOnManagementBP.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("Error on create URL Process", body, "text/xml");
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

def Message ErrorOnSendToMagento(Message message) {  //SaveLogErrOnMagento.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("Log on Send to Magento", body, "text/xml");
    }                              
                
    return message;
}

def Message PrintUrlNotFound(Message message) {  //SaveLogErrOnMagento.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("Material no found on Magento", body, "text/xml");
    }                              
                
    return message;
}