import com.sap.it.api.mapping.*;
import com.sap.it.api.mapping.MappingContext;

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

def String GetNumberRange(String argInput){



 String value1 = context.getHeader(header.InvoiceNumber);


//    String value2 = context.getProperty("InvoiceRangeProp");
    

//    def map = message.getProperty();
//    def value = map.get("InvoiceRangeProp");

//     String value2 = value;
    
    
	return value1; 
}


def String GetN1Value(String E1EDL20, String Partner, String prefix, String postfix){ 
    
    String out = "";  

    def xml = new XmlSlurper().parseText(E1EDL20); 
    
    for (E1ADRM1 in xml.E1ADRM1) {     
        if(E1ADRM1.PARTNER_Q == Partner){  
            out = prefix  + E1ADRM1.NAME1 + postfix;     
            break;
        }  
    } 

    return out;

}

def String GetN3Value(String E1EDL20, String Partner, String prefix, String postfix){ 
    
    String out = "";  

    def xml = new XmlSlurper().parseText(E1EDL20); 
    
    for (E1ADRM1 in xml.E1ADRM1) {     
        if(E1ADRM1.PARTNER_Q == Partner){  
            out = prefix  + E1ADRM1.STREET1 + postfix;     
            break;
        }  
    } 

    return out;

}

def String GetN4Value(String E1EDL20, String Partner, String prefix, String postfix){ 
    
    String out = "";  

    def xml = new XmlSlurper().parseText(E1EDL20); 
    
    for (E1ADRM1 in xml.E1ADRM1) {     
        if(E1ADRM1.PARTNER_Q == Partner){  
            out = prefix  + E1ADRM1.CITY1 + "*" + E1ADRM1.REGION + "*" + 
                            E1ADRM1.POSTL_COD1 + "*" + E1ADRM1.COUNTRY1 + postfix;     
            break;
        }  
    } 

    return out;

}

def String getProperty(String property_name, MappingContext context) {

    def propValue= context.getPropery(property_name);

    return propValue;

}


def String Get_ChargData(String SegL24, String SearchMatnrRFC){
    
    String output = "";
    //Convert string to XML //it must be send like a XML
     def xml = new XmlSlurper().parseText(SegL24) 
        //<?xml version="1.0" encoding="UTF-8"?><item><MATNR>NUS-30170</MATNR><CHARG>200687</CHARG></item>
     
      output = SegL24;
//     
        //for (E1EDL24Item in xml) { 
            
        //    if(E1EDL24Item.MATNR == SearchMatnrRFC){ 

          //      output =E1EDL24Item.CHARG;break;
        //}  
//      } 
     
//    return  xml; 
    return output; 
}

def String getChargDetail(String Attributes,     String txtMATNR){
    
    int cont  = 0;
    int index = 0;
  
    String out = "";
    
   //Convert string to XML //it must be send like a XML
    def xml = new XmlSlurper().parseText(Attributes) 
		  
			//Looking for position of the actual SKU 
			for (itemLine in xml.item.MATNR) {
				if(itemLine == txtMATNR) 
					index = cont;  
				
				cont = cont + 1;
			}
			//Get the Price by position
			out =  xml.item.CHARG[index];
		 
	
    return out;
    
		}