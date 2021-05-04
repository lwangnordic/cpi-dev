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

    
    JSch jsch = new JSch();
    Session sessionMove855PRD = null;
    Channel channelMove855PRD = null;
    ChannelSftp sftpChannelMove855PRD = null;
        userName = "cpiprd"
        hostName = "sapftp.nordicnaturals.com"
        port = "3396"
        password = "lushSpr!ng75"
    
    try {
        
    sessionMove855PRD = jsch.getSession(userName, hostName, port.toInteger());
    sessionMove855PRD.setConfig("StrictHostKeyChecking", "no");
    sessionMove855PRD.setPassword(password);
    
    sessionMove855PRD.connect();
    
    channelMove855PRD = sessionMove855PRD.openChannel("sftp");
    channelMove855PRD.connect();
    sftpChannelMove855PRD = (ChannelSftp) channelMove855PRD;
    
    String moveFolder = "";
    String msgHelp = "";
    if (StatusCode == "401") {
        moveFolder = ProcessFolder.replace("/Processed/","/");
        msgHelp = "Moved file from processed folder to 855 Records folder";
    } else {
        moveFolder = ProcessFolder.replace("/Processed/","/Fail/Fail");
        msgHelp = "Moved file from processed folder to fail folder";
    };
    
    sftpChannelMove855PRD.rename(ProcessFolder, moveFolder);
    
    def messageLog = messageLogFactory.getMessageLog(message);
    if (messageLog != null) {                              
		messageLog.addAttachmentAsString(msgHelp, "From Folder: " + ProcessFolder + " to Folder: " + moveFolder , "text/plain");
    };     
    
    channelMove855PRD.disconnect();
    sftpChannelMove855PRD.exit();
    sessionMove855PRD.disconnect();
    
    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message);       
	        JSchExceptionLog.addAttachmentAsString("JSchExceptionLog", e.getMessage(), "text/plain");
    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message);
	        SftpExceptionLog.addAttachmentAsString("SftpExceptionLog", ee.getMessage(), "text/plain");
    };
    
    return message;

    
};