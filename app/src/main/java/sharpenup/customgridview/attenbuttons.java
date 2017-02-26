package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class attenbuttons extends Activity {

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
        setContentView(R.layout.atten);
        getActionBar().hide();
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(attenbuttons.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        Button create = (Button) findViewById(R.id.createatten);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(attenbuttons.this, AttendanceActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });


        Button view = (Button) findViewById(R.id.viewatten);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(attenbuttons.this, StudAtten.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });


    }


}
