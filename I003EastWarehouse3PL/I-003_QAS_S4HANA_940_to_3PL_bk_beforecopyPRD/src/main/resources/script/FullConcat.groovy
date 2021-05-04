import com.sap.it.api.mapping.*;


def String fullConcatenation(String arg1){
   String line = new XmlSlurper().parseText(arg1);
    line = line.replace("&apos;","'");
    line = line.replace("*'","'");
   return line; 
}