package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fi.jessestenroth.koskaelokuvat.data.OwnLocation;
import fi.jessestenroth.koskaelokuvat.fragments.ListFragment;
import fi.jessestenroth.koskaelokuvat.fragments.infoFragment;
import fi.jessestenroth.koskaelokuvat.fragments.searchFragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements searchFragment.sendData, ListFragment.sendToInfo, LocationListener {
    private searchFragment sf;
    private ListFragment lf;
    private SavingFeature save;
    private boolean gpsOn = false;
    private final int PERMISSION_LOCATION = 2020;
    private boolean debuggi = true;
    private ArrayList<OwnLocation> list;
    private boolean locationCanUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        putDataToList();
        FragmentManager fm = getSupportFragmentManager();
        sf = (searchFragment) fm.findFragmentById(R.id.palkki);
        lf = (ListFragment) fm.findFragmentById(R.id.lista);
        locationCanUpdate = true;
        save = new SavingFeature(this);
        gpsOn = save.getBoolean("gps");
        checkLocation();
        save.saveBoolean("update", false);
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

    private void putDataToList(){
        list.add(new OwnLocation(24.945831, 60.192059, "Helsinki", "1002"));
        list.add(new OwnLocation(24.655899, 60.205490, "Espoo", "1012"));
        list.add(new OwnLocation(23.743065, 61.504967, "Tampere", "1021"));
    }

    private void checkLocation(){
        if(save.getBoolean("gps")){

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if(permissionCheck == PackageManager.PERMISSION_DENIED){
                String[] lis = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, lis, PERMISSION_LOCATION);
            } else{
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == PERMISSION_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                try{
                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                } catch (SecurityException e){
                    if(debuggi){
                        e.printStackTrace();
                    }
                }
            } else{
                Toast.makeText(this, getString(R.string.location_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
            //new Location
            if (debuggi) {
                Log.d("Location", "longi " + location.getLongitude() + " lati " + location.getLatitude());
            }
            if(save.getBoolean("gps")) {
                OwnLocation best = list.get(0);
                float ShortestDistance = distance(location, best);
                for (int lap = 1; lap < list.size(); lap++) {
                    OwnLocation now = list.get(lap);
                    if (distance(location, now) < ShortestDistance) {
                        best = now;
                    }
                }
                if(locationCanUpdate) {
                    save.saveArea(best.getName(), best.getCode());
                    save.saveBoolean("asetettu", true);
                    updateFragment();
                }
            }
    }

    private float distance(Location location, OwnLocation best) {
        double otherLongi = best.getLongitude();
        double otherLati = best.getLatitude();
        Location other = new Location("help");
        other.setLongitude(otherLongi);
        other.setLatitude(otherLati);
        return location.distanceTo(other);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private void updateFragment(){
        try {
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(sf);
                ft.attach(sf);
                ft.commit();
                locationCanUpdate = false;
        } catch (Exception e){
            if(debuggi){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
