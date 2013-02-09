package ch.alexi.sensitiverevival.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements TimerListener {
	private JPanel statusBarPanel;
	private JLabel fpsLabel;
	
	public MainFrame() {
		this.setTitle("SensitiveRevival");
		this.setResizable(false);
		this.getContentPane().add(GameManager.getInst().getBord(),BorderLayout.CENTER);
		this.fpsLabel = new JLabel();
		this.statusBarPanel = new JPanel() {
			{
				this.add(new JLabel("Actual FPS: "));
				this.add(fpsLabel);
			}
		};
		this.getContentPane().add(this.statusBarPanel,BorderLayout.SOUTH);
		this.addKeyListener(GameManager.getInst());
		GameTimer.getInst().addTimerListener(this);
	}

	@Override
	public void onTimerStarted(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerTick(GameTimerEvent event) {
		this.fpsLabel.setText(Integer.toString(GameTimer.FPS));
		
	}

	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}

}
