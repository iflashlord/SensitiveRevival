package ch.alexi.sensitiverevival.demo.anim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.BoardElement;

public class GalaxyBallElement extends BoardElement {
	Color c = Color.yellow;
	int posX = 0;
	float realPosX = 0;
	float msPerPixel = 0;
	int posY = 0;
	int width = 30;
	int speed = 1;
	boolean first = true;
	
	public GalaxyBallElement(JComponent el, Color c,int posY,int speed) {
		super(el);
		this.c = c;
		this.posY = posY;
		this.speed = speed;
	}
	
	@Override
	public void onTimerTick(GameTimerEvent e) {
		if (first) {
			first = false;
			// How far do I want to travel within a certain amount of time?
			// how many ms per pixel does that mean?
			// ms / pixel = time to take / travel width
			msPerPixel = 15000f / speed / this.boardElement.getWidth();
		}
		
		// OK, so I have the time per pixel, now how many time has
		// gone since last tick? How far (how many pixels) do I have to move then?
		realPosX = (realPosX + new Long(e.timeDelta).floatValue() / msPerPixel) % boardElement.getWidth();
				
	}
	
	@Override
	public void updateGraphics(Graphics g) {
		if (first) {
			first = false;
			// How far do I want to travel within a certain amount of time?
			// how many ms per pixel does that mean?
			// ms / pixel = time to take / travel width
			msPerPixel = 15000f / speed / this.boardElement.getWidth();
		}
		
		g.setColor(c);
		g.fillRect(Math.round(realPosX), posY, width, width);
		
		g.setColor(Color.white);
		g.drawString("Travels the width in "+(15.0/speed)+" seconds, that means "+msPerPixel+"ms per pixel", 20, posY + 50);
	}
}
