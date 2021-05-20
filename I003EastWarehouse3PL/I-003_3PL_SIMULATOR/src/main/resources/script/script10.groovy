import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.xml.*

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

// this file is abandoned

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
    String finalString = "test";
    
   
        Session session855PRD = null;
        Channel channel855PRD = null;
        ChannelSftp sftpChannel855PRD = null;
        userName = "qa_3pl_cpi";
        hostName = "sapftp.nordicnaturals.com";
        port = "3396";
        password = "lumpyM3mory94";
    
    try {
        
     JSch jsch = new JSch();
    session855PRD = jsch.getSession(userName, hostName, port.toInteger());
    session855PRD.setPassword(password);
    
    session855PRD.setConfig("StrictHostKeyChecking", "no");
//    sessionPRD855.setTimeout(120000);
    session855PRD.connect(120000);

    
    channel855PRD = session855PRD.openChannel("sftp");
    channel855PRD.connect();
    sftpChannel855PRD = (ChannelSftp) channel855PRD;
    
    Vector<ChannelSftp.LsEntry> directoryEntries = sftpChannel855PRD.ls(path);
	for (ChannelSftp.LsEntry file : directoryEntries) {
	    finalString = "";
	    if (file.getFilename() != "Processed" && file.getFilename() != "Fail" && file.getFilename() != "FilesPause4Edition") {
	        String filePath = path + "/" + file.getFilename();
	        nameFiles = nameFiles + "<Name>"+filePath+"</Name>";
	        InputStream stream = sftpChannel855PRD.get(filePath);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = br.readLine()) != null) {
            	    finalString = finalString + line;
                }
                String processedFolder = path + processedPath + "Archive_" + file.getFilename();
                Files = Files + "<FILE><EDI>"+finalString+"</EDI><ProcessFolder>"+processedFolder+"</ProcessFolder></FILE>";
                sftpChannel855PRD.rename(filePath, processedFolder);
                
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
	
    sftpChannel855PRD.exit();
    channel855PRD.disconnect(); 
    session855PRD.disconnect();
    
    
    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message);       
	        JSchExceptionLog.addAttachmentAsString("JSchExceptionLog", e.getMessage(), "text/plain");
    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message);
	        SftpExceptionLog.addAttachmentAsString("SftpExceptionLog", ee.getMessage(), "text/plain");
    }
    finally{
        if (channel855PRD != null){
            channel855PRD.disconnect();
            session855PRD.disconnect();
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