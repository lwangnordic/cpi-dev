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

def String GetE1TXTH8Data(String SegL20, String tdformatID_q, String Column = ""){
    
    String output = "";
    //Convert string to XML //it must be send like a XML
     def xml = new XmlSlurper().parseText(SegL20) 
     
        for (E1TXTH8 in xml.E1TXTH8) { 
            
            if(E1TXTH8.TDID == tdformatID_q){ 

                switch(Column) {
                    case "TDLINE":
                        output = E1TXTH8.E1TXTP8.TDLINE; break; 
//                    case "TDLINE":
//                        output = E1TXTH8.TDLINE; break; 
    
                } 
                 break;
        }  
      } 
     
//    return  xml; 
    return output; 
}