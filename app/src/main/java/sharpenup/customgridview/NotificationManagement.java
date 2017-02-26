package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressWarnings("deprecation")
public class NotificationManagement extends Activity {


    JSONParser jparser = new JSONParser();
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> bachoKNaam;
    EditText heading, message;
    ListView lv1;
    Button b1;
    int myVariable;
    String roll = "";
    Context ctx = NotificationManagement.this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        myVariable = 0;
        spinner_center = (Spinner) findViewById(R.id.cent);
        bachoKNaam = new ArrayList<String>();
        heading = (EditText) findViewById(R.id.heading);
        message = (EditText) findViewById(R.id.message);
        categories_center = new ArrayList<String>();
        categories_center.add("");
        categories_center.add("PB");
        categories_center.add("CC");
        categories_center.add("RT");
        categories_center.add("DDN");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterB = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_center);

        // Drop down layout style - list view with radio button
        dataAdapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_center.setAdapter(dataAdapterB);


        spinner_batch = (Spinner) findViewById(R.id.batch);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_batch = new ArrayList<String>();
        categories_batch.add("");
        categories_batch.add("A");
        categories_batch.add("B");
        categories_batch.add("C");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_batch);

        // Drop down layout style - list view with radio button
        dataAdapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_batch.setAdapter(dataAdapterC);

        spinner_class = (Spinner) findViewById(R.id.cls);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_class = new ArrayList<String>();
        categories_class.add("");
        categories_class.add("Junior");
        categories_class.add("6th");
        categories_class.add("7th");
        categories_class.add("8th");
        categories_class.add("9th");
        categories_class.add("10th");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_class);

        // Drop down layout style - list view with radio button
        dataAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_class.setAdapter(dataAdapterA);

    }

    public void notifyAll(View v) {
        new time().execute();

    }

    public void notifyStudent(View v) {
        new getStudentsNameValuePair().execute();


    }

    public void listViewSetting() {
        final Dialog dialogReg = new Dialog(ctx);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.studentworkbook);

        b1 = (Button) dialogReg.findViewById(R.id.button1);
        b1.setText("Notify Student");
        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv1.setTextFilterEnabled(true);


        b1.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {


                String parts[] = lv1.getItemAtPosition(lv1.getCheckedItemPosition()).toString().split("-");
                roll = parts[0];
                new time().execute();

                dialogReg.dismiss();
            }
        });
        viewall(bachoKNaam);

        dialogReg.show();
    }

    private void viewall(List<String> dsip_Students) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_checked, dsip_Students);

        lv1.setTextFilterEnabled(true);
        lv1.setAdapter(dataAdapter);


        // ListView Item Click Listener

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ctx, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
        NotificationManagement.this.finish();
    }

    private class getStudentsNameValuePair extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(NotificationManagement.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");
        JSONObject json;


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
            //params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/studentsview.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();


            try {
                // Checking for SUCCESS TAG

                bachoKNaam.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String NAME = json.getString("NAME");
                    String Roll = json.getString("ROLL");

                    bachoKNaam.add(Roll + "-" + NAME);
                    //categories_description.add(description);


                }
                listViewSetting();


            } catch (Exception e) {
                e.printStackTrace();
                pDialog.setTitleText("Oops....")
                        .setContentText(e.getMessage())
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

            }


        }

    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(NotificationManagement.this, SweetAlertDialog.PROGRESS_TYPE)
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


            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("message", message.getText().toString()));
            params.add(new BasicNameValuePair("heading", heading.getText().toString()));
            params.add(new BasicNameValuePair("serial", roll));
            String url = "http://176.32.230.250/anshuli.com/sharpenup3/setMessageNotify.php";

            jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String result) {
            message.setText("");
            heading.setText("");

            roll = "";
            pDialog.setTitleText("Notifications Dispatched")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

	 

	      
	  
	      /*  Intent time = new Intent(cnv.this,Main.class);
			startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
			finish();*/


        }

    }


}
