package fi.jessestenroth.koskaelokuvat.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import fi.jessestenroth.koskaelokuvat.data.finnkinoapi.FinnkinoAPIGetterInfo;
import fi.jessestenroth.koskaelokuvat.R;

public class infoFragment extends Fragment {
    private View view;
    private TextView time;
    private TextView location;
    private TextView synopsis;
    private TextView genre;
    private ImageView rating;
    private TextView vuosi;
    private TextView kesto;
    private TextView title;
    private ImageView imagee;
    private Button osta;
    private String ticket = "";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        view = inflater.inflate(R.layout.info_fragment, container,false);
        imagee = (ImageView) view.findViewById(R.id.movieImage);
        title = (TextView) view.findViewById(R.id.movieTitle);
        kesto = (TextView) view.findViewById(R.id.kesto);
        vuosi = (TextView) view.findViewById(R.id.year);
        rating = (ImageView) view.findViewById(R.id.rating);
        genre = (TextView) view.findViewById(R.id.genre);
        synopsis = (TextView) view.findViewById(R.id.synopsis);
        time = (TextView) view.findViewById(R.id.movieTime);
        location = (TextView) view.findViewById(R.id.movieLocation);
        osta = (Button) view.findViewById(R.id.buyButton);
        osta.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ticket));
            startActivity(browserIntent);
        });
        view.setVisibility(View.GONE);
        return view;
    }

    public void updateInfo(int event){
        view.setVisibility(View.VISIBLE);
        //update elements
        FinnkinoAPIGetterInfo getterInfo = new FinnkinoAPIGetterInfo(getActivity(), event, imagee, title, kesto, vuosi, rating, genre, synopsis, time, location);
    }
    public void changeTime(String text){
        this.time.setText(text);
    }
    public void changeLocation(String text){
        this.location.setText(text);
    }
    public void changeTicket(String Ticket){
        this.ticket = Ticket;
    }
}
