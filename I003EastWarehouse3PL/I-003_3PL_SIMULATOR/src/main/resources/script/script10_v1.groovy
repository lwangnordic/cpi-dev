import com.sap.gateway.ip.core.customdev.util.Message
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URLDecoder
import groovy.xml.*

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.Session
import com.jcraft.jsch.SftpException
import com.sap.it.api.securestore.SecureStoreService
import com.sap.it.api.securestore.UserCredential
import com.sap.it.api.ITApiFactory


Message processData(Message message) {
    
    def messageLog = messageLogFactory.getMessageLog(message)
    def map = message.getProperties()
    
    

    String userName =  ""
    String password = ""
    String hostName = map.get("SFTP_ADDRESS")
    String path = map.get("SFTP_PATH")
    String processedPath = map.get("SFTP_PROCESSED_PATH")
    String port = map.get("SFTP_PORT")
    String credentialName = map.get("Credential_Name")
    int maxFilesCount = map.get("MAX_FILES_COUNT")==null?2:Integer.parseInt(map.get("MAX_FILES_COUNT"))
    String Files = ""
    String nameFiles = ""
    String finalString = ""
    def query = map.get("URL_QUERY")
    def queryParams = query?query.split('&'):null;
    def mapParams = !queryParams?null:queryParams.collectEntries { param -> param.split('=').collect { URLDecoder.decode(it) }}
    String EmailTo = mapParams?mapParams.get("EmailTo"):null

    if (EmailTo == null || EmailTo.indexOf('@')<=0) {
        EmailTo = ""
    }

    Session session855PRD = null
    Channel channel855PRD = null
    ChannelSftp sftpChannel855PRD = null
/*
    userName = "accelyuser@ad.nordicnaturals.com";
    hostName = "sapftp.nordicnaturals.com"
    port = "3396"
    password = "aG938s\$sF9x64kQ#"
    path = "/940 Records QA"
*/
    //Credential specific code
    SecureStoreService secureStoreService = ITApiFactory.getService(SecureStoreService.class, null)
    UserCredential userCredential = secureStoreService.getUserCredential(credentialName)

    userName = userCredential.getUsername().toString()
    password = userCredential.getPassword().toString()

    
    try {
        
        JSch jsch = new JSch()
        session855PRD = jsch.getSession(userName, hostName, port.toInteger())
        session855PRD.setPassword(password)

        session855PRD.setConfig("StrictHostKeyChecking", "no")
        session855PRD.connect(120000)


        channel855PRD = session855PRD.openChannel("sftp")
        channel855PRD.connect()
        sftpChannel855PRD = (ChannelSftp) channel855PRD

        Vector<ChannelSftp.LsEntry> directoryEntries = sftpChannel855PRD.ls(path)

        Collections.sort(directoryEntries, new Comparator<ChannelSftp.LsEntry>() {
            @Override
            int compare(ChannelSftp.LsEntry o1, ChannelSftp.LsEntry o2) {
                if (o1.getAttrs().getMTime() == o2.getAttrs().getMTime())
                    return 0
                else if (o1.getAttrs().getMTime()< o2.getAttrs().getMTime())
                    return 1
                else
                    return -1
            }
        })


        for (ChannelSftp.LsEntry file : directoryEntries) {
	    finalString = ""
            if (!file.getAttrs().isDir() ) {
	        String filePath = path + "/" + file.getFilename()
                nameFiles = nameFiles + "<Name>"+filePath+"</Name>"
                InputStream stream = sftpChannel855PRD.get(filePath)
                try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream))
                    String line
                    while ((line = br.readLine()) != null) {
            	    finalString = finalString + line
                    }
                String processedFolder = path + processedPath + "Archive_" + file.getFilename()
                    Files = Files + "<FILE><EDI><![CDATA["+finalString+"]]></EDI><ProcessFolder>"+processedFolder+"</ProcessFolder><EmailTo><![CDATA[" + EmailTo + "]]></EmailTo></FILE>"
                    if (maxFilesCount-- <= 0) break
                    //sftpChannel855PRD.rename(filePath, processedFolder);
                
            } catch (IOException io) {
                finalString = io.getMessage()
                    def IOExceptionLog = messageLogFactory.getMessageLog(message)
                    IOExceptionLog.addAttachmentAsString("IOExceptionLog", io.getMessage()+ " - " + filePath , "text/plain")
                } catch (Exception e) {
                finalString = e.getMessage()
                    def ExceptionLog = messageLogFactory.getMessageLog(message)
                    ExceptionLog.addAttachmentAsString("ExceptionLog", e.getMessage() + " - " + filePath , "text/plain")
                }
	        
	    }
	}
	
    sftpChannel855PRD.exit()
        channel855PRD.disconnect()
    session855PRD.disconnect()


    } catch (JSchException e) {
        def JSchExceptionLog = messageLogFactory.getMessageLog(message)
	        JSchExceptionLog.addAttachmentAsString("JSchExceptionLog", e.getMessage(), "text/plain")
    } catch (SftpException ee) {
        def SftpExceptionLog = messageLogFactory.getMessageLog(message)
        SftpExceptionLog.addAttachmentAsString("SftpExceptionLog", ee.getMessage(), "text/plain")
    }
    finally{
        if (channel855PRD != null){
            channel855PRD.disconnect()
            session855PRD.disconnect()
        }
    }

    String XML = ""
    if (Files != null && Files != "") {
        XML = XmlUtil.serialize("<SFTP>"+Files+"</SFTP>")
    } else {
        XML = XmlUtil.serialize("<SFTP></SFTP>")
    }
    
    messageLog.addAttachmentAsString("XML Files Names", XML.trim(), "text/plain")
    messageLog.addAttachmentAsString("NameFilePath", XmlUtil.serialize("<log><NAMES>"+nameFiles+"</NAMES><EmailTo>" + EmailTo + "</EmailTo></log>"), "text/plain")

    message.setBody(XML.trim())

    return message
}