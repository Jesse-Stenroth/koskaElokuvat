package fi.jessestenroth.koskaelokuvat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.FinnkinoAPIGetter;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.area;

public class searchFragment extends Fragment {
    private Spinner aikaa;
    private Spinner paikkaa;
    private FinnkinoAPIGetter xml;
    private sendData callback;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.search_fragment, container,false);
        aikaa = (Spinner) view.findViewById(R.id.aikaSpinner);
        paikkaa = (Spinner) view.findViewById(R.id.paikkaSpinner);
        xml = new FinnkinoAPIGetter(aikaa, paikkaa, getActivity(), callback);
        area a = xml.getAreas();
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callback = (sendData) context;
    }

    public interface sendData{
        public void sendDataToList(String areaId, String time);
        public void clearList();
    }
}
