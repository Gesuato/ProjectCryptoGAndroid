package com.cryptog.cryptog;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.cryptog.cryptog.adapter.ViewPagerAdapter;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.dataDB.FileHandler;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DataCollection dataCollection;
    private ViewPagerAdapter adapter;
    private int index = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private final float FILETER = 0.8f;
    private float[] gravity = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileHandler fileHandler = new FileHandler(MainActivity.this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        dataCollection = new DataCollection();
        dataCollection.setCoins(fileHandler.fetchData());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            tabLayout = findViewById(R.id.tabLayoutId);
            viewPager = findViewById(R.id.viewPagerId);
            adapter = new ViewPagerAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.tabTitleFrench));
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
    }

    public void notifyChangeListCoinDataCollection(){
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.tabTitleFrench));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            gravity[0] = gravity[0] * FILETER + (1 - FILETER) * sensorEvent.values[0];
            gravity[1] = gravity[1] * FILETER + (1 - FILETER ) * sensorEvent.values[1];

            float accelerationX = Math.abs(sensorEvent.values[0] - gravity[0]);
            float biggestValue = 7;

            if (accelerationX > biggestValue && index == 0) {
                viewPager.setCurrentItem(1);
                index = 1;

            } else if (accelerationX > biggestValue && index == 1) {
                viewPager.setCurrentItem(0);
                index = 0;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
