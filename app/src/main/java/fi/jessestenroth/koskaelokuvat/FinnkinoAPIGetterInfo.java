package fi.jessestenroth.koskaelokuvat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class FinnkinoAPIGetterInfo {
    private Context context;
    private readShowAndMovie xml;
    private boolean suomeksi = true;

    public FinnkinoAPIGetterInfo(Context context, int event, ImageView imagee2, TextView title2, TextView kesto2, TextView vuosi2, ImageView rating2, TextView genre2, TextView synopsis2, TextView timeView2, TextView location2) {
        this.context = context;
        xml = new readShowAndMovie(event, imagee2, title2, kesto2, vuosi2, rating2, genre2, synopsis2, timeView2, location2);
        xml.execute();
    }

    public ShowTime getShow(){
        return xml.getShowTime();
    }
    public Movie getMovie(){
        return xml.getMovieInfo();
    }

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
                        } else if (xpp2.getName().equalsIgnoreCase("Title") && suomeksi) {
                            if (insideItem2)
                                titlee = xpp2.nextText();
                        } else if (xpp2.getName().equalsIgnoreCase("OriginalTitle") && !suomeksi) {
                            if (insideItem2)
                                titlee = xpp2.nextText();
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
            Movie movie = this.mov;
            ShowTime show = this.time;
            title.setText(movie.getTitle());
            kesto.setText(movie.getLength() + " min");
            vuosi.setText(movie.getYear() + " ");
            genre.setText(movie.getGenres());
            synopsis.setText(movie.getSynopsis());
            try {
                URL image = new URL(movie.getMediumImagePortrait());
                Bitmap bitmap = BitmapFactory.decodeStream(image.openConnection().getInputStream());
                imagee.setImageBitmap(bitmap);
                URL r = new URL(movie.getRatingImageURL());
                Bitmap bitmap1 = BitmapFactory.decodeStream(r.openConnection().getInputStream());
                rating.setImageBitmap(bitmap1);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }
        public ShowTime getShowTime(){
            return this.time;
        }
        public Movie getMovieInfo(){
            return this.mov;
        }
    }
}
