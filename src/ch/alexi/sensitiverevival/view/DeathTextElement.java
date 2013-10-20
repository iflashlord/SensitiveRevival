package ch.alexi.sensitiverevival.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import ch.alexi.sensitiverevival.events.GameTimerEvent;

public class DeathTextElement extends BoardElement {
	Font textFont;
	FontMetrics metrics;
	Color color;
	int textHeight;

	public DeathTextElement(GameBoard el) {
		super(el);
		textFont = new Font("Arial", Font.BOLD,48);
		metrics = this.board.getFontMetrics(textFont);
		textHeight = metrics.getHeight();
		color = Color.WHITE;
	}

	@Override
	public void updateElement(GameTimerEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateGraphics(Graphics g) {
		String text = "ABYSS!!!";
		if (this.board.getActState() == GameBoard.STATE.START) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, board.getWidth(), board.getHeight());
			g.setColor(color);
			g.setFont(textFont);
			g.drawString(text, (this.board.getWidth()-metrics.stringWidth(text))/2, (this.board.getHeight()-this.textHeight)/2);
		}
	}
}
