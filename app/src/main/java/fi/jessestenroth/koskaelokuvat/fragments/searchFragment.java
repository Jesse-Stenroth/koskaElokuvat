package fi.jessestenroth.koskaelokuvat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.data.finnkinoapi.FinnkinoAPIGetterNavigation;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.data.area;

/**
 * This class hold view of search view
 * @author Jesse Stenroth
 */
public class searchFragment extends Fragment {
    private Spinner aikaa;
    private Spinner paikkaa;
    private FinnkinoAPIGetterNavigation xml;
    private sendData callback;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.search_fragment, container,false);
        aikaa = (Spinner) view.findViewById(R.id.aikaSpinner);
        paikkaa = (Spinner) view.findViewById(R.id.paikkaSpinner);
        //get data from finnkino API
        xml = new FinnkinoAPIGetterNavigation(aikaa, paikkaa, getActivity(), callback);
        area a = xml.getAreas();
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callback = (sendData) context;
    }

    /**
     * This interface send data to activity or fragment
     * @author Jesse Stenroth
     */
    public interface sendData{
        /**
         * This method send nedded data
         * @param areaId area id of theatre
         * @param time showtime location
         */
        public void sendDataToList(String areaId, String time);

        /**
         * This method clear all list
         */
        public void clearList();
    }
}
