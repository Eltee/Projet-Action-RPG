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
	//private ArrayList entities = new ArrayList();
	/** Liste des entités à enlever */
	//private ArrayList removeList = new ArrayList();
	/** L'entité du joueur */
	//private Entity player;
	/** Booleen si le loop doit faire quelque chose de particulier cette fois-ci */
	//private boolean logicRequiredThisLoop = false;
	/** Hashtable contenant la liste des donjons */
	//private Hashtable dungeons;
	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	
	public Game() {

		JFrame container = new JFrame("Projet Action-RPG");

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);

		setBounds(0,0,800,600);
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
		
		// Le système de contrôles

		addKeyListener(new KeyInputHandler());

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
	}
	
	private void init() {
		
	}
	
	public void worldStep() {
		
		long lastStepTime = System.currentTimeMillis();

		while (gameRunning) {

			//Le delta sert à calculer le temps entre les steps
			
			long delta = System.currentTimeMillis() - lastStepTime;
			lastStepTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
			}
			else {
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Or don't ;)",(800-g.getFontMetrics().stringWidth("Or don't ;)"))/2,300);
			}
			
			// On se debarasse du Graphics2D pour liberer les ressources 

			g.dispose();
			strategy.show();
			
			// Délai entre chaque step; en théorie le délai devrait faire fonctionner le jeu a 100 FPS.

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
		} 
		
		public void keyReleased(KeyEvent e) {
			// On saute les keyReleased si on attend d'appuyer sur un bouton
			if (waitingForKeyPress) {
				return;
			}
		}

		public void keyTyped(KeyEvent e) {

			if (waitingForKeyPress) {
				if (pressCount == 1) {
					waitingForKeyPress = false;
					init();
					pressCount = 0;
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		g.worldStep();
	}

}
