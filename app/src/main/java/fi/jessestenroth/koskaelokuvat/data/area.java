package fi.jessestenroth.koskaelokuvat.data;

import java.util.ArrayList;

public class area {
    private ArrayList<String> id;
    private ArrayList<String> name;

    public area(ArrayList<String> id, ArrayList<String> name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<String> getId() {
        return id;
    }

    public ArrayList<String> getName() {
        return name;
    }
}
