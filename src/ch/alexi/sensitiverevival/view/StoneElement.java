package ch.alexi.sensitiverevival.view;

import java.awt.Point;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.logic.GameManager;

public abstract class StoneElement extends BordElement {
	protected int bordPosX, bordPosY;

	public StoneElement(JComponent el, int bordPosX, int bordPosY) {
		super(el);
		this.bordPosX = bordPosX;
		this.bordPosY = bordPosY;
	}
	
	public Point bordCoordsToPixel(int bordX, int bordY) {
		return new Point(bordX * GameManager.stoneWidth, bordY * GameManager.stoneHeight);
	}
	
	public Point bordCoordsToPixel() {
		return this.bordCoordsToPixel(this.bordPosX, bordPosY);
	}
}
