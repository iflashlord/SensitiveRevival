package ch.alexi.sensitiverevival.stones;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.view.GameBoard;

public class DoubleStoneElement extends StoneElement {
	private Image stoneImg1 = GameManager.getInst().getImage("/res/stones/double_stone1_1.png");
	private Image stoneImg2 = GameManager.getInst().getImage("/res/stones/double_stone1_2.png");
	private Image actStone;
	private Point pixelPos;
	private long animCounter = 0;
	
	private enum State {
		INITIAL, PLAYER_ENTERED, PLAYER_LEAVED, PLAYER_ENTERED_2nd_TIME, REMOVED
	}
	private State actState = State.INITIAL;
	private int timeToRemove = GameManager.playerSpeed * 2 + 100;
	
	protected boolean removable = true;
	
	
	public DoubleStoneElement(GameBoard el, int bordPosX, int bordPosY) {
		super(el, bordPosX, bordPosY);
		this.pixelPos = this.bordCoordsToPixel();
		this.actStone = this.stoneImg1;
		
	}

	@Override
	public void updateElement(GameTimerEvent e) {
		this.calcNextAnimationStep(e);
		
		switch (this.actState) {
		case INITIAL:
			if (this.board.getActLevel().getPlayer().bordPosX == this.bordPosX &&
					this.board.getActLevel().getPlayer().bordPosY == this.bordPosY) {
				this.actState = State.PLAYER_ENTERED;
			}
			break;
		case PLAYER_ENTERED:
			this.timeToRemove -= e.timeDelta;
			if (this.timeToRemove < 0) {
				this.actState = State.PLAYER_LEAVED;
				this.timeToRemove = GameManager.playerSpeed * 2 + 100;
			}
			break;
		case PLAYER_LEAVED:
			if (this.board.getActLevel().getPlayer().bordPosX == this.bordPosX &&
					this.board.getActLevel().getPlayer().bordPosY == this.bordPosY) {
				this.actState = State.PLAYER_ENTERED_2nd_TIME;
			}
			break;
		case PLAYER_ENTERED_2nd_TIME:
			this.timeToRemove -= e.timeDelta;
			if (this.timeToRemove < 0) {
				this.actState = State.REMOVED;
				this.removeFromBord();
			}
			break;
		}
	}
	
	private void removeFromBord() {
		this.board.getActLevel().removeStone(this);
	}
	
	private void calcNextAnimationStep(GameTimerEvent e) {
		if (this.actState == State.INITIAL) {
			animCounter += e.timeDelta;
			if (animCounter > 200) {
				animCounter = animCounter - 200;
				if (actStone == stoneImg1) {
					actStone = stoneImg2;
				} else {
					actStone = stoneImg1;
				}
			}
		} else {
			actStone = stoneImg1;
		}
	}

	@Override
	public void updateGraphics(Graphics g) {
		if  (this.actState != State.REMOVED) {
			g.drawImage(this.actStone, this.pixelPos.x, this.pixelPos.y, this.board);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.stoneImg1 = null;
		this.stoneImg2 = null;
	}

}
