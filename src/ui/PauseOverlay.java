package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.UrmButtons.*;

public class PauseOverlay {

	private Playing playing;
	//image
	private BufferedImage image;
	private int x, y, w, h;
	//buttons
	private UrmButton menu, replay, pause;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();

		createUrmButtons();

	}

	private void createUrmButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int replayX = (int) (387 * Game.SCALE);
		int pauseX = (int) (462 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);

		menu = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replay = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		pause = new UrmButton(pauseX, bY, URM_SIZE, URM_SIZE, 0);
	}

	private void loadBackground() {
		image = LoadSave.GetImg("pause.png");
		w = (int) (image.getWidth() * Game.SCALE);
		h = (int) (image.getHeight() * Game.SCALE);
		x = Game.GWIDTH / 2 - w / 2;
		y = (int) (25 * Game.SCALE);
	}

	public void update() {
		menu.update();
		replay.update();
		pause.update();

	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, w, h, null);

		menu.render(g);
		replay.render(g);
		pause.render(g);

	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, menu))
			menu.setMousePressed(true);
		else if (isIn(e, replay))
			replay.setMousePressed(true);
		else if (isIn(e, pause))
			pause.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menu)) {
			if (menu.isMousePressed()) {
				playing.setGamestate(Gamestate.MENU);
				playing.backInG();
			}
		} else if (isIn(e, replay)) {
			if (replay.isMousePressed()) {
				playing.resetAll();
				playing.backInG();
			}
		} else if (isIn(e, pause)) {
			if (pause.isMousePressed())
				playing.backInG();
		}

		menu.resetBools();
		replay.resetBools();
		pause.resetBools();

	}

	public void mouseMoved(MouseEvent e) {
		menu.setMouseOver(false);
		replay.setMouseOver(false);
		pause.setMouseOver(false);

		if (isIn(e, menu))
			menu.setMouseOver(true);
		else if (isIn(e, replay))
			replay.setMouseOver(true);
		else if (isIn(e, pause))
			pause.setMouseOver(true);
	}

	private boolean isIn(MouseEvent e, UrmButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}