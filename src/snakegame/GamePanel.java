package snakegame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, Serializable {
	private static final long serialVersionUID = 8247714953091137596L;
	private Snake snake;
	private Apple apple;
	private boolean running;
	private boolean paused;
	private boolean keyPressEnabled;
	private boolean playAgain;
	private TextPanel textPanel;
	private int gamePanelWidth;
	private int gamePanelHeight;
	private int score;
	private int delay;
	
	public GamePanel(int width, int height, int snakeStartLength, double gameSpeed) {
		super();
		
		gamePanelWidth = width;
		gamePanelHeight = height;
		
		score = 0;
		delay = 200 - (int)(gameSpeed * 100);
		
		snake = new Snake(gamePanelWidth, gamePanelHeight, snakeStartLength);
		apple = new Apple(gamePanelWidth, gamePanelHeight);
	
		running = true;
		paused = false;
		keyPressEnabled = true;
		playAgain = false;
		
		textPanel = new TextPanel(gamePanelWidth, 30, snake.getStartLength());
		
		this.setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(1, 1));
		
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new GamePanelKeyListener(this));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		apple.draw(this, g);
		snake.drawSnake(this, g);
	}
	
	
	public void runGame() throws InterruptedException {
		do {
			getReadyForGame();
			
			while(running) {
				Thread.sleep(delay);
				
				if(paused)
					pauseMenu();
				
				snake.step();
				
				if(snakeCollide())
					break;
				
				checkAppleHit();
				
				repaint();
				requestFocus();
				this.setKeyPressEnabled(true);
			}
			
			gameOver();
		} while(playAgain);
	}
	
	private void getReadyForGame() throws InterruptedException {
		for(int i = 5; i > 0; i--) {
			textPanel.setGameStateMessage("Get ready! Game starts in " + i + " seconds.");
			Thread.sleep(1000);
		}
		
		textPanel.setGameStateMessage("Game started. Good luck & have fun!");
	}
	
	private void pauseMenu() throws InterruptedException {
        textPanel.setGameStateMessage("Game paused.");
		
		JDialog d = new JDialog(new JFrame(), "Game paused");
		this.initPauseMenuDialog(d);
        
        while(this.paused) {
        	Thread.sleep(500);
        	
        	if(!d.isDisplayable()) {
        		for(int i = 5; i > 0; i--) {
        			textPanel.setGameStateMessage("Game continues in " + i + " seconds.");
        			Thread.sleep(1000);
        		}
        		
        		this.paused = false;
        	}
        }
        
        textPanel.setGameStateMessage("Game ongoing.");
	}
	
	private void initPauseMenuDialog(JDialog d) {
		int dialogWidth, dialogHeight;
		dialogWidth = 180;
		dialogHeight = 160;
		
		d.setPreferredSize(new Dimension(dialogWidth, dialogHeight));
        d.setResizable(false);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        d.setLayout(new BoxLayout(d.getContentPane(), BoxLayout.Y_AXIS));
        d.setAlwaysOnTop(true);
        
        JButton b1 = new JButton("Continue game");
        b1.setActionCommand("continue");
        b1.addActionListener(new PauseMenuListener(d, this));
        
        JButton b2 = new JButton("Save game");
        b2.setActionCommand("save");
        b2.addActionListener(new PauseMenuListener(d, this));
        
        JButton b3 = new JButton("Exit without saving");
        b3.setActionCommand("exit");
        b3.addActionListener(new PauseMenuListener(d, this));
        
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(b1);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(b2);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(b3);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        b3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        d.pack();
        d.setLocationRelativeTo(d.getContentPane());
        d.setVisible(true);
	}
	
	private boolean snakeCollide() {
		boolean selfHit = snake.checkSelfHit();
		boolean wallHit = snake.isHeadOutOfPanel();
		
		boolean collide = selfHit || wallHit;
		
		if(collide) {
			running = false;
		}
		
		return collide;
	}
	
	private void checkAppleHit() {
		if(snake.AppleHit(apple)) {
			score += 10;
			
			textPanel.setScore(score);
			textPanel.setLength(snake.getLength());
		}
	}
	
	public void setKeyPressEnabled(boolean keyPressEnabled) {
		this.keyPressEnabled = keyPressEnabled;
	}
	
	private void gameOver() throws InterruptedException {
		textPanel.setGameStateMessage("Game over. Better luck next time!");
		
		JDialog d = new JDialog(new JFrame(), "Game over.");
		this.initGameOverDialog(d);
		
		while(d.isVisible()) {
			Thread.sleep(500);
		}
	}
	
	private void initGameOverDialog(JDialog d) {
		int dialogWidth, dialogHeight;
		dialogWidth = 180;
		dialogHeight = 120;
		
		d.setPreferredSize(new Dimension(dialogWidth, dialogHeight));
        d.setResizable(false);
        d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        d.setLayout(new BoxLayout(d.getContentPane(), BoxLayout.Y_AXIS));
        d.setAlwaysOnTop(true);
        
        JButton b1 = new JButton("Start new game");
        b1.addActionListener(actionEvent -> {
        	this.playAgain();
        	d.dispose();
        });
        
        JButton b2 = new JButton("Return to main menu");
        b2.setActionCommand("exitToMainMenu");
        
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(b1);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(b2);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        d.pack();
        d.setLocationRelativeTo(d.getContentPane());
        d.setVisible(true);
	}
	
	
	private void playAgain() {
		this.playAgain = true;
		this.running = true;
		
		int snakeStartLength = snake.getStartLength();
		
		snake = new Snake(gamePanelWidth, gamePanelHeight, snakeStartLength);
		apple = new Apple(gamePanelWidth, gamePanelHeight);
		
		textPanel.setScore(0);
		textPanel.setLength(snakeStartLength);
		
		repaint();
	}
	
	public boolean isKeyPressEnabled() {
		return keyPressEnabled;
	}
	
	public TextPanel getTextPanel() {
		return textPanel;
	}
	
	public int getTextPanelHeight() {
		return textPanel.getHeight();
	}
		
	public boolean isGamePaused() {
		return paused;
	}
	
	public void pauseGame() {
		this.paused = true;
	}
	
	
	public int getHeight() {
		return gamePanelHeight;
	}
	
	public int getWidth() {
		return gamePanelWidth;
	}

	@Override
	public void run() {
		try {
			this.runGame();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

	public void saveGame() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setCurrentDirectory(new File("/home/me/Documents"));
	    int returnValue  = fileChooser.showSaveDialog(null);
	    
	    if (returnValue  == JFileChooser.APPROVE_OPTION) {
	        try {
	            FileOutputStream f = new FileOutputStream(fileChooser.getSelectedFile());
	            ObjectOutputStream out = new ObjectOutputStream(f);
	            
	            out.writeObject(this);
	            out.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	
	
	public void loadImages() {
		snake.loadImage();
		apple.loadIMG("apple.png");
	}
	
	public void debug() {
		System.out.println("GAMEPANEL DEBUG");
		System.out.println("-------------------------------------------------------");
		System.out.println();
		
		System.out.println("isRunning: " + running);
		System.out.println("isPaused: " + paused);
		System.out.println("isKeyPressEnabled: " + keyPressEnabled);
		System.out.println("score: " + score);
		System.out.println("delay: " + delay);
		
		snake.debug();
		apple.debug();
		textPanel.debug();
	}
	
	public void setPaused(boolean b)  {
		paused = b;
	}

	public Snake getSnake() {
		return snake;
	}
}


