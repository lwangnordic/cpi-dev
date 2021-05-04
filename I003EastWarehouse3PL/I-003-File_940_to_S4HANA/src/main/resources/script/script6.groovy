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
                    }
                }

    return message;
}

