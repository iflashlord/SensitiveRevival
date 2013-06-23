package ch.alexi.sensitiverevival.stones;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.view.GameBoard;

public class StartStoneElement extends StoneElement {
	private Image stoneImg = GameManager.getInst().getImage("/res/stones/start_stone.png");
	private Point pixelPos;
	
	
	public StartStoneElement(GameBoard el, int bordPosX, int bordPosY) {
		super(el, bordPosX, bordPosY);
		this.pixelPos = this.bordCoordsToPixel();
		
	}

	@Override
	public void updateElement(GameTimerEvent e) {

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
