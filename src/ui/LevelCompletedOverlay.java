package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.UrmButtons.*;

public class LevelCompletedOverlay {

	private Playing playing;
	private UrmButton menu, next;
	private BufferedImage image;
	private int x, y, w, h;

	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		loadImg();
		loadButtons();
	}

	private void loadButtons() {
		next = new UrmButton((int) (445 * Game.SCALE), (int) (195 * Game.SCALE), URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton((int) (330 * Game.SCALE), (int) (195 * Game.SCALE), URM_SIZE, URM_SIZE, 2);
	}

	private void loadImg() {
		image = LoadSave.GetImg("LVLcompleted.png");
		w = (int) (image.getWidth() * Game.SCALE);
		h = (int) (image.getHeight() * Game.SCALE);
		x = Game.GWIDTH / 2 - w / 2;
		y = (int) (75 * Game.SCALE);
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GWIDTH, Game.GHEIGHT);

		g.drawImage(image, x, y, w, h, null);
		next.render(g);
		menu.render(g);
	}

	public void update() {
		next.update();
		menu.update();
	}

	private boolean BoundCheck(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);

		if (BoundCheck(menu, e))
			menu.setMouseOver(true);
		else if (BoundCheck(next, e))
			next.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (BoundCheck(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGamestate(Gamestate.MENU);
			}
		} else if (BoundCheck(next, e))
			if (next.isMousePressed()) {
				playing.loadNextLevel();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
			}

		menu.resetBools();
		next.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (BoundCheck(menu, e))
			menu.setMousePressed(true);
		else if (BoundCheck(next, e))
			next.setMousePressed(true);
	}

}
