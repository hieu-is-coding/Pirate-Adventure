package ui;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import gamestates.Gamestate;
import utilz.LoadSave;
import static utilz.Constants.UI.Buttons.*;
public class MenuButton {
	private int x, y, pos, index;
	private Gamestate state;
	private BufferedImage[] image;
	private boolean mouseIn, mouseClick;
	private Rectangle hitbox;
	public MenuButton(int x, int y, int pos, Gamestate state) {
		this.x = x;
		this.y = y;
		this.pos = pos;
		this.state = state;
		loadImgs();
		initBounds();
	}
	//Initialize the hitbox bounds for the button
	private void initBounds() {
		hitbox = new Rectangle(x - B_WID / 2, y, B_WID, B_HEI);
	}
	//Load the images for the button states
	private void loadImgs() {
		image = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetImg("buttons.png");
		for (int i = 0; i < image.length; i++) {
			image[i] = temp.getSubimage(i * B_DEF_WID, pos * B_DEF_HEI, B_DEF_WID, B_DEF_HEI);
		}
	}
	public void render(Graphics g) {
		g.drawImage(image[index], x - B_WID / 2, y, B_WID, B_HEI, null);
	}
	public void update() {
		index = 0;
		if (mouseIn) {
			index = 1;
		}
		if (mouseClick) {
			index = 2;
		}
	}
	public boolean isMouseOver() {
		return mouseIn;
	}
	public void setMouseOver(boolean mouseIn) {
		this.mouseIn = mouseIn;
	}
	public boolean isMousePressed() {
		return mouseClick;
	}
	public void setMousePressed(boolean mouseClick) {
		this.mouseClick = mouseClick;
	}
	public Rectangle getBounds() {
		return hitbox;
	}
 	//Apply the associated gamestate when the button is clicked
	public void applyGamestate() {
		Gamestate.state = state;
	}
	public void resetBools() {
		mouseIn = false;
		mouseClick = false;
	}
	public Gamestate getState() {
		return state;
	}
}
