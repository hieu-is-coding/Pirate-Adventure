package utilz;
import static utilz.Constants.CrabEnemy.CRABBY;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Crabby;
import main.Game;
public class HelpMethods {
	//Check if can move without colliding with the map
	public static boolean Movable(float x, float y, float width, float height, int[][] LevelSprites) {
		if (!CheckMap(x, y, LevelSprites) && !CheckMap(x + width, y + height, LevelSprites) && !CheckMap(x + width, y, LevelSprites) && !CheckMap(x, y + height, LevelSprites)) {
						return true;
		}
		return false;
	}
	//Check if the specified coordinates collide with the map
	private static boolean CheckMap(float x, float y, int[][] LevelSprites) {
		if (x < 0 || x >= LevelSprites[0].length * Game.TILES_SIZE || y < 0 || y >= Game.GHEIGHT) {
			return true;
		}
		return TileCollion((int) (x / Game.TILES_SIZE), (int) (y / Game.TILES_SIZE), LevelSprites);
	}
	//Check if a specific tile has collision
	public static boolean TileCollion(int x, int y, int[][] LevelSprites) {
		int valC = LevelSprites[y][x];
		if (valC >= 48 || valC < 0 || valC != 11) {
			return true;
		}
		return false;
	}
	//Check if there is a floor above the specified hitbox
	public static boolean CheckAboveFloor(Rectangle2D.Float hitbox, int[][] LevelSprites) {
		if (!CheckMap(hitbox.x, hitbox.y + hitbox.height + 1, LevelSprites)) {
			if (!CheckMap(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, LevelSprites)) {
				return false;
			}
		}
		return true;
	}
	//Check if there is a floor below the specified hitbox considering the xSpeed
	public static boolean CheckFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] LevelSprites) {
		if (xSpeed > 0) {
			return CheckMap(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, LevelSprites);
		}
		else {
			return CheckMap(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, LevelSprites);
		}
	}
	//Extract level data from an image and return it as a 2D array
	public static int[][] GetLevelData(BufferedImage image) {
		int[][] LevelSprites = new int[image.getHeight()][image.getWidth()];
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				Color c = new Color(image.getRGB(i, j));
				int valC = c.getRed();
				if (valC >= 48) {
					valC = 0;
				}
				LevelSprites[j][i] = valC;
			}
		}
		return LevelSprites;
	}
	//Extract crab objects from an image and return them as a list
	public static ArrayList<Crabby> GetCrabs(BufferedImage image) {
		ArrayList<Crabby> list = new ArrayList<>();
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				Color c = new Color(image.getRGB(i, j));
				int valC = c.getGreen();
				if (valC == CRABBY) {
					list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
				}
			}
		}
		return list;
	}
	//Set the spawn point based on a specific pixel color in the image
	public static Point SetSpawn(BufferedImage image) {
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				Color c = new Color(image.getRGB(i, j));
				int valC = c.getGreen();
				if (valC == 100) {
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				}
			}
		}
		return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}
}
