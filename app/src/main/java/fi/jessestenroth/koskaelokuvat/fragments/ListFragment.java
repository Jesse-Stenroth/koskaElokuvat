package fi.jessestenroth.koskaelokuvat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.ShowTime;
import fi.jessestenroth.koskaelokuvat.ShowTimeAdapter;

public class ListFragment extends Fragment {
    private ArrayList<ShowTime> l = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.list_fragment, container,false);
        ListView lista = (ListView) view.findViewById(R.id.fullList);
        List<String> kielet = new ArrayList<>();
        kielet.add("Suomi");
        kielet.add("Ruotsi");
        l.add(new ShowTime(123, "fsf","14:00","16:00",13212, "Matkalla", "1233", "Sali 3", "Suomi", kielet, "fff"));
        l.add(new ShowTime(122, "ff","15:00","17:00",13212, "Matkalla", "1233", "Sali 3", "Suomi", kielet, "fff"));
        lista.setAdapter(new ShowTimeAdapter(getActivity(), l));
        return view;
    }
}
