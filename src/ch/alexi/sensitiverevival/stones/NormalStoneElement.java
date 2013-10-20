package ch.alexi.sensitiverevival.stones;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.view.GameBoard;

public class NormalStoneElement extends StoneElement {
	private Image stoneImg = GameManager.getInst().getImage("/res/stones/normal_stone1.png");
	private Point pixelPos;
	private enum State {
		INITIAL, PLAYER_ENTERED, REMOVED
	}
	private State actState = State.INITIAL;
	private int timeToRemove = GameManager.playerSpeed * 2 + 100;
	
	protected boolean removable = true;
	
	
	public NormalStoneElement(GameBoard el, int bordPosX, int bordPosY) {
		super(el, bordPosX, bordPosY);
		this.pixelPos = this.bordCoordsToPixel();
		
	}

	@Override
	public void updateElement(GameTimerEvent e) {
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
				this.actState = State.REMOVED;
				this.removeFromBord();
			}
			break;
		}
	}
	
	private void removeFromBord() {
		this.board.getActLevel().removeStone(this);
	}
	
	@Override
	public void updateGraphics(Graphics g) {
		if (this.actState != State.REMOVED) {
			g.drawImage(this.stoneImg, this.pixelPos.x, this.pixelPos.y, this.board);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.stoneImg = null;
	}

}
