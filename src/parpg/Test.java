package parpg;

import java.io.File;

public class Test {
	
	public static void main (String args[]) {
		System.out.println("Test");
		File dir = new File("Ressources/Tilesets");
		File[] children = dir.listFiles();
		if (children == null) {
			System.out.println("Dossier introuvable");
		} 
		else if(children.length == 0){
			System.out.println("Dossier vide");
		}
		else {
		    for (int i=0; i<children.length; i++) {
		        // Get filename of file or directory
		    	if(children[i].isDirectory()){
			    	System.out.println(children[i].getName());	
			    	File[] tiles = children[i].listFiles();
			    	for(int j=0; j<tiles.length; j++){
			    		System.out.println(tiles[j].getName().substring(0, tiles[j].getName().length()-4));
			    	}
		    	}

		    }
		}
		
	 }
	
}
