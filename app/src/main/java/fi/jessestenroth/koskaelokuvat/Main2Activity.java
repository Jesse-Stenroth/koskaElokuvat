package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import fi.jessestenroth.koskaelokuvat.fragments.infoFragment;

import android.content.Intent;
import android.os.Bundle;

/**
 * This class is for smartphones and this hold details of showtime
 * @author Jesse Stenroth
 */
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        infoFragment info = (infoFragment) getSupportFragmentManager().findFragmentById(R.id.info);
        Intent i = getIntent();
        info.changeTime(i.getStringExtra("time"));
        info.changeLocation(i.getStringExtra("location"));
        info.updateInfo(i.getIntExtra("event", 0));
        info.changeTicket(i.getStringExtra("ticket"));
    }
}
