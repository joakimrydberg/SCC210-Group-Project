package game;

import controllers.MapMenu;

import java.util.Random;

/**
 * @author Joakim Rydberg.
 */
public class Level {
    private MapMenu map;
    private String levelName = "";
    private int difficulty;
    private int numberOfRooms = 0;
    private int maxRooms = 15;
    private Room[][] rooms = new Room[10][10];
    private Room startRoom = new Room(this);
    private Room endRoom = null;
    private Room currentRoom;

    public Level(String name, int diff, MapMenu map) {
        this.map = map;
        this.levelName = name;
        this.difficulty = diff;

//		int failedLevels = 0;
//		while (endRoom == null) {
//			generateLevel();
//
//			if (endRoom == null) System.out.println("Failed levels: " + ++failedLevels);
//		}
//
//		currentRoom = startRoom;
//		currentRoom.load();

        Room testRoom = new Room(this);
        testRoom.create("test_level");
        testRoom.load();

        testRoom.addDoor(testRoom.getPotentialDoors().keySet().iterator().next());
    }

    private void generateLevel() {
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                rooms[i][j] = null;
            }
        }

		Room testRoom = new Room(this);
		testRoom.create("test_new_level");
		testRoom.load();

        startRoom.addDoor("South");
        setRoom(4, 5, "North", 0);
    }

    private void setLayout(int x, int y, int stepsFromStart) {
        if (numberOfRooms < maxRooms) {
            Random rn = new Random();
            for (String potentialDoor : rooms[x][y].getPotentialDoors().keySet()) {
                if (rn.nextInt(4) == 0) {
                    switch (potentialDoor) {
                        case "North":
                            if (x > 0) {
                                if (rooms[x - 1][y] != null) {
                                    if (connectRoom(x - 1, y, "South")) rooms[x][y].addDoor("North");
                                } else {
                                    if (stepsFromStart > 3 && endRoom == null) {
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
                                    if (stepsFromStart > 3 && endRoom == null) {
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
                                    if (stepsFromStart > 3 && endRoom == null) {
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
                                    if (stepsFromStart > 3 && endRoom == null) {
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
            String newRoomID = "Room_" + String.valueOf(rn.nextInt(10) + 1);
            newRoom.create(newRoomID);
            for (String potentialDoor : newRoom.getPotentialDoors().keySet()) {
                if(potentialDoor.equals(entrance)) roomFound = true;
            }
        }
        rooms[x][y] = newRoom;
        newRoom.addDoor(entrance);
        stepsFromStart++;
        numberOfRooms++;

        setLayout(x ,y, stepsFromStart);
    }

    private void setEndRoom(int x, int y, String entrance) {
        Random rn = new Random();
        boolean roomFound = false;
        Room newRoom = new Room(this);
        while (!roomFound) {
            String newRoomID = "End_Room_" + String.valueOf(rn.nextInt(10) + 1);
            newRoom.create(newRoomID);
            for (String potentialDoor : newRoom.getPotentialDoors().keySet()) {
                if(potentialDoor.equals(entrance)) roomFound = true;
            }
        }
        endRoom = newRoom;
        rooms[x][y] = endRoom;
        endRoom.addDoor(entrance);
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
//	private void setLayout(int x, int y, int stepsFromStart) {
//		if (numberOfRooms < maxRooms) {
//			Random rn = new Random();
//			for (String potentialDoor : rooms[x][y].getPotentialDoors().keySet()) {
//				if (rn.nextInt(4) == 0) {
//					switch (potentialDoor) {
//						case "North":
//							if (x > 0) {
//								if (rooms[x - 1][y] != null) {
//									if (connectRoom(x - 1, y, "South")) rooms[x][y].addDoor("North");
//								} else {
//									if (stepsFromStart > 3 && endRoom == null) {
//										setEndRoom(x - 1, y, "South");
//									} else {
//										setRoom(x - 1, y, "South", stepsFromStart);
//									}
//									rooms[x][y].addDoor("North");
//								}
//							}
//							break;
//						case "East":
//							if (y < 11) {
//								if (rooms[x][y + 1] != null) {
//									if (connectRoom(x, y + 1, "West")) rooms[x][y].addDoor("East");
//								} else {
//									if (stepsFromStart > 3 && endRoom == null) {
//										setEndRoom(x, y + 1, "West");
//									} else {
//										setRoom(x, y + 1, "West", stepsFromStart);
//									}
//									rooms[x][y].addDoor("East");
//								}
//							}
//							break;
//						case "South":
//							if (x < 11) {
//								if (rooms[x + 1][y] != null) {
//									if (connectRoom(x + 1, y, "North")) rooms[x][y].addDoor("South");
//								} else {
//									if (stepsFromStart > 3 && endRoom == null) {
//										setEndRoom(x + 1, y, "North");
//									} else {
//										setRoom(x + 1, y, "North", stepsFromStart);
//									}
//									rooms[x][y].addDoor("South");
//								}
//							}
//							break;
//						case "West":
//							if (y > 0) {
//								if (rooms[x][y - 1] != null) {
//									if (connectRoom(x, y - 1, "East")) rooms[x][y].addDoor("West");
//								} else {
//									if (stepsFromStart > 3 && endRoom == null) {
//										setEndRoom(x, y - 1, "East");
//									} else {
//										setRoom(x, y - 1, "East", stepsFromStart);
//									}
//									rooms[x][y].addDoor("West");
//								}
//							}
//							break;
//						default:
//							throw new RuntimeException("Invalid door direction: " + potentialDoor);
//					}
//				}
//			}
//		}
//	}
//
	private void setRoom(int x, int y, String entrance, int stepsFromStart){
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
		stepsFromStart++;
		numberOfRooms++;

    public void moveRooms(Room room, String direction) {
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
                }

            }
        }
    }

    public void endRoomExited(Room room) {
        if (room.getEndRoom()) {
            map.levelComplete();
        }
    }

    public int getDifficulty() {
        return difficulty;
    }
}
