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
import sharpenup.previous.sharpenup.admin;
import sharpenup.previous.sharpenup.buttons;
import sharpenup.worksheet.WorksheetActivity;

public class MainActivity extends Activity
        GridView grid;
String[]web={
        "Time Table",
        "Add Student",
        "Attendance",
        "Test Actions",
        "Workbook",
        "Complaint",
        "Database",
        "Notifications",
        "Observe Tool",
        "Logout"


        };
        int[]imageId={
        R.drawable.timetable,
        R.drawable.addnew,
        R.drawable.attendance,
        R.drawable.test,
        R.drawable.workbook,
        R.drawable.compain,
        R.drawable.database,
        R.drawable.notificationbell,
        R.drawable.observation,
        R.drawable.logout,


        };
private PrefManager pref;

        {@Override
public void onBackPressed(){
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main,R.anim.close_next);
        }

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().hide();
        pref=new PrefManager(getApplicationContext());


        AlertDialogManager alert=new AlertDialogManager();
        ConnectionDetector cd=new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if(!cd.isConnectingToInternet()){
        // Internet Connection is not present
        alert.showAlertDialog(MainActivity.this,
        "Internet Connection Error",
        "Please connect to working Internet connection",false);
        // stop executing code by return
        return;
        }


        CustomGrid adapter=new CustomGrid(MainActivity.this,web,imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){

@Override
public void onItemClick(AdapterView<?>parent,View view,
        int position,long id){


        // String  itemValue    = (String) grid.getItemAtPosition(position);

        if(web[+position].equals("Time Table"))
        {

        Intent time=new Intent(MainActivity.this,time_table.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }

        if(web[+position].equals("Add Student"))
        {

        Intent time=new Intent(MainActivity.this,Entry.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }

        if(web[+position].equals("Attendance"))
        {

        Intent time=new Intent(MainActivity.this,attenbuttons.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }


        if(web[+position].equals("Test Actions"))
        {

        Intent time=new Intent(MainActivity.this,testmenu.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }

        if(web[+position].equals("Workbook"))
        {

        Intent time=new Intent(MainActivity.this,WorksheetActivity.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }
        if(web[+position].equals("Complaint"))
        {

        Intent time=new Intent(MainActivity.this,buttons.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }
        if(web[+position].equals("Database"))
        {

        Intent time=new Intent(MainActivity.this,admin.class);
        startActivity(time);overridePendingTransition(R.anim.open_next,R.anim.close_main);


        }

        if(web[+position].equals("Notifications"))
        {


        Intent intent=new Intent(MainActivity.this,NotificationManagement.class);

        startActivity(intent);overridePendingTransition(R.anim.open_next,R.anim.close_main);
        finish();

        }

        if(web[+position].equals("Observe Tool"))
        {


        Intent intent=new Intent(MainActivity.this,ObserveTool.class);

        startActivity(intent);overridePendingTransition(R.anim.open_next,R.anim.close_main);
        finish();

        }


        if(web[+position].equals("Logout"))
        {

        pref.logout();
        Intent intent=new Intent(MainActivity.this,homewall.class);

        startActivity(intent);overridePendingTransition(R.anim.open_next,R.anim.close_main);
        finish();

        }


        }
        });

        }


        }
