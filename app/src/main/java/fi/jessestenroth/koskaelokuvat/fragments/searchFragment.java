package fi.jessestenroth.koskaelokuvat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.FinnkinoAPIGetter;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.area;

public class searchFragment extends Fragment {
    private Spinner aikaa;
    private ArrayList<String> areaNames;
    private ArrayList<String> areaId;
    private FinnkinoAPIGetter xml = new FinnkinoAPIGetter();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.search_fragment, container,false);
        aikaa = (Spinner) view.findViewById(R.id.aikaSpinner);
        Spinner paikkaa = (Spinner) view.findViewById(R.id.paikkaSpinner);
        area a = xml.getAreas();
        areaNames = a.getName();
        areaId = a.getId();
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areaNames);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paikkaa.setAdapter(arrayAdapter2);

        aikaa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String code = areaId.get(position);
                ArrayList<String> ajat = xml.getTimesInList(code);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ajat);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                aikaa.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }
}
