import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*
import java.lang.*;  



 def Message processData(Message message) {

  def EDI = message.getBody(String.class);
  
    String VBELN = "";
    String EXTZVBELN = "";
    String NTANF = "";
    String REFNR = "";
    String DOCNUM = "";
    String G62Shipdate = '';
    String[] positions;
    int linesAfterLX = 8;

  String[] lines = EDI.split("\\~");
  
 
  
  //DOCNUM
  positions = lines[0].split("\\*");
  if (positions.size() >= 14) {
      DOCNUM = positions[13];
  }
  
  
 //N9*IK - VBELN
  positions = lines[3].split("\\*");
  if (positions.size() >= 3) {
      VBELN = positions[2];
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
  
//G62  
  positions = lines[8].split("\\*");
  if (positions.size() >= 3) {
      G62Shipdate = positions[2];  //WADAT_IST
  }
  


  //LX
  String findStrLX = "LX*";  
  int lastIndexLX = 0;
  int LnIndexG62 = 0;
  int occurrenceLX = 0;
  Float fDELQTY_LGMNG = new Float("0.000");
  Float fREQQTY_LFIMG = new Float("0.000");
  Float flotmp = new Float("0.000");
  
  String strtmpLGMNG;
  String strtmpLFIMG;

  // Count LX
  while (lastIndexLX != -1) {
   lastIndexLX = EDI.indexOf(findStrLX, lastIndexLX);
//   LnIndexG62 = lastIndexLX -1;
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
      
    fREQQTY_LFIMG = 0;
    fDELQTY_LGMNG = 0;
      
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
    
    strtmpLGMNG = DELQTY_LGMNG[i];
    strtmpLFIMG = REQQTY_LFIMG[i];

    if (fREQQTY_LFIMG.parseFloat(REQQTY_LFIMG[i]) > 0){
        if (fDELQTY_LGMNG.parseFloat(DELQTY_LGMNG[i]) > 0){
            if (strtmpLFIMG == strtmpLFIMG){
                E1EDL24 = E1EDL24 + "<E1EDL24><POSNR>" + LX_POSNR[i] + "</POSNR><LFIMG>" + REQQTY_LFIMG[i] + "</LFIMG><LGMNG>" + DELQTY_LGMNG[i] + "</LGMNG><CHARG>" + BATCH_CHARG[i] + "</CHARG><MATNR>" + MATERIAL_MATNR[i] + "</MATNR></E1EDL24>";    
            }
        }
    }
    linesAfterLX = linesAfterLX + 4;
  }


//  String[] positionsN9IK;  
  //N9*IK - ZVBELNExt
  positions = lines[linesAfterLX].split("\\*");
 if (positions.size() >= 3) {
      EXTZVBELN = positions[2];
  }
  

  String XML = XmlUtil.serialize("<root><DOCNUM>" + DOCNUM + "</DOCNUM><NTANF>"+ NTANF +"</NTANF><REFNR>"+  REFNR +"</REFNR><VBELN>" + VBELN + "</VBELN><EXTZVBELN>" + EXTZVBELN + "</EXTZVBELN><WADAT_IST> " + G62Shipdate + "</WADAT_IST>" + E1EDL24  + "</root>");
//  String XML = XmlUtil.serialize("<root><DOCNUM>" + DOCNUM + "</DOCNUM><NTANF>"+ NTANF +"</NTANF><REFNR>"+  REFNR +"</REFNR><VBELN>" + VBELN + "</VBELN>" + E1EDL24  + "</root>");
  message.setBody(XML.trim());
  return message;
  
 }
 