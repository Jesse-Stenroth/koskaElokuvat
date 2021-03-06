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

/**
 * This class getting data to Settings activity
 * @author Jesse Stenroth
 */
public class FinnkinoAPIGetterSettings {
    private Spinner paikka;
    private String codeHelp = "1029";
    private Context context;
    private area data;
    private getDataToSettings callback;
    private SavingFeature save;
    private boolean firstRun = true;

    /**
     * This constructor save basic data to class memory
     * @param location spinner what hold theatre areas
     * @param con context of activity
     * @param call callback to move data
     */
    public FinnkinoAPIGetterSettings(Spinner location, Context con, getDataToSettings call){
        paikka = location;
        context = con;
        callback = call;
        save = new SavingFeature(con);
    }

    /**
     * This method return area list
     * @return list of areas
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
     * This class read data from finnkino API (areas of theatres)
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
            //after downloading update spinner of areas
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
                    if(!save.getBoolean("gps")) {
                        String code = data.getId().get(position);
                        codeHelp = code;
                        callback.setArea(data.getName().get(position), code);
                    }
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
         * @return list of ids
         */
        public ArrayList<String> getIds()
        {
            return ids;
        }

        /**
         * This method return area names
         * @return list of names
         */
        public ArrayList<String> getNames()
        {
            return names;
        }
    }

    /**
     * This interface make possible sent data to settings
     * @author Jesse Stenroth
     */
    public interface getDataToSettings{
        /**
         * This method set information of area name ond code
         * @param name area name
         * @param code area code (id)
         */
        public void setArea(String name, String code);
    }
}
