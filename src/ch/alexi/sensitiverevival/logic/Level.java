package ch.alexi.sensitiverevival.logic;

import java.awt.Graphics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.alexi.sensitiverevival.events.GameTimerEvent;
import ch.alexi.sensitiverevival.interfaces.TimerListener;
import ch.alexi.sensitiverevival.logic.GameManager.GAME_STATE;
import ch.alexi.sensitiverevival.stones.DoubleStoneElement;
import ch.alexi.sensitiverevival.stones.FixStoneElement;
import ch.alexi.sensitiverevival.stones.NormalStoneElement;
import ch.alexi.sensitiverevival.stones.StartStoneElement;
import ch.alexi.sensitiverevival.stones.StoneElement;
import ch.alexi.sensitiverevival.stones.TargetStoneElement;
import ch.alexi.sensitiverevival.view.Board;
import ch.alexi.sensitiverevival.view.GameBoard;
import ch.alexi.sensitiverevival.view.BoardElement;
import ch.alexi.sensitiverevival.view.GalaxyBGElement;
import ch.alexi.sensitiverevival.view.GameBoard.STATE;
import ch.alexi.sensitiverevival.view.PlayerElement;
import ch.alexi.sensitiverevival.view.StartTextElement;

public class Level implements TimerListener {
	protected int levelNr = 0;
	protected String levelTitle = "";
	protected String levelPassword = "";
	
	protected BoardElement backgroundElement;
	protected BoardElement[][] stoneElements;
	protected BoardElement[][] explosionElements;
	protected PlayerElement playerElement;
	protected StartTextElement startTextElement;
	protected GameBoard bordComponent;
	
	public Level(GameBoard bordComponent) {
		this.bordComponent = bordComponent;
	}
	
	public void setupLevelFromDefinitionObject(JSONObject defObj) {
		this.stoneElements = new BoardElement[GameManager.stonesX][GameManager.stonesY];
		this.explosionElements = new BoardElement[GameManager.stonesX][GameManager.stonesY];
		this.startTextElement = new StartTextElement(bordComponent);
		
		if (defObj.has("levelNr")) this.levelNr = Integer.parseInt(defObj.getString("levelNr"));
		if (defObj.has("levelTitle")) this.levelTitle = defObj.getString("levelTitle");
		if (defObj.has("levelPassword")) this.levelPassword = defObj.getString("levelPassword");
		if (defObj.has("backgroundType")) this.setBackgroundElement(defObj.getString("backgroundType"), this.bordComponent);
		
		try {
			JSONArray def = defObj.getJSONArray("levelDefinition");
			if (def != null) {
				this.setStoneElementsFromJSONArray(def, this.bordComponent);
			}
		} catch (JSONException e) {
			//NOOP
		}
		
		this.backgroundElement = new GalaxyBGElement(bordComponent);
	}
	
	private void setBackgroundElement(String ident, GameBoard board) {
		BoardElement bgElement = null;
		
		if (ident.equals("galaxy1")) {
			bgElement = new GalaxyBGElement(board);
		}
		
		if (bgElement == null) {
			bgElement = new GalaxyBGElement(board);
		}
		if (this.backgroundElement != null) this.backgroundElement.dispose();
		this.backgroundElement = bgElement;
	}
	
	/**
	 * Reads the board stone definition from the given json array. Each array entry
	 * is a string representing one horizontal line of stones, while the stones are the
	 * single chars in the string.
	 * E.g.: "-----------------S11111E---------" defines a single line of simple stones,
	 * surrounded by the start and end pad.
	 * 
	 * Known stone chars:
	 * "-", " ": no stone, background
	 * "S"     : Start stone
	 * "E"     : End / Target stone
	 * "1"     : Normal stone
	 * "2"     : Double stone
	 * "F"     : Fix (gray) stone
	 * 
	 * Unknown chars are treated as none/background.
	 * @param arr
	 * @param board
	 */
	private void setStoneElementsFromJSONArray(JSONArray arr, GameBoard board) {
		BoardElement actEl = null;
		for (int line = 0; line < arr.length(); line++) {
			String lineStr = arr.getString(line);
			for (int col = 0; col < lineStr.length(); col++) {
				char c = lineStr.charAt(col);
				switch (c) {
				case'S': 
					actEl = new StartStoneElement(board, col, line);
					if (this.playerElement != null) this.playerElement.dispose();
					this.playerElement = new PlayerElement(board,col,line);
				break;
				case'1': actEl = new NormalStoneElement(board, col, line);break;
				case'2': actEl = new DoubleStoneElement(board, col, line);break;
				case'F': actEl = new FixStoneElement(board, col, line);break;
				case'E': actEl = new TargetStoneElement(board, col, line);break;
				
				default:
					actEl = null;
					break;
				}
				if (col < this.stoneElements.length && line < this.stoneElements[col].length) {
					this.stoneElements[col][line] = actEl;
				}
			}
		}
	}
	
