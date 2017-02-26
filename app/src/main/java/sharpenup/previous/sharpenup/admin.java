package sharpenup.previous.sharpenup;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.R;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class admin extends Activity {
    private static final int SWIPE_DURATION = 250;
    private static final int MOVE_DURATION = 150;
    private static PrefManager pref;
    String itemValue;
    JSONObject json1;
    Button handle;
    String reset_code;
    Button mode;
    Button nature;
    Button common_details;
    Button atb;
    Button centre;
    Button person;
    Button search;
    ListView lv1;
    String HANDLED;
    Dialog dialogStudName;
    List<String> pend;
    EditText stud_edit;
    JSONParser jParser1 = new JSONParser();
    StableArrayAdapter mAdapter;
    ListView mListView;
    BackgroundContainer mBackgroundContainer;
    boolean mSwiping = false;
    boolean mItemPressed = false;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
    Dialog dialogReg;
    Spinner spinner_reset;
    JSONParserNew jParser = new JSONParserNew();

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        getActionBar().hide();
        pref = new PrefManager(getApplicationContext());

        handle = (Button) findViewById(R.id.handle_me);


        pend = new ArrayList<String>();

 /*       
Button    logout = (Button) findViewById(R.id.logout2);




        
        
        logout.setOnClickListener(new View.OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				
        				pref.logout();
        				Intent intent = new Intent(admin.this, LoginData.class);
        				
        				startActivity(intent);
        				
        			}
        		});
        */


        handle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                itemValue = "handle_by";
                //myDialog();

                Intent intent = new Intent(admin.this, delete.class);
                intent.putExtra("key", "handle_by");
                startActivity(intent);
                finish();

            }
        });


        mode = (Button) findViewById(R.id.mode_me);


        mode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(admin.this, delete.class);
                intent.putExtra("key", "mode");
                startActivity(intent);
                finish();

            }
        });


        nature = (Button) findViewById(R.id.nature_me);


        nature.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(admin.this, delete.class);
                intent.putExtra("key", "nature");
                startActivity(intent);
                finish();

            }
        });


        common_details = (Button) findViewById(R.id.common_me);


        common_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(admin.this, delete.class);
                intent.putExtra("key", "common_detail");
                startActivity(intent);
                finish();

            }
        });


        atb = (Button) findViewById(R.id.atb_me);


        atb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(admin.this, delete.class);
                intent.putExtra("key", "action_to_action");
                startActivity(intent);
                finish();
            }
        });

        person = (Button) findViewById(R.id.personres_me);


        person.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(admin.this, delete.class);
                intent.putExtra("key", "person_res");
                startActivity(intent);
                finish();

            }
        });

        Button reset;
        reset = (Button) findViewById(R.id.reset);


        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new searching().execute();


            }
        });


        Button search = (Button) findViewById(R.id.search_me);


        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(admin.this, SearchActivity.class);
                intent.putExtra("back", "admin");

                startActivity(intent);
                finish();

            }
        });


    }

    protected void resetDialog() {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(admin.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.reset);

        Button register = (Button) dialogReg.findViewById(R.id.reset_sub);

        spinner_reset = (Spinner) dialogReg.findViewById(R.id.spinner_reset);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter20 = new ArrayAdapter<String>(admin.this, android.R.layout.simple_spinner_item, pend);

        // Drop down layout style - list view with radio button
        dataAdapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_reset.setAdapter(dataAdapter20);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // reset_des= ((EditText)dialogReg.findViewById(R.id.reset_des)).getText().toString();
                reset_code = ((EditText) dialogReg.findViewById(R.id.reset_code)).getText().toString();


                new reset().execute();


                dialogReg.dismiss();

            	/*Intent refresh = new Intent(pend.this,pend.class);
            startActivity(refresh);
            finish();*/

            }
        });

        dialogReg.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem bedMenuItem = menu.findItem(R.id.pendadmin);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.pendadmin) {

            Intent ipend = new Intent(admin.this, Pendings.class);
            ipend.putExtra("back", "admin");
            startActivity(ipend);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class reset extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(admin.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("handle_by", spinner_reset.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("reset", reset_code));

            String url = "http://176.32.230.250/anshuli.com/sharpenup/reset.php";


            JSONObject json = jParser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();


        }

    }

    private class searching extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(admin.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("itemvaluenew", "handle_by"));

            String url = "http://176.32.230.250/anshuli.com/sharpenup/listview.php";


            json1 = jParser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray jsonArray;

            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json1.getJSONArray("list");
                for (int i = 0; i < account.length(); i++) {
                    json1 = account.getJSONObject(i);


		        	 	 /*String S_NO= json.getString("S_No");
                        String NAME= json.getString("NAME");
		        	 	String PARENT= json.getString("PARENT");
		        	 	String PHONE= json.getString("PHONE");*/

                    HANDLED = json1.getString("handle_by");


                    if (HANDLED != null && !HANDLED.equals(""))
                        pend.add(HANDLED);



		        	 	/*categories_handle.add(handled_1);
		        	 	categories_mode.add(mode_1);
		        	 	categories_nature.add(nature_1);
		        	 	categories_details.add(detail_1);
		        	 	categories_person_res.add(person_res_1);
		        	 	categories_action_taken.add(action_taken_1);*/
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            pDialog.dismiss();
            resetDialog();

        }

    }


}

  
