def String getPartyPropertyByType(String source, String partyType, String propertyName){
    def xml = new XmlSlurper().parseText(source)
    String out = "";
     for (party in xml.Party) {
        if(party.PartnerFunction == partyType){
            out = party.getProperty(propertyName);
        }
    }
    if(out == null){
        out = "";
    }
    
    return out;
}

def String existsPartyByType(String source, String partyType){
 def xml = new XmlSlurper().parseText(source)
 String out = false;
 
 
     for (party in xml.Party) {
        if(party.PartnerFunction == partyType){
            return out = true;
        }
    }

    return out;
}


def String getPartyAddressPropertyByType(String source, String partyType, String propertyName){
    def xml = new XmlSlurper().parseText(source)
    String out = "";
     for (party in xml.Party) {
        if(party.PartnerFunction == partyType){
			out = party.Address.getProperty(propertyName);
        }
    }
    if(out == null){
        out = "";
    }
    
    return out;
}

def String getDescriptionPropertyByType(String source, String textElement){
    def xml = new XmlSlurper().parseText(source)
    String out = "";
     for (text in xml.Text) {
        if(text.TextElement == textElement){
			out = text.TextLine[0].TextElementText;
        }
    }
    if(out == null){
        out = "";
    }
    
    return out;
}

def String getMatnrByGTIN(String GTIN){
       
    def map   = message.getProperties();
    def Tbl = map.get("Matnr_Table"); 
    
    
    String Table = Tbl; 
    
    def xml = new XmlSlurper().parseText(Table)
    String out = "";
    
     for (item in xml.T_MATNR.item) {
        if(item.EAN11 == GTIN){
			out = item.MATNR;
        }
    }
    
    if(out == null){
        out = "";
    }
    
    return out;
}

