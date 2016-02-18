package game;

import java.util.ArrayList;

/**
 * @author Joakim Rydberg.
 */
public class Room {
	private String roomID;
	private LevelPart[][] tiles = new LevelPart[11][11];
	private ArrayList<String> potentialDoors = new ArrayList<>();
	private final static String LEVEL_ID_DIR =  "assets" + Constants.SEP + "levels"  + Constants.SEP;

	public Room(String roomID) {
		this.roomID = roomID;
		ArrayList<Object> objects = FileHandling.readFile(LEVEL_ID_DIR + roomID);

		for (int i = 0; i < 11; i++){
			for (int j = 0; j < 11; j++){
				tiles[i][j] = (LevelPart) objects.get(i * 11 + j);
			}
		}

		locateDoorSpots();
	}

	public LevelPart[][] getTiles() {
		return tiles;
	}

	private void locateDoorSpots(){
		if(tiles[0][6].getType().equals("Door")) {
			potentialDoors.add("North");
		}
		if(tiles[6][0].getType().equals("Door")) {
			potentialDoors.add("West");
		}
		if(tiles[6][10].getType().equals("Door")) {
			potentialDoors.add("East");
		}
		if(tiles[10][6].getType().equals("Door")) {
			potentialDoors.add("South");
		}
	}
}
