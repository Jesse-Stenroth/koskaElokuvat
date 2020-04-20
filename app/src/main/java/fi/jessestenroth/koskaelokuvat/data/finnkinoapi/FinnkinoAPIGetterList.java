package fi.jessestenroth.koskaelokuvat.data.finnkinoapi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fi.jessestenroth.koskaelokuvat.data.ShowTime;
import fi.jessestenroth.koskaelokuvat.fragments.ListFragment;

/**
 * This class get data to listview
 * @author Jesse Stenroth
 */
public class FinnkinoAPIGetterList {
    private Context context;
    private ArrayList<ShowTime> list;
    private ListView listView;
    private boolean suomeksi = true;
    private ListFragment Lfragment;

    /**
     * This constructor set basic information memory and use them to update view
     * @param context context of activity
     * @param list list what contains showtimes
     * @param listView view what contains showtimes
     * @param fragment list fragment
     */
    public FinnkinoAPIGetterList(Context context, ArrayList<ShowTime> list, ListView listView, ListFragment fragment) {
        this.context = context;
        String CurrentLang = Locale.getDefault().getLanguage();
        suomeksi = CurrentLang.equals("fi");
        this.list = list;
        this.listView = listView;
        Lfragment = fragment;
    }

    /**
     * This method return list of showtimes
     * @param day showtime time
     * @param area showtime area
     * @return list of showtimes
     */
    public ArrayList<ShowTime> getData(String day, String area) {
        readShowFeed xml = new readShowFeed(day, area);
        xml.execute();
        return xml.getList();
    }

    /**
     * This class read data from internet using AsyncTask
     * @author Jesse Stenroth
     */
    private class readShowFeed extends AsyncTask{
        URL url;
        ArrayList<ShowTime> out = new ArrayList<>();
        private String area;
        private String day;

        /**
         * This constructor set area and time information own memory
         * @param d time
         * @param a area
         */
        public readShowFeed(String d, String a){
            area = a;
            day = d;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                url = new URL("https://www.finnkino.fi/xml/Schedule/?area=" + area + "&dt=" + day);
                System.out.println("https://www.finnkino.fi/xml/Schedule/?area=" + area + "&dt=" + day);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                List<String> ids = new ArrayList<>();
                List<String> rating = new ArrayList<>();
                List<String> start = new ArrayList<>();
                List<String> end = new ArrayList<>();
                List<String> event = new ArrayList<>();
                List<String> title = new ArrayList<>();
                List<String> theatre = new ArrayList<>();
                List<String> auditorium = new ArrayList<>();
                List<String> image = new ArrayList<>();
                List<String> tickets = new ArrayList<>();
                int eventType = xpp.getEventType();
                //read data from schedules
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("Show")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("ID")) {
                            if (insideItem)
                                ids.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("RatingImageUrl")) {
                            if (insideItem)
                                rating.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("dttmShowStart")) {
                            if (insideItem)
                                start.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("dttmShowEnd")) {
                            if (insideItem)
                                end.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("EventID")) {
                            if (insideItem)
                                event.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("Title") && suomeksi) {
                            if (insideItem)
                                title.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("OriginalTitle") && !suomeksi) {
                            if (insideItem)
                                title.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("TheatreID")) {
                            if (insideItem)
                                theatre.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("TheatreAndAuditorium")) {
                            if (insideItem)
                                auditorium.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("EventSmallImagePortrait")) {
                            if (insideItem)
                                image.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("ShowURL")) {
                            if (insideItem)
                                tickets.add(xpp.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("Show")) {
                        insideItem = false;
                    }
                    eventType = xpp.next(); //move to next element
                }
                ArrayList<ShowTime> listHelp = new ArrayList<>();
                for(int lap=0; lap < ids.size(); lap++){
                    listHelp.add(new ShowTime(Integer.parseInt(ids.get(lap)), rating.get(lap), start.get(lap), end.get(lap), Integer.parseInt(event.get(lap)), title.get(lap), theatre.get(lap), auditorium.get(lap), image.get(lap), tickets.get(lap)));
                }
                list = listHelp;
                out = listHelp;
                return listHelp;
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
            //after data getting update view
            Lfragment.changeArrayList(out);
            Lfragment.updateList();
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
         * return list of showtimes
         * @return showtime list
         */
        public ArrayList<ShowTime> getList(){
            return list;
        }
    }
}
