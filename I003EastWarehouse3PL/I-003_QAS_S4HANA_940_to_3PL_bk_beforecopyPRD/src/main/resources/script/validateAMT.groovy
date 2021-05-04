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
    DecimalFormat df = new DecimalFormat("#.00");

    if ( segment == "ZZ"){
        if (amount.isNumber()){
            if (amount.toDouble()>0){
                return df.format(amount.toDouble());
            }
            return '0';
        }
        else if (amount.length()==0){
            return '.00';
        }
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
