package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.UrmButtons.*;

public class UrmButton {
	private BufferedImage[] image;
	private int pos, index;
	private boolean mouseIn, mouseClick;

	private int x, y, width, height;
	private Rectangle hitbox;
	
	public UrmButton(int x, int y, int width, int height, int pos) {
		this.pos = pos;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle(x, y, width, height);
		loadImg();
	}

	
	private void loadImg() {
		BufferedImage temp = LoadSave.GetImg("urm_buttons.png");
		image = new BufferedImage[3];
		for (int i = 0; i < image.length; i++)
			image[i] = temp.getSubimage(i * URM_DEF_SIZE, pos * URM_DEF_SIZE, URM_DEF_SIZE, URM_DEF_SIZE);

	}

	public void update() {
		index = 0;
		if (mouseIn)
			index = 1;
		if (mouseClick)
			index = 2;

	}

	public void render(Graphics g) {
		g.drawImage(image[index], x, y, URM_SIZE, URM_SIZE, null);
	}

	public void resetBools() {
		mouseIn = false;
		mouseClick = false;
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
}
