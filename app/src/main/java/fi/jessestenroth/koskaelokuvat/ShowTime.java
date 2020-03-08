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
    private String SpokenLanguage;
    private List<String> subtitleLanguages;
    private String EventSmallImagePortrait;

    public ShowTime(int ID, String ratingImage, String start, String end, int eventID, String title, String theathreID, String theatreAndAuditorium, String spokenLanguage, List<String> subtitleLanguages, String eventSmallImagePortrait) {
        this.ID = ID;
        this.ratingImage = ratingImage;
        this.start = start;
        this.end = end;
        this.eventID = eventID;
        this.title = title;
        this.theathreID = theathreID;
        TheatreAndAuditorium = theatreAndAuditorium;
        SpokenLanguage = spokenLanguage;
        this.subtitleLanguages = subtitleLanguages;
        EventSmallImagePortrait = eventSmallImagePortrait;
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

    public String getSpokenLanguage() {
        return SpokenLanguage;
    }

    public List<String> getSubtitleLanguages() {
        return subtitleLanguages;
    }

    public String getEventSmallImagePortrait() {
        return EventSmallImagePortrait;
    }
}
