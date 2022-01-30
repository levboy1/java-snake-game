package snakegame;

import java.io.Serializable;
import java.util.Random;
import javax.swing.ImageIcon;

public class Apple extends DrawableSquaredImage implements Serializable {
	private static final long serialVersionUID = -5781734002589081848L;
	int maxX, maxY;
	
	public Apple(int maxX, int maxY) {
		img = new ImageIcon("apple.png");
		this.x = 500;
		this.y = 500;		
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public void nextPostion() {
		int newX, newY, imgSize;
		
		imgSize = img.getIconWidth();
		
		Random random = new Random();
		int r = random.nextInt();
		r = Math.abs(r);
		newX = r % ((maxX / imgSize));
		
		r = random.nextInt();
		r = Math.abs(r);
		newY = r % ((maxY / imgSize));
		
		newX *= imgSize;
		newY *= imgSize;
		
		this.x = newX;
		this.y = newY;
	}
	
	public void loadImage() {
		img = new ImageIcon("apple.png");
	}
	
	public void debug() {
		System.out.println();
		System.out.println("APPLE DEBUG");
		System.out.println("-------------------------------------------------------");
		System.out.println();
		
		System.out.println("x: " + x);
		System.out.println("y: " + y);
		System.out.println("maxX: " + maxX);
		System.out.println("maxY: " + maxY);
		System.out.println("imgsize: " + imgSize);
		
		String s = img == null ? "null" : "not null";
		System.out.println("img state: " + s);
	}
}
