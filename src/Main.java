import javax.swing.JFrame;

import ch.alexi.sensitiverevival.demo.anim.AnimMainFrame;


public class Main {
	public static void createAndShowGUI() {
		JFrame f = new AnimMainFrame();
		f.pack();
		f.setVisible(true);
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
