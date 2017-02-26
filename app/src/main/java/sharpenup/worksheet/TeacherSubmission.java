package sharpenup.worksheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.PrefManager;
import sharpenup.customgridview.R;


public class TeacherSubmission extends Activity {


    ListView lv0;
    ListView lv1;
    String teacher;
    Dialog dialogReg;
    List<String> ultimate;
    List<String> s_no;
    List<String> studs;
    String submitted;
    String notSubmitted;
    Button b1;
    String sid;
    JSONParser jparser = new JSONParser();
    private PrefManager pref;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachersubmission);
        submitted = "";
        notSubmitted = "";
        lv0 = (ListView) findViewById(R.id.lv1);
        Intent i = getIntent();
        pref = new PrefManager(getApplicationContext());

        teacher = pref.getTeacher();
        ultimate = new ArrayList<String>();
        s_no = new ArrayList<String>();

        studs = new ArrayList<String>();
        new studentsview().execute();

    }

    protected void setlist(List<String> dsip_Students) {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(TeacherSubmission.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.studentworkbook);

        b1 = (Button) dialogReg.findViewById(R.id.button1);
        lv1 = (ListView) dialogReg.findViewById(R.id.listView1);
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        lv1.setTextFilterEnabled(true);


        b1.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                int cntChoice = lv1.getCount();


                SparseBooleanArray sparseBooleanArray = lv1.getCheckedItemPositions();

                for (int i = 0; i < cntChoice; i++) {

                    if (sparseBooleanArray.get(i) == true) {
                        submitted += lv1.getItemAtPosition(i).toString() + ",";
                    } else if (sparseBooleanArray.get(i) == false) {
                        notSubmitted += lv1.getItemAtPosition(i).toString() + ",";
                    }

                }
                new time().execute();
                Toast.makeText(getBaseContext(), submitted, Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), notSubmitted, Toast.LENGTH_SHORT).show();
                TeacherSubmission.this.recreate();    // TODO Auto-generated method stub
                dialogReg.dismiss();
            }
        });
        viewall(dsip_Students);

        dialogReg.show();
    }

    private void viewall(List<String> dsip_Students) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_checked, dsip_Students);

        lv1.setTextFilterEnabled(true);
        lv1.setAdapter(dataAdapter);
        for (int i = 0; i < dataAdapter.getCount(); i++) {
            lv1.setItemChecked(i, true);
        }

        // ListView Item Click Listener

    }

    public String addedDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 3);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String output = sdf1.format(c.getTime());
        //   Toast.makeText(TeacherSubmission.this, "forwarded : " +output, 1).show();
        return output;

    }

    public String currentDate() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        //   Toast.makeText(TeacherSubmission.this, "Current : " +formattedDate, 1).show();
        return formattedDate;

    }

    private void triggerNotification() {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.tasks;
        System.currentTimeMillis();
        getApplicationContext();
        CharSequence contentTitle = "WorkBooks";
        CharSequence contentText = "Check The WorkBooks";
        Intent notificationIntent = new Intent(this, TeacherSubmission.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);


        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.BigTextStyle(builder)
                    .bigText(contentText).build();
        }

        // and this
        final int HELLO_ID = 1;
        mNotificationManager.notify(HELLO_ID, notification);

    }

    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(TeacherSubmission.this, SweetAlertDialog.PROGRESS_TYPE)
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

            params.add(new BasicNameValuePair("id", sid));
            params.add(new BasicNameValuePair("submitted", submitted));
            params.add(new BasicNameValuePair("notsubmitted", notSubmitted));
            //params.add(new BasicNameValuePair("leave", leave));
            String url = "http://176.32.230.250/anshuli.com/sharpenup3/setStudentsWorkbook.php";

            JSONObject json = jparser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

       /* Intent time = new Intent(AttendanceActivity.this,Main.class);
		startActivity(time);
		finish();*/


        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(TeacherSubmission.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("teacher", teacher));
            //params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            //params.add(new BasicNameValuePair("class", spinner_class.getSelectedItem().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup3/getStudentsWorkbook.php";


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

                    String students = json.getString("STUDENTS");
                    String id = json.getString("S_NO");
                    String myclass = json.getString("CLASS");
                    String center = json.getString("CENTER");
                    String batch = json.getString("BATCH");
                    String subject = json.getString("SUBJECT");
                    String topic = json.getString("TOPIC");
                    String date = json.getString("SUBMIT_DATE");
                    String submitted1 = json.getString("SUBMITTED");
                    String flag = "";
                    if (submitted1.equals("")) {
                        flag = "Submission due";
                        ultimate.add(" Centre : " + center
                                + "\r\n Class : " + myclass
                                + "\r\n Batch : " + batch
                                + "\r\n Subject : " + subject
                                + "\r\n Topic : " + topic
                                + "\r\n Date : " + date
                                + "\r\n Status : " + flag);
                        s_no.add(id);
                        studs.add(students);


                        if (addedDate(date).trim().equals(currentDate().trim())) {
                            triggerNotification();
                        }
                    } else
                        flag = "Done";


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(TeacherSubmission.this, android.R.layout.simple_list_item_1, ultimate);
            lv0.setAdapter(dataAdapter);
            lv0.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    final String itemValue = (String) lv0.getItemAtPosition(position);
                    sid = s_no.get(position);
                    List<String> dsip_Students = Arrays.asList(studs.get(position).split(","));
                    setlist(dsip_Students);
                }

            });

        }


    }

}
