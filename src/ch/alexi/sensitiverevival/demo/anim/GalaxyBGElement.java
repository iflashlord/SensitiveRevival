package ch.alexi.sensitiverevival.demo.anim;

import java.awt.Graphics;
import java.awt.Image;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.view.GameBoard;
import ch.alexi.sensitiverevival.view.BoardElement;

public class GalaxyBGElement extends BoardElement {
	Image bgImg;
	float realPosX = 0;
	boolean first = true;
	float msPerPixel = 0;

	public GalaxyBGElement(GameBoard el) {
		super(el);

		bgImg = GameManager.getInst().getImage("/res/galaxy1.jpg");
	}

	@Override
	public void updateElement(GameTimerEvent e) {
		if (first) {
			first = false;
			// How far do I want to travel within a certain amount of time?
			// how many ms per pixel does that mean?
			// ms / pixel = time to take / travel width
			msPerPixel = 15000f / 300; // 15 seconds for 300 pixels
		}
		// OK, so I have the time per pixel, now how many time has
		// gone since last tick? How far (how many pixels) do I have to move then?
		realPosX = (realPosX + new Long(e.timeDelta).floatValue() / msPerPixel) % board.getWidth();
	}
	
	@Override
	public void updateGraphics(Graphics g) {
		if (first) {
			first = false;
			// How far do I want to travel within a certain amount of time?
			// how many ms per pixel does that mean?
			// ms / pixel = time to take / travel width
			msPerPixel = 15000f / 300; // 15 seconds for 300 pixels
		}
		int posX = board.getWidth() - Math.round(realPosX);
		g.drawImage(bgImg, posX, 0, board.getWidth(), board.getHeight(), 
				0, 0, board.getWidth() - posX, board.getHeight(), board);
		g.drawImage(bgImg, 0, 0, posX, board.getHeight(),
				bgImg.getWidth(null)-posX,0,bgImg.getWidth(null),board.getHeight(), board);
	}
}
