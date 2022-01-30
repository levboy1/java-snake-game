package snakegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Program {
	private JFrame frame;
	private JPanel mainPanel;
	private GamePanel gamePanel;
	private int frameWidth;
	private int frameHeight;
	private int snakeStartLength;
	private double gameSpeed;
	
	public Program() {
		frame = new JFrame();
		mainPanel = new JPanel();
		
		frameWidth = 400;
		frameHeight = 600;	
		snakeStartLength = 3;
		gameSpeed = 1.0;
		
		gamePanel = new GamePanel(800, 600, snakeStartLength, gameSpeed);
	}
	
	
	
	private void initFrame() {
		frame.setSize(new Dimension(frameWidth, frameHeight));
		frame.setResizable(false);
		frame.setTitle("Snake by Lev");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initMainPanel() {	
		ImageIcon titleicon = new ImageIcon("titleicon.png");
		JLabel label =  new JLabel(titleicon);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(label, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.Y_AXIS));
		
		JButton b1, b2, b3, b4;
		
		b1 = new JButton("Start new game");
		b2 = new JButton("Load game");
		b3 = new JButton("Change settings");
		b4 = new JButton("Exit to desktop");
		
		b1.setActionCommand("new");
		b2.setActionCommand("load");
		b3.setActionCommand("settings");
		b4.setActionCommand("exit");
		
		b1.addActionListener(new MainMenuButtonListener(this));
		b2.addActionListener(new MainMenuButtonListener(this));
		b3.addActionListener(new MainMenuButtonListener(this));
		b4.addActionListener(new MainMenuButtonListener(this));
		
		b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        b3.setAlignmentX(Component.CENTER_ALIGNMENT);
        b4.setAlignmentX(Component.CENTER_ALIGNMENT);
		
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		buttonPanel.add(b1);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		buttonPanel.add(b2);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		buttonPanel.add(b3);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		buttonPanel.add(b4);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		buttonPanel.setBackground(Color.BLACK);	
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
	}
	
	private void initGUI() {
		initFrame();
		initMainPanel();
		
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void startNewGame() {
		gamePanel = new GamePanel(800, 600, snakeStartLength, gameSpeed);
		showGamePanel();
		gamePanel.requestFocus();
		
		Thread t = new Thread(gamePanel);
		t.start();
	}
	
	private void showGamePanel() {
		frame.setVisible(false);
		frame.remove(mainPanel);
		
		frameWidth = gamePanel.getWidth();
		frameHeight = gamePanel.getHeight() + gamePanel.getTextPanelHeight();	
		frame.setSize(frameWidth, frameHeight);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(gamePanel.getTextPanel(), BorderLayout.NORTH);
		mainPanel.add(gamePanel, BorderLayout.CENTER);
		
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void loadGame() {
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setCurrentDirectory(new File("/home/me/Documents"));
	    int returnValue  = fileChooser.showOpenDialog(null);
	    
	    if (returnValue  == JFileChooser.APPROVE_OPTION) {
	        try {
	            File selectedFile = fileChooser.getSelectedFile();
	            FileInputStream f = new FileInputStream(selectedFile);
	            ObjectInputStream in = new ObjectInputStream(f);    
	            GamePanel g = (GamePanel)in.readObject();
	            gamePanel = g;            
	            in.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        
	        gamePanel.loadImages();
	        gamePanel.setPaused(false);
	        showGamePanel();
			gamePanel.requestFocus();
			
			Thread t = new Thread(gamePanel);
			t.start();
	    }
	}
	
	public void changeSettings() {
		JDialog d = new JDialog(new JFrame(), "Change settings");	
		initChangeSettingsDialog(d);
	}
	
	private void initChangeSettingsDialog(JDialog d) {
		int dialogWidth, dialogHeight;
		dialogWidth = 180;
		dialogHeight = 220;
		
		d.setPreferredSize(new Dimension(dialogWidth, dialogHeight));
        d.setResizable(false);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        d.setLayout(new BoxLayout(d.getContentPane(), BoxLayout.Y_AXIS));
        d.setAlwaysOnTop(true);
        
        JLabel lengthLabel = new JLabel("Snake starting length");
        JLabel gameSpeedLabel = new JLabel("Game speed");
        
        JComboBox<Integer> lengthComboBox = new JComboBox<Integer>();
        lengthComboBox.addItem(3);
        lengthComboBox.addItem(4);
        lengthComboBox.addItem(5);
        lengthComboBox.addItem(6);
        
        JComboBox<Double> gameSpeedComboBox = new JComboBox<Double>();
        gameSpeedComboBox.addItem(0.75);
        gameSpeedComboBox.addItem(1.0);
        gameSpeedComboBox.addItem(1.25);
        gameSpeedComboBox.addItem(1.5);
        
        JButton applyButton = new JButton("Apply changes");
        
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(lengthLabel);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(lengthComboBox);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(gameSpeedLabel);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(gameSpeedComboBox);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        d.add(applyButton);
        d.add(Box.createRigidArea(new Dimension(0, 10)));
        
        lengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameSpeedComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        applyButton.addActionListener(actionEvent -> {
        	snakeStartLength = (int)lengthComboBox.getSelectedItem();
        	gameSpeed = (double)gameSpeedComboBox.getSelectedItem();
        	d.dispose();
        });
        
        d.pack();
        d.setLocationRelativeTo(d.getContentPane());
        d.setVisible(true);
	}
	
	public void start() {
		initGUI();
	}
}
