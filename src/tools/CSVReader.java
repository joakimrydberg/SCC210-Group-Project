package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author josh
 * @date 16/02/16.
 */
public class CSVReader {

    public static ArrayList<String[]> read(String filePath) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String[] levelIDMap;

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                if (line.length() != 0
                        && (line.charAt(0) != '/' && line.charAt(1) != '/')) { //allow for any 'comments'

                    levelIDMap = line.split(",");

                    for (int i = 0; i < levelIDMap.length; i++) {
                        if (levelIDMap[i].charAt(0) == ' ') {
                            levelIDMap[i] = levelIDMap[i].substring(1);
                        }
                    }

                    arrayList.add(levelIDMap);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return arrayList;
    }

}
