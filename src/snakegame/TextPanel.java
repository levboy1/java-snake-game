package snakegame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextPanel extends JPanel implements Serializable {
	private static final long serialVersionUID = -4597018949109607486L;
	private int width;
	private int height;
	JTextField scoreTF;
	JTextField lengthTF;
	JTextField messageTF;
	
	public TextPanel(int width, int height, int startLength) {
		super();		
		this.width = width;
		this.height = height;		
		this.initTextPanel(startLength);
	}
	
	private void initTextPanel(int startLength) {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(width, height));
				
		JLabel scoreLabel = new JLabel("Score: ");
		JLabel lengthLabel = new JLabel("Snake length: ");
		JLabel messageLabel = new JLabel("Game state: ");
		
		scoreTF =  new JTextField(5);
		scoreTF.setText("0");
		scoreTF.setHorizontalAlignment(JTextField.CENTER);

		lengthTF =  new JTextField(5);
		lengthTF.setText(Integer.toString(startLength));
		lengthTF.setHorizontalAlignment(JTextField.CENTER);
		
		messageTF =  new JTextField(25);
		messageTF.setHorizontalAlignment(JTextField.CENTER);
		
		scoreTF.setEditable(false);
		lengthTF.setEditable(false);
		messageTF.setEditable(false);
		
		this.add(scoreLabel);
		this.add(scoreTF);
		this.add(lengthLabel);
		this.add(lengthTF);
		this.add(messageLabel);
		this.add(messageTF);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setScore(int score) {
		scoreTF.setText(Integer.toString(score));
	}
	
	public void setLength(int length) {
		lengthTF.setText(Integer.toString(length));
	}
	
	public void setGameStateMessage(String message) {
		messageTF.setText(message);
	}
	
	public void debug() {
		System.out.println();
		System.out.println("TEXTPANEL DEBUG");
		System.out.println("------------------------------------------");
		
		System.out.println("width: " + width);
		System.out.println("height: " + height);
		System.out.println("score: " + scoreTF.getText());
		System.out.println("length: " + lengthTF.getText());
		System.out.println("message: " + messageTF.getText());
	}
}
