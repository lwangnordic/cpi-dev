import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message SetToken(Message message) { 
    
    //Body 
    def body = message.getBody(java.lang.String); 
        
    String valor = "Bearer " + body.replace("\"", ""); 
       
    message.setBody(valor);
    
    return message;
}