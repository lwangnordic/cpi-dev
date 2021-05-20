import com.sap.it.api.mapping.*;


def String GetProperty(String Property, MappingContext context) {
         String value = context.getHeader(Property); 
         return value;
}

def String ValidatePostCode(String PostCode){
    
    String Response = PostCode.replaceAll("-","");
     
    return Response;
}

def String DeliveryBlock_validation(String BillingAddress, String ShippingAddress){
    
    String Response = "";
    
    def BillTo = new XmlSlurper().parseText(BillingAddress);
    def ShipTo = new XmlSlurper().parseText(ShippingAddress);
    
    String BillToName = BillTo.firstname + " " + BillTo.lastname;
    String ShipToName = ShipTo.firstname + " " + ShipTo.lastname;
    
    
    // Validation for Name and Address, CODE 62
     if((BillTo.customer_name              != ShipTo.name)     ||
        (BillTo.billing_contact_address1   != ShipTo.address1) ||
        (BillTo.billing_contact_address2   != ShipTo.address2) ||
        (BillTo.billing_contact_city       != ShipTo.city)     ||
        (BillTo.billing_contact_state      != ShipTo.state)    || 
        (BillTo.billing_contact_postalcode != ShipTo.postal_code))
    {
        Response = "62"; 
    }
     
     
    return Response;
}


def String getShippingCond(String ShipVia){
     
  
    String Condition = ""; //Standard EN 
    String alphaOnly = ShipVia.replaceAll("[^a-zA-Z0-9]+","");
    
    ShipVia = alphaOnly.toLowerCase(); 
    
    switch(ShipVia) {   

        case "sfexpress":            // SF Express    
            Condition = "SF";break;  // SF Express  

        case "nextdayres":           // UPS NEXT DAY RES 
            Condition = "NR";break;  // Next Day RES

        case "seconddayres":         // UPS 2ND DAY RES 
            Condition = "SR";break;  // Second Day RES 

        case "threedayres":          // UPS 3 DAY RES
            Condition = "TR";break;  // Three Day RES    

        case "upsgndres":            // UPS GND RES  
            Condition = "SE";break;  // Standard RES

        case "standardres":          // Standard RES
            Condition = "SE";break;  // Standard RES
    }   
    
    return Condition;
    
}


def String getShippingRoute(String ShipVia){
     
  
    String Route = ""; //Standard EN
      
    String alphaOnly = ShipVia.replaceAll("[^a-zA-Z0-9]+","");
    
    ShipVia = alphaOnly.toLowerCase();

    
    switch(ShipVia) {   

        case "sfexpress":           // SF Express    
            Route = "000170";break; // SF Express  

        case "overnight":           // UPS NEXT DAY RES 
            Route = "000180";break; // Next Day RES

        case "2nddayair":           // UPS 2ND DAY RES 
            Route = "000190";break; // Second Day RES 

        case "3rddayair":           // UPS 3 DAY RES
            Route = "000200";break; // Three Day RES    

        case "upsgndres":              // UPS GND RES  
            Route = "000210";break; // Standard RES
    }   
    
    return Route;
    
}

def String bPointsPayed(String bse_Paid = '0'){ 
    
    String output = "0"; 
    
    double dvaluePaid = bse_Paid as double;
    
    if (dvaluePaid == 0){
        output = "1";
        
    }
        
    return output;

}


def String getWerks(String Region, String Country){
    
    String Response = "WCHQ";
    String List = ["IN","DE","WV","RI","ND","VT","ME","KY","NE","NH","OH","DC",
                   "IL","TN","KS","MO","SD","MS","AR","AL","SC","IA","PA","NC",
                   "CT","NJ","NY","MD","MA","LA","OK","MN","TX","FL","GA","WI",
                   "VA","MI"];
    
    if (Country == "US"){
        
        if(List.contains(Region)){
        
            Response = "ECPL";
        }
        
    }
     
    return Response;
}

 
 ///////////////////////////////////////////////////////////////////////////////
 ///////////////////////////////////////////////////////////////////////////////
 ///////////////////////////////////////////////////////////////////////////////
 

