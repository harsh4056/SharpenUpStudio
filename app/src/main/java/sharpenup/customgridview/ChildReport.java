package sharpenup.customgridview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class ChildReport extends Activity {
    Button showComplainStatus, showRemarks;
    TextView datehigh;
    TextView datelow;
    int flag;
    List<String> list_remarks;
    List<String> list_reason;
    List<String> list_remarksBy;
    List<String> list_date_timeOfRemarks;
    List<String> complain;
    List<String> action_to_be_taken;
    List<String> action_taken;
    List<String> complain_date;
    List<String> actiondate_date;
    List<String[]> dataContainer = new ArrayList<String[]>();
    PrefManager pref;
    JSONParser jparser = new JSONParser();
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_report);
        getActionBar().hide();
        list_date_timeOfRemarks = new ArrayList<String>();
        list_reason = new ArrayList<String>();
        list_remarksBy = new ArrayList<String>();
        list_remarks = new ArrayList<String>();

        complain = new ArrayList<String>();
        action_to_be_taken = new ArrayList<String>();
        action_taken = new ArrayList<String>();
        complain_date = new ArrayList<String>();
        actiondate_date = new ArrayList<String>();


        pref = new PrefManager(getApplicationContext());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        datehigh = (TextView) findViewById(R.id.textpdh);
        datelow = (TextView) findViewById(R.id.textpdl);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));

        showComplainStatus = (Button) findViewById(R.id.createatten);
        showRemarks = (Button) findViewById(R.id.viewatten);
        showRemarks.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new studentTool().execute();

            }
        });

        showComplainStatus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new complainTool().execute();

            }
        });


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
        datelow.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
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
        datehigh.setText(new StringBuilder().append(year).append("-")
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ChildReport.this, MainActivityNew.class);
        startActivity(i);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
        ChildReport.this.finish();
    }

    private class studentTool extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ChildReport.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("datelow", datelow.getText().toString()));
            params.add(new BasicNameValuePair("datehigh", datehigh.getText().toString()));
            params.add(new BasicNameValuePair("roll", pref.getRoll()));
            String url = "http://176.32.230.250/anshuli.com/sharpenup/getObservation.php";


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

                list_reason.clear();
                list_remarksBy.clear();
                list_remarks.clear();
                list_date_timeOfRemarks.clear();
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();
                list_reason.add("REASON");
                list_remarksBy.add("REMARKS_BY");
                list_remarks.add("REMARKS");
                list_date_timeOfRemarks.add("DateOfRemark");


                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    list_reason.add(json.getString("REASON"));
                    list_remarksBy.add(json.getString("REMARKS_BY"));
                    list_remarks.add(json.getString("REMARKS"));
                    list_date_timeOfRemarks.add(json.getString("DateOfRemark"));


                }


                dataContainer.clear();
                dataContainer.add(list_date_timeOfRemarks.toArray(new String[list_date_timeOfRemarks.size()]));
                dataContainer.add(list_remarks.toArray(new String[list_remarks.size()]));
                dataContainer.add(list_reason.toArray(new String[list_reason.size()]));
                dataContainer.add(list_remarksBy.toArray(new String[list_remarksBy.size()]));


                new UniversalTableLayout(dataContainer, ChildReport.this);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ChildReport.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }



		      /*  Intent time = new Intent(cnv.this,Main.class);
				startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
				finish();*/


        }

    }

    private class complainTool extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ChildReport.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("datelow", datelow.getText().toString()));
            params.add(new BasicNameValuePair("datehigh", datehigh.getText().toString()));
            params.add(new BasicNameValuePair("roll", pref.getRoll()));
            String url = "http://176.32.230.250/anshuli.com/sharpenup/getComplaint.php";


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

                complain.clear();
                action_to_be_taken.clear();
                action_taken.clear();
                complain_date.clear();
                actiondate_date.clear();
                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();
                complain.add("Complain");
                action_to_be_taken.add("Action to be taken");
                action_taken.add("Action taken");
                complain_date.add("Complain Date");
                actiondate_date.add("Action Date");


                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    complain.add(json.getString("DETAIL"));
                    action_to_be_taken.add(json.getString("ACTION_TAKEN"));
                    action_taken.add(json.getString("ACTION_TAKEN_FLAG"));
                    complain_date.add(json.getString("Date_LOG"));
                    actiondate_date.add(json.getString("ACTION_BY_TIME"));


                }


                dataContainer.clear();
                dataContainer.add(complain.toArray(new String[complain.size()]));
                dataContainer.add(action_to_be_taken.toArray(new String[action_to_be_taken.size()]));
                dataContainer.add(action_taken.toArray(new String[action_taken.size()]));
                dataContainer.add(complain_date.toArray(new String[complain_date.size()]));
                dataContainer.add(actiondate_date.toArray(new String[actiondate_date.size()]));


                new UniversalTableLayout(dataContainer, ChildReport.this);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ChildReport.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }



		      /*  Intent time = new Intent(cnv.this,Main.class);
				startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
				finish();*/


        }

    }

}
