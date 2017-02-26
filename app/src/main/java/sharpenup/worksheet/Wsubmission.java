package sharpenup.worksheet;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nonactivity.javafunctionality.classes.StaticMethods;
import sharpenup.customgridview.R;

@SuppressWarnings("deprecation")
public class Wsubmission extends Activity {

    Spinner spinner_topics;
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_fac;
    Spinner spinner_subject;
    TextView date_pend_up;
    TextView date_pend_down;
    List<String> categories_code;
    List<String> categories_description;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_faculty;
    List<String> categories_topics;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String send;
    String topics;
    private Calendar calendar;
    //private TextView date_pend_down,date_pend_up,date_log_down,date_log_up;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            arg2 = arg2 + 1;
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            String strm = "";
            String strd = "";

            strm = appending0(arg2);
            strd = appending0(arg3);
            if (flag == 0)
                showDate0(arg1, strm, strd);
            else if (flag == 1)
                showDate1(arg1, strm, strd);
                  /*else  if (flag==2)
		 	         showDate2(arg1, strm, strd);
		    	  else if (flag==3)
		 	         showDate3(arg1, strm, strd);*/
        }
    };
//	int flag,flag_btn_click;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
    //-----------------------------------------------settting method ends--------------------------------------------------------------------

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override


    //-----------------------------------------------settting method--------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wsubmission);
        getActionBar().hide();
        categories_code = new ArrayList<String>();
        //send =new ArrayList<String>();
        send = "";
        categories_description = new ArrayList<String>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        date_pend_up = (TextView) findViewById(R.id.textpdhsub);

        // showDate0(year, appending0(month+1),appending0( day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */
        spinner_topics = (Spinner) findViewById(R.id.topicsub);

        categories_topics = new ArrayList<String>();
        categories_topics.add("SELECT TOPIC");
        ArrayAdapter<String> dataAdapter_top = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_topics);

        // Drop down layout style - list view with radio button
        dataAdapter_top.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_topics.setAdapter(dataAdapter_top);
        spinner_topics.setSelection(0, false);
        spinner_topics.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP)
                    new studentsview1().execute();
                return false;
            }
        });


        spinner_center = (Spinner) findViewById(R.id.centsub);

        spinner_fac = (Spinner) findViewById(R.id.facsub);
        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_faculty = new ArrayList<String>();
        categories_faculty.add("SELECT FACULTY");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_fac = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_faculty);

        // Drop down layout style - list view with radio button
        dataAdapter_fac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_fac.setAdapter(dataAdapter_fac);
        spinner_fac.setSelection(0, false);

        StaticMethods sm = new StaticMethods();
        sm.setFacultySpinner(dataAdapter_fac, categories_faculty);

        spinner_center = (Spinner) findViewById(R.id.centsub);

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
        spinner_center.setSelection(0, false);

        spinner_subject = (Spinner) findViewById(R.id.subsub);


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
        spinner_subject.setSelection(0, false);
        spinner_subject.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                new studentsview1().execute();
                spinner_subject.setSelection(position, true);// your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        /*if(spinner_center != null)
        {

        	new studentsview().execute();

        }
        */

        spinner_batch = (Spinner) findViewById(R.id.batchsub);

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
        spinner_batch.setSelection(0, false);
        spinner_batch.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                new studentsview().execute();
                spinner_batch.setSelection(position, true);// your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        //  spinner_class = (Spinner) findViewById(R.id.center);


        spinner_class = (Spinner) findViewById(R.id.clssub);

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
        spinner_class.setSelection(0, false);
        spinner_class.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                new studentsview1().execute();
                spinner_class.setSelection(position, true);// your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        submit = (Button) findViewById(R.id.submit_calsub);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new SweetAlertDialog(Wsubmission.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure you want to schedule this submission?")

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
        });

        Button students;
        students = (Button) findViewById(R.id.studentssub);

        students.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                send = "";

                new studentsview().execute(); //Yes button clicked


            }
        });


    }

    public void getSelectedItems() {
        int cntChoice = lv1.getCount();


        SparseBooleanArray sparseBooleanArray = lv1.getCheckedItemPositions();

        for (int i = 0; i < cntChoice; i++) {

            if (sparseBooleanArray.get(i) == true) {
                send += lv1.getItemAtPosition(i).toString() + ",";
            } else if (sparseBooleanArray.get(i) == false) {
                //  absent+= lv1.getItemAtPosition(i).toString() + ",";
            }

        }
    }

    public void topicsfill() {
        ArrayAdapter<String> dataAdapter_top1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_topics);

        // Drop down layout style - list view with radio button
        dataAdapter_top1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_topics.setAdapter(dataAdapter_top1);
    }

    //------------------for topics---------------------------------------

    public void date_pend_low_range(View view) {
        showDialog(999);
        //   Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT).show();
        flag = 0;
    }


    //------------------------------------------------------------------------------------

    public void date_pend_high_range(View view) {
        showDialog(999);
        //  Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
        flag = 1;
    }


    //--------------------------------------------Date Time ranges-------------------------------------------

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

    private void showDate0(int year, String month, String day) {
        date_pend_down.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
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

    @SuppressWarnings("static-access")
    protected void setlist() {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(Wsubmission.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(lv1.CHOICE_MODE_MULTIPLE);
        lv1.setTextFilterEnabled(true);
        viewall();

        dialogReg.show();
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {

    }

    private void viewall() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Wsubmission.this, android.R.layout.simple_list_item_checked, categories_code);

        lv1.setTextFilterEnabled(true);
        lv1.setAdapter(dataAdapter);
        for (int i = 0; i < dataAdapter.getCount(); i++) {
            lv1.setItemChecked(i, true);
        }

        // ListView Item Click Listener

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

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(Wsubmission.this, SweetAlertDialog.PROGRESS_TYPE)
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
            getSelectedItems();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //	params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("subject", spinner_subject.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("faculty", spinner_fac.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("topics", spinner_topics.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("send", send));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/submit_schedule.php";


            jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();
            Wsubmission.this.recreate();
      /*  Intent time = new Intent(cnv.this,Main.class);
		startActivity(time);
		finish();*/


        }

    }

    private class studentsview1 extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Wsubmission.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");
        JSONObject json;
        String parts[];


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
                    parts = topics.split(",");
                    categories_topics = Arrays.asList(parts);
                    topicsfill();
                    // forjson.add(Roll+"-"+ NAME);
                    //categories_description.add(description);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Wsubmission.this, SweetAlertDialog.PROGRESS_TYPE)
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

                categories_code.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String NAME = json.getString("NAME");
                    String Roll = json.getString("ROLL");

                    categories_code.add(Roll + "-" + NAME);
                    //categories_description.add(description);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            setlist();

        }

    }

}

