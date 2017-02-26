package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class LoginActivity2 extends Activity {
    Button login;
    EditText user;
    String username;
    String password;
    EditText pass;
    JSONParser jParser5 = new JSONParser();
    String userget;
    String passget;
    String admin;
    JSONObject json;
    String flag;
    String roll;
    String center;
    String batch;
    String cls;
    String name;
    String serial;
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
        setContentView(R.layout.login_stud);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(LoginActivity2.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        if (pref.getLogin() != null) {
           /*Intent reload = new Intent(LoginActivity.this, LoginActivity.class);
			startActivity(reload);
			finish();*/

            if (pref.getLogin().equals("child")) {


                // Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.",Toast.LENGTH_SHORT).show();

                Intent admin = new Intent(LoginActivity2.this, MainActivityNew.class);
                startActivity(admin);
                finish();

            }


        }


        user = (EditText) findViewById(R.id.user_stud);
        pass = (EditText) findViewById(R.id.pass_stud);


        login = (Button) findViewById(R.id.login_stud);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                username = user.getText().toString();
                password = pass.getText().toString();


                new studentlogin().execute();


            }
        });


    }


    private class studentlogin extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity2.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");


        /** progress dialog to show user that the backup is processing. */
        /**
         * application context.
         */

        protected void onPreExecute() {
            pDialog.show();
            pDialog.setCancelable(false);
        }


        @Override
        protected String doInBackground(String... urls) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));


            String log = "http://176.32.230.250/anshuli.com/sharpenup2/login_stud.php";


            json = jParser5.makeHttpRequest(log, "POST", params);


            //visible
            return null;


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {  //gone
            // //System.out.println("RESULT : " + result);

            pDialog.dismiss();
            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    userget = json.getString("USERNAME");
                    passget = json.getString("PASSWORD");
                    //admin = json.getString("ADMIN");
                    flag = json.getString("NAME");
                    roll = json.getString("ROLL");
                    name = json.getString("NAME");
                    cls = json.getString("CLASS");
                    batch = json.getString("BATCH");
                    center = json.getString("CENTRE");


                }

                if (username.equals(userget) && password.equals(passget)) {


                    Intent student = new Intent(LoginActivity2.this, MainActivityNew.class);
                    //	Intent intent =new Intent(UserLogin.this,User.class);


                    pref.setBatch(batch);
                    pref.setCenter(center);
                    pref.setClass(cls);
                    pref.setRoll(roll);
                    pref.setName(name);
                    pref.setPassword(password);
                    pref.setUsername(username);


                    startActivity(student);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);
                    finish();
                    Toast.makeText(getApplicationContext(), "You are succesfully logged in.", Toast.LENGTH_SHORT).show();
                    pref.setLogin("child");
							/*int i = 300;
							Intent intent = new Intent(LoginActivity2.this, MyBroadcastReceiver.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							PendingIntent pendingIntent = PendingIntent.getBroadcast(
									getBaseContext(), 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
							 AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
							alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+ (i * 1000), pendingIntent);
						//	Toast.makeText(LoginActivity2.this, "Periodic service invoker set for " + i + " seconds!",Toast.LENGTH_LONG).show();
						*/

                } else {

                    Toast.makeText(getApplicationContext(), "Login failed:Check your credentials.", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Network Connection Error!!!", Toast.LENGTH_SHORT).show();
            }


        }
    }


}
