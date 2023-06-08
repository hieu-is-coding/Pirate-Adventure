package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox,hitzone;
	protected int aniTick, aniIndex;
	
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int health;
	
	protected float walkSpeed;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	/*
	 * each attack box is rectangle, use drawRect, hitzone - offset of img
	 */

	// checkk
	//debug: draw attack box of ent
	protected void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (hitzone.x - xLvlOffset), (int) hitzone.y, (int) hitzone.width, (int) hitzone.height);
		
	}

	protected void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
		
	}

	protected void initHitbox(int width, int height) {
		
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
		
		
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
		
	}

	public int getState() {
		return state;
		
	}

	public int getAniIndex() {
		return aniIndex;
		
	}


}









