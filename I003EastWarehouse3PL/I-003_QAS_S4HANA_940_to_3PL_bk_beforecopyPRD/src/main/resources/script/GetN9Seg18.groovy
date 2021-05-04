import com.sap.it.api.mapping.*;
import java.text.DecimalFormat;

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

def String Get_Column_Data(String SegL20, String Qualf_q, String Column = ""){
    
    String output = "0";
    //Convert string to XML //it must be send like a XML
     def xml = new XmlSlurper().parseText(SegL20) 
     
        for (E1EDL18 in xml.E1EDL18) { 
            
            if(E1EDL18.QUALF == Qualf_q){ 

                switch(Column) {
                    case "QUALF":
                        output = E1EDL18.QUALF; break; 
    
                    case "PARAM":
                        output = E1EDL18.PARAM; break; 
    
                } 
                 break;
        } 
      } 
     
     if (Qualf_q=="ZZ"){
         DecimalFormat df = new DecimalFormat("#.00");
         if (output.isNumber()){
            if (output.toDouble()>0){
                return df.format(output.toDouble());
            }
            return '0';
        }
        else if (output.length()==0){
            return '.00';
        }
     }
     
//    return  xml; 
    return output; 
}