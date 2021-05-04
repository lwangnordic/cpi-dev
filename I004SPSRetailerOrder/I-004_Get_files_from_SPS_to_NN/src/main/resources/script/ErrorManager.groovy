import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message Error_main(Message message) {
                
    def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null)
    {                              
    	messageLog.addAttachmentAsString("Error Main Process", body, "text/plain");
    }                              
                
    return message;
}

def Message Incoming_payload(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null)
    {                              
		messageLog.addAttachmentAsString("Incoming payload", body, "text/plain");
    }                              
                
    return message;
}