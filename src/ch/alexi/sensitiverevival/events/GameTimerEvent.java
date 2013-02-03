package ch.alexi.sensitiverevival.events;

import ch.alexi.sensitiverevival.logic.GameTimer;

public class GameTimerEvent {
	public GameTimer gameTimer = null;
	
	public long actTime;
	public long timeDelta;
	
	public GameTimerEvent(GameTimer g, long actTime, long timeDelta) {
		this.gameTimer = g;
		this.actTime = actTime;
		this.timeDelta = timeDelta;
	}
}
