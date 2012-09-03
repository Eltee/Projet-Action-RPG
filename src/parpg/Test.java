package parpg;

import java.io.File;

public class Test {
	
	public static void main (String args[]) {
		System.out.println("Drug");
		File dir = new File("Ressources/Tilesets");
		File[] children = dir.listFiles();
		if (children == null) {
			System.out.println("Orgasmo");
		} 
		else if(children.length == 0){
			System.out.println("nnnng");
		}
		else {
		    for (int i=0; i<children.length; i++) {
		        // Get filename of file or directory
		    	if(children[i].isDirectory()){
			    	System.out.println(children[i].getName());	
		    	}

		    }
		}
		
	 }
	
}
