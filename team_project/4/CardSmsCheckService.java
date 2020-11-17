package kr.co.cardledger;

import android.Manifest;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

/**
 * 문자 도착시 수신하는 서비스 클래스 카드 문자인지 체크하여 사용금액을 체크한후 설정한 금액이 넘을경우 알람으로 알려준다.
 */
public class CardSmsCheckService extends Service implements iCardConstant,
        LocationListener {


    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    long notify_interval = 1000;
    public static String str_receiver = "servicetutorial.service.receiver";


    private SharedPreferences defaultSharedPref;
    private CardData mData;
    // BroadcastReceiver for SMS
    BroadcastReceiver rcvIncoming = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            // 공유환경 설정 가져오기 setting.xml 값
            defaultSharedPref = PreferenceManager
                    .getDefaultSharedPreferences(context);
            Log.i("NAUTES", "SMS received");
            Bundle data = intent.getExtras();
            if (data != null) {
                Object pdus[] = (Object[]) data.get("pdus");
                String message = "New message:\n";
                String sender = null;
                for (Object pdu : pdus) {
                    SmsMessage part = SmsMessage.createFromPdu((byte[]) pdu);
                    message += part.getDisplayMessageBody();
                    if (sender == null) {
                        sender = part.getDisplayOriginatingAddress();
                    }
                }


                sender = sender.replace("-", "");
                // 카드사 문자인지 체크
                if (sender.equals(KB_CARD_NUMBER)
                        || sender.equals(LOTTE_CARD_NUMBER)
                        || sender.equals(CITY_CARD_NUMBER)
                        || sender.equals(HD_CARD_NUMBER)
                        || sender.equals(BC_CARD_NUMBER)
                        || sender.equals(SH_CARD_NUMBER)
                        || sender.equals(HANA_SK_CARD_NUMBER)
                        || sender.equals(TEST_CARD_NUMBER)) {

                    // 현재 위치 받아오기
                    Log.i("NAUTES", "SMS 33received");
                    Location location = getCurrentLocation();
                    CardContentInsert cardInsert = new CardContentInsert(
                            context);
                    Log.i("NAUTES", "SMS 5received");
                    mData = cardInsert.insert(sender, message, location,
                            System.currentTimeMillis());

                    new AsyncTaskSmsContentUpload().execute();
                    checkLimit();

                    Log.i("NAUTES", "From: " + sender);
                    Log.i("NAUTES", "Message: " + message);

                    Toast.makeText(context, "카드 문자를 수신하였습니다.", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    /**
     * 설정값 한도 카드값을 넘는지 체크한다.
     */
    private void checkLimit() {
        // db에서 카드 sms 내역을 불러온다.
        SQLiteOpenHelper dbhelper = new DBHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.CARD_TABLE, null, null, null, null,
                null, "date desc");
        ArrayList<CardData> list = null; // 카드 사용내역을 담을 리스트
        CardData data = null;
        if (cursor.moveToFirst()) { // 내역이 있으면
            list = new ArrayList<CardData>();
            do {
                data = new CardData();
                data.setCardCompany(cursor.getString(cursor
                        .getColumnIndex("company")));
                data.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                data.setShop(cursor.getString(cursor.getColumnIndex("shop")));
                data.setDate(cursor.getString(cursor.getColumnIndex("date")));
                data.setMillis(cursor.getLong(cursor.getColumnIndex("no")));
                list.add(data); // 카드내역 데이터 추가
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbhelper.close();

        // 이달의 카드 내역을 가져온다.
        Calendar cal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();
        int amount = 0; // 사용한 금액
        // 한도 금액가져오기
        int limitAmount = Integer.valueOf(defaultSharedPref.getString(
                "card_limit_value_preference", "100000"));
        if (list == null) {
            return;
        }
        for (CardData card : list) {
            try {
                cal.setTimeInMillis(card.getMillis());
            } catch (NumberFormatException e) {
                continue;
            }

            Log.i("NAUTES", "YEAR  " + cal.get(Calendar.YEAR));
            Log.i("NAUTES", "MONTH : " + cal.get(Calendar.MONTH));

            Log.i("NAUTES", "currentCal YEAR  " + currentCal.get(Calendar.YEAR));
            Log.i("NAUTES",
                    "currentCal MONTH : " + currentCal.get(Calendar.MONTH));

            // 현재 년과 월이 같은지 체크한다.
            if (cal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)
                    && cal.get(Calendar.MONTH) == currentCal
                    .get(Calendar.MONTH)) {
                String price;
                try {
                    price = card.getPrice();
                    price = price.replace(",", "").replace("원", "");
                    if (price.contains("(")) {
                        price = price.substring(0, price.indexOf("("));
                    }
                    if (!TextUtils.isEmpty(price))
                        amount += Integer.valueOf(price);
                    Log.i("card", "	amount : " + amount);
                } catch (NumberFormatException e) {
                    continue;
                }
                Log.i("card", "price : " + price);
            }
        }

        //  if (amount > limitAmount) { // 총 사용한 금액이 넘을경우
        // 알람 설정
        // 리시버 발생시 수신할 알람리시버
        Intent i = new Intent(getBaseContext(),
                CardLimitReceiver.class);
        i.putExtra("amount", amount);
        PendingIntent sender = PendingIntent.getBroadcast(
                getBaseContext(), 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        try {
            sender.send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 서비스가 실행될때
     */
    @Override
    public int onStartCommand(final Intent intent, final int flags,
                              final int startId) {
        registerReceiver(rcvIncoming, new IntentFilter(
                "android.provider.Telephony.SMS_RECEIVED"));
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    private Location getCurrentLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            // 저장 불가
        } else {
            if (isNetworkEnable) {  // 네트워크로 위치 수신
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    // 마지막 기록된 위치를 반환
                    return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

            }


            if (isGPSEnable) {  // GPS가 사용가능할시
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    // 마지막 기록된 위치를 반환
                    return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }


        }

        return null;

    }

    @Override
    public void onLocationChanged(Location location) {

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
     * 카드 사용내역 전송
     */
    private class AsyncTaskSmsContentUpload extends
            AsyncTask<Object, String, Integer> {

        @Override
        protected void onPostExecute(final Integer count) { // 전송 완료후
        }

        /*
         */
        @Override
        protected void onPreExecute() { // 전송전 프로그래스 다이얼로그로 전송중임을 사용자에게 알린다.
        }

        @Override
        protected void onProgressUpdate(final String... values) {
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[]) 비동기 모드로 전송
         */
        @Override
        protected Integer doInBackground(final Object... params) { // 전송중
            // http 로 보낼 이름 값 쌍 컬랙션
            Vector<NameValuePair> vars = new Vector<NameValuePair>();
            DeviceInfo di = DeviceInfo
                    .setDeviceInfo((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
            String phone = di.getDeviceNumber();
            Log.i("tag", "phone num" + phone);
            vars.add(new BasicNameValuePair("count", "1")); // 카드사
            vars.add(new BasicNameValuePair("phone_num", phone)); // 전화번호
            vars.add(new BasicNameValuePair("company", mData
                    .getCardCompany())); // 카드사
            vars.add(new BasicNameValuePair("shop", mData.getShop())); // 상호명
            vars.add(new BasicNameValuePair("price", mData.getPrice())); // 가격
            vars.add(new BasicNameValuePair("date", String.valueOf(mData.getDate())));// 날짜
            vars.add(new BasicNameValuePair("buy", String.valueOf(mData
                    .getBuy()))); // 날짜
            Log.i("cardledger",
                    mData.getCardCompany() + " " + mData.getShop()
                            + "   price : " + mData.getPrice()
                            + "   date : " + String.valueOf(mData.getDate())
                            + "   buy :" + String.valueOf(mData.getBuy())
                            + "   phone_num : " + phone);
            try {
                String url = "http://" + iCardConstant.SERVER_URL
                        + "/cardledger/insert.php?"
                        + URLEncodedUtils.format(vars, null);
                url += URLEncodedUtils.format(vars, "UTF-8");
                HttpGet request = new HttpGet(url);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                HttpClient client = new DefaultHttpClient();
                final String responseBody = client.execute(request,
                        responseHandler); // 전송

                Log.i("tag", responseBody);
            } catch (ClientProtocolException e) {
                Log.e("tag", "Failed to get playerId (protocol): ", e);
                // return false;
            } catch (IOException e) {
                Log.e("tag", "Failed to get playerId (io): ", e);
                // return false;
            }

            return 1;
        }

    }
}
