package ch.alexi.sensitiverevival.logic;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import ch.alexi.sensitiverevival.view.Board;
import ch.alexi.sensitiverevival.view.GameBoard;
import ch.alexi.sensitiverevival.view.BoardFrame;
import ch.alexi.sensitiverevival.view.MainMenuBoard;

public class GameManager implements KeyListener {
	private static GameManager _inst;
	private LevelManager lm;
	
	public static int boardWidth = 900;
	public static int boardHeight = 600;
	public static int stoneWidth = 30;
	public static int stoneHeight = 30;
	public static int stonesX = boardWidth / stoneWidth;
	public static int stonesY = boardHeight / stoneHeight;
	public static int playerSpeed = 250; // time in ms to travel 1 stone width
	
	public enum GAME_STATE {
		STATE_MAIN_MENU,
		STATE_PREPARE_LEVEL,
		STATE_GAME_RUNNING,
		STATE_GAME_PAUSED,
		STATE_END_SEQUENCE
	};
	
	private GAME_STATE actState = GAME_STATE.STATE_MAIN_MENU;
	private GAME_STATE prevState = null; // used for pause / unpause game
	
	public GAME_STATE getActState() {
		return actState;
	}

	private Map<String, Image> images;
	
	//private Board board;
	private BoardFrame boardFrame;
	
	private int actKeyCode = 0;
	public int getActKeyCode() {
		return this.actKeyCode;
	}
	
	private GameManager() {
		this.images = new HashMap<String, Image>();
		this.lm = new LevelManager();
	}
	
	public static GameManager getInst() {
		if (GameManager._inst == null) {
			GameManager._inst = new GameManager();
		}
		return GameManager._inst;
	}
	
	public boolean isPaused() {
		return this.actState == GAME_STATE.STATE_GAME_PAUSED;
	}

	public void setPaused(boolean paused) {
		if (paused) {
			// enter pause state:
			if (this.actState != GAME_STATE.STATE_GAME_PAUSED) {
				this.prevState = this.actState;
				this.actState = GAME_STATE.STATE_GAME_PAUSED;
			}
		} else {
			// exit pause state, restore previous state:
			this.actState = this.prevState;
		}
	}
	/*
	public Board getActBord() {
		return this.board;
	}
	*/
	
	public BoardFrame getBoardFrame() {
		if (this.boardFrame == null) {
			this.boardFrame = new BoardFrame();
			//this.boardFrame.setBoard(this.getActBord());
		}
		return this.boardFrame;
	}
	
	
	public Image getImage(String resKey) {
		if (this.images.containsKey(resKey)) {
			return this.images.get(resKey);
		} else {
			// ATTENTION: DO not use Toolkit.getDefaultToolkit().create/getImage,
			// as the image is NOT yet ready when the function returns! It seems
			// that those functions are running asynchronous!
			// Instead, use this approach:
			BufferedImage im = null;
			try {
			    im = ImageIO.read(getClass().getResource(resKey));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Image im = Toolkit.getDefaultToolkit().createImage(getClass().getResource(resKey));
			this.images.put(resKey, im);
			return im;
		}
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		this.actKeyCode = e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		this.actKeyCode = 0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Starts the game, means, returns to the start state (main menu):
	 * - sets the initial state
	 * - starts the game timer
	 * - set up the board frame with the initial (main menu) board
	 */
	public void start() {
		this.setPaused(false);
		this.actState = GAME_STATE.STATE_MAIN_MENU;
		this.getBoardFrame().setBoard(new MainMenuBoard());
		this.getBoardFrame().pack();
		this.getBoardFrame().setVisible(true);
		GameTimer.getInst().start();
	}
	
	
	public void startNextLevel() {
		this.setPaused(true);
		this.actState = GAME_STATE.STATE_PREPARE_LEVEL;
		
		//TODO: Load level somehow
		GameBoard board = new GameBoard();
		Level nextLevel = this.lm.loadNextLevel(board);
		if (nextLevel != null) {
			this.getBoardFrame().setBoard(board);
			this.actState = GAME_STATE.STATE_GAME_RUNNING;
			this.setPaused(false);
		} else {
			this.endSequence();
		}
	}
	
	public void endSequence() {
		this.setPaused(false);
		this.actState = GAME_STATE.STATE_END_SEQUENCE;
	}
}
