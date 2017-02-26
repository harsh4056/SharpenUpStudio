package sharpenup.previous.sharpenup;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import sharpenup.customgridview.R;


public class Pendings extends Activity {
    TableLayout table_layout;
    ListView lv1;
    List<String> categories_pedsrno;
    List<String> categories_pendact;
    List<String> categories_penddate;
    List<String> categories_pendper;
    List<String> pendings;
    Dialog dialogReg;
    /*TableLayout tl;
    TableRow tr;
    TextView companyTV,valueTV;*/
    String NAME;
    String ACTION_TAKEN;
    String DATE;
    String PERSON_RESPONSIBLE;
    String SERIAL;
    String complete_action;
    String act_taken_by;
    String back_flag;
    String companies[] = {"Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung"};
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendings);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        String url = "http://176.32.230.250/anshuli.com/sharpenup/pendings.php";
        new pendings().execute(url);

        Intent intent = getIntent();

        back_flag = intent.getStringExtra("back");


        categories_pedsrno = new ArrayList<String>();
        pendings = new ArrayList<String>();
        categories_pendact = new ArrayList<String>();
        categories_penddate = new ArrayList<String>();
        categories_pendper = new ArrayList<String>();
        lv1 = (ListView) findViewById(R.id.listView1);


    }

    protected void completeActionDialog(final String number) {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(Pendings.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.action_taken_dia);

        Button register = (Button) dialogReg.findViewById(R.id.button1);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                complete_action = ((EditText) dialogReg.findViewById(R.id.editText1)).getText().toString();
                act_taken_by = ((EditText) dialogReg.findViewById(R.id.act_taken_by)).getText().toString();

                completeParse userinfo = new completeParse(getBaseContext(),
                        complete_action,
                        number, act_taken_by
                );


                userinfo.execute();


                dialogReg.dismiss();

                Intent refresh = new Intent(Pendings.this, Pendings.class);
                startActivity(refresh);


            }
        });

        dialogReg.show();
    }

    @Override
    public void onBackPressed() {

        if (back_flag.equals("button")) {
            Intent intent = new Intent(Pendings.this, buttons.class);
            startActivity(intent);
            finish();
        } else if (back_flag.equals("admin"))

        {
            Intent intent = new Intent(Pendings.this, admin.class);
            startActivity(intent);
            finish();


        }
    }

    private class pendings extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Pendings.this, SweetAlertDialog.PROGRESS_TYPE)
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
            JSONArray jsonArray;
            JSONObject json;
            try {
                // Checking for SUCCESS TAG


                json = new JSONObject(result);
                JSONArray account = json.getJSONArray("pendings");


                pref.setEvent(null);
                //Toast.makeText(Pendings.this, " something", 1).show();


                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    SERIAL = json.getString("S_No");
                    NAME = json.getString("NAME");
                    ACTION_TAKEN = json.getString("ACTION_TAKEN");
                    DATE = json.getString("DATE");
                    PERSON_RESPONSIBLE = json.getString("PERSON_RESPONSIBLE");

				        		/* time= time + json.getString("Time");
                                 others= others + json.getString("Others");
				        		 */
                    pendings.add(" Serial no.: " + SERIAL + " " + " \r\nName : " + NAME + " \r\n Action to be taken: " + ACTION_TAKEN + "\r\n Should be done by date: " + DATE + "\r\n Person responsible :" + PERSON_RESPONSIBLE);
                    categories_pendact.add(ACTION_TAKEN);
                    categories_penddate.add(DATE);
                    categories_pendper.add(PERSON_RESPONSIBLE);

                    // DataHolderClass.getInstance().setDistributor_id(ACTION_TAKEN);


                    String flag = "1";

                    pref.setEvent(flag);


                }


                viewall();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            pDialog.dismiss();

        }


        private void viewall() {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Pendings.this, android.R.layout.simple_list_item_1, pendings);
            lv1.setAdapter(dataAdapter);

            // ListView Item Click Listener
            lv1.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

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

  
