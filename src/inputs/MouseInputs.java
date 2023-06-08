package inputs;

import java.awt.event.*;
import gamestates.Gamestate;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel panel;

	public MouseInputs(GamePanel panel) {
		this.panel = panel;
	}
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			panel.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().mouseMoved(e);
			break;
		default:
			break;

		}

	}

	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			panel.getGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;

		}

	}

	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			panel.getGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().mousePressed(e);
			break;
		default:
			break;

		}

	}

	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			panel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			panel.getGame().getPlaying().mouseReleased(e);
			break;
		default:
			break;

		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {

	}

}