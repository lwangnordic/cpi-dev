import com.sap.it.api.mapping.*;
import java.time.YearMonth;

/*Add MappingContext parameter to read or set headers and properties
def String customFunc1(String P1,String P2,MappingContext context) {
         String value1 = context.getHeader(P1);
         String value2 = context.getProperty(P2);
         return value1+value2;
}

Add Output parameter to assign the output value.
def void custFunc2(String[] is,String[] ps, Output output, MappingContext context) {
        String value1 = context.getHeader(is[0]);
        String value2 = context.getProperty(ps[0]);
        output.addValue(value1);
        output.addValue(value2);
}*/

def String getCardType(String brand){
    
    String Response = "";
    
    switch(brand) {            
			
         case "PayPal Direct Capt": 
            Response = "DP1P";  break; 
            
         case "PayPal with Auth.": 
            Response = "DP2P";  break; 
            
         case "American Express": 
            Response = "DPAM";  break; 
            
         case "Dinersclub": 
            Response = "DPDI";  break; 
            
         case "Discovercard": 
            Response = "DPDS";  break; 
            
         case "JCBcard": 
            Response = "DPJC";  break; 
            
         case "Mastercard": 
            Response = "DPMC";  break; 
            
         case "Eurocard": 
            Response = "DPMC";  break; 
            
         case "Visa": 
            Response = "DPVI";  break; 
      }
      
	return Response 
}

def String getToken(String PaymentCards, String Customer){
    
    
    int cont  = 0;
    int index = 0;
    String out = "";
    
    //Convert string to XML //it must be send like a XML
    def xml = new XmlSlurper().parseText(PaymentCards) 
 
    //Looking for position of the actual CustNumber
	for (XCust in xml.PaymentCards.PaytCardByPaytServiceProvider) {
		if(XCust == Customer) 
			index = cont;  
				
		cont = cont + 1;
	}
	
    //Get the Token by position
	out = xml.PaymentCards.PaytCardByDigitalPaymentSrvc[index];

    return out;
}

def String getValidTo(String Year, String Month){

    int Y = Year.toInteger();
    int M = Month.toInteger();
    int lastDayOfMonth = YearMonth.of(Y, M).lengthOfMonth();
    
    
    return (Month + "/" + lastDayOfMonth.toString() + "/" + Year );
}










