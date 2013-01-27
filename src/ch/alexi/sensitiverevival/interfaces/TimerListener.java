package ch.alexi.sensitiverevival.interfaces;

import ch.alexi.sensitiverevival.events.GameTimerEvent;

public interface TimerListener {
	public void onTimerStarted(GameTimerEvent event);
	public void onTimerTick(GameTimerEvent event);
	public void onTimerPaused(GameTimerEvent event);
}
