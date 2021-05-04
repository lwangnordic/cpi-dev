import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {

    def body = message.getBody(String.class);
    String xml = new XmlSlurper().parseText(body);
    xml = xml.replaceAll("&apos;","~");
    xml = xml.replaceAll("~","~\n");
    message.setBody(xml);

    return message;
}