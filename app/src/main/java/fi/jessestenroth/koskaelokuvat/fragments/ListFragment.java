package fi.jessestenroth.koskaelokuvat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.data.finnkinoapi.FinnkinoAPIGetterList;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.data.ShowTime;
import fi.jessestenroth.koskaelokuvat.ShowTimeAdapter;

/**
 * This class hold view of showtimes list
 * @author Jesse Stenroth
 */
public class ListFragment extends Fragment {
    //list of showtimes
    private ArrayList<ShowTime> l = new ArrayList<>();
    private ListView lista;
    //data getter class
    private FinnkinoAPIGetterList xml;
    //callback function to update other fragments
    private sendToInfo callback;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.list_fragment, container,false);
        lista = (ListView) view.findViewById(R.id.fullList);
        xml = new FinnkinoAPIGetterList(getActivity(), l, lista, this);
        updateList();
        return view;
    }

    /**
     * This method clear list and clear listview
     */
    public void clearList(){
        l.clear();
        lista.setAdapter(null);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callback = (sendToInfo) context;
    }

    /**
     * This method change list of showtimes
     * @param in new list of showtimes
     */
    public void changeArrayList(ArrayList<ShowTime> in){
        this.l = in;
    }

    /**
     * This method get data list of showtimes
     * @param day day os showtimes
     * @param areaId area where show must show
     */
    public void updateData(String day, String areaId){
        l = xml.getData(day, areaId);
    }

    /**
     * This method update listview
     */
    public void updateList(){
        lista.setAdapter(new ShowTimeAdapter(getActivity(), l));
        lista.setOnItemClickListener((parent, view, position, id) -> {
            ShowTime help = l.get((int) id);
            int event = help.getEventID();
            callback.sendEvent(event, help.getStart() + " - " + help.getEnd(), help.getTheatreAndAuditorium(), help.getTicket());
        });
    }

    /**
     * This interface make possible send data to other activity or fragment
     * @author Jesse Stenroth
     */
    public interface sendToInfo{
        /**
         *This method send data what need to show details of showtime
         * @param event showtime event id
         * @param time showtime time
         * @param location showtime location
         * @param ticket showtime ticket buying url
         */
        public void sendEvent(int event, String time, String location, String ticket);
    }
}
