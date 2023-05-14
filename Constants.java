package PirateAdventure;

public class Constants {

	public static class UI {
		//Menu Button
		public static final int Def_BWidth= 140;
		public static final int Def_BHeight = 56;
		public static final int Bwidth = (int) (Def_BWidth* Game.SCALE);
		public static final int Bheight = (int) (Def_BHeight * Game.SCALE);
		
		//Sound Button
		public static final int Def_SSize = 42;
		public static final int Ssize = (int) (Def_SSize * Game.SCALE);

		//URM Button
		public static final int Def_USize = 56;
		public static final int Usize = (int) (Def_USize * Game.SCALE);
		
		//Volume Button
		public static final int Def_VWidth = 28;
		public static final int Def_VHeight = 44;
		public static final int Def_SliderWidth = 215;
		
		public static final int Vwidth = (int) (Def_VWidth * Game.SCALE);
		public static final int Vheight = (int) (Def_VHeight * Game.SCALE);
		public static final int SliderWidth= (int) (Def_SliderWidth * Game.SCALE);
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int GROUND = 4;
		public static final int HIT = 5;

		public static int GetSpriteIndex(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
				return 3;
			case GROUND:
				return 2;
			case FALLING:
			default:
				return 1;
			}
		}
	}

}
