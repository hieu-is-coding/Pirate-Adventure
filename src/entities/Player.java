package entities;

import static utilz.Constants.PlayerConst.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Playing;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] animations;
	private boolean moving = false, attacking = false;
	private boolean left, right, jump, jump2;
	private int[][] LevelSprites;
	private float xOffset = 21 * Game.SCALE;
	private float yOffset = 4 * Game.SCALE;
	private float jumpSpeed = -2.1f * Game.SCALE;
	private int jumpAgain = 1;


	private int Xrotate = 0;
	private int Wrotate = 1;

	private boolean attCheck;
	private Playing playing;
	private int LevelNo;


	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.health = 100;
		this.walkSpeed = Game.SCALE * 1.0f;
		loadAnimations();
		initHitbox(20, 27);
		hitzone = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
		this.LevelNo = playing.getLevelManager().getLevelIndex();
	}
	
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetImg("player.png");
		animations = new BufferedImage[7][8];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
		
	}
	
	public void update() {

		if (health <= 0 || hitbox.y >= 620) {
			if (state != DEAD) {
				state = DEAD;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDead(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
			} else if (aniIndex == GetAniTotal(DEAD) - 1 && aniTick >= NORMAL_SPEED - 1) {
				playing.setGameOver();
				playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else
				updateAnimationTick();

			return;
		}

		updateHitzone();
		updatePos();
		if (attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
	}


	private void checkAttack() {
		if (attCheck || aniIndex != 1)
			return;
		attCheck = true;
		playing.checkEnemyHit(hitzone);
		playing.getGame().getAudioPlayer().playAttackSound();
	}

	private void updateHitzone() {
		if (right)
			hitzone.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
		else if (left)
			hitzone.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);

		hitzone.y = hitbox.y + (Game.SCALE * 10);
	}


	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xOffset) - lvlOffset + Xrotate, (int) (hitbox.y - yOffset), width * Wrotate, height, null);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= NORMAL_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetAniTotal(state)) {
				aniIndex = 0;
				attacking = false;
				attCheck = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = state;

		if (moving)
			state = RUNNING;
		else
			state = IDLE;

		if (inAir) {
			if (airSpeed < 0)
				state = JUMP;
			else
				state = FALLING;
		}

		if (attacking) {
			state = ATTACK;
			if (startAni != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		if (startAni != state)
		{
			aniTick = 0;
			aniIndex = 0;
		}
	}



	private void updatePos() {
		moving = false;
		System.out.printf("%b %d\n",jump,jumpAgain);
		if (jump)
		{
			if(!inAir)
			{
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
				inAir = true;
				airSpeed = jumpSpeed;
			}
		}


		if (!inAir)
		{
			if ((!left && !right) || (right && left))
				return;
		}

		float xSpeed = 0;

		if (left) {
			xSpeed -= walkSpeed;
			Xrotate = width;
			Wrotate = -1;
		}
		if (right) {
			xSpeed += walkSpeed;
			Xrotate = 0;
			Wrotate = 1;
		}
		
		if (!inAir)
			if (!CheckAboveFloor(hitbox, LevelSprites))
				inAir = true;


		if (inAir) {

			if (Movable(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, LevelSprites)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				if (airSpeed > 0)
				{
					inAir = false;
					airSpeed = 0;
				}
				else
					airSpeed = 0.5f * Game.SCALE;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}


	private void updateXPos(float xSpeed) {
		if (Movable(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelSprites))
			hitbox.x += xSpeed;
	}


	public void loadLvlData(int[][] LevelSprites) {
		this.LevelSprites = LevelSprites;
		if (!CheckAboveFloor(hitbox, LevelSprites))
			inAir = true;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public void setDoubleJump(boolean jump2) {
		this.jump2 = jump2;
	}
	public boolean canJumpAgain() {
		if(jumpAgain > 0) return true;
		return false;
	}
	public void setJumpAgain() {
		jumpAgain -= 1;
	}
	public boolean isInAir() {
		return inAir;
	}
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = IDLE;
		health = 100;
		jumpAgain = 2;
		
		hitbox.x = x;
		hitbox.y = y;

		if (!CheckAboveFloor(hitbox, LevelSprites))
			inAir = true;
	}


}
