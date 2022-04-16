package kg.geekteck.weatherapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kg.geekteck.weatherapp.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity implements LocationListener,
        AccessibilityManager.AccessibilityStateChangeListener{
    ActivityMain2Binding binding;
    private LocationManager manager;
    private double latitude;
    private double longitude;
    private Handler h;
    public static String userLocation=null;
    private Thread t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity2.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity2.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity2.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 11);
        }

        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
                binding.tvTitle.setVisibility(View.VISIBLE);
                binding.progressCircular.setVisibility(View.VISIBLE);
                binding.tvReload.setVisibility(View.VISIBLE);
                binding.tvTryAgain.setVisibility(View.GONE);
                binding.line.setVisibility(View.GONE);
                if (msg.what == 5){
                    if (userLocation==null){
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.tvTitle.setVisibility(View.GONE);
                        binding.tvReload.setVisibility(View.VISIBLE);
                        binding.tvTryAgain.setVisibility(View.VISIBLE);
                        binding.line.setVisibility(View.VISIBLE);
                    }
                }
            };
        };
        load();
        binding.tvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocations();
                load();
            }
        });

        binding.tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void load() {
        t = new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    h.sendEmptyMessage(i);
                    System.out.println("I ---  "+i);
                }
                if (userLocation!=null){
                    System.out.println("____Good");
                }
            }
        });
        t.start();
        getLocations();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getLocations() {
        System.out.println("____________+======MA================");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            System.out.println("*************____MA____============");
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    18,
                    0,
                    this
            );
            System.out.println("*************____1_MA___============");
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    18,
                    0,
                    this);
            return;
        }else {
            System.out.println("*************____1_MA___====77========");
        }
        System.out.println("*******87******____1_MA___============");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onstart");
        getLocations();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        System.out.println("90======MA=======");
        t.interrupt();
        manager.removeUpdates(this);
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        userLocation = lat+":"+lon;
        System.out.println(userLocation+"-----==-=");
        System.out.println("removed---------------");
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onFlushComplete(int requestCode) {
        System.out.println("--------------------");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("--------------------");

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        System.out.println("--------------------");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        System.out.println("--------------------");

    }

    @Override
    public void onAccessibilityStateChanged(boolean b) {
        System.out.println("--"+b);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.removeUpdates(this);
    }
}