package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import ranking.ScoreBoard;

public class LevelManager {

	private Game game;
	private BufferedImage[] image;
	private ArrayList<Level> levels;
	private int LevelNo = 0;

	public LevelManager(Game game) {
		this.game = game;
		levels = new ArrayList<>();
		loadImg();
		InitLevels();
	}

	public void NextLevel() {
		//check current level;
		LevelNo++;
		if (LevelNo >= 1) {
			LevelNo = 0;
			System.out.println("CONGRATULATION! YOU WIN");
			System.out.println("Total played time:" + (int) Playing.getTime()/1000);
			//display scoreboard
			new ScoreBoard((int) Playing.getTime()/1000);
			Gamestate.state = Gamestate.MENU;
		}

		Level newLevel = levels.get(LevelNo);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
	}

	//get all levels
	private void InitLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img));
	}

	private void loadImg() {
		BufferedImage img = LoadSave.GetImg("ground.png");
		image = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				image[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void render(Graphics g, int offsetTiles) {
		for (int j = 0; j < Game.TILES_HEI; j++)
			for (int i = 0; i < levels.get(LevelNo).getLevelData()[0].length; i++) {
				int index = levels.get(LevelNo).getSpriteIndex(i, j);
				g.drawImage(image[index], Game.TILES_SIZE * i - offsetTiles, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public Level getCurrentLevel() {
		return levels.get(LevelNo);
	}


	public int getLevelIndex() {
		return LevelNo;
	}

}
