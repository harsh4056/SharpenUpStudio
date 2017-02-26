package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class homewall extends Activity {
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
        setContentView(R.layout.homewall);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        if (pref.getLogin() != null) {
               /*Intent reload = new Intent(LoginActivity.this, LoginActivity.class);
				startActivity(reload);
				finish();*/

            if (pref.getLogin().equals("child")) {


                // Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.",Toast.LENGTH_SHORT).show();

                Intent admin = new Intent(homewall.this, MainActivityNew.class);
                startActivity(admin);
                finish();

            }

            if (pref.getLogin().equals("admin")) {


                // Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.",Toast.LENGTH_SHORT).show();

                Intent admin = new Intent(homewall.this, MainActivity.class);
                startActivity(admin);
                finish();

            } else if (pref.getLogin().equals("teacher")) {
                Intent zebra = new Intent(homewall.this, MainActivityTeacher.class);

                startActivity(zebra);
                finish();

            }


        }


    }

    public void mybg(View v) {
        Intent intent = new Intent(homewall.this, home.class);

        startActivity(intent);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }
}
