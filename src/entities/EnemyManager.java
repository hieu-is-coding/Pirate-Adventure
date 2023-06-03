package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.CrabEnemy.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] crabbyImg;
	private ArrayList<Crabby> crabbies = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		crabbies = level.takeCrabs();
	}

	public void update(int[][] LevelSprites, Player player) {
		boolean isAnyActive = false;
		for (Crabby c : crabbies)
			if (c.isAlive()) {
				c.update(LevelSprites, player);
				isAnyActive = true;
			}
		if (!isAnyActive)
			playing.setLevelCompleted();
	}

	public void render(Graphics g, int xLvlOffset) {
		for (Crabby c : crabbies)
			if (c.isAlive()) {

				g.drawImage(crabbyImg[c.getState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - CRAB_OFFSETX + c.Xrotate(), (int) c.getHitbox().y - CRAB_OFFSETY,
						CRAB_WID * c.Wrotate(), CRAB_HEI, null);
			}
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Crabby c : crabbies)
			if (c.isAlive())
				if (attackBox.intersects(c.getHitbox())) {
					c.hurt(2);
					return;
				}
	}

	private void loadEnemyImgs() {
		crabbyImg = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.GetImg("crabby.png");
		for (int j = 0; j < crabbyImg.length; j++)
			for (int i = 0; i < crabbyImg[j].length; i++)
				crabbyImg[j][i] = temp.getSubimage(i * DEF_WID, j * DEF_HEI, DEF_WID, DEF_HEI);
	}

	public void resetAll() {
		for (Crabby c : crabbies)
			c.resetEnemy();
	}

}
