package ch.alexi.sensitiverevival.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import ch.alexi.sensitiverevival.view.GameBoard;

public class LevelManager {
	private int actLevelNr = 0;
	private Level actLevel = null;
	
	/**
	 * Loads the next level definition, creates a Level
	 * object and returns it.
	 * 
	 * A Level is stored in a JSON file (level-nnnnn.json),
	 * beginning with level-00001.json. It contains all the
	 * necessary definitions like the board stones, level name,
	 * passkey etc.
	 * 
	 * @return The next level, or null if there is no next level.
	 */
	public Level loadNextLevel(GameBoard board) {
		this.actLevelNr++;
		Level l = this.getLevelObjectFromJsonObject(this.getLevelJson(), board);
		if (l != null) {
			this.actLevel = l;
			board.setLevel(this.actLevel);
		}
		
		return l;
	}
	
	private String getLevelFilename(int nr) {
		System.out.println(String.format("level-%05d", nr));
		return String.format("level-%05d.json", nr);
	}
	
	private String readInputStreamAsString(InputStream stream) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	
	private JSONObject getLevelJson() {
		String lvlNr = this.getLevelFilename(this.actLevelNr);
		InputStream is = this.getClass().getResourceAsStream("/res/levels/"+lvlNr);
		try {
			String levelTxt = this.readInputStreamAsString(is);
			JSONObject levelObj = new JSONObject(levelTxt);
			return levelObj;
		} catch (Exception e) {
			return null;
		}
	}
	
	private Level getLevelObjectFromJsonObject(JSONObject levelJson, GameBoard board) {
		if (levelJson != null) {
			Level level = new Level(board);
			level.setupLevelFromDefinitionObject(levelJson);
			return level;
		}
		return null;
	}
}
