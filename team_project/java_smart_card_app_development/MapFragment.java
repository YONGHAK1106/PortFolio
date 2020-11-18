package kr.co.cardledger;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.otto.Bus;

import java.util.ArrayList;

/**
 * 지도 화면
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        android.location.LocationListener {
    // LogCat tag
    private static final String TAG = MapFragment.class.getSimpleName();
    double latitude, longitude;
    LocationManager locationManager;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    private Context mContext;
    private GoogleMap mMap;
    private Bus mBus;
    private Marker mMyMarker;         // 내 마커 위치
    private Marker arduinoMarker;     // 아두이노 마커
    private Integer mCircleDistance;
    private Circle mCircle;
    private PolylineOptions polylineOptions;
    private MapView mapView;
    private Marker myMarker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(
                R.layout.map_layout, container, false);

        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        mContext = getActivity().getApplicationContext();

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 샘플
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("04/15 12:50");
//        markerOptions.snippet("국민카드 : 맥도날드");
//        googleMap.addMarker(markerOptions);
        mMap = googleMap;

        getCurrentLocation();

        getAllList();
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    private void updateMyMarker(Location l){
        if(l == null){
            return;
        }
        if(myMarker != null){
            myMarker.remove();
        }
        LatLng my = new LatLng(l.getLatitude(), l.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(my);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.my));
        markerOptions.title("내위치");
        myMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(my));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    private void updateCardMarker(CardData data){
        if(TextUtils.isEmpty(data.getLat()) || TextUtils.isEmpty(data.getLng())){
            return;
        }

        LatLng my = new LatLng(Double.valueOf(data.getLat()), Double.valueOf(data.getLng()));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(my);
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.my));
        markerOptions.title(data.getCardCompany() + " | " + data.getDate());
        markerOptions.snippet(data.getShop() + " : " + data.getPrice());
        mMap.addMarker(markerOptions);
    }



    private void getCurrentLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            // 저장 불가
        } else {
            if (isNetworkEnable) {  // 네트워크로 위치 수신
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    // 마지막 기록된 위치를 반환
                    Location l = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    updateMyMarker(l);
                }

            }


            if (isGPSEnable) {  // GPS가 사용가능할시
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    // 마지막 기록된 위치를 반환
                    Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    updateMyMarker(l);
                }
            }


        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateMyMarker(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * 전체 사용내역을 리스트에 담는다.
     */
    private int getAllList() {
        // db에서 카드 sms 내역을 불러온다.
        SQLiteOpenHelper dbhelper = new DBHelper(mContext);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.CARD_TABLE, null, null, null, null, null, "date desc");
        int sum = 0;
        String price;
        CardData data = null;
        if(cursor.moveToFirst()){			// 내역이 있으면
            ArrayList<CardData> list = new ArrayList<CardData>();
            do{
                data = new CardData();
                data.setCardCompany(cursor.getString(cursor.getColumnIndex("company")));
                data.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                data.setShop(cursor.getString(cursor.getColumnIndex("shop")));
                data.setBuy(cursor.getInt(cursor.getColumnIndex("buy")));
                data.setDate(cursor.getString(cursor.getColumnIndex("date")));
                data.setLat(cursor.getString(cursor.getColumnIndex("lat")));
                data.setLng(cursor.getString(cursor.getColumnIndex("lng")));
                list.add(data);				// 카드내역 데이터 추가
            }while(cursor.moveToNext());

            for(CardData d : list){
                updateCardMarker(d);
            }

        }

        cursor.close();
        db.close();
        dbhelper.close();
        return sum;

    }

}

