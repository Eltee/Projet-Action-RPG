package parpg;

import java.awt.Graphics;
import java.io.File;
import java.util.Hashtable;

public class Tileset{
	private String name;
	private Hashtable<String, Sprite> tileset = new Hashtable(); //Key(nom), URL
	
	public Tileset(String name){
		this.name = name;
		File dir = new File("bin/Ressources/Tilesets/" + name);
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
		    		Sprite tempo = SpriteStore.get().getSprite("Ressources/Tilesets/" + name + "/" + children[i].getName());
			    	tileset.put(children[i].getName().substring(0, children[i].getName().length()-4), tempo);
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
	
	public void draw(Graphics g, int tile, int x, int y){
		switch (tile){
			case 0: //Plancher normal
				tileset.get("FLOORA").draw(g, x, y);
				break;
			case 1: //Mur horizontal top
				tileset.get("WALLHORIZTOP").draw(g, x, y);
				break;
			case 2: //Mur vertical gauche
				tileset.get("WALLVERTLEFT").draw(g, x, y);
				break;
			case 3: //Mur horizontal bas
				tileset.get("WALLHORIZBOTTOM").draw(g, x, y);
				break;
			case 4: //Mur vertical droite
				tileset.get("WALLVERTRIGHT").draw(g, x, y);
				break;
			case 5: //Coin haut-gauche
				tileset.get("WALLCORNERTL").draw(g, x, y);
				break;
			case 6: //Coin bas-gauche
				tileset.get("WALLCORNERBL").draw(g, x, y);
				break;
			case 7: //Coin haut-droite
				tileset.get("WALLCORNERTR").draw(g, x, y);
				break;
			case 8: //Coin bas-droite
				tileset.get("WALLCORNERBR").draw(g, x, y);
				break;
			case 9: //Bloc
				tileset.get("BLOCKA").draw(g, x, y);
				break;
			case 10: //Eau
				tileset.get("WATER").draw(g, x, y);
				break;
			case 11: //Eau top
				tileset.get("WATERTOP").draw(g, x, y);
				break;
		}
	}
	
}
