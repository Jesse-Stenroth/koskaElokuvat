package fi.jessestenroth.koskaelokuvat.data.finnkinoapi;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fi.jessestenroth.koskaelokuvat.data.functions.SavingFeature;
import fi.jessestenroth.koskaelokuvat.data.area;
import fi.jessestenroth.koskaelokuvat.fragments.searchFragment;


/**
 * This class get data to searchFragment
 * @author Jesse Stenroth
 */
public class FinnkinoAPIGetterNavigation {
    private Spinner aika;
    private Spinner paikka;
    private String codeHelp = "1029";
    private Context context;
    private area data;
    private ArrayList<String> times = new ArrayList<>();
    private searchFragment.sendData callback;
    private SavingFeature save;

    /**
     * This constructor set needed data to class memory
     * @param time element what contains showtimes times of area
     * @param location element what contains showtime locations
     * @param con context of activity
     * @param call callback what use to send data
     */
    public FinnkinoAPIGetterNavigation(Spinner time, Spinner location, Context con, searchFragment.sendData call){
        aika = time;
        paikka = location;
        context = con;
        callback = call;
        save = new SavingFeature(con);
    }

    /**
     * This method return area informations
     * @return areas
     */
    public area getAreas(){
        readAreaFeed xml = new readAreaFeed();
        xml.execute();
        ArrayList<String> id = xml.getIds();
        ArrayList<String> name = xml.getNames();
        area out = new area(id, name);
        data = out;
        return out;
    }

    /**
     * This method update list of times
     * @param code id code of area
     * @return showtime times
     */
    public ArrayList<String> getTimesInList(String code){
        readScheduleFeed xml = new readScheduleFeed(code);
        xml.execute();
        return xml.getTimes();
    }

    /**
     * This class read data from finnkino API of areas
     * @author Jesse Stenroth
     */
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
        protected void onPostExecute(Object obj){
            //after getting data update elements
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data.getName());
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paikka.setAdapter(arrayAdapter2);
            //if settings contains default option then get this
            if(save.getBoolean("asetettu")){
                String kaytossa = save.getAreaName();
                int ind = 0;
                ArrayList<String> ap = data.getName();
                for(int lap=0; lap < ap.size(); lap++){
                    String vali = ap.get(lap);
                    if(vali.equals(kaytossa)){
                        ind = lap;
                        break;
                    }
                }
                paikka.setSelection(ind);
            }
            paikka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String code = data.getId().get(position);
                    codeHelp = code;
                    System.out.println("Code: " + code);
                    //update times
                    times = getTimesInList(codeHelp);
                    callback.clearList();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }

            });

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
         * This method return area ids
         * @return id list
         */
        public ArrayList<String> getIds()
        {
            return ids;
        }

        /**
         * This method return area names
         * @return name list
         */
        public ArrayList<String> getNames()
        {
            return names;
        }
    }

    /**
     * This class getting data of times from finnkino API
     * @author Jesse Stenroth
     */
    private class readScheduleFeed extends AsyncTask {
        URL url;
        ArrayList<String> times = new ArrayList();
        private String code;

        /**
         * This constructor save basic data class memory
         * @param c code of area
         */
        public readScheduleFeed(String c){
            code = c;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                url = new URL("https://www.finnkino.fi/xml/ScheduleDates/?area=" + code);
                System.out.println("https://www.finnkino.fi/xml/ScheduleDates/?area=" + code);

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
        protected void onPostExecute(Object obj){
            //after times download update elements
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, times);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            aika.setAdapter(arrayAdapter);
            aika.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println("aika: " + parent.getItemAtPosition(position).toString());
                    callback.sendDataToList(codeHelp, parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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
         * This method return times
         * @return times of showtimes
         */
        public ArrayList<String> getTimes() {
            return times;
        }
    }
}
