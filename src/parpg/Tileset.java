package parpg;

import java.io.File;
import java.util.Hashtable;

public class Tileset{
	private String name;
	private Hashtable<String, String> tileset = new Hashtable(); //Key(nom), URL
	
	public Tileset(String name){
		this.name = name;
		File dir = new File("Ressources/Tilesets/" + name);
		File[] children = dir.listFiles();
		if (children == null) {
			fail("Aucune tuile presente dans le dossier " + name);
		} 
		else if(children.length == 0){
			fail("Aucune tuile presente dans le dossier " + name);
		}
		else {
		    for (int i=0; i<children.length; i++) {
		    	if(children[i].getName().endsWith(".png")){
			    	tileset.put(children[i].getName().substring(0, children[i].getName().length()-4), "Ressources/Tilesets/" + name + "/" + children[i].getName());
		    	}
		    	else{
		    		fail("Fichier non-compatible: " + children[i].getName());
		    	}
		    }
		}
		System.out.println("Nombre de tuiles pour " + name + ": " + tileset.size());
	}
	
	private void fail(String message) { 
		System.err.println(message);
		System.exit(0);
	}
	
}
