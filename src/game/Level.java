package game;

import java.util.Random;

/**
 * @author Joakim Rydberg.
 */
public class Level /*extends Drawer  TODO josh messing with your code, see room entity. (feel free to put this back in) */ {

	private String levelID;
	private Room[][] rooms = new Room[10][10];
	private int numberOfRooms = 0;
	private int maxRooms = 10;
	private Room startRoom;
	private Room endRoom;
	private Room currentRoom;

	public Level() {
		//super("Level " + id);

		this.levelID = "";

		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				rooms[i][j] = null;
			}
		}

		startRoom = (Room)Driver.getDrawer(null, Room.class);

		try {
			startRoom = (startRoom == null) ? (Room) Room.class.newInstance() : startRoom;
		} catch (Exception e) {
			e.printStackTrace();
		}


		rooms[3][5] = startRoom;
		currentRoom = startRoom;

		createLayout(4, 5);

		startRoom.create("test_level");
		startRoom.load();
	}

	private void createLayout(int x, int y){
		Random rand = new Random();

	}
}
