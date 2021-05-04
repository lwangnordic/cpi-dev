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

def String validateAmount(String amount="0", String segment){
    DecimalFormat general = new DecimalFormat("#.00");
    DecimalFormat op = new DecimalFormat("#.0000");

    if ( segment == "T") {
        if (amount.length()==0) {
            return '.00';
        }
        return amount;
    }

    if (amount.isNumber()) {
        if (amount.toDouble()>0) {
            if ( segment == "0P") {
                return op.format(amount.toDouble());
            } else {
                return general.format(amount.toDouble());
            }
        }
        if ( segment == "0P"){
            return '.0000';    
        }
        return '.00';
    } else if (amount.length()==0) {
        if ( segment == "0P") {
            return '.0000';    
        }
        return '.00';
    }
    
    return amount;
    
}

def String determineG6Segment(String qalf="0"){
    if ( qalf == "010") {
        return "10";
    } else if ( qalf == "004") {
        return "04";
    } else {
        return "";
    }
}

def String getG62Value(String SegL20, String Qualf, String Column = ""){
 
    String output = "";


    //Convert string to XML //it must be send like a XML
    def xml = new XmlSlurper().parseText(SegL20) 

    for (E1EDT13 in xml.E1EDT13) { 
        if(E1EDT13.QUALF == Qualf){ 

            switch(Column) {
            case "QUALF":
                output = E1EDT13.QUALF; break; 
    
            case "NTEND":
                output = E1EDT13.NTEND; break; 
    
            } 
            break;
        }  
    } 
      
      
    return output; 
}
 
 
 
 
 
 
 
 
 
 
 
 
 