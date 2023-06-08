package inputs;
import java.awt.event.*;
import gamestates.Gamestate;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {

	private GamePanel panel;

	public KeyboardInputs(GamePanel panel) {
		this.panel = panel;
	}
	public void keyTyped(KeyEvent e) {
	}
	//handle key release
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			panel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;

		}
	}
	//handle keyPressed
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			panel.getGame().getPlaying().keyPressed(e);
			break;
		}
	}
}