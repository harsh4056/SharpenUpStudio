

package sharpenup.customgridview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class webview extends Activity {

    Button b1;
    EditText ed1;
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_top;
    Spinner spinner_subject;
    String id;
    TextView date_pend_up;
    TextView date_pend_down;
    List<String> categories_code;
    List<String> all_students;
    List<String> categories_description;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_top;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String present;
    String absent;
    String[] split;
    String leave;
    TextView eReminderTime;
    Calendar cal;
    private Calendar calendar;
    //private TextView date_pend_down,date_pend_up,date_log_down,date_log_up;
    private int year, month, day;
    private WebView wv1;
    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {

            if (spinner_subject.getSelectedItem().toString() != null &&
                    !spinner_subject.getSelectedItem().toString().equals("SELECT SUBJECT"))

                new subject().execute();

            else {
                Toast.makeText(webview.this, "Please select subject... ", 1).show();
            }
            // ListView Clicked item index
            int itemPosition = position;

            // ListView Clicked item value
            String itemValue = (String) spinner_class.getItemAtPosition(position);


            // spinnerDialog(itemValue);


            // Show Alert
           /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {


            arg2 = arg2 + 1;
            cal = Calendar.getInstance();
            cal.set(arg1, arg2, arg3);


            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            String strm = "";
            String strd = "";

            strm = appending0(arg2);
            strd = appending0(arg3);
                 /* if (flag==0)
		         showDate0(arg1, strm, strd);
		    	  else if  (flag==1)*/
            showDate1(arg1, strm, strd);
            cal = new GregorianCalendar(arg1, Calendar.MONTH, arg3);
		    	  /*else  if (flag==2)
		 	         showDate2(arg1, strm, strd);
		    	  else if (flag==3)
		 	         showDate3(arg1, strm, strd);*/
        }
    };

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        // getActionBar().hide();
        all_students = new ArrayList<String>();
        categories_code = new ArrayList<String>();
        //send =new ArrayList<String>();
        present = "";
        absent = "";
        leave = "";
        categories_description = new ArrayList<String>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        //date_pend_down = (TextView) findViewById(R.id.textpdh_test);
        date_pend_up = (TextView) findViewById(R.id.textpdh_upload);

        //  showDate0(year, appending0(month+1),appending0( day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */

        spinner_center = (Spinner) findViewById(R.id.cent_upload);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_center = new ArrayList<String>();
        categories_center.add("SELECT CENTER");
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


	        /*if(spinner_center != null)
	        {

	        	new studentsview().execute();

	        }
	        */


        spinner_top = (Spinner) findViewById(R.id.top_upload);
        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_top = new ArrayList<String>();
        categories_top.add("SELECT TOPIC");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_top = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_top);

        // Drop down layout style - list view with radio button
        dataAdapter_top.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_top.setAdapter(dataAdapter_top);


        spinner_subject = (Spinner) findViewById(R.id.sub_upload);

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

        //subject.setAdapter(dataAdapter_class);
        //spinner_nature.setAdapter(dataAdapter2);
        int position = 0;
        spinner_subject.setSelection(position, false);

        spinner_subject.setOnItemSelectedListener(itemSelectedListener);


        spinner_batch = (Spinner) findViewById(R.id.batch_upload);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_batch = new ArrayList<String>();
        categories_batch.add("SELECT BATCH");
        categories_batch.add("A");
        categories_batch.add("B");
        categories_batch.add("C");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_batch);

        // Drop down layout style - list view with radio button
        dataAdapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_batch.setAdapter(dataAdapterC);


        //  spinner_class = (Spinner) findViewById(R.id.center);


        spinner_class = (Spinner) findViewById(R.id.cls_upload);

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
        ArrayAdapter<String> dataAdapter_class = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_class);

        // Drop down layout style - list view with radio button
        dataAdapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        spinner_class.setAdapter(dataAdapter_class);


        wv1 = (WebView) findViewById(R.id.wv);
        wv1.setWebViewClient(new MyBrowser());
        b1 = (Button) findViewById(R.id.submit_upload);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String class_add = spinner_class.getSelectedItem().toString();
                String batch_add = spinner_batch.getSelectedItem().toString();
                String center_add = spinner_center.getSelectedItem().toString();
                String sub_add = spinner_subject.getSelectedItem().toString();
                String top_add = spinner_top.getSelectedItem().toString();
                String url = "http://176.32.230.250/anshuli.com/sharpenup2/download.php?class=" + class_add
                        + "&batch=" + batch_add
                        + "&center=" + center_add
                        + "&subject=" + sub_add
                        + "&topic=" + top_add;
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });


        wv1.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
            }
        });


    }

    public boolean universalChecker() {
        boolean flag = true;

        if (spinner_batch.getSelectedItem().toString().equals("SELECT BATCH"))
            flag = false;
        if (spinner_class.getSelectedItem().toString().equals("SELECT CLASS"))
            flag = false;
        if (spinner_center.getSelectedItem().toString().equals("SELECT CENTER"))
            flag = false;
        if (spinner_top.getSelectedItem().toString().equals("SELECT TOPIC"))
            flag = false;
        if (spinner_subject.getSelectedItem().toString().equals("SELECT SUBJECT"))
            flag = false;


        return flag;


    }

    //--------------------------------------------Date Time ranges-------------------------------------------
    @SuppressWarnings("deprecation")
    public void date_pend_low_range(View view) {
        showDialog(999);
        //   Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT).show();
        flag = 0;
    }

    @SuppressWarnings("deprecation")
    public void date_pend_high_range(View view) {
        showDialog(999);
        //  Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
        flag = 1;
    }

    /*@SuppressWarnings("deprecation")
      public void date_logged_low(View view) {
          showDialog(999);
          //Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
          flag=2;
       }
    @SuppressWarnings("deprecation")
      public void date_logged_high(View view) {
          showDialog(999);
         // Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
          flag=3;
       }
*/
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    public String appending0(int argument) {
        String s;

        if (argument < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append("0");
            sb.append(argument);
            s = sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append(argument);
            s = sb.toString();
        }


        return s;
    }

    private void showDate1(int year, String month, String day) {
        date_pend_up.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

    }

		 /*  private void showDate0(int year, String month, String day) {
			   date_pend_down.setText(new StringBuilder().append(year).append("-")
		      .append(month).append("-").append(day));
		   }*/

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class subject extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(webview.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
            //params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/gettopics.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            JSONArray jsonArray;

            try {
                // Checking for SUCCESS TAG

                categories_code.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String TOPIC = json.getString("TOPIC");
//			        	/String  Roll= json.getString("ROLL");

                    String[] split = TOPIC.split(",");
                    // categories_code.add(Roll+"-"+ NAME);
                    //categories_description.add(description);
                    List<String> temp = Arrays.asList(split);

                    categories_top.addAll(temp);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            // setlist();

        }
    }
		   /*private void showDate2(int year, String month, String day) {
			   date_log_down.setText(new StringBuilder().append(year).append("-")
					      .append(month).append("-").append(day));
		   }
		   private void showDate3(int year, String month, String day) {
			   date_log_up.setText(new StringBuilder().append(year).append("-")
					      .append(month).append("-").append(day));
		   }*/
    //-----------------------------------------------------------------------------------------------


}