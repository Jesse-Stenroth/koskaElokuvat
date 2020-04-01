package fi.jessestenroth.koskaelokuvat;

import java.util.List;

public class ShowTime {
    private int ID;
    private String ratingImage;
    private String start;
    private String end;
    private int eventID;
    private String title;
    private String theathreID;
    private String TheatreAndAuditorium;
    private String EventSmallImagePortrait;

    public ShowTime(int ID, String ratingImage, String start, String end, int eventID, String title, String theathreID, String theatreAndAuditorium, String eventSmallImagePortrait) {
        this.ID = ID;
        this.ratingImage = ratingImage;
        setStartAndEnd(start, end);
        this.eventID = eventID;
        this.title = title;
        this.theathreID = theathreID;
        TheatreAndAuditorium = theatreAndAuditorium;
        EventSmallImagePortrait = eventSmallImagePortrait;
    }

    private void setStartAndEnd(String start, String end) {
        String[] starts = start.split("T");
        String[] ends = end.split("T");
        this.end = ends[1];
        this.start = starts[1];
    }

    public int getID() {
        return ID;
    }

    public String getRatingImage() {
        return ratingImage;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getEventID() {
        return eventID;
    }

    public String getTitle() {
        return title;
    }

    public String getTheathreID() {
        return theathreID;
    }

    public String getTheatreAndAuditorium() {
        return TheatreAndAuditorium;
    }

    public String getEventSmallImagePortrait() {
        return EventSmallImagePortrait;
    }
}
