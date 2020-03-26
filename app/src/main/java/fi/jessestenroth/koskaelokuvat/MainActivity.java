package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import fi.jessestenroth.koskaelokuvat.fragments.ListFragment;
import fi.jessestenroth.koskaelokuvat.fragments.searchFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements searchFragment.sendData{
    private searchFragment sf;
    private ListFragment lf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        sf = (searchFragment) fm.findFragmentById(R.id.palkki);
        lf = (ListFragment) fm.findFragmentById(R.id.lista);
    }

    @Override
    public void sendDataToList(String areaId, String time) {
        lf.updateData(time, areaId);
    }

    @Override
    public void clearList() {
        lf.clearList();
    }
}
