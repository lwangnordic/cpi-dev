import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*


 def Message processData(Message message) {

  def EDI = message.getBody(String.class);
  
    String VBELN = "";
    String NTANF = "";
    String REFNR = "";
    String DOCNUM = "";
    String[] positions;
    int linesAfterLX = 8;

  String[] lines = EDI.split("\\~");
  
  //DOCNUM
  positions = lines[0].split("\\*");
  if (positions.size() >= 14) {
      DOCNUM = positions[13];
  }
  
  //W06 - NTANF
  positions = lines[3].split("\\*");
  if (positions.size() >= 4) {
      NTANF = positions[3];
  }
  
  //N9*2I - REFNR
  positions = lines[7].split("\\*");
  if (positions.size() >= 3) {
      REFNR = positions[2];
  }
  

  //LX
  String findStrLX = "LX*";  
  int lastIndexLX = 0;
  int occurrenceLX = 0;

  // Count LX
  while (lastIndexLX != -1) {
   lastIndexLX = EDI.indexOf(findStrLX, lastIndexLX);
   if (lastIndexLX != -1) {
    occurrenceLX++;
    lastIndexLX += findStrLX.length();
   }
  }
  
  String E1EDL24 = "";
  String[] LX_POSNR = new String[occurrenceLX];
  String[] REQQTY_LFIMG = new String[occurrenceLX];
  String[] DELQTY_LGMNG = new String[occurrenceLX];
  String[] BATCH_CHARG = new String[occurrenceLX];
  String[] MATERIAL_MATNR = new String[occurrenceLX];

  for (i = 0; i < occurrenceLX; i++) {
    int currentLine = linesAfterLX + 2; 
    LX_POSNR[i] = i + 1;
    positions = lines[currentLine].split("\\*");
    if (positions.size() >= 3) {
        REQQTY_LFIMG[i] = positions[2];
    } else {
        REQQTY_LFIMG[i] = "";
    }
    if (positions.size() >= 4) {
        DELQTY_LGMNG[i] = positions[3];
    } else {
        DELQTY_LGMNG[i] = "";
    }
    if (positions.size() >= 10) {
        BATCH_CHARG[i] = positions[9];
    } else {
        BATCH_CHARG[i] = "";
    }
    if (positions.size() >= 9) {
        MATERIAL_MATNR[i] = positions[8];
    } else {
        MATERIAL_MATNR[i] = "";
    }
    E1EDL24 = E1EDL24 + "<E1EDL24><POSNR>" + LX_POSNR[i] + "</POSNR><LFIMG>" + REQQTY_LFIMG[i] + "</LFIMG><LGMNG>" + DELQTY_LGMNG[i] + "</LGMNG><CHARG>" + BATCH_CHARG[i] + "</CHARG><MATNR>" + MATERIAL_MATNR[i] + "</MATNR></E1EDL24>";
    linesAfterLX = linesAfterLX + 3;
  }

  //N9*IK - VBELN
  positions = lines[linesAfterLX + 1].split("\\*");
  if (positions.size() >= 3) {
      VBELN = positions[2];
  }
  

  String XML = XmlUtil.serialize("<root><DOCNUM>" + DOCNUM + "</DOCNUM><NTANF>"+ NTANF +"</NTANF><REFNR>"+  REFNR +"</REFNR><VBELN>" + VBELN + "</VBELN>" + E1EDL24  + "</root>");
  message.setBody(XML.trim());
  return message;
  
 }