	public int getLevelNr() {
		return levelNr;
	}

	public String getLevelTitle() {
		return levelTitle;
	}

	public PlayerElement getPlayer() {
		return this.playerElement;
	}

	@Override
	public void onTimerStarted(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerTick(GameTimerEvent event) {
		if (backgroundElement != null) {
			backgroundElement.onTimerTick(event);
		}
		for (int x = 0; x < stoneElements.length; x++) {
			for (int y = 0; y < stoneElements[x].length;y++) {
				if (stoneElements[x][y] != null) {
					stoneElements[x][y].onTimerTick(event);
				}
			}
		}
		
		
		for (int x = 0; x < explosionElements.length; x++) {
			for (int y = 0; y < explosionElements[x].length;y++) {
				if (explosionElements[x][y] != null) {
					explosionElements[x][y].onTimerTick(event);
				}
			}
		}
		
		if (playerElement != null) {
			playerElement.onTimerTick(event);
		}
		
		if (this.bordComponent.getActState() == STATE.START) {
			startTextElement.onTimerTick(event);
		}
	}
	
	public void removeStone(StoneElement stone) {
		this.stoneElements[stone.getBordPosX()][stone.getBordPosY()] = null;
		stone.dispose();
	}

	@Override
	public void onTimerPaused(GameTimerEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateGraphics(Graphics g) {
		if (backgroundElement != null) {
			backgroundElement.updateGraphics(g);
		}
		
		for (int x = 0; x < stoneElements.length; x++) {
			for (int y = 0; y < stoneElements[x].length;y++) {
				if (stoneElements[x][y] != null) {
					stoneElements[x][y].updateGraphics(g);
				}
			}
		}
		
		for (int x = 0; x < explosionElements.length; x++) {
			for (int y = 0; y < explosionElements[x].length;y++) {
				if (explosionElements[x][y] != null) {
					explosionElements[x][y].updateGraphics(g);
				}
			}
		}
		
		if (playerElement != null) {
			playerElement.updateGraphics(g);
		}
		
		startTextElement.updateGraphics(g);
	}
	
	
	public void dispose() {
		if (this.backgroundElement != null) this.backgroundElement.dispose();
		if (this.stoneElements != null) {
			for (int x = 0; x < this.stoneElements.length; x++) {
				for (int y = 0; y < this.stoneElements[x].length; y++) {
					if (this.stoneElements[x][y] != null) {
						this.stoneElements[x][y].dispose();
					}
				}
			}
		}
		
		if (this.explosionElements != null) {
			for (int x = 0; x < this.explosionElements.length; x++) {
				for (int y = 0; y < this.explosionElements[x].length; y++) {
					if (this.explosionElements[x][y] != null) {
						this.explosionElements[x][y].dispose();
					}
				}
			}
		}
		
		if (this.playerElement != null) this.playerElement.dispose();
		if (this.startTextElement != null) this.startTextElement.dispose();
		
		this.bordComponent = null;
	}
	
	
	/**
	 * Checks if the player element is actual "over an abyss", means, is the
	 * player element either driving over background or a dying stone?
	 * 
	 * @return True if player is over abyss, false if not
	 */
	public boolean isPlayerOverAbyss() {
		if (this.playerElement != null) {
			int playerX = this.playerElement.getBordPosX();
			int playerY = this.playerElement.getBordPosY();
			
			// No stone under the player: abyss!
			if (this.stoneElements[playerX][playerY] == null) return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player sits on the end stone and all
	 * removable stones are gone
	 * @return
	 */
	public boolean isPlayerOnTargetAndEverythingIsClean() {
		if (this.playerElement != null) {
			int playerX = this.playerElement.getBordPosX();
			int playerY = this.playerElement.getBordPosY();
			
			// Player sits on target element:
			if (this.stoneElements[playerX][playerY] instanceof TargetStoneElement) {
				// All removable stones are gone:
				boolean allGone = true;
				for (int x = 0; x < this.stoneElements.length; x++) {
					for (int y = 0; y < this.stoneElements[x].length;y++) {
						BoardElement el = this.stoneElements[x][y];
						if (el != null && el instanceof StoneElement) {
							StoneElement s = (StoneElement)el;
							if (s.isRemovable()) allGone = false; // some stones are not removed!
						}
					}
				}
				return allGone;
			}
		}
		return false;
	}
}
