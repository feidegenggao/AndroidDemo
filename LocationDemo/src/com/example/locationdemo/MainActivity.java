package com.example.locationdemo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private TextView locationTV;
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		locationTV = (TextView) findViewById(R.id.locationTV);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;

		} else {
			Toast.makeText(this, "No Location provider to use",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, provider, Toast.LENGTH_SHORT).show();
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			showLocation(location);
		} else {
			Log.d(TAG, "getLastKnownLocation location is null");
		}

		locationManager.requestLocationUpdates(provider, 5000, 1,
				locationListener);
	}

	private void showLocation(Location location) {
		String currentLocation = "Latitude is :" + location.getLatitude()
				+ "\n" + "Longitude" + location.getLongitude();
		Log.d(TAG, "showLocation:" + currentLocation);
		locationTV.setText(currentLocation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
			Log.d(TAG, "remove locationListener");
		}
	}

	LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d(TAG, "onStatusChanged:" + provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.d(TAG, "onProviderEnabled:" + provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.d(TAG, "onProviderDisabled:" + provider);
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.d(TAG, "onLocationChanged");
			showLocation(location);
		}
	};
}
