import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import groovy.xml.*

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sap.it.api.securestore.SecureStoreService;
import com.sap.it.api.securestore.UserCredential;
import com.sap.it.api.ITApiFactory;


def Message processData(Message message) {
    
    def messageLog = messageLogFactory.getMessageLog(message);
    def map = message.getProperties();
   
    
    String userName =  map.get("SFTP_USER");
    String passwordPart1 = map.get("SFTP_PASSWORD_PART1");
    String passwordPart2 = map.get("SFTP_PASSWORD_PART2");
    String password = passwordPart1 + passwordPart2;
    String hostName = map.get("SFTP_ADDRESS");
    String path = map.get("SFTP_PATH");
    String processedPath = map.get("SFTP_PROCESSED_PATH");
    String port = map.get("SFTP_PORT");
    String Files = "";
    String nameFiles = "";
    String finalString = "";

        Session session945PRD = null; 
        Channel channel945PRD = null;
        ChannelSftp sftpchannel945PRD = null;

        userName = "prd_3pl_cpi";
        hostName = "sapftp.nordicnaturals.com";
        port = "3396";
        password = "bumpyEngin348";


    try {
        
        JSch jsch = new JSch();
        session945PRD = jsch.getSession(userName, hostName, port.toInteger());
        
//        
        session945PRD.setPassword(password);
        

        session945PRD.setConfig("StrictHostKeyChecking", "no");        
        session945PRD.connect(120000);
    
        channel945PRD = session945PRD.openChannel("sftp");
        channel945PRD.connect();
        
        sftpchannel945PRD = (ChannelSftp) channel945PRD;
          

    Vector<ChannelSftp.LsEntry> directoryEntries = sftpchannel945PRD.ls(path);
	for (ChannelSftp.LsEntry file : directoryEntries) {
	    fileNameBeginsWith = file.getFilename().substring(0,3);
	    finalString = "";
	    if (file.getFilename() != "Processed" && file.getFilename() != "Fail" && fileNameBeginsWith == "945") {
	        
	        
	        String filePath = path + "/" + file.getFilename();
	        nameFiles = nameFiles + "<Name>"+filePath+"</Name>";
	        InputStream stream = sftpchannel945PRD.get(filePath);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = br.readLine()) != null) {
            	    finalString = finalString + line;
                }
                String processedFolder = path + processedPath + "Archive_" + file.getFilename();
                Files = Files + "<FILE><EDI>"+finalString+"</EDI><ProcessFolder>"+processedFolder+"</ProcessFolder></FILE>";
                sftpchannel945PRD.rename(filePath, processedFolder);
                
            } catch (IOException io) {
                finalString = io.getMessage();
                def IOExceptionLog = messageLogFactory.getMessageLog(message);
                    IOExceptionLog.addAttachmentAsString("IOExceptionLog", io.getMessage()+ " - " + filePath , "text/plain");
            } catch (Exception e) {
                finalString = e.getMessage();
                def ExceptionLog = messageLogFactory.getMessageLog(message);
	                ExceptionLog.addAttachmentAsString("ExceptionLog", e.getMessage() + " - " + filePath , "text/plain");

            }	        
	        
	    }
	}

    sftpchannel945PRD.exit();
    channel945PRD.disconnect();   
    session945PRD.disconnect();

    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message);       
	        JSchExceptionLog.addAttachmentAsString("JSchExceptionLog", e.getMessage(), "text/plain");

    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message);
	        SftpExceptionLog.addAttachmentAsString("SftpExceptionLog", ee.getMessage(), "text/plain");
    } 
    finally{
        if (channel945PRD != null){
            channel945PRD.disconnect();
            session945PRD.disconnect();
        }
    }


    String XML = "";
    if (Files != null && Files != "") {
        XML = XmlUtil.serialize("<SFTP>"+Files+"</SFTP>");
    } else {
        XML = XmlUtil.serialize("<SFTP></SFTP>");
    }
    
    messageLog.addAttachmentAsString("XML Files Names", XML.trim(), "text/plain");
    messageLog.addAttachmentAsString("NameFilePath", XmlUtil.serialize("<NAMES>"+nameFiles+"</NAMES>"), "text/plain");
   
  
   message.setBody(XML.trim());
    
   return message;

};


