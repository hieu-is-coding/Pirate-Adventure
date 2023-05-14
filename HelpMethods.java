package PirateAdventure;

import java.awt.geom.Rectangle2D;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData) && !IsSolid(x + width, y + height, lvlData) && !IsSolid(x + width, y, lvlData) && !IsSolid(x, y + height, lvlData)) 
			return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value >= 46 || value < 0 || value != 11)
			return true;
		return false;
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}

}