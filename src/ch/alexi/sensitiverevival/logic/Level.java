package ch.alexi.sensitiverevival.logic;

import java.awt.Graphics;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.view.BordElement;

public class Level implements TimerListener{
	protected BordElement backgroundElement;
	protected List<BordElement> stoneElements;
	protected List<BordElement> explosionElements;
	protected BordElement playerElement;
	protected JComponent bordComponent;
	
	public Level(JComponent bordComponent) {
		this.bordComponent = bordComponent;
		this.stoneElements = new Vector<BordElement>();
		this.explosionElements = new Vector<BordElement>();
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
		for (BordElement el : stoneElements) {
			el.onTimerTick(event);
		}
		for (BordElement el : explosionElements) {
			el.onTimerTick(event);
		}
		if (playerElement != null) {
			playerElement.onTimerTick(event);
		}
		
	}

	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateGraphics(Graphics g) {
		if (backgroundElement != null) {
			backgroundElement.updateGraphics(g);
		}
		for (BordElement el : stoneElements) {
			el.updateGraphics(g);
		}
		for (BordElement el : explosionElements) {
			el.updateGraphics(g);
		}
		if (playerElement != null) {
			playerElement.updateGraphics(g);
		}
	}
	
}
