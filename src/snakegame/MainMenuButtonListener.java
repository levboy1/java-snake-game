package snakegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuButtonListener implements ActionListener {
	private Program program;
	
	public MainMenuButtonListener(Program p) {
		program = p;
	}
	
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		
		switch(ac) {
			case "new": {
				program.startNewGame();
				break;
			}
			
			case "load": {
				program.loadGame();
				break;
			}
			
			case "settings": {
				program.changeSettings();
				break;
			}
			
			case "exit": {
				System.exit(0);
				break;
			}
		}
		
	}
}
