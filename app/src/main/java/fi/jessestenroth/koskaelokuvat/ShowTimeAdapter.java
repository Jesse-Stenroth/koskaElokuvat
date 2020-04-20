package fi.jessestenroth.koskaelokuvat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import fi.jessestenroth.koskaelokuvat.data.ShowTime;

/**
 * This class is adapter for listview
 * @author Jesse Stenroth
 */
public class ShowTimeAdapter extends ArrayAdapter<ShowTime> {

    private Context mContext;
    private ArrayList<ShowTime> l;

    public ShowTimeAdapter(@NonNull Context context, ArrayList<ShowTime> list) {
        super(context, 0 , list);
        mContext = context;
        l = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            //layout of one listitem
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }
        //get elements
        ImageView image = (ImageView) listItem.findViewById(R.id.imageElement);
        TextView title = (TextView) listItem.findViewById(R.id.title_item);
        TextView location = (TextView) listItem.findViewById(R.id.location_item);
        TextView aika = (TextView) listItem.findViewById(R.id.aika);
        ImageView rating = (ImageView) listItem.findViewById(R.id.ratingImageItem);

        //set data to view
        title.setText(l.get(position).getTitle());
        location.setText(l.get(position).getTheatreAndAuditorium());
        aika.setText(l.get(position).getStart() +" - " + l.get(position).getEnd());

        return listItem;
    }
}
