import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message Save_Payload(Message message) {
    
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message); 
    
    def map = message.getProperties();
    String payload = map.get("SPS_file");
       
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Payload SPS Process", payload, "text/xml");
    }                              
    
    return message;
}

def Message Log_SoldTo(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("Log On Sold to Process", body, "text/xml");
    }                              
                
    return message;
} 

def Message ErrorOnMainProcess(Message message) { 
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("ErrorOnMainProcess", body, "text/xml");
    }                              
                
    return message; 
}