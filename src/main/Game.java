package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.*;

public class Game implements Runnable {

	private GameWindow window;
	private GamePanel panel;
	private Thread thread;
	private final int FPS = 120;
	private final int UPS = 200;

	private Playing playing;
	private Menu menu;
	private AudioPlayer audioPlayer;

	public final static float SCALE = 1.5f;
	public final static int TILES_WID = 26;
	public final static int TILES_HEI = 14;
	public final static int TILES_SIZE = (int) (32 * SCALE);
	public final static int GWIDTH = TILES_SIZE * TILES_WID;
	public final static int GHEIGHT = TILES_SIZE * TILES_HEI;

	public Game() {
		initClasses();
		
		// frame va panel
		panel = new GamePanel(this);
		window = new GameWindow(panel);
		panel.setFocusable(true);
		panel.requestFocus();

		startGameLoop();
	}

	//tao audio,menu va playing
	private void initClasses() {
		audioPlayer = new AudioPlayer();
		menu = new Menu(this);
		playing = new Playing(this);
		
	}

	private void startGameLoop() {
		thread = new Thread(this);
		thread.start();
	}

	public void update() {
		switch (Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;

		}
	}

	public void render(Graphics g) {
		switch (Gamestate.state) {
		case MENU:
			menu.render(g);
			break;
		case PLAYING:
			playing.render(g);
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		
		//set up ups and fps
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			//update
			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			//render
			if (deltaF >= 1) {
				panel.repaint();
				frames++;
				deltaF--;
			}
			//debug
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
//				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}

	public void windowFocusLost() {
		if (Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}