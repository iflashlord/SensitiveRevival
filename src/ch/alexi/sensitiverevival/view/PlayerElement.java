package ch.alexi.sensitiverevival.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.vecmath.Point2f;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.stones.StoneElement;

public class PlayerElement extends StoneElement {
	private Image stoneImg = GameManager.getInst().getImage("/res/stones/player.png");
	private Point pixelPos;
	private State actState = State.STOP;
	private float msPerPixel = 0;
	private Point2f realPixelPos;
	
	private enum State {
		MOVE_UP,MOVE_RIGHT,MOVE_DOWN,MOVE_LEFT,STOP;
	}
	
	
	public PlayerElement(GameBoard el, int bordPosX, int bordPosY) {
		super(el, bordPosX, bordPosY);
		this.pixelPos = this.bordCoordsToPixel();
		this.realPixelPos = new Point2f(this.pixelPos.x, this.pixelPos.y);
		
		this.msPerPixel = GameManager.playerSpeed / GameManager.stoneWidth; // travel 1 stone in x sec
		
	}

	@Override
	public void updateElement(GameTimerEvent e) {
		if (this.board.getActState() != GameBoard.STATE.RUNNING) return;
		
		
		// Select appropriate state:
		switch (this.actState) {
		case STOP:
			switch (GameManager.getInst().getActKeyCode()) {
			case KeyEvent.VK_UP: this.actState = State.MOVE_UP; this.bordPosY--;break;
			case KeyEvent.VK_RIGHT: this.actState = State.MOVE_RIGHT; this.bordPosX++;break;
			case KeyEvent.VK_DOWN: this.actState = State.MOVE_DOWN; this.bordPosY++;break;
			case KeyEvent.VK_LEFT: this.actState = State.MOVE_LEFT; this.bordPosX--;break;
			}
			break;
		}
		
		// Move the stone:
	
		// How many time has
		// gone since last tick? How far (how many pixels) do I have to move then?
		Point playerBordPixelPos = this.bordCoordsToPixel();
		switch (this.actState) {
		case STOP: break;
		case MOVE_UP:
			this.realPixelPos.y = (realPixelPos.y - new Long(e.timeDelta).floatValue() / msPerPixel);
			if (this.realPixelPos.y < playerBordPixelPos.y) {
				this.realPixelPos.y = playerBordPixelPos.y;
				this.actState = State.STOP;
			}
			break;
		case MOVE_DOWN:
			this.realPixelPos.y = (realPixelPos.y + new Long(e.timeDelta).floatValue() / msPerPixel);
			if (this.realPixelPos.y > playerBordPixelPos.y) {
				this.realPixelPos.y = playerBordPixelPos.y;
				this.actState = State.STOP;
			}
			break;
		case MOVE_RIGHT:
			this.realPixelPos.x = (realPixelPos.x + new Long(e.timeDelta).floatValue() / msPerPixel);
			if (this.realPixelPos.x > playerBordPixelPos.x) {
				this.realPixelPos.x = playerBordPixelPos.x;
				this.actState = State.STOP;
			}
			break;
		case MOVE_LEFT:
			this.realPixelPos.x = (realPixelPos.x - new Long(e.timeDelta).floatValue() / msPerPixel);
			if (this.realPixelPos.x < playerBordPixelPos.x) {
				this.realPixelPos.x = playerBordPixelPos.x;
				this.actState = State.STOP;
			}
			break;
		}
		
		this.pixelPos.x = Math.round(this.realPixelPos.x);
		this.pixelPos.y = Math.round(this.realPixelPos.y);
	}

	@Override
	public void updateGraphics(Graphics g) {
		g.drawImage(this.stoneImg, this.pixelPos.x, this.pixelPos.y, this.board);

	}
	
	@Override
	public void dispose() {
		this.stoneImg = null;
	}

}
