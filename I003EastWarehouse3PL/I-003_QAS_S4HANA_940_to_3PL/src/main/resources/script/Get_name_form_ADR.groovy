import com.sap.it.api.mapping.*;

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

def String Get_Column_Data(String SegL20, String Partner_q, String Column = ""){
    
    String output = "";
    String result = ""; 
    //Convert string to XML //it must be send like a XML
     def xml = new XmlSlurper().parseText(SegL20) 
     
        for (E1ADRM1 in xml.E1ADRM1) { 
            
            if(E1ADRM1.PARTNER_Q == Partner_q){ 

                switch(Column) {
                    case "NAME1":
                        output = E1ADRM1.NAME1; break; 
    
                    case "STREET1":
                        output = E1ADRM1.STREET1; break; 
    
                    case "CITY1":
                        output = E1ADRM1.CITY1; break; 
    
                    case "REGION":
                        output = E1ADRM1.REGION;break; 
    
                    case "POSTL_COD1":
                        output = E1ADRM1.POSTL_COD1;break; 
    
                    case "COUNTRY1":
                        output = E1ADRM1.COUNTRY1;break; 
    
                    case "PARTNER_ID":
                        output = E1ADRM1.PARTNER_ID;break; 
    
                    case "TELEPHONE1":
                        output = E1ADRM1.TELEPHONE1;break; 
                        
                    case "STREET2":
                        output = E1ADRM1.STREET2;
//                          result = E1ADRM1.STREET2;
	

                        break; 
                } 
                 break;
        }  
      } 
     
     
	return output; 
}