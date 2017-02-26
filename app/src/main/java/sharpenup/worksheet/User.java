package sharpenup.worksheet;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.AlertDialogManager;
import sharpenup.customgridview.ConnectionDetector;
import sharpenup.customgridview.PrefManager;
import sharpenup.customgridview.R;
import sharpenup.customgridview.UniversalTableLayout;

@SuppressWarnings("deprecation")
public class User extends Activity {


    TextView date_pend_up;
    TextView date_pend_down;
    TextView tvname;
    TextView tvclass;
    TextView tvbatch;
    TextView tvcenter;
    List<String> categories_code;
    List<String> categories_description;
    List<String> categories_batch;
    List<String> categories_class;
    List<String> categories_center;
    List<String> categories_subject;
    List<String> categories_faculty;
    List<String> categories_topics;
    List<String> categories_student;
    List<String> categories_submitted_Info;
    List<String> categories_completed_Info;
    List<String> categories_Date;
    int flag, flag_btn_click;
    Button sub;
    JSONParser jparser = new JSONParser();
    Dialog dialogReg;
    ListView lv1;
    String send;
    String topics;
    String name;
    String roll;
    String center;
    String batch;
    String cls;
    String subject;
    String NAME_ROLL_PAIR;
    TableLayout t1;
    int total, comp, notcomp;
    //	int flag,flag_btn_click;
    String password_string;
    String username_string;
    List<String[]> dataContainer;
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
        getActionBar().hide();

        dataContainer = new ArrayList<String[]>();
        subject = "";
        total = 0;
        comp = 0;
        notcomp = 0;
        name = "";
        roll = "";
        center = "";
        batch = "";
        cls = "";
        NAME_ROLL_PAIR = "";
        setContentView(R.layout.studentview);
        //	getActionBar().hide();
        pref = new PrefManager(getApplicationContext());
        categories_code = new ArrayList<String>();
        //send =new ArrayList<String>();
        send = "";
        categories_description = new ArrayList<String>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        categories_subject = new ArrayList<String>();
        categories_student = new ArrayList<String>();
        categories_topics = new ArrayList<String>();
        categories_submitted_Info = new ArrayList<String>();
        categories_completed_Info = new ArrayList<String>();
        categories_Date = new ArrayList<String>();
        Bundle extras = getIntent().getExtras();
        //  username_string = extras.getString("EXTRA_USERNAME");
        // password_string = extras.getString("EXTRA_PASSWORD");
        //  name=  extras.getString("name");
        //  roll=  extras.getString("roll");
        //   batch=  extras.getString("batch");
        //   center=   extras.getString("center");
        // cls=	extras.getString("class");
        NAME_ROLL_PAIR = pref.getRoll() + "-" + pref.getName();
        date_pend_up = (TextView) findViewById(R.id.textpdh);
        date_pend_down = (TextView) findViewById(R.id.textpdl);
	/*	tvname= (TextView) findViewById(R.id.welcome);
		tvclass= (TextView) findViewById(R.id.cls);
		tvcenter= (TextView) findViewById(R.id.cntr);
		tvbatch= (TextView) findViewById(R.id.batch);*/

