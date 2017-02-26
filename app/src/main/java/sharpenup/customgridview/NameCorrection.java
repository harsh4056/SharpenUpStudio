package sharpenup.customgridview;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MOON on 04-06-2016.
 */
public class NameCorrection extends Activity {
    int iinc = 166;

    JSONParser jparser = new JSONParser();
    TextView tv;
    String absent, present, gen, students;
    String send = "";
    List<String> students_list = new ArrayList<String>();
    List<String> new_students_list = new ArrayList<String>();
    JSONObject json;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namecorrection);
        getActionBar().hide();
        tv = (TextView) findViewById(R.id.counter_data);
        Button b1 = (Button) findViewById(R.id.correct);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new time().execute();
            }
        });
    }


    private class time extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(NameCorrection.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("S_No", "" + iinc));
            //	params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/synchroniseManager.php";


            //@SuppressWarnings("unused")
            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {


                JSONArray account = json.getJSONArray("pendings");
                if (!json.equals("")) {
                    for (int i = 0; i < account.length(); i++) {
                        json = account.getJSONObject(i);
                        String S_No = json.getString("S_No");
                        String STUDENTS = json.getString("STUDENTS");
                        String ABSENT = json.getString("ABSENT");
                        String PRESENT = json.getString("PRESENT");
                        String GEN = json.getString("GEN");
                        if (!ABSENT.equals("") || !PRESENT.equals("") || !GEN.equals("")) {
                            String newStuds[] = (PRESENT + ABSENT + GEN).split(",");
                            List<String> studsNEW = new ArrayList(Arrays.asList(newStuds));
                            List<String> studsOLD = new ArrayList(Arrays.asList(STUDENTS.split(",")));
                            HashMap<String, String> studsNEWHM = new HashMap<String, String>();
                            HashMap<String, String> studsOLDHM = new HashMap<String, String>();
                            int oldSize, newSize;
                            newSize = studsNEW.size();
                            oldSize = studsOLD.size();


                            for (int looper1 = 0; looper1 < studsOLD.size(); looper1++) {

                                studsOLDHM.put(studsOLD.get(looper1).split("-")[0], studsOLD.get(looper1).split("-")[1]);
                            }
                            for (int looper = 0; looper < studsNEW.size(); looper++) {
                                studsOLDHM.put(studsNEW.get(looper).split("-")[0], studsNEW.get(looper).split("-")[1]);
                            }


                            for (Map.Entry<String, String> m : studsOLDHM.entrySet()) {

                                send += (m.getKey() + "-" + m.getValue() + ",");

                            }


                        }


                    }
                } else {
                    iinc++;
                }
                new studentsview().execute();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NameCorrection.this, e.getMessage(), Toast.LENGTH_LONG).show();


            }

      /*  Intent time = new Intent(cnv.this,Main.class);
        startActivity(time); 	overridePendingTransition (R.anim.open_next, R.anim.close_main);
		finish();*/
            pDialog.dismiss();

        }

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(NameCorrection.this, SweetAlertDialog.PROGRESS_TYPE)
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

            params.add(new BasicNameValuePair("S_No", "" + iinc++));
            params.add(new BasicNameValuePair("send", send));
            String url = "http://176.32.230.250/anshuli.com/sharpenup2/NameFixer.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();


            tv.setText("" + (iinc - 1) + "::" + send);
            send = "";
            new time().execute();

        }

    }


}
