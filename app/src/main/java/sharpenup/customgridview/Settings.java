package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressWarnings("deprecation")
public class Settings extends Activity {
    Dialog dialogReg;
    String reset_code;
    JSONParser jparser = new JSONParser();
    JSONObject json;
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
        setContentView(R.layout.settings);

        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());


    }

    public void cgpwd(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(Settings.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        resetDialog();

    }

    public void cguser(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(Settings.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        usernameDialog();

    }

    protected void resetDialog() {

        dialogReg = new Dialog(Settings.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.reset_pass);

        Button register = (Button) dialogReg.findViewById(R.id.reset_pass_sub);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String oldPwd, newPwd, cnfPwd;
                oldPwd = ((EditText) dialogReg.findViewById(R.id.oldPass)).getText().toString();
                newPwd = ((EditText) dialogReg.findViewById(R.id.newPass)).getText().toString();
                cnfPwd = ((EditText) dialogReg.findViewById(R.id.cnfNewPass)).getText().toString();

                if (oldPwd.equals(pref.getPassword())) {
                    if (newPwd.equals(cnfPwd)) {
                        if (newPwd.length() >= 8) {
                            reset_code = newPwd;
                            new reset().execute();
                            dialogReg.dismiss();

                        } else
                            Toast.makeText(getBaseContext(), "Password should be more than 8 characters!!!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getBaseContext(), "Passwords dont match!!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getBaseContext(), "The password entered does not match the old password!!!", Toast.LENGTH_SHORT).show();




            	/*Intent refresh = new Intent(pend.this,pend.class);
            startActivity(refresh);
            finish();*/

            }
        });

        dialogReg.show();
    }

    protected void usernameDialog() {

        dialogReg = new Dialog(Settings.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.user_reset);

        Button register = (Button) dialogReg.findViewById(R.id.reset_user_sub);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String oldPwd = ((EditText) dialogReg.findViewById(R.id.password)).getText().toString();
                if (oldPwd.equals(pref.getPassword())) {

                    reset_code = ((EditText) dialogReg.findViewById(R.id.reset_username)).getText().toString();
                    new resetUser().execute();
                    dialogReg.dismiss();
                } else
                    Toast.makeText(getBaseContext(), "The password entered does not match the old password!!!", Toast.LENGTH_SHORT).show();






            	/*Intent refresh = new Intent(pend.this,pend.class);
            startActivity(refresh);
            finish();*/

            }
        });

        dialogReg.show();
    }

    private class reset extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(Settings.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("password", pref.getPassword()));
            params.add(new BasicNameValuePair("reset", reset_code));
            params.add(new BasicNameValuePair("roll", pref.getRoll()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/reset_pass.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            pref.setPassword(reset_code);

            pDialog.setTitleText("Your password succesfully changed!")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


        }

    }

    private class resetUser extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(Settings.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("username", pref.getUsername()));
            params.add(new BasicNameValuePair("reset", reset_code));
            params.add(new BasicNameValuePair("roll", pref.getRoll()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/reset_user.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            pref.setUsername(reset_code);

            try {
                String account = json.getString("pendings");


                if (account.equals("SUCCESS"))

                {
                    pDialog.setTitleText("Credentials sucessfully changed!")
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                } else {
                    pDialog.setTitleText("Username already exists..")
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                pDialog.setTitleText("Oops....")
                        .setContentText("Something went wrong!!! PLease Retry!!!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }

        }

    }


}
