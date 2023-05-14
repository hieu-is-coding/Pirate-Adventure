package PirateAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private int[][] lvlData;

	public LevelManager(Game game) {
		this.game = game;
		initialLevelSprites();
		lvlData = LoadSave.GetLevelData();
	}

	private void initialLevelSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = lvlData[j][i];
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}


	public int[][] getCurrentLevel() {
		return lvlData;
	}

}
