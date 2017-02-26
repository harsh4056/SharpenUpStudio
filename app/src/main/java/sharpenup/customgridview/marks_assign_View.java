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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.androidbegin.viewpagertutorial.marksfill;

@SuppressWarnings("deprecation")
public class marks_assign_View extends Activity implements OnItemClickListener {

    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_fac;
    Spinner spinner_subject;
    String id;
    String name_roll;
    TextView date_pend_up;
    TextView date_pend_down;
    List<String> categories_code;
    List<String> all_students;
    List<String> categories_description;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_faculty;
    List<String> list;

    List<String> per_class;
    List<String> per_batch;
    List<String> per_center;
    List<String> per_faculty;
    List<String> per_subject;
    List<String> per_absent;
    List<String> per_present;
    List<String> per_leave;
    List<String> per_noclass;
    List<String> per_date;
    List<String> DATE_HIGH;
    List<String> DAY;
    String Roll;
    String batch, center, cls;
    Bundle b;
    //List<String> per_date ;

    int variableNotify;
    int crashPreventer;


    JSONObject json;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String present;
    String absent;
    String leave;
    String number;
    boolean viewMarks;
    ArrayAdapter<String> dataAdapter;
    PrefManager pref;
    private Calendar calendar;
    //	int flag,flag_btn_click;
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
        getActionBar().hide();
        pref = new PrefManager(marks_assign_View.this);
        setContentView(R.layout.marks_assign);
        //getActionBar().hide();
        per_class = new ArrayList<String>();
        per_batch = new ArrayList<String>();
        per_center = new ArrayList<String>();
        per_faculty = new ArrayList<String>();
        per_subject = new ArrayList<String>();

        variableNotify = 1;
        crashPreventer = 0;
        Intent i = getIntent();
        viewMarks = i.getBooleanExtra("marksView", false);


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


        //date_pend_down = (TextView) findViewById(R.id.textpdl);
        date_pend_up = (TextView) findViewById(R.id.textpdh_marks);
        date_pend_down = (TextView) findViewById(R.id.textpdl_marks);
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */
        list = new ArrayList<String>();
        spinner_center = (Spinner) findViewById(R.id.cent_marks);

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


        spinner_batch = (Spinner) findViewById(R.id.batch_marks);

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


        spinner_class = (Spinner) findViewById(R.id.myclass_marks);

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


