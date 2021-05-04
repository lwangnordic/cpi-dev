import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

 
                def map = message.getProperties();

           
                def ex = map.get("CamelExceptionCaught");
                if (ex!=null)
                {

                    if (ex.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException"))
                    {
                        message.setProperty("responseBodyProperty",ex.getResponseBody());
                        message.setProperty("statusCodeProperty",ex.getStatusCode());
                        message.setProperty("statusTexPropertyt",ex.getStatusText());
                        
                        def body = message.getBody(java.lang.String) as String;
                        def messageLog = messageLogFactory.getMessageLog(message);
                        if(messageLog != null)
                            {                              
                                messageLog.addAttachmentAsString("ResponseSAPLog", ex.getResponseBody(), "text/xml");
                            }                
                    }
                }

    return message;
}


