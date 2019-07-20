package id.wth.celenganmandiri.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

import id.wth.celenganmandiri.activity.MainActivity;
import id.wth.celenganmandiri.app.App;
import id.wth.celenganmandiri.config.Config;

/**
 * Created by adminmc on 13/03/17.
 */

public class Utils {

    private static String TAG = Utils.class.getSimpleName();

    private String longitude, latitude, addressLine = "", deviceID = "";
    private Context context;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    //Firebase
    public static final String URL_STORAGE_REFERENCE = "kongkon-f23ca.appspot.com";
    public static final String FOLDER_STORAGE_IMG = "images";

    public Utils(Context context) {
        this.context = context;
    }

    /*
    |-----------------------------------------------------------------------------------------------
    | Dialog for alert no internet connection
    |-----------------------------------------------------------------------------------------------
    */
    public void showErrorDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(context,
                        android.R.style.Theme_Dialog));
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.setIcon(android.R.drawable.stat_sys_warning);
        alert.show();
    }

    /*
   |-----------------------------------------------------------------------------------------------
   | Dialog for alert request failed
   |-----------------------------------------------------------------------------------------------
   */
    public void showAlertDlg(Handler handler, String message, Context context) {
        final String strMessage = message;
        final Context mContext = context;
        handler.post(new Runnable() {
            public void run() {
                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setMessage(strMessage)
                        .setCancelable(false)
                        .setNegativeButton("close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                alertDialog.show();
            }
        });
    }

    /*
    |-----------------------------------------------------------------------------------------------
    | Set mobile IMEI
    |-----------------------------------------------------------------------------------------------
    */
    public void setIMEI() {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            deviceID = tm.getDeviceId();
            SharedPreferences imeiPref = context.getSharedPreferences(Config.KEY_IMEI, Context.MODE_PRIVATE);
            SharedPreferences.Editor shareEditor = imeiPref.edit();
            shareEditor.putString("device_id", deviceID);
            shareEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    |-----------------------------------------------------------------------------------------------
    | Get mobile IMEI
    |-----------------------------------------------------------------------------------------------
    */
    public String getIMEI() {
        try {
            SharedPreferences sessionpref = context.getSharedPreferences(Config.KEY_IMEI, Context.MODE_PRIVATE);
            deviceID = sessionpref.getString("device_id", null);
            Log.d("DatabaseManager", "imei= " + deviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceID;
    }

    /*
   |-----------------------------------------------------------------------------------------------
   | Method for get ip address
   |-----------------------------------------------------------------------------------------------
   */
    public static String getMobileIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipaddress = inetAddress .getHostAddress().toString();
                        Log.e(TAG, "Exception in Get IP Address: " + ipaddress);
                        //ip_address = Integer.toString(ipaddress);
                        return ipaddress;

                    }
                }
            }

        } catch (SocketException ex) {
            Log.i(TAG, "Exception in Get IP Address: " + ex.toString());
        }
        return null;
    }

    /*
   |-----------------------------------------------------------------------------------------------
   | Method for direct to login form
   |-----------------------------------------------------------------------------------------------
   */
     public static void gotoLogin(Activity activity) {
         App.getInstance().getPrefManager().clear();
         Intent intent = new Intent(activity, MainActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                 | Intent.FLAG_ACTIVITY_CLEAR_TASK
                 |Intent.FLAG_ACTIVITY_NO_HISTORY
                 | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                 | Intent.FLAG_ACTIVITY_CLEAR_TOP);
         activity.startActivity(intent);
         activity.finish();
     }

    /**
     * Method to get current date
     */
    public String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        return  formattedDate;
    }

    public static String getCurrentDateandTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return  formattedDate;
    }

    public String getCurrentTime() {
        String locaTime ="";
        try {
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
            Date currentLocalTime = c.getTime();
            DateFormat date = new SimpleDateFormat("HH:mm:ss");
            date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            locaTime = date.format(currentLocalTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locaTime;
    }

    public static String parseDateToddMMyyyy(String time) {
        time = time.replaceAll("T"," ");
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Method for set time field
     */
    public void setTimeField(Context context, TimePickerDialog timePickerDialog, TextView textView) {
        final TextView time = textView;
        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {
                time.setText(hourOfDay + ":" + minuteOfDay);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    /**
     * Method for set date field
     */
    public void setDateField(Context context, DatePickerDialog datePickerDialog, SimpleDateFormat dateFormatter,
                             TextView textView) {
        final TextView date = textView;
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        final SimpleDateFormat simpleDateFormat = dateFormatter;

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Part of Floating Button library
     */
    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int distance(Location StartP, Location EndP) {
       /* Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);*/

        float distanceInMeters = StartP.distanceTo(EndP);
        int dist = (int) distanceInMeters;
        return dist;
    }

    public static int distances(Location StartP, Location EndP) {
        double earthRadius = 6371000; //meters
        double lat1 = StartP.getLatitude();
        double lat2 = EndP.getLatitude();
        double lng1 = StartP.getLongitude();
        double lng2 = EndP.getLongitude();

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        int dist = (int) (earthRadius * c);
        return dist;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = Utils.getConnectivityStatus(context);
        String status = null;
        if (conn == Utils.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == Utils.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == Utils.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodedImage;
    }

    public  static boolean verificaConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }

    public static String local(String latitudeFinal,String longitudeFinal){
        return "https://maps.googleapis.com/maps/api/staticmap?center="+latitudeFinal+","+longitudeFinal+"&zoom=18&size=280x280&markers=color:red|"+latitudeFinal+","+longitudeFinal;
    }

    public class LocationConstants {
        public static final int SUCCESS_RESULT = 0;

        public static final int FAILURE_RESULT = 1;

        public static final String PACKAGE_NAME = "com.sample.sishin.maplocation";

        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

        public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

        public static final String LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA";
        public static final String LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY";
        public static final String LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET";
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
