package sharpenup.previous.sharpenup;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sharpenup.customgridview.R;


@SuppressWarnings("deprecation")
public class buttons extends Activity {
    Button pendings;
    Button feed;
    Button search;
    private PrefManager pref;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttons);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());

        //String pending_status =  DataHolderClass.getInstance().getDistributor_id();

        if (pref.getEvent() != null) {
            Notify();


        }
        
        
    /*Button    logout = (Button) findViewById(R.id.logout1);




        
        
        logout.setOnClickListener(new View.OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				
        				pref.logout();
        				Intent intent = new Intent(buttons.this, LoginData.class);
        				
        				startActivity(intent);
        			
        				
        			}
        		});
        */

        pendings = (Button) findViewById(R.id.pendings);


        pendings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(buttons.this, Pendings.class);
                intent.putExtra("back", "button");
                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                finish();

            }
        });


        feed = (Button) findViewById(R.id.feed);


        feed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(buttons.this, MainData.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                finish();
            }
        });


        search = (Button) findViewById(R.id.search);


        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(buttons.this, SearchActivity.class);
                intent.putExtra("back", "button");
                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                finish();

            }
        });


    }


    @SuppressLint("NewApi")
    private void Notify() {
       /* NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")
        
        Notification notification = new Notification(R.drawable.ic_launcher,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this,Pendings.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        
        notification.setLatestEventInfo(buttons.this, notificationTitle,notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;*/

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(buttons.this, Pendings.class);
        PendingIntent pIntent = PendingIntent.getActivity(buttons.this, 0, intent, 0);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("Pendings")
                .setContentText("You have pending list to complete.")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(soundUri)

                .addAction(R.drawable.ic_launcher, "View", pIntent)


                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }

    public void cancelNotification(int notificationId) {

        if (Context.NOTIFICATION_SERVICE != null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
    }


}
