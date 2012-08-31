package parpg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Tileset implements Serializable {
	private String name;
	private Hashtable<String, Sprite> tileset;
	
	public Tileset(String name, ArrayList<String> spritenames){
		this.name = name;
		for(int i = 0; i < spritenames.size(); i++){
			this.tileset.put(spritenames.get(i), SpriteStore.get().getSprite(spritenames.get(i))); //Temporaire, permet d'avoir seulement une sprite dans le spriteset..
		}
		
	}
	
}
