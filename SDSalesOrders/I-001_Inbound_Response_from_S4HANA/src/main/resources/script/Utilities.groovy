import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message SetMagentoToken(Message message) {
    //Body 
    def body = message.getBody(java.lang.String); 
        
    String valor = "Bearer " + body.replace("\"", ""); 
       
    message.setBody(valor);
    return message;
}


def Message CompleteJSonFile(Message message) {
       
    //Body 
       def body = message.getBody(java.lang.String); 
       
    String newBody = body.replace ("\"element\":", "");

    String othBody = newBody.replace ("}}}", "}]}");

    newBody = othBody.replace ("},\"notify", "],\"notify");

    othBody= newBody.replace ("items\":{", "items\":[");

    body = othBody.replace ("tracks\":{", "tracks\":[");
    
    message.setBody(body);
    
    return message;
}

def Message DeleteLeftZeros(Message message) {
    
    String value = "";
    
    //Body 
    def xml = new XmlSlurper().parseText(message.getBody(java.lang.String));
        
        //Looking for the Order
     for (E1EDL41 in xml.DELVRY07.IDOC.E1EDL20.E1EDL24.E1EDL41) {
        if(E1EDL41.QUALI == "001"){
            value = E1EDL41.BSTNR; 
            break;
        }
      }
      
    String newBody = value.replaceFirst ("^0*", "");
       
    message.setBody(newBody);
    return message;
}