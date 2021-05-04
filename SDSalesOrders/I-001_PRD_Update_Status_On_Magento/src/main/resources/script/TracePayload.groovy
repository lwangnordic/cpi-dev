import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message SaveLogErrorMain(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("OnMainUpdStatus", body, "text/xml");
    }                              
                
    return message;
}

def Message SaveLogErrOnMagento(Message message) {
                
	def body = message.getBody(java.lang.String) as String;
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null) 
	{                              
		messageLog.addAttachmentAsString("OnSendMagento", body, "text/xml");
    }                              
                
    return message;
}