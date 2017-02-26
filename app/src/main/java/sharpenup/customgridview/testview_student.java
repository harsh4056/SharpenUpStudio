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
import nonactivity.javafunctionality.classes.StaticMethods;

@SuppressWarnings("deprecation")
public class testview_student extends Activity {
    int crashPreventer;
    String DATE;
    String DAY;
    String FACULTY;
    String SUBJECT;
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
    List<String[]> dataContainer = new ArrayList<String[]>();
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
        setContentView(R.layout.testview_student);
        getActionBar().hide();
        categories_code = new ArrayList<String>();
        //send =new ArrayList<String>();
        send = "";
        categories_description = new ArrayList<String>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        pref = new PrefManager(getApplicationContext());
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        crashPreventer = 0;
        categories_pendact = new ArrayList<String>();
        date_pend_up = (TextView) findViewById(R.id.pdh_test_student);
        date_pend_down = (TextView) findViewById(R.id.pdl_test_student);

        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */
        date_pend_down.setText(StaticMethods.addedDate(date_pend_down.getText().toString(), 7));
        date_pend_up.setText(StaticMethods.addedDate(date_pend_up.getText().toString(), -7));
        list = new ArrayList<String>();
	  /*
	    spinner_fac = (Spinner) findViewById(R.id.subject_test_student);
        // Spinner click listener
      //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
	categories_faculty = new ArrayList<String>();
	categories_faculty .add("SELECT SUBJECT");
	categories_faculty .add("MATHLETICS");
	categories_faculty .add("SCI-FI");
	categories_faculty .add("SOCIALITY");
	categories_faculty .add("ENGLISH");
	categories_faculty .add("OTHER");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_fac = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_faculty );

        // Drop down layout style - list view with radio button
        dataAdapter_fac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_fac.setAdapter(dataAdapter_fac);*/


        submit = (Button) findViewById(R.id.submittest_student);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialogManager alert = new AlertDialogManager();
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                // Check if Internet present
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present
                    alert.showAlertDialog(testview_student.this,
                            "Internet Connection Error",
                            "Please connect to working Internet connection", false);
                    // stop executing code by return
                    return;
                }

                new studentsview().execute();

            }
        });

	/*Button students;
	students = (Button)findViewById(R.id.students);

	students.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {



			new studentsview().execute();

		}
	});*/


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

        dialogReg = new Dialog(testview_student.this);
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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(testview_student.this, android.R.layout.simple_list_item_checked, categories_code);

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
        final SweetAlertDialog pDialog = new SweetAlertDialog(testview_student.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("batch", pref.getBatch()));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("class", pref.getclass()));
            params.add(new BasicNameValuePair("center", pref.getCenter()));

            //params.add(new BasicNameValuePair("subject", spinner_subject.getSelectedItem().toString()));
            //params.add(new BasicNameValuePair("subject", spinner_fac.getSelectedItem().toString()));
            //params.add(new BasicNameValuePair("send", send));

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
                    arrayList4.add("GOOD LUCK...");


                    dataContainer.clear();
                    dataContainer.add(arrayList.toArray(new String[arrayList.size()]));
                    dataContainer.add(arrayList3.toArray(new String[arrayList3.size()]));
                    dataContainer.add(arrayList2.toArray(new String[arrayList2.size()]));
                    dataContainer.add(arrayList1.toArray(new String[arrayList1.size()]));
                }


                // Intent i=new Intent(context, Class);

                if (crashPreventer == 1) {
                    crashPreventer = 0;

                    new UniversalTableLayout(dataContainer, testview_student.this);

                    //finish();
                } else {
                    Toast.makeText(testview_student.this, "Data empty!!!", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(testview_student.this, "Internet connection error!!!", Toast.LENGTH_SHORT).show();
            }

            //  setlist();

        }

    }


}
