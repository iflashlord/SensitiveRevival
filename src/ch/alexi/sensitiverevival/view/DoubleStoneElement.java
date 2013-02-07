package ch.alexi.sensitiverevival.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.logic.GameManager;

public class DoubleStoneElement extends StoneElement {
	private Image stoneImg1 = GameManager.getInst().getImage("/res/stones/double_stone1_1.png");
	private Image stoneImg2 = GameManager.getInst().getImage("/res/stones/double_stone1_2.png");
	private Image actStone;
	private Point pixelPos;
	
	private long msCounter = 0;
	
	
	public DoubleStoneElement(JComponent el, int bordPosX, int bordPosY) {
		super(el, bordPosX, bordPosY);
		this.pixelPos = this.bordCoordsToPixel();
		this.actStone = this.stoneImg1;
		
	}

	@Override
	public void updateElement(GameTimerEvent e) {
		msCounter += e.timeDelta;
		if (msCounter > 200) {
			msCounter = msCounter - 200;
			if (actStone == stoneImg1) {
				actStone = stoneImg2;
			} else {
				actStone = stoneImg1;
			}
		}
	}

	@Override
	public void updateGraphics(Graphics g) {
		g.drawImage(this.actStone, this.pixelPos.x, this.pixelPos.y, this.bordElement);

	}

}
