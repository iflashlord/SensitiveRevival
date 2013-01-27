package ch.alexi.sensitiverevival.demo.anim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.events.TimerListenerAdapter;
import ch.alexi.sensitiverevival.interfaces.BoardElement;
import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;

public class AnimMainFrame extends JFrame {
	JComponent mainCmp;
	JButton btn;
	BoardElement[] boardElements;
	
	GameTimerEvent actTimerEvent;
	Image bufImg = null;
	
	public AnimMainFrame() {
		mainCmp = new JComponent() {
			{
				this.setMinimumSize(new Dimension(800, 600));
				this.setPreferredSize(new Dimension(800, 600));
			}
			
			@Override
			public void update(Graphics g) {
				this.paint(g);
			}
			
			@Override
			public void paint(Graphics g) {
				if (bufImg != null) {
					synchronized (bufImg) {
						g.drawImage(bufImg, 0, 0,this);
					}
				}
			}
		};
		this.getContentPane().add(mainCmp,BorderLayout.CENTER);
		
		this.addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent arg0) {
				if (bufImg == null) {
					bufImg = createImage(mainCmp.getWidth(), mainCmp.getHeight());
				}
				
				BoardElement el = null;
				
				for (int i = 0; i < boardElements.length; i++) {
					el = boardElements[i];
					if (el != null) {
						el.drawActFrame(bufImg.getGraphics());
					}
				}
				mainCmp.repaint();
				
			}
			public void componentResized(ComponentEvent arg0) {
			}
			public void componentMoved(ComponentEvent arg0) {
			}
			public void componentHidden(ComponentEvent arg0) {
			}
		});
		
		btn = new JButton("Start");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GameTimer.getInst().isRunning()) {
					GameTimer.getInst().pause();
					btn.setText("Start");
				} else {
					GameTimer.getInst().start();
					btn.setText("Pause");
				}
			}
		});
		this.getContentPane().add(btn,BorderLayout.NORTH);

		this.setResizable(false);
		this.setTitle("Animation Demo");
		
		
		boardElements = new BoardElement[8];
		boardElements[0] = new GalaxyBGElement(mainCmp);
		boardElements[1] = new GalaxyBallElement(mainCmp, Color.yellow,20,1);
		boardElements[2] = new GalaxyBallElement(mainCmp, Color.red,80,2);
		boardElements[3] = new GalaxyBallElement(mainCmp, Color.green,140,5);
		boardElements[4] = new GalaxyBallElement(mainCmp, Color.blue,200,10);
		boardElements[5] = new GalaxyBallElement(mainCmp, Color.magenta,260,15);
		boardElements[6] = new GalaxyBallElement(mainCmp, Color.cyan,320,20);
		boardElements[7] = new GalaxyBallElement(mainCmp, Color.gray,380,40);
		
		
		GameTimer.getInst().addTimerListener(new TimerListenerAdapter() {
			@Override
			public void onTimerTick(GameTimerEvent e) {
				if (bufImg == null) {
					bufImg = mainCmp.createImage(mainCmp.getWidth(), mainCmp.getHeight());
				}
				synchronized (bufImg) {
					AnimMainFrame.this.actTimerEvent = e;
					BoardElement el = null;
					if (e != null) {
						for (int i = 0; i < boardElements.length; i++) {
							el = boardElements[i];
							if (el != null) {
								el.onTimerTick(e, bufImg.getGraphics());
							}
						}
					}
				}
				
				AnimMainFrame.this.mainCmp.repaint();
			}
		});
	}
	
}
