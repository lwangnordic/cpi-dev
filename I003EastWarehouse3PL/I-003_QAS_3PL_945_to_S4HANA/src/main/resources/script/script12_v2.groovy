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
    
    def body = message.getBody();
    def map = message.getProperties();
    def ProcessFolder = map.get("ProcessFolder");
    String StatusCode = map.get("statusCodeProperty"); 
    
    String userName =  map.get("SFTP_USER");
    String passwordPart1 = map.get("SFTP_PASSWORD_PART1");
    String passwordPart2 = map.get("SFTP_PASSWORD_PART2");
    String password = passwordPart1 + passwordPart2;
    String hostName = map.get("SFTP_ADDRESS");
    String port = map.get("SFTP_PORT");
    String credentialName = "SFTP_QAS";//SFTP credentialName

    
    JSch jsch = new JSch();
    Session sessionMove = null;
    channelMove = null;
    sftpChannelMove = null;
        
        //userName = "qa_3pl_cpi"
        hostName = "sapftpqas.nordicnaturals.com"
        port = "3398"
        //password = "lumpyM3mory94"
    
    try {
        //Credential  code
		SecureStoreService secureStoreService = ITApiFactory.getService(SecureStoreService.class, null)
		UserCredential userCredential = secureStoreService.getUserCredential(credentialName)

		userName = userCredential.getUsername().toString()
		password = userCredential.getPassword().toString()


    sessionMove = jsch.getSession(userName, hostName, port.toInteger());
    sessionMove.setConfig("StrictHostKeyChecking", "no");
    sessionMove.setPassword(password);
    
    sessionMove.connect(120000);
    
    Channel channelMove = sessionMove.openChannel("sftp");
    channelMove.connect();
    ChannelSftp sftpChannelMove = (ChannelSftp) channelMove;
    
    String moveFolder = "";
    String msgHelp = "";
    if (StatusCode == "401") {
        moveFolder = ProcessFolder.replace("/Processed/","/");
        msgHelp = "Moved file from processed folder to 945 Records folder";
    } else {
        moveFolder = ProcessFolder.replace("/Processed/","/Fail/Fail");
        msgHelp = "Moved file from processed folder to fail folder";
    };
    
    sftpChannelMove.rename(ProcessFolder, moveFolder);
    
    def messageLog = messageLogFactory.getMessageLog(message);
    if (messageLog != null) {                              
		messageLog.addAttachmentAsString(msgHelp, "From Folder: " + ProcessFolder + " to Folder: " + moveFolder , "text/plain");
    };        
    
    
	channelMove.disconnect();
    sftpChannelMove.exit();
    sessionMove.disconnect();
    
    
    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message);       
	        JSchExceptionLog.addAttachmentAsString("JSchExceptionLog", e.getMessage(), "text/plain");
    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message);
	        SftpExceptionLog.addAttachmentAsString("SftpExceptionLog", ee.getMessage(), "text/plain");
    };

    return message;
    
};