        //tvname.setText("Welcome " +name);
        //  tvclass.setText(cls);
        // tvbatch.setText(batch);
        // tvcenter.setText("    "+center);
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));


    }

    public void MATHLETICS(View v) {
        clearAllLists();
        subject = "MATHLETICS";
        new studentsview().execute();
    }

    public void sci(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(User.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        clearAllLists();
        subject = "SCI-FI";
        new studentsview().execute();
    }

    public void soc(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(User.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        clearAllLists();
        subject = "SOCIALITY";
        new studentsview().execute();
    }

    public void eng(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(User.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        clearAllLists();
        subject = "ENGLISH";
        new studentsview().execute();
    }

    public void other(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(User.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        clearAllLists();
        subject = "OTHERS";
        new studentsview().execute();
    }

    public void all(View v) {
        AlertDialogManager alert = new AlertDialogManager();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(User.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        clearAllLists();
        categories_description.clear();
        subject = "";
        new studentsview().execute();
    }

    //------------------for topics---------------------------------------

    public int counter(String s) {

        int counter = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                counter++;
            }
        }
        return counter;
    }


    //------------------------------------------------------------------------------------

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
        dataContainer.clear();
        dataContainer.add(categories_Date.toArray(new String[categories_Date.size()]));
        if (subject.equals(""))
            dataContainer.add(categories_subject.toArray(new String[categories_subject.size()]));
        dataContainer.add(categories_topics.toArray(new String[categories_topics.size()]));
        dataContainer.add(categories_submitted_Info.toArray(new String[categories_submitted_Info.size()]));
        dataContainer.add(categories_completed_Info.toArray(new String[categories_completed_Info.size()]));

        new UniversalTableLayout(dataContainer, User.this);

    }

    public void clearAllLists() {
        categories_student.clear();
        categories_Date.clear();
        categories_subject.clear();
        categories_completed_Info.clear();
        categories_submitted_Info.clear();
        categories_topics.clear();
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;
        //Toast.makeText(this, c[position] + " checked : " +
        //item.isChecked(), Toast.LENGTH_SHORT).show();
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

    private void viewall() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(User.this, android.R.layout.simple_list_item_1, categories_description);

        lv1.setTextFilterEnabled(true);
        lv1.setAdapter(dataAdapter);
        for (int i = 0; i < dataAdapter.getCount(); i++) {
            lv1.setItemChecked(i, true);
        }

        // ListView Item Click Listener

    }

    private class time extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(User.this, SweetAlertDialog.PROGRESS_TYPE)
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
            //	params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));


            String url = "http://176.32.230.250/anshuli.com/sharpenup3/submit_schedule.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            try {
                // Checking for SUCCESS TAG

                //forjson.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    topics = json.getString("TOPIC");

                    // forjson.add(Roll+"-"+ NAME);
                    //categories_description.add(description);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            User.this.recreate();
      /*  Intent time = new Intent(cnv.this,Main.class);
		startActivity(time);
		finish();*/


        }

    }

    private class studentsview1 extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(User.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("username", username_string));
            //params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("password", password_string));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/studDetails.php";


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


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(User.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("subject", subject));
            //params.add(new BasicNameValuePair("teacher", ));
            //	params.add(new BasicNameValuePair("topics", ));
            params.add(new BasicNameValuePair("center", pref.getCenter()));
            //params.add(new BasicNameValuePair("send", send));
            String url = "http://176.32.230.250/anshuli.com/sharpenup3/getStudents.php";


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
                categories_Date.add("Submission Date");
                categories_subject.add("Subject");
                categories_submitted_Info.add("Submitted");
                categories_completed_Info.add("Completed");
                categories_topics.add("Topic");


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);
                    String student = json.getString("STUDENTS");
                    String sbmtd = json.getString("SUBMITTED");
                    String cmptd = json.getString("COMPLETED");
                    String not_cmptd = json.getString("NOTCOMPLETED");
                    String not_sbmtd = json.getString("NOTSUBMITTED");

                    String topic = json.getString("TOPIC");
                    String subject = json.getString("SUBJECT");
                    String date = json.getString("SUBMIT_DATE");
                    if (student.contains(NAME_ROLL_PAIR)) {
                        categories_student.add(student);
                        categories_Date.add(date);
                        categories_subject.add(subject);
                        if (!(sbmtd.equals("")) && !(cmptd.equals("")) && !(not_cmptd.equals("")) && !(not_sbmtd.equals(""))) {
                            if (sbmtd.contains(NAME_ROLL_PAIR)) {
                                categories_submitted_Info.add("Yes");

                            } else {
                                categories_submitted_Info.add("No");
                            }


                            if (cmptd.contains(NAME_ROLL_PAIR)) {
                                categories_completed_Info.add("Yes");

                            } else {
                                if (not_cmptd.contains(NAME_ROLL_PAIR))
                                    categories_completed_Info.add("No");
                                else {
                                    categories_completed_Info.add("-");
                                }
                            }
                        } else {
                            categories_submitted_Info.add("Submission Due");
                            categories_completed_Info.add("-");
                        }


                        categories_topics.add(topic);
                    }
                }








			      /* Toast.makeText(User.this, "total:"+total+"Completed:"+comp+"notcompleted:"+notcomp, 1).show();
			       int perComp= (int)((comp * 100.0f) / total);
			       categories_code.add("Completed :"+perComp+"%");
			       int perNotComp=(int)((notcomp * 100.0f) / total);
			       categories_code.add("Not Completed :"+perNotComp+"%");*/
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(User.this, "Internet connection error!!!", Toast.LENGTH_SHORT).show();
            }

            setlist();

        }

    }

}

