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



def String ValidSku(String Material){
    
    String Response = "false";
    
    if(Material.substring(0,3)== 'RUS' ||
       Material.substring(0,3)== 'NUS' ||
       Material.substring(0,3)== 'PAK' ||
       Material.substring(0,3)== 'PER' ||
       Material.substring(0,3)== 'PUS' )
       Response = "true";
    
     
    return Response;
}
