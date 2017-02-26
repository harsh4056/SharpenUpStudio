package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nonactivity.javafunctionality.classes.StaticMethods;

@SuppressWarnings("deprecation")
public class ScheduleClass extends Activity {

    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_fac;
    Spinner spinner_subject;
    Spinner spinner_top;
    TextView date_pend_up;
    TextView date_pend_down;
    List<String> categories_code;
    int day_of_month;
    String monthname;

    List<String> categories_description;
    List<String> categories_top;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_faculty;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String send;
    Calendar scheduleDate;
    String TOPIC;
    JSONObject json;
    private Calendar calendar;
    //private TextView date_pend_down,date_pend_up,date_log_down,date_log_up;
    private int year, month, day;

    //	int flag,flag_btn_click;
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            scheduleDate = new GregorianCalendar(arg1, arg2 + 1, arg3);
            arg2 = arg2 + 1;

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
		    	  /*else  if (flag==2)
		 	         showDate2(arg1, strm, strd);
		    	  else if (flag==3)
		 	         showDate3(arg1, strm, strd);*/
        }
    };
    private OnItemSelectedListener itemSelectedListener1 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            if (checkerForstudent())
                new studentsview().execute();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }
    };
    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {

            if (spinner_subject.getSelectedItem().toString() != null &&
                    !spinner_subject.getSelectedItem().toString().equals("SELECT SUBJECT")) {
                new subject().execute();
                //spinner_top.setSelection(position, true);
            } else {
                Toast.makeText(ScheduleClass.this, "Please select subject... ", Toast.LENGTH_SHORT).show();
            }
            // ListView Clicked item index


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override


    //-----------------------------------------------settting method--------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnv);
        getActionBar().hide();
        categories_code = new ArrayList<String>();
        //send =new ArrayList<String>();
        send = "";
        categories_description = new ArrayList<String>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        day_of_month = calendar.get(Calendar.DAY_OF_WEEK);

        monthname = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());


        date_pend_up = (TextView) findViewById(R.id.textpdh);

        // showDate0(year, appending0(month+1),appending0( day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */


        spinner_fac = (Spinner) findViewById(R.id.fac);

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

        StaticMethods sm = new StaticMethods();
        sm.setFacultySpinner(dataAdapter_fac, categories_faculty);


        spinner_center = (Spinner) findViewById(R.id.cent);

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

        int position = 0;
        spinner_subject.setSelection(position, false);


        spinner_top = (Spinner) findViewById(R.id.toptt);
        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_top = new ArrayList<String>();
        categories_top.add("SELECT TOPIC");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_t = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_top);

        // Drop down layout style - list view with radio button
        dataAdapter_t.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_top.setAdapter(dataAdapter_t);
        spinner_top.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP)
                    new subject().execute();
                return false;
            }
        });












        /*if(spinner_center != null)
        {

        	new studentsview().execute();

        }
        */

        spinner_batch = (Spinner) findViewById(R.id.batch);

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


        spinner_class = (Spinner) findViewById(R.id.myclass);

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


        submit = (Button) findViewById(R.id.submit_cal);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkerForSubmission()) {
                    new SweetAlertDialog(ScheduleClass.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("")
                            .setCancelText("No,cancel plx!")
                            .setConfirmText("Yes,do it!")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    // reuse previous dialog instance, keep widget user state, reset them if you need
                                    sDialog.setTitleText("Cancelled!")
                                            .setContentText("Not Scheduled")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                    // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {


                                    int cntChoice = lv1.getCount();


                                    SparseBooleanArray sparseBooleanArray = lv1.getCheckedItemPositions();

                                    for (int i = 0; i < cntChoice; i++) {

                                        if (sparseBooleanArray.get(i) == true) {
                                            send += lv1.getItemAtPosition(i).toString() + ",";
                                        }


                                    }

                                    if (send == null) {
                                        Toast.makeText(ScheduleClass.this, "Please mark Absent,Present or Leave", Toast.LENGTH_SHORT).show();
                                    } else {
                                        new time().execute();

                                    }
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(ScheduleClass.this, "Please Select all fields!!!", Toast.LENGTH_SHORT).show();
                }


                send = "";

            }


        });


        spinner_center.setOnItemSelectedListener(itemSelectedListener1);
        spinner_batch.setOnItemSelectedListener(itemSelectedListener1);
        spinner_class.setOnItemSelectedListener(itemSelectedListener1);


    }

    //-----------------------------------------------settting method ends--------------------------------------------------------------------
    public boolean checkerForstudent() {
        boolean flag = true;

        if (spinner_batch.getSelectedItem().toString().equals("SELECT BATCH"))
            flag = false;
        if (spinner_class.getSelectedItem().toString().equals("SELECT CLASS"))
            flag = false;
        if (spinner_center.getSelectedItem().toString().equals("SELECT CENTER"))
            flag = false;


        return flag;


    }
	
	
	
	
	
	/*public boolean universalChecker(){
		
	}
	
	
	
	*/


    //--------------------------------------------Date Time ranges-------------------------------------------

    public boolean checkerForSubmission() {
        boolean flag = true;

        if (spinner_top.getSelectedItem().toString().equals("SELECT TOPIC"))
            flag = false;
        if (spinner_subject.getSelectedItem().toString().equals("SELECT SUBJECT"))
            flag = false;
        if (spinner_fac.getSelectedItem().toString().equals("SELECT FACULTY"))
            flag = false;


        return flag;


    }

    public void date_pend_low_range(View view) {
        showDialog(999);
        //   Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT).show();
        flag = 0;
    }

    public void date_pend_high_range(View view) {
        showDialog(999);
        //  Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
        flag = 1;
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

		/*   private void showDate0(int year, String month, String day) {
			   date_pend_down.setText(new StringBuilder().append(year).append("-")
		      .append(month).append("-").append(day));
		   }*/

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
		   /*private void showDate2(int year, String month, String day) {
			   date_log_down.setText(new StringBuilder().append(year).append("-")
					      .append(month).append("-").append(day));
		   }
		   private void showDate3(int year, String month, String day) {
			   date_log_up.setText(new StringBuilder().append(year).append("-")
					      .append(month).append("-").append(day));
		   }*/
    //-----------------------------------------------------------------------------------------------


    @SuppressWarnings("static-access")
    protected void setlist() {


        dialogReg = new Dialog(ScheduleClass.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(lv1.CHOICE_MODE_MULTIPLE);
        lv1.setTextFilterEnabled(true);
        viewall();

        dialogReg.show();
    }

    public String dayName(Calendar c) {

        String day = "";
        int result = c.get(Calendar.DAY_OF_WEEK);
        if (result == 1)
            result = 8;
        switch (result - 1) {
            case Calendar.MONDAY:
                day = "MONDAY";
                break;
            case Calendar.TUESDAY:
                day = "TUESDAY";
                break;
            case Calendar.WEDNESDAY:
                day = "WEDNESDAY";
                break;
            case Calendar.THURSDAY:
                day = "THURSDAY";
                break;
            case Calendar.FRIDAY:
                day = "FRIDAY";
                break;
            case Calendar.SATURDAY:
                day = "SATURDAY";
                break;
            case Calendar.SUNDAY:
                day = "SUNDAY";
                break;


        }
        return day;
    }


    public void onListItemClick(ListView parent, View v, int position, long id) {

    }

    private void viewall() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ScheduleClass.this, android.R.layout.simple_list_item_checked, categories_code);

        lv1.setTextFilterEnabled(true);
        lv1.setAdapter(dataAdapter);
        for (int i = 0; i < dataAdapter.getCount(); i++) {
            lv1.setItemChecked(i, true);
        }

        // ListView Item Click Listener
        lv1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }

        });
    }

    public void topicsfill() {
        ArrayAdapter<String> dataAdapter_top1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_top);

        // Drop down layout style - list view with radio button
        dataAdapter_top1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_top.setAdapter(dataAdapter_top1);


    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ScheduleClass.this, SweetAlertDialog.PROGRESS_TYPE)
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
            //	params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("topic", spinner_top.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("subject", spinner_subject.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("faculty", spinner_fac.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("day", dayName(scheduleDate)));

            params.add(new BasicNameValuePair("send", send));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/schedule.php";


            //@SuppressWarnings("unused")
            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {
                String account = json.getString("pendings");


                if (account.equals("FAILED")) {


                    pDialog.setTitleText("Data Modified!")
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                } else {
                    pDialog.setTitleText("Scheduled!")
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                }


                if (spinner_top.getSelectedItem().toString().trim().equalsIgnoreCase("test")) {
                    new SweetAlertDialog(ScheduleClass.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Do you want to schedule the test as well??")
                            .setContentText("")
                            .setCancelText("No,cancel plx!")
                            .setConfirmText("Yes,do it!")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    // reuse previous dialog instance, keep widget user state, reset them if you need
                                    sDialog.setTitleText("Cancelled!")
                                            .setContentText("Not Scheduled")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                    // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    Intent i = new Intent(ScheduleClass.this, testSchedule.class);
                                    i.putExtra("center", spinner_center.getSelectedItemPosition());
                                    i.putExtra("class", spinner_class.getSelectedItemPosition());
                                    i.putExtra("batch", spinner_batch.getSelectedItemPosition());
                                    i.putExtra("subject", spinner_subject.getSelectedItemPosition());
                                    i.putExtra("date", date_pend_up.getText().toString());
                                    i.putExtra("fromTimeTable", true);

                                    startActivity(i);

                                    sDialog.dismiss();
                                }
                            })
                            .show();
                }


            } catch (Exception e) {
                e.printStackTrace();

                pDialog.setTitleText("Oops....")
                        .setContentText("Something went wrong!!!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }

      /*  Intent time = new Intent(cnv.this,Main.class);
		startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/


        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ScheduleClass.this, SweetAlertDialog.PROGRESS_TYPE)
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


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ScheduleClass.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            setlist();

        }

    }

    private class subject extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ScheduleClass.this, SweetAlertDialog.PROGRESS_TYPE)
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


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();


            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    TOPIC = json.getString("TOPIC");
//					        	/String  Roll= json.getString("ROLL");

                    String[] split = TOPIC.split(",");
                    // categories_code.add(Roll+"-"+ NAME);
                    //categories_description.add(description);
                    categories_top = Arrays.asList(split);
                    topicsfill();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
