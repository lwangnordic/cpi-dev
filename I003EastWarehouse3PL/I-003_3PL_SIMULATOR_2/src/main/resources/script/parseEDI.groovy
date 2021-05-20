import com.sap.gateway.ip.core.customdev.util.Message;

import com.berryworks.edireader.EDIReader;
import com.berryworks.edireader.error.EDISyntaxExceptionHandler;
import com.berryworks.edireader.error.RecoverableSyntaxException;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

def Message processData(Message message) {
    def map = message.getProperties();
    String EDI = message.getBody(String);
    def headerMap = message.getHeaders();
	def path = headerMap.get("CamelFileName");
    StringReader strReader = new StringReader(EDI);
    StringWriter generatedOutput = new StringWriter();
    InputSource inputSource = new InputSource(strReader);
    try {

        XMLReader ediReader = new EDIReader();
        ((EDIReader) ediReader).setNamespaceEnabled(false);

        ((EDIReader) ediReader).setSyntaxExceptionHandler(new IgnoreSyntaxExceptions());

        // Establish the SAXSource
        SAXSource source = new SAXSource(ediReader, inputSource);

        // Establish a Transformer
        Transformer transformer = TransformerFactory.newInstance().newTransformer();


        // Use a StreamResult to capture the generated XML output
        StreamResult result = new StreamResult(generatedOutput);

        // Call the Transformer to generate XML output from the parsed input
        transformer.transform(source, result);

        message.setBody(generatedOutput.toString());

    } catch(TransformerConfigurationException e) {
        //System.err.println("\nUnable to create Transformer: " + e);
        message.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?><error><![CDATA[Unable to create Transformer: " + e + "\n" + EDI +"]]></error>");
    } catch (TransformerException e) {
        //System.err.println("\nFailure to transform: " + e);
        //System.err.println(e.getMessage());
        message.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?><error><![CDATA[Failure to transform: " + e.getMessage()+ "\n" + EDI + "\n" + generatedOutput.toString()+"]]></error>");
    }

    try {
        strReader.close();
    } catch (IOException ignored) {
    }
    try {
        generatedOutput.close();
    } catch (IOException ignored) {
    }

    return message;
}

public class IgnoreSyntaxExceptions implements EDISyntaxExceptionHandler
{
    public boolean process(RecoverableSyntaxException syntaxException) {

        return true;
    }
}

