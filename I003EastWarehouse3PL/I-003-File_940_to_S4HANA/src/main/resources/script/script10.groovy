import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*


 def Message processData(Message message) {

  def EDI = message.getBody(String.class);
   
       String VBELN = "";
      String NTANF = "";
      String REFNR = "";
      String DOCNUM = "";
      String EDATU20 = "";
      String DATUM03 = ""; 
      String[] positions;
      int linesAfterLX = 27;
      int linesAfterG62 = 5;
   
  
  String[] lines = EDI.split("\\'");

    //DOCNUM
  positions = lines[0].split("\\*");
  if (positions.size() >= 13) {
      DOCNUM = positions[13];
  }
  
  //E1EDK03
    //DATUM03
    positions = lines[1].split("\\*");
    if (positions.size() >= 4) {
        DATUM03 = positions[4];
    }
//println DATUM03;
  //E1EDK01
    //BELNR
  positions = lines[3].split("\\*");
  if (positions.size() >= 2) {
      BELNR = positions[2];
  }

// E1EDK03
    //IDDAT
  positions = lines[0].split("\\*");
  if (positions.size() >= 13) {
      IDDAT = positions[13];
  }
  
 
      //20EDATU  ShiptoDate
//    positions = lines[20].split("\\*");
//    if (positions.size() >= 2) {
//        EDATU20 = positions[2];
//    }  
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
//  E1EDP20
    
// def str = message.getBody(String.class);


//G62*10
//  String findStrG62 = "G62*10*";  
//  String indStrLXG62 = "LX*";  
  int lastIndexG62 = 0;
  int occurrenceG62 = 0;



  // Count G62
   def str = EDI; // message.getBody(String.class);
       String findStrG62 = "G62*10*"; 
       int lastIndex = 0; 
       int occurrence = 0; 
       
       while(lastIndex != -1) {      
        lastIndex = str.indexOf(findStrG62,lastIndex);      
            if(lastIndex != -1) {         
                occurrence ++;         
                lastIndex += findStrG62.length();     
            } 
        } 
        
        String results = "";
        for (i = 0; i < occurrence; i++) {  
            String result = str.substring(0, (str.indexOf('G62*10*') + 7));  
            str = str.replace(result,"");
            String result2 = str.substring(0, str.indexOf('*')-4);   
            if (i == 0) {
//                results = result2;
                EDATU20 = result2;
//            } else {
//                results = results +  "<I_VBELN>"+result2+"</I_VBELN>";
            }
            
        }
//println EDATU20;
//        


//  for (i = 0; i < occurrenceG62; i++) {
//    int currentLineG62 = linesAfterG62 + 2; 
//    LX_POSNR[i] = i + 1;
//    positions = lines[currentLineG62].split("\\*");

//    if (positions.size() >= 1) {
//        EDATU20=  positions[1];
//    } else {
//        EDATU20 = "";
//    }
//  }

//println EDATU20;

//    positions = lines[occurrence].split("\\*");



// String[] LX_POSNR = new String[occurrenceLX];

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
  String E1EDKA1AG = "";  //Partner      N1*AO**91*2961174'  
  String E1EDKA1WE = "";  //Partner     CALL FUNCTION


//G62*10
//  String findStrG62 = "N1*AO*";  
//  String indStrLXAG = "LX*";  
//  int lastIndexAG = 0;
//  int occurrenceAG = 0;

    result2 = "";

  // Count AG
    str = EDI; // message.getBody(String.class);
       String findStrAG = "N1*AO*"; 
       int lastIndexAG = 0; 
       int occurrenceAG = 0; 
       
       while(lastIndex != -1) {      
        lastIndex = str.indexOf(findStrG62,lastIndex);      
            if(lastIndex != -1) {         
                occurrence ++;         
                lastIndex += findStrG62.length();     
            } 
        } 
        
        results = "";
        for (i = 0; i < occurrence; i++) {  
            String result = str.substring(0, (str.indexOf('N1*AO*') +10));  
            str = str.replace(result,"");
            result2 = str.substring(0, str.indexOf('*')-4);   
            if (i == 0) {
//                results = result2;
//                print results;
//                print results2;
                E1EDKA1AG = result2;
//            } else {
//                results = results +  "<I_VBELN>"+result2+"</I_VBELN>";
            }
            
        }
//println E1EDKA1AG ;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////

//LX
  String findStrLX = "LX*";  
  String indStrLX = "LX*";  
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
  
  
  
  String E1EDKA1 = "";  //Partner
  String E1EDK02 = "";  //Customer PO 
  String E1EDP01 = "";  //Item Quantity
  String E1EDP20 = "";  //Quantity Delivery
  String E1EDL19 = "";  //Items

  
  String[] LX_POSNR = new String[occurrenceLX];
  String[] MATERIAL_MATNR = new String[occurrenceLX];
  String[] WMENG20 = new String[occurrenceLX];
//  String[] EDATU20 = new String[occurrenceLX];
  
  
//  String[] EDATU20 = new String[occurrenceLX];
  

  for (i = 0; i < occurrenceLX; i++) {
    int currentLine = linesAfterLX + 2; 
    LX_POSNR[i] = i + 1;
    positions = lines[currentLine].split("\\*");
    

//E1EDP01
    if (positions.size() >= 2) {
        WMENG20[i] = positions[2];
    } else {
        WMENG20[i] = "";
    }


//   E1EDL19  WMENG20
    if (positions.size() >= 2) {
        WMENG20[i] = positions[1];
    } else {
        WMENG20[i] = "";
    }

      if (positions.size() >= 4) {
          MATERIAL_MATNR[i] = positions[5];
    } else {
        MATERIAL_MATNR[i] = "";
    }
    
    E1EDP01 = E1EDP01 + "<E1EDP01><POSEX>" + LX_POSNR[i]  + "</POSEX><MENGE>" + WMENG20[i] + "</MENGE></E1EDP01>"
    
    E1EDL19 = E1EDL19 + "<E1EDL19><QUALF>002</QUALF><IDTNR>" + MATERIAL_MATNR[i] + "</IDTNR><WMENG>" +  WMENG20[i] + "</WMENG></E1EDL19>";
    
    E1EDP20 = E1EDP20 + "<E1EDP20><WMENG>" + WMENG20[i] + "</WMENG><EDATU>" + EDATU20  + "</EDATU></E1EDP20>";
    //E1EDL19 = E1EDL19 + "<E1EDL19><MATNR>" + LX_POSNR[i] +MATERIAL_MATNR[i] + "</MATNR></E1EDL19>";
    linesAfterLX = linesAfterLX + 6;
  }


     E1EDK03 = "<E1EDK03><DATUM>" + DATUM03  + "</DATUM></E1EDK03>"
     E1EDK02 = "<E1EDK02><BELNR>" + BELNR + "</BELNR></E1EDK02>"

    DATUM = DATUM03;
//     println DATUM ; 
     E1EDKA1 = "<E1EDKA1><PARTNAG>" + E1EDKA1AG + "</PARTNAG></E1EDKA1>"

//  //N9*IK - VBELN
//  positions = lines[linesAfterLX + 1].split("\\*");
//  if (positions.size() >= 3) {
//      VBELN = positions[2];
//  }
  

  String XML = XmlUtil.serialize("<root><items><DOCNUM>" + DOCNUM + "</DOCNUM><NTANF>"+ NTANF +"</NTANF><DATUM>"+ DATUM +"</DATUM><REFNR>"+  REFNR +"</REFNR><VBELN>" + VBELN + "</VBELN>" + E1EDKA1  + E1EDK02 + E1EDP01 + E1EDK03 + E1EDL19  + E1EDP20 + "</items></root>");
  message.setBody(XML.trim());
  return message;
  
}
 

/////////////////////////////////////////////////////
////////////////////////////////////////////////////
  
  