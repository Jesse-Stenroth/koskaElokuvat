package fi.jessestenroth.koskaelokuvat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.Movie;
import fi.jessestenroth.koskaelokuvat.R;
import fi.jessestenroth.koskaelokuvat.ShowTime;

public class infoFragment extends Fragment {
    private List<String> l = new ArrayList<>();
    private ShowTime show;
    private Movie movie;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.info_fragment, container,false);
        movie = new Movie(12322,120,1400,"Maailmalla","","komedia","Mnnfi3firiniIWEFI3QWNFWIJNFGWIEJfnjwfijenwfijwnefiwkljnfiwejgknweoifjnegienwgje","","");
        l.add("Suomi");
        l.add("Ruotsi");
        show = new ShowTime(123,"","14:00","17:00",1234,"Maailmalla","12334","Sali 3, Tampere","Suomi", l, "");
        ImageView imagee = (ImageView) view.findViewById(R.id.movieImage);
        TextView title = (TextView) view.findViewById(R.id.movieTitle);
        TextView kesto = (TextView) view.findViewById(R.id.kesto);
        TextView vuosi = (TextView) view.findViewById(R.id.year);
        ImageView rating = (ImageView) view.findViewById(R.id.rating);
        TextView genre = (TextView) view.findViewById(R.id.genre);
        TextView synopsis = (TextView) view.findViewById(R.id.synopsis);
        TextView time = (TextView) view.findViewById(R.id.movieTime);
        TextView location = (TextView) view.findViewById(R.id.movieLocation);
        title.setText(movie.getTitle());
        kesto.setText(movie.getLength() + " min");
        vuosi.setText(movie.getYear() + "");
        genre.setText(movie.getGenres());
        synopsis.setText(movie.getSynopsis());
        time.setText(show.getStart());
        location.setText(show.getTheatreAndAuditorium());
        return view;
    }
}
