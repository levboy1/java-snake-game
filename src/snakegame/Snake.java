package snakegame;

import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class Snake implements Serializable {
	private static final long serialVersionUID = -4117618268509436441L;
	private ArrayList<SnakePart> parts;
	private Direction direction;
	private int startLength;
	private int minX, minY;
	private int maxX, maxY;
	
	public Snake(int maxX, int maxY, int snakeStartLength) {
		this.minX = 0;
		this.minY = 0;	
		this.maxX = maxX;
		this.maxY = maxY;
		
		parts = new ArrayList<SnakePart>();
		direction = Direction.RIGHT;
		startLength = snakeStartLength;
		
		this.initSnake();
	}
	
	private void initSnake() {
		SnakePart head = new SnakePart();
		head.initAsHead(300, 200);
		parts.add(head);
		
		for(int i = 0; i < startLength - 1; i++) {
			SnakePart last = parts.get(parts.size() - 1);
			int delta = last.getImgSize();
			
			SnakePart newPart = new SnakePart();
			newPart.initAsBody(last.getX() - delta, last.getY());
			
			parts.add(newPart);
		}
		
	}
	
	public void drawSnake(Component c, Graphics g) {
		for(int i = 0; i < parts.size(); i++) {
			SnakePart p = parts.get(i);
			p.draw(c, g);
		}
	}
	
	public void grow() {
		SnakePart newPart = new SnakePart();	
		SnakePart last = parts.get(parts.size() - 1);
		SnakePart secondLast = parts.get(parts.size() - 2);
		int delta = last.getImgSize();
		
		int newPartX = 0, newPartY = 0;
		
		if(secondLast.getX() > last.getX())
			newPartX = last.getX() - delta;
			
		if(secondLast.getX() < last.getX()) 
			newPartX = last.getX() + delta;
			
		if(secondLast.getX() == last.getX())
			newPartX = last.getX();
		
		if(secondLast.getY() < last.getY()) 
			newPartY = last.getY() + delta;
			
		if(secondLast.getY() > last.getY()) 
			newPartY = last.getY() - delta;
			
		if(secondLast.getY() == last.getY()) 
			newPartY = last.getY();
		
		newPart.initAsBody(newPartX, newPartY);
		parts.add(newPart);
	}
	
	public void step() {
		for(int i = parts.size() - 1; i >= 1; i--) {
			SnakePart current = parts.get(i);
			SnakePart next = parts.get(i - 1);
			
			int x = next.getX();
			int y = next.getY();
			
			current.setX(x);
			current.setY(y);
		}
		
		SnakePart head = parts.get(0);
		int oldX = head.getX();
		int oldY = head.getY();
		
		int delta = head.getImgSize();
		
		switch(direction) {
			case UP: {
				head.setY(oldY - delta);
				break;
			}
				
			case LEFT: {
				head.setX(oldX - delta);
				break;
			}
				
			case DOWN: {
				head.setY(oldY + delta);
				break;
			}
			
			case RIGHT: {
				head.setX(oldX + delta);
				break;
			}
		}
	}
	
	public void setDirection(Direction d) {
		direction = d;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public boolean AppleHit(Apple a) {
		boolean appleHit = false;
		SnakePart head = parts.get(0);
		
		if( head.getX() == a.getX() && head.getY() == a.getY() ) {
			appleHit = true; 
			
			do {
				a.nextPostion();
			} while(samePositonWith(a));
				
			this.grow();
		}
		
		return appleHit;
	}
	
	private boolean samePositonWith(Apple a) {
		boolean samePositon = false;
		
		for(int i = 0; i < parts.size(); i++) {
			SnakePart p = parts.get(i);
			
			if( p.getX() == a.getX() && p.getY() == a.getY() )
				samePositon = true;
		}
		
		return samePositon;
	}
	
	public boolean checkSelfHit() {
		boolean selfHit = false;
		SnakePart head = parts.get(0);
		int headX, headY;
		
		headX = head.getX();
		headY = head.getY();
		
		for(int i = 1; i < parts.size(); i++) {
			SnakePart p =  parts.get(i);
			int pX, pY;
			
			pX = p.getX();
			pY = p.getY();
			
			if((pX == headX) && (pY == headY))
				selfHit = true;
		}
		
		return selfHit;
	}
	
	public boolean isHeadOutOfPanel() {
		boolean headOutOfPanel = false;
		
		SnakePart head = parts.get(0);
		SnakePart p1 = parts.get(1);
		
		if(head.getX() >= maxX) {
			headOutOfPanel = true;
		}
		
		if(head.getX() == minX && p1.getX() == minX && direction == Direction.LEFT) {
			headOutOfPanel = true;
		}
		
		if(head.getY() >= maxY) {
			headOutOfPanel = true;
		}
		
		if(head.getY() == minY && p1.getY() == minY && direction == Direction.UP) {
			headOutOfPanel = true;
		}
		
		return headOutOfPanel;
	}
	
	public int getStartLength() {
		return startLength;
	}
	
	public int getLength() {
		return parts.size();
	}
	
	public int headPosX() {
		SnakePart head = parts.get(0);
		return head.getX();
	}
	
	public int headPosY() {
		SnakePart head = parts.get(0);
		return head.getY();
	}
	
	public void loadImage() {
		SnakePart head = parts.get(0);
		head.loadIMG("snakehead.png");
		
		for(int i = 1; i < parts.size(); i++) {
			SnakePart p = parts.get(i);
			p.loadIMG("snakebody.png");
		}
	}
	
	public void debug() {
		System.out.println();
		System.out.println("SNAKE DEBUG");
		System.out.println("-------------------------------------------------------");
		System.out.println();
		
		System.out.println("minX: " + minX);
		System.out.println("minY: " + minY);
		System.out.println("maxX: " + maxX);
		System.out.println("maxY: " + maxY);
		System.out.println("length: " + parts.size());
		System.out.println("starlength: " + startLength);
		System.out.println("direction: " + direction);
		
		for(int i = 0; i < parts.size(); i++) {
			SnakePart p = parts.get(i);
			p.debug();
		}
		
	}

	public void setStartLength(int snakeStartLength) {
		startLength = snakeStartLength;
	}
}
