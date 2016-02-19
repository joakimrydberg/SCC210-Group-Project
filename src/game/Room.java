package game;

import components.Player;
import components.RoomEntity;
import tools.Constants;
import tools.FileHandling;

import java.util.ArrayList;

/**
 * @author Joakim Rydberg.
 */
public class Room extends RoomEntity {
	private String roomID;

	private ArrayList<String> potentialDoors = new ArrayList<>();

	private final static String LEVEL_ID_DIR = "assets" + Constants.SEP + "levels" + Constants.SEP;
	public Room() {

	}

	public void create(String roomID) {
		this.roomID = roomID;
		ArrayList<Object> objects = FileHandling.readFile(LEVEL_ID_DIR + roomID);
		LevelPart[][] tiles = new LevelPart[11][11];

		for (int i = 0; i < 11; i++){
			for (int j = 0; j < 11; j++){
				tiles[i][j] = (LevelPart) objects.get(i * 11 + j);
			}
		}
		Player p = new Player();
		p.setClass("mage");

		create(tiles);
		addEntity(p);
		locatePotentialDoors();
	}

	public ArrayList<String> getPotentialDoors() {
		return potentialDoors;
	}

	private void locatePotentialDoors(){
		if(getTiles()[0][6].getType().equals("Door")) {
			potentialDoors.add("North");
		}
		if(getTiles()[6][0].getType().equals("Door")) {
			potentialDoors.add("West");
		}
		if(getTiles()[6][10].getType().equals("Door")) {
			potentialDoors.add("East");
		}
		if(getTiles()[10][6].getType().equals("Door")) {
			potentialDoors.add("South");
		}
	}
}
