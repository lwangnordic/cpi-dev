import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApiFactory; 
import com.sap.it.api.securestore.SecureStoreService; 
import com.sap.it.api.securestore.UserCredential; 


def Message SetToken(Message message) {
    
    
    //Body 
    def body = message.getBody(java.lang.String); 
        
    String valor = "Bearer " + body.replace("\"", ""); 
       
    message.setBody(valor);
   
 
    return message;
}



def Message GetEmailInfo(Message message) {
    
    map = message.getProperties();
    
    def body    = message.getBody(java.lang.String) as String; 
    def secureStorageService =  ITApiFactory.getService(SecureStoreService.class, null)
    
    
    //Magento email address To
    def emailTo_alias = message.getProperty("email_to")
        try{
            def sPemailTo = secureStorageService.getUserCredential(emailTo_alias)
            def emailTo   = sPemailTo.getPassword().toString()
            message.setHeader("M-server", emailTo)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    
    //Magento email address From
    def mToken_alias = message.getProperty("email_from")
        try{
            def sPmToken = secureStorageService.getUserCredential(mToken_alias)
            def mToken   = sPmToken.getPassword().toString()
            message.setHeader("M-token", mToken)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    //Magento email address CC
      def mGOrders_alias = message.getProperty("email_CC")
        try{
            def sPmGOrders = secureStorageService.getUserCredential(mGOrders_alias)
            def mGOrders   = sPmGOrders.getPassword().toString()
            message.setHeader("M-gOrders", mGOrders)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
 
    return message;
}  


def Message GetQuery(Message message) {
    
    //Body 
    def order = message.getBody(java.lang.String);
     
    String search = "searchCriteria[filterGroups][1][filters][0][field]=entity_id" +
                 "&searchCriteria[filterGroups][1][filters][0][value]=" + order +
                 "&searchCriteria[filterGroups][1][filters][0][conditionType]=eq";
     
     message.setProperty("Query", search);
     
     
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