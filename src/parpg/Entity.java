package parpg;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Entity {
	private int x;
	private int y;
	private Hashtable<String, Sprite> spriteset;
	private double dx;
	private double dy;
	private Rectangle hitbox;
	private Rectangle compareHitbox;
	private boolean respawn;
	private double delay;
	private String state;
	
	public Entity(ArrayList<String> spritenames, int x, int y){
		for(int i = 0; i < spritenames.size(); i++){
			this.spriteset.put(spritenames.get(i), SpriteStore.get().getSprite(spritenames.get(i))); //Temporaire, permet d'avoir seulement une sprite dans le spriteset..
		}
		this.x = x;
		this.y = y;	
	}
	
	public void move(long delta) {
		// D�place l'entit� en tenant compte du delta (timing du worldStep)
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}
	
	public void setMoveX(double dx) {
		this.dx = dx;
	}

	public void setMoveY(double dy) {
		this.dy = dy;
	}
	
	public double getMoveX() {
		return dx;
	}

	public double getMoveY() {
		return dy;
	}
	
	public void draw(Graphics g) {
		//spriteset.get(state).draw(g,(int) x,(int) y);
	}
	
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
	
}
