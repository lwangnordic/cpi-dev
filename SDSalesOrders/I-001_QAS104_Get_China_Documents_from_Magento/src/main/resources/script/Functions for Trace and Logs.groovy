import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap; 


def Message ErrorOnSendMagento(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
     //Properties 
       map = message.getProperties();
       value = map.get("Order");
       String LogName = "OnSendMagento_Order_" + value;
    
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString(LogName, body, "text/xml");
    }                              
                
    return message;
}
 
    
def Message ErrorCreateUrlProcess(Message message) { //SaveLogErrorMain.groovy
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("ErrorOnCreateURLProcess", body, "text/xml");
    }                              
                
    return message;
}
 
 
def Message ReceivedEmptyURL(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    
     //Properties 
       map = message.getProperties();
       value = map.get("Order");
       String LogName = "ReceivedEmptyURL_Order_" + value;
    
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString(LogName, body, "text/xml");
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