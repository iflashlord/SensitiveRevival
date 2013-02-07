package ch.alexi.sensitiverevival.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.demo.anim.AnimMainFrame;
import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.events.TimerListenerAdapter;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;
import ch.alexi.sensitiverevival.logic.Level;
import ch.alexi.sensitiverevival.test.StoneTestLevel;

public class Bord extends JComponent implements TimerListener{
	private GameTimerEvent _lastGameTimerEvent;
	private Image bufImg = null;
	
	
	private Level actLevel;
	
	public Bord() {
		super();
		this.setMinimumSize(new Dimension(GameManager.bordWidth,GameManager.bordHeight));
		this.setPreferredSize(new Dimension(GameManager.bordWidth,GameManager.bordHeight));
		
		this.actLevel = new StoneTestLevel(this);
		GameTimer.getInst().addTimerListener(this);
		
	}
	
	@Override
	public void update(Graphics g) {
		this.paint(g);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		if (bufImg != null) {
			synchronized (bufImg) {
				g.drawImage(bufImg, 0, 0,this);
			}
		}
	}


	@Override
	public void onTimerStarted(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTimerTick(GameTimerEvent event) {
		Bord.this._lastGameTimerEvent = event;
		if (bufImg == null) {
			bufImg = this.createImage(this.getWidth(), this.getHeight());
		}
		synchronized (bufImg) {
			if (event != null && this.actLevel != null) {
				this.actLevel.onTimerTick(event);
				this.actLevel.updateGraphics(bufImg.getGraphics());
				/*
				for (int i = 0; i < BordElements.length; i++) {
					el = BordElements[i];
					if (el != null) {
						el.onTimerTick(e);
						el.updateGraphics(bufImg.getGraphics());
					}
				}
				*/
			}
		}
		this.repaint();
		
	}


	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
