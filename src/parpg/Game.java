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
	
	/** Strat�gie graphique qui am�liore la vitesse d'affichage */
	private BufferStrategy strategy;
	/** D�note si le jeu est en marche */
	private boolean gameRunning = true;
	/** Liste des entit�s active */
	//private ArrayList entities = new ArrayList();
	/** Liste des entit�s � enlever */
	//private ArrayList removeList = new ArrayList();
	/** L'entit� du joueur */
	//private Entity player;
	/** Booleen si le loop doit faire quelque chose de particulier cette fois-ci */
	private boolean logicRequiredThisLoop = false;
	/** Hashtable contenant la liste des donjons */
	//private Hashtable<String, Dungeon> dungeons = new Hashtable<String,Dungeon>();
	private Hashtable<String, Tileset> tilesets = new Hashtable<String,Tileset>();
	private String message = "";
	private boolean waitingForKeyPress = true;
	private int resX = 1024;
	private int resY = 768;
	private JFrame container;
	private JPanel panel;
	private Room testRoom;
	private PlayerEntity testPlayer;
	
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
		
		// Le syst�me de contr�les

		addKeyListener(new KeyInputHandler());

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
	}
	
	private void init() { //En ce moment, ne fait que loader les tilesets (noms de tuiles, references URL)
		loadTilesets();
		testRoom = new Room("Test", tilesets.get("Default"));
		ArrayList<String> sprites = new ArrayList<String>();
		sprites.add("playerFrontA.png");
		testPlayer = new PlayerEntity(sprites, 200, 200);
	}
	
	private void loadTilesets() {
		File dir = new File("bin/Ressources/Tilesets");
		File[] children = dir.listFiles();
		if (children == null) {
			fail("Impossible de trouver le dossier");
		} 
		else if(children.length == 0){
			fail("Aucun dossier tileset présent.");
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

			//Le delta sert � calculer le temps entre les steps
			
			long delta = System.currentTimeMillis() - lastStepTime;
			lastStepTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, resX, resY);
			
			g.setColor(Color.gray); //Margin top
			g.fillRect(0, 0, resX, 96);
			
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(resX-g.getFontMetrics().stringWidth(message))/2,resY-(resY/4));
				g.drawString("Press any key",(resX-g.getFontMetrics().stringWidth("Press any key"))/2,resY-(resY/4));
			}
			/*else {
				if(tilesets.size() > 0){
					g.setColor(Color.white);
					g.drawString(message,(resX-g.getFontMetrics().stringWidth(message))/2,resY-(resY/4));
					g.drawString("Number of tilesets loaded: " + tilesets.size(),(resX-g.getFontMetrics().stringWidth("Number of tilesets loaded: " + tilesets.size()))/2,resY-(resY/4));
				}
				else{
					g.setColor(Color.white);
					g.drawString(message,(resX-g.getFontMetrics().stringWidth(message))/2,resY-(resY/4));
					g.drawString("Or don't ;)",(resX-g.getFontMetrics().stringWidth("Or don't ;)"))/2,resY-(resY/4));
				}
				
			}*/
			
			if(testRoom != null){
				testRoom.draw(g);
			}
			if(testPlayer != null){
				if(upPressed)
					testPlayer.setMoveY(-1);
				else if(downPressed)
					testPlayer.setMoveY(1);
				else if(leftPressed)
					testPlayer.setMoveX(-1);
				else if(rightPressed)
					testPlayer.setMoveX(1);
				testPlayer.move(delta);
				testPlayer.draw(g);
			}
			else{
				g.setColor(Color.darkGray); //Murs top
				g.fillRect(0, 96, resX, 64);
				g.setColor(Color.darkGray); //Murs gauche
				g.fillRect(0, 96, 64, resY);
				g.setColor(Color.darkGray); //Murs droite
				g.fillRect(960, 96, 64, resY);
				g.setColor(Color.darkGray); //Murs bas
				g.fillRect(0, 704, resX, 64);
			}
			
			// On se debarasse du Graphics2D pour liberer les ressources 

			g.dispose();
			strategy.show();
			
			// D�lai entre chaque step; en th�orie le d�lai devrait faire fonctionner le jeu a 100 FPS.

			try { 
				Thread.sleep(10); 
			} 
			catch (Exception e){
			}
		}
	}

	private class KeyInputHandler extends KeyAdapter {
		// Nombre de touches
		private int pressCount = 1;
		
		public void keyPressed(KeyEvent e) {
			// On saute les keyPressed si on attend d'appuyer sur un bouton
			if (waitingForKeyPress) {
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
			if (waitingForKeyPress) {
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

			if (waitingForKeyPress) {
				if (pressCount == 1) {
					waitingForKeyPress = false;
					pressCount = 0;
					//changeRes(1024,768);
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
