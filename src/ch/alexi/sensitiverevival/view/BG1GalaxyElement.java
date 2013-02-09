package ch.alexi.sensitiverevival.view;

import java.awt.Graphics;
import java.awt.Image;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;

public class BG1GalaxyElement extends BordElement {
	Image bgImg;
	float realPosX = 0;
	float msPerPixel = 0;

	public BG1GalaxyElement(Bord el) {
		super(el);

		bgImg = GameManager.getInst().getImage("/res/galaxy1.jpg");
		msPerPixel = 15000f / 300; // 15 seconds for 300 pixels
	}

	@Override
	public void updateElement(GameTimerEvent e) {
		// OK, so I have the time per pixel, now how many time has
		// gone since last tick? How far (how many pixels) do I have to move then?
		realPosX = (realPosX + new Long(e.timeDelta).floatValue() / msPerPixel) % bordElement.getWidth();
	}
	
	@Override
	public void updateGraphics(Graphics g) {
		int posX = bordElement.getWidth() - Math.round(realPosX);
		g.drawImage(bgImg, posX, 0, bordElement.getWidth(), bordElement.getHeight(), 
				0, 0, bordElement.getWidth() - posX, bordElement.getHeight(), bordElement);
		g.drawImage(bgImg, 0, 0, posX, bordElement.getHeight(),
				bgImg.getWidth(null)-posX,0,bgImg.getWidth(null),bordElement.getHeight(), bordElement);
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		this.bgImg = null;
	}
}
