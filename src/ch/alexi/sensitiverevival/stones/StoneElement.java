package ch.alexi.sensitiverevival.stones;

import java.awt.Point;

import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.view.BoardElement;
import ch.alexi.sensitiverevival.view.GameBoard;

public abstract class StoneElement extends BoardElement {
	protected int bordPosX, bordPosY;
	protected boolean removable = false;

	public StoneElement(GameBoard el, int bordPosX, int bordPosY) {
		super(el);
		this.bordPosX = bordPosX;
		this.bordPosY = bordPosY;
	}
	
	public boolean isRemovable() {
		return this.removable;
	}
	
	public int getBordPosX() {
		return this.bordPosX;
	}
	public int getBordPosY() {
		return this.bordPosY;
	}
	
	public Point bordCoordsToPixel(int bordX, int bordY) {
		return new Point(bordX * GameManager.stoneWidth, bordY * GameManager.stoneHeight);
	}
	
	public Point bordCoordsToPixel() {
		return this.bordCoordsToPixel(this.bordPosX, bordPosY);
	}
}
