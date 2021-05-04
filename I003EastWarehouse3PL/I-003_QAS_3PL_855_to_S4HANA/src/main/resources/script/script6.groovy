import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*


def Message processData(Message message) {

       def str = message.getBody(String.class);
       String findStr = "~BAK*"; 
       int lastIndex = 0; 
       int occurrence = 0; 
       
       while(lastIndex != -1) {      
        lastIndex = str.indexOf(findStr,lastIndex);      
            if(lastIndex != -1) {         
                occurrence ++;         
                lastIndex += findStr.length();     
            } 
        } 
        
        String results = "";
        for (i = 0; i < occurrence; i++) {  
            String result = str.substring(0, (str.indexOf('~BAK*00*AD*') + 11));  str = str.replace(result,"");
            String result2 = str.substring(0, str.indexOf('*'));
            if (i == 0) {
                results = "<I_VBELN>"+result2+"</I_VBELN>";
            } else {
                results = results +  "<I_VBELN>"+result2+"</I_VBELN>";
            }
            
        }
       String XML = XmlUtil.serialize("<I_VBELN>"+results+"</I_VBELN>");
       message.setBody(XML.trim());
       return message;
}