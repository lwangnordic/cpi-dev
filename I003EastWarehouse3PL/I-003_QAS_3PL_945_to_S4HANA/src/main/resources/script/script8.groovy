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
    def fileNameBeginsWith; 
    
    
    String userName =  map.get("SFTP_USER");;
    String passwordPart1 = map.get("SFTP_PASSWORD_PART1");;
    String passwordPart2 = map.get("SFTP_PASSWORD_PART2");;
    String password = passwordPart1 + passwordPart2;
    String hostName = map.get("SFTP_ADDRESS");;
    String path = map.get("SFTP_PATH");;
    String processedPath = map.get("SFTP_PROCESSED_PATH");;
    String port = map.get("SFTP_PORT");
    String Files = "";
    String nameFiles = "";
    String finalString = "";

        Session session945 = null; 
        Channel channel945 = null;
        ChannelSftp sftpChannel945 = null;

        userName = "qa_3pl_cpi";
        hostName = "sapftp.nordicnaturals.com";
        port = "3396";
        password = "lumpyM3mory94";


    try {
        
        JSch jsch = new JSch();
        session945 = jsch.getSession(userName, hostName, port.toInteger());
        session945.setPassword(password);
        
        session945.setConfig("StrictHostKeyChecking", "no");        
        session945.connect(120000);
    
        channel945 = session945.openChannel("sftp");
        channel945.connect();
        sftpChannel945 = (ChannelSftp) channel945;


    Vector<ChannelSftp.LsEntry> directoryEntries = sftpChannel945.ls(path);
	for (ChannelSftp.LsEntry file : directoryEntries) {
	    fileNameBeginsWith = file.getFilename().substring(0,3);
	    finalString = "";
	    if (file.getFilename() != "Processed" && file.getFilename() != "Fail" && fileNameBeginsWith == "945") {
	        
	        
	        String filePath = path + "/" + file.getFilename();
	        nameFiles = nameFiles + "<Name>"+filePath+"</Name>";
	        InputStream stream = sftpChannel945.get(filePath);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = br.readLine()) != null) {
            	    finalString = finalString + line;
                }
                String processedFolder = path + processedPath + "Archive_" + file.getFilename();
                Files = Files + "<FILE><EDI>"+finalString+"</EDI><ProcessFolder>"+processedFolder+"</ProcessFolder></FILE>";
                sftpChannel945.rename(filePath, processedFolder);
                
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

    sftpChannel945.exit();
    channel945.disconnect();  
    session945.disconnect();

    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message);       
	        JSchExceptionLog.addAttachmentAsString("JSchExceptionLog", e.getMessage(), "text/plain");

    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message);
	        SftpExceptionLog.addAttachmentAsString("SftpExceptionLog", ee.getMessage(), "text/plain");
    } finally{
        if (sftpChannel945 != null){
            sftpChannel945.disconnect();
            session945.disconnect();
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


