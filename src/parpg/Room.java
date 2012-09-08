package parpg;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
	private String name;
	//Taille différentes peut-etre un jour, mais pour l'instant les salles seront toutes 25x20 cases entourées de murs.
	private int width;
	private int height;
	//private Floor master; //L'étage dont la salle fait partie
	private int[][] tileMatrix; //La map comme tel
	/*
	 0 = Bloc
	 1 = Plancher
	 2 = Eau
	 3 = Eau avec top
	 4 = Mur haut B
	 5 = Mur haut T
	 6 = Mur bas B
	 7 = Mur bas T
	 8 = Mur gauche L
	 9 = Mur gauche R
	 10 = Mur droit L
	 11 = Mur droit R
	 12 = Coin bas-gauche BL
	 13 = Coin bas-gauche BR
	 14 = Coin bas-gauche TL
	 15 = Coin bas-gauche TR
	 16 = Coin bas-droit BL
	 17 = Coin bas-droit BR
	 18 = Coin bas-droit TL
	 19 = Coin bas-droit TR
	 20 = Coin haut-gauche TR
	 21 = Coin haut-gauche BR
	 22 = Coin haut-gauche TL
	 23 = Coin haut-gauche BL
	 24 = Coin haut-droit BL
	 25 = Coin haut-droit BR
	 26 = Coin haut-droit TL
	 27 = Coin haut-droit BL
	 */
	//private ArrayList spawnList; //La liste d'entitées à spawner dans la salle
	private Tileset tileset;
	
	//Constructeur par défaut, salle vide. (Pour le test, la salle ne sera pas juste vide.)
	public Room(String name, Tileset tileset, int width, int height){ 
		if(width > 32)
			this.width = 32;
		else if(width < 5)
			this.width = 5;
		else
			this.width = width;
		if(height > 21)
			this.height = 21;
		else if(height < 5)
			this.height = 5;
		else
			this.height = height;
		
		this.name = name;
		this.tileset = tileset;
		this.tileMatrix = new int[height][width];
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				tileMatrix[i][j] = 1;
			}
		}		
	}
	
	//Constructeur qui passe une matrice
	public Room(String name, Tileset tileset, int[][] tileMatrix){ 
		this.name = name;
		this.tileset = tileset;
		if(tileMatrix.length < 4 || tileMatrix.length > 32)
			fail("Salle trop grande ou trop petite horizontalement.");
		if(tileMatrix[0].length < 4 || tileMatrix[0].length > 21)
			fail("Salle trop grande ou trop petite verticalement.");
		this.width = tileMatrix[0].length;
		this.height = tileMatrix.length;
		this.tileMatrix = tileMatrix;
	}
	
	public int[][] getTileMatrix() {
		return tileMatrix;
	}

	public void draw(Graphics g){
		int top = 96 + (32 * (21 - height) / 2);
		int left = 0 + (32 * (32 - width) / 2);
		
		//Tuiles
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				tileset.draw(g, "" + tileMatrix[i][j], left+(j*32), top+(i*32));
			}
		}
	}
	
	private void fail(String message) { 
		System.err.println(message);
		System.exit(0);
	}
}
