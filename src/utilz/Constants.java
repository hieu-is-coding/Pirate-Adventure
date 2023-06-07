package utilz;
import main.Game;
public class Constants {
	public static final float GRAVITY = 0.034f * Game.SCALE; //Gravity constant for the game
	public static final int NORMAL_SPEED = 25; //Normal speed value
	public static class CrabEnemy {
		//Crab enemy states
		public static final int CRABBY = 0;
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int DEAD = 4;
		//Crab enemy dimensions
		public static final int DEF_WID = 72;
		public static final int DEF_HEI = 32;
		public static final int CRAB_WID = (int) (DEF_WID * Game.SCALE);
		public static final int CRAB_HEI = (int) (DEF_HEI * Game.SCALE);
		public static final int CRAB_OFFSETX = (int) (26 * Game.SCALE);
		public static final int CRAB_OFFSETY = (int) (9 * Game.SCALE);		
		public static final int CRAB_DAM = 100; //Crab enemy damage value, in Crabby file
		public static int GetAniTotal(int crab_state) {
			switch (crab_state) {
			case IDLE:
				return 9;
			case RUNNING:
				return 6;
			case ATTACK:
				return 7;
			case DEAD:
				return 5;
			}
			return 0; 
		}
	}
	public static class Cloud {
		//Cloud dimensions
		public static final int CLOUD_WID = (int) (448 * Game.SCALE);
		public static final int CLOUD_HEI = (int) (101 * Game.SCALE);
	}
	public static class UI {
		public static class Buttons {
			//Button dimensions
			public static final int B_DEF_WID = 140;
			public static final int B_DEF_HEI = 56;
			public static final int B_WID = (int) (B_DEF_WID * Game.SCALE);
			public static final int B_HEI = (int) (B_DEF_HEI * Game.SCALE);
		}
		public static class UrmButtons {
			//Urmbutton size
			public static final int URM_DEF_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEF_SIZE * Game.SCALE);
		}
	}
	public static class Directions {
		//Direction constants
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	public static class PlayerConst {
		//Player movement states
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HIT = 5;
		public static final int DEAD = 6;
		public static int GetAniTotal(int movement) {
			switch (movement) {
			case DEAD:
				return 8;
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case FALLING:
			default:
				return 1;
			}
		}
	}

}
