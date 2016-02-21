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
	private Room endRoom = null;
	private Room currentRoom;

	public Level(String name, String diff) {
		this.levelName = name;
		this.difficulty = diff;

		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				rooms[i][j] = null;
			}
		}


		//startRoom.create("test_level"); //renamed
	//	rooms[3][5] = startRoom;
	//	currentRoom = startRoom;

		//startRoom.addDoor("South");
		//setRoom(4, 5, "North");

		//currentRoom.load();

		Room testRoom = new Room(this);
		testRoom.create("test_level");
		testRoom.load();

	}

	private void setLayout(int x, int y) {
		if (numberOfRooms < maxRooms) {
			Random rn = new Random();
			for (String potentialDoor : rooms[x][y].getPotentialDoors().keySet()) {
				if (rn.nextInt(4) == 0) {
					switch (potentialDoor) {
						case "North":
							if (rooms[x-1][y] != null) {
								if(connectRoom(x-1, y, "South")) rooms[x][y].addDoor("North");
							} else {
								setRoom(x-1, y, "South");
								rooms[x][y].addDoor("North");
							}
							break;
						case "East":
							if (rooms[x-1][y] != null) {
								if(connectRoom(x-1, y, "West")) rooms[x][y].addDoor("East");
							} else {
								setRoom(x-1, y, "West");
								rooms[x][y].addDoor("East");
							}
							break;
						case "South":
							if (rooms[x-1][y] != null) {
								if(connectRoom(x-1, y, "North")) rooms[x][y].addDoor("South");
							} else {
								setRoom(x-1, y, "North");
								//rooms[x][y].addDoor("South");
							}
							break;
						case "West":
							if (rooms[x-1][y] != null) {
								if(connectRoom(x-1, y, "East")) rooms[x][y].addDoor("West");
							} else {
								setRoom(x-1, y, "East");
								rooms[x][y].addDoor("West");
							}
							break;
					}
				}
			}
		}
	}

	private void setRoom(int x, int y, String entrance){
		Random rn = new Random();
		boolean roomFound = false;
		Room newRoom = new Room(this);
		while (!roomFound) {
			String newRoomID = "Room_" + String.valueOf(rn.nextInt(10) + 1);
			newRoom.create(newRoomID);
			for (String potentialDoor : newRoom.getPotentialDoors().keySet()) {
				if(potentialDoor.equals(entrance)) roomFound = true;
			}
		}
		rooms[x][y] = newRoom;
		newRoom.addDoor(entrance);
		numberOfRooms++;

		setLayout(x ,y);
	}

	private boolean connectRoom(int x, int y, String entrance) {
		for (String potentialDoor : rooms[x][y].getPotentialDoors().keySet()) {
			if (potentialDoor.equals(entrance)) {
				//rooms[x][y].addDoor(entrance);
				return true;
			}
		}
		return false;
	}
}
