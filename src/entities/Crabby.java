package entities;

import static utilz.Constants.CrabEnemy.*;
import static utilz.HelpMethods.Movable;
import static utilz.HelpMethods.CheckAboveFloor;
import static utilz.HelpMethods.CheckFloor;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.NORMAL_SPEED;
import static utilz.Constants.GRAVITY;
import static utilz.Constants.Directions.*;

import main.Game;

public class Crabby extends Entity {
	
	protected int direction = LEFT;
	protected float attDis = Game.TILES_SIZE;
	protected boolean alive = true;
	protected boolean attCheck;
	private int OffsetX;

	public Crabby(float x, float y) {
		super(x, y, CRAB_WID, CRAB_HEI);
		health = 10;
		walkSpeed = Game.SCALE * 0.35f;
		initHitbox(22, 19);
		hitzone = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
		OffsetX = (int) (Game.SCALE * 30);
	}

	public void update(int[][] LevelSprites, Player player) {
		updateBehavior(LevelSprites, player);
		updateAnimationTick();
		hitzone.x = hitbox.x - OffsetX;
		hitzone.y = hitbox.y;
	}

	private void updateBehavior(int[][] LevelSprites, Player player) {
		if (!CheckAboveFloor(hitbox, LevelSprites))
			inAir = true;

		if (inAir)
		{
			if (Movable(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, LevelSprites)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
			} else {
				inAir = false;
			}
		}
		else {
			switch (state) {
			case IDLE:
				ChangeState(RUNNING);
				break;
			case RUNNING:
				if (OnView(LevelSprites, player)) {
					FaceTowardPlayer(player);
					if (InAttackRange(player))
						ChangeState(ATTACK);
				}

				move(LevelSprites);
				break;
			case ATTACK:
				if (aniIndex == 0)
					attCheck = false;
				if (aniIndex == 3 && !attCheck)
				{
					if (hitzone.intersects(player.hitbox))
						player.health -= CRAB_DAM;
					attCheck = true;
				}
				break;
			}
		}
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= NORMAL_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetAniTotal(state)) {
				aniIndex = 0;

				switch (state) {
					case ATTACK:
						state = IDLE;
						break;
					case DEAD:
						alive = false;
						break;
				}
			}
		}
	}


	protected void move(int[][] LevelSprites) {
		float xSpeed = 0;

		if (direction == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (Movable(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelSprites))
			if (CheckFloor(hitbox, xSpeed, LevelSprites)) {
				hitbox.x += xSpeed;
				return;
			}

		if (direction == LEFT)
			direction = RIGHT;
		else
			direction = LEFT;
	}

	protected void FaceTowardPlayer(Player player) {
		if (player.hitbox.x > hitbox.x)
			direction = RIGHT;
		else
			direction = LEFT;
	}

	protected boolean OnView(int[][] LevelSprites, Player player) {
		//check if player is on the same tiles 
		if ((int) (player.getHitbox().y / Game.TILES_SIZE) == (int) (hitbox.y / Game.TILES_SIZE))
			if (isPlayerInRange(player)) {
				return true;
			}

		return false;
	}

	protected boolean isPlayerInRange(Player player) {
		int possible = (int) Math.abs(player.hitbox.x - hitbox.x);
		return possible <= attDis * 5;
	}

	protected boolean InAttackRange(Player player) {
		int possible = (int) Math.abs(player.hitbox.x - hitbox.x);
		return possible <= attDis;
	}

	protected void ChangeState(int enemyState) {
		this.state = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}

	public void hurt(int amount) {
		health -= amount;
		if (health <= 0)
			ChangeState(DEAD);
	}


	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		health = 10;
		ChangeState(IDLE);
		alive = true;
		airSpeed = 0;
	}


	public boolean isAlive() {
		return alive;
	}
	

	public int Xrotate() {
		if (direction == RIGHT)
			return width;
		else
			return 0;
	}

	public int Wrotate() {
		if (direction == RIGHT)
			return -1;
		else
			return 1;
	}
}