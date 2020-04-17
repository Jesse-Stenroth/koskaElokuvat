package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import fi.jessestenroth.koskaelokuvat.fragments.ListFragment;
import fi.jessestenroth.koskaelokuvat.fragments.infoFragment;
import fi.jessestenroth.koskaelokuvat.fragments.searchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements searchFragment.sendData, ListFragment.sendToInfo{
    private searchFragment sf;
    private ListFragment lf;
    private SavingFeature save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = new SavingFeature(this);
        save.saveBoolean("update", false);

        FragmentManager fm = getSupportFragmentManager();
        sf = (searchFragment) fm.findFragmentById(R.id.palkki);
        lf = (ListFragment) fm.findFragmentById(R.id.lista);
    }

    @Override
    public void sendDataToList(String areaId, String time) {
        lf.clearList();
        lf.updateData(time, areaId);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        if(save.getBoolean("update")){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public void clearList() {
        lf.clearList();
    }

    private void updateInfoFragment(int event, infoFragment info, String time, String location, String ticket) {
        info.updateInfo(event);
        info.changeTime(time);
        info.changeLocation(location);
        info.changeTicket(ticket);
    }
    private void startNewActivity(int event, String time, String location, String ticket) {
        Intent showInfo = new Intent(this, Main2Activity.class);
        showInfo.putExtra("event", event);
        showInfo.putExtra("time", time);
        showInfo.putExtra("location", location);
        showInfo.putExtra("ticket", ticket);
        startActivity(showInfo);
    }

    @Override
    public void sendEvent(int event, String time, String location, String ticket) {
        infoFragment info = (infoFragment) getSupportFragmentManager().findFragmentById(R.id.info);
        if(info == null){
            startNewActivity(event, time, location, ticket);
        } else{
            updateInfoFragment(event, info, time, location, ticket);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem m = menu.findItem(R.id.clickSettings);
        m.setEnabled(true);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()){
            case (R.id.clickSettings):
                //go
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                return true;
        }
        return false;
    }
}
