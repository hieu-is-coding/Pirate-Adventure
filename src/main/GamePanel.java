package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.*;

public class GamePanel extends JPanel {

	private MouseInputs inputM;
	private Game game;

	public GamePanel(Game game) {
		this.game = game;
		setPanelSize();
		addInput();
	}
	
	private void addInput() {
		inputM = new MouseInputs(this);
		addMouseListener(inputM);
		addMouseMotionListener(inputM);
		addKeyListener(new KeyboardInputs(this));
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GWIDTH, GHEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public Game getGame() {
		return game;
	}

}