package nonactivity.javafunctionality.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sharpenup.customgridview.JSONParser;

@SuppressWarnings("deprecation")
public class StaticMethods {
    Context ctx;
    JSONParser jparser = new JSONParser();
    List<String> categories_handle = new ArrayList<String>();
    Spinner teacher;
    ArrayAdapter<String> faculty;

    public static String addedDate(String date, int days) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String output = sdf1.format(c.getTime());
        //   Toast.makeText(TeacherSubmission.this, "forwarded : " +output, 1).show();
        return output;

    }

    public void setFacultySpinner(ArrayAdapter<String> faculty, List<String> categories_handle) {
        this.faculty = faculty;
        this.categories_handle = categories_handle;
        new TeacherNames().execute();
        this.faculty.notifyDataSetChanged();


    }

    private class TeacherNames extends AsyncTask<String, Void, String> {
        JSONObject json;


        /** progress dialog to show user that the backup is processing. */
        /**
         * application context.
         */

        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup/data_schema.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("schedule");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);


                    String handled_1 = json.getString("handle_by");
                    if (!handled_1.equals(""))
                        categories_handle.add(handled_1);


                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }

    // Drop down layout style - list view with radio button


    // attaching data adapter to spinner


}

