package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public class MyService extends Service {


    JSONParser jparser = new JSONParser();
    PrefManager pref;
    String roll;
    //PrefManager pref = new PrefManager(getApplicationContext());


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {


    }


    @Override
    public void onStart(Intent intent, int startId) {

        try {


            pref = new PrefManager(getApplication());


            roll = pref.getRoll();


//Toast.makeText(this, "Notification Service was Started", Toast.LENGTH_LONG).show();
            new studentsview().execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    @Override
    public void onDestroy() {

        //Toast.makeText(this, "Notification Service was Stopped", Toast.LENGTH_LONG).show();

    }


    private void triggerNotification(String message, String heading) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.tasks;
        System.currentTimeMillis();
        getApplicationContext();
        CharSequence contentTitle = heading;
        CharSequence contentText = message;
        Intent notificationIntent = new Intent(this, MainActivityNew.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Builder builder = new Notification.Builder(this);
        builder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);


        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(contentText).build();

        // and this
        final int HELLO_ID = 1;
        mNotificationManager.notify(HELLO_ID, notification);


    }


    private class studentsview extends AsyncTask<String, Void, String> {
        JSONObject json;


        /** progress dialog to show user that the backup is processing. */
        /**
         * application context.
         */

        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("serial", roll));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/getMessage.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            //JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            //JSONArray jsonArray;

            try {
                // Checking for SUCCESS TAG

                //forjson.clear();

                String heading = "";
                String message = "";
                int appVersion = 0;
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    heading = json.getString("Heading");
                    message = json.getString("Message");
                    appVersion = Integer.parseInt(json.getString("APPVERSION"));
                    // forjson.add(Roll+"-"+ NAME);
                    //categories_description.add(description);


                }
                if ((!heading.equals("") && !message.equals("")))
                    triggerNotification(message, heading);


                pref.setUpdater(appVersion);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


}