package ch.alexi.sensitiverevival.test;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.Level;
import ch.alexi.sensitiverevival.view.DoubleStoneElement;
import ch.alexi.sensitiverevival.view.FixStoneElement;
import ch.alexi.sensitiverevival.view.NormalStoneElement;

public class StoneTestLevel extends Level {

	public StoneTestLevel(JComponent bordComp) {
		super(bordComp);
		
		// Fill in stones:
		for (int x = 0; x < GameManager.stonesX; x+=2) {
			for (int y = 0; y < GameManager.stonesY; y+=2) {
				FixStoneElement stone = new FixStoneElement(this.bordComponent, x, y);
				this.stoneElements.add(stone);
			}
		}
		
		for (int x = 1; x < GameManager.stonesX; x+=2) {
			for (int y = 1; y < GameManager.stonesY; y+=2) {
				NormalStoneElement stone = new NormalStoneElement(this.bordComponent, x, y);
				this.stoneElements.add(stone);
			}
		}
		
		for (int x = 1; x < GameManager.stonesX; x+=2) {
			for (int y = 0; y < GameManager.stonesY; y+=2) {
				DoubleStoneElement stone = new DoubleStoneElement(this.bordComponent, x, y);
				this.stoneElements.add(stone);
			}
		}
	}

}
