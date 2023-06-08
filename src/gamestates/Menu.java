package gamestates;

import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State {

	private MenuButton[] buttons = new MenuButton[2];
	private BufferedImage menuLabel, bgMenu;
	private int x, y, w, h;

	public Menu(Game game) {
		super(game);
		loadImg();
		bgMenu = LoadSave.GetImg("background2.jpg");

	}

	private void loadImg() {
		//background
		menuLabel = LoadSave.GetImg("menu.png");
		w = (int) (menuLabel.getWidth() * Game.SCALE);
		h = (int) (menuLabel.getHeight() * Game.SCALE);
		x = Game.GWIDTH / 2 - w / 2;
		y = (int) (45 * Game.SCALE);
		//buttons
		buttons[0] = new MenuButton(Game.GWIDTH / 2, (int) (180 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GWIDTH / 2, (int) (260 * Game.SCALE), 2, Gamestate.QUIT);
	}


	
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
	}

	
	public void render(Graphics g) {

		g.drawImage(bgMenu, 0, 0, Game.GWIDTH, Game.GHEIGHT, null);
		g.drawImage(menuLabel, x, y, w, h, null);

		for (MenuButton mb : buttons)
			mb.render(g);
	}


	
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
	}

	
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				if (mb.getState() == Gamestate.PLAYING)
					game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
				break;
			}
		}

		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

	
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);

		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}
}