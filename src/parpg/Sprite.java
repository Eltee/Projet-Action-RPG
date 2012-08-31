package parpg;


import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	private String name;
	private Image image;
	
	public Sprite(String name, Image image) {
		this.name = name;
		this.image = image;
	}
	
	public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image,x,y,null);
	}
}
