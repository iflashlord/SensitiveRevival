package ch.alexi.sensitiverevival.view;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import ch.alexi.sensitiverevival.logic.GameManager;

public class MainFrame extends JFrame {

	public MainFrame() {
		this.setTitle("SensitiveRevival");
		this.setResizable(false);
		this.getContentPane().add(GameManager.getInst().getBord(),BorderLayout.CENTER);
	}

}
