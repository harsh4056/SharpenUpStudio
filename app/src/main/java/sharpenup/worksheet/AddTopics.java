package sharpenup.worksheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.R;


@SuppressWarnings("deprecation")
public class AddTopics extends Activity {

    JSONParser jparser = new JSONParser();
    Spinner spinner_subject;
    List<String> categories_subject;
    Spinner spinner_class;
    List<String> categories_class;
    String t;
    String topics;
    String id;
    EditText et;
    EditText tv;
    List<String> forjson;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addingtopics);
        {
            topics = "";
            id = "";

            et = (EditText) findViewById(R.id.edit_topic);


            tv = (EditText) findViewById(R.id.disp_topics);


            spinner_subject = (Spinner) findViewById(R.id.sub);

            // Spinner click listener
            //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

            // Spinner Drop down elements
            categories_subject = new ArrayList<String>();
            categories_subject.add("SELECT SUBJECT");
            categories_subject.add("MATHLETICS");
            categories_subject.add("SCI-FI");
            categories_subject.add("SOCIALITY");
            categories_subject.add("ENGLISH");
            categories_subject.add("OTHER");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter_sub = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_subject);

            // Drop down layout style - list view with radio button
            dataAdapter_sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_subject.setAdapter(dataAdapter_sub);


            spinner_class = (Spinner) findViewById(R.id.cls);

            // Spinner click listener
            //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

            // Spinner Drop down elements
            categories_class = new ArrayList<String>();
            categories_class.add("SELECT CLASS");
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

            tv.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    tv.setText("");
                                    t = "";
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddTopics.this);
                    builder.setMessage("Are you sure you want to clear text?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    return true;
                }
            });


        }


    }

    public void showTopics(View v) {
        new studentsview().execute();
    }

    public void addToText(View v) {

        if (et.getText().toString().equals(""))
            Toast.makeText(AddTopics.this, "Well! You must enter something first !", Toast.LENGTH_SHORT).show();

        else {

            if (t.equals(""))
                t = et.getText().toString();
            else
                t = t + "," + et.getText().toString();
            tv.setText(t);
            et.setText("");
        }

    }

    public void submitToDb(View v) {

        new SweetAlertDialog(AddTopics.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure you want to submit these topics?")

                .setConfirmText("Yes,do it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        new time().execute();
                        sDialog.dismiss();
                    }
                })
                .show();


    }


    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(AddTopics.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("subject", spinner_subject.getSelectedItem().toString()));
            //params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/gettopics.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            //JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            //JSONArray jsonArray;

            try {
                // Checking for SUCCESS TAG

                //forjson.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    topics = json.getString("TOPIC");
                    id = json.getString("S_NO");

                    // forjson.add(Roll+"-"+ NAME);
                    //categories_description.add(description);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            tv.setText(topics);
            t = topics;

        }

    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(AddTopics.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("topic", tv.getText().toString()));
            params.add(new BasicNameValuePair("id", id));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/settopics.php";


            jparser.makeHttpRequest(url, "POST", params);


            //	JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.setTitleText("Sucess!")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

	      /*  Intent time = new Intent(cnv.this,Main.class);
			startActivity(time);
			finish();*/


        }

    }

}
