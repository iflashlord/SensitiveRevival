package ch.alexi.sensitiverevival.view;

import java.awt.Graphics;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;

public abstract class BordElement {
	protected Bord bordElement;
	
	public BordElement(Bord el) {
		this.bordElement = el;
	}
	
	public void onTimerTick(GameTimerEvent e) {
		if (!GameManager.getInst().isPaused()) {
			this.updateElement(e);
		}
	}
	
	/**
	 *  Must be implemented in child classes: Is called from
	 *  within onTimerTick whenever the element is not paused
	 * @param e
	 */
	abstract public void updateElement (GameTimerEvent e);
	
	abstract public void updateGraphics(Graphics g);
	
	
	
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
	
	
	/**
	 * to be called whenever the element is removed from the bord, to clean
	 * up references and stuff
	 */
	public void dispose() {
		this.bordElement = null;
	}
}
