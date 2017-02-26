package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressWarnings("deprecation")
public class AttendanceActivity extends Activity {
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;

    String id;
    TextView date_pend_up;
    TextView date_pend_down;
    List<String> absenties_names;
    List<String> categories_code;
    List<String> all_students;
    List<String> categories_description;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_faculty;
    List<String> bachoKRoll;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String present;
    String absent;
    String leave;
    List<String> categories_top;
    Context ctx = AttendanceActivity.this;
    private Calendar calendar;
    //private TextView date_pend_down,date_pend_up,date_log_down,date_log_up;
    private int year, month, day;
    //	int flag,flag_btn_click;
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);
        //getActionBar().hide();
        bachoKRoll = new ArrayList<String>();
        all_students = new ArrayList<String>();
        categories_code = new ArrayList<String>();
        absenties_names = new ArrayList<String>();
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
        date_pend_up = (TextView) findViewById(R.id.textpdh);

        //  showDate0(year, appending0(month+1),appending0( day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */

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


        spinner_class = (Spinner) findViewById(R.id.cls);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_class = new ArrayList<String>();
        categories_class.add("SELECT CLASS");
        categories_class.add("Junior");
        categories_class.add("5th");
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


                new SweetAlertDialog(AttendanceActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure you want to mark attendance ?")

                        .setConfirmText("Yes,do it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                submit();
                                sDialog.dismiss();
                            }
                        })
                        .show();


            }


        });

        Button students;
        students = (Button) findViewById(R.id.students);

        students.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (universalChecker()) {


                    new studentsview().execute();
                } else {
                    Toast.makeText(AttendanceActivity.this, "Please Select all fields!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void submit() {
        int cntChoice = lv1.getCount();


        SparseBooleanArray sparseBooleanArray = lv1.getCheckedItemPositions();

        for (int i = 0; i < cntChoice; i++) {

            if (sparseBooleanArray.get(i) == true) {
                present += lv1.getItemAtPosition(i).toString() + ",";
            } else if (sparseBooleanArray.get(i) == false) {
                absent += lv1.getItemAtPosition(i).toString() + ",";

                String[] split_roll = lv1.getItemAtPosition(i).toString().split("-");
                String roll = split_roll[0];

                absenties_names.add(split_roll[0]);


            }

        }
        Toast.makeText(getApplicationContext(),
                leave, Toast.LENGTH_SHORT)
                .show();
        new time().execute();
    }

    public boolean universalChecker() {
        boolean flag = true;

        if (spinner_batch.getSelectedItem().toString().equals("SELECT BATCH"))
            flag = false;
        if (spinner_class.getSelectedItem().toString().equals("SELECT CLASS"))
            flag = false;
        if (spinner_center.getSelectedItem().toString().equals("SELECT CENTER"))
            flag = false;


        return flag;


    }

    public void warnDialog() {

        final Dialog dialogReg = new Dialog(ctx);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setTitle("These students are irregular ");
        dialogReg.setCanceledOnTouchOutside(false);
        dialogReg.setContentView(R.layout.warn_dialog);

        Button setzero = (Button) dialogReg.findViewById(R.id.setzero);

        setzero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!bachoKRoll.get(0).equals("All are regular.")) {
                    new setzeroAsync().execute();
                } else {
                    absenties_names.clear();
                }

                dialogReg.dismiss();
            }
        });


        lv1 = (ListView) dialogReg.findViewById(R.id.listwarnview);
        lv1.setTextFilterEnabled(true);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AttendanceActivity.this, android.R.layout.simple_list_item_1, bachoKRoll);


        lv1.setAdapter(dataAdapter);


        dialogReg.show();

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


    //--------------------------------------------Date Time ranges-------------------------------------------

    private void showDate1(int year, String month, String day) {
        date_pend_up.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    protected void setlist() {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(AttendanceActivity.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        lv1.setTextFilterEnabled(true);
        viewall();

        dialogReg.show();
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;
        //Toast.makeText(this, c[position] + " checked : " +
        //item.isChecked(), Toast.LENGTH_SHORT).show();
    }

    private void viewall() {

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AttendanceActivity.this, android.R.layout.simple_list_item_checked, categories_code);

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

                CheckedTextView item = (CheckedTextView) view;
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value


			       /*     if(item.isChecked())
			            {

			            	send.add(itemValue);

			            	present = present + itemValue + "," ;
			            }

			            else if(item.isChecked()==false){

			            	absent = absent + itemValue + "," ;

			            }*/
			           /* Toast.makeText(getApplicationContext(), itemValue + " checked : " +
			            		item.isChecked(), Toast.LENGTH_SHORT).show();
			             // Show Alert

			             Toast.makeText(getApplicationContext(),
			               "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT)
			               .show();*/

            }

        });
        //


        lv1.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub
                final String itemValue = (String) lv1.getItemAtPosition(pos);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                leave = leave + itemValue + ",";
                                dataAdapter.remove(dataAdapter.getItem(pos));
                                dataAdapter.notifyDataSetChanged();  //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(arg1.getContext());
                builder.setMessage("Are you sure this student is on leave today ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;

            }
        });

    }

		 /*  private void showDate0(int year, String month, String day) {
			   date_pend_down.setText(new StringBuilder().append(year).append("-")
		      .append(month).append("-").append(day));
		   }*/

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(AttendanceActivity.this, SweetAlertDialog.PROGRESS_TYPE)
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

            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("present", present));
            params.add(new BasicNameValuePair("absent", absent));
            params.add(new BasicNameValuePair("leave", leave));
            String url = "http://176.32.230.250/anshuli.com/sharpenup2/setattendance.php";

            present = "";
            absent = "";
            leave = "";
            JSONObject json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            if (absenties_names.size() == 0) {
                bachoKRoll.clear();
                bachoKRoll.add("All are regular.");
                warnDialog();
            } else {
                bachoKRoll.clear();
                new absentiesAsync().execute();
            }
       /* Intent time = new Intent(AttendanceActivity.this,Main.class);
		startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/


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

    private class absentiesAsync extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(AttendanceActivity.this, SweetAlertDialog.PROGRESS_TYPE)
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
            int count = absenties_names.size();
            for (int i = 0; i < count; i++) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("roll", absenties_names.get(i)));

                String url = "http://176.32.230.250/anshuli.com/sharpenup2/absent_count.php";

                JSONObject json = jparser.makeHttpRequest(url, "POST", params);

            }

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            new warnAsync().execute();


        }

    }

    private class warnAsync extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(AttendanceActivity.this, SweetAlertDialog.PROGRESS_TYPE)
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
            int count = absenties_names.size();
            for (int i = 0; i < count; i++) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("roll", absenties_names.get(i)));

                String url = "http://176.32.230.250/anshuli.com/sharpenup2/warn_absent.php";

                JSONObject json = jparser.makeHttpRequest(url, "POST", params);

                try {
                    //   bachoKRoll.clear();
                    JSONArray account = json.getJSONArray("pendings");
                    for (int j = 0; j < account.length(); j++) {
                        json = account.getJSONObject(j);

                        int COUNT_ABSENT = json.getInt("ABSENT_COUNT");

                        String Name = json.getString("NAME");

                        if (COUNT_ABSENT > 1) {
                            bachoKRoll.add(Name + " is irregular.");

                        } else {
                            bachoKRoll.clear();
                            bachoKRoll.add("All are regular.");
                            //warnDialog();
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            warnDialog();
       /* Intent time = new Intent(AttendanceActivity.this,Main.class);
		startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/


        }

    }

    private class setzeroAsync extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(AttendanceActivity.this, SweetAlertDialog.PROGRESS_TYPE)
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
            int count = absenties_names.size();
            for (int i = 0; i < count; i++) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("roll", absenties_names.get(i)));

                String url = "http://176.32.230.250/anshuli.com/sharpenup2/setzero.php";

                JSONObject json = jparser.makeHttpRequest(url, "POST", params);

            }

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            absenties_names.clear();


        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(AttendanceActivity.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/getattendance.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();


            try {


                categories_code.clear();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String NAMES = json.getString("STUDENTS");
                    String Sid = json.getString("S_No");
                    String[] parts = NAMES.split(",");
                    categories_code.addAll(Arrays.asList(parts));
                    //categories_description.add(description);

                    id = Sid;

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            setlist();

        }

    }
}
		
	
