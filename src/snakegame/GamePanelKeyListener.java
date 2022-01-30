package snakegame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class GamePanelKeyListener extends KeyAdapter implements Serializable {
	private static final long serialVersionUID = 1289762503859653043L;
	private GamePanel gamePanel;
	
	public GamePanelKeyListener(GamePanel g) {
		gamePanel = g;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(gamePanel.isKeyPressEnabled()) {
			gamePanel.setKeyPressEnabled(false);
			
			int keyCode = e.getKeyCode();
			Snake snake = gamePanel.getSnake();
			Direction currentDirection = snake.getDirection();
			
			switch(keyCode) {
				case KeyEvent.VK_UP: {
					if(currentDirection != Direction.DOWN) {
						if(snake.headPosY() == 0)
							break;
						snake.setDirection(Direction.UP);
					}
					break;
				}
				
				case KeyEvent.VK_LEFT: {
					if(currentDirection != Direction.RIGHT) {
						if(snake.headPosX() == 0)
							break;
						snake.setDirection(Direction.LEFT);
					}
					break;
				}
				
				case KeyEvent.VK_DOWN: {
					if(currentDirection != Direction.UP) {
						if(snake.headPosY() >= 575)
							break;
						snake.setDirection(Direction.DOWN);
					}
					break;
				}
				
				case KeyEvent.VK_RIGHT: {
					if(currentDirection != Direction.LEFT) {
						if(snake.headPosX() >= 775)
							break;
						snake.setDirection(Direction.RIGHT);
					}
					break;
				}
				
				case KeyEvent.VK_ESCAPE: {
						gamePanel.pauseGame();
					break;
				}
			}	
		}
	}
}
