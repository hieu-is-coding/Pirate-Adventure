package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.*;
import levels.LevelManager;
import main.Game;
import ui.*;
import utilz.LoadSave;
import static utilz.Constants.Cloud.*;

public class Playing extends State {
	private Player player;
	private LevelManager LManager;
	private EnemyManager EManger;
	private PauseOverlay PauseO;
	private GameOverOverlay GameO;
	private LevelCompletedOverlay LevelO;
	private boolean paused = false;

	private int xLvlOffset;
	private int maxLvlOffsetX;

	private BufferedImage backgroundImg, CLOUD;

	private boolean gameOver;
	private boolean lvlCompleted;
	private boolean playerDying;
	
	public long timeCheck, lastCheck;

	public Playing(Game game) {
		super(game);
		timeCheck = System.currentTimeMillis();
		initClasses();
		loadBackground();

		calcLvlOffset();
		loadStartLevel();
	}

	public void loadNextLevel() {
		resetAll();
		LManager.NextLevel();
		player.setSpawn(LManager.getCurrentLevel().getPlayerSpawn());
	}

	private void loadStartLevel() {
		EManger.loadEnemies(LManager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = LManager.getCurrentLevel().getLvlOffset();
	}
	
	private void loadBackground() {
		backgroundImg = LoadSave.GetImg("playing.png");
		CLOUD = LoadSave.GetImg("clouds.png");
	}
	private void initClasses() {
		LManager = new LevelManager(game);
		EManger = new EnemyManager(this);

		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
		player.loadLvlData(LManager.getCurrentLevel().getLevelData());
		player.setSpawn(LManager.getCurrentLevel().getPlayerSpawn());

		PauseO = new PauseOverlay(this);
		GameO = new GameOverOverlay(this);
		LevelO = new LevelCompletedOverlay(this);
	}

	public void update() {
		if (paused) {
			PauseO.update();
		} else if (lvlCompleted) {
			LevelO.update();
		} else if (gameOver) {
			GameO.update();
		} else if (playerDying) {
			player.update();
		} else {
			lastCheck = System.currentTimeMillis();
			player.update();
			EManger.update(LManager.getCurrentLevel().getLevelData(), player);
			checkCloseBorder();
		}
	}
	
	public void checkCloseBorder() {
		int leftSide = (int) (0.2 * Game.GWIDTH);
		int rightSide = (int) (0.8 * Game.GWIDTH);
		int playerX = (int)player.getHitbox().x;
		
		if(playerX - xLvlOffset > rightSide) {
			xLvlOffset = playerX - rightSide; 
		}
		else if(playerX - xLvlOffset < leftSide) {
			xLvlOffset = playerX - leftSide; 
		}
		if(xLvlOffset > maxLvlOffsetX) {
			xLvlOffset = maxLvlOffsetX;
		}
		else if(xLvlOffset < 0) {
			xLvlOffset = 0;
		}
	}


	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GWIDTH, Game.GHEIGHT, null);

		CloudRender(g);

		LManager.render(g, xLvlOffset);
		player.render(g, xLvlOffset);
		EManger.render(g, xLvlOffset);

		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GWIDTH, Game.GHEIGHT);
			PauseO.draw(g);
		} else if (gameOver)
			GameO.draw(g);
		else if (lvlCompleted)
			LevelO.draw(g);
	}

	private void CloudRender(Graphics g) {
		for (int i = 0; i < 3; i++)
			g.drawImage(CLOUD, i * CLOUD_WID - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), CLOUD_WID, CLOUD_HEI, null);
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		playerDying = false;
		player.resetAll();
		EManger.resetAll();
	}

	public void setGameOver() {
		this.gameOver = true;
	}


	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		EManger.checkEnemyHit(attackBox);
	}

	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			if(player.isInAir() && player.canJumpAgain()) {
				player.setDoubleJump(true);
				player.setJumpAgain();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			break;
		}
	}


	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				if(!player.canJumpAgain()) player.setDoubleJump(false);
				break;
			}

	}


	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				PauseO.mousePressed(e);
			else if (lvlCompleted)
				LevelO.mousePressed(e);
		} else
			GameO.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				PauseO.mouseReleased(e);
			else if (lvlCompleted)
				LevelO.mouseReleased(e);
		} else
			GameO.mouseReleased(e);
	}

	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				PauseO.mouseMoved(e);
			else if (lvlCompleted)
				LevelO.mouseMoved(e);
		} else
			GameO.mouseMoved(e);
	}

	public void setLevelCompleted() {
		this.lvlCompleted = true;
		System.out.println("Played time: " + (lastCheck - timeCheck));
		game.getAudioPlayer().lvlCompleted();
	}

	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}

	public void backInG() {
		paused = false;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

	public EnemyManager getEnemyManager() {
		return EManger;
	}

	public LevelManager getLevelManager() {
		return LManager;
	}

	public void setPlayerDead(boolean playerDying) {
		this.playerDying = playerDying;

	}
	
	public boolean getLvlCompleted() {
		return lvlCompleted;
	}

}
