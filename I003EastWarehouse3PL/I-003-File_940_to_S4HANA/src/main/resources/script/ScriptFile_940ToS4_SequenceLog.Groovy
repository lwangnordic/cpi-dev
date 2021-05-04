import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message processData(Message message) {
                
                                def body = message.getBody();
                                def map = message.getProperties();
                                def EDI = map.get("EDI");
                                def ProcessFolder = map.get("ProcessFolder");
                                def messageLog = messageLogFactory.getMessageLog(message);
                                if(messageLog != null)
                                {                              
									messageLog.addAttachmentAsString("Individual file to process", "<FILE><EDI>"+EDI+"</EDI><ProcessFolder>"+ProcessFolder+"</ProcessFolder></FILE>", "text/xml");
                                }                              
                
                return message;
}