package PirateAdventure;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


abstract public class State {
	
	abstract  void update();

	abstract  void draw(Graphics g);

	abstract  void mouseClicked(MouseEvent e);

	abstract  void mousePressed(MouseEvent e);

	abstract  void mouseReleased(MouseEvent e);

	abstract  void mouseMoved(MouseEvent e);

	abstract  void keyPressed(KeyEvent e);

	abstract  void keyReleased(KeyEvent e);

	protected Game game;

	public State(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	

	public Game getGame() {
		return game;
	}
}