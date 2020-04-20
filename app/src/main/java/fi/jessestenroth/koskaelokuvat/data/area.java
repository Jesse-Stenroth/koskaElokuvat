package fi.jessestenroth.koskaelokuvat.data;

import java.util.ArrayList;

/**
 * This class hold information of areas
 * @author Jesse Stenroth
 */
public class area {
    private ArrayList<String> id;
    private ArrayList<String> name;

    /**
     * This constructor set data of areas
     * @param id area ids
     * @param name area names
     */
    public area(ArrayList<String> id, ArrayList<String> name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This method return list of area ids
     * @return area ids
     */
    public ArrayList<String> getId() {
        return id;
    }

    /**
     * This method return list of area names
     * @return area names
     */
    public ArrayList<String> getName() {
        return name;
    }
}
