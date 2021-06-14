import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApiFactory; 
import com.sap.it.api.securestore.SecureStoreService; 
import com.sap.it.api.securestore.UserCredential; 


def Message SetToken(Message message) {
    
    
    //Body 
    //def body = message.getBody(java.lang.String); 
    def body = message.getBody(java.io.Reader);
        
    String valor = "Bearer " + body.replace("\"", ""); 
       
    message.setBody(valor);
   
 
    return message;
}

def Message GetQuery(Message message) {
    
    //Body 
    //def order = message.getBody(java.lang.String);
    def order = message.getBody(java.io.Reader);
     
    String search = "searchCriteria[filterGroups][1][filters][0][field]=entity_id" +
                    "&searchCriteria[filterGroups][1][filters][0][value]=" + order +
                    "&searchCriteria[filterGroups][1][filters][0][conditionType]=eq";
     
     message.setProperty("Query", search);
     
     
    return message;
}

def Message GetCredentials(Message message) {
    
    map = message.getProperties();
    String Credentials_name = map.get("Credentials4Magento");
    
   // def body = message.getBody(java.lang.String) as String;
    def body = message.getBody(java.io.Reader);
    
    def service = ITApiFactory.getApi(SecureStoreService.class, null);  
    def credential = service.getUserCredential(Credentials_name);
    if (credential == null){
        throw new IllegalStateException("No credential found for alias '" + Credentials_name + "'"); 
        
    } 
    String user     = credential.getUsername(); 
    String password = new String(credential.getPassword());
    
        
    String result = "{ \"username\": \"" + user + "\",  \"password\": \"" + password + "\" }"; 
       
    message.setBody(result);
       
    return message;
}  