package entities;

//import may cai tu util voi import Game trong main nhe
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
	
	private int direction = LEFT;
	private int OffsetX;

	private float attDis = Game.TILES_SIZE;
	private boolean alive = true;
	private boolean attCheck;
	
// set up hitbox, hitzone luon
	public Crabby(float x, float y) {
		super(x, y, CRAB_WID, CRAB_HEI);
		health = 10;
		walkSpeed = Game.SCALE * 0.35f;
		
		
		initHitbox(22, 19);
		hitzone = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
		OffsetX = (int) (Game.SCALE * 30);
		
	}

	
	// update crab behavior and animation, hitzone position
	public void update(int[][] LevelSprites, Player player) {
		
		updateBehavior(LevelSprites, player);
		updateAnimation();
		hitzone.x = hitbox.x - OffsetX;
		hitzone.y = hitbox.y;
		
		
	}

	
	// handle movement, jump, facing direction, attack logic
	private void updateBehavior(int[][] LevelSprites, Player player) {
		if (!CheckAboveFloor(hitbox, LevelSprites)) {
			inAir = true;

		}

		if (inAir) {
			
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
					
					if (InAttackRange(player)) {
						ChangeState(ATTACK);
					}
				}

				move(LevelSprites);
				break;
				
			case ATTACK:
				if (aniIndex == 0) {
					attCheck = false;
				}
				
				if (aniIndex == 3 && !attCheck) {
					if (hitzone.intersects(player.hitbox)) {
						player.health -= CRAB_DAM;
					}
					attCheck = true;
				}
				break;
			}
			
			
		}
	}
	
	private void updateAnimation() {
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


	private void move(int[][] LevelSprites) {
		float xSpeed = 0;

		if (direction == LEFT) {
			xSpeed = -walkSpeed;
		}
		else {
			xSpeed = walkSpeed;
		}

		if (Movable(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelSprites)) {
			if (CheckFloor(hitbox, xSpeed, LevelSprites)) {
				hitbox.x += xSpeed;
				return;
			}
		}

		if (direction == LEFT) {
			direction = RIGHT;

		}
		else {
			direction = LEFT;
		}
	}
	
	// update facing direction base on player position - player near -> go back 2 player
	// left

	private void FaceTowardPlayer(Player player) {
		if (player.hitbox.x > hitbox.x) {
			direction = RIGHT;
		} else {
			direction = LEFT;
	}
	}


	private boolean OnView(int[][] LevelSprites, Player player) {
		//check if on the same row
		if ((int) (player.getHitbox().y / Game.TILES_SIZE) == (int) (hitbox.y / Game.TILES_SIZE))
			if (isPlayerInRange(player)) {
				return true;
			}

		return false;
	}

	// check if within range
	private boolean isPlayerInRange(Player player) {
		int possible = (int) Math.abs(player.hitbox.x - hitbox.x);
		return possible <= attDis * 6;
	}

	private boolean InAttackRange(Player player) {
		int possible = (int) Math.abs(player.hitbox.x - hitbox.x);
		return possible <= attDis;
	}

	private void ChangeState(int enemyState) {
		this.state = enemyState;
		aniTick = 0;
		aniIndex = 0;
		
	}

	public void hurt(int amount) {
		health -= amount;
		if (health <= 0)
			ChangeState(DEAD);
	}

	// 

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
	
	// add width scaling factor to rotate crab sprite
	

	public int Xrotate() {
		if (direction == RIGHT) {
			return width;
		} else {
			return 0;
		}
	}

	public int Wrotate() {
		if (direction == RIGHT) {
			return -1;
		} else {
			return 1;
		}
		
		
	}
	
}









































