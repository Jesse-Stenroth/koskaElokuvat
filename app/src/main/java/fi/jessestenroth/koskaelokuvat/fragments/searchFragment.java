package fi.jessestenroth.koskaelokuvat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.R;

public class searchFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.search_fragment, container,false);
        return view;
    }
}
