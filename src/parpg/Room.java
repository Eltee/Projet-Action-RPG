package parpg;

import java.awt.Graphics;
import java.util.ArrayList;

public class Room {
	private String name;
	//Taille différentes peut-etre un jour, mais pour l'instant les salles seront toutes 25x20 cases entourées de murs.
	private int width = 28;
	private int height = 17;
	//private Floor master; //L'étage dont la salle fait partie
	private int[][] tileMatrix = new int[width][height]; //La map comme tel
	//private ArrayList spawnList; //La liste d'entitées à spawner dans la salle
	private Tileset tileset;
	
	//Constructeur par défaut, salle vide. (Pour le test, la salle ne sera pas juste vide.)
	public Room(String name, Tileset tileset){ 
		this.name = name;
		this.tileset = tileset;
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				tileMatrix[i][j] = 0;
			}
		}
		tileMatrix[12][4] = 11;
		tileMatrix[12][5] = 10;
		tileMatrix[12][6] = 10;
		tileMatrix[15][5] = 11;
		tileMatrix[14][5] = 11;
		tileMatrix[13][5] = 11;
		tileMatrix[15][6] = 10;
		tileMatrix[14][6] = 10;
		tileMatrix[13][6] = 10;
		tileMatrix[13][7] = 10;
		
		tileMatrix[5][12] = 9;
		tileMatrix[3][16] = 9;
		tileMatrix[21][4] = 9;
		tileMatrix[26][3] = 9;
		tileMatrix[10][10] = 9;
		
	}
	
	//Constructeur qui passe une matrice
	public Room(String name, Tileset tileset, int[][] tileMatrix){ 
		this.name = name;
		this.tileset = tileset;
		this.tileMatrix = tileMatrix;
	}

	public void draw(Graphics g){
		int top = 160;
		int left = 64;
		
		//Murs haut
		for(int i=0; i<width; i++){
			tileset.draw(g, 1, left+(i*32), top-64);
		}
		
		//Murs bas
		for(int i=0; i<width; i++){
			tileset.draw(g, 3, left+(i*32), 704);
		}
		
		//Murs gauche
		for(int i=0; i<height; i++){
				tileset.draw(g, 2, 0, top+(i*32));
		}
		
		//Murs droite
		for(int i=0; i<height; i++){
				tileset.draw(g, 4, 960, top+(i*32));
		}
		
		//Coin haut-gauche
		tileset.draw(g, 5, 0, top-64);
		
		//Coin haut-droite
		tileset.draw(g, 7, 960, top-64);
		
		//Coin bas-gauche
		tileset.draw(g, 6, 0, 704);
		
		//Coin bas-droite
		tileset.draw(g, 8, 960, 704);
		
		//Tuiles
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				tileset.draw(g, tileMatrix[i][j], left+(i*32), top+(j*32));
			}
		}
	}
}
