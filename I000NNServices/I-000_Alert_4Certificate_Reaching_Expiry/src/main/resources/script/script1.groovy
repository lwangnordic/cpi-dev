/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import java.text.SimpleDateFormat; 

def Message processData(Message message) {
    
    def map = message.getHeaders();

    String InputValue = map.get("CertExpiryDate");
    String getCertExpirydate = InputValue.substring(0,19);
     
    LocalDateTime ValidDate = LocalDateTime.parse(getCertExpirydate);

    LocalDateTime now = LocalDateTime.now();     
    LocalDateTime DueDate= now.plusDays(9); 
         
    boolean nearToExpire = ValidDate.isBefore(DueDate);

   /*  Date CertExpirydate = new SimpleDateFormat("yyyy-MM-dd").parse(getCertExpirydate);

     Date dateNow = new Date(System.currentTimeMillis());

     long dateDiff = CertExpirydate.getTime() – dateNow.getTime();

     def daysToExpire = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);*/

     message.setHeader("daysToExpire", nearToExpire);
     message.setBody(nearToExpire);
     return message;
}