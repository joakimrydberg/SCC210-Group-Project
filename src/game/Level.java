package game;

import java.util.Random;

/**
 * @author Joakim Rydberg.
 */
public class Level {

	private String levelName = "";
	private String difficulty;
	private int numberOfRooms = 0;
	private int maxRooms = 10;
	private Room[][] rooms = new Room[10][10];
	private Room startRoom = new Room(this);
	private Room endRoom;
	private Room currentRoom;

	public Level(String name, String diff) {
		this.levelName = name;
		this.difficulty = diff;

		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				rooms[i][j] = null;
			}
		}


		startRoom.create("test_level"); //renamed
		rooms[3][5] = startRoom;
		currentRoom = startRoom;

		createLayout(4, 5);

/*
		currentRoom.load();
*/

		Room testRoom = new Room(this);
		testRoom.create("test_level"); //renamed
		testRoom.load();

	}

	private void createLayout(int x, int y){
		Random rand = new Random();

	}
}
