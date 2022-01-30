package snakegame;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class SnakePart extends DrawableSquaredImage implements Serializable {
	private static final long serialVersionUID = -7144896286413739620L;

	public SnakePart() { }
	
	public SnakePart(String path, int x, int y) {
		init(path, x, y);
	}
	
	private void init(String path, int x, int y) {
		this.x = x;
		this.y = y;
		img = new ImageIcon(path);
		imgSize = img.getIconHeight();
	}
	
	public void initAsHead(int x, int y) {
		init("snakehead.png", x, y);
	}
	
	public void initAsBody(int x, int y) {
		init("snakebody.png", x, y);
	}
	
	public void debug() {
		System.out.println();
		System.out.println("x: " + x);
		System.out.println("y: " + y);		
		System.out.println("imgsize: " + imgSize);
		
		String s = img == null ? "null" : "not null";
		System.out.println("img state: " + s);
	}
}
