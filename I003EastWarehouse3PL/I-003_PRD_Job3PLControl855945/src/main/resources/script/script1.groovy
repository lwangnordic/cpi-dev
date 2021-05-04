import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.time.LocalDateTime;



def Message GetMinutes945(Message message){
    
def body = message.getBody(java.lang.String);     



Date dateObj = new Date();	
String MinuteObj = dateObj.getMinutes();
String strreturn = "0";

     if(MinuteObj.reverse().take(1).reverse() == "3" || MinuteObj.reverse().take(1).reverse() == "8"){
		strreturn = '945';
   	}
   	
    if(MinuteObj.reverse().take(1).reverse() == "4" || MinuteObj.reverse().take(1).reverse() == "9"){
		strreturn = '855';
   	}
   	
	message.setBody(strreturn);
	message.setProperty("execute", strreturn);

return message;
}