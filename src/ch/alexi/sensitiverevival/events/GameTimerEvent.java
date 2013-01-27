package ch.alexi.sensitiverevival.events;

import ch.alexi.sensitiverevival.logic.GameTimer;

public class GameTimerEvent {
	public GameTimer gameTimer = null;
	public int actFrame = 0;
	public int frameDelta = 0;
	
	public GameTimerEvent(GameTimer g, int actFrame, int frameDelta) {
		this.gameTimer = g;
		this.actFrame = actFrame;
		this.frameDelta = frameDelta;
	}
}
