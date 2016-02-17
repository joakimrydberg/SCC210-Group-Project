package game;

import java.util.Random;

/**
 * @author Joakim Rydberg.
 */
public class Level extends Drawer{
	private String levelID;
	private Room[][] rooms = new Room[10][10];
	private int numberOfRooms = 0;
	private int maxRooms = 10;
	private Room startRoom;
	private Room endRoom;
	private Room currentRoom;

	public Level(String id) {  //TODO A trusty Josh comment.. Updated Drawer (and the rest) so that we don't need the
		super("Level " + id);  //TODO RenderWindow / Driver in the constructor
		this.levelID = id;

		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				rooms[i][j] = null;
			}
		}

		startRoom = new Room("1");
		rooms[3][5] = startRoom;
		currentRoom = startRoom;

		createLayout(4, 5);
	}

	private void createLayout(int x, int y){
		Random rand = new Random();

	}
}
