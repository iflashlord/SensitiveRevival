package ch.alexi.sensitiverevival.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;
import ch.alexi.sensitiverevival.logic.Level;
import ch.alexi.sensitiverevival.test.StoneTestLevel;

@SuppressWarnings("serial")
public class GameBoard extends Board implements TimerListener {
	private Image bufImg = null;
	private Level actLevel;
	private STATE actState = STATE.START;
	private long startTimeout = 0; // Duration of the start sequence (state START > RUNNING)
	
	public enum STATE {
		START, RUNNING, DEATH_INVOKED, DEATH, DONE, DISPOSED
	};
	
	public GameBoard() {
		super();
		this.setMinimumSize(new Dimension(GameManager.boardWidth,
				GameManager.boardHeight));
		this.setPreferredSize(new Dimension(GameManager.boardWidth,
				GameManager.boardHeight));
		this.setSize(GameManager.boardWidth,
				GameManager.boardHeight);
		GameTimer.getInst().addTimerListener(this);
	}
	
	public void setLevel(Level l) {
		if (this.actLevel != null) this.actLevel.dispose();
		this.actLevel = l;
	}

	public Level getActLevel() {
		return this.actLevel;
	}
	
	public STATE getActState() {
		return this.actState;
	}

	@Override
	public void update(Graphics g) {
		this.paint(g);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (bufImg != null) {
			synchronized (bufImg) {
				g.drawImage(bufImg, 0, 0, this);
			}
		}
	}

	@Override
	public void onTimerStarted(GameTimerEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTimerTick(GameTimerEvent event) {
		if (this.actState == STATE.DISPOSED) return;
		if (this.actLevel == null) return;
		
		if (bufImg == null) {
			bufImg = this.createImage(GameManager.boardWidth,GameManager.boardHeight);
		}

		if (bufImg != null) {
			synchronized (bufImg) {
				if (event != null && this.actLevel != null) {
					this.actLevel.onTimerTick(event);
					this.actLevel.updateGraphics(bufImg.getGraphics());
				}
			}
			this.repaint();
		}
		
		switch (this.getActState()) {
		case START:
			if (startTimeout < 3000) {
				startTimeout += event.timeDelta;
			} else {
				this.startTimeout = 0;
				this.actState = STATE.RUNNING;
			}
			break;
		case RUNNING:
			// collision detection, respectively check if player is death / on end stone.
			if (this.getActLevel().isPlayerOverAbyss()) {
				this.actState = STATE.DEATH_INVOKED;
			}
			
			if (this.getActLevel().isPlayerOnTargetAndEverythingIsClean()) {
				this.actState = STATE.DONE;
				GameManager.getInst().startNextLevel();
			}
			
			break;
		case DEATH_INVOKED:
			this.actState = STATE.DEATH;
			GameManager.getInst().playerDeath();
			break;
		case DEATH: break;
		case DONE: break;
		}
		
	}

	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		this.actState = STATE.DISPOSED;
		GameTimer.getInst().removeTimerListener(this);
		if (this.actLevel != null) this.actLevel.dispose();
		this.actLevel = null;
		this.bufImg = null;
	}
}
