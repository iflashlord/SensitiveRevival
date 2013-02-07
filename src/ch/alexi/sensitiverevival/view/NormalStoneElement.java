package ch.alexi.sensitiverevival.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;

public class NormalStoneElement extends StoneElement {
	private Image stoneImg = GameManager.getInst().getImage("/res/stones/normal_stone1.png");
	private Point pixelPos;
	
	
	public NormalStoneElement(JComponent el, int bordPosX, int bordPosY) {
		super(el, bordPosX, bordPosY);
		this.pixelPos = this.bordCoordsToPixel();
		
	}

	@Override
	public void updateElement(GameTimerEvent e) {

	}

	@Override
	public void updateGraphics(Graphics g) {
		g.drawImage(this.stoneImg, this.pixelPos.x, this.pixelPos.y, this.bordElement);

	}

}
