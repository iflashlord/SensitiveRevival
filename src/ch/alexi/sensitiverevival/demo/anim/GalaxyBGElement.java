package ch.alexi.sensitiverevival.demo.anim;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.BoardElement;
import ch.alexi.sensitiverevival.logic.GameManager;

public class GalaxyBGElement extends BoardElement {
	Image bgImg;
	float counter = 0;
	boolean first = true;
	float ppf = 0f;

	public GalaxyBGElement(JComponent el) {
		super(el);

		bgImg = GameManager.getInst().getImage("/res/galaxy1.jpg");
	}

	@Override
	public void onTimerTick(GameTimerEvent e, Graphics g) {
		this.drawActFrame(g);
		counter += e.frameDelta*ppf;
		counter = counter % boardElement.getWidth();
	}
	
	@Override
	public void drawActFrame(Graphics g) {
		if (first) {
			first = false;
			// How long to travel the whole width? We want to do it in
			// 60secs:
			ppf = this.pixelPerFrame(60.0f, boardElement.getWidth());
			
		}
		int posX = boardElement.getWidth() - Math.round(counter);
		g.drawImage(bgImg, posX, 0, boardElement.getWidth(), boardElement.getHeight(), 
				0, 0, boardElement.getWidth() - posX, boardElement.getHeight(), boardElement);
		g.drawImage(bgImg, 0, 0, posX, boardElement.getHeight(),
				bgImg.getWidth(null)-posX,0,bgImg.getWidth(null),boardElement.getHeight(), boardElement);
	}
}
