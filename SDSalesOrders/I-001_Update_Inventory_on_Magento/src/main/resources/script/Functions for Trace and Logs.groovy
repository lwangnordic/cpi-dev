import java.util.HashMap; 
import java.time.LocalDateTime; 
import com.sap.it.api.ITApiFactory; 
import java.time.format.DateTimeFormatter; 
import com.sap.it.api.securestore.UserCredential; 
import com.sap.gateway.ip.core.customdev.util.Message;
import com.sap.it.api.securestore.SecureStoreService; 




def Message Payload_GotFrom_S4HANA(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    
    LocalDateTime now = LocalDateTime.now();     
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");          
    String formatDateTime = now.format(formatter);                 
    String Timestamp = "Inventory Got From S4Hana at TimeStamp " + formatDateTime + "  " + body;
    
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null)
    {                              
        messageLog.addAttachmentAsString("Payload_GotFrom_S4HANA", Timestamp, "text/xml");
    }                              
    
    return message;
}
 
 
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