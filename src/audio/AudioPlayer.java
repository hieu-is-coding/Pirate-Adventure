package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public static final int MENU_1 = 0;
	public static final int LEVEL_1 = 1;
	public static final int LEVEL_2 = 2;

	public static final int DIE = 0;
	public static final int JUMP = 1;
	public static final int GAMEOVER = 2;
	public static final int LVL_COMPLETED = 3;
	public static final int ATTACK_ONE = 4;
	public static final int ATTACK_TWO = 5;
	public static final int ATTACK_THREE = 6;

	private Clip[] songs, effects;
	private int songId;
	private float volume = 1f;
	private Random rand = new Random();

	public AudioPlayer() {
		getSongs();
		getEffects();
		playSong(MENU_1);
	}

	private void getSongs() {
		songs = new Clip[3];
		songs[0] = getClip("test");
		songs[1] = getClip("level1");
		songs[2] = getClip("level2");
	}

	private void getEffects() {
		effects = new Clip[7];
		effects[0] = getClip("die");
		effects[1] = getClip("jump");
		effects[2] = getClip("gameover");
		effects[3] = getClip("lvlcompleted");
		effects[4] = getClip("attack1");
		effects[5] = getClip("attack2");
		effects[6] = getClip("attack3");
		
		updateEffectsVolume();
		
	}

	private Clip getClip(String name) {
		URL source = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(source);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	}


	public void stopSong() {
		if (songs[songId].isActive())
			songs[songId].stop();
	}

	public void setLevelSong(int lvlIndex) {
		if (lvlIndex == 0)
			playSong(LEVEL_1);
		else
			playSong(LEVEL_2);
	}

	public void lvlCompleted() {
		stopSong();
		playEffect(LVL_COMPLETED);
	}

	public void playAttackSound() {
		int start = 4;
		start += rand.nextInt(3);
		playEffect(start);
	}

	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

	public void playSong(int song) {
		stopSong();

		songId = song;
		songs[songId].setMicrosecondPosition(0);
		songs[songId].loop(Clip.LOOP_CONTINUOUSLY);
	}

	private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl control = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float def = ((control.getMaximum() - control.getMinimum()) * volume) + control.getMinimum();
			control.setValue(def);
		}
	}

}
