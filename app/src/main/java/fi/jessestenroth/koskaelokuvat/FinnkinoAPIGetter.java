package fi.jessestenroth.koskaelokuvat;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FinnkinoAPIGetter {

    public area getAreas(){
        readAreaFeed xml = new readAreaFeed();
        xml.execute();
        ArrayList<String> id = xml.getIds();
        ArrayList<String> name = xml.getNames();
        area out = new area(id, name);
        return out;
    }

    public ArrayList<String> getTimesInList(String code){
        readScheduleFeed xml = new readScheduleFeed(code);
        xml.execute();
        return xml.getTimes();
    }
    private class readAreaFeed extends AsyncTask {
        URL url;
        ArrayList<String> ids = new ArrayList();
        ArrayList<String> names = new ArrayList();
        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                url = new URL("https://www.finnkino.fi/xml/TheatreAreas/");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("TheatreArea")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("ID")) {
                            if (insideItem)
                                ids.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("Name")) {
                            if (insideItem)
                                names.add(xpp.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("TheatreArea")) {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ids;
        }


        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        public ArrayList<String> getIds()
        {
            return ids;
        }
        public ArrayList<String> getNames()
        {
            return names;
        }
    }

    private class readScheduleFeed extends AsyncTask {
        URL url;
        ArrayList<String> times = new ArrayList();
        private String code;
        public readScheduleFeed(String c){
            code = c;
        }
        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                url = new URL("https://www.finnkino.fi/xml/ScheduleDates/?area=" + code);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("dateTime")) {
                                String text = xpp.nextText();
                                String[] splits = text.split("T");
                                String day = "" + splits[0].charAt(8) + splits[0].charAt(9);
                                String month = "" + splits[0].charAt(5) + splits[0].charAt(6);
                                String year = "" + splits[0].charAt(0) + splits[0].charAt(1) + splits[0].charAt(2) + splits[0].charAt(3);
                                times.add(day + "." + month + "." + year);
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                    }

                    eventType = xpp.next(); //move to next element
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return times;
        }


        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        public ArrayList<String> getTimes() {
            return times;
        }
    }
}
