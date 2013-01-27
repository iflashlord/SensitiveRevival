package ch.alexi.sensitiverevival.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.events.TimerListenerAdapter;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameTimer;

public class Board extends JComponent {
	private int _nrOfLayers;
	private int _actFrame = 0;
	
	public Board(int nrOfLayers) {
		super();
		this._nrOfLayers = nrOfLayers;
		this.setMinimumSize(new Dimension(800,600));
		this.setPreferredSize(new Dimension(800,600));
		GameTimer.getInst().addTimerListener(new TimerListenerAdapter() {
			@Override
			public void onTimerTick(GameTimerEvent e) {
				Board.this._actFrame = new Long(4*e.actFrame % 256).intValue();
				Board.this.repaint();
			}
		});
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(this._actFrame, this._actFrame, this._actFrame));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
}
