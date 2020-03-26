package fi.jessestenroth.koskaelokuvat;

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
import java.util.LinkedList;
import java.util.List;

import fi.jessestenroth.koskaelokuvat.fragments.ListFragment;

public class FinnkinoAPIGetterList {
    private Context context;
    private ArrayList<ShowTime> list;
    private ListView listView;
    private boolean suomeksi = true;
    private ListFragment Lfragment;

    public FinnkinoAPIGetterList(Context context, ArrayList<ShowTime> list, ListView listView, ListFragment fragment) {
        this.context = context;
        this.list = list;
        this.listView = listView;
        Lfragment = fragment;
    }

    public ArrayList<ShowTime> getData(String day, String area) {
        readShowFeed xml = new readShowFeed(day, area);
        xml.execute();
        return xml.getList();
    }

    private class readShowFeed extends AsyncTask{
        URL url;
        ArrayList<ShowTime> out = new ArrayList<>();
        private String area;
        private String day;
        public readShowFeed(String d, String a){
            area = a;
            day = d;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                url = new URL("https://www.finnkino.fi/xml/Schedule/?area=" + area + "&dt=" + day);

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
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("Show")) {
                        insideItem = false;
                    }
                    eventType = xpp.next(); //move to next element
                }
                ArrayList<ShowTime> listHelp = new ArrayList<>();
                for(int lap=0; lap < ids.size(); lap++){
                    listHelp.add(new ShowTime(Integer.parseInt(ids.get(lap)), rating.get(lap), start.get(lap), end.get(lap), Integer.parseInt(event.get(lap)), title.get(lap), theatre.get(lap), auditorium.get(lap), image.get(lap)));
                }
                list = listHelp;
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
            Lfragment.updateList();
        }
        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }
        public ArrayList<ShowTime> getList(){
            return list;
        }
    }
}
