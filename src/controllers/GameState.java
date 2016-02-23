package controllers;

import abstract_classes.ClassDescription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author josh
 * @date 23/02/16.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 99L;  //actually needed
    private static HashMap<ClassDescription, ArrayList<Object>> state = new HashMap<>();

    private static void checkForClass(ClassDescription classDescription) {
        if (!state.containsKey(classDescription))
            state.put(classDescription, new ArrayList<>());
    }

    public static ArrayList<Object> getObjects(ClassDescription classDescription) {
        return state.get(classDescription);
    }

    public static void addObjects(ClassDescription classDescription, ArrayList<Object> objects) {
        checkForClass(classDescription);

        getObjects(classDescription).addAll(objects);
    }

    public static void addObjects(ClassDescription classDescription, Object[] objects) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collections.addAll(arrayList, objects);
        addObjects(classDescription, arrayList);
    }

    public static void addObject(ClassDescription classDescription, Object object) {
        checkForClass(classDescription);

        getObjects(classDescription).add(object);
    }
}
