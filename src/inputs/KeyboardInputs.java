package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {

	private GamePanel panel;

	public KeyboardInputs(GamePanel panel) {
		this.panel = panel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			panel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			panel.getGame().getPlaying().keyPressed(e);
			break;
		}
	}
}