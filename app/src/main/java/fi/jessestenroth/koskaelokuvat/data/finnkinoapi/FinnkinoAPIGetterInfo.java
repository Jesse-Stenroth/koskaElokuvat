package fi.jessestenroth.koskaelokuvat.data.finnkinoapi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import fi.jessestenroth.koskaelokuvat.data.functions.ImageLoadTask;
import fi.jessestenroth.koskaelokuvat.data.Movie;
import fi.jessestenroth.koskaelokuvat.data.ShowTime;

/**
 * This class get data of movie details
 * @author Jesse Stenroth
 */
public class FinnkinoAPIGetterInfo {
    private Context context;
    private readShowAndMovie xml;
    private boolean suomeksi = true;

    /**
     * This constructor get data and elements what update
     * @param context context of activity
     * @param event event id code
     * @param imagee2 imageview of portrait movie image
     * @param title2 title of movie
     * @param kesto2 movie length
     * @param vuosi2 movie year
     * @param rating2 imageview of rating image
     * @param genre2 movie genres
     * @param synopsis2 movie synopsis
     * @param timeView2 movie showtime time
     * @param location2 movie showtime location
     */
    public FinnkinoAPIGetterInfo(Context context, int event, ImageView imagee2, TextView title2, TextView kesto2, TextView vuosi2, ImageView rating2, TextView genre2, TextView synopsis2, TextView timeView2, TextView location2) {
        this.context = context;
        String CurrentLang = Locale.getDefault().getLanguage();
        suomeksi = CurrentLang.equals("fi");
        xml = new readShowAndMovie(event, imagee2, title2, kesto2, vuosi2, rating2, genre2, synopsis2, timeView2, location2);
        xml.execute();
    }

    /**
     * This method return showtime
     * @return showtime
     */
    public ShowTime getShow(){
        return xml.getShowTime();
    }

    /**
     * This method return movie
     * @return movie
     */
    public Movie getMovie(){
        return xml.getMovieInfo();
    }

    /**
     * This class get data using AsyncTask function
     * @author Jesse Stenroth
     */
    private class readShowAndMovie extends AsyncTask {
        URL url;
        URL url2;
        private ShowTime time;
        private Movie mov;
        private int ev;
        private ImageView imagee;
        private TextView title;
        private TextView kesto;
        private TextView vuosi;
        private ImageView rating;
        private TextView genre;
        private TextView synopsis;
        private TextView timeView;
        private TextView location;

        /**
         * This constructor get data what search and where data put
         * @param event showtime event id
         * @param imagee imageview of portrait movie image
         * @param title title of movie
         * @param kesto length of movie
         * @param vuosi release year of movie
         * @param rating rating image
         * @param genre genres of movie
         * @param synopsis synopsis of movie
         * @param timeView showtime time
         * @param location showtime location
         */
        public readShowAndMovie(int event, ImageView imagee, TextView title, TextView kesto, TextView vuosi, ImageView rating, TextView genre, TextView synopsis, TextView timeView, TextView location) {
            this.ev = event;
            this.imagee = imagee;
            this.title = title;
            this.kesto = kesto;
            this.vuosi = vuosi;
            this.rating = rating;
            this.genre = genre;
            this.synopsis = synopsis;
            this.timeView = timeView;
            this.location = location;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                url = new URL("https://www.finnkino.fi/xml/Events/?eventID=" + ev);
                //get movie data
                XmlPullParserFactory factory2 = XmlPullParserFactory.newInstance();
                factory2.setNamespaceAware(false);
                XmlPullParser xpp2 = factory2.newPullParser();
                xpp2.setInput(getInputStream(url), "UTF_8");
                boolean insideItem2 = false;
                String idMovie = "";
                String length = "";
                String year = "";
                String titlee = "";
                String rat = "";
                String genr = "";
                String syn = "";
                String mediumImage = "";
                String largeImage = "";
                boolean titleMissing = true;
                int eventType2 = xpp2.getEventType();
                while (eventType2 != XmlPullParser.END_DOCUMENT) {
                    if (eventType2 == XmlPullParser.START_TAG) {

                        if (xpp2.getName().equalsIgnoreCase("Event")) {
                            insideItem2 = true;
                        }else if (xpp2.getName().equalsIgnoreCase("ID")) {
                            if (insideItem2)
                                idMovie = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("LengthInMinutes")) {
                            if (insideItem2)
                                length = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("ProductionYear")) {
                            if (insideItem2)
                                year = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("Title") && suomeksi && titleMissing) {
                            if (insideItem2) {
                                titlee = xpp2.nextText();
                                titleMissing = false;
                            }
                        } else if (xpp2.getName().equalsIgnoreCase("OriginalTitle") && !suomeksi && titleMissing) {
                            if (insideItem2) {
                                titlee = xpp2.nextText();
                                titleMissing = false;
                            }
                        } else if (xpp2.getName().equalsIgnoreCase("RatingImageUrl")) {
                            if (insideItem2)
                                rat = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("Genres")) {
                            if (insideItem2)
                                genr = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("Synopsis")) {
                            if (insideItem2)
                                syn = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("EventMediumImagePortrait")) {
                            if (insideItem2)
                                mediumImage = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("EventLargeImageLandscape")) {
                            if (insideItem2)
                                largeImage = xpp2.nextText();
                        }
                    } else if (eventType2 == XmlPullParser.END_TAG && xpp2.getName().equalsIgnoreCase("Event")) {
                        insideItem2 = false;
                    }
                    eventType2 = xpp2.next(); //move to next element
                }

                Movie movie = new Movie(Integer.parseInt(idMovie), Integer.parseInt(length), Integer.parseInt(year), titlee, rat, genr, syn, mediumImage, largeImage);
                this.mov = movie;
                return movie;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Object obj){
            //after get data put data to view elements
            Movie movie = this.mov;
            ShowTime show = this.time;
            title.setText(movie.getTitle());
            kesto.setText(movie.getLength() + " min");
            vuosi.setText(movie.getYear() + " ");
            genre.setText(movie.getGenres());
            synopsis.setText(movie.getSynopsis());
            try {
                //load images from internet and put them to view
                new ImageLoadTask(movie.getMediumImagePortrait(), imagee).execute();
                new ImageLoadTask(movie.getRatingImageURL(), rating).execute();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * open stream and connection
         * @param url url where connect
         * @return inputStream
         */
        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * get showtime
         * @return showtime with information
         */
        public ShowTime getShowTime(){
            return this.time;
        }

        /**
         * get movie
         * @return movie with information
         */
        public Movie getMovieInfo(){
            return this.mov;
        }
    }
}
