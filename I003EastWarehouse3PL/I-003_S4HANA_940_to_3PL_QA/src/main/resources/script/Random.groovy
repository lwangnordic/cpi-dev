import com.sap.it.api.mapping.*;


def String customFunc(String arg1){
    
    String randomNumber = new Random().nextInt(100000 + 1)  + 90000 
	return randomNumber;
}