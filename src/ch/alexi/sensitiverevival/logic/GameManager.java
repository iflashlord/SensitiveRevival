package ch.alexi.sensitiverevival.logic;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import ch.alexi.sensitiverevival.view.Bord;

public class GameManager implements KeyListener {
	private static GameManager _inst;
	
	public static int bordWidth = 900;
	public static int bordHeight = 600;
	public static int stoneWidth = 30;
	public static int stoneHeight = 30;
	public static int stonesX = bordWidth / stoneWidth;
	public static int stonesY = bordHeight / stoneHeight;
	public static int playerSpeed = 250; // time in ms to travel 1 stone width
	
	
	private Map<String, Image> images;
	
	private boolean paused = false;
	
	private Bord bord;
	
	private int actKeyCode = 0;
	public int getActKeyCode() {
		return this.actKeyCode;
	}
	
	private GameManager() {
		this.images = new HashMap<String, Image>();
	}
	
	public static GameManager getInst() {
		if (GameManager._inst == null) {
			GameManager._inst = new GameManager();
		}
		return GameManager._inst;
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public Bord getBord() {
		if (this.bord == null) {
			this.bord = new Bord();
		}
		return this.bord;
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
	
}
