package sharpenup.customgridview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
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

@SuppressWarnings("deprecation")
public class testview extends Activity {
    int crashPreventer;
    String DATE;
    String DAY;
    String FACULTY;
    String SUBJECT;
    String CENTER;
    String BATCH;
    String CLASS;
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_fac;
    Spinner spinner_subject;
    TextView date_pend_up;
    TextView date_pend_down;
    List<String> categories_code;
    List<String> categories_pendact;
    List<String> categories_description;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> list;
    List<String> categories_faculty;
    List<String[]> dataContainer;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String send;
    ArrayList<String> date;
    ArrayList<String> fac;
    ArrayList<String> sub;
    Bundle b;
    private Calendar calendar;
    //private TextView date_pend_down,date_pend_up,date_log_down,date_log_up;
    private int year, month, day;
    private PrefManager pref;
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
        setContentView(R.layout.testview);
        getActionBar().hide();
        dataContainer = new ArrayList<String[]>();
        categories_code = new ArrayList<String>();
        //send =new ArrayList<String>();
        send = "";
        crashPreventer = 0;
        categories_description = new ArrayList<String>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        pref = new PrefManager(getApplicationContext());
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        categories_pendact = new ArrayList<String>();
        date_pend_up = (TextView) findViewById(R.id.pdh_test);
        date_pend_down = (TextView) findViewById(R.id.pdl_test);

        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */
        list = new ArrayList<String>();

        spinner_fac = (Spinner) findViewById(R.id.subject_test);
        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_faculty = new ArrayList<String>();
        categories_faculty.add("SELECT SUBJECT");
        categories_faculty.add("MATHLETICS");
        categories_faculty.add("SCI-FI");
        categories_faculty.add("SOCIALITY");
        categories_faculty.add("ENGLISH");
        categories_faculty.add("OTHER");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_fac = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_faculty);

        // Drop down layout style - list view with radio button
        dataAdapter_fac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_fac.setAdapter(dataAdapter_fac);


        spinner_center = (Spinner) findViewById(R.id.center_test);

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


        spinner_batch = (Spinner) findViewById(R.id.batch_test);

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


        spinner_class = (Spinner) findViewById(R.id.mycl_test);

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


        submit = (Button) findViewById(R.id.submittest);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (universalChecker()) {

                    new SweetAlertDialog(testview.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure you want to view test schedule?")

                            .setConfirmText("Yes,do it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    // reuse previous dialog instance
                                    new studentsview().execute();
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(testview.this, "Please Select all fields!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public boolean universalChecker() {
        boolean flag = true;

        if (spinner_batch.getSelectedItem().toString().equals("SELECT BATCH"))
            BATCH = "";
        if (spinner_class.getSelectedItem().toString().equals("SELECT CLASS"))
            CLASS = "";
        if (spinner_center.getSelectedItem().toString().equals("SELECT CENTER"))
            CENTER = "";
        if (spinner_fac.getSelectedItem().toString().equals("SELECT SUBJECT"))
            FACULTY = "";


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

        dialogReg = new Dialog(testview.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(lv1.CHOICE_MODE_MULTIPLE);
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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(testview.this, android.R.layout.simple_list_item_checked, categories_code);

        lv1.setTextFilterEnabled(true);
        lv1.setAdapter(dataAdapter);

        // ListView Item Click Listener
        lv1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                CheckedTextView item = (CheckedTextView) view;
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) lv1.getItemAtPosition(position);

                if (item.isChecked()) {

			            	/*send.add(itemValue);*/

                    send = send + itemValue + ",";
                }
                Toast.makeText(getApplicationContext(), itemValue + " checked : " +
                        item.isChecked(), Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + Toast.LENGTH_SHORT, itemPosition)
                        .show();

            }

        });

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(testview.this, SweetAlertDialog.PROGRESS_TYPE)
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
            BATCH = spinner_batch.getSelectedItem().toString();


            CENTER = spinner_center.getSelectedItem().toString();


            CLASS = spinner_class.getSelectedItem().toString();
            FACULTY = spinner_fac.getSelectedItem().toString();

            universalChecker();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("batch", BATCH));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("class", CLASS));
            params.add(new BasicNameValuePair("center", CENTER));

            //params.add(new BasicNameValuePair("subject", spinner_subject.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("subject", FACULTY));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/testview.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();


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
                    DATE = json.getString("DATE");
                    //	Date theDate = new Date(day);
                    //theDate = (Date) format.parse(DATE);
                    DAY = json.getString("DAY");
                    FACULTY = json.getString("TOPIC");
                    SUBJECT = json.getString("SUBJECT");

                    CENTER = json.getString("CENTER");
                    CLASS = json.getString("CLASS");
                    BATCH = json.getString("BATCH");
                    // categories_code.add(Roll+"-"+ NAME);
                    //categories_description.add(description);
		        	/* date = new ArrayList<String>();
		        	 Collection l = Arrays.asList(date);
		        	date.add(DATE);
		        	 fac = new ArrayList<String>();
		        	 Collection m = Arrays.asList(fac);

		        	fac.add(FACULTY);
		        	 sub = new ArrayList<String>();
		        	 Collection n = Arrays.asList(sub);

		        	sub.add(SUBJECT);
		        	 	*/
		        	/*  String[] arr = null;
		        	  arr[i] = DATE;
		        	  String[] arr1 = null;
		        	  arr1[i] = FACULTY;
		        	  String[] arr2 = null;
		        	  arr2[i] = SUBJECT;*/

                    list.add(" DATE: " + DATE + " " + " \r\n SUBJECT : " + SUBJECT + " \r\n TOPIC: " + FACULTY);

                    arrayList.add(DATE);


                    arrayList1.add(FACULTY);


                    arrayList2.add(SUBJECT);

                    arrayList3.add(DAY);
                    arrayList4.add("Good Luck...");
                    arrayList5.add(CENTER);
                    arrayList6.add(CLASS);
                    arrayList7.add(BATCH);
                    b = new Bundle();
                    b.putStringArrayList("date", arrayList);
                    b.putStringArrayList("faculty", arrayList1);
                    b.putStringArrayList("subject", arrayList2);
                    b.putStringArrayList("day", arrayList3);


                }

                dataContainer.clear();
                dataContainer.add(arrayList.toArray(new String[arrayList.size()]));
                dataContainer.add(arrayList5.toArray(new String[arrayList5.size()]));
                dataContainer.add(arrayList6.toArray(new String[arrayList6.size()]));
                dataContainer.add(arrayList7.toArray(new String[arrayList7.size()]));
                dataContainer.add(arrayList1.toArray(new String[arrayList1.size()]));
                dataContainer.add(arrayList2.toArray(new String[arrayList2.size()]));
                dataContainer.add(arrayList3.toArray(new String[arrayList3.size()]));
                // Intent i=new Intent(context, Class);

                if (crashPreventer == 1) {

                    crashPreventer = 0;
                    new UniversalTableLayout(dataContainer, testview.this);
                } else {
                    Toast.makeText(testview.this, "Data Empty!!!", Toast.LENGTH_SHORT).show();
                }


                //finish();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(testview.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            //  setlist();

        }

    }


}
