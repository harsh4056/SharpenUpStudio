package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class viewscores extends Activity {
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_fac;
    Spinner spinner_subject;
    String id;
    String roll_identity;
    String roll_match;
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
    Bundle b;
    int a, no;
    //List<String> per_date ;
    ArrayList<Double> scores;
    String Roll;
    String number;
    String number2;
    String get;
    double per = 0;
    double per_new;
    double item;
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
    List<String[]> dataContainer;
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewscores);
        //getActionBar().hide();
        dataContainer = new ArrayList<String[]>();
        per_class = new ArrayList<String>();
        per_batch = new ArrayList<String>();
        per_center = new ArrayList<String>();
        per_faculty = new ArrayList<String>();
        per_subject = new ArrayList<String>();

        crashPreventer = 0;

        scores = new ArrayList<Double>();


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
        date_pend_up = (TextView) findViewById(R.id.textpdh_stud);
        date_pend_down = (TextView) findViewById(R.id.textpdl_stud);
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
	      /*showDate2(year, appending0(month+1),appending0( day));
	      showDate3(year, appending0(month+1),appending0( day));
	      */

        spinner_center = (Spinner) findViewById(R.id.cent_stud);

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


        spinner_batch = (Spinner) findViewById(R.id.batch_stud);

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


        spinner_class = (Spinner) findViewById(R.id.myclass_stud);

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


        submit = (Button) findViewById(R.id.submit_cal_stud);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new SweetAlertDialog(viewscores.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure you want to view scores?")

                        .setConfirmText("Yes,do it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                int cntChoice = lv1.getCount();


                                SparseBooleanArray sparseBooleanArray = lv1.getCheckedItemPositions();

                                for (int i = 0; i < cntChoice; i++) {

                                    if (sparseBooleanArray.get(i) == true) {
                                        present = lv1.getItemAtPosition(i).toString() + ",";
                                        String[] parts = present.split("-");
                                        roll_identity = parts[0];
                                    }


                                }

                                new percentile().execute();
                                sDialog.dismiss();
                            }
                        })
                        .show();


            }
        });

        Button students;
        students = (Button) findViewById(R.id.students_submit);

        students.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (universalChecker())

                    new studentsview().execute();
                else
                    Toast.makeText(viewscores.this, "Please Select all fields!!!", Toast.LENGTH_SHORT).show();

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

    protected void setlist() {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(viewscores.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
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

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(viewscores.this, android.R.layout.simple_list_item_checked, categories_code);

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
                String itemValue = (String) lv1.getItemAtPosition(position);
			        /*
			   if(item.isChecked())
			            {


			            	name_roll= itemValue;
			            	String[] parts = name_roll.split("-");
			            	roll_identity  = parts[0];
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
		   /*private void showDate2(int year, String month, String day) {
			   date_log_down.setText(new StringBuilder().append(year).append("-")
					      .append(month).append("-").append(day));
		   }
		   private void showDate3(int year, String month, String day) {
			   date_log_up.setText(new StringBuilder().append(year).append("-")
					      .append(month).append("-").append(day));
		   }*/
    //-----------------------------------------------------------------------------------------------

    private class percentile extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(viewscores.this, SweetAlertDialog.PROGRESS_TYPE)
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

            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            String url = "http://176.32.230.250/anshuli.com/sharpenup2/viewscores.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                // Checking for SUCCESS TAG
                ArrayList<String> per_marks = new ArrayList<String>();
                per_marks.clear();
                ArrayList<String> per_absent = new ArrayList<String>();
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();
                ArrayList<String> per_leave = new ArrayList<String>();
                ArrayList<String> per_noclass = new ArrayList<String>();
                ArrayList<String> per_date = new ArrayList<String>();
                ArrayList<String> per_day = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> subject = new ArrayList<String>();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String s = json.getString("MARKS_STUDENT");


                    String[] parts = s.split("Marks");
                    a = parts.length;
                    no = (int) (0.2 * (a - 1));
                    for (i = 1; i < a; i++) {

                        number = parts[i];
                        // String roll_con = parts[0];
                        String[] roll_con_1 = number.split("=");
                        roll_match = roll_con_1[1];
                        String[] parts1 = number.split(" out of ");
                        number2 = parts1[0];
                        String[] parts4 = number2.split("=");

                        get = parts4[1];


                        per_new = Double.parseDouble(get.trim());
                        if (per_new != -1.0) {
                            scores.add(per_new);
                            per = per + per_new;
                        }
                    }


                    if (scores != null) {
                        Collections.reverse(scores);
                        item = scores.get(no);
                    }


                    ArrayList<String> per_markss = new ArrayList<String>();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            pDialog.dismiss();

            new time().execute();


        }

    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(viewscores.this, SweetAlertDialog.PROGRESS_TYPE)
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

            params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            String url = "http://176.32.230.250/anshuli.com/sharpenup2/viewscores.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                // Checking for SUCCESS TAG
                ArrayList<String> per_marks = new ArrayList<String>();
                ArrayList<String> cutoffCleared = new ArrayList<String>();
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();
                ArrayList<String> topics = new ArrayList<String>();
                ArrayList<String> per_noclass = new ArrayList<String>();
                ArrayList<String> per_date = new ArrayList<String>();
                ArrayList<String> per_day = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> subject = new ArrayList<String>();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    crashPreventer = 1;
                    json = account.getJSONObject(i);

                    String s = json.getString("MARKS_STUDENT");


                    if (s.contains("Roll Number =" + roll_identity)) {
                        topics.add(json.getString("TOPIC"));
                        per_class.add(json.getString("CLASS"));
                        per_batch.add(json.getString("BATCH"));
                        subject.add(json.getString("SUBJECT"));

                        per_center.add(json.getString("CENTER"));
                        per_day.add(json.getString("DAY"));

                        per_date.add(json.getString("DATE"));
                        String cutoff = json.getString("CUTOFF");
                        String[] parts = s.split("Roll Number =" + roll_identity);
                        String number = parts[1];
                        String[] parts1 = number.split("=");
                        //	per_marks.add("You have got" + + " out of" + );


                        String number2 = parts1[1];
                        String[] parts2 = number2.split(" out of ");
                        String get = parts2[0];
                        String out = parts2[1];
                        String[] out_split = out.split(" ");
                        String final_out = out_split[0];
                        if (get.equals(" -1"))
                            per_marks.add("Absent");

                        else
                            per_marks.add("Student has got" + get + " out of " + final_out);

                        double per_get = Double.parseDouble(get.trim());
                        double cutoff_decimal = Double.parseDouble(cutoff.trim());


                        if (per_get >= cutoff_decimal) {
                            cutoffCleared.add("No ,cutoff=" + cutoff_decimal);
                        } else {

                            cutoffCleared.add(" Yes ,cutoff=" + cutoff_decimal);

                        }

                    }


                    name.add(present);

                    b = new Bundle();
                    b.putStringArrayList("present", per_marks);
                    b.putStringArrayList("day", per_day);
                    b.putStringArrayList("leave", topics);
                    b.putStringArrayList("absent", cutoffCleared);

                    b.putStringArrayList("date", per_date);
                    b.putStringArrayList("name", name);
                    b.putStringArrayList("subject", subject);


                }
                if (crashPreventer == 1) {

                    crashPreventer = 0;

                    dataContainer.add(per_date.toArray(new String[per_date.size()]));

                    dataContainer.add(per_day.toArray(new String[per_day.size()]));

                    dataContainer.add(topics.toArray(new String[topics.size()]));

                    dataContainer.add(subject.toArray(new String[subject.size()]));

                    dataContainer.add(per_marks.toArray(new String[per_marks.size()]));
                    dataContainer.add(cutoffCleared.toArray(new String[cutoffCleared.size()]));

                    new UniversalTableLayout(dataContainer, viewscores.this);


                } else {
                    Toast.makeText(viewscores.this, "Data Empty!!!", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            pDialog.dismiss();

       /* Intent time = new Intent(AttendanceActivity.this,Main.class);
		startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/


        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(viewscores.this, SweetAlertDialog.PROGRESS_TYPE)
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


            String a = spinner_batch.getSelectedItem().toString();
            String b = spinner_center.getSelectedItem().toString();
            String c = spinner_class.getSelectedItem().toString();
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

                    String NAME = json.getString("NAME");
                    Roll = json.getString("ROLL");

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
