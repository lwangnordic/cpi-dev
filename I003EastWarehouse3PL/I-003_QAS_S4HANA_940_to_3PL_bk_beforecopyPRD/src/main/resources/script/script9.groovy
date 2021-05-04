import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {

    def body = message.getBody(String.class);
    String xml = new XmlSlurper().parseText(body);
    xml = xml.replaceAll("&apos;","'");

    String[] segment = xml.split("NUMBERLINES");
    String[] lines = new String[(segment.size() - 1)]; 
    String result = "";

    for (i = 0; i < (segment.size() - 1); i++) {    
        String segmento = segment[i].substring(segment[i].indexOf("ST*940"));
        String findStr = "'";
        int lastIndex = 0;
        int occurrence = 0;
   
        while (lastIndex != -1) {
            lastIndex = segmento.indexOf(findStr, lastIndex);
            if (lastIndex != -1) {
                occurrence++;
                lastIndex += findStr.length();
            }
        }
        
        lines[i] = (occurrence + 1);
        result  = result + segment[i].replace("SE*", "SE*" + lines[i].toString() );   
    }
        
    result  = result  + segment[segment.size() -1] ;
    result = result.replaceAll("'","'\n");
    message.setBody(result);

    return message;
}