/*def String covertTo2Decimals(String Value){ 
    
    String Response = "";
    double valor = Value as double;
    
   valor = (valor * 10);
        Response = valor;
        
    return Response;
}



def String IsChina(String store_id = ""){ 
    
    String Response = "0"; 
    if (store_id == "7")  
        Response = "1"; 
        
    return Response;
}

def String IsPatient(String orderType = ""){ 
    
    String Response = "0"; 
    if (orderType == "patient_order")  
        Response = "1"; 
        
    return Response;
}

def String IsCustomerOrder(String orderType = ""){ 
    
    String Response = "0"; 
    if (orderType == "consumer_order")  
        Response = "1"; 
        
    return Response;
}

def String has3decimals(String Value = "0"){ 
    
    String Response = "0";
    String[] result = Value.split("\\.");
    
    if(result[1].length() > 2)
        Response = "1";
        
    return Response;
}


def String CreateText(String MagOrdr = "", String CouponCode = ""){ 
     
    String CCode = CouponCode as String; 
    String value = "magento order: "  + MagOrdr;
    
      if (CCode != "" ){
        value = value + " | coupon code: " + CCode;} 
        
    return value;

}



def String IsPro(String orderType = ""){ 
    
    String result = "0";
    
    if (orderType == "patient_order")
        result = "1";
    return result;
}

def String UseGenerical(String erp_customer_number = "", String orderType = ""){ 
    
    String Response = "1"; 
    
    if (orderType == "patient_order") 
        Response = "1"; 
        
    if (erp_customer_number == null || erp_customer_number == "" ) 
        Response = "0";  
    
    if (orderType == "pro_order") 
        Response = "0"; 
        
    
        
    return Response;
}

 


def String CreateExpFee(String Amount_usd = "0"){
    
    String Response = "0"; 
    double dAmount_usd = Amount_usd as double;
    
    if (dAmount_usd > 50)
        Response = "1";
  
     
    return Response;
}


def String UseGenericalShipTo(String BillAdr, String ShipAdr){
    
     String output = "1";
    //Convert string to XML //it must be send like a XML
     def xBillto = new XmlSlurper().parseText(BillAdr);
     def xShipto = new XmlSlurper().parseText(ShipAdr); 
     
    if ((xBillto.city         == xShipto.city)      &&
         (xBillto.firstname   == xShipto.firstname) &&
         (xBillto.lastname    == xShipto.lastname)  &&
         (xBillto.street      == xShipto.street)    &&
         (xBillto.region_code == xShipto.region_code)){
    
        output = "0";  
    
    }
    return output;
}




def String getShippingCond(String ShipDesc){
    
  //  String alpha  = ShipDesc.replaceAll("Select Shipping Method - ","");
  
    String Condition = ""; //Standard EN
    
    String[] str = ShipDesc.split('-');  
    String alpha = str[1];
    String alphaOnly = alpha.replaceAll("[^a-zA-Z0-9]+","");
    
    alpha = alphaOnly.toLowerCase(); 
    
    switch(alpha) {   

        case "sfexpress":           // SF Express    
            Condition = "SF";break; // SF Express  

        case "overnight":           // UPS NEXT DAY RES 
            Condition = "NR";break; // Next Day RES

        case "2nddayair":           // UPS 2ND DAY RES 
            Condition = "SR";break; // Second Day RES 

        case "3rddayair":           // UPS 3 DAY RES
            Condition = "TR";break; // Three Day RES    

        case "ground":              // UPS GND RES  
            Condition = "SE";break; // Standard RES
    }   
    
    return Condition;
    
}



def String getPriceUSD(String Attributes,     String textSku, 
					   String store_id,       String orderType = "", 
					   String base_row_total = "0", String qty_ordered = "0",
					   String item_id = "0", String base_price = "0"){
    
    int cont  = 0;
    int index = 0;
  
	double dResult = 0;
	double dbase_price = base_price as double;
	double dRowTotal = base_row_total as double;
	double dQuantity = qty_ordered as double;
    String out = "";
    
    //Convert string to XML //it must be send like a XML
    def xml = new XmlSlurper().parseText(Attributes) 
 
	if (orderType == "patient_order"){
		
 
			
		int iItemId = item_id as int;      // Cast to integer
		iItemId++;                         // Add 1 number by Parent Item
        String Temp_id = iItemId;
		
		//Looking for position of the actual SKU by ItemId 
		for (items_item_id in xml.patient_order_items_item_id) {
			if(items_item_id == Temp_id)
				index = cont; 
			
			cont = cont + 1;
		}
		//Get the Price by position
		out = xml.patient_order_items_price_usd[index];
		  
	}
	else{
		  
		if (store_id == '7'){
		  
			//Looking for position of the actual SKU 
			for (sku in xml.china_order_items_sku) {
				if(sku == textSku) 
					index = cont;  
				
				cont = cont + 1;
			}
			//Get the Price by position
			out =  xml.china_order_items_price_usd[index];
		}
		else{
		 	if(qty_ordered != 0) 
		 		dResult = dRowTotal / dQuantity;
		 		
		    if(dResult == 0)
				dResult = dbase_price;//JRIVERA 2020.11.10 the line has the value of the item
			 
			out = dResult.round(3);   // Comentarized, no rounding
		}
		  
	}
	
    return out;

}
 
def String bPayedByPoints(String Store_Id, String CH_Amount_Usd ='0', String Ch_Tax_Usd ='0', String Ch_Freight_Usd ='0', String Reward_Point_Used = '0', String Subtotal_Inv ='0', String Shipping_Inv ='0'){
    
    String output = "false";
    double value = Reward_Point_Used as double;
    double dPoints = 0;
    double dTotal = 0;
    dPoints = value.round(2);
   
    if(dPoints != 0){
    
        if (Store_Id == "7"){ //China values
            double dAmount = CH_Amount_Usd as double;
            double dTax = Ch_Tax_Usd as double;
            double dCFreight = Ch_Freight_Usd as double;
            value = dAmount + dTax + dCFreight; 
        }
        else{
            double dAmount = Subtotal_Inv as double; 
            double dFreight = Shipping_Inv as double;
            value = dAmount + dFreight;
        }
      
        dTotal = value.round(2);
     
        if (dTotal == dPoints){
            output = "true";
        }
    }
    output = value
    return output;

}

def String calculateJR1byPosition(String AmountTax, String RowTotal){
    
   double dResult = 0;
   double dAmountTax = AmountTax as double;
   double dRowTotal = RowTotal as double;
    
    if(AmountTax != 0){
        dResult = (dAmountTax / dRowTotal) * 100;
        }  
    
   //String Response = dResult.round(3);
   String Response = dResult.round(2);
   
    return Response;

}

def String setDecimals(String Value, int decimals){
     
    double dValue = Value as double; 
    
    String Response = dValue.round(decimals);
   
    return Response;
}


def String getRowValue(String RowTotal, String Quantity){
    
   double dResult = 0;
   double dRowTotal = RowTotal as double;
   double dQuantity = Quantity as double;
    
    if(Quantity != 0){
        dResult = dRowTotal / dQuantity;
        }  
    
   String Response = dResult.round(2);
   
    return Response;

}





def String getAuthNumber(String last_trans_id, String CC_Approval_Code, String CC_Last4, String CC_Type, String CC_AVS_Status, String Magento_Order){ 
    
    String result = "";
    
    if (last_trans_id != ""){ result.concat(last_trans_id);}
    
    
    if (CC_Approval_Code != ""){ result.concat(CC_Approval_Code);}
    
    if (CC_Last4 != ""){ result.concat(CC_Last4);}
    
    if (CC_Type != ""){ result.concat(CC_Type);}
    
    if (CC_AVS_Status != ""){ result.concat(CC_AVS_Status);}
    
    if (Magento_Order != ""){ result.concat(Magento_Order);}
    
    
    
    
    return result;
    
}


def String LockValidation(String BillingAddress, String ShippingAddress, String USD_Amount, String GlobalAmount, String PaymentAmount, String order_type){
    
    String Response = "";
    
    def BillTo = new XmlSlurper().parseText(BillingAddress);
    def ShipTo = new XmlSlurper().parseText(ShippingAddress);
    
    String BillToName = BillTo.firstname + " " + BillTo.lastname;
    String ShipToName = ShipTo.firstname + " " + ShipTo.lastname;
    
    String Address = ShipTo.street;
    
    
    // Validation for Name and Address, CODE 62
     if((BillToName         != ShipToName)         ||
        (BillTo.street      != ShipTo.street)      ||
        (BillTo.city        != ShipTo.city)        ||
        (BillTo.region_code != ShipTo.region_code) ||
        (BillTo.country_id  != ShipTo.country_id)  ||
        (BillTo.postcode    != ShipTo.postcode)){
        Response = "62";
    }
 
    // Validation for No PO BOX, CODE 63
    if (Address.toLowerCase().contains("pobox".toLowerCase()) ||
        Address.toLowerCase().contains("po box".toLowerCase())){
        Response = "63";
    }
    
    
    // Validation for not exceed Global Amount, CODE 64
    double usdAmount = USD_Amount as double;
    if (usdAmount > 500){
        Response = "64";
    }
    
    // Validation Pro / Patient order, CODE 70
    if ((order_type == "pro_order") ||
        (order_type == "patient_order" )){
        Response = "70";
    }
    
    
    // Validation for no diference  beetwen Order amount and Payment amount, CODE 65 
    if (GlobalAmount != PaymentAmount){
        Response = "65";
    }
     
     
    return Response;
}
*/