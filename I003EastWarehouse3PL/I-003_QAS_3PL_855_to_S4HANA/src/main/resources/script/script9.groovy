import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    
    def fileName = message.getBody(String.class);
    fileName = fileName.replace("<FILE>", "");
    fileName = fileName.replace("</FILE>", "");
    String filePath = "/855 Records/" + fileName;
    String processedFolder = "/855 Records/Processed/" + fileName;
    
    def messageLog = messageLogFactory.getMessageLog(message);
    messageLog.addAttachmentAsString("FilePath", filePath, "text/plain");

    String userName =  "accelyuser@ad.nordicnaturals.com";
    String passwordPart1 = "aG938s";
    String passwordPart2 = "\$sF9x64kQ#";
    String password = passwordPart1 + passwordPart2;
    String hostName = "sapftp.nordicnaturals.com";
    

    Integer port = 3396; 
    String finalString = "";
    

    JSch jsch = new JSch();
    Session session = null;
    
    try {
        
    session = jsch.getSession(userName, hostName, port);
    session.setConfig("StrictHostKeyChecking", "no");
    session.setPassword(password);
    
    session.connect();
    
    Channel channel = session.openChannel("sftp");
    channel.connect();
    ChannelSftp sftpChannel = (ChannelSftp) channel;
    
    filePath = filePath.trim();
    processedFolder = processedFolder.trim();
    InputStream stream = sftpChannel.get(filePath);
    messageLog.addAttachmentAsString("InputStream", stream, "text/plain");
    //sftpChannel.rename(filePath, processedFolder);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = br.readLine()) != null) {
            	finalString = finalString + line; // You can have your own logic over here
            }
            
        } catch (IOException io) {
            finalString = io.getMessage();
            def IOExceptionLog = messageLogFactory.getMessageLog(message);
                IOExceptionLog.addAttachmentAsString("Read IOExceptionLog", io.getMessage()+ " - " + filePath , "text/plain");

        } catch (Exception e) {
           finalString = e.getMessage();
           def ExceptionLog = messageLogFactory.getMessageLog(message);
	           ExceptionLog.addAttachmentAsString("Read ExceptionLog", e.getMessage() + " - " + filePath , "text/plain");
        }
    
    sftpChannel.exit();
    session.disconnect();
    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message);       
	        JSchExceptionLog.addAttachmentAsString("Read JSchExceptionLog", e.getMessage() + " - " + filePath, "text/plain");
    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message);
	        SftpExceptionLog.addAttachmentAsString("Read SftpExceptionLog", ee.getMessage() + " - " + filePath, "text/plain");
    }

    if (finalString != null && finalString != "") {
        messageLog.addAttachmentAsString("EDI File", finalString, "text/plain");
    } else {
        messageLog.addAttachmentAsString("EDI File", "No generated - " + fileName, "text/plain");
    }
    
    message.setBody(finalString);
    
	return message;

}