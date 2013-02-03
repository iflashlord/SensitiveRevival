package ch.alexi.sensitiverevival.interfaces;

import java.awt.Graphics;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameTimer;

public abstract class BoardElement {
	protected JComponent boardElement;
	
	public BoardElement(JComponent el) {
		this.boardElement = el;
	}
	
	public void onTimerTick(GameTimerEvent e) {
		
	}
	
	public void updateGraphics(Graphics g) {
		
	}
	
	/**
	 * Calcs how many pixels per frame an animation must run
	 * to travel a given width within a given duration (in seconds).
	 * 
	 * @param duration How long (in seconds) the travel should take
	 * @param width How far the travel goes (in px)
	 * @return The pixels per frame to travel forward
	 */
	public float pixelPerFrame(float duration, int width) {
		// How long to travel the whole width? We want to do it in
		// duration seconds:
		// duration * fps = nr of frames until goal is reached
		// width / nr of frames = add pixels per frame
		return width / (duration * GameTimer.FPS);
	}
}
