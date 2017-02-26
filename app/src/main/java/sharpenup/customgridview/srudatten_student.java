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
import android.view.View;
import android.view.Window;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressWarnings("deprecation")
public class srudatten_student extends Activity {

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
    List<String[]> dataContainer = new ArrayList<String[]>();
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
    int crashPreventer;
    JSONObject json;
    //List<String> per_date ;
    int flag, flag_btn_click;
    Button submit;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String present;
    String absent;
    String leave;
    private PrefManager pref;
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
        setContentView(R.layout.studatten_student);
        getActionBar().hide();
        per_class = new ArrayList<String>();
        per_batch = new ArrayList<String>();
        per_center = new ArrayList<String>();
        per_faculty = new ArrayList<String>();
        per_subject = new ArrayList<String>();


        crashPreventer = 0;

        pref = new PrefManager(getApplicationContext());

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
        date_pend_up = (TextView) findViewById(R.id.textpdh_student);
        date_pend_down = (TextView) findViewById(R.id.textpdl_student);
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));


        submit = (Button) findViewById(R.id.submit_cal_student);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialogManager alert = new AlertDialogManager();
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                // Check if Internet present
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present
                    alert.showAlertDialog(srudatten_student.this,
                            "Internet Connection Error",
                            "Please connect to working Internet connection", false);
                    // stop executing code by return
                    return;
                }

                new time().execute();

            }
        });


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

        dialogReg = new Dialog(srudatten_student.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.students);


        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(lv1.CHOICE_MODE_SINGLE);
        lv1.setTextFilterEnabled(true);
        viewall();

        //dialogReg.show();
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

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(srudatten_student.this, android.R.layout.simple_list_item_checked, categories_code);

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


                // ListView Clicked item value
                String itemValue = (String) lv1.getItemAtPosition(position);

                if (item.isChecked()) {


                    name_roll = itemValue;
                }


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

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(srudatten_student.this, SweetAlertDialog.PROGRESS_TYPE)
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

            params.add(new BasicNameValuePair("batch", pref.getBatch()));
            params.add(new BasicNameValuePair("center", pref.getCenter()));
            params.add(new BasicNameValuePair("class", pref.getclass()));
            params.add(new BasicNameValuePair("datehigh", date_pend_up.getText().toString()));
            params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            String url = "http://176.32.230.250/anshuli.com/sharpenup2/peformance.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                // Checking for SUCCESS TAG
                ArrayList<String> per_present = new ArrayList<String>();

                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                ArrayList<String> per_date = new ArrayList<String>();
                ArrayList<String> per_day = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> subject = new ArrayList<String>();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    crashPreventer = 1;
                    json = account.getJSONObject(i);

                    String s = json.getString("STUDENTS");
                    String p = json.getString("PRESENT");
                    String a = json.getString("ABSENT");
                    String l = json.getString("GEN");
                    per_class.add(json.getString("CLASS"));
                    per_batch.add(json.getString("BATCH"));

                    per_faculty.add(json.getString("FACULTY"));
                    per_center.add(json.getString("CENTER"));

                    String rollNamePair = pref.getRoll() + "-" + pref.getName();

                    if (s.contains(rollNamePair)) {

                        per_day.add(json.getString("DAY"));

                        subject.add(json.getString("SUBJECT"));
                        per_date.add(json.getString("DATE_HIGH"));


                        if (p.contains(rollNamePair)) {
                            per_present.add("PRESENT");

                        } else if (a.contains(rollNamePair)) {

                            per_present.add("ABSENT");

                        } else if (l.contains(rollNamePair)) {
                            per_present.add("LEAVE");

                        } else {
                            per_present.add("N/A");

                        }


                    } else {

    			        		/*per_present.add("-");
			        			per_absent.add("-");
			        			per_leave.add("-");
			        			per_noclass.add("NO CLASS FOR YOU");*/

                    }
                    name.add(rollNamePair);

                    dataContainer.clear();
                    dataContainer.add(per_date.toArray(new String[per_date.size()]));
                    dataContainer.add(per_day.toArray(new String[per_day.size()]));
                    dataContainer.add(per_present.toArray(new String[per_present.size()]));


                    dataContainer.add(subject.toArray(new String[subject.size()]));


                }
                if (crashPreventer == 1) {
                    crashPreventer = 0;
                    new UniversalTableLayout(dataContainer, srudatten_student.this);

                } else {
                    Toast.makeText(srudatten_student.this, "Date empty!!!", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(srudatten_student.this, "Internet connection error!!!", Toast.LENGTH_SHORT).show();
            }


            pDialog.dismiss();

       /* Intent time = new Intent(AttendanceActivity.this,Main.class);
		startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/


        }

    }

}
