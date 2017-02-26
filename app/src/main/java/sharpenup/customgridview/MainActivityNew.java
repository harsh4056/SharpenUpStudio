package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.worksheet.User;

public class MainActivityNew extends Activity {

    AlarmManager alarmManager;
    GridView grid;
    String[] web = {
            "Time Table",

            "Attendance",
            "Test",
            "Workbook",
            "Settings",
            "ID Card",
            "Child Report",
            "Logout"


    };
    int[] imageId = {
            R.drawable.timetable,

            R.drawable.attendance,
            R.drawable.test,
            R.drawable.workbook,
            R.drawable.settings,
            R.drawable.idcardicon,
            R.drawable.building1,
            R.drawable.logout


    };
    private PrefManager pref;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);


        int i = 300;
        Intent intent = new Intent(MainActivityNew.this, MyBroadcastReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);


        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());


        CustomGrid adapter = new CustomGrid(MainActivityNew.this, web, imageId);
        grid = (GridView) findViewById(R.id.grid2);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // String  itemValue    = (String) grid.getItemAtPosition(position);

                if (web[+position].equals("Time Table")) {

                    Intent time = new Intent(MainActivityNew.this, viewtt_student.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }


                if (web[+position].equals("Attendance")) {

                    Intent time = new Intent(MainActivityNew.this, srudatten_student.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }


                if (web[+position].equals("Test")) {

                    Intent time = new Intent(MainActivityNew.this, testmenu_student.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }

                if (web[+position].equals("Workbook")) {

                    Intent time = new Intent(MainActivityNew.this, User.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }
                if (web[+position].equals("Settings")) {

                    Intent time = new Intent(MainActivityNew.this, Settings.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }

                if (web[+position].equals("Logout")) {

                    pref.logout();

                    Intent i = new Intent(MainActivityNew.this, MyBroadcastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getBaseContext(), 234324243, i, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();


                    Intent intent = new Intent(MainActivityNew.this, homewall.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);
                    finish();
                    //Intent i = new Intent(MainActivityNew.this, MyBroadcastReceiver.class);


                }

                if (web[+position].equals("Child Report")) {

                    Intent intent = new Intent(MainActivityNew.this, ChildReport.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);
                    finish();

                }


                if (web[+position].equals("ID Card")) {
                    String data =
                            "\nClass : " + pref.getclass() + "-" + pref.getBatch() +
                                    "\nCentre : " + pref.getCenter();
                    new SweetAlertDialog(MainActivityNew.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText(pref.getName() + data)

                            .setCustomImage(R.drawable.idcardicon)
                            .show();


                }


                //    Toast.makeText(MainActivityNew.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        if (pref.getUpdater() != getAppVersion(MainActivityNew.this)) {
            new SweetAlertDialog(MainActivityNew.this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Update Available!")
                    .setContentText("A newer version of the Sharpen Up app is available at the playstore!!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=sharpenup.customgridview"));
                            sweetAlertDialog.dismiss();
                            startActivity(intent1);

                        }
                    }).show();


        }
    }


    private int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
