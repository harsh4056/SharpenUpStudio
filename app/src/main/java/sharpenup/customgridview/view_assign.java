package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


@SuppressWarnings("deprecation")
public class view_assign extends Activity {

    TableLayout table_layout;
    ListView lv1;
    List<String> categories_pedsrno;
    List<String> categories_pendact;
    List<String> categories_penddate;
    List<String> categories_pendper;
    List<String> pendings;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_date;
    List<String> categories_topic;
    List<String[]> dataContainer;
    UniversalTableLayout tableKaHandle;
    /*TableLayout tl;
    TableRow tr;
    TextView companyTV,valueTV;*/
    //private PrefManager pref;
    Dialog dialogReg;
    String TOPIC;
    String CENTER;
    String DATE;
    String BATCH;
    String SERIAL;
    String CLASS;
    String FACULTY;
    String SUBJECT;
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
        setContentView(R.layout.view_assign);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        String url = "http://176.32.230.250/anshuli.com/sharpenup/view_assign.php";
        new pendings().execute(url);

        //Intent intent = getIntent();

        // back_flag = intent.getStringExtra("back");

        categories_batch = new ArrayList<String>();
        categories_class = new ArrayList<String>();
        categories_center = new ArrayList<String>();
        categories_subject = new ArrayList<String>();
        categories_date = new ArrayList<String>();
        categories_topic = new ArrayList<String>();

        dataContainer = new ArrayList<String[]>();


        categories_pedsrno = new ArrayList<String>();
        pendings = new ArrayList<String>();
        categories_pendact = new ArrayList<String>();
        categories_penddate = new ArrayList<String>();
        categories_pendper = new ArrayList<String>();
        lv1 = (ListView) findViewById(R.id.listView2);


    }

    protected void completeActionDialog(final String number) {
    }

    private class pendings extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(view_assign.this, SweetAlertDialog.PROGRESS_TYPE)
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
            //visible
            return GET(urls[0]);


        }

        // onPostExecute displays the results of the AsyncTask.
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String result) {  //gone
            // //System.out.println("RESULT : " + result);

            JSONObject json;
            try {
                // Checking for SUCCESS TAG


                json = new JSONObject(result);
                JSONArray account = json.getJSONArray("pendings");


                //pref.setEvent(null);
                //Toast.makeText(Pendings.this, " something", 1).show();


                categories_batch.add("BATCH");
                categories_center.add("CENTER");
                categories_class.add("CLASS");
                categories_subject.add("SUBJECT");
                categories_topic.add("TOPIC");
                categories_date.add("DUE DATE");

                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    SERIAL = json.getString("S.No");
                    CENTER = json.getString("CENTER");
                    BATCH = json.getString("BATCH");
                    DATE = json.getString("DATE");
                    CLASS = json.getString("CLASS");
                    FACULTY = json.getString("FACULTY");
                    SUBJECT = json.getString("SUBJECT");
                    TOPIC = json.getString("TOPIC");

				        		/* time= time + json.getString("Time");
                                 others= others + json.getString("Others");
				        		 */

                    if (pref.getTeacher().equals(FACULTY)) {
                        categories_batch.add(BATCH);
                        categories_center.add(CENTER);
                        categories_class.add(CLASS);
                        categories_subject.add(SUBJECT);
                        categories_topic.add(TOPIC);
                        categories_date.add(DATE);


                    }
                    String flag = "1";

                    pref.setEvent(flag);


                }


                dataContainer.clear();
                dataContainer.add(categories_center.toArray(new String[categories_center.size()]));
                dataContainer.add(categories_batch.toArray(new String[categories_batch.size()]));
                dataContainer.add(categories_class.toArray(new String[categories_class.size()]));
                dataContainer.add(categories_subject.toArray(new String[categories_subject.size()]));
                dataContainer.add(categories_topic.toArray(new String[categories_topic.size()]));
                dataContainer.add(categories_date.toArray(new String[categories_date.size()]));
                tableKaHandle = new UniversalTableLayout(dataContainer, view_assign.this);
                tableKaHandle.dialogReg.setOnDismissListener(new OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        view_assign.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        view_assign.this.finish();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

            pDialog.dismiss();

        }


        private void viewall() {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view_assign.this, android.R.layout.simple_list_item_1, pendings);
            lv1.setAdapter(dataAdapter);

            // ListView Item Click Listener
            lv1.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index


                    // ListView Clicked item value
                    String itemValue = (String) lv1.getItemAtPosition(position);

                    String[] parts = itemValue.split(" ");

                    String number = parts[3];
                    completeActionDialog(number);
                    // Show Alert
            /* Toast.makeText(getApplicationContext(),
               "Position :"+itemPosition+"  ListItem : " +parts[3] , Toast.LENGTH_SHORT)
               .show();
          */
                }

            });

        }

        public String GET(String url) {
            InputStream inputStream = null;
            String result = "";
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                //  Toast.makeText(MainActivity, e.getLocalizedMessage(), 1000).show();
            }
            return result;
        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }


    }


}

  
