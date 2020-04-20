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

/**
 * This is fragment what hold view of showtime details
 * @author Jesse Stenroth
 */
public class infoFragment extends Fragment {
    //view of details
    private View view;
    //elements of view what must change when get data
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
    //ticket buying url
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
        //because tablet mode info showing elements without data so set view not visible
        view.setVisibility(View.GONE);
        return view;
    }

    /**
     * This method update elements and get data
     * @param event showtime event id
     */
    public void updateInfo(int event){
        //set view visible
        view.setVisibility(View.VISIBLE);
        //update elements (get data)
        FinnkinoAPIGetterInfo getterInfo = new FinnkinoAPIGetterInfo(getActivity(), event, imagee, title, kesto, vuosi, rating, genre, synopsis, time, location);
    }

    /**
     * This method set showtime time
     * @param text time
     */
    public void changeTime(String text){
        this.time.setText(text);
    }

    /**
     * This method set showtime location
     * @param text location
     */
    public void changeLocation(String text){
        this.location.setText(text);
    }

    /**
     * This method set ticket buying url
     * @param Ticket url of ticket
     */
    public void changeTicket(String Ticket){
        this.ticket = Ticket;
    }
}
