import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.time.LocalDateTime;



def Message ExecuteProcess(Message message){
    
	def body = message.getBody(java.lang.String);     



	Date dateObj = new Date();	
	String MinuteObj = dateObj.getMinutes();
	String strreturn = "0";

		 if(MinuteObj.reverse().take(2).reverse() == "0"){
			strreturn = 'Inventory';
		}
		
		if(MinuteObj.reverse().take(2).reverse() == "05" || 
		   MinuteObj.reverse().take(2).reverse() == "20" || 
		   MinuteObj.reverse().take(2).reverse() == "35" || 
		   MinuteObj.reverse().take(2).reverse() == "50"  ){
			strreturn = 'CreateOrders';
		}
		
		
		if(MinuteObj.reverse().take(2).reverse() == "10" || 
		   MinuteObj.reverse().take(2).reverse() == "25" ||
		   MinuteObj.reverse().take(2).reverse() == "40" || 
		   MinuteObj.reverse().take(2).reverse() == "55"  ){
			strreturn = 'ChinaDocs';
		}
		
		message.setBody(strreturn);
		message.setProperty("execute", strreturn);

	return message;
}