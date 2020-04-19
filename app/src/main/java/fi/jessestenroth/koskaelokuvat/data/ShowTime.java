package fi.jessestenroth.koskaelokuvat.data;

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
    private String Ticket;

    public ShowTime(int ID, String ratingImage, String start, String end, int eventID, String title, String theathreID, String theatreAndAuditorium, String eventSmallImagePortrait, String ticketBuy) {
        this.ID = ID;
        this.ratingImage = ratingImage;
        setStartAndEnd(start, end);
        this.eventID = eventID;
        this.title = title;
        this.theathreID = theathreID;
        TheatreAndAuditorium = theatreAndAuditorium;
        EventSmallImagePortrait = eventSmallImagePortrait;
        this.Ticket = ticketBuy;
    }

    private void setStartAndEnd(String start, String end) {
        String[] starts = start.split("T");
        String[] ends = end.split("T");
        this.end = ends[1];
        this.start = starts[1];
    }

    public String getTicket() {
        return Ticket;
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
