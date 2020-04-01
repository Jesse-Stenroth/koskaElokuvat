package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import fi.jessestenroth.koskaelokuvat.fragments.ListFragment;
import fi.jessestenroth.koskaelokuvat.fragments.infoFragment;
import fi.jessestenroth.koskaelokuvat.fragments.searchFragment;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements searchFragment.sendData, ListFragment.sendToInfo{
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

    private void updateInfoFragment(int event, infoFragment info) {
        info.updateInfo(event);
    }
    private void startNewActivity(int event, String time, String location) {
        Intent showInfo = new Intent(this, Main2Activity.class);
        showInfo.putExtra("event", event);
        showInfo.putExtra("time", time);
        showInfo.putExtra("location", location);
        startActivity(showInfo);
    }

    @Override
    public void sendEvent(int event, String time, String location) {
        infoFragment info = (infoFragment) getSupportFragmentManager().findFragmentById(R.id.info);
        if(info == null){
            startNewActivity(event, time, location);
        } else{
            updateInfoFragment(event, info);
        }
    }
}
