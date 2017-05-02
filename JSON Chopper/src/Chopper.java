import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class Chopper {
	private static String formAlias = "afgNarrative";
	private static int stringLength = 1000;
	
	@Test
	public void main(){
		System.out.println("Input JSON String: ");
		Scanner sc = new Scanner(System.in);
		String json = sc.next();
		ArrayList<String> choppedString = jsonChopper(json);
		for(String str: choppedString){
			System.out.println("update forms set json = json|| '"+str+"' where alias = '"+formAlias+"';");
		}
	}
	
	private static ArrayList<String> jsonChopper(String json){
		ArrayList<String> choppedString = new ArrayList<String>();
		
		int startIndex = 0;
		while(startIndex <= json.length()){
			int endIndex = startIndex+stringLength;
			if(endIndex > json.length()){
				endIndex = json.length();
			}
			choppedString.add(json.substring(startIndex, endIndex));
		}
		
		
		return choppedString;
	}

}
