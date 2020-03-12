package fi.jessestenroth.koskaelokuvat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.R;

public class searchFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.search_fragment, container,false);
        Spinner aikaa = (Spinner) view.findViewById(R.id.aikaSpinner);
        Spinner paikkaa = (Spinner) view.findViewById(R.id.paikkaSpinner);
        ArrayList<String> ajat = new ArrayList<>();
        ArrayList<String> paikat = new ArrayList<>();
        ajat.add("122.224.44141");
        ajat.add("2.5.1234");
        ajat.add("56.23.1345");
        paikat.add("Tampere");
        paikat.add("Helsinki");
        paikat.add("Espoo");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ajat);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aikaa.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paikat);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paikkaa.setAdapter(arrayAdapter2);
        return view;
    }
}
