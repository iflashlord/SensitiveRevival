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

import javax.swing.JButton;
import javax.swing.JFrame;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.events.TimerListenerAdapter;
import ch.alexi.sensitiverevival.logic.GameTimer;
import ch.alexi.sensitiverevival.view.Bord;
import ch.alexi.sensitiverevival.view.BordElement;

@SuppressWarnings("serial")
public class AnimMainFrame extends JFrame {
	Bord mainCmp;
	JButton btn;
	BordElement[] BordElements;
	
	GameTimerEvent actTimerEvent;
	Image bufImg = null;
	
	public AnimMainFrame() {
		mainCmp = new Bord() {
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
				
				BordElement el = null;
				
				for (int i = 0; i < BordElements.length; i++) {
					el = BordElements[i];
					if (el != null) {
						el.updateGraphics(bufImg.getGraphics());
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
		
		
		BordElements = new BordElement[9];
		BordElements[0] = new GalaxyBGElement(mainCmp);
		BordElements[1] = new GalaxyBallElement(mainCmp, Color.yellow,20,1);
		BordElements[2] = new GalaxyBallElement(mainCmp, Color.red,80,2);
		BordElements[3] = new GalaxyBallElement(mainCmp, Color.green,140,5);
		BordElements[4] = new GalaxyBallElement(mainCmp, Color.blue,200,10);
		BordElements[5] = new GalaxyBallElement(mainCmp, Color.magenta,260,15);
		BordElements[6] = new GalaxyBallElement(mainCmp, Color.cyan,320,20);
		BordElements[7] = new GalaxyBallElement(mainCmp, Color.gray,380,40);
		BordElements[8] = new GalaxyBallElement(mainCmp, Color.orange,440,60);
		
		
		GameTimer.getInst().addTimerListener(new TimerListenerAdapter() {
			@Override
			public void onTimerTick(GameTimerEvent e) {
				if (bufImg == null) {
					bufImg = mainCmp.createImage(mainCmp.getWidth(), mainCmp.getHeight());
				}
				synchronized (bufImg) {
					AnimMainFrame.this.actTimerEvent = e;
					BordElement el = null;
					if (e != null) {
						for (int i = 0; i < BordElements.length; i++) {
							el = BordElements[i];
							if (el != null) {
								el.onTimerTick(e);
								el.updateGraphics(bufImg.getGraphics());
							}
						}
					}
				}
				
				AnimMainFrame.this.mainCmp.repaint();
			}
		});
	}
}
