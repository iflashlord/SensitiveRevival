package ch.alexi.sensitiverevival.logic;

import java.awt.Graphics;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.view.Bord;
import ch.alexi.sensitiverevival.view.BordElement;
import ch.alexi.sensitiverevival.view.GalaxyBGElement;
import ch.alexi.sensitiverevival.view.PlayerElement;
import ch.alexi.sensitiverevival.view.StoneElement;

public class Level implements TimerListener{
	protected BordElement backgroundElement;
	protected BordElement[][] stoneElements;
	protected BordElement[][] explosionElements;
	protected PlayerElement playerElement;
	protected Bord bordComponent;
	
	public Level(Bord bordComponent) {
		this.bordComponent = bordComponent;
		this.backgroundElement = new GalaxyBGElement(bordComponent);
		this.stoneElements = new BordElement[GameManager.stonesX][GameManager.stonesY];
		this.explosionElements = new BordElement[GameManager.stonesX][GameManager.stonesY];
		this.playerElement = new PlayerElement(bordComponent, 4, 4);
	}
	
	public PlayerElement getPlayer() {
		return this.playerElement;
	}

	@Override
	public void onTimerStarted(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerTick(GameTimerEvent event) {
		if (backgroundElement != null) {
			backgroundElement.onTimerTick(event);
		}
		for (int x = 0; x < stoneElements.length; x++) {
			for (int y = 0; y < stoneElements[x].length;y++) {
				if (stoneElements[x][y] != null) {
					stoneElements[x][y].onTimerTick(event);
				}
			}
		}
		
		for (int x = 0; x < explosionElements.length; x++) {
			for (int y = 0; y < explosionElements[x].length;y++) {
				if (explosionElements[x][y] != null) {
					explosionElements[x][y].onTimerTick(event);
				}
			}
		}
		
		if (playerElement != null) {
			playerElement.onTimerTick(event);
		}
	}
	
	public void removeStone(StoneElement stone) {
		this.stoneElements[stone.getBordPosX()][stone.getBordPosY()] = null;
		stone.dispose();
	}

	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateGraphics(Graphics g) {
		if (backgroundElement != null) {
			backgroundElement.updateGraphics(g);
		}
		
		for (int x = 0; x < stoneElements.length; x++) {
			for (int y = 0; y < stoneElements[x].length;y++) {
				if (stoneElements[x][y] != null) {
					stoneElements[x][y].updateGraphics(g);
				}
			}
		}
		
		for (int x = 0; x < explosionElements.length; x++) {
			for (int y = 0; y < explosionElements[x].length;y++) {
				if (explosionElements[x][y] != null) {
					explosionElements[x][y].updateGraphics(g);
				}
			}
		}
		
		if (playerElement != null) {
			playerElement.updateGraphics(g);
		}
	}
	
}
