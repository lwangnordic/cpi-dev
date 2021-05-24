import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message BuiltUrl(Message message) { 
       
    //Property QUERY
    map = message.getProperties();
    MATNR = map.get("MATNR");
    value = map.get("URL");
    
    String prefix ="https://china-kdabkla-hraqe4drzlnyk.us.magentosite.cloud/consumers/rest/all/V1/products/";
    String suffix = "/stockItems/1";
        
        
    message.setProperty("URL", prefix + MATNR + suffix);
  
       
       
    return message;
}

def Message SetToken(Message message) { 
    
    //Body 
    def body = message.getBody(java.lang.String); 
        
    String valor = "Bearer " + body.replace("\"", ""); 
       
    message.setBody(valor);
    
    return message;
}


def Message completeCallFunction(Message message) { 
    
    //Body 
    def body = message.getBody(java.lang.String); 
        
    String valor = body.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""); 
    
    body = valor.replace("<?xml version='1.0' encoding='UTF-8'?>", "");  
       
    message.setBody(body);
    
    return message;
}