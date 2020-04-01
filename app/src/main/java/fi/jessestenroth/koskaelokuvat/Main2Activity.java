package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import fi.jessestenroth.koskaelokuvat.fragments.infoFragment;

import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        infoFragment info = (infoFragment) getSupportFragmentManager().findFragmentById(R.id.info);
        info.updateInfo(getIntent().getIntExtra("event", 0));
    }
}
