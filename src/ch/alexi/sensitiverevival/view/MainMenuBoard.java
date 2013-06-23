package ch.alexi.sensitiverevival.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;

public class MainMenuBoard extends Board implements TimerListener {
	private JButton startBtn;
	private JButton quitBtn;
	
	public MainMenuBoard() {
			this.setMinimumSize(new Dimension(GameManager.boardWidth,GameManager.boardHeight));
			this.setPreferredSize(new Dimension(GameManager.boardWidth,GameManager.boardHeight));
			
			GameTimer.getInst().addTimerListener(this);
			
			this.startBtn = new JButton("Start Game");
			this.startBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GameManager.getInst().startNextLevel();
				}
			});
			
			
			this.quitBtn = new JButton("Quit Game");
			this.quitBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			
			this.add(this.startBtn);
			this.add(this.quitBtn);
	}

	@Override
	public void onTimerStarted(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerTick(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void dispose() {
		GameTimer.getInst().removeTimerListener(this);
	}
}
