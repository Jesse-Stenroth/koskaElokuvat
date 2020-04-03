package fi.jessestenroth.koskaelokuvat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.FinnkinoAPIGetterList;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.ShowTime;
import fi.jessestenroth.koskaelokuvat.ShowTimeAdapter;

public class ListFragment extends Fragment {
    private ArrayList<ShowTime> l = new ArrayList<>();
    private ListView lista;
    private FinnkinoAPIGetterList xml;
    private sendToInfo callback;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.list_fragment, container,false);
        lista = (ListView) view.findViewById(R.id.fullList);
        xml = new FinnkinoAPIGetterList(getActivity(), l, lista, this);
        updateList();
        return view;
    }
    public void clearList(){
        l.clear();
       // updateList();
        lista.setAdapter(null);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callback = (sendToInfo) context;
    }
    public void updateData(String day, String areaId){
        l = xml.getData(day, areaId);
    }
    public void updateList(){
        lista.setAdapter(new ShowTimeAdapter(getActivity(), l));
        lista.setOnItemClickListener((parent, view, position, id) -> {
            ShowTime help = l.get((int) id);
            int event = help.getEventID();
            callback.sendEvent(event, help.getStart() + " - " + help.getEnd(), help.getTheatreAndAuditorium(), help.getTicket());
        });
    }
    public interface sendToInfo{
        public void sendEvent(int event, String time, String location, String ticket);
    }
}
