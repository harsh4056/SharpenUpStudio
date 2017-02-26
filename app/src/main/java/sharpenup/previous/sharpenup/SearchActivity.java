package sharpenup.previous.sharpenup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.R;


@SuppressWarnings("deprecation")
public class SearchActivity extends Activity {


    EditText et;
    EditText et2;
    EditText et3;
    EditText et01;
    EditText datehigh;
    EditText datelow;
    String back_flag;

    DBAdapter myDb;
    String name[];
    int ko;
    List<String> data;
    String date_view;
    int flag, flag_btn_click;
    DatePickerDialog.OnDateSetListener date;
    private Calendar calendar;
    private TextView date_pend_down, date_pend_up, date_log_down, date_log_up;
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
            else if (flag == 2)
                showDate2(arg1, strm, strd);
            else if (flag == 3)
                showDate3(arg1, strm, strd);
        }
    };


    //--------------------------------------------Date Time ranges-------------------------------------------

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        getActionBar().hide();
        Intent intent = getIntent();

        back_flag = intent.getStringExtra("back");


        date_pend_down = (TextView) findViewById(R.id.textpdl);
        date_pend_up = (TextView) findViewById(R.id.textpdh);
        date_log_down = (TextView) findViewById(R.id.textdll);
        date_log_up = (TextView) findViewById(R.id.textdlh);
        et = (EditText) findViewById(R.id.editTextName);
        et2 = (EditText) findViewById(R.id.editTextper_);
        et3 = (EditText) findViewById(R.id.editTextbatch);
        et01 = (EditText) findViewById(R.id.EditTextCentre);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


        //getActionBar().hide();


        data = new ArrayList<String>();
        name = new String[100];
        String url = "http://176.32.230.250/anshuli.com/sharpenup/search.php";
        new searching().execute(url);

        openDB();


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

    public void date_logged_low(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
        flag = 2;
    }

    public void date_logged_high(View view) {
        showDialog(999);
        // Toast.makeText(getApplicationContext(), "You Rock DUde!!", Toast.LENGTH_SHORT) .show();
        flag = 3;
    }

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
    //-----------------------------------------------------------------------------------------------

    private void showDate2(int year, String month, String day) {
        date_log_down.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    private void showDate3(int year, String month, String day) {
        date_log_up.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {

        myDb.deleteAll();
        myDb.close();
    }

    public int dateTOint(String date) {
        int foo = 0;
        if (date != "") {
            String[] parts = date.split("-");
            String ndate = parts[0] + parts[1] + parts[2];

            foo = Integer.parseInt(ndate);

        }

        return foo;


    }

    public String intTOdate(int date) {
        if (date != 0) {
            String x = Integer.toString(date);
            x = x.substring(0, 4) + "-" + x.substring(4, 6) + "-" + x.substring(6, x.length());
            return x;
        } else return "0000-00-00";

    }

    public void onClick_AddRecord(View v) {
        //displayText("Clicked add record!");
        closeDB();
        this.recreate();
    }

    //----------------------------------------------Button Clicks------------------
    public void name(View v) {
        //displayText("Clicked add record!");
        String nameSearch = et.getText().toString();
        String per = et2.getText().toString();
        String batch = et3.getText().toString();
        String centre = et01.getText().toString();

        String pdl = date_pend_down.getText().toString();
        String pdh = date_pend_up.getText().toString();
        String ddl = date_log_down.getText().toString();
        String ddh = date_log_up.getText().toString();
        flag_btn_click = 0;
        Cursor cursor = myDb.getSelectedRows(nameSearch, batch, centre, per, dateTOint(pdl), dateTOint(pdh), dateTOint(ddl), dateTOint(ddh), flag_btn_click);
        displayRecordSet(cursor);
        et.setText("");
        et2.setText("");
        et3.setText("");
        et01.setText("");
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


    }

    public void per_res(View v) {
        //displayText("Clicked add record!");
        String nameSearch = et.getText().toString();
        String per = et2.getText().toString();
        String batch = et3.getText().toString();
        String centre = et01.getText().toString();

        String pdl = date_pend_down.getText().toString();
        String pdh = date_pend_up.getText().toString();
        String ddl = date_log_down.getText().toString();
        String ddh = date_log_up.getText().toString();
        flag_btn_click = 1;
        Cursor cursor = myDb.getSelectedRows(nameSearch, batch, centre, per, dateTOint(pdl), dateTOint(pdh), dateTOint(ddl), dateTOint(ddh), flag_btn_click);
        displayRecordSet(cursor);
        et.setText("");
        et2.setText("");
        et3.setText("");
        et01.setText("");
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


    }

    public void batch(View v) {
        //displayText("Clicked add record!");
        String nameSearch = et.getText().toString();
        String per = et2.getText().toString();
        String batch = et3.getText().toString();
        String centre = et01.getText().toString();

        String pdl = date_pend_down.getText().toString();
        String pdh = date_pend_up.getText().toString();
        String ddl = date_log_down.getText().toString();
        String ddh = date_log_up.getText().toString();
        flag_btn_click = 2;
        Cursor cursor = myDb.getSelectedRows(nameSearch, batch, centre, per, dateTOint(pdl), dateTOint(pdh), dateTOint(ddl), dateTOint(ddh), flag_btn_click);
        displayRecordSet(cursor);
        et.setText("");
        et2.setText("");
        et3.setText("");
        et01.setText("");
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


    }

    public void centre(View v) {
        //displayText("Clicked add record!");
        String nameSearch = et.getText().toString();
        String per = et2.getText().toString();
        String batch = et3.getText().toString();
        String centre = et01.getText().toString();

        String pdl = date_pend_down.getText().toString();
        String pdh = date_pend_up.getText().toString();
        String ddl = date_log_down.getText().toString();
        String ddh = date_log_up.getText().toString();
        flag_btn_click = 3;
        Cursor cursor = myDb.getSelectedRows(nameSearch, batch, centre, per, dateTOint(pdl), dateTOint(pdh), dateTOint(ddl), dateTOint(ddh), flag_btn_click);
        displayRecordSet(cursor);
        et.setText("");
        et2.setText("");
        et3.setText("");
        et01.setText("");
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


    }

    public void log_date(View v) {
        //displayText("Clicked add record!");
        String nameSearch = et.getText().toString();
        String per = et2.getText().toString();
        String batch = et3.getText().toString();
        String centre = et01.getText().toString();

        String pdl = date_pend_down.getText().toString();
        String pdh = date_pend_up.getText().toString();
        String ddl = date_log_down.getText().toString();
        String ddh = date_log_up.getText().toString();
        flag_btn_click = 4;
        Cursor cursor = myDb.getSelectedRows(nameSearch, batch, centre, per, dateTOint(pdl), dateTOint(pdh), dateTOint(ddl), dateTOint(ddh), flag_btn_click);
        displayRecordSet(cursor);
        et.setText("");
        et2.setText("");
        et3.setText("");
        et01.setText("");
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


    }

    public void pend_date(View v) {
        //displayText("Clicked add record!");
        String nameSearch = et.getText().toString();
        String per = et2.getText().toString();
        String batch = et3.getText().toString();
        String centre = et01.getText().toString();

        String pdl = date_pend_down.getText().toString();
        String pdh = date_pend_up.getText().toString();
        String ddl = date_log_down.getText().toString();
        String ddh = date_log_up.getText().toString();
        flag_btn_click = 5;
        Cursor cursor = myDb.getSelectedRows(nameSearch, batch, centre, per, dateTOint(pdl), dateTOint(pdh), dateTOint(ddl), dateTOint(ddh), flag_btn_click);
        displayRecordSet(cursor);
        et.setText("");
        et2.setText("");
        et3.setText("");
        et01.setText("");
        showDate0(year, appending0(month + 1), appending0(day));
        showDate1(year, appending0(month + 1), appending0(day));
        showDate2(year, appending0(month + 1), appending0(day));
        showDate3(year, appending0(month + 1), appending0(day));


    }

    public void onSearchByName(View v) {
        //displayText("Clicked clear all!");


        //myDb.deleteAll();
    }

    public void onClick_DisplayRecords(View v) {
        //displayText("Clicked display record!");
        data.clear();
        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);
    }

    // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor) {

        Cursor updat_cursor = cursor;
        String message = "";
        // populate the message from the cursor
        data.clear();
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                String studentParent = cursor.getString(DBAdapter.COL_Parent);
                String handled = cursor.getString(DBAdapter.COL_handled);
                String Batch = cursor.getString(DBAdapter.COL_Batch);
                String MODE = cursor.getString(DBAdapter.COL_MODE);
                String NATURE = cursor.getString(DBAdapter.COL_NATURE);
                String code_nature = cursor.getString(DBAdapter.COL_code_nature);
                String description_nature = cursor.getString(DBAdapter.COL_description_nature);
                String remarks_nature = cursor.getString(DBAdapter.COL_remarks_nature);
                String DETAIL = cursor.getString(DBAdapter.COL_DETAIL);
                String REMARKS = cursor.getString(DBAdapter.COL_REMARKS);
                String ACTION_TAKEN_FLAG = cursor.getString(DBAdapter.COL_ACTION_TAKEN_FLAG);
                String ACTION_TAKEN = cursor.getString(DBAdapter.COL_ACTION_TAKEN);
                String ACTION_TAKEN_CODE = cursor.getString(DBAdapter.COL_ACTION_TAKEN_CODE);
                String ACTION_TAKEN_DESCRIPTION = cursor.getString(DBAdapter.COL_ACTION_TAKEN_DESCRIPTION);
                String ACTION_TAKEN_REMARKS = cursor.getString(DBAdapter.COL_ACTION_TAKEN_REMARKS);
                int DATE = cursor.getInt(DBAdapter.COL_DATE);
                String PERSON_RESPONSIBLE = cursor.getString(DBAdapter.COL_PERSON_RESPONSIBLE);
                String Time = cursor.getString(DBAdapter.COL_Time);
                int Date_LOG = cursor.getInt(DBAdapter.COL_Date_LOG);
                String centre = cursor.getString(DBAdapter.COL_centre);
                String act_by = cursor.getString(DBAdapter.COL_completed_by);
                String act_by_date = cursor.getString(DBAdapter.COL_completed_date);
                if (remarks_nature == "")
                    remarks_nature = "-------";
                // Append data to the message:
                message =
                        " Name : " + name
                                + "\r\n Date of Action Logged : " + intTOdate(Date_LOG)
                                + "\r\n handled : " + handled
                                + "\r\n MODE : " + MODE
                                + "\r\n Pending Date of Action : " + intTOdate(DATE)
                                + "\r\n Action taken : " + ACTION_TAKEN_FLAG
                                + "\r\n Action taken by: " + act_by
                                + "\r\n Action taken date: " + act_by_date

                                + "\r\n Person Responsible : " + PERSON_RESPONSIBLE
                                + "\r\n Nature : " + NATURE
                                + "\r\n Centre : " + centre
                                + "\r\n Batch : " + Batch
                ;
                data.add(message);
            } while (cursor.moveToNext());

            //--------------------------------SQLITE DATA UPDATION---------------------------------


        }
        //dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);


        myDb.deleteAll();

        if (updat_cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                String studentParent = cursor.getString(DBAdapter.COL_Parent);
                String handled = cursor.getString(DBAdapter.COL_handled);
                String Batch = cursor.getString(DBAdapter.COL_Batch);
                String MODE = cursor.getString(DBAdapter.COL_MODE);
                String NATURE = cursor.getString(DBAdapter.COL_NATURE);
                String code_nature = cursor.getString(DBAdapter.COL_code_nature);
                String description_nature = cursor.getString(DBAdapter.COL_description_nature);
                String remarks_nature = cursor.getString(DBAdapter.COL_remarks_nature);
                String DETAIL = cursor.getString(DBAdapter.COL_DETAIL);
                String REMARKS = cursor.getString(DBAdapter.COL_REMARKS);
                String ACTION_TAKEN_FLAG = cursor.getString(DBAdapter.COL_ACTION_TAKEN_FLAG);
                String ACTION_TAKEN = cursor.getString(DBAdapter.COL_ACTION_TAKEN);
                String ACTION_TAKEN_CODE = cursor.getString(DBAdapter.COL_ACTION_TAKEN_CODE);
                String ACTION_TAKEN_DESCRIPTION = cursor.getString(DBAdapter.COL_ACTION_TAKEN_DESCRIPTION);
                String ACTION_TAKEN_REMARKS = cursor.getString(DBAdapter.COL_ACTION_TAKEN_REMARKS);
                int DATE = cursor.getInt(DBAdapter.COL_DATE);
                String PERSON_RESPONSIBLE = cursor.getString(DBAdapter.COL_PERSON_RESPONSIBLE);
                String Time = cursor.getString(DBAdapter.COL_Time);
                int Date_LOG = cursor.getInt(DBAdapter.COL_Date_LOG);
                String centre = cursor.getString(DBAdapter.COL_centre);
                String act_by = cursor.getString(DBAdapter.COL_completed_by);
                String act_by_date = cursor.getString(DBAdapter.COL_completed_date);

                //-------------------InSErting searched rows
                myDb.insertRow(
                        id,
                        name,
                        handled,
                        studentParent,
                        Batch,
                        centre,
                        MODE,
                        NATURE,
                        code_nature,
                        description_nature,
                        remarks_nature,
                        DETAIL,
                        REMARKS,
                        ACTION_TAKEN_FLAG,
                        ACTION_TAKEN,
                        ACTION_TAKEN_CODE,
                        ACTION_TAKEN_DESCRIPTION,
                        ACTION_TAKEN_REMARKS,
                        DATE,
                        PERSON_RESPONSIBLE,
                        Time,
                        Date_LOG,
                        act_by,
                        act_by_date
                );


            } while (updat_cursor.moveToNext());

            //--------------------------------SQLITE DATA UPDATION---------------------------------


        }


        // Close the cursor to avoid a resource leak.
        cursor.close();
        updat_cursor.close();
        Intent intent = new Intent(SearchActivity.this, SearchResults.class);
        intent.putStringArrayListExtra("data", (ArrayList<String>) data);
        startActivity(intent);
        finish();
    }

    public String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            //  Toast.makeText(MainActivity, e.getLocalizedMessage(), 1000).show();
        }
        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    @Override
    public void onBackPressed() {

        if (back_flag.equals("button")) {
            Intent intent = new Intent(SearchActivity.this, buttons.class);
            startActivity(intent);
            finish();
        } else if (back_flag.equals("admin"))

        {
            Intent intent = new Intent(SearchActivity.this, admin.class);
            startActivity(intent);
            finish();


        }
    }

