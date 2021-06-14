import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message InboundPayload(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("InboundPayload", body, "text/xml");
    }                              
                
    return message;
}

def Message ErrorOnMainProcess(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("OnMainProcess", body, "text/xml");
    }                              
                
    return message;
}

def Message ErrorOnResponseZEDI(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("OnResponseZEDI", body, "text/xml");
    }                              
                
    return message;
}

def Message ErrorOn856Magento(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("On856Magento", body, "text/xml");
    }                              
                
    return message;
}


def Message ErrorOnSendMagento(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("OnSendMagento", body, "text/xml");
    }                              
                
    return message;
}



def Message ErrorOnZAC_STS(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("OnZAC_STS", body, "text/xml");
    }                              
                
    return message;
}

def Message Incoming_payload(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null)
    {                              
		messageLog.addAttachmentAsString("Payloadd", body, "text/plain");
    }                              
                
    return message;
}