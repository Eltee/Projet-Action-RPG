package parpg;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Entity {
	protected int x;
	protected int y;
	protected Hashtable<String, Sprite> spriteset = new Hashtable<String, Sprite>();
	protected Sprite sprite;
	protected Rectangle hitbox;
	private Rectangle compareHitbox;
	private boolean respawn;
	private double delay;
	private String state;
	
	public Entity(int x, int y){ //Constructeur sans sprite
		this.x = x;
		this.y = y;	
	}
	
	public Entity(String sprite, String folder, int x, int y){ //Constructeur avec une seule sprite
		this.sprite = SpriteStore.get().getSprite(folder + sprite);
		this.x = x;
		this.y = y;	
	}
	
	public Entity(ArrayList<String> spritenames, String folder, int x, int y){ //Constructeur avec plusieurs sprites
		for(int i = 0; i < spritenames.size(); i++){
			this.spriteset.put(spritenames.get(i).substring(0,spritenames.get(i).length()-4), SpriteStore.get().getSprite(folder + spritenames.get(i)));
		}
		this.x = x;
		this.y = y;	
	}
	
	//public void move(long delta) {
		// D�place l'entit� en tenant compte du delta (timing du worldStep)
		//x += (delta * dx) / 1000;
		//y += (delta * dy) / 1000;
	//}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean collidesWith(Entity other) { //M�thode de collisions
		hitbox.setBounds((int)x, (int)y, spriteset.get(state).getWidth(), spriteset.get(state).getHeight());
		compareHitbox.setBounds((int)other.x, (int)other.y, other.spriteset.get(state).getWidth(), other.spriteset.get(state).getHeight());

		return hitbox.intersects(compareHitbox);
	}
	
	public abstract void collidedWith(Entity other);
	
	public abstract void draw(Graphics g);
	
	public abstract void step(long delta, int[][] tileMatrix);
	
}
