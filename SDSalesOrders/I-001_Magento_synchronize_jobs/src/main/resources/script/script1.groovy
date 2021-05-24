import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.time.LocalDateTime;



def Message ExecuteProcess(Message message){
    
def body = message.getBody(java.lang.String);     



Date dateObj = new Date();	
String MinuteObj = dateObj.getMinutes();
String strreturn = "0";

     if(MinuteObj.reverse().take(1).reverse() == "0"{
		strreturn = 'Inventory';
   	}
   	
    if(MinuteObj.reverse().take(1).reverse() == "5"  || 
       MinuteObj.reverse().take(1).reverse() == "20" ||
       MinuteObj.reverse().take(1).reverse() == "35" || 
       MinuteObj.reverse().take(1).reverse() == "50"  ){
		strreturn = 'CreateOrders';
   	}
   	
	message.setBody(strreturn);
	message.setProperty("execute", strreturn);

return message;
}