import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message SelectMagServer(Message message) {
       
    String value   = "";
    String rServer = "";
    String RUpdObject = "";
    String DSName = "";
    String EntryId = "";
   
    def body = message.getBody(java.lang.String); 
    
    switch(body) {  
        case "104": 
            rServer    = "https://staging.nordicnaturals.com";
            RUpdObject = "/104/UpdMagento/Status";
            DSName     = "QAS104_MAG_Token";
            EntryId    = "ID_QAS104_MAG_Token";
            break;    
        default:   
            rServer    = "https://integration-5ojmyuq-hraqe4drzlnyk.us.magentosite.cloud"; 
            RUpdObject = "/UpdMagento/Status"; 
            DSName     = "QAS_MAG_Token";
            EntryId    = "ID_QAS_MAG_Token";
            break;
    }
    
    //Properties 
    map = message.getProperties(); 
    message.setProperty("rServer", rServer);
    message.setProperty("RUpdObject", RUpdObject);
    message.setProperty("DSName", DSName);
    message.setProperty("EntryId", EntryId);
    return message;
}


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
    for (E1EDL41 in xml.E1EDL24.E1EDL41) {
        if(E1EDL41.QUALI == "001"){
            value = E1EDL41.BSTNR; 
            break;
        }
    }
    
    String BOLNR = xml.BOLNR;   
    //Properties 
    map = message.getProperties(); 
    message.setProperty("BOLNR", BOLNR);
    
    String newBody = value.replaceFirst ("^0*", "");
     
    message.setBody(newBody);
    
    
    return message;
}