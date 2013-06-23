package ch.alexi.sensitiverevival.test;

import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.Level;
import ch.alexi.sensitiverevival.stones.DoubleStoneElement;
import ch.alexi.sensitiverevival.stones.FixStoneElement;
import ch.alexi.sensitiverevival.stones.NormalStoneElement;
import ch.alexi.sensitiverevival.view.GameBoard;

public class StoneTestLevel extends Level {

	public StoneTestLevel(GameBoard bordComp) {
		super(bordComp);
		
		// Fill in stones:
		for (int x = 0; x < GameManager.stonesX; x+=2) {
			for (int y = 0; y < GameManager.stonesY; y+=2) {
				FixStoneElement stone = new FixStoneElement(this.bordComponent, x, y);
				this.stoneElements[x][y] = stone;
			}
		}
		
		for (int x = 1; x < GameManager.stonesX; x+=2) {
			for (int y = 1; y < GameManager.stonesY; y+=2) {
				NormalStoneElement stone = new NormalStoneElement(this.bordComponent, x, y);
				this.stoneElements[x][y] = stone;
			}
		}
		
		for (int x = 1; x < GameManager.stonesX; x+=2) {
			for (int y = 0; y < GameManager.stonesY; y+=2) {
				DoubleStoneElement stone = new DoubleStoneElement(this.bordComponent, x, y);
				this.stoneElements[x][y] = stone;
			}
		}
	}

}
