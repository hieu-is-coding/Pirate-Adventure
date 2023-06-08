package ui;
import static utilz.Constants.UI.UrmButtons.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
public class GameOverOverlay {
	private Playing playing;
	private BufferedImage image;
	private int x, y, w, h;
	private UrmButton menu, play;
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		loadImg();
		loadButtons();
	}
	//Load the buttons for the game over overlay
	private void loadButtons() {
		play = new UrmButton((int) (440 * Game.SCALE), (int) (195 * Game.SCALE), URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton((int) (335 * Game.SCALE), (int) (195 * Game.SCALE), URM_SIZE, URM_SIZE, 2);
	}
	//Load the image for the game over overlay
	private void loadImg() {
		image = LoadSave.GetImg("death_screen.png");
		w = (int) (image.getWidth() * Game.SCALE);
		h = (int) (image.getHeight() * Game.SCALE);
		x = Game.GWIDTH / 2 - w / 2;
		y = (int) (100 * Game.SCALE);
	}
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GWIDTH, Game.GHEIGHT);
		g.drawImage(image, x, y, w, h, null);
		menu.render(g);
		play.render(g);
	}
	public void update() {
		menu.update();
		play.update();
	}
	//Check if the mouse is within the bounds of a button
	private boolean BoundCheck(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);
		if (BoundCheck(menu, e)) {
			menu.setMouseOver(true);
		}
		else if (BoundCheck(play, e)) {
			play.setMouseOver(true);
		}
	}
	public void mouseReleased(MouseEvent e) {
		if (BoundCheck(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGamestate(Gamestate.MENU);
			}
		} 
		else if (BoundCheck(play, e)) {
			if (play.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
			}
		}
		menu.resetBools();
		play.resetBools();
	}
	public void mousePressed(MouseEvent e) {
		if (BoundCheck(menu, e)) {
			menu.setMousePressed(true);
		}
		else if (BoundCheck(play, e)) {
			play.setMousePressed(true);
		}
	}
}
