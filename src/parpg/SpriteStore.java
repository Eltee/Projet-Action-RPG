package parpg;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteStore {
	//La seule instance de la classe
	private static SpriteStore single = new SpriteStore();
	
	public static SpriteStore get() {
		return single;
	}
	
	private HashMap<String,Sprite> sprites = new HashMap<String, Sprite>();
	
	public Sprite getSprite(String key) {
		// Si la sprite est déjà chargée..
		if (sprites.get(key) != null) {
			return (Sprite) sprites.get(key);
		}
		
		// Si la sprite n'est pas chargée, l'ajouter au buffer
		BufferedImage sourceImage = null;
		
		try {
			URL url = this.getClass().getClassLoader().getResource(key);
			
			if (url == null) {
				fail("Impossible de trouver: " + key);
			}
			
			sourceImage = ImageIO.read(url);	
		} 
		catch (IOException e) {
			fail("Impossible de charger: "+key);
		}
		
		// On créer une image accelerée de la bonne taille
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		image.getGraphics().drawImage(sourceImage,0,0,null);
		
		// On l'ajoute au buffer
		Sprite sprite = new Sprite(key, image);
		sprites.put(key,sprite);
		
		return sprite;
	}
	
	public void storeSprite(String key, BufferedImage sourceImage){
		//Deja loadée
		if (sprites.get(key) != null) {
			System.out.println("Deja existant.");
		}
		else{
			// On créer une image accelerée de la bonne taille
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
			image.getGraphics().drawImage(sourceImage,0,0,null);
					
			// On l'ajoute au buffer
			Sprite sprite = new Sprite(key, image);
			sprites.put(key,sprite);
		}
		
	}
	
	// Si il y a erreur, quitter le jeu.
	private void fail(String message) { 
		System.err.println(message);
		System.exit(0);
	}
}