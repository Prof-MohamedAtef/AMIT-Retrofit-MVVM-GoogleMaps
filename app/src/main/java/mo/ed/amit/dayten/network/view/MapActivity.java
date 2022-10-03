package mo.ed.amit.dayten.network.view;

import static mo.ed.amit.dayten.network.util.MapHelper.displayMarker;
import static mo.ed.amit.dayten.network.util.MapHelper.markerOptions;
import static mo.ed.amit.dayten.network.util.MapHelper.returnCameraPosition;
import static mo.ed.amit.dayten.network.util.MapHelper.statusCheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.databinding.ActivityMapBinding;
import mo.ed.amit.dayten.network.util.Configs;
import mo.ed.amit.dayten.network.util.MapHelper;
import mo.ed.amit.dayten.network.util.VerifyConnection;
import mo.ed.amit.dayten.network.view.adapter.ProfilesRecyclerAdapter;
import mo.ed.amit.dayten.network.view.fragment.ProfilesFragment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        android.location.LocationListener,
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        MapHelper.OnAddressPrintReady ,
        MapHelper.OnServiceUnavailable,
        ProfilesRecyclerAdapter.OnProfileSelected {

    private static int UPDATE_INTERVAL = 10000;
    private static int FATEST_INTERVAL = 5000;
    private static int DISPLACMENT = 10;
    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICES_RES_REQUEST = 7001;

    ActivityMapBinding binding;
    private View parentLayout;
    private VerifyConnection verifyConnection;
    private SupportMapFragment mapFragment;
    private LocationManager manager;
    private FusedLocationProviderClient location;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private CameraPosition googlePlex;
    private Location mLastLocation;
    private Marker mUserMarker;
    private GoogleMap mGoogleMap;
    private MapStyleOptions mMapStyleOptions;
    private boolean permissionGranted;
    private LatLng mCenterLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configs.MapActivity=MapActivity.this;
        binding= DataBindingUtil.setContentView(this, R.layout.activity_map);

        parentLayout = findViewById(android.R.id.content);
        verifyConnection=new VerifyConnection(getApplicationContext());

        binding.centerCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CenterCamera: ", "Clicked me");
                if (mLastLocation != null) {
                    if (mGoogleMap!=null){
                        animateCameraMyLocation(mGoogleMap);
                    }
                }
            }
        });
        initGoogleMap();
        buildGoogleApiClient();
        createLocationRequest();

        inflateRecyclerFragment();
    }

    private void inflateRecyclerFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.recyclerFrame.getId(), ProfilesFragment.newInstance()).commitAllowingStateLoss();
    }


    private void animateCameraMyLocation(GoogleMap mMap) {
        if (mLastLocation != null) {
            googlePlex = returnCameraPosition(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()), 19.0f);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1500, null);
            displayMarker(mUserMarker,mMap,"MyLocation",new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),R.drawable.me);
            mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    mGoogleMap.clear();
                    displayMarker(mUserMarker, mGoogleMap, "MyLocation",new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), R.drawable.me);
                }
            });
        } else {
            Log.d("ERROR", "Cannot get Your Location");
            retryRequestLocationUpdates();
        }
    }

    private void animateCameraToDriver(GoogleMap mGoogleMap, String latitude, String longitude, String driverName) {
        if (mLastLocation != null) {
            LatLng latLong = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(19.0f)
                    .bearing(0)
                    .tilt(45).build();
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            displayMarker(mUserMarker, mGoogleMap, driverName, latLong, R.drawable.taxi);
            mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    mGoogleMap.clear();
                    displayMarker(mUserMarker, mGoogleMap, driverName, latLong, R.drawable.taxi);
                }
            });
        } else {
            Log.d("ERROR", "Cannot get Your Location");
            retryRequestLocationUpdates();
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACMENT);
    }

    private void retryRequestLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            createLocationRequest();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGoogleMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map_fragment);
        mapFragment.getMapAsync(this);
        manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        location = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        LocationGrantPermissionTry();
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                .addConnectionCallbacks(MapActivity.this)
                .addOnConnectionFailedListener(MapActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void LocationGrantPermissionTry() {
        ActivityCompat.requestPermissions(MapActivity.this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                Configs.MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        try {
            if (location != null)
                changeMap(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeMap(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // check if map is created successfully or not
        if (mGoogleMap != null) {
            mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;


            latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(19f).tilt(70).build();

            try {
                mGoogleMap.setMyLocationEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                LocationGrantPermissionTry();
            }
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        } else {
//            Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MapActivity.this);
                setUpLocation();
            }
        }
    }


    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request runtime permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                displayLocation();
            }
        }
    }


    /*
    check google play services on your device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RES_REQUEST).show();
            } else {
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }


    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            final double latitude = mLastLocation.getLatitude();
            final double longitude = mLastLocation.getLongitude();
            if (mUserMarker != null) {
                mUserMarker.remove();// remove already marker
                if (mGoogleMap != null) {

                    // Move Camera To this position
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 19.0f));
                    // draw animation rotate marker
                    displayMarker(mUserMarker,mGoogleMap,"MyLocation",new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),R.drawable.me);
                    mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                        @Override
                        public void onCameraMove() {
                            mGoogleMap.clear();
                            displayMarker(mUserMarker, mGoogleMap, "MyLocation",new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), R.drawable.me);
                        }
                    });
//                            rotateMarker(mCurrent, -360, mMap);
                }
            } else {
                if (mGoogleMap != null) {
                    // Move Camera To this position
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 19.0f));
                    // draw animation rotate marker
//                            rotateMarker(mCurrent, -360, mMap);
                    displayMarker(mUserMarker,mGoogleMap,"MyLocation",new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),R.drawable.me);
                    mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                        @Override
                        public void onCameraMove() {
                            mGoogleMap.clear();
                            displayMarker(mUserMarker, mGoogleMap, "MyLocation",new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), R.drawable.me);
                        }
                    });
                }
            }
            if (verifyConnection.isConnected()){
                displayStreetName();
            }
        } else {
            Log.d("ERROR", "Cannot get Your Location");
            retryRequestLocationUpdates();
            ShowSnackBar(parentLayout, getResources().getString(R.string.internet_location_disabled));
        }
    }

    private void displayStreetName() {
        MapHelper.getAddressPrint(getApplicationContext(),mLastLocation,(MapHelper.OnServiceUnavailable)MapActivity.this,true );
    }

    private void ShowSnackBar(View parentLayout, String msg) {
        final Snackbar snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
        snackBar.show();
    }

    private void ShowSnackBar(View parentLayout) {
        final Snackbar snackBar = Snackbar.make(parentLayout, getString(R.string.accept_grant), Snackbar.LENGTH_LONG);
        snackBar.setAction("Grant", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (permissionGranted) {
                            if (mLastLocation != null) {
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 19.0f));
                            }
                        } else {
                            LocationGrantPermissionTry();
                        }
                    }
                }).setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Configs.MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //If user presses allow
//                Toast.makeText(MapActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                permissionGranted = true;
            } else {
                //If user presses deny
                permissionGranted = false;
            }
            if (location == null) {
                statusCheck(manager, getApplicationContext());
            } else {
                //If everything went fine lets get latitude and longitude
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLastLocation = location;
                            double currentLatitude = location.getLatitude();
                            double currentLongitude = location.getLongitude();
                            mUserMarker=displayMarker(mUserMarker, mGoogleMap, "MyLocation", new LatLng(currentLatitude,currentLongitude),R.drawable.me);
                        } else {
                            ShowSnackBar(parentLayout, getResources().getString(R.string.internet_location_disabled));
                        }
                    }
                });
            }
        }else if(requestCode==Configs.MY_PERMISSIONS_REQUEST_CALL_PHONE){
            if (grantResults.length>0
                &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Calls are allowed!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),"Calls are not allowed!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void printAddress(String address) {
        binding.tvUsername.setText(address);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            if (verifyConnection.isConnected()) {
                if (mLastLocation != null) {
                    displayStreetName();
                }
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap=googleMap;
        mMapStyleOptions= MapStyleOptions.loadRawResourceStyle(getApplicationContext(),R.raw.map_style);
        mGoogleMap.setMapStyle(mMapStyleOptions);
        animateCameraMyLocation(mGoogleMap);
        mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("Camera postion change" + "", cameraPosition + "");
                mCenterLatLong = cameraPosition.target;
//                mGoogleMap.clear();
                try {
                    mLastLocation.setLatitude(mCenterLatLong.latitude);
                    mLastLocation.setLongitude(mCenterLatLong.longitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void ServiceUnavailable(String msg) {
        if (verifyConnection.isConnected()){

        }else {
            ShowSnackBar(parentLayout,msg);
        }
    }

    @Override
    public void onProfileItemSelection(String latitude, String longitude, String driverName) {
        animateCameraToDriver(mGoogleMap,latitude, longitude, driverName);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 10/2/2022 best practice
        Configs.MapActivity=null;
    }
}