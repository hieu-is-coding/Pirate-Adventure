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
	private BufferedImage[][] img;
	private ArrayList<Crabby> crabbies = new ArrayList<>();
	
/*
 * load enemies from level
 * update state of enemies based on current level, position of player
 * render eneies on screen (check if alive too; draw image with corresponding animation)
 * check if enemy is hit by player, set damage (attackBox.intersects
 * load img of crab
 * 
 */
	
// call loadImg ngay trong constructor

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadImg();
	}

	public void loadEnemies(Level level) {
		
		crabbies = level.takeCrabs();
	}

	public void update(int[][] LevelSprites, Player player) {
		boolean isAnyActive = false;
		for (Crabby c : crabbies) {
			
			if (c.isAlive()) {
				c.update(LevelSprites, player);
				isAnyActive = true;
			}
		}
		
		if (!isAnyActive) {
			playing.setLevelCompleted();
		}
		
	}

	public void render(Graphics g, int xLvlOffset) {
		for (Crabby c : crabbies) {
			
			if (c.isAlive()) {
				
				// check ho phan nay
				
				g.drawImage(img[c.getState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - CRAB_OFFSETX + c.Xrotate(), (int) c.getHitbox().y - CRAB_OFFSETY,
						CRAB_WID * c.Wrotate(), CRAB_HEI, null);
			}
		}
		
		
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Crabby c : crabbies) {
			if (c.isAlive()) {
				
				// set dmg
				if (attackBox.intersects(c.getHitbox())) {
					c.hurt(10);
					return;
				}
			}
			
		}

	}

	
	// dung getSubimage()
	// (j* wid, i*hei, wid, hei)

	private void loadImg() {
		
		img = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.GetImg("crabby.png");
		
		for (int i = 0; i < img.length; ++i) {

			for (int j = 0; j < img[i].length; ++j)
				img[i][j] = temp.getSubimage(j * DEF_WID, i * DEF_HEI, DEF_WID, DEF_HEI);
		}
	}

	
	// them reset enemy
	
	public void resetAll() {
		for (Crabby c : crabbies)
			c.resetEnemy();
	}
	

}















