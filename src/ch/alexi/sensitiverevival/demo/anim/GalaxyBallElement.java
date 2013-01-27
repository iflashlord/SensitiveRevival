package ch.alexi.sensitiverevival.demo.anim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.BoardElement;
import ch.alexi.sensitiverevival.logic.GameTimer;

public class GalaxyBallElement extends BoardElement {
	Color c = Color.yellow;
	int posX = 0;
	float realPosX = 0;
	float ppf = 0;
	int posY = 0;
	int width = 30;
	int speed = 5;
	boolean first = true;
	
	public GalaxyBallElement(JComponent el, Color c,int posY,int speed) {
		super(el);
		this.c = c;
		this.posY = posY;
		this.speed = speed;
	}
	
	@Override
	public void onTimerTick(GameTimerEvent e, Graphics g) {
		this.drawActFrame(g);
		
		realPosX = (realPosX + ppf*e.frameDelta) % boardElement.getWidth();
	}
	
	@Override
	public void drawActFrame(Graphics g) {
		if (first) {
			first = false;
			// How long to travel the whole width? We want to do it in
			// 5secs:
			// 15*fps = nr of frames until goal is reached
			// width / nr of frames = add pixels per frame
			ppf = this.pixelPerFrame(15.0f/speed, boardElement.getWidth());
			
		}
		g.setColor(c);
		g.fillRect(Math.round(realPosX), posY, width, width);
		
		g.setColor(Color.white);
		g.drawString("Travels the width in "+(15.0/speed)+" seconds, that means "+ppf+"px/frame by "+GameTimer.FPS+"fps", 20, posY + 50);
	}
}
