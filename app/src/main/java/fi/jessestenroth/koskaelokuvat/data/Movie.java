package fi.jessestenroth.koskaelokuvat.data;


/**
 * This class hold information of movie
 * @author Jesse Stenroth
 */
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

    /**
     * This constructor set data to movie class
     * @param ID movie id
     * @param length length of movie in minutes
     * @param year movie release year
     * @param title movie title
     * @param ratingImageURL movie rating image url
     * @param genres movie genres
     * @param synopsis movie synopsis
     * @param mediumImagePortrait image portrait url
     * @param largeImageLandscape image large landscape url
     */
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

    /**
     * get movie id
     * @return id of movie
     */
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

    /**
     * This method return movie release year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * This method return movie title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method return rating image url
     * @return rating image url
     */
    public String getRatingImageURL() {
        return ratingImageURL;
    }

    /**
     * This method return movie genres
     * @return genres
     */
    public String getGenres() {
        return genres;
    }

    /**
     * This method return movie synopsis
     * @return synopsis
     */
    public String getSynopsis() {
        return Synopsis;
    }

    /**
     * This method return movie portrait image url
     * @return portrait image url
     */
    public String getMediumImagePortrait() {
        return mediumImagePortrait;
    }

    /**
     * This method return movie landscape image url
     * @return landscape image url
     */
    public String getLargeImageLandscape() {
        return LargeImageLandscape;
    }
}
