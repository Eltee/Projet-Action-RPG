package parpg;

import java.awt.Graphics;
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
	private double speed;
	private double maxVel;

	public PlayerEntity(ArrayList<String> spritenames, int x, int y) {
		super(spritenames, "Ressources/Sprites/Player/", x, y);
	}

	public void collidedWith(Entity other) {
		
	} 
	
	public void draw(Graphics g){
		this.spriteset.get("playerFrontA").draw(g, x, y);
	}

}
