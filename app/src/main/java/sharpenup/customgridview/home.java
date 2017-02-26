package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class home extends Activity {

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
        setContentView(R.layout.home);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        if (pref.getLogin() != null) {
               /*Intent reload = new Intent(LoginActivity.this, LoginActivity.class);
				startActivity(reload);
				finish();*/

            if (pref.getLogin().equals("child")) {


                // Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.",Toast.LENGTH_SHORT).show();

                Intent admin = new Intent(home.this, MainActivityNew.class);
                startActivity(admin);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                finish();

            }

            if (pref.getLogin().equals("admin")) {


                // Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.",Toast.LENGTH_SHORT).show();

                Intent admin = new Intent(home.this, MainActivity.class);
                startActivity(admin);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                finish();

            } else if (pref.getLogin().equals("teacher")) {
                Intent zebra = new Intent(home.this, MainActivityTeacher.class);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                startActivity(zebra);
                finish();

            }


        }
        ImageButton create = (ImageButton) findViewById(R.id.studentlogin);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(home.this, LoginActivity2.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);


            }
        });


        ImageButton view = (ImageButton) findViewById(R.id.proflogin);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pref.logout();
                Intent intent = new Intent(home.this, LoginActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);

            }
        });


    }


}
