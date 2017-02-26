package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class testmenu extends Activity {
    PrefManager pref;

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
        setContentView(R.layout.testmenu);
        pref = new PrefManager(testmenu.this);
        getActionBar().hide();
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(testmenu.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLay);
        Button create = (Button) findViewById(R.id.viewtt);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(testmenu.this, testview.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });


        Button view = (Button) findViewById(R.id.assignres);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(testmenu.this, assignfac.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });
        if (pref.getLogin().equals("teacher"))
            ll.removeView(view);

        Button marks = (Button) findViewById(R.id.marks);

        marks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(testmenu.this, marks_assign_View.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });

        Button upload = (Button) findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(testmenu.this, webview.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });


        Button stud = (Button) findViewById(R.id.stud_view);

        stud.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(testmenu.this, marks_assign_View.class);
                intent.putExtra("marksView", true);
                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });
        Button marksViewStudWise = (Button) findViewById(R.id.button);
        marksViewStudWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testmenu.this, viewscores.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
            }
        });

    }


}
