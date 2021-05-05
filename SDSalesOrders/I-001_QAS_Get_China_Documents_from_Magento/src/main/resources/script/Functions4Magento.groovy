import com.sap.gateway.ip.core.customdev.util.Message;
import com.sap.it.api.securestore.SecureStoreService; 
import com.sap.it.api.securestore.UserCredential; 
import java.time.format.DateTimeFormatter; 
import com.sap.it.api.ITApiFactory; 
import java.time.LocalDateTime; 
import java.util.HashMap;




def Message SetToken(Message message) { 
    //Body 
    def body = message.getBody(java.lang.String) as String; 
   
    String valor = "Bearer " + body.replace("\"", ""); 
       
    message.setBody(valor);
   
 
    return message; 
   
}


def Message GetCredentials(Message message) {
    
    map = message.getProperties();
    String Credentials_name = map.get("Credentials4Magento");
    
    def body = message.getBody(java.lang.String) as String;
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

def Message GetQuery(Message message) {
    
     
   
    //Property QUERY
    map = message.getProperties();
    value = map.get("Query"); 
    
    String prefix = "searchCriteria[filterGroups][0][filters][0][field]=xtuple_order_number" +
                    "&searchCriteria[filterGroups][0][filters][0][conditionType]=null"       +
                    
                    "&searchCriteria[filterGroups][1][filters][0][field]=status" + 
                    "&searchCriteria[filterGroups][1][filters][0][value]=processing,pending" +
                    "&searchCriteria[filterGroups][1][filters][0][conditionType]=in" +
                    
                    "&searchCriteria[filterGroups][2][filters][0][field]=created_at" + 
                    "&searchCriteria[filterGroups][2][filters][0][value]='";
                    
    String suffix = "&searchCriteria[filterGroups][2][filters][0][conditionType]=gt" +
    
                    "&searchCriteria[filterGroups][3][filters][0][field]=base_total_due" +
                    "&searchCriteria[filterGroups][3][filters][0][value]=0" +
                    "&searchCriteria[filterGroups][3][filters][0][conditionType]=eq" 
   
    LocalDateTime now = LocalDateTime.now();    
    LocalDateTime date2qry = now.minusDays(30); 
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");          
    String formatDateTime = date2qry.format(formatter);                
       
       
     message.setProperty("Query", prefix + formatDateTime + suffix);
     
    return message;
}


def Message GetManualQuery(Message message) {
    
    //Body 
    def order = message.getBody(java.lang.String);
     
    String search = "searchCriteria[filterGroups][1][filters][0][field]=entity_id" +
                    "&searchCriteria[filterGroups][1][filters][0][value]=" + order +
                    "&searchCriteria[filterGroups][1][filters][0][conditionType]=eq";
     
     message.setProperty("Query", search);
     
     
    return message;
}


