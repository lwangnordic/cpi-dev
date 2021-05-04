
//import com.sap.it.api.ITApiFactory;
//import com.sap.it.api.nrc.NumberRangeConfigurationService;
//import com.sap.it.api.nrc.exception.NumberRangeConfigException;

//def service = ITApiFactory.getApi(NumberRangeConfigurationService.class, null); 
//if( service != null) {
//    //Get next value from number range configured in web tooling. 
//    def nextValue = service.getNextValuefromNumberRange(“InvoiceNumber”, null); 
//    
//    return nextValue;
//}



//def String getNextNumber(String InvoiceNumber) {
    
// def NRCS = ITApiFactory.getApi(NumberRangeConfigurationService.class, null);
// def MY_NEW_NUMBER = NRCS.getNextValuefromNumberRange(InvoiceNumber,null);
 
// return MY_NEW_NUMBER; 
//}


//import com.sap.it.api.ITApiFactory;
//import com.sap.it.api.nrc.NumberRangeConfigurationService;
//import com.sap.it.api.nrc.exception. NumberRangeConfigException;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {

//def service = ITApiFactory.getApi(NumberRangeConfigurationService.class, null);
//if( service != null)
//{
//def value = service.getNextValuefromNumberRange("TempExternalInvoiceNumber",null);
//message.setHeader("TempExternalInvoiceNumber", value)
//message.setProperty("Empty_String", "")
//}

//Headers
    def map = message.getHeaders();
    def value = map.get("InvoiceNumber")
    def newstring = value;

message.setBody(newstring.toString())

return message;
}



