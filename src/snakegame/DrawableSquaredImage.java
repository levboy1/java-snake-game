package snakegame;

import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class DrawableSquaredImage implements Serializable {
	private static final long serialVersionUID = -6185829392621311662L;
	protected ImageIcon img;
	protected int x, y;
	protected int imgSize;
	
	public DrawableSquaredImage() {	}
	
	public void draw(Component c, Graphics g) {
		img.paintIcon(c, g, x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		if(x >= 0) 
			this.x = x;
	}
	
	public void setY(int y) {
		if(y >= 0)
			this.y = y;
	}
	
	public int getImgSize() {
		return imgSize;
	}
	
	public void loadIMG(String path) {
		img = new ImageIcon(path);
	}
}
