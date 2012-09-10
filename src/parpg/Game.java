package parpg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {
	
	/** Stratégie graphique qui améliore la vitesse d'affichage */
	private BufferStrategy strategy;
	/** Dénote si le jeu est en marche */
	private boolean gameRunning = true;
	/** Liste des entités active */
	//private ArrayList<Entity> entities = new ArrayList<Entity>();
	/** Liste des entités enlever */
	private Hashtable<String, Entity> removeList = new Hashtable<String, Entity>();
	/** L'entité du joueur */
	private PlayerEntity player;
	/** Booleen si le loop doit faire quelque chose de particulier cette fois-ci */
	private boolean logicRequiredThisLoop = false;
	/** Hashtable contenant la liste des donjons */
	//private Hashtable<String, Dungeon> dungeons = new Hashtable<String,Dungeon>();
	/** Hashtable contenant la liste des tilesets */
	private Hashtable<String, Tileset> tilesets = new Hashtable<String,Tileset>();
	/** Message à afficher */
	private String message = "Press any key";
	/** Résolution */
	private int resX = 1024;
	private int resY = 768;
	/** Affichage */
	private JFrame container;
	private JPanel panel;
	/** Variable d'état du jeu (permet de trier le worldStep) */
	private String gameState = "Init";
	
	/** Variables de test */
	private Room currentRoom;
	
	/** Variables de contrôles */
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	
	public Game() {

		container = new JFrame("Projet Action-RPG");

		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(resX,resY));
		panel.setLayout(null);

		setBounds(0,0,resX,resY);
		panel.add(this);
	
		// Ignore le repaint automatique; sera fait manuellement

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Le systï¿½me de contrï¿½les

		addKeyListener(new KeyInputHandler());

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
	}
	
	private void init() { //En ce moment, ne fait que loader les tilesets (noms de tuiles, references URL)
		loadTilesets();
		int testArray[][] = 
			{ {22,23,5,5,5,5,5,5,5,5,5,5,5,5,5,5,26,27},
              {20,21,4,4,4,4,4,4,4,4,4,4,4,4,4,4,24,25},
              {8,9,0,1,0,1,1,1,1,1,1,1,1,1,1,1,10,11},
              {8,9,1,0,1,1,1,1,1,1,1,1,3,3,3,1,10,11},
              {8,9,0,1,0,1,1,1,1,1,1,1,2,2,2,1,10,11},
              {8,9,1,1,1,1,1,1,1,1,1,1,1,2,1,1,10,11},
              {8,9,1,1,1,1,1,1,1,1,1,1,3,2,3,1,10,11},
              {8,9,1,1,1,1,1,1,1,1,1,1,1,2,1,1,10,11},
              {14,15,7,7,7,7,7,7,7,7,7,7,7,7,7,7,18,19},
              {12,13,6,6,6,6,6,6,6,6,6,6,6,6,6,6,16,17}
		    };
		currentRoom = new Room("Test", tilesets.get("Default"), testArray);
		int[] tile = {8,4};
		int[] tempoXY = currentRoom.getTileXY(tile);
		ArrayList<String> sprites = new ArrayList<String>();
		sprites.add("playerFrontA.png");
		player = new PlayerEntity(sprites, tempoXY[0], tempoXY[1], 2.0, 0.5);
		gameState = "Ingame";
	}
	
	private void loadTilesets() {
		File dir = new File("bin/Ressources/Tilesets");
		File[] children = dir.listFiles();
		if (children == null) {
			fail("Impossible de trouver le dossier");
		} 
		else if(children.length == 0){
			fail("Aucun dossier tileset prÃ©sent.");
		}
		else {
		    for (int i=0; i<children.length; i++) {
		    	if(children[i].isDirectory()){
			    	Tileset tempo = new Tileset(children[i].getName());
			    	tilesets.put(children[i].getName(), tempo);
		    	}
		    }
		}
	}
	
	/*private void changeRes(int resX, int resY){
		this.resX = resX;
		this.resY = resY;
		panel.setPreferredSize(new Dimension(resX,resY));
		setBounds(0,0,resX,resY);
		container.pack();
	}*/
	
	public void worldStep() {
		
		long lastStepTime = System.currentTimeMillis();

		while (gameRunning) {

			//Le delta sert à calculer le temps entre les steps
			
			long delta = System.currentTimeMillis() - lastStepTime;
			lastStepTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, resX, resY);
			
			if(gameState == "Init"){
				g.setColor(Color.white);
				g.drawString(message,(resX-g.getFontMetrics().stringWidth(message))/2,resY-(resY/4));
			}
			else if(gameState == "Ingame"){
				g.setColor(Color.gray); //Margin top
				g.fillRect(0, 0, resX, 96);
				
				if(currentRoom != null){
					currentRoom.draw(g);
				}
				
				if(player != null){
					if(upPressed)
						player.moveY("up");
					if(downPressed)
						player.moveY("down");
					if(leftPressed)
						player.moveX("left");
					if(rightPressed)
						player.moveX("right");
					//System.out.println("X: " + player.x + "  Y: " + player.y);
					player.step(delta, currentRoom.getTileMatrix());
					player.draw(g);
				}
			}
			
			
			
			
			
			// On se debarasse du Graphics2D pour liberer les ressources 

			g.dispose();
			strategy.show();
			
			// Dï¿½lai entre chaque step; en thï¿½orie le dï¿½lai devrait faire fonctionner le jeu a 100 FPS.

			try { 
				Thread.sleep(10); 
			} 
			catch (Exception e){
			}
		}
	}

	private class KeyInputHandler extends KeyAdapter {
		// Nombre de touches
		private int pressCount = 0;
		
		public void keyPressed(KeyEvent e) {
			// On saute les keyPressed si on attend d'appuyer sur un bouton
			if (gameState == "Init") {
				return;
			}
			else{
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downPressed = true;
				}
			}
		} 
		
		public void keyReleased(KeyEvent e) {
			// On saute les keyReleased si on attend d'appuyer sur un bouton
			if (gameState == "Init") {
				return;
			}
			else{
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upPressed = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downPressed = false;
				}
			}
		}

		public void keyTyped(KeyEvent e) {

			if (gameState == "Init") {
				if (pressCount == 0) {
					pressCount++;
					message = "Please wait, now loading..";
					init();
				} 
				else {
					pressCount++;
				}
			}

			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	
	private void fail(String message) { 
		System.err.println(message);
		System.exit(0);
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		g.worldStep();
	}

}
