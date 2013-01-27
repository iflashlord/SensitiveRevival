package ch.alexi.sensitiverevival.logic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class GameManager {
	private static GameManager _inst;
	
	private Map<String, Image> images;
	
	private GameManager() {
		this.images = new HashMap<String, Image>();
	}
	
	public static GameManager getInst() {
		if (GameManager._inst == null) {
			GameManager._inst = new GameManager();
		}
		return GameManager._inst;
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
	
}
