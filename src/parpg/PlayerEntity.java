package parpg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class PlayerEntity extends Entity {
	private String name;
	private int level;
	private int hp;
	private int mp;
	private int statHP;
	private int statMP;
	private int statSTR;
	private int statDEF;
	private int statMAG;
	//private Hashtable<String, Entity> inventory;
	//private WeaponEntity equipWeapon;
	//private ArmorEntity equipArmor;
	//private AccessoryEntity equipAccessory;
	//private Hashtable<String, SpellEntity> spells;
	//private SpellEntity equipSpell;
	private String state;
	//private Arraylist statusEffects;
	private int attributePoints;
	private int directionFacing;
	private double velX = 0.0;
	private double velY = 0.0;
	private double maxVel;
	private double speed;

	public PlayerEntity(ArrayList<String> spritenames, int x, int y, double maxVel, double speed) {
		super(spritenames, "Ressources/Sprites/Player/", x, y);
		this.maxVel = maxVel;
		this.speed = speed;
		hitbox = new Rectangle();
		hitbox.setBounds(x+6,y+10,23,25);
	}

	public void collidedWith(Entity other) {
		
	} 
	
	public void draw(Graphics g){
		this.spriteset.get("playerFrontA").draw(g, x, y);
		g.setColor(Color.RED); //Pour dessiner son hitbox..
		g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
	
	public void moveX(String side){
		if(side == "right"){
			
			if((velX += speed) < maxVel){
				velX += speed;
			}
			
			else{ 
				velX = maxVel;
			}
			
		}
		else{
			
			if((velX -= speed) > -maxVel){ 
				velX -= speed;
			}
			
			else{ 
				velX = -maxVel;
			}
			
		}
	}
	
	public void moveY(String side){
		if(side == "down"){
			
			if((velY += speed) < maxVel){
				velY += speed;
			}
			
			else{ 
				velY = maxVel;
			}
			
		}
		else{
			
			if((velY -= speed) > -maxVel){ 
				velY -= speed;
			}
			
			else{ 
				velY = -maxVel;
			}
			
		}		
	}
	
	public void move(long delta, int[][] tileMatrix){

		System.out.println(mapCollisions(x + ((delta/10) * velX), y + ((delta/10) * velY), tileMatrix));

		if(velX > 0){ 
			x += ((delta/10) * velX);

			if((velX - (speed / 4 )) > 0){ 
				velX -= (speed / 4 );
			}
			else{ 
				velX = 0;
			}

		}
		else if(velX < 0){ 
			x += ((delta/10) * velX);

			if((velX + (speed / 4 )) < 0){ 
				velX += (speed / 4 );
			}
			else{ 
				velX = 0;
			}
		}

		if(this.velY > 0){ 
			y += ((delta/10) * velY);

			if((velY - (speed / 4 )) > 0){ 
				velY -= (speed / 4 );
			}
			else{ 
				velY = 0;
			}
		}
		else if(velY < 0){ 
			y += ((delta/10) * velY);

			if((velY + (speed / 4 )) < 0){ 
				velY += (speed / 4 );
			}
			else{ 
				velY = 0;
			}
		}
	}
	
	public void updateHitbox(){
		hitbox.setBounds(x+6,y+10,23,25);
	}
	
	public boolean mapCollisions(double dx, double dy, int[][] tileMatrix){

		int top = 96 + (32 * (21 - tileMatrix.length) / 2);
		int left = 0 + (32 * (32 - tileMatrix[0].length) / 2);

		//System.out.println("Case X: " + Math.round((dx / 32) - (left / 32)) + "  Case Y: " + Math.round((dy / 32) - (top / 32)) );
		try{
			if(tileMatrix[(int) Math.round((dy / 32) - (top / 32))][(int) Math.round((dx / 32) - (left / 32))] != 1)
				return false;
			else
				return true;
		}
		catch(ArrayIndexOutOfBoundsException e){
			return true;
		}

	}
	
	public void step(long delta, int[][] tileMatrix){
		move(delta, tileMatrix);
		updateHitbox();
	}

}
