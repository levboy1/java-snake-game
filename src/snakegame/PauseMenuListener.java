package snakegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JDialog;

public class PauseMenuListener implements ActionListener, Serializable {
	private static final long serialVersionUID = -176810637054537684L;
	JDialog dialog;
	GamePanel gamePanel;
	
	public PauseMenuListener(JDialog d, GamePanel g) {
		dialog = d;
		gamePanel = g;
	}
	
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		
		switch(ac) {
			case "continue": {
				dialog.dispose();
				break;
			}
			
			case "save": {
				dialog.setVisible(false);
				gamePanel.saveGame();
				dialog.setVisible(true);
				break;
			}
			
			case "exit": {
				break;
			}
		}
	}
	
	
}
