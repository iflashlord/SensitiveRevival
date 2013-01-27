package ch.alexi.sensitiverevival.logic;

import java.util.Vector;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;

public class GameTimer implements Runnable {
	public final static int FPS = 40; 
	private static GameTimer _inst;
	private Thread _runThread;
	private boolean _isRunning = false;
	private Integer frameNr = 0;
	private Integer lastFrameNr = 0;
	
	private Vector<TimerListener> _listeners;
	
	private enum EventType {
		START,TICK,PAUSE,STOP
	};
	
	private GameTimer() {
		this._runThread = new Thread(this);
		this._listeners = new Vector<TimerListener>();
		
	}
	
	public static GameTimer getInst() {
		if (GameTimer._inst == null) {
			GameTimer._inst = new GameTimer();
		}
		return GameTimer._inst;
	}
	
	public void start() {
		if (!this._isRunning) {
			this._isRunning = true;
			this._runThread.start();
			GameTimerEvent e = new GameTimerEvent(this,this.frameNr,(this.frameNr-this.lastFrameNr));
			this.informTimerListeners(EventType.START,e);
		}
	}
	
	public void pause() {
		if (this._isRunning) {
			this._isRunning = false;
			try {
				this._runThread.join();
				GameTimerEvent e = new GameTimerEvent(this,this.frameNr,(this.frameNr-this.lastFrameNr));
				this.informTimerListeners(EventType.PAUSE,e);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this._runThread = new Thread(this);
	}
	
	public void stop() {
		this.pause();
		this.frameNr = 0;
	}
	
	public boolean isRunning() {
		return this._isRunning;
	}
	
	public void addTimerListener(TimerListener t) {
		if (!this._listeners.contains(t)) {
			this._listeners.add(t);
		}
	}
	
	private void informTimerListeners(EventType t, GameTimerEvent e) {
		switch (t) {
		case START:
			for (TimerListener tl : this._listeners) {
				tl.onTimerStarted(e);
			}
			break;
		case TICK:
			for (TimerListener tl : this._listeners) {
				tl.onTimerTick(e);
			}
			break;
		case PAUSE:
			for (TimerListener tl : this._listeners) {
				tl.onTimerPaused(e);
			}
			break;
		}
	}

	@Override
	public void run() {
		int sleep = Math.round(1000 / GameTimer.FPS);
		long start;
		long deltaSleep = 0;
		GameTimerEvent ev = new GameTimerEvent(this, this.frameNr,0);
		while (this._isRunning) {
			start = System.currentTimeMillis();
			
			ev.actFrame = this.frameNr;
			ev.frameDelta = this.frameNr - this.lastFrameNr;
			this.informTimerListeners(EventType.TICK, ev);
			this.lastFrameNr = this.frameNr;
			
			deltaSleep = sleep - (System.currentTimeMillis() - start);
			
			if (deltaSleep >= 0) {
				this.frameNr++;
				try {
					Thread.sleep(deltaSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				// How much frames do we need to skip? How many times have we overtaken sleep timeframe?
				int skip = new Long(deltaSleep).intValue() / sleep;
				this.frameNr = this.frameNr+1+skip;
				System.err.println("Warning: Game Timer loop took too long to finish: "+deltaSleep);
			}
			
		}
	}
}
