import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import ch.alexi.sensitiverevival.logic.GameManager;
import ch.alexi.sensitiverevival.logic.GameTimer;
import ch.alexi.sensitiverevival.view.BoardFrame;


public class Main {
	public static void createAndShowGUI() {
		/*
		JFrame f = new AnimMainFrame();
		f.pack();
		f.setVisible(true);
		*/
		
		
		//BoardFrame mf = GameManager.getInst().getBoardFrame();
		/*mf.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent evt) {
				GameTimer.getInst().start();
		    }
		});*/
		GameManager.getInst().start();
	}
	
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
