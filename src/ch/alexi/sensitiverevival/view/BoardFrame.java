package ch.alexi.sensitiverevival.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.management.snmp.util.JvmContextFactory;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;

@SuppressWarnings("serial")
public class BoardFrame extends JFrame implements TimerListener {
	private JPanel statusBarPanel;
	private JLabel fpsLabel;
	private JPanel boardContainer;
	
	public BoardFrame() {
		this.setTitle("SensitiveRevival");
		this.setResizable(false);
		
		this.boardContainer = new JPanel();
		this.boardContainer.setLayout(new BorderLayout());
		
		this.getContentPane().add(this.boardContainer,BorderLayout.CENTER);
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
		
		// To enable key events catched by the window, set fousable and request focus:
		this.setFocusable(true);
		this.requestFocusInWindow();
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
	
	public JPanel getBoardContainer() {
		return this.boardContainer;
	}
	
	public Board getBoard() {
		if (this.getBoardContainer().getComponentCount() > 0) {
			Component c = this.getBoardContainer().getComponent(0);
			if (c != null && c instanceof Board) {
				return (Board)c;
			}
		}
		return null;
	}
	
	public void setBoard(Board b) {
		if (this.getBoardContainer().getComponentCount() > 0) {
			Component c = this.getBoardContainer().getComponent(0);
			if (c != null && c instanceof Board) {
				Board oldB = (Board)c;
				this.getBoardContainer().remove(oldB);
				oldB.dispose();
				oldB = null;
			}
		}
		this.getBoardContainer().add(b,BorderLayout.CENTER);
		
	}

}
