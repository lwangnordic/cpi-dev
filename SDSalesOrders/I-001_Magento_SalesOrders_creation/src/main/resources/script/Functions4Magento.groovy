import java.util.HashMap; 
import java.time.LocalDateTime; 
import com.sap.it.api.ITApiFactory; 
import java.time.format.DateTimeFormatter; 
import com.sap.it.api.mapping.ValueMappingApi 
import com.sap.it.api.securestore.UserCredential; 
import com.sap.it.api.securestore.SecureStoreService;
import com.sap.gateway.ip.core.customdev.util.Message; 
import com.sap.it.api.securestore.exception.SecureStoreException

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


def Message GetCredentials(Message message) {
    
    def head = message.getHeaders();
      
    map = message.getProperties();
    
    def body    = message.getBody(java.lang.String) as String; 
    def secureStorageService =  ITApiFactory.getService(SecureStoreService.class, null)
    
    
    //Magento server
    def mServer_alias = head.get("MAG_Server")
        try{
            def sPmServer = secureStorageService.getUserCredential(mServer_alias)
            def mServer   = sPmServer.getPassword().toString()
            message.setProperty("M-server", mServer)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    
    //Magento url SetToken
    def mToken_alias = head.get("MAG_Token")
        try{
            def sPmToken = secureStorageService.getUserCredential(mToken_alias)
            def mToken   = sPmToken.getPassword().toString()
            message.setProperty("M-token", mToken)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    //Magento url GetOrders
      def mGOrders_alias = head.get("MAG_GetOrders")
        try{
            def sPmGOrders = secureStorageService.getUserCredential(mGOrders_alias)
            def mGOrders   = sPmGOrders.getPassword().toString()
            message.setProperty("M-gOrders", mGOrders)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    //SAP Server
    def sServer_alias = head.get("SAP_Server")
        try{
            def sPsServer = secureStorageService.getUserCredential(sServer_alias)
            def sServer   = sPsServer.getPassword().toString()
            message.setProperty("SAP-server", sServer)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    //SAP mandt
    def sMandt_alias = head.get("SAP_Mandt")
        try{
            def sPsMandt = secureStorageService.getUserCredential(sMandt_alias)
            def sMandt   = sPsMandt.getPassword().toString()
            message.setProperty("SAP-mandt", sMandt)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    
    //SAP rfc
    def SAP_Rfc_alias = head.get("SAP_Rfc")
        try{
            def sPSAP_Rfc = secureStorageService.getUserCredential(SAP_Rfc_alias)
            def SAP_Rfc   = sPSAP_Rfc.getPassword().toString()
            message.setProperty("SAP-rfc", SAP_Rfc)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    
    //SAP idoc
    def SAP_Idoc_alias = head.get("SAP_Idoc")
        try{
            def sPSAP_Idoc = secureStorageService.getUserCredential(SAP_Idoc_alias)
            def SAP_Idoc   = sPSAP_Idoc.getPassword().toString()
            message.setProperty("SAP-idoc", SAP_Idoc)
        } catch(Exception e){
            throw new SecureStoreException("Secure Parameter not available")
        }
    
    // Magento credentials
    String Credentials_name = map.get("Credentials4Magento");
    
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


