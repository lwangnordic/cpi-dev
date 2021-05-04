import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {

       def body = message.getBody(java.lang.String);
       message.setBody(body.trim());

       return message;
}