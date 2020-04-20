package fi.jessestenroth.koskaelokuvat.data;

/**
 * This class is object what hold data of one showtime
 * @author Jesse Stenroth
 */
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

    /**
     * This constructor set all data to object
     * @param ID showtime id
     * @param ratingImage rating image url
     * @param start start time
     * @param end end time
     * @param eventID showtime event id
     * @param title title of movie
     * @param theathreID theatre where showtime is
     * @param theatreAndAuditorium auditorium where show movie
     * @param eventSmallImagePortrait small image of movie
     * @param ticketBuy ticket buying url
     */
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

    /**
     * This method set start and end times to showtime
     * @param start starting time
     * @param end ending time
     */
    private void setStartAndEnd(String start, String end) {
        String[] starts = start.split("T");
        String[] ends = end.split("T");
        this.end = ends[1];
        this.start = starts[1];
    }

    /**
     * This method return ticket buying url
     * @return url of ticket
     */
    public String getTicket() {
        return Ticket;
    }

    /**
     * This method return showtime id
     * @return showtime id
     */
    public int getID() {
        return ID;
    }

    /**
     * This method return image url
     * @return rating image url
     */
    public String getRatingImage() {
        return ratingImage;
    }

    /**
     * This method return start time
     * @return movie start
     */
    public String getStart() {
        return start;
    }

    /**
     * This method return end time
     * @return movie end
     */
    public String getEnd() {
        return end;
    }

    /**
     * This method return event id
     * @return event id
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * This method return title of movie
     * @return movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method return theaatre id
     * @return id where showtime is
     */
    public String getTheathreID() {
        return theathreID;
    }

    /**
     * This method return details where movie show
     * @return auditorium where movie is
     */
    public String getTheatreAndAuditorium() {
        return TheatreAndAuditorium;
    }

    /**
     * Small image of movie
     * @return image url of movie
     */
    public String getEventSmallImagePortrait() {
        return EventSmallImagePortrait;
    }
}
