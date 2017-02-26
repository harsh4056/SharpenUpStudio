package sharpenup.customgridview;


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


@SuppressWarnings("deprecation")
public class LoginActivity extends Activity {
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
        setContentView(R.layout.login);
        getActionBar().hide();


        pref = new PrefManager(getApplicationContext());
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(LoginActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        if (pref.getLogin() != null) {
           /*Intent reload = new Intent(LoginActivity.this, LoginActivity.class);
			startActivity(reload);
			finish();*/

            if (pref.getLogin().equals("admin")) {


                // Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.",Toast.LENGTH_SHORT).show();

                Intent admin = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(admin);
                finish();

            } else if (pref.getLogin().equals("teacher")) {
                Intent zebra = new Intent(LoginActivity.this, MainActivityTeacher.class);

                startActivity(zebra);
                finish();

            }

        }


        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);


        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                username = user.getText().toString();
                password = pass.getText().toString();


                new login().execute();
					
					/*if(username.equals("admin") && password.equals("admin"))
					{
					
					Intent admin = new Intent(LoginActivity.this, admin.class);
					
					startActivity(admin);
					finish();
					}
					
					if(username.equals("user") && password.equals("user"))
						{
						Intent zebra = new Intent(LoginActivity.this, buttons.class);
						
						startActivity(zebra);
						finish();
						
					}
					*/


            }
        });


    }


    private class login extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");


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


            String log = "http://176.32.230.250/anshuli.com/sharpenup/login.php";


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

                    userget = json.getString("handle_by");
                    passget = json.getString("password");
                    admin = json.getString("ADMIN");


                }

                if (username.equals(userget) && password.equals(passget)) {

                    if (username.equals(userget) && password.equals(passget) && admin.equals("1")) {
                        Toast.makeText(getApplicationContext(), "You are succesfully logged in as ADMIN.", Toast.LENGTH_SHORT).show();

                        Intent admin = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(admin);
                        finish();
                        pref.setLogin("admin");


                    } else {
                        Intent zebra = new Intent(LoginActivity.this, MainActivityTeacher.class);

                        startActivity(zebra);
                        overridePendingTransition(R.anim.open_next, R.anim.close_main);
                        finish();
                        Toast.makeText(getApplicationContext(), "You are succesfully logged in.", Toast.LENGTH_SHORT).show();
                        pref.setLogin("teacher");
                        pref.setTeacher(username);
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Login failed:Check your credentials.", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
                pDialog.setTitleText("Network connection Error!!!!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

            }


        }
    }


   /* @Override
    public void onBackPressed()
    {
   	 
   	 
    	Intent intent = new Intent(this, MainActivityTeacher.class);
        startActivity(intent); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
        finish();
   	 
    }

*/


}
