package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ObserveToolEntry extends Activity {
    TextView reason, remarks, remarks_by;
    Spinner batch;
    Spinner student;
    Spinner cls;
    Spinner centre;
    List<String> batch_;
    List<String> cls_;
    List<String> centre_;
    List<String> student_;
    List<String> bachoKNaam;
    Context ctx = ObserveToolEntry.this;
    JSONParser jparser = new JSONParser();

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation_entry);
        batch_ = new ArrayList<String>();
        cls_ = new ArrayList<String>();
        centre_ = new ArrayList<String>();
        student_ = new ArrayList<String>();
        getActionBar().hide();

        bachoKNaam = new ArrayList<String>();
        student = (Spinner) findViewById(R.id.studentName);
        batch = (Spinner) findViewById(R.id.batchreg);
        cls = (Spinner) findViewById(R.id.cls);
        centre = (Spinner) findViewById(R.id.centre);
        reason = (TextView) findViewById(R.id.reason);
        remarks = (TextView) findViewById(R.id.remarks);
        remarks_by = (TextView) findViewById(R.id.remarksBy);

        batch_.add("A");
        batch_.add("B");
        batch_.add("C");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterbatch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, batch_);

        // Drop down layout style - list view with radio button
        dataAdapterbatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        batch.setAdapter(dataAdapterbatch);


        cls_.add("Junior");
        cls_.add("6th");
        cls_.add("7th");
        cls_.add("8th");
        cls_.add("9th");
        cls_.add("10th");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterclass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cls_);

        // Drop down layout style - list view with radio button
        dataAdapterclass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        cls.setAdapter(dataAdapterclass);


        //-----------------------------------------------------------------------------------------


        centre_.add("DDN");
        centre_.add("RT");
        centre_.add("CC");
        centre_.add("PB");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdaptercentre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, centre_);

        // Drop down layout style - list view with radio button
        dataAdaptercentre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        centre.setAdapter(dataAdaptercentre);

        student.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    new getStudentsNameValuePair().execute();


                }
                return false;
            }
        });


    }

    public void submit(View view) {
        if (emptychecker())
            new submitObservation().execute();
        else
            Toast.makeText(ObserveToolEntry.this, "Fields cannot be empty!!!", Toast.LENGTH_LONG).show();
    }

    public boolean emptychecker() {
        boolean bl = true;
        if (reason.getText().toString().equals("")) {
            bl = false;
            //Toast.makeText(ObserveToolEntry.this, "Reason cannot be empty!!!", Toast.LENGTH_LONG).show();
        }
        if (remarks.getText().toString().equals("")) {
            bl = false;
            // Toast.makeText(ObserveToolEntry.this, "Remarks cannot be empty!!!", Toast.LENGTH_LONG).show();
        }
        if (remarks_by.getText().toString().equals("")) {
            bl = false;
            //Toast.makeText(ObserveToolEntry.this, "Remarks by cannot be empty!!!", Toast.LENGTH_LONG).show();
        }


        return bl;
    }

    public void listViewSetting(List<String> list) {
        ArrayAdapter<String> bache = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        bache.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        student.setAdapter(bache);

    }

    private class getStudentsNameValuePair extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ObserveToolEntry.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("batch", batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", centre.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", cls.getSelectedItem().toString()));

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
                listViewSetting(bachoKNaam);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ObserveToolEntry.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }

    }

    private class submitObservation extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ObserveToolEntry.this, SweetAlertDialog.PROGRESS_TYPE)
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
            String parts[] = student.getSelectedItem().toString().split("-");
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("batch", batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("name", parts[1]));
            params.add(new BasicNameValuePair("class", cls.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", centre.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("reason", reason.getText().toString()));
            params.add(new BasicNameValuePair("remarks", remarks.getText().toString()));
            params.add(new BasicNameValuePair("remarks_by", remarks_by.getText().toString()));
            params.add(new BasicNameValuePair("roll", parts[0]));
            String url = "http://176.32.230.250/anshuli.com/sharpenup/Obser_Tool.php";

            jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String result) {
            reason.setText("");
            remarks.setText("");
            remarks_by.setText("");

            pDialog.setTitleText("Sucess!!")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


        }
    }

}
