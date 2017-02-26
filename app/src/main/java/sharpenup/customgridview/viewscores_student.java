package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import sharpenup.androidbegin.viewpagertutorial.view_scores;

@SuppressWarnings("deprecation")
public class viewscores_student extends Activity {
    Spinner spinner_batch;
    Spinner spinner_class;
    Spinner spinner_center;
    Spinner spinner_fac;
    Spinner spinner_subject;
    String id;
    String up, down;
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
    List<String[]> dataContainer;
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewscores_student);
        getActionBar().hide();
        per_class = new ArrayList<String>();
        per_batch = new ArrayList<String>();
        per_center = new ArrayList<String>();
        per_faculty = new ArrayList<String>();
        per_subject = new ArrayList<String>();

        pref = new PrefManager(getApplicationContext());
        dataContainer = new ArrayList<String[]>();
        scores = new ArrayList<Double>();
        crashPreventer = 0;


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
        date_pend_up = (TextView) findViewById(R.id.textpdh_stud_1);
        date_pend_down = (TextView) findViewById(R.id.textpdl_stud_1);

        down = date_pend_down.getText().toString();

        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));


        submit = (Button) findViewById(R.id.submit_cal_stud_1);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialogManager alert = new AlertDialogManager();
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                // Check if Internet present
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present
                    alert.showAlertDialog(viewscores_student.this,
                            "Internet Connection Error",
                            "Please connect to working Internet connection", false);
                    // stop executing code by return
                    return;
                }

                Toast.makeText(getApplicationContext(),
                        leave, Toast.LENGTH_SHORT)
                        .show();

                new percentile().execute();

            }
        });
//new studentsview().execute();
/*Button students;
students = (Button)findViewById(R.id.students_submit_1);

students.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {





			}
});*/

        // TODO Auto-generated catch block


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

    protected void setlist() {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(viewscores_student.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv1.setTextFilterEnabled(true);
        viewall();

        //dialogReg.show();
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;
        //Toast.makeText(this, c[position] + " checked : " +
        //item.isChecked(), Toast.LENGTH_SHORT).show();
    }

    private void viewall() {

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(viewscores_student.this, android.R.layout.simple_list_item_checked, categories_code);

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
			            }
			            */

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


        final SweetAlertDialog pDialog = new SweetAlertDialog(viewscores_student.this, SweetAlertDialog.PROGRESS_TYPE)
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
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
				/*//params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
				params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
				params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
				params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
				params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));*/

                params.add(new BasicNameValuePair("batch", pref.getBatch()));
                params.add(new BasicNameValuePair("center", pref.getCenter()));
                params.add(new BasicNameValuePair("class", pref.getclass()));
                params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
                params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
                String url = "http://176.32.230.250/anshuli.com/sharpenup2/viewscores.php";
                json = jparser.makeHttpRequest(url, "POST", params);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String s = json.getString("MARKS_STUDENT");


                    String[] parts = s.split("Marks");
                    a = parts.length;
                    no = (int) (0.2 * (a - 1));
                    for (int j = 1; j < a; j++) {

                        number = parts[j];
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


                        //Collections.reverse(	scores);
                    }

                    if (scores != null) {
                        Collections.reverse(scores);
                        item = scores.get(no);
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            pDialog.dismiss();

            new time().execute();


        }

    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(viewscores_student.this, SweetAlertDialog.PROGRESS_TYPE)
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
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
			/*//params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
			params.add(new BasicNameValuePair("batch", spinner_batch.getSelectedItem().toString()));
			params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
			params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));
			params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));*/

                params.add(new BasicNameValuePair("batch", pref.getBatch()));
                params.add(new BasicNameValuePair("center", pref.getCenter()));
                params.add(new BasicNameValuePair("class", pref.getclass()));
                params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
                params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
                String url = "http://176.32.230.250/anshuli.com/sharpenup2/viewscores.php";


                json = jparser.makeHttpRequest(url, "POST", params);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                // Checking for SUCCESS TAG
                ArrayList<String> per_marks = new ArrayList<String>();
                ArrayList<String> per_absent = new ArrayList<String>();
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();
                ArrayList<String> per_leave = new ArrayList<String>();
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

                    per_leave.add(json.getString("TOPIC"));
                    per_class.add(json.getString("CLASS"));
                    per_batch.add(json.getString("BATCH"));
                    subject.add(json.getString("SUBJECT"));

                    per_center.add(json.getString("CENTER"));
                    per_day.add(json.getString("DAY"));

                    per_date.add(json.getString("DATE"));
                    if (s.contains("Roll Number =" + pref.getRoll())) {
                        String cutoff = json.getString("CUTOFF");
                        String[] parts = s.split("Roll Number =" + pref.getRoll());
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
                            per_marks.add(":( Student was absent.");

                        else
                            per_marks.add("Student has got" + get + " out of " + final_out);

                        double per_get = Double.parseDouble(get.trim());
                        double cutoff_decimal = Double.parseDouble(cutoff.trim());

                        if (per_get >= cutoff_decimal) {
                            per_absent.add("Hurray!! Student has passed the cut-off and the cut-off is " + cutoff_decimal);
                        } else {

                            per_absent.add(" :( Student has not passed the cut-off and the cut-off is " + cutoff_decimal);

                        }

                    }


                    name.add("Roll Number =" + pref.getRoll());


                    b = new Bundle();
                    b.putStringArrayList("present", per_marks);
                    b.putStringArrayList("day", per_day);
                    b.putStringArrayList("leave", per_leave);
                    b.putStringArrayList("absent", per_absent);

                    b.putStringArrayList("date", per_date);
                    b.putStringArrayList("name", name);
                    b.putStringArrayList("subject", subject);

                }
                if (crashPreventer == 1) {
                    crashPreventer = 0;
                    Intent i = new Intent(viewscores_student.this, view_scores.class);
                    i.putExtras(b);


                    startActivity(i);
                    overridePendingTransition(R.anim.open_next, R.anim.close_main);
                } else {
                    Toast.makeText(viewscores_student.this, "Data empty", Toast.LENGTH_SHORT).show();
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
        final SweetAlertDialog pDialog = new SweetAlertDialog(viewscores_student.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("batch", pref.getBatch()));
            params.add(new BasicNameValuePair("center", pref.getCenter()));
            params.add(new BasicNameValuePair("class", pref.getclass()));

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
