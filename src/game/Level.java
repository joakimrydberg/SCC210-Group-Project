package game;

import controllers.MapMenu;
import org.jsfml.system.Vector2i;

import java.util.Random;

/**
 * @author Joakim Rydberg.
 */
public class Level {

    private MapMenu map;
    private String levelName = "";
    private int difficulty;
    private int numberOfRooms = 0;
    public final static int MAX_ROOMS = 10;
    private Room[][] rooms = new Room[10][10];
    private Room startRoom = null;
    private Room endRoom = null;
    private Room currentRoom;
	private int availableRooms = 9;
	private int availableEndRooms = 8;

    public Level(String name, int diff, MapMenu map) {
        this.map = map;
        this.levelName = name;
        this.difficulty = diff;

		int failedLevels = 0;
		while (endRoom == null) {
			generateLevel();

			if (endRoom == null) {
				System.out.println("\nFailed levels: " + (++failedLevels));
				startRoom.resetCounter();
			}
		}
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (rooms[i][j] != null) {
					Driver.addDrawer(rooms[i][j]);
				}
            }
        }
		currentRoom = startRoom;
		currentRoom.load();

/*
        Room testRoom = new Room(this);
        testRoom.create("end_room_1");
        testRoom.load();
*/

    //    testRoom.addDoor(testRoom.getPotentialDoors().keySet().iterator().next());
    }

    private void generateLevel() {
//        for (int i = 0; i < 10; i++){
//            for (int j = 0; j < 10; j++){
//                if (rooms[i][j] != null) {
//					Driver.removeDrawer(rooms[i][j]);
//					rooms[i][j] = null;
//				}
//            }
//        }

		Random rn = new Random();
		startRoom = new Room(this);
		numberOfRooms = 1;

		if (rn.nextInt(1) == 1) {
			rooms[3][5] = startRoom;
			startRoom.create("start_room_south");
			startRoom.addDoor("South");
			startRoom.addDoor("North");
			setRoom(4, 5, "North", 0);
		} else {
			rooms[6][5] = startRoom;
			startRoom.create("start_room_north");
			startRoom.addDoor("North");
			startRoom.addDoor("South");
			setRoom(5, 5, "South", 0);
		}
	}

	private void setLayout(int x, int y, int stepsFromStart) {
		Random rn = new Random();
		for (String potentialDoor : rooms[x][y].getPotentialDoors().keySet()) {
			if (numberOfRooms < MAX_ROOMS) {
				if (rn.nextInt(3) == 0) {
					switch (potentialDoor) {
						case "North":
							if (x > 0) {
								if (rooms[x - 1][y] != null) {
									if (connectRoom(x - 1, y, "South")) rooms[x][y].addDoor("North");
								} else {
									if (stepsFromStart > 2 && endRoom == null) {
										setEndRoom(x - 1, y, "South");
									} else {
										setRoom(x - 1, y, "South", stepsFromStart);
									}
									rooms[x][y].addDoor("North");
								}
							}
							break;
						case "East":
							if (y < 11) {
								if (rooms[x][y + 1] != null) {
									if (connectRoom(x, y + 1, "West")) rooms[x][y].addDoor("East");
								} else {
									if (stepsFromStart > 2 && endRoom == null) {
										setEndRoom(x, y + 1, "West");
									} else {
										setRoom(x, y + 1, "West", stepsFromStart);
									}
									rooms[x][y].addDoor("East");
								}
							}
							break;
						case "South":
							if (x < 11) {
								if (rooms[x + 1][y] != null) {
									if (connectRoom(x + 1, y, "North")) rooms[x][y].addDoor("South");
								} else {
									if (stepsFromStart > 2 && endRoom == null) {
										setEndRoom(x + 1, y, "North");
									} else {
										setRoom(x + 1, y, "North", stepsFromStart);
									}
									rooms[x][y].addDoor("South");
								}
							}
							break;
						case "West":
							if (y > 0) {
								if (rooms[x][y - 1] != null) {
									if (connectRoom(x, y - 1, "East")) rooms[x][y].addDoor("West");
								} else {
									if (stepsFromStart > 2 && endRoom == null) {
										setEndRoom(x, y - 1, "East");
									} else {
										setRoom(x, y - 1, "East", stepsFromStart);
									}
									rooms[x][y].addDoor("West");
								}
							}
							break;
						default:
							throw new RuntimeException("Invalid door direction: " + potentialDoor);
					}
				}
			}
		}
	}

	private void setRoom(int x, int y, String entrance, int stepsFromStart){
		Random rn = new Random();
		boolean roomFound = false;
		Room newRoom = new Room(this);
		while (!roomFound) {
			String newRoomID = "room_" + String.valueOf(rn.nextInt(availableRooms) + 1);
			newRoom.create(newRoomID);
			for (String potentialDoor : newRoom.getPotentialDoors().keySet()) {
				if(potentialDoor.equals(entrance)) {
					roomFound = true;
					break;
				}
			}
			//if (!roomFound) Driver.removeDrawer(newRoom);
		}
		rooms[x][y] = newRoom;
		newRoom.addDoor(entrance);
		stepsFromStart++;
		numberOfRooms++;

        setLayout(x, y, stepsFromStart);
    }

	private void setEndRoom(int x, int y, String entrance) {
		Random rn = new Random();
		boolean roomFound = false;
		Room newRoom = new Room(this);
		while (!roomFound) {
			String newRoomID = "end_room_" + String.valueOf(rn.nextInt(availableEndRooms) + 1);
			newRoom.create(newRoomID);
			for (String potentialDoor : newRoom.getPotentialDoors().keySet()) {
				if(potentialDoor.equals(entrance)) {
					roomFound = true;
					break;
				}
			}
			//if (!roomFound) Driver.removeDrawer(newRoom);
		}
		endRoom = newRoom;
		rooms[x][y] = endRoom;
		endRoom.addDoor(entrance);
		numberOfRooms++;
	}

    private boolean connectRoom(int x, int y, String entrance) {
        for (String potentialDoor : rooms[x][y].getPotentialDoors().keySet()) {
            if (potentialDoor.equals(entrance)) {
                rooms[x][y].addDoor(entrance);
                return true;
            }
        }
        return false;
    }

    public void moveRooms(Room room, String direction) {
        System.out.println("Moving " + direction);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (rooms[i][j] != null && rooms[i][j].equals(room)) {
                    int changeI = 0, changeJ = 0;
                    switch (direction) {
                        case "North":
                            changeI = -1;
                            break;
                        case "East":
                            changeJ = 1;
                            break;
                        case "South":
                            changeI = 1;
                            break;
                        case "West":
                            changeJ = -1;
                            break;
                        default:
                            throw new RuntimeException("Invalid rotation" + direction);
                    }
                    room.unload();
                    rooms[i + changeI][j + changeJ].load();
                    break;
                }

            }
        }
    }

    public void endRoomExited(Room room) {
        if (room.getEndRoom()) {
            map.levelComplete();
        }
    }

    public int getDifficulty(String roomID) {
        return difficulty;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Vector2i getCurrentRoomIndex() {
        return findRoom(currentRoom);
    }
    public Room[][] getRooms() {
        return rooms;
    }

    public Vector2i findRoom(Room room) {
        for (int i = 0; i < MAX_ROOMS; i++) {
            for (int j = 0; j < MAX_ROOMS; j++) {
                if (rooms[i][j] != null && rooms[i][j].equals(room)) {
                    return new Vector2i(i, j);
                }
            }
        }
        return null;
    }
}
