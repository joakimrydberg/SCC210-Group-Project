package game;

import java.io.*;
import java.util.ArrayList;

/**
 * This class will provide all functionality that relates to handling the file IO
 *
 * @author Alexander J Mills
 */
public abstract class FileHandling {
	/*
	 * static constants for all the files necessary for the game
	 */
	public static final String LEVEL = "example.txt",
			EXAMPLEFILE2 = "example2.txt";

	/**
	 * This read the file and return an ArrayList of objects
	 *
	 * @param fileName - the name of the file (use one of the constants in the class)
	 * @return The arraylist
	 */
	public static ArrayList<Object> readFile(String fileName) {
		ArrayList<Object> recordsArray = new ArrayList<>();

		try {
			checkFileExists(fileName);    //check the file exists if not create it

			InputStream fileStream = new FileInputStream(fileName);

			if (fileStream.available() > 0) {
				ObjectInputStream objStream = new ObjectInputStream(fileStream);

				int size = objStream.readInt();   //read the number of records in the file

				//read the records into the ArrayList
				for (int i = 0; i < size; i++) {
					recordsArray.add(objStream.readObject());
				}

				objStream.close();
			}
			fileStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return recordsArray;
	}

	/**
	 * Saves the objects in the ArrayList to the file.
	 *
	 * @param objects - objects to save
	 * @param fileName - file path, constants in this class
	 */
	public static void writeToFile(ArrayList<Object> objects, String fileName) {
		try {
			checkFileExists(fileName);

			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			ObjectOutputStream objStream = new ObjectOutputStream(fileOutputStream);

			objStream.writeInt(objects.size());   //write the number of records

			for (Object r : objects) {
				objStream.writeObject(r);     //write the records to the byte array
			}

			objStream.flush();
			fileOutputStream.flush();

			fileOutputStream.close();
			objStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check the file exists and if not create it 
	 *
	 * @param fileName - the file path, constants in this class
	 */
	private static void checkFileExists(String fileName) {
		File file = new File(fileName);

		if (!file.exists())
			try {
				if (file.createNewFile())
					System.out.println(fileName + " not found, so created.");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}

