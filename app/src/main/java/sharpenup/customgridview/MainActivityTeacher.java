package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import sharpenup.previous.sharpenup.PrefManager;
import sharpenup.previous.sharpenup.buttons;
import sharpenup.worksheet.TeacherSelect;

public class MainActivityTeacher extends Activity {

    GridView grid;
    String[] web = {
            "Time Table",
            "Attendance",
            "Test",
            "Workbook",
            "Complain",
            "Logout"


    };
    int[] imageId = {
            R.drawable.timetable,

            R.drawable.attendance,
            R.drawable.test,
            R.drawable.workbook,
            R.drawable.compain,
            R.drawable.logout,


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
        setContentView(R.layout.activity_teacher);

        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivityTeacher.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        CustomGrid adapter = new CustomGrid(MainActivityTeacher.this, web, imageId);
        grid = (GridView) findViewById(R.id.gridTeacher);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // String  itemValue    = (String) grid.getItemAtPosition(position);

                if (web[+position].equals("Time Table")) {

                    Intent time = new Intent(MainActivityTeacher.this, time_table.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }


                if (web[+position].equals("Attendance")) {

                    Intent time = new Intent(MainActivityTeacher.this, attenbuttons.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }


                if (web[+position].equals("Test")) {

                    Intent time = new Intent(MainActivityTeacher.this, testmenu.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }

                if (web[+position].equals("Workbook")) {

                    Intent time = new Intent(MainActivityTeacher.this, TeacherSelect.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }
                if (web[+position].equals("Complain")) {

                    Intent time = new Intent(MainActivityTeacher.this, buttons.class);
                    startActivity(time);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);


                }

                if (web[+position].equals("Logout")) {

                    pref.logout();
                    Intent intent = new Intent(MainActivityTeacher.this, homewall.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);
                    finish();

                }


            }
        });

    }


}