        submit = (Button) findViewById(R.id.submit_marks);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new SweetAlertDialog(marks_assign_View.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")

                        .setConfirmText("Yes,do it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                batch = spinner_batch.getSelectedItem().toString();
                                center = spinner_center.getSelectedItem().toString();
                                cls = spinner_class.getSelectedItem().toString();
                                batchClassEmptyChecker();

                                //
                                list.clear();


                                new time().execute();
                                variableNotify = 0;
                                sDialog.dismiss();
                            }
                        })
                        .show();

            }


        });


    }

    public boolean batchClassEmptyChecker() {
        boolean flag = true;

        if (spinner_batch.getSelectedItem().toString().equals("SELECT BATCH"))
            batch = "";
        if (spinner_class.getSelectedItem().toString().equals("SELECT CLASS"))
            cls = "";
        if (spinner_center.getSelectedItem().toString().equals("SELECT CENTER"))
            center = "";


        return flag;


    }


    //--------------------------------------------Date Time ranges-------------------------------------------

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

        dialogReg = new Dialog(marks_assign_View.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(lv1.CHOICE_MODE_SINGLE);
        lv1.setTextFilterEnabled(true);
        viewall();

        dialogReg.show();
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

    public void onListItemClick(ListView parent, View v, int position, long id) {

    }

    private void viewall() {

        dataAdapter = new ArrayAdapter<String>(marks_assign_View.this, android.R.layout.simple_list_item_checked, categories_code);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.myclass_marks:
                variableNotify = 1;
                break;
            case R.id.batch_marks:
                variableNotify = 1;
                break;
            case R.id.cent_marks:
                variableNotify = 1;
                break;

            default:
                break;


        }


    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(marks_assign_View.this, SweetAlertDialog.PROGRESS_TYPE)
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
			/*//params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
			params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
			params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
			params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
			params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));*/

            if (pref.getLogin().equals("teacher"))
                params.add(new BasicNameValuePair("faculty", pref.getTeacher()));


            params.add(new BasicNameValuePair("batch", batch));
            params.add(new BasicNameValuePair("center", center));
            params.add(new BasicNameValuePair("class", cls));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            String url = "http://176.32.230.250/anshuli.com/sharpenup2/testmarksquery.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                // Checking for SUCCESS TAG
                //categories_code.clear();
                ArrayList<String> arrayList = new ArrayList<String>();
                ArrayList<String> arrayList1 = new ArrayList<String>();
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();
                ArrayList<String> arrayList2 = new ArrayList<String>();
                ArrayList<String> arrayList3 = new ArrayList<String>();
                ArrayList<String> arrayList4 = new ArrayList<String>();
                ArrayList<String> arrayList5 = new ArrayList<String>();
                ArrayList<String> arrayList6 = new ArrayList<String>();
                ArrayList<String> arrayList7 = new ArrayList<String>();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    crashPreventer = 1;
                    json = account.getJSONObject(i);

                    // int day = 	myCal.get(Calendar.DAY_OF_MONTH);
                    String DATE = json.getString("DATE");
                    //	Date theDate = new Date(day);
                    //theDate = (Date) format.parse(DATE);
                    String DAY = json.getString("DAY");
                    String TOPIC = json.getString("TOPIC");
                    String SUBJECT = json.getString("SUBJECT");
                    String CLASS = json.getString("CLASS");
                    String BATCH = json.getString("BATCH");
                    String CENTER = json.getString("CENTER");
                    String MARKS = json.getString("MARKS_STUDENT");
                    String Cutoff = json.getString("CUTOFF");


                    list.add(" DATE: " + DATE + " " + " \r\n CENTER : " + CENTER + "" + " \r\n CLASS : " + CLASS + "" + " \r\n BATCH : " + BATCH + " " + " \r\n SUBJECT : " + SUBJECT + " \r\n TOPIC: " + TOPIC + " " + "\r\n Marks@" + MARKS);

                    arrayList.add(DATE);


                    arrayList1.add(TOPIC);


                    arrayList2.add(SUBJECT);

                    arrayList3.add(DAY);
                    arrayList4.add(CLASS);
                    arrayList5.add(BATCH);
                    arrayList6.add(CENTER);
                    arrayList7.add(" DATE: " + DATE + " " + " \r\n CENTER : " + CENTER + "" + " \r\n CLASS : " + CLASS + "" + " \r\n BATCH : " + BATCH + " " + " \r\n SUBJECT : " + SUBJECT + " \r\n TOPIC: " + TOPIC + " \r\n CUTT-OFF: " + Cutoff);
                    b = new Bundle();
                    b.putBoolean("viewMarks", viewMarks);
                    b.putString("date", DATE);
                    b.putString("faculty", TOPIC);
                    b.putString("subject", SUBJECT);
                    b.putString("cls", CLASS);
                    b.putString("center", CENTER);
                    b.putString("batch", BATCH);
                    b.putStringArrayList("list", arrayList7);
                    b.putStringArrayList("listWithMarks", (ArrayList<String>) list);


                }


                // Intent i=new Intent(context, Class);

                if (crashPreventer == 1) {
                    crashPreventer = 0;
                    Intent i = new Intent(marks_assign_View.this, marksfill.class);
                    b.putString("name_roll", number);
                    i.putExtras(b);

                    startActivity(i);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);
                } else {
                    Toast.makeText(marks_assign_View.this, "Data empty!!!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(marks_assign_View.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }


            pDialog.dismiss();

       /* Intent time = new Intent(AttendanceActivity.this,Main.class);
		startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/


        }

    }


}
