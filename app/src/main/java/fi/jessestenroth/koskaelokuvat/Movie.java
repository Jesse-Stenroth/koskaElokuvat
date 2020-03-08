package fi.jessestenroth.koskaelokuvat;

public class Movie {
    private int ID;
    private int Length;
    private int year;
    private String title;
    private String ratingImageURL;
    private String genres;
    private String Synopsis;
    private String mediumImagePortrait;
    private String LargeImageLandscape;

    public Movie(int ID, int length, int year, String title, String ratingImageURL, String genres, String synopsis, String mediumImagePortrait, String largeImageLandscape) {
        this.ID = ID;
        Length = length;
        this.year = year;
        this.title = title;
        this.ratingImageURL = ratingImageURL;
        this.genres = genres;
        Synopsis = synopsis;
        this.mediumImagePortrait = mediumImagePortrait;
        LargeImageLandscape = largeImageLandscape;
    }

    public int getID() {
        return ID;
    }

    /**
     * Length of movie
     * @return minutes
     */
    public int getLength() {
        return Length;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getRatingImageURL() {
        return ratingImageURL;
    }

    public String getGenres() {
        return genres;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public String getMediumImagePortrait() {
        return mediumImagePortrait;
    }

    public String getLargeImageLandscape() {
        return LargeImageLandscape;
    }
}