/* public void onBackPressed()
 {
 	Intent intent = new Intent();
 	
     startActivity(intent);
     finish();
 }*/

    private class searching extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(SearchActivity.this, SweetAlertDialog.PROGRESS_TYPE)
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
            //visible
            return GET(urls[0]);


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {  //gone
            // //System.out.println("RESULT : " + result);

            JSONObject json;
            try {
                // Checking for SUCCESS TAG


                json = new JSONObject(result);
                JSONArray account = json.getJSONArray("complain");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);


                    int S_NO = json.getInt("S_No");
                    String NAME = json.getString("NAME");
                    String PARENT = json.getString("PARENT");
                    String Batch = json.getString("PHONE");
                    String CENTRE = json.getString("Centre");
                    String HANDLED = json.getString("HANDLED");
                    String MODE = json.getString("MODE");
                    String NATURE = json.getString("NATURE");
                    String code_nature = json.getString("code_nature");
                    String description_nature = json.getString("description_nature");
                    String remarks_nature = json.getString("remarks_nature");
                    String DETAIL = json.getString("DETAIL");
                    String REMARKS = json.getString("REMARKS");
                    String ACTION_TAKEN_FLAG = json.getString("ACTION_TAKEN_FLAG");
                    String ACTION_TAKEN = json.getString("ACTION_TAKEN");
                    String ACTION_TAKEN_CODE = json.getString("ACTION_TAKEN_CODE");
                    String ACTION_TAKEN_DESCRIPTION = json.getString("ACTION_TAKEN_DESCRIPTION");
                    String ACTION_TAKEN_REMARKS = json.getString("ACTION_TAKEN_REMARKS");
                    int DATE = dateTOint(json.getString("DATE"));
                    String PERSON_RESPONSIBLE = json.getString("PERSON_RESPONSIBLE");
                    String Time = json.getString("Time");
                    int Date_LOG = dateTOint(json.getString("Date_LOG"));
                    String ACT_by = json.getString("ACTION_BY");
                    String ACT_by_date = json.getString("ACTION_BY_TIME");


                    myDb.insertRow(
                            S_NO,
                            NAME,
                            HANDLED,
                            PARENT,
                            Batch,
                            CENTRE,
                            MODE,
                            NATURE,
                            code_nature,
                            description_nature,
                            remarks_nature,
                            DETAIL,
                            REMARKS,
                            ACTION_TAKEN_FLAG,
                            ACTION_TAKEN,
                            ACTION_TAKEN_CODE,
                            ACTION_TAKEN_DESCRIPTION,
                            ACTION_TAKEN_REMARKS,
                            DATE,
                            PERSON_RESPONSIBLE,
                            Time,
                            Date_LOG,
                            ACT_by,
                            ACT_by_date
                    );


                    // Query for the record we just added.

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            pDialog.dismiss();

            //Toast.makeText(SearchActivity.this, "done", Toast.LENGTH_SHORT).show();


        }

    }

}


