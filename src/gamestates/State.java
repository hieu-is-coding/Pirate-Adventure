package gamestates;

import java.awt.event.MouseEvent;
import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}
	//check if mouse placed on the button
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	
public Game getGame() { return game; }

	public void setGamestate(Gamestate state) {
		Gamestate.state = state;
		switch (state) {
		case MENU:
			game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
			break;
		case PLAYING:
		 	game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
			break;
		default:
			break;
		} }
	}