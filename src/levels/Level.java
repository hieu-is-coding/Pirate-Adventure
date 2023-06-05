package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.SetSpawn;

public class Level {

	private BufferedImage image;
	private int[][] LevelSprites;
	private ArrayList<Crabby> crabies;
	private int lvlTilesWide, maxTilesOffset, maxLvlOffset;
	private Point playerSpawn;

	public Level(BufferedImage image) {
		this.image = image;
		LevelSprites = GetLevelData(image);
		crabies = GetCrabs(image);
		lvlTilesWide = image.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_WID;
		maxLvlOffset = Game.TILES_SIZE * maxTilesOffset;
		playerSpawn = SetSpawn(image);
	}


	public int getSpriteIndex(int x, int y) {
		return LevelSprites[y][x];
	}

	public int[][] getLevelData() {
		return LevelSprites;
	}

	public int getLvlOffset() {
		return maxLvlOffset;
	}

	public ArrayList<Crabby> takeCrabs() {
		return crabies;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